package minefantasy.mfr.container;

import minefantasy.mfr.tile.TileEntityResearchBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerResearchBench extends ContainerBase {
	private final TileEntityResearchBench tile;
	private int lastProgress;
	private int lastProgressMax;

	public ContainerResearchBench(EntityPlayer player, TileEntityResearchBench tile) {
		super(player.inventory, tile);
		this.tile = tile;

		this.addSlotToContainer(new SlotItemHandler(tile.getInventory(), 0, 83, 40));

		addPlayerSlots(player.inventory, 10, 134);
	}

	@Override
	public void detectAndSendChanges() {
		for (IContainerListener listener : this.listeners) {

			if (this.lastProgress != tile.progress) {
				listener.sendWindowProperty(this, 0, (int) tile.progress);
			}
			if (this.lastProgressMax != tile.maxProgress) {
				listener.sendWindowProperty(this, 1, (int) tile.maxProgress);
			}
		}
		this.lastProgress = (int) tile.progress;
		this.lastProgressMax = (int) tile.maxProgress;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		if (id == 0) {
			tile.progress = value;
		}
		if (id == 1) {
			tile.maxProgress = value;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tile.isUsableByPlayer(player);
	}

	@Nonnull
	@Override
	public ItemStack transferStackInSlot(EntityPlayer user, int clicked) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(clicked);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (clicked > 0)// INVENTORY
			{
				if (TileEntityResearchBench.canAccept(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (clicked >= 1 && clicked < 28)// INVENTORY
				{
					if (!this.mergeItemStack(itemstack1, 28, 37, false)) {
						return ItemStack.EMPTY;
					}
				}
				// BAR
				else if (clicked >= 28 && clicked < 37 && !this.mergeItemStack(itemstack1, 1, 28, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 1, 37, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(user, itemstack1);
		}

		return itemstack;
	}
}