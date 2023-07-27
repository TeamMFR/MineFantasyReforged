package minefantasy.mfr.recipe;

import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.api.heating.IHotItem;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import java.util.Arrays;

/**
 * @author AnonymousProductions
 */
public class ShapedAnvilRecipes extends AnvilRecipeBase {

	public ShapedAnvilRecipes(NonNullList<Ingredient> inputs, ItemStack output, String toolType, int time, int hammer, int anvil, boolean hot, String research, Skill skill) {
		super(output, inputs, skill, research, toolType, anvil, hammer, time, hot);
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
	public boolean matches(AnvilCraftMatrix matrix) {
		for (int x = 0; x <= ShapelessAnvilRecipes.globalWidth - WIDTH; ++x) {
			for (int y = 0; y <= ShapelessAnvilRecipes.globalHeight - HEIGHT; ++y) {
				if (this.checkMatch(matrix, x, y, true) || this.checkMatch(matrix, x, y, false)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
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
	protected boolean checkMatch(InventoryCrafting matrix, int x, int y, boolean b) {
		for (int matrixX = 0; matrixX < ShapelessAnvilRecipes.globalWidth; ++matrixX) {
			for (int matrixY = 0; matrixY < ShapelessAnvilRecipes.globalHeight; ++matrixY) {
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

				if ((inputItem != null && !inputItem.isEmpty()) || ingredient != null && !ingredient.apply(ItemStack.EMPTY)) {
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

					if (Arrays.stream(ingredient.getMatchingStacks()).anyMatch(stack -> {
						if (stack.getItem().hasContainerItem(stack)) {
							return stack.getItem().getContainerItem(stack).isItemEqual(stack);
						}
						return false;
					})) {
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

	protected ItemStack getHotItem(ItemStack item) {
		if (item.isEmpty())
			return ItemStack.EMPTY;
		if (!(item.getItem() instanceof IHotItem)) {
			return item;
		}

		ItemStack hotItem = Heatable.getItemStack(item);

		if (!hotItem.isEmpty()) {
			return hotItem;
		}

		return item;
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
