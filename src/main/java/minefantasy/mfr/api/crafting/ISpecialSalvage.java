package minefantasy.mfr.api.crafting;

import net.minecraft.item.ItemStack;

public interface ISpecialSalvage {
	/**
	 * Returns a list of Items, Blocks, or ItemStacks dropped by salvage (similar to
	 * the regular function)
	 */
	Object[] getSalvage(ItemStack item);

}
