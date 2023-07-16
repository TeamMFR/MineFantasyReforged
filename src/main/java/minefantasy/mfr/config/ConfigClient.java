package minefantasy.mfr.config;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ConfigClient extends ConfigurationBaseMF {

	public static final String CATEGORY_AESTHETIC = "Aesthetics";
	public static final String CATEGORY_GUI_STAMINA = "Stamina Bar Positioning";
	public static final String CATEGORY_GUI_ARMOR_RATING = "Armour Rating Positioning";
	public static final String CATEGORY_GUI_ARMOR_COUNT = "Arrow Count Positioning";
	public static final String CATEGORY_GUI_COGWORK_ARMOR_FUEL = "Clockwork Armour Fuel Positioning";
	public static final String CATEGORY_DEBUG = "Debug Info";
	public static final String CATEGORY_SOUND = "Sound Settings";
	public static boolean playBreath;
	public static boolean playHitsound;
	public static boolean playArmorSound;
	public static boolean customModel;
	public static boolean shouldUseMfrCustomAnimations;
	public static int stam_xOrient;
	public static int stam_yOrient;
	public static int stam_xPos;
	public static int stam_yPos;
	public static int stam_direction;
	public static int AR_xOrient;
	public static int AR_yOrient;
	public static int AR_xPos;
	public static int AR_yPos;
	public static int AC_xOrient;
	public static int AC_yOrient;
	public static int AC_xPos;
	public static int AC_yPos;
	public static int CF_xOrient;
	public static int CF_yOrient;
	public static int CF_xPos;
	public static int CF_yPos;
	public static boolean displayOreDict;

	public ConfigClient(String name) {
		super(name);
	}

	@Override
	protected void initializeCategories() {
		config.addCustomCategoryComment(CATEGORY_AESTHETIC, "Configs that control aesthetics on the Client Side");
		config.addCustomCategoryComment(CATEGORY_GUI_STAMINA, "Configs that control where the Stamina Bar is");
		config.addCustomCategoryComment(CATEGORY_GUI_ARMOR_RATING, "Configs that control where the Armor Rating is shown");
		config.addCustomCategoryComment(CATEGORY_GUI_ARMOR_COUNT, "Configs that control where the Armor Count is");
		config.addCustomCategoryComment(CATEGORY_GUI_COGWORK_ARMOR_FUEL, "Configs that control where the Cogwork Armor Fuel Bar is");
		config.addCustomCategoryComment(CATEGORY_DEBUG, "Configs that control debug options");
		config.addCustomCategoryComment(CATEGORY_SOUND, "Configs that control sound options");
	}

	@Override
	protected void initializeValues() {
		playBreath = Boolean.parseBoolean(config.get(CATEGORY_SOUND, "Make Breathe Sound", true,
				"[With Stamina System] Plays breath sounds when low on energy(sound may be annoying to some...)")
				.getString());
		playHitsound = Boolean.parseBoolean(config.get(CATEGORY_SOUND, "Make Hit Sound", true,
				"Plays sounds when hitting entities with different items").getString());
		playHitsound = Boolean.parseBoolean(config.get(CATEGORY_SOUND, "Make Hit Sound", true,
				"Plays sounds when hitting entities with different items").getString());

		playArmorSound = Boolean.parseBoolean(config.get(CATEGORY_SOUND, "Armor Sounds", true,
						"Should armor make sounds when over 50kg in weight")
				.getString());


		customModel = Boolean
				.parseBoolean(config
						.get(CATEGORY_AESTHETIC, "Custom Apparel Model", true,
								"Determines if some work apparel (like aprons and clothing) use special models")
						.getString());
		shouldUseMfrCustomAnimations = Boolean.parseBoolean(config.get(CATEGORY_AESTHETIC, "MFR Custom Animations", true,
				"Should MFR Custom animations for things like counterattacks after parries and spear and halberd animations occur, true or false. Disable if you are using a mod that adds custom animations for such things, as it mess them up."
		).getString());

		stam_xOrient = Integer.parseInt(config.get(CATEGORY_GUI_STAMINA, "X Orient", 1,
				"The orientation for the X axis (-1 = left, 0 = middle, 1 = right). Determines what point in the axis to snap to")
				.getString());
		stam_yOrient = Integer.parseInt(config.get(CATEGORY_GUI_STAMINA, "Y Orient", 1,
				"The orientation for the Y axis (-1 = top, 0 = middle, 1 = bottom). Determines what point in the axis to snap to")
				.getString());
		stam_xPos = Integer.parseInt(
				config.get(CATEGORY_GUI_STAMINA, "X Position", -82, "The Offset value away from the orient (-)left, (+)right")
						.getString());
		stam_yPos = Integer.parseInt(
				config.get(CATEGORY_GUI_STAMINA, "Y Position", -7, "The Offset value away from the orient (-)up, (+)down")
						.getString());
		stam_direction = Integer.parseInt(config.get(CATEGORY_GUI_STAMINA, "Metre Direction", 1,
				"The direction the metre goes down: -1 = left to right, 0 = middle, 1 = right to left (May have subtle flaws in altered directions 1 and 0)")
				.getString());

		AR_xOrient = Integer.parseInt(config.get(CATEGORY_GUI_ARMOR_RATING, "X Orient", -1,
				"The orientation for the X axis (-1 = left, 0 = middle, 1 = right). Determines what point in the axis to snap to")
				.getString());
		AR_yOrient = Integer.parseInt(config.get(CATEGORY_GUI_ARMOR_RATING, "Y Orient", -1,
				"The orientation for the Y axis (-1 = top, 0 = middle, 1 = bottom). Determines what point in the axis to snap to")
				.getString());
		AR_xPos = Integer.parseInt(
				config.get(CATEGORY_GUI_ARMOR_RATING, "X Position", 4, "The Offset value away from the orient (-)left, (+)right")
						.getString());
		AR_yPos = Integer.parseInt(config
				.get(CATEGORY_GUI_ARMOR_RATING, "Y Position", 4, "The Offset value away from the orient (-)up, (+)down").getString());

		AC_xOrient = Integer.parseInt(config.get(CATEGORY_GUI_ARMOR_COUNT, "X Orient", -1,
				"The orientation for the X axis (-1 = left, 0 = middle, 1 = right). Determines what point in the axis to snap to")
				.getString());
		AC_yOrient = Integer.parseInt(config.get(CATEGORY_GUI_ARMOR_COUNT, "Y Orient", -1,
				"The orientation for the Y axis (-1 = top, 0 = middle, 1 = bottom). Determines what point in the axis to snap to")
				.getString());
		AC_xPos = Integer.parseInt(
				config.get(CATEGORY_GUI_ARMOR_COUNT, "X Position", 4, "The Offset value away from the orient (-)left, (+)right")
						.getString());
		AC_yPos = Integer.parseInt(config
				.get(CATEGORY_GUI_ARMOR_COUNT, "Y Position", 4, "The Offset value away from the orient (-)up, (+)down").getString());

		CF_xOrient = Integer.parseInt(config.get(CATEGORY_GUI_COGWORK_ARMOR_FUEL, "X Orient", 1,
				"The orientation for the X axis (-1 = left, 0 = middle, 1 = right). Determines what point in the axis to snap to")
				.getString());
		CF_yOrient = Integer.parseInt(config.get(CATEGORY_GUI_COGWORK_ARMOR_FUEL, "Y Orient", -1,
				"The orientation for the Y axis (-1 = top, 0 = middle, 1 = bottom). Determines what point in the axis to snap to")
				.getString());
		CF_xPos = Integer.parseInt(
				config.get(CATEGORY_GUI_COGWORK_ARMOR_FUEL, "X Position", -164, "The Offset value away from the orient (-)left, (+)right")
						.getString());
		CF_yPos = Integer.parseInt(config
				.get(CATEGORY_GUI_COGWORK_ARMOR_FUEL, "Y Position", -4, "The Offset value away from the orient (-)up, (+)down").getString());

		displayOreDict = Boolean.parseBoolean(config.get(CATEGORY_DEBUG, "Show Debug OreDict", false,
				"Displays a list of Ore Dictionary entries to tooltips").getString());
	}

}
