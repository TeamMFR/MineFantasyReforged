package minefantasy.mfr.block;

import minefantasy.mfr.api.heating.TongsHelper;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyMaterials;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.item.ItemHammer;
import minefantasy.mfr.item.ItemTongs;
import minefantasy.mfr.material.BaseMaterial;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.network.SparkParticlePacket;
import minefantasy.mfr.tile.TileEntityAnvil;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class BlockAnvilMF extends BlockTileEntity<TileEntityAnvil> {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool BURNING = PropertyBool.create("burning");
	public BaseMaterial material;
	private final int tier;
	private static final AxisAlignedBB ANVIL_STONE_AABB_NORTH_SOUTH = new AxisAlignedBB(0.1875D, 0.0D, 0.3125D, 0.8125D, 0.8125D, 0.6875D);
	private static final AxisAlignedBB ANVIL_STONE_AABB_EAST_WEST = new AxisAlignedBB(0.3125D, 0.0D, 0.1875D, 0.6875D, 0.8125D, 0.8125D);
	private static final AxisAlignedBB ANVIL_AABB_NORTH_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
	private static final AxisAlignedBB ANVIL_AABB_EAST_WEST = new AxisAlignedBB(0.3125D, 0.0D, 0.0D, 0.6875D, 0.8125D, 1.0D);


	public BlockAnvilMF(BaseMaterial material) {
		super(Material.ANVIL);
		this.material = material;
		String name = "anvil_" + material.name;
		this.tier = material.tier;

		setRegistryName(name);
		setTranslationKey(name);
		this.setSoundType(SoundType.METAL);
		this.setHardness(material.hardness + 1 / 2F);
		this.setResistance(material.hardness + 1);
		this.setLightOpacity(0);
		this.setCreativeTab(MineFantasyTabs.tabUtil);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, BURNING);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		TileEntityAnvil anvil = new TileEntityAnvil();
		anvil.setTier(tier);
		anvil.setTextureName(this.getRegistryName().getPath());
		return anvil;
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
				.withProperty(BURNING, (meta & 4) != 0)
				.withProperty(FACING, EnumFacing.byHorizontalIndex(meta & 3));
	}

	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(FACING).getHorizontalIndex();

		if (state.getValue(BURNING)) {
			i |= 4;
		}

		return i;
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState()
				.withProperty(FACING, placer.getHorizontalFacing())
				.withProperty(BURNING, false);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (state.getValue(FACING) == EnumFacing.NORTH || state.getValue(FACING) == EnumFacing.SOUTH){
			return material == MineFantasyMaterials.STONE ? ANVIL_STONE_AABB_NORTH_SOUTH : ANVIL_AABB_NORTH_SOUTH;
		}
		else{
			return material == MineFantasyMaterials.STONE ? ANVIL_STONE_AABB_EAST_WEST : ANVIL_AABB_EAST_WEST;
		}

	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(BURNING) ? 5 : 0;
	}

	/**
	 * Get the geometry of the queried face at the given position and state. This is used to decide whether things like
	 * buttons are allowed to be placed on the face, or how glass panes connect to the face, among other things.
	 * <p>
	 * Common values are {@code SOLID}, which is the default, and {@code UNDEFINED}, which represents something that
	 * does not fit the other descriptions and will generally cause other things not to connect to the face.
	 *
	 * @return an approximation of the form of the given face
	 */
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}


	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack stack) {
		TileEntityAnvil tile = (TileEntityAnvil) world.getTileEntity(pos);
		if (tile != null){
			tile.reassignHitValues();
		}
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack held = player.getHeldItem(hand);
		TileEntityAnvil tile = (TileEntityAnvil) getTile(world, pos);
		if (tile != null) {
			if (facing == EnumFacing.UP && !held.isEmpty() && held.getItem() instanceof ItemTongs && onUsedTongs(player, held, tile)) {
				return true;
			}
			if (facing != EnumFacing.UP || !tile.tryCraft(player, true) && !world.isRemote) {
				tile.openGUI(world, player);
			}
			if (held.getItem() instanceof ItemHammer) {
				generateSparks(world, pos.getX() + hitX, pos.getY(), pos.getZ() + hitZ, player.dimension);
				tile.setHit(true);
			}
		}
		if (!world.isRemote) {
			ResearchLogic.syncData(player);
		}
		return true;
	}

	private boolean onUsedTongs(EntityPlayer user, ItemStack held, TileEntityAnvil tile) {
		ItemStack result = tile.getInventory().getStackInSlot(tile.getInventory().getSlots() - 1);
		ItemStack grabbed = TongsHelper.getHeldItem(held);

		// GRAB
		if (grabbed.isEmpty()) {
			if (!result.isEmpty() && result.getItem() == MineFantasyItems.HOT_ITEM) {
				if (TongsHelper.trySetHeldItem(held, result)) {
					tile.getInventory().setStackInSlot(tile.getInventory().getSlots() - 1, ItemStack.EMPTY);
					return true;
				}
			}
		} else {
			for (int slot_number = 0; slot_number < (tile.getInventory().getSlots() - 1); slot_number++) {
				ItemStack slot = tile.getInventory().getStackInSlot(slot_number);
				if (slot.isEmpty()) {
					tile.getInventory().setStackInSlot(slot_number, grabbed);
					TongsHelper.clearHeldItem(held, user);
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
		TileEntityAnvil tile = (TileEntityAnvil) getTile(world, pos);
		if (tile != null) {
			if (player.isSneaking()) {
				tile.upset(player);
			} else {
				tile.tryCraft(player, false);
				if (player.getHeldItemMainhand().getItem() instanceof ItemHammer) {
					generateSparks(world, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, player.dimension);
					tile.setHit(true);
				}
			}
		}
	}

	public int getTier() {
		return tier;
	}

	private void generateSparks(World world, double x, double y, double z, int dimensionId) {
		if (!world.isRemote) {
			TargetPoint targetPoint = new TargetPoint(dimensionId, x, y, z, 0);
			SparkParticlePacket packet = new SparkParticlePacket(x, y, z);
			NetworkHandler.sendToAllTrackingBlock(targetPoint, packet);
		}
	}
}