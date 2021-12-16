package minefantasy.mfr.config;

import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.mechanics.CombatMechanics;
import minefantasy.mfr.mechanics.knowledge.InformationBase;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.tile.TileEntityRoast;

public class ConfigHardcore extends ConfigurationBaseMF {
	public static final String CATEGORY_CRAFTING = "1: HARDCORE CRAFTING";
	public static final String CATEGORY_RESEARCH = "2: Research";
	public static final String CATEGORY_FOOD = "3: Food and Hunting";
	public static final String CATEGORY_MOB = "4: Monster Upgrades";
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
	public static boolean preventCook;
	public static boolean upgradeZombieWep;
	public static float zombieWepChance;
	public static boolean fastZombies;
	public static boolean critLimp;

	@Override
	protected void loadConfig() {
		HCCreduceIngots = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Hardcore Ingots", true,
				"Some Metals (Like iron, steel and direct ore smelts) Must be worked manually on an anvil rather than smelted. They may also cost more! Big furnace still works.").getString());
		HCChotBurn = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Hot burns", true,
				"You cannot hold hot items (apron or not), tongs must be used.").getString());
		Heatable.HCCquenchRuin = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Hardcore Quench", true,
				"Hot items can be damaged if a trough is not used.").getString());
		HCCWeakItems = Boolean.parseBoolean(config.get(CATEGORY_CRAFTING, "Weaken Basic items", true,
				"This will significantly reduce the durability of basic items (made on basic crafting table), they can still be crafted but are practically useless.").getString());
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
		preventCook = Boolean.parseBoolean(config.get(CATEGORY_FOOD, "Prevent furnace food", false,
				"Stop food and ceramic from being cooked in a furnace").getString());
		TileEntityRoast.enableOverheat = Boolean.parseBoolean(config.get(CATEGORY_FOOD, "Burn at high temperature", true,
				"Cooking food on a stove or oven will automatically burn at high temperatures").getString());

		upgradeZombieWep = Boolean.parseBoolean(config.get(CATEGORY_MOB, "Give Zombie Weapon", true,
				"Zombies have a chance on spawning with forged iron weapons, It also controls some zombies having MF armour").getString());
		zombieWepChance = Float.parseFloat(config.get(CATEGORY_MOB, "Zombie Weapon Spawn Chance Modifier", 1.0F,
				"Chance for Zombies to have forged weapons, increased with difficulty").getString());
		fastZombies = Boolean.parseBoolean(config.get(CATEGORY_MOB, "Speed up zombies", true,
				"Speed up zombies (Sure it's not as real.. but it makes them a bit more dangerous)").getString());
		critLimp = Boolean.parseBoolean(config.get(CATEGORY_MOB, "Critical Injury Limp", true,
				"This means when you're badly wounded, you slow down and limp").getString());
		CombatMechanics.swordSkeleton = Boolean.parseBoolean(
				config.get(CATEGORY_MOB, "Skeleton Swords", true, "Some Skeletons use swords").getString());
	}
}
