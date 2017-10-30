package minefantasy.mf2.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.block.tileentity.TileEntityBigFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 */
public class ContainerBigFurnace extends Container {
    public TileEntityBigFurnace smelter;
    public int lastProgress;
    public int lastFuel;
    public int lastMaxFuel;
    public int lastHeat;
    public int lastMaxHeat;

    public ContainerBigFurnace(EntityPlayer play, TileEntityBigFurnace tileentityfurnace) {
        lastProgress = 0;
        lastFuel = 0;
        lastMaxFuel = 0;
        lastHeat = 0;
        lastMaxHeat = 0;
        smelter = tileentityfurnace;
        tileentityfurnace.openChest();

        if (smelter.isHeater()) {
            addSlotToContainer(new Slot(smelter, 0, 59, 44));
        } else {
            // IN
            addSlotToContainer(new Slot(smelter, 0, 36, 26));
            addSlotToContainer(new Slot(smelter, 1, 54, 26));
            addSlotToContainer(new Slot(smelter, 2, 36, 44));
            addSlotToContainer(new Slot(smelter, 3, 54, 44));

            // OUT
            addSlotToContainer(new SlotFurnace(play, smelter, 4, 106, 26));
            addSlotToContainer(new SlotFurnace(play, smelter, 5, 124, 26));
            addSlotToContainer(new SlotFurnace(play, smelter, 6, 106, 44));
            addSlotToContainer(new SlotFurnace(play, smelter, 7, 124, 44));
        }

        // PLAYER INV
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 9; k++) {
                addSlotToContainer(new Slot(play.inventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }

        for (int j = 0; j < 9; j++) {
            addSlotToContainer(new Slot(play.inventory, j, 8 + j * 18, 142));
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        this.smelter.closeChest();
    }

    @Override
    public void addCraftingToCrafters(ICrafting icrafting) {
        super.addCraftingToCrafters(icrafting);
        icrafting.sendProgressBarUpdate(this, 0, smelter.progress);
        icrafting.sendProgressBarUpdate(this, 1, smelter.fuel);
        icrafting.sendProgressBarUpdate(this, 2, smelter.maxFuel);
        icrafting.sendProgressBarUpdate(this, 3, (int) smelter.heat);
        icrafting.sendProgressBarUpdate(this, 4, (int) smelter.maxHeat);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (this.lastProgress != this.smelter.progress) {
                icrafting.sendProgressBarUpdate(this, 0, this.smelter.progress);
            }

            if (this.lastFuel != this.smelter.fuel) {
                icrafting.sendProgressBarUpdate(this, 1, this.smelter.fuel);
            }

            if (this.lastMaxFuel != this.smelter.maxFuel) {
                icrafting.sendProgressBarUpdate(this, 2, this.smelter.maxFuel);
            }

            if (this.lastHeat != this.smelter.heat) {
                icrafting.sendProgressBarUpdate(this, 3, (int) this.smelter.heat);
            }

            if (this.lastMaxHeat != this.smelter.maxHeat) {
                icrafting.sendProgressBarUpdate(this, 4, (int) this.smelter.maxHeat);
            }

        }

        this.lastProgress = this.smelter.progress;
        this.lastFuel = this.smelter.fuel;
        this.lastMaxFuel = this.smelter.maxFuel;
        this.lastHeat = (int) this.smelter.heat;
        this.lastMaxHeat = (int) this.smelter.maxHeat;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {
        if (id == 0) {
            this.smelter.progress = value;
        }

        if (id == 1) {
            this.smelter.fuel = value;
        }

        if (id == 2) {
            this.smelter.maxFuel = value;
        }

        if (id == 3) {
            this.smelter.heat = value;
        }

        if (id == 4) {
            this.smelter.maxHeat = value;
        }
    }

    public boolean canInteractWith(EntityPlayer entityplayer) {
        return smelter.isUseableByPlayer(entityplayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int num) {
        int invSize = 8;
        if (smelter.isHeater()) {
            invSize = 1;
        }
        ItemStack placedItem = null;
        Slot slot = (Slot) this.inventorySlots.get(num);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemSlot = slot.getStack();
            placedItem = itemSlot.copy();

            // Take
            if (num < invSize) {
                if (!this.mergeItemStack(itemSlot, invSize, 36 + invSize, true)) {
                    return null;
                }

                slot.onSlotChange(itemSlot, placedItem);
            }
            // Put
            else {
                if (smelter.isHeater()) {
                    if (smelter.isItemFuel(itemSlot)) {
                        if (!this.mergeItemStack(itemSlot, 0, 1, false)) {
                            return null;
                        }
                    }
                } else {
                    if (smelter.getResult(itemSlot) != null) {
                        if (!this.mergeItemStack(itemSlot, 0, 4, false)) {
                            return null;
                        }
                    }
                }

                slot.onSlotChange(itemSlot, placedItem);
            }

            if (itemSlot.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (itemSlot.stackSize == placedItem.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, itemSlot);
        }

        return placedItem;
    }
}
