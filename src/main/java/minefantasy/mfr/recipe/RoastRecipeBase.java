package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RoastRecipeBase extends IForgeRegistryEntry.Impl<RoastRecipeBase> implements IRecipeMFR{
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected ItemStack burntOutput;
	protected int minTemperature;
	protected int maxTemperature;
	protected int cookTime;
	protected int burnTime;
	protected boolean canBurn;
	protected boolean isOvenRecipe;
	protected String requiredResearch;
	protected Skill skill;
	protected Integer skillXp;
	protected float vanillaXp;

	public RoastRecipeBase(
			ItemStack output, NonNullList<Ingredient> inputs,
			ItemStack burntOutput, int minTemperature, int maxTemperature,
			int cookTime, int burnTime, boolean canBurn, boolean isOvenRecipe,
			String requiredResearch, Skill skill, int skillXp, float vanillaXp) {
		this.output = output;
		this.inputs = inputs;
		this.burntOutput = burntOutput;
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
		this.cookTime = cookTime;
		this.burnTime = burnTime;
		this.canBurn = canBurn;
		this.isOvenRecipe = isOvenRecipe;
		this.requiredResearch = requiredResearch;
		this.skill = skill;
		this.skillXp = skillXp;
		this.vanillaXp = vanillaXp;
	}

	boolean matches(ItemStack input, boolean isOvenRecipe) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input)) && isOvenRecipe == isOvenRecipe();
	}

	public ItemStack getRoastRecipeOutput() {
		return output;
	}

	public NonNullList<Ingredient> getInputs() {
		return inputs;
	}

	public ItemStack getBurntOutput() {
		return burntOutput;
	}

	public int getMinTemperature() {
		return minTemperature;
	}

	public int getMaxTemperature() {
		return maxTemperature;
	}

	public int getCookTime() {
		return cookTime;
	}

	public int getBurnTime() {
		return burnTime;
	}

	public boolean canBurn() {
		return canBurn;
	}

	public boolean isOvenRecipe() {
		return isOvenRecipe;
	}

	@Override
	public String getName() {
		return CraftingManagerRoast.getRecipeName(this);
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
