package minefantasy.mf2.entity.mob;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeType;

import minefantasy.mf2.config.ConfigMobs;
import minefantasy.mf2.util.MFLogUtil;

public class DragonBreed 
{
	private static final ArrayList<DragonBreed>[]breeds = new ArrayList[]{new ArrayList<DragonBreed>(), new ArrayList<DragonBreed>(), new ArrayList<DragonBreed>(), new ArrayList<DragonBreed>(), new ArrayList<DragonBreed>()};
	
	/* New System Breeds
	 * Red: Fire, plains and hills
	 * Green: Poison, swamps, forests and plains
	 * White: Frost, cold regions
	 * Black: Dire, nether
	 * 
	 */
	public static DragonBreed YOUNGRED = new DragonBreed(0, "dragon_young", "red", "dragon_red", DragonBreath.fire).setCombat(ConfigMobs.youngdragonHP,       0.0F, 0F, ConfigMobs.youngdragonMD, 40,   200).setProjectile(ConfigMobs.youngdragonFD, ConfigMobs.youngdragonFT, 1000, 0.05F).register();
	public static DragonBreed YOUNGWHITE = new DragonBreed(0, "dragon_young", "white", "dragon_frost", DragonBreath.frost).setCombat(ConfigMobs.youngdragonHP,   0.0F, 0F, ConfigMobs.youngdragonMD, 40,   200).setProjectile(ConfigMobs.youngdragonFD, ConfigMobs.youngdragonFT, 800, 0.05F).register();
	public static DragonBreed YOUNGGREEN = new DragonBreed(0, "dragon_young", "green", "dragon_green", DragonBreath.poison).setCombat(ConfigMobs.youngdragonHP,   0.0F, 0F, ConfigMobs.youngdragonMD, 30,   100).setProjectile(ConfigMobs.youngdragonFD, ConfigMobs.youngdragonFT, 600, 0.05F).register();
	
	public static DragonBreed RED = new DragonBreed(1, "dragon", "red", "dragon_red", DragonBreath.fire).setCombat(ConfigMobs.dragonHP, 		  2.0F, 0F,   ConfigMobs.dragonMD, 40, 300).setProjectile(ConfigMobs.dragonFD, ConfigMobs.dragonFT, 500, 0.1F).register();
	public static DragonBreed WHITE = new DragonBreed(1, "dragon", "white", "dragon_frost", DragonBreath.frost).setCombat(ConfigMobs.youngdragonHP, 2.0F, 0F,   ConfigMobs.dragonMD, 40, 300).setProjectile(ConfigMobs.dragonFD, ConfigMobs.dragonFT, 400, 0.1F).register();
	public static DragonBreed GREEN = new DragonBreed(1, "dragon", "green", "dragon_green", DragonBreath.poison).setCombat(ConfigMobs.dragonHP, 	  2.0F, 0F,   ConfigMobs.dragonMD, 30, 200).setProjectile(ConfigMobs.dragonFD, ConfigMobs.dragonFT, 300, 0.1F).register();
	
	public static DragonBreed REDM = new DragonBreed(2, "dragon_mature", "red", "dragon_red", DragonBreath.fire).setCombat(ConfigMobs.diredragonHP, 2.0F, 1F,   ConfigMobs.diredragonMD, 40, 300).setProjectile(ConfigMobs.diredragonFD, ConfigMobs.diredragonFT, 400, 0.25F).register();
	public static DragonBreed WHITEM = new DragonBreed(2, "dragon_mature", "white", "dragon_frost", DragonBreath.frost).setCombat(ConfigMobs.diredragonHP, 2.0F, 1F,   ConfigMobs.diredragonMD, 40, 300).setProjectile(ConfigMobs.diredragonFD, ConfigMobs.diredragonFT, 300, 0.25F).register();
	public static DragonBreed GREENM = new DragonBreed(2, "dragon_mature", "green", "dragon_green", DragonBreath.poison).setCombat(ConfigMobs.diredragonHP, 2.0F, 1F,   ConfigMobs.diredragonMD, 30, 200).setProjectile(ConfigMobs.diredragonFD, ConfigMobs.diredragonFT, 200, 0.25F).register();
	
	public static DragonBreed ELDERRED = new DragonBreed(3, "dragon_elder", "red", "dragon_red", DragonBreath.fire).setCombat(ConfigMobs.elderdragonHP, 2.0F, 2F, ConfigMobs.elderdragonMD, 40, 300).setProjectile(ConfigMobs.elderdragonFD, ConfigMobs.elderdragonFT, 400, 0.35F).register();
	public static DragonBreed ELDERWHITE = new DragonBreed(3, "dragon_elder", "white", "dragon_frost", DragonBreath.frost).setCombat(ConfigMobs.elderdragonHP,  2.0F, 2F, ConfigMobs.elderdragonMD, 40, 300).setProjectile(ConfigMobs.elderdragonFD, ConfigMobs.elderdragonFT, 300, 0.35F).register();
	public static DragonBreed ELDERGREEN = new DragonBreed(3, "dragon_elder", "green", "dragon_green", DragonBreath.poison).setCombat(ConfigMobs.elderdragonHP,  2.0F, 2F, ConfigMobs.elderdragonMD, 30, 200).setProjectile(ConfigMobs.elderdragonFD, ConfigMobs.elderdragonFT, 200, 0.35F).register();
	
	public static DragonBreed ANCIENT = new DragonBreed(4, "dragon_ancient", "dire", "dragon_ancient", DragonBreath.fire).setCombat(ConfigMobs.ancientdragonHP, 2.0F, 5F, ConfigMobs.ancientdragonMD, 40, 300).setProjectile(ConfigMobs.ancientdragonFD, ConfigMobs.ancientdragonFT, 400, 1.0F).register();
	
	
	public int tier = 0;
	public String name = "dragon";
	public String breedName = "red";
	public DragonBreath rangedAttack;
	public String tex = "dragonGreen";
	public float health = 200; 
	public float regenRate = 0;
	public float meleeDamage = 8;
	public float fireDamage = 5;
	public float pyro = 0.1F;
	public int disengageChance = 200;
	public int fireTimer = 50;
	public int coolTimer = 200;
	public int meleeSpeed = 30;
	public float DT = 2.0F;
	
	public DragonBreed(int tier, String name, String breedname, String tex, DragonBreath breath)
	{
		this.tier = tier;
		this.name = name;
		this.breedName = breedname;
		this.tex = tex;
		this.rangedAttack = breath;
	}
	public DragonBreed setCombat(float health, float DT, float regenRate, float meleeDamage, int meleeSpeed, int disengageChance)
	{
		this.health = health;
		this.regenRate = regenRate;
		this.meleeDamage = meleeDamage;
		this.meleeSpeed = meleeSpeed;
		this.disengageChance = disengageChance;
		this.DT = DT;
		return this;
	}
	public DragonBreed setProjectile(float fireDamage, int fireTimer, int coolTimer, float pyro)
	{
		this.fireDamage = fireDamage;
		this.fireTimer = fireTimer;
		this.coolTimer = coolTimer;
		this.pyro = pyro;
		return this;
	}
	public DragonBreed register()
	{
		breeds[tier].add(this);
		return this;
	}
	
	public static DragonBreed getBreed(int tier, int id)
	{
		DragonBreed breed = breeds[tier].get(id);
		return breed != null ? breed : breeds[tier].get(0);
	}
	private static Random rand = new Random();
	public static int getRandomDragon(EntityDragon dragon, int tier)
	{
		 ArrayList<DragonBreed> pool = breeds[tier];
		 BiomeGenBase biome = dragon.worldObj.getBiomeGenForCoords((int)(dragon.posX), (int)(dragon.posZ));
		 int id = getIdForBiome(biome);
		 if(id >= pool.size())
		 {
			 id = pool.size()-1;
		 }
		 MFLogUtil.logDebug("Biome ID: " + id + " " + biome.biomeName);
		 return id;
	}
	private static int getIdForBiome(BiomeGenBase biome) 
	{
		if(biome.getEnableSnow() || biome.temperature <= 0.25F)return 1;//Frost
		if(biome.isHighHumidity()) return 2;//Poison
		return 0;//Fire
	}
}
