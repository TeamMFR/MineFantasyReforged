package minefantasy.mf2.api.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;

import java.util.HashMap;

public class BasicTierRecipe extends ShapedRecipes {

    public BasicTierRecipe(int width, int height, ItemStack[] input, ItemStack output) {
        super(width, height, input, output);
    }

    public static BasicTierRecipe add(ItemStack result, Object... input) {
        String var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        int var9;

        if (input[var4] instanceof String[]) {
            String[] var7 = ((String[]) input[var4++]);
            String[] var8 = var7;
            var9 = var7.length;

            for (int var10 = 0; var10 < var9; ++var10) {
                String var11 = var8[var10];
                ++var6;
                var5 = var11.length();
                var3 = var3 + var11;
            }
        } else {
            while (input[var4] instanceof String) {
                String var13 = (String) input[var4++];
                ++var6;
                var5 = var13.length();
                var3 = var3 + var13;
            }
        }

        HashMap var14;

        for (var14 = new HashMap(); var4 < input.length; var4 += 2) {
            Character var16 = (Character) input[var4];
            ItemStack var17 = null;

            if (input[var4 + 1] instanceof Item) {
                var17 = new ItemStack((Item) input[var4 + 1], 1, 32767);
            } else if (input[var4 + 1] instanceof Block) {
                var17 = new ItemStack((Block) input[var4 + 1], 1, 32767);
            } else if (input[var4 + 1] instanceof ItemStack) {
                var17 = (ItemStack) input[var4 + 1];
            }

            var14.put(var16, var17);
        }

        ItemStack[] var15 = new ItemStack[var5 * var6];

        for (var9 = 0; var9 < var5 * var6; ++var9) {
            char var18 = var3.charAt(var9);

            if (var14.containsKey(Character.valueOf(var18))) {
                var15[var9] = ((ItemStack) var14.get(Character.valueOf(var18))).copy();
            } else {
                var15[var9] = null;
            }
        }

        BasicTierRecipe recipe = new BasicTierRecipe(var5, var6, var15, result);
        GameRegistry.addRecipe(recipe);
        return recipe;
    }

    @Override
    public boolean matches(InventoryCrafting matrix, World world) {
        for (int i = 0; i <= 3 - this.recipeWidth; ++i) {
            for (int j = 0; j <= 3 - this.recipeHeight; ++j) {
                if (this.checkTierMatch(matrix, i, j, true)) {
                    return true;
                }

                if (this.checkTierMatch(matrix, i, j, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    private boolean checkTierMatch(InventoryCrafting matrix, int x, int y, boolean mirror) {
        String wood = null;
        String metal = null;
        for (int k = 0; k < 3; ++k) {
            for (int l = 0; l < 3; ++l) {
                int i1 = k - x;
                int j1 = l - y;
                ItemStack itemstack = null;

                if (i1 >= 0 && j1 >= 0 && i1 < this.recipeWidth && j1 < this.recipeHeight) {
                    if (mirror) {
                        itemstack = this.recipeItems[this.recipeWidth - i1 - 1 + j1 * this.recipeWidth];
                    } else {
                        itemstack = this.recipeItems[i1 + j1 * this.recipeWidth];
                    }
                }

                ItemStack itemstack1 = matrix.getStackInRowAndColumn(k, l);

                if (itemstack1 != null || itemstack != null) {
                    // String recipe_wood = CustomToolHelper.getComponentMaterial(itemstack,
                    // "wood");
                    String recipe_metal = CustomToolHelper.getComponentMaterial(itemstack, "metal");

                    // String component_wood = CustomToolHelper.getComponentMaterial(itemstack1,
                    // "wood");
                    String component_metal = CustomToolHelper.getComponentMaterial(itemstack1, "metal");

                    if (recipe_metal != null) {
                        if (component_metal == null)
                            return false;
                        if (!component_metal.equalsIgnoreCase(recipe_metal))
                            return false;
                    }

                    if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null) {
                        return false;
                    }

                    if (itemstack.getItem() != itemstack1.getItem()) {
                        return false;
                    }

                    if (itemstack.getItemDamage() != 32767 && itemstack.getItemDamage() != itemstack1.getItemDamage()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
