package minefantasy.mfr.world.gen.structure;

import net.minecraft.world.World;

public class WorldGenAncientAlter extends WorldGenStructureBase {
    public WorldGenAncientAlter() {
    }

    @Override
    protected StructureModuleMFR getStartPiece(World world, int x, int y, int z, int direction) {
        return new StructureGenAncientAlter(world, x, y, z, direction);
    }

    @Override
    protected boolean isBlockAcceptableOrigin(World world, int x, int y, int z) {
        return world.getBlock(x, y, z).getMaterial().isSolid() && isValidGround(world, x, y, z)
                && world.canBlockSeeTheSky(x, y + 2, z);
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
        return new int[]{64, 255};
    }
}
