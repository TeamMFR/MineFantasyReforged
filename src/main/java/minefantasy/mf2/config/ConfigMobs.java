package minefantasy.mf2.config;

import minefantasy.mf2.block.tileentity.blastfurnace.TileEntityBlastFH;
import minefantasy.mf2.mechanics.CombatMechanics;

public class ConfigMobs extends ConfigurationBaseMF
{
	public static final String BASIC = "1-1: Basic Entities";
	public static int entityID;
	public static final String MOB_DRAGON = "2-4: DRAGON";
	//public static int dragonID;
	public static int youngdragonHP, dragonHP, diredragonHP, elderdragonHP, ancientdragonHP;
	public static int youngdragonMD, dragonMD, diredragonMD, elderdragonMD, ancientdragonMD;
	public static int youngdragonFD, dragonFD, diredragonFD, elderdragonFD, ancientdragonFD;
	public static int youngdragonFT, dragonFT, diredragonFT, elderdragonFT, ancientdragonFT;
	public static float dragonChance;
	public static int dragonInterval;
	public static boolean dragonKillNPC;
	public static boolean dragonGriefFire;
	public static boolean dragonGriefGeneral;
	public static boolean dragonMSG;
	
	public static final String MOB_MINOTAUR = "5-5: MINOTAUR";
	//public static int dragonID;
	public static int minotaurHP, netherminotaurHP;
	public static int minotaurMD, netherminotaurMD;
	public static int minotaurGD, netherminotaurGD;
	public static int netherminotaurBT, netherminotaurBD, netherminotaurDC, netherminotaurGC, netherminotaurGCB, netherminotaurTC;
	public static int minotaurBT, minotaurBD, minotaurDC, minotaurGC, minotaurGCB, minotaurTC;
	public static int minotaurSpawnrate, minotaurSpawnrateNether;
	
	@Override
	protected void loadConfig()
	{
		entityID =  Integer.parseInt(config.get(BASIC, "1: Basic Entity ID", -1, "Where MF Entities start (Each entity adds 1) set to -1 for auto-assign").getString());
		
		//DragonBreed
		//dragonID =  Integer.parseInt(config.get(MOB_DRAGON, "1A: Dragon ID", 200, "The ID For dragons").getString());
		
		youngdragonHP =  Integer.parseInt(config.get(MOB_DRAGON, 	"2Aa: Health", 60, "Young Dragon Stats").getString());
		youngdragonMD =  Integer.parseInt(config.get(MOB_DRAGON, 	"2Ab: Bite dmg", 4).getString());
		youngdragonFD =  Integer.parseInt(config.get(MOB_DRAGON, 	"2Ab: Fire dmg", 2).getString());
		youngdragonFT =  Integer.parseInt(config.get(MOB_DRAGON, 	"2Ac: Fire time", 10).getString());
		
		dragonHP =  Integer.parseInt(config.get(MOB_DRAGON,		 	"2Ba: Health", 100, "Adult Dragon Stats").getString());
		dragonMD =  Integer.parseInt(config.get(MOB_DRAGON, 		"2Bb: Bite dmg", 7).getString());
		dragonFD =  Integer.parseInt(config.get(MOB_DRAGON, 		"2Bb: Fire dmg", 5).getString());
		dragonFT =  Integer.parseInt(config.get(MOB_DRAGON, 		"2Bc: Fire time", 40).getString());
		
		diredragonHP =  Integer.parseInt(config.get(MOB_DRAGON,  	"2Ca: Health", 200, "Dire Dragon Stats").getString());
		diredragonMD =  Integer.parseInt(config.get(MOB_DRAGON, 	"2Cb: Bite dmg", 8).getString());
		diredragonFD =  Integer.parseInt(config.get(MOB_DRAGON, 	"2Cb: Fire dmg", 8).getString());
		diredragonFT =  Integer.parseInt(config.get(MOB_DRAGON, 	"2Cc: Fire time", 40).getString());
		
		elderdragonHP =  Integer.parseInt(config.get(MOB_DRAGON, 	"2Da: Health", 500, "Elder Dragon Stats").getString());
		elderdragonMD =  Integer.parseInt(config.get(MOB_DRAGON, 	"2Db: Bite dmg", 14).getString());
		elderdragonFD =  Integer.parseInt(config.get(MOB_DRAGON, 	"2Db: Fire dmg", 10).getString());
		elderdragonFT =  Integer.parseInt(config.get(MOB_DRAGON, 	"2Dc: Fire time", 50).getString());
		
		ancientdragonHP =  Integer.parseInt(config.get(MOB_DRAGON, 	"2Ea: Health", 1000, "Ancient Dragon Stats").getString());
		ancientdragonMD =  Integer.parseInt(config.get(MOB_DRAGON, 	"2Eb: Bite dmg", 20).getString());
		ancientdragonFD =  Integer.parseInt(config.get(MOB_DRAGON, 	"2Eb: Fire dmg", 10).getString());
		ancientdragonFT =  Integer.parseInt(config.get(MOB_DRAGON, 	"2Ec: Fire time", 100).getString());
		
		
		dragonInterval =  Integer.parseInt(config.get(MOB_DRAGON, "3A: Dragon Spawn Interval", 12000, "How many ticks between visits (12000 means 4 times a day), there is a chance for a dragon each time").getString());		
		dragonChance =  Float.parseFloat(config.get(MOB_DRAGON, "3B: Spawn Chance", 5F, "A Percent (0-100) chance that a dragon spawns at set times").getString());
		
		dragonKillNPC =  Boolean.parseBoolean(config.get(MOB_DRAGON, "4A: Kill NPC Grief", true, "Should dragons kill NPCs (including villages as well as animals/mobs)... Not as determined though").getString());
		dragonGriefFire =  Boolean.parseBoolean(config.get(MOB_DRAGON, "4B: Fire Grief", true, "Should fire breath start fires").getString());
		dragonGriefGeneral =  Boolean.parseBoolean(config.get(MOB_DRAGON, "4C: General Block Grief", true, "Should blocks be frozen by frost breath, melted by fire, or glass shatter with fire and stomping").getString());
		dragonMSG =  Boolean.parseBoolean(config.get(MOB_DRAGON, "4D: Spawn Message", true, "Will players get a message when dragons enter/leave").getString());
	
		minotaurHP =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Aa: Health", 50, "Brown Minotaur").getString());
		minotaurMD =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Ab: Pound dmg", 5).getString());
		minotaurGD =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Ac: Gore dmg", 5).getString());
		minotaurBD =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Ad: Beserk dmg", 7).getString());
		minotaurBT =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Ae: Beserk threshold (% health)", 10).getString());
		minotaurDC =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Af: Disarm chance when mob power attacks", 10).getString());
		minotaurGC =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Ag: Grab Chance", 5).getString());
		minotaurGCB =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Ah: Grab Chance (Beserk)", 10).getString());
		minotaurTC =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Ai: Throw Chance", 20).getString());
		minotaurSpawnrate  =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Aj: Spawnrate", 5).getString());
		
		netherminotaurHP =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Ba: Health", 80, "Dread Minotaur").getString());
		netherminotaurMD =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Bb: Pound dmg", 8).getString());
		netherminotaurGD =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Bc: Gore dmg", 8).getString());
		netherminotaurBD =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Bd: Beserk dmg", 10).getString());
		netherminotaurBT =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Be: Beserk threshold (% health)", 25).getString());
		netherminotaurDC =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Bf: Disarm chance when mob power attacks", 20).getString());
		netherminotaurGC =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Bg: Grab Chance", 25).getString());
		netherminotaurGCB =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Bh: Grab Chance (Beserk)", 50).getString());
		netherminotaurTC =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Bi: Throw Chance", 20).getString());
		minotaurSpawnrateNether  =  Integer.parseInt(config.get(MOB_MINOTAUR, 	"5Bj: Spawnrate", 25).getString());
	}

}
