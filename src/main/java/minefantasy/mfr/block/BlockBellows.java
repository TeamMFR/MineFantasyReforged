package minefantasy.mfr.block;

import codechicken.lib.model.ModelRegistryHelper;
import com.google.common.collect.ImmutableMap;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.client.model.block.ModelDummyParticle;
import minefantasy.mfr.client.render.block.TileEntityBellowsRenderer;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.tile.TileEntityBellows;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
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
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.Map;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 */
public class BlockBellows extends BlockTileEntity<TileEntityBellows> implements IClientRegister {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private static final Map<EnumFacing, AxisAlignedBB> AABBS = ImmutableMap.of(
			EnumFacing.WEST, new AxisAlignedBB(0.0F, 0.0F, 0.188F, 0.7F, 0.95F, 0.81F),
			EnumFacing.EAST, new AxisAlignedBB(0.3F, 0.0F, 0.188F, 1.0F, 0.95F, 0.81F),
			EnumFacing.SOUTH, new AxisAlignedBB(0.188F, 0.0F, 0.3F, 0.81F, 0.95F, 1.0F),
			EnumFacing.NORTH, new AxisAlignedBB(0.188F, 0.0F, 0.0F, 0.81F, 0.95F, 0.7F));

	public BlockBellows() {
		super(Material.WOOD);

		setRegistryName("bellows");
		setUnlocalizedName("bellows");
		this.setHardness(1F);
		this.setResistance(0.5F);
		this.setCreativeTab(MineFantasyTabs.tabUtil);
		MineFantasyReborn.PROXY.addClientRegister(this);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityBellows();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

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
		world.setBlockState(pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityBellows bellows = (TileEntityBellows) getTile(world, pos);
		if (bellows != null) {
			bellows.interact(player, 2F);
		}
		return true;
	}

	@Override
	public String getTexture() {
		return "noteblock";
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
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerClient() {
		ModelResourceLocation modelLocation = new ModelResourceLocation(getRegistryName(), "special");
		ModelRegistryHelper.registerItemRenderer(Item.getItemFromBlock(this), new TileEntityBellowsRenderer<>());
		ModelRegistryHelper.register(modelLocation, new ModelDummyParticle(this.getTexture()));
		ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return modelLocation;
			}
		});
	}
}
