package minefantasy.mfr.block;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.tile.TileEntityTanningRack;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockTanningRack extends BlockTileEntity<TileEntityTanningRack> implements IClientRegister {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	private static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0, 0, 5.5f / 16f, 1f, 19 / 16f, 10.5 / 16f);
	private static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(5.5f / 16f, 0, 0, 10.5 / 16f, 19 / 16f, 1f);

	public int tier;
	public String tex;

	public BlockTanningRack(int tier, String tex) {
		super(Material.WOOD);

		this.tier = tier;
		this.tex = tex;
		String name = "tanner" + tex;

		setRegistryName(name);
		setUnlocalizedName(name);
		this.setHardness(1F + 0.5F * tier);
		this.setResistance(1F);
		this.setLightOpacity(0);
		this.setCreativeTab(MineFantasyTabs.tabUtil);
		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityTanningRack();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityTanningRack tile = (TileEntityTanningRack) getTile(world, pos);
		if (tile != null) {
			return tile.interact(user, false, false);
		}
		return true;
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer user) {
		TileEntityTanningRack tile = (TileEntityTanningRack) getTile(world, pos);
		if (tile != null) {
			tile.interact(user, true, false);
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
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (state.getValue(FACING)) {
			case NORTH:
			case SOUTH:
				return AABB_NORTH;
			case WEST:
			case EAST:
				return AABB_WEST;
			default:
				return AABB_NORTH;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerClient() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "normal"));
	}
}
