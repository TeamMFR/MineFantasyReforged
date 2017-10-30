package minefantasy.mf2.api.archery;

import net.minecraft.item.ItemStack;

public interface IArrowRetrieve {
    /**
     * Gets if the arrow will be dropped by a killed mob (this pretty much connects
     * to the canBePickedUp variable)
     */
    public boolean canBePickedUp();

    /**
     * Gets the item dropped when the enemy is killed (the arrow item used)
     */
    public ItemStack getDroppedItem();
}
