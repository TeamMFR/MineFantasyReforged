package minefantasy.mf2.container;

import minefantasy.mf2.block.tileentity.TileEntityBombBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBombBench extends Container {
    private TileEntityBombBench tile;
    private boolean isGuiContainer = false;

    public ContainerBombBench(InventoryPlayer user, TileEntityBombBench tile) {
        isGuiContainer = true;
        this.tile = tile;

        this.addSlotToContainer(new SlotBombBench("case", tile, 0, 77, 74));
        this.addSlotToContainer(new SlotBombBench("powder", tile, 1, 77, 48));
        this.addSlotToContainer(new SlotBombBench("filling", tile, 2, 52, 23));
        this.addSlotToContainer(new SlotBombBench("fuse", tile, 3, 102, 23));
        this.addSlotToContainer(new SlotBombBench("bomb", tile, 4, 147, 48));
        this.addSlotToContainer(new SlotBombBench("misc", tile, 5, 147, 75));

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
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (clicked > 4)// INVENTORY
            {
                if (TileEntityBombBench.isMatch(itemstack1, "bolt") || TileEntityBombBench.isMatch(itemstack1, "arrow")
                        || TileEntityBombBench.isMatch(itemstack1, "bombcase")
                        || TileEntityBombBench.isMatch(itemstack1, "minecase")) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                } else if (TileEntityBombBench.isMatch(itemstack1, "powder")) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return null;
                    }
                } else if (TileEntityBombBench.isMatch(itemstack1, "filling")) {
                    if (!this.mergeItemStack(itemstack1, 2, 3, false)) {
                        return null;
                    }
                } else if (TileEntityBombBench.isMatch(itemstack1, "fuse")) {
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