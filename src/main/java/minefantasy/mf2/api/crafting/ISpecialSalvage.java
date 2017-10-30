package minefantasy.mf2.api.crafting;

import net.minecraft.item.ItemStack;

public interface ISpecialSalvage {
    /**
     * Returns a list of Items, Blocks, or ItemStacks dropped by salvage (similar to
     * the regular function)
     */
    public Object[] getSalvage(ItemStack item);

}
