package minefantasy.mfr.registry.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.registry.material.CustomMaterialRegistry;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class CarpenterShapedCustomMaterialRecipe extends CarpenterRecipeBase {
	protected int width;
	protected int height;
	protected boolean tierModifyOutputCount;

	public CarpenterShapedCustomMaterialRecipe(
			ItemStack output, NonNullList<Ingredient> inputs,
			int toolTier, int carpenterTier, int craftTime,
			int skillXp, float vanillaXp, String toolType, SoundEvent soundOfCraft,
			String research, Skill skillUsed, boolean tierModifyOutputCount,
			int width, int height) {
		super(output, inputs, toolTier, carpenterTier, craftTime,
				skillXp, vanillaXp, toolType, soundOfCraft, research, skillUsed);
		this.width = width;
		this.height = height;
		this.tierModifyOutputCount = tierModifyOutputCount;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(CarpenterCraftMatrix matrix, World worldIn) {
		for (int i = 0; i <= matrix.getWidth() - this.width; ++i) {
			for (int j = 0; j <= matrix.getHeight() - this.height; ++j) {
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

	private boolean checkMatch(CarpenterCraftMatrix matrix, int x, int y, boolean b) {
		String wood = null;
		String metal = null;

		for (int matrixX = 0; matrixX < MAX_WIDTH; ++matrixX) {
			for (int matrixY = 0; matrixY < MAX_HEIGHT; ++matrixY) {
				int recipeX = matrixX - x;
				int recipeY = matrixY - y;
				Ingredient ingredient = Ingredient.EMPTY;

				if (recipeX >= 0 && recipeY >= 0 && recipeX < width && recipeY < height) {
					if (b) {
						ingredient = inputs.get(width - recipeX - 1 + recipeY * width);
					} else {
						ingredient = inputs.get(recipeX + recipeY * width);
					}
				}

				ItemStack inputItem = matrix.getStackInRowAndColumn(matrixX, matrixY);

				if (!inputItem.isEmpty() || !ingredient.apply(ItemStack.EMPTY)) {
					String component_wood = CustomToolHelper.getComponentMaterial(inputItem, CustomMaterialType.WOOD_MATERIAL);
					String component_metal = CustomToolHelper.getComponentMaterial(inputItem, CustomMaterialType.METAL_MATERIAL);

					if (component_metal != null)// CHECK CUSTOM METAL
					{
						if (metal == null) {
							metal = component_metal;
						} else {
							if (!metal.equalsIgnoreCase(component_metal)) {
								return false;
							}
						}
					}

					if (component_wood != null)// CHECK CUSTOM WOOD
					{
						if (wood == null) {
							wood = component_wood;
						} else {
							if (!wood.equalsIgnoreCase(component_wood)) {
								return false;
							}
						}
					}

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
		if (!modifyTiers(matrix, metal)) {
			modifyTiers(matrix, wood);
		}

		return true;
	}

	@Override
	public ItemStack getCraftingResult(CarpenterCraftMatrix matrix) {
		ItemStack result = super.getCraftingResult(matrix);

		String wood = null;
		String metal = null;
		for (int i = 0; i < matrix.getSizeInventory(); i++) {
			ItemStack item = matrix.getStackInSlot(i);
			if (!item.isEmpty()) {
				String component_wood = CustomToolHelper.getComponentMaterial(item, CustomMaterialType.WOOD_MATERIAL);
				String component_metal = CustomToolHelper.getComponentMaterial(item, CustomMaterialType.METAL_MATERIAL);
				if (wood == null && component_wood != null) {
					wood = component_wood;
				}
				if (metal == null && component_metal != null) {
					metal = component_metal;
				}
			}
		}
		if (metal != null && !tierModifyOutputCount) {
			CustomMaterialRegistry.addMaterial(result, CustomToolHelper.slot_main, metal);
		}
		if (wood != null && !tierModifyOutputCount) {
			CustomMaterialRegistry.addMaterial(result, metal == null ? CustomToolHelper.slot_main : CustomToolHelper.slot_haft,
					wood);
		}

		if (tierModifyOutputCount) {
			int modifiedCount = MathHelper.clamp(
					CustomMaterialRegistry.getMaterial(wood).getTier() * result.getCount(),
					1,
					result.getMaxStackSize());
			result.setCount(modifiedCount);
		}

		return result;
	}

	public boolean useCustomTiers() {
		return true;
	}

	@Override
	public boolean isTierModifyOutputCount() {
		return tierModifyOutputCount;
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
