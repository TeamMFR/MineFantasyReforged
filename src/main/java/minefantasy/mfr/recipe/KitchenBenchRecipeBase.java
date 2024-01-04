package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public abstract class KitchenBenchRecipeBase extends IForgeRegistryEntry.Impl<KitchenBenchRecipeBase>{
	public static final int MAX_WIDTH = 4;
	public static final int MAX_HEIGHT = 4;
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected final int toolTier;
	protected final int kitchenBenchTier;
	protected final int craftTime;
	protected final float recipeExperience;
	protected final String toolType;
	protected final SoundEvent soundOfCraft;
	protected final String research;
	protected final Skill skillUsed;
	protected final int dirtyProgressAmount;

	public KitchenBenchRecipeBase(ItemStack output, NonNullList<Ingredient> inputs, int toolTier, int kitchenBenchTier,
			int craftTime, float recipeExperience, String toolType,
			SoundEvent soundOfCraft, String research, Skill skillUsed,
			int dirtyProgressAmount) {
		this.output = output;
		this.inputs = inputs;
		this.toolTier = toolTier;
		this.kitchenBenchTier = kitchenBenchTier;
		this.craftTime = craftTime;
		this.recipeExperience = recipeExperience;
		this.toolType = toolType;
		this.soundOfCraft = soundOfCraft;
		this.research = research;
		this.skillUsed = skillUsed;
		this.dirtyProgressAmount = dirtyProgressAmount;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	abstract boolean matches(KitchenBenchCraftMatrix matrix, @Nonnull World world);

	public int getRecipeSize() {
		return 0;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(KitchenBenchCraftMatrix matrix) {
		return output.copy();
	}

	public NonNullList<Ingredient> getIngredients() {
		return inputs;
	}

	public int getCraftTime() {
		return craftTime;
	}

	public int getToolTier() {
		return toolTier;
	}

	public float getExperience() {
		return recipeExperience;
	}

	public int getKitchenBenchTier() {
		return kitchenBenchTier;
	}

	public String getToolType() {
		return toolType;
	}

	public SoundEvent getSound() {
		return soundOfCraft;
	}

	public ItemStack getKitchenBenchRecipeOutput() {
		return output;
	}

	public String getResearch() {
		return research;
	}

	public Skill getSkill() {
		return skillUsed;
	}

	public int getDirtyProgressAmount() {
		return dirtyProgressAmount;
	}

	public int getWidth() {
		return MAX_WIDTH;
	}

	public int getHeight() {
		return MAX_HEIGHT;
	}
}
