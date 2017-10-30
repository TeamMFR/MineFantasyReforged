package minefantasy.mf2.client.render;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;

public class AnimationHandlerMF {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            tickStart(event.player);
        }
    }

    public void tickStart(EntityPlayer entityPlayer) {
    }
}
