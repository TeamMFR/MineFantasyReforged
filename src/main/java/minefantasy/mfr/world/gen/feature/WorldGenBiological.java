package minefantasy.mfr.world.gen.feature;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.block.BlockBerryBush;
import minefantasy.mfr.config.ConfigWorldGen;
import minefantasy.mfr.init.MineFantasyBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenBiological implements IWorldGenerator {
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		boolean debug = world.getWorldInfo().getTerrainType() == WorldType.FLAT && MineFantasyReborn.isDebug();

		Biome biome = world.getBiome(new BlockPos(chunkX * 16, 0, chunkZ * 16));
		if (debug || isBiomeInConstraint(biome, ConfigWorldGen.berryMinTemp, ConfigWorldGen.berryMaxTemp, ConfigWorldGen.berryMinRain, ConfigWorldGen.berryMaxRain)) {
			generatePlant(MineFantasyBlocks.BERRY_BUSH.getDefaultState().withProperty(BlockBerryBush.AGE, random.nextInt(10) == 0 ? 3 : 4), world, random, 8 + chunkX * 16, 8 + chunkZ * 16, ConfigWorldGen.berryRarity, ConfigWorldGen.berryGroupSize);
		}
		if (debug || isBiomeInConstraint(biome, ConfigWorldGen.yewMinTemp, ConfigWorldGen.yewMaxTemp, ConfigWorldGen.yewMinRain, ConfigWorldGen.yewMaxRain)) {
			generateTree(random, chunkX, chunkZ, world, MineFantasyBlocks.LOG_YEW, MineFantasyBlocks.LEAVES_YEW, ConfigWorldGen.yewRarity);
		}
		if (debug || isBiomeInConstraint(biome, ConfigWorldGen.ironbarkMinTemp, ConfigWorldGen.ironbarkMaxTemp, ConfigWorldGen.ironbarkMinRain, ConfigWorldGen.ironbarkMaxRain)) {
			generateTree(random, chunkX, chunkZ, world, MineFantasyBlocks.LOG_IRONBARK, MineFantasyBlocks.LEAVES_IRONBARK, ConfigWorldGen.ironbarkRarity);
		}
		if (debug || isBiomeInConstraint(biome, ConfigWorldGen.ebonyMinTemp, ConfigWorldGen.ebonyMaxTemp, ConfigWorldGen.ebonyMinRain, ConfigWorldGen.ebonyMaxRain)) {
			generateTree(random, chunkX, chunkZ, world, MineFantasyBlocks.LOG_EBONY, MineFantasyBlocks.LEAVES_EBONY, ConfigWorldGen.ebonyRarity);
		}
	}

	public static boolean isBiomeInConstraint(Biome biome, float tempMin, float tempMax, float rainMin, float rainMax) {
		if (biome != null) {
			return biome.getDefaultTemperature() >= tempMin && biome.getDefaultTemperature() < tempMax
					&& biome.getRainfall() >= rainMin && biome.getRainfall() < rainMax;
		}
		return false;
	}

	/**
	 * Generates the specified plant randomly throughout the world.
	 *
	 * @param state          The plant block
	 * @param world          The world
	 * @param random         A instance of {@code Random} to use
	 * @param x              The x coordinate of the first block in the chunk
	 * @param z              The y coordinate of the first block in the chunk
	 * @param chancesToSpawn Number of chances to spawn a flower patch
	 * @param groupSize      The number of times to try generating a flower per flower patch spawn
	 */
	public static void generatePlant(IBlockState state, World world, Random random, int x, int z, int chancesToSpawn, int groupSize) {

		for (int i = 0; i < chancesToSpawn; i++) {

			int randPosX = x + random.nextInt(16);
			int randPosY = random.nextInt(256);
			int randPosZ = z + random.nextInt(16);

			for (int l = 0; l < groupSize; ++l) {

				int i1 = randPosX + random.nextInt(8) - random.nextInt(8);
				int j1 = randPosY + random.nextInt(4) - random.nextInt(4);
				int k1 = randPosZ + random.nextInt(8) - random.nextInt(8);

				BlockPos pos = new BlockPos(i1, j1, k1);

				if (world.isBlockLoaded(pos) && world.isAirBlock(pos) && (!world.provider.isNether() || j1 < 127) && state.getBlock().canPlaceBlockOnSide(world, pos, EnumFacing.UP)) {

					world.setBlockState(pos, state, 2);
				}
			}
		}
	}

	private static void generateTree(Random random, int chunkX, int chunkZ, World world, Block log, Block leaves, float chance) {
		boolean doGen = world.getWorldInfo().getTerrainType() != WorldType.FLAT || MineFantasyReborn.isDebug() && world.getWorldInfo().getTerrainType() == WorldType.FLAT;
		if (doGen && random.nextFloat() < chance) {
			int j = (chunkX * 16) + random.nextInt(8);
			int k = (chunkZ * 16) + random.nextInt(8);
			BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(j, 0, k));
			(new WorldGenMFTree(false, log, leaves)).generate(world, random, pos);
		}
	}

}
