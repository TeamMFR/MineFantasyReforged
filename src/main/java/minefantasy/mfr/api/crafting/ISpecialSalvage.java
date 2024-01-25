package minefantasy.mfr.api.crafting;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface ISpecialSalvage {
	/**
	 * Returns a list of Items, Blocks, or ItemStacks dropped by salvage (similar to
	 * the regular function)
	 */
	List<ItemStack> getSalvage(ItemStack item);

}
