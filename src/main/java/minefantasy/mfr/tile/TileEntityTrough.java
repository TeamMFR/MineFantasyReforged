package minefantasy.mfr.tile;

import minefantasy.mfr.api.heating.IQuenchBlock;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.item.ItemBlockTrough;
import minefantasy.mfr.util.BlockUtils;
import minefantasy.mfr.util.CustomToolHelper;
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

public class TileEntityTrough extends TileEntityWoodDecor implements IQuenchBlock, ITickable {
	public static int capacityScale = 8;
	public int colorInt;
	/**
	 * The number of fill_count entries in the blockstate json
	 */
	private static final int fillLevels = 7;
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
		if (!isEmpty()) {
			removeFluid(1);
			return 0F;
		}
		return -1F;
	}

	public void setColorInt(ItemStack stack) {
		if (stack.getItem() instanceof ItemBlockTrough) {
			this.colorInt = CustomToolHelper.getColourFromItemStack(stack, 0);
		}
	}

	public int getColorInt() {
		return colorInt;
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
				if (stack.getItem() == MineFantasyItems.JUG_WATER) {
					stack.shrink(1);
					PlayerUtils.giveStackToPlayer(player, new ItemStack(MineFantasyItems.JUG_EMPTY));
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
				if (stack.getItem() == MineFantasyItems.JUG_EMPTY) {
					stack.shrink(1);
					PlayerUtils.giveStackToPlayer(player, new ItemStack(MineFantasyItems.JUG_WATER));
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

	public boolean isFull() {
		return fill == getCapacity();
	}

	public boolean isEmpty() {
		return fill == 0;
	}

	public int getFill() {
		return fill;
	}

	public void setFill(int fill) {
		this.fill = fill;
	}

	/**
	 * Insert the specified amount of fluid into the trough
	 *
	 * @param amount the fluid amount to insert
	 */
	public void addFluid(int amount) {
		int cap = getCapacity();
		fill = Math.min(cap, fill + amount);
		markDirty();
		BlockUtils.notifyBlockUpdate(this);
	}

	/**
	 * Attempt to remove the specified amount of fluid from the trough
	 *
	 * @param amount the fluid amount to insert
	 */
	public void removeFluid(int amount) {
		fill = Math.max(0, fill - amount);
		markDirty();
		BlockUtils.notifyBlockUpdate(this);
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
		if (fillCount >= 7) {
			fillCount = 6;
		}
		return fillCount;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeUpdateNBT(tag);
		return new SPacketUpdateTileEntity(pos, 0, tag);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		writeUpdateNBT(tag);
		return tag;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		handleUpdateTag(pkt.getNbtCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		readNBT(tag);
		BlockUtils.notifyBlockUpdate(this);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		return writeNBT(nbt);
	}

	public NBTTagCompound writeNBT(NBTTagCompound nbt) {
		nbt.setInteger("fill", fill);
		nbt.setInteger("color_int", colorInt);
		return nbt;
	}

	protected void writeUpdateNBT(NBTTagCompound tag) {
		writeNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		readNBT(nbt);
		markDirty();
	}

	public void readNBT(NBTTagCompound nbt) {
		fill = nbt.getInteger("fill");
		colorInt = nbt.getInteger("color_int");
		markDirty();
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
