package minefantasy.mfr.constants;

/**
 * Stores various global constants used in Minefantasy Reforged.
 */
public final class Constants {

	public static final String ASSET_DIRECTORY = "assets/%s/registry";//use with String.format() to insert the modId
	public static final String CONFIG_DIRECTORY = "MineFantasyReforged";
	public static final String JSON_FILE_EXT = "json";
	public static final String CRAFTED_BY_NAME_TAG = "mfr_crafted_by_name";
	public static final String MF_HELD_ITEM_TAG = "MF_HeldItem";
	public static final String UNBREAKABLE_TAG = "Unbreakable";
	public static final String INJURED_TAG = "MF_Injured";
	public static final String DROP_LOOT_TAG = "MF_LootDrop";
	public static final String HUNTER_KILL_TAG = "hunterKill";
	public static final String MFR_BOW_DAMAGE_TAG = "MF_Bow_Damage";

	public static final String SCRAP_WOOD_TAG = "scrap_wood";
	public static final String REFINED_WOOD_TAG = "refined_wood";

	public static final String MFR_ARROW_HANDLER = "mfr_arrow";
	public static final String MFR_BOLT_HANDLER = "mfr_bolt";
	public static final String VANILLA_ARROW_HANDLER = "vanilla_arrow";

	public static class StorageTextures {
		public static final String JUG = "jug";
		public static final String JUG_WATER = "jugwater";
		public static final String JUG_OIL = "jugoil";
		public static final String JUG_MILK = "jugmilk";
		public static final String FIREBRICK = "firebrick";
		public static final String TRAY = "tray";
		public static final String BIGPLATE = "bigplate";
		public static final String BAR = "bar";
		public static final String MOULD = "mould";
		public static final String SHEET = "sheet";
		public static final String WOOD_PANE = "woodpane";
		public static final String POT = "pot";
		public static final String PLANK = "plank";
		public static final String PLANK_CUT = "plankcut";
		public static final String PERSIST_FLAG = "persist_flag";
	}

	public enum StorageType {
		NONE,
		JUG,
		JUG_WATER,
		JUG_OIL,
		JUG_MILK,
		FIREBRICK,
		TRAY,
		BIGPLATE,
		BAR,
		MOULD,
		SHEET,
		WOOD_PANE,
		POT,
		PLANK,
		PLANK_CUT,
		;

		StorageType() {
		}
	}

}
