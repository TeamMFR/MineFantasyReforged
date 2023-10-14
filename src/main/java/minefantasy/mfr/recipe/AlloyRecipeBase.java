package minefantasy.mfr.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;


public abstract class AlloyRecipeBase extends IForgeRegistryEntry.Impl<AlloyRecipeBase> {
	public static final int MAX_WIDTH = 3;
	public static final int MAX_HEIGHT = 3;

	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected int tier;


	public AlloyRecipeBase(ItemStack output, NonNullList<Ingredient> inputs, int tier) {
		this.output = output;
		this.inputs = inputs;
		this.tier = tier;
	}

	public abstract boolean matches(CrucibleCraftMatrix matrix);

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(CrucibleCraftMatrix matrix) {
		return output.copy();
	}

	public ItemStack getAlloyRecipeOutput() {
		return output;
	}

	public NonNullList<Ingredient> getInputs() {
		return inputs;
	}

	public int getTier() {
		return tier;
	}

	public int getWidth() {
		return MAX_WIDTH;
	}

	public int getHeight() {
		return MAX_HEIGHT;
	}

	public static int getMaxCrucibleSize() {
		return MAX_WIDTH * MAX_HEIGHT;
	}
}
