package minefantasy.mfr.client.model;


import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.init.CustomToolListMFR;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class MFRColorManager{
	private static final Minecraft minecraft = Minecraft.getMinecraft();

	public static void registerColorHandlers(){
		final ItemColors itemColors = minecraft.getItemColors();

		registerItemColourHandlers(itemColors);
	}
	private static void registerItemColourHandlers( final ItemColors itemColors){
		final IItemColor itemColorHandler = (itemColors::colorMultiplier);
		itemColors.registerItemColorHandler(itemColorHandler, CustomToolListMFR.standard_pick);
	}
}
