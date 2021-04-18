package minefantasy.mfr.world.gen.structure.pieces;

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
    protected IBlockState FLOOR = MineFantasyBlocks.COBBLE_BRICK.getDefaultState();

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
        return state != MineFantasyBlocks.REINFORCED_STONE_BRICKS.getDefaultState()
                && state != MineFantasyBlocks.REINFORCED_STONE.getDefaultState()
                && state != MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_0.getDefaultState()
                && state != MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_1.getDefaultState()
                && state != MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_2.getDefaultState()
                && state != MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_3.getDefaultState();
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
                IBlockState state;
                // FLOOR
                state = getFloor(width_span, depth, x, z);
                if (state != null) {
                    placeBlockWithState(world, state, x, 0, z);
                }
                // WALLS
                for (int y = 1; y <= height + 1; y++) {
                    state = getWalls(width_span, depth, x, z);
                    if (state != null) {

                        placeBlockWithState(world, state, x, y, z);
                    }
                }
                // CEILING
                state = getCeiling(width_span, depth, x, z);
                if (state != null) {
                    placeBlockWithState(world, state, x, height + 1, z);
                }

                // TRIM
                state = getTrim(width_span, depth, x, z);
                if (state != null) {
                    placeBlockWithState(world, state, x, height, z);
                    placeBlockWithState(world, state, x, 4, z);
                    if (state == MineFantasyBlocks.REINFORCED_STONE_FRAMED.getDefaultState()) {
                        for (int h = height - 1; h > 1; h--) {
                            if (h == 5){
                                placeBlock(world, Blocks.GLOWSTONE, x, h, z);
                            }
                            else if (h == 2){
                                placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_0, x, h, z );
                            }
                            else {
                                placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x, h, z);
                            }
                        }
                        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, x, 1, z);
                    }
                }

            }
        }

        // DOORWAY
        buildDoorway();

        generateCrossroad();
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

    private IBlockState getTrim(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1)) {
            if (z == (int) Math.ceil((float) depth / 2) || z == (int) Math.floor((float) depth / 2)) {
                return MineFantasyBlocks.REINFORCED_STONE_FRAMED.getDefaultState();
            }
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            if ((x == -(radius - 1) && (z == (depth - 1) || z == 1)) || (x == (radius - 1) && (z == (depth - 1) || z == 1))) {
                return MineFantasyBlocks.REINFORCED_STONE_FRAMED.getDefaultState();
            }
            Block block = MineFantasyBlocks.REINFORCED_STONE;
            if (x == 0 || z == 5 || z == (depth - 5)) {
                block = MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_0;
            }
            return block.getDefaultState();
        }
        return null;
    }

    private IBlockState getCeiling(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
        }
        return getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random);
    }

    private IBlockState getFloor(int radius, int depth, int x, int z) {
        if (z < 2 && x >= -1 && x <= 1) {
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

    private IBlockState getWalls(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            if ((x == -radius && (z == depth || z == 0)) || (x == radius && (z == depth || z == 0))) {
                return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
            }

            return getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random);
        }
        return Blocks.AIR.getDefaultState();
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

        tryPlaceCrossroadHall(0, depth + 1, facing, stairSide, 0);

        tryPlaceCrossroadHall((width_span + 1), zOffset, facing.rotateYCCW(), stairSide, 20);
        tryPlaceCrossroadHall((width_span + 1), depth - zOffset, facing.rotateYCCW(), !stairSide, 40);

        tryPlaceCrossroadHall(-(width_span + 1), zOffset, facing.rotateY(), stairSide, 60);
        tryPlaceCrossroadHall(-(width_span + 1), depth - zOffset, facing.rotateY(), !stairSide, 80);

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

    protected void tryPlaceCrossroadHall(int x, int z, EnumFacing facing, boolean stair, int delay) {
        Class<? extends StructureModuleMFR> extension = stair ? StructureGenDSStairs.class : StructureGenDSHall.class;
        mapStructure(x, 0, z, facing, extension, delay);
    }
}
