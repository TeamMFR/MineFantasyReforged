package minefantasy.mf2.mechanics.worldGen;

import java.util.Random;

import minefantasy.mf2.mechanics.worldGen.structure.WorldGenAncientAlter;
import minefantasy.mf2.mechanics.worldGen.structure.WorldGenAncientForge;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.world.World;

public class WorldGenRuins 
{
	public static float ancientForgeChance = 0.01F;
	public static float ancientAlterChance = 0.05F;
	public static void generate(Random seed, int chunkX, int chunkZ, World world) 
	{
		if(seed.nextFloat() < ancientForgeChance)
		{
			generateAncientForge(seed, chunkX, chunkZ, world);
		}
		if(seed.nextFloat() < ancientAlterChance)
		{
			generateAncientAlter(seed, chunkX, chunkZ, world);
		}
	}
	private static void generateAncientForge(Random seed, int chunkX, int chunkZ, World world) 
	{
		int x = (16*chunkX);
		int z = (16*chunkZ);
		WorldGenAncientForge forge = new WorldGenAncientForge();
		for(int x1 = 0; x1 < 16; x1++)
		{
			for(int z1 = 0; z1 < 16; z1++)
			{
				if(forge.generate(world, seed, x+x1, 0, z+z1))
				{
					MFLogUtil.logDebug("Placed Ancient Forge at " + x+x1 +  " " + z+z1);
					return;
				}
			}
		}
		forge = null;
	}
	private static void generateAncientAlter(Random seed, int chunkX, int chunkZ, World world) 
	{
		int spawnZ = world.getSpawnPoint().posZ;
		int spawnChunkZ = (int)( (float)spawnZ/16F);
		
		if(chunkZ == spawnChunkZ)
		{
			int x = (16*chunkX);
			int z = (16*chunkZ);
			
			WorldGenAncientAlter ruin = new WorldGenAncientAlter();
			for(int x1 = 0; x1 < 16; x1++)
			{
				//for(int z1 = 0; z1 < 16; z1++)
				{
					if(ruin.generate(world, seed, x+x1, 0, spawnZ))
					{
						MFLogUtil.logDebug("Placed Ancient Alter at " + x+x1 +  " " + spawnZ);
						return;
					}
				}
			}
			ruin = null;
		}
	}
}
