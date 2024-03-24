package minefantasy.mfr.tile;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.recipe.IRecipeMFR;
import minefantasy.mfr.util.InventoryUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public abstract class TileEntityBase extends TileEntity {

	public static final String TIER_TAG = "tier";
	public static final String INVENTORY_TAG = "inventory";
	public static final String PROGRESS_TAG = "progress";
	public static final String PROGRESS_MAX_TAG = "progress_max";
	public static final String RESULT_STACK_TAG = "result_stack";
	public static final String RESULT_STACK_SPECIAL_TAG = "result_stack_special";
	public static final String RESEARCH_REQUIRED_TAG = "research_required";
	public static final String QUALITY_TAG = "quality";
	public static final String RECIPE_NAME_TAG = "recipe";
	public static final String KNOWN_RESEARCHES_TAG = "knownResearches";

	private IRecipeMFR recipe;

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

	public void setRecipe(IRecipeMFR recipe) {
		this.recipe = recipe;
	}

	public IRecipeMFR getRecipe() {
		return recipe;
	}

	public IRecipeMFR getRecipeByOutput(ItemStack stack) {
		return null;
	}

	/**
	 * Open the GUI for the specified player.
	 *
	 * @param world  The world
	 * @param player The player
	 */
	public void openGUI(final World world, final EntityPlayer player) {
		if (!world.isRemote) {
			player.openGui(MineFantasyReforged.MOD_ID, getGuiId(), world, pos.getX(), pos.getY(), pos.getZ());
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
		if (getInventory() != null) {
			InventoryUtils.dropItemsInWorld(world, getInventory(), pos);
		}
	}

	public void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
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

	public static int calculateRedstoneFromInventory(ItemStackHandler inv)
	{
		if (inv == null)
		{
			return 0;
		}
		else
		{
			int i = 0;
			float f = 0.0F;

			for (int j = 0; j < inv.getSlots(); ++j)
			{
				ItemStack itemstack = inv.getStackInSlot(j);

				if (!itemstack.isEmpty())
				{
					f += (float)itemstack.getCount() / (float)Math.min(inv.getSlots(), itemstack.getMaxStackSize());
					++i;
				}
			}

			f = f / (float)inv.getSlots();
			return MathHelper.floor(f * 14.0F) + (i > 0 ? 1 : 0);
		}
	}
}