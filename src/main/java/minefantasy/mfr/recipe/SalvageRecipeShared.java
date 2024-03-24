package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collections;
import java.util.List;

public class SalvageRecipeShared extends SalvageRecipeBase {
	protected NonNullList<ItemStack> shared;

	public SalvageRecipeShared(ItemStack input, NonNullList<Ingredient> outputs, NonNullList<ItemStack> shared,
			String requiredResearch, Skill skill, int skillXp, float vanillaXp) {
		super(input, outputs, requiredResearch, skill, skillXp, vanillaXp);
		this.shared = shared;
	}

	boolean matches(ItemStack stack) {
		List<Boolean> matches = new java.util.ArrayList<>(Collections.nCopies(shared.size(), false));

		if (findMatch(input, stack)) {
			return true;
		}
		else {
			for (int i = 0; i < shared.size(); i++) {
				matches.set(i, findMatch(shared.get(i), stack));
			}
		}

		return matches.contains(true);
	}

	private boolean findMatch(ItemStack input, ItemStack stack) {
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

	public NonNullList<ItemStack> getShared() {
		return shared;
	}
}
