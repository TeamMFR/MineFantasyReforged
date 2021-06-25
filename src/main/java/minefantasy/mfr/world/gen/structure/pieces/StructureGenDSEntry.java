package minefantasy.mfr.world.gen.structure.pieces;


import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.entity.mob.EntityMinotaur;
import minefantasy.mfr.entity.mob.MinotaurBreed;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.Random;

public class StructureGenDSEntry extends StructureModuleMFR {
    private static final IBlockState FLOOR = MineFantasyBlocks.COBBLE_BRICK.getDefaultState();
    public boolean isSurfaceBuild;
    private ResourceLocation lootType = LootTableList.CHESTS_SIMPLE_DUNGEON;

    public StructureGenDSEntry(World world, BlockPos pos, EnumFacing facing, Random random, boolean surface) {
        super(world, pos, facing, random);
        this.isSurfaceBuild = surface;
    }

    @Override
    public void generate() {
        int environment = MinotaurBreed.getEnvironment(world, pos.getX(), pos.getZ(), 0);
        subtype = environment == 2 ? "frost" : "normal";

        int width_span = 4;
        int depth = 10;
        int height = 5;
        for (int x = -width_span; x <= width_span; x++) {
            for (int z = 0; z <= depth; z++) {
                // FOUNDATION
                IBlockState state = getFoundation(width_span, depth, x, z);
                if (state != null) {
                    this.buildFoundation(state, x, -1, z, 32, 4, false);
                }

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
                    placeBlockWithState(world, state, x, height + 2, z);
                    placeBlockWithState(world, state, x, height, z);
                    if (state == MineFantasyBlocks.REINFORCED_STONE_FRAMED.getDefaultState()) {
                        for (int h = height - 1; h > 1; h--) {
                            placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x, h, z);
                        }
                        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED,  x, 1, z);
                    }
                }

            }
        }

        // DOORWAY
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 3; y++) {
                placeBlock(world, Blocks.AIR, x, y, 0);
            }
            placeBlockWithState(world, getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random), x, 0, -1);
            placeBlockWithState(world, getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random), x, 0, -2);

            placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x, -1, -1);
            placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x, -1, -2);
            placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x, 0, -3);
        }
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, -2, 0, -1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, -2, 0, -2);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 2, 0, -1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 2, 0, -2);

        placeDoorway(-1, height);
        placeDoorway(1, height);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 0, height - 1, -3);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 0, height, -1);

        EntityMinotaur mob = new EntityMinotaur(world);
        this.placeEntity(world, mob, 0, 1, depth / 2);
        mob.worldGenTier(MinotaurBreed.getEnvironment(subtype), 1);

        this.lengthId = -99;
        mapStructure(0, 0, depth, StructureGenDSStairs.class);

        if (isSurfaceBuild) {
            mapStructure(-width_span, 0, depth - 1, facing.rotateY(), StructureGenDSSurfaceAppendage.class);
            mapStructure(-width_span, 0, 2, facing.rotateY(), StructureGenDSSurfaceAppendage.class);

            mapStructure(width_span, 0, depth - 1, facing.rotateYCCW(), StructureGenDSSurfaceAppendage.class);
            mapStructure(width_span, 0, 2, facing.rotateYCCW(), StructureGenDSSurfaceAppendage.class);
        }

        MineFantasyReforged.LOG.error("Placed Dwarven Stronghold at: " + pos);
    }

    private void placeDoorway(int mod, int height) {
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 3 * mod, 1, -1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 3 * mod, 1, -2);
        for (int y = 1; y < height; y++) {
            placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 2 * mod, y, -3);
        }
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, mod, height - 1, -3);

        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 2 * mod, height, -3);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 2 * mod, height, -2);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE,        mod, height, -1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 2 * mod, height, -1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 3 * mod, height, -1);

        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 2 * mod, height + 1, -1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 2 * mod, height + 2, -1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 2 * mod, height + 2, 0);
    }

    private IBlockState getTrim(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1)) {
            if (z == (int) Math.ceil((float) depth / 2)) {
                return MineFantasyBlocks.REINFORCED_STONE_FRAMED.getDefaultState();
            }
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            if ((x == -(radius - 1) && (z == (depth - 1) || z == 1)) || (x == (radius - 1) && (z == (depth - 1) || z == 1))) {
                return MineFantasyBlocks.REINFORCED_STONE_FRAMED.getDefaultState();
            }
            return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
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

    private IBlockState getFoundation(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            if (x == -radius || x == radius || x == 0) {
                if (z == depth || z == Math.ceil((float) depth / 2) || z == 0) {
                    return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
                }
            }
        }
        return getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random);
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
}
