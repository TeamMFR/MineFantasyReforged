package minefantasy.mfr.mechanics.worldGen.structure;

import minefantasy.mfr.init.MineFantasyBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.Random;

public class StructureGenAncientForge extends StructureModuleMFR {
    public StructureGenAncientForge(World world, BlockPos pos, int d) {
        super(world, pos, d);
    }

    public StructureGenAncientForge(World world, StructureCoordinates position) {
        super(world, position);
    }

    public static int getRandomMetadata(Random RNG) {
        float f = RNG.nextFloat();

        if (f < 0.2F) {
            return 2;
        } else if (f < 0.5F) {
            return 1;
        }
        return 0;
    }

    @Override
    public void generate() {
        placeBlock(Blocks.STONEBRICK, new BlockPos(0, 0, -1));

        int width = 14;
        int depth = 16;
        // FLOOR
        for (int x = -width / 2; x <= width / 2; x++) {
            for (int z = 0; z <= depth; z++) {
                placeBlock(Blocks.STONEBRICK, new BlockPos(x, 0, z));
            }
        }

        // CEIL
        for (int x = -width / 2; x <= width / 2; x++) {
            for (int z = 0; z <= depth; z++) {
                placeBlock(Blocks.STONEBRICK, new BlockPos(x, 5, z));
            }
        }
        // WALLS
        for (int x = -width / 2; x <= width / 2; x++) {
            for (int z = 0; z <= depth; z++) {
                for (int y = 1; y <= 4; y++) {
                    placeBlock(Blocks.STONEBRICK, new BlockPos(x, y, z));
                }
            }
        }
        // INTERIOR
        for (int x = (-width / 2) + 1; x <= (width / 2) - 1; x++) {
            for (int z = 1; z <= depth - 1; z++) {
                for (int y = 1; y <= 4; y++) {
                    if (x % 4 != 0 || z % 4 != 0)
                        placeBlock(Blocks.AIR, new BlockPos(x, y, z));
                }
            }
        }

        // OBSIDIAN
        for (int x = -3; x <= 3; x++) {
            for (int z = 5; z <= 11; z++) {
                for (int y = -3; y <= 0; y++) {
                    placeBlock(y == 0 ? MineFantasyBlocks.MYTHIC_STONE_DECORATED : Blocks.OBSIDIAN, new BlockPos(x, y, z));
                }
            }
        }
        // HOLLOW
        for (int x = -2; x <= 2; x++) {
            for (int z = 6; z <= 10; z++) {
                for (int y = -2; y <= 0; y++) {
                    placeBlock(Blocks.AIR, new BlockPos(x, y, z));
                }
            }
        }
        // LAVA
        for (int x = -2; x <= 2; x++) {
            for (int z = 6; z <= 10; z++) {
                placeBlock(Blocks.LAVA, new BlockPos(x, -2, z));
            }
        }
        placeBlock(Blocks.AIR, new BlockPos(0, 2, depth / 2));
        placeBlock(Blocks.AIR, new BlockPos(0, 3, depth / 2));
        placeBlock(Blocks.AIR, new BlockPos(0, 4, depth / 2));

        placeBlock(MineFantasyBlocks.MYTHIC_STONE_FRAMED, new BlockPos(-3, 0, depth / 2));
        placeBlock(MineFantasyBlocks.MYTHIC_STONE_FRAMED, new BlockPos(+3, 0, depth / 2));
        placeBlock(MineFantasyBlocks.MYTHIC_STONE_FRAMED, new BlockPos(0, 0, depth / 2 + 3));
        placeBlock(MineFantasyBlocks.MYTHIC_STONE_FRAMED, new BlockPos(0, 0, depth / 2 - 3));

        placeBlock(MineFantasyBlocks.MYTHIC_STONE_FRAMED, new BlockPos(-3, 0, depth / 2 - 3));
        placeBlock(MineFantasyBlocks.MYTHIC_STONE_FRAMED, new BlockPos(-3, 0, depth / 2 + 3));
        placeBlock(MineFantasyBlocks.MYTHIC_STONE_FRAMED, new BlockPos(+3, 0, depth / 2 - 3));
        placeBlock(MineFantasyBlocks.MYTHIC_STONE_FRAMED, new BlockPos(+3, 0, depth / 2 + 3));

        placeBlock(Blocks.OBSIDIAN, new BlockPos(0, 0, depth / 2));
        placeBlock(Blocks.OBSIDIAN, new BlockPos(0, 0, depth / 2 - 1));
        placeBlock(Blocks.OBSIDIAN, new BlockPos(0, 0, depth / 2 - 2));

        placeBlock(MineFantasyBlocks.CRUCIBLE_MYTHIC, new BlockPos(0, 1, depth / 2));
        placeBlock(Blocks.END_PORTAL_FRAME, new BlockPos(3, 0, depth / 2 - 1));
        placeBlock(Blocks.END_PORTAL_FRAME, new BlockPos(-3, 0, depth / 2 - 1));
        placeBlock(Blocks.END_PORTAL_FRAME, new BlockPos(-1, 0, depth / 2 + 3));
        placeBlock(Blocks.END_PORTAL_FRAME, new BlockPos(-1, 0, depth / 2 - 3));

        placeBlock(Blocks.END_PORTAL_FRAME, new BlockPos(3, 0, depth / 2 + 1));
        placeBlock(Blocks.END_PORTAL_FRAME, new BlockPos(-3, 0, depth / 2 + 1));
        placeBlock(Blocks.END_PORTAL_FRAME, new BlockPos(1, 0, depth / 2 + 3));
        placeBlock(Blocks.END_PORTAL_FRAME, new BlockPos(1, 0, depth / 2 - 3));

        for (int y = 0; y < 4; y++) {
            placeBlock(Blocks.AIR, new BlockPos(0, y + 1, depth / 2 - 4));
        }
        placeBlock(Blocks.GLOWSTONE, new BlockPos(0, 5, depth / 2));
        placeBlock(Blocks.AIR, new BlockPos(0, 1, 0));
        placeBlock(Blocks.AIR, new BlockPos(0, 2, 0));

        getRandomForge(new BlockPos(0, 0, depth), direction).generate();

        getRandomForge(new BlockPos(width / 2, 0, depth / 2), rotateLeft()).generate();
        getRandomForge(new BlockPos(-width / 2, 0, depth / 2), rotateRight()).generate();

    }

    private StructureModuleMFR getRandomForge(BlockPos pos, int newDirection) {
        BlockPos coord = this.offsetPos(pos, direction);
        StructureGenAFRoom room = new StructureGenAFRoom(world, coord, newDirection);

        if (newDirection == direction) {
            room.giveTrinket();
        }
        room.setLoot(getRandomLoot());

        return room;
    }

    private ResourceLocation getRandomLoot() {
        int i = rand.nextInt(3);
        if (i == 0) {
            return rand.nextBoolean() ? LootTableList.CHESTS_STRONGHOLD_CORRIDOR : LootTableList.CHESTS_STRONGHOLD_CROSSING;
        }
        if (i == 1) {
            return rand.nextBoolean() ? LootTableList.CHESTS_DESERT_PYRAMID : LootTableList.CHESTS_JUNGLE_TEMPLE;
        }
        return LootTableList.CHESTS_SIMPLE_DUNGEON;
    }
}
