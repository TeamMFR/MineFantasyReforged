package minefantasy.mfr.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class BigFurnaceRecipeBase extends IForgeRegistryEntry.Impl<BigFurnaceRecipeBase> {
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected int tier;

	public BigFurnaceRecipeBase(ItemStack output, NonNullList<Ingredient> inputs, int tier) {
		this.output = output;
		this.inputs = inputs;
		this.tier = tier;
	}

	public boolean matches(ItemStack input) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input));
	}

	public ItemStack getBigFurnaceRecipeOutput(){
		return output;
	}

	public NonNullList<Ingredient> getInputs() {
		return inputs;
	}

	public int getTier() {
		return tier;
	}
}
