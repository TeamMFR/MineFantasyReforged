package minefantasy.mf2.knowledge;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.crafting.carpenter.ICarpenterRecipe;
import minefantasy.mf2.api.knowledge.InformationBase;
import minefantasy.mf2.api.knowledge.InformationList;
import minefantasy.mf2.api.knowledge.InformationPage;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.refine.Alloy;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.item.ItemComponentMF;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.ArmourListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CustomArmourListMF;
import minefantasy.mf2.item.list.CustomToolListMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.material.MetalMaterial;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public class KnowledgeListMF
{
	public static InformationPage artisanry = InformationList.artisanry;
	public static InformationPage construction = InformationList.construction;
	public static InformationPage engineering = InformationList.engineering;
	public static InformationPage provisioning = InformationList.provisioning;
	public static InformationPage mastery = InformationList.mastery;
	
	public static InformationPage dwarvern = InformationList.dwarvern;
	public static InformationPage gnomish = InformationList.gnomish;
	
	//BASICS -FREE
	public static InformationBase carpenter, gettingStarted, salvage, research, talisman, ores, plants, chimney, tanning, commodities, dust, craftCrafters, stamina, combat, craftArmourBasic, craftHCCTools, firemaker;
	public static InformationBase bloomery, crucible, crucible2, smeltCopper, smeltBronze, smeltIron, coalflux, blastfurn, smeltPig, smeltSteel, encrusted, obsidian, smeltBlackSteel, smeltDragonforge, smeltBlueSteel, smeltRedSteel, smeltMithril, smeltAdamant, smeltMaster, smeltMithium, smeltIgnotumite, smeltEnderforge;
    public static InformationBase bellows, trough, forge, anvil, apron, craftTools, craftAdvTools, craftWeapons, craftAdvWeapons, arrows, /*craftOrnateWeapons, craftAdvOrnateWeapons,*/ craftArmourLight, craftArmourMedium, craftArmourHeavy, arrowsBodkin, arrowsBroad, repair_basic, repair_advanced, repair_ornate;
	public static InformationBase coke, etools, ecomponents, tungsten, climber, spyglass, parachute, syringe, engTanner, advforge, advcrucible, blackpowder, advblackpowder, bombs, bpress, bombarrow, bombFuse, shrapnel, firebomb, stickybomb, bombCeramic, bombIron, bombObsidian, bombCrystal, mineCeramic, mineIron, mineObsidian, mineCrystal, crossbows, crossShafts, crossHeads, crossHeadAdvanced, crossShaftAdvanced, crossAmmo, crossScope, crossBayonet, cogArmour, compPlate;
    public static InformationBase refined_planks, reinforced_stone, clay_wall, glass, brickworks, decorated_stone, bars, thatch, easyRefine, paint_brush;
    public static InformationBase toughness, fitness, armourpro, parrypro, counteratt, autoparry, scrapper, firstaid, doctor;
    public static InformationBase cookingutensil, generic_meat, stew, jerky, saussage, sandwitch, sandwitchBig, meatpie, shepardpie, bread, oats, salt, jug, berry, icing, sweetroll, eclair, cake, carrotcake, chococake, bfcake, applepie, berrypie, cheese, cheeseroll, bandage, bandageadv;
    
    public static InformationBase dwarvernKnowledge;
    public static InformationBase gnomishKnowledge;
    
    public static void init()
	{
    	carpenter = (new InformationBase("carpenter", 		        0, -3,  0, BlockListMF.carpenter, (InformationBase)null)).registerStat().setUnlocked();
    	salvage = (new InformationBase("salvage", 		        	0, -4,  0, BlockListMF.salvage_basic, (InformationBase)null)).registerStat().setUnlocked();
    	gettingStarted = (new InformationBase("gettingStarted", 		0, 0,  0, Items.book, (InformationBase)null)).registerStat().setUnlocked();
    	research = (new InformationBase("research", 				    1, 1, 0, ToolListMF.researchBook, (InformationBase)null)).registerStat().setUnlocked();
    	talisman = (new InformationBase("talisman", 				    4, 2,  0, ComponentListMF.talisman_lesser, research)).registerStat().setUnlocked();
    	ores = (new InformationBase("ores",  						    1, -2, 0, BlockListMF.oreCopper, (InformationBase)null)).registerStat().setUnlocked();
    	plants = (new InformationBase("plants",  					    1, -3, 0, BlockListMF.log_ironbark, ores)).registerStat().setUnlocked();
    	chimney = (new InformationBase("chimney",  				    0, 2, 0, BlockListMF.chimney_stone, (InformationBase)null)).registerStat().setUnlocked();
    	tanning = (new InformationBase("tanning", 					0, -2 ,0, Items.leather, (InformationBase)null)).registerStat().setUnlocked().setSpecial();
    	commodities = (new InformationBase("commodities",				-1 ,-2 ,0, ComponentListMF.nail, (InformationBase)null)).registerStat().setUnlocked();
    	dust = (new InformationBase("dust",								-1 ,-3 ,0, ComponentListMF.clay_pot, commodities)).registerStat().setUnlocked();
    	craftCrafters = (new InformationBase("craftCrafters", 		-1, 1, 0, CustomToolListMF.standard_hammer, (InformationBase)null)).registerStat().setUnlocked();
    	stamina = (new InformationBase("stamina", 					-3, 1, 0, Items.feather, craftCrafters)).registerStat().setUnlocked();
    	combat = (new InformationBase("combat", 						-5, 2, 0, Items.iron_sword, stamina)).registerStat().setUnlocked();
    	craftArmourBasic = (new InformationBase("craftArmourBasic",   -5, 0, 5, ArmourListMF.armour(ArmourListMF.leather, 0, 1), combat)).registerStat().setUnlocked();
    	firemaker = (new InformationBase("firemaker", 		       	   5,  1,  0, Items.flint_and_steel, (InformationBase)null)).registerStat().setUnlocked();
    	
    	//ARTISANRY -From Not very to the most Expensive
    	bloomery = (new InformationBase("bloomery", 				4, -2, 5, BlockListMF.bloomery, crucible)).registerStat().setPage(artisanry).setUnlocked().setSpecial();
    	crucible = (new InformationBase("crucible", 				4, 0,  5, BlockListMF.crucible, (InformationBase)null)).registerStat().setPage(artisanry).setUnlocked().setSpecial();
    	crucible2 = (new InformationBase("crucible2",  				6, 0, 10, BlockListMF.crucibleadv_active, crucible)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 40);
    	
    	smeltCopper = (new InformationBase("smeltCopper",  			1, 0, 0, ComponentListMF.ingots[0], (InformationBase)null)).registerStat().setPage(artisanry).setUnlocked().setDescriptValues(getMetalTier("copper"));
    	smeltBronze = (new InformationBase("smeltBronze",  			1, 2,  5, ComponentListMF.ingots[2], crucible)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 5).setDescriptValues(getMetalTier("bronze"));
        smeltIron = (new InformationBase("smeltIron",  			    1, 4,  5, Items.iron_ingot, smeltBronze)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 10).setDescriptValues(getMetalTier("iron"));
        coalflux = (new InformationBase("coalflux",  			    1, 6,  5, ComponentListMF.coal_flux, smeltIron)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 15);
        blastfurn = (new InformationBase("blastfurn",  				2, 5, 15, BlockListMF.blast_heater_active, smeltIron)).registerStat().setPage(artisanry).setSpecial().addSkill(SkillList.artisanry, 25);
        smeltPig = (new InformationBase("smeltPig",  					3, 3, 0, ComponentListMF.ingots[3], blastfurn)).registerStat().setPage(artisanry).setUnlocked().addSkill(SkillList.artisanry, 25);
        smeltSteel = (new InformationBase("smeltSteel",  				4, 5, 0, ComponentListMF.ingots[4], smeltPig)).registerStat().setPage(artisanry).setUnlocked().addSkill(SkillList.artisanry, 25).setDescriptValues(getMetalTier("Steel"));
        encrusted = (new InformationBase("smeltEncrusted",  			6, 5, 10, ComponentListMF.diamond_shards, smeltSteel)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 35).setDescriptValues(getMetalTier("encrusted"));
        obsidian = (new InformationBase("smeltObsidian",  				6, 3, 10, ComponentListMF.ingots[19], smeltSteel)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 40).setDescriptValues(getMetalTier("obsidian"));
        smeltBlackSteel = (new InformationBase("smeltBlackSteel",		4, 7, 10, ComponentListMF.ingots[7], smeltSteel)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 50).setDescriptValues(getMetalTier("blacksteel"));
        smeltDragonforge = (new InformationBase("smeltDragonforge",	6, 7, 15, ComponentListMF.dragon_heart, smeltBlackSteel)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 60);
        smeltRedSteel = (new InformationBase("smeltRedSteel", 		3, 9, 10, ComponentListMF.ingots[10], smeltBlackSteel)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 65).setDescriptValues(getMetalTier("redsteel"));
        smeltBlueSteel = (new InformationBase("smeltBlueSteel", 		5, 9, 10, ComponentListMF.ingots[12], smeltBlackSteel)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 65).setDescriptValues(getMetalTier("bluesteel"));
        smeltMithril = (new InformationBase("smeltMithril", 			5, 11, 30, ComponentListMF.ingots[14], smeltBlueSteel)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 75).setDescriptValues(getMetalTier("mithril"));
        smeltAdamant = (new InformationBase("smeltAdamantium", 		3, 11, 30, ComponentListMF.ingots[13], smeltRedSteel)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 75).setDescriptValues(getMetalTier("adamantium"));
        
        smeltMaster = (new InformationBase("smeltMaster", 			4, 13, 50,  BlockListMF.oreMythic, (InformationBase)null)).registerStat().setPage(artisanry).setSpecial().addSkill(SkillList.artisanry, 100);
        smeltIgnotumite = (new InformationBase("smeltIgnotumite", 	2, 15, 100, ComponentListMF.ingots[15], smeltMaster)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 100).setDescriptValues(getMetalTier("ignotumite"));
        smeltMithium = (new InformationBase("smeltMithium", 			6, 15, 100, ComponentListMF.ingots[16], smeltMaster)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 100).setDescriptValues(getMetalTier("mithium"));
        smeltEnderforge = (new InformationBase("smeltEnder", 	4, 16, 100, ComponentListMF.ingots[17], smeltMaster)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 100).setDescriptValues(getMetalTier("ender"));
        
        craftHCCTools = (new InformationBase("craftHCCTools", 		-1, -2, 0, ToolListMF.pickStone, (InformationBase)null)).registerStat().setPage(artisanry).setUnlocked();
        bellows = (new InformationBase("bellows", 					 0, -1,0, BlockListMF.bellows, (InformationBase)null)).registerStat().setPage(artisanry).setUnlocked();
        trough = (new InformationBase("trough", 					 	 0, -2,0, BlockListMF.trough_wood, bellows)).registerStat().setPage(artisanry).setUnlocked();
        forge = (new InformationBase("forge", 						 0, 0, 0, BlockListMF.forge, (InformationBase)null)).registerStat().setPage(artisanry).setUnlocked();
        anvil = (new InformationBase("anvil", 						-1, 0, 0, BlockListMF.anvil[1], forge)).registerStat().setPage(artisanry).setUnlocked().setSpecial();
        apron = (new InformationBase("apron", 						-1, -1, 0, ArmourListMF.leatherapron, anvil)).registerStat().setPage(artisanry).setUnlocked();
        craftTools = (new InformationBase("craftTools", 				-3, 2, 0, CustomToolListMF.standard_pick, anvil)).registerStat().setPage(artisanry).setUnlocked();
        craftAdvTools = (new InformationBase("craftAdvTools", 		-5, 2, 0, CustomToolListMF.standard_hvypick, craftTools)).registerStat().setPage(artisanry).setUnlocked();
        craftWeapons = (new InformationBase("craftWeapons", 			-3, 1, 5, CustomToolListMF.standard_sword, anvil)).registerStat().setPage(artisanry).setUnlocked();
        craftAdvWeapons = (new InformationBase("craftAdvWeapons",     -5, 1, 0, CustomToolListMF.standard_battleaxe, craftWeapons)).registerStat().setPage(artisanry).setUnlocked();
        arrows = (new InformationBase("arrows", 		  		   		 -3, 4, 0, CustomToolListMF.standard_arrow, anvil)).registerStat().setPage(artisanry).setUnlocked();
        
        //craftOrnateWeapons = (new InformationBase("craftOrnateWeapons",  			-3, -1, 10, CustomToolListMF.dragonforged_sword, craftWeapons)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 25);
        //craftAdvOrnateWeapons = (new InformationBase("craftAdvOrnateWeapons", 	-5, -1, 15, CustomToolListMF.dragonforged_battleaxe, craftOrnateWeapons)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 30);
        craftArmourLight = (new InformationBase("craftArmourLight", 			-3, 3, 5, ArmourListMF.armour(ArmourListMF.leather, 3, 1), anvil)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 0);
        craftArmourMedium = (new InformationBase("craftArmourMedium", 		-4, 3, 10, CustomArmourListMF.standard_chain_chest, craftArmourLight)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 0);
        craftArmourHeavy = (new InformationBase("craftArmourHeavy",       	-5, 3, 10, CustomArmourListMF.standard_plate_chest, craftArmourMedium)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 10);
        arrowsBodkin = (new InformationBase("arrowsBodkin", 		  	 -4, 5, 10, CustomToolListMF.standard_arrow_bodkin, arrows)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 10);
        arrowsBroad = (new InformationBase("arrowsBroad", 		  	 -5, 5, 10, CustomToolListMF.standard_arrow_broad, arrows)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 20);
        
        //ENGINEERING - Highly Expensive
        etools = (new InformationBase("etools", 					    3, 0, 0, ToolListMF.spanner, (InformationBase)null)).registerStat().setPage(engineering).setUnlocked();
        ecomponents = (new InformationBase("ecomponents",             5, 0, 0, ComponentListMF.bolt, etools)).registerStat().setPage(engineering).setUnlocked();
        tungsten = (new InformationBase("tungsten",             	  8, -1, 10, ComponentListMF.ingots[18], ecomponents)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 20).addSkill(SkillList.artisanry, 35);
        coke = (new InformationBase("coke",             	 		  4, -1, 5, ComponentListMF.coke, ecomponents)).registerStat().setPage(engineering);
        climber = (new InformationBase("climber",             		7, 0, 10, ToolListMF.climbing_pick_basic, ecomponents)).registerStat().setPage(engineering);
        spyglass = (new InformationBase("spyglass",             		8, 1, 10, new ItemStack(ToolListMF.spyglass, 1, 2), climber)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 5);
        parachute = (new InformationBase("parachute",             	9, 2, 20, ToolListMF.parachute, climber)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 10);
        syringe = (new InformationBase("syringe",             	    5, -2, 20, ToolListMF.syringe_empty, ecomponents)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 25);
        engTanner = (new InformationBase("engTanner",             	5,  2, 30, BlockListMF.engTanner, ecomponents)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 40).addSkill(SkillList.artisanry, 25);
        advcrucible = (new InformationBase("advcrucible",             	7,  3, 30, BlockListMF.crucibleauto, engTanner)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 50).addSkill(SkillList.artisanry, 35);
        advforge = (new InformationBase("advforge",             		9,  3, 30, BlockListMF.forge_metal, advcrucible)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 75).addSkill(SkillList.artisanry, 50);
        
        blackpowder = (new InformationBase("blackpowder", 			0, 0, 10, ComponentListMF.blackpowder, (InformationBase)null)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 0);
        advblackpowder = (new InformationBase("advblackpowder", 		2, -2, 30, ComponentListMF.blackpowder_advanced, blackpowder)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 50);
        bombs = (new InformationBase("bombs", 						0, 2, 10, ToolListMF.bomb_custom, blackpowder)).registerStat().setPage(engineering).setSpecial().addSkill(SkillList.engineering, 10);
        bpress = (new InformationBase("bpress", 					   -1, 1, 40, BlockListMF.bombPress, bombs)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 30);
        bombarrow = (new InformationBase("bombarrow", 			    1, 1, 40, ToolListMF.exploding_arrow, bombs)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 40);
        bombFuse = (new InformationBase("bombFuse", 		 		    2, 2, 0, ComponentListMF.bomb_fuse, bombs)).registerStat().setUnlocked().setPage(engineering);
        shrapnel = (new InformationBase("shrapnel", 				    0, 4, 20, ComponentListMF.shrapnel, bombs)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 25);
        firebomb = (new InformationBase("firebomb", 		  		    0, 6, 30, Items.blaze_powder, shrapnel)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 85);
        stickybomb = (new InformationBase("stickybomb", 			   -2, 2, 30, Items.slime_ball, bombs)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 50);
        bombCeramic = (new InformationBase("bombCeramic", 		    2, 3, 0, ComponentListMF.bomb_casing, bombs)).registerStat().setUnlocked().setPage(engineering);
        bombIron = (new InformationBase("bombIron", 				    4, 5, 10, ComponentListMF.bomb_casing_iron, bombCeramic)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 20);
        bombObsidian = (new InformationBase("bombObsidian", 		    4, 7, 15, ComponentListMF.bomb_casing_obsidian, bombIron)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 35);
        bombCrystal = (new InformationBase("bombCrystal", 		    2, 9, 20, ComponentListMF.bomb_casing_crystal, bombObsidian)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 80);
        mineCeramic = (new InformationBase("mineCeramic", 		   -2, 3, 5,  ComponentListMF.mine_casing, bombs)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 15);
        mineIron = (new InformationBase("mineIron", 				   -4, 5, 10, ComponentListMF.mine_casing_iron, mineCeramic)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 20);
        mineObsidian = (new InformationBase("mineObsidian", 		   -4, 7, 15, ComponentListMF.mine_casing_obsidian, mineIron)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 35);
        mineCrystal = (new InformationBase("mineCrystal", 		   -2, 9, 20, ComponentListMF.mine_casing_crystal, mineObsidian)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 80);
        
        crossbows = (new InformationBase("crossbows", 					-4,  0, 10, BlockListMF.crossbowBench, null)).registerStat().setPage(engineering).setSpecial().addSkill(SkillList.engineering, 0);
        crossShafts = (new InformationBase("crossShafts", 				-6,  2, 10, ComponentListMF.crossbow_stock_wood, crossbows)).registerStat().setPage(engineering).setUnlocked();
        crossShaftAdvanced = (new InformationBase("crossShaftAdvanced", -6,  4, 20, ComponentListMF.crossbow_stock_iron, crossShafts)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 40);
        crossHeads = (new InformationBase("crossHeads", 				-2, -2, 10, ComponentListMF.cross_arms_basic, crossbows)).registerStat().setPage(engineering).setUnlocked();
        crossHeadAdvanced = (new InformationBase("crossHeadAdvanced", 	-3, -3, 20, ComponentListMF.cross_arms_advanced, crossHeads)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 30);
        crossBayonet = (new InformationBase("crossBayonet", 			-1, -3, 10, ComponentListMF.cross_bayonet, crossHeads)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 10);
        crossAmmo = (new InformationBase("crossAmmo", 					-5,  3, 20, ComponentListMF.cross_ammo, crossShafts)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 30);
        crossScope = (new InformationBase("crossScope", 				-7,  3, 25, ComponentListMF.cross_scope, crossShafts)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 20);
        
        cogArmour = (new InformationBase("cogArmour",             	 	10, -1, 30, ArmourListMF.cogwork_frame_chest, tungsten)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 85);
        compPlate = (new InformationBase("compPlate",             	 	10, -3, 30, ComponentListMF.ingotCompositeAlloy, cogArmour)).registerStat().setPage(engineering).addSkill(SkillList.engineering, 90).addSkill(SkillList.artisanry, 50);
        
        repair_basic = (new InformationBase("repair_basic",  		   8, 0, 5, BlockListMF.repair_basic, (InformationBase)null)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 10);
        repair_advanced = (new InformationBase("repair_advanced",    10, 0, 15, BlockListMF.repair_advanced, repair_basic)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 25);
        repair_ornate = (new InformationBase("repair_ornate",        12, 2, 20, BlockListMF.repair_ornate, repair_advanced)).registerStat().setPage(artisanry).addSkill(SkillList.artisanry, 50);
        
        reinforced_stone = (new InformationBase("reinforced_stone",     1, 0, 0, BlockListMF.reinforced_stone, (InformationBase)null)).registerStat().setPage(construction).setUnlocked();
        glass = (new InformationBase("glass",             0,  1, 0, BlockListMF.framed_glass, (InformationBase)null)).registerStat().setPage(construction).setUnlocked();
        brickworks = (new InformationBase("brickworks",   3,  0, 0, BlockListMF.cobble_brick, reinforced_stone)).registerStat().setPage(construction).setUnlocked();
        bars = (new InformationBase("bars",               0,  3, 0, BlockListMF.bars[2], glass)).registerStat().setPage(construction).setUnlocked();
        thatch = (new InformationBase("thatch",           0,  -3, 0, BlockListMF.thatch, clay_wall)).registerStat().setPage(construction).setUnlocked();

        refined_planks = (new InformationBase("refined_planks",      -1, 0, 5, BlockListMF.refined_planks, (InformationBase)null)).registerStat().setPage(construction).addSkill(SkillList.construction, 0);
        clay_wall = (new InformationBase("clay_wall",     0, -1, 5, BlockListMF.clayWall, (InformationBase)null)).registerStat().setPage(construction).addSkill(SkillList.construction, 5);
        paint_brush = (new InformationBase("paint_brush",  2, 2, 10, ToolListMF.paint_brush, (InformationBase)null)).registerStat().setPage(construction).addSkill(SkillList.construction, 10);
        decorated_stone = (new InformationBase("decorated_stone",   5,  0, 15, BlockListMF.reinforced_stone_framed, brickworks)).registerStat().setPage(construction).addSkill(SkillList.construction, 15);
        
        //COOKING -The Cheapest
        cookingutensil = (new InformationBase("cookingutensil",-1, 0, 0, FoodListMF.pie_tray, (InformationBase)null)).registerStat().setPage(provisioning).setUnlocked();
        
        generic_meat = (new InformationBase("generic_meat", 0,  -1, 0, FoodListMF.generic_meat_uncooked, (InformationBase)null)).registerStat().setPage(provisioning).setUnlocked();
        stew = (new InformationBase("stew",               0,  -3, 0, FoodListMF.stew, generic_meat)).registerStat().setPage(provisioning).setUnlocked();
        jerky = (new InformationBase("jerky",             0,  -5, 5, FoodListMF.jerky, stew)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 10);
        saussage = (new InformationBase("saussage",             2,  -5, 8, FoodListMF.saussage_cooked, jerky)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 35);
        sandwitch = (new InformationBase("sandwitch",     1,  -7, 15, FoodListMF.sandwitch_meat, jerky)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 15);
        sandwitchBig = (new InformationBase("sandwitchBig",     3,  -7, 20, FoodListMF.sandwitch_big, sandwitch)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 25);
        meatpie = (new InformationBase("meatpie",   		-1,  -7, 20, BlockListMF.pie_meat, jerky)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 35);
        shepardpie = (new InformationBase("shepardpie",  	-2,  -9, 25, BlockListMF.pie_shepards, meatpie)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 80);
        bread = (new InformationBase("bread",             1,   0, 0, FoodListMF.breadroll, (InformationBase)null)).registerStat().setPage(provisioning).setUnlocked();
        oats = (new InformationBase("oats",               3,   0, 0, FoodListMF.oats, bread)).registerStat().setPage(provisioning).setUnlocked();
        
        salt = (new InformationBase("salt",            	-2,  -2, 0, FoodListMF.salt, (InformationBase)null)).registerStat().setPage(provisioning).setUnlocked();
        jug = (new InformationBase("jug",            	-1,  -2, 0, FoodListMF.jug_water, (InformationBase)null)).registerStat().setPage(provisioning).setUnlocked();
        berry = (new InformationBase("berry",            	 0,  1, 0, FoodListMF.berries, (InformationBase)null)).registerStat().setPage(provisioning).setUnlocked();
        icing = (new InformationBase("icing",             -1,  2, 0, FoodListMF.icing, berry)).registerStat().setPage(provisioning).setUnlocked();
        sweetroll = (new InformationBase("sweetroll",      0,  3, 5, FoodListMF.sweetroll, berry)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 15);
        eclair = (new InformationBase("eclair",            2,  3, 10, FoodListMF.eclair, sweetroll)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 75);
        cake = (new InformationBase("cake",	 	         0,  5, 15, BlockListMF.cake_vanilla, sweetroll)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 25);
        carrotcake = (new InformationBase("carrotcake",	-1,  7, 10, BlockListMF.cake_carrot, cake)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 40);
        chococake = (new InformationBase("chococake",	 	 1,  7, 20, BlockListMF.cake_chocolate, cake)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 40);
        bfcake = (new InformationBase("bfcake",	 	     1,  9, 25, BlockListMF.cake_bf, chococake)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 90);
        berrypie = (new InformationBase("berrypie",	 	 2,  1, 10, BlockListMF.pie_berry, berry)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 20);
        applepie = (new InformationBase("applepie",	 	 4,  1, 10, BlockListMF.pie_apple, berrypie)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 30);
        
        cheese = (new InformationBase("cheese",	 		 1, -1, 0, BlockListMF.cheese_wheel, (InformationBase)null)).registerStat().setPage(provisioning).setUnlocked();
        cheeseroll = (new InformationBase("cheeseroll",	 3, -1, 10, FoodListMF.cheese_roll, cheese)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 15);
        
        bandage = (new InformationBase("bandage",	        -3, 0,  0,  ToolListMF.bandage_wool, (InformationBase)null)).registerStat().setPage(provisioning).setUnlocked();
        bandageadv = (new InformationBase("bandageadv",	-5, -1, 10, ToolListMF.bandage_tough, bandage)).registerStat().setPage(provisioning).addSkill(SkillList.provisioning, 40);
        
        //MASTERY
        toughness = (new InformationBase("toughness",	-1, 0,  30, CustomArmourListMF.standard_plate_helmet, (InformationBase)null)).registerStat().setPage(mastery).addSkill(SkillList.combat, 10).setPerk();
        fitness = (new InformationBase("fitness",	     1, 0,  30, ComponentListMF.dragon_heart, (InformationBase)null)).registerStat().setPage(mastery).addSkill(SkillList.combat, 20).setPerk();
        armourpro = (new InformationBase("armourpro",	 2, -1, 50, Items.diamond_chestplate, fitness)).registerStat().setPage(mastery).addSkill(SkillList.combat, 60).setPerk();
        parrypro = (new InformationBase("parrypro",	 	-2, -1, 50, Items.iron_sword, toughness)).registerStat().setPage(mastery).addSkill(SkillList.combat, 20).setPerk();
        counteratt = (new InformationBase("counteratt",	-1, -2, 50, CustomToolListMF.standard_waraxe, parrypro)).registerStat().setPage(mastery).addSkill(SkillList.combat, 25).setPerk();
        autoparry = (new InformationBase("autoparry",   -3, -2, 60, CustomToolListMF.standard_sword, parrypro)).registerStat().setPage(mastery).addSkill(SkillList.combat, 50).setPerk();
        firstaid = (new InformationBase("firstaid",   	 0, 1, 30, ToolListMF.bandage_wool, (InformationBase)null)).registerStat().setPage(mastery).addSkill(SkillList.provisioning, 25).setPerk();
        doctor = (new InformationBase("doctor",          0, 3, 60, ToolListMF.syringe, firstaid)).registerStat().setPage(mastery).addSkill(SkillList.provisioning, 50).setPerk();
        scrapper = (new InformationBase("scrapper",   	 0,-1, 30, BlockListMF.salvage_basic, (InformationBase)null)).registerStat().setPage(mastery).addSkill(SkillList.artisanry, 35).setPerk();
        
        dwarvernKnowledge = (new InformationBase("dwarvernKnowledge",   	 0, 0, 0, ToolListMF.skillbook_dwarvern, (InformationBase)null)).registerStat().setPage(dwarvern).setPerk();
        gnomishKnowledge = (new InformationBase("gnomishKnowledge",   	     0, 0, 0, ToolListMF.skillbook_gnomish, (InformationBase)null)).registerStat().setPage(gnomish).setPerk();
	}
    private static Object getMetalTier(String string) 
    {
    	CustomMaterial mat = MetalMaterial.getMaterial(string);
    	if(mat != null)return mat.crafterTier;
    	
		return "?";
	}
    public static IRecipe carpenterRecipe, waterJugR, milkJugR, plantOilR, sugarRecipe, stickRecipe, dryrocksR, meatpieOut, shepardOut, berryOut, appleOut, pumpPieOut;
    public static final ArrayList<IRecipe> plankRecipe = new ArrayList<IRecipe>();
    public static final ArrayList<IRecipe> stoneBricksR = new ArrayList<IRecipe>();
    
    public static final ArrayList<IAnvilRecipe> hunkR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> bucketR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> ingotR = new ArrayList<IAnvilRecipe>();
    
    public static final ArrayList<IAnvilRecipe> talismanRecipe = new ArrayList<IAnvilRecipe>();
    public static ICarpenterRecipe artBookR, conBookR, proBookR, engBookR, comBookR, artBook2R, conBook2R, proBook2R, engBook2R, comBook2R;
    public static IAnvilRecipe greatTalismanRecipe;
    
    public static ICarpenterRecipe fireclayR, fireBrickR, fireBricksR, fireBrickStairR, refinedPlankBlockR, clayWallR, bSalvageR, tannerRecipe, stoneAnvilRecipe, forgeRecipe, apronRecipe, woodTroughRecipe;
    public static ICarpenterRecipe researchTableRecipe, framedGlassR, windowR, thatchR, thatchStairR;
    public static IAnvilRecipe framedStoneR, iframedStoneR, fluxR;
    public static final ArrayList<IAnvilRecipe> barsR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> nailR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> rivetR = new ArrayList<IAnvilRecipe>();
    
    public static IAnvilRecipe tinderboxR, flintAndSteelR;
    public static ICarpenterRecipe lStripsR, threadR, stringR, sharpRocksR, stonePickR, stoneAxeR, stoneSpadeR, stoneHoeR, stoneSwordR, stoneWarR, stoneMaceR, stoneSpearR, stoneHammerR, stoneTongsR, boneNeedleR, stoneKnifeR, quernR;
    
    public static Alloy[] reStone, bronze, steel, obsidalloy, black, red, blue, mithril, adamantium, ignotumite, mithium, enderforge, wolframiteR;
    public static IAnvilRecipe coalfluxR, encrustedR, steelR, obsidianHunkR, diamondR;
    public static ICarpenterRecipe nailPlanksR, nailStairR, refinedStairR, strongRackR, rockTroughRecipe, strongwoodTroughRecipe, bellowsRecipe, bloomeryR, crucibleRecipe, advCrucibleRecipe, chimneyRecipe, wideChimneyRecipe, extractChimneyRecipe;
    
    public static ICarpenterRecipe hideHelmR, hideChestR, hideLegsR, hideBootsR, roughHelmetR, roughChestR, roughLegsR, roughBootsR, reHelmetR, reChestR, reLegsR, reBootsR;
    public static IAnvilRecipe studHelmetR, studChestR, studLegsR, studBootsR;
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
    
    public static final ArrayList<IAnvilRecipe> pickR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> axeR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> spadeR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> hoeR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> shearsR = new ArrayList<IAnvilRecipe>();
    
    public static final ArrayList<IAnvilRecipe> daggerR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> swordR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> waraxeR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> maceR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> spearR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> bowR = new ArrayList<IAnvilRecipe>();
    
    public static final ArrayList<IAnvilRecipe> katanaR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> gswordR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> whammerR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> battleaxeR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> halbeardR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> lanceR = new ArrayList<IAnvilRecipe>();
    
    public static final ArrayList<IAnvilRecipe> trowR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> hvyShovelR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> handpickR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> hvyPickR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> scytheR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> matockR = new ArrayList<IAnvilRecipe>();
    
    public static final ArrayList<IAnvilRecipe> hammerR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> tongsR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> hvyHammerR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> needleR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> sawsR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> knifeR = new ArrayList<IAnvilRecipe>();
    
    public static final ArrayList<IAnvilRecipe> arrowheadR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> bodkinheadR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> broadheadR = new ArrayList<IAnvilRecipe>();
    public static final ArrayList<IAnvilRecipe> crossBoltR = new ArrayList<IAnvilRecipe>();
    public static ICarpenterRecipe fletchingR;
    public static final ArrayList<ICarpenterRecipe> arrowR = new ArrayList<ICarpenterRecipe>();
    public static final ArrayList<ICarpenterRecipe> malletR = new ArrayList<ICarpenterRecipe>();
    public static final ArrayList<ICarpenterRecipe> spoonR = new ArrayList<ICarpenterRecipe>();
    
    public static IAnvilRecipe ironPrepR, ironPrepR2, coalPrepR;
    public static IAnvilRecipe blastChamR, blastHeatR;
    
    public static ICarpenterRecipe padding[] = new ICarpenterRecipe[4];
    public static ICarpenterRecipe repairBasicR, repairAdvancedR, repairOrnateR;
    public static ICarpenterRecipe spyglassR, bombBenchCraft, bombPressCraft, advancedForgeR, engTannerR, autoCrucibleR;
    public static ICarpenterRecipe crudeBombR, bombFuseR, longFuseR;
    public static ICarpenterRecipe crossBenchCraft, crossStockWoodR, crossStockIronR, crossHandleWoodR, crossHeadLightR, crossHeadMediumR, crossHeadHeavyR, crossHeadAdvancedR, crossAmmoR, crossScopeR;
    
    public static ICarpenterRecipe bombCaseCeramicR, mineCaseCeramicR, bombCaseCrystalR, mineCaseCrystalR;
    public static IAnvilRecipe bombCaseIronR, mineCaseIronR, bombCaseObsidianR, mineCaseObsidianR, crossBayonetR;
    
    public static final ArrayList<ICarpenterRecipe> meatRecipes = new ArrayList<ICarpenterRecipe>();
    public static ICarpenterRecipe meatStripR, meatHunkR, gutsRecipe;
    public static IAnvilRecipe caketinRecipe;
    public static ICarpenterRecipe breadSliceR, pastryRecipe, doughRecipe, breadRecipe, curdRecipe, oatsRecipe, custardRecipe, icingRecipe, stewRecipe, saussageR, jerkyRecipe, meatPieRecipe, sandwitchRecipe, sandwitchBigRecipe, shepardRecipe, sweetrollRecipe, iceSR, eclairDoughR, eclairIceR, eclairFillR;
    public static ICarpenterRecipe pumpPieR, simpCakeR, simpCakeOut, berryR, appleR, cheeserollR, cakeR, carrotCakeR, chocoCakeR, bfCakeR, cakeI, carrotCakeI, chocoCakeI, bfCakeI;

    public static ICarpenterRecipe syringeR, parachuteR, bandageR, badBandageR, goodBandageR, cogShaftR;
    
    public static IAnvilRecipe brushRecipe, spannerRecipe, blkspannerR, climbPickbR, iframeR, boltR, istrutR, bgearR, tgearR, stubeR, eatoolsR, bombarrowR, bombBoltR, compPlateR, cogFrameHelmetR, cogFrameChestR, cogFrameLegsR, cogFrameBootsR, cogArmourHelmetR, cogArmourChestR, cogArmourLegsR, cogArmourBootsR;
    public static ICarpenterRecipe mouldRecipe, jugRecipe, potRecipe, pieTrayRecipe, blackpowderRec, advblackpowderRec, magmaRefinedR, chocoRecipe;
    
    public static final ArrayList<ICarpenterRecipe> easyPaintPlank = new ArrayList<ICarpenterRecipe>();
    public static final ArrayList<ICarpenterRecipe> refinedPlankR = new ArrayList<ICarpenterRecipe>();
}
