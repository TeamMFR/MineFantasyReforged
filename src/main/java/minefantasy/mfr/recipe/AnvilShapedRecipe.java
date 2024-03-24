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
public class AnvilShapedRecipe extends AnvilRecipeBase {
	protected int width;
	protected int height;

	public AnvilShapedRecipe(NonNullList<Ingredient> inputs, ItemStack output, String toolType,
			int craftTime, int hammerTier, int anvilTier,
			boolean hotOutput, String requiredResearch, Skill requiredSkill,
			int skillXp, float vanillaXp,
			int width, int height) {
		super(inputs, output, toolType, craftTime, hammerTier, anvilTier, hotOutput,
				requiredResearch, requiredSkill, skillXp, vanillaXp);
		this.width = width;
		this.height = height;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(AnvilCraftMatrix inv, World worldIn) {
		for (int i = 0; i <= inv.getWidth() - width; ++i) {
			for (int j = 0; j <= inv.getHeight() - height; ++j) {
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
	protected boolean checkMatch(AnvilCraftMatrix matrix, int x, int y, boolean mirror) {
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

					if (inputItem.isEmpty()) {
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
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return width * height;
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
