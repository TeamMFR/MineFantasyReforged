package minefantasy.mf2.recipe;

import minefantasy.mf2.item.armour.ItemArmourMF;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;

public class RecipeArmourDyeMF implements IRecipe {
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
    public boolean matches(InventoryCrafting matrix, World world) {
        ItemStack itemstack = null;
        ArrayList arraylist = new ArrayList();

        for (int i = 0; i < matrix.getSizeInventory(); ++i) {
            ItemStack itemstack1 = matrix.getStackInSlot(i);

            if (itemstack1 != null) {
                if (itemstack1.getItem() instanceof ItemArmourMF) {
                    ItemArmourMF itemarmor = (ItemArmourMF) itemstack1.getItem();

                    if (!itemarmor.canColour() || itemstack != null) {
                        return false;
                    }

                    itemstack = itemstack1;
                } else {
                    if (itemstack1.getItem() != Items.dye) {
                        return false;
                    }

                    arraylist.add(itemstack1);
                }
            }
        }

        return itemstack != null && !arraylist.isEmpty();
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting matrix) {
        ItemStack itemstack = null;
        int[] aint = new int[3];
        int i = 0;
        int j = 0;
        ItemArmourMF itemarmor = null;
        int k;
        int l;
        float f;
        float f1;
        int l1;

        for (k = 0; k < matrix.getSizeInventory(); ++k) {
            ItemStack itemstack1 = matrix.getStackInSlot(k);

            if (itemstack1 != null) {
                if (itemstack1.getItem() instanceof ItemArmourMF) {
                    itemarmor = (ItemArmourMF) itemstack1.getItem();

                    if (!itemarmor.canColour() || itemstack != null) {
                        return null;
                    }

                    itemstack = itemstack1.copy();
                    itemstack.stackSize = 1;

                    if (itemarmor.hasColor(itemstack1)) {
                        l = itemarmor.getColor(itemstack);
                        f = (l >> 16 & 255) / 255.0F;
                        f1 = (l >> 8 & 255) / 255.0F;
                        float f2 = (l & 255) / 255.0F;
                        i = (int) (i + Math.max(f, Math.max(f1, f2)) * 255.0F);
                        aint[0] = (int) (aint[0] + f * 255.0F);
                        aint[1] = (int) (aint[1] + f1 * 255.0F);
                        aint[2] = (int) (aint[2] + f2 * 255.0F);
                        ++j;
                    }
                } else {
                    if (itemstack1.getItem() != Items.dye) {
                        return null;
                    }

                    float[] afloat = EntitySheep.fleeceColorTable[BlockColored
                            .func_150032_b(itemstack1.getItemDamage())];
                    int j1 = (int) (afloat[0] * 255.0F);
                    int k1 = (int) (afloat[1] * 255.0F);
                    l1 = (int) (afloat[2] * 255.0F);
                    i += Math.max(j1, Math.max(k1, l1));
                    aint[0] += j1;
                    aint[1] += k1;
                    aint[2] += l1;
                    ++j;
                }
            }
        }

        if (itemarmor == null) {
            return null;
        } else {
            k = aint[0] / j;
            int i1 = aint[1] / j;
            l = aint[2] / j;
            f = (float) i / (float) j;
            f1 = Math.max(k, Math.max(i1, l));
            k = (int) (k * f / f1);
            i1 = (int) (i1 * f / f1);
            l = (int) (l * f / f1);
            l1 = (k << 8) + i1;
            l1 = (l1 << 8) + l;
            itemarmor.func_82813_b(itemstack, l1);
            return itemstack;
        }
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