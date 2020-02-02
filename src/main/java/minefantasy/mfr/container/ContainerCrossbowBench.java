package minefantasy.mfr.container;

import minefantasy.mfr.block.tile.TileEntityCrossbowBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCrossbowBench extends Container {
    private TileEntityCrossbowBench tile;
    private boolean isGuiContainer = false;

    public ContainerCrossbowBench(InventoryPlayer user, TileEntityCrossbowBench tile) {
        isGuiContainer = true;
        this.tile = tile;

        this.addSlotToContainer(new SlotRestrictive(tile, 0, 77, 74));
        this.addSlotToContainer(new SlotRestrictive(tile, 1, 77, 48));
        this.addSlotToContainer(new SlotRestrictive(tile, 2, 52, 48));
        this.addSlotToContainer(new SlotRestrictive(tile, 3, 100, 30));
        this.addSlotToContainer(new SlotRestrictive(tile, 4, 147, 48));

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
                if (isGuiContainer) {
                    tile.onInventoryChanged();
                }

                itemstack1 = itemstack == null ? null : itemstack.copy();
                this.inventoryItemStacks.set(i, itemstack1);

                for (int j = 0; j < this.listeners.size(); ++j) {
                    ((InventoryCrafting) this.listeners.get(j)).setInventorySlotContents( i, itemstack1);
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tile.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer user, int clicked) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(clicked);
        // TOTAL SLOTS: 41 = 5+27+9
        // Crossbow = 0-4
        // Inv = 5-31
        // Bar = 32-40

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (clicked == 4)// OUTPUT
            {
                if (!this.mergeItemStack(itemstack1, 5, 41, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (clicked > 4)// INVENTORY
            {
                if (TileEntityCrossbowBench.isMatch(itemstack1, "stock")) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                } else if (TileEntityCrossbowBench.isMatch(itemstack1, "mechanism")) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return null;
                    }
                } else if (TileEntityCrossbowBench.isMatch(itemstack1, "mod")) {
                    if (!this.mergeItemStack(itemstack1, 2, 3, false)) {
                        return null;
                    }
                } else if (TileEntityCrossbowBench.isMatch(itemstack1, "muzzle")) {
                    if (!this.mergeItemStack(itemstack1, 3, 4, false)) {
                        return null;
                    }
                } else if (clicked >= 5 && clicked < 32)// INVENTORY
                {
                    if (!this.mergeItemStack(itemstack1, 32, 41, false)) {
                        return null;
                    }
                }
                // BAR
                else if (clicked >= 32 && clicked < 41 && !this.mergeItemStack(itemstack1, 5, 32, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 5, 41, false))// BOMB INVENTORY
            {
                return null;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return null;
            }

            slot.onTake(user, itemstack1);
        }

        return itemstack;
    }
}