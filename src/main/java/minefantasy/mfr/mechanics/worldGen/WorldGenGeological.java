package minefantasy.mfr.mechanics.worldGen;

import com.google.common.base.Predicate;
import minefantasy.mfr.config.ConfigWorldGen;
import minefantasy.mfr.init.MineFantasyBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class WorldGenGeological {
	public static void generate(Random seed, int chunkX, int chunkZ, World world, int dimension) {
		generateOre(seed, chunkX, chunkZ, world, MineFantasyBlocks.COPPER_ORE, BlockMatcher.forBlock(Blocks.STONE),
				ConfigWorldGen.copperSize, ConfigWorldGen.copperFrequencyMin, ConfigWorldGen.copperFrequencyMax,
				ConfigWorldGen.copperRarity, ConfigWorldGen.copperLayerMin, ConfigWorldGen.copperLayerMax);
		generateOre(seed, chunkX, chunkZ, world, MineFantasyBlocks.TIN_ORE, BlockMatcher.forBlock(Blocks.STONE),
				ConfigWorldGen.tinSize, ConfigWorldGen.tinFrequencyMin, ConfigWorldGen.tinFrequencyMax,
				ConfigWorldGen.tinRarity, ConfigWorldGen.tinLayerMin, ConfigWorldGen.tinLayerMax);
		generateOre(seed, chunkX, chunkZ, world, MineFantasyBlocks.SILVER_ORE, BlockMatcher.forBlock(Blocks.STONE),
				ConfigWorldGen.silverSize, ConfigWorldGen.silverFrequencyMin, ConfigWorldGen.silverFrequencyMax,
				ConfigWorldGen.silverRarity, ConfigWorldGen.silverLayerMin, ConfigWorldGen.silverLayerMax);
		generateOreWithNeighbour(seed, chunkX, chunkZ, world, MineFantasyBlocks.MYTHIC_ORE, BlockMatcher.forBlock(Blocks.STONE), Blocks.BEDROCK,
				ConfigWorldGen.mythicSize, ConfigWorldGen.mythicFrequencyMin, ConfigWorldGen.mythicFrequencyMax,
				ConfigWorldGen.mythicRarity, ConfigWorldGen.mythicLayerMin, ConfigWorldGen.mythicLayerMax);

		generateOre(seed, chunkX, chunkZ, world, MineFantasyBlocks.KAOLINITE_ORE, BlockMatcher.forBlock(Blocks.STONE),
				ConfigWorldGen.kaoliniteSize, ConfigWorldGen.kaoliniteFrequencyMin,
				ConfigWorldGen.kaoliniteFrequencyMax, ConfigWorldGen.kaoliniteRarity, ConfigWorldGen.kaoliniteLayerMin,
				ConfigWorldGen.kaoliniteLayerMax);
		generateOre(seed, chunkX, chunkZ, world, MineFantasyBlocks.CLAY_ORE, BlockMatcher.forBlock(Blocks.DIRT),
				ConfigWorldGen.claySize, ConfigWorldGen.clayFrequencyMin, ConfigWorldGen.clayFrequencyMax,
				ConfigWorldGen.clayRarity, ConfigWorldGen.clayLayerMin, ConfigWorldGen.clayLayerMax);
		generateOreWithNeighbour(seed, chunkX, chunkZ, world, MineFantasyBlocks.NITRE_ORE, BlockMatcher.forBlock(Blocks.STONE), Blocks.AIR,
				ConfigWorldGen.nitreSize, ConfigWorldGen.nitreFrequencyMin, ConfigWorldGen.nitreFrequencyMax,
				ConfigWorldGen.nitreRarity, ConfigWorldGen.nitreLayerMin, ConfigWorldGen.nitreLayerMax);
		generateOre(seed, chunkX, chunkZ, world, MineFantasyBlocks.SULFUR_ORE, BlockMatcher.forBlock(Blocks.STONE),
				ConfigWorldGen.sulfurSize, ConfigWorldGen.sulfurFrequencyMin, ConfigWorldGen.sulfurFrequencyMax,
				ConfigWorldGen.sulfurRarity, ConfigWorldGen.sulfurLayerMin, ConfigWorldGen.sulfurLayerMax);
		generateOre(seed, chunkX, chunkZ, world, MineFantasyBlocks.BORAX_ORE, BlockMatcher.forBlock(Blocks.STONE),
				ConfigWorldGen.boraxSize, ConfigWorldGen.boraxFrequencyMin, ConfigWorldGen.boraxFrequencyMax,
				ConfigWorldGen.boraxRarity, ConfigWorldGen.boraxLayerMin, ConfigWorldGen.boraxLayerMax);
		generateOre(seed, chunkX, chunkZ, world, MineFantasyBlocks.TUNGSTEN_ORE, BlockMatcher.forBlock(Blocks.STONE),
				ConfigWorldGen.wolframiteSize, ConfigWorldGen.wolframiteFrequencyMin,
				ConfigWorldGen.wolframiteFrequencyMax, ConfigWorldGen.wolframiteRarity,
				ConfigWorldGen.wolframiteLayerMin, ConfigWorldGen.wolframiteLayerMax);
		generateOre(seed, chunkX, chunkZ, world, MineFantasyBlocks.COAL_RICH_ORE, BlockMatcher.forBlock(Blocks.STONE),
				ConfigWorldGen.coalSize, ConfigWorldGen.coalFrequencyMin, ConfigWorldGen.coalFrequencyMax,
				ConfigWorldGen.coalRarity, ConfigWorldGen.coalLayerMin, ConfigWorldGen.coalLayerMax);

		generateOre(seed, chunkX, chunkZ, world, MineFantasyBlocks.LIMESTONE, BlockMatcher.forBlock(Blocks.STONE),
				ConfigWorldGen.limestoneSize, ConfigWorldGen.limestoneFrequencyMin,
				ConfigWorldGen.limestoneFrequencyMax, ConfigWorldGen.limestoneRarity, ConfigWorldGen.limestoneLayerMin,
				ConfigWorldGen.limestoneLayerMax);
	}

	/**
	 * Not used yet, allows a vein consisting of two block types
	 */
	private static void generateDuelOre(Random seed, int chunkX, int chunkZ, World world, Block ore, Block bed,
			int size, int frequencyMin, int frequencyMax, float rarity, int layerMin, int layerMax, Block special,
			float chance) {
		int frequency = MathHelper.getInt(seed, frequencyMin, frequencyMax);
		if (seed.nextFloat() < rarity) {
			for (int count = 0; count < frequency; count++) {
				BlockPos pos = new BlockPos(chunkX * 16 + seed.nextInt(16), MathHelper.getInt(seed, layerMin, layerMax),
						chunkZ * 16 + seed.nextInt(16));
				(new WorldGenDuelMinable(ore, size, special, chance)).generate(world, seed, pos);
			}
		}
	}

	private static void generateOre(Random seed, int chunkX, int chunkZ, World world, Block ore,
			Predicate<IBlockState> bed, int size, int frequencyMin, int frequencyMax, float rarity, int layerMin,
			int layerMax) {
		int frequency = MathHelper.getInt(seed, frequencyMin, frequencyMax);
		if (seed.nextFloat() < rarity) {
			for (int count = 0; count < frequency; count++) {
				BlockPos pos = new BlockPos(chunkX * 16 + seed.nextInt(16), MathHelper.getInt(seed, layerMin, layerMax),
						chunkZ * 16 + seed.nextInt(16));
				(new WorldGenMinable(ore.getDefaultState(), size, bed)).generate(world, seed, pos);
			}
		}
	}

	private static void generateOreWithNeighbour(Random seed, int chunkX, int chunkZ, World world, Block ore, Predicate<IBlockState> bed,
			Material neighbour, int size, int frequencyMin, int frequencyMax, float rarity, int layerMin,
			int layerMax) {
		int frequency = MathHelper.getInt(seed, frequencyMin, frequencyMax);
		if (seed.nextFloat() < rarity) {
			for (int count = 0; count < frequency; count++) {
				BlockPos pos = new BlockPos(chunkX * 16 + seed.nextInt(16), MathHelper.getInt(seed, layerMin, layerMax),
						chunkZ * 16 + seed.nextInt(16));

				if (isNeibourNear(world, pos, neighbour)) {
					if ((new WorldGenMinableMF(ore, size, bed)).generate(world, seed, pos)) {
					}
				}
			}
		}
	}

	private static void generateOreWithNeighbour2(Random seed, int chunkX, int chunkZ, World world, Block basic,
			float chance, Block special, Predicate<IBlockState> bed, Block neighbour, int size, int frequencyMin, int frequencyMax,
			float rarity, int layerMin, int layerMax) {
		int frequency = MathHelper.getInt(seed, frequencyMin, frequencyMax);
		if (seed.nextFloat() < rarity) {
			for (int count = 0; count < frequency; count++) {
				BlockPos pos = new BlockPos(chunkX * 16 + seed.nextInt(16), MathHelper.getInt(seed, layerMin, layerMax),
						chunkZ * 16 + seed.nextInt(16));

				if (isNeibourNear(world, pos, neighbour)) {
					Block ore = (seed.nextFloat() <= chance ? special : basic);
					if ((new WorldGenMinableMF(ore, size, bed)).generate(world, seed, pos)) {
					}
				}
			}
		}
	}

	private static void generateOreWithNeighbour(Random seed, int chunkX, int chunkZ, World world, Block ore, Predicate<IBlockState> bed,
			Block neighbour, int size, int frequencyMin, int frequencyMax, float rarity, int layerMin, int layerMax) {
		int frequency = MathHelper.getInt(seed, frequencyMin, frequencyMax);
		if (seed.nextFloat() < rarity) {
			for (int count = 0; count < frequency; count++) {
				BlockPos pos = new BlockPos(chunkX * 16 + seed.nextInt(16), MathHelper.getInt(seed, layerMin, layerMax),
						chunkZ * 16 + seed.nextInt(16));

				if (isNeibourNear(world, pos, neighbour)) {
					if ((new WorldGenMinableMF(ore, size, bed)).generate(world, seed, pos)) {
					}
				}
			}
		}
	}

	private static boolean isNeibourNear(World world, BlockPos pos, Block neighbour) {
		return world.getBlockState(pos.add(-1, 0, 0)).getBlock() == neighbour
				|| world.getBlockState(pos.add(1, 0, 0)).getBlock() == neighbour
				|| world.getBlockState(pos.add(0, -1, 0)).getBlock() == neighbour
				|| world.getBlockState(pos.add(0, 1, 0)).getBlock() == neighbour
				|| world.getBlockState(pos.add(0, 0, -1)).getBlock() == neighbour
				|| world.getBlockState(pos.add(0, 0, 1)).getBlock() == neighbour;
	}

	private static boolean isNeibourNear(World world, BlockPos pos, Material neighbour) {
		return world.getBlockState(pos.add(-1, 0, 0)).getMaterial() == neighbour
				|| world.getBlockState(pos.add(1, 0, 0)).getMaterial() == neighbour
				|| world.getBlockState(pos.add(0, -1, 0)).getMaterial() == neighbour
				|| world.getBlockState(pos.add(0, 1, 0)).getMaterial() == neighbour
				|| world.getBlockState(pos.add(0, 0, -1)).getMaterial() == neighbour
				|| world.getBlockState(pos.add(0, 0, 1)).getMaterial() == neighbour;
	}
}
