package minefantasy.mfr.recipe;

import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * @author AnonymousProductions
 */
public class CustomToolRecipe extends ShapedAnvilRecipes {
    public CustomToolRecipe(int wdth, int heit, ItemStack[] inputs, ItemStack output, String toolType, int time, int hammer, int anvi, boolean hot, String research, Skill skill) {
        super(wdth, heit, inputs, output, toolType, time, hammer, anvi, hot, research, skill);
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    protected boolean checkMatch(InventoryCrafting matrix, int x, int y, boolean b) {
        String wood = "";
        String metal = "";

        for (int matrixX = 0; matrixX < ShapelessAnvilRecipes.globalWidth; ++matrixX) {
            for (int matrixY = 0; matrixY < ShapelessAnvilRecipes.globalHeight; ++matrixY) {
                int recipeX = matrixX - x;
                int recipeY = matrixY - y;
                ItemStack recipeItem = ItemStack.EMPTY;

                if (recipeX >= 0 && recipeY >= 0 && recipeX < this.recipeWidth && recipeY < this.recipeHeight) {
                    if (b) {
                        recipeItem = this.recipeItems[this.recipeWidth - recipeX - 1 + recipeY * this.recipeWidth];
                    } else {
                        recipeItem = this.recipeItems[recipeX + recipeY * this.recipeWidth];
                    }
                }

                ItemStack inputItem = matrix.getStackInRowAndColumn(matrixX, matrixY);

                if (!inputItem.isEmpty() || recipeItem != null && !recipeItem.isEmpty()) {

                    wood = CustomToolHelper.getComponentMaterial(inputItem, "wood");
                    metal = CustomToolHelper.getComponentMaterial(inputItem, "metal");

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
        if (matrix instanceof AnvilCraftMatrix){
            if (!modifyTiers((AnvilCraftMatrix) matrix, metal, true)) {
                modifyTiers((AnvilCraftMatrix) matrix, wood, false);
            }
        }

        return true;
    }

    private boolean modifyTiers(AnvilCraftMatrix matrix, String tier, boolean isMain) {
        CustomMaterial material = CustomMaterial.getMaterial(tier);
        if (material != null) {
            int newTier = recipeHammer < 0 ? material.crafterTier : recipeHammer;
            int newAnvil = anvilTier < 0 ? material.crafterAnvilTier : anvilTier;
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
