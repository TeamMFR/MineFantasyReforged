package minefantasy.mfr.tile;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.util.InventoryUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public abstract class TileEntityBase extends TileEntity {

	public static final String TIER_TAG = "tier";
	public static final String INVENTORY_TAG = "inventory";
	public static final String PROGRESS_TAG = "progress";
	public static final String PROGRESS_MAX_TAG = "progress_max";
	public static final String RESULT_NAME_TAG = "result_name";
	public static final String TOOL_TYPE_REQUIRED_TAG = "tool_type_required";
	public static final String RESEARCH_REQUIRED_TAG = "research_required";
	public static final String QUALITY_TAG = "quality";

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
	public void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
		markDirty();
	}

	private IBlockState getState() {
		return world.getBlockState(pos);
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}

	public abstract boolean isItemValidForSlot(int slot, ItemStack item);
}