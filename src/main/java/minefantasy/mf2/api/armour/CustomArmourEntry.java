package minefantasy.mf2.api.armour;

import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.helpers.ArmourCalculator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class CustomArmourEntry {
    public static HashMap<Integer, CustomArmourEntry> entries = new HashMap<Integer, CustomArmourEntry>();
    public boolean alterSpeed;
    public int itemID;
    /**
     * The Weight of the piece(not suit)
     */
    public float weight;
    /**
     * The Bulk of the piece(not suit)
     */
    public float bulkiness;
    /**
     * Light Medium or Heavy
     */
    public String AC;

    private CustomArmourEntry(int id, float weight, float bulk, boolean alterSpeed, String AC) {
        this.alterSpeed = alterSpeed;
        this.itemID = id;
        this.weight = weight;
        this.bulkiness = bulk;
        this.AC = AC;
    }

    /**
     * Registers an item to a template of an ArmourDesign, while dividing the suit
     * into the 4 pieces If it's unable to divide it, it simply quarters it's value
     *
     * @param piece    the item to register
     * @param template the ArmourDesign to work off
     */
    public static void registerItem(Item piece, ArmourDesign template) {
        registerItem(piece, template, 1.0F, template.getGroup());
    }

    /**
     * Registers an item to a template of an ArmourDesign, while dividing the suit
     * into the 4 pieces If it's unable to divide it, it simply quarters it's value
     *
     * @param piece     the item to register
     * @param template  the ArmourDesign to work off
     * @param weightMod the multiplier for the tier weight if a tier is heavier or lighter
     */
    public static void registerItem(Item piece, ArmourDesign template, float weightMod, String weightType) {
        float divider = 0.25F;
        if (piece instanceof ItemArmor) {
            divider = ArmourCalculator.sizes[((ItemArmor) piece).armorType];
        }
        registerItem(piece, template.getWeight() * weightMod, template.getBulk() * divider, weightType);
    }

    /**
     * Registeres a piece (called by code or config)
     *
     * @param piece  the item to list
     * @param weight the weight of the piece (Kg)
     * @param bulk   the bulk of the piece
     */
    public static void registerItem(Item piece, float weight, float bulk, String AC) {
        registerItem(piece, weight, bulk, true, AC);
    }

    /**
     * Registeres a piece (called by code or config)
     *
     * @param piece      the item to list
     * @param weight     the weight of the piece (Kg)
     * @param bulk       the bulk of the piece
     * @param alterSpeed if the armour's weight slows you down
     */
    public static void registerItem(Item piece, float weight, float bulk, boolean alterSpeed, String AC) {
        int id = Item.getIdFromItem(piece);

        MineFantasyAPI.debugMsg("Added Custom " + AC + " armour: " + piece.getUnlocalizedName() + "(" + id
                + ") Traits = " + weight + "," + bulk + " alter speed = " + alterSpeed);
        entries.put(id, new CustomArmourEntry(id, weight, bulk, alterSpeed, AC));
    }

    /**
     * Returns the "Armour Class" Weight type Light, Medium or Heavy
     */
    public static String getArmourClass(ItemStack piece) {
        CustomArmourEntry entry = getEntry(piece);
        if (entry != null) {
            return entry.AC;
        }
        ArmourDesign deft = ArmourCalculator.getDefaultAD(piece);
        return deft.getGroup();
    }

    /**
     * Gets the variables for the item if there are any: order is weight, bulk
     *
     * @param deft the default preset if not present
     */
    public static float[] getEntryVars(ItemStack piece) {
        CustomArmourEntry entry = getEntry(piece);
        if (entry != null) {
            return new float[]{entry.weight, entry.bulkiness};
        }
        ArmourDesign deft = ArmourCalculator.getDefaultAD(piece);
        return new float[]{deft.getWeight(), deft.getBulk()};
    }

    /**
     * Determines if a piece can slow you down
     */
    public static boolean doesPieceSlowDown(ItemStack piece) {
        if (piece != null) {
            CustomArmourEntry entry = getEntry(piece);
            if (entry != null) {
                return entry.alterSpeed;
            }
        }
        return false;
    }

    /**
     * Gets the entry for an item
     *
     * @param piece the armour item
     * @return the entry(if there is one), else null
     */
    public static CustomArmourEntry getEntry(ItemStack piece) {
        return getEntry(piece.getItem());
    }

    /**
     * Gets the entry for an item
     *
     * @param piece the armour item
     * @return the entry(if there is one), else null
     */
    public static CustomArmourEntry getEntry(Item piece) {
        if (piece != null) {
            int id = Item.getIdFromItem(piece);
            if (entries.containsKey(id)) {
                return entries.get(id);
            }
        }
        return null;
    }
}
