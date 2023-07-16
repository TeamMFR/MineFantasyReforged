package minefantasy.mfr.config;

public class ConfigSpecials extends ConfigurationBaseMF {
	public static final String CATEGORY_ARCHERY = "Archery Features";
	public static final String CATEGORY_MISC = "Misc Features";
	public static boolean shouldResearchBookSpawn;
	public static boolean stickArrows;
	public static boolean dynamicArrows;
	public static String debug = "";
	public static boolean mythicOreSoundTooltip;
	public static Integer syringeCooldownValue;


	public ConfigSpecials(String name) {
		super(name);
	}

	@Override
	protected void initializeCategories() {
		config.addCustomCategoryComment(CATEGORY_ARCHERY, "Controls settings involving Archery");
		config.addCustomCategoryComment(CATEGORY_MISC, "Controls miscellaneous features");
	}

	@Override
	protected void initializeValues() {
		stickArrows = Boolean.parseBoolean(config.get(CATEGORY_ARCHERY, "Save Arrows", true,
				"With this active; arrows fired into enemies save, so they can be dropped on death").getString());
		dynamicArrows = Boolean.parseBoolean(config.get(CATEGORY_ARCHERY, "Dynamic arrow sync", true,
				"This is for whatever hosts the world (singleplayer, or servers); it increases the rate arrows sync their data for smoother rendering for players. This however increases packet traffic. If you have a lot of players on a server or a lot of arrows, disable this to help clean it up").getString());
		debug = config.get("###Enable Debug Mode###", "Debug Passcode", "").getString();

		mythicOreSoundTooltip = Boolean.parseBoolean(config.get(CATEGORY_MISC, "Mythic Ore Sound Tooltip", false,
				"When enabled, the Mythic ore will also send a written tooltip when ore emits a chiming sound.").getString());
		shouldResearchBookSpawn = Boolean.parseBoolean(config.get(CATEGORY_MISC, "Should Spawn Research Book", true,
				"Should the Research book be given to the Player on FIRST world load or not").getString());

		syringeCooldownValue = Integer.parseInt(config.get(CATEGORY_MISC, "Syringe Item Use Cooldown", 80,
						"This controls how long the cooldown for using (right clicking) the syringe on yourself")
				.getString());
	}

}
