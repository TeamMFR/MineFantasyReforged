package minefantasy.mfr.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFrameHolder extends BasicBlockMF {

	private static final AxisAlignedBB FRAME_AABB = new AxisAlignedBB(0 / 16D, 4 / 16D, 0 / 16D, 16 / 16D, 12 / 16D, 16 / 16D);

	public BlockFrameHolder(String name, Object drop) {
		super(name, Material.IRON, drop);
		setCreativeTab(null);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FRAME_AABB;
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
}
