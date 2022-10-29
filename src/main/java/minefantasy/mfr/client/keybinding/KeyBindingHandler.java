package minefantasy.mfr.client.keybinding;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.archery.IFirearm;
import minefantasy.mfr.init.MineFantasyKeybindings;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.network.OpenReloadGUIPacket;
import minefantasy.mfr.network.RemoveOffhandPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MineFantasyReforged.MOD_ID)
public class KeyBindingHandler {
	private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

	@SubscribeEvent
	public static void clientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END) {
			return;
		}

		if (MineFantasyKeybindings.RELOAD_MENU.isPressed()
				&& !MineFantasyKeybindings.RELOAD_MENU.isSetToDefaultValue()
				&& !MINECRAFT.gameSettings.keyBindUseItem.isKeyDown()
				&& !MINECRAFT.gameSettings.keyBindUseItem.isPressed()) {
			NetworkHandler.sendToServer(new OpenReloadGUIPacket(MINECRAFT.player));
		}

		if (MineFantasyKeybindings.REMOVE_OFFHAND.isPressed()) {
			NetworkHandler.sendToServer(new RemoveOffhandPacket(MINECRAFT.player));
		}
	}

	@SubscribeEvent
	public static void onItemRightClick(final PlayerInteractEvent.RightClickItem event) {

		if (event.getItemStack().getItem() instanceof IFirearm
				&& MineFantasyKeybindings.RELOAD_MENU.isKeyDown()
				&& MINECRAFT.gameSettings.keyBindUseItem.isKeyDown()
				&& MineFantasyKeybindings.RELOAD_MENU.isSetToDefaultValue()){

			NetworkHandler.sendToServer(new OpenReloadGUIPacket(MINECRAFT.player));
			event.setCanceled(true);
		}
	}
}

