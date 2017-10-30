package minefantasy.mf2.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import minefantasy.mf2.api.crafting.tanning.TanningRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.Arrays;

public class RecipeHandlerTanning extends TemplateRecipeHandler {

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("tile.tannerStrong.name");
    }

    @Override
    public String getGuiTexture() {
        return "minefantasy2:textures/gui/quern.png"; // need to draw texture
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (TanningRecipe recipe : TanningRecipe.recipeList) {
            if (NEIServerUtils.areStacksSameTypeCrafting(result, recipe.output)) {
                TanningPair cachedRecipe = new TanningPair(recipe);
                arecipes.add(cachedRecipe);
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        TanningRecipe recipe = TanningRecipe.getRecipe(ingredient);
        if (recipe != null) {
            TanningPair cachedRecipe = new TanningPair(recipe);
            cachedRecipe.setIngredientPermutation(Arrays.asList(cachedRecipe.input), ingredient);
            arecipes.add(cachedRecipe);
        }
    }

    @Override
    public void drawExtras(int recipe) {
        TanningPair cachedRecipe = (TanningPair) this.arecipes.get(recipe);
        GuiDraw.drawString(String.format("%s: %s", StatCollector.translateToLocal("nei.method.tanning.tool"),
                cachedRecipe.toolType), 10, 85, -16777216, false);
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    private class TanningPair extends CachedRecipe {

        private PositionedStack input;
        private PositionedStack output;
        private String toolType;

        private TanningPair(TanningRecipe recipe) {
            input = new PositionedStack(recipe.input, 50, 20);
            output = new PositionedStack(recipe.output, 100, 20);
            toolType = recipe.toolType;
        }

        @Override
        public PositionedStack getOtherStack() {
            return input;
        }

        @Override
        public PositionedStack getResult() {
            return output;
        }
    }
}
