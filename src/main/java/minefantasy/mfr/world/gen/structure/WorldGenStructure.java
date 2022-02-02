package minefantasy.mfr.world.gen.structure;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.config.ConfigWorldGen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenStructure implements IWorldGenerator {
	private static int STRONGHOLD_COUNT;
	private static int CHUNK_COUNT;

	private static boolean confineToGrid(int chunkX, int chunkZ, int chunkSize) {
		return chunkX % chunkSize == 0 && chunkZ % chunkSize == 0;
	}

	private static void generateAncientForge(Random seed, int chunkX, int chunkZ, World world) {
		int x = (16 * chunkX);
		int z = (16 * chunkZ);
		WorldGenAncientForge forge = new WorldGenAncientForge();
		for (int x1 = 0; x1 < 16; x1++) {
			for (int z1 = 0; z1 < 16; z1++) {
				if (forge.generate(world, seed, new BlockPos(x + x1, 0, z + z1))) {
					return;
				}
			}
		}
	}

	private static void generateDwarvenStronghold(Random seed, int chunkX, int chunkZ, World world) {
		int x = (16 * chunkX);
		int z = (16 * chunkZ);
		WorldGenDwarvenStronghold stronghold = new WorldGenDwarvenStronghold();
		for (int x1 = 0; x1 < 16; x1++) {
			for (int z1 = 0; z1 < 16; z1++) {
				if (stronghold.generate(world, seed, new BlockPos(x + x1, 0, z + z1))) {
					++STRONGHOLD_COUNT;
					String s = "Gen: " + STRONGHOLD_COUNT + " Strongholds in " + CHUNK_COUNT + " Chunks = " + ((float) STRONGHOLD_COUNT / (float) CHUNK_COUNT * 100F) + "% cases";
					MineFantasyReforged.LOG.debug(s);
					return;
				}
			}
		}
		stronghold.setSurfaceMode(true);
		int x1 = seed.nextInt(16);
		int z1 = seed.nextInt(16);
		if (stronghold.generate(world, seed, new BlockPos(x + x1, 0, z + z1))) {
			++STRONGHOLD_COUNT;
			String s = "Gen: " + STRONGHOLD_COUNT + " Strongholds in " + CHUNK_COUNT + " Chunks = " + ((float) STRONGHOLD_COUNT / (float) CHUNK_COUNT * 100F) + "% cases";
			MineFantasyReforged.LOG.debug(s);
			return;
		}
	}

	private static void generateAncientAlter(Random seed, int chunkX, int chunkZ, World world) {
		int x = (16 * chunkX);
		int z = (16 * chunkZ);

		WorldGenAncientAltar altar = new WorldGenAncientAltar();
		for (int x1 = 0; x1 < 16; x1++) {
			if (altar.generate(world, seed, new BlockPos(x + x1, 0, z))) {
				return;
			}
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		++CHUNK_COUNT;
		if (world.provider.getDimension() == 0) {
			if (confineToGrid(chunkX, chunkZ, ConfigWorldGen.ancientAltarGrid)) {
				if (random.nextFloat() < ConfigWorldGen.ancientAltarSpawnChance) {
					generateAncientAlter(random, chunkX, chunkZ, world);
				}
			}

			if (confineToGrid(chunkX, chunkZ, ConfigWorldGen.ancientForgeGrid)) {
				if (random.nextFloat() < ConfigWorldGen.ancientForgeSpawnChance) {
					generateAncientForge(random, chunkX, chunkZ, world);
				}
			}

			if (confineToGrid(chunkX, chunkZ, ConfigWorldGen.dwarvenStrongholdGrid)) {
				if (random.nextFloat() < ConfigWorldGen.dwarvenStrongholdSpawnChance) {
					generateDwarvenStronghold(random, chunkX, chunkZ, world);
				}
			}
		}
	}
}