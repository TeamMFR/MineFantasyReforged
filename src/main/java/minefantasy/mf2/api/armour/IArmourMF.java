package minefantasy.mf2.api.armour;

import net.minecraft.item.ItemStack;

public interface IArmourMF {
    // NOTE: "Slot" Variable works 0=Helmet, 1=Chest, 2=Legs, 3=Boots

    /**
     * Gets how heavy the piece is. (mesured in Kg). this is used for slowing
     * movement, and stamina decay
     */
    public float getPieceWeight(ItemStack item, int slot);

    /**
     * This is if the suit is light or heavy armour (pretty much anything above 50kg
     * is heavy)
     *
     * @return "light" "medium" or "heavy"
     */
    public String getSuitWeigthType(ItemStack item);
}
