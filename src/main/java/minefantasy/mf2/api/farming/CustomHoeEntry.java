package minefantasy.mf2.api.farming;

import minefantasy.mf2.api.MineFantasyAPI;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class CustomHoeEntry {
    public static HashMap<Integer, CustomHoeEntry> entries = new HashMap<Integer, CustomHoeEntry>();
    public int itemID;
    /**
     * The Efficiency(same variable as dig speed)
     */
    public float efficiency;

    private CustomHoeEntry(int id, float efficiency) {
        this.itemID = id;
        this.efficiency = efficiency;
    }

    /**
     * Registeres a piece (called by code or config)
     *
     * @param piece      the item to list
     * @param weight     the weight of the piece (Kg)
     * @param bulk       the bulk of the piece
     * @param alterSpeed if the armour's weight slows you down
     */
    public static void registerItem(Item piece, float efficiency) {
        int id = Item.getIdFromItem(piece);

        MineFantasyAPI.debugMsg("Added Custom hoe: " + piece.getUnlocalizedName() + " Efficiency = " + efficiency);
        entries.put(id, new CustomHoeEntry(id, efficiency));
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
            int id = Item.getIdFromItem(piece);
            if (entries.containsKey(id)) {
                return entries.get(id);
            }
        }
        return null;
    }
}
