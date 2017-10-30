package minefantasy.mf2.api.armour;

import net.minecraft.item.ItemStack;

public interface IArmourRating {
    /**
     * Gets the scale in relation for the total suit (0-4 = head, chest, legs,
     * boots)
     */
    public float scalePiece(ItemStack item);
}
