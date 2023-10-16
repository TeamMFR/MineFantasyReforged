package minefantasy.mfr.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class BloomeryRecipeBase extends IForgeRegistryEntry.Impl<BloomeryRecipeBase>{
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;

	public BloomeryRecipeBase(ItemStack output, NonNullList<Ingredient> inputs) {
		this.output = output;
		this.inputs = inputs;
	}

	public boolean matches(ItemStack input) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input));
	}

	public ItemStack getBloomeryRecipeOutput(){
		return output;
	}

	public NonNullList<Ingredient> getInputs() {
		return inputs;
	}
}
