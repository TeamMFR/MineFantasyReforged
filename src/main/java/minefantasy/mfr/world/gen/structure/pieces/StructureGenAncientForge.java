package minefantasy.mfr.world.gen.structure.pieces;

import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.Random;

public class StructureGenAncientForge extends StructureModuleMFR {
    public StructureGenAncientForge(World world, BlockPos pos, EnumFacing facing, Random random) {
        super(world, pos, facing, random);
    }

    @Override
    public void generate() {
        placeBlockWithState(world, getRandomVariantBlock(Blocks.STONEBRICK, random), 0, 0, -1);

        int width = 14;
        int depth = 16;
        int height = 6;
        // FLOOR
        for (int x = -width / 2; x <= width / 2; x++) {
            for (int z = 0; z <= depth; z++) {
                placeBlockWithState(world, getRandomVariantBlock(Blocks.STONEBRICK, random), x, 0, z);
            }
        }

        // CEIL
        for (int x = -width / 2; x <= width / 2; x++) {
            for (int z = 0; z <= depth; z++) {
                placeBlockWithState(world, getRandomVariantBlock(Blocks.STONEBRICK, random), x, height, z);
            }
        }
        // WALLS
        for (int x = -width / 2; x <= width / 2; x++) {
            for (int z = 0; z <= depth; z++) {
                for (int y = 1; y <= height; y++) {
                    placeBlockWithState(world, getRandomVariantBlock(Blocks.STONEBRICK, random), x, y, z);
                }
            }
        }
        // INTERIOR
        for (int x = (-width / 2) + 1; x <= (width / 2) - 1; x++) {
            for (int z = 1; z <= depth - 1; z++) {
                for (int y = 1; y <= height - 1; y++) {
                    if (x % 4 != 0 || z % 4 != 0){
                        placeBlock(world, Blocks.AIR, x, y, z);
                    }
                }
            }
        }

        // OBSIDIAN
        for (int x = -3; x <= 3; x++) {
            for (int z = 5; z <= 11; z++) {
                for (int y = -3; y <= 0; y++) {
                    placeBlock(world, y == 0 ? MineFantasyBlocks.MYTHIC_STONE_DECORATED : Blocks.OBSIDIAN, x, y, z);
                }
            }
        }
        // HOLLOW
        for (int x = -2; x <= 2; x++) {
            for (int z = 6; z <= 10; z++) {
                for (int y = -2; y <= 0; y++) {
                    placeBlock(world, Blocks.AIR, x, y, z);
                }
            }
        }
        // LAVA
        for (int x = -2; x <= 2; x++) {
            for (int z = 6; z <= 10; z++) {
                placeBlock(world, Blocks.LAVA, x, -1, z);
                placeBlock(world, Blocks.LAVA, x, -2, z);
            }
        }
        placeBlock(world, Blocks.AIR, 0, 2, depth / 2);
        placeBlock(world, Blocks.AIR, 0, 3, depth / 2);
        placeBlock(world, Blocks.AIR, 0, 4, depth / 2);

        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_FRAMED, -3, 0, depth / 2);
        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_FRAMED, +3, 0, depth / 2);
        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_FRAMED, 0, 0, depth / 2 + 3);
        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_FRAMED, 0, 0, depth / 2 - 3);

        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_FRAMED, -3, 0, depth / 2 - 3);
        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_FRAMED, -3, 0, depth / 2 + 3);
        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_FRAMED, +3, 0, depth / 2 - 3);
        placeBlock(world, MineFantasyBlocks.MYTHIC_STONE_FRAMED, +3, 0, depth / 2 + 3);

        placeBlock(world, Blocks.OBSIDIAN, 0, 0, depth / 2);
        placeBlock(world, Blocks.OBSIDIAN, 0, 0, depth / 2 - 1);
        placeBlock(world, Blocks.OBSIDIAN, 0, 0, depth / 2 - 2);

        placeBlock(world, MineFantasyBlocks.CRUCIBLE_MYTHIC, 0, 1, depth / 2);
        placeBlock(world, Blocks.END_PORTAL_FRAME, 3, 0, depth / 2 - 1);
        placeBlock(world, Blocks.END_PORTAL_FRAME, -3, 0, depth / 2 - 1);
        placeBlock(world, Blocks.END_PORTAL_FRAME, -1, 0, depth / 2 + 3);
        placeBlock(world, Blocks.END_PORTAL_FRAME, -1, 0, depth / 2 - 3);

        placeBlock(world, Blocks.END_PORTAL_FRAME, 3, 0, depth / 2 + 1);
        placeBlock(world, Blocks.END_PORTAL_FRAME, -3, 0, depth / 2 + 1);
        placeBlock(world, Blocks.END_PORTAL_FRAME, 1, 0, depth / 2 + 3);
        placeBlock(world, Blocks.END_PORTAL_FRAME, 1, 0, depth / 2 - 3);

        for (int y = 1; y < height; y++) {
            placeBlock(world, Blocks.AIR, 0, y, depth / 2 - 4);
        }
        placeBlock(world, Blocks.GLOWSTONE, 0, height, depth / 2);
        placeBlock(world, Blocks.AIR, 0, height - 1, depth / 2);

        //Doorway
        for (int y = 1; y <= 4; y++){
            placeBlock(world, Blocks.AIR, 0, y,0);
        }

        placeRandomForge(world, 0, depth, facing, random).generate();

        placeRandomForge(world, width / 2, depth / 2, facing.rotateYCCW(), random).generate();
        placeRandomForge(world, -width / 2, depth / 2, facing.rotateY(), random).generate();

    }

    private StructureModuleMFR placeRandomForge(World world, int x, int z, EnumFacing newFacing, Random random) {
        StructureGenAncientForgeRoom room = new StructureGenAncientForgeRoom(world, offsetPos(x, 0, z, facing), newFacing, random);

        if (newFacing == facing) {
            room.giveTrinket();
        }
        room.setLoot(getRandomLoot(random));

        return room;
    }

    private ResourceLocation getRandomLoot(Random random) {
        int i = random.nextInt(3);
        if (i == 0) {
            return random.nextBoolean() ? LootTableList.CHESTS_STRONGHOLD_CORRIDOR : LootTableList.CHESTS_STRONGHOLD_CROSSING;
        }
        if (i == 1) {
            return random.nextBoolean() ? LootTableList.CHESTS_DESERT_PYRAMID : LootTableList.CHESTS_JUNGLE_TEMPLE;
        }
        return LootTableList.CHESTS_SIMPLE_DUNGEON;
    }
}
