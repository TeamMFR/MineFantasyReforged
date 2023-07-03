package minefantasy.mfr.api.crafting;

import minefantasy.mfr.constants.Tool;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class CustomCrafterEntry {
	public static HashMap<ResourceLocation, CustomCrafterEntry> entries = new HashMap<>();
	public Item itemID;
	/**
	 * The Efficiency(same variable as dig speed)
	 */
	public String type;
	public float efficiency;
	public int tier;

	private CustomCrafterEntry(Item id, String type, float efficiency, int tier) {
		this.itemID = id;
		this.type = type;
		this.efficiency = efficiency;
		this.tier = tier;
	}

	/**
	 * Registeres a piece (called by code or config)
	 *
	 * @param piece the item to list
	 * @param type  the crafter type
	 */
	public static void registerItem(ItemStack piece, String type, float efficiency, int tier) {
		entries.put(piece.getItem().getRegistryName(), new CustomCrafterEntry(piece.getItem(), type, efficiency, tier));
	}

	/**
	 * Gets the variables for the item if there are any: order is weight, bulk
	 */
	public static Tool getEntryType(ItemStack piece) {
		CustomCrafterEntry entry = getEntry(piece);
		if (entry != null) {
			return Tool.fromName(entry.type);
		}
		return Tool.OTHER;
	}

	public static float getEntryEfficiency(ItemStack piece) {
		CustomCrafterEntry entry = getEntry(piece);
		if (entry != null) {
			return entry.efficiency;
		}
		return 2.0F;
	}

	public static int getEntryTier(ItemStack piece) {
		CustomCrafterEntry entry = getEntry(piece);
		if (entry != null) {
			return entry.tier;
		}
		return -1;
	}

	/**
	 * Gets the entry for an item
	 *
	 * @param piece the armour item
	 * @return the entry(if there is one), else null
	 */
	public static CustomCrafterEntry getEntry(ItemStack piece) {
		if (piece != null) {
			if (entries.containsKey(piece.getItem().getRegistryName())) {
				return entries.get(piece.getItem().getRegistryName());
			}
		}
		return null;
	}
}
