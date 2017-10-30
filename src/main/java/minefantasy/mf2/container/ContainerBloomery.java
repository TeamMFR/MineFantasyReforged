package minefantasy.mf2.container;

import minefantasy.mf2.block.tileentity.TileEntityBloomery;
import minefantasy.mf2.block.tileentity.blastfurnace.TileEntityBlastFC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBloomery extends Container {
    private TileEntityBloomery tile;
    private boolean isGuiContainer = false;

    public ContainerBloomery(InventoryPlayer user, TileEntityBloomery tile) {
        isGuiContainer = true;
        this.tile = tile;

        this.addSlotToContainer(new SlotRestrictive(tile, 0, 80, 30));
        this.addSlotToContainer(new SlotRestrictive(tile, 1, 80, 68));

        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(user, j + i * 9 + 9, 8 + j * 18, 126 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(user, i, 8 + i * 18, 184));
        }
    }

    @Override
    public void detectAndSendChanges() {
        for (int i = 0; i < this.inventorySlots.size(); ++i) {
            ItemStack itemstack = ((Slot) this.inventorySlots.get(i)).getStack();
            ItemStack itemstack1 = (ItemStack) this.inventoryItemStacks.get(i);

            if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
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
    public ItemStack transferStackInSlot(EntityPlayer user, int clicked) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(clicked);
        // TOTAL SLOTS: 38 = 2+27+9
        // Chamber = 0-1
        // Inv = 2-28
        // Bar = 29-47

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (clicked > 1)// INVENTORY
            {
                if (TileEntityBloomery.isInput(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                } else if (TileEntityBlastFC.isCarbon(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return null;
                    }
                } else if (clicked >= 2 && clicked < 29)// INVENTORY
                {
                    if (!this.mergeItemStack(itemstack1, 29, 38, false)) {
                        return null;
                    }
                }
                // BAR
                else if (clicked >= 29 && clicked < 38 && !this.mergeItemStack(itemstack1, 2, 29, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
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