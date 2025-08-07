package minefantasy.mfr.registry.recipe;

import minefantasy.mfr.constants.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public class BigFurnaceRecipe extends BigFurnaceRecipeBase {
	public BigFurnaceRecipe(ItemStack output, NonNullList<Ingredient> inputs, int tier,
			String requiredResearch, Skill skill, int skillXp, float vanillaXp) {
		super(output, inputs, tier, requiredResearch, skill, skillXp, vanillaXp);
	}

	@Override
	public boolean matches(ItemStack input) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input));
	}
}
