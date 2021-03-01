package minefantasy.mfr.world.gen.structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenAncientAlter extends WorldGenStructureBase {
	public WorldGenAncientAlter() {
	}

	@Override
	protected StructureModuleMFR getStartPiece(World world, BlockPos pos, int direction) {
		return new StructureGenAncientAlter(world, pos, direction);
	}

	@Override
	protected boolean isBlockAcceptableOrigin(World world, BlockPos pos) {
		return world.getBlockState(pos).getMaterial().isSolid() && isValidGround(world, pos)
				&& world.canBlockSeeSky(pos.add(0, 2, 0));
	}

	@Override
	protected boolean canStructureBuild(StructureModuleMFR piece) {
		return true;
	}

	@Override
	protected boolean isDirectionRandom() {
		return true;
	}

	@Override
	protected int[] getYGenBounds(World world) {
		return new int[] {64, 255};
	}
}
