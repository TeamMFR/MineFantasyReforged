package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KitchenBenchShapelessRecipe extends KitchenBenchRecipeBase {

	public KitchenBenchShapelessRecipe(ItemStack output, NonNullList<Ingredient> inputs,
			int toolTier, int kitchenBenchTier, int craftTime,
			String toolType, SoundEvent soundOfCraft,
			String research, Skill skillUsed, int skillXp, float vanillaXp,
			int dirtyProgressAmount) {
		super(output, inputs, toolTier, kitchenBenchTier, craftTime,
				toolType, soundOfCraft,
				research, skillUsed, skillXp, vanillaXp,
				dirtyProgressAmount);
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(KitchenBenchCraftMatrix matrix, World world) {
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

		return !ingredientsMatched.contains(false);
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return this.inputs.size();
	}
}
