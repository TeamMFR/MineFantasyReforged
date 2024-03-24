package minefantasy.mfr.container;

import minefantasy.mfr.config.ConfigWeapon;
import minefantasy.mfr.container.slots.SlotReload;
import minefantasy.mfr.item.ItemCrossbow;
import minefantasy.mfr.mechanics.AmmoMechanics;
import minefantasy.mfr.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ContainerReload extends Container {
	private final ItemStackHandler weaponInv;
	private final ItemStack weapon;

	public ContainerReload(InventoryPlayer user, ItemStack weapon) {
		this.weapon = weapon;
		weaponInv = new ItemStackHandler(1) {
			@Override
			protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
				if (Utils.isVanillaArrow(stack)) {
					return ConfigWeapon.vanillaArrowStackLimit;
				}
				else {
					return super.getStackLimit(slot, stack);
				}
			}
		};
		weaponInv.setStackInSlot(0, AmmoMechanics.getAmmo(weapon));
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
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		ItemStack result = super.slotClick(slotId, dragType, clickTypeIn, player);

		ItemStack ammo = weaponInv.getStackInSlot(0);
		AmmoMechanics.setAmmo(weapon, ammo);
		if (!(weapon.getItem() instanceof ItemCrossbow)) {
			AmmoMechanics.putAmmoOnFirearm(weapon, ammo);
		}

		return result;
	}

	@Override
	public void detectAndSendChanges() {
		for (int i = 0; i < this.inventorySlots.size(); ++i) {
			ItemStack itemstack = this.inventorySlots.get(i).getStack();
			ItemStack itemstack1 = this.inventoryItemStacks.get(i);

			if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
				itemstack1 = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();
				this.inventoryItemStacks.set(i, itemstack1);
				for (IContainerListener listener : this.listeners) {
					(listener).sendSlotContents(this, i, itemstack1);
				}
			}
		}
		ItemStack previousAmmo = weaponInv.getStackInSlot(0);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer user, int clicked) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(clicked);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (clicked > 0)// INVENTORY
			{
				if (canAccept(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (clicked < 27)// INVENTORY
				{
					if (!this.mergeItemStack(itemstack1, 27, 36, false)) {
						return ItemStack.EMPTY;
					}
				}
				// BAR
				else if (clicked < 36 && !this.mergeItemStack(itemstack1, 1, 27, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 1, 36, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(user, itemstack1);
		}

		return itemstack;
	}

	public boolean canAccept(ItemStack ammo) {
		return Utils.canAcceptArrow(ammo, weapon);
	}

	@Override
	public boolean canInteractWith(EntityPlayer user) {
		return true;
	}
}