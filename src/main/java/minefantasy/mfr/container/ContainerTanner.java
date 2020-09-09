package minefantasy.mfr.container;

import minefantasy.mfr.tile.TileEntityTanningRack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerTanner extends ContainerBase {
    private TileEntityTanningRack tile;
    private int lastProgress;
    private int lastProgressMax;

    public ContainerTanner(InventoryPlayer inventoryPlayer, TileEntityTanningRack tile) {
        super(inventoryPlayer, tile);
        this.tile = tile;
        for (int a = 0; a < 2; a++)
            this.addSlotToContainer(new SlotItemHandler(tile.getInventory(), a, 0, 0));
    }

    @Override
    public void detectAndSendChanges() {
        for (IContainerListener listener : this.listeners) {

            if (this.lastProgress != (int) tile.progress) {
                listener.sendWindowProperty(this, 0, (int) tile.progress);
            }
            if (this.lastProgressMax != (int) tile.maxProgress) {
                listener.sendWindowProperty(this, 1, (int) tile.maxProgress);
            }
        }
        this.lastProgress = (int) tile.progress;
        this.lastProgressMax = (int) tile.maxProgress;

        for (int i = 0; i < this.inventorySlots.size(); ++i) {
            ItemStack itemstack = this.inventorySlots.get(i).getStack();
            ItemStack itemstack1 = this.inventoryItemStacks.get(i);

            if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {

                itemstack1 = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();
                this.inventoryItemStacks.set(i, itemstack1);

                for (IContainerListener listener : this.listeners) {
                    ((InventoryCrafting) listener).setInventorySlotContents(i, itemstack1);
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

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(EntityPlayer user, int currentSlot) {
        int slotCount = tile.getInventory().getSlots();
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(currentSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (currentSlot < slotCount) {
                if (!this.mergeItemStack(itemstack1, slotCount, this.inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, slotCount - 1, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() <= 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}