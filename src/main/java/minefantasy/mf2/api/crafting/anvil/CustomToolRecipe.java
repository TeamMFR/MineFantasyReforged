package minefantasy.mf2.api.crafting.anvil;

import minefantasy.mf2.api.heating.Heatable;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.rpg.Skill;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * @author AnonymousProductions
 */
public class CustomToolRecipe extends ShapedAnvilRecipes {
    public CustomToolRecipe(int wdth, int heit, ItemStack[] inputs, ItemStack output, String toolType, int time,
                            int hammer, int anvi, boolean hot, String research, Skill skill) {
        super(wdth, heit, inputs, output, toolType, time, hammer, anvi, hot, research, skill);
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    @Override
    protected boolean checkMatch(AnvilCraftMatrix matrix, int x, int y, boolean b) {
        String wood = null;
        String metal = null;
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
                    String component_wood = CustomToolHelper.getComponentMaterial(inputItem, "wood");
                    String component_metal = CustomToolHelper.getComponentMaterial(inputItem, "metal");

                    if (component_metal != null)// CHECK CUSTOM METAL
                    {
                        if (metal == null) {
                            metal = component_metal;
                        } else {
                            if (!metal.equalsIgnoreCase(component_metal)) {
                                return false;
                            }
                        }
                    }

                    if (component_wood != null)// CHECK CUSTOM WOOD
                    {
                        if (wood == null) {
                            wood = component_wood;
                        } else {
                            if (!wood.equalsIgnoreCase(component_wood)) {
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
                    if (!CustomToolHelper.doesMatchForRecipe(recipeItem, inputItem)) {
                        return false;
                    }
                }
            }
        }
        if (!modifyTiers(matrix, metal, true)) {
            modifyTiers(matrix, wood, false);
        }
        return true;
    }

    private boolean modifyTiers(AnvilCraftMatrix matrix, String tier, boolean isMain) {
        CustomMaterial material = CustomMaterial.getMaterial(tier);
        if (material != null) {
            int newTier = recipeHammer < 0 ? material.crafterTier : recipeHammer;
            int newAnvil = anvil < 0 ? material.crafterAnvilTier : anvil;
            matrix.modifyTier(newTier, newAnvil, (int) (recipeTime * material.craftTimeModifier));
            if (isMain) {
                matrix.modifyResearch("smelt" + material.getName());
            }
            return true;
        }
        return false;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public ItemStack getCraftingResult(AnvilCraftMatrix matrix) {
        ItemStack result = super.getCraftingResult(matrix);

        String wood = null;
        String metal = null;
        for (int i = 0; i < matrix.getSizeInventory(); i++) {
            ItemStack item = matrix.getStackInSlot(i);
            String component_wood = CustomToolHelper.getComponentMaterial(item, "wood");
            String component_metal = CustomToolHelper.getComponentMaterial(item, "metal");
            if (wood == null && component_wood != null) {
                wood = component_wood;
            }
            if (metal == null && component_metal != null) {
                metal = component_metal;
            }
        }
        if (metal != null) {
            CustomMaterial.addMaterial(result, CustomToolHelper.slot_main, metal);
        }
        if (wood != null) {
            CustomMaterial.addMaterial(result, CustomToolHelper.slot_haft, wood);
        }
        return result;
    }

    @Override
    public boolean useCustomTiers() {
        return true;
    }
}
