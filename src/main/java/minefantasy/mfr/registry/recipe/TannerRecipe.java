package minefantasy.mfr.registry.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public class TannerRecipe extends TannerRecipeBase {
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected Tool toolType;
	protected int tannerTier;
	protected int craftTime;
	protected String requiredResearch;
	protected Skill skill;
	protected Integer skillXp;
	protected float vanillaXp;

	public TannerRecipe(ItemStack output, NonNullList<Ingredient> inputs, String toolType, int tannerTier,
			int craftTime, String requiredResearch, Skill skill, int skillXp, float vanillaXp) {
		super(output, inputs, toolType, tannerTier, craftTime, requiredResearch, skill, skillXp, vanillaXp);
	}

	@Override
	public boolean matches(ItemStack input) {
		return inputs.stream().anyMatch(ingredient -> ingredient.apply(input));
	}
}
