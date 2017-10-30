package minefantasy.mf2.container;

import minefantasy.mf2.api.archery.AmmoMechanicsMF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryReload implements IInventory {
    private ItemStack weapon;

    public InventoryReload(ItemStack weapon) {
        this.weapon = weapon;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return AmmoMechanicsMF.getAmmo(weapon);
    }

    @Override
    public ItemStack decrStackSize(int slot, int num) {
        ItemStack ammo = AmmoMechanicsMF.getAmmo(weapon);
        if (ammo != null) {
            ItemStack ammo2 = ammo.copy();
            ammo.stackSize -= num;
            ammo2.stackSize = num;
            if (ammo.stackSize > 0) {
                setInventorySlotContents(slot, ammo);
            } else {
                setInventorySlotContents(slot, null);
            }
            return ammo2;
        }
        return ammo;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        if (item != null) {
            AmmoMechanicsMF.setAmmo(weapon, item);
        } else {
            AmmoMechanicsMF.removeAmmo(weapon);
        }
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer user) {
        return true;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return false;
    }

}
