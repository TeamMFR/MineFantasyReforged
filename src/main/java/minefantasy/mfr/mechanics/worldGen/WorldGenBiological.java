package minefantasy.mfr.mechanics.worldGen;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.config.ConfigWorldGen;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public class WorldGenBiological {
    public static void generate(Random seed, int chunkX, int chunkZ, World world, int dimension) {
        boolean debug = world.getWorldInfo().getTerrainType() == WorldType.FLAT && MineFantasyReborn.isDebug();

        Biome biome = world.getBiome(new BlockPos(chunkX * 16, 0, chunkZ * 16));
        if (debug || isBiomeInConstraint(biome, ConfigWorldGen.berryMinTemp, ConfigWorldGen.berryMaxTemp,
                ConfigWorldGen.berryMinRain, ConfigWorldGen.berryMaxRain)) {
            generatePlant(seed, chunkX, chunkZ, world, BlockListMFR.BERRY_BUSH, ConfigWorldGen.berryRarity);
        }
        if (debug || isBiomeInConstraint(biome, ConfigWorldGen.yewMinTemp, ConfigWorldGen.yewMaxTemp,
                ConfigWorldGen.yewMinRain, ConfigWorldGen.yewMaxRain)) {
            generateTree(seed, chunkX, chunkZ, world, BlockListMFR.LOG_YEW, BlockListMFR.LEAVES_YEW,
                    ConfigWorldGen.yewRarity);
        }
        if (debug || isBiomeInConstraint(biome, ConfigWorldGen.ironbarkMinTemp, ConfigWorldGen.ironbarkMaxTemp,
                ConfigWorldGen.ironbarkMinRain, ConfigWorldGen.ironbarkMaxRain)) {
            generateTree(seed, chunkX, chunkZ, world, BlockListMFR.LOG_IRONBARK, BlockListMFR.LEAVES_IRONBARK,
                    ConfigWorldGen.ironbarkRarity);
        }
        if (debug || isBiomeInConstraint(biome, ConfigWorldGen.ebonyMinTemp, ConfigWorldGen.ebonyMaxTemp,
                ConfigWorldGen.ebonyMinRain, ConfigWorldGen.ebonyMaxRain)) {
            generateTree(seed, chunkX, chunkZ, world, BlockListMFR.LOG_EBONY, BlockListMFR.LEAVES_EBONY,
                    ConfigWorldGen.ebonyRarity);
        }
    }

    public static boolean isBiomeInConstraint(Biome biome, float tempMin, float tempMax, float rainMin,
                                              float rainMax) {
        if (biome != null) {
            return biome.getDefaultTemperature() >= tempMin && biome.getDefaultTemperature() < tempMax && biome.getRainfall() >= rainMin
                    && biome.getRainfall() < rainMax;
        }
        return false;
    }

    private static void generatePlant(Random seed, int chunkX, int chunkZ, World world, Block plant,
            float chance) {
        boolean doGen = world.getWorldInfo().getTerrainType() != WorldType.FLAT;
        if (doGen && seed.nextFloat() < chance) {
            for (int a = 0; a < 6; a++) {
                int j = chunkX * 16 + seed.nextInt(16);
                int k = chunkZ * 16 + seed.nextInt(16);
                BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(j, 0, k));
                (new WorldGenBush(plant, 0)).generate(world, seed, pos);
            }
        }
    }

    private static void generateTree(Random seed, int chunkX, int chunkZ, World world, Block log, Block leaves,
                                     float chance) {
        boolean doGen = world.getWorldInfo().getTerrainType() != WorldType.FLAT
                || (doGen = MineFantasyReborn.isDebug() && world.getWorldInfo().getTerrainType() == WorldType.FLAT);
        if (doGen && seed.nextFloat() < chance) {
            int j = chunkX * 16 + seed.nextInt(16);
            int k = chunkZ * 16 + seed.nextInt(16);
            BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(j,0, k));
            (new WorldGenMFTree(false, log, leaves)).generate(world, seed, pos);
        }
    }

    private static void generateOreWithNeighbour(Random seed, int chunkX, int chunkZ, World world, Block ore, Block bed, Material neighbour, int size, int frequencyMin, int frequencyMax, float rarity, int layerMin, int layerMax) {
        int frequency = MathHelper.getInt(seed, frequencyMin, frequencyMax);
        if (seed.nextFloat() < rarity) {
            for (int count = 0; count < frequency; count++) {
                BlockPos pos = new BlockPos(chunkX * 16 + seed.nextInt(16), MathHelper.getInt(seed, layerMin, layerMax), chunkZ * 16 + seed.nextInt(16) );

                if (isNeibourNear(world, pos, neighbour)) {
                    if ((new WorldGenMinableMF(ore, size, bed)).generate(world, seed, pos)) {
                    }
                }
            }
        }
    }

    private static void generateOreWithNeighbour(Random seed, int chunkX, int chunkZ, World world, Block ore, Block bed,
                                                 Block neighbour, int size, int frequencyMin, int frequencyMax, float rarity, int layerMin, int layerMax) {
        int frequency = MathHelper.getInt(seed, frequencyMin, frequencyMax);
        if (seed.nextFloat() < rarity) {
            for (int count = 0; count < frequency; count++) {
                BlockPos pos = new BlockPos(chunkX * 16 + seed.nextInt(16), MathHelper.getInt(seed, layerMin, layerMax), chunkZ * 16 + seed.nextInt(16));

                if (isNeibourNear(world, pos, neighbour)) {
                    if ((new WorldGenMinableMF(ore, size, bed)).generate(world, seed, pos)) {
                    }
                }
            }
        }
    }

    private static boolean isNeibourNear(World world, BlockPos pos , Block neighbour) {
        return world.getBlockState(pos.add(-1,0,0)) == neighbour || world.getBlockState(pos.add(1,0,0)) == neighbour
                || world.getBlockState(pos.add(0,-1,0)) == neighbour || world.getBlockState(pos.add(0,1,0)) == neighbour
                || world.getBlockState(pos.add(0,0,-1)) == neighbour || world.getBlockState(pos.add(0,0,1)) == neighbour;
    }

    private static boolean isNeibourNear(World world, BlockPos pos, Material neighbour) {
        return world.getBlockState(pos.add(-1, 0, 0)).getMaterial() == neighbour
                || world.getBlockState(pos.add(1,0,0)).getMaterial() == neighbour
                || world.getBlockState(pos.add(0,-1,0)).getMaterial() == neighbour
                || world.getBlockState(pos.add(0,1,0)).getMaterial() == neighbour
                || world.getBlockState(pos.add(0,0,-1)).getMaterial() == neighbour
                || world.getBlockState(pos.add(0,0,1)).getMaterial() == neighbour;
    }
}
