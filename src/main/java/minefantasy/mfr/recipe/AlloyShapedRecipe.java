package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public class AlloyShapedRecipe extends AlloyRecipeBase{

	protected int height;
	protected int width;

	public AlloyShapedRecipe(ItemStack output, NonNullList<Ingredient> inputs, int tier,
			String requiredResearch, Skill skill, int skillXp, float xp,
			int height, int width) {
		super(output, inputs, tier, requiredResearch, skill, skillXp, xp);
		this.height = height;
		this.width = width;
	}

	@Override
	public boolean matches(CrucibleCraftMatrix matrix) {
		for (int i = 0; i <= matrix.getWidth() - width; ++i) {
			for (int j = 0; j <= matrix.getHeight() - height; ++j) {
				if (this.checkMatch(matrix, i, j, true)) {
					return true;
				}

				if (this.checkMatch(matrix, i, j, false)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks if the region of a crafting inventory is match for the recipe.
	 */
	protected boolean checkMatch(CrucibleCraftMatrix matrix, int x, int y, boolean mirror) {
		for (int matrixX = 0; matrixX < MAX_WIDTH; ++matrixX) {
			for (int matrixY = 0; matrixY < MAX_HEIGHT; ++matrixY) {
				int recipeX = matrixX - x;
				int recipeY = matrixY - y;
				Ingredient ingredient = Ingredient.EMPTY;

				if (recipeX >= 0 && recipeY >= 0 && recipeX < width && recipeY < height) {
					if (mirror) {
						ingredient = inputs.get(width - recipeX - 1 + recipeY * width);
					} else {
						ingredient = inputs.get(recipeX + recipeY * width);
					}
				}

				ItemStack inputItem = matrix.getStackInRowAndColumn(matrixX, matrixY);

				if ((!inputItem.isEmpty()) || !ingredient.apply(ItemStack.EMPTY)) {
					if (inputItem.isEmpty()) {
						return false;
					}

					if (ingredient.apply(ItemStack.EMPTY)) {
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


	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
}
