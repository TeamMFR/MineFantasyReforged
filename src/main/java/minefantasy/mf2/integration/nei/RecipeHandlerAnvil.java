package minefantasy.mf2.integration.nei;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import minefantasy.mf2.api.crafting.anvil.CraftingManagerAnvil;
import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.crafting.anvil.ShapedAnvilRecipes;
import minefantasy.mf2.api.crafting.anvil.ShapelessAnvilRecipes;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class RecipeHandlerAnvil extends TemplateRecipeHandler {

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("method.anvil");
	}

	@Override
	public String getGuiTexture() {
		return "minefantasy2:textures/gui/knowledge/anvilGrid.png";
	}

	@Override
	public void drawBackground(int recipe) {
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 5, 22, 166, 147);
	}

	@Override
	public int recipiesPerPage() {
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadCraftingRecipes(ItemStack inputStack) {
		for (IAnvilRecipe irecipe : (List<IAnvilRecipe>) CraftingManagerAnvil.getInstance().getRecipeList()) {
			if (CustomToolHelper.areEqual(irecipe.getRecipeOutput(), inputStack)
					&& ResearchLogic.hasInfoUnlocked(Minecraft.getMinecraft().thePlayer, irecipe.getResearch())) {
				CachedAnvilRecipe recipe = handleRecipe(irecipe, inputStack);

				if (recipe == null) {
					continue;
				}

				arecipes.add(recipe);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		for (IAnvilRecipe irecipe : (List<IAnvilRecipe>) CraftingManagerAnvil.getInstance().getRecipeList()) {
			if (ResearchLogic.hasInfoUnlocked(Minecraft.getMinecraft().thePlayer, irecipe.getResearch())) {
				continue;
			}

			CachedAnvilRecipe recipe = handleRecipe(irecipe, null);
			if (recipe == null || !recipe.contains(recipe.ingredients, ingredient.getItem())) {
				continue;
			}

			if (recipe.contains(recipe.ingredients, ingredient)) {
				recipe.setIngredientPermutation(recipe.ingredients, ingredient);
				arecipes.add(recipe);
			}
		}
	}

	private CachedAnvilRecipe handleRecipe(IAnvilRecipe irecipe, ItemStack inputStack) {
		if (irecipe instanceof ShapedAnvilRecipes) {
			return new CachedAnvilRecipe((ShapedAnvilRecipes) irecipe);
		} else if (irecipe instanceof ShapelessAnvilRecipes) {
			return new CachedAnvilRecipe((ShapelessAnvilRecipes) irecipe);
		}

		return null;
	}

	public class CachedAnvilRecipe extends CachedRecipe {

		public final ArrayList<PositionedStack> ingredients = new ArrayList<PositionedStack>();
		public final PositionedStack result;

		public CachedAnvilRecipe(ShapedAnvilRecipes recipe) {
			result = new PositionedStack(recipe.getRecipeOutput(), 75, 20);
			setShapedRecipeIngredients(recipe.recipeWidth, recipe.recipeHeight, recipe.recipeItems);
		}

		public CachedAnvilRecipe(ShapelessAnvilRecipes recipe) {
			result = new PositionedStack(recipe.getRecipeOutput(), 75, 20);
		}

		public void setShapedRecipeIngredients(int width, int height, Object[] items) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (items[y * width + x] == null)
						continue;

					PositionedStack stack = new PositionedStack(items[y * width + x], 31 + x * 18, 54 + y * 18, false);
					stack.setMaxSize(1);
					ingredients.add(stack);
				}
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
