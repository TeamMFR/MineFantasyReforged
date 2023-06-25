package minefantasy.mfr.config;

public class ConfigWeapon extends ConfigurationBaseMF {
	public static final String CATEGORY_PENALTIES = "Penalties";
	public static final String CATEGORY_BONUS = "Bonuses";
	public static final String CATEGORY_ANIMATIONS = "Animations";
	public static boolean shouldUseMfrCustomAnimations;
	public static boolean useBalance;
	public static boolean breakArrowsGround;
	public static float arrowBreakMod;
	public static boolean xpTrain;
	public static double blockSpeedMod;
	public static float vanillaRegularArrowSpreadDefault;
	public static float vanillaRegularArrowFirepowerMod;
	public static float vanillaSpecialArrowSpreadDefault;
	public static float vanillaSpecialArrowFirepowerMod;
	public static int vanillaArrowStackLimit;

	@Override
	protected void loadConfig() {
		shouldUseMfrCustomAnimations = Boolean.parseBoolean(config.get(CATEGORY_ANIMATIONS, "MFR Custom Animations", true,
				"Should MFR Custom animations for things like counterattacks after parries and spear and halberd animations occur, true or false. Disable if you are using a mod that adds custom animations for such things, as it mess them up."
				).getString());
		useBalance = Boolean.parseBoolean(config.get(CATEGORY_PENALTIES, "Heavy Weapon Balance", true,
				"This causes heavy weapons to throw the camera off angle. This is recommended as it gives a downside to avoid overpowering heavy weapons. With this active: spamming hits is difficult")
				.getString());

		blockSpeedMod = Double.parseDouble(config.get(CATEGORY_PENALTIES, "Parry Block Speed Penalty", 1.5F,
				"How much should blocking for parrying slow the player down. Greater values speed the player up, lower values slow the player down. 2 appears to be completely normal player walking speed")
				.getString());

		breakArrowsGround = Boolean.parseBoolean(
				config.get(CATEGORY_PENALTIES, "Arrow Ground Break", true, "Arrows can break when hitting the ground")
						.getString());

		arrowBreakMod = Float.parseFloat(config.get(CATEGORY_PENALTIES, "Arrow Break Chance modifier", 1.0F,
				"This modifies the rate that arrows break when hitting blocks, 1.0=normal higher numbers increases the break chance")
				.getString());

		vanillaRegularArrowSpreadDefault = Float.parseFloat(config.get(CATEGORY_PENALTIES, "Vanilla Regular Arrow Spread Default", 3.5F,
						"This controls the amount of inaccuracy vanilla regular arrows will have when fired by an MFR Bow. Vanilla Bow spread amount is 1, so higher than that is more inaccurate. For MFR Bows it is further modified by the type of MFR bow")
				.getString());

		vanillaRegularArrowFirepowerMod = Float.parseFloat(config.get(CATEGORY_PENALTIES, "Vanilla Regular Arrow Firepower Mod", 1.5F,
						"This controls how much the firepower (velocity) of vanilla regular arrows will be modified by when fired by an MFR Bow, like so: [firepower * mod]. Vanilla and MFR default modifier is 3")
				.getString());

		vanillaSpecialArrowSpreadDefault = Float.parseFloat(config.get(CATEGORY_PENALTIES, "Vanilla Special Arrow Spread Default", 1.25F,
						"This controls the amount of inaccuracy vanilla special arrows will have when fired by an MFR Bow. Vanilla Bow spread amount is 1, so higher than that is more inaccurate. For MFR Bows it is further modified by the type of MFR bow")
				.getString());

		vanillaSpecialArrowFirepowerMod = Float.parseFloat(config.get(CATEGORY_PENALTIES, "Vanilla Special Arrow Firepower Mod", 3F,
						"This controls how much the firepower (velocity) of vanilla special arrows will be modified by when fired by an MFR Bow, like so: [firepower * mod]. Vanilla and MFR default modifier is 3")
				.getString());
		vanillaArrowStackLimit = Integer.parseInt(config.get(CATEGORY_PENALTIES, "Vanilla Arrow stack limit", 32,
						"This controls how many vanilla arrows can fit in the MFR Bow ammo slot")
				.getString());

		xpTrain = Boolean.parseBoolean(config
				.get(CATEGORY_BONUS, "Training weapon xp", true,
						"This option allows training weapons to increase your xp level slowly when hitting with them")
				.getString());
	}

}
