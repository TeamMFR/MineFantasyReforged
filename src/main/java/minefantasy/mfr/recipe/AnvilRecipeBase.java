package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public abstract class AnvilRecipeBase extends IForgeRegistryEntry.Impl<AnvilRecipeBase> {
	public static final int WIDTH = 6;
	public static final int HEIGHT = 4;
	public ItemStack output;
	public NonNullList<Ingredient> inputs;
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

	abstract boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World world);

	public ItemStack getCraftingResult(AnvilCraftMatrix var1) {
		return output.copy();
	}

	public NonNullList<Ingredient> getIngredients() {
		return inputs;
	}

	public int getCraftTime() {
		return craftTime;
	}

	public int getRecipeSize() {
		return 0;
	}

	public int getHammerTier() {
		return hammerTier;
	}

	public int getAnvilTier() {
		return anvilTier;
	}

	public boolean outputHot() {
		return hotOutput;
	}

	public String getToolType() {
		return toolType;
	}

	public String getResearch() {
		return requiredResearch;
	}

	public ItemStack getAnvilRecipeOutput() {
		return output;
	}

	public Skill getSkill() {
		return requiredSkill;
	}

	public boolean useCustomTiers() {
		return false;
	}
}
