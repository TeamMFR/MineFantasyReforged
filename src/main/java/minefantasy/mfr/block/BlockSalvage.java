package minefantasy.mfr.block;

import minefantasy.mfr.api.crafting.ISalvageDrop;
import minefantasy.mfr.api.crafting.ISpecialSalvage;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.mechanics.AmmoMechanics;
import minefantasy.mfr.mechanics.RPGElements;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.registry.recipe.CraftingManagerSalvage;
import minefantasy.mfr.registry.recipe.SalvageRecipeBase;
import minefantasy.mfr.registry.recipe.ingredients.IngredientMaterial;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.ToolHelper;
import minefantasy.mfr.util.XSTRandom;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockSalvage extends BasicBlockMF{
	private static final XSTRandom random = new XSTRandom();
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

	private boolean salvageItem(World world, EntityPlayer user, ItemStack inputStack, BlockPos pos) {
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

		List<ItemStack> salvage = salvage(user, inputStack, dropLevel * getPlayerDropLevel(user) * modifier);

		if (salvage != null) {
			dropSalvage(world, pos, salvage);
			world.playSound(user, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.AMBIENT, 1F, 1F);
			world.playSound(user, pos, SoundEvents.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, SoundCategory.AMBIENT, 0.5F, 1.5F);
			world.playSound(user, pos, SoundEvents.BLOCK_GLASS_HIT, SoundCategory.AMBIENT, 1.0F, 0.5F);
			return true;
		}
		return false;
	}

	private void dropSalvage(World world, BlockPos pos, List<ItemStack> salvage) {
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

	public static List<ItemStack> salvage(EntityPlayer user, ItemStack inputStack, float dropRate) {
		SalvageRecipeBase salvageRecipe = CraftingManagerSalvage.findMatchingRecipe(inputStack, user);
		if ((salvageRecipe == null || inputStack.isEmpty()) && !(inputStack.getItem() instanceof ISpecialSalvage)) {
			return null;
		}

		float durability = 1F;
		if (inputStack.isItemDamaged()) {
			durability = (float) (inputStack.getMaxDamage() - inputStack.getItemDamage()) / (float) inputStack.getMaxDamage();
		}

		float chanceModifier = 1.25F;// 80% Succcess rate
		float chance = dropRate * durability;// Modifier for skill and durability

		return dropItems(inputStack, user, salvageRecipe, chanceModifier, chance);
	}

	private static List<ItemStack> dropItems(ItemStack inputStack, EntityPlayer user, SalvageRecipeBase salvageRecipe,
			float chanceModifier, float chance) {
		List<ItemStack> items = new ArrayList<>();

		//Special
		if (inputStack.getItem() instanceof ISpecialSalvage) {
			List<ItemStack> special = ((ISpecialSalvage) inputStack.getItem()).getSalvage(inputStack);
			if (special != null) {
				return special;
			}
		}

		//Normal
		for (Ingredient ingredient : salvageRecipe.getOutputs()) {

			items.addAll(dropItemStack(inputStack, user, ingredient, chanceModifier, chance));
		}

		//Grant XP
		int totalCount = items.stream().mapToInt(ItemStack::getCount).sum();
		if (!user.world.isRemote) {
			salvageRecipe.giveVanillaXp(user, 0, totalCount);
			salvageRecipe.giveSkillXpPerCount(user, 0, totalCount);
		}

		return items;
	}

	private static List<ItemStack> dropItemStack(ItemStack inputStack, EntityPlayer user,
			Ingredient outputIngredient, float chanceModifier, float chance) {

		List<ItemStack> items = new ArrayList<>();

		ItemStack outputStack = outputIngredient.getMatchingStacks()[0];
		for (int a = 0; a < outputStack.getCount(); a++) {
			if (random.nextFloat() * chanceModifier < chance) {
				boolean canSalvage = true;

				if (outputStack.getItem() instanceof ISalvageDrop) {
					canSalvage = ((ISalvageDrop) outputStack.getItem()).canSalvage(user, outputStack);
				}
				if (canSalvage) {
					ItemStack newItem = outputStack.copy();
					newItem.setCount(1);
					if (outputIngredient instanceof IngredientMaterial) {
						CustomToolHelper.tryDeconstruct(newItem, inputStack);
					}
					items.add(newItem);
				}
			}
		}
		return items;
	}
}
