package minefantasy.mfr.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientItemsMFR {

	public static boolean showSpecials(List<String> list) {
		if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) {
			return true;
		} else {
			String keyname = Keyboard.getKeyName(Keyboard.KEY_LSHIFT);
			list.add(I18n.format("info.tooltip.moreinfo", keyname.toUpperCase()));
			return false;
		}
	}

}
