package minefantasy.mf2.api.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerTagData {
    /**
     * Saves a custom integer
     */
    public static void setPersistedVariable(EntityPlayer user, String name, int lvl) {
        NBTTagCompound nbt = getPersistedData(user);
        nbt.setInteger(name, lvl);
    }

    /**
     * Gets a custom integer saved
     */
    public static int getPersistedVariable(EntityPlayer user, String name) {
        NBTTagCompound nbt = getPersistedData(user);
        return nbt.hasKey(name) ? nbt.getInteger(name) : 0;
    }

    public static NBTTagCompound getPersistedData(EntityPlayer user) {
        if (!user.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
            user.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
        }
        return (NBTTagCompound) (user.getEntityData().getTag(EntityPlayer.PERSISTED_NBT_TAG));
    }
}
