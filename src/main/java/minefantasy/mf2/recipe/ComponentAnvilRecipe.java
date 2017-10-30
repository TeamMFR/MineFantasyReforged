package minefantasy.mf2.recipe;

import minefantasy.mf2.api.crafting.anvil.ShapedAnvilRecipes;
import minefantasy.mf2.api.crafting.anvil.ShapelessAnvilRecipes;
import minefantasy.mf2.api.heating.Heatable;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.rpg.Skill;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ComponentAnvilRecipe extends ShapedAnvilRecipes {
    public ComponentAnvilRecipe(int wdth, int heit, ItemStack[] inputs, ItemStack output, String toolType, int time,
                                int hammer, int anvi, float exp, boolean hot, String research, Skill skill) {
        super(wdth, heit, inputs, output, toolType, time, hammer, anvi, exp, hot, research, skill);
    }

    protected boolean checkMatch(InventoryCrafting matrix, int x, int y, boolean b) {
        String baseMaterial = null;
        for (int var5 = 0; var5 < ShapelessAnvilRecipes.globalWidth; ++var5) {
            for (int var6 = 0; var6 < ShapelessAnvilRecipes.globalHeight; ++var6) {
                int var7 = var5 - x;
                int var8 = var6 - y;
                ItemStack recipeItem = null;

                if (var7 >= 0 && var8 >= 0 && var7 < this.recipeWidth && var8 < this.recipeHeight) {
                    if (b) {
                        recipeItem = this.recipeItems[this.recipeWidth - var7 - 1 + var8 * this.recipeWidth];
                    } else {
                        recipeItem = this.recipeItems[var7 + var8 * this.recipeWidth];
                    }
                }

                ItemStack inputItem = matrix.getStackInRowAndColumn(var5, var6);

                if (inputItem != null || recipeItem != null) {
                    CustomMaterial material = CustomMaterial.getMaterialFor(inputItem, "base");
                    if (material != null) {
                        if (baseMaterial == null) {
                            baseMaterial = material.name;
                        } else {
                            if (material.name != baseMaterial) {
                                return false;
                            }
                        }
                    }
                    // HEATING
                    if (Heatable.requiresHeating && Heatable.canHeatItem(inputItem)) {
                        return false;
                    }
                    if (!Heatable.isWorkable(inputItem)) {
                        return false;
                    }
                    inputItem = getHotItem(inputItem);

                    if (inputItem == null && recipeItem != null || inputItem != null && recipeItem == null) {
                        return false;
                    }

                    if (inputItem == null) {
                        return false;
                    }

                    if (recipeItem.getItem() != inputItem.getItem()) {
                        return false;
                    }

                    if (recipeItem.getItemDamage() != OreDictionary.WILDCARD_VALUE
                            && recipeItem.getItemDamage() != inputItem.getItemDamage()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
