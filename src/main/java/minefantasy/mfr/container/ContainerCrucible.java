package minefantasy.mfr.container;

import minefantasy.mfr.container.slots.SlotCrucibleOut;
import minefantasy.mfr.tile.TileEntityCrucible;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ContainerCrucible extends ContainerBase {

	private final TileEntityCrucible tile;
	private int lastProgress;
	private int lastProgressMax;
	private int lastTemp;

	public ContainerCrucible(TileEntityCrucible tile) {
		this.tile = tile;
	}

	public ContainerCrucible(EntityPlayer player, InventoryPlayer playerInventory, TileEntityCrucible tile) {
		super(playerInventory, tile);

		this.tile = tile;

		addTileSlots(3, 3, 62, 14);

		this.addSlotToContainer(new SlotCrucibleOut(this.tile, player, this.tile.inventory.getSlots() - 1, 129, 32));

		addPlayerSlots(playerInventory, 8, 162);

	}

	@Override
	public void detectAndSendChanges() {

		super.detectAndSendChanges();

		for (IContainerListener listener : this.listeners) {
			if (this.lastProgress !=  tile.getProgress()) {
				listener.sendWindowProperty(this, 0,  tile.getProgress());
			}
			if (this.lastProgressMax !=  tile.getProgressMax()) {
				listener.sendWindowProperty(this, 1,  tile.getProgressMax());
			}
			if (this.lastTemp !=  tile.getTemperature()) {
				listener.sendWindowProperty(this, 2,  tile.getTemperature());
			}
		}

		this.lastProgress = tile.getProgress();
		this.lastProgressMax = tile.getProgressMax();
		this.lastTemp = tile.getTemperature();

		for (int i = 0; i < this.inventorySlots.size(); ++i) {
			ItemStack itemstack = this.inventorySlots.get(i).getStack();
			ItemStack itemstack1 = this.inventoryItemStacks.get(i);

			if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {

				itemstack1 = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();
				this.inventoryItemStacks.set(i, itemstack1);

				for (IContainerListener listener : this.listeners) {
					(listener).sendSlotContents(this, i, itemstack1);
				}
			}
		}
	}

	@Override
	public void addListener(IContainerListener listener) {
		if (this.lastProgress != tile.getProgress()) {
			listener.sendWindowProperty(this, 0, tile.getProgress());
		}
		if (this.lastProgressMax != tile.getProgressMax()) {
			listener.sendWindowProperty(this, 1, tile.getProgressMax());
		}
		if (this.lastTemp != tile.getTemperature()) {
			listener.sendWindowProperty(this, 2, tile.getTemperature());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		if (id == 0) {
			tile.setProgress(value);
		}

		if (id == 1) {
			tile.setProgressMax(value);
		}

		if (id == 2) {
			tile.setTemperature(value);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tile.isUsableByPlayer(player);
	}

	@Nonnull
	@Override
	public ItemStack transferStackInSlot(final EntityPlayer player, final int currentSlot) {
		int slotCount = tile.inventory.getSlots();
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(currentSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (currentSlot < slotCount) {
				if (!this.mergeItemStack(itemstack1, slotCount, this.inventorySlots.size(), false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, slotCount - 1, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() <= 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}
}