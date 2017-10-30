package minefantasy.mf2.recipe;

import minefantasy.mf2.item.list.ToolListMF;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

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
                if (itemstack1.getItem() == ToolListMF.syringe_empty) {
                    syringe = itemstack1;
                } else if (itemstack1.getItem() instanceof ItemPotion) {
                    ItemPotion potion = (ItemPotion) itemstack1.getItem();

                    if (potion.isSplash(itemstack1.getItemDamage())) {
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
                if (itemstack1.getItem() == ToolListMF.syringe_empty) {
                    syringe = itemstack1;
                } else if (itemstack1.getItem() instanceof ItemPotion) {
                    ItemPotion potion = (ItemPotion) itemstack1.getItem();

                    if (potion.isSplash(itemstack1.getItemDamage())) {
                        return null;
                    }

                    filler = itemstack1;
                }
            }
        }
        if (syringe != null && filler != null) {
            return new ItemStack(ToolListMF.syringe, 1, filler.getItemDamage());
        }
        return null;
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
    public int getRecipeSize() {
        return 10;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}