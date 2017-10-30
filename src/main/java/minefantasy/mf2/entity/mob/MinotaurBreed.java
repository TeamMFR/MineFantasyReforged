package minefantasy.mf2.entity.mob;

import minefantasy.mf2.config.ConfigMobs;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.Random;

public class MinotaurBreed {
    public static final ArrayList normal = new ArrayList<MinotaurBreed>();
    public static final ArrayList frost = new ArrayList<MinotaurBreed>();
    public static final ArrayList nether = new ArrayList<MinotaurBreed>();
    public static MinotaurBreed BROWN = new MinotaurBreed(normal, "minotaur", "Brown", null, 10, ConfigMobs.minotaurHP,
            ConfigMobs.minotaurMD, ConfigMobs.minotaurGD, 0)
            .setGrabs(ConfigMobs.minotaurDC, ConfigMobs.minotaurGC, ConfigMobs.minotaurTC)
            .setBeserk(ConfigMobs.minotaurBT, ConfigMobs.minotaurBD, ConfigMobs.minotaurGCB).setLesser();
    public static MinotaurBreed BROWNGUARD = new MinotaurBreed(normal, "minotaur_guard", "GuardBrown", "iron", 20,
            ConfigMobs.guardminotaurHP, ConfigMobs.guardminotaurMD, ConfigMobs.guardminotaurGD,
            ConfigMobs.lightminotaurAR)
            .setGrabs(ConfigMobs.guardminotaurDC, ConfigMobs.guardminotaurGC, ConfigMobs.guardminotaurTC)
            .setBeserk(ConfigMobs.guardminotaurBT, ConfigMobs.guardminotaurBD, ConfigMobs.guardminotaurGCB)
            .setIntelligence(1);
    public static MinotaurBreed BROWNELITE = new MinotaurBreed(normal, "minotaur_elite", "EliteBrown", "steel", 40,
            ConfigMobs.eliteminotaurHP, ConfigMobs.eliteminotaurMD, ConfigMobs.eliteminotaurGD,
            ConfigMobs.mediumminotaurAR)
            .setGrabs(ConfigMobs.eliteminotaurDC, ConfigMobs.eliteminotaurGC, ConfigMobs.eliteminotaurTC)
            .setBeserk(ConfigMobs.eliteminotaurBT, ConfigMobs.eliteminotaurBD, ConfigMobs.eliteminotaurGCB)
            .setIntelligence(2);
    public static MinotaurBreed BROWNBOSS = new MinotaurBreed(normal, "minotaur_boss", "BossBrown", "obsidian", 100,
            ConfigMobs.bossminotaurHP, ConfigMobs.bossminotaurMD, ConfigMobs.bossminotaurGD, ConfigMobs.heavyminotaurAR)
            .setGrabs(ConfigMobs.bossminotaurDC, ConfigMobs.bossminotaurGC, ConfigMobs.bossminotaurTC)
            .setBeserk(ConfigMobs.bossminotaurBT, ConfigMobs.bossminotaurBD, ConfigMobs.bossminotaurGCB)
            .setIntelligence(3).throwBombs();
    public static MinotaurBreed FROST = new MinotaurBreed(frost, "frostminotaur", "White", null, 15,
            ConfigMobs.minotaurHP, ConfigMobs.minotaurMD, ConfigMobs.minotaurGD, ConfigMobs.frostminotaurAR)
            .setGrabs(ConfigMobs.minotaurDC, ConfigMobs.minotaurGC, ConfigMobs.minotaurTC)
            .setBeserk(ConfigMobs.minotaurBT, ConfigMobs.minotaurBD, ConfigMobs.minotaurGCB).setLesser();
    public static MinotaurBreed FROSTGUARD = new MinotaurBreed(frost, "frostminotaur_guard", "GuardWhite", "iron", 25,
            ConfigMobs.guardminotaurHP, ConfigMobs.guardminotaurMD, ConfigMobs.guardminotaurGD,
            ConfigMobs.lightminotaurAR + ConfigMobs.frostminotaurAR)
            .setGrabs(ConfigMobs.guardminotaurDC, ConfigMobs.guardminotaurGC, ConfigMobs.guardminotaurTC)
            .setBeserk(ConfigMobs.guardminotaurBT, ConfigMobs.guardminotaurBD, ConfigMobs.guardminotaurGCB)
            .setIntelligence(1);
    public static MinotaurBreed FROSTELITE = new MinotaurBreed(frost, "frostminotaur_elite", "EliteWhite", "steel", 50,
            ConfigMobs.eliteminotaurHP, ConfigMobs.eliteminotaurMD, ConfigMobs.eliteminotaurGD,
            ConfigMobs.mediumminotaurAR + ConfigMobs.frostminotaurAR)
            .setGrabs(ConfigMobs.eliteminotaurDC, ConfigMobs.eliteminotaurGC, ConfigMobs.eliteminotaurTC)
            .setBeserk(ConfigMobs.eliteminotaurBT, ConfigMobs.eliteminotaurBD, ConfigMobs.eliteminotaurGCB)
            .setIntelligence(2);
    public static MinotaurBreed FROSTBOSS = new MinotaurBreed(frost, "frostminotaurr_boss", "BossWhite", "obsidian",
            120, ConfigMobs.bossminotaurHP, ConfigMobs.bossminotaurMD, ConfigMobs.bossminotaurGD,
            ConfigMobs.heavyminotaurAR + ConfigMobs.frostminotaurAR)
            .setGrabs(ConfigMobs.bossminotaurDC, ConfigMobs.bossminotaurGC, ConfigMobs.bossminotaurTC)
            .setBeserk(ConfigMobs.bossminotaurBT, ConfigMobs.bossminotaurBD, ConfigMobs.bossminotaurGCB)
            .setIntelligence(3).throwBombs();
    public static MinotaurBreed NETHER = new MinotaurBreed(nether, "dredminotaur", "Nether", null, 15,
            ConfigMobs.minotaurHP, ConfigMobs.minotaurMD, ConfigMobs.minotaurGD, ConfigMobs.dreadminotaurAR)
            .setGrabs(ConfigMobs.minotaurDC, ConfigMobs.minotaurGC, ConfigMobs.minotaurTC)
            .setBeserk(ConfigMobs.minotaurBT, ConfigMobs.minotaurBD, ConfigMobs.minotaurGCB).setLesser();
    public static MinotaurBreed NETHERGUARD = new MinotaurBreed(nether, "dredminotaur_guard", "GuardNether", "iron", 25,
            ConfigMobs.guardminotaurHP, ConfigMobs.guardminotaurMD, ConfigMobs.guardminotaurGD,
            ConfigMobs.lightminotaurAR + ConfigMobs.dreadminotaurAR)
            .setGrabs(ConfigMobs.guardminotaurDC, ConfigMobs.guardminotaurGC, ConfigMobs.guardminotaurTC)
            .setBeserk(ConfigMobs.guardminotaurBT, ConfigMobs.guardminotaurBD, ConfigMobs.guardminotaurGCB)
            .setIntelligence(1);
    public static MinotaurBreed NETHERELITE = new MinotaurBreed(nether, "dredminotaur_elite", "EliteNether", "steel",
            50, ConfigMobs.eliteminotaurHP, ConfigMobs.eliteminotaurMD, ConfigMobs.eliteminotaurGD,
            ConfigMobs.mediumminotaurAR + ConfigMobs.dreadminotaurAR)
            .setGrabs(ConfigMobs.eliteminotaurDC, ConfigMobs.eliteminotaurGC, ConfigMobs.eliteminotaurTC)
            .setBeserk(ConfigMobs.eliteminotaurBT, ConfigMobs.eliteminotaurBD, ConfigMobs.eliteminotaurGCB)
            .setIntelligence(2);
    public static MinotaurBreed NETHERBOSS = new MinotaurBreed(nether, "dredminotaur_boss", "BossNether", "obsidian",
            150, ConfigMobs.bossminotaurHP, ConfigMobs.bossminotaurMD, ConfigMobs.bossminotaurGD,
            ConfigMobs.heavyminotaurAR + ConfigMobs.dreadminotaurAR)
            .setGrabs(ConfigMobs.bossminotaurDC, ConfigMobs.bossminotaurGC, ConfigMobs.bossminotaurTC)
            .setBeserk(ConfigMobs.bossminotaurBT, ConfigMobs.bossminotaurBD, ConfigMobs.bossminotaurGCB)
            .setIntelligence(3).throwBombs();
    private static Random rand = new Random();
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

    public int experienceValue;
    public boolean isSpecial = true;
    public String weaponTier;
    public int armour_rating;
    public boolean throwsBombs = false;

    public MinotaurBreed(ArrayList pool, String name, String tex, String weapons, int xp, float health,
                         float poundDamage, float goreDamage, int AR) {
        this.pool = pool;
        pool.add(this);
        this.name = name;
        this.tex = tex;
        this.health = health;
        this.poundDamage = poundDamage;
        this.goreDamage = goreDamage;
        this.beserkDamage = poundDamage * 1.5F;
        this.weaponTier = weapons;
        this.experienceValue = xp;
        this.armour_rating = AR;
    }

    public static int getRandomMinotaur(int species) {
        return getRandomMinotaur(species == 1 ? MinotaurBreed.nether : MinotaurBreed.normal);
    }

    public static int getRandomMinotaur(ArrayList<MinotaurBreed> pool) {
        if (pool.size() <= 1) {
            return 0;
        }
        return rand.nextInt(pool.size());
    }

    public static MinotaurBreed getBreed(int species, int id) {
        return getBreed(species == 2 ? MinotaurBreed.frost : species == 1 ? MinotaurBreed.nether : MinotaurBreed.normal,
                id);
    }

    public static MinotaurBreed getBreed(ArrayList<MinotaurBreed> pool, int id) {
        if (id >= pool.size()) {
            id = (pool.size() - 1);
        }
        return pool.get(id);
    }

    public static int getEnvironment(EntityMinotaur mob) {
        return getEnvironment(mob.worldObj, (int) mob.posX, (int) mob.posZ, mob.dimension);
    }

    public static int getEnvironment(World world, int x, int z, int dimension) {
        if (dimension == -1)
            return 1;// NETHER

        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
        if (biome != null) {
            if (biome.getEnableSnow() || biome.temperature <= 0.25F
                    || biome.getTempCategory() == BiomeGenBase.TempCategory.COLD) {
                return 2;// FROST
            }
        }
        return 0;// BROWN
    }

    /**
     * What environment is a particular structure
     */
    public static int getEnvironment(String subtype) {
        return subtype.equalsIgnoreCase("Frost") ? (byte) 2 : subtype.equalsIgnoreCase("Nether") ? (byte) 1 : (byte) 0;
    }

    /**
     * Set beserk stats
     *
     * @param threshold  the percent (0-100) health where it triggers (Default 25%)
     * @param damage     (The damage done by melee)
     * @param grabChance the percent (0-100) chance to grab enemies when beserked (Default
     *                   25%)
     */
    public MinotaurBreed setBeserk(int threshold, float damage, int grabChance) {
        this.beserkThreshold = threshold;
        this.beserkDamage = damage;
        this.grabChanceBeserk = grabChance;
        return this;
    }

    /**
     * @param grabChance the percent (0-100) chance to disarm enemies with a power attack
     *                   (Default 10%)
     * @param grabChance the percent (0-100) chance to grab enemies (Default 10%)
     * @param grabChance the percent (0-100) chance to throw grabbed enemies when hit
     *                   (Default 20%)
     * @return
     */
    public MinotaurBreed setGrabs(int disarmChance, int grabChance, int throwChance) {
        this.disarmChance = disarmChance;
        this.grabChance = grabChance;
        this.throwChance = throwChance;
        return this;
    }

    /**
     * Set the intelligence for the mob 0 For docile... 1 For basic... 2 For Orderly
     */
    public MinotaurBreed setIntelligence(int level) {
        this.intelligenceLvl = level;
        return this;
    }

    /**
     * Enables spawning and despawning
     */
    public MinotaurBreed setLesser() {
        this.isSpecial = false;
        return this;
    }

    public MinotaurBreed throwBombs() {
        this.throwsBombs = true;
        return this;
    }
}
