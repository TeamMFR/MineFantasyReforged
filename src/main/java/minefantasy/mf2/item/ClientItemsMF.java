package minefantasy.mf2.item;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientItemsMF
{

	public static boolean showSpecials(ItemStack item, EntityPlayer user, List list, boolean fullInfo)
	{
		if(GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak))
    	{
			return true;
    	}
    	else
    	{
    		String keyname = Keyboard.getKeyName(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode());
    		list.add("Hold [" + EnumChatFormatting.AQUA + keyname.toUpperCase() + EnumChatFormatting.GRAY + "] for more information.");
    		return false;
    	}
	}

}
