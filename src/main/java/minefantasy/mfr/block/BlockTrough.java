package minefantasy.mfr.block;

import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.tile.TileEntityTrough;
import minefantasy.mfr.tile.TileEntityWoodDecor;
import minefantasy.mfr.util.WorldUtils;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockTrough extends BlockWoodDecor {
	private static final PropertyInteger FILL_COUNT = PropertyInteger.create("fill_count", 0, 6);
	public static final String FILL_LEVEL = "fill_level";

	private static AxisAlignedBB AABB = new AxisAlignedBB(0, 0F, 2 / 16F, 1.0F, (7F / 16F), 14 / 16F);

	public BlockTrough(String name) {
		super(name);

		setRegistryName(name);
		setUnlocalizedName(name);
		this.setHardness(1F);
		this.setResistance(0.5F);
		this.setCreativeTab(MineFantasyTabs.tabUtil);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityTrough();
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FILL_COUNT);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(FILL_COUNT, WorldUtils.getTile(world, pos, TileEntityTrough.class).map(TileEntityTrough::getFillCount).orElse(0));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FILL_COUNT);
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FILL_COUNT, Integer.valueOf(meta));
	}

	@Nonnull
	@Override
	public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
		return getDefaultState().withProperty(FILL_COUNT, 0);
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack stack) {

		TileEntityTrough tile = (TileEntityTrough) getTile(world, pos);
		if (tile != null) {
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey(FILL_LEVEL)) {
				tile.fill = stack.getTagCompound().getInteger(FILL_LEVEL);
			}
			tile.setColorInt(stack);
		}
		super.onBlockPlacedBy(world, pos, state, user, stack);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = user.getHeldItemMainhand();
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityTrough) {
			if (((TileEntityTrough) tile).interact(user, stack)) {
				world.playSound(user, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.AMBIENT, 0.125F + user.getRNG().nextFloat() / 4F, 0.5F + user.getRNG().nextFloat());
				return true;
			}
		}
		return false;
	}

	@Override
	protected ItemStack modifyDrop(TileEntityWoodDecor tile, ItemStack item) {
		return modifyFill((TileEntityTrough) tile, super.modifyDrop(tile, item));
	}

	private ItemStack modifyFill(TileEntityTrough tile, ItemStack item) {
		if (tile != null && !item.isEmpty()) {
			item.getTagCompound().setInteger(FILL_LEVEL, tile.fill);
		}
		return item;
	}
}
