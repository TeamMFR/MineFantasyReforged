package minefantasy.mf2.hunger;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import minefantasy.mf2.api.MineFantasyAPI;
import net.minecraft.entity.player.EntityPlayer;

public class HungerSystemMF {
    private static final String prevFoodNBT = "MF_previousFoodLevel";
    private static final String tempFoodNBT = "MF_tempFood";
    private static final String saturationNBT = "MF_saturationTicks";
    public static int slowdownRate = 3;

    public static void setSaturation(EntityPlayer user, float value) {
        user.getEntityData().setFloat(saturationNBT, value);
    }

    public static float getSaturation(EntityPlayer user) {
        if (user.getEntityData().hasKey(saturationNBT)) {
            return user.getEntityData().getFloat(saturationNBT);
        }
        return 0F;
    }

    public static void setTempFood(EntityPlayer user, float value) {
        user.getEntityData().setFloat(tempFoodNBT, value);
    }

    public static float getTempFood(EntityPlayer user) {
        if (user.getEntityData().hasKey(tempFoodNBT)) {
            return user.getEntityData().getFloat(tempFoodNBT);
        }
        return 0F;
    }

    public static void decrSaturation(EntityPlayer user) {
        float sat = getSaturation(user);
        sat--;
        if (sat < 0) {
            sat = -1;
        }
        if (!user.worldObj.isRemote) {
            setSaturation(user, sat);
        }
    }

    public static void applySaturation(EntityPlayer consumer, float newValue) {
        float value = getSaturation(consumer);

        if (newValue > value) {
            setSaturation(consumer, newValue);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!event.player.worldObj.isRemote) {
            if (event.phase == TickEvent.Phase.START) {
                event.player.getEntityData().setInteger(prevFoodNBT, event.player.getFoodStats().getFoodLevel());
            }
            if (event.phase == TickEvent.Phase.END) {
                slowHunger(event.player);
            }
        }
    }

    public void slowHunger(EntityPlayer player) {
        // init vars
        int food = player.getFoodStats().getFoodLevel();

        int prevFood = food;
        if (player.getEntityData().hasKey(prevFoodNBT)) {
            prevFood = player.getEntityData().getInteger(prevFoodNBT);
        }

        if (food < prevFood) {
            float sat = getSaturation(player);
            float temp = getTempFood(player);
            MineFantasyAPI.debugMsg("Saturation: " + sat + " Temp: " + temp);

            if (slowdownRate < 0 || temp > 0) {
                MineFantasyAPI.debugMsg("Hunger drop cancelled: Temp");
                setTempFood(player, temp - 1);
                player.getFoodStats().addStats(1, 0.0F);
            } else if (sat > 0) {
                decrSaturation(player);
                MineFantasyAPI.debugMsg("Hunger drop cancelled: Sat = " + (sat - 1));
                player.getFoodStats().addStats(1, 0.0F);
            }

            if (temp <= 0) {
                setTempFood(player, getTempSlowdownLvl(player));
            }
        }
    }

    private float getTempSlowdownLvl(EntityPlayer player) {
        return slowdownRate;
    }
}
