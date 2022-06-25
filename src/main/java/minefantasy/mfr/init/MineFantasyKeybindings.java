package minefantasy.mfr.init;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class MineFantasyKeybindings {
	private static final String CATEGORY = "key.category.mfr.general";

	public static KeyBinding RELOAD_MENU = new KeyBinding("key.mfr.reload_menu", KeyConflictContext.IN_GAME, Keyboard.KEY_LSHIFT, CATEGORY);
	public static KeyBinding REMOVE_OFFHAND = new KeyBinding("key.mfr.remove_offhand", KeyConflictContext.IN_GAME, Keyboard.KEY_G, CATEGORY);

	public static void registerKeyBindings() {
		ClientRegistry.registerKeyBinding(RELOAD_MENU);
		ClientRegistry.registerKeyBinding(REMOVE_OFFHAND);
	}

}
