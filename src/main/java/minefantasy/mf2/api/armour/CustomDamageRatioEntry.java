package minefantasy.mf2.api.armour;

import net.minecraft.item.Item;

import java.util.HashMap;

public class CustomDamageRatioEntry {
    public static HashMap<Integer, CustomDamageRatioEntry> entries = new HashMap<Integer, CustomDamageRatioEntry>();
    public static HashMap<String, CustomDamageRatioEntry> entriesProj = new HashMap<String, CustomDamageRatioEntry>();

    public float[] vars;

    private CustomDamageRatioEntry(float[] vars) {
        this.vars = vars;
    }

    /**
     * Register a weapon to give variables
     *
     * @param item the item
     * @param vars the damage type ratio cutting:blunt
     */
    public static void registerItem(Item item, float[] vars) {
        registerItem(Item.getIdFromItem(item), vars);
    }

    /**
     * Register a weapon to give variables
     *
     * @param id   the item id
     * @param vars the damage type ratio cutting:blunt
     */
    public static void registerItem(int id, float[] vars) {
        entries.put(id, new CustomDamageRatioEntry(vars));
    }

    /**
     * Register an entity (like arrows) to give variables
     *
     * @param id   the entity id
     * @param vars the damage type ratio cutting:blunt
     */
    public static void registerEntity(String id, float[] vars) {
        entriesProj.put(id, new CustomDamageRatioEntry(vars));
    }

    /**
     * Gets the ratio for an item, null if it's not found
     */
    public static float[] getTraits(Item item) {
        return getTraits(Item.getIdFromItem(item));
    }

    /**
     * Gets the ratio for an item, null if it's not found
     */
    public static float[] getTraits(int id) {
        return entries.get(id) != null ? entries.get(id).vars : null;
    }

    /**
     * Gets the ratio for an entity, null if it's not found
     */
    public static float[] getTraits(String id) {
        return entriesProj.get(id) != null ? entriesProj.get(id).vars : new float[]{1, 1, 1};
    }
}
