package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class TannerRecipeBase extends IForgeRegistryEntry.Impl<TannerRecipeBase> implements IRecipeMFR{
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected Tool toolType;
	protected int tannerTier;
	protected int craftTime;
	protected String requiredResearch;
	protected Skill skill;
	protected Integer skillXp;
	protected float vanillaXp;

	public TannerRecipeBase(ItemStack output, NonNullList<Ingredient> inputs, String toolType, int tannerTier,
			int craftTime, String requiredResearch, Skill skill, int skillXp, float vanillaXp) {
		this.output = output;
		this.inputs = inputs;
		this.toolType = Tool.fromName(toolType);
		this.tannerTier = tannerTier;
		this.craftTime = craftTime;
		this.requiredResearch = requiredResearch;
		this.skill = skill;
		this.skillXp = skillXp;
		this.vanillaXp = vanillaXp;
	}

	public boolean matches(ItemStack input) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input));
	}

	@Override
	public String getName() {
		return CraftingManagerTanner.getRecipeName(this);
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
	public float getVanillaXp() {
		return vanillaXp;
	}

	@Override
	public boolean shouldSlotGiveSkillXp() {
		return false;
	}
}
