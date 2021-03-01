package minefantasy.mfr.container;

import minefantasy.mfr.mechanics.AmmoMechanics;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

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
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return AmmoMechanics.getAmmo(weapon);
	}

	@Override
	public ItemStack decrStackSize(int slot, int num) {
		ItemStack ammo = AmmoMechanics.getAmmo(weapon);
		if (ammo != null) {
			ItemStack ammo2 = ammo.copy();
			ammo.shrink(num);
			ammo2.setCount(num);
			if (ammo.getCount() > 0) {
				setInventorySlotContents(slot, ammo);
			} else {
				setInventorySlotContents(slot, null);
			}
			return ammo2;
		}
		return ammo;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack item) {
		if (item != null) {
			AmmoMechanics.setAmmo(weapon, item);
		} else {
			AmmoMechanics.removeAmmo(weapon);
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return false;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {

	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}
}
