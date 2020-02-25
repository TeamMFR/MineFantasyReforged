package minefantasy.mfr.mechanics.worldGen;

import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class WorldGenMFBase implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        generate(random, chunkX, chunkZ, world, world.provider.getDimension());
    }

    public void generate(Random random, int chunkX, int chunkZ, World world, int dimension) {
        WorldGenGeological.generate(random, chunkX, chunkZ, world, dimension);
        WorldGenBiological.generate(random, chunkX, chunkZ, world, dimension);
        WorldGenStructures.generate(random, chunkX, chunkZ, world, dimension);
    }


}
