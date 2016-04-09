package minefantasy.mf2.entity.list;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.config.ConfigMobs;
import minefantasy.mf2.entity.mob.*;

import cpw.mods.fml.common.registry.EntityRegistry;

public class MobListMF
{
	public static void register(boolean auto, int IDBase)
	{
		DragonBreath.init();
		addEntity(auto ? EntityListMF.autoAssign() : IDBase, EntityDragon.class, "MF_Dragon", 0, 12698049);++IDBase;
		addEntity(auto ? EntityListMF.autoAssign() : IDBase, EntityMinotaur.class, "MF_Minotaur", 5651507, 11013646);
		
		addSpawn(EntityDragon.class, 1, 1, 1, EnumCreatureType.monster, Type.NETHER);
		if(ConfigMobs.minotaurSpawnrate > 0)
		{
			addSpawn(EntityMinotaur.class, ConfigMobs.minotaurSpawnrate, 1, 1, EnumCreatureType.monster);
		}
		if(ConfigMobs.minotaurSpawnrateNether > 0)
		{
			addSpawn(EntityMinotaur.class, ConfigMobs.minotaurSpawnrateNether, 1, 1, EnumCreatureType.monster, Type.NETHER);
		}
	}
	
	private static void addEntity(int IDBase, Class<? extends Entity> entityClass, String entityName, int eggColor, int eggDotsColor)
    {
            if (MineFantasyII.isDebug())
            {
            	System.out.println("MineFantasy: Register Mob " + entityClass + " with Mod ID " + IDBase);
            }
            EntityRegistry.registerModEntity(entityClass, entityName, IDBase, MineFantasyII.instance, 128, 1, true);
            EntityList.entityEggs.put(Integer.valueOf(IDBase), new EntityEggInfo(IDBase, eggColor, eggDotsColor));
            EntityList.addMapping(entityClass, entityName, IDBase);
    }
    
    public static void addSpawn(Class <? extends EntityLiving > entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature)
    {
        for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray())
        {
        	if(biome != null)
        	{
        		if(BiomeDictionary.isBiomeRegistered(biome))
        		{
        			if(BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.END))
        			{
        				return;
        			}
        			if(BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.NETHER))
        			{
        				return;
        			}
        			if(BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MUSHROOM))
        			{
        				return;
        			}
        		}
        		
        		EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biome);
        	}
        }
    }
    
    public static void addSpawn(Class <? extends EntityLiving > entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, BiomeDictionary.Type type)
    {
        for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray())
        {
        	if(biome != null)
        	{
        		if(BiomeDictionary.isBiomeRegistered(biome))
        		{
        			if(BiomeDictionary.isBiomeOfType(biome, type))
        			{
        				EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biome);
        			}
        		}
        	}
        }
    }
}
