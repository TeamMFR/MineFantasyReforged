package minefantasy.mf2.api.armour;

import net.minecraft.item.ItemStack;

public interface ICogworkArmour
{
	/**
	 * Determines if the piece needs power (Only applies to chest usually)
	 */
	public boolean needsPower(ItemStack item);
	/**
	 * ONLY IF IT NEEDS POWER: How much will it consume
	 */
	public float getPowerCost(ItemStack item);
	/**
	 * Gets the modifier for weight (when powered)
	 */
	//public float getPoweredWeightMod(ItemStack item);
}
