package minefantasy.mfr.item;

import minefantasy.mfr.api.heating.IQuenchBlock;
import minefantasy.mfr.constants.Rarity;
import minefantasy.mfr.init.MineFantasyTabs;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemHide extends ItemBaseMFR {
	private final Item result;
	private final float hardness;

	public ItemHide(String name, Item result, float hardness) {
		super(name, Rarity.POOR);
		this.result = result;
		this.hardness = hardness;
		setCreativeTab(MineFantasyTabs.tabMaterials);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		// from net.minecraft.item.Item.getSubItems to avoid the override in minefantasy.mfr.item.ItemComponentMFR.getSubItems
		if (this.isInCreativeTab(tab))
		{
			items.add(new ItemStack(this));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack item = player.getHeldItem(hand);
		RayTraceResult rayTraceResult = this.rayTrace(world, player, true);

		if (rayTraceResult == null) {
			return ActionResult.newResult(EnumActionResult.PASS, item);
		} else {
			if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
				if (!world.canMineBlockBody(player, rayTraceResult.getBlockPos())) {
					return ActionResult.newResult(EnumActionResult.PASS, item);
				}

				if (!player.canPlayerEdit(rayTraceResult.getBlockPos(), rayTraceResult.sideHit, item)) {
					return ActionResult.newResult(EnumActionResult.PASS, item);
				}

				if (isWaterSource(world, rayTraceResult.getBlockPos())) {
					tryClean(item, world, player, rayTraceResult.getBlockPos(), hand);
				}
			}

			return ActionResult.newResult(EnumActionResult.FAIL, item);
		}
	}

	private void tryClean(ItemStack item, World world, EntityPlayer player, BlockPos pos, EnumHand hand) {
		player.swingArm(hand);
		if (!world.isRemote) {
			if (world.rand.nextFloat() * 2 * hardness < 1.0F) {
				item.shrink(1);
				EntityItem resultItem = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(result));
				world.spawnEntity(resultItem);
			}
		}
		world.playSound(player, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.AMBIENT, 0.125F + world.rand.nextFloat() / 4F, 0.5F + world.rand.nextFloat());
		for (int a = 0; a < 5; a++) {
			world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, pos.getX() + world.rand.nextInt(2), pos.getY() + world.rand.nextInt(2), pos.getZ() + world.rand.nextInt(2), 0, 0.065F + world.rand.nextInt(a + 1), 0);
		}
	}

	private boolean isWaterSource(World world, BlockPos pos) {
		if (world.getBlockState(pos).getMaterial() == Material.WATER) {
			return true;
		}
		if (isCauldron(world, pos)) {
			return true;
		}
		if (isTrough(world, pos)) {
			return true;
		}
		return false;
	}

	public boolean isTrough(World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		return tile instanceof IQuenchBlock;
	}

	public boolean isCauldron(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() == Blocks.CAULDRON;
	}
}
