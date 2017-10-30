package minefantasy.mf2.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.block.tileentity.TileEntityTanningRack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTanner extends Container {
    private TileEntityTanningRack tile;
    private int lastProgress;
    private int lastProgressMax;

    public ContainerTanner(TileEntityTanningRack tile) {
        this.tile = tile;
        for (int a = 0; a < 2; a++)
            this.addSlotToContainer(new Slot(tile, a, 0, 0));
    }

    @Override
    public void detectAndSendChanges() {
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (this.lastProgress != (int) tile.progress) {
                icrafting.sendProgressBarUpdate(this, 0, (int) tile.progress);
            }
            if (this.lastProgressMax != (int) tile.maxProgress) {
                icrafting.sendProgressBarUpdate(this, 1, (int) tile.maxProgress);
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

                for (int j = 0; j < this.crafters.size(); ++j) {
                    ((ICrafting) this.crafters.get(j)).sendSlotContents(this, i, itemstack1);
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