package minefantasy.mfr.entity.mob;

import minefantasy.mfr.config.ConfigMobs;
import minefantasy.mfr.init.MineFantasyMaterials;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;

public class MinotaurBreed {
	public static final ArrayList<MinotaurBreed> NORMAL_BREEDS = new ArrayList<>();
	public static final ArrayList<MinotaurBreed> FROST_BREEDS = new ArrayList<>();
	public static final ArrayList<MinotaurBreed> NETHER_BREEDS = new ArrayList<>();

	public static MinotaurBreed BROWN = new MinotaurBreed("minotaur", "_brown", null, 10, ConfigMobs.minotaurHP, ConfigMobs.minotaurMD, ConfigMobs.minotaurGD, 0).setGrabs(ConfigMobs.minotaurDC, ConfigMobs.minotaurGC, ConfigMobs.minotaurTC).setBeserk(ConfigMobs.minotaurBT, ConfigMobs.minotaurBD, ConfigMobs.minotaurGCB).setLesser();
	public static MinotaurBreed BROWN_GUARD = new MinotaurBreed("minotaur_guard", "_guard_brown", MineFantasyMaterials.Names.IRON, 20, ConfigMobs.guardminotaurHP, ConfigMobs.guardminotaurMD, ConfigMobs.guardminotaurGD, ConfigMobs.lightminotaurAR).setGrabs(ConfigMobs.guardminotaurDC, ConfigMobs.guardminotaurGC, ConfigMobs.guardminotaurTC).setBeserk(ConfigMobs.guardminotaurBT, ConfigMobs.guardminotaurBD, ConfigMobs.guardminotaurGCB).setIntelligence(1);
	public static MinotaurBreed BROWN_ELITE = new MinotaurBreed("minotaur_elite", "_elite_brown", MineFantasyMaterials.Names.STEEL, 40, ConfigMobs.eliteminotaurHP, ConfigMobs.eliteminotaurMD, ConfigMobs.eliteminotaurGD, ConfigMobs.mediumminotaurAR).setGrabs(ConfigMobs.eliteminotaurDC, ConfigMobs.eliteminotaurGC, ConfigMobs.eliteminotaurTC).setBeserk(ConfigMobs.eliteminotaurBT, ConfigMobs.eliteminotaurBD, ConfigMobs.eliteminotaurGCB).setIntelligence(2);
	public static MinotaurBreed BROWN_BOSS = new MinotaurBreed("minotaur_boss", "_boss_brown", MineFantasyMaterials.Names.OBSIDIAN, 100, ConfigMobs.bossminotaurHP, ConfigMobs.bossminotaurMD, ConfigMobs.bossminotaurGD, ConfigMobs.heavyminotaurAR).setGrabs(ConfigMobs.bossminotaurDC, ConfigMobs.bossminotaurGC, ConfigMobs.bossminotaurTC).setBeserk(ConfigMobs.bossminotaurBT, ConfigMobs.bossminotaurBD, ConfigMobs.bossminotaurGCB).setIntelligence(3).throwBombs();
	public static MinotaurBreed FROST = new MinotaurBreed("frost_minotaur", "_white", null, 15, ConfigMobs.minotaurHP, ConfigMobs.minotaurMD, ConfigMobs.minotaurGD, ConfigMobs.frostminotaurAR).setGrabs(ConfigMobs.minotaurDC, ConfigMobs.minotaurGC, ConfigMobs.minotaurTC).setBeserk(ConfigMobs.minotaurBT, ConfigMobs.minotaurBD, ConfigMobs.minotaurGCB).setLesser();
	public static MinotaurBreed FROST_GUARD = new MinotaurBreed("frost_minotaur_guard", "_guard_white", MineFantasyMaterials.Names.IRON, 25, ConfigMobs.guardminotaurHP, ConfigMobs.guardminotaurMD, ConfigMobs.guardminotaurGD, ConfigMobs.lightminotaurAR + ConfigMobs.frostminotaurAR).setGrabs(ConfigMobs.guardminotaurDC, ConfigMobs.guardminotaurGC, ConfigMobs.guardminotaurTC).setBeserk(ConfigMobs.guardminotaurBT, ConfigMobs.guardminotaurBD, ConfigMobs.guardminotaurGCB).setIntelligence(1);
	public static MinotaurBreed FROST_ELITE = new MinotaurBreed("frost_minotaur_elite", "_elite_white", MineFantasyMaterials.Names.STEEL, 50, ConfigMobs.eliteminotaurHP, ConfigMobs.eliteminotaurMD, ConfigMobs.eliteminotaurGD, ConfigMobs.mediumminotaurAR + ConfigMobs.frostminotaurAR).setGrabs(ConfigMobs.eliteminotaurDC, ConfigMobs.eliteminotaurGC, ConfigMobs.eliteminotaurTC).setBeserk(ConfigMobs.eliteminotaurBT, ConfigMobs.eliteminotaurBD, ConfigMobs.eliteminotaurGCB).setIntelligence(2);
	public static MinotaurBreed FROST_BOSS = new MinotaurBreed("frost_minotaur_boss", "_boss_white", MineFantasyMaterials.Names.OBSIDIAN, 120, ConfigMobs.bossminotaurHP, ConfigMobs.bossminotaurMD, ConfigMobs.bossminotaurGD, ConfigMobs.heavyminotaurAR + ConfigMobs.frostminotaurAR).setGrabs(ConfigMobs.bossminotaurDC, ConfigMobs.bossminotaurGC, ConfigMobs.bossminotaurTC).setBeserk(ConfigMobs.bossminotaurBT, ConfigMobs.bossminotaurBD, ConfigMobs.bossminotaurGCB).setIntelligence(3).throwBombs();
	public static MinotaurBreed NETHER = new MinotaurBreed("dread_minotaur", "_nether", null, 15, ConfigMobs.minotaurHP, ConfigMobs.minotaurMD, ConfigMobs.minotaurGD, ConfigMobs.dreadminotaurAR).setGrabs(ConfigMobs.minotaurDC, ConfigMobs.minotaurGC, ConfigMobs.minotaurTC).setBeserk(ConfigMobs.minotaurBT, ConfigMobs.minotaurBD, ConfigMobs.minotaurGCB).setLesser();
	public static MinotaurBreed NETHER_GUARD = new MinotaurBreed("dread_minotaur_guard", "_guard_nether", MineFantasyMaterials.Names.IRON, 25, ConfigMobs.guardminotaurHP, ConfigMobs.guardminotaurMD, ConfigMobs.guardminotaurGD, ConfigMobs.lightminotaurAR + ConfigMobs.dreadminotaurAR).setGrabs(ConfigMobs.guardminotaurDC, ConfigMobs.guardminotaurGC, ConfigMobs.guardminotaurTC).setBeserk(ConfigMobs.guardminotaurBT, ConfigMobs.guardminotaurBD, ConfigMobs.guardminotaurGCB).setIntelligence(1);
	public static MinotaurBreed NETHER_ELITE = new MinotaurBreed("dread_minotaur_elite", "_elite_nether", MineFantasyMaterials.Names.STEEL, 50, ConfigMobs.eliteminotaurHP, ConfigMobs.eliteminotaurMD, ConfigMobs.eliteminotaurGD, ConfigMobs.mediumminotaurAR + ConfigMobs.dreadminotaurAR).setGrabs(ConfigMobs.eliteminotaurDC, ConfigMobs.eliteminotaurGC, ConfigMobs.eliteminotaurTC).setBeserk(ConfigMobs.eliteminotaurBT, ConfigMobs.eliteminotaurBD, ConfigMobs.eliteminotaurGCB).setIntelligence(2);
	public static MinotaurBreed NETHER_BOSS = new MinotaurBreed("dread_minotaur_boss", "_boss_nether", MineFantasyMaterials.Names.OBSIDIAN, 150, ConfigMobs.bossminotaurHP, ConfigMobs.bossminotaurMD, ConfigMobs.bossminotaurGD, ConfigMobs.heavyminotaurAR + ConfigMobs.dreadminotaurAR).setGrabs(ConfigMobs.bossminotaurDC, ConfigMobs.bossminotaurGC, ConfigMobs.bossminotaurTC).setBeserk(ConfigMobs.bossminotaurBT, ConfigMobs.bossminotaurBD, ConfigMobs.bossminotaurGCB).setIntelligence(3).throwBombs();

	public String name;
	public String tex;
	public float health;
	public float poundDamage;
	public float goreDamage;
	public float beserkDamage;
	public int beserkThreshold = 40;
	public int grabChance = 10;
	public int grabChanceBeserk = 25;
	public int throwChance = 20;
	public int disarmChance = 10;
	public int intelligenceLvl = 0;

	public int experienceValue;
	public boolean isSpecial = true;
	public String weaponTier;
	public int armour_rating;
	public boolean throwsBombs = false;

	static {
		//REGISTER BREEDS

		//Normal
		NORMAL_BREEDS.add(BROWN);
		NORMAL_BREEDS.add(BROWN_GUARD);
		NORMAL_BREEDS.add(BROWN_BOSS);
		NORMAL_BREEDS.add(BROWN_ELITE);

		//Frost
		FROST_BREEDS.add(FROST);
		FROST_BREEDS.add(FROST_GUARD);
		FROST_BREEDS.add(FROST_BOSS);
		FROST_BREEDS.add(FROST_ELITE);

		NETHER_BREEDS.add(NETHER);
		NETHER_BREEDS.add(NETHER_GUARD);
		NETHER_BREEDS.add(NETHER_BOSS);
		NETHER_BREEDS.add(NETHER_ELITE);
	}

	public MinotaurBreed(String name, String tex, String weapons, int xp, float health, float poundDamage, float goreDamage, int AR) {
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

	public static MinotaurBreed getBreed(int species, int id) {
		return getBreed(species == 2 ? MinotaurBreed.FROST_BREEDS : species == 1 ? MinotaurBreed.NETHER_BREEDS : MinotaurBreed.NORMAL_BREEDS, id);
	}

	public static MinotaurBreed getBreed(ArrayList<MinotaurBreed> pool, int id) {
		if (id >= pool.size()) {
			id = (pool.size() - 1);
		}
		return pool.get(id);
	}

	public static int getEnvironment(EntityMinotaur mob) {
		return getEnvironment(mob.world, (int) mob.posX, (int) mob.posZ, mob.dimension);
	}

	public static int getEnvironment(World world, int x, int z, int dimension) {
		if (dimension == -1)
			return 1;// NETHER

		Biome biome = world.getBiome(new BlockPos(x, 0, z));
		if (biome != null) {
			if (biome.getEnableSnow() || biome.getDefaultTemperature() <= 0.25F
					|| biome.getTempCategory() == Biome.TempCategory.COLD) {
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
	 * @param disarmChance the percent (0-100) chance to disarm enemies with a power attack
	 *                     (Default 10%)
	 * @param grabChance   the percent (0-100) chance to grab enemies (Default 10%)
	 * @param throwChance  the percent (0-100) chance to throw grabbed enemies when hit
	 *                     (Default 20%)
	 * @return MinotaurBreed
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
