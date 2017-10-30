package minefantasy.mf2.config;

import minefantasy.mf2.entity.mob.EntityDragon;

public class ConfigMobs extends ConfigurationBaseMF {
    public static final String BASIC = "1-1: Basic Entities";
    public static final String MOB_DRAGON = "2-4: DRAGON";
    public static final String MOB_MINOTAUR = "5-5: MINOTAUR";
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
    public static int minotaurHP, minotaurMD, minotaurGD, minotaurBT, minotaurBD, minotaurDC, minotaurGC, minotaurGCB,
            minotaurTC;
    public static int guardminotaurHP, guardminotaurMD, guardminotaurGD, guardminotaurBT, guardminotaurBD,
            guardminotaurDC, guardminotaurGC, guardminotaurGCB, guardminotaurTC;
    public static int eliteminotaurHP, eliteminotaurMD, eliteminotaurGD, eliteminotaurBT, eliteminotaurBD,
            eliteminotaurDC, eliteminotaurGC, eliteminotaurGCB, eliteminotaurTC;
    public static int bossminotaurHP, bossminotaurMD, bossminotaurGD, bossminotaurBT, bossminotaurBD, bossminotaurDC,
            bossminotaurGC, bossminotaurGCB, bossminotaurTC;
    public static int lightminotaurAR, mediumminotaurAR, heavyminotaurAR, frostminotaurAR, dreadminotaurAR;
    public static int minotaurSpawnrate, minotaurSpawnrateNether;

    @Override
    protected void loadConfig() {
        youngdragonHP = Integer.parseInt(config.get(MOB_DRAGON, "2Aa: Health", 60, "Young Dragon Stats").getString());
        youngdragonMD = Integer.parseInt(config.get(MOB_DRAGON, "2Ab: Bite dmg", 4).getString());
        youngdragonFD = Integer.parseInt(config.get(MOB_DRAGON, "2Ab: Fire dmg", 2).getString());
        youngdragonFT = Integer.parseInt(config.get(MOB_DRAGON, "2Ac: Fire time", 10).getString());

        dragonHP = Integer.parseInt(config.get(MOB_DRAGON, "2Ba: Health", 100, "Adult Dragon Stats").getString());
        dragonMD = Integer.parseInt(config.get(MOB_DRAGON, "2Bb: Bite dmg", 7).getString());
        dragonFD = Integer.parseInt(config.get(MOB_DRAGON, "2Bb: Fire dmg", 5).getString());
        dragonFT = Integer.parseInt(config.get(MOB_DRAGON, "2Bc: Fire time", 40).getString());

        diredragonHP = Integer.parseInt(config.get(MOB_DRAGON, "2Ca: Health", 200, "Dire Dragon Stats").getString());
        diredragonMD = Integer.parseInt(config.get(MOB_DRAGON, "2Cb: Bite dmg", 8).getString());
        diredragonFD = Integer.parseInt(config.get(MOB_DRAGON, "2Cb: Fire dmg", 8).getString());
        diredragonFT = Integer.parseInt(config.get(MOB_DRAGON, "2Cc: Fire time", 40).getString());

        elderdragonHP = Integer.parseInt(config.get(MOB_DRAGON, "2Da: Health", 500, "Elder Dragon Stats").getString());
        elderdragonMD = Integer.parseInt(config.get(MOB_DRAGON, "2Db: Bite dmg", 14).getString());
        elderdragonFD = Integer.parseInt(config.get(MOB_DRAGON, "2Db: Fire dmg", 10).getString());
        elderdragonFT = Integer.parseInt(config.get(MOB_DRAGON, "2Dc: Fire time", 50).getString());

        ancientdragonHP = Integer
                .parseInt(config.get(MOB_DRAGON, "2Ea: Health", 1000, "Ancient Dragon Stats").getString());
        ancientdragonMD = Integer.parseInt(config.get(MOB_DRAGON, "2Eb: Bite dmg", 20).getString());
        ancientdragonFD = Integer.parseInt(config.get(MOB_DRAGON, "2Eb: Fire dmg", 10).getString());
        ancientdragonFT = Integer.parseInt(config.get(MOB_DRAGON, "2Ec: Fire time", 100).getString());

        dragonInterval = Integer.parseInt(config.get(MOB_DRAGON, "3A: Dragon Spawn Interval", 12000,
                "How many ticks between visits (12000 means 4 times a day), there is a chance for a dragon each time")
                .getString());
        dragonChance = Float.parseFloat(config
                .get(MOB_DRAGON, "3B: Spawn Chance", 5F, "A Percent (0-100) chance that a dragon spawns at set times")
                .getString());

        dragonKillNPC = Boolean.parseBoolean(config.get(MOB_DRAGON, "4A: Kill NPC Grief", true,
                "Should dragons kill NPCs (including villages as well as animals/mobs)... Not as determined though")
                .getString());
        dragonGriefFire = Boolean.parseBoolean(
                config.get(MOB_DRAGON, "4B: Fire Grief", true, "Should fire breath start fires").getString());
        dragonGriefGeneral = Boolean.parseBoolean(config.get(MOB_DRAGON, "4C: General Block Grief", true,
                "Should blocks be frozen by frost breath, melted by fire, or glass shatter with fire and stomping")
                .getString());
        dragonMSG = Boolean.parseBoolean(
                config.get(MOB_DRAGON, "4D: Spawn Message", true, "Will players get a message when dragons enter/leave")
                        .getString());
        EntityDragon.interestTimeSeconds = Integer.parseInt(config.get(MOB_DRAGON, "4E: Dragon Interest Time", 90,
                "How many seconds until a dragon leaves (2x as long if wounded) ").getString());
        EntityDragon.heartChance = Float.parseFloat(config
                .get(MOB_DRAGON, "4F: Heart Drop chance modifier", 1F, "Modify chance of getting a heart").getString());

        minotaurSpawnrate = Integer.parseInt(config.get(MOB_MINOTAUR, "5Aa: Overworld Spawnrate", 5).getString());
        minotaurSpawnrateNether = Integer.parseInt(config.get(MOB_MINOTAUR, "5Ab: Nether Spawnrate", 25).getString());

        minotaurHP = Integer.parseInt(config.get(MOB_MINOTAUR, "5Ba: Health", 30, "Lesser Minotaur").getString());
        minotaurMD = Integer.parseInt(config.get(MOB_MINOTAUR, "5Bb: Pound dmg", 5).getString());
        minotaurGD = Integer.parseInt(config.get(MOB_MINOTAUR, "5Bc: Gore dmg", 5).getString());
        minotaurBD = Integer.parseInt(config.get(MOB_MINOTAUR, "5Bd: Beserk dmg", 7).getString());
        minotaurBT = Integer.parseInt(config.get(MOB_MINOTAUR, "5Be: Beserk threshold (% health)", 25).getString());
        minotaurDC = Integer
                .parseInt(config.get(MOB_MINOTAUR, "5Bf: Disarm chance when mob power attacks", 10).getString());
        minotaurGC = Integer.parseInt(config.get(MOB_MINOTAUR, "5Bg: Grab Chance", 5).getString());
        minotaurGCB = Integer.parseInt(config.get(MOB_MINOTAUR, "5Bh: Grab Chance (Beserk)", 10).getString());
        minotaurTC = Integer.parseInt(config.get(MOB_MINOTAUR, "5Bi: Throw Chance", 20).getString());

        guardminotaurHP = Integer.parseInt(config.get(MOB_MINOTAUR, "5Ca: Health", 30, "Minotaur Warrior").getString());
        guardminotaurMD = Integer.parseInt(config.get(MOB_MINOTAUR, "5Cb: Pound dmg", 6).getString());
        guardminotaurGD = Integer.parseInt(config.get(MOB_MINOTAUR, "5Cc: Gore dmg", 7).getString());
        guardminotaurBD = Integer.parseInt(config.get(MOB_MINOTAUR, "5Cd: Beserk dmg", 8).getString());
        guardminotaurBT = Integer
                .parseInt(config.get(MOB_MINOTAUR, "5Ce: Beserk threshold (% health)", 35).getString());
        guardminotaurDC = Integer
                .parseInt(config.get(MOB_MINOTAUR, "5Cf: Disarm chance when mob power attacks", 10).getString());
        guardminotaurGC = Integer.parseInt(config.get(MOB_MINOTAUR, "5Cg: Grab Chance", 5).getString());
        guardminotaurGCB = Integer.parseInt(config.get(MOB_MINOTAUR, "5Ch: Grab Chance (Beserk)", 10).getString());
        guardminotaurTC = Integer.parseInt(config.get(MOB_MINOTAUR, "5Ci: Throw Chance", 20).getString());
        lightminotaurAR = Integer.parseInt(config.get(MOB_MINOTAUR, "5Cj: Armour rating", 100).getString());

        eliteminotaurHP = Integer.parseInt(config.get(MOB_MINOTAUR, "5Da: Health", 50, "Elite Minotaur").getString());
        eliteminotaurMD = Integer.parseInt(config.get(MOB_MINOTAUR, "5Db: Pound dmg", 6).getString());
        eliteminotaurGD = Integer.parseInt(config.get(MOB_MINOTAUR, "5Dc: Gore dmg", 6).getString());
        eliteminotaurBD = Integer.parseInt(config.get(MOB_MINOTAUR, "5Dd: Beserk dmg", 8).getString());
        eliteminotaurBT = Integer
                .parseInt(config.get(MOB_MINOTAUR, "5De: Beserk threshold (% health)", 40).getString());
        eliteminotaurDC = Integer
                .parseInt(config.get(MOB_MINOTAUR, "5Df: Disarm chance when mob power attacks", 20).getString());
        eliteminotaurGC = Integer.parseInt(config.get(MOB_MINOTAUR, "5Dg: Grab Chance", 10).getString());
        eliteminotaurGCB = Integer.parseInt(config.get(MOB_MINOTAUR, "5Dh: Grab Chance (Beserk)", 20).getString());
        eliteminotaurTC = Integer.parseInt(config.get(MOB_MINOTAUR, "5Di: Throw Chance", 20).getString());
        mediumminotaurAR = Integer.parseInt(config.get(MOB_MINOTAUR, "5Dj: Armour rating", 400).getString());

        bossminotaurHP = Integer.parseInt(config.get(MOB_MINOTAUR, "5Ea: Health", 60, "Minotaur Warlord").getString());
        bossminotaurMD = Integer.parseInt(config.get(MOB_MINOTAUR, "5Eb: Pound dmg", 8).getString());
        bossminotaurGD = Integer.parseInt(config.get(MOB_MINOTAUR, "5Ec: Gore dmg", 10).getString());
        bossminotaurBD = Integer.parseInt(config.get(MOB_MINOTAUR, "5Ed: Beserk dmg", 10).getString());
        bossminotaurBT = Integer.parseInt(config.get(MOB_MINOTAUR, "5Ee: Beserk threshold (% health)", 40).getString());
        bossminotaurDC = Integer
                .parseInt(config.get(MOB_MINOTAUR, "5Ef: Disarm chance when mob power attacks", 40).getString());
        bossminotaurGC = Integer.parseInt(config.get(MOB_MINOTAUR, "5Eg: Grab Chance", 20).getString());
        bossminotaurGCB = Integer.parseInt(config.get(MOB_MINOTAUR, "5Eh: Grab Chance (Beserk)", 40).getString());
        bossminotaurTC = Integer.parseInt(config.get(MOB_MINOTAUR, "5Ei: Throw Chance", 20).getString());
        heavyminotaurAR = Integer.parseInt(config.get(MOB_MINOTAUR, "5Ej: Armour rating", 600).getString());

        frostminotaurAR = Integer
                .parseInt(config.get(MOB_MINOTAUR, "5Fa: Frost armour bonus", 50, "Species Bonus").getString());
        dreadminotaurAR = Integer.parseInt(config.get(MOB_MINOTAUR, "5Fb: Dread armour bonus", 100).getString());

    }

}
