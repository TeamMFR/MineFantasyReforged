package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class KitchenBenchShapedRecipe extends KitchenBenchRecipeBase {
	protected int width;
	protected int height;
	protected boolean shouldMirror;

	public KitchenBenchShapedRecipe(
			ItemStack output, NonNullList<Ingredient> inputs,
			int toolTier, int kitchenBenchTier, int craftTime,
			String toolType, SoundEvent soundOfCraft,
			String research, Skill skillUsed, int skillXp, float vanillaXp, int dirtyProgressAmount,
			boolean shouldMirror, int width, int height) {
		super(output, inputs, toolTier, kitchenBenchTier, craftTime,
				toolType, soundOfCraft,
				research, skillUsed, skillXp, vanillaXp, dirtyProgressAmount);
		this.shouldMirror = shouldMirror;
		this.width = width;
		this.height = height;

	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(KitchenBenchCraftMatrix matrix, @Nonnull World world) {
		for (int i = 0; i <= matrix.getWidth() - this.width; ++i) {
			for (int j = 0; j <= matrix.getHeight() - this.height; ++j) {
				if (this.checkMatch(matrix, i, j, true) && shouldMirror) {
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
	private boolean checkMatch(InventoryCrafting matrix, int x, int y, boolean b) {
		for (int matrixX = 0; matrixX < KitchenBenchRecipeBase.MAX_WIDTH; ++matrixX) {
			for (int matrixY = 0; matrixY < KitchenBenchRecipeBase.MAX_HEIGHT; ++matrixY) {
				int recipeX = matrixX - x;
				int recipeY = matrixY - y;
				Ingredient ingredient = Ingredient.EMPTY;

				if (recipeX >= 0 && recipeY >= 0 && recipeX < this.width && recipeY < this.height) {
					if (b) {
						ingredient = this.inputs.get(this.width - recipeX - 1 + recipeY * this.width);
					} else {
						ingredient = this.inputs.get(recipeX + recipeY * this.width);
					}
				}

				ItemStack inputItem = matrix.getStackInRowAndColumn(matrixX, matrixY);

				boolean i = !inputItem.isEmpty();
				boolean j = !ingredient.apply(ItemStack.EMPTY);
				if (i || j) {
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
