package minefantasy.mfr.recipe;

import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author AnonymousProductions
 */
public class AnvilShapelessRecipe extends AnvilRecipeBase {

	public AnvilShapelessRecipe(NonNullList<Ingredient> inputs, ItemStack output, String toolType,
			int craftTime, int hammerTier, int anvilTier, boolean hotOutput,
			String requiredResearch, Skill requiredSkill,
			int skillXp, float vanillaXp) {
		super(inputs, output, toolType, craftTime, hammerTier, anvilTier, hotOutput,
				requiredResearch, requiredSkill, skillXp, vanillaXp);
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	boolean matches(@Nonnull AnvilCraftMatrix inv, @Nonnull World world) {
		NonNullList<Ingredient> ingredients = getIngredients();
		List<Boolean> ingredientsMatched = new ArrayList<>(Collections.nCopies(ingredients.size(), false));

		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack inputItem = inv.getStackInSlot(i);
			if (inputItem.isEmpty()) {
				continue;
			}
			boolean matched = false;
			for (int j = 0; j < ingredients.size(); j++) {
				boolean passesChecks = true;

				// HEATING
				if (Heatable.requiresHeating && Heatable.canHeatItem(inputItem)) {
					passesChecks = false;
				}
				if (!Heatable.isWorkable(inputItem)) {
					passesChecks = false;
				}
				inputItem = getHotItem(inputItem);

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
