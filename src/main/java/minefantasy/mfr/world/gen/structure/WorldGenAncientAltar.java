package minefantasy.mfr.world.gen.structure;

import minefantasy.mfr.world.gen.structure.pieces.StructureGenAncientAltar;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenAncientAltar extends WorldGenStructureBase {
    public WorldGenAncientAltar() {
    }

    @Override
    protected StructureModuleMFR getStartPiece(World world, BlockPos pos, EnumFacing facing, Random random) {
        return new StructureGenAncientAltar(world, pos, facing, random);
    }

    @Override
    protected boolean isBlockAcceptableOrigin(World world, BlockPos pos) {
        return world.getBlockState(pos).getMaterial().isSolid() && isValidGround(world, pos) && world.canBlockSeeSky(pos.add(0,2,0));
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
        return new int[]{66, 255};
    }
}
