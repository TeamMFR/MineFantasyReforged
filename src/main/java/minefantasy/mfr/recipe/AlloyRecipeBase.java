package minefantasy.mfr.recipe;

import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.constants.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;


public abstract class AlloyRecipeBase extends IForgeRegistryEntry.Impl<AlloyRecipeBase> implements IRecipeMFR{
	public static final int MAX_WIDTH = 3;
	public static final int MAX_HEIGHT = 3;

	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected int tier;
	protected String requiredResearch;
	protected Skill skill;
	protected Integer skillXp;
	protected float vanillaXp;

	public AlloyRecipeBase(ItemStack output, NonNullList<Ingredient> inputs, int tier,
			String requiredResearch, Skill skill, int skillXp, float vanillaXp) {
		this.output = output;
		this.inputs = inputs;
		this.tier = tier;
		this.requiredResearch = requiredResearch;
		this.skill = skill;
		this.skillXp = skillXp;
		this.vanillaXp = vanillaXp;
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

	@Override
	public String getName() {
		return CraftingManagerAlloy.getRecipeName(this);
	}

	@Override
	public String getRequiredResearch() {
		return requiredResearch;
	}

	@Override
	public Skill getSkill() {
		return skill;
	}

	@Override
	public int getSkillXp() {
		return skillXp;
	}

	@Override
	public boolean shouldSlotGiveSkillXp() {
		return !ConfigHardcore.HCCreduceIngots;
	}

	@Override
	public float getVanillaXp() {
		return vanillaXp;
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
