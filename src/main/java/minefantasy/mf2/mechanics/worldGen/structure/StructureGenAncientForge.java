package minefantasy.mf2.mechanics.worldGen.structure;

import minefantasy.mf2.block.list.BlockListMF;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

import java.util.Random;

public class StructureGenAncientForge extends StructureModuleMF {
    public StructureGenAncientForge(World world, int x, int y, int z, int d) {
        super(world, x, y, z, d);
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
        placeBlock(Blocks.stonebrick, getRandomMetadata(rand), 0, 0, -1);

        int width = 14;
        int depth = 16;
        // FLOOR
        for (int x = -width / 2; x <= width / 2; x++) {
            for (int z = 0; z <= depth; z++) {
                placeBlock(Blocks.stonebrick, getRandomMetadata(rand), x, 0, z);
            }
        }

        // CEIL
        for (int x = -width / 2; x <= width / 2; x++) {
            for (int z = 0; z <= depth; z++) {
                placeBlock(Blocks.stonebrick, getRandomMetadata(rand), x, 5, z);
            }
        }
        // WALLS
        for (int x = -width / 2; x <= width / 2; x++) {
            for (int z = 0; z <= depth; z++) {
                for (int y = 1; y <= 4; y++) {
                    placeBlock(Blocks.stonebrick, getRandomMetadata(rand), x, y, z);
                }
            }
        }
        // INTERIOR
        for (int x = (-width / 2) + 1; x <= (width / 2) - 1; x++) {
            for (int z = 1; z <= depth - 1; z++) {
                for (int y = 1; y <= 4; y++) {
                    if (x % 4 != 0 || z % 4 != 0)
                        placeBlock(Blocks.air, 0, x, y, z);
                }
            }
        }

        // OBSIDIAN
        for (int x = -3; x <= 3; x++) {
            for (int z = 5; z <= 11; z++) {
                for (int y = -3; y <= 0; y++) {
                    placeBlock(y == 0 ? BlockListMF.mythic_decor : Blocks.obsidian, 0, x, y, z);
                }
            }
        }
        // HOLLOW
        for (int x = -2; x <= 2; x++) {
            for (int z = 6; z <= 10; z++) {
                for (int y = -2; y <= 0; y++) {
                    placeBlock(Blocks.air, 0, x, y, z);
                }
            }
        }
        // LAVA
        for (int x = -2; x <= 2; x++) {
            for (int z = 6; z <= 10; z++) {
                placeBlock(Blocks.lava, 0, x, -2, z);
            }
        }
        placeBlock(Blocks.air, 0, 0, 2, depth / 2);
        placeBlock(Blocks.air, 0, 0, 3, depth / 2);
        placeBlock(Blocks.air, 0, 0, 4, depth / 2);

        placeBlock(BlockListMF.mythic_decor, 1, -3, 0, depth / 2);
        placeBlock(BlockListMF.mythic_decor, 1, +3, 0, depth / 2);
        placeBlock(BlockListMF.mythic_decor, 1, 0, 0, depth / 2 + 3);
        placeBlock(BlockListMF.mythic_decor, 1, 0, 0, depth / 2 - 3);

        placeBlock(BlockListMF.mythic_decor, 1, -3, 0, depth / 2 - 3);
        placeBlock(BlockListMF.mythic_decor, 1, -3, 0, depth / 2 + 3);
        placeBlock(BlockListMF.mythic_decor, 1, +3, 0, depth / 2 - 3);
        placeBlock(BlockListMF.mythic_decor, 1, +3, 0, depth / 2 + 3);

        placeBlock(Blocks.obsidian, 0, 0, 0, depth / 2);
        placeBlock(Blocks.obsidian, 0, 0, 0, depth / 2 - 1);
        placeBlock(Blocks.obsidian, 0, 0, 0, depth / 2 - 2);

        placeBlock(BlockListMF.cruciblemythic, 0, 0, 1, depth / 2);
        placeBlock(Blocks.end_portal_frame, 0, 3, 0, depth / 2 - 1);
        placeBlock(Blocks.end_portal_frame, 0, -3, 0, depth / 2 - 1);
        placeBlock(Blocks.end_portal_frame, 0, -1, 0, depth / 2 + 3);
        placeBlock(Blocks.end_portal_frame, 0, -1, 0, depth / 2 - 3);

        placeBlock(Blocks.end_portal_frame, 0, 3, 0, depth / 2 + 1);
        placeBlock(Blocks.end_portal_frame, 0, -3, 0, depth / 2 + 1);
        placeBlock(Blocks.end_portal_frame, 0, 1, 0, depth / 2 + 3);
        placeBlock(Blocks.end_portal_frame, 0, 1, 0, depth / 2 - 3);

        for (int y = 0; y < 4; y++) {
            placeBlock(Blocks.air, 0, 0, y + 1, depth / 2 - 4);
        }
        placeBlock(Blocks.glowstone, 0, 0, 5, depth / 2);
        placeBlock(Blocks.air, 0, 0, 1, 0);
        placeBlock(Blocks.air, 0, 0, 2, 0);

        getRandomForge(0, 0, depth, direction).generate();

        getRandomForge(width / 2, 0, depth / 2, rotateLeft()).generate();
        getRandomForge(-width / 2, 0, depth / 2, rotateRight()).generate();

    }

    private StructureModuleMF getRandomForge(int x, int y, int z, int newDirection) {
        int[] coord = this.offsetPos(x, y, z, direction);
        StructureGenAFRoom room = new StructureGenAFRoom(worldObj, coord[0], coord[1], coord[2], newDirection);

        if (newDirection == direction) {
            room.giveTrinket();
        }
        room.setLoot(getRandomLoot());

        return room;
    }

    private String getRandomLoot() {
        int i = rand.nextInt(3);
        if (i == 0) {
            return rand.nextBoolean() ? ChestGenHooks.STRONGHOLD_CORRIDOR : ChestGenHooks.STRONGHOLD_CROSSING;
        }
        if (i == 1) {
            return rand.nextBoolean() ? ChestGenHooks.PYRAMID_DESERT_CHEST : ChestGenHooks.PYRAMID_JUNGLE_CHEST;
        }
        return ChestGenHooks.DUNGEON_CHEST;
    }
}
