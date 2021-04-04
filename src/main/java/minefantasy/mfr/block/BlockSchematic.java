package minefantasy.mfr.block;

import minefantasy.mfr.init.MineFantasyTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSchematic extends Block {

	private static AxisAlignedBB AABB = new AxisAlignedBB(0D, 0.01D, 0.125D, 1D, 0.01D, 0.8125D);


	public BlockSchematic(String name) {
		super(Material.CLOTH);

		setRegistryName(name);
		setUnlocalizedName(name);

		this.setCreativeTab(MineFantasyTabs.tabMaterials);
	}


//	public static boolean useSchematic(ItemStack item, World world, EntityPlayer user, RayTraceResult movingobjectposition) {
//		if (movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK) {
//			BlockPos hit = movingobjectposition.getBlockPos();
//
//			if (movingobjectposition.sideHit == EnumFacing.UP) {
//				hit.add(0, 1, 0);
//			}
//
//			if (movingobjectposition.sideHit == EnumFacing.DOWN) {
//				hit.add(0, 1, 0);
//			}
//
//			if (movingobjectposition.sideHit == EnumFacing.NORTH) {
//				hit.add(0, 0, 1);
//			}
//
//			if (movingobjectposition.sideHit == EnumFacing.SOUTH) {
//				hit.add(0, 0, 1);
//			}
//
//			if (movingobjectposition.sideHit == EnumFacing.EAST) {
//				hit.add(1, 0, 0);
//			}
//
//			if (movingobjectposition.sideHit == EnumFacing.WEST) {
//				hit.add(1, 0, 0);
//			}
//
//			if (user.canPlayerEdit(hit, movingobjectposition.sideHit, item)) {
//				return placeSchematic(item.getItemDamage(), user, item, user.world, hit);
//			}
//		}
//		return false;
//	}
//
//	public static boolean placeSchematic(int meta, EntityPlayer user, ItemStack item, World world, BlockPos pos) {
//		if (world.isAirBlock(pos) && canBuildOn(world, pos.add(0, -1, 0))) {
//			world.setBlockState(pos, (MineFantasyBlocks.SCHEMATIC_GENERAL).getDefaultState());
//			return true;
//		}
//		return false;
//	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}
}
