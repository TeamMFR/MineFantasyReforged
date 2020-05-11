package minefantasy.mfr.init;

import minefantasy.mfr.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mfr.api.crafting.carpenter.ICarpenterRecipe;
import minefantasy.mfr.api.knowledge.InformationBase;
import minefantasy.mfr.api.knowledge.InformationList;
import minefantasy.mfr.api.knowledge.InformationPage;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.refine.Alloy;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.material.MetalMaterial;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;

public class KnowledgeListMFR {
    public static final ArrayList<IRecipe> plankRecipe = new ArrayList<IRecipe>();
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
    public static InformationBase cookingutensil, firepit, generic_meat, stew, jerky, saussage, sandwitch, sandwitchBig,
            meatpie, shepardpie, bread, oats, salt, jug, berry, icing, sweetroll, eclair, cake, carrotcake, chococake,
            bfcake, applepie, berrypie, cheese, cheeseroll, bandage, bandageadv;
    public static IRecipe firepitRecipe, cooktopRecipe, carpenterRecipe, waterJugR, milkJugR, plantOilR, sugarRecipe,
            stickRecipe, dryrocksR, meatpieOut, shepardOut, cheeseOut, berryOut, appleOut, pumpPieOut;
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
        carpenter = (new InformationBase("carpenter", 0, -3, 0, BlockListMFR.CARPENTER, (InformationBase) null))
                .registerStat().setUnlocked();
        salvage = (new InformationBase("salvage", 0, -4, 0, BlockListMFR.SALVAGE_BASIC, (InformationBase) null))
                .registerStat().setUnlocked();
        gettingStarted = (new InformationBase("gettingStarted", 0, 0, 0, Items.BOOK, (InformationBase) null))
                .registerStat().setUnlocked();
        research = (new InformationBase("research", 1, 1, 0, ToolListMFR.RESEARCH_BOOK, (InformationBase) null))
                .registerStat().setUnlocked();
        talisman = (new InformationBase("talisman", 4, 2, 0, ComponentListMFR.TALISMAN_LESSER, research)).registerStat()
                .setUnlocked();
        ores = (new InformationBase("ores", 1, -2, 0, BlockListMFR.ORE_COPPER, (InformationBase) null)).registerStat()
                .setUnlocked();
        plants = (new InformationBase("plants", 1, -3, 0, BlockListMFR.LOG_IRONBARK, ores)).registerStat().setUnlocked();
        chimney = (new InformationBase("chimney", 0, 2, 0, BlockListMFR.CHIMNEY_STONE, (InformationBase) null))
                .registerStat().setUnlocked();
        tanning = (new InformationBase("tanning", 0, -2, 0, Items.LEATHER, (InformationBase) null)).registerStat()
                .setUnlocked().setSpecial();
        commodities = (new InformationBase("commodities", -1, -2, 0, ComponentListMFR.NAIL, (InformationBase) null))
                .registerStat().setUnlocked();
        dust = (new InformationBase("dust", -1, -3, 0, ComponentListMFR.CLAY_POT, commodities)).registerStat()
                .setUnlocked();
        craftCrafters = (new InformationBase("craftCrafters", -1, 1, 0, CustomToolListMFR.STANDARD_HAMMER,
                (InformationBase) null)).registerStat().setUnlocked();
        stamina = (new InformationBase("stamina", -3, 1, 0, Items.FEATHER, craftCrafters)).registerStat().setUnlocked();
        combat = (new InformationBase("combat", -5, 2, 0, Items.IRON_SWORD, stamina)).registerStat().setUnlocked();
        craftArmourBasic = (new InformationBase("craftArmourBasic", -5, 0, 5,
                ArmourListMFR.armour(ArmourListMFR.LEATHER, 0, 1), combat)).registerStat().setUnlocked();
        firemaker = (new InformationBase("firemaker", 5, 1, 0, Items.FLINT_AND_STEEL, (InformationBase) null))
                .registerStat().setUnlocked();

        dragons = (new InformationBase("dragons", -1, 3, 0, ComponentListMFR.DRAGON_HEART, (InformationBase) null))
                .registerStat().setUnlocked();
        minotaurs = (new InformationBase("minotaurs", 1, 3, 0, Items.BEEF, (InformationBase) null)).registerStat()
                .setUnlocked();

        // ARTISANRY -From Not very to the most Expensive
        bloomery = (new InformationBase("bloomery", 4, -2, 0, BlockListMFR.BLOOMERY, crucible)).registerStat()
                .setPage(artisanry).setUnlocked().setSpecial();
        crucible = (new InformationBase("crucible", 4, 0, 0, BlockListMFR.CRUCIBLE, (InformationBase) null))
                .registerStat().setPage(artisanry).setUnlocked().setSpecial();
        crucible2 = (new InformationBase("crucible2", 6, 0, 1, BlockListMFR.CRUCIBLE_ADV_ACTIVE, crucible)).registerStat()
                .setPage(artisanry).addSkill(SkillList.artisanry, 40);

        smeltCopper = (new InformationBase("smeltCopper", 1, 0, 0, ComponentListMFR.COPPER_INGOT, (InformationBase) null))
                .registerStat().setPage(artisanry).setUnlocked().setDescriptValues(getMetalTier("copper"));
        smeltBronze = (new InformationBase("smeltBronze", 1, 2, 2, ComponentListMFR.BRONZE_INGOT, crucible)).registerStat()
                .setPage(artisanry).addSkill(SkillList.artisanry, 5).setDescriptValues(getMetalTier("bronze"));
        smeltIron = (new InformationBase("smeltIron", 1, 4, 1, Items.IRON_INGOT, null)).registerStat()
                .setPage(artisanry).addSkill(SkillList.artisanry, 10).setDescriptValues(getMetalTier("iron"));
        coalflux = (new InformationBase("coalflux", 1, 6, 2, ComponentListMFR.COAL_FLUX, smeltIron)).registerStat()
                .setPage(artisanry).addSkill(SkillList.artisanry, 15);
        blastfurn = (new InformationBase("blastfurn", 2, 5, 5, BlockListMFR.BLAST_HEATER_ACTIVE, smeltIron))
                .registerStat().setPage(artisanry).setSpecial().addSkill(SkillList.artisanry, 25);
        bigfurn = (new InformationBase("bigfurn", 0, 5, 4, BlockListMFR.FURNACE_STONE, smeltIron)).registerStat()
                .setPage(artisanry).setSpecial().addSkill(SkillList.artisanry, 10);
        smeltPig = (new InformationBase("smeltPig", 3, 3, 0, ComponentListMFR.PIG_IRON_INGOT, blastfurn)).registerStat()
                .setPage(artisanry).setUnlocked().addSkill(SkillList.artisanry, 25);
        smeltSteel = (new InformationBase("smeltSteel", 4, 5, 1, ComponentListMFR.STEEL_INGOT, smeltPig)).registerStat()
                .setPage(artisanry).addSkill(SkillList.artisanry, 25).setDescriptValues(getMetalTier("Steel"));
        encrusted = (new InformationBase("smeltEncrusted", 6, 5, 2, ComponentListMFR.DIAMOND_SHARDS, smeltSteel))
                .registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 35)
                .setDescriptValues(getMetalTier("encrusted"));
        obsidian = (new InformationBase("smeltObsidian", 6, 3, 2, ComponentListMFR.OBSIDIAN_INGOT, smeltSteel))
                .registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 40)
                .setDescriptValues(getMetalTier("obsidian"));
        smeltBlackSteel = (new InformationBase("smeltBlackSteel", 4, 7, 3, ComponentListMFR.BLACK_STEEL_INGOT, smeltSteel))
                .registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 50)
                .setDescriptValues(getMetalTier("blacksteel"));
        smeltDragonforge = (new InformationBase("smeltDragonforge", -4, -1, 1, ComponentListMFR.DRAGON_HEART, null))
                .registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 50);
        smeltRedSteel = (new InformationBase("smeltRedSteel", 3, 9, 5, ComponentListMFR.RED_STEEL_INGOT, smeltBlackSteel))
                .registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 65)
                .setDescriptValues(getMetalTier("redsteel"));
        smeltBlueSteel = (new InformationBase("smeltBlueSteel", 5, 9, 5, ComponentListMFR.BLUE_STEEL_INGOT, smeltBlackSteel))
                .registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 65)
                .setDescriptValues(getMetalTier("bluesteel"));
        smeltMithril = (new InformationBase("smeltMithril", 5, 12, 3, ComponentListMFR.MITHRIL_INGOT, null)).registerStat()
                .setPage(artisanry).addSkill(SkillList.artisanry, 75).setDescriptValues(getMetalTier("mithril"));
        smeltAdamant = (new InformationBase("smeltAdamantium", 3, 12, 3, ComponentListMFR.ADAMANTIUM_INGOT, null))
                .registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 75)
                .setDescriptValues(getMetalTier("adamantium"));

        smeltMaster = (new InformationBase("smeltMaster", 4, 13, 3, new ItemStack(ComponentListMFR.ARTEFACTS, 1, 3),
                (InformationBase) null)).registerStat().setPage(artisanry).setSpecial().addSkill(SkillList.artisanry,
                100);
        smeltIgnotumite = (new InformationBase("smeltIgnotumite", 2, 15, 3, ComponentListMFR.IGNOTUMITE_INGOT, smeltMaster))
                .registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 100)
                .setDescriptValues(getMetalTier("ignotumite"));
        smeltMithium = (new InformationBase("smeltMithium", 6, 15, 3, ComponentListMFR.MITHIUM_INGOT, smeltMaster))
                .registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 100)
                .setDescriptValues(getMetalTier("mithium"));
        smeltEnderforge = (new InformationBase("smeltEnder", 4, 16, 3, ComponentListMFR.ENDER_INGOT, smeltMaster))
                .registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 100)
                .setDescriptValues(getMetalTier("ender"));

        craftHCCTools = (new InformationBase("craftHCCTools", -1, -2, 0, ToolListMFR.STONE_PICK, (InformationBase) null))
                .registerStat().setPage(artisanry).setUnlocked();
        bellows = (new InformationBase("bellows", 0, -1, 0, BlockListMFR.BELLOWS, (InformationBase) null)).registerStat()
                .setPage(artisanry).setUnlocked();
        trough = (new InformationBase("trough", 0, -2, 0, BlockListMFR.TROUGH_WOOD, bellows)).registerStat()
                .setPage(artisanry).setUnlocked();
        forge = (new InformationBase("forge", 0, 0, 0, BlockListMFR.FORGE, (InformationBase) null)).registerStat()
                .setPage(artisanry).setUnlocked();
        anvil = (new InformationBase("anvil", -1, 0, 0, BlockListMFR.ANVIL_IRON, forge)).registerStat().setPage(artisanry)
                .setUnlocked().setSpecial();
        bar = (new InformationBase("bar", -1, 2, 0, ComponentListMFR.BAR, anvil)).registerStat().setPage(artisanry)
                .setUnlocked();
        apron = (new InformationBase("apron", -1, -1, 0, ArmourListMFR.LEATHER_APRON, anvil)).registerStat()
                .setPage(artisanry).setUnlocked();
        craftTools = (new InformationBase("craftTools", -3, 2, 0, CustomToolListMFR.STANDARD_PICK, bar)).registerStat()
                .setPage(artisanry).setUnlocked();
        craftAdvTools = (new InformationBase("craftAdvTools", -5, 2, 0, CustomToolListMFR.STANDARD_HVYPICK, craftTools))
                .registerStat().setPage(artisanry).setUnlocked();
        craftWeapons = (new InformationBase("craftWeapons", -3, 1, 5, CustomToolListMFR.STANDARD_SWORD, bar))
                .registerStat().setPage(artisanry).setUnlocked();
        craftAdvWeapons = (new InformationBase("craftAdvWeapons", -5, 1, 0, CustomToolListMFR.STANDARD_BATTLEAXE,
                craftWeapons)).registerStat().setPage(artisanry).setUnlocked();
        arrows = (new InformationBase("arrows", -3, 4, 0, CustomToolListMFR.STANDARD_ARROW, bar)).registerStat()
                .setPage(artisanry).setUnlocked();

        craftOrnate = (new InformationBase("craftOrnate", -3, -1, 1, ComponentListMFR.ORNATE_ITEMS, null)).registerStat()
                .setPage(artisanry).addSkill(SkillList.artisanry, 35);
        craftArmourLight = (new InformationBase("craftArmourLight", -3, 3, 1,
                ArmourListMFR.armour(ArmourListMFR.LEATHER, 3, 1), anvil)).registerStat().setPage(artisanry)
                .setUnlocked();
        craftArmourMedium = (new InformationBase("craftArmourMedium", -4, 3, 1, CustomArmourListMFR.STANDARD_CHAIN_CHEST,
                craftArmourLight)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 0);
        craftArmourHeavy = (new InformationBase("craftArmourHeavy", -5, 3, 3, CustomArmourListMFR.STANDARD_PLATE_CHEST,
                craftArmourMedium)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 10);
        arrowsBodkin = (new InformationBase("arrowsBodkin", -4, 5, 1, CustomToolListMFR.STANDARD_ARROW_BODKIN, arrows))
                .registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 10);
        arrowsBroad = (new InformationBase("arrowsBroad", -5, 5, 2, CustomToolListMFR.STANDARD_ARROW_BROAD, arrows))
                .registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 20);

        // ENGINEERING - Highly Expensive
        etools = (new InformationBase("etools", 3, 0, 0, CustomToolListMFR.STANDARD_SPANNER, (InformationBase) null))
                .registerStat().setPage(engineering).setUnlocked();
        ecomponents = (new InformationBase("ecomponents", 5, 0, 0, ComponentListMFR.BOLT, etools)).registerStat()
                .setPage(engineering).setUnlocked();
        tungsten = (new InformationBase("tungsten", 8, -1, 1, ComponentListMFR.TUNGSTEN_INGOT, ecomponents)).registerStat()
                .setPage(engineering).addSkill(SkillList.engineering, 20).addSkill(SkillList.artisanry, 20);
        coke = (new InformationBase("coke", 4, -1, 2, ComponentListMFR.COKE, ecomponents)).registerStat()
                .setPage(engineering);
        climber = (new InformationBase("climber", 7, 0, 1, ToolListMFR.CLIMBING_PICK_BASIC, ecomponents)).registerStat()
                .setPage(engineering).setUnlocked();
        spyglass = (new InformationBase("spyglass", 8, 1, 2, new ItemStack(ToolListMFR.SPYGLASS, 1, 2), climber))
                .registerStat().setPage(engineering).addSkill(SkillList.engineering, 5);
        parachute = (new InformationBase("parachute", 9, 2, 2, ToolListMFR.PARACHUTE, climber)).registerStat()
                .setPage(engineering).addSkill(SkillList.engineering, 10);
        syringe = (new InformationBase("syringe", 5, -2, 1, ToolListMFR.SYRINGE_EMPTY, ecomponents)).registerStat()
                .setPage(engineering).addSkill(SkillList.engineering, 25);
        engTanner = (new InformationBase("engTanner", 5, 2, 1, BlockListMFR.ENG_TANNER, ecomponents)).registerStat()
                .setPage(engineering).addSkill(SkillList.engineering, 40).addSkill(SkillList.artisanry, 25);
        advcrucible = (new InformationBase("advcrucible", 7, 3, 1, BlockListMFR.CRUCIBLE_AUTO, engTanner)).registerStat()
                .setPage(engineering).addSkill(SkillList.engineering, 50).addSkill(SkillList.artisanry, 35);
        advforge = (new InformationBase("advforge", 9, 3, 1, BlockListMFR.FORGE_METAL, advcrucible)).registerStat()
                .setPage(engineering).addSkill(SkillList.engineering, 75).addSkill(SkillList.artisanry, 50);

        blackpowder = (new InformationBase("blackpowder", 0, 0, 4, ComponentListMFR.BLACKPOWDER, (InformationBase) null))
                .registerStat().setPage(engineering).addSkill(SkillList.engineering, 0);
        advblackpowder = (new InformationBase("advblackpowder", 2, -2, 2, ComponentListMFR.BLACKPOWDER_ADVANCED,
                blackpowder)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 50);
        bombs = (new InformationBase("bombs", 0, 2, 3, ToolListMFR.BOMB_CUSTOM, blackpowder)).registerStat()
                .setPage(engineering).setSpecial().addSkill(SkillList.engineering, 10);
        bpress = (new InformationBase("bpress", -1, 1, 2, BlockListMFR.BOMB_PRESS, bombs)).registerStat()
                .setPage(engineering).addSkill(SkillList.engineering, 30);
        bombarrow = (new InformationBase("bombarrow", 1, 1, 2, ToolListMFR.EXPLODING_ARROW, bombs)).registerStat()
                .setPage(engineering).addSkill(SkillList.engineering, 40);
        bombFuse = (new InformationBase("bombFuse", 2, 2, 0, ComponentListMFR.BOMB_FUSE, bombs)).registerStat()
                .setUnlocked().setPage(engineering);
        shrapnel = (new InformationBase("shrapnel", 0, 4, 1, ComponentListMFR.SHRAPNEL, bombs)).registerStat()
                .setPage(engineering).addSkill(SkillList.engineering, 25);
        firebomb = (new InformationBase("firebomb", 0, 6, 2, Items.BLAZE_POWDER, shrapnel)).registerStat()
                .setPage(engineering).addSkill(SkillList.engineering, 85);
        stickybomb = (new InformationBase("stickybomb", -2, 2, 1, Items.SLIME_BALL, bombs)).registerStat()
                .setPage(engineering).addSkill(SkillList.engineering, 50);
        bombCeramic = (new InformationBase("bombCeramic", 2, 3, 0, ComponentListMFR.BOMB_CASING, bombs)).registerStat()
                .setUnlocked().setPage(engineering);
        bombIron = (new InformationBase("bombIron", 4, 5, 1, ComponentListMFR.BOMB_CASING_IRON, bombCeramic))
                .registerStat().setPage(engineering).addSkill(SkillList.engineering, 20);
        bombObsidian = (new InformationBase("bombObsidian", 4, 7, 2, ComponentListMFR.BOMB_CASING_OBSIDIAN, bombIron))
                .registerStat().setPage(engineering).addSkill(SkillList.engineering, 35);
        bombCrystal = (new InformationBase("bombCrystal", 2, 9, 1, ComponentListMFR.BOMB_CASING_CRYSTAL, bombObsidian))
                .registerStat().setPage(engineering).addSkill(SkillList.engineering, 40);
        mineCeramic = (new InformationBase("mineCeramic", -2, 3, 2, ComponentListMFR.MINE_CASING, bombs)).registerStat()
                .setPage(engineering).addSkill(SkillList.engineering, 15);
        mineIron = (new InformationBase("mineIron", -4, 5, 1, ComponentListMFR.MINE_CASING_IRON, mineCeramic))
                .registerStat().setPage(engineering).addSkill(SkillList.engineering, 20);
        mineObsidian = (new InformationBase("mineObsidian", -4, 7, 2, ComponentListMFR.MINE_CASING_OBSIDIAN, mineIron))
                .registerStat().setPage(engineering).addSkill(SkillList.engineering, 35);
        mineCrystal = (new InformationBase("mineCrystal", -2, 9, 1, ComponentListMFR.MINE_CASING_CRYSTAL, mineObsidian))
                .registerStat().setPage(engineering).addSkill(SkillList.engineering, 40);

        crossbows = (new InformationBase("crossbows", -4, 0, 3, BlockListMFR.CROSSBOW_BENCH, null)).registerStat()
                .setPage(engineering).setSpecial().addSkill(SkillList.engineering, 0);
        crossShafts = (new InformationBase("crossShafts", -6, 2, 0, ComponentListMFR.CROSSBOW_STOCK_WOOD, crossbows))
                .registerStat().setPage(engineering).setUnlocked();
        crossShaftAdvanced = (new InformationBase("crossShaftAdvanced", -6, 4, 2, ComponentListMFR.CROSSBOW_STOCK_IRON,
                crossShafts)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 40);
        crossHeads = (new InformationBase("crossHeads", -2, -2, 0, ComponentListMFR.CROSS_ARMS_BASIC, crossbows))
                .registerStat().setPage(engineering).setUnlocked();
        crossHeadAdvanced = (new InformationBase("crossHeadAdvanced", -3, -3, 2, ComponentListMFR.CROSS_ARMS_ADVANCED,
                crossHeads)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 30);
        crossBayonet = (new InformationBase("crossBayonet", -1, -3, 1, ComponentListMFR.CROSS_BAYONET, crossHeads))
                .registerStat().setPage(engineering).addSkill(SkillList.engineering, 10);
        crossAmmo = (new InformationBase("crossAmmo", -5, 3, 1, ComponentListMFR.CROSS_AMMO, crossShafts)).registerStat()
                .setPage(engineering).addSkill(SkillList.engineering, 30);
        crossScope = (new InformationBase("crossScope", -7, 3, 1, ComponentListMFR.CROSS_SCOPE, crossShafts))
                .registerStat().setPage(engineering).addSkill(SkillList.engineering, 20);

        cogArmour = (new InformationBase("cogArmour", 8, -3, 2, BlockListMFR.COGWORK_HELM, tungsten)).registerStat()
                .setPage(engineering).addSkill(SkillList.engineering, 60);
        compPlate = (new InformationBase("compPlate", 10, -3, 1, ComponentListMFR.INGOT_COMPOSITE_ALLOY, tungsten))
                .registerStat().setPage(engineering).addSkill(SkillList.engineering, 50)
                .addSkill(SkillList.artisanry, 40);

        repair_basic = (new InformationBase("repair_basic", 8, 0, 2, BlockListMFR.REPAIR_BASIC, (InformationBase) null))
                .registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 10);
        repair_advanced = (new InformationBase("repair_advanced", 10, 0, 3, BlockListMFR.REPAIR_ADVANCED, repair_basic))
                .registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 25);
        repair_ornate = (new InformationBase("repair_ornate", 12, 2, 3, BlockListMFR.REPAIR_ORNATE, repair_advanced))
                .registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 50);

        constructionPts = (new InformationBase("constructionPts", 0, 0, 0, ComponentListMFR.PLANK_CUT,
                (InformationBase) null)).registerStat().setPage(construction).setUnlocked();
        reinforced_stone = (new InformationBase("reinforced_stone", 1, 0, 0, BlockListMFR.REINFORCED_STONE,
                (InformationBase) null)).registerStat().setPage(construction).setUnlocked();
        glass = (new InformationBase("glass", 0, 1, 0, BlockListMFR.FRAMED_GLASS, (InformationBase) null)).registerStat()
                .setPage(construction).setUnlocked();
        brickworks = (new InformationBase("brickworks", 3, 0, 0, BlockListMFR.COBBLE_BRICK, reinforced_stone))
                .registerStat().setPage(construction).setUnlocked();
        bars = (new InformationBase("bars", 0, 3, 0, BlockListMFR.STEEL_BARS, glass)).registerStat().setPage(construction)
                .setUnlocked();
        thatch = (new InformationBase("thatch", 0, -3, 0, BlockListMFR.THATCH, clay_wall)).registerStat()
                .setPage(construction).setUnlocked();
        refined_planks = (new InformationBase("refined_planks", -1, 0, 1, BlockListMFR.REFINED_PLANKS,
                (InformationBase) null)).registerStat().setPage(construction).addSkill(SkillList.construction, 0);
        clay_wall = (new InformationBase("clay_wall", 0, -1, 2, BlockListMFR.CLAY_WALL, (InformationBase) null))
                .registerStat().setPage(construction).addSkill(SkillList.construction, 5);
//        paint_brush = (new InformationBase("paint_brush", -3, 0, 1, BlockListMFR.paint_brush, refined_planks))
//                .registerStat().setPage(construction).addSkill(SkillList.construction, 10);
        decorated_stone = (new InformationBase("decorated_stone", 5, 0, 2, BlockListMFR.REINFORCED_STONE_FRAMED,
                brickworks)).registerStat().setPage(construction).addSkill(SkillList.construction, 15);

//        bed_roll = (new InformationBase("bed_roll", 2, 3, 1, BlockListMFR.bedroll, (InformationBase) null)).registerStat()
//                .setPage(construction).setUnlocked();
        tool_rack = (new InformationBase("tool_rack", 2, 2, 0, BlockListMFR.TOOL_RACK_WOOD, (InformationBase) null))
                .registerStat().setPage(construction).setUnlocked();
        food_box = (new InformationBase("food_box", 2, 4, 1, BlockListMFR.FOOD_BOX_BASIC, tool_rack)).registerStat()
                .setPage(construction).setUnlocked();
        ammo_box = (new InformationBase("ammo_box", 4, 4, 1, BlockListMFR.AMMO_BOX_BASIC, food_box)).registerStat()
                .setPage(construction).addSkill(SkillList.construction, 15);
        big_box = (new InformationBase("big_box", 6, 4, 1, BlockListMFR.CRATE_BASIC, ammo_box)).registerStat()
                .setPage(construction).addSkill(SkillList.construction, 25);

        // COOKING -The Cheapest
        cookingutensil = (new InformationBase("cookingutensil", -1, 0, 0, FoodListMFR.PIE_TRAY, (InformationBase) null))
                .registerStat().setPage(provisioning).setUnlocked();
        firepit = (new InformationBase("firepit", 0, 0, 0, BlockListMFR.FIREPIT, (InformationBase) null)).registerStat()
                .setPage(provisioning).setUnlocked();

        generic_meat = (new InformationBase("generic_meat", 0, -1, 0, FoodListMFR.GENERIC_MEAT_UNCOOKED,
                (InformationBase) null)).registerStat().setPage(provisioning).setUnlocked();
        stew = (new InformationBase("stew", 0, -3, 0, FoodListMFR.STEW, generic_meat)).registerStat()
                .setPage(provisioning).setUnlocked();
        jerky = (new InformationBase("jerky", 0, -5, 1, FoodListMFR.JERKY, stew)).registerStat().setPage(provisioning)
                .addSkill(SkillList.provisioning, 10);
        saussage = (new InformationBase("saussage", 2, -5, 2, FoodListMFR.SAUSAGE_COOKED, jerky)).registerStat()
                .setPage(provisioning).addSkill(SkillList.provisioning, 35);
        sandwitch = (new InformationBase("sandwitch", 1, -7, 3, FoodListMFR.SANDWITCH_MEAT, jerky)).registerStat()
                .setPage(provisioning).addSkill(SkillList.provisioning, 15);
        sandwitchBig = (new InformationBase("sandwitchBig", 3, -7, 3, FoodListMFR.SANDWITCH_BIG, sandwitch))
                .registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 25);
        meatpie = (new InformationBase("meatpie", -1, -7, 2, BlockListMFR.PIE_MEAT, jerky)).registerStat()
                .setPage(provisioning).addSkill(SkillList.provisioning, 35);
        shepardpie = (new InformationBase("shepardpie", -2, -9, 3, BlockListMFR.PIE_SHEPARDS, meatpie)).registerStat()
                .setPage(provisioning).addSkill(SkillList.provisioning, 80);
        bread = (new InformationBase("bread", 1, 0, 0, FoodListMFR.BREADROLL, (InformationBase) null)).registerStat()
                .setPage(provisioning).setUnlocked();
        oats = (new InformationBase("oats", 3, 0, 0, FoodListMFR.OATS, bread)).registerStat().setPage(provisioning)
                .setUnlocked();

        salt = (new InformationBase("salt", -2, -2, 0, FoodListMFR.SALT, (InformationBase) null)).registerStat()
                .setPage(provisioning).setUnlocked();
        jug = (new InformationBase("jug", -1, -2, 0, FoodListMFR.JUG_WATER, (InformationBase) null)).registerStat()
                .setPage(provisioning).setUnlocked();
        berry = (new InformationBase("berry", 0, 1, 0, FoodListMFR.BERRIES, (InformationBase) null)).registerStat()
                .setPage(provisioning).setUnlocked();
        icing = (new InformationBase("icing", -1, 2, 0, FoodListMFR.ICING, berry)).registerStat().setPage(provisioning)
                .setUnlocked();
        sweetroll = (new InformationBase("sweetroll", 0, 3, 2, FoodListMFR.SWEETROLL, berry)).registerStat()
                .setPage(provisioning).addSkill(SkillList.provisioning, 15);
        eclair = (new InformationBase("eclair", 2, 3, 3, FoodListMFR.ECLAIR, sweetroll)).registerStat()
                .setPage(provisioning).addSkill(SkillList.provisioning, 75);
        cake = (new InformationBase("cake", 0, 5, 2, BlockListMFR.CAKE_VANILLA, sweetroll)).registerStat()
                .setPage(provisioning).addSkill(SkillList.provisioning, 25);
        carrotcake = (new InformationBase("carrotcake", -1, 7, 3, BlockListMFR.CAKE_CARROT, cake)).registerStat()
                .setPage(provisioning).addSkill(SkillList.provisioning, 40);
        chococake = (new InformationBase("chococake", 1, 7, 3, BlockListMFR.CAKE_CHOCOLATE, cake)).registerStat()
                .setPage(provisioning).addSkill(SkillList.provisioning, 40);
        bfcake = (new InformationBase("bfcake", 1, 9, 4, BlockListMFR.CAKE_BF, chococake)).registerStat()
                .setPage(provisioning).addSkill(SkillList.provisioning, 90);
        berrypie = (new InformationBase("berrypie", 2, 1, 2, BlockListMFR.PIE_BERRY, berry)).registerStat()
                .setPage(provisioning).addSkill(SkillList.provisioning, 20);
        applepie = (new InformationBase("applepie", 4, 1, 2, BlockListMFR.PIE_APPLE, berrypie)).registerStat()
                .setPage(provisioning).addSkill(SkillList.provisioning, 30);

        cheese = (new InformationBase("cheese", 1, -1, 0, BlockListMFR.CHEESE_WHEEL, (InformationBase) null))
                .registerStat().setPage(provisioning).setUnlocked();
        cheeseroll = (new InformationBase("cheeseroll", 3, -1, 2, FoodListMFR.CHEESE_ROLL, cheese)).registerStat()
                .setPage(provisioning).addSkill(SkillList.provisioning, 15);

        bandage = (new InformationBase("bandage", -3, 0, 0, ToolListMFR.BANDAGE_WOOL, (InformationBase) null))
                .registerStat().setPage(provisioning).setUnlocked();
        bandageadv = (new InformationBase("bandageadv", -5, -1, 2, ToolListMFR.BANDAGE_TOUGH, bandage)).registerStat()
                .setPage(provisioning).addSkill(SkillList.provisioning, 40);

        // MASTERY
        toughness = (new InformationBase("toughness", -1, 0, 0, CustomArmourListMFR.STANDARD_PLATE_HELMET,
                (InformationBase) null)).registerStat().setPage(mastery).addSkill(SkillList.combat, 10).setPerk();
        fitness = (new InformationBase("fitness", 1, 0, 0, ComponentListMFR.DRAGON_HEART, (InformationBase) null))
                .registerStat().setPage(mastery).addSkill(SkillList.combat, 20).setPerk();
        armourpro = (new InformationBase("armourpro", 2, -1, 0, Items.DIAMOND_CHESTPLATE, fitness)).registerStat()
                .setPage(mastery).addSkill(SkillList.combat, 60).setPerk();
        parrypro = (new InformationBase("parrypro", -2, -1, 0, Items.IRON_SWORD, toughness)).registerStat()
                .setPage(mastery).addSkill(SkillList.combat, 20).setPerk();
        counteratt = (new InformationBase("counteratt", -1, -2, 0, CustomToolListMFR.STANDARD_WARAXE, parrypro))
                .registerStat().setPage(mastery).addSkill(SkillList.combat, 25).setPerk();
        autoparry = (new InformationBase("autoparry", -3, -2, 0, CustomToolListMFR.STANDARD_SWORD, parrypro))
                .registerStat().setPage(mastery).addSkill(SkillList.combat, 50).setPerk();
        firstaid = (new InformationBase("firstaid", 0, 1, 0, ToolListMFR.BANDAGE_WOOL, (InformationBase) null))
                .registerStat().setPage(mastery).addSkill(SkillList.provisioning, 25).setPerk();
        doctor = (new InformationBase("doctor", 0, 3, 0, ToolListMFR.SYRINGE, firstaid)).registerStat().setPage(mastery)
                .addSkill(SkillList.provisioning, 50).setPerk();
        scrapper = (new InformationBase("scrapper", 0, -1, 0, BlockListMFR.SALVAGE_BASIC, (InformationBase) null))
                .registerStat().setPage(mastery).addSkill(SkillList.artisanry, 35).setPerk();

    }

    private static Object getMetalTier(String string) {
        CustomMaterial mat = MetalMaterial.getMaterial(string);
        if (mat != null)
            return mat.crafterTier;

        return "?";
    }
}
