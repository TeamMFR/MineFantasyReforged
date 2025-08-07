package minefantasy.mfr.registry.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.registry.knowledge.ResearchBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class BloomeryRecipeBase extends IForgeRegistryEntry.Impl<BloomeryRecipeBase> implements IRecipeMFR{
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected ResearchBase requiredResearch;
	protected Skill skill;
	protected Integer skillXp;
	protected float vanillaXp;

	public BloomeryRecipeBase(ItemStack output, NonNullList<Ingredient> inputs,
			ResearchBase requiredResearch, Skill skill, int skillXp, float vanillaXp) {
		this.output = output;
		this.inputs = inputs;
		this.requiredResearch = requiredResearch;
		this.skill = skill;
		this.skillXp = skillXp;
		this.vanillaXp = vanillaXp;
	}

	public boolean matches(ItemStack input) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input));
	}

	@Override
	public String getResourceLocation() {
		return this.getRegistryName() != null ? this.getRegistryName().toString() : "";
	}

	public ItemStack getBloomeryRecipeOutput(){
		return output;
	}

	public NonNullList<Ingredient> getInputs() {
		return inputs;
	}

	@Override
	public ResearchBase getRequiredResearch() {
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
