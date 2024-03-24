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

		for (IContainerListener icontainerlistener : this.listeners) {
			if (this.lastProgress != (int) tile.getProgress()) {
				icontainerlistener.sendWindowProperty(this, 0, (int) tile.getProgress());
			}
			if (this.lastProgressMax != (int) tile.getProgressMax()) {
				icontainerlistener.sendWindowProperty(this, 1, (int) tile.getProgressMax());
			}
			if (this.lastTemp != (int) tile.getTemperature()) {
				icontainerlistener.sendWindowProperty(this, 2, (int) tile.getTemperature());
			}
		}

		this.lastProgress = (int) tile.getProgress();
		this.lastProgressMax = (int) tile.getProgressMax();
		this.lastTemp = (int) tile.getTemperature();

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