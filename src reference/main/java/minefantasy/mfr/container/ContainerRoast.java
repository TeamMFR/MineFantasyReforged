package minefantasy.mfr.container;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.tile.TileEntityRoast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRoast extends Container {
    private TileEntityRoast tile;
    private int lastProgress;
    private int lastProgressMax;

    public ContainerRoast(TileEntityRoast tile) {
        this.tile = tile;
        this.addSlotToContainer(new Slot(tile, 0, 0, 0));
    }

    @Override
    public void detectAndSendChanges() {
        for (int i = 0; i < this.listeners.size(); ++i) {
            Container icrafting = (Container) this.listeners.get(i);

            if (this.lastProgress != (int) tile.progress) {
                icrafting.updateProgressBar( 0, (int) tile.progress);
            }
            if (this.lastProgressMax != (int) tile.maxProgress) {
                icrafting.updateProgressBar( 1, (int) tile.maxProgress);
            }
        }
        this.lastProgress = (int) tile.progress;
        this.lastProgressMax = (int) tile.maxProgress;

        for (int i = 0; i < this.inventorySlots.size(); ++i) {
            ItemStack itemstack = ((Slot) this.inventorySlots.get(i)).getStack();
            ItemStack itemstack1 = (ItemStack) this.inventoryItemStacks.get(i);

            if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
                tile.onInventoryChanged();

                itemstack1 = itemstack == null ? null : itemstack.copy();
                this.inventoryItemStacks.set(i, itemstack1);

                for (int j = 0; j < this.listeners.size(); ++j) {
                    ((InventoryCrafting) this.listeners.get(j)).setInventorySlotContents( i, itemstack1);
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
            tile.maxProgress = value;
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