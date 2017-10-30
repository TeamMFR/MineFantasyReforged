package minefantasy.mf2.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.ItemList;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.refine.BigFurnaceRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class RecipeHandlerBigFurnace extends TemplateRecipeHandler {

    private static ArrayList<RecipePair> recipeList;

    @Override
    public TemplateRecipeHandler newInstance() {
        if (recipeList == null || recipeList.isEmpty()) {
            fillRecipeList();
        }

        return super.newInstance();
    }

    private void fillRecipeList() {
        recipeList = new ArrayList<RecipePair>();
        for (ItemStack item : ItemList.items) {
            BigFurnaceRecipes tempRecipe = BigFurnaceRecipes.getResult(item);
            if (tempRecipe != null) {
                recipeList.add(new RecipePair(item, tempRecipe.result));
            }
        }
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("nei.method.big_furnace");
    }

    @Override
    public String getGuiTexture() {
        return "minefantasy2:textures/gui/furnace_top.png";
    }

    @Override
    public int recipiesPerPage() {
        return 2;
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 63);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (RecipePair recipePair : recipeList) {
            if (CustomToolHelper.areEqual(recipePair.outputStack, result)) {
                BigFurnaceRecipe cachedRecipe = new BigFurnaceRecipe(recipePair.inputStack, recipePair.outputStack);
                arecipes.add(cachedRecipe);
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        BigFurnaceRecipes recipe = BigFurnaceRecipes.getResult(ingredient);
        if (recipe != null) {
            BigFurnaceRecipe cachedRecipe = new BigFurnaceRecipe(ingredient, recipe.result);
            arecipes.add(cachedRecipe);
        }
    }

    private class RecipePair {
        private ItemStack inputStack;
        private ItemStack outputStack;

        private RecipePair(ItemStack input, ItemStack output) {
            inputStack = input;
            outputStack = output;
        }
    }

    private class BigFurnaceRecipe extends CachedRecipe {

        private PositionedStack input;
        private PositionedStack output;

        private BigFurnaceRecipe(ItemStack inputStack, ItemStack outputStack) {
            input = new PositionedStack(inputStack, 31, 15);
            output = new PositionedStack(outputStack, 102, 16); // Hell of a perfectionist
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
