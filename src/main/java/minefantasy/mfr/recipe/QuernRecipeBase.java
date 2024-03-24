package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class QuernRecipeBase extends IForgeRegistryEntry.Impl<QuernRecipeBase> implements IRecipeMFR {
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected NonNullList<Ingredient> potInputs;
	protected boolean consumePot;
	protected String requiredResearch;
	protected Skill skill;
	protected Integer skillXp;
	protected float vanillaXp;

	public QuernRecipeBase(ItemStack output, NonNullList<Ingredient> inputs,
			NonNullList<Ingredient> potInputs, boolean consumePot,
			String requiredResearch, Skill skill, int skillXp, float vanillaXp) {
		this.output = output;
		this.inputs = inputs;
		this.potInputs = potInputs;
		this.consumePot = consumePot;
		this.requiredResearch = requiredResearch;
		this.skill = skill;
		this.skillXp = skillXp;
		this.vanillaXp = vanillaXp;
	}


	public boolean matches(ItemStack input, ItemStack potInput) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input))
				&& potInputs.stream().anyMatch(ingredient -> ingredient.apply(potInput));
	}

	public boolean inputMatches(ItemStack input) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input));
	}

	public boolean inputPotMatches(ItemStack inputPot) {
		return potInputs.stream().anyMatch(ingredient -> ingredient.apply(inputPot));
	}

	@Override
	public String getName() {
		return CraftingManagerQuern.getRecipeName(this);
	}

	public ItemStack getQuernRecipeOutput() {
		return output;
	}

	public NonNullList<Ingredient> getInputs() {
		return inputs;
	}

	public NonNullList<Ingredient> getPotInputs() {
		return potInputs;
	}

	public boolean shouldConsumePot() {
		return consumePot;
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
