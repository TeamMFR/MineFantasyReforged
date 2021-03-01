package minefantasy.mfr.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientItemsMFR {

	public static boolean showSpecials(ItemStack item, World world, List<String> list, ITooltipFlag fullInfo) {
		if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) {
			return true;
		} else {
			String keyname = Keyboard.getKeyName(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode());
			list.add(I18n.format("info.tooltip.moreinfo", keyname.toUpperCase()));
			return false;
		}
	}

}
