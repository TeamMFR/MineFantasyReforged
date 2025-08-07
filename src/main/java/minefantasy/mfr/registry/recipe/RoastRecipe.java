package minefantasy.mfr.registry.recipe;

import minefantasy.mfr.constants.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public class RoastRecipe extends RoastRecipeBase {
	public RoastRecipe(
			ItemStack output, NonNullList<Ingredient> inputs,
			ItemStack burntOutput, int minTemperature, int maxTemperature,
			int cookTime, int burnTime, boolean canBurn, boolean isOvenRecipe,
			String requiredResearch, Skill skill, int skillXp, float vanillaXp) {
		super(output, inputs, burntOutput, minTemperature, maxTemperature, cookTime, burnTime,
				canBurn, isOvenRecipe, requiredResearch, skill, skillXp, vanillaXp);
	}

	@Override
	public boolean matches(ItemStack input, boolean isOvenRecipe) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input)) && isOvenRecipe == isOvenRecipe();
	}
}
