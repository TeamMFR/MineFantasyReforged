package minefantasy.mfr.api.archery;

import net.minecraft.item.ItemStack;

public interface IAmmo {
	/**
	 * What ammo is it (arrow, bolt, bullet, etc)
	 * @param ammo the Itemstack of the ammo
	 * @return String that names what type of ammo this is
	 */
	String getAmmoType(ItemStack ammo);
}
