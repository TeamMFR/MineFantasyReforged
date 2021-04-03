package minefantasy.mfr.world.gen.structure.pieces;


import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.entity.mob.EntityMinotaur;
import minefantasy.mfr.entity.mob.MinotaurBreed;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.Random;

public class StructureGenDSEntry extends StructureModuleMFR {
    private static final Block floor = MineFantasyBlocks.COBBLE_BRICK;
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
                Block block = getFoundation(width_span, depth, x, z);
                if (block != null) {
                    this.buildFoundation(block, x, -1, z, false, 32, 4, false);
                }

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
                    placeBlock(world, block, x, height + 2, z);
                    placeBlock(world, block, x, height, z);
                    if (block == MineFantasyBlocks.REINFORCED_STONE_FRAMED) {
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
            placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_BRICKS, x, 0, -1);
            placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_BRICKS, x, 0, -2);

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
            mapStructure(-width_span, 0, depth - 1, StructureGenDSSurfaceAppendage.class);
            mapStructure(-width_span, 0, 2, StructureGenDSSurfaceAppendage.class);

            mapStructure(width_span, 0, depth - 1, StructureGenDSSurfaceAppendage.class);
            mapStructure(width_span, 0, 2, StructureGenDSSurfaceAppendage.class);
        }

        MineFantasyReborn.LOG.error("Placed Dwarven Stronghold at: " + pos);
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

    private Block getTrim(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1)) {
            if (z == (int) Math.ceil((float) depth / 2)) {
                return MineFantasyBlocks.REINFORCED_STONE_FRAMED;
            }
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            if ((x == -(radius - 1) && (z == (depth - 1) || z == 1)) || (x == (radius - 1) && (z == (depth - 1) || z == 1))) {
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

    private Block getFoundation(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            if (x == -radius || x == radius || x == 0) {
                if (z == depth || z == Math.ceil((float) depth / 2) || z == 0) {
                    return MineFantasyBlocks.REINFORCED_STONE;
                }
            }
        }
        return MineFantasyBlocks.REINFORCED_STONE_BRICKS;
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
}
