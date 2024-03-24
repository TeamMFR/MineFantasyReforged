package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.material.CustomMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public abstract class CarpenterRecipeBase extends IForgeRegistryEntry.Impl<CarpenterRecipeBase> implements IRecipeMFR{
	public static final int MAX_WIDTH = 4;
	public static final int MAX_HEIGHT = 4;
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected final int toolTier;
	protected final int carpenterTier;
	protected final int craftTime;
	protected Integer skillXp;
	protected float vanillaXp;
	protected final Tool toolType;
	protected final SoundEvent soundOfCraft;
	protected final String research;
	protected final Skill skillUsed;

	public CarpenterRecipeBase(ItemStack output, NonNullList<Ingredient> inputs,
			int toolTier, int carpenterTier, int craftTime,
			int skillXp, float recipeExperience, String toolType, SoundEvent soundOfCraft,
			String research, Skill skillUsed) {
		this.output = output;
		this.inputs = inputs;
		this.toolTier = toolTier;
		this.carpenterTier = carpenterTier;
		this.craftTime = craftTime;
		this.skillXp = skillXp;
		this.vanillaXp = recipeExperience;
		this.toolType = Tool.fromName(toolType);
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

	@Override
	public String getName() {
		return CraftingManagerCarpenter.getRecipeName(this);
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

	@Override
	public int getSkillXp() {
		return skillXp;
	}

	@Override
	public float getVanillaXp() {
		return vanillaXp;
	}

	public int getCarpenterTier() {
		return carpenterTier;
	}

	public Tool getToolType() {
		return toolType;
	}

	public SoundEvent getSound() {
		return soundOfCraft;
	}

	public ItemStack getCarpenterRecipeOutput() {
		return output;
	}

	@Override
	public String getRequiredResearch() {
		return research;
	}

	@Override
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

	@Override
	public boolean shouldSlotGiveSkillXp() {
		return false;
	}
}
