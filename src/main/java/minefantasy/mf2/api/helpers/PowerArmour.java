package minefantasy.mf2.api.helpers;

import minefantasy.mf2.api.armour.IPowerArmour;
import minefantasy.mf2.api.heating.ForgeFuel;
import minefantasy.mf2.api.heating.ForgeItemHandler;
import minefantasy.mf2.block.crafting.BlockFrame;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class PowerArmour {
    public static boolean isWearingCogwork(EntityLivingBase user) {
        return user.ridingEntity != null && user.ridingEntity instanceof IPowerArmour;
    }

    public static boolean isPowered(EntityLivingBase user) {
        if (user.ridingEntity != null && user.ridingEntity instanceof IPowerArmour) {
            return ((IPowerArmour) user.ridingEntity).isPowered();
        }
        return false;
    }

    public static boolean isFullyArmoured(EntityLivingBase user) {
        if (user.ridingEntity != null && user.ridingEntity instanceof IPowerArmour) {
            return ((IPowerArmour) user.ridingEntity).isFullyArmoured();
        }
        return false;
    }

    public static float modifyDamage(EntityLivingBase user, float dam, DamageSource src) {
        if (user.ridingEntity != null) {
            if (user.ridingEntity instanceof IPowerArmour) {
                return ((IPowerArmour) user.ridingEntity).modifyDamage(user, dam, src);
            }
        }
        return dam;
    }

    public static boolean allowDamageToBlock(DamageSource src) {
        if (src == DamageSource.starve || src == DamageSource.outOfWorld || src == DamageSource.drown) {
            return false;
        }
        return true;
    }

    public static float getFuelValue(ItemStack item) {
        ForgeFuel stats = ForgeItemHandler.getStats(item);
        if (stats != null && stats.isRefined) {
            return stats.duration;
        }
        return 0;
    }

    public static boolean isStationBlock(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        return isBasicStationFrame(world, x, y, z) && block instanceof BlockFrame
                && ((BlockFrame) block).isCogworkHolder;
    }

    public static boolean isBasicStationFrame(World world, int x, int y, int z) {
        if (!(world.getBlock(x, y, z) instanceof BlockFrame)) {
            return false;
        }
        int shafts = 0;

        if (isShaft(world, x + 1, y, z)) {
            ++shafts;
        }
        if (isShaft(world, x - 1, y, z)) {
            ++shafts;
        }
        if (isShaft(world, x, y, z + 1)) {
            ++shafts;
        }
        if (isShaft(world, x, y, z - 1)) {
            ++shafts;
        }

        return shafts == 3;
    }

    public static boolean isShaft(World world, int x, int y, int z) {
        for (int y2 = 0; y2 < 4; y2++) {
            if (!(world.getBlock(x, y, z) instanceof BlockFrame)) {
                return false;
            }
        }
        return true;
    }
}
