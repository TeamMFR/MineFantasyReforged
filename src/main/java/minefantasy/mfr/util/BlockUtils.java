package minefantasy.mfr.util;

import minefantasy.mfr.tile.TileEntityTrough;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
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

	/**
	 * Transfer Old BlockState Property to New BlockState
	 * @param newState the new BlockState, the blockState to transfer the property value to.
	 * @param oldState the old BlockState, the blockState to transfer the property value from.
	 * @param property the property for the value to be transferred from.
	 * @return the new BlockState with the modified property value.
	 * @param <T> the property type.
	 */
	public static <T extends Comparable<T>> IBlockState transferOldToNewProperty(
			IBlockState newState,
			IBlockState oldState,
			IProperty<T> property) {
		return newState.withProperty(property, oldState.getValue(property));
	}

	public static TileEntityChest getOtherDoubleChest(TileEntity inv) {
		if (inv instanceof TileEntityChest) {
			TileEntityChest chest = (TileEntityChest) inv;

			TileEntityChest adjacent = null;

			if (chest.adjacentChestXNeg != null) {
				adjacent = chest.adjacentChestXNeg;
			}

			if (chest.adjacentChestXPos != null) {
				adjacent = chest.adjacentChestXPos;
			}

			if (chest.adjacentChestZNeg != null) {
				adjacent = chest.adjacentChestZNeg;
			}

			if (chest.adjacentChestZPos != null) {
				adjacent = chest.adjacentChestZPos;
			}

			return adjacent;
		}
		return null;
	}

	/**
	 * Gets the Grid slot in relation to mouse click
	 *
	 * @param clickX  the area clicked on X (called in blockActivated)
	 * @param clickY  the area clicked on Y (called in blockActivated)
	 * @param xBound  the x boundry of the block
	 * @param xBound2 the x max boundry of the block
	 * @param yBound  the y boundry of the block
	 * @param yBound2 the y max boundry of the block
	 * @param xSlots  the slot count x
	 * @param ySlots  the slot count y
	 * @param facing  the facing the block is facing
	 */
	public static int[] getCoordsFor(float clickX, float clickY, float xBound, float xBound2, float yBound, float yBound2, int xSlots, int ySlots, EnumFacing facing) {
		if (clickX < xBound || clickX > xBound2 || clickY < yBound || clickY > yBound2) {
			return null;
		}

		clickX -= xBound;
		clickY -= yBound;

		float xMax = xBound2 - xBound;
		float yMax = yBound2 - yBound;

		int xSlot = 0;
		int ySlot = 0;

		float xSpace = xMax / xSlots;
		float ySpace = yMax / ySlots;

		for (int xT = 0; xT < xSlots; xT++) {
			float MinSpace = xSpace * xT;
			float MaxSpace = xSpace * (xT + 1);
			if (clickX < MaxSpace && clickX > MinSpace) {
				xSlot = xT;
			}
		}

		for (int yT = 0; yT < ySlots; yT++) {
			float MinSpace = ySpace * yT;
			float MaxSpace = ySpace * (yT + 1);
			if (clickY < MaxSpace && clickY > MinSpace) {
				ySlot = yT;
			}
		}
		return translateCoords(xSlot, ySlot, xSlots, ySlots, facing);
	}

	/**
	 * Translates the coords on angles (NORTH is default)
	 *
	 * @param x      the slot x
	 * @param y      the slot y
	 * @param maxX   the slot count x
	 * @param maxY   the slot count y
	 * @param facing the facing facing
	 */
	public static int[] translateCoords(int x, int y, int maxX, int maxY, EnumFacing facing) {
		if (facing == EnumFacing.NORTH) {
			int newX = (maxX - x - 1);
			int newY = (maxY - y - 1);
			return new int[] {newX, newY};
		}

		if (facing == EnumFacing.WEST) {
			int newX = (y);
			int newY = (maxX - x - 1);
			return new int[] {newX, newY};
		}
		if (facing == EnumFacing.EAST) {
			int newY = (x);
			int newX = (maxY - y - 1);
			return new int[] {newX, newY};
		}

		return new int[] {x, y};
	}
}
