package minefantasy.mfr.recipe;

import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

/**
 * @author AnonymousProductions
 */
public class ShapedAnvilRecipes extends AnvilRecipeBase {

	public ShapedAnvilRecipes(NonNullList<Ingredient> inputs, ItemStack output, String toolType, int craftTime, int hammerTier, int anvilTier, boolean hotOutput, String requiredResearch, Skill requiredSkill) {
		super(inputs, output, toolType, craftTime, hammerTier, anvilTier, hotOutput, requiredResearch, requiredSkill);
	}

	@Override
	public ItemStack getAnvilRecipeOutput() {
		return this.output;
	}

	@Override
	public int getCraftTime() {
		return craftTime;
	}

	@Override
	public int getHammerTier() {
		return hammerTier;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(AnvilCraftMatrix inv, World worldIn) {
		for (int i = 0; i <= inv.getWidth() - WIDTH; ++i) {
			for (int j = 0; j <= inv.getHeight() - HEIGHT; ++j) {
				if (this.checkMatch(inv, i, j, true)) {
					return true;
				}

				if (this.checkMatch(inv, i, j, false)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks if the region of a crafting inventory is match for the recipe.
	 */
	protected boolean checkMatch(AnvilCraftMatrix matrix, int x, int y, boolean b) {
		for (int matrixX = 0; matrixX < WIDTH; ++matrixX) {
			for (int matrixY = 0; matrixY < HEIGHT; ++matrixY) {
				int recipeX = matrixX - x;
				int recipeY = matrixY - y;
				Ingredient ingredient = Ingredient.EMPTY;

				if (recipeX >= 0 && recipeY >= 0 && recipeX < WIDTH && recipeY < HEIGHT) {
					if (b) {
						ingredient = inputs.get(WIDTH - recipeX - 1 + recipeY * WIDTH);
					} else {
						ingredient = inputs.get(recipeX + recipeY * WIDTH);
					}
				}

				ItemStack inputItem = matrix.getStackInRowAndColumn(matrixX, matrixY);

				if ((!inputItem.isEmpty()) || !ingredient.apply(ItemStack.EMPTY)) {
					// HEATING
					if (Heatable.requiresHeating && Heatable.canHeatItem(inputItem)) {
						return false;
					}
					if (!Heatable.isWorkable(inputItem)) {
						return false;
					}
					inputItem = getHotItem(inputItem);

					if (inputItem == null && ingredient != null || inputItem != null && ingredient == null) {
						return false;
					}

					if (inputItem == null) {
						return false;
					}

					if (!ingredient.apply(inputItem)) {
						return false;
					}

					if (!CustomToolHelper.doesMatchForRecipe(ingredient, inputItem)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(AnvilCraftMatrix matrix) {
		return output.copy();
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return WIDTH * HEIGHT;
	}

	@Override
	public int getAnvilTier() {
		return anvilTier;
	}

	@Override
	public boolean outputHot() {
		return hotOutput;
	}

	@Override
	public String getToolType() {
		return toolType;
	}

	@Override
	public String getResearch() {
		return requiredResearch;
	}

	@Override
	public Skill getSkill() {
		return requiredSkill;
	}

	@Override
	public boolean useCustomTiers() {
		return false;
	}
}
