package minefantasy.mf2.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientItemsMF {

    public static boolean showSpecials(ItemStack item, EntityPlayer user, List<String> list, boolean fullInfo) {
        if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) {
            return true;
        } else {
            String keyname = Keyboard.getKeyName(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode());
            list.add(StatCollector.translateToLocalFormatted("info.tooltip.moreinfo", keyname.toUpperCase()));
            return false;
        }
    }

}
