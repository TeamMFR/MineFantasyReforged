package minefantasy.mf2.client.render;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.item.archery.ItemBowMF;
import minefantasy.mf2.item.gadget.IScope;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

@SideOnly(Side.CLIENT)
public class HudHandlerMF {
    private MineFantasyHUD inGameGUI = new MineFantasyHUD();

    @SubscribeEvent
    public void postRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            inGameGUI.renderGameOverlay(event.partialTicks, event.mouseX, event.mouseY);
        }
        if (event.type == RenderGameOverlayEvent.ElementType.HELMET) {
            inGameGUI.renderViewport();
        }
    }

    @SubscribeEvent
    public void onBowFOV(FOVUpdateEvent event) {
        ItemStack stack = event.entity.getItemInUse();
        if (stack != null && stack.getItem() instanceof ItemBowMF) {
            int i = event.entity.getItemInUseDuration();
            float f1 = i / 20.0F;
            if (f1 > 1.0F) {
                f1 = 1.0F;
            } else {
                f1 *= f1;
            }
            event.newfov *= 1.0F - f1 * 0.15F;
        }
        if (stack != null && stack.getItem() instanceof IScope) {
            IScope spyglass = (IScope) stack.getItem();
            event.newfov *= 1.0F - spyglass.getZoom(stack);
        }
    }
}
