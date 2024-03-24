package minefantasy.mfr.container;

import minefantasy.mfr.container.slots.SlotBombBench;
import minefantasy.mfr.tile.TileEntityBombBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ContainerBombBench extends ContainerBase {
	private final TileEntityBombBench tile;
	private final boolean isGuiContainer;

	public ContainerBombBench(EntityPlayer player, TileEntityBombBench tile) {
		super(player.inventory, tile);
		isGuiContainer = true;
		this.tile = tile;

		this.addSlotToContainer(new SlotBombBench("case", tile.getInventory(), 0, 77, 74));
		this.addSlotToContainer(new SlotBombBench("powder", tile.getInventory(), 1, 77, 48));
		this.addSlotToContainer(new SlotBombBench("filling", tile.getInventory(), 2, 52, 23));
		this.addSlotToContainer(new SlotBombBench("fuse", tile.getInventory(), 3, 102, 23));
		this.addSlotToContainer(new SlotBombBench("bomb", tile.getInventory(), 4, 147, 48));
		this.addSlotToContainer(new SlotBombBench("misc", tile.getInventory(), 5, 147, 75));

		addPlayerSlots(player.inventory, 8, 184);
	}

	@Override
	public void detectAndSendChanges() {
		for (int i = 0; i < this.inventorySlots.size(); ++i) {
			ItemStack itemstack = this.inventorySlots.get(i).getStack();
			ItemStack itemstack1 = this.inventoryItemStacks.get(i);

			if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
				if (isGuiContainer) {
					tile.onInventoryChanged();
				}

				itemstack1 = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();
				this.inventoryItemStacks.set(i, itemstack1);

				for (IContainerListener listener : this.listeners) {
					listener.sendSlotContents(this, i, itemstack1);
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
		Slot slot = (Slot) this.inventorySlots.get(clicked);
		// TOTAL SLOTS: 41 = 5+27+9
		// Bomb = 0-4
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
				if (TileEntityBombBench.isMatch(itemstack1, "bolt") || TileEntityBombBench.isMatch(itemstack1, "arrow")
						|| TileEntityBombBench.isMatch(itemstack1, "bombcase")
						|| TileEntityBombBench.isMatch(itemstack1, "minecase")) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (TileEntityBombBench.isMatch(itemstack1, "powder")) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				} else if (TileEntityBombBench.isMatch(itemstack1, "filling")) {
					if (!this.mergeItemStack(itemstack1, 2, 3, false)) {
						return ItemStack.EMPTY;
					}
				} else if (TileEntityBombBench.isMatch(itemstack1, "fuse")) {
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