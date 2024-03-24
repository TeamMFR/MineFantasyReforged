package minefantasy.mfr.container;

import minefantasy.mfr.tile.TileEntityForge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerForge extends ContainerBase {
	private final TileEntityForge tile;
	private int lastTemp;

	public ContainerForge(InventoryPlayer inventoryPlayer, TileEntityForge tile) {
		super(inventoryPlayer, tile);
		this.tile = tile;

		this.addSlotToContainer(new SlotItemHandler(this.tile.inventory, 0, 70 + 18, 14 + 18));

		addPlayerSlots(inventoryPlayer, 8, 151);
	}

	@Override
	public void detectAndSendChanges() {
		for (IContainerListener listener : this.listeners) {

			if (this.lastTemp != (int) tile.temperature) {
				listener.sendWindowProperty(this, 0, (int) tile.temperature);
			}
		}
		this.lastTemp = (int) tile.temperature;

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
			tile.temperature = value;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tile.isUsableByPlayer(player);
	}

	@Nonnull
	@Override
	public ItemStack transferStackInSlot(EntityPlayer user, int currentSlot) {
		int slotCount = tile.getInventory().getSlots();
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(currentSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (currentSlot < slotCount) {
				if (!this.mergeItemStack(itemstack1, slotCount, this.inventorySlots.size(), false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(true, itemstack1, 0, slotCount, false)) {
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

	@Override
	protected boolean mergeItemStack(ItemStack item, int minSlot, int maxSlot, boolean goBackwards) {
		return mergeItemStack(true, item, minSlot, maxSlot, goBackwards);
	}

	protected boolean mergeItemStack(boolean allowStack, ItemStack item, int minSlot, int maxSlot, boolean goBackwards) {
		boolean flag1 = false;
		int k = minSlot;

		if (goBackwards) {
			k = maxSlot - 1;
		}

		Slot slot;
		ItemStack itemStack1;

		if (allowStack && item.isStackable()) {
			while (item.getCount() > 0 && (!goBackwards && k < maxSlot || goBackwards && k >= minSlot)) {
				slot = this.inventorySlots.get(k);
				itemStack1 = slot.getStack();

				if (!itemStack1.isEmpty() && itemStack1.getItem() == item.getItem()
						&& (!item.getHasSubtypes() || item.getItemDamage() == itemStack1.getItemDamage())
						&& ItemStack.areItemStackTagsEqual(item, itemStack1)) {
					int l = itemStack1.getCount() + item.getCount();

					if (l <= item.getMaxStackSize()) {
						item.setCount(0);
						itemStack1.setCount(l);
						slot.onSlotChanged();
						flag1 = true;
					} else if (itemStack1.getCount() < item.getMaxStackSize()) {
						item.shrink(item.getMaxStackSize() - itemStack1.getCount());
						itemStack1.setCount(item.getMaxStackSize());
						slot.onSlotChanged();
						flag1 = true;
					}
				}

				if (goBackwards) {
					--k;
				} else {
					++k;
				}
			}
		}

		if (item.getCount() > 0) {
			if (goBackwards) {
				k = maxSlot - 1;
			} else {
				k = minSlot;
			}

			while (!goBackwards && k < maxSlot || goBackwards && k >= minSlot) {
				slot = this.inventorySlots.get(k);
				itemStack1 = slot.getStack();

				if (itemStack1.isEmpty()) {
					ItemStack itemStack2 = item.copy();
					itemStack2.setCount(1);
					slot.putStack(itemStack2);
					slot.onSlotChanged();
					item.shrink(1);
					flag1 = true;
					break;
				}

				if (goBackwards) {
					--k;
				} else {
					++k;
				}
			}
		}

		return flag1;
	}
}