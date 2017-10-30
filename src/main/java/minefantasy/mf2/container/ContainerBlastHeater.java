package minefantasy.mf2.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.block.tileentity.blastfurnace.TileEntityBlastFH;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBlastHeater extends Container {
    private TileEntityBlastFH tile;
    private boolean isGuiContainer = false;
    private int lastFuel;
    private int lastFuelMax;

    public ContainerBlastHeater(InventoryPlayer user, TileEntityBlastFH tile) {
        isGuiContainer = true;
        this.tile = tile;

        this.addSlotToContainer(new Slot(tile, 0, 80, 76));

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
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (this.lastFuel != tile.fuel) {
                icrafting.sendProgressBarUpdate(this, 0, tile.fuel);
            }

            if (this.lastFuelMax != tile.maxFuel) {
                icrafting.sendProgressBarUpdate(this, 1, tile.maxFuel);
            }
        }
        this.lastFuel = tile.fuel;
        this.lastFuelMax = tile.maxFuel;

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
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {
        if (id == 0) {
            tile.fuel = value;
        }

        if (id == 1) {
            tile.maxFuel = value;
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

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (clicked > 0)// INVENTORY
            {
                if (TileEntityBlastFH.isFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                } else if (clicked >= 1 && clicked < 28)// INVENTORY
                {
                    if (!this.mergeItemStack(itemstack1, 28, 37, false)) {
                        return null;
                    }
                }
                // BAR
                else if (clicked >= 28 && clicked < 37 && !this.mergeItemStack(itemstack1, 1, 28, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 1, 37, false)) {
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