package minefantasy.mf2.api.crafting;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class CustomCrafterEntry {
    public static HashMap<Item, CustomCrafterEntry> entries = new HashMap<Item, CustomCrafterEntry>();
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
    public static void registerItem(Item piece, String type, float efficiency, int tier) {
        entries.put(piece, new CustomCrafterEntry(piece, type, efficiency, tier));
    }

    /**
     * Gets the variables for the item if there are any: order is weight, bulk
     */
    public static String getEntryType(ItemStack piece) {
        CustomCrafterEntry entry = getEntry(piece);
        if (entry != null) {
            return entry.type;
        }
        return "nothing";
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
        return getEntry(piece.getItem());
    }

    /**
     * Gets the entry for an item
     *
     * @param piece the armour item
     * @return the entry(if there is one), else null
     */
    public static CustomCrafterEntry getEntry(Item piece) {
        if (piece != null) {
            if (entries.containsKey(piece)) {
                return entries.get(piece);
            }
        }
        return null;
    }
}
