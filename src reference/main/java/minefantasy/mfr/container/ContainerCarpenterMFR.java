package minefantasy.mfr.container;

import minefantasy.mfr.tile.TileEntityCarpenterMFR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCarpenterMFR extends Container {
    private TileEntityCarpenterMFR tile;
    private boolean isGuiContainer = false;

    public ContainerCarpenterMFR(TileEntityCarpenterMFR tile) {
        isGuiContainer = false;
        this.tile = tile;
        int width = tile.width;
        int height = tile.height;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int slot = y * width + x;
                this.addSlotToContainer(new Slot(tile, slot, 44 + x * 18, 54 + y * 18));
            }
        }
        this.addSlotToContainer(new Slot(tile, tile.getSizeInventory() - 5, 174, 80));
        for (int y = 0; y < 4; y++) {
            int slot = tile.getSizeInventory() - 4 + y;
            this.addSlotToContainer(new Slot(tile, slot, 3, 54 + y * 18));
        }
    }

    public ContainerCarpenterMFR(InventoryPlayer user, TileEntityCarpenterMFR tile) {
        isGuiContainer = true;
        this.tile = tile;
        int width = tile.width;
        int height = tile.height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int slot = y * width + x;
                this.addSlotToContainer(new Slot(tile, slot, 44 + x * 18, 54 + y * 18));
            }
        }
        this.addSlotToContainer(new Slot(tile, tile.getSizeInventory() - 5, 174, 80));
        for (int y = 0; y < 4; y++) {
            int slot = tile.getSizeInventory() - 4 + y;
            this.addSlotToContainer(new Slot(tile, slot, 3, 54 + y * 18));
        }

        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(user, j + i * 9 + 9, 8 + j * 18, 158 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(user, i, 8 + i * 18, 216));
        }
    }

    @Override
    public void detectAndSendChanges() {
        for (int i = 0; i < this.inventorySlots.size(); ++i) {
            ItemStack itemstack = ((Slot) this.inventorySlots.get(i)).getStack();
            ItemStack itemstack1 = (ItemStack) this.inventoryItemStacks.get(i);

            if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
                if (isGuiContainer) {
                    tile.onInventoryChanged();
                }

                itemstack1 = itemstack == null ? null : itemstack.copy();
                this.inventoryItemStacks.set(i, itemstack1);

                for (int j = 0; j < this.listeners.size(); ++j) {
                    ((InventoryCrafting) this.listeners.get(j)).setInventorySlotContents(i, itemstack1);
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tile.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer user, int currentSlot) {
        int slotCount = tile.getSizeInventory();
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(currentSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (currentSlot < slotCount) {
                if (!this.mergeItemStack(itemstack1, slotCount, this.inventorySlots.size(), false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, slotCount - 5, false)) {
                return null;
            }

            if (itemstack1.getCount() <= 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}