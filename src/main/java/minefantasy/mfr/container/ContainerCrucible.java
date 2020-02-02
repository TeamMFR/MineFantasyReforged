package minefantasy.mfr.container;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.block.tile.TileEntityCrucible;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCrucible extends Container {
    private TileEntityCrucible tile;
    private int lastProgress;
    private int lastProgressMax;
    private int lastTemp;

    public ContainerCrucible(InventoryPlayer user, TileEntityCrucible tile) {
        this.tile = tile;
        int width = 3;
        int height = 3;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int slot = y * width + x;
                this.addSlotToContainer(new Slot(tile, slot, 62 + x * 18, 14 + y * 18));
            }
        }
        this.addSlotToContainer(new SlotCrucibleOut(tile, tile.getSizeInventory() - 1, 129, 32));
        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(user, j + i * 9 + 9, 8 + j * 18, 104 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(user, i, 8 + i * 18, 162));
        }
    }

    @Override
    public void detectAndSendChanges() {
        for (int i = 0; i < this.listeners.size(); ++i) {
            Container icrafting = (Container) this.listeners.get(i);

            if (this.lastProgress != (int) tile.progress) {
                icrafting.updateProgressBar( 0, (int) tile.progress);
            }
            if (this.lastProgressMax != (int) tile.progressMax) {
                icrafting.updateProgressBar( 1, (int) tile.progressMax);
            }
            if (this.lastTemp != (int) tile.temperature) {
                icrafting.updateProgressBar( 2, (int) tile.temperature);
            }
        }
        this.lastProgress = (int) tile.progress;
        this.lastProgressMax = (int) tile.progressMax;
        this.lastTemp = (int) tile.temperature;

        for (int i = 0; i < this.inventorySlots.size(); ++i) {
            ItemStack itemstack = ((Slot) this.inventorySlots.get(i)).getStack();
            ItemStack itemstack1 = (ItemStack) this.inventoryItemStacks.get(i);

            if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
                tile.onInventoryChanged();

                itemstack1 = itemstack == null ? null : itemstack.copy();
                this.inventoryItemStacks.set(i, itemstack1);

                for (int j = 0; j < this.listeners.size(); ++j) {
                    ((InventoryCrafting) this.listeners.get(j)).setInventorySlotContents(i, itemstack1);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {
        if (id == 0) {
            tile.progress = value;
        }

        if (id == 1) {
            tile.progressMax = value;
        }

        if (id == 2) {
            tile.temperature = value;
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
            } else if (!this.mergeItemStack(itemstack1, 0, slotCount - 1, false)) {
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