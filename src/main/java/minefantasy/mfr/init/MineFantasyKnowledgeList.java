package minefantasy.mfr.init;

import minefantasy.mfr.api.refine.Alloy;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.material.MetalMaterial;
import minefantasy.mfr.mechanics.knowledge.InformationBase;
import minefantasy.mfr.mechanics.knowledge.InformationList;
import minefantasy.mfr.mechanics.knowledge.InformationPage;
import minefantasy.mfr.mechanics.knowledge.ResearchArtefacts;
import minefantasy.mfr.recipe.IAnvilRecipe;
import minefantasy.mfr.recipe.ICarpenterRecipe;
import minefantasy.mfr.recipe.RecipeHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class MineFantasyKnowledgeList {

	// note: please follow the naming convention for static final vars, these should be all upper (https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html)
	public static final IRecipe STICK_RECIPE = RecipeHelper.getMFRRecipe("sticks");
	public static final IRecipe PLANK_RECIPE = RecipeHelper.getMFRRecipe("plank_cut");
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
	public static final IRecipe JUG_PLANT_OIL_RECIPE = RecipeHelper.getMFRRecipe("plant_oil");
	public static final IRecipe JUG_WATER_RECIPE = RecipeHelper.getMFRRecipe("jug_water");
	public static final IRecipe JUG_MILK_RECIPE = RecipeHelper.getMFRRecipe("jug_milk");
	public static final IRecipe DRYROCKS_RECIPE = ConfigHardcore.HCCallowRocks ? RecipeHelper.getMFRRecipe("dryrocks_hc") : RecipeHelper.getMFRRecipe("dryrocks");
	public static final ArrayList<IRecipe> stoneBricksR = new ArrayList<IRecipe>();
	public static final ArrayList<IAnvilRecipe> barR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> baringotR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> talismanRecipe = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> barsR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> mailRecipes = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> mailHelmetR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> mailChestR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> mailLegsR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> mailBootsR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> scaleRecipes = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> scaleHelmetR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> scaleChestR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> scaleLegsR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> scaleBootsR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> splintRecipes = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> splintHelmetR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> splintChestR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> splintLegsR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> splintBootsR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> plateRecipes = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> plateHelmetR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> plateChestR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> plateLegsR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> plateBootsR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> ornateWepsR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<IAnvilRecipe> advOrnateWepsR = new ArrayList<IAnvilRecipe>();
	public static final ArrayList<ICarpenterRecipe> arrowR = new ArrayList<ICarpenterRecipe>();
	public static final ArrayList<ICarpenterRecipe> meatRecipes = new ArrayList<ICarpenterRecipe>();
	public static final ArrayList<ICarpenterRecipe> easyPaintPlank = new ArrayList<ICarpenterRecipe>();
	public static final ArrayList<ICarpenterRecipe> refinedPlankR = new ArrayList<ICarpenterRecipe>();
	public static InformationPage artisanry = InformationList.artisanry;
	public static InformationPage construction = InformationList.construction;
	public static InformationPage engineering = InformationList.engineering;
	public static InformationPage provisioning = InformationList.provisioning;
	public static InformationPage mastery = InformationList.mastery;
	// BASICS -FREE
	public static InformationBase carpenter, gettingStarted, salvage, research, talisman, ores, plants, chimney,
			tanning, commodities, dust, craftCrafters, stamina, combat, craftArmourBasic, craftHCCTools, firemaker,
			dragons, minotaurs;
	public static InformationBase bloomery, crucible, crucible2, smeltCopper, smeltBronze, smeltIron, coalflux, bigfurn,
			blastfurn, smeltPig, smeltSteel, encrusted, obsidian, smeltBlackSteel, smeltDragonforge, smeltBlueSteel,
			smeltRedSteel, smeltMithril, smeltAdamant, smeltMaster, smeltMithium, smeltIgnotumite, smeltEnderforge;
	public static InformationBase bellows, trough, forge, anvil, bar, apron, craftTools, craftAdvTools, craftWeapons,
			craftAdvWeapons, arrows, craftOrnate, craftArmourLight, craftArmourMedium, craftArmourHeavy, arrowsBodkin,
			arrowsBroad, repair_basic, repair_advanced, repair_ornate;
	public static InformationBase coke, etools, ecomponents, tungsten, climber, spyglass, parachute, syringe, engTanner,
			advforge, advcrucible, blackpowder, advblackpowder, bombs, bpress, bombarrow, bombFuse, shrapnel, firebomb,
			stickybomb, bombCeramic, bombIron, bombObsidian, bombCrystal, mineCeramic, mineIron, mineObsidian,
			mineCrystal, crossbows, crossShafts, crossHeads, crossHeadAdvanced, crossShaftAdvanced, crossAmmo,
			crossScope, crossBayonet, cogArmour, compPlate;
	public static InformationBase constructionPts, refined_planks, reinforced_stone, clay_wall, glass, brickworks,
			decorated_stone, bars, thatch, easyRefine, paint_brush, tool_rack, food_box, ammo_box, big_box, bed_roll;
	public static InformationBase toughness, fitness, armourpro, parrypro, counteratt, autoparry, scrapper, firstaid,
			doctor;
	public static InformationBase cookingutensil, firepit, generic_meat, stew, jerky, saussage, sandwitch, sandwitch_big,
			meatpie, shepardpie, bread, oats, salt, jug, berry, icing, sweetroll, eclair, cake, carrotcake, chococake,
			bfcake, applepie, berrypie, cheese, cheeseroll, bandage, bandageadv;
	public static IRecipe waterJugR, milkJugR, plantOilR;
	public static IAnvilRecipe hunkR, ingotR, bucketR, crestR;
	public static ICarpenterRecipe artBookR, conBookR, proBookR, engBookR, comBookR, artBook2R, conBook2R, proBook2R,
			engBook2R, comBook2R;
	public static IAnvilRecipe greatTalismanRecipe;
	public static ICarpenterRecipe fireclayR, fireBrickR, fireBricksR, fireBrickStairR, refinedPlankBlockR, clayWallR,
			bSalvageR, tannerRecipe, stoneAnvilRecipe, forgeRecipe, apronRecipe, woodTroughRecipe;
	public static ICarpenterRecipe researchTableRecipe, framedGlassR, windowR, thatchR, thatchStairR;
	public static IAnvilRecipe smokePipeR, framedStoneR, iframedStoneR, fluxR, nailR, rivetR;
	public static IAnvilRecipe tinderboxR, flintAndSteelR;
	public static ICarpenterRecipe dirtRockR, lStripsR, threadR, stringR, sharpRocksR, stonePickR, stoneAxeR,
			stoneSpadeR, stoneHoeR, stoneSwordR, stoneWarR, stoneMaceR, stoneSpearR, stoneHammerR, stoneTongsR,
			boneNeedleR, stoneKnifeR, quernR, stoneovenRecipe;
	public static Alloy[] reStone, bronze, steel, obsidalloy, black, red, blue, mithril, adamantium, ignotumite,
			mithium, enderforge, wolframiteR;
	public static IAnvilRecipe coalfluxR, encrustedR, steelR, obsidianHunkR, diamondR;
	public static ICarpenterRecipe nailPlanksR, nailStairR, refinedStairR, strongRackR, bellowsRecipe, bloomeryR,
			crucibleRecipe, advCrucibleRecipe, trilogyRecipe, chimneyRecipe, wideChimneyRecipe, extractChimneyRecipe;
	public static ICarpenterRecipe hideHelmR, hideChestR, hideLegsR, hideBootsR, roughHelmetR, roughChestR, roughLegsR,
			roughBootsR, reHelmetR, reChestR, reLegsR, reBootsR;
	public static IAnvilRecipe studHelmetR, studChestR, studLegsR, studBootsR;
	public static IAnvilRecipe pickR, axeR, spadeR, hoeR, shearsR;
	public static IAnvilRecipe daggerR, swordR, waraxeR, maceR, spearR, bowR, katanaR, gswordR, whammerR, battleaxeR,
			halbeardR, lanceR;
	public static IAnvilRecipe trowR, hvyPickR, hvyShovelR, handpickR, scytheR, mattockR, lumberR;
	public static IAnvilRecipe hammerR, tongsR, hvyHammerR, needleR, sawsR, knifeR, spannerR;
	public static IAnvilRecipe arrowheadR, bodkinheadR, broadheadR, crossBoltR;
	public static ICarpenterRecipe fletchingR, fletchingR2, malletR, spoonR;
	public static IAnvilRecipe ironPrepR, ironPrepR2, coalPrepR;
	public static IAnvilRecipe blastChamR, blastHeatR, bigFurnR, bigHeatR;
	public static ICarpenterRecipe padding[] = new ICarpenterRecipe[4];
	public static ICarpenterRecipe repairBasicR, repairAdvancedR, repairOrnateR;
	public static ICarpenterRecipe spyglassR, bombBenchCraft, bombPressCraft, advancedForgeR, engTannerR, autoCrucibleR;
	public static ICarpenterRecipe crudeBombR, bombFuseR, longFuseR;
	public static ICarpenterRecipe crossBenchCraft, crossStockWoodR, crossStockIronR, crossHandleWoodR, crossHeadLightR,
			crossHeadMediumR, crossHeadHeavyR, crossHeadAdvancedR, crossAmmoR, crossScopeR;
	public static ICarpenterRecipe bombCaseCeramicR, mineCaseCeramicR, bombCaseCrystalR, mineCaseCrystalR;
	public static IAnvilRecipe bombCaseIronR, mineCaseIronR, bombCaseObsidianR, mineCaseObsidianR, crossBayonetR;
	public static ICarpenterRecipe meatStripR, meatHunkR, gutsRecipe;
	public static IAnvilRecipe caketinRecipe;
	public static ICarpenterRecipe breadSliceR, pastryRecipe, doughRecipe, breadRecipe, curdRecipe, oatsRecipe,
			custardRecipe, icingRecipe, stewRecipe, saussageR, jerkyRecipe, meatPieRecipe, sandwitchRecipe,
			sandwitchBigRecipe, shepardRecipe, sweetrollRecipe, iceSR, eclairDoughR, eclairIceR, eclairFillR;
	public static ICarpenterRecipe pumpPieR, simpCakeR, simpCakeOut, berryR, appleR, cheeserollR, cakeR, carrotCakeR,
			chocoCakeR, bfCakeR, cakeI, carrotCakeI, chocoCakeI, bfCakeI;

	public static ICarpenterRecipe syringeR, parachuteR, bandageR, badBandageR, goodBandageR, cogShaftR;

	public static IAnvilRecipe hingeRecipe, brushRecipe, climbPickbR, iframeR, boltR, istrutR, bgearR, tgearR, stubeR,
			eatoolsR, bombarrowR, bombBoltR, compPlateR;
	public static ICarpenterRecipe mouldRecipe, jugRecipe, potRecipe, pieTrayRecipe, blackpowderRec, advblackpowderRec,
			magmaRefinedR, chocoRecipe, bedrollR;

	public static IAnvilRecipe frameBlockR, cogPulleyR, cogLegsR, cogChestR, cogHelmR;
	public static ArrayList<IAnvilRecipe> hugePlateR = new ArrayList<IAnvilRecipe>();
	public static ArrayList<IAnvilRecipe> cogPlateR = new ArrayList<IAnvilRecipe>();
	public static ArrayList<ICarpenterRecipe> sawnPlankR = new ArrayList<ICarpenterRecipe>();
	public static ArrayList<ICarpenterRecipe> plankPaneR = new ArrayList<ICarpenterRecipe>();
	public static ArrayList<ICarpenterRecipe> rackRecipe = new ArrayList<ICarpenterRecipe>();
	public static ArrayList<ICarpenterRecipe> foodboxR = new ArrayList<ICarpenterRecipe>();
	public static ArrayList<ICarpenterRecipe> ammoboxR = new ArrayList<ICarpenterRecipe>();
	public static ArrayList<ICarpenterRecipe> bigboxR = new ArrayList<ICarpenterRecipe>();
	public static ArrayList<ICarpenterRecipe> nailTroughR = new ArrayList<ICarpenterRecipe>();

	public static void init() {
		carpenter = (new InformationBase("carpenter", 0, -3, 0, MineFantasyBlocks.CARPENTER, (InformationBase) null))
				.registerStat().setUnlocked();
		salvage = (new InformationBase("salvage", 0, -4, 0, MineFantasyBlocks.SALVAGE_BASIC, (InformationBase) null))
				.registerStat().setUnlocked();
		gettingStarted = (new InformationBase("gettingStarted", 0, 0, 0, Items.BOOK, (InformationBase) null))
				.registerStat().setUnlocked();
		research = (new InformationBase("research", 1, 1, 0, MineFantasyItems.RESEARCH_BOOK, (InformationBase) null))
				.registerStat().setUnlocked();
		talisman = (new InformationBase("talisman", 4, 2, 0, MineFantasyItems.TALISMAN_LESSER, research)).registerStat()
				.setUnlocked();
		ores = (new InformationBase("ores", 1, -2, 0, MineFantasyBlocks.COPPER_ORE, (InformationBase) null)).registerStat()
				.setUnlocked();
		plants = (new InformationBase("plants", 1, -3, 0, MineFantasyBlocks.LOG_IRONBARK, ores)).registerStat().setUnlocked();
		chimney = (new InformationBase("chimney", 0, 2, 0, MineFantasyBlocks.CHIMNEY_STONE, (InformationBase) null))
				.registerStat().setUnlocked();
		tanning = (new InformationBase("tanning", 0, -2, 0, Items.LEATHER, (InformationBase) null)).registerStat()
				.setUnlocked().setSpecial();
		commodities = (new InformationBase("commodities", -1, -2, 0, MineFantasyItems.NAIL, (InformationBase) null))
				.registerStat().setUnlocked();
		dust = (new InformationBase("dust", -1, -3, 0, MineFantasyItems.CLAY_POT, commodities)).registerStat()
				.setUnlocked();
		craftCrafters = (new InformationBase("craftCrafters", -1, 1, 0, MineFantasyItems.STANDARD_HAMMER,
				(InformationBase) null)).registerStat().setUnlocked();
		stamina = (new InformationBase("stamina", -3, 1, 0, Items.FEATHER, craftCrafters)).registerStat().setUnlocked();
		combat = (new InformationBase("combat", -5, 2, 0, Items.IRON_SWORD, stamina)).registerStat().setUnlocked();
		craftArmourBasic = (new InformationBase("craftArmourBasic", -5, 0, 5,
				LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 0, 1), combat)).registerStat().setUnlocked();
		firemaker = (new InformationBase("firemaker", 5, 1, 0, Items.FLINT_AND_STEEL, (InformationBase) null))
				.registerStat().setUnlocked();

		dragons = (new InformationBase("dragons", -1, 3, 0, MineFantasyItems.DRAGON_HEART, (InformationBase) null))
				.registerStat().setUnlocked();
		minotaurs = (new InformationBase("minotaurs", 1, 3, 0, Items.BEEF, (InformationBase) null)).registerStat()
				.setUnlocked();

		// ARTISANRY -From Not very to the most Expensive
		bloomery = (new InformationBase("bloomery", 4, -2, 0, MineFantasyBlocks.BLOOMERY, crucible)).registerStat()
				.setPage(artisanry).setUnlocked().setSpecial();
		crucible = (new InformationBase("crucible", 4, 0, 0, MineFantasyBlocks.CRUCIBLE_STONE, (InformationBase) null))
				.registerStat().setPage(artisanry).setUnlocked().setSpecial();
		crucible2 = (new InformationBase("crucible2", 6, 0, 1, MineFantasyBlocks.CRUCIBLE_FIRECLAY, crucible)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 40);

		smeltCopper = (new InformationBase("smeltCopper", 1, 0, 0, MineFantasyItems.COPPER_INGOT, (InformationBase) null))
				.registerStat().setPage(artisanry).setUnlocked().setDescriptValues(getMetalTier(MineFantasyMaterials.Names.COPPER));
		smeltBronze = (new InformationBase("smeltBronze", 1, 2, 2, MineFantasyItems.BRONZE_INGOT, crucible)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 5).setDescriptValues(getMetalTier(MineFantasyMaterials.Names.BRONZE));
		smeltIron = (new InformationBase("smeltIron", 1, 4, 1, Items.IRON_INGOT, null)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 10).setDescriptValues(getMetalTier(MineFantasyMaterials.Names.IRON));
		coalflux = (new InformationBase("coalflux", 1, 6, 2, MineFantasyItems.COAL_FLUX, smeltIron)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 15);
		blastfurn = (new InformationBase("blastfurn", 2, 5, 5, MineFantasyBlocks.BLAST_HEATER, smeltIron))
				.registerStat().setPage(artisanry).setSpecial().addSkill(Skill.ARTISANRY, 25);
		bigfurn = (new InformationBase("bigfurn", 0, 5, 4, MineFantasyBlocks.FURNACE_STONE, smeltIron)).registerStat()
				.setPage(artisanry).setSpecial().addSkill(Skill.ARTISANRY, 10);
		smeltPig = (new InformationBase("smeltPig", 3, 3, 0, MineFantasyItems.PIG_IRON_INGOT, blastfurn)).registerStat()
				.setPage(artisanry).setUnlocked().addSkill(Skill.ARTISANRY, 25);
		smeltSteel = (new InformationBase("smeltSteel", 4, 5, 1, MineFantasyItems.STEEL_INGOT, smeltPig)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 25).setDescriptValues(getMetalTier(MineFantasyMaterials.Names.STEEL));
		encrusted = (new InformationBase("smeltEncrusted", 6, 5, 2, MineFantasyItems.DIAMOND_SHARDS, smeltSteel))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 35)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.ENCRUSTED));
		obsidian = (new InformationBase("smeltObsidian", 6, 3, 2, MineFantasyItems.OBSIDIAN_INGOT, smeltSteel))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 40)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.OBSIDIAN));
		smeltBlackSteel = (new InformationBase("smeltBlackSteel", 4, 7, 3, MineFantasyItems.BLACK_STEEL_INGOT, smeltSteel))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 50)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.BLACK_STEEL));
		smeltDragonforge = (new InformationBase("smeltDragonforge", -4, -1, 1, MineFantasyItems.DRAGON_HEART, null))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 50);
		smeltRedSteel = (new InformationBase("smeltRedSteel", 3, 9, 5, MineFantasyItems.RED_STEEL_INGOT, smeltBlackSteel))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 65)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.RED_STEEL));
		smeltBlueSteel = (new InformationBase("smeltBlueSteel", 5, 9, 5, MineFantasyItems.BLUE_STEEL_INGOT, smeltBlackSteel))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 65)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.BLUE_STEEL));
		smeltMithril = (new InformationBase("smeltMithril", 5, 12, 3, MineFantasyItems.MITHRIL_INGOT, null)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 75).setDescriptValues(getMetalTier(MineFantasyMaterials.Names.MITHRIL));
		smeltAdamant = (new InformationBase("smeltAdamantium", 3, 12, 3, MineFantasyItems.ADAMANTIUM_INGOT, null))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 75)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.ADAMANTIUM));

		smeltMaster = (new InformationBase("smeltMaster", 4, 13, 3, new ItemStack(MineFantasyItems.ANCIENT_JEWEL_MASTER, 1, 3),
				(InformationBase) null)).registerStat().setPage(artisanry).setSpecial().addSkill(Skill.ARTISANRY,
				100);
		smeltIgnotumite = (new InformationBase("smeltIgnotumite", 2, 15, 3, MineFantasyItems.IGNOTUMITE_INGOT, smeltMaster))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 100)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.IGNOTUMITE));
		smeltMithium = (new InformationBase("smeltMithium", 6, 15, 3, MineFantasyItems.MITHIUM_INGOT, smeltMaster))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 100)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.MITHIUM));
		smeltEnderforge = (new InformationBase("smeltEnder", 4, 16, 3, MineFantasyItems.ENDER_INGOT, smeltMaster))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 100)
				.setDescriptValues(getMetalTier(MineFantasyMaterials.Names.ENDER));

		craftHCCTools = (new InformationBase("craftHCCTools", -1, -2, 0, MineFantasyItems.STONE_PICK, (InformationBase) null))
				.registerStat().setPage(artisanry).setUnlocked();
		bellows = (new InformationBase("bellows", 0, -1, 0, MineFantasyBlocks.BELLOWS, (InformationBase) null)).registerStat()
				.setPage(artisanry).setUnlocked();
		trough = (new InformationBase("trough", 0, -2, 0, MineFantasyBlocks.TROUGH_WOOD, bellows)).registerStat()
				.setPage(artisanry).setUnlocked();
		forge = (new InformationBase("forge", 0, 0, 0, MineFantasyBlocks.FORGE, (InformationBase) null)).registerStat()
				.setPage(artisanry).setUnlocked();
		anvil = (new InformationBase("anvil", -1, 0, 0, MineFantasyBlocks.ANVIL_IRON, forge)).registerStat().setPage(artisanry)
				.setUnlocked().setSpecial();
		bar = (new InformationBase("bar", -1, 2, 0, MineFantasyItems.BAR, anvil)).registerStat().setPage(artisanry)
				.setUnlocked();
		apron = (new InformationBase("apron", -1, -1, 0, LeatherArmourListMFR.LEATHER_APRON, anvil)).registerStat()
				.setPage(artisanry).setUnlocked();
		craftTools = (new InformationBase("craftTools", -3, 2, 0, MineFantasyItems.STANDARD_PICK, bar)).registerStat()
				.setPage(artisanry).setUnlocked();
		craftAdvTools = (new InformationBase("craftAdvTools", -5, 2, 0, MineFantasyItems.STANDARD_HVYPICK, craftTools))
				.registerStat().setPage(artisanry).setUnlocked();
		craftWeapons = (new InformationBase("craftWeapons", -3, 1, 5, MineFantasyItems.STANDARD_SWORD, bar))
				.registerStat().setPage(artisanry).setUnlocked();
		craftAdvWeapons = (new InformationBase("craftAdvWeapons", -5, 1, 0, MineFantasyItems.STANDARD_BATTLEAXE,
				craftWeapons)).registerStat().setPage(artisanry).setUnlocked();
		arrows = (new InformationBase("arrows", -3, 4, 0, MineFantasyItems.STANDARD_ARROW, bar)).registerStat()
				.setPage(artisanry).setUnlocked();

		craftOrnate = (new InformationBase("craftOrnate", -3, -1, 1, MineFantasyItems.ORNATE_ITEMS, null)).registerStat()
				.setPage(artisanry).addSkill(Skill.ARTISANRY, 35);
		craftArmourLight = (new InformationBase("craftArmourLight", -3, 3, 1,
				LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 3, 1), anvil)).registerStat().setPage(artisanry)
				.setUnlocked();
		craftArmourMedium = (new InformationBase("craftArmourMedium", -4, 3, 1, MineFantasyItems.STANDARD_CHAIN_CHEST,
				craftArmourLight)).registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 0);
		craftArmourHeavy = (new InformationBase("craftArmourHeavy", -5, 3, 3, MineFantasyItems.STANDARD_PLATE_CHEST,
				craftArmourMedium)).registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 10);
		arrowsBodkin = (new InformationBase("arrowsBodkin", -4, 5, 1, MineFantasyItems.STANDARD_ARROW_BODKIN, arrows))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 10);
		arrowsBroad = (new InformationBase("arrowsBroad", -5, 5, 2, MineFantasyItems.STANDARD_ARROW_BROAD, arrows))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 20);

		// ENGINEERING - Highly Expensive
		etools = (new InformationBase("etools", 3, 0, 0, MineFantasyItems.STANDARD_SPANNER, (InformationBase) null))
				.registerStat().setPage(engineering).setUnlocked();
		ecomponents = (new InformationBase("ecomponents", 5, 0, 0, MineFantasyItems.BOLT, etools)).registerStat()
				.setPage(engineering).setUnlocked();
		tungsten = (new InformationBase("tungsten", 8, -1, 1, MineFantasyItems.TUNGSTEN_INGOT, ecomponents)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 20).addSkill(Skill.ARTISANRY, 20);
		coke = (new InformationBase("coke", 4, -1, 2, MineFantasyItems.COKE, ecomponents)).registerStat()
				.setPage(engineering);
		climber = (new InformationBase("climber", 7, 0, 1, MineFantasyItems.CLIMBING_PICK_BASIC, ecomponents)).registerStat()
				.setPage(engineering).setUnlocked();
		spyglass = (new InformationBase("spyglass", 8, 1, 2, new ItemStack(MineFantasyItems.SPYGLASS, 1, 2), climber))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 5);
		parachute = (new InformationBase("parachute", 9, 2, 2, MineFantasyItems.PARACHUTE, climber)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 10);
		syringe = (new InformationBase("syringe", 5, -2, 1, MineFantasyItems.SYRINGE_EMPTY, ecomponents)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 25);
		engTanner = (new InformationBase("engTanner", 5, 2, 1, MineFantasyBlocks.TANNER_METAL, ecomponents)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 40).addSkill(Skill.ARTISANRY, 25);
		advcrucible = (new InformationBase("advcrucible", 7, 3, 1, MineFantasyBlocks.CRUCIBLE_AUTO, engTanner)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 50).addSkill(Skill.ARTISANRY, 35);
		advforge = (new InformationBase("advforge", 9, 3, 1, MineFantasyBlocks.FORGE_METAL, advcrucible)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 75).addSkill(Skill.ARTISANRY, 50);

		blackpowder = (new InformationBase("blackpowder", 0, 0, 4, MineFantasyItems.BLACKPOWDER, (InformationBase) null))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 0);
		advblackpowder = (new InformationBase("advblackpowder", 2, -2, 2, MineFantasyItems.BLACKPOWDER_ADVANCED,
				blackpowder)).registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 50);
		bombs = (new InformationBase("bombs", 0, 2, 3, MineFantasyItems.BOMB_CUSTOM, blackpowder)).registerStat()
				.setPage(engineering).setSpecial().addSkill(Skill.ENGINEERING, 10);
		bpress = (new InformationBase("bpress", -1, 1, 2, MineFantasyBlocks.BOMB_PRESS, bombs)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 30);
		bombarrow = (new InformationBase("bombarrow", 1, 1, 2, MineFantasyItems.EXPLODING_ARROW, bombs)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 40);
		bombFuse = (new InformationBase("bombFuse", 2, 2, 0, MineFantasyItems.BOMB_FUSE, bombs)).registerStat()
				.setUnlocked().setPage(engineering);
		shrapnel = (new InformationBase("shrapnel", 0, 4, 1, MineFantasyItems.SHRAPNEL, bombs)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 25);
		firebomb = (new InformationBase("firebomb", 0, 6, 2, Items.BLAZE_POWDER, shrapnel)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 85);
		stickybomb = (new InformationBase("stickybomb", -2, 2, 1, Items.SLIME_BALL, bombs)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 50);
		bombCeramic = (new InformationBase("bombCeramic", 2, 3, 0, MineFantasyItems.BOMB_CASING, bombs)).registerStat()
				.setUnlocked().setPage(engineering);
		bombIron = (new InformationBase("bombIron", 4, 5, 1, MineFantasyItems.BOMB_CASING_IRON, bombCeramic))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 20);
		bombObsidian = (new InformationBase("bombObsidian", 4, 7, 2, MineFantasyItems.BOMB_CASING_OBSIDIAN, bombIron))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 35);
		bombCrystal = (new InformationBase("bombCrystal", 2, 9, 1, MineFantasyItems.BOMB_CASING_CRYSTAL, bombObsidian))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 40);
		mineCeramic = (new InformationBase("mineCeramic", -2, 3, 2, MineFantasyItems.MINE_CASING, bombs)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 15);
		mineIron = (new InformationBase("mineIron", -4, 5, 1, MineFantasyItems.MINE_CASING_IRON, mineCeramic))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 20);
		mineObsidian = (new InformationBase("mineObsidian", -4, 7, 2, MineFantasyItems.MINE_CASING_OBSIDIAN, mineIron))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 35);
		mineCrystal = (new InformationBase("mineCrystal", -2, 9, 1, MineFantasyItems.MINE_CASING_CRYSTAL, mineObsidian))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 40);

		crossbows = (new InformationBase("crossbows", -4, 0, 3, MineFantasyBlocks.CROSSBOW_BENCH, null)).registerStat()
				.setPage(engineering).setSpecial().addSkill(Skill.ENGINEERING, 0);
		crossShafts = (new InformationBase("crossShafts", -6, 2, 0, MineFantasyItems.CROSSBOW_STOCK_WOOD, crossbows))
				.registerStat().setPage(engineering).setUnlocked();
		crossShaftAdvanced = (new InformationBase("crossShaftAdvanced", -6, 4, 2, MineFantasyItems.CROSSBOW_STOCK_IRON,
				crossShafts)).registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 40);
		crossHeads = (new InformationBase("crossHeads", -2, -2, 0, MineFantasyItems.CROSSBOW_ARMS_BASIC, crossbows))
				.registerStat().setPage(engineering).setUnlocked();
		crossHeadAdvanced = (new InformationBase("crossHeadAdvanced", -3, -3, 2, MineFantasyItems.CROSSBOW_ARMS_ADVANCED,
				crossHeads)).registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 30);
		crossBayonet = (new InformationBase("crossBayonet", -1, -3, 1, MineFantasyItems.CROSSBOW_BAYONET, crossHeads))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 10);
		crossAmmo = (new InformationBase("crossAmmo", -5, 3, 1, MineFantasyItems.CROSSBOW_AMMO, crossShafts)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 30);
		crossScope = (new InformationBase("crossScope", -7, 3, 1, MineFantasyItems.CROSSBOW_SCOPE, crossShafts))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 20);

		cogArmour = (new InformationBase("cogArmour", 8, -3, 2, MineFantasyBlocks.BLOCKCOGWORK_HELM, tungsten)).registerStat()
				.setPage(engineering).addSkill(Skill.ENGINEERING, 60);
		compPlate = (new InformationBase("compPlate", 10, -3, 1, MineFantasyItems.COMPOSITE_ALLOY_INGOT, tungsten))
				.registerStat().setPage(engineering).addSkill(Skill.ENGINEERING, 50)
				.addSkill(Skill.ARTISANRY, 40);

		repair_basic = (new InformationBase("repair_basic", 8, 0, 2, MineFantasyBlocks.REPAIR_BASIC, (InformationBase) null))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 10);
		repair_advanced = (new InformationBase("repair_advanced", 10, 0, 3, MineFantasyBlocks.REPAIR_ADVANCED, repair_basic))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 25);
		repair_ornate = (new InformationBase("repair_ornate", 12, 2, 3, MineFantasyBlocks.REPAIR_ORNATE, repair_advanced))
				.registerStat().setPage(artisanry).addSkill(Skill.ARTISANRY, 50);

		constructionPts = (new InformationBase("constructionPts", 0, 0, 0, MineFantasyItems.TIMBER_CUT,
				(InformationBase) null)).registerStat().setPage(construction).setUnlocked();
		reinforced_stone = (new InformationBase("reinforced_stone", 1, 0, 0, MineFantasyBlocks.REINFORCED_STONE,
				(InformationBase) null)).registerStat().setPage(construction).setUnlocked();
		glass = (new InformationBase("glass", 0, 1, 0, MineFantasyBlocks.FRAMED_GLASS, (InformationBase) null)).registerStat()
				.setPage(construction).setUnlocked();
		brickworks = (new InformationBase("brickworks", 3, 0, 0, MineFantasyBlocks.COBBLE_BRICK, reinforced_stone))
				.registerStat().setPage(construction).setUnlocked();
		bars = (new InformationBase("bars", 0, 3, 0, MineFantasyBlocks.STEEL_BARS, glass)).registerStat().setPage(construction)
				.setUnlocked();
		thatch = (new InformationBase("thatch", 0, -3, 0, MineFantasyBlocks.THATCH, clay_wall)).registerStat()
				.setPage(construction).setUnlocked();
		refined_planks = (new InformationBase("refined_planks", -1, 0, 1, MineFantasyBlocks.REFINED_PLANKS,
				(InformationBase) null)).registerStat().setPage(construction).addSkill(Skill.CONSTRUCTION, 0);
		clay_wall = (new InformationBase("clay_wall", 0, -1, 2, MineFantasyBlocks.CLAY_WALL, (InformationBase) null))
				.registerStat().setPage(construction).addSkill(Skill.CONSTRUCTION, 5);
		//        paint_brush = (new InformationBase("paint_brush", -3, 0, 1, BlockListMFR.paint_brush, refined_planks))
		//                .registerStat().setPage(construction).addSkill(Skill.CONSTRUCTION, 10);
		decorated_stone = (new InformationBase("decorated_stone", 5, 0, 2, MineFantasyBlocks.REINFORCED_STONE_FRAMED,
				brickworks)).registerStat().setPage(construction).addSkill(Skill.CONSTRUCTION, 15);

		//        bed_roll = (new InformationBase("bed_roll", 2, 3, 1, BlockListMFR.bedroll, (InformationBase) null)).registerStat()
		//                .setPage(construction).setUnlocked();
		tool_rack = (new InformationBase("tool_rack", 2, 2, 0, MineFantasyBlocks.TOOL_RACK_WOOD, (InformationBase) null))
				.registerStat().setPage(construction).setUnlocked();
		food_box = (new InformationBase("food_box", 2, 4, 1, (MineFantasyBlocks.FOOD_BOX_BASIC).construct(MineFantasyMaterials.Names.OAK_WOOD), tool_rack)).registerStat()
				.setPage(construction).setUnlocked();
		ammo_box = (new InformationBase("ammo_box", 4, 4, 1, (MineFantasyBlocks.AMMO_BOX_BASIC).construct(MineFantasyMaterials.Names.OAK_WOOD), food_box)).registerStat()
				.setPage(construction).addSkill(Skill.CONSTRUCTION, 15);
		big_box = (new InformationBase("big_box", 6, 4, 1, (MineFantasyBlocks.CRATE_BASIC).construct(MineFantasyMaterials.Names.OAK_WOOD), ammo_box)).registerStat()
				.setPage(construction).addSkill(Skill.CONSTRUCTION, 25);

		// COOKING -The Cheapest
		cookingutensil = (new InformationBase("cookingutensil", -1, 0, 0, MineFantasyItems.PIE_TRAY, (InformationBase) null))
				.registerStat().setPage(provisioning).setUnlocked();
		firepit = (new InformationBase("firepit", 0, 0, 0, MineFantasyBlocks.FIREPIT, (InformationBase) null)).registerStat()
				.setPage(provisioning).setUnlocked();

		generic_meat = (new InformationBase("generic_meat", 0, -1, 0, MineFantasyItems.GENERIC_MEAT_UNCOOKED,
				(InformationBase) null)).registerStat().setPage(provisioning).setUnlocked();
		stew = (new InformationBase("stew", 0, -3, 0, MineFantasyItems.STEW, generic_meat)).registerStat()
				.setPage(provisioning).setUnlocked();
		jerky = (new InformationBase("jerky", 0, -5, 1, MineFantasyItems.JERKY, stew)).registerStat().setPage(provisioning)
				.addSkill(Skill.PROVISIONING, 10);
		saussage = (new InformationBase("saussage", 2, -5, 2, MineFantasyItems.SAUSAGE_COOKED, jerky)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 35);
		sandwitch = (new InformationBase("sandwitch", 1, -7, 3, MineFantasyItems.SANDWITCH_MEAT, jerky)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 15);
		sandwitch_big = (new InformationBase("sandwitch_big", 3, -7, 3, MineFantasyItems.SANDWITCH_BIG, sandwitch))
				.registerStat().setPage(provisioning).addSkill(Skill.PROVISIONING, 25);
		meatpie = (new InformationBase("meatpie", -1, -7, 2, MineFantasyBlocks.PIE_MEAT, jerky)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 35);
		shepardpie = (new InformationBase("shepardpie", -2, -9, 3, MineFantasyBlocks.PIE_SHEPARDS, meatpie)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 80);
		bread = (new InformationBase("bread", 1, 0, 0, MineFantasyItems.BREADROLL, (InformationBase) null)).registerStat()
				.setPage(provisioning).setUnlocked();
		oats = (new InformationBase("oats", 3, 0, 0, MineFantasyItems.OATS, bread)).registerStat().setPage(provisioning)
				.setUnlocked();

		salt = (new InformationBase("salt", -2, -2, 0, MineFantasyItems.SALT, (InformationBase) null)).registerStat()
				.setPage(provisioning).setUnlocked();
		jug = (new InformationBase("jug", -1, -2, 0, MineFantasyItems.JUG_WATER, (InformationBase) null)).registerStat()
				.setPage(provisioning).setUnlocked();
		berry = (new InformationBase("berry", 0, 1, 0, MineFantasyItems.BERRIES, (InformationBase) null)).registerStat()
				.setPage(provisioning).setUnlocked();
		icing = (new InformationBase("icing", -1, 2, 0, MineFantasyItems.ICING, berry)).registerStat().setPage(provisioning)
				.setUnlocked();
		sweetroll = (new InformationBase("sweetroll", 0, 3, 2, MineFantasyItems.SWEETROLL, berry)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 15);
		eclair = (new InformationBase("eclair", 2, 3, 3, MineFantasyItems.ECLAIR, sweetroll)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 75);
		cake = (new InformationBase("cake", 0, 5, 2, MineFantasyBlocks.CAKE_VANILLA, sweetroll)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 25);
		carrotcake = (new InformationBase("carrotcake", -1, 7, 3, MineFantasyBlocks.CAKE_CARROT, cake)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 40);
		chococake = (new InformationBase("chococake", 1, 7, 3, MineFantasyBlocks.CAKE_CHOCOLATE, cake)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 40);
		bfcake = (new InformationBase("bfcake", 1, 9, 4, MineFantasyBlocks.CAKE_BF, chococake)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 90);
		berrypie = (new InformationBase("berrypie", 2, 1, 2, MineFantasyBlocks.PIE_BERRY, berry)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 20);
		applepie = (new InformationBase("applepie", 4, 1, 2, MineFantasyBlocks.PIE_APPLE, berrypie)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 30);

		cheese = (new InformationBase("cheese", 1, -1, 0, MineFantasyBlocks.CHEESE_WHEEL, (InformationBase) null))
				.registerStat().setPage(provisioning).setUnlocked();
		cheeseroll = (new InformationBase("cheeseroll", 3, -1, 2, MineFantasyItems.CHEESE_ROLL, cheese)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 15);

		bandage = (new InformationBase("bandage", -3, 0, 0, MineFantasyItems.BANDAGE_WOOL, (InformationBase) null))
				.registerStat().setPage(provisioning).setUnlocked();
		bandageadv = (new InformationBase("bandageadv", -5, -1, 2, MineFantasyItems.BANDAGE_TOUGH, bandage)).registerStat()
				.setPage(provisioning).addSkill(Skill.PROVISIONING, 40);

		// MASTERY
		toughness = (new InformationBase("toughness", -1, 0, 0, MineFantasyItems.STANDARD_PLATE_HELMET,
				(InformationBase) null)).registerStat().setPage(mastery).addSkill(Skill.COMBAT, 10).setPerk();
		fitness = (new InformationBase("fitness", 1, 0, 0, MineFantasyItems.DRAGON_HEART, (InformationBase) null))
				.registerStat().setPage(mastery).addSkill(Skill.COMBAT, 20).setPerk();
		armourpro = (new InformationBase("armourpro", 2, -1, 0, Items.DIAMOND_CHESTPLATE, fitness)).registerStat()
				.setPage(mastery).addSkill(Skill.COMBAT, 60).setPerk();
		parrypro = (new InformationBase("parrypro", -2, -1, 0, Items.IRON_SWORD, toughness)).registerStat()
				.setPage(mastery).addSkill(Skill.COMBAT, 20).setPerk();
		counteratt = (new InformationBase("counteratt", -1, -2, 0, MineFantasyItems.STANDARD_WARAXE, parrypro))
				.registerStat().setPage(mastery).addSkill(Skill.COMBAT, 25).setPerk();
		autoparry = (new InformationBase("autoparry", -3, -2, 0, MineFantasyItems.STANDARD_SWORD, parrypro))
				.registerStat().setPage(mastery).addSkill(Skill.COMBAT, 50).setPerk();
		firstaid = (new InformationBase("firstaid", 0, 1, 0, MineFantasyItems.BANDAGE_WOOL, (InformationBase) null))
				.registerStat().setPage(mastery).addSkill(Skill.PROVISIONING, 25).setPerk();
		doctor = (new InformationBase("doctor", 0, 3, 0, MineFantasyItems.SYRINGE, firstaid)).registerStat().setPage(mastery)
				.addSkill(Skill.PROVISIONING, 50).setPerk();
		scrapper = (new InformationBase("scrapper", 0, -1, 0, MineFantasyBlocks.SALVAGE_BASIC, (InformationBase) null))
				.registerStat().setPage(mastery).addSkill(Skill.ARTISANRY, 35).setPerk();

	}

	private static Object getMetalTier(String string) {
		CustomMaterial mat = MetalMaterial.getMaterial(string);
		if (mat != null)
			return mat.crafterTier;

		return "?";
	}

	public static class ArtefactListMFR {
		public static void init() {
			addArtisanry();
			addConstruction();
			addProvisioning();
			addEngineering();
		}

		private static void addEngineering() {
			add(blackpowder, MineFantasyItems.NITRE, MineFantasyItems.SULFUR, Items.COAL, Items.GUNPOWDER);
			add(advblackpowder, Items.GLOWSTONE_DUST, Items.REDSTONE);
			add(tungsten, MineFantasyItems.ORE_TUNGSTEN, MineFantasyBlocks.TUNGSTEN_ORE);
			add(coke, Items.COAL, Items.REDSTONE);
			add(spyglass, MineFantasyItems.BRONZE_GEARS, Blocks.GLASS);
			add(parachute, Items.FEATHER, Blocks.WOOL);
			add(syringe, Items.POTIONITEM);
			add(engTanner, MineFantasyItems.BRONZE_GEARS);
			add(bombarrow, Items.FEATHER, MineFantasyItems.BLACKPOWDER);
			add(bpress, MineFantasyItems.BRONZE_GEARS, Blocks.LEVER);
			add(bombs, MineFantasyItems.BLACKPOWDER, Items.REDSTONE, Items.STRING);
			add(shrapnel, Items.FLINT);
			add(firebomb, MineFantasyItems.DRAGON_HEART, Items.MAGMA_CREAM);
			add(stickybomb, Items.SLIME_BALL);
			add(mineCeramic, MineFantasyItems.BLACKPOWDER, Blocks.STONE_PRESSURE_PLATE);
			add(bombIron, Items.IRON_INGOT);
			add(mineIron, Items.IRON_INGOT);
			add(bombObsidian, Blocks.OBSIDIAN);
			add(mineObsidian, Blocks.OBSIDIAN);
			add(bombCrystal, Items.DIAMOND);
			add(mineCrystal, Items.DIAMOND);

			add(crossbows, Items.STRING, MineFantasyItems.TIMBER, Blocks.LEVER);
			add(crossShaftAdvanced, MineFantasyItems.TUNGSTEN_GEARS);
			add(crossHeadAdvanced, MineFantasyItems.TUNGSTEN_GEARS);
			add(crossAmmo, MineFantasyItems.TUNGSTEN_GEARS);
			add(crossScope, MineFantasyItems.SPYGLASS);
			add(crossBayonet, MineFantasyItems.STANDARD_DAGGER);
		}

		private static void addProvisioning() {
			add(jerky, MineFantasyItems.GENERIC_MEAT_UNCOOKED);
			add(saussage, MineFantasyItems.GENERIC_MEAT_UNCOOKED, MineFantasyItems.GUTS);
			add(sandwitch, MineFantasyItems.GENERIC_MEAT_UNCOOKED, MineFantasyItems.CHEESE_SLICE, Items.BREAD);
			add(sandwitch_big, MineFantasyItems.GENERIC_MEAT_UNCOOKED, MineFantasyItems.CHEESE_SLICE, Items.BREAD);

			add(meatpie, MineFantasyItems.GENERIC_MEAT_UNCOOKED, MineFantasyItems.PASTRY);
			add(shepardpie, MineFantasyItems.GENERIC_MEAT_UNCOOKED, Items.POTATO, MineFantasyItems.PASTRY);
			add(berrypie, MineFantasyItems.BERRIES, MineFantasyItems.PASTRY);
			add(applepie, Items.APPLE, MineFantasyItems.PASTRY);

			add(sweetroll, Items.SUGAR, MineFantasyItems.BERRIES, MineFantasyItems.SUGAR_POT);
			add(eclair, Items.EGG, new ItemStack(Items.DYE, 1, 3), MineFantasyItems.PASTRY);
			add(cheeseroll, Items.BREAD, MineFantasyItems.CHEESE_SLICE);

			add(cake, MineFantasyItems.FLOUR, Items.EGG);
			add(carrotcake, MineFantasyItems.FLOUR, Items.EGG, Items.CARROT);
			add(chococake, MineFantasyItems.FLOUR, Items.EGG, new ItemStack(Items.DYE, 1, 3));
			add(bfcake, MineFantasyItems.FLOUR, Items.EGG, new ItemStack(Items.DYE, 1, 3),
					MineFantasyItems.BERRIES_JUICY);

			add(bandageadv, Blocks.WOOL, Items.LEATHER);
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
					add(smeltBronze, copper, tin);
				}
			}
			add(coalflux, Items.COAL, MineFantasyItems.FLUX);
			add(smeltIron, Blocks.IRON_ORE);
			add(crucible2, MineFantasyItems.FIRECLAY);
			add(blastfurn, Items.IRON_INGOT, Blocks.IRON_ORE, Blocks.FURNACE, MineFantasyBlocks.BLOOMERY,
					MineFantasyBlocks.LIMESTONE, MineFantasyItems.KAOLINITE);
			add(bigfurn, Items.IRON_INGOT, Blocks.FURNACE, MineFantasyBlocks.BLOOMERY, MineFantasyItems.KAOLINITE,
					Items.COAL);
			for (ItemStack pig : OreDictionary.getOres("ingotPigIron")) {
				add(smeltSteel, pig);
			}
			for (ItemStack steel : OreDictionary.getOres("ingotSteel")) {
				add(encrusted, steel, Items.DIAMOND);
				add(obsidian, steel, Blocks.OBSIDIAN);
				for (ItemStack bronze : OreDictionary.getOres("ingotBronze")) {
					add(smeltBlackSteel, Blocks.OBSIDIAN, bronze, steel);
				}
			}
			for (ItemStack black : OreDictionary.getOres("ingotBlackSteel")) {
				for (ItemStack silver : OreDictionary.getOres("ingotSilver")) {
					add(smeltBlueSteel, Items.BLAZE_POWDER, silver, black, new ItemStack(Items.DYE, 1, 4),
							MineFantasyItems.FLUX_STRONG);
				}
				add(smeltRedSteel, Items.BLAZE_POWDER, Items.GOLD_INGOT, Items.REDSTONE, black,
						MineFantasyItems.FLUX_STRONG);
			}

			for (ItemStack silver : OreDictionary.getOres("ingotSilver")) {
				add(smeltMithril, MineFantasyBlocks.MYTHIC_ORE, silver, MineFantasyItems.ANCIENT_JEWEL_MITHRIL);
			}
			add(smeltAdamant, MineFantasyBlocks.MYTHIC_ORE, Items.GOLD_INGOT, MineFantasyItems.ANCIENT_JEWEL_ADAMANT);
			add(smeltMaster, MineFantasyItems.ANCIENT_JEWEL_ADAMANT, MineFantasyItems.ANCIENT_JEWEL_MITHRIL, MineFantasyItems.ANCIENT_JEWEL_MASTER);

			for (ItemStack mithril : OreDictionary.getOres("ingotMithril")) {
				add(smeltMithium, mithril, Items.GHAST_TEAR, Items.DIAMOND, MineFantasyItems.ANCIENT_JEWEL_ADAMANT);
				for (ItemStack adamant : OreDictionary.getOres("ingotAdamantium")) {
					add(smeltIgnotumite, adamant, Items.EMERALD, Items.BLAZE_POWDER);
					add(smeltEnderforge, adamant, mithril, Items.ENDER_PEARL);
				}
			}
			add(craftArmourMedium, Items.LEATHER);
			add(craftArmourHeavy, Items.LEATHER, Blocks.WOOL, Items.FEATHER);
			add(smeltDragonforge, MineFantasyItems.DRAGON_HEART);

			add(craftOrnate, new ItemStack(Items.DYE, 1, 4));

			add(arrowsBodkin, Items.FEATHER);
			add(arrowsBroad, Items.FEATHER, Items.FLINT);

			add(repair_basic, Items.LEATHER, Items.FLINT, MineFantasyItems.NAIL);
			add(repair_advanced, MineFantasyBlocks.REPAIR_BASIC, Items.SLIME_BALL, Items.STRING);
			add(repair_ornate, Items.DIAMOND, Items.GOLD_INGOT, MineFantasyBlocks.REPAIR_ADVANCED);
		}

		private static void add(InformationBase info, Object... artifacts) {
			for (Object artifact : artifacts) {
				ResearchArtefacts.addArtefact(artifact, info);
			}
		}
	}
}
