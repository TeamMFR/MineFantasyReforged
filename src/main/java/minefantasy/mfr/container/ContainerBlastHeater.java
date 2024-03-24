package minefantasy.mfr.container;

import minefantasy.mfr.tile.blastfurnace.TileEntityBlastHeater;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerBlastHeater extends ContainerBase {
	private final TileEntityBlastHeater tile;
	private int lastFuel;
	private int lastFuelMax;

	public ContainerBlastHeater(InventoryPlayer playerInventory, TileEntityBlastHeater tile) {
		super(playerInventory, tile);
		this.tile = tile;

		IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		this.addSlotToContainer(new SlotItemHandler(inventory, 0, 80, 76));

		addPlayerSlots(playerInventory, 8, 184);
	}

	@Override
	public void detectAndSendChanges() {

		super.detectAndSendChanges();

		for (IContainerListener icontainerlistener : this.listeners) {
			if (this.lastFuel != tile.fuel) {
				icontainerlistener.sendWindowProperty(this, 0, tile.fuel);
			}
			if (this.lastFuelMax != tile.maxFuel) {
				icontainerlistener.sendWindowProperty(this, 1, tile.maxFuel);
			}
		}

		this.lastFuel = tile.fuel;
		this.lastFuelMax = tile.maxFuel;

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
			tile.fuel = value;
		}

		if (id == 1) {
			tile.maxFuel = value;
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
				if (TileEntityBlastHeater.isFuel(itemstack1)) {
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