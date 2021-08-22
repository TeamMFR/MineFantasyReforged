package minefantasy.mfr.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
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
		return stack.getItemUseAction() == EnumAction.valueOf("mfr_block");
	}
}
