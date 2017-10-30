package minefantasy.mf2.api.heating;

import net.minecraft.item.ItemStack;

public interface IHotItem {
    /**
     * Determines if an item is hot(requiring tongs to pick up)
     */
    boolean isHot(ItemStack item);

    /**
     * Determines if an item can be cooled
     */
    boolean isCoolable(ItemStack item);
}
