package minefantasy.mfr.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class SalvageRecipeBase extends IForgeRegistryEntry.Impl<SalvageRecipeBase> {
	protected ItemStack input;
	protected NonNullList<Ingredient> outputs;

	public SalvageRecipeBase(ItemStack input, NonNullList<Ingredient> outputs) {
		this.input = input;
		this.outputs = outputs;
	}

	abstract boolean matches(ItemStack stack);

	public ItemStack getInput() {
		return input;
	}

	public NonNullList<Ingredient> getOutputs() {
		return outputs;
	}

}
