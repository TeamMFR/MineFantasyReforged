package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarpenterShapelessCustomMaterialRecipe extends CarpenterRecipeBase {

	public CarpenterShapelessCustomMaterialRecipe(
			ItemStack output, NonNullList<Ingredient> inputs,
			int toolTier, int carpenterTier, int craftTime,
			int skillXp, float vanillaXp,
			String toolType, SoundEvent soundOfCraft, String research, Skill skillUsed) {
		super(output, inputs, toolTier, carpenterTier, craftTime,
				skillXp, vanillaXp, toolType, soundOfCraft, research, skillUsed);
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(CarpenterCraftMatrix matrix, World world) {
		String wood = null;
		String metal = null;
		NonNullList<Ingredient> ingredients = getIngredients();
		List<Boolean> ingredientsMatched = new ArrayList<>(Collections.nCopies(ingredients.size(), false));

		for (int i = 0; i < matrix.getSizeInventory(); ++i) {
			ItemStack inputItem = matrix.getStackInSlot(i);
			if (inputItem.isEmpty()) {
				continue;
			}
			boolean matched = false;
			for (int j = 0; j < ingredients.size(); j++) {
				boolean passesChecks = true;

				String component_wood = CustomToolHelper.getComponentMaterial(inputItem, "wood");
				String component_metal = CustomToolHelper.getComponentMaterial(inputItem, "metal");

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

				if (inputItem.isEmpty()) {
					passesChecks = false;
				}
				if (!CustomToolHelper.doesMatchForRecipe(ingredients.get(j), inputItem)) {
					passesChecks = false;
				}

				if (passesChecks && ingredients.get(j).apply(inputItem)) {
					ingredientsMatched.set(j, true);
					matched = true;
					break;
				}
			}
			if (!matched) {
				return false;
			}
		}
		if (!modifyTiers(matrix, metal)) {
			modifyTiers(matrix, wood);
		}

		return !ingredientsMatched.contains(false);
	}

	@Override
	public ItemStack getCraftingResult(CarpenterCraftMatrix matrix) {
		ItemStack result = super.getCraftingResult(matrix);

		String wood = null;
		String metal = null;
		for (int i = 0; i < matrix.getSizeInventory(); i++) {
			ItemStack item = matrix.getStackInSlot(i);
			String component_wood = CustomToolHelper.getComponentMaterial(item, "wood");
			String component_metal = CustomToolHelper.getComponentMaterial(item, "metal");
			if (wood == null && component_wood != null) {
				wood = component_wood;
			}
			if (metal == null && component_metal != null) {
				metal = component_metal;
			}
		}
		if (metal != null) {
			CustomMaterial.addMaterial(result, CustomToolHelper.slot_main, metal);
		}
		if (wood != null) {
			CustomMaterial.addMaterial(result, metal == null ? CustomToolHelper.slot_main : CustomToolHelper.slot_haft,
					wood);
		}
		return result;
	}
	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return this.inputs.size();
	}

	@Override
	public boolean useCustomTiers() {
		return true;
	}
}
