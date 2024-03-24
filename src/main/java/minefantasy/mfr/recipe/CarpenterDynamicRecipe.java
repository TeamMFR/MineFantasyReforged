package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.material.WoodMaterial;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;

public class CarpenterDynamicRecipe extends CarpenterRecipeBase {
	protected int width;
	protected int height;
	public CarpenterDynamicRecipe(
			ItemStack output, NonNullList<Ingredient> inputs,
			int toolTier, int carpenterTier, int craftTime,
			int skillXp, float vanillaXp, String toolType, SoundEvent soundOfCraft,
			String research, Skill skillUsed,
			int width, int height) {
		super(output, inputs, toolTier, carpenterTier, craftTime,
				skillXp, vanillaXp, toolType, soundOfCraft, research, skillUsed);
		this.width = width;
		this.height = height;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(CarpenterCraftMatrix matrix, @Nonnull World worldIn) {
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

	protected boolean checkMatch(CarpenterCraftMatrix matrix, int startX, int startY, boolean mirror) {
		for (int matrixX = 0; matrixX < MAX_WIDTH; matrixX++) {
			for (int matrixY = 0; matrixY < MAX_HEIGHT; matrixY++) {
				int recipeX = matrixX - startX;
				int recipeY = matrixY - startY;
				Ingredient target = Ingredient.EMPTY;

				if (recipeX >= 0 && recipeY >= 0 && recipeX < width && recipeY < height) {
					if (mirror) {
						target = inputs.get(width - recipeX - 1 + recipeY * width);
					} else {
						target = inputs.get(recipeX + recipeY * width);
					}
				}
				if (!target.apply(matrix.getStackInRowAndColumn(matrixX, matrixY))) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public ItemStack getCraftingResult(CarpenterCraftMatrix matrix) {
		ItemStack inputStack = ItemStack.EMPTY;
		for (int i = 0; i < matrix.getSizeInventory(); i++) {
			ItemStack stackInSlot = matrix.getStackInSlot(i);
			if (!stackInSlot.isEmpty() && getIngredients().get(0).apply(stackInSlot)) {
				inputStack = stackInSlot;
			}
		}
		ItemStack outputModified = output.copy();
		CustomMaterial inputMaterial = CustomMaterial.NONE;
		for (CustomMaterial material : CustomMaterial.getList("wood")) {
			if (material instanceof WoodMaterial) {
				Item materialItem = ForgeRegistries.ITEMS.getValue(((WoodMaterial) material).inputItemResourceLocation);
				if (materialItem != null) {
					ItemStack materialItemStack = new ItemStack(materialItem, 1, ((WoodMaterial) material).inputItemMeta);
					if (inputStack.isItemEqual(materialItemStack)) {
						inputMaterial = material;
					}
				}
			}
		}
		if (inputMaterial == CustomMaterial.NONE) {
			inputMaterial = CustomMaterial.getMaterial(Constants.SCRAP_WOOD_TAG);
		}
		CustomMaterial.addMaterial(outputModified, CustomToolHelper.slot_main, inputMaterial.name);
		return outputModified;
	}

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
