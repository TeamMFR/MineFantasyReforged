package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class SalvageRecipeBase extends IForgeRegistryEntry.Impl<SalvageRecipeBase> implements IRecipeMFR{
	protected ItemStack input;
	protected NonNullList<Ingredient> outputs;
	protected String requiredResearch;
	protected Skill skill;
	protected Integer skillXp;
	protected float vanillaXp;

	public SalvageRecipeBase(ItemStack input, NonNullList<Ingredient> outputs,
			String requiredResearch, Skill skill, int skillXp, float vanillaXp) {
		this.input = input;
		this.outputs = outputs;
		this.requiredResearch = requiredResearch;
		this.skill = skill;
		this.skillXp = skillXp;
		this.vanillaXp = vanillaXp;
	}

	abstract boolean matches(ItemStack stack);

	public ItemStack getInput() {
		return input;
	}

	public NonNullList<Ingredient> getOutputs() {
		return outputs;
	}

	@Override
	public String getName() {
		return this.getRegistryName().toString();
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
