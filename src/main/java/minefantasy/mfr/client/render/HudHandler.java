package minefantasy.mfr.client.render;

import minefantasy.mfr.api.tool.IScope;
import minefantasy.mfr.item.ItemBowMFR;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HudHandler {
	private final MineFantasyHUD inGameGUI = new MineFantasyHUD();

	@SubscribeEvent
	public void postRenderOverlay(RenderGameOverlayEvent.Post event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
			inGameGUI.renderGameOverlay();
		}
		if (event.getType() == RenderGameOverlayEvent.ElementType.HELMET) {
			inGameGUI.renderViewport();
		}
	}

	@SubscribeEvent()
	public void onHudRender(RenderGameOverlayEvent event){
		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR && MineFantasyHUD.isScoped){
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onBowFOV(FOVUpdateEvent event) {
		ItemStack stack = event.getEntity().getActiveItemStack();
		if (!stack.isEmpty() && stack.getItem() instanceof ItemBowMFR) {
			int i = event.getEntity().getItemInUseMaxCount();
			float f1 = i / 20.0F;
			if (f1 > 1.0F) {
				f1 = 1.0F;
			} else {
				f1 *= f1;
			}
			event.setNewfov(1.0F - f1 * 0.15F);
		}
		if (!stack.isEmpty() && stack.getItem() instanceof IScope) {
			IScope spyglass = (IScope) stack.getItem();
			event.setNewfov(1.0F - spyglass.getZoom(stack));
		}
	}
}
