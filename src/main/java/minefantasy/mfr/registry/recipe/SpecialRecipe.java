package minefantasy.mfr.registry.recipe;

import minefantasy.mfr.registry.knowledge.ResearchBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public  class SpecialRecipe extends SpecialRecipeBase {
	public SpecialRecipe(ItemStack output, Ingredient input, Ingredient specialInput, ResearchBase research, String design) {
		super(output, input, specialInput, research, design);
	}

	@Override
	public boolean matches(ItemStack recipeInput, ItemStack specialInput) {
		return input.apply(recipeInput) && this.specialInput.apply(specialInput);
	}
}
