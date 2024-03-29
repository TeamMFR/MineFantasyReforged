package minefantasy.mfr.api.armour;

import net.minecraft.item.ItemStack;

public interface IGasProtector {
	/**
	 * Applies ONLY to helmets, gets the percentage (1-100) of gas protection from
	 * smoke
	 */
	float getGasProtection(ItemStack item);
}
