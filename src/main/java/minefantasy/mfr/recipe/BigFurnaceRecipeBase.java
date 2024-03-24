package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class BigFurnaceRecipeBase extends IForgeRegistryEntry.Impl<BigFurnaceRecipeBase> implements IRecipeMFR {
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected int tier;
	protected String requiredResearch;
	protected Skill skill;
	protected Integer skillXp;
	protected float vanillaXp;

	public BigFurnaceRecipeBase(ItemStack output, NonNullList<Ingredient> inputs, int tier,
			String requiredResearch, Skill skill, int skillXp, float vanillaXp) {
		this.output = output;
		this.inputs = inputs;
		this.tier = tier;
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
		return CraftingManagerBigFurnace.getRecipeName(this);
	}

	public ItemStack getBigFurnaceRecipeOutput(){
		return output;
	}

	public NonNullList<Ingredient> getInputs() {
		return inputs;
	}

	public int getTier() {
		return tier;
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
		return true;
	}
}
