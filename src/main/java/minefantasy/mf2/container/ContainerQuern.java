package minefantasy.mf2.container;

import minefantasy.mf2.block.tileentity.TileEntityQuern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerQuern extends Container {
    private TileEntityQuern tile;
    private boolean isGuiContainer = false;

    public ContainerQuern(InventoryPlayer user, TileEntityQuern tile) {
        isGuiContainer = true;
        this.tile = tile;

        this.addSlotToContainer(new SlotRestrictive(tile, 0, 81, 9));
        this.addSlotToContainer(new SlotRestrictive(tile, 1, 81, 32));
        this.addSlotToContainer(new SlotRestrictive(tile, 2, 81, 55));

        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(user, j + i * 9 + 9, 8 + j * 18, 93 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(user, i, 8 + i * 18, 151));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer user) {
        return this.tile.isUseableByPlayer(user);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer user, int clicked) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(clicked);
        // TOTAL SLOTS: 39 = 3+27+9
        // Chamber = 0-2
        // Inv = 2-28
        // Bar = 29-47

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (clicked > 2)// INVENTORY
            {
                if (TileEntityQuern.isInput(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                } else if (TileEntityQuern.isPot(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return null;
                    }
                } else if (clicked >= 3 && clicked < 30)// INVENTORY
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return null;
                    }
                }
                // BAR
                else if (clicked >= 30 && clicked < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(user, itemstack1);
        }

        return itemstack;
    }
}