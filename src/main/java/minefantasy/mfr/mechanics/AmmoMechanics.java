package minefantasy.mfr.mechanics;

import minefantasy.mfr.api.archery.IArrowHandler;
import minefantasy.mfr.block.BlockAmmoBox;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AmmoMechanics {
	public static final String arrowOnBowNBT = "loadedArrow";
	public static final String savedAmmoNBT = "MF_PresetArrow";
	private static final String ammoNBT = "MFammo";
	/**
	 * List of fireable arrows
	 */
	public static List<ItemStack> arrows = new ArrayList<>();
	/**
	 * List of handlers
	 */
	public static HashMap<String, IArrowHandler> handlers = new HashMap<>();

	/**
	 * Adds an arrow that can be fired
	 */
	public static void addArrow(ItemStack item) {
		arrows.add(item);
	}

	/**
	 * Adds an arrow that can be fired not considering sub Ids
	 */
	public static void addArrow(Item item) {
		addArrow(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
	}

	/**
	 * Adds a handler
	 */
	public static void addHandler(String name, IArrowHandler handler) {
		handlers.put(name, handler);
	}

	public static void putAmmoOnFirearm(ItemStack weapon, ItemStack ammo) {
		NBTTagCompound save = new NBTTagCompound();
		if (ammo.isEmpty()) {
			getNBT(weapon).removeTag(arrowOnBowNBT);
			return;
		}
		ammo.writeToNBT(save);
		getNBT(weapon).setTag(arrowOnBowNBT, save);
	}

	/**
	 * Gets the arrow loaded on the bow used for rendering and firing
	 */
	public static ItemStack getArrowOnBow(ItemStack bow) {
		if (!bow.isEmpty() && bow.hasTagCompound()) {
			if (bow.getTagCompound().hasKey(arrowOnBowNBT)) {
				return new ItemStack(bow.getTagCompound().getCompoundTag(arrowOnBowNBT));
			}
		}
		return ItemStack.EMPTY;
	}

	/**
	 * Gets a loaded arrow from a bow
	 */
	public static ItemStack reloadBow(ItemStack bow) {
		if (!bow.hasTagCompound()) {
			return ItemStack.EMPTY;
		}
		if (bow.getTagCompound().hasKey(savedAmmoNBT)) {
			ItemStack ammo = new ItemStack(bow.getTagCompound().getCompoundTag(savedAmmoNBT));

			if (!ammo.isEmpty()) {
				ItemStack arrow = ammo.copy();
				arrow.setCount(1);
				return arrow;
			}
		}
		return ItemStack.EMPTY;
	}

	/**
	 * Removes 1 from the ammo
	 */
	public static void consumeAmmo(EntityPlayer user, ItemStack bow) {
		ItemStack ammo = getAmmo(bow);
		if (user.capabilities.isCreativeMode) {
			return;
		}
		if (!ammo.isEmpty()) {
			ammo.shrink(1);
			if (ammo.getCount() > 0) {
				setAmmo(bow, ammo);
			} else {
				getNBT(bow).removeTag(savedAmmoNBT);
			}
		}
	}

	/**
	 * Gets what ammo is loaded
	 */
	public static ItemStack getAmmo(ItemStack bow) {
		if (!bow.hasTagCompound()) {
			return ItemStack.EMPTY;
		}
		if (bow.getTagCompound().hasKey(savedAmmoNBT)) {
			return new ItemStack(bow.getTagCompound().getCompoundTag(savedAmmoNBT));
		}
		return ItemStack.EMPTY;
	}

	/**
	 * Sets the ammo for the bow
	 */
	public static void setAmmo(ItemStack bow, ItemStack ammo) {
		NBTTagCompound nbt = getNBT(bow);

		if (!ammo.isEmpty()) {
			NBTTagCompound save = new NBTTagCompound();
			ammo.writeToNBT(save);
			nbt.setTag(savedAmmoNBT, save);
		} else {
			nbt.removeTag(savedAmmoNBT);
		}
	}

	public static NBTTagCompound getNBT(ItemStack item) {
		if (!item.hasTagCompound()) {
			item.setTagCompound(new NBTTagCompound());
		}
		return item.getTagCompound();
	}

	public static void removeAmmo(ItemStack bow) {
		getNBT(bow).removeTag(savedAmmoNBT);
	}

	public static boolean isDepleted(ItemStack firearm) {
		return getAmmo(firearm).isEmpty() && EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, firearm) == 0;
	}

	public static boolean isFirearmLoaded(ItemStack firearm) {
		return !getAmmo(firearm).isEmpty() && getGunAmmoCount(firearm) > 0;
	}

	public static void modifyAmmo(ItemStack item, int value) {
		int ammo = getGunAmmoCount(item) + value;
		setGunAmmoCount(item, ammo);
	}

	public static void setGunAmmoCount(ItemStack item, int ammo) {
		NBTTagCompound nbt = getNBT(item);
		nbt.setInteger(ammoNBT, ammo);
	}

	public static int getGunAmmoCount(ItemStack item) {
		if (getArrowOnBow(item).isEmpty()) {
			return 0;
		}
		NBTTagCompound nbt = getNBT(item);

		if (nbt.hasKey(ammoNBT)) {
			return nbt.getInteger(ammoNBT);
		}
		return 1;
	}

	public static void dropAmmoCrate(World world, ItemStack crate, double x, double y, double z) {
		ItemStack drop = BlockAmmoBox.getHeld(crate, true);
		int stock = BlockAmmoBox.getStock(crate, true);
		if (!drop.isEmpty()) {
			while (stock > 0) {
				int stackSize = Math.min(stock, drop.getMaxStackSize());
				stock -= stackSize;
				ItemStack newdrop = drop.copy();
				newdrop.setCount(stackSize);
				entityDropItem(world, x, y, z, newdrop);
			}
		}
	}

	/**
	 * Drops the loaded and storred ammo for an item, from coords
	 */
	public static void dropAmmo(World world, ItemStack firearm, double x, double y, double z) {
		ItemStack ammo = AmmoMechanics.getAmmo(firearm);
		ItemStack loaded = AmmoMechanics.getArrowOnBow(firearm);
		if (!ammo.isEmpty()) {
			entityDropItem(world, x, y, z, ammo);
			AmmoMechanics.setAmmo(firearm, ItemStack.EMPTY);
		}
		if (!loaded.isEmpty()) {
			entityDropItem(world, x, y, z, loaded);
			AmmoMechanics.putAmmoOnFirearm(firearm, ItemStack.EMPTY);
		}
	}

	/**
	 * Drops the loaded and storred ammo for an item from a user
	 */
	public static void dropContents(World world, ItemStack firearm, EntityLivingBase user) {
		dropAmmo(world, firearm, user.posX, user.posY + user.getEyeHeight(), user.posZ);
		dropAmmoCrate(world, firearm, user.posX, user.posY + user.getEyeHeight(), user.posZ);
	}

	private static EntityItem entityDropItem(World world, double x, double y, double z, ItemStack item) {
		if (item.getCount() != 0 && !item.isEmpty()) {
			EntityItem entityitem = new EntityItem(world, x, y, z, item);
			entityitem.setPickupDelay(10);
			world.spawnEntity(entityitem);
			return entityitem;
		} else {
			return null;
		}
	}

	public static void damageContainer(ItemStack item, EntityPlayer user) {
		damageContainer(item, user, 1);
	}

	/**
	 * Damages a firearm, dropping inventory if broken
	 */
	public static void damageContainer(ItemStack item, EntityPlayer user, int dam) {
		item.damageItem(dam, user);
		if (!user.capabilities.isCreativeMode && item.getCount() == 0) {
			if (!user.world.isRemote) {
				dropContents(user.world, item, user);
			}
			user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
		}
	}
}
