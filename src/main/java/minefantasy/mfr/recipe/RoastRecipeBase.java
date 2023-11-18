package minefantasy.mfr.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RoastRecipeBase extends IForgeRegistryEntry.Impl<RoastRecipeBase> {
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected ItemStack burntOutput;
	protected int minTemperature;
	protected int maxTemperature;
	protected int cookTime;
	protected int burnTime;
	protected boolean canBurn;
	protected boolean isOvenRecipe;

	public RoastRecipeBase(
			ItemStack output, NonNullList<Ingredient> inputs,
			ItemStack burntOutput, int minTemperature, int maxTemperature,
			int cookTime, int burnTime, boolean canBurn, boolean isOvenRecipe) {
		this.output = output;
		this.inputs = inputs;
		this.burntOutput = burntOutput;
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
		this.cookTime = cookTime;
		this.burnTime = burnTime;
		this.canBurn = canBurn;
		this.isOvenRecipe = isOvenRecipe;
	}

	boolean matches(ItemStack input, boolean isOvenRecipe) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input)) && isOvenRecipe == isOvenRecipe();
	}

	public ItemStack getRoastRecipeOutput() {
		return output;
	}

	public NonNullList<Ingredient> getInputs() {
		return inputs;
	}

	public ItemStack getBurntOutput() {
		return burntOutput;
	}

	public int getMinTemperature() {
		return minTemperature;
	}

	public int getMaxTemperature() {
		return maxTemperature;
	}

	public int getCookTime() {
		return cookTime;
	}

	public int getBurnTime() {
		return burnTime;
	}

	public boolean canBurn() {
		return canBurn;
	}

	public boolean isOvenRecipe() {
		return isOvenRecipe;
	}
}
