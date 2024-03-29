package minefantasy.mfr.block;

import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.mechanics.AmmoMechanics;
import minefantasy.mfr.mechanics.RPGElements;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.List;

public class BlockSalvage extends BasicBlockMF{
	public float dropLevel;

	public BlockSalvage(String name, float dropLevel) {
		super("salvage_" + name, Material.WOOD);

		this.dropLevel = dropLevel;
		this.setSoundType(SoundType.ANVIL);
		this.setHardness(2F);
		this.setResistance(1F);
		this.setCreativeTab(MineFantasyTabs.tabGadget);
	}

	public static float getPlayerDropLevel(EntityPlayer user) {
		float rate = 1.0F;

		if (RPGElements.isSystemActive) {
			int lvl = RPGElements.getLevel(user, Skill.ARTISANRY);
			if (lvl > 10) {
				rate += ((lvl - 10) * 0.01F);
			}
		}
		if (ResearchLogic.hasInfoUnlocked(user, "scrapper")) {
			rate += 0.5F;
		}

		return rate;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}

		EntityItem drop = getDrop(world, pos);
		if (drop != null && !user.isSwingInProgress) {
			ItemStack junk = drop.getItem();
			int amount = Math.min(junk.getCount(), 4);

			for (int a = 0; a < amount; a++) {
				if (salvageItem(world, user, junk, pos)) {
					if (junk.getCount() == 1) {
						AmmoMechanics.dropAmmo(world, junk, pos.getX() + 0.5D, pos.getY() + 1.25D, pos.getZ() + 0.5D);
						AmmoMechanics.dropAmmoCrate(world, junk, pos.getX() + 0.5D, pos.getY() + 1.25D, pos.getZ() + 0.5D);
					}

					drop.getItem().shrink(1);
					if (drop.getItem().getCount() <= 0) {
						drop.setDead();
						return true;
					}
				}
			}
			return true;
		}
		user.sendStatusMessage(new TextComponentTranslation("info.salvage.usage"), true);

		return false;
	}

	private boolean salvageItem(World world, EntityPlayer user, ItemStack junk, BlockPos pos) {
		float modifier = 0.5F;
		ItemStack held = user.getHeldItemMainhand();
		Tool tool = ToolHelper.getToolTypeFromStack(held);
		float efficiency = ToolHelper.getCrafterEfficiency(held);
		if (tool == Tool.SAW) {
			modifier += (efficiency * 0.1F);
			world.playSound(user, pos.add(0.5, 0.5, 0.5), MineFantasySounds.SAW_CARPENTER, SoundCategory.NEUTRAL, 2F, 1F);
		}
		if (tool == Tool.HAMMER || tool == Tool.HEAVY_HAMMER) {
			modifier += (efficiency * 0.05F);
			world.playSound(user, pos.add(0.5, 0.5, 0.5), MineFantasySounds.ANVIL_SUCCEED, SoundCategory.NEUTRAL, 2F, 1F);
		}

		List<ItemStack> salvage = Salvage.salvage(user, junk, dropLevel * getPlayerDropLevel(user) * modifier);

		if (salvage != null) {
			dropSalvage(world, pos, salvage, junk);
			world.playSound(user, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.AMBIENT, 1F, 1F);
			world.playSound(user, pos, SoundEvents.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, SoundCategory.AMBIENT, 0.5F, 1.5F);
			world.playSound(user, pos, SoundEvents.BLOCK_GLASS_HIT, SoundCategory.AMBIENT, 1.0F, 0.5F);
			return true;
		}
		return false;
	}

	private void dropSalvage(World world, BlockPos pos, List<ItemStack> salvage, ItemStack junk) {
		for (ItemStack drop : salvage) {
			if (!drop.isEmpty())// && !user.inventory.addItemStackToInventory(drop))
			{
				entityDropItem(world, pos, drop);
			}
		}
	}

	public EntityItem entityDropItem(World world, BlockPos pos, ItemStack item) {
		if (item.getCount() != 0 && !item.isEmpty()) {
			EntityItem entityitem = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 1.25D, pos.getZ() + 0.5D, item);
			entityitem.setPickupDelay(10);
			world.spawnEntity(entityitem);
			return entityitem;
		}
		return null;
	}

	private EntityItem getDrop(World world, BlockPos pos) {
		AxisAlignedBB box = new AxisAlignedBB(pos.getX() + 0.1D, pos.getY() + 1D, pos.getZ() + 0.1D,
				pos.getX() + 0.9D, pos.getY() + 1.5D, pos.getZ() + 0.9D);
		List<EntityItem> drops = world.getEntitiesWithinAABB(EntityItem.class, box);
		if (drops != null && !drops.isEmpty()) {
			return (drops.get(0));
		}
		return null;
	}
}
