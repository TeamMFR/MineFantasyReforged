package minefantasy.mfr.block;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.util.PowerArmour;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockFrame extends BasicBlockMF {
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");

	protected static AxisAlignedBB[] AABB_BY_INDEX = new AxisAlignedBB[] {
			new AxisAlignedBB(4 / 16D, 0 / 16D, 4 / 16D, 12 / 16D, 12 / 16D, 12 / 16D), //DOWN  0
			new AxisAlignedBB(4 / 16D, 4 / 16D, 4 / 16D, 12 / 16D, 16 / 16D, 12 / 16D), //UP    1
			new AxisAlignedBB(4 / 16D, 4 / 16D, 0 / 16D, 12 / 16D, 12 / 16D, 12 / 16D), //NORTH 2
			new AxisAlignedBB(4 / 16D, 4 / 16D, 4 / 16D, 12 / 16D, 12 / 16D, 16 / 16D), //SOUTH 3
			new AxisAlignedBB(0 / 16D, 4 / 16D, 4 / 16D, 12 / 16D, 12 / 16D, 12 / 16D), //WEST  4
			new AxisAlignedBB(4 / 16D, 4 / 16D, 4 / 16D, 16 / 16D, 12 / 16D, 12 / 16D), //EAST  5
			new AxisAlignedBB(4 / 16D, 4 / 16D, 0 / 16D, 12 / 16D, 12 / 16D, 16 / 16D), //N,E,S 6
			new AxisAlignedBB(4 / 16D, 0 / 16D, 4 / 16D, 12 / 16D, 16 / 16D, 12 / 16D), //D,S   7
			new AxisAlignedBB(4 / 16D, 4 / 16D, 4 / 16D, 12 / 16D, 12 / 16D, 12 / 16D)};//NONE  8

	protected static AxisAlignedBB AABB_FOR_COGWORK = new AxisAlignedBB(15 / 16D, 0 / 16D, 15 / 16D, 1 / 16D, 16 / 16D, 1 / 16D);

	public BlockFrame(String name) {
		this(name, null);
	}

	public BlockFrame(String name, Object drop) {
		super(name, Material.IRON, drop);
		this.setCreativeTab(MineFantasyTabs.tabGadget);
		this.setHardness(1.0F);
		this.setResistance(3.0F);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH, UP, DOWN);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(NORTH, canFrameConnectTo(world, pos, EnumFacing.NORTH))
				.withProperty(SOUTH, canFrameConnectTo(world, pos, EnumFacing.SOUTH))
				.withProperty(WEST, canFrameConnectTo(world, pos, EnumFacing.WEST))
				.withProperty(EAST, canFrameConnectTo(world, pos, EnumFacing.EAST))
				.withProperty(UP, canFrameConnectTo(world, pos, EnumFacing.UP))
				.withProperty(DOWN, canFrameConnectTo(world, pos, EnumFacing.DOWN));
	}

	@Override
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		BlockPos offset = pos.offset(facing);
		return attachesTo(world, world.getBlockState(offset), pos, facing);
	}

	public boolean canFrameConnectTo(IBlockAccess world, BlockPos pos, EnumFacing dir) {
		BlockPos other = pos.offset(dir);
		IBlockState state = world.getBlockState(other);
		return state.getBlock().canBeConnectedTo(world, other, dir.getOpposite()) || attachesTo(world, state, pos, dir);
	}

	public final boolean attachesTo(IBlockAccess iBlockAccess, IBlockState state, BlockPos pos, EnumFacing facing) {
		BlockFaceShape blockfaceshape = state.getBlockFaceShape(iBlockAccess, pos, facing);
		if (facing == EnumFacing.DOWN && blockfaceshape == BlockFaceShape.SOLID && !(state.getBlock() instanceof BlockCogwork)){
			return true;
		}
		else{
			return state.getBlock() instanceof BlockFrame || state.getBlock() instanceof BlockFrameHolder;
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (ToolHelper.getToolTypeFromStack(player.getHeldItemMainhand()) == Tool.SPANNER) {
			return tryBuild(player, world, pos);
		}
		return false;
	}

	private boolean tryBuild(EntityPlayer player, World world, BlockPos pos) {
		if (!world.isRemote && PowerArmour.isBasicStationFrame(world, pos) && (player.capabilities.isCreativeMode || player.inventory.hasItemStack(new ItemStack(MineFantasyItems.COGWORK_PULLEY)))) {
			if (!player.capabilities.isCreativeMode) {
				player.inventory.removeStackFromSlot(player.inventory.findSlotMatchingUnusedItem(new ItemStack(MineFantasyItems.COGWORK_PULLEY)));
			}
			world.setBlockState(pos, MineFantasyBlocks.COGWORK_HOLDER.getDefaultState(), 2);
			return true;
		}
		else if (!world.isRemote && !player.inventory.hasItemStack(new ItemStack(MineFantasyItems.COGWORK_PULLEY))) {
			player.sendMessage(new TextComponentTranslation("vehicle.noPulley"));
		}
		else if (!world.isRemote && !PowerArmour.isBasicStationFrame(world, pos)) {
			player.sendMessage(new TextComponentTranslation("vehicle.noFrame"));
		}
		return false;
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
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		if (!isActualState) {
			state = this.getActualState(state, worldIn, pos);
		}

		if (entityIn instanceof EntityCogwork) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(6 / 16D, 0 / 16D, 6 / 16D, 10 / 16D, 16 / 16D, 10 / 16D));
			return;
		}

		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[8]);

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

		if (state.getValue(UP)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[getBoundingBoxIndex(EnumFacing.UP)]);
		}

		if (state.getValue(DOWN)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[getBoundingBoxIndex(EnumFacing.DOWN)]);
		}
	}

	private static int getBoundingBoxIndex(EnumFacing enumFacing) {
		return enumFacing.getIndex();
	}

	private static int getBoundingBoxIndex(IBlockState state) {
		int i = AABB_BY_INDEX.length - 1;

		if (state.getValue(NORTH)) {
			i = getBoundingBoxIndex(EnumFacing.NORTH);
			if (i > AABB_BY_INDEX.length){
				MineFantasyReforged.LOG.error(state.getValue(NORTH));
			}
		}

		if (state.getValue(EAST)) {
			i = getBoundingBoxIndex(EnumFacing.EAST);
			if (i > AABB_BY_INDEX.length){
				MineFantasyReforged.LOG.error(state.getValue(EAST));
			}
		}

		if (state.getValue(SOUTH)) {
			i = getBoundingBoxIndex(EnumFacing.SOUTH);
			if (i > AABB_BY_INDEX.length){
				MineFantasyReforged.LOG.error(state.getValue(SOUTH));
			}
		}

		if (state.getValue(WEST)) {
			i = getBoundingBoxIndex(EnumFacing.WEST);
			if (i > AABB_BY_INDEX.length){
				MineFantasyReforged.LOG.error(state.getValue(SOUTH));
			}
		}

		if (state.getValue(UP)) {
			i = getBoundingBoxIndex(EnumFacing.UP);
			if (i > AABB_BY_INDEX.length){
				MineFantasyReforged.LOG.error(state.getValue(UP));
			}
		}

		if (state.getValue(DOWN)) {
			i = getBoundingBoxIndex(EnumFacing.DOWN);
			if (i > AABB_BY_INDEX.length){
				MineFantasyReforged.LOG.error(state.getValue(DOWN));
			}
		}

		if (state.getValue(NORTH) && state.getValue(EAST) && state.getValue(SOUTH)){
			i = 6;
		}

		if (state.getValue(DOWN) && state.getValue(UP)){
			i = 7;
		}

		return i;
	}
}
