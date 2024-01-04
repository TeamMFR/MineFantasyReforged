package minefantasy.mfr.util;

import minefantasy.mfr.tile.TileEntityTrough;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
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

	public static boolean isBlockWithinHorizontalRange(BlockPos blockPos, BlockPos pointPos, int range) {
		int dx = blockPos.getX() - pointPos.getX();
		int dz = blockPos.getZ() - pointPos.getZ();
		int distance = (int) Math.sqrt(dx * dx + dz * dz);
		return distance <= range;
	}

	public static boolean isWaterSource(World world, BlockPos pos) {
		if (world.getBlockState(pos).getMaterial() == Material.WATER) {
			return true;
		}
		if (world.getBlockState(pos).getBlock() == Blocks.CAULDRON) {
			return true;
		}
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityTrough) {
			TileEntityTrough trough = (TileEntityTrough) tile;
			if (!trough.isEmpty()) {
				trough.removeFluid(1);
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
}
