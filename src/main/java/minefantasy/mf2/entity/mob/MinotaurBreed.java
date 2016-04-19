package minefantasy.mf2.entity.mob;

import java.util.ArrayList;
import java.util.Random;

import minefantasy.mf2.config.ConfigMobs;
import net.minecraft.world.biome.BiomeGenBase;

public class MinotaurBreed 
{
	public static final ArrayList normal = new ArrayList<MinotaurBreed>();
	public static final ArrayList frost = new ArrayList<MinotaurBreed>();
	public static final ArrayList nether = new ArrayList<MinotaurBreed>();
	private static Random rand = new Random();
	
	public static MinotaurBreed BROWN = new MinotaurBreed(normal, "minotaur", "Brown", ConfigMobs.minotaurHP, ConfigMobs.minotaurMD, ConfigMobs.minotaurGD).setGrabs(ConfigMobs.minotaurDC, ConfigMobs.minotaurGC, ConfigMobs.minotaurTC).setBeserk(ConfigMobs.minotaurBT, ConfigMobs.minotaurBD, ConfigMobs.minotaurGCB);
	public static MinotaurBreed FROST = new MinotaurBreed(frost, "frostminotaur", "White", ConfigMobs.frostminotaurHP, ConfigMobs.frostminotaurMD, ConfigMobs.frostminotaurGD).setGrabs(ConfigMobs.frostminotaurDC, ConfigMobs.frostminotaurGC, ConfigMobs.frostminotaurTC).setBeserk(ConfigMobs.frostminotaurBT, ConfigMobs.frostminotaurBD, ConfigMobs.frostminotaurGCB);
	public static MinotaurBreed NETHER = new MinotaurBreed(nether, "dredminotaur", "Nether", ConfigMobs.netherminotaurHP, ConfigMobs.netherminotaurMD, ConfigMobs.netherminotaurGD).setGrabs(ConfigMobs.netherminotaurDC, ConfigMobs.netherminotaurGC, ConfigMobs.netherminotaurTC).setBeserk(ConfigMobs.netherminotaurBT, ConfigMobs.netherminotaurBD, ConfigMobs.netherminotaurGCB).setIntelligence(1);
	
	public String name = "minotaur";
	public String tex = "Brown";
	public float health = 50; 
	public float poundDamage = 8;
	public float goreDamage = 8;
	public float beserkDamage = 12;
	public int beserkThreshold = 40;
	public int grabChance = 10;
	public int grabChanceBeserk = 25;
	public int throwChance = 20;
	public int disarmChance = 10;
	public ArrayList pool;
	public int intelligenceLvl = 0;
	
	public MinotaurBreed(ArrayList pool, String name, String tex, float health, float poundDamage, float goreDamage)
	{
		this.pool = pool;
		pool.add(this);
		this.name = name;
		this.tex = tex;
		this.health = health;
		this.poundDamage = poundDamage;
		this.goreDamage = goreDamage;
		this.beserkDamage = poundDamage *1.5F;
	}
	/**
	 * Set beserk stats
	 * @param threshold the percent (0-100) health where it triggers (Default 25%)
	 * @param damage (The damage done by melee)
	 * @param grabChance the percent (0-100) chance to grab enemies when beserked (Default 25%)
	 */
	public MinotaurBreed setBeserk(int threshold, float damage, int grabChance)
	{
		this.beserkThreshold = threshold;
		this.beserkDamage = damage;
		this.grabChanceBeserk = grabChance;
		return this;
	}
	/**
	 * 
	 * @param grabChance the percent (0-100) chance to disarm enemies with a power attack (Default 10%)
	 * @param grabChance the percent (0-100) chance to grab enemies (Default 10%)
	 * @param grabChance the percent (0-100) chance to throw grabbed enemies when hit (Default 20%)
	 * @return
	 */
	public MinotaurBreed setGrabs(int disarmChance, int grabChance, int throwChance)
	{
		this.disarmChance = disarmChance;
		this.grabChance = grabChance;
		this.throwChance = throwChance;
		return this;
	}
	
	/**
	 * Set the intelligence for the mob -1 For feral... 0 For basic... 1 For Orderly
	 */
	public MinotaurBreed setIntelligence(int level)
	{
		this.intelligenceLvl = level;
		return this;
	}
	
	public static int getRandomMinotaur(int species)
	{
		return getRandomMinotaur(species == 1 ? MinotaurBreed.nether : MinotaurBreed.normal);
	}
	public static int getRandomMinotaur(ArrayList<MinotaurBreed> pool)
	{
		 if(pool.size() <= 1)
		 {
			 return 0;
		 }
		 return rand.nextInt(pool.size());
	}
	public static MinotaurBreed getBreed(int species, int id)
	{
		return getBreed(species == 2 ? MinotaurBreed.frost : species == 1 ? MinotaurBreed.nether : MinotaurBreed.normal, id);
	}
	public static MinotaurBreed getBreed(ArrayList<MinotaurBreed> pool, int id)
	{
		if(id >= pool.size())
		{
			id = (pool.size()-1);
		}
		return pool.get(id);
	}
	public static int getEnvironment(EntityMinotaur mob) 
	{
		if(mob.dimension == -1)return 1;//NETHER
		
		BiomeGenBase biome =mob.worldObj.getBiomeGenForCoords((int)mob.posY, (int)mob.posZ);
		if(biome != null)
		{
			if(biome.getEnableSnow() || biome.temperature <= 0.25F || biome.getTempCategory() == BiomeGenBase.TempCategory.COLD)
			{
				return 2;//FROST
			}
		}
		return 0;//BROWN
	}
}
