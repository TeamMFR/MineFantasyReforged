package minefantasy.mfr.registry.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.registry.knowledge.ResearchBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public class BlastFurnaceRecipe extends BlastFurnaceRecipeBase {
	public BlastFurnaceRecipe(ItemStack output, NonNullList<Ingredient> inputs,
			ResearchBase requiredResearch, Skill skill, int skillXp, float vanillaXp) {
		super(output, inputs, requiredResearch, skill, skillXp, vanillaXp);
	}

	@Override
	public boolean matches(ItemStack input) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input));
	}
}
