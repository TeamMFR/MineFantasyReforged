package minefantasy.mfr.tile;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.util.InventoryUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileEntityBase extends TileEntity {

	/**
	 * Create the inventory.
	 *
	 * @return The inventory
	 */
	protected abstract ItemStackHandler createInventory();


	/**
	 * Get the inventory.
	 *
	 * @return The inventory
	 */
	public abstract ItemStackHandler getInventory();

	/**
	 * Open the GUI for the specified player.
	 *
	 * @param world  The world
	 * @param player The player
	 */
	public void openGUI(final World world, final EntityPlayer player) {
		if (!world.isRemote) {
			player.openGui(MineFantasyReborn.MOD_ID, getGuiId(), world, pos.getX(), pos.getY(), pos.getZ());
		}
	}

	/**
	 * Create a {@link Container} of this inventory for the specified player.
	 *
	 * @param player The player
	 * @return The Container
	 */
	public abstract ContainerBase createContainer(EntityPlayer player);

	/**
	 * Get the GUI ID.
	 *
	 * @return The GUI ID
	 */
	protected abstract int getGuiId();

	public boolean isUsableByPlayer(EntityPlayer user) {
		return user.getDistance(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) < 8D;
	}

	/**
	 * On Block Break, drop inventory items.
	 */
	public void onBlockBreak() {
		if (getInventory() != null){
			InventoryUtils.dropItemsInWorld(world, getInventory(), pos);
		}
	}

	public abstract boolean isItemValidForSlot(int slot, ItemStack item);
}