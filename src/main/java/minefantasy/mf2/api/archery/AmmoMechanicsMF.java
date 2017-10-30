package minefantasy.mf2.api.archery;

import minefantasy.mf2.block.decor.BlockAmmoBox;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class AmmoMechanicsMF {
    public static final String arrowOnBowNBT = "loadedArrow";
    public static final String savedAmmoNBT = "MF_PresetArrow";
    private static final String ammoNBT = "MFammo";
    /**
     * List of fireable arrows
     */
    public static List<ItemStack> arrows = new ArrayList<ItemStack>();
    /**
     * List of handlers
     */
    public static List<IArrowHandler> handlers = new ArrayList<IArrowHandler>();

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
    public static void addHandler(IArrowHandler handler) {
        handlers.add(handler);
    }

    public static void putAmmoOnFirearm(ItemStack weapon, ItemStack ammo) {
        NBTTagCompound save = new NBTTagCompound();
        if (ammo == null) {
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
        if (bow != null && bow.hasTagCompound()) {
            if (bow.getTagCompound().hasKey(arrowOnBowNBT)) {
                return ItemStack.loadItemStackFromNBT(bow.getTagCompound().getCompoundTag(arrowOnBowNBT));
            }
        }
        return null;
    }

    /**
     * Gets a loaded arrow from a bow
     */
    public static ItemStack reloadBow(ItemStack bow) {
        if (!bow.hasTagCompound()) {
            return null;
        }
        if (bow.getTagCompound().hasKey(savedAmmoNBT)) {
            ItemStack ammo = ItemStack.loadItemStackFromNBT(bow.getTagCompound().getCompoundTag(savedAmmoNBT));

            if (ammo != null) {
                ItemStack arrow = ammo.copy();
                arrow.stackSize = 1;
                return arrow;
            }
        }
        return null;
    }

    /**
     * Removes 1 from the ammo
     */
    public static void consumeAmmo(EntityPlayer user, ItemStack bow) {
        ItemStack ammo = getAmmo(bow);
        if (user.capabilities.isCreativeMode) {
            return;
        }
        if (ammo != null) {
            --ammo.stackSize;
            if (ammo.stackSize > 0) {
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
            return null;
        }
        if (bow.getTagCompound().hasKey(savedAmmoNBT)) {
            return ItemStack.loadItemStackFromNBT(bow.getTagCompound().getCompoundTag(savedAmmoNBT));
        }
        return null;
    }

    /**
     * Sets the ammo for the bow
     */
    public static void setAmmo(ItemStack bow, ItemStack ammo) {
        NBTTagCompound nbt = getNBT(bow);

        if (ammo != null) {
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
        return getGunAmmoCount(firearm) == 0 && getAmmo(firearm) == null
                && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, firearm) == 0;
    }

    public static boolean isFirearmLoaded(ItemStack firearm) {
        return getGunAmmoCount(firearm) > 0;
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
        if (getArrowOnBow(item) == null) {
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
        if (drop != null) {
            while (stock > 0) {
                int stackSize = Math.min(stock, drop.getMaxStackSize());
                stock -= stackSize;
                ItemStack newdrop = drop.copy();
                newdrop.stackSize = stackSize;
                entityDropItem(world, x, y, z, newdrop);
            }
        }
    }

    /**
     * Drops the loaded and storred ammo for an item, from coords
     */
    public static void dropAmmo(World world, ItemStack firearm, double x, double y, double z) {
        ItemStack ammo = AmmoMechanicsMF.getAmmo(firearm);
        ItemStack loaded = AmmoMechanicsMF.getArrowOnBow(firearm);
        if (ammo != null) {
            entityDropItem(world, x, y, z, ammo);
            AmmoMechanicsMF.setAmmo(firearm, null);
        }
        if (loaded != null) {
            entityDropItem(world, x, y, z, loaded);
            AmmoMechanicsMF.putAmmoOnFirearm(firearm, null);
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
        if (item.stackSize != 0 && item.getItem() != null) {
            EntityItem entityitem = new EntityItem(world, x, y, z, item);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
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
        if (!user.capabilities.isCreativeMode && item.stackSize == 0) {
            if (!user.worldObj.isRemote) {
                dropContents(user.worldObj, item, user);
            }
            user.setCurrentItemOrArmor(0, null);
        }
    }
}
