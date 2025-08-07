package minefantasy.mfr.registry.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class SalvageRecipeStandard extends SalvageRecipeBase {

	public SalvageRecipeStandard(NonNullList<Ingredient> outputs, ItemStack input,
			String requiredResearch, Skill skill, int skillXp, float vanillaXp) {
		super(outputs, input, requiredResearch, skill, skillXp, vanillaXp);
	}

	public boolean matches(ItemStack stack) {
		ItemStack salvageInput = stack.copy();
		if (!CustomToolHelper.doesMatchForRecipe(input, salvageInput)) {
			return false;
		}
		if (salvageInput.isItemStackDamageable()) {
			salvageInput.setItemDamage(OreDictionary.WILDCARD_VALUE);
		}
		return salvageInput.getItem() == input.getItem() && (salvageInput.getItemDamage() == OreDictionary.WILDCARD_VALUE
				|| salvageInput.getItemDamage() == input.getItemDamage());
	}
}
