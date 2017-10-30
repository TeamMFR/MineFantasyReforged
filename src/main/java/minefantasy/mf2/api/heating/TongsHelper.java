package minefantasy.mf2.api.heating;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TongsHelper {
    /**
     * Determines if an item is held
     */
    public static boolean hasHeldItem(ItemStack tongs) {
        NBTTagCompound nbt = getNBT(tongs);

        return nbt.hasKey("Held") && nbt.getBoolean("Held");
    }

    /**
     * Empties the item
     *
     * @return
     */
    public static ItemStack clearHeldItem(ItemStack tongs, EntityLivingBase user) {
        if (!user.worldObj.isRemote) {
            NBTTagCompound nbt = getNBT(tongs);
            nbt.setBoolean("Held", false);
        }
        tongs.damageItem(1, user);

        return tongs;
    }

    /**
     * Picks up an item
     */
    public static boolean trySetHeldItem(ItemStack tongs, ItemStack item) {
        if (item == null || item.getItem() == null || !isHotItem(item) || item.getItem() instanceof ItemBlock) {
            return false;
        }
        NBTTagCompound nbt = getNBT(tongs);
        nbt.setBoolean("Held", true);
        NBTTagCompound save = new NBTTagCompound();
        item.writeToNBT(save);
        nbt.setTag("Saved", save);

        return true;
    }

    /**
     * Used to determine if an item burns you when held, and if tongs can pick it up
     */
    public static boolean isHotItem(ItemStack item) {
        if (item.getItem() instanceof IHotItem) {
            return ((IHotItem) item.getItem()).isHot(item);
        }
        return false;
    }

    /**
     * Determines if it can be cooled in a water source
     */
    public static boolean isCoolableItem(ItemStack item) {
        if (item.getItem() instanceof IHotItem) {
            return ((IHotItem) item.getItem()).isCoolable(item);
        }
        return false;
    }

    /**
     * Gets the item picked up
     */
    public static ItemStack getHeldItem(ItemStack tongs) {
        NBTTagCompound nbt = getNBT(tongs);
        if (nbt.hasKey("Held")) {
            if (nbt.getBoolean("Held")) {
                if (nbt.hasKey("Saved")) {
                    NBTTagCompound save = nbt.getCompoundTag("Saved");
                    return ItemStack.loadItemStackFromNBT(save);
                }
            }
        }
        return null;
    }

    /**
     * Gets the item held from tongs
     *
     * @param tongs the itemstack used
     */
    public static ItemStack getHeldItemTongs(ItemStack tongs) {
        NBTTagCompound nbt = getNBT(tongs);
        if (nbt.hasKey("Held")) {
            if (nbt.getBoolean("Held")) {
                if (nbt.hasKey("Saved")) {
                    NBTTagCompound save = nbt.getCompoundTag("Saved");
                    return ItemStack.loadItemStackFromNBT(save);
                }
            }
        }
        return null;
    }

    /**
     * Used for getting the NBT for itemstacks, if none exists; it creates one
     */
    public static NBTTagCompound getNBT(ItemStack item) {
        if (!item.hasTagCompound()) {
            item.setTagCompound(new NBTTagCompound());
        }
        return item.getTagCompound();
    }

    public static float getWaterSource(World world, int i, int j, int k) {
        float special = TongsHelper.getQuenced(world, i, j, k);
        if (special >= 0) {
            return special;
        }
        if (world.getBlock(i, j, k).getMaterial() == Material.water) {
            world.setBlockToAir(i, j, k);
            return 25F;
        }
        if (isCauldron(world, i, j, k)) {
            return 10F;
        }
        return -1F;
    }

    public static boolean isCauldron(World world, int x, int y, int z) {
        return world.getBlock(x, y, z) == Blocks.cauldron && world.getBlockMetadata(x, y, z) > 0;
    }

    public static float getQuenced(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IQuenchBlock) {
            return ((IQuenchBlock) tile).quench();
        }
        return -1F;
    }
}
