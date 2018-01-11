package minefantasy.mf2.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import minefantasy.mf2.api.crafting.refine.QuernRecipes;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class RecipeHandlerQuern extends TemplateRecipeHandler {

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("method.quern");
    }

    @Override
    public String getGuiTexture() {
        return "minefantasy2:textures/gui/quern.png";
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 0, 122, 80);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (QuernRecipes recipe : QuernRecipes.recipeList) {
            if (CustomToolHelper.areEqual(recipe.result, result)) {
                CachedQuernRecipe cachedRecipe = new CachedQuernRecipe(recipe);
                arecipes.add(cachedRecipe);
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (ingredient != null) {
            if (ingredient.getItem().equals(ComponentListMF.clay_pot)) {
                for (QuernRecipes recipe : QuernRecipes.recipeList) {
                    CachedQuernRecipe cachedRecipe = new CachedQuernRecipe(recipe);
                    arecipes.add(cachedRecipe);
                }
                return;
            }

            QuernRecipes output = QuernRecipes.getResult(ingredient);
            if (output != null) {
                CachedQuernRecipe recipe = new CachedQuernRecipe(output);
                arecipes.add(recipe);
            }
        }
    }

    private class CachedQuernRecipe extends CachedRecipe {
        private ItemStack input, output;
        private boolean consumePot;

        private CachedQuernRecipe(QuernRecipes recipe) {
            input = recipe.input;
            output = recipe.result;
            consumePot = recipe.consumePot;
        }

        @Override
        public PositionedStack getIngredient() {
            return new PositionedStack(input, 76, 9);
        }

        @Override
        public PositionedStack getOtherStack() {
            if (consumePot) {
                return new PositionedStack(new ItemStack(ComponentListMF.clay_pot), 76, 32);
            }
            return null;
        }

        @Override
        public PositionedStack getResult() {
            return new PositionedStack(output, 76, 55);
        }
    }
}
