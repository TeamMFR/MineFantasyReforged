package minefantasy.mf2.api.heating;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class ForgeItemHandler {
    public static List<ForgeFuel> forgeFuel = new ArrayList();
    public static int forgeMaxTemp = 0;

    public static void addFuel(ItemStack item, int fuel, int heat, boolean willLight) {
        forgeFuel.add(new ForgeFuel(item, fuel, heat, willLight));
    }

    public static ForgeFuel getStats(ItemStack item) {
        if (item == null)
            return null;

        for (ForgeFuel fuel : forgeFuel) {
            if (fuel != null) {
                if (fuel.fuel.getItem() == item.getItem()) {
                    if (fuel.fuel.getItemDamage() == OreDictionary.WILDCARD_VALUE
                            || fuel.fuel.getItemDamage() == item.getItemDamage()) {
                        return fuel;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets the amount of smelts for an item
     *
     * @param item The item in the fuel slot
     * @return The amount of smelts it has(will not consume if its 0)
     */
    public static float getForgeFuel(ItemStack item) {
        if (item == null)
            return 0;

        for (ForgeFuel fuel : forgeFuel) {
            if (fuel != null) {
                if (fuel.fuel.getItem() == item.getItem()) {
                    if (fuel.fuel.getItemDamage() == OreDictionary.WILDCARD_VALUE
                            || fuel.fuel.getItemDamage() == item.getItemDamage()) {
                        return fuel.duration;
                    }
                }
            }
        }
        return 0;
    }

    public static boolean willLight(ItemStack item) {
        if (item == null) {
            return false;
        }

        for (ForgeFuel fuel : forgeFuel) {
            if (fuel != null) {
                if (fuel.fuel.getItem() == item.getItem()) {
                    if (fuel.fuel.getItemDamage() == OreDictionary.WILDCARD_VALUE
                            || fuel.fuel.getItemDamage() == item.getItemDamage()) {
                        return fuel.doesLight;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Gets the amount of smelts for an item
     *
     * @param item The item id in the fuel slot
     * @return The amount of smelts it has(will not consume if its 0)
     */
    public static float getForgeFuelWithoutSubid(Item id) {
        for (ForgeFuel fuel : forgeFuel) {
            if (fuel != null) {
                if (fuel.fuel.getItem() == id) {
                    return fuel.baseHeat;
                }
            }
        }
        return 0;
    }

    /**
     * Gets the base temperature for an item
     *
     * @param item The item in the fuel slot
     * @return The amount of smelts it has(will not consume if its 0)
     */
    public static int getForgeHeat(ItemStack item) {
        if (item == null)
            return 0;

        for (ForgeFuel fuel : forgeFuel) {
            if (fuel != null) {
                if (fuel.fuel.getItem() == item.getItem()) {
                    if (fuel.fuel.getItemDamage() == OreDictionary.WILDCARD_VALUE
                            || fuel.fuel.getItemDamage() == item.getItemDamage()) {
                        return fuel.baseHeat;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Gets the base temperature for an item
     *
     * @param item The item id in the fuel slot
     * @return The amount of smelts it has(will not consume if its 0)
     */
    public static int getForgeHeat(Item item) {
        for (ForgeFuel fuel : forgeFuel) {
            if (fuel != null && fuel.fuel.getItem() == item) {
                return fuel.baseHeat;
            }
        }
        return 0;
    }

    private static boolean matches(ItemStack item, ItemStack compare) {
        if (item == null || compare == null) {
            return false;
        }

        if (item.getItem() != compare.getItem()) {
            return false;
        }

        if (item.getItemDamage() != compare.getItemDamage() && compare.getItemDamage() != OreDictionary.WILDCARD_VALUE
                && item.getItemDamage() != OreDictionary.WILDCARD_VALUE) {
            return false;
        }
        return true;
    }
}
