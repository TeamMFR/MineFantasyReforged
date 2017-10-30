package minefantasy.mf2.api.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class EntityHelper {
    public static final String MFNBT = "MineFantasy_KeptNBT";

    public static NBTTagCompound getMFNBT(EntityPlayer player) {
        if (!player.getEntityData().hasKey(MFNBT)) {
            player.getEntityData().setTag(MFNBT, new NBTTagCompound());
        }
        return player.getEntityData().getCompoundTag(MFNBT);
    }

    public static void cloneNBT(EntityPlayer origin, EntityPlayer spawn) {
        if (origin.getEntityData().hasKey(MFNBT)) {
            spawn.getEntityData().setTag(MFNBT, origin.getEntityData().getTag(MFNBT).copy());
        }
    }
}
