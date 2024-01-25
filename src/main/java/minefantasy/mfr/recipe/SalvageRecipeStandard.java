package minefantasy.mfr.recipe;

import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class SalvageRecipeStandard extends SalvageRecipeBase {

	public SalvageRecipeStandard(ItemStack input, NonNullList<Ingredient> outputs) {
		super(input, outputs);
	}

	boolean matches(ItemStack stack) {
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
