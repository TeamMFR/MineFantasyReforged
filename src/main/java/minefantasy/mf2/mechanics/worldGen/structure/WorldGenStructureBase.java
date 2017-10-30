package minefantasy.mf2.mechanics.worldGen.structure;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public abstract class WorldGenStructureBase extends WorldGenerator {
    public WorldGenStructureBase() {
    }

    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        StructureModuleMF building;
        int[] bounds = getYGenBounds(world);
        for (int y = bounds[1]; y >= bounds[0]; y--) {
            if (isBlockAcceptableOrigin(world, i, y, k)) {
                if (isDirectionRandom()) {
                    building = getStartPiece(world, i, y + 1, k, random.nextInt(4));
                    if (building != null && canStructureBuild(building)) {
                        building.generate();
                        return true;
                    }
                } else {
                    for (int d = 0; d < 4; d++) {
                        building = getStartPiece(world, i, y + 1, k, d);
                        if (building != null && canStructureBuild(building)) {
                            building.generate();
                            return true;
                        }
                    }
                }
                building = null;
                return false;
            }
        }
        building = null;
        return false;
    }

    protected boolean isValidGround(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (!block.getMaterial().isSolid()) {
            return false;
        }

        return block.getMaterial() == Material.rock || block.getMaterial() == Material.ground
                || block.getMaterial() == Material.grass;
    }

    /**
     * The min then max values which y axis allows generation
     */
    protected abstract int[] getYGenBounds(World world);

    /**
     * Whether or not a particular block allows a generation
     */
    protected abstract boolean isBlockAcceptableOrigin(World world, int x, int y, int z);

    /**
     * Called after IsBlockAcceptableOrigin: If a specific structure can generate
     * here
     */
    protected abstract boolean canStructureBuild(StructureModuleMF ruin);

    /**
     * True if direction is random, false if it tries one of four
     */
    protected abstract boolean isDirectionRandom();

    /**
     * The "Entry" instance
     */
    protected abstract StructureModuleMF getStartPiece(World world, int x, int y, int z, int direction);
}
