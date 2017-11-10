package minefantasy.mf2.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Utils {

    public static boolean doesMatch(ItemStack item1, ItemStack item2) {
        return item2.getItem() == item1.getItem() && (item2.getItemDamage() == OreDictionary.WILDCARD_VALUE
                || item2.getItemDamage() == item1.getItemDamage());
    }

}
