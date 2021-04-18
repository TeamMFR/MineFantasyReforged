package minefantasy.mfr.world.gen.structure;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class WorldGenStructureBase {
    public WorldGenStructureBase() {
    }

    public boolean generate(World world, Random random, BlockPos pos) {
        StructureModuleMFR building;
        int[] bounds = getYGenBounds(world);
        for (int y = bounds[1]; y >= bounds[0]; y--) {
            BlockPos newPos = new BlockPos(pos.getX(), y, pos.getZ());
            if (isBlockAcceptableOrigin(world, newPos)) {
                if (isDirectionRandom()) {
                    building = getStartPiece(world, newPos.add(0, 1,0), EnumFacing.HORIZONTALS[random.nextInt(EnumFacing.HORIZONTALS.length)], random);
                    if (building != null && canStructureBuild(building)) {
                        building.generate();
                        return true;
                    }
                } else {
                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                        building = getStartPiece(world, newPos.add(0, 1, 0), facing, random);
                        if (building != null && canStructureBuild(building)) {
                            building.generate();
                            return true;
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }

    protected boolean isValidGround(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        if (!state.getMaterial().isSolid()) {
            return false;
        }

        return state.getMaterial() == Material.ROCK || state.getMaterial() == Material.GROUND || state.getMaterial() == Material.GRASS;
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
    protected abstract boolean canStructureBuild(StructureModuleMFR structure);

    /**
     * True if direction is random, false if it tries one of four
     */
    protected abstract boolean isDirectionRandom();

    /**
     * The "Entry" instance
     */
    protected abstract StructureModuleMFR getStartPiece(World world, BlockPos pos, EnumFacing facing, Random random);
}
