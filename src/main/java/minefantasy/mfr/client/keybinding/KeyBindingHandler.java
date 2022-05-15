package minefantasy.mfr.client.keybinding;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.init.MineFantasyKeybindings;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.network.OpenBowGUIPacket;
import minefantasy.mfr.network.RemoveOffhandPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MineFantasyReforged.MOD_ID)
public class KeyBindingHandler {
	private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

	@SubscribeEvent
	public static void clientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END)
			return;

		if (MineFantasyKeybindings.BOW_MENU.isKeyDown() && !MineFantasyKeybindings.BOW_MENU.isSetToDefaultValue()) {
			NetworkHandler.sendToServer(new OpenBowGUIPacket(MINECRAFT.player));
		}

		if (MineFantasyKeybindings.REMOVE_OFFHAND.isPressed()) {
			NetworkHandler.sendToServer(new RemoveOffhandPacket(MINECRAFT.player));
		}
	}
}

