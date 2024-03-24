package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public abstract class KitchenBenchRecipeBase extends IForgeRegistryEntry.Impl<KitchenBenchRecipeBase> implements IRecipeMFR{
	public static final int MAX_WIDTH = 4;
	public static final int MAX_HEIGHT = 4;
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected final int toolTier;
	protected final int kitchenBenchTier;
	protected final int craftTime;
	protected final Tool toolType;
	protected final SoundEvent soundOfCraft;
	protected final String research;
	protected final Skill skillUsed;
	protected Integer skillXp;
	protected float vanillaXp;
	protected final int dirtyProgressAmount;

	public KitchenBenchRecipeBase(ItemStack output, NonNullList<Ingredient> inputs, int toolTier, int kitchenBenchTier,
			int craftTime, String toolType,
			SoundEvent soundOfCraft, String research, Skill skillUsed, int skillXp, float vanillaXp,
			int dirtyProgressAmount) {
		this.output = output;
		this.inputs = inputs;
		this.toolTier = toolTier;
		this.kitchenBenchTier = kitchenBenchTier;
		this.craftTime = craftTime;
		this.toolType = Tool.fromName(toolType);
		this.soundOfCraft = soundOfCraft;
		this.research = research;
		this.skillUsed = skillUsed;
		this.skillXp = skillXp;
		this.vanillaXp = vanillaXp;
		this.dirtyProgressAmount = dirtyProgressAmount;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	abstract boolean matches(KitchenBenchCraftMatrix matrix, @Nonnull World world);

	public int getRecipeSize() {
		return 0;
	}

	@Override
	public String getName() {
		return CraftingManagerKitchenBench.getRecipeName(this);
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult() {
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

	public int getKitchenBenchTier() {
		return kitchenBenchTier;
	}

	public Tool getToolType() {
		return toolType;
	}

	public SoundEvent getSound() {
		return soundOfCraft;
	}

	public ItemStack getKitchenBenchRecipeOutput() {
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

	@Override
	public int getSkillXp() {
		return skillXp;
	}

	@Override
	public float getVanillaXp() {
		return vanillaXp;
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

	@Override
	public boolean shouldSlotGiveSkillXp() {
		return false;
	}
}
