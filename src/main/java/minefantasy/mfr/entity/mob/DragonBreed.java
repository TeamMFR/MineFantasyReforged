package minefantasy.mfr.entity.mob;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import minefantasy.mfr.config.ConfigMobs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class DragonBreed {
	public static final ArrayList<DragonBreed> WHITE = new ArrayList<>();
	public static final ArrayList<DragonBreed> GREEN = new ArrayList<>();
	public static final ArrayList<DragonBreed> RED = new ArrayList<>();
	public static final ArrayList<DragonBreed> ASH = new ArrayList<>();

	public static final Table<Integer, String, DragonBreed> BREED_BY_TIER_AND_NAME = HashBasedTable.create();

	/*
	 * New System Breeds Red: Fire, plains and hills Green: Poison, swamps, forests
	 * and plains White: Frost, cold regions Black: Dire, nether
	 *
	 */
	public static DragonBreed RED_YOUNG = new DragonBreed(0, "dragon_young", "red", "dragon_red", DragonBreath.fire)
			.setCombat(ConfigMobs.youngdragonHP, 0.0F, 0F, ConfigMobs.youngdragonMD, 15, 200)
			.setProjectile(ConfigMobs.youngdragonFD, ConfigMobs.youngdragonFT, 1000, 0.05F);
	public static DragonBreed WHITE_YOUNG = new DragonBreed(0, "dragon_young", "white", "dragon_frost",
			DragonBreath.frost).setCombat(ConfigMobs.youngdragonHP, 0.0F, 0F, ConfigMobs.youngdragonMD, 25, 200)
			.setProjectile(ConfigMobs.youngdragonFD, ConfigMobs.youngdragonFT, 800, 0.05F);
	public static DragonBreed GREEN_YOUNG = new DragonBreed(0, "dragon_young", "green", "dragon_green",
			DragonBreath.poison).setCombat(ConfigMobs.youngdragonHP, 0.0F, 0F, ConfigMobs.youngdragonMD, 35, 100)
			.setProjectile(ConfigMobs.youngdragonFD, ConfigMobs.youngdragonFT, 400, 0.05F);
	public static DragonBreed ASH_YOUNG = new DragonBreed(0, "dragon_young", "ash", "dragon_ash", DragonBreath.ash)
			.setCombat(ConfigMobs.youngdragonHP, 0.0F, 0F, ConfigMobs.youngdragonMD, 15, 200)
			.setProjectile(ConfigMobs.youngdragonFD, ConfigMobs.youngdragonFT, 200, 0.05F).setBlind();

	public static DragonBreed RED_NORMAL = new DragonBreed(1, "dragon", "red", "dragon_red", DragonBreath.fire)
			.setCombat(ConfigMobs.dragonHP, 2.0F, 0F, ConfigMobs.dragonMD, 15, 300)
			.setProjectile(ConfigMobs.dragonFD, ConfigMobs.dragonFT, 500, 0.1F);
	public static DragonBreed WHITE_NORMAL = new DragonBreed(1, "dragon", "white", "dragon_frost", DragonBreath.frost)
			.setCombat(ConfigMobs.dragonHP, 2.0F, 0F, ConfigMobs.dragonMD, 25, 300)
			.setProjectile(ConfigMobs.dragonFD, ConfigMobs.dragonFT, 400, 0.1F);
	public static DragonBreed GREEN_NORMAL = new DragonBreed(1, "dragon", "green", "dragon_green", DragonBreath.poison)
			.setCombat(ConfigMobs.dragonHP, 2.0F, 0F, ConfigMobs.dragonMD, 35, 200)
			.setProjectile(ConfigMobs.dragonFD, ConfigMobs.dragonFT, 300, 0.1F);
	public static DragonBreed ASH_NORMAL = new DragonBreed(1, "dragon", "ash", "dragon_ash", DragonBreath.ash)
			.setCombat(ConfigMobs.dragonHP, 2.0F, 0F, ConfigMobs.dragonMD, 15, 300)
			.setProjectile(ConfigMobs.dragonFD, ConfigMobs.dragonFT, 150, 0.1F).setBlind();

	public static DragonBreed RED_MATURE = new DragonBreed(2, "dragon_mature", "red", "dragon_red", DragonBreath.fire)
			.setCombat(ConfigMobs.diredragonHP, 2.0F, 1F, ConfigMobs.diredragonMD, 15, 300)
			.setProjectile(ConfigMobs.diredragonFD, ConfigMobs.diredragonFT, 400, 0.25F);
	public static DragonBreed WHITE_MATURE = new DragonBreed(2, "dragon_mature", "white", "dragon_frost", DragonBreath.frost)
			.setCombat(ConfigMobs.diredragonHP, 2.0F, 1F, ConfigMobs.diredragonMD, 25, 300)
			.setProjectile(ConfigMobs.diredragonFD, ConfigMobs.diredragonFT, 300, 0.25F);
	public static DragonBreed GREEN_MATURE = new DragonBreed(2, "dragon_mature", "green", "dragon_green", DragonBreath.poison)
			.setCombat(ConfigMobs.diredragonHP, 2.0F, 1F, ConfigMobs.diredragonMD, 30, 200)
			.setProjectile(ConfigMobs.diredragonFD, ConfigMobs.diredragonFT, 200, 0.25F);
	public static DragonBreed ASH_MATURE = new DragonBreed(2, "dragon_mature", "ash", "dragon_ash", DragonBreath.ash)
			.setCombat(ConfigMobs.diredragonHP, 2.0F, 1F, ConfigMobs.diredragonMD, 15, 300)
			.setProjectile(ConfigMobs.diredragonFD, ConfigMobs.diredragonFT, 100, 0.25F).setBlind();

	public static DragonBreed RED_ELDER = new DragonBreed(3, "dragon_elder", "red", "dragon_red", DragonBreath.fire)
			.setCombat(ConfigMobs.elderdragonHP, 2.0F, 2F, ConfigMobs.elderdragonMD, 15, 300)
			.setProjectile(ConfigMobs.elderdragonFD, ConfigMobs.elderdragonFT, 400, 0.35F);
	public static DragonBreed WHITE_ELDER = new DragonBreed(3, "dragon_elder", "white", "dragon_frost",
			DragonBreath.frost).setCombat(ConfigMobs.elderdragonHP, 2.0F, 2F, ConfigMobs.elderdragonMD, 25, 300)
			.setProjectile(ConfigMobs.elderdragonFD, ConfigMobs.elderdragonFT, 300, 0.35F);
	public static DragonBreed GREEN_ELDER = new DragonBreed(3, "dragon_elder", "green", "dragon_green",
			DragonBreath.poison).setCombat(ConfigMobs.elderdragonHP, 2.0F, 2F, ConfigMobs.elderdragonMD, 35, 200)
			.setProjectile(ConfigMobs.elderdragonFD, ConfigMobs.elderdragonFT, 200, 0.35F);
	public static DragonBreed ASH_ELDER = new DragonBreed(3, "dragon_elder", "ash", "dragon_ash", DragonBreath.ash)
			.setCombat(ConfigMobs.elderdragonHP, 2.0F, 2F, ConfigMobs.elderdragonMD, 15, 300)
			.setProjectile(ConfigMobs.elderdragonFD, ConfigMobs.elderdragonFT, 100, 0.35F).setBlind();

	public static DragonBreed ANCIENT = new DragonBreed(4, "dragon_ancient", "dire", "dragon_ancient", DragonBreath.fire)
			.setCombat(ConfigMobs.ancientdragonHP, 2.0F, 5F, ConfigMobs.ancientdragonMD, 20, 300)
			.setProjectile(ConfigMobs.ancientdragonFD, ConfigMobs.ancientdragonFT, 400, 1.0F);

	private static final Random rand = new Random();

	static {
		RED.add(RED_YOUNG);
		RED.add(RED_NORMAL);
		RED.add(RED_MATURE);
		RED.add(RED_ELDER);

		GREEN.add(GREEN_YOUNG);
		GREEN.add(GREEN_NORMAL);
		GREEN.add(GREEN_MATURE);
		GREEN.add(GREEN_ELDER);

		WHITE.add(WHITE_YOUNG);
		WHITE.add(WHITE_NORMAL);
		WHITE.add(WHITE_MATURE);
		WHITE.add(WHITE_ELDER);

		ASH.add(ASH_YOUNG);
		ASH.add(ASH_NORMAL);
		ASH.add(ASH_MATURE);
		ASH.add(ASH_ELDER);

		BREED_BY_TIER_AND_NAME.put(RED_YOUNG.tier, RED_YOUNG.breedName, RED_YOUNG);
		BREED_BY_TIER_AND_NAME.put(GREEN_YOUNG.tier, GREEN_YOUNG.breedName, GREEN_YOUNG);
		BREED_BY_TIER_AND_NAME.put(WHITE_YOUNG.tier, WHITE_YOUNG.breedName, WHITE_YOUNG);
		BREED_BY_TIER_AND_NAME.put(ASH_YOUNG.tier, ASH_YOUNG.breedName, ASH_YOUNG);

		BREED_BY_TIER_AND_NAME.put(RED_NORMAL.tier, RED_NORMAL.breedName, RED_NORMAL);
		BREED_BY_TIER_AND_NAME.put(GREEN_NORMAL.tier, GREEN_NORMAL.breedName, GREEN_NORMAL);
		BREED_BY_TIER_AND_NAME.put(WHITE_NORMAL.tier, WHITE_NORMAL.breedName, WHITE_NORMAL);
		BREED_BY_TIER_AND_NAME.put(ASH_NORMAL.tier, ASH_NORMAL.breedName, ASH_NORMAL);

		BREED_BY_TIER_AND_NAME.put(RED_MATURE.tier, RED_MATURE.breedName, RED_MATURE);
		BREED_BY_TIER_AND_NAME.put(GREEN_MATURE.tier, GREEN_MATURE.breedName, GREEN_MATURE);
		BREED_BY_TIER_AND_NAME.put(WHITE_MATURE.tier, WHITE_MATURE.breedName, WHITE_MATURE);
		BREED_BY_TIER_AND_NAME.put(ASH_MATURE.tier, ASH_MATURE.breedName, ASH_MATURE);

		BREED_BY_TIER_AND_NAME.put(RED_ELDER.tier, RED_ELDER.breedName, RED_ELDER);
		BREED_BY_TIER_AND_NAME.put(GREEN_ELDER.tier, GREEN_ELDER.breedName, GREEN_ELDER);
		BREED_BY_TIER_AND_NAME.put(WHITE_ELDER.tier, WHITE_ELDER.breedName, WHITE_ELDER);
		BREED_BY_TIER_AND_NAME.put(ASH_ELDER.tier, ASH_ELDER.breedName, ASH_ELDER);

		BREED_BY_TIER_AND_NAME.put(ANCIENT.tier, ANCIENT.breedName, ANCIENT);
	}


	public int tier;
	public String name;
	public String breedName;
	public DragonBreath rangedAttack;
	public String tex;
	public float health = 200;
	public float regenRate = 0;
	public float meleeDamage = 8;
	public float fireDamage = 5;
	public float pyro = 0.1F;
	public int disengageChance = 200;
	public int fireTimer = 50;
	public int coolTimer = 200;
	public int meleeSpeed = 20;
	public float DT = 2.0F;
	private boolean isBlind = false;

	public DragonBreed(int tier, String name, String breedname, String tex, DragonBreath breath) {
		this.tier = tier;
		this.name = name;
		this.breedName = breedname;
		this.tex = tex;
		this.rangedAttack = breath;
	}

	public static DragonBreed getBreed(int tier, String breedName) {
		return BREED_BY_TIER_AND_NAME.get(tier, breedName);
	}

	public static String getRandomDragon(EntityDragon dragon, int tier) {
		Biome biome = dragon.world.getBiome(new BlockPos((int) (dragon.posX), 0, (int) (dragon.posZ)));
		Optional<DragonBreed> breed = getBreedForBiome(biome, dragon.world.isRaining(), dragon.dimension).stream()
				.filter(dragonBreed -> dragonBreed.tier == tier).distinct().findFirst();

		return breed.map(dragonBreed -> dragonBreed.breedName).orElse(null);
	}

	private static ArrayList<DragonBreed> getBreedForBiome(Biome biome, boolean isRaining, int dimension) {
		if (dimension == -1) {
			return rand.nextBoolean() ? ASH : RED;
		}

		Biome.TempCategory category = biome.getTempCategory();
		if (!biome.isSnowyBiome() && !biome.canRain()) {
			return ASH;// Ash
		}
		if (biome.getEnableSnow() || biome.getDefaultTemperature() <= 0.25F || category == Biome.TempCategory.COLD) {
			return WHITE;// Frost
		}
		if (biome.isHighHumidity() || (category == Biome.TempCategory.MEDIUM && isRaining)) {
			return GREEN;// Poison
		}
		if (category == Biome.TempCategory.OCEAN) {
			return GREEN;// Poison
		}
		return RED;// Fire
	}

	public DragonBreed setCombat(float health, float DT, float regenRate, float meleeDamage, int meleeSpeed,
			int disengageChance) {
		this.health = health;
		this.regenRate = regenRate;
		this.meleeDamage = meleeDamage;
		this.meleeSpeed = meleeSpeed;
		this.disengageChance = disengageChance;
		this.DT = DT;
		return this;
	}

	public DragonBreed setProjectile(float fireDamage, int fireTimer, int coolTimer, float pyro) {
		this.fireDamage = fireDamage;
		this.fireTimer = fireTimer;
		this.coolTimer = coolTimer;
		this.pyro = pyro;
		return this;
	}

	public DragonBreed setBlind() {
		isBlind = true;
		return this;
	}

	public boolean isBlind() {
		return isBlind;
	}
}
