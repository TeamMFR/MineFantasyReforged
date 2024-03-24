package minefantasy.mfr.container;

import minefantasy.mfr.container.slots.SlotRestrictive;
import minefantasy.mfr.tile.TileEntityCrossbowBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ContainerCrossbowBench extends ContainerBase {
	private final TileEntityCrossbowBench tile;

	public ContainerCrossbowBench(EntityPlayer player, TileEntityCrossbowBench tile) {
		super(player.inventory, tile);
		this.tile = tile;

		this.addSlotToContainer(new SlotRestrictive(tile, 0, 77, 74));
		this.addSlotToContainer(new SlotRestrictive(tile, 1, 77, 48));
		this.addSlotToContainer(new SlotRestrictive(tile, 2, 52, 48));
		this.addSlotToContainer(new SlotRestrictive(tile, 3, 100, 30));
		this.addSlotToContainer(new SlotRestrictive(tile, 4, 147, 48));

		addPlayerSlots(player.inventory, 8, 184);
	}

	@Override
	public void detectAndSendChanges() {
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
	public boolean canInteractWith(EntityPlayer player) {
		return this.tile.isUsableByPlayer(player);
	}

	@Nonnull
	@Override
	public ItemStack transferStackInSlot(EntityPlayer user, int clicked) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(clicked);
		// TOTAL SLOTS: 41 = 5+27+9
		// Crossbow = 0-4
		// Inv = 5-31
		// Bar = 32-40

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (clicked == 4)// OUTPUT
			{
				if (!this.mergeItemStack(itemstack1, 5, 41, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (clicked > 4)// INVENTORY
			{
				if (TileEntityCrossbowBench.isMatch(itemstack1, "stock")) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (TileEntityCrossbowBench.isMatch(itemstack1, "mechanism")) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				} else if (TileEntityCrossbowBench.isMatch(itemstack1, "mod")) {
					if (!this.mergeItemStack(itemstack1, 2, 3, false)) {
						return ItemStack.EMPTY;
					}
				} else if (TileEntityCrossbowBench.isMatch(itemstack1, "muzzle")) {
					if (!this.mergeItemStack(itemstack1, 3, 4, false)) {
						return ItemStack.EMPTY;
					}
				} else if (clicked >= 5 && clicked < 32)// INVENTORY
				{
					if (!this.mergeItemStack(itemstack1, 32, 41, false)) {
						return ItemStack.EMPTY;
					}
				}
				// BAR
				else if (clicked >= 32 && clicked < 41 && !this.mergeItemStack(itemstack1, 5, 32, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 5, 41, false))// BOMB INVENTORY
			{
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