package minefantasy.mfr.api.tool;

import net.minecraft.item.ItemStack;

public interface IHuntingItem {
    /**
     * Determines if item can retrive drops from animals
     */
    boolean canRetrieveDrops(ItemStack item);
}
