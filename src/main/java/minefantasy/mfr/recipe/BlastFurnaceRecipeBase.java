package minefantasy.mfr.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class BlastFurnaceRecipeBase extends IForgeRegistryEntry.Impl<BlastFurnaceRecipeBase> {
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;

	public BlastFurnaceRecipeBase(ItemStack output, NonNullList<Ingredient> inputs) {
		this.output = output;
		this.inputs = inputs;
	}

	public boolean matches(ItemStack input) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input));
	}

	public ItemStack getBlastFurnaceRecipeOutput() {
		return output;
	}

	public NonNullList<Ingredient> getInputs() {
		return inputs;
	}
}
