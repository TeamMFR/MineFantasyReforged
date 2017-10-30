package minefantasy.mf2.api.crafting.refine;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class BloomRecipe {// FurnaceRecipes
    public static HashMap<ItemStack, ItemStack> recipeList = new HashMap();
    public ItemStack result;
    public int time;
    public String research;

    public BloomRecipe(ItemStack result, int time, String research) {
        this.result = result;
        this.time = time;
        this.research = research;
    }

    public static void addRecipe(ItemStack input, ItemStack output) {
        recipeList.put(input, output);
    }

    public static void addRecipe(Block input, ItemStack output) {
        addRecipe(Item.getItemFromBlock(input), output);
    }

    public static void addRecipe(Item input, ItemStack output) {
        addRecipe(new ItemStack(input, 1, OreDictionary.WILDCARD_VALUE), output);
    }

    public static ItemStack getSmeltingResult(ItemStack item) {
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
