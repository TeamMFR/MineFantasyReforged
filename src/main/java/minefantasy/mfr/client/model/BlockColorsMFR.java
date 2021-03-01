package minefantasy.mfr.client.model;

import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.tile.TileEntityRack;
import minefantasy.mfr.tile.TileEntityTrough;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ColorizerFoliage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockColorsMFR {
	private BlockColorsMFR() {} // no instances!

	public static void init() {
		BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();

		final IBlockColor leavesColourHandler = (state, blockAccess, pos, tintIndex) -> ColorizerFoliage.getFoliageColorBasic();

		IBlockColor blockColorForToolRack = (state, blockAccess, pos, tintIndex) -> {
			if (tintIndex == 0 && blockAccess != null && pos != null) {
				TileEntity tile = blockAccess.getTileEntity(pos);
				if (tile instanceof TileEntityRack) {
					return ((TileEntityRack) tile).getColorInt();
				}
			}
			return 0xFFFFFF;
		};

		IBlockColor blockColorForTrough = (state, blockAccess, pos, tintIndex) -> {
			if (tintIndex == 0 && blockAccess != null && pos != null) {
				TileEntity tile = blockAccess.getTileEntity(pos);
				if (tile instanceof TileEntityTrough) {
					return ((TileEntityTrough) tile).getColorInt();
				}
			}
			return 0xFFFFFF;
		};

		//Coloration of Leaves and Bushes
		blockColors.registerBlockColorHandler(leavesColourHandler, MineFantasyBlocks.LEAVES_YEW);
		blockColors.registerBlockColorHandler(leavesColourHandler, MineFantasyBlocks.LEAVES_IRONBARK);
		blockColors.registerBlockColorHandler(leavesColourHandler, MineFantasyBlocks.LEAVES_EBONY);
		blockColors.registerBlockColorHandler(leavesColourHandler, MineFantasyBlocks.BERRY_BUSH);

		//Coloration of static model Wood Decoration Blocks
		blockColors.registerBlockColorHandler(blockColorForToolRack, MineFantasyBlocks.TOOL_RACK_WOOD);
		blockColors.registerBlockColorHandler(blockColorForTrough, MineFantasyBlocks.TROUGH_WOOD);
	}
}

