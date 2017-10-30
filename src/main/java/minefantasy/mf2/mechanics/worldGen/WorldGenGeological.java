package minefantasy.mf2.mechanics.worldGen;

import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.config.ConfigWorldGen;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class WorldGenGeological {
    public static void generate(Random seed, int chunkX, int chunkZ, World world, int dimension) {
        generateOre(seed, chunkX, chunkZ, world, BlockListMF.oreCopper, Blocks.stone, ConfigWorldGen.copperSize,
                ConfigWorldGen.copperFrequencyMin, ConfigWorldGen.copperFrequencyMax, ConfigWorldGen.copperRarity,
                ConfigWorldGen.copperLayerMin, ConfigWorldGen.copperLayerMax);
        generateOre(seed, chunkX, chunkZ, world, BlockListMF.oreTin, Blocks.stone, ConfigWorldGen.tinSize,
                ConfigWorldGen.tinFrequencyMin, ConfigWorldGen.tinFrequencyMax, ConfigWorldGen.tinRarity,
                ConfigWorldGen.tinLayerMin, ConfigWorldGen.tinLayerMax);
        generateOre(seed, chunkX, chunkZ, world, BlockListMF.oreSilver, Blocks.stone, ConfigWorldGen.silverSize,
                ConfigWorldGen.silverFrequencyMin, ConfigWorldGen.silverFrequencyMax, ConfigWorldGen.silverRarity,
                ConfigWorldGen.silverLayerMin, ConfigWorldGen.silverLayerMax);
        generateOreWithNeighbour(seed, chunkX, chunkZ, world, BlockListMF.oreMythic, Blocks.stone, Blocks.bedrock,
                ConfigWorldGen.mythicSize, ConfigWorldGen.mythicFrequencyMin, ConfigWorldGen.mythicFrequencyMax,
                ConfigWorldGen.mythicRarity, ConfigWorldGen.mythicLayerMin, ConfigWorldGen.mythicLayerMax);

        generateOre(seed, chunkX, chunkZ, world, BlockListMF.oreKaolinite, Blocks.stone, ConfigWorldGen.kaoliniteSize,
                ConfigWorldGen.kaoliniteFrequencyMin, ConfigWorldGen.kaoliniteFrequencyMax,
                ConfigWorldGen.kaoliniteRarity, ConfigWorldGen.kaoliniteLayerMin, ConfigWorldGen.kaoliniteLayerMax);
        generateOre(seed, chunkX, chunkZ, world, BlockListMF.oreClay, Blocks.dirt, ConfigWorldGen.claySize,
                ConfigWorldGen.clayFrequencyMin, ConfigWorldGen.clayFrequencyMax, ConfigWorldGen.clayRarity,
                ConfigWorldGen.clayLayerMin, ConfigWorldGen.clayLayerMax);
        generateOreWithNeighbour(seed, chunkX, chunkZ, world, BlockListMF.oreNitre, Blocks.stone, Blocks.air,
                ConfigWorldGen.nitreSize, ConfigWorldGen.nitreFrequencyMin, ConfigWorldGen.nitreFrequencyMax,
                ConfigWorldGen.nitreRarity, ConfigWorldGen.nitreLayerMin, ConfigWorldGen.nitreLayerMax);
        generateOre(seed, chunkX, chunkZ, world, BlockListMF.oreSulfur, Blocks.stone, ConfigWorldGen.sulfurSize,
                ConfigWorldGen.sulfurFrequencyMin, ConfigWorldGen.sulfurFrequencyMax, ConfigWorldGen.sulfurRarity,
                ConfigWorldGen.sulfurLayerMin, ConfigWorldGen.sulfurLayerMax);
        generateOre(seed, chunkX, chunkZ, world, BlockListMF.oreBorax, Blocks.stone, ConfigWorldGen.boraxSize,
                ConfigWorldGen.boraxFrequencyMin, ConfigWorldGen.boraxFrequencyMax, ConfigWorldGen.boraxRarity,
                ConfigWorldGen.boraxLayerMin, ConfigWorldGen.boraxLayerMax);
        generateOre(seed, chunkX, chunkZ, world, BlockListMF.oreTungsten, Blocks.stone, ConfigWorldGen.wolframiteSize,
                ConfigWorldGen.wolframiteFrequencyMin, ConfigWorldGen.wolframiteFrequencyMax,
                ConfigWorldGen.wolframiteRarity, ConfigWorldGen.wolframiteLayerMin, ConfigWorldGen.wolframiteLayerMax);
        generateOre(seed, chunkX, chunkZ, world, BlockListMF.oreCoalRich, Blocks.stone, ConfigWorldGen.coalSize,
                ConfigWorldGen.coalFrequencyMin, ConfigWorldGen.coalFrequencyMax, ConfigWorldGen.coalRarity,
                ConfigWorldGen.coalLayerMin, ConfigWorldGen.coalLayerMax);

        generateOre(seed, chunkX, chunkZ, world, BlockListMF.limestone, Blocks.stone, ConfigWorldGen.limestoneSize,
                ConfigWorldGen.limestoneFrequencyMin, ConfigWorldGen.limestoneFrequencyMax,
                ConfigWorldGen.limestoneRarity, ConfigWorldGen.limestoneLayerMin, ConfigWorldGen.limestoneLayerMax);
    }

    /**
     * Not used yet, allows a vein consisting of two block types
     */
    private static void generateDuelOre(Random seed, int chunkX, int chunkZ, World world, Block ore, Block bed,
                                        int size, int frequencyMin, int frequencyMax, float rarity, int layerMin, int layerMax, Block special,
                                        float chance) {
        int frequency = MathHelper.getRandomIntegerInRange(seed, frequencyMin, frequencyMax);
        if (seed.nextFloat() < rarity) {
            for (int count = 0; count < frequency; count++) {
                int x = chunkX * 16 + seed.nextInt(16);
                int y = MathHelper.getRandomIntegerInRange(seed, layerMin, layerMax);
                int z = chunkZ * 16 + seed.nextInt(16);
                (new WorldGenDuelMinable(ore, size, special, chance)).generate(world, seed, x, y, z);
            }
        }
    }

    private static void generateOre(Random seed, int chunkX, int chunkZ, World world, Block ore, Block bed, int size,
                                    int frequencyMin, int frequencyMax, float rarity, int layerMin, int layerMax) {
        int frequency = MathHelper.getRandomIntegerInRange(seed, frequencyMin, frequencyMax);
        if (seed.nextFloat() < rarity) {
            for (int count = 0; count < frequency; count++) {
                int x = chunkX * 16 + seed.nextInt(16);
                int y = MathHelper.getRandomIntegerInRange(seed, layerMin, layerMax);
                int z = chunkZ * 16 + seed.nextInt(16);
                (new WorldGenMinable(ore, size, bed)).generate(world, seed, x, y, z);
            }
        }
    }

    private static void generateOreWithNeighbour(Random seed, int chunkX, int chunkZ, World world, Block ore, Block bed,
                                                 Material neighbour, int size, int frequencyMin, int frequencyMax, float rarity, int layerMin,
                                                 int layerMax) {
        int frequency = MathHelper.getRandomIntegerInRange(seed, frequencyMin, frequencyMax);
        if (seed.nextFloat() < rarity) {
            for (int count = 0; count < frequency; count++) {
                int x = chunkX * 16 + seed.nextInt(16);
                int y = MathHelper.getRandomIntegerInRange(seed, layerMin, layerMax);
                int z = chunkZ * 16 + seed.nextInt(16);

                if (isNeibourNear(world, x, y, z, neighbour)) {
                    if ((new WorldGenMinableMF(ore, size, bed)).generate(world, seed, x, y, z)) {
                    }
                }
            }
        }
    }

    private static void generateOreWithNeighbour2(Random seed, int chunkX, int chunkZ, World world, Block basic,
                                                  float chance, Block special, Block bed, Block neighbour, int size, int frequencyMin, int frequencyMax,
                                                  float rarity, int layerMin, int layerMax) {
        int frequency = MathHelper.getRandomIntegerInRange(seed, frequencyMin, frequencyMax);
        if (seed.nextFloat() < rarity) {
            for (int count = 0; count < frequency; count++) {
                int x = chunkX * 16 + seed.nextInt(16);
                int y = MathHelper.getRandomIntegerInRange(seed, layerMin, layerMax);
                int z = chunkZ * 16 + seed.nextInt(16);

                if (isNeibourNear(world, x, y, z, neighbour)) {
                    Block ore = (seed.nextFloat() <= chance ? special : basic);
                    if ((new WorldGenMinableMF(ore, size, bed)).generate(world, seed, x, y, z)) {
                    }
                }
            }
        }
    }

    private static void generateOreWithNeighbour(Random seed, int chunkX, int chunkZ, World world, Block ore, Block bed,
                                                 Block neighbour, int size, int frequencyMin, int frequencyMax, float rarity, int layerMin, int layerMax) {
        int frequency = MathHelper.getRandomIntegerInRange(seed, frequencyMin, frequencyMax);
        if (seed.nextFloat() < rarity) {
            for (int count = 0; count < frequency; count++) {
                int x = chunkX * 16 + seed.nextInt(16);
                int y = MathHelper.getRandomIntegerInRange(seed, layerMin, layerMax);
                int z = chunkZ * 16 + seed.nextInt(16);

                if (isNeibourNear(world, x, y, z, neighbour)) {
                    if ((new WorldGenMinableMF(ore, size, bed)).generate(world, seed, x, y, z)) {
                    }
                }
            }
        }
    }

    private static boolean isNeibourNear(World world, int x, int y, int z, Block neighbour) {
        return world.getBlock(x - 1, y, z) == neighbour || world.getBlock(+1, y, z) == neighbour
                || world.getBlock(x, y - 1, z) == neighbour || world.getBlock(x, y + 1, z) == neighbour
                || world.getBlock(x, y, z - 1) == neighbour || world.getBlock(x, y, z + 1) == neighbour;
    }

    private static boolean isNeibourNear(World world, int x, int y, int z, Material neighbour) {
        return world.getBlock(x - 1, y, z).getMaterial() == neighbour
                || world.getBlock(+1, y, z).getMaterial() == neighbour
                || world.getBlock(x, y - 1, z).getMaterial() == neighbour
                || world.getBlock(x, y + 1, z).getMaterial() == neighbour
                || world.getBlock(x, y, z - 1).getMaterial() == neighbour
                || world.getBlock(x, y, z + 1).getMaterial() == neighbour;
    }
}
