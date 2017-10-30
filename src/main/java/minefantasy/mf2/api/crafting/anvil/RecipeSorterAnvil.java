package minefantasy.mf2.api.crafting.anvil;

import java.util.Comparator;

/**
 * @author AnonymousProductions
 */
class RecipeSorterAnvil implements Comparator {
    final CraftingManagerAnvil craftingManager;

    RecipeSorterAnvil(CraftingManagerAnvil manager) {
        this.craftingManager = manager;
    }

    public int compareRecipes(IAnvilRecipe recipe1, IAnvilRecipe recipe2) {
        return recipe1 instanceof ShapelessAnvilRecipes && recipe2 instanceof ShapedAnvilRecipes ? 1
                : (recipe2 instanceof ShapelessAnvilRecipes && recipe1 instanceof ShapedAnvilRecipes ? -1
                : (recipe2.getRecipeSize() < recipe1.getRecipeSize() ? -1
                : (recipe2.getRecipeSize() > recipe1.getRecipeSize() ? 1 : 0)));
    }

    @Override
    public int compare(Object input1, Object input2) {
        return this.compareRecipes((IAnvilRecipe) input1, (IAnvilRecipe) input2);
    }
}
