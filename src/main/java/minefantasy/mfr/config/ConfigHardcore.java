package minefantasy.mfr.config;

import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.mechanics.knowledge.InformationBase;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;

public class ConfigHardcore extends ConfigurationBaseMF {
	public static final String CATEGORY_CRAFTING = "1: HARDCORE CRAFTING";
	public static final String CATEGORY_RESEARCH = "2: Research";
	public static final String CATEGORY_FOOD = "3: Cooking and Hunting";
	public static boolean HCCreduceIngots = true;
	public static boolean HCChotBurn = true;
	public static boolean HCCWeakItems = true;
	public static boolean HCCallowRocks = true;
	public static boolean HCCRemoveCraftBread = true;
	public static boolean HCCRemoveCraftPumpkinPie = true;
	public static boolean HCCRemoveCraftCake = true;
	public static boolean HCCRemoveCraftFlintAndSteel = true;
	public static boolean HCCRemoveCraftBucket = true;
	public static boolean HCCRemoveBooksCraft = false;
	public static boolean HCCRemoveTalismansCraft = false;
	public static boolean hunterKnife;
	public static boolean lessHunt;
	public static boolean dropRawhide;
	public static boolean preventCook;
	public static boolean preventCeramic;
	public static boolean enableOverheat = true;
	public static int foodRepeatPenaltyLimit;
	public static int dirtyProgressSkillModifier;
	public static int dirtyProgressMax;

	public ConfigHardcore(String name) {
		super(name);
	}

	@Override
	protected void initializeCategories() {
		config.addCustomCategoryComment(CATEGORY_CRAFTING, "Controls Hardcore crafting settings. (NO LONGER HANDLES MFR RECIPE DISABLING, SEE CRAFTING CONFIG");
		config.addCustomCategoryComment(CATEGORY_RESEARCH, "Controls Research settings");
		config.addCustomCategoryComment(CATEGORY_FOOD, "Controls Food and Hunting settings");
	}

	@Override
	protected void initializeValues() {
		HCCreduceIngots = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Hardcore Ingots", true,
				"Some Metals (Like iron, steel and direct ore smelts) Must be worked manually on an anvil rather than smelted. They may also cost more! Big furnace still works.").getString());
		HCChotBurn = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Hot burns", true,
				"You cannot hold hot items (apron or not), tongs must be used.").getString());
		Heatable.HCCquenchRuin = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Hardcore Quench", true,
				"Hot items can be damaged if a trough is not used.").getString());
		HCCWeakItems = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Weaken Basic items", true,
				"This will significantly reduce the durability of vanilla items, they can still be crafted but are practically useless.").getString());
		HCCallowRocks = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Allow Stone-Age", true,
				"Allows punching stone for sharp rocks, and using them on leaves for sticks/vines: These make primitive stone tools").getString());
		HCCRemoveCraftBread = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Remove Bread Recipes", true,
				"Should Bread recipes be removed, since MF has its own recipe for such items.").getString());
		HCCRemoveCraftPumpkinPie = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Remove Pumpkin Pie Recipes", true,
				"Should Pumpkin Pie recipes be removed, since MF has its own recipe for such items.").getString());
		HCCRemoveCraftCake = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Remove Cake Recipes", true,
				"Should Cake recipes be removed, since MF has its own recipe for such items.").getString());
		HCCRemoveCraftFlintAndSteel = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Remove Flint and Steel Recipes", true,
				"Should Flint and Steel recipes be removed, since MF has its own recipe for such items.").getString());
		HCCRemoveCraftBucket = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Remove Bucket Recipes", true,
				"Should Bucket recipes be removed, since MF has its own recipe for such items.").getString());
		HCCRemoveBooksCraft = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Remove Books Recipes", false,
				"Skill books recipes will be disabled, but you still can find them it the world.").getString());
		HCCRemoveTalismansCraft = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Remove Talismans Recipes", false,
				"Research talismans recipes will be disabled, but you still can find them it the world.").getString());
		ResearchLogic.knowledgelyr = Integer.parseInt(config.get(CATEGORY_RESEARCH, "###CHANGE RESEARCH ID###", 0,
				"This changes the research ID, removing all entries").getString());
		InformationBase.unlockAll = Boolean.parseBoolean(config.get(CATEGORY_RESEARCH, "Unlock entries", false,
				"If you don't want to research, this will unlock all entries.").getString());
		InformationBase.easyResearch = Boolean.parseBoolean(config.get(CATEGORY_RESEARCH, "Baby-Mode Research", false,
				"This removes the process of examining artefacts, research is unlocked by clicking entries in the book.").getString());

		hunterKnife = Boolean.parseBoolean(config.get(CATEGORY_FOOD, "Restrict to hunting weapon", false,
				"This option means animals ONLY drop meat and hide when killed with a hunting weapon such as a knife, only the killing blow counts").getString());
		lessHunt = Boolean.parseBoolean(config.get(CATEGORY_FOOD, "Reduce Meat Drops", false,
				"This will alter the stack size of animal meat drops, meaning they only drop 1 every time").getString());
		dropRawhide = Boolean.parseBoolean(config.get(CATEGORY_FOOD, "Drop Leather or Rawhide", true,
				"This will force rawhide drops from animals, rather than leather from leather-based animals, like cows").getString());
		preventCook = Boolean.parseBoolean(config.get(CATEGORY_FOOD, "Prevent furnace food", false,
				"Stop food from being cooked in a furnace").getString());
		preventCeramic = Boolean.parseBoolean(config.get(CATEGORY_FOOD, "Prevent furnace ceramic", false,
				"Stop ceramics from being cooked in a furnace").getString());
		enableOverheat = Boolean.parseBoolean(config.get(CATEGORY_FOOD, "Burn at high temperature", true,
				"Cooking food on a stove or oven will automatically burn at high temperatures").getString());
		foodRepeatPenaltyLimit = Integer.parseInt(config.get(CATEGORY_FOOD, "Food Repeat Penalty Limit", 3,
				"How much of certain foods you can eat in a row before vomiting").getString());
		dirtyProgressSkillModifier = Integer.parseInt(config.get(CATEGORY_FOOD, "Dirty Progress Skill Mod", 2,
				"How much to modify the player's current provisioning skill by to reduce the amount of dirtiness "
						+ "a kitchen bench recipe adds. Formula is as follows, "
						+ "where dirtyProgressAmount is from the recipe, maxLevel = Provisioning Skill Max Level:\n"
						+ "dirtyProgressAmount - (dirtyProgressAmount * ((currentLevel / maxLevel) / {this value}))").getString());
		dirtyProgressMax = Integer.parseInt(config.get(CATEGORY_FOOD, "Dirty Progress Max", 100,
				"The max amount of dirtiness on the Kitchen Bench, as accumulated by recipe dirty amount").getString());
	}
}
