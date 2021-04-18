package minefantasy.mfr.world.gen.structure.pieces;

import minefantasy.mfr.entity.mob.EntityMinotaur;
import minefantasy.mfr.entity.mob.MinotaurBreed;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class StructureGenDSIntersection extends StructureGenDSHall {

    public StructureGenDSIntersection(World world, BlockPos pos, EnumFacing facing, Random random) {
        super(world, pos, facing, random);
    }

    @Override
    protected void buildNext(int width_span, int depth, int height) {
        --deviationCount;
        int offset = 0;
        boolean[] intersection = getRandomIntersectionPattern();
        if (intersection[0]) {
            tryPlaceHall(0, 0, depth + offset, facing);
        }
        if (intersection[1]) {
            tryPlaceHall(-(width_span + offset), 0, (int) Math.floor((float) depth / 2), facing.rotateY());
        }
        if (intersection[2]) {
            tryPlaceHall((width_span + offset), 0, (int) Math.floor((float) depth / 2), facing.rotateYCCW());
        }
    }

    private boolean[] getRandomIntersectionPattern() {
        boolean[][] groups = new boolean[][]{new boolean[]{true, true, true}, new boolean[]{true, true, false},
                new boolean[]{true, false, true}, new boolean[]{false, true, true},};

        return groups[random.nextInt(groups.length)];
    }

    @Override
    protected int getHeight() {
        return 4;
    }

    @Override
    protected int getDepthSpan() {
        return 8;
    }

    @Override
    protected int getWidthSpan() {
        return 4;
    }

    @Override
    protected IBlockState getTrim(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }

        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            if ((x == -(radius - 1) && (z == (depth - 1) || z == 1))
                    || (x == (radius - 1) && (z == (depth - 1) || z == 1))) {
                return MineFantasyBlocks.REINFORCED_STONE_FRAMED.getDefaultState();
            }
            return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
        }
        return null;
    }

    @Override
    protected IBlockState getCeiling(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
        }
        return getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random);
    }

    @Override
    protected IBlockState getFloor(int radius, int depth, int x, int z) {
        if (z == 0 && x >= -1 && x <= 1) {
            return FLOOR;
        }
        if (x == -radius || x == radius || z == depth || z == 0) {
            return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
        }
        return FLOOR;
    }

    @Override
    protected IBlockState getWalls(int radius, int height, int depth, int x, int y, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            if ((x == -radius && (z == depth || z == 0)) || (x == radius && (z == depth || z == 0))) {
                return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
            }

            return y == height / 2 ? getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_1, random) : getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random);
        }
        return Blocks.AIR.getDefaultState();
    }

    @Override
    protected void buildDoorway(int width_span, int depth, int height) {
        for (int x = -2; x <= 2; x++) {
            for (int y = 1; y <= 3; y++) {
                for (int z = 0; z >= -1; z--) {
                    placeBlock(world, Blocks.AIR, x, y, z);
                }
            }
        }
        for (int x = -1; x <= 1; x++) {
            for (int z = 0; z >= -1; z--) {
                placeBlock(world, Blocks.AIR, x, 4, z);
            }
        }

        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE,-2, 4, 0);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 2, 4, 0);

        EntityMinotaur mob = new EntityMinotaur(world);
        this.placeEntity(world, mob, 0, 0, depth / 2);
        mob.setSpecies(MinotaurBreed.getEnvironment(subtype));
        mob.worldGenTier(MinotaurBreed.getEnvironment(subtype), 1);
    }

    @Override
    protected Class<? extends StructureModuleMFR> getRandomExtension() {
        if (lengthId == 1) {
            return StructureGenDSRoom.class;
        }
        return (pos.getY() >= StructureGenDSStairs.minLevel && random.nextInt(3) == 0) ? StructureGenDSStairs.class : StructureGenDSHall.class;
    }
}
