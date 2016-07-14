package minefantasy.mf2.mechanics.worldGen;

import java.util.Random;

import minefantasy.mf2.config.ConfigWorldGen;
import minefantasy.mf2.mechanics.worldGen.structure.WorldGenAncientAlter;
import minefantasy.mf2.mechanics.worldGen.structure.WorldGenAncientForge;
import minefantasy.mf2.mechanics.worldGen.structure.dwarven.WorldGenDwarvenStronghold;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.world.World;

public class WorldGenStructures 
{
	public static void generate(Random seed, int chunkX, int chunkZ, World world) 
	{
		if(seed.nextFloat() < ConfigWorldGen.MFChance)
		{
			generateAncientForge(seed, chunkX, chunkZ, world);
		}
		if(seed.nextFloat() < ConfigWorldGen.MAChance)
		{
			generateAncientAlter(seed, chunkX, chunkZ, world);
		}
		if(seed.nextFloat() < ConfigWorldGen.DSChance)
		{
			generateDwarvenStronghold(seed, chunkX, chunkZ, world);
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
	private static void generateDwarvenStronghold(Random seed, int chunkX, int chunkZ, World world) 
	{
		int x = (16*chunkX);
		int z = (16*chunkZ);
		WorldGenDwarvenStronghold stronghold = new WorldGenDwarvenStronghold();
		for(int x1 = 0; x1 < 16; x1++)
		{
			for(int z1 = 0; z1 < 16; z1++)
			{
				if(stronghold.generate(world, seed, x+x1, 0, z+z1))
				{
					MFLogUtil.logDebug("Placed Dwarven Stronghold at " + x+x1 +  " " + z+z1);
					return;
				}
			}
		}
		stronghold = null;
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
