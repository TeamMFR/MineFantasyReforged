package minefantasy.mf2.mechanics.worldGen.structure;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class WorldGenAncientForge extends WorldGenStructureBase {
    public WorldGenAncientForge() {
    }

    @Override
    protected StructureModuleMF getStartPiece(World world, int x, int y, int z, int direction) {
        return new StructureGenAncientForgeEntry(world, x, y, z, direction);
    }

    @Override
    protected boolean isBlockAcceptableOrigin(World world, int x, int y, int z) {
        return world.getBlock(x, y, z).getMaterial().isSolid() && isValidGround(world, x, y, z);
    }

    @Override
    protected boolean canStructureBuild(StructureModuleMF piece) {
        // SEARCH FOR CLIFF
        for (int x = -1; x <= 1; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 1; z <= 2; z++) {
                    int[] pos = piece.offsetPos(x, y, z, piece.direction);
                    Material material = piece.worldObj.getBlock(pos[0], pos[1], pos[2]).getMaterial();

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
