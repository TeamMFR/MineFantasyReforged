package minefantasy.mf2.container;

import minefantasy.mf2.api.archery.AmmoMechanicsMF;
import minefantasy.mf2.api.archery.IAmmo;
import minefantasy.mf2.api.archery.IFirearm;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerReload extends Container {
    private InventoryBasic weaponInv;
    private ItemStack weapon;
    private ItemStack previousAmmo;

    public ContainerReload(InventoryPlayer user, ItemStack weapon) {
        this.weapon = weapon;
        weaponInv = new InventoryBasic("reload", false, 1);
        weaponInv.setInventorySlotContents(0, AmmoMechanicsMF.getAmmo(weapon));
        this.addSlotToContainer(new SlotReload(this, weaponInv, 0, 79, 11));

        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(user, j + i * 9 + 9, 8 + j * 18, 66 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            if (i != user.currentItem) {
                this.addSlotToContainer(new Slot(user, i, 8 + i * 18, 124));
            }
        }
    }

    @Override
    public ItemStack slotClick(int p_75144_1_, int p_75144_2_, int p_75144_3_, EntityPlayer p_75144_4_) {
        ItemStack result = super.slotClick(p_75144_1_, p_75144_2_, p_75144_3_, p_75144_4_);

        ItemStack ammo = weaponInv.getStackInSlot(0);
        AmmoMechanicsMF.setAmmo(weapon, ammo);

        return result;
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
        previousAmmo = weaponInv.getStackInSlot(0);
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
                if (canAccept(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                } else if (clicked >= 1 && clicked < 27)// INVENTORY
                {
                    if (!this.mergeItemStack(itemstack1, 27, 36, false)) {
                        return null;
                    }
                }
                // BAR
                else if (clicked >= 27 && clicked < 36 && !this.mergeItemStack(itemstack1, 1, 27, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 1, 36, false)) {
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

    public boolean canAccept(ItemStack ammo) {
        String ammoType = "null";
        if (ammo != null && ammo.getItem() instanceof IAmmo) {
            ammoType = ((IAmmo) ammo.getItem()).getAmmoType(ammo);
        }

        if (weapon != null && weapon.getItem() instanceof IFirearm) {
            return ((IFirearm) weapon.getItem()).canAcceptAmmo(weapon, ammoType);
        }

        return ammoType.equalsIgnoreCase("arrow");
    }

    @Override
    public boolean canInteractWith(EntityPlayer user) {
        return true;
    }
}