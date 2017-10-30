package minefantasy.mf2.api.archery;

import net.minecraft.item.ItemStack;

public interface IAmmo {
    /**
     * What ammo is it (arrow, bolt, bullet, etc)
     */
    public String getAmmoType(ItemStack arrow);
}
