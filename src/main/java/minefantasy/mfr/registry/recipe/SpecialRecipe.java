package minefantasy.mfr.registry.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public  class SpecialRecipe extends SpecialRecipeBase {
	public SpecialRecipe(ItemStack output, Ingredient input, Ingredient specialInput, String research, String design) {
		super(output, input, specialInput, research, design);
	}

	@Override
	public boolean matches(ItemStack recipeInput, ItemStack specialInput) {
		return input.apply(recipeInput) && this.specialInput.apply(specialInput);
	}
}
