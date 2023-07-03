package minefantasy.mfr.api.farming;

import minefantasy.mfr.api.MineFantasyReforgedAPI;
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

	private CustomHoeEntry(ResourceLocation hoe, float efficiency) {
		this.item = hoe;
		this.efficiency = efficiency;
	}

	/**
	 * Registers a hoeStack (called by code or config)
	 *
	 * @param hoeStack      the item to list
	 * @param efficiency 	the efficiency of the hoe
	 **/

	public static void registerItem(ItemStack hoeStack, float efficiency) {

		MineFantasyReforgedAPI.debugMsg("Added Custom hoe: " + hoeStack.getTranslationKey() + " Efficiency = " + efficiency);
		entries.put(hoeStack.getItem().getRegistryName(), new CustomHoeEntry(hoeStack.getItem().getRegistryName(), efficiency));
	}

	/**
	 * Gets the variables for the item if there are any, in this case, efficiency
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
	 * @param hoe the hoe item
	 * @return the entry(if there is one), else null
	 */
	public static CustomHoeEntry getEntry(ItemStack hoe) {
		if (hoe != null) {
			if (entries.containsKey(hoe.getItem().getRegistryName())) {
				return entries.get(hoe.getItem().getRegistryName());
			}
		}
		return null;
	}
}
