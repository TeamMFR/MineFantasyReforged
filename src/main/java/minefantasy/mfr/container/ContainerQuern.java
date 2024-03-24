package minefantasy.mfr.container;

import minefantasy.mfr.container.slots.SlotCraftingOutRestrictive;
import minefantasy.mfr.container.slots.SlotRestrictive;
import minefantasy.mfr.tile.TileEntityQuern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ContainerQuern extends ContainerBase {
	private final TileEntityQuern tile;

	public ContainerQuern(EntityPlayer player, InventoryPlayer inventoryPlayer, TileEntityQuern tile) {
		super(inventoryPlayer, tile);
		this.tile = tile;

		this.addSlotToContainer(new SlotRestrictive(tile, 0, 81, 9));
		this.addSlotToContainer(new SlotRestrictive(tile, 1, 81, 32));
		this.addSlotToContainer(new SlotCraftingOutRestrictive(tile, player, 2, 81, 55));

		addPlayerSlots(inventoryPlayer, 8, 151);
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
		// TOTAL SLOTS: 39 = 3+27+9
		// Chamber = 0-2
		// Inv = 2-28
		// Bar = 29-47

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (clicked > 2)// INVENTORY
			{
				if (tile.isInput(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (tile.isPot(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				} else if (clicked >= 3 && clicked < 30)// INVENTORY
				{
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				}
				// BAR
				else if (clicked >= 30 && clicked < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
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