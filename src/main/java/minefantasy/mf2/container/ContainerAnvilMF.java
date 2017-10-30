package minefantasy.mf2.container;

import minefantasy.mf2.block.tileentity.TileEntityAnvilMF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAnvilMF extends Container {
    private TileEntityAnvilMF tile;
    private boolean isGuiContainer = false;
    private int xInvOffset = 28;

    public ContainerAnvilMF(TileEntityAnvilMF tile) {
        isGuiContainer = false;
        this.tile = tile;
        int width = 6;
        int height = 4;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int slot = y * width + x;
                this.addSlotToContainer(new Slot(tile, slot, 8 + x * 18, 38 + y * 18));
            }
        }
        this.addSlotToContainer(new Slot(tile, tile.getSizeInventory() - 1, 150, 65));
    }

    public ContainerAnvilMF(InventoryPlayer user, TileEntityAnvilMF tile) {
        isGuiContainer = true;
        this.tile = tile;
        int width = 6;
        int height = 4;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int slot = y * width + x;
                this.addSlotToContainer(new Slot(tile, slot, 44 + x * 18, 39 + y * 18));
            }
        }
        this.addSlotToContainer(new Slot(tile, tile.getSizeInventory() - 1, 214, 66));
        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(user, j + i * 9 + 9, 8 + j * 18 + xInvOffset, 128 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(user, i, 8 + i * 18 + xInvOffset, 186));
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

                for (int j = 0; j < this.crafters.size(); ++j) {
                    ((ICrafting) this.crafters.get(j)).sendSlotContents(this, i, itemstack1);
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_) {
        return this.tile.isUseableByPlayer(p_75145_1_);
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
            } else if (!this.mergeItemStack(itemstack1, 0, slotCount - 1, false)) {
                return null;
            }

            if (itemstack1.stackSize <= 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}