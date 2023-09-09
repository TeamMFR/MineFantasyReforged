package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.material.CustomMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public abstract class CarpenterRecipeBase extends IForgeRegistryEntry.Impl<CarpenterRecipeBase>{
	public static final int MAX_WIDTH = 4;
	public static final int MAX_HEIGHT = 4;
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected final int toolTier;
	protected final int carpenterTier;
	protected final int craftTime;
	protected final float recipeExperience;
	protected final String toolType;
	protected final SoundEvent soundOfCraft;
	protected final String research;
	protected final Skill skillUsed;

	public CarpenterRecipeBase(ItemStack output, NonNullList<Ingredient> inputs, int toolTier, int carpenterTier, int craftTime, float recipeExperience, String toolType, SoundEvent soundOfCraft, String research, Skill skillUsed) {
		this.output = output;
		this.inputs = inputs;
		this.toolTier = toolTier;
		this.carpenterTier = carpenterTier;
		this.craftTime = craftTime;
		this.recipeExperience = recipeExperience;
		this.toolType = toolType;
		this.soundOfCraft = soundOfCraft;
		this.research = research;
		this.skillUsed = skillUsed;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	abstract boolean matches(CarpenterCraftMatrix matrix, @Nonnull World world);

	protected boolean modifyTiers(CarpenterCraftMatrix matrix, String tier) {
		CustomMaterial material = CustomMaterial.getMaterial(tier);
		if (material != null) {
			int newTier = toolTier < 0 ? material.crafterTier : toolTier;
			matrix.modifyTier(newTier, (int) (craftTime * material.craftTimeModifier));
			return true;
		}
		return false;
	}

	public int getRecipeSize() {
		return 0;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(CarpenterCraftMatrix matrix) {
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

	public int getCarpenterTier() {
		return carpenterTier;
	}

	public String getToolType() {
		return toolType;
	}

	public SoundEvent getSound() {
		return soundOfCraft;
	}

	public ItemStack getCarpenterRecipeOutput() {
		return output;
	}

	public String getResearch() {
		return research;
	}

	public Skill getSkill() {
		return skillUsed;
	}

	public boolean useCustomTiers() {
		return false;
	}

	public int getWidth() {
		return MAX_WIDTH;
	}

	public int getHeight() {
		return MAX_HEIGHT;
	}
}
