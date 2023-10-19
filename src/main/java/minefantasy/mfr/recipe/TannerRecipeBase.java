package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Tool;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class TannerRecipeBase extends IForgeRegistryEntry.Impl<TannerRecipeBase> {
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected Tool toolType;
	protected int tannerTier;
	protected int craftTime;

	public TannerRecipeBase(ItemStack output, NonNullList<Ingredient> inputs, String toolType, int tannerTier, int craftTime) {
		this.output = output;
		this.inputs = inputs;
		this.toolType = Tool.fromName(toolType);
		this.tannerTier = tannerTier;
		this.craftTime = craftTime;
	}

	public boolean matches(ItemStack input) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input));
	}

	public ItemStack getTannerRecipeOutput() {
		return output;
	}

	public NonNullList<Ingredient> getInputs() {
		return inputs;
	}

	public Tool getToolType() {
		return toolType;
	}

	public int getTannerTier() {
		return tannerTier;
	}

	public int getCraftTime() {
		return craftTime;
	}
}
