package minefantasy.mf2.integration.nei;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.ItemList;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import minefantasy.mf2.api.crafting.refine.QuernRecipes;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class RecipeHandlerQuern extends TemplateRecipeHandler {

	private static ArrayList<RecipePair> recipeList;

	@Override
	public TemplateRecipeHandler newInstance() {
		if (recipeList == null || recipeList.isEmpty()) {
			fillRecipeList();
		}

		return super.newInstance();
	}

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
		GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 122, 80);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (RecipePair recipeInpit : recipeList) {
			if (CustomToolHelper.areEqual(recipeInpit.outputStack, result)) {
				CachedQuernRecipe cachedRecipe = new CachedQuernRecipe(recipeInpit.inputStack, result);
				arecipes.add(cachedRecipe);
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		QuernRecipes output = QuernRecipes.getResult(ingredient);
		if (output != null) {
			CachedQuernRecipe recipe = new CachedQuernRecipe(ingredient, output.result);
			arecipes.add(recipe);
		}
	}

	private void fillRecipeList() {
		recipeList = new ArrayList<RecipePair>();
		for (ItemStack item : ItemList.items) {
			QuernRecipes tempRecipe = QuernRecipes.getResult(item);
			if (tempRecipe != null) {
				recipeList.add(new RecipePair(item, tempRecipe.result));
			}
		}
	}

	private static class RecipePair {
		private ItemStack inputStack;
		private ItemStack outputStack;

		private RecipePair(ItemStack input, ItemStack output) {
			inputStack = input;
			outputStack = output;
		}
	}

	private class CachedQuernRecipe extends CachedRecipe {

		private PositionedStack resultStack;
		private PositionedStack potStack;
		private PositionedStack inputStack;

		private CachedQuernRecipe(ItemStack ingredient, ItemStack result) {
			inputStack = new PositionedStack(ingredient, 81, 9);
			potStack = new PositionedStack(new ItemStack(ComponentListMF.clay_pot), 81, 32);
			resultStack = new PositionedStack(result, 81, 55);
		}

		@Override
		public PositionedStack getIngredient() {
			return inputStack;
		}

		@Override
		public PositionedStack getOtherStack() {
			return potStack;
		}

		@Override
		public PositionedStack getResult() {
			return resultStack;
		}
	}
}
