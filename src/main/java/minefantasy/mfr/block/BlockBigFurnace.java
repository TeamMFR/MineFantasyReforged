package minefantasy.mfr.block;

import codechicken.lib.model.ModelRegistryHelper;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.client.model.block.ModelDummyParticle;
import minefantasy.mfr.client.render.block.TileEntityBigFurnaceRenderer;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.tile.TileEntityBase;
import minefantasy.mfr.tile.TileEntityBigFurnace;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockBigFurnace extends BlockTileEntity<TileEntityBigFurnace> implements IClientRegister {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public final boolean isHeater;
	public final int tier;

	public BlockBigFurnace(String name, boolean isHeater, int tier) {
		super(Material.ROCK);

		setRegistryName(name);
		setUnlocalizedName(name);
		this.isHeater = isHeater;
		this.tier = tier;
		this.setCreativeTab(MineFantasyTabs.tabUtil);
		this.setSoundType(SoundType.STONE);
		this.setHardness(3.5F);
		this.setResistance(2F);
		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityBigFurnace();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity tile = getTile(world, pos);
		return ((TileEntityBigFurnace) tile).isBurning() ? 15 : 0;
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntityBigFurnace tile = (TileEntityBigFurnace) getTile(world, pos);
			if (tile != null) {
				tile.openGUI(world, player);
			}

		}
		return true;
	}

	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		return TileEntityBase.calculateRedstoneFromInventory(getTile(worldIn, pos).getInventory());
	}

	@Override
	public String getTexture() {
		return "cauldron_side";
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

	@SideOnly(Side.CLIENT)
	@Override
	public void registerClient() {
		ModelResourceLocation modelLocation = new ModelResourceLocation(getRegistryName(), "special");
		ModelRegistryHelper.registerItemRenderer(Item.getItemFromBlock(this), new TileEntityBigFurnaceRenderer<>());
		ModelRegistryHelper.register(modelLocation, new ModelDummyParticle(this.getTexture()));
		ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return modelLocation;
			}
		});
	}
}
