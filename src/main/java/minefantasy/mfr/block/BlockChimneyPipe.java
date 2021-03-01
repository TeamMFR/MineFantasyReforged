package minefantasy.mfr.block;

import minefantasy.mfr.tile.TileEntityChimney;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockChimneyPipe extends BlockChimney {
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");

	protected static final AxisAlignedBB[] AABB_BY_INDEX = new AxisAlignedBB[] {
			new AxisAlignedBB(0.128D, 0.128D, 0.128D, 0.873D, 0.873D, 0.873D),
			new AxisAlignedBB(0.128D, 0.128D, 0.128D, 0.873D, 0.873D, 1.0D),
			new AxisAlignedBB(0.0D, 0.128D, 0.128D, 0.873D, 0.873D, 0.873D),
			new AxisAlignedBB(0.0D, 0.128D, 0.128D, 0.873D, 0.873D, 1.0D),
			new AxisAlignedBB(0.128D, 0.128D, 0.0D, 0.873D, 0.873D, 0.873D),
			new AxisAlignedBB(0.128D, 0.128D, 0.0D, 0.873D, 0.873D, 1.0D),
			new AxisAlignedBB(0.0D, 0.128D, 0.0D, 0.873D, 0.873D, 0.873D),
			new AxisAlignedBB(0.0D, 0.128D, 0.0D, 0.873D, 0.873D, 1.0D),
			new AxisAlignedBB(0.128D, 0.128D, 0.128D, 1.0D, 0.873D, 0.873D),
			new AxisAlignedBB(0.128D, 0.128D, 0.128D, 1.0D, 0.873D, 1.0D),
			new AxisAlignedBB(0.0D, 0.128D, 0.128D, 1.0D, 0.873D, 0.873D),
			new AxisAlignedBB(0.0D, 0.128D, 0.128D, 1.0D, 0.873D, 1.0D),
			new AxisAlignedBB(0.128D, 0.128D, 0.0D, 1.0D, 0.873D, 0.873D),
			new AxisAlignedBB(0.128D, 0.128D, 0.0D, 1.0D, 0.873D, 1.0D),
			new AxisAlignedBB(0.0D, 0.128D, 0.0D, 1.0D, 0.873D, 0.873D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
	/**
	 * Weather it can absorb smoke indirectly (not directly above a source)
	 */
	public boolean isIndirect;

	public BlockChimneyPipe(boolean indirect, int size) {
		super("pipe", false, indirect, size);
		this.isIndirect = indirect;
		this.size = size;
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH, UP, DOWN);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityChimney();
	}

	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(NORTH, canPipeConnectTo(world, pos, EnumFacing.NORTH))
				.withProperty(SOUTH, canPipeConnectTo(world, pos, EnumFacing.SOUTH))
				.withProperty(WEST, canPipeConnectTo(world, pos, EnumFacing.WEST))
				.withProperty(EAST, canPipeConnectTo(world, pos, EnumFacing.EAST))
				.withProperty(UP, canPipeConnectTo(world, pos, EnumFacing.UP))
				.withProperty(DOWN, canPipeConnectTo(world, pos, EnumFacing.DOWN));
	}

	@Override
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		BlockPos offset = pos.offset(facing);
		return attachesTo(world, world.getBlockState(offset), offset, facing.getOpposite());
	}

	public boolean canPipeConnectTo(IBlockAccess world, BlockPos pos, EnumFacing dir) {
		BlockPos other = pos.offset(dir);
		IBlockState state = world.getBlockState(other);
		return state.getBlock().canBeConnectedTo(world, other, dir.getOpposite()) || attachesTo(world, state, other, dir.getOpposite());
	}

	public final boolean attachesTo(IBlockAccess blockAccess, IBlockState state, BlockPos pos, EnumFacing facing) {
		BlockFaceShape blockfaceshape = state.getBlockFaceShape(blockAccess, pos, facing);
		return blockfaceshape == BlockFaceShape.SOLID || blockfaceshape == BlockFaceShape.MIDDLE_POLE_THIN;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 */
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		switch (rot) {
			case CLOCKWISE_180:
				return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
			case COUNTERCLOCKWISE_90:
				return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
			case CLOCKWISE_90:
				return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
			default:
				return state;
		}
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 */
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		switch (mirrorIn) {
			case LEFT_RIGHT:
				return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
			case FRONT_BACK:
				return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
			default:
				return super.withMirror(state, mirrorIn);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = this.getActualState(state, source, pos);
		return AABB_BY_INDEX[getBoundingBoxIndex(state)];
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable
			Entity entityIn, boolean isActualState) {
		if (!isActualState) {
			state = this.getActualState(state, worldIn, pos);
		}

		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[0]);

		if (state.getValue(NORTH)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[getBoundingBoxIndex(EnumFacing.NORTH)]);
		}

		if (state.getValue(SOUTH)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[getBoundingBoxIndex(EnumFacing.SOUTH)]);
		}

		if (state.getValue(EAST)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[getBoundingBoxIndex(EnumFacing.EAST)]);
		}

		if (state.getValue(WEST)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[getBoundingBoxIndex(EnumFacing.WEST)]);
		}
	}

	private static int getBoundingBoxIndex(EnumFacing enumFacing) {
		return 1 << enumFacing.getHorizontalIndex();
	}

	private static int getBoundingBoxIndex(IBlockState state) {
		int i = 0;

		if (state.getValue(NORTH)) {
			i |= getBoundingBoxIndex(EnumFacing.NORTH);
		}

		if (state.getValue(EAST)) {
			i |= getBoundingBoxIndex(EnumFacing.EAST);
		}

		if (state.getValue(SOUTH)) {
			i |= getBoundingBoxIndex(EnumFacing.SOUTH);
		}

		if (state.getValue(WEST)) {
			i |= getBoundingBoxIndex(EnumFacing.WEST);
		}

		return i;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

}
