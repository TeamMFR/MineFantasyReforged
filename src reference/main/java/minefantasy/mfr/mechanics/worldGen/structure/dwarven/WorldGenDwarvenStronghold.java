package minefantasy.mfr.mechanics.worldGen.structure.dwarven;

import minefantasy.mfr.mechanics.worldGen.structure.StructureModuleMFR;
import minefantasy.mfr.mechanics.worldGen.structure.WorldGenStructureBase;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenDwarvenStronghold extends WorldGenStructureBase {
    /**
     * A debug method used to allow strongholds to generate in the air, for easy
     * observation of shape and size.
     */
    public static final boolean debug_air = false;
    /**
     * Max percentage of air blocks allowed for structures to build
     */
    public static float maxAir = 0.25F;
    private boolean isSurfaceBuild = false;

    public WorldGenDwarvenStronghold() {
    }

    public void setSurfaceMode(boolean flag) {
        isSurfaceBuild = flag;
    }

    @Override
    protected StructureModuleMFR getStartPiece(World world,BlockPos pos, int direction) {
        return new StructureGenDSEntry(world, pos.add(0,-1,0), direction, isSurfaceBuild);
        /*
         * if(startPiece == null) { startPiece = new StructureGenDSEntry(world, x, y-1,
         * z, direction, isSurfaceBuild); } startPiece.direction = direction; return
         * startPiece;
         *
         */
    }

    @Override
    protected boolean isBlockAcceptableOrigin(World world, BlockPos pos) {
        return isValidGround(world, pos);// && world.canBlockSeeTheSky(x, y+2, z);
    }

    @Override
    protected boolean canStructureBuild(StructureModuleMFR piece) {
        if (debug_air || isSurfaceBuild) {
            return true;
        }
        // SEARCH FOR CLIFF
        for (int x = -3; x <= 3; x++) {
            for (int y = 0; y < 5; y++) {
                for (int z = 4; z <= 8; z++) {
                    BlockPos pos = piece.offsetPos(x, y, z, piece.direction);
                    Material material = piece.world.getBlockState(pos).getMaterial();

                    if (!material.isOpaque()) {
                        return false;
                    }
                    if (!material.isSolid()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected boolean isDirectionRandom() {
        return isSurfaceBuild;
    }

    @Override
    protected int[] getYGenBounds(World world) {
        return new int[]{60, 255};
    }
}
