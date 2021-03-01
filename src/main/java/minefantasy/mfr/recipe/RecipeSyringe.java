package minefantasy.mfr.recipe;

import minefantasy.mfr.init.ToolListMFR;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RecipeSyringe implements IRecipe {
	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting matrix, World world) {
		ItemStack syringe = null;
		ItemStack filler = null;

		for (int i = 0; i < matrix.getSizeInventory(); ++i) {
			ItemStack itemstack1 = matrix.getStackInSlot(i);

			if (itemstack1 != null) {
				if (itemstack1.getItem() == ToolListMFR.SYRINGE_EMPTY) {
					syringe = itemstack1;
				} else if (itemstack1.getItem() instanceof ItemPotion) {
					ItemPotion potion = (ItemPotion) itemstack1.getItem();

					if (potion == Items.SPLASH_POTION) {
						return false;
					}

					filler = itemstack1;
				}
			}
		}

		return syringe != null && filler != null;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting matrix) {
		ItemStack syringe = null;
		ItemStack filler = null;

		for (int i = 0; i < matrix.getSizeInventory(); ++i) {
			ItemStack itemstack1 = matrix.getStackInSlot(i);

			if (itemstack1 != null) {
				if (itemstack1.getItem() == ToolListMFR.SYRINGE_EMPTY) {
					syringe = itemstack1;
				} else if (itemstack1.getItem() instanceof ItemPotion) {
					ItemPotion potion = (ItemPotion) itemstack1.getItem();

					if (itemstack1.getItem() == Items.SPLASH_POTION) {
						return null;
					}

					filler = itemstack1;
				}
			}
		}
		if (syringe != null && filler != null) {
			return new ItemStack(ToolListMFR.SYRINGE, 1, filler.getItemDamage());
		}
		return null;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width >= 5 && height >= 5;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	@Override
	public IRecipe setRegistryName(ResourceLocation name) {
		return null;
	}

	@Nullable
	@Override
	public ResourceLocation getRegistryName() {
		return null;
	}

	@Override
	public Class<IRecipe> getRegistryType() {
		return null;
	}
}