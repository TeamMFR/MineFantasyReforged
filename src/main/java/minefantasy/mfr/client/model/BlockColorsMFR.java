package minefantasy.mfr.client.model;

import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.item.tool.ItemAxe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockColorsMFR {
	private BlockColorsMFR() {} // no instances!

	public static void init() {
		BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();

		final IBlockColor leavesColourHandler = (state, blockAccess, pos, tintIndex) -> ColorizerFoliage.getFoliageColorBasic();

		blockColors.registerBlockColorHandler(leavesColourHandler, BlockListMFR.LEAVES_YEW);
		blockColors.registerBlockColorHandler(leavesColourHandler, BlockListMFR.LEAVES_IRONBARK);
		blockColors.registerBlockColorHandler(leavesColourHandler, BlockListMFR.LEAVES_EBONY);
	}
}

