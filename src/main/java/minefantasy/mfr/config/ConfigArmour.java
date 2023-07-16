package minefantasy.mfr.config;

public class ConfigArmour extends ConfigurationBaseMF {
	public static final String CATEGORY_GENERAL = "General Armour Features";
	public static final String CATEGORY_BONUS = "Bonuses";
	public static final String CATEGORY_PENALTIES = "Penalties";
	public static final String CATEGORY_COGWORK = "Cogwork Features";
	public static boolean advancedDamageTypes = true;
	public static boolean resistArrow;
	public static float arrowDeflectChance = 1.0F;
	public static boolean shouldSlow = true;
	public static float minWeightSpeed = 10F;
	public static float slowRate = 1.0F;
	public static boolean useConfigIndirectDmg = true;
	public static int allowedBulk = 1;
	public static float rating_modifier = 1.0F;
	public static float health_modifier = 1.0F;
	public static boolean cogworkGrief;
	public static boolean cogworkJump;
	public static float cogworkFuelUnits;

	public ConfigArmour(String name) {
		super(name);
	}

	@Override
	protected void initializeCategories() {
		config.addCustomCategoryComment(CATEGORY_GENERAL, "Controls Armor Overall Features");
		config.addCustomCategoryComment(CATEGORY_BONUS, "Configs related to bonuses to armor");
		config.addCustomCategoryComment(CATEGORY_PENALTIES, "Configs related to penalties to armor");
		config.addCustomCategoryComment(CATEGORY_COGWORK, "Configs related to Cogwork Armor");
	}

	@Override
	protected void initializeValues() {
		String AADesc = "This feature Sets supported armours to calculate weapons differently: \n"
				+ "Damage is divided into cutting and blunt, and MF armours will alter their ratings depending \n"
				+ "Both weapons and ranged entities can be registered to deliver a certain ratio \n"
				+ "Armours can't be registered, all coding needs to be hardwired into the code themselves \n"
				+ "So this means mod armours can not support this, they need to have a system built-in to their own coding"
				+ "But this ratio only effects armours that are told to. so it should have no effect on unsupported armours, being balanced as usual \n"
				+ "Undefined weapon damages however have no effect either, but it will try and guess \n"
				+ "This will also change change axes and blunt weapons to match blade damage (since armour is vulnerable to blunt";
		advancedDamageTypes = Boolean.parseBoolean(config.get(CATEGORY_GENERAL, "Advanced Armour Calculator", true,
				AADesc).getString());
		resistArrow = config.get(CATEGORY_BONUS, "Deflect Arrows", true,
				"With this option; arrows have a chance to deflect off armour. It compares the arrow damage and armour rating. Chain and plate have higher rates. Arrows bounced off can hurt enemies").getBoolean();
		arrowDeflectChance = Float.parseFloat(config.get(CATEGORY_BONUS, "Deflect Arrows Modifier", 1.0F,
				"This modifies the chance for arrows to deflect off armour: Increasing this increases the deflect chance, decreasing decreases the chance, 0 means it's impossible").getString());
		shouldSlow = config.get(CATEGORY_PENALTIES, "Armour Slow Movement", true,
				"With this: Heavier armours slow your movement down acrosss both walking and sprinting. Speed is affected by design and material").getBoolean();
		minWeightSpeed = Float.parseFloat(config.get(CATEGORY_PENALTIES, "Min armour weigh speed", 10F,
						"The minimal speed percent capable from weight (10 = 10%), MF armours don't go lower than 75% anyway").getString());
		slowRate = Float.parseFloat(config.get(CATEGORY_PENALTIES, "Slowdown Rate", 1.0F,
						"A modifier for the rate armours slow down movement, increasing this slows you more, decreasing it slows less, just keep it above 0").getString());
		useConfigIndirectDmg = Boolean.parseBoolean(config.get("Misc", "Use config indirect damage",
						true,
						"Allows config for indirect projectiles, disable if you get problems when getting hit by modded projectiles").getString());
		cogworkGrief = Boolean.parseBoolean(config.get(CATEGORY_COGWORK, "Cogwork Grief", true,
				"Should cogwork armour cause environmental damage when impact landing").getString());
		cogworkJump = Boolean.parseBoolean(config.get(CATEGORY_COGWORK, "Cogwork Jump", true,
				"Should cogwork armour be able to jump").getString());
		cogworkFuelUnits = Float.parseFloat(
				config.get(CATEGORY_COGWORK, "Cogwork Fuel Modifier", 1F, "Modify the amount of fuel added to cogworks").getString());
		health_modifier = Float.parseFloat(config.get(CATEGORY_COGWORK, "Cogwork Durability Modifier",
				1.0F, "Modify the relative durability of cogwork armour").getString());
		rating_modifier = Float.parseFloat(config.get(CATEGORY_COGWORK, "Cogwork Armour Modifier", 1.0F,
				"Modify the relative armour rating of cogwork armour").getString());
		allowedBulk = Integer.parseInt(config.get(CATEGORY_COGWORK, "Worn Apparel Restrict", 0,
						"-1: can't wear armour with cogwork, 0=Can enter cogwork with light armour, 1=can enter with medium, 2=can enter in any armour")
				.getString());
	}
}
