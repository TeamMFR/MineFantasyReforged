package minefantasy.mfr.tile.decor;

import minefantasy.mfr.api.heating.IQuenchBlock;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.util.BlockUtils;
import minefantasy.mfr.util.PlayerUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityTrough extends TileEntityWoodDecor implements IQuenchBlock, ITickable {
	public static int capacityScale = 8;
	/**
	 * The number of fill_count entries in the blockstate json
	 */
	private static int fillLevels = 7;
	public int fill;

	public TileEntityTrough() {
		super("trough_wood");
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void update() {
		/* Fill with some water every 5 seconds if its raining and the position */
		if (world.getTotalWorldTime() % 100 == 0 && world.isRainingAt(pos.add(0, 1, 0))) {
			addFluid(1);
		}
	}

	/**
	 * Called when a hot item is soaked into the water of the trough
	 *
	 * @return apparently returns 0F if the trough had water, -1F if it didn't // TODO: does this really needs to be a float? It should probably return a boolean
	 */
	@Override
	public float quench() {
		if (fill >= 6) {
			removeFluid(6);
			return 0F;
		}
		return -1F;
	}

	public boolean interact(EntityPlayer player, ItemStack stack) {
		if (!stack.isEmpty()) {
			int glassBottleAmount = 1, jugAmount = 1, bucketAmount = 16;

			// Add fluid
			if (!isFull()) {
				if (stack.getItem() == Items.WATER_BUCKET) {
					stack.shrink(1);
					PlayerUtils.giveStackToPlayer(player, new ItemStack(Items.BUCKET));
					addFluid(bucketAmount);
					return true;
				}
				if (stack.getItem() == FoodListMFR.JUG_WATER) {
					stack.shrink(1);
					PlayerUtils.giveStackToPlayer(player, new ItemStack(FoodListMFR.JUG_EMPTY));
					addFluid(jugAmount);
					return true;
				}
				if (stack.getItem() == Items.POTIONITEM && PotionUtils.getPotionFromItem(stack) == PotionTypes.WATER) {
					stack.shrink(1);
					PlayerUtils.giveStackToPlayer(player, new ItemStack(Items.GLASS_BOTTLE));
					addFluid(glassBottleAmount);
					return true;
				}
			}

			// Take fluid
			if (!isEmpty()) {
				if (stack.getItem() == Items.GLASS_BOTTLE && PotionUtils.getPotionFromItem(stack) == PotionTypes.EMPTY) {
					stack.shrink(1);
					PlayerUtils.giveStackToPlayer(player, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM, 1), PotionTypes.WATER));
					removeFluid(glassBottleAmount);
					return true;
				}
				if (stack.getItem() == FoodListMFR.JUG_EMPTY) {
					stack.shrink(1);
					PlayerUtils.giveStackToPlayer(player, new ItemStack(FoodListMFR.JUG_WATER));
					removeFluid(jugAmount);
					return true;
				}
				if (fill >= bucketAmount && stack.getItem() == Items.BUCKET) {
					stack.shrink(1);
					PlayerUtils.giveStackToPlayer(player, new ItemStack(Items.WATER_BUCKET));
					removeFluid(bucketAmount);
					return true;
				}
			}
		}
		return false;
	}

	private boolean isFull() {
		return fill == getCapacity();
	}

	private boolean isEmpty() {
		return fill == 0;
	}

	/**
	 * Insert the specified amount of fluid into the trough
	 *
	 * @param amount the fluid amount to insert
	 */
	private void addFluid(int amount) {
		int cap = getCapacity();
		fill = Math.min(cap, fill + amount);
		sendUpdates();
	}

	/**
	 * Attempt to remove the specified amount of fluid from the trough
	 *
	 * @param amount the fluid amount to insert
	 */
	private void removeFluid(int amount) {
		fill = Math.max(0, fill - amount);
		sendUpdates();
	}

	@Override
	public int getCapacity() {
		return super.getCapacity() * capacityScale;
	}

	/**
	 * Used to get the fill_count blockstate
	 *
	 * @return the fill count (0-6). It always returns at least 1, if the trough contains any water!
	 */
	public int getFillCount() {
		float fillPercent = (float) fill / getCapacity();

		int fillCount = 0;
		if (fillPercent > 0) {
			fillCount = Math.max((int) (fillPercent * fillLevels - 1), 1);
		}
		return fillCount;
	}

	public void sendUpdates() {
		BlockUtils.notifyBlockUpdate(this);
		markDirty();
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
		handleUpdateTag(pkt.getNbtCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.readFromNBT(tag);
		BlockUtils.notifyBlockUpdate(this);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("fill", fill);
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		fill = nbt.getInteger("fill");
	}

	///////// UNUSED /////////

	@Override
	protected ItemStackHandler createInventory() {
		return null;
	}

	@Override
	public ItemStackHandler getInventory() {
		return null;
	}

	@Override
	public ContainerBase createContainer(EntityPlayer player) {
		return null;
	}

	@Override
	protected int getGuiId() {
		return 0;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return false;
	}

}
