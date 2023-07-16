package minefantasy.mfr.config;

public class ConfigStamina extends ConfigurationBaseMF {
	public static String CATEGORY_VALUES = "Value Modifiers";
	public static boolean isSystemActive = true;
	public static float defaultMax = 100F;
	public static float decayModifierCfg = 1.0F;
	public static float decayModifierBase = 0.5F;
	public static float configRegenModifier = 1.0F;
	public static float pauseModifier = 1.0F;
	public static float configArmourWeightModifier = 1.0F;
	public static float configBulk = 1.0F;
	public static boolean scaleDifficulty = true;

	public static boolean levelUp = false;

	public static float levelAmount = 5F;

	public static float exhaustDamage;
	public static float weaponModifier;
	public static float sprintModifier;
	public static float dodgeForceModifier;
	public static float dodgeCostModifier;
	public static int dodgeShieldCost;
	public static int fullRegenSeconds;
	public static int eatDelayModifier;
	public static int fatAccumulationModifier;
	public static int fatThreshold;
	public static float weaponDrain;
	public static float bowModifier;
	public static float miningSpeed;

	public static String OPTION = "Options";

	public static boolean affectSpeed;
	public static boolean affectMining;

	public static String OTHER = "Misc";

	public ConfigStamina(String name) {
		super(name);
	}

	@Override
	protected void initializeCategories() {
		config.addCustomCategoryComment(CATEGORY_VALUES, "Controls Stamina Values");
	}

	@Override
	protected void initializeValues() {
		isSystemActive = Boolean.parseBoolean(config.get("##Activate Stamina System##", "Is enabled", true,
				"This is the main switch for the stamina bar and all it's features. Setting false disables the system entirely")
				.getString());

		decayModifierCfg = Float
				.parseFloat(config.get(CATEGORY_VALUES, "Decay Modifier", 1.0F, "This modifies the rate of decay").getString());

		configRegenModifier = Float
				.parseFloat(config.get(CATEGORY_VALUES, "Regen Modifier", 1.0F, "This modifies the rate of regen").getString());

		pauseModifier = Float.parseFloat(
				config.get(CATEGORY_VALUES, "Idle Modifier", 1.0F, "This modifies the time between decaying and regenning back")
						.getString());

		configBulk = Float.parseFloat(config.get(CATEGORY_VALUES, "Bulk Regen Modifier", 1.0F,
				"This is the rate that worn apparel slows regen: Most items bring it down by 25%, plate is more scale/leather is less")
				.getString());

		scaleDifficulty = Boolean.parseBoolean(config.get(CATEGORY_VALUES, "Difficulty Scale Decay", false,
				"This makes difficulty change your decay rate across all fields: peaceful(-50%), easy(-25%), normal(base), hard(+25%)")
				.getString());

		defaultMax = Float.parseFloat(config.get(CATEGORY_VALUES, "Max stamina base", 100F,
				"This is where your stamina starts on spawning, Note that when changed: it only applies on death or new worlds")
				.getString());

		weaponModifier = Float.parseFloat(
				config.get(CATEGORY_VALUES, "Weapon Modifier", 1.0F, "This modifies the amount using weapons influences stamina")
						.getString());

		sprintModifier = Float.parseFloat(
				config.get(CATEGORY_VALUES, "Sprint Modifier", 1.0F, "Modify how fast sprinting decays stamina").getString());

		dodgeForceModifier = Float.parseFloat(
				config.get(CATEGORY_VALUES, "Dodge Force Modifier", 1.0F,
						"Modify how much force is in a dodge. 0.5 is half the force of the default dodge").getString());

		dodgeCostModifier = Float.parseFloat(
				config.get(CATEGORY_VALUES, "Dodge Stamina Cost Modifier", 1.0F,
						"Modify how much stamina it costs to dodge. 0.5 is half the cost of the default dodge, 2 is twice the cost ").getString());

		dodgeShieldCost = Integer.parseInt(
				config.get(CATEGORY_VALUES, "Dodge Shield Stamina cost", 100, "Modify how much dodging with a shield costs in stamina points.").getString());

		fullRegenSeconds = Integer.parseInt(config.get(CATEGORY_VALUES, "Regen Time", 15,
				"Base time(seconds) until the metre is refilled(this is the base, other variables will be modified)")
				.getString());

		eatDelayModifier = Integer.parseInt(config.get(CATEGORY_VALUES, "Eat Delay Modifier", 10,
						"Multiplied by the fatty content of food to delay the next time you can eat")
				.getString());

		fatAccumulationModifier = Integer.parseInt(config.get(CATEGORY_VALUES, "Fat Accumulation Modifier", 10,
						"Multiplied by the fatty content of food to determine how much fat accumulation occurs")
				.getString());

		fatThreshold = Integer.parseInt(config.get(CATEGORY_VALUES, "Fat Threshold", 400,
						"The threshold for what is considered 'fat' and begins to impact max stamina")
				.getString());

		weaponDrain = Float.parseFloat(config.get(CATEGORY_VALUES, "Exhausted Damage Modifier", 0.85F,
				"This is how being exhausted(empty stamina) influences your damage as a decimal. 0.85 = 85%. A value more than 1 increases the damage when out of stamina")
				.getString());

		bowModifier = Float.parseFloat(
				config.get(CATEGORY_VALUES, "Bow Time", 1.0F, "Modifies the rate drawing bows will drain stamina").getString());

		configArmourWeightModifier = Float.parseFloat(config
				.get(CATEGORY_VALUES, "Weight Modifier", 1.0F, "Modifies the amount weight of armour slows decay").getString());

		miningSpeed = Float.parseFloat(config
				.get(CATEGORY_VALUES, "Mining Modifier", 1.0F, "Modifies the rate block breaking drains stamina").getString());

		exhaustDamage = Float.parseFloat(config.get(CATEGORY_VALUES, "Exhausted Damage Modifier", 1.5F,
				"How much damage you take when out of stamina (1.5 = +50% more damage)").getString());

		affectSpeed = Boolean.parseBoolean(
				config.get(OPTION, "Affect Speed", true, "Slowness effect adds when out of stamina").getString());

		affectMining = Boolean.parseBoolean(config
				.get(OPTION, "Affect Mining", true, "Breaking blocks drains stamina, adds mining fatigue").getString());

		levelUp = Boolean.parseBoolean(
				config.get(OTHER, "Level Max Stamina", false, "This makes your experience bar increase max stamina")
						.getString());

		levelAmount = Float.parseFloat(config
				.get(OTHER, "Level Up Amount", 5F, "How much an experience level increases stamina max").getString());
	}

}
