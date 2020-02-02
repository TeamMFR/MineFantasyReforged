package minefantasy.mfr.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.input.Keyboard;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientItemsMF {

    public static boolean showSpecials(ItemStack item, World world, List<String> list, ITooltipFlag fullInfo) {
        if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) {
            return true;
        } else {
            String keyname = Keyboard.getKeyName(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode());
            list.add(I18n.translateToLocalFormatted("info.tooltip.moreinfo", keyname.toUpperCase()));
            return false;
        }
    }

}
