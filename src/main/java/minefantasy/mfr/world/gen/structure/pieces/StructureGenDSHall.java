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

public class StructureGenDSHall extends StructureModuleMFR {
    protected static Block floor = MineFantasyBlocks.COBBLE_BRICK;
    protected ResourceLocation lootType = LootTableList.CHESTS_SIMPLE_DUNGEON;

    public StructureGenDSHall(World world, BlockPos pos, EnumFacing facing, Random random) {
        super(world, pos, facing, random);
    }

    public static int getRandomEngravedWall(Random rand) {
        return 2 + rand.nextInt(3);
    }

    @Override
    public boolean canGenerate() {
        int width_span = getWidthSpan();
        int depth = getDepthSpan();
        int height = getHeight();
        int empty_spaces = 0;
        int filledSpaces = 0, emptySpaces = 0;
        for (int x = -width_span; x <= width_span; x++) {
            for (int y = 0; y <= height; y++) {
                for (int z = 1; z <= depth; z++) {
                    IBlockState state = world.getBlockState(offsetPos(x, y, z, facing));
                    if (!allowBuildOverBlock(state) || this.isUnbreakable(world, x, y, z, facing)) {
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
                    placeBlock(world, block, x, 0, z);
                }
                // WALLS
                for (int y = 1; y <= height + 1; y++) {
                    block = getWalls(width_span, height, depth, x, y, z);
                    if (block != null) {
                        placeBlock(world, block, x, y, z);
                    }
                }
                // CEILING
                block = getCeiling(width_span, depth, x, z);
                if (block != null) {
                    placeBlock(world, block, x, height + 1, z);
                }

                // TRIM
                block = getTrim(width_span, depth, x, z);
                if (block != null) {
                    placeBlock(world, block,  x, height, z);
                    if (block == MineFantasyBlocks.REINFORCED_STONE_FRAMED) {
                        for (int h = height - 1; h > 1; h--) {
                            placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x, h, z);
                        }
                        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, x, 1, z);
                    }
                }

            }
        }

        // DOORWAY
        buildDoorway(width_span, depth, height);

        if (lengthId > 0) {
            buildNext(width_span, depth, height);
        } else {
            mapStructure(0, 0, depth, StructureGenDSRoom.class);
        }
        if (random.nextInt(3) != 0) {
            tryPlaceMinorRoom((width_span), (int) Math.floor((float) depth / 2));
        }
        if (random.nextInt(3) != 0) {
            tryPlaceMinorRoom(-(width_span), (int) Math.floor((float) depth / 2));
        }
        if (this instanceof StructureGenDSIntersection || this.lengthId % 2 == 0) {
            placeBlock(world, Blocks.GLOWSTONE, 0, height + 1, depth / 2);
        }

        MineFantasyReborn.LOG.error("Placed DSHall at: " + pos);
    }

    protected void buildDoorway(int width_span, int depth, int height) {
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 3; y++) {
                for (int z = 0; z >= -1; z--) {
                    placeBlock(world, Blocks.AIR,  x, y, z);
                }
            }
        }
    }

    protected void buildNext(int width_span, int depth, int height) {
        tryPlaceHall(0, 0, depth);
    }

    protected void tryPlaceHall(int x, int y, int z) {
        Class<? extends StructureModuleMFR> extension = getRandomExtension();
        if (extension != null) {
            mapStructure(x, y, z, extension);
        }
    }

    protected void tryPlaceMinorRoom(int x, int z) {
        Class<? extends StructureModuleMFR> extension = getRandomMinorRoom();
        if (extension != null) {
            mapStructure(x, 0, z, extension);
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

    protected Class<? extends StructureModuleMFR> getRandomMinorRoom() {
        return StructureGenDSRoomSml.class;
    }

    protected Class<? extends StructureModuleMFR> getRandomExtension() {
        if (lengthId == 1) {
            return StructureGenDSRoom.class;
        }
        if (deviationCount > 0 && random.nextInt(4) == 0) {
            return StructureGenDSIntersection.class;
        }
        if (random.nextInt(16) == 0 && this.pos.getY() >= StructureGenDSStairs.minLevel) {
            return StructureGenDSStairs.class;
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

    protected Block getCeiling(int radius, int depth, int x, int z) {
        return x == 0 ? MineFantasyBlocks.REINFORCED_STONE : MineFantasyBlocks.REINFORCED_STONE_BRICKS;
    }

    protected Block getFloor(int radius, int depth, int x, int z) {
        if (x >= -1 && x <= 1) {
            return floor;
        }
        if (x == -radius || x == radius || z == depth || z == 0) {
            return MineFantasyBlocks.REINFORCED_STONE;
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return MineFantasyBlocks.REINFORCED_STONE;
        }
        return floor;
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
