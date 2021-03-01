package minefantasy.mfr.api;

import com.google.common.collect.Lists;
import minefantasy.mfr.api.crafting.engineer.ICrossbowPart;
import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.api.refine.Alloy;
import minefantasy.mfr.api.refine.AlloyRecipes;
import minefantasy.mfr.api.refine.BigFurnaceRecipes;
import minefantasy.mfr.api.refine.BlastFurnaceRecipes;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.recipe.CookRecipe;
import minefantasy.mfr.recipe.CraftingManagerAnvil;
import minefantasy.mfr.recipe.IAnvilRecipe;
import minefantasy.mfr.recipe.refine.QuernRecipes;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class MineFantasyRebornAPI {
    /**
     * This variable saves if MineFantasy is in debug mode
     */
    public static boolean isInDebugMode;
    /**
     * This fuel handler is for MineFantasy equipment, it uses real fuel(like coal)
     * not wood
     */
    private static List<IFuelHandler> fuelHandlers = Lists.newArrayList();

    @SideOnly(Side.CLIENT)
    public static void init() {
    }

    public static void debugMsg(String msg) {
        MFRLogUtil.logDebug(msg);
    }

    public static IAnvilRecipe addAnvilRecipe(Skill skill, ItemStack result, String research, boolean hot, int hammerType, int anvil, int forgeTime, Object... input) {
        return addAnvilRecipe(skill, result, research, hot, "hammer", hammerType, anvil, forgeTime, input);
    }

    /**
     * Adds a shaped recipe for anvils with all variables
     * @param skill      The skill required for crafting
     * @param result     The output item
     * @param research   The research required
     * @param hot        True if the result is hot(Does not apply to blocks)
     * @param toolType   the tool type required to hit ("hammer", "hvyHammer", etc)
     * @param hammerType the hammer tier required for creation:
     * @param anvil      the anvil required bronze 0, iron 1, steel 2
     * @param forgeTime  The time taken to forge(default is 200. each hit is about 100)
     * @param input      The input for the item (Exactly the same as regular recipes)
     */
    public static IAnvilRecipe addAnvilRecipe(Skill skill, ItemStack result, String research, boolean hot, String toolType, int hammerType, int anvil, int forgeTime, Object... input) {
        return CraftingManagerAnvil.getInstance().addRecipe(result, skill, research, hot, toolType, hammerType, anvil, forgeTime, input);
    }

    /**
     * A special tier-sensitive recipe system for tools
     *
     * @param skill      the Skill used
     * @param result     what item comes out (basic itemstack)
     * @param research   what research is used
     * @param hot        whether it requires quenching
     * @param toolType   the tool used (hammer or hvyHammer)
     * @param hammerType the tier of hammer
     * @param anvil      the tier of anvil
     * @param forgeTime  the time it takes to forge
     * @param input      the items input
     * @return IAnvilRecipe
     */
    public static IAnvilRecipe addAnvilToolRecipe(Skill skill, ItemStack result, String research, boolean hot, String toolType, int hammerType, int anvil, int forgeTime, Object... input) {
        return CraftingManagerAnvil.getInstance().addToolRecipe(result, skill, research, hot, toolType, hammerType, anvil, forgeTime, input);
    }

    public static IAnvilRecipe addAnvilToolRecipe(Skill skill, Item result, String research, boolean hot, String toolType, int hammerType, int anvil, int forgeTime, Object... input) {
        return addAnvilToolRecipe(skill, new ItemStack(result), research, hot, toolType, hammerType, anvil, forgeTime, input);
    }

    public static void addBlastFurnaceRecipe(Item input, ItemStack output) {
        BlastFurnaceRecipes.smelting().addRecipe(input, output);
    }

    public static void registerFuelHandler(IFuelHandler handler) {
        fuelHandlers.add(handler);
    }

    public static int getFuelValue(ItemStack itemStack) {
        int fuelValue = 0;
        for (IFuelHandler handler : fuelHandlers) {
            fuelValue = Math.max(fuelValue, handler.getBurnTime(itemStack));
        }
        return fuelValue;
    }

    /**
     * Adds an alloy for any Crucible
     *
     * @param out The result
     * @param in  The list of required items
     */
    public static void addAlloy(ItemStack out, Object... in) {
        AlloyRecipes.addAlloy(out, convertList(in));
    }

    /**
     * Adds an alloy with a minimal furnace level
     *
     * @param out   The result
     * @param level The minimal furnace level
     * @param in    The list of required items
     */
    public static void addAlloy(ItemStack out, int level, Object... in) {
        AlloyRecipes.addAlloy(out, level, convertList(in));
    }

    /**
     * Adds an alloy with a minimal furnace level
     *
     * @param dupe  the amount of times the ratio can be added
     * @param out   The result
     * @param level The minimal furnace level
     * @param in    The list of required items
     */
    public static Alloy[] addRatioAlloy(int dupe, ItemStack out, int level, Object... in) {
        return AlloyRecipes.addRatioRecipe(out, level, convertList(in), dupe);
    }

    /**
     * Adds an alloy with any smelter
     *
     * @param dupe  the amount of times the ratio can be added
     * @param out   The result
     * @param in    The list of required items
     */
    public static Alloy[] addRatioAlloy(int dupe, ItemStack out, Object... in) {
        return AlloyRecipes.addRatioRecipe(out, 0, convertList(in), dupe);
    }

    /**
     * Adds a custom alloy
     *
     * @param alloy the Alloy to add Use this if you want your alloy to have special
     *              properties
     * @see Alloy
     */
    public static void addAlloy(Alloy alloy) {
        AlloyRecipes.addAlloy(alloy);
    }

    private static List convertList(Object[] in) {
        ArrayList arraylist = new ArrayList();
        Object[] aobject = in;
        int i = in.length;

        for (int j = 0; j < i; ++j) {
            Object object1 = aobject[j];

            if (object1 instanceof ItemStack) {
                arraylist.add(((ItemStack) object1).copy());
            } else if (object1 instanceof Item) {
                arraylist.add(new ItemStack((Item) object1));
            } else {
                if (!(object1 instanceof Block)) {
                    throw new RuntimeException("MineFantasy: Invalid alloy!");
                }

                arraylist.add(new ItemStack((Block) object1));
            }
        }

        return arraylist;
    }

    /**
     * Allows an item to be heated
     *
     * @param item     the item to heat
     * @param min      the minimum heat to forge with(celcius)
     * @param max      the maximum heat until the item is ruined(celcius)
     * @param unstable when the ingot is unstable(celcius)
     */
    public static void setHeatableStats(ItemStack item, int min, int unstable, int max) {
        Heatable.addItem(item, min, unstable, max);
    }

    /**
     * Allows an item to be heated ignoring subId
     *
     * @param id       the item to heat
     * @param min      the minimum heat to forge with(celcius)
     * @param max      the maximum heat until the item is ruined(celcius)
     * @param unstable when the ingot is unstable(celcius)
     */
    public static void setHeatableStats(Item id, int min, int unstable, int max) {
        Heatable.addItem(new ItemStack(id, 1, OreDictionary.WILDCARD_VALUE), min, unstable, max);
    }

    public static void setHeatableStats(String oredict, int min, int unstable, int max) {
        for (ItemStack item : OreDictionary.getOres(oredict)) {
            setHeatableStats(item, min, unstable, max);
        }
    }

    /**
     * Adds a crossbow part Make sure to do this when adding the item
     */
    public static void registerCrossbowPart(ICrossbowPart part) {
        ICrossbowPart.components.put(part.getComponentType() + part.getID(), part);
    }

    /**
     * For Hardcore Crafting: Can remove smelting recipes
     *
     * @param input can be an "Item", "Block" or "ItemStack"
     */
    public static boolean removeSmelting(Object input) {
        ItemStack object = ItemStack.EMPTY;
        if (input instanceof Item) {
            object = new ItemStack((Item) input, 1, 32767);
        } else if (input instanceof Block) {
            object = new ItemStack((Block) input, 1, 32767);
        } else if (input instanceof ItemStack) {
            object = (ItemStack) input;
        }
        if (!object.isEmpty()) {
            return removeFurnaceInput(object);
        }
        return false;
    }

    private static boolean removeFurnaceInput(ItemStack input) {
        Iterator iterator = FurnaceRecipes.instance().getSmeltingList().entrySet().iterator();
        Entry entry;

        do {
            if (!iterator.hasNext()) {
                return false;
            }

            entry = (Entry) iterator.next();
        } while (!checkMatch(input, (ItemStack) entry.getKey()));

        FurnaceRecipes.instance().getSmeltingList().remove(entry.getKey());
        return true;
    }

    private static boolean checkMatch(ItemStack item1, ItemStack item2) {
        return item2.getItem() == item1.getItem() && (item2.getItemDamage() == 32767 || item2.getItemDamage() == item1.getItemDamage());
    }

    /**
     * Add a cooking recipe
     *
     * @param input           what goes in
     * @param output          what is made
     * @param min_temperature minimal temperature to work
     * @param max_temperature maximum temperature until burn
     * @param time            how many ticks until it finishes
     * @param requireBaking   whether it needs to be in an oven
     */
    public static CookRecipe addCookingRecipe(ItemStack input, ItemStack output, int min_temperature, int max_temperature, int time, boolean requireBaking) {
        return CookRecipe.addRecipe(input, output, min_temperature, max_temperature, time, requireBaking, true);
    }

    /**
     * Add a cooking recipe
     *
     * @param input           what goes in
     * @param output          what is made
     * @param min_temperature minimal temperature to work
     * @param max_temperature maximum temperature until burn
     * @param time            how many ticks until it finishes
     * @param requireBaking   whether it needs to be in an oven
     * @param canBurn         false if it cannot burn by traditional means (such as if its in a
     *                        container)
     */
    public static CookRecipe addCookingRecipe(ItemStack input, ItemStack output, int min_temperature, int max_temperature, int time, boolean requireBaking, boolean canBurn) {
        return CookRecipe.addRecipe(input, output, min_temperature, max_temperature, time, requireBaking, canBurn);
    }

    /**
     * Add a cooking recipe
     *
     * @param input           what goes in
     * @param output          what is made
     * @param min_temperature minimal temperature to work
     * @param max_temperature maximum temperature until burn
     * @param time            how many ticks until it finishes
     * @param requireBaking   whether it needs to be in an oven
     */
    public static CookRecipe addCookingRecipe(ItemStack input, ItemStack output, ItemStack burnItem, int min_temperature, int max_temperature, int time, boolean requireBaking) {
        return CookRecipe.addRecipe(input, output, burnItem, min_temperature, max_temperature, time, requireBaking,
                true);
    }

    /**
     * Add a cooking recipe
     *
     * @param input           what goes in
     * @param output          what is made
     * @param min_temperature minimal temperature to work
     * @param max_temperature maximum temperature until burn
     * @param time            how many ticks until it finishes
     * @param burn_time       how long until a finished product burns
     * @param requireBaking   whether it needs to be in an oven
     */
    public static CookRecipe addCookingRecipe(ItemStack input, ItemStack output, ItemStack burnItem,
            int min_temperature, int max_temperature, int time, int burn_time, boolean requireBaking) {
        return CookRecipe.addRecipe(input, output, burnItem, min_temperature, max_temperature, time, burn_time,
                requireBaking, true);
    }

    public static CookRecipe addCookingRecipe(ItemStack input, ItemStack output, ItemStack burnItem,
            int min_temperature, int max_temperature, int time, int burn_time, boolean requireBaking, boolean canBurn) {
        return CookRecipe.addRecipe(input, output, burnItem, min_temperature, max_temperature, time, burn_time,
                requireBaking, canBurn);
    }

    public static QuernRecipes addQuernRecipe(ItemStack input, ItemStack output) {
        return addQuernRecipe(input, output, 1, true);
    }

    public static QuernRecipes addQuernRecipe(ItemStack input, ItemStack output, int tier) {
        return addQuernRecipe(input, output, tier, true);
    }

    public static QuernRecipes addQuernRecipe(ItemStack input, ItemStack output, int tier, boolean consumePot) {
        return QuernRecipes.addRecipe(input, output, tier, consumePot);
    }

    public static BigFurnaceRecipes addFurnaceRecipe(ItemStack input, ItemStack output, int tier) {
        return BigFurnaceRecipes.addRecipe(input, output, tier);
    }
}
