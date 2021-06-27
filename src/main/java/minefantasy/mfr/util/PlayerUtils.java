package minefantasy.mfr.util;

import minefantasy.mfr.item.ItemWeaponMFR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PlayerUtils {

	private PlayerUtils() {} // no instances!

	/**
	 * Gives the specified itemStack to the player, drops it if the inventory is full
	 *
	 * @param player     the item receiver player
	 * @param itemToGive the itemStack to give
	 */
	public static void giveStackToPlayer(EntityPlayer player, ItemStack itemToGive) {
		if (!player.inventory.addItemStackToInventory(itemToGive)) {
			player.dropItem(itemToGive, false);
		}
	}

	public static boolean shouldItemStackBlock(ItemStack stack){
		return stack.getItem() instanceof ItemWeaponMFR;
	}
}
