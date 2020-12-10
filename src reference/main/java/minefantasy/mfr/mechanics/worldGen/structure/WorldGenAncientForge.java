package minefantasy.mfr.world.gen.structure;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenAncientForge extends WorldGenStructureBase {
    public WorldGenAncientForge() {
    }

    @Override
    protected StructureModuleMFR getStartPiece(World world, int x, int y, int z, int direction) {
        return new StructureGenAncientForgeEntry(world, x, y, z, direction);
    }

    @Override
    protected boolean isBlockAcceptableOrigin(World world, BlockPos pos) {
        return world.getBlockState(pos).getMaterial().isSolid() && isValidGround(world, pos);
    }

    @Override
    protected boolean canStructureBuild(StructureModuleMFR piece) {
        // SEARCH FOR CLIFF
        for (int x = -1; x <= 1; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 1; z <= 2; z++) {
                    int[] pos = piece.offsetPos(x, y, z, piece.direction);
                    Material material = piece.world.getBlockState(pos[0], pos[1], pos[2]).getMaterial();

                    if (!material.isOpaque()) {
                        return false;
                    }
                    if (!material.isSolid()) {
                        return false;
                    }
                }
                for (int z = 0; z > -2; z--) {
                    Block block = piece.getBlock(x, y, z);
                    if (block.getMaterial().isSolid()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected boolean isDirectionRandom() {
        return false;
    }

    @Override
    protected int[] getYGenBounds(World world) {
        return new int[]{64, 255};
    }
}
