package minefantasy.mfr.world.gen.feature;

import minefantasy.mfr.config.ConfigWorldGen;
import minefantasy.mfr.init.MineFantasyBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenGeological implements IWorldGenerator {

	@Override
	public void generate(Random random, int blockXPos, int blockZPos, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		//COPPER ORE
		generateOreInStone(MineFantasyBlocks.COPPER_ORE.getDefaultState(), world, random, blockXPos * 16, blockZPos * 16,
				ConfigWorldGen.copperSize, MathHelper.getInt(random, ConfigWorldGen.copperFrequencyMin, ConfigWorldGen.copperFrequencyMax),
				ConfigWorldGen.copperLayerMin, ConfigWorldGen.copperLayerMax, ConfigWorldGen.copperRarity);
		//TIN ORE
		generateOreInStone(MineFantasyBlocks.TIN_ORE.getDefaultState(), world, random, blockXPos * 16, blockZPos * 16,
				ConfigWorldGen.tinSize, MathHelper.getInt(random, ConfigWorldGen.tinFrequencyMin, ConfigWorldGen.tinFrequencyMax),
				ConfigWorldGen.tinLayerMin, ConfigWorldGen.tinLayerMax, ConfigWorldGen.tinRarity);
		//SILVER ORE
		generateOreInStone(MineFantasyBlocks.SILVER_ORE.getDefaultState(), world, random, blockXPos * 16, blockZPos * 16,
				ConfigWorldGen.silverSize, MathHelper.getInt(random, ConfigWorldGen.silverFrequencyMin, ConfigWorldGen.silverFrequencyMax),
				ConfigWorldGen.silverLayerMin, ConfigWorldGen.silverLayerMax, ConfigWorldGen.silverRarity);
		//MYTHIC ORE
		generateOreInStone(MineFantasyBlocks.MYTHIC_ORE.getDefaultState(), world, random, blockXPos * 16, blockZPos * 16,
				ConfigWorldGen.mythicSize, MathHelper.getInt(random, ConfigWorldGen.mythicFrequencyMin, ConfigWorldGen.mythicFrequencyMax),
				ConfigWorldGen.mythicLayerMin, ConfigWorldGen.mythicLayerMax, ConfigWorldGen.mythicRarity);
		//KAOLINITE ORE
		generateOreInStone(MineFantasyBlocks.KAOLINITE_ORE.getDefaultState(), world, random, blockXPos * 16, blockZPos * 16,
				ConfigWorldGen.kaoliniteSize, MathHelper.getInt(random, ConfigWorldGen.kaoliniteFrequencyMin, ConfigWorldGen.kaoliniteFrequencyMax),
				ConfigWorldGen.kaoliniteLayerMin, ConfigWorldGen.kaoliniteLayerMax, ConfigWorldGen.kaoliniteRarity);
		//RICH CLAY ORE
		generateOreInBlock(MineFantasyBlocks.CLAY_ORE.getDefaultState(), Blocks.DIRT, world, random, blockXPos * 16, blockZPos * 16,
				ConfigWorldGen.claySize, MathHelper.getInt(random, ConfigWorldGen.clayFrequencyMin, ConfigWorldGen.clayFrequencyMax),
				ConfigWorldGen.clayLayerMin, ConfigWorldGen.clayLayerMax, ConfigWorldGen.clayRarity);
		//SULFUR ORE
		generateOreInStone(MineFantasyBlocks.SULFUR_ORE.getDefaultState(), world, random, blockXPos * 16, blockZPos * 16,
				ConfigWorldGen.sulfurSize, MathHelper.getInt(random, ConfigWorldGen.sulfurFrequencyMin, ConfigWorldGen.sulfurFrequencyMax),
				ConfigWorldGen.sulfurLayerMin, ConfigWorldGen.sulfurLayerMax, ConfigWorldGen.sulfurRarity);

		//NITRE ORE
		generateOreInStone(MineFantasyBlocks.NITRE_ORE.getDefaultState(), world, random, blockXPos * 16, blockZPos * 16,
				ConfigWorldGen.nitreSize, MathHelper.getInt(random, ConfigWorldGen.nitreFrequencyMin, ConfigWorldGen.nitreFrequencyMax),
				ConfigWorldGen.nitreLayerMin, ConfigWorldGen.nitreLayerMax, ConfigWorldGen.nitreRarity);
		//BORAX ORE
		generateOreInStone(MineFantasyBlocks.BORAX_ORE.getDefaultState(), world, random, blockXPos * 16, blockZPos * 16,
				ConfigWorldGen.boraxSize, MathHelper.getInt(random, ConfigWorldGen.boraxFrequencyMin, ConfigWorldGen.boraxFrequencyMax),
				ConfigWorldGen.boraxLayerMin, ConfigWorldGen.boraxLayerMax, ConfigWorldGen.boraxRarity);
		//WOLFRAMITE ORE
		generateOreInStone(MineFantasyBlocks.TUNGSTEN_ORE.getDefaultState(), world, random, blockXPos * 16, blockZPos * 16,
				ConfigWorldGen.wolframiteSize, MathHelper.getInt(random, ConfigWorldGen.wolframiteFrequencyMin, ConfigWorldGen.wolframiteFrequencyMax),
				ConfigWorldGen.wolframiteLayerMin, ConfigWorldGen.wolframiteLayerMax, ConfigWorldGen.wolframiteRarity);
		//RICH COAL ORE
		generateOreInStone(MineFantasyBlocks.COAL_RICH_ORE.getDefaultState(), world, random, blockXPos * 16, blockZPos * 16,
				ConfigWorldGen.coalSize, MathHelper.getInt(random, ConfigWorldGen.coalFrequencyMin, ConfigWorldGen.coalFrequencyMax),
				ConfigWorldGen.coalLayerMin, ConfigWorldGen.coalLayerMax, ConfigWorldGen.coalRarity);
		//LIMESTONE GENERATION
		generateOreInStone(MineFantasyBlocks.LIMESTONE.getDefaultState(), world, random, blockXPos * 16, blockZPos * 16,
				ConfigWorldGen.limestoneSize, MathHelper.getInt(random, ConfigWorldGen.limestoneLayerMin, ConfigWorldGen.limestoneLayerMax),
				ConfigWorldGen.limestoneLayerMin, ConfigWorldGen.limestoneLayerMax, ConfigWorldGen.limestoneRarity);
	}

	public void generateOreInStone(IBlockState state, World world, Random random, int blockXPos, int blockZPos, int maxVeinSize, int frequency, int minY, int maxY, float rarity) {
		int deltaY = maxY - minY;

		if (random.nextFloat() < rarity) {
			for (int x = 0; x < frequency; x++) {
				int posX = blockXPos + random.nextInt(16);
				int posY = minY + random.nextInt(deltaY);
				int posZ = blockZPos + random.nextInt(16);
				// N.B. This method applies the anti-cascading-lag offset itself
				(new WorldGenMinable(state, maxVeinSize)).generate(world, random, new BlockPos(posX, posY, posZ));
			}
		}
	}

	public void generateOreInBlock(IBlockState state, Block bed, World world, Random random, int blockXPos, int blockZPos, int maxVeinSize, int frequency, int minY, int maxY, float rarity) {
		int deltaY = maxY - minY;

		if (random.nextFloat() < rarity) {
			for (int x = 0; x < frequency; x++) {
				int posX = blockXPos + random.nextInt(16);
				int posY = minY + random.nextInt(deltaY);
				int posZ = blockZPos + random.nextInt(16);
				// N.B. This method applies the anti-cascading-lag offset itself
				(new WorldGenMinable(state, maxVeinSize, BlockMatcher.forBlock(bed))).generate(world, random, new BlockPos(posX, posY, posZ));
			}
		}
	}
}
