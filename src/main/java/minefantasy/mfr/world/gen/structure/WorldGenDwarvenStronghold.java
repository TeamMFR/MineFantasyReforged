package minefantasy.mfr.world.gen.structure;

import minefantasy.mfr.world.gen.structure.pieces.StructureGenDSEntry;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

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
    protected StructureModuleMFR getStartPiece(World world, BlockPos pos, EnumFacing facing, Random random) {
        return new StructureGenDSEntry(world, pos.add(0, -1, 0), facing, random, isSurfaceBuild);
    }

    @Override
    protected boolean isBlockAcceptableOrigin(World world, BlockPos pos) {
        return isValidGround(world, pos);
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
                    BlockPos pos = piece.offsetPos(x, y, z, piece.facing);
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
