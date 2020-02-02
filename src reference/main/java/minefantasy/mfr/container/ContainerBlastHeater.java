package minefantasy.mfr.container;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.block.tile.blastfurnace.TileEntityBlastFH;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
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
        for (int i = 0; i < this.listeners.size(); ++i) {
            Container icrafting = (Container) this.listeners.get(i);

            if (this.lastFuel != tile.fuel) {
                icrafting.updateProgressBar( 0, tile.fuel);
            }

            if (this.lastFuelMax != tile.maxFuel) {
                icrafting.updateProgressBar( 1, tile.maxFuel);
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
            tile.fuel = value;
        }

        if (id == 1) {
            tile.maxFuel = value;
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