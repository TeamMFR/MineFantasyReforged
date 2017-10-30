package minefantasy.mf2.api.archery;

import net.minecraft.item.ItemStack;

public interface IFirearm {
    /**
     * Determines the type of firing mechanism used (such as arrow, bolt, bullet,
     * etc)
     *
     * @param weapon the item that is being accessed
     * @param ammo   the ammo type that weapon is being loaded with
     */
    public boolean canAcceptAmmo(ItemStack weapon, String ammo);
}
