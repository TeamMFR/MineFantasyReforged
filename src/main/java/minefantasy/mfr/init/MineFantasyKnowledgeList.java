package minefantasy.mfr.init;

import minefantasy.mfr.api.refine.Alloy;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.item.ItemBomb;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.material.MetalMaterial;
import minefantasy.mfr.mechanics.knowledge.IArtefact;
import minefantasy.mfr.mechanics.knowledge.InformationBase;
import minefantasy.mfr.mechanics.knowledge.InformationList;
import minefantasy.mfr.mechanics.knowledge.InformationPage;
import minefantasy.mfr.mechanics.knowledge.ResearchArtefacts;
import minefantasy.mfr.util.RecipeHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;

public class MineFantasyKnowledgeList {

	// note: please follow the naming convention for static final vars, these should be all upper (https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html)
	public static final IRecipe STICK_RECIPE = RecipeHelper.getMFRRecipe("sticks");
	public static final IRecipe TIMBER_RECIPE = RecipeHelper.getMFRRecipe("timber");
	public static final IRecipe FIREPIT_RECIPE = RecipeHelper.getMFRRecipe("firepit");
	public static final IRecipe STOVE_RECIPE = RecipeHelper.getMFRRecipe("stove");
	public static final IRecipe CARPENTER_RECIPE = RecipeHelper.getMFRRecipe("carpenter");
	public static final IRecipe SUGAR_POT_RECIPE = RecipeHelper.getMFRRecipe("sugar_pot");
	public static final IRecipe CHEESE_WHEEL_RECIPE = RecipeHelper.getMFRRecipe("cheese_wheel");
	public static final IRecipe PIE_MEAT_RECIPE = RecipeHelper.getMFRRecipe("pie_meat");
	public static final IRecipe PIE_SHEPARDS_RECIPE = RecipeHelper.getMFRRecipe("pie_shepards");
	public static final IRecipe PIE_APPLE_RECIPE = RecipeHelper.getMFRRecipe("pie_apple");
	public static final IRecipe PIE_BERRY_RECIPE = RecipeHelper.getMFRRecipe("pie_berry");
	public static final IRecipe PIE_PUMPKIN_RECIPE = RecipeHelper.getMFRRecipe("pie_pumpkin");
	public static final IRecipe JUG_PLANT_OIL_RECIPE = RecipeHelper.getMFRRecipe("jug_plant_oil");
	public static final IRecipe JUG_WATER_RECIPE = RecipeHelper.getMFRRecipe("jug_water");
	public static final IRecipe JUG_MILK_RECIPE = RecipeHelper.getMFRRecipe("jug_milk");
	public static final IRecipe DRYROCKS_RECIPE = ConfigHardcore.HCCallowRocks ? RecipeHelper.getMFRRecipe("dryrocks_hc") : RecipeHelper.getMFRRecipe("dryrocks");
	
	public static InformationPage artisanry = InformationList.artisanry;
	public static InformationPage construction = InformationList.construction;
	public static InformationPage engineering = InformationList.engineering;
	public static InformationPage provisioning = InformationList.provisioning;
	public static InformationPage mastery = InformationList.mastery;
	
	// BASICS -FREE
	public static InformationBase carpenter;
	public static InformationBase getting_started;
	public static InformationBase salvage;
	public static InformationBase research;
	public static InformationBase talisman;
	public static InformationBase ores;
	public static InformationBase plants;
	public static InformationBase chimney;
	public static InformationBase tanning;
	public static InformationBase commodities;
	public static InformationBase dust;
	public static InformationBase craft_crafters;
	public static InformationBase stamina;
	public static InformationBase combat;
	public static InformationBase craft_armour_basic;
	public static InformationBase crafting_HCC_tools;
	public static InformationBase firemaker;
	public static InformationBase dragons;
	public static InformationBase minotaurs;

	//ARTISAN
	//Smelting
	public static InformationBase bloomery;
	public static InformationBase crucible;
	public static InformationBase firebrick_crucible;
	public static InformationBase smelt_copper;
	public static InformationBase smelt_bronze;
	public static InformationBase smelt_iron;
	public static InformationBase coal_flux;
	public static InformationBase big_furnace;
	public static InformationBase blast_furnace;
	public static InformationBase smelt_pig_iron;
	public static InformationBase smelt_steel;
	public static InformationBase smelt_encrusted;
	public static InformationBase smelt_obsidian;
	public static InformationBase smelt_black_steel;
	public static InformationBase smelt_dragonforged;
	public static InformationBase smelt_blue_steel;
	public static InformationBase smelt_red_steel;
	public static InformationBase smelt_mithril;
	public static InformationBase smelt_adamantium;
	public static InformationBase smelt_master;
	public static InformationBase smelt_mithium;
	public static InformationBase smelt_ignotumite;
	public static InformationBase smelt_ender;

	//Info and Crafting
	public static InformationBase bellows;
	public static InformationBase trough;
	public static InformationBase forge;
	public static InformationBase anvil;
	public static InformationBase bar;
	public static InformationBase apron;
	public static InformationBase craft_tools;
	public static InformationBase craft_advanced_tools;
	public static InformationBase craft_weapons;
	public static InformationBase craft_advanced_weapons;
	public static InformationBase arrows;
	public static InformationBase craft_ornate;
	public static InformationBase craft_armour_light;
	public static InformationBase craft_armour_medium;
	public static InformationBase craft_armour_heavy;
	public static InformationBase arrows_bodkin;
	public static InformationBase arrows_broad;
	public static InformationBase repair_basic;
	public static InformationBase repair_advanced;
	public static InformationBase repair_ornate;

	//ENGINEERING
	public static InformationBase coke;
	public static InformationBase engineering_tools;
	public static InformationBase engineering_components;
	public static InformationBase tungsten;
	public static InformationBase climber;
	public static InformationBase spyglass;
	public static InformationBase parachute;
	public static InformationBase syringe;
	public static InformationBase engineering_tanner;
	public static InformationBase advanced_forge;
	public static InformationBase advanced_crucible;
	public static InformationBase blackpowder;
	public static InformationBase advanced_blackpowder;
	public static InformationBase bombs;
	public static InformationBase bomb_press;
	public static InformationBase bomb_arrow;
	public static InformationBase bomb_fuse;
	public static InformationBase shrapnel;
	public static InformationBase firebomb;
	public static InformationBase sticky_bomb;
	public static InformationBase bomb_ceramic;
	public static InformationBase bomb_iron;
	public static InformationBase bomb_obsidian;
	public static InformationBase bomb_crystal;
	public static InformationBase mine_ceramic;
	public static InformationBase mine_iron;
	public static InformationBase mine_obsidian;
	public static InformationBase mine_crystal;
	public static InformationBase crossbows;
	public static InformationBase crossbow_shafts;
	public static InformationBase crossbow_heads;
	public static InformationBase crossbow_head_advanced;
	public static InformationBase crossbow_shaft_advanced;
	public static InformationBase crossbow_ammo;
	public static InformationBase crossbow_scope;
	public static InformationBase crossbow_bayonet;
	public static InformationBase cogwork_armour;
	public static InformationBase composite_alloy;

	//CONSTRUCTION
	public static InformationBase construction_parts;
	public static InformationBase refined_planks;
	public static InformationBase reinforced_stone;
	public static InformationBase clay_wall;
	public static InformationBase glass;
	public static InformationBase brickworks;
	public static InformationBase decorated_stone;
	public static InformationBase bars;
	public static InformationBase thatch;
	public static InformationBase paint_brush;
	public static InformationBase tool_rack;
	public static InformationBase food_box;
	public static InformationBase ammo_box;
	public static InformationBase big_box;
	public static InformationBase bed_roll;

	//PROVISIONING
	public static InformationBase cooking_utensils;
	public static InformationBase firepit;
	public static InformationBase generic_meat;
	public static InformationBase stew;
	public static InformationBase jerky;
	public static InformationBase sausage;
	public static InformationBase sandwitch;
	public static InformationBase sandwitch_big;
	public static InformationBase meatpie;
	public static InformationBase shepard_pie;
	public static InformationBase bread;
	public static InformationBase oats;
	public static InformationBase salt;
	public static InformationBase jug;
	public static InformationBase berry;
	public static InformationBase icing;
	public static InformationBase sweetroll;
	public static InformationBase eclair;
	public static InformationBase cake;
	public static InformationBase carrot_cake;
	public static InformationBase chocolate_cake;
	public static InformationBase black_forest_cake;
	public static InformationBase apple_pie;
	public static InformationBase berry_pie;
	public static InformationBase cheese;
	public static InformationBase cheese_roll;
	public static InformationBase bandage;
	public static InformationBase bandage_advanced;

	//MASTERY
	public static InformationBase toughness;
	public static InformationBase fitness;
	public static InformationBase armour_pro;
	public static InformationBase parry_pro;
	public static InformationBase counter_attack;
	public static InformationBase auto_parry;
	public static InformationBase scrapper;
	public static InformationBase first_aid;
	public static InformationBase doctor;

	public static Alloy[] reinforced_stone_alloy;
	public static Alloy[] bronze_alloy;
	public static Alloy[] steel_alloy;
	public static Alloy[] obsidian_alloy;
	public static Alloy[] black_steel_alloy;
	public static Alloy[] red_steel_alloy;
	public static Alloy[] blue_steel_alloy;
	public static Alloy[] mithril_alloy;
	public static Alloy[] adamantium_alloy;
	public static Alloy[] ignotumite_alloy;
	public static Alloy[] mithium_alloy;
	public static Alloy[] enderforge_alloy;
	public static Alloy[] wolframite_raw_alloy;
	
	public static void init() {
		carpenter = (new InformationBase("carpenter", 0, -3, 0, MineFantasyBlocks.CARPENTER, null))
				.registerStat().setUnlocked();
		salvage = (new InformationBase("salvage", 0, -4, 0, MineFantasyBlocks.SALVAGE_BASIC, null))
				.registerStat().setUnlocked();
		getting_started = (new InformationBase("getting_started", 0, 0, 0, Items.BOOK, null))
				.registerStat().setUnlocked();
		research = (new InformationBase("research", 1, 1, 0, MineFantasyItems.RESEARCH_BOOK, null))
				.registerStat().setUnlocked();
		talisman = (new InformationBase("talisman", 4, 2, 0, MineFantasyItems.TALISMAN_LESSER, research))
				.registerStat().setUnlocked();
		ores = (new InformationBase("ores", 1, -2, 0, MineFantasyBlocks.COPPER_ORE, null))
				.registerStat().setUnlocked();
		plants = (new InformationBase("plants", 1, -3, 0, MineFantasyBlocks.LOG_IRONBARK, ores))
				.registerStat().setUnlocked();
		chimney = (new InformationBase("chimney", 0, 2, 0, MineFantasyBlocks.CHIMNEY_STONE, null))
				.registerStat().setUnlocked();
		tanning = (new InformationBase("tanning", 0, -2, 0, Items.LEATHER, null))
				.registerStat().setUnlocked().setSpecial();
		commodities = (new InformationBase("commodities", -1, -2, 0, MineFantasyItems.NAIL, null))
				.registerStat().setUnlocked();
		dust = (new InformationBase("dust", -1, -3, 0, MineFantasyItems.CLAY_POT, commodities))
				.registerStat().setUnlocked();
		craft_crafters = (new InformationBase("craft_crafters", -1, 1, 0, MineFantasyItems.STANDARD_HAMMER, null))
				.registerStat().setUnlocked();
		stamina = (new InformationBase("stamina", -3, 1, 0, Items.FEATHER, craft_crafters))
				.registerStat().setUnlocked();
		combat = (new InformationBase("combat", -5, 2, 0, Items.IRON_SWORD, stamina))
				.registerStat().setUnlocked();
		craft_armour_basic = (new InformationBase("craft_armour_basic", -5, 0, 5, LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 0, 1), combat))
				.registerStat().setUnlocked();
		firemaker = (new InformationBase("firemaker", 5, 1, 0, Items.FLINT_AND_STEEL, null))
				.registerStat().setUnlocked();
		dragons = (new InformationBase("dragons", -1, 3, 0, MineFantasyItems.DRAGON_HEART, null))
				.registerStat().setUnlocked();
		minotaurs = (new InformationBase("minotaurs", 1, 3, 0, Items.BEEF, null))
				.registerStat().setUnlocked();

		// ARTISANRY -From Not very to the most Expensive
		bloomery = (new InformationBase("bloomery", 4, -2, 0, MineFantasyBlocks.BLOOMERY, crucible)).registerStat()
				.setPage(artisanry).setUnlocked().setSpecial();
		crucible = (new InformationBase("crucible", 4, 0, 0, MineFantasyBlocks.CRUCIBLE_STONE, null))
				.registerStat().setPage(artisanry).setUnlocked().setSpecial();
		firebrick_crucible = (new InformationBase("firebrick_crucible", 6, 0, 1, MineFantasyBlocks.CRUCIBLE_FIRECLAY, crucible)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 40);

		smelt_copper = (new InformationBase("smelt_copper", 1, 0, 0, MineFantasyItems.COPPER_INGOT, null))
				.registerStat().setPage(artisanry).setUnlocked().setDescriptValues(getMetalTier(MineFantasyMaterials.Names.COPPER));
		smelt_bronze = (new InformationBase("smelt_bronze", 1, 2, 2, MineFantasyItems.BRONZE_INGOT, crucible)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 5).setDescriptValues(getMetalTier(MineFantasyMaterials.Names.BRONZE));
		smelt_iron = (new InformationBase("smelt_iron", 1, 4, 1, Items.IRON_INGOT, null)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 10).setDescriptValues(getMetalTier(MineFantasyMaterials.Names.IRON));
		coal_flux = (new InformationBase("coal_flux", 1, 6, 2, MineFantasyItems.COAL_FLUX, smelt_iron)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 15);
		blast_furnace = (new InformationBase("blast_furnace", 2, 5, 5, MineFantasyBlocks.BLAST_HEATER, smelt_iron))
				.registerStat().setPage(artisanry).setSpecial().addSkill(Skill.ARTISANRY, 25);
		big_furnace = (new InformationBase("big_furnace", 0, 5, 4, MineFantasyBlocks.FURNACE_STONE, smelt_iron)).registerStat()
				.setPage(artisanry).setSpecial().addSkill(Skill.ARTISANRY, 10);
		smelt_pig_iron = (new InformationBase("smelt_pig_iron", 3, 3, 0, MineFantasyItems.PIG_IRON_INGOT, blast_furnace)).registerStat()
				.setPage(artisanry).setUnlocked().addSkill(Skill.ARTISANRY, 25);
		smelt_steel = (new InformationBase("smelt_steel", 4, 5, 1, MineFantasyItems.STEEL_INGOT, smelt_pig_iron)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 25).setDescriptValues(getMetalTier(MineFantasyMaterials.Names.STEEL));
		smelt_encrusted = (new InformationBase("smelt_encrusted", 6, 5, 2, MineFantasyItems.DIAMOND_SHARDS, smelt_steel))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 35)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.ENCRUSTED));
		smelt_obsidian = (new InformationBase("smelt_obsidian", 6, 3, 2, MineFantasyItems.OBSIDIAN_INGOT, smelt_steel))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 40)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.OBSIDIAN));
		smelt_black_steel = (new InformationBase("smelt_black_steel", 4, 7, 3, MineFantasyItems.BLACK_STEEL_INGOT, smelt_steel))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 50)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.BLACK_STEEL));
		smelt_dragonforged = (new InformationBase("smelt_dragonforged", -4, -1, 1, MineFantasyItems.DRAGON_HEART, null))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 50);
		smelt_red_steel = (new InformationBase("smelt_red_steel", 3, 9, 5, MineFantasyItems.RED_STEEL_INGOT, smelt_black_steel))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 65)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.RED_STEEL));
		smelt_blue_steel = (new InformationBase("smelt_blue_steel", 5, 9, 5, MineFantasyItems.BLUE_STEEL_INGOT, smelt_black_steel))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 65)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.BLUE_STEEL));
		smelt_mithril = (new InformationBase("smelt_mithril", 5, 12, 3, MineFantasyItems.MITHRIL_INGOT, null)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 75).setDescriptValues(getMetalTier(MineFantasyMaterials.Names.MITHRIL));
		smelt_adamantium = (new InformationBase("smelt_adamantium", 3, 12, 3, MineFantasyItems.ADAMANTIUM_INGOT, null))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 75)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.ADAMANTIUM));
		smelt_master = (new InformationBase("smelt_master", 4, 13, 3, MineFantasyItems.ANCIENT_JEWEL_MASTER, null))
				.registerStat().setPage(artisanry).setSpecial().addSkill(Skill.ARTISANRY, 100);
		smelt_ignotumite = (new InformationBase("smelt_ignotumite", 2, 15, 3, MineFantasyItems.IGNOTUMITE_INGOT, smelt_master))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 100)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.IGNOTUMITE));
		smelt_mithium = (new InformationBase("smelt_mithium", 6, 15, 3, MineFantasyItems.MITHIUM_INGOT, smelt_master))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 100)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.MITHIUM));
		smelt_ender = (new InformationBase("smelt_ender", 4, 16, 3, MineFantasyItems.ENDER_INGOT, smelt_master))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 100)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.ENDER));

		crafting_HCC_tools = (new InformationBase("crafting_HCC_tools", -1, -2, 0, MineFantasyItems.STONE_PICK, null)).registerStat()
				.setPage(artisanry).setUnlocked();
		bellows = (new InformationBase("bellows", 0, -1, 0, MineFantasyBlocks.BELLOWS, null)).registerStat()
				.setPage(artisanry).setUnlocked();
		trough = (new InformationBase("trough", 0, -2, 0, MineFantasyBlocks.TROUGH_WOOD, bellows)).registerStat()
				.setPage(artisanry).setUnlocked();
		forge = (new InformationBase("forge", 0, 0, 0, MineFantasyBlocks.FORGE, null)).registerStat()
				.setPage(artisanry).setUnlocked();
		anvil = (new InformationBase("anvil", -1, 0, 0, MineFantasyBlocks.ANVIL_IRON, forge)).registerStat().setPage(artisanry)
				.setUnlocked().setSpecial();
		bar = (new InformationBase("bar", -1, 2, 0, MineFantasyItems.BAR, anvil)).registerStat().setPage(artisanry)
				.setUnlocked();
		apron = (new InformationBase("apron", -1, -1, 0, LeatherArmourListMFR.LEATHER_APRON, anvil)).registerStat()
				.setPage(artisanry).setUnlocked();
		craft_tools = (new InformationBase("craft_tools", -3, 2, 0, MineFantasyItems.STANDARD_PICK, bar)).registerStat()
				.setPage(artisanry).setUnlocked();
		craft_advanced_tools = (new InformationBase("craft_advanced_tools", -5, 2, 0, MineFantasyItems.STANDARD_HEAVY_PICK, craft_tools)).registerStat()
				.setPage(artisanry).setUnlocked();
		craft_weapons = (new InformationBase("craft_weapons", -3, 1, 5, MineFantasyItems.STANDARD_SWORD, bar)).registerStat()
				.setPage(artisanry).setUnlocked();
		craft_advanced_weapons = (new InformationBase("craft_advanced_weapons", -5, 1, 0, MineFantasyItems.STANDARD_BATTLEAXE, craft_weapons)).registerStat()
				.setPage(artisanry).setUnlocked();
		arrows = (new InformationBase("arrows", -3, 4, 0, MineFantasyItems.STANDARD_ARROW, bar)).registerStat()
				.setPage(artisanry).setUnlocked();

		craft_ornate = (new InformationBase("craft_ornate", -3, -1, 1, MineFantasyItems.ORNATE_ITEMS, null)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 35);
		craft_armour_light = (new InformationBase("craft_armour_light", -3, 3, 1, LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 3, 1), anvil)).registerStat()
				.setPage(artisanry).setUnlocked();
		craft_armour_medium = (new InformationBase("craft_armour_medium", -4, 3, 1, MineFantasyItems.STANDARD_CHAIN_CHESTPLATE, craft_armour_light)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 0);
		craft_armour_heavy = (new InformationBase("craft_armour_heavy", -5, 3, 3, MineFantasyItems.STANDARD_PLATE_CHESTPLATE,
				craft_armour_medium)).registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 10);
		arrows_bodkin = (new InformationBase("arrows_bodkin", -4, 5, 1, MineFantasyItems.STANDARD_ARROW_BODKIN, arrows)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 10);
		arrows_broad = (new InformationBase("arrows_broad", -5, 5, 2, MineFantasyItems.STANDARD_ARROW_BROAD, arrows)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 20);

		// ENGINEERING - Highly Expensive
		engineering_tools = (new InformationBase("engineering_tools", 3, 0, 0, MineFantasyItems.STANDARD_SPANNER, null)).registerStat()
				.setPage(engineering).setUnlocked();
		engineering_components = (new InformationBase("engineering_components", 5, 0, 0, MineFantasyItems.BOLT, engineering_tools)).registerStat()
				.setPage(engineering).setUnlocked();
		tungsten = (new InformationBase("tungsten", 8, -1, 1, MineFantasyItems.TUNGSTEN_INGOT, engineering_components)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 20).addSkill(Skill.ARTISANRY, 20);
		coke = (new InformationBase("coke", 4, -1, 2, MineFantasyItems.COKE, engineering_components)).registerStat()
				.setPage(engineering);
		climber = (new InformationBase("climber", 7, 0, 1, MineFantasyItems.CLIMBING_PICK_BASIC, engineering_components)).registerStat()
				.setPage(engineering).setUnlocked();
		spyglass = (new InformationBase("spyglass", 8, 1, 2, new ItemStack(MineFantasyItems.SPYGLASS, 1, 2), climber))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 5);
		parachute = (new InformationBase("parachute", 9, 2, 2, MineFantasyItems.PARACHUTE, climber)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 10);
		syringe = (new InformationBase("syringe", 5, -2, 1, MineFantasyItems.SYRINGE_EMPTY, engineering_components)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 25);
		engineering_tanner = (new InformationBase("engineering_tanner", 5, 2, 1, MineFantasyBlocks.TANNER_METAL, engineering_components)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 40).addSkill(Skill.ARTISANRY, 25);
		advanced_crucible = (new InformationBase("v", 7, 3, 1, MineFantasyBlocks.CRUCIBLE_AUTO, engineering_tanner)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 50).addSkill(Skill.ARTISANRY, 35);
		advanced_forge = (new InformationBase("advanced_forge", 9, 3, 1, MineFantasyBlocks.FORGE_METAL, advanced_crucible)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 75).addSkill(Skill.ARTISANRY, 50);

		blackpowder = (new InformationBase("blackpowder", 0, 0, 4, MineFantasyItems.BLACKPOWDER, null))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 0);
		advanced_blackpowder = (new InformationBase("advanced_blackpowder", 2, -2, 2, MineFantasyItems.BLACKPOWDER_ADVANCED, blackpowder)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 50);
		bombs = (new InformationBase("bombs", 0, 2, 3, ItemBomb.createExplosive(MineFantasyItems.BOMB_CUSTOM, "ceramic", "basic", "basic", "black_powder", 1), blackpowder)).registerStat()
				.setPage(engineering).setSpecial().addSkill(Skill.ENGINEERING, 10);
		bomb_press = (new InformationBase("bomb_press", -1, 1, 2, MineFantasyBlocks.BOMB_PRESS, bombs)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 30);
		bomb_arrow = (new InformationBase("bomb_arrow", 1, 1, 2, MineFantasyItems.EXPLODING_ARROW, bombs)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 40);
		bomb_fuse = (new InformationBase("bomb_fuse", 2, 2, 0, MineFantasyItems.BOMB_FUSE, bombs)).registerStat()
				.setUnlocked().setPage(engineering);
		shrapnel = (new InformationBase("shrapnel", 0, 4, 1, MineFantasyItems.SHRAPNEL, bombs)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 25);
		firebomb = (new InformationBase("firebomb", 0, 6, 2, Items.BLAZE_POWDER, shrapnel)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 85);
		sticky_bomb = (new InformationBase("sticky_bomb", -2, 2, 1, Items.SLIME_BALL, bombs)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 50);
		bomb_ceramic = (new InformationBase("bomb_ceramic", 2, 3, 0, MineFantasyItems.BOMB_CASING_CERAMIC, bombs)).registerStat()
				.setUnlocked().setPage(engineering);
		bomb_iron = (new InformationBase("bomb_iron", 4, 5, 1, MineFantasyItems.BOMB_CASING_IRON, bomb_ceramic))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 20);
		bomb_obsidian = (new InformationBase("bomb_obsidian", 4, 7, 2, MineFantasyItems.BOMB_CASING_OBSIDIAN, bomb_iron))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 35);
		bomb_crystal = (new InformationBase("bomb_crystal", 2, 9, 1, MineFantasyItems.BOMB_CASING_CRYSTAL, bomb_obsidian))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 40);
		mine_ceramic = (new InformationBase("mine_ceramic", -2, 3, 2, MineFantasyItems.MINE_CASING_CERAMIC, bombs)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 15);
		mine_iron = (new InformationBase("mine_iron", -4, 5, 1, MineFantasyItems.MINE_CASING_IRON, mine_ceramic))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 20);
		mine_obsidian = (new InformationBase("mine_obsidian", -4, 7, 2, MineFantasyItems.MINE_CASING_OBSIDIAN, mine_iron))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 35);
		mine_crystal = (new InformationBase("mine_crystal", -2, 9, 1, MineFantasyItems.MINE_CASING_CRYSTAL, mine_obsidian))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 40);

		crossbows = (new InformationBase("crossbows", -4, 0, 3, MineFantasyBlocks.CROSSBOW_BENCH, null)).registerStat()
				.setPage(engineering).setSpecial().addSkill(Skill.ENGINEERING, 0);
		crossbow_shafts = (new InformationBase("crossbow_shafts", -6, 2, 0, MineFantasyItems.CROSSBOW_STOCK_WOOD, crossbows))
				.registerStat().setPage(engineering).setUnlocked();
		crossbow_shaft_advanced = (new InformationBase("cross_shaft_advanced", -6, 4, 2, MineFantasyItems.CROSSBOW_STOCK_IRON,
				crossbow_shafts)).registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 40);
		crossbow_heads = (new InformationBase("crossbow_heads", -2, -2, 0, MineFantasyItems.CROSSBOW_ARMS_BASIC, crossbows))
				.registerStat().setPage(engineering).setUnlocked();
		crossbow_head_advanced = (new InformationBase("crossbow_head_advanced", -3, -3, 2, MineFantasyItems.CROSSBOW_ARMS_ADVANCED,
				crossbow_heads)).registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 30);
		crossbow_bayonet = (new InformationBase("crossbow_bayonet", -1, -3, 1, MineFantasyItems.CROSSBOW_BAYONET, crossbow_heads))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 10);
		crossbow_ammo = (new InformationBase("crossbow_ammo", -5, 3, 1, MineFantasyItems.CROSSBOW_AMMO, crossbow_shafts)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 30);
		crossbow_scope = (new InformationBase("crossbow_scope", -7, 3, 1, MineFantasyItems.CROSSBOW_SCOPE, crossbow_shafts))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 20);

		cogwork_armour = (new InformationBase("cogwork_armour", 8, -3, 2, MineFantasyBlocks.BLOCK_COGWORK_HELM, tungsten)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 60);
		composite_alloy = (new InformationBase("composite_alloy", 10, -3, 1, MineFantasyItems.COMPOSITE_ALLOY_INGOT, tungsten))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 50)
				.addSkill(Skill.ARTISANRY, 40);

		repair_basic = (new InformationBase("repair_basic", 8, 0, 2, MineFantasyBlocks.REPAIR_BASIC, null))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 10);
		repair_advanced = (new InformationBase("repair_advanced", 10, 0, 3, MineFantasyBlocks.REPAIR_ADVANCED, repair_basic))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 25);
		repair_ornate = (new InformationBase("repair_ornate", 12, 2, 3, MineFantasyBlocks.REPAIR_ORNATE, repair_advanced))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 50);

		construction_parts = (new InformationBase("construction_parts", 0, 0, 0, MineFantasyItems.TIMBER_CUT,
				null)).registerStat().setPage(construction).setUnlocked();
		reinforced_stone = (new InformationBase("reinforced_stone", 1, 0, 0, MineFantasyBlocks.REINFORCED_STONE,
				null)).registerStat().setPage(construction).setUnlocked();
		glass = (new InformationBase("glass", 0, 1, 0, MineFantasyBlocks.FRAMED_GLASS, null)).registerStat()
				.setPage(construction).setUnlocked();
		brickworks = (new InformationBase("brickworks", 3, 0, 0, MineFantasyBlocks.COBBLE_BRICK, reinforced_stone))
				.registerStat().setPage(construction).setUnlocked();
		bars = (new InformationBase("bars", 0, 3, 0, MineFantasyBlocks.STEEL_BARS, glass)).registerStat().setPage(construction)
				.setUnlocked();
		thatch = (new InformationBase("thatch", 0, -3, 0, MineFantasyBlocks.THATCH, clay_wall)).registerStat()
				.setPage(construction).setUnlocked();
		refined_planks = (new InformationBase("refined_planks", -1, 0, 1, MineFantasyBlocks.REFINED_PLANKS,
				null)).registerStat().setPage(construction).addSkill(Skill.CONSTRUCTION, 0);
		clay_wall = (new InformationBase("clay_wall", 0, -1, 2, MineFantasyBlocks.CLAY_WALL, null))
				.registerStat().setPage(construction).addSkill(Skill.CONSTRUCTION, 5);
		paint_brush = (new InformationBase("paint_brush", -3, 0, 1, MineFantasyItems.PAINT_BRUSH, refined_planks))
				.registerStat().setPage(construction).addSkill(Skill.CONSTRUCTION, 10);
		decorated_stone = (new InformationBase("decorated_stone", 5, 0, 2, MineFantasyBlocks.REINFORCED_STONE_FRAMED,
				brickworks)).registerStat().setPage(construction).addSkill(Skill.CONSTRUCTION, 15);

		//        bed_roll = (new InformationBase("bed_roll", 2, 3, 1, BlockListMFR.bedroll, (InformationBase) null)).registerStat()
		//                .setPage(construction).setUnlocked();
		tool_rack = (new InformationBase("tool_rack", 2, 2, 0, MineFantasyBlocks.TOOL_RACK_WOOD, null))
				.registerStat().setPage(construction).setUnlocked();
		food_box = (new InformationBase("food_box", 2, 4, 1, (MineFantasyBlocks.FOOD_BOX_BASIC).construct(MineFantasyMaterials.Names.OAK_WOOD), tool_rack)).registerStat()
				.setPage(construction).setUnlocked();
		ammo_box = (new InformationBase("ammo_box", 4, 4, 1, (MineFantasyBlocks.AMMO_BOX_BASIC).construct(MineFantasyMaterials.Names.OAK_WOOD), food_box)).registerStat()
				.setPage(construction).addSkill(Skill.CONSTRUCTION, 15);
		big_box = (new InformationBase("big_box", 6, 4, 1, (MineFantasyBlocks.CRATE_BASIC).construct(MineFantasyMaterials.Names.OAK_WOOD), ammo_box)).registerStat()
				.setPage(construction).addSkill(Skill.CONSTRUCTION, 25);

		// COOKING -The Cheapest
		cooking_utensils = (new InformationBase("cooking_utensils", -1, 0, 0, MineFantasyItems.PIE_TRAY, null))
				.registerStat().setPage(provisioning).setUnlocked();
		firepit = (new InformationBase("firepit", 0, 0, 0, MineFantasyBlocks.FIREPIT, null)).registerStat()
				.setPage(provisioning).setUnlocked();

		generic_meat = (new InformationBase("generic_meat", 0, -1, 0, MineFantasyItems.GENERIC_MEAT_UNCOOKED,
				null)).registerStat().setPage(provisioning).setUnlocked();
		stew = (new InformationBase("stew", 0, -3, 0, MineFantasyItems.STEW, generic_meat)).registerStat()
				.setPage(provisioning).setUnlocked();
		jerky = (new InformationBase("jerky", 0, -5, 1, MineFantasyItems.JERKY, stew)).registerStat().setPage(provisioning)
				.addSkill(Skill.PROVISIONING, 10);
		sausage = (new InformationBase("sausage", 2, -5, 2, MineFantasyItems.SAUSAGE_COOKED, jerky)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 35);
		sandwitch = (new InformationBase("sandwitch", 1, -7, 3, MineFantasyItems.SANDWITCH_MEAT, jerky)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 15);
		sandwitch_big = (new InformationBase("sandwitch_big", 3, -7, 3, MineFantasyItems.SANDWITCH_BIG, sandwitch))
				.registerStat().setPage(provisioning).addSkill(Skill.PROVISIONING, 25);
		meatpie = (new InformationBase("meatpie", -1, -7, 2, MineFantasyBlocks.PIE_MEAT, jerky)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 35);
		shepard_pie = (new InformationBase("shepard_pie", -2, -9, 3, MineFantasyBlocks.PIE_SHEPARDS, meatpie)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 80);
		bread = (new InformationBase("bread", 1, 0, 0, MineFantasyItems.BREADROLL, null)).registerStat()
				.setPage(provisioning).setUnlocked();
		oats = (new InformationBase("oats", 3, 0, 0, MineFantasyItems.OATS, bread)).registerStat().setPage(provisioning)
				.setUnlocked();

		salt = (new InformationBase("salt", -2, -2, 0, MineFantasyItems.SALT, null)).registerStat()
				.setPage(provisioning).setUnlocked();
		jug = (new InformationBase("jug", -1, -2, 0, MineFantasyItems.JUG_WATER, null)).registerStat()
				.setPage(provisioning).setUnlocked();
		berry = (new InformationBase("berry", 0, 1, 0, MineFantasyItems.BERRIES, null)).registerStat()
				.setPage(provisioning).setUnlocked();
		icing = (new InformationBase("icing", -1, 2, 0, MineFantasyItems.ICING, berry)).registerStat().setPage(provisioning)
				.setUnlocked();
		sweetroll = (new InformationBase("sweetroll", 0, 3, 2, MineFantasyItems.SWEETROLL, berry)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 15);
		eclair = (new InformationBase("eclair", 2, 3, 3, MineFantasyItems.ECLAIR, sweetroll)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 75);
		cake = (new InformationBase("cake", 0, 5, 2, MineFantasyBlocks.CAKE_VANILLA, sweetroll)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 25);
		carrot_cake = (new InformationBase("carrot_cake", -1, 7, 3, MineFantasyBlocks.CAKE_CARROT, cake)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 40);
		chocolate_cake = (new InformationBase("chocolate_cake", 1, 7, 3, MineFantasyBlocks.CAKE_CHOCOLATE, cake)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 40);
		black_forest_cake = (new InformationBase("black_forest_cake", 1, 9, 4, MineFantasyBlocks.CAKE_BF, chocolate_cake)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 90);
		berry_pie = (new InformationBase("berry_pie", 2, 1, 2, MineFantasyBlocks.PIE_BERRY, berry)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 20);
		apple_pie = (new InformationBase("apple_pie", 4, 1, 2, MineFantasyBlocks.PIE_APPLE, berry_pie)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 30);

		cheese = (new InformationBase("cheese", 1, -1, 0, MineFantasyBlocks.CHEESE_WHEEL, null))
				.registerStat().setPage(provisioning).setUnlocked();
		cheese_roll = (new InformationBase("cheese_roll", 3, -1, 2, MineFantasyItems.CHEESE_ROLL, cheese)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 15);

		bandage = (new InformationBase("bandage", -3, 0, 0, MineFantasyItems.BANDAGE_WOOL, null))
				.registerStat().setPage(provisioning).setUnlocked();
		bandage_advanced = (new InformationBase("bandage_advanced", -5, -1, 2, MineFantasyItems.BANDAGE_TOUGH, bandage)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 40);

		// MASTERY
		toughness = (new InformationBase("toughness", -1, 0, 0, MineFantasyItems.STANDARD_PLATE_HELMET,
				null)).registerStat().setPage(mastery).addSkill(Skill.COMBAT, 10).setPerk();
		fitness = (new InformationBase("fitness", 1, 0, 0, MineFantasyItems.DRAGON_HEART, null))
				.registerStat().setPage(mastery).addSkill(Skill.COMBAT, 20).setPerk();
		armour_pro = (new InformationBase("armour_pro", 2, -1, 0, Items.DIAMOND_CHESTPLATE, fitness)).registerStat()
				.setPage(mastery).addSkill(Skill.COMBAT, 60).setPerk();
		parry_pro = (new InformationBase("parry_pro", -2, -1, 0, Items.IRON_SWORD, toughness)).registerStat()
				.setPage(mastery).addSkill(Skill.COMBAT, 20).setPerk();
		counter_attack = (new InformationBase("counter_attack", -1, -2, 0, MineFantasyItems.STANDARD_WARAXE, parry_pro))
				.registerStat().setPage(mastery).addSkill(Skill.COMBAT, 25).setPerk();
		auto_parry = (new InformationBase("auto_parry", -3, -2, 0, MineFantasyItems.STANDARD_SWORD, parry_pro))
				.registerStat().setPage(mastery).addSkill(Skill.COMBAT, 50).setPerk();
		first_aid = (new InformationBase("first_aid", 0, 1, 0, MineFantasyItems.BANDAGE_WOOL, null))
				.registerStat().setPage(mastery).addSkill(Skill.PROVISIONING, 25).setPerk();
		doctor = (new InformationBase("doctor", 0, 3, 0, MineFantasyItems.SYRINGE, first_aid)).registerStat().setPage(mastery)
				.addSkill(Skill.PROVISIONING, 50).setPerk();
		scrapper = (new InformationBase("scrapper", 0, -1, 0, MineFantasyBlocks.SALVAGE_BASIC, null))
				.registerStat().setPage(mastery).addSkill(Skill.ARTISANRY, 35).setPerk();

	}

	private static Object getMetalTier(String string) {
		CustomMaterial mat = MetalMaterial.getMaterial(string);
		if (mat != CustomMaterial.NONE){
			return mat.crafterTier;
		}
		return "?";
	}

	public static class ArtefactListMFR {
		public static void init() {
			addArtisanry();
			addConstruction();
			addProvisioning();
			addEngineering();
			addArtefacts();
		}

		private static void addEngineering() {
			add(blackpowder, MineFantasyItems.NITRE, MineFantasyItems.SULFUR, Items.COAL, Items.GUNPOWDER);
			add(advanced_blackpowder, Items.GLOWSTONE_DUST, Items.REDSTONE);
			add(tungsten, MineFantasyItems.ORE_TUNGSTEN, MineFantasyBlocks.TUNGSTEN_ORE);
			add(coke, Items.COAL, Items.REDSTONE);
			add(spyglass, MineFantasyItems.BRONZE_GEARS, Blocks.GLASS);
			add(parachute, Items.FEATHER, Blocks.WOOL);
			add(syringe, Items.POTIONITEM);
			add(engineering_tanner, MineFantasyItems.BRONZE_GEARS);
			add(bomb_arrow, Items.FEATHER, MineFantasyItems.BLACKPOWDER);
			add(bomb_press, MineFantasyItems.BRONZE_GEARS, Blocks.LEVER);
			add(bombs, MineFantasyItems.BLACKPOWDER, Items.REDSTONE, Items.STRING);
			add(shrapnel, Items.FLINT);
			add(firebomb, MineFantasyItems.DRAGON_HEART, Items.MAGMA_CREAM);
			add(sticky_bomb, Items.SLIME_BALL);
			add(mine_ceramic, MineFantasyItems.BLACKPOWDER, Blocks.STONE_PRESSURE_PLATE);
			add(bomb_iron, Items.IRON_INGOT);
			add(mine_iron, Items.IRON_INGOT);
			add(bomb_obsidian, Blocks.OBSIDIAN);
			add(mine_obsidian, Blocks.OBSIDIAN);
			add(bomb_crystal, Items.DIAMOND);
			add(mine_crystal, Items.DIAMOND);

			add(crossbows, Items.STRING, MineFantasyItems.TIMBER, Blocks.LEVER);
			add(crossbow_shaft_advanced, MineFantasyItems.TUNGSTEN_GEARS);
			add(crossbow_head_advanced, MineFantasyItems.TUNGSTEN_GEARS);
			add(crossbow_ammo, MineFantasyItems.TUNGSTEN_GEARS);
			add(crossbow_scope, MineFantasyItems.SPYGLASS);
			add(crossbow_bayonet, MineFantasyItems.STANDARD_DAGGER);
		}

		private static void addProvisioning() {
			add(jerky, MineFantasyItems.GENERIC_MEAT_UNCOOKED);
			add(sausage, MineFantasyItems.GENERIC_MEAT_UNCOOKED, MineFantasyItems.GUTS);
			add(sandwitch, MineFantasyItems.GENERIC_MEAT_UNCOOKED, MineFantasyItems.CHEESE_SLICE, Items.BREAD);
			add(sandwitch_big, MineFantasyItems.GENERIC_MEAT_UNCOOKED, MineFantasyItems.CHEESE_SLICE, Items.BREAD);

			add(meatpie, MineFantasyItems.GENERIC_MEAT_UNCOOKED, MineFantasyItems.PASTRY);
			add(shepard_pie, MineFantasyItems.GENERIC_MEAT_UNCOOKED, Items.POTATO, MineFantasyItems.PASTRY);
			add(berry_pie, MineFantasyItems.BERRIES, MineFantasyItems.PASTRY);
			add(apple_pie, Items.APPLE, MineFantasyItems.PASTRY);

			add(sweetroll, Items.SUGAR, MineFantasyItems.BERRIES, MineFantasyItems.SUGAR_POT);
			add(eclair, Items.EGG, new ItemStack(Items.DYE, 1, 3), MineFantasyItems.PASTRY);
			add(cheese_roll, Items.BREAD, MineFantasyItems.CHEESE_SLICE);

			add(cake, MineFantasyItems.FLOUR, Items.EGG);
			add(carrot_cake, MineFantasyItems.FLOUR, Items.EGG, Items.CARROT);
			add(chocolate_cake, MineFantasyItems.FLOUR, Items.EGG, new ItemStack(Items.DYE, 1, 3));
			add(black_forest_cake, MineFantasyItems.FLOUR, Items.EGG, new ItemStack(Items.DYE, 1, 3),
					MineFantasyItems.BERRIES_JUICY);

			add(bandage_advanced, Blocks.WOOL, Items.LEATHER);
		}

		private static void addConstruction() {
			add(refined_planks, MineFantasyItems.NAIL);
			add(clay_wall, Items.CLAY_BALL, MineFantasyItems.NAIL);
			add(paint_brush, Blocks.WOOL);
			add(decorated_stone, Items.IRON_INGOT, MineFantasyBlocks.REINFORCED_STONE);
			add(bed_roll, Items.BED);
			add(ammo_box, Blocks.CHEST);
			add(big_box, Blocks.CHEST);

		}

		private static void addArtisanry() {
			for (ItemStack copper : OreDictionary.getOres("ingotCopper")) {
				for (ItemStack tin : OreDictionary.getOres("ingotTin")) {
					add(smelt_bronze, copper, tin);
				}
			}
			add(coal_flux, Items.COAL, MineFantasyItems.FLUX);
			add(smelt_iron, Blocks.IRON_ORE);
			add(firebrick_crucible, MineFantasyItems.FIRECLAY);
			add(blast_furnace, Items.IRON_INGOT, Blocks.IRON_ORE, Blocks.FURNACE, MineFantasyBlocks.BLOOMERY,
					MineFantasyBlocks.LIMESTONE, MineFantasyItems.KAOLINITE);
			add(big_furnace, Items.IRON_INGOT, Blocks.FURNACE, MineFantasyBlocks.BLOOMERY, MineFantasyItems.KAOLINITE,
					Items.COAL);
			for (ItemStack pig : OreDictionary.getOres("ingotPigIron")) {
				add(smelt_steel, pig);
			}
			for (ItemStack steel : OreDictionary.getOres("ingotSteel")) {
				add(smelt_encrusted, steel, Items.DIAMOND);
				add(smelt_obsidian, steel, Blocks.OBSIDIAN);
				for (ItemStack bronze : OreDictionary.getOres("ingotBronze")) {
					add(smelt_black_steel, Blocks.OBSIDIAN, bronze, steel);
				}
			}
			for (ItemStack black : OreDictionary.getOres("ingotBlackSteel")) {
				for (ItemStack silver : OreDictionary.getOres("ingotSilver")) {
					add(smelt_blue_steel, Items.BLAZE_POWDER, silver, black, new ItemStack(Items.DYE, 1, 4),
							MineFantasyItems.FLUX_STRONG);
				}
				add(smelt_red_steel, Items.BLAZE_POWDER, Items.GOLD_INGOT, Items.REDSTONE, black,
						MineFantasyItems.FLUX_STRONG);
			}

			for (ItemStack silver : OreDictionary.getOres("ingotSilver")) {
				add(smelt_mithril, MineFantasyBlocks.MYTHIC_ORE, silver, MineFantasyItems.ANCIENT_JEWEL_MITHRIL);
			}
			add(smelt_adamantium, MineFantasyBlocks.MYTHIC_ORE, Items.GOLD_INGOT, MineFantasyItems.ANCIENT_JEWEL_ADAMANT);
			add(smelt_master, MineFantasyItems.ANCIENT_JEWEL_ADAMANT, MineFantasyItems.ANCIENT_JEWEL_MITHRIL, MineFantasyItems.ANCIENT_JEWEL_MASTER);

			for (ItemStack mithril : OreDictionary.getOres("ingotMithril")) {
				add(smelt_mithium, mithril, Items.GHAST_TEAR, Items.DIAMOND, MineFantasyItems.ANCIENT_JEWEL_ADAMANT);
				for (ItemStack adamant : OreDictionary.getOres("ingotAdamantium")) {
					add(smelt_ignotumite, adamant, Items.EMERALD, Items.BLAZE_POWDER);
					add(smelt_ender, adamant, mithril, Items.ENDER_PEARL);
				}
			}
			add(craft_armour_medium, Items.LEATHER);
			add(craft_armour_heavy, Items.LEATHER, Blocks.WOOL, Items.FEATHER);
			add(smelt_dragonforged, MineFantasyItems.DRAGON_HEART);

			add(craft_ornate, new ItemStack(Items.DYE, 1, 4));

			add(arrows_bodkin, Items.FEATHER);
			add(arrows_broad, Items.FEATHER, Items.FLINT);

			add(repair_basic, Items.LEATHER, Items.FLINT, MineFantasyItems.NAIL);
			add(repair_advanced, MineFantasyBlocks.REPAIR_BASIC, Items.SLIME_BALL, Items.STRING);
			add(repair_ornate, Items.DIAMOND, Items.GOLD_INGOT, MineFantasyBlocks.REPAIR_ADVANCED);
		}

		private static void addArtefacts() {
			register(MineFantasyItems.ANCIENT_JEWEL_ADAMANT);
			register(MineFantasyItems.ANCIENT_JEWEL_MITHRIL);
			register(MineFantasyItems.ANCIENT_JEWEL_MASTER);

			register(MineFantasyBlocks.SCHEMATIC_ALLOY_ITEM);
			register(MineFantasyBlocks.SCHEMATIC_BOMB_ITEM);
			register(MineFantasyBlocks.SCHEMATIC_CROSSBOW_ITEM);
			register(MineFantasyBlocks.SCHEMATIC_FORGE_ITEM);
			register(MineFantasyBlocks.SCHEMATIC_COGWORK_ITEM);
			register(MineFantasyBlocks.SCHEMATIC_GEARS_ITEM);
		}

		private static void add(InformationBase info, Object... artifacts) {
			for (Object artifact : artifacts) {
				ResearchArtefacts.addArtefact(artifact, info);
			}
		}

		public static void register(Item item) {
			if (item instanceof IArtefact){
				if (((IArtefact) item).getResearches() != null) {
					for (String research : ((IArtefact) item).getResearches()) {
						ResearchArtefacts.addArtefact(new ItemStack(item, 1), research);
					}
				}
			}
		}
	}
}
