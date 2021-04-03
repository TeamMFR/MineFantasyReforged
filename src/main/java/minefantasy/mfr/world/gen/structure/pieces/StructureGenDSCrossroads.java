package minefantasy.mfr.world.gen.structure.pieces;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.config.ConfigWorldGen;
import minefantasy.mfr.entity.mob.EntityMinotaur;
import minefantasy.mfr.entity.mob.MinotaurBreed;
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

public class StructureGenDSCrossroads extends StructureModuleMFR {
    protected ResourceLocation lootType = LootTableList.CHESTS_SIMPLE_DUNGEON;
    protected Block floor_block = MineFantasyBlocks.COBBLE_BRICK;

    public StructureGenDSCrossroads(World world, BlockPos pos, EnumFacing facing, Random random) {
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
                    IBlockState block = world.getBlockState(offsetPos(x, y, z, facing));
                    if (!allowBuildOverBlock(block) || this.isUnbreakable(world, x, y, z, facing)) {
                        return false;
                    }
                    if (!block.getMaterial().isSolid()) {
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
        if (lengthId == -100) {
            this.lengthId = ConfigWorldGen.DSLength;
            this.deviationCount = ConfigWorldGen.DSDeviations;
        }

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
                    block = getWalls(width_span, depth, x, z);
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
                    placeBlock(world, block, x, height, z);
                    placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x, 4, z);
                    if (block == MineFantasyBlocks.REINFORCED_STONE_FRAMED) {
                        for (int h = height - 1; h > 1; h--) {
                            placeBlock(world, h == 5 ? Blocks.GLOWSTONE : MineFantasyBlocks.REINFORCED_STONE, x, h, z);
                        }
                        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, x, 1, z);
                    }
                }

            }
        }

        // DOORWAY
        buildDoorway();

        generateCrossroad();

        MineFantasyReborn.LOG.error("Placed DSCrossroads at: " + pos);
    }

    protected void buildDoorway() {
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 3; y++) {
                for (int z = 0; z >= -1; z--) {
                    placeBlock(world, Blocks.AIR, x, y, z);
                }
            }
        }
    }

    protected int getHeight() {
        return 7;
    }

    protected int getDepthSpan() {
        return 19;
    }

    protected int getWidthSpan() {
        return 7;
    }

    private Block getTrim(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1)) {
            if (z == (int) Math.ceil((float) depth / 2) || z == (int) Math.floor((float) depth / 2)) {
                return MineFantasyBlocks.REINFORCED_STONE_FRAMED;
            }
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            if ((x == -(radius - 1) && (z == (depth - 1) || z == 1))
                    || (x == (radius - 1) && (z == (depth - 1) || z == 1))) {
                return MineFantasyBlocks.REINFORCED_STONE_FRAMED;
            }
            return MineFantasyBlocks.REINFORCED_STONE;
        }
        return null;
    }

    private Block getCeiling(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return MineFantasyBlocks.REINFORCED_STONE;
        }
        return MineFantasyBlocks.REINFORCED_STONE_BRICKS;
    }

    private Block getFloor(int radius, int depth, int x, int z) {
        if (z < 2 && x >= -1 && x <= 1) {
            return floor_block;
        }
        if (x == -radius || x == radius || z == depth || z == 0) {
            return MineFantasyBlocks.REINFORCED_STONE;
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return MineFantasyBlocks.REINFORCED_STONE;
        }
        return floor_block;
    }

    private Block getWalls(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            if ((x == -radius && (z == depth || z == 0)) || (x == radius && (z == depth || z == 0))) {
                return MineFantasyBlocks.REINFORCED_STONE;
            }

            return MineFantasyBlocks.REINFORCED_STONE_BRICKS;
        }
        return Blocks.AIR;
    }

    public StructureModuleMFR setLoot(ResourceLocation loot) {
        this.lootType = loot;
        return this;
    }

    public void generateCrossroad() {
        int width_span = getWidthSpan();
        int depth = getDepthSpan();

        int zOffset = 4;

        boolean stairSide = random.nextBoolean();

        tryPlaceCrossroadHall(0, depth + 1, stairSide, 0);

        tryPlaceCrossroadHall((width_span + 1), zOffset, stairSide, 20);
        tryPlaceCrossroadHall((width_span + 1), depth - zOffset, !stairSide, 40);

        tryPlaceCrossroadHall(-(width_span + 1), zOffset, stairSide, 60);
        tryPlaceCrossroadHall(-(width_span + 1), depth - zOffset, !stairSide, 80);

        this.placeMinotaur(depth - 2);
        this.placeMinotaur(2);

        int w = 2, d = 3;
        for (int x = -w; x <= w; x++) {
            for (int z = -d; z <= d; z++) {
                Block block = Blocks.WATER;
                if (z == -d || z == d || x == -w || x == w) {
                    block = MineFantasyBlocks.REINFORCED_STONE;
                }
                this.placeBlock(world, block, x, 1, depth / 2 + z);
            }
        }

    }

    private void placeMinotaur(int z) {
        EntityMinotaur mob = new EntityMinotaur(world);
        this.placeEntity(world, mob, 0, 1, z);
        mob.setSpecies(MinotaurBreed.getEnvironment(subtype));
        mob.worldGenTier(MinotaurBreed.getEnvironment(subtype), 1);
    }

    protected void tryPlaceCrossroadHall(int x, int z, boolean stair, int delay) {
        Class<? extends StructureModuleMFR> extension = stair ? StructureGenDSStairs.class : StructureGenDSHall.class;
        mapStructure(x, 0, z, extension, delay);
    }
}
