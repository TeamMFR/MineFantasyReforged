package minefantasy.mfr.registry.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.registry.knowledge.ResearchBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public class QuernRecipe extends QuernRecipeBase {
	public QuernRecipe(ItemStack output, NonNullList<Ingredient> inputs,
			NonNullList<Ingredient> potInputs, boolean consumePot,
			ResearchBase requiredResearch, Skill skill, int skillXp, float vanillaXp) {
		super(output, inputs, potInputs, consumePot, requiredResearch, skill, skillXp, vanillaXp);
	}

	@Override
	public boolean matches(ItemStack input, ItemStack potInput) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input))
				&& potInputs.stream().anyMatch(ingredient -> ingredient.apply(potInput));
	}
}
