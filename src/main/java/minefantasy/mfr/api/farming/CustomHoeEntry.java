package minefantasy.mfr.api.farming;

import minefantasy.mfr.api.MineFantasyReforgedAPI;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class CustomHoeEntry {
	public static HashMap<ResourceLocation, CustomHoeEntry> entries = new HashMap<>();
	public ResourceLocation item;
	/**
	 * The Efficiency(same variable as dig speed)
	 */
	public float efficiency;

	private CustomHoeEntry(ResourceLocation itemResourceLocation, float efficiency) {
		this.item = itemResourceLocation;
		this.efficiency = efficiency;
	}

	/*
	 * Registeres a piece (called by code or config)
	 *
	 * @param piece      the item to list
	 * @param weight     the weight of the piece (Kg)
	 * @param bulk       the bulk of the piece
	 * @param alterSpeed if the armour's weight slows you down
	 */

	public static void registerItem(Item piece, float efficiency) {

		MineFantasyReforgedAPI.debugMsg("Added Custom hoe: " + piece.getUnlocalizedName() + " Efficiency = " + efficiency);
		entries.put(piece.getRegistryName(), new CustomHoeEntry(piece.getRegistryName(), efficiency));
	}

	/**
	 * Gets the variables for the item if there are any: order is weight, bulk
	 */
	public static float getEntryEfficiency(ItemStack piece, float Default) {
		CustomHoeEntry entry = getEntry(piece);
		if (entry != null) {
			return entry.efficiency;
		}
		return Default;
	}

	/**
	 * Gets the entry for an item
	 *
	 * @param piece the armour item
	 * @return the entry(if there is one), else null
	 */
	public static CustomHoeEntry getEntry(ItemStack piece) {
		return getEntry(piece.getItem());
	}

	/**
	 * Gets the entry for an item
	 *
	 * @param piece the armour item
	 * @return the entry(if there is one), else null
	 */
	public static CustomHoeEntry getEntry(Item piece) {
		if (piece != null) {
			if (entries.containsKey(piece.getRegistryName())) {
				return entries.get(piece.getRegistryName());
			}
		}
		return null;
	}
}
