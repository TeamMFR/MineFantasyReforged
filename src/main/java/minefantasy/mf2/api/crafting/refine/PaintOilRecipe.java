package minefantasy.mf2.api.crafting.refine;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class PaintOilRecipe {// FurnaceRecipes
    public static HashMap<ItemStack, ItemStack> recipeList = new HashMap();

    /**
     * Turn one specific meta block to another
     */
    public static void addRecipe(Block input, int inMeta, Block output, int outMeta) {
        recipeList.put(new ItemStack(input, 1, inMeta), new ItemStack(output, 1, outMeta));
    }

    /**
     * Turn a block to a specific meta
     */
    public static void addRecipe(Block input, Block output, int meta) {
        addRecipe(input, OreDictionary.WILDCARD_VALUE, output, meta);
    }

    /**
     * Turn a block to a block without a meta change
     */
    public static void addRecipe(Block input, Block output) {
        addRecipe(input, OreDictionary.WILDCARD_VALUE, output, OreDictionary.WILDCARD_VALUE);
    }

    public static ItemStack getPaintResult(ItemStack item) {
        Iterator iterator = recipeList.entrySet().iterator();
        Entry entry;

        do {
            if (!iterator.hasNext()) {
                return null;
            }

            entry = (Entry) iterator.next();
        } while (!doesMatch(item, (ItemStack) entry.getKey()));

        return (ItemStack) entry.getValue();
    }

    private static boolean doesMatch(ItemStack item1, ItemStack item2) {
        return item2.getItem() == item1.getItem() && (item2.getItemDamage() == OreDictionary.WILDCARD_VALUE
                || item2.getItemDamage() == item1.getItemDamage());
    }
}
