package minefantasy.mf2.mechanics.worldGen;

import minefantasy.mf2.config.ConfigWorldGen;
import minefantasy.mf2.mechanics.worldGen.structure.WorldGenAncientAlter;
import minefantasy.mf2.mechanics.worldGen.structure.WorldGenAncientForge;
import minefantasy.mf2.mechanics.worldGen.structure.dwarven.WorldGenDwarvenStronghold;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenStructures {
    private static int strongholdCount, chunkCount;

    public static void generate(Random seed, int chunkX, int chunkZ, World world, int dimension) {
        ++chunkCount;
        if (dimension == 0) {
            if (seed.nextFloat() < ConfigWorldGen.MFChance) {
                generateAncientForge(seed, chunkX, chunkZ, world);
            }
            if (seed.nextFloat() < ConfigWorldGen.MAChance) {
                generateAncientAlter(seed, chunkX, chunkZ, world);
            }
            if (confineToGrid(chunkX, chunkZ, ConfigWorldGen.DSGrid)) {
                if (seed.nextFloat() < ConfigWorldGen.DSChance) {
                    generateDwarvenStronghold(seed, chunkX, chunkZ, world);
                } else {
                    // placeGridBeacon(world, chunkX, chunkZ);
                }
            }
        }
    }

    /**
     * Used in testing to determine the potential areas for dwarven entries
     */
    private static void placeGridBeacon(World world, int chunkX, int chunkZ) {
        int x = (16 * chunkX);
        int z = (16 * chunkZ);
        int y = world.getTopSolidOrLiquidBlock(x, z) + 1;
        for (int x1 = 0; x1 < 3; x1++) {
            for (int z1 = 0; z1 < 3; z1++) {
                world.setBlock(x + x1, y, z + z1, (x == 0 && z == 0) ? Blocks.gold_block : Blocks.iron_block);
            }
        }
        world.setBlock(x + 1, y + 1, z + 1, Blocks.beacon);
    }

    private static boolean confineToGrid(int chunkX, int chunkZ, int chunkSize) {
        return chunkX % chunkSize == 0 && chunkZ % chunkSize == 0;
    }

    private static void generateAncientForge(Random seed, int chunkX, int chunkZ, World world) {
        int x = (16 * chunkX);
        int z = (16 * chunkZ);
        WorldGenAncientForge forge = new WorldGenAncientForge();
        for (int x1 = 0; x1 < 16; x1++) {
            for (int z1 = 0; z1 < 16; z1++) {
                if (forge.generate(world, seed, x + x1, 0, z + z1)) {
                    MFLogUtil.logDebug("Placed Ancient Forge at " + x + x1 + " " + z + z1);
                    return;
                }
            }
        }
        forge = null;
    }

    private static void generateDwarvenStronghold(Random seed, int chunkX, int chunkZ, World world) {
        int x = (16 * chunkX);
        int z = (16 * chunkZ);
        WorldGenDwarvenStronghold stronghold = new WorldGenDwarvenStronghold();
        for (int x1 = 0; x1 < 16; x1++) {
            for (int z1 = 0; z1 < 16; z1++) {
                if (stronghold.generate(world, seed, x + x1, 0, z + z1)) {
                    ++strongholdCount;
                    MFLogUtil.logDebug("Placed Dwarven Stronghold in wall at " + x + x1 + " " + z + z1);
                    String s = "Gen: " + strongholdCount + " Strongholds in " + chunkCount + " Chunks = "
                            + ((float) strongholdCount / (float) chunkCount * 100F) + "% cases";
                    System.out.println(s);
                    return;
                }
            }
        }
        stronghold.setSurfaceMode(true);
        int x1 = seed.nextInt(16);
        int z1 = seed.nextInt(16);
        if (stronghold.generate(world, seed, x + x1, 0, z + z1)) {
            ++strongholdCount;
            MFLogUtil.logDebug("Placed Dwarven Stronghold on ground at " + x + x1 + " " + z + z1);
            String s = "Gen: " + strongholdCount + " Strongholds in " + chunkCount + " Chunks = "
                    + ((float) strongholdCount / (float) chunkCount * 100F) + "% cases";
            System.out.println(s);
            return;
        }
        stronghold = null;
    }

    private static void generateAncientAlter(Random seed, int chunkX, int chunkZ, World world) {
        int spawnZ = world.getSpawnPoint().posZ;
        int spawnChunkZ = (int) (spawnZ / 16F);

        if (chunkZ == spawnChunkZ) {
            int x = (16 * chunkX);
            int z = (16 * chunkZ);

            WorldGenAncientAlter ruin = new WorldGenAncientAlter();
            for (int x1 = 0; x1 < 16; x1++) {
                // for(int z1 = 0; z1 < 16; z1++)
                {
                    if (ruin.generate(world, seed, x + x1, 0, spawnZ)) {
                        MFLogUtil.logDebug("Placed Ancient Alter at " + x + x1 + " " + spawnZ);
                        return;
                    }
                }
            }
            ruin = null;
        }
    }
}
