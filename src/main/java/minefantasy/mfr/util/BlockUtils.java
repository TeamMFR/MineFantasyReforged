package minefantasy.mfr.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUtils {

	public static void notifyBlockUpdate(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
	}

	public static void notifyBlockUpdate(TileEntity tile) {
		notifyBlockUpdate(tile.getWorld(), tile.getPos());
	}
}
