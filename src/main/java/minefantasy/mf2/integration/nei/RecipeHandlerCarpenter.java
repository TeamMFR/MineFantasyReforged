package minefantasy.mf2.integration.nei;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import minefantasy.mf2.api.crafting.carpenter.CraftingManagerCarpenter;
import minefantasy.mf2.api.crafting.carpenter.ICarpenterRecipe;
import minefantasy.mf2.api.crafting.carpenter.ShapedCarpenterRecipes;
import minefantasy.mf2.api.crafting.carpenter.ShapelessCarpenterRecipes;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class RecipeHandlerCarpenter extends TemplateRecipeHandler {

	public int[][] stackorder = new int[][] { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 }, { 0, 2 }, { 1, 2 }, { 2, 0 },
			{ 2, 1 }, { 2, 2 } };

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("method.carpenter");
	}

	@Override
	public String getGuiTexture() {
		return"minefantasy2:textures/gui/knowledge/carpenterGrid.png";
	}

	@Override
	public int recipiesPerPage() {
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (ICarpenterRecipe irecipe : (List<ICarpenterRecipe>) CraftingManagerCarpenter.getInstance()
				.getRecipeList()) {
			if (CustomToolHelper.areEqual(irecipe.getRecipeOutput(), result)
					&& ResearchLogic.hasInfoUnlocked(Minecraft.getMinecraft().thePlayer, irecipe.getResearch())) {
				CachedCarpenterRecipe recipe = handleRecipe(irecipe);

				if (recipe == null)
					continue;

				recipe.computeVisuals();
				arecipes.add(recipe);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		for (ICarpenterRecipe irecipe : (List<ICarpenterRecipe>) CraftingManagerCarpenter.getInstance()
				.getRecipeList()) {

			if (ResearchLogic.hasInfoUnlocked(Minecraft.getMinecraft().thePlayer, irecipe.getResearch())) {
				continue;
			}

			CachedCarpenterRecipe recipe = handleRecipe(irecipe);

			if (recipe == null || !recipe.contains(recipe.ingredients, ingredient.getItem()))
				continue;

			recipe.computeVisuals();
			if (recipe.contains(recipe.ingredients, ingredient)) {
				recipe.setIngredientPermutation(recipe.ingredients, ingredient);
				arecipes.add(recipe);
			}
		}
	}

	@Override
	public void drawBackground(int recipe) {
		GL11.glColor3f(255, 255, 255);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 5, 33, 166, 171);
	}

	private CachedCarpenterRecipe handleRecipe(ICarpenterRecipe irecipe) {
		if (irecipe instanceof ShapedCarpenterRecipes) {
			return new CachedCarpenterRecipe((ShapedCarpenterRecipes) irecipe);
		} else if (irecipe instanceof ShapelessCarpenterRecipes) {
			return new CachedCarpenterRecipe((ShapelessCarpenterRecipes) irecipe);
		}
		return null;
	}

	class CachedCarpenterRecipe extends CachedRecipe {

		ICarpenterRecipe carpRecipe;
		ArrayList<PositionedStack> ingredients = new ArrayList<PositionedStack>();
		PositionedStack result;

		public CachedCarpenterRecipe(ShapedCarpenterRecipes recipe) {
			carpRecipe = recipe;
			result = new PositionedStack(recipe.getRecipeOutput(), 75, 8);
			setIngredients(recipe.recipeWidth, recipe.recipeHeight, recipe.recipeItems);
		}

		@SuppressWarnings("unchecked")
		public CachedCarpenterRecipe(ShapelessCarpenterRecipes recipe) {
			carpRecipe = recipe;
			result = new PositionedStack(recipe.getRecipeOutput(), 75, 8);
			setIngredients(recipe.recipeItems);
		}

		public void setIngredients(List<PositionedStack> items) {
			ingredients.clear();
			for (int ingred = 0; ingred < items.size(); ingred++) {
				MFPositionedStack stack = new MFPositionedStack(items.get(ingred), 41 + stackorder[ingred][0] * 18,
						47 + stackorder[ingred][1] * 18);
				stack.setMaxSize(1);
				ingredients.add(stack);
			}
		}

		public void setIngredients(int width, int height, Object[] items) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (items[y * width + x] == null)
						continue;

					MFPositionedStack stack = new MFPositionedStack(items[y * width + x], 41 + x * 18, 47 + y * 18,
							false);
					stack.setMaxSize(1);
					ingredients.add(stack);
				}
			}
		}

		public void computeVisuals() {
			for (PositionedStack p : ingredients) {
				p.generatePermutations();
			}
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, ingredients);
		}

		@Override
		public PositionedStack getResult() {
			return result;
		}
	}
}