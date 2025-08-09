package minefantasy.mfr.registry.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.registry.knowledge.ResearchBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class SpecialRecipeBase extends IForgeRegistryEntry.Impl<SpecialRecipeBase> implements IRecipeMFR {
	protected Ingredient input;
	protected Ingredient specialInput;
	protected ItemStack output;
	protected ResearchBase research;
	protected String design;

	public SpecialRecipeBase(ItemStack output, Ingredient input, Ingredient specialInput, ResearchBase research, String design) {
		this.input = input;
		this.specialInput = specialInput;
		this.output = output;
		this.research = research;
		this.design = design;
	}

	public abstract boolean matches(ItemStack recipeInput, ItemStack specialInput);

	public Ingredient getInput() {
		return input;
	}

	public Ingredient getSpecialInput() {
		return specialInput;
	}

	public ItemStack getOutput() {
		return output;
	}

	public ResearchBase getRequiredResearch() {
		return research;
	}

	public String getDesign() {
		return design;
	}

	@Override
	public String getResourceLocation() {
		return this.getRegistryName() != null ? this.getRegistryName().toString() : "";
	}

	@Override
	public Skill getSkill() {
		return null;
	}

	@Override
	public int getSkillXp() {
		return 0;
	}

	@Override
	public boolean shouldSlotGiveSkillXp() {
		return false;
	}

	@Override
	public float getVanillaXp() {
		return 0;
	}
}
