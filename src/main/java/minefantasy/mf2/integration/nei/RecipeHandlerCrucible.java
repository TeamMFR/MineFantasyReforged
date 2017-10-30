package minefantasy.mf2.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.refine.Alloy;
import minefantasy.mf2.api.refine.AlloyRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class RecipeHandlerCrucible extends TemplateRecipeHandler {

    private String recipeName = "method.crucible";

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal(recipeName);
    }

    @Override
    public String getGuiTexture() {
        return getTexture(0);
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (Alloy alloy : AlloyRecipes.alloys) {
            if (CustomToolHelper.areEqual(alloy.recipeOutput, result)) {
                CachedAlloyRecipe recipe = new CachedAlloyRecipe(alloy);
                arecipes.add(recipe);
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (Alloy alloy : AlloyRecipes.alloys) {
            for (Object object : alloy.recipeItems) {
                ItemStack recipeIngredient = (ItemStack) object;
                if (CustomToolHelper.areEqual(recipeIngredient, ingredient)) {
                    CachedAlloyRecipe recipe = new CachedAlloyRecipe(alloy);
                    arecipes.add(recipe);
                }
            }
        }
    }

    @Override
    public void drawBackground(int recipe) {
        CachedAlloyRecipe cachedRecipe = (CachedAlloyRecipe) arecipes.get(recipe);
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(getTexture(cachedRecipe.tier));
        GuiDraw.drawTexturedModalRect(0, 0, 5, 0, 151, 94);
    }

    private String getTexture(int tier) {
        if (tier == 1) {
            return "minefantasy2:textures/gui/crucible_advanced.png";
        }
        if (tier >= 2) {
            return "minefantasy2:textures/gui/crucible_mythic.png";
        }
        return "minefantasy2:textures/gui/crucible.png";
    }

    private class CachedAlloyRecipe extends CachedRecipe {

        private ArrayList<PositionedStack> ingredients = new ArrayList<PositionedStack>();
        private PositionedStack output;
        private int tier;

        @SuppressWarnings("unchecked")
        private CachedAlloyRecipe(Alloy alloy) {
            setIngridients(alloy.recipeItems);
            output = new PositionedStack(alloy.recipeOutput, 124, 32);
            tier = alloy.level;
        }

        private void setIngridients(List<Object> recipeItems) {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    int index = y * 3 + x;
                    if (recipeItems.size() > index) {
                        ItemStack currentStack = (ItemStack) recipeItems.get(index);
                        if (currentStack == null) {
                            continue;
                        }

                        PositionedStack stack = new PositionedStack(currentStack, 57 + x * 18, 14 + y * 18);
                        ingredients.add(stack);
                    }
                }
            }
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, ingredients);
        }

        @Override
        public PositionedStack getResult() {
            return output;
        }
    }
}
