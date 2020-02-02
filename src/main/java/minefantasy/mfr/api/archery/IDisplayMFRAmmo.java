package minefantasy.mfr.api.archery;

import net.minecraft.item.ItemStack;

/**
 * Implement this to your bow item so it displays the preset arrow when wielded
 * *Note that the preset arrow only fires if you implement MineFantasy's arrow
 * firer
 */
public interface IDisplayMFRAmmo {
    public int getAmmoCapacity(ItemStack item);
}
