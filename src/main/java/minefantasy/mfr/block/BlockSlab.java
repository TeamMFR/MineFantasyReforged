package minefantasy.mfr.block;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.proxy.IClientRegister;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockSlab extends Block implements IClientRegister {
	public static final PropertyEnum<SlabType> TYPE = PropertyEnum.create("type", SlabType.class);;
	protected static final AxisAlignedBB BOTTOM_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	protected static final AxisAlignedBB TOP_AABB = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

	public BlockSlab(String name, Material material, SoundType soundType) {
		super(material);
		this.setRegistryName(name);
		this.setTranslationKey(name);
		this.setSoundType(soundType);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

		MineFantasyReforged.PROXY.addClientRegister(this);
		setDefaultState(this.getDefaultState().withProperty(TYPE, SlabType.BOTTOM));
	}

	/**
	 * Registers our blockState properties with the block (Is how I interpret it).
	 * @return the BlockStateContainer with new properties
	 */
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	/**
	 * **Obligatory screw meta**.
	 * Retrieves the appropriate blockState with correct properties from the given metadata value.
	 * In this case, meta is 0, 1, or 2, which corresponds with the ordinal value of the SlabType Enum {@link SlabType}.
	 * @return the blockState
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		SlabType slabType = SlabType.fromMeta(meta);
		return this.getDefaultState().withProperty(TYPE, slabType);
	}

	/**
	 * **Obligatory screw meta**.
	 * Gets the meta value of the block based on BlockState properties.
	 * In this case, the meta int is the ordinal value of the SlabType Enum {@link SlabType} for the given BlockState.
	 * @param state the blockState of the block
	 * @return the meta int value of the block
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).ordinal();
	}

	/**
	 * Determines the BlockState the slab should be placed with based on how the player placed it, ie, as a bottom slab or a top slab.
	 * Also handles placing a slab on slab, which makes the block a full block.
	 */
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		IBlockState state = world.getBlockState(pos);
		if (getBlockFromItem(placer.getHeldItemMainhand().getItem()) != state.getBlock() && state.getBlock() != Blocks.AIR) {
			return state;
		}
		if (state.getBlock() == this){
			return state.withProperty(TYPE, SlabType.DOUBLE);
		}
		else {
			IBlockState bottomSlabState = this.getDefaultState().withProperty(TYPE, SlabType.BOTTOM);
			IBlockState topSlabState = this.getDefaultState().withProperty(TYPE, SlabType.TOP);
			return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double)hitY <= 0.5D) ? bottomSlabState : topSlabState;
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos.down());
		Block block = state.getBlock();

		return super.canPlaceBlockAt(worldIn, pos) || block == this && !state.getValue(TYPE).equals(SlabType.DOUBLE);
	}

//	@Override
//	public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
//		IBlockState state = world.getBlockState(pos);
//		return !state.getValue(TYPE).equals(SlabType.DOUBLE);
//	}

	/**
	 * Handles how many slab ItemBlocks should be dropped based on BlockState, 2 for a DOUBLE Slab, 1 for either TOP or BOTTOM
	 */
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		SlabType slabType = state.getValue(TYPE);
		return slabType.equals(SlabType.DOUBLE) ? 2 : 1;
	}

		/**
		 * Handles how slabs have different AABBs based on their relative position:
		 * <p>
		 * BOTTOM, where the slab is the bottom half of a full block
		 * <p>
		 * TOP, where the slab is the upper half of a full block
		 * <p>
		 * DOUBLE, where the slab(s?) is a full block
		 * @return the AABB dependent on block state
		 */
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		SlabType slabType = state.getValue(TYPE);
		switch(slabType) {
			case DOUBLE:
				return Block.FULL_BLOCK_AABB;
			case TOP:
				return TOP_AABB;
			default:
				return BOTTOM_AABB;
		}
	}

	/**
	 * Determines if the slab is top solid, aka DOUBLE or TOP
	 * @param state slab state
	 * @return true if the top is solid, false if its not (type == BOTTOM)
	 */
	@Override
	public boolean isTopSolid(IBlockState state) {
		SlabType slabType = state.getValue(TYPE);
		return slabType.equals(SlabType.DOUBLE) || state.getValue(TYPE).equals(SlabType.TOP);
	}

	/**
	 * Handles determining if the slab is a solid block, aka, stopping movement without a jump on it based on BlockState
	 * Solid for DOUBLE AND TOP, Undefined for BOTTOM
	 */
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
		SlabType slabType = state.getValue(TYPE);
		if (slabType.equals(SlabType.DOUBLE)) {
			return BlockFaceShape.SOLID;
		}
		else if (face == EnumFacing.UP && slabType == SlabType.TOP) {
			return BlockFaceShape.SOLID;
		}
		else {
			return face == EnumFacing.DOWN && slabType.equals(SlabType.BOTTOM) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
		}
	}

	/**
	 * Determines block side rendering based on BlockState
	 */
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		if (net.minecraftforge.common.ForgeModContainer.disableStairSlabCulling) {
			return super.doesSideBlockRendering(state, world, pos, face);
		}

		if (state.isOpaqueCube() ) {
			return true;
		}

		SlabType slabType = state.getValue(TYPE);
		return (slabType.equals(SlabType.TOP) && face == EnumFacing.UP) || (slabType.equals(SlabType.BOTTOM) && face == EnumFacing.DOWN);
	}

	/**
	 * Is the slab a full cube or not, based on blockState. DOUBLE is a full cube, other are not
	 */
	@Override
	public boolean isFullCube(IBlockState state) {
		SlabType slabType = state.getValue(TYPE);
		return slabType.equals(SlabType.DOUBLE);
	}

	/**
	 * Is the slab an opaque cube or not, based on blockState. DOUBLE is an opaque cube, other are not
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		SlabType slabType = state.getValue(TYPE);
		return slabType.equals(SlabType.DOUBLE);
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess access, BlockPos pos, EnumFacing side) {
		SlabType slabType = state.getValue(TYPE);
		if (slabType.equals(SlabType.DOUBLE)) {
			return super.shouldSideBeRendered(state, access, pos, side);
		}
		else if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(state, access, pos, side)) {
			return false;
		}

		return super.shouldSideBeRendered(state, access, pos, side);
	}

	/**
	 * Embedded Enum for slab variants, aka if it's a bottom half slab, top half slab, or a double slab (full block)
	 */
	public enum SlabType implements IStringSerializable {
		BOTTOM("bottom"),
		TOP("top"),
		DOUBLE("double");

		private final String name;

		SlabType(String p_i49332_3_) {
			this.name = p_i49332_3_;
		}

		public static SlabType fromMeta(int meta) {
			return values()[meta];
		}

		@Override
		public String toString() {
			return this.name;
		}

		@Override
		public String getName() {
			return this.name;
		}
	}

	/**
	 * pretty sure this handles rendering the itemblock based on variants?
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "normal"));
	}
}
