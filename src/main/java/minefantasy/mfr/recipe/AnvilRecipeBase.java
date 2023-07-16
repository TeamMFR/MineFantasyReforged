package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class AnvilRecipeBase extends IForgeRegistryEntry.Impl<AnvilRecipeBase> implements IAnvilRecipe {
	public static final int width = 6;
	public static final int height = 4;
	public ItemStack output;
	private NonNullList<Ingredient> inputs;
	public Skill requiredSkill;
	public String requiredResearch;
	public String toolType;
	public int anvilTier;
	public int hammerTier;
	public int craftTime;
	public boolean hotOutput;

	public AnvilRecipeBase(
			ItemStack output, NonNullList<Ingredient> inputs, Skill requiredSkill,
			String requiredResearch, String toolType, int anvilTier,
			int hammerTier, int craftTime, boolean hotOutput) {
		this.output = output;
		this.inputs = inputs;
		this.requiredSkill = requiredSkill;
		this.requiredResearch = requiredResearch;
		this.toolType = toolType;
		this.anvilTier = anvilTier;
		this.hammerTier = hammerTier;
		this.craftTime = craftTime;
		this.hotOutput = hotOutput;
	}

	@Override
	public ItemStack getCraftingResult(AnvilCraftMatrix var1) {
		return output.copy();
	}

	public NonNullList<Ingredient> getIngredients() {
		return inputs;
	}

	@Override
	public int getCraftTime() {
		return craftTime;
	}

	@Override
	public int getRecipeSize() {
		return 0;
	}

	@Override
	public int getHammerTier() {
		return hammerTier;
	}

	@Override
	public int getAnvilTier() {
		return anvilTier;
	}

	@Override
	public boolean outputHot() {
		return hotOutput;
	}

	@Override
	public String getToolType() {
		return toolType;
	}

	@Override
	public String getResearch() {
		return requiredResearch;
	}

	@Override
	public ItemStack getAnvilRecipeOutput() {
		return output;
	}

	@Override
	public Skill getSkill() {
		return requiredSkill;
	}

	@Override
	public boolean useCustomTiers() {
		return false;
	}
}
