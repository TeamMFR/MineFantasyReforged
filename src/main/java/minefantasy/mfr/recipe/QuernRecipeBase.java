package minefantasy.mfr.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class QuernRecipeBase extends IForgeRegistryEntry.Impl<QuernRecipeBase> {
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected NonNullList<Ingredient> potInputs;
	protected boolean consumePot;

	public QuernRecipeBase(ItemStack output, NonNullList<Ingredient> inputs, NonNullList<Ingredient> potInputs, boolean consumePot) {
		this.output = output;
		this.inputs = inputs;
		this.potInputs = potInputs;
		this.consumePot = consumePot;
	}


	public boolean matches(ItemStack input, ItemStack potInput) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input))
				&& potInputs.stream().anyMatch(ingredient -> ingredient.apply(potInput));
	}

	public boolean inputMatches(ItemStack input) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input));
	}

	public boolean inputPotMatches(ItemStack inputPot) {
		return potInputs.stream().anyMatch(ingredient -> ingredient.apply(inputPot));
	}

	public ItemStack getQuernRecipeOutput() {
		return output;
	}

	public NonNullList<Ingredient> getInputs() {
		return inputs;
	}

	public NonNullList<Ingredient> getPotInputs() {
		return potInputs;
	}

	public boolean shouldConsumePot() {
		return consumePot;
	}
}
