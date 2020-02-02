package minefantasy.mfr.mechanics.worldGen.structure;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public abstract class WorldGenStructureBase extends WorldGenerator {
    public WorldGenStructureBase() {
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos) {
        int i = pos.getX();
        int k = pos.getZ();
        StructureModuleMFR building;
        int[] bounds = getYGenBounds(world);
        for (int y = bounds[1]; y >= bounds[0]; y--) {
            BlockPos newPos = new BlockPos(i,y,k);
            if (isBlockAcceptableOrigin(world, newPos)) {
                if (isDirectionRandom()) {
                    building = getStartPiece(world, newPos.add(0,1,0), random.nextInt(4));
                    if (building != null && canStructureBuild(building)) {
                        building.generate();
                        return true;
                    }
                } else {
                    for (int d = 0; d < 4; d++) {
                        building = getStartPiece(world, newPos.add(0,1,0), d);
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

    protected boolean isValidGround(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        Block block = world.getBlockState(pos).getBlock();
        if (!block.getMaterial(state).isSolid()) {
            return false;
        }

        return block.getMaterial(state) == Material.ROCK || block.getMaterial(state) == Material.GROUND
                || block.getMaterial(state) == Material.GRASS;
    }

    /**
     * The min then max values which y axis allows generation
     */
    protected abstract int[] getYGenBounds(World world);

    /**
     * Whether or not a particular block allows a generation
     */
    protected abstract boolean isBlockAcceptableOrigin(World world, BlockPos pos);

    /**
     * Called after IsBlockAcceptableOrigin: If a specific structure can generate
     * here
     */
    protected abstract boolean canStructureBuild(StructureModuleMFR ruin);

    /**
     * True if direction is random, false if it tries one of four
     */
    protected abstract boolean isDirectionRandom();

    /**
     * The "Entry" instance
     */
    protected abstract StructureModuleMFR getStartPiece(World world, BlockPos pos, int direction);
}
