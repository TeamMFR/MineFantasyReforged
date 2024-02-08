package minefantasy.mfr.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
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

	/**
	 * Essentially the same as {@link InventoryPlayer#getSlotFor(ItemStack), but ignores NBT}
	 * @param player The Player whose inventory should be searched for the stack to find its slot
	 * @param stack The stack to search for
	 * @return The slot index
	 */
	public static int getSlotFor(EntityPlayer player, ItemStack stack) {
		for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
			if (!player.inventory.mainInventory.get(i).isEmpty()
					&& areStackSameIgnoreNBT(stack, player.inventory.mainInventory.get(i))) {
				return i;
			}
		}

		return -1;
	}

	private static boolean areStackSameIgnoreNBT(ItemStack stack1, ItemStack stack2) {
		return stack1.getItem() == stack2.getItem() && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata()) && ItemStack.areItemsEqualIgnoreDurability(stack1, stack2);
	}

	public static boolean shouldItemStackBlock(ItemStack stack, ItemStack offhand){
		return stack.getItemUseAction() == EnumAction.valueOf("mfr_block") && offhand.isEmpty();
	}
}
