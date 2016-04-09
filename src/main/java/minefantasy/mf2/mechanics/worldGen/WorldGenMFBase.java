package minefantasy.mf2.mechanics.worldGen;

import java.util.HashMap;
import java.util.Random;

import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenMFBase implements IWorldGenerator 
{
	public static String generatorLayer = "MineFantasy2";
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		generate(random, chunkX, chunkZ, world);
	}
	public void generate(Random random, int chunkX, int chunkZ, World world) 
	{
		if(shouldGenerate(null, chunkX, chunkZ))
		{
			WorldGenGeological.generate(random, chunkX, chunkZ, world);
			WorldGenBiological.generate(random, chunkX, chunkZ, world);
		}
	}
	
	private static boolean shouldGenerate(NBTTagCompound nbt, int x, int z)
	{
		if(nbt == null)return true;
		String index = "WorldGenMF_x" + x + "z" + z + generatorLayer;
		
		if(nbt.hasKey(index))
		{
			return !nbt.getBoolean(index);
		}
		nbt.setBoolean(index, true);
		return true;
	}
}
