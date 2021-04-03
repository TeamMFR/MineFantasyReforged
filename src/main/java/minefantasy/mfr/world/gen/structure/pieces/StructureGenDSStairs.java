package minefantasy.mfr.world.gen.structure.pieces;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import minefantasy.mfr.world.gen.structure.WorldGenDwarvenStronghold;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.Random;

public class StructureGenDSStairs extends StructureModuleMFR {
    /**
     * The minimal height for a stairway to be created
     */
    public static final int minLevel = 20;
    protected ResourceLocation lootType = LootTableList.CHESTS_SIMPLE_DUNGEON;

    public StructureGenDSStairs(World world, BlockPos pos, EnumFacing facing, Random random) {
        super(world, pos, facing, random);
    }

    @Override
    public boolean canGenerate() {
        if (lengthId == -100) {
            return true;
        }

        int width_span = getWidthSpan();
        int depth = getDepthSpan();
        int height = getHeight();
        int filledSpaces = 0, emptySpaces = 0;
        for (int x = -width_span; x <= width_span; x++) {
            for (int y = 0; y <= height; y++) {
                for (int z = 1; z <= depth; z++) {
                    IBlockState state = world.getBlockState(offsetPos(x, y - z, z, facing));
                    if (!allowBuildOverBlock(state) || this.isUnbreakable(world, x, y - z, z, facing)) {
                        return false;
                    }
                    if (!state.getMaterial().isSolid()) {
                        ++emptySpaces;
                    } else {
                        ++filledSpaces;
                    }
                }
            }
        }
        if (WorldGenDwarvenStronghold.debug_air) {
            return true;
        }
        return ((float) emptySpaces / (float) (emptySpaces + filledSpaces)) < WorldGenDwarvenStronghold.maxAir;// at
        // least
        // 75%
        // full
    }

    private boolean allowBuildOverBlock(IBlockState state) {
        return state != MineFantasyBlocks.REINFORCED_STONE_BRICKS.getDefaultState() && state != MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
    }

    @Override
    public void generate() {
        int width_span = getWidthSpan();
        int depth = getDepthSpan();
        int height = getHeight();
        for (int x = -width_span; x <= width_span; x++) {
            for (int z = 0; z <= depth; z++) {
                Block block;

                // FLOOR
                block = getFloor(width_span, depth, x, z);
                if (block != null) {
                    placeBlock(world, block, x, -z - 1, z);

                    placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_BRICKS, x, -z - 2, z);

                    if (x == (width_span - 1) || x == -(width_span - 1)) {
                        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE,  x, -z, z);
                    } else {
                        placeBlock(world, Blocks.AIR, x, -z, z);
                    }
                }
                // WALLS
                for (int y = 0; y <= height + 1; y++) {
                    block = getWalls(width_span, height, depth, x, y, z);
                    if (block != null) {
                        placeBlock(world, block, x, y - z, z);
                    }
                }
                // CEILING
                block = getCeiling(x, z);
                if (block != null) {
                    placeBlock(world, block, x, height + 1 - z, z);
                }

                // TRIM
                block = getTrim(width_span, depth, x, z);
                if (block != null) {
                    placeBlock(world, block, x, height - z, z);
                    if (block == MineFantasyBlocks.REINFORCED_STONE_FRAMED) {
                        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x, height - z, z);
                        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, x, height - z - 1, z);

                        for (int h = height - 1; h > 1; h--) {
                            placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x, h - z - 1, z);
                        }
                        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, x, -z, z);
                    }
                }

            }
            MineFantasyReborn.LOG.error("Placed DSStairs at: " + pos);
        }

        // DOORWAY
        buildDoorway();

        if (lengthId == -100) {
            this.lengthId = -99;
            if ((pos.getY() > 64 && random.nextInt(3) != 0) || (pos.getY() >= 56 && random.nextInt(3) == 0)) {
                mapStructure(0, -depth, depth, StructureGenDSStairs.class);
            } else {
                mapStructure(0, -depth, depth, StructureGenDSCrossroads.class);
            }
        }
        ++lengthId;// Stairs don't count toward length
        if (lengthId > 0) {
            buildNext(depth);
        }
    }

    protected void buildDoorway() {
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 3; y++) {
                for (int z = 0; z >= -1; z--) {
                    placeBlock(world, Blocks.AIR, x, y, z);
                }
            }
            placeStairBlock(world, MineFantasyBlocks.COBBLE_BRICK_STAIRS, x, 0, -1, facing, facing.getOpposite());
        }
    }

    protected void buildNext(int depth) {
        if (lengthId > 1 && random.nextInt(3) == 0 && pos.getY() >= minLevel) {
            mapStructure(0, -depth, depth, StructureGenDSStairs.class);
        } else {
            tryPlaceHall(0, -depth, depth);
        }
    }

    protected void tryPlaceHall(int x, int y, int z) {
        Class<? extends StructureModuleMFR> extension = getRandomExtension();
        if (extension != null) {
            mapStructure(x, y, z, extension);
        }
    }

    protected int getHeight() {
        return 4;
    }

    protected int getDepthSpan() {
        return 9;
    }

    protected int getWidthSpan() {
        return 3;
    }

    protected Class<? extends StructureModuleMFR> getRandomExtension() {
        if (random.nextInt(20) == 0 && this.pos.getY() > 24) {
            return StructureGenDSStairs.class;
        }
        if (lengthId == 1) {
            return StructureGenDSRoom.class;
        }
        if (deviationCount > 0 && random.nextInt(4) == 0) {
            return StructureGenDSIntersection.class;
        }
        return StructureGenDSHall.class;
    }

    protected Block getTrim(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius) {
            return null;
        }

        if (x == -(radius - 1) || x == (radius - 1)) {
            if (z == Math.floor((float) depth / 2)) {
                return MineFantasyBlocks.REINFORCED_STONE_FRAMED;
            }
            return MineFantasyBlocks.REINFORCED_STONE;
        }
        return null;
    }

    protected Block getCeiling(int x, int z) {
        return x == 0 ? MineFantasyBlocks.REINFORCED_STONE : MineFantasyBlocks.REINFORCED_STONE_BRICKS;
    }

    protected Block getFloor(int radius, int depth, int x, int z) {
        if (x >= -1 && x <= 1) {
            if (z >= depth - 1) {
                return MineFantasyBlocks.COBBLE_BRICK;
            }
            return MineFantasyBlocks.COBBLE_BRICK_STAIRS;
        }
        if (x == -radius || x == radius || z == depth || z == 0) {
            return MineFantasyBlocks.REINFORCED_STONE;
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return MineFantasyBlocks.REINFORCED_STONE;
        }
        return MineFantasyBlocks.COBBLE_BRICK;
    }

    protected Block getWalls(int radius, int height, int depth, int x, int y, int z) {
        if (x != -radius && x != radius && z == 0) {
            return Blocks.AIR;
        }
        if (x == -radius || x == radius || z == depth) {
            return y == height / 2 ? MineFantasyBlocks.REINFORCED_STONE : MineFantasyBlocks.REINFORCED_STONE_BRICKS;
        }
        return Blocks.AIR;
    }

    public StructureModuleMFR setLoot(ResourceLocation loot) {
        this.lootType = loot;
        return this;
    }
}
