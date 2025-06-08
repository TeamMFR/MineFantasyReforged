package minefantasy.mfr.block;

import com.google.common.collect.ImmutableMap;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.tile.TileEntityTrough;
import minefantasy.mfr.tile.TileEntityWoodDecor;
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
import javax.annotation.Nullable;
import java.util.Map;

public class BlockTrough extends BlockWoodDecor {
	public static final String FILL_LEVEL = "fill_level";

	private static final Map<EnumFacing, AxisAlignedBB> AABBS = ImmutableMap.of(
			EnumFacing.WEST, new AxisAlignedBB(14F / 16F, 0.0F, 1.0F, 2 / 16F, 7F / 16F, 0F),
			EnumFacing.EAST, new AxisAlignedBB(14F / 16F, 0.0F, 1.0F, 2 / 16F, 7F / 16F, 0F),
			EnumFacing.SOUTH, new AxisAlignedBB(0, 0F, 2 / 16F, 1.0F, 7F / 16F, 14F / 16F),
			EnumFacing.NORTH, new AxisAlignedBB(0, 0F, 2 / 16F, 1.0F, 7F / 16F, 14F / 16F));

	public BlockTrough(String name) {
		super(name);

		setRegistryName(name);
		setTranslationKey(name);
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
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return AABBS.get(state.getValue(FACING));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABBS.get(state.getValue(FACING));
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack stack) {

		TileEntityTrough tile = (TileEntityTrough) getTile(world, pos);
		if (tile != null) {
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey(FILL_LEVEL)) {
				tile.setFill(stack.getTagCompound().getInteger(FILL_LEVEL));
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
			item.getTagCompound().setInteger(FILL_LEVEL, tile.getFill());
		}
		return item;
	}
}
