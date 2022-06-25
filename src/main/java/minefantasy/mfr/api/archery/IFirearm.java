package minefantasy.mfr.api.archery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IFirearm {
	/**
	 * Determines the type of firing mechanism used (such as arrow, bolt, bullet,
	 * etc)
	 * Should be used in conjunction with {@link IAmmo#getAmmoType(ItemStack)}
	 * @param weapon the item that is being accessed
	 * @param ammo   the ammo type that weapon is being loaded with
	 */
	boolean canAcceptAmmo(ItemStack weapon, String ammo);

	void reloadFirearm(EntityPlayer player);
}
