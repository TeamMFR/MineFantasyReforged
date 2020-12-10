package minefantasy.mfr.mechanics.worldGen.structure.dwarven;

import minefantasy.mfr.entity.mob.EntityMinotaur;
import minefantasy.mfr.entity.mob.MinotaurBreed;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.mechanics.worldGen.structure.StructureModuleMFR;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureGenDSIntersection extends StructureGenDSHall {

    public StructureGenDSIntersection(World world, StructureCoordinates position) {
        super(world, position);
    }

    @Override
    protected void buildNext(int width_span, int depth, int height) {
        --deviationCount;
        int offset = 0;
        boolean[] intersection = getRandomIntersectionPattern();
        if (intersection[0]) {
            tryPlaceHall(new BlockPos(0, 0, depth + offset), direction);
        }
        if (intersection[1]) {
            tryPlaceHall(new BlockPos(-(width_span + offset), 0, (int) Math.floor((float) depth / 2)), rotateRight());
        }
        if (intersection[2]) {
            tryPlaceHall(new BlockPos((width_span + offset), 0, (int) Math.floor((float) depth / 2)), rotateLeft());
        }
    }

    private boolean[] getRandomIntersectionPattern() {
        boolean[][] groups = new boolean[][]{new boolean[]{true, true, true}, new boolean[]{true, true, false},
                new boolean[]{true, false, true}, new boolean[]{false, true, true},};

        return groups[rand.nextInt(groups.length)];
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

    protected void tryPlaceMinorRoom(int x, int y, int z, int d) {
    }

    @Override
    protected Object[] getTrim(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }

        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            if ((x == -(radius - 1) && (z == (depth - 1) || z == 1))
                    || (x == (radius - 1) && (z == (depth - 1) || z == 1))) {
                return new Object[]{MineFantasyBlocks.REINFORCED_STONE_FRAMED, false};
            }
            return new Object[]{MineFantasyBlocks.REINFORCED_STONE, false};
        }
        return null;
    }

    @Override
    protected Object[] getCeiling(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return new Object[]{MineFantasyBlocks.REINFORCED_STONE, false};
        }
        return new Object[]{MineFantasyBlocks.REINFORCED_STONE_BRICKS, true};
    }

    @Override
    protected Object[] getFloor(int radius, int depth, int x, int z) {
        if (z == 0 && x >= -1 && x <= 1) {
            return new Object[]{floor, false};
        }
        if (x == -radius || x == radius || z == depth || z == 0) {
            return new Object[]{MineFantasyBlocks.REINFORCED_STONE, false};
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return new Object[]{MineFantasyBlocks.REINFORCED_STONE, false};
        }
        return new Object[]{floor, false};
    }

    @Override
    protected Object[] getWalls(int radius, int height, int depth, BlockPos pos) {
        if (pos.getX() == -radius || pos.getX() == radius || pos.getZ() == depth || pos.getZ() == 0) {
            if ((pos.getX() == -radius && (pos.getZ() == depth || pos.getZ() == 0)) || (pos.getX() == radius && (pos.getZ() == depth || pos.getZ() == 0))) {
                return new Object[]{MineFantasyBlocks.REINFORCED_STONE, false};
            }

            return pos.getY() == height / 2 ? new Object[]{MineFantasyBlocks.REINFORCED_STONE, "Hall"}
                    : new Object[]{MineFantasyBlocks.REINFORCED_STONE_BRICKS, true};
        }
        return new Object[]{Blocks.AIR, false};
    }

    @Override
    protected void buildDoorway(int width_span, int depth, int height) {
        for (int x = -2; x <= 2; x++) {
            for (int y = 1; y <= 3; y++) {
                for (int z = 0; z >= -1; z--) {
                    placeBlock(Blocks.AIR, new BlockPos(x, y, z) );
                }
            }
        }
        for (int x = -1; x <= 1; x++) {
            for (int z = 0; z >= -1; z--) {
                placeBlock(Blocks.AIR, new BlockPos( x, 4, z));
            }
        }

        placeBlock(MineFantasyBlocks.REINFORCED_STONE, new BlockPos(-2, 4, 0) );
        placeBlock(MineFantasyBlocks.REINFORCED_STONE, new BlockPos(2, 4, 0) );

        EntityMinotaur mob = new EntityMinotaur(world);
        this.placeEntity(mob, new BlockPos(0, 0, depth / 2));
        mob.setSpecies(MinotaurBreed.getEnvironment(subtype));
        mob.worldGenTier(MinotaurBreed.getEnvironment(subtype), 1);
    }

    @Override
    protected Class<? extends StructureModuleMFR> getRandomExtension() {
        if (lengthId == 1) {
            return StructureGenDSRoom.class;
        }
        return (pos.getY() >= StructureGenDSStairs.minLevel && rand.nextInt(3) == 0) ? StructureGenDSStairs.class
                : StructureGenDSHall.class;
    }
}
