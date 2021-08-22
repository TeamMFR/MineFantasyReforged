package minefantasy.mfr.client;

import minefantasy.mfr.client.knowledge.EntryPage;
import minefantasy.mfr.client.knowledge.EntryPageBaking;
import minefantasy.mfr.client.knowledge.EntryPageBlastFurnace;
import minefantasy.mfr.client.knowledge.EntryPageCrucible;
import minefantasy.mfr.client.knowledge.EntryPageGrind;
import minefantasy.mfr.client.knowledge.EntryPageImage;
import minefantasy.mfr.client.knowledge.EntryPageRecipeAnvil;
import minefantasy.mfr.client.knowledge.EntryPageRecipeBase;
import minefantasy.mfr.client.knowledge.EntryPageRecipeBloom;
import minefantasy.mfr.client.knowledge.EntryPageRecipeCarpenter;
import minefantasy.mfr.client.knowledge.EntryPageSmelting;
import minefantasy.mfr.client.knowledge.EntryPageText;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyKnowledgeList;
import minefantasy.mfr.recipe.CraftingManagerAnvil;
import minefantasy.mfr.recipe.CraftingManagerCarpenter;
import minefantasy.mfr.util.RecipeHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KnowledgePageRegistry {
	public static void registerPages() {
		ItemStack pig_iron = MineFantasyItems.bar("pig_iron");
		ItemStack black = MineFantasyItems.bar("black_steel");
		ItemStack red = MineFantasyItems.bar("red_steel");
		ItemStack blue = MineFantasyItems.bar("blue_steel");

		if (ConfigHardcore.HCCallowRocks) {
			MineFantasyKnowledgeList.gettingStarted.addPages(
					new EntryPageText("knowledge.gettingStarted.hcc"),
					new EntryPageRecipeBase(MineFantasyKnowledgeList.CARPENTER_RECIPE),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("sharp_rock")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_pick")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_hammer")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("sharp_rock-2")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_spear")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_axe")));
		}

		MineFantasyKnowledgeList.gettingStarted.addPages(
				new EntryPageText("knowledge.gettingStarted.1"),
				new EntryPageText("knowledge.gettingStarted.2"),
				new EntryPageText("knowledge.gettingStarted.carpenter"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.CARPENTER_RECIPE),
				new EntryPageText("knowledge.gettingStarted.fire"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.FIREPIT_RECIPE),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.DRYROCKS_RECIPE),
				new EntryPageText("knowledge.gettingStarted.food"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.STOVE_RECIPE),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("oven")),
				new EntryPageText("knowledge.gettingStarted.tanning"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("tanner")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_knife")),
				new EntryPageText("knowledge.gettingStarted.forging"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("anvil_stone")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("forge_stone")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_hammer")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_tongs")),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.DRYROCKS_RECIPE),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("leather_apron")),
				new EntryPageText("knowledge.gettingStarted.forgingbars"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName( "ingot")),
				new EntryPageText("knowledge.gettingStarted.flux"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("flux")),
				new EntryPageText("knowledge.gettingStarted.3"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_shears")),
				new EntryPageText("knowledge.gettingStarted.4"),
				new EntryPageText("knowledge.gettingStarted.5"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_hammer")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_tongs")),
				new EntryPageText("knowledge.gettingStarted.6"),
				new EntryPageText("knowledge.gettingStarted.upgrade"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("research_bench")),
				new EntryPageText("knowledge.gettingStarted.7"),
				new EntryPageText("knowledge.gettingStarted.10"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("clay_pot_uncooked")),
				new EntryPageSmelting(MineFantasyItems.CLAY_POT_UNCOOKED, MineFantasyItems.CLAY_POT),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("quern")),
				new EntryPageText("knowledge.gettingStarted.11"),
				new EntryPageText("knowledge.gettingStarted.12"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("salvage_basic")),
				new EntryPageText("knowledge.gettingStarted.13"));

		MineFantasyKnowledgeList.research.addPages(
				new EntryPageText("knowledge.research.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("research_bench")),
				new EntryPageText("knowledge.research.2"));

		//IDKH to make it look not ugly without significant code changes
		MineFantasyKnowledgeList.talisman.addPages(new EntryPageText("knowledge.talisman.1"));
		if (!ConfigHardcore.HCCRemoveTalismansCraft) {
			MineFantasyKnowledgeList.talisman.addPages(new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("talisman_lesser")));
		}

		if (!ConfigHardcore.HCCRemoveBooksCraft) {
			MineFantasyKnowledgeList.talisman.addPages(
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_artisanry")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_construction")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_provisioning")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_engineering")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_combat")));
		}

		MineFantasyKnowledgeList.talisman.addPages(new EntryPageText("knowledge.talisman.2"));

		if (!ConfigHardcore.HCCRemoveTalismansCraft) {
			MineFantasyKnowledgeList.talisman.addPages(new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("talisman_greater")));
		}

		if (!ConfigHardcore.HCCRemoveBooksCraft) {
			MineFantasyKnowledgeList.talisman.addPages(
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_artisanry_max")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_construction_max")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_provisioning_max")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_engineering_max")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_combat_max")));
		}

		MineFantasyKnowledgeList.stamina.addPages(new EntryPageText("knowledge.stamina.1"));

		MineFantasyKnowledgeList.combat.addPages(
				new EntryPageText("knowledge.combat.1"),
				new EntryPageText("knowledge.parry.info"),
				new EntryPageText("knowledge.advparry.info"),
				new EntryPageText("knowledge.poweratt.info"),
				new EntryPageText("knowledge.dodge.info"),
				new EntryPageText("knowledge.armour.info"));

		MineFantasyKnowledgeList.craftArmourBasic.addPages(
				new EntryPageText("knowledge.craftArmourBasic.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("hide_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("hide_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("hide_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("hide_boots")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_boots")));

		MineFantasyKnowledgeList.carpenter.addPages(
				new EntryPageText("knowledge.carpenter.1"),
				new EntryPageRecipeBase(RecipeHelper.getMFRRecipe("carpenter")));

		MineFantasyKnowledgeList.salvage.addPages(
				new EntryPageText("knowledge.salvage.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("salvage_basic")),
				new EntryPageText("knowledge.salvage.2"));

		MineFantasyKnowledgeList.commodities.addPages(
				new EntryPageText("knowledge.commodities.1"),
				new EntryPageText("knowledge.commodities.plank"),
				new EntryPageRecipeBase(RecipeHelper.getMFRRecipe("timber")),
				new EntryPageRecipeBase(RecipeHelper.getMFRRecipe("sticks")),
				new EntryPageText("knowledge.commodities.refinedplank"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("jug_uncooked")),
				new EntryPageSmelting(MineFantasyItems.JUG_UNCOOKED, MineFantasyItems.JUG_EMPTY),
				new EntryPageRecipeBase(RecipeHelper.getMFRRecipe("jug_plant_oil")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("refined_timber")),
				new EntryPageText("knowledge.commodities.flux"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("flux")),
				new EntryPageText("knowledge.bar.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("metal_hunk")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("ingot")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar_hunks")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar")),
				new EntryPageText("knowledge.commodities.hunks"),
				new EntryPageText("knowledge.commodities.nail"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("nail")),
				new EntryPageText("knowledge.commodities.rivet"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("rivet")),
				new EntryPageText("knowledge.commodities.leatherstrip"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("leather_strip")),
				new EntryPageText("knowledge.commodities.thread"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("thread")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("string")),
				new EntryPageText("knowledge.commodities.bucket"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bucket")));

		MineFantasyKnowledgeList.dust.addPages(
				new EntryPageText("knowledge.dust.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("clay_pot_uncooked")),
				new EntryPageSmelting(MineFantasyItems.CLAY_POT_UNCOOKED, MineFantasyItems.CLAY_POT),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("quern")),
				new EntryPageText("knowledge.dust.quern"),
				new EntryPageGrind(new ItemStack(Items.COAL), new ItemStack(MineFantasyItems.COAL_DUST)),
				new EntryPageGrind(new ItemStack(MineFantasyItems.KAOLINITE), new ItemStack(MineFantasyItems.KAOLINITE_DUST)),
				new EntryPageGrind(new ItemStack(Items.WHEAT), new ItemStack(MineFantasyItems.FLOUR)),
				new EntryPageGrind(new ItemStack(Items.DYE, 1, 3), new ItemStack(MineFantasyItems.COCA_POWDER)),
				new EntryPageText("knowledge.dust.icing"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("standard_spoon")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("icing")));

		MineFantasyKnowledgeList.ores.addPages(
				new EntryPageText("knowledge.ores.1"),
				new EntryPageText(""));

		MineFantasyKnowledgeList.ores.addPages(assembleOreDescHC("copper", MineFantasyBlocks.COPPER_ORE, MineFantasyItems.COPPER_INGOT));
		MineFantasyKnowledgeList.ores.addPages(assembleOreDescHC("tin", MineFantasyBlocks.TIN_ORE, MineFantasyItems.TIN_INGOT));
		MineFantasyKnowledgeList.ores.addPages(assembleOreDescHC("silver", MineFantasyBlocks.SILVER_ORE, MineFantasyItems.SILVER_INGOT));
		MineFantasyKnowledgeList.ores.addPages(assembleOreDesc("wolframite", MineFantasyBlocks.TUNGSTEN_ORE));
		MineFantasyKnowledgeList.ores.addPages(new EntryPageCrucible(MineFantasyKnowledgeList.wolframiteR));
		MineFantasyKnowledgeList.ores.addPages(assembleOreDesc("mythic", MineFantasyBlocks.MYTHIC_ORE));
		MineFantasyKnowledgeList.ores.addPages(new EntryPageText("knowledge.ores.2"));
		MineFantasyKnowledgeList.ores.addPages(assembleMineralDesc("clay", MineFantasyBlocks.CLAY_ORE));
		MineFantasyKnowledgeList.ores.addPages(assembleMineralDesc("kaolinite", MineFantasyBlocks.KAOLINITE_ORE));
		MineFantasyKnowledgeList.ores.addPages(assembleMineralDesc("limestone", MineFantasyBlocks.LIMESTONE));
		MineFantasyKnowledgeList.ores.addPages(assembleMineralDesc("borax", MineFantasyBlocks.BORAX_ORE));
		MineFantasyKnowledgeList.ores.addPages(assembleMineralDesc("nitre", MineFantasyBlocks.NITRE_ORE));
		MineFantasyKnowledgeList.ores.addPages(assembleMineralDesc("sulfur", MineFantasyBlocks.SULFUR_ORE));

		MineFantasyKnowledgeList.plants.addPages(new EntryPageText("knowledge.plants.1"));
		MineFantasyKnowledgeList.plants.addPages(assembleImgPage("berry", MineFantasyBlocks.BERRY_BUSH));
		MineFantasyKnowledgeList.plants.addPages(new EntryPageText("knowledge.plants.2"));
		MineFantasyKnowledgeList.plants.addPages(assembleImgPage("yew", MineFantasyBlocks.LOG_YEW));
		MineFantasyKnowledgeList.plants.addPages(assembleImgPage("ironbark", MineFantasyBlocks.LOG_IRONBARK));
		MineFantasyKnowledgeList.plants.addPages(assembleImgPage("ebony", MineFantasyBlocks.LOG_EBONY));

		MineFantasyKnowledgeList.minotaurs.addPages(new EntryPageText("knowledge.minotaurs.1"),
				new EntryPageText("knowledge.minotaurs.2"), new EntryPageText("knowledge.minotaurs.3"),
				new EntryPageText("knowledge.minotaurs.4"));
		MineFantasyKnowledgeList.minotaurs.addPages(assembleMobDesc("minotaur"));
		MineFantasyKnowledgeList.minotaurs.addPages(assembleMobDesc("minotaur_frost"));
		MineFantasyKnowledgeList.minotaurs.addPages(assembleMobDesc("minotaur_dread"));
		MineFantasyKnowledgeList.minotaurs.addPages(new EntryPageText("knowledge.minotaurs.combat"));

		MineFantasyKnowledgeList.dragons.addPages(
				new EntryPageText("knowledge.dragons.1"),
				new EntryPageText("knowledge.dragons.2"));

		MineFantasyKnowledgeList.dragons.addPages(assembleMobDesc("red_dragon"));
		MineFantasyKnowledgeList.dragons.addPages(assembleMobDesc("blue_dragon"));
		MineFantasyKnowledgeList.dragons.addPages(assembleMobDesc("green_dragon"));
		MineFantasyKnowledgeList.dragons.addPages(assembleMobDesc("ash_dragon"));
		MineFantasyKnowledgeList.dragons.addPages(new EntryPageText("knowledge.dragons.combat"));

		MineFantasyKnowledgeList.chimney.addPages(
				new EntryPageText("knowledge.chimney.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("chimney_stone_thin")),
				new EntryPageText("knowledge.chimney.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("chimney_stone_wide")),
				new EntryPageText("knowledge.chimney.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("chimney_stone_extractor_wide")),
				new EntryPageText("knowledge.chimney.pipe"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("chimney_pipe_thin")),
				assembleSimpleImgPage("smoke_pipe_example", "knowledge.chimney.pipe.2"));

		MineFantasyKnowledgeList.tanning.addPages(
				new EntryPageText("knowledge.tanning.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("tanner")),
				new EntryPageText("knowledge.tanning.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("tanner_refined")));

		MineFantasyKnowledgeList.bloomery.addPages(
				new EntryPageText("knowledge.bloomery.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bloomery")),
				new EntryPageText("knowledge.bloomery.2"),
				new EntryPageText("knowledge.bloomery.3"));

		MineFantasyKnowledgeList.crucible.addPages(
				assembleSimpleImgPage("crucible_example", "knowledge.crucible.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crucible_stone")),
				new EntryPageText("knowledge.crucible.2"));

		if (ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.crucible.addPages(
					new EntryPageText("knowledge.crucible.hcc"),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("ingot_mould_uncooked")),
					new EntryPageSmelting(MineFantasyItems.INGOT_MOULD_UNCOOKED, MineFantasyItems.INGOT_MOULD));
		}

		MineFantasyKnowledgeList.crucible2.addPages(
				assembleSimpleImgPage("fire_crucible_example", "knowledge.crucible2.1"),
				new EntryPageGrind(new ItemStack(MineFantasyItems.KAOLINITE), new ItemStack(MineFantasyItems.KAOLINITE_DUST)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fireclay")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crucible_fireclay")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebricks")),
				assembleSimpleImgPage("fire_crucible", "knowledge.crucible2.blocks"));

		MineFantasyKnowledgeList.bar.addPages(
				new EntryPageText("knowledge.bar.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar_hunks")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("ingot")));

		MineFantasyKnowledgeList.smelt_copper.addPages(new EntryPageText("knowledge.smelt_copper.1"));
		MineFantasyKnowledgeList.smelt_bronze.addPages(new EntryPageText("knowledge.smelt_bronze.1"));

		if (ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.smelt_iron.addPages(new EntryPageText("knowledge.smelt_iron.1"));
			MineFantasyKnowledgeList.smelt_copper.addPages(new EntryPageRecipeBloom(new ItemStack(MineFantasyBlocks.COPPER_ORE), new ItemStack(MineFantasyItems.COPPER_INGOT)));
			MineFantasyKnowledgeList.smelt_bronze.addPages(new EntryPageRecipeBloom(new ItemStack(MineFantasyBlocks.TIN_ORE), new ItemStack(MineFantasyItems.TIN_INGOT)));
		} else {
			MineFantasyKnowledgeList.smelt_copper.addPages(new EntryPageSmelting(new ItemStack(MineFantasyBlocks.COPPER_ORE), new ItemStack(MineFantasyItems.COPPER_INGOT)));
			MineFantasyKnowledgeList.smelt_bronze.addPages(new EntryPageSmelting(new ItemStack(MineFantasyBlocks.TIN_ORE), new ItemStack(MineFantasyItems.TIN_INGOT)));
		}
		MineFantasyKnowledgeList.smelt_bronze.addPages(new EntryPageCrucible(MineFantasyKnowledgeList.bronze));
		MineFantasyKnowledgeList.smelt_iron.addPages(new EntryPageText("knowledge.smelt_iron.2"));

		MineFantasyKnowledgeList.smelt_pig_iron.addPages(
				new EntryPageText("knowledge.smelt_pig_iron.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("prepared_iron")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("prepared_iron-2")),
				new EntryPageBlastFurnace(MineFantasyItems.PREPARED_IRON, pig_iron),
				new EntryPageText("knowledge.blastfurn.9"));

		MineFantasyKnowledgeList.smelt_steel.addPages(
				new EntryPageText("knowledge.smelt_steel.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar_steel")));

		if (!ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.smelt_steel.addPages(new EntryPageCrucible(MineFantasyKnowledgeList.steel));
		}

		MineFantasyKnowledgeList.smelt_encrusted.addPages(
				new EntryPageText("knowledge.smelt_encrusted.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("diamond_shards")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar_encrusted")));

		MineFantasyKnowledgeList.smelt_obsidian.addPages(
				new EntryPageText("knowledge.smelt_obsidian.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("obsidian_rock")),
				new EntryPageCrucible(MineFantasyKnowledgeList.obsidalloy));

		if (ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.smelt_iron.addPages(
					new EntryPageRecipeBloom(new ItemStack(Blocks.IRON_ORE), new ItemStack(Items.IRON_INGOT)));
		} else {
			MineFantasyKnowledgeList.smelt_iron
					.addPages(new EntryPageSmelting(new ItemStack(Blocks.IRON_ORE), new ItemStack(Items.IRON_INGOT)));
		}

		MineFantasyKnowledgeList.apron.addPages(
				new EntryPageText("knowledge.apron.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("leather_apron")));

		MineFantasyKnowledgeList.bellows.addPages(
				new EntryPageText("knowledge.bellows.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bellows")));

		MineFantasyKnowledgeList.trough.addPages(
				new EntryPageText("knowledge.trough.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("trough_wood_scrap")),
				new EntryPageText("knowledge.trough.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("trough_wood")));

		MineFantasyKnowledgeList.forge.addPages(
				assembleSimpleImgPage("forge_example", "knowledge.forge.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("forge_stone")),
				new EntryPageText("knowledge.forge.2"),
				new EntryPageText("knowledge.forge.3"));

		MineFantasyKnowledgeList.anvil.addPages(
				assembleSimpleImgPage("smithy_example", "knowledge.anvil.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("anvil_stone")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("anvil_bronze")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("anvil_iron")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("anvil_red_steel")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("anvil_black_steel")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("anvil_blue_steel")),
				new EntryPageText("knowledge.anvil.2"),
				new EntryPageImage("textures/gui/knowledge/anvil_gui_example.png", 128, 128, ""),
				new EntryPageText("knowledge.anvil.3"),
				new EntryPageText("knowledge.anvil.4"),
				new EntryPageText("knowledge.anvil.5"),
				new EntryPageText("knowledge.anvil.6"),
				new EntryPageImage("textures/gui/knowledge/quality_example.png", 128, 128, "knowledge.anvil.7"));

		MineFantasyKnowledgeList.smeltDragonforge.addPages(
				new EntryPageText("knowledge.smeltDragonforge.1"),
				new EntryPageText("knowledge.smeltDragonforge.2"));

		MineFantasyKnowledgeList.craftOrnate.addPages(
				new EntryPageText("knowledge.craftOrnate.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("ornate_items")));

		MineFantasyKnowledgeList.craftArmourLight.addPages(
				new EntryPageText("knowledge.craftArmourLight.1"),
				new EntryPageText("knowledge.craftArmourLight.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_boots")),
				new EntryPageText("knowledge.craftArmourLight.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_boots")),
				new EntryPageText("knowledge.craftArmourLight.4"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("strong_leather_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("strong_leather_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("strong_leather_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("strong_leather_boots")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("stud_leather_helmet")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("stud_leather_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("stud_leather_leggings")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("stud_leather_boots")));

		MineFantasyKnowledgeList.craftArmourMedium.addPages(
				new EntryPageText("knowledge.craftArmourMedium.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("chain_mesh")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_chain_helmet")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_chain_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_chain_leggings")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_chain_boots")),
				new EntryPageText("knowledge.craftArmourMedium.scale"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("scale_mesh")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_scale_helmet")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_scale_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_scale_leggings")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_scale_boots")));

		MineFantasyKnowledgeList.craftArmourHeavy.addPages(
				new EntryPageText("knowledge.craftArmourHeavy.1"),
				new EntryPageText("knowledge.craftArmourHeavy.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_boots")),
				new EntryPageText("knowledge.craftArmourHeavy.splint"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("splint_mesh")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_splint_helmet")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_splint_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_splint_leggings")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_splint_boots")),
				new EntryPageText("knowledge.craftArmourHeavy.plate"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("plate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_plate_helmet")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_plate_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_plate_leggings")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_plate_boots")));

		MineFantasyKnowledgeList.coalflux.addPages(
				new EntryPageText("knowledge.coalflux.1"),
				new EntryPageGrind(new ItemStack(MineFantasyItems.FLUX), new ItemStack(MineFantasyItems.FLUX_POT)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("coal_flux")));

		MineFantasyKnowledgeList.bigfurn.addPages(
				assembleSimpleImgPage("furnace_example", "knowledge.bigfurn.1"),
				new EntryPageText("knowledge.bigfurn.2"),
				new EntryPageGrind(new ItemStack(MineFantasyItems.KAOLINITE), new ItemStack(MineFantasyItems.KAOLINITE_DUST)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fireclay")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fireclay_brick")),
				new EntryPageSmelting(new ItemStack(MineFantasyItems.FIRECLAY_BRICK), new ItemStack(MineFantasyItems.STRONG_BRICK)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebricks")),
				new EntryPageText("knowledge.bigfurn.heater"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("furnace_heater")),
				assembleSimpleImgPage("furnace_heater", MineFantasyBlocks.FURNACE_HEATER.getUnlocalizedName() + ".name"),
				new EntryPageText("knowledge.bigfurn.top"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("furnace_stone")),
				assembleSimpleImgPage("furnace_full", "knowledge.bigfurn.structure"));

		MineFantasyKnowledgeList.blastfurn.addPages(
				new EntryPageText("knowledge.blastfurn.1"),
				new EntryPageText("knowledge.blastfurn.2"),
				new EntryPageGrind(new ItemStack(MineFantasyItems.KAOLINITE), new ItemStack(MineFantasyItems.KAOLINITE_DUST)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fireclay")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fireclay_brick")),
				new EntryPageSmelting(new ItemStack(MineFantasyItems.FIRECLAY_BRICK), new ItemStack(MineFantasyItems.STRONG_BRICK)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebricks")),
				new EntryPageText("knowledge.blastfurn.3"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("blast_chamber")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("blast_heater")),
				new EntryPageText("knowledge.blastfurn.4"),
				new EntryPageText("knowledge.blastfurn.5"),
				new EntryPageImage("textures/gui/knowledge/blast_example.png", 96, 96, "knowledge.blastfurn.6"),
				new EntryPageText("knowledge.blastfurn.7"));

		if (ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.blastfurn.addPages(new EntryPageText("knowledge.blastfurn.hcc"));
		}

		MineFantasyKnowledgeList.etools.addPages(
				new EntryPageText("knowledge.etools.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_spanner")), new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("engin_anvil_tools")));

		MineFantasyKnowledgeList.ecomponents.addPages(
				new EntryPageText("knowledge.ecomponents.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bolt")),
				new EntryPageText("knowledge.ecomponents.2"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("iron_frame")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bronze_gears")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("tungsten_gears")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("iron_strut")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("steel_tube")));

		MineFantasyKnowledgeList.tungsten.addPages(
				new EntryPageText("knowledge.tungsten.1"),
				new EntryPageText("knowledge.tungsten.2"),
				new EntryPageText("knowledge.tungsten.3"),
				new EntryPageCrucible(MineFantasyKnowledgeList.wolframiteR));

		MineFantasyKnowledgeList.climber.addPages(
				new EntryPageText("knowledge.climber.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("climbing_pick_basic")),
				new EntryPageText("knowledge.climber.2"));

		MineFantasyKnowledgeList.spyglass.addPages(
				new EntryPageText("knowledge.spyglass.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("spyglass")));

		MineFantasyKnowledgeList.parachute.addPages(
				new EntryPageText("knowledge.parachute.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("parachute")));

		MineFantasyKnowledgeList.syringe.addPages(
				new EntryPageText("knowledge.syringe.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("syringe_empty")));

		MineFantasyKnowledgeList.engTanner.addPages(
				new EntryPageText("knowledge.engTanner.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("tanner_metal")));

		MineFantasyKnowledgeList.advcrucible.addPages(
				assembleSimpleImgPage("auto_crucible_example", MineFantasyBlocks.CRUCIBLE_AUTO.getUnlocalizedName() + ".name"),
				new EntryPageText("knowledge.advcrucible.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crucible_auto")),
				new EntryPageText("knowledge.crucible2.blocks"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebricks")),
				assembleSimpleImgPage("auto_crucible", "knowledge.basicstructure"));

		MineFantasyKnowledgeList.advforge.addPages(
				new EntryPageText("knowledge.advforge.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("forge_metal")));

		MineFantasyKnowledgeList.coke.addPages(
				new EntryPageText("knowledge.coke.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("prepared_coal")),
				new EntryPageSmelting(MineFantasyItems.PREPARED_COAL, MineFantasyItems.COKE));

		MineFantasyKnowledgeList.blackpowder.addPages(
				new EntryPageText("knowledge.blackpowder.1"),
				new EntryPageGrind(new ItemStack(Items.COAL), new ItemStack(MineFantasyItems.COAL_DUST)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("blackpowder")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_crude")));

		MineFantasyKnowledgeList.advblackpowder.addPages(
				new EntryPageText("knowledge.advblackpowder.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("blackpowder_advanced")));

		MineFantasyKnowledgeList.bombs.addPages(
				new EntryPageText("knowledge.bombs.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_bench")),
				new EntryPageText("knowledge.bombs.2"),
				new EntryPageImage("textures/gui/knowledge/bomb_gui_example.png", 128, 128, "knowledge.guiSubtitle"),
				new EntryPageText("knowledge.bombs.3"),
				new EntryPageText("knowledge.bombs.4"));

		MineFantasyKnowledgeList.bpress.addPages(
				new EntryPageText("knowledge.bpress.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_press")));

		MineFantasyKnowledgeList.bombarrow.addPages(
				new EntryPageText("knowledge.bombarrow.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bomb_casing_arrow")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bomb_casing_bolt")));

		MineFantasyKnowledgeList.shrapnel.addPages(
				new EntryPageText("knowledge.shrapnel.1"),
				new EntryPageGrind(new ItemStack(Items.FLINT), new ItemStack(MineFantasyItems.SHRAPNEL)));

		MineFantasyKnowledgeList.firebomb.addPages(
				new EntryPageText("knowledge.firebomb.1"),
				new EntryPageText("knowledge.firebomb.2"),
				new EntryPageText("knowledge.firebomb.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("magma_cream_refined")));

		MineFantasyKnowledgeList.bombCeramic.addPages(
				new EntryPageText("knowledge.bombCeramic.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_casing_uncooked")),
				new EntryPageSmelting(MineFantasyItems.BOMB_CASING_UNCOOKED, MineFantasyItems.BOMB_CASING_CERAMIC));

		MineFantasyKnowledgeList.bombIron.addPages(
				new EntryPageText("knowledge.bombIron.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bomb_casing_iron")));

		MineFantasyKnowledgeList.bombObsidian.addPages(
				new EntryPageText("knowledge.bombObsidian.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bomb_casing_obsidian")));

		MineFantasyKnowledgeList.bombCrystal.addPages(
				new EntryPageText("knowledge.bombCrystal.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_casing_crystal")));

		MineFantasyKnowledgeList.mineCeramic.addPages(
				new EntryPageText("knowledge.mineCeramic.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("mine_casing_uncooked")),
				new EntryPageSmelting(MineFantasyItems.MINE_CASING_UNCOOKED, MineFantasyItems.MINE_CASING_CERAMIC));

		MineFantasyKnowledgeList.mineIron.addPages(
				new EntryPageText("knowledge.mineIron.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("mine_casing_iron")));

		MineFantasyKnowledgeList.mineObsidian.addPages(
				new EntryPageText("knowledge.mineObsidian.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("mine_casing_obsidian")));

		MineFantasyKnowledgeList.mineCrystal.addPages(
				new EntryPageText("knowledge.mineCrystal.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("mine_casing_crystal")));

		MineFantasyKnowledgeList.bombFuse.addPages(
				new EntryPageText("knowledge.bombFuse.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_fuse")),
				new EntryPageText("knowledge.bombFuse.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_fuse_long")));

		MineFantasyKnowledgeList.stickybomb.addPages(
				new EntryPageText("knowledge.stickybomb.1"),
				new EntryPageText("knowledge.stickybomb.2"));

		MineFantasyKnowledgeList.crossbows.addPages(
				new EntryPageText("knowledge.crossbows.1"),
				new EntryPageText("knowledge.crossbows.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_bench")),
				new EntryPageText("knowledge.crossbows.3"),
				new EntryPageText("knowledge.crossbows.4"),
				new EntryPageText("knowledge.crossbows.5"),
				new EntryPageText("knowledge.crossbows.6"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_bolt")));

		MineFantasyKnowledgeList.crossShafts.addPages(
				new EntryPageText("knowledge.crossShafts.handle"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_handle_wood")),
				new EntryPageText("knowledge.crossShafts.stock"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_stock_wood")));

		MineFantasyKnowledgeList.crossHeads.addPages(
				new EntryPageText("knowledge.crossHeads.light"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_arms_light")),
				new EntryPageText("knowledge.crossHeads.medium"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_arms_basic")),
				new EntryPageText("knowledge.crossHeads.heavy"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_arms_heavy")));

		MineFantasyKnowledgeList.crossHeadAdvanced.addPages(
				new EntryPageText("knowledge.crossHeads.advanced"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_arms_advanced")));

		MineFantasyKnowledgeList.crossShaftAdvanced.addPages(
				new EntryPageText("knowledge.crossShafts.advanced"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_stock_iron")));

		MineFantasyKnowledgeList.crossScope.addPages(
				new EntryPageText("knowledge.crossScope.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_scope")));

		MineFantasyKnowledgeList.crossAmmo.addPages(
				new EntryPageText("knowledge.crossAmmo.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_ammo")));

		MineFantasyKnowledgeList.crossBayonet.addPages(
				new EntryPageText("knowledge.crossBayonet.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("crossbow_bayonet")));

		MineFantasyKnowledgeList.cogArmour.addPages(
				new EntryPageText("knowledge.cogArmour.1"),
				new EntryPageText("knowledge.cogArmour.2"),
				new EntryPageText("knowledge.cogArmour.station"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("frame_block")),
				assembleSimpleImgPage("cogwork_station", "knowledge.cogwork_station"),
				new EntryPageText("knowledge.cogArmour.station.2"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("cogwork_pulley")),
				assembleSimpleImgPage("cogwork_station_2", "knowledge.rightclickspanner"),
				new EntryPageText("knowledge.cogArmour.crafting"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cogwork_shaft")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("block_cogwork_helm")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("block_cogwork_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("block_cogwork_legs")),
				assembleSimpleImgPage("cogwork_suit_craft", "knowledge.rightclickspanner"),
				new EntryPageText("knowledge.cogArmour.armour"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("plate_huge")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("cogwork_armour")),
				new EntryPageText("knowledge.cogArmour.advantage"),
				new EntryPageText("knowledge.cogArmour.advantage.2"),
				new EntryPageText("knowledge.cogArmour.advantage.3"),
				new EntryPageText("knowledge.cogArmour.disadvantage"),
				new EntryPageText("knowledge.cogArmour.disadvantage.2"),
				new EntryPageText("knowledge.cogArmour.removal"));

		MineFantasyKnowledgeList.compPlate.addPages(
				new EntryPageText("knowledge.compPlate.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar_composite_alloy")));

		MineFantasyKnowledgeList.repair_basic.addPages(
				new EntryPageText("knowledge.repair_basic.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("repair_kit_basic")));

		MineFantasyKnowledgeList.repair_advanced.addPages(
				new EntryPageText("knowledge.repair_advanced.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("repair_kit_advanced")));

		MineFantasyKnowledgeList.repair_ornate.addPages(
				new EntryPageText("knowledge.repair_ornate.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("repair_kit_ornate")));

		MineFantasyKnowledgeList.refined_planks.addPages(
				new EntryPageText("knowledge.refined_planks.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("nailed_planks")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("refined_planks")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("nailed_planks_stairs")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("refined_planks_stairs")));

		MineFantasyKnowledgeList.reinforced_stone.addPages(
				new EntryPageText("knowledge.reinforced_stone.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("obsidian_rock")),
				new EntryPageCrucible(MineFantasyKnowledgeList.reinforcedStone));

		MineFantasyKnowledgeList.brickworks.addPages(
				new EntryPageText("knowledge.brickworks.1"),
				new EntryPageRecipeBase(RecipeHelper.getMFRRecipe("cobble_brick"),
						RecipeHelper.getMFRRecipe("reinforced_stone_bricks"),
						RecipeHelper.getMFRRecipe("mud_brick")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebricks")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebrick_stairs")));

		MineFantasyKnowledgeList.clay_wall.addPages(
				new EntryPageText("knowledge.clay_wall.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("clay_wall")));

		MineFantasyKnowledgeList.glass.addPages(
				new EntryPageText("knowledge.glass.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("framed_glass")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("window")));

		MineFantasyKnowledgeList.thatch.addPages(
				new EntryPageText("knowledge.thatch.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("thatch")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("thatch_stairs")));

		MineFantasyKnowledgeList.bars.addPages(
				new EntryPageText("knowledge.bars.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bronze_bars", "iron_bars",
						"steel_bars", "black_steel_bars", "red_steel_bars", "blue_steel_bars")));

		MineFantasyKnowledgeList.paint_brush.addPages(
				new EntryPageText("knowledge.paint_brush.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("paint_brush")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("refined_timber_brush")));

		MineFantasyKnowledgeList.decorated_stone.addPages(
				new EntryPageText("knowledge.decorated_stone.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("reinforced_stone_framed")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("reinforced_stone_framed_iron")));

		//        KnowledgeListMFR.bed_roll.addPages(new EntryPageText("knowledge.bed_roll.1"),
		//                new EntryPageRecipeCarpenter(KnowledgeListMFR.bedrollR));

		MineFantasyKnowledgeList.tool_rack.addPages(
				new EntryPageText("knowledge.tool_rack.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rack_wood")),
				new EntryPageText("knowledge.tool_rack.rules"));

		MineFantasyKnowledgeList.food_box.addPages(
				new EntryPageText("knowledge.food_box.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("food_box_basic")),
				new EntryPageText("knowledge.ammo_box.2"));

		MineFantasyKnowledgeList.ammo_box.addPages(
				new EntryPageText("knowledge.ammo_box.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("ammo_box_basic")),
				new EntryPageText("knowledge.ammo_box.2"));

		MineFantasyKnowledgeList.big_box.addPages(
				new EntryPageText("knowledge.big_box.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crate_basic")),
				new EntryPageText("knowledge.ammo_box.2"));

		MineFantasyKnowledgeList.constructionPts.addPages(
				new EntryPageText("knowledge.constructionPts.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("timber_cut")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("timber_pane")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("hinge")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("jug_uncooked")),
				new EntryPageSmelting(MineFantasyItems.JUG_UNCOOKED, MineFantasyItems.JUG_EMPTY),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.JUG_PLANT_OIL_RECIPE),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("refined_timber")));

		if (ConfigHardcore.HCCallowRocks) {
			MineFantasyKnowledgeList.craftHCCTools.addPages(
					new EntryPageText("knowledge.craftHCCTools.1"),
					new EntryPageText("knowledge.craftHCCTools.2"),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("sharp_rock")),
					new EntryPageText("knowledge.craftHCCTools.3"),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_pick")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_spade")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_axe")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_hoe")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_knife")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_hammer")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_tongs")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bone_needle")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_sword")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_mace")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_waraxe")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_spear")));
		}

		MineFantasyKnowledgeList.craftTools.addPages(
				new EntryPageText("knowledge.craftTools.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_pick")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_axe")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_spade")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_hoe")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_shears")));

		MineFantasyKnowledgeList.craftAdvTools.addPages(
				new EntryPageText("knowledge.craftAdvTools.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_handpick")),
				new EntryPageText("knowledge.heavy_pick.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_heavy_pick")),
				new EntryPageText("knowledge.trow.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_trow")),
				new EntryPageText("knowledge.heavy_shovel.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_heavy_shovel")),
				new EntryPageText("knowledge.scythe.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_scythe")),
				new EntryPageText("knowledge.mattock.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_mattock")),
				new EntryPageText("knowledge.mattock.use"),
				new EntryPageText("knowledge.lumber.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_lumber")));

		MineFantasyKnowledgeList.firemaker.addPages(
				new EntryPageText("knowledge.dryrocks.info"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.DRYROCKS_RECIPE),
				new EntryPageText("knowledge.tinderbox.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("tinderbox")),
				new EntryPageText("knowledge.flintsteel.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("flint_and_steel")));

		MineFantasyKnowledgeList.craftCrafters.addPages(
				new EntryPageText("knowledge.craftCrafters.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_hammer")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_hammer")),
				new EntryPageText(""),
				new EntryPageText("knowledge.heavy_hammer.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_heavy_hammer")),
				new EntryPageText("knowledge.tongs.info"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_tongs")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_tongs")),
				new EntryPageText(""),
				new EntryPageText("knowledge.knife.info"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_knife")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_knife")),
				new EntryPageText(""),
				new EntryPageText("knowledge.saw.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_saw")),
				new EntryPageText("knowledge.needle.info"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bone_needle")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_needle")),
				new EntryPageText(""),
				new EntryPageText("knowledge.mallet.info"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("standard_mallet")),
				new EntryPageText("knowledge.spoon.info"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("standard_spoon")));

		MineFantasyKnowledgeList.craftWeapons.addPages(
				new EntryPageText("knowledge.craftWeapons.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_dagger")),
				new EntryPageText("knowledge.sword.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_sword")),
				new EntryPageText("knowledge.waraxe.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_waraxe")),
				new EntryPageText("knowledge.mace.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_mace")),
				new EntryPageText("knowledge.spear.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_spear")),
				new EntryPageText("knowledge.bow.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_bow")));

		MineFantasyKnowledgeList.craftAdvWeapons.addPages(
				new EntryPageText("knowledge.craftAdvWeapons.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_katana")),
				new EntryPageText("knowledge.greatsword.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_greatsword")),
				new EntryPageText("knowledge.battleaxe.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_battleaxe")),
				new EntryPageText("knowledge.warhammer.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_warhammer")),
				new EntryPageText("knowledge.halbeard.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_halbeard")),
				new EntryPageText("knowledge.lance.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_lance")));

		MineFantasyKnowledgeList.arrows.addPages(
				new EntryPageText("knowledge.arrows.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fletching")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fletching-2")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("arrowhead")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("standard_arrow"),
						                     CraftingManagerCarpenter.getRecipeByName("standard_arrow_broad"),
						                     CraftingManagerCarpenter.getRecipeByName("standard_arrow_bodkin")));

		MineFantasyKnowledgeList.arrowsBodkin.addPages(
				new EntryPageText("knowledge.arrowsBodkin.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bodkin_head")));

		MineFantasyKnowledgeList.arrowsBroad.addPages(
				new EntryPageText("knowledge.arrowsBroad.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("broad_head")));

		MineFantasyKnowledgeList.smelt_black_steel.addPages(
				new EntryPageText("knowledge.smelt_black_steel.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("obsidian_rock")),
				new EntryPageCrucible(MineFantasyKnowledgeList.black),
				new EntryPageBlastFurnace(MineFantasyItems.BLACK_STEEL_WEAK_INGOT, black));

		MineFantasyKnowledgeList.smelt_red_steel.addPages(
				new EntryPageText("knowledge.smelt_red_steel.1"),
				new EntryPageCrucible(MineFantasyKnowledgeList.red),
				new EntryPageBlastFurnace(MineFantasyItems.RED_STEEL_WEAK_INGOT, red));

		MineFantasyKnowledgeList.smelt_blue_steel.addPages(
				new EntryPageText("knowledge.smelt_blue_steel.1"),
				new EntryPageCrucible(MineFantasyKnowledgeList.blue),
				new EntryPageBlastFurnace(MineFantasyItems.BLUE_STEEL_WEAK_INGOT, blue));

		MineFantasyKnowledgeList.smelt_adamantium.addPages(
				new EntryPageText("knowledge.smelt_adamantium.1"),
				new EntryPageText("knowledge.smelt_adamantium.2"),
				new EntryPageCrucible(MineFantasyKnowledgeList.adamantium));

		MineFantasyKnowledgeList.smelt_mithril.addPages(
				new EntryPageText("knowledge.smelt_mithril.1"),
				new EntryPageText("knowledge.smelt_mithril.2"),
				new EntryPageCrucible(MineFantasyKnowledgeList.mithril));

		MineFantasyKnowledgeList.smeltMaster.addPages(
				new EntryPageText("knowledge.smeltMaster.1"),
				new EntryPageText("knowledge.smeltMaster.2"),
				new EntryPageText("knowledge.smeltMaster.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("trilogy_jewel")));

		MineFantasyKnowledgeList.smelt_ignotumite.addPages(
				new EntryPageText("knowledge.smelt_ignotumite.1"),
				new EntryPageCrucible(MineFantasyKnowledgeList.ignotumite));

		MineFantasyKnowledgeList.smelt_mithium.addPages(
				new EntryPageText("knowledge.smelt_mithium.1"),
				new EntryPageCrucible(MineFantasyKnowledgeList.mithium));

		MineFantasyKnowledgeList.smelt_ender.addPages(
				new EntryPageText("knowledge.smelt_ender.1"),
				new EntryPageCrucible(MineFantasyKnowledgeList.enderforge));

		MineFantasyKnowledgeList.firepit.addPages(
				new EntryPageText("knowledge.firepit.1"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.FIREPIT_RECIPE),
				new EntryPageText("knowledge.firepit.2"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.STOVE_RECIPE),
				new EntryPageText("knowledge.firepit.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("oven")),
				new EntryPageText("knowledge.firepit.4"));

		MineFantasyKnowledgeList.cookingutensil.addPages(
				new EntryPageText("knowledge.cookingutensil.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("cake_tin")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("pie_tray_uncooked")),
				new EntryPageSmelting(MineFantasyItems.PIE_TRAY_UNCOOKED, MineFantasyItems.PIE_TRAY));

		MineFantasyKnowledgeList.salt.addPages(
				new EntryPageText("knowledge.salt.1"),
				new EntryPageSmelting(new ItemStack(MineFantasyItems.BOWL_WATER_SALT), new ItemStack(MineFantasyItems.SALT)));

		MineFantasyKnowledgeList.jug.addPages(
				new EntryPageText("knowledge.jug.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("jug_uncooked")),
				new EntryPageSmelting(MineFantasyItems.JUG_UNCOOKED, MineFantasyItems.JUG_EMPTY),
				new EntryPageText("knowledge.jug.2"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.JUG_WATER_RECIPE),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.JUG_MILK_RECIPE));

		MineFantasyKnowledgeList.generic_meat.addPages(
				new EntryPageText("knowledge.generic_meat.1"),
				new EntryPageRecipeCarpenter(
						CraftingManagerCarpenter.getRecipeByName("generic_meat_uncooked_0"),
						CraftingManagerCarpenter.getRecipeByName("generic_meat_uncooked_1"),
						CraftingManagerCarpenter.getRecipeByName("generic_meat_uncooked_2"),
						CraftingManagerCarpenter.getRecipeByName("generic_meat_uncooked_3"),
						CraftingManagerCarpenter.getRecipeByName("generic_meat_uncooked_4"),
						CraftingManagerCarpenter.getRecipeByName("generic_meat_uncooked_5"),
						CraftingManagerCarpenter.getRecipeByName("generic_meat_uncooked_6"),
						CraftingManagerCarpenter.getRecipeByName("generic_meat_uncooked_7")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("generic_meat_strip_uncooked")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("generic_meat_strip_cooked")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("generic_meat_chunk_uncooked")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("generic_meat_chunk_cooked")),
				new EntryPageGrind(new ItemStack(MineFantasyItems.GENERIC_MEAT_UNCOOKED), new ItemStack(MineFantasyItems.GENERIC_MEAT_MINCE_UNCOOKED)));

		MineFantasyKnowledgeList.stew.addPages(
				new EntryPageText("knowledge.stew.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stew")));

		MineFantasyKnowledgeList.jerky.addPages(
				new EntryPageText("knowledge.jerky.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("jerky")));

		MineFantasyKnowledgeList.saussage.addPages(
				new EntryPageText("knowledge.saussage.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("guts")),
				new EntryPageGrind(new ItemStack(MineFantasyItems.BREADROLL), new ItemStack(MineFantasyItems.BREADCRUMBS)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("saussage_raw")),
				new EntryPageSmelting(new ItemStack(MineFantasyItems.SAUSAGE_RAW), new ItemStack(MineFantasyItems.SAUSAGE_COOKED)));

		MineFantasyKnowledgeList.sandwitch.addPages(
				new EntryPageText("knowledge.sandwitch.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bread_slice")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("sandwitch_meat")));

		MineFantasyKnowledgeList.sandwitch_big.addPages(
				new EntryPageText("knowledge.sandwitch_big.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("sandwitch_big")));

		MineFantasyKnowledgeList.meatpie.addPages(
				new EntryPageText("knowledge.meatpie.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("pie_meat_uncooked")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.PIE_MEAT_UNCOOKED), new ItemStack(MineFantasyItems.PIE_MEAT_COOKED)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_MEAT_RECIPE));

		MineFantasyKnowledgeList.shepardpie.addPages(
				new EntryPageText("knowledge.shepardpie.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("pie_shepard_uncooked")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.PIE_SHEPARD_UNCOOKED), new ItemStack(MineFantasyItems.PIE_SHEPARD_COOKED)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_SHEPARDS_RECIPE));

		MineFantasyKnowledgeList.bread.addPages(
				new EntryPageText("knowledge.bread.1"),
				new EntryPageGrind(new ItemStack(Items.WHEAT), new ItemStack(MineFantasyItems.FLOUR)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("dough")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.DOUGH), new ItemStack(MineFantasyItems.BREADROLL)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("raw_bread")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.RAW_BREAD), new ItemStack(Items.BREAD)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("pastry")),
				new EntryPageGrind(new ItemStack(MineFantasyItems.BREADROLL), new ItemStack(MineFantasyItems.BREADCRUMBS)),
				new EntryPageText("knowledge.bread.other"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("pie_pumpkin_uncooked")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.PIE_PUMPKIN_UNCOOKED), new ItemStack(MineFantasyItems.PIE_PUMPKIN_COOKED)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_PUMPKIN_RECIPE),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake_simple_raw")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.CAKE_SIMPLE_RAW), new ItemStack(MineFantasyItems.CAKE_SIMPLE_UNICED)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake")));

		MineFantasyKnowledgeList.oats.addPages(
				new EntryPageText("knowledge.oats.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("oats")));

		MineFantasyKnowledgeList.berry.addPages(assembleImgPage("berry", MineFantasyBlocks.BERRY_BUSH));

		MineFantasyKnowledgeList.icing.addPages(
				new EntryPageText("knowledge.icing.1"),
				new EntryPageGrind(new ItemStack(Items.REEDS), new ItemStack(MineFantasyItems.SUGAR_POT)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.SUGAR_POT_RECIPE),
				new EntryPageText("knowledge.icing.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("icing")));

		MineFantasyKnowledgeList.sweetroll.addPages(
				new EntryPageText("knowledge.sweetroll.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("sweetroll_raw")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.SWEETROLL_RAW), new ItemStack(MineFantasyItems.SWEETROLL_UNICED)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("sweetroll")));

		MineFantasyKnowledgeList.cake.addPages(
				new EntryPageText("knowledge.cake.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake_raw")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.CAKE_RAW), new ItemStack(MineFantasyItems.CAKE_UNICED)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake_vanilla")));

		MineFantasyKnowledgeList.carrotcake.addPages(
				new EntryPageText("knowledge.carrotcake.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake_carrot_raw")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.CAKE_CARROT_RAW), new ItemStack(MineFantasyItems.CAKE_CARROT_UNICED)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake_carrot")));

		MineFantasyKnowledgeList.chococake.addPages(
				new EntryPageText("knowledge.chococake.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("chocolate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake_choc_raw")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.CAKE_CHOC_RAW), new ItemStack(MineFantasyItems.CAKE_CHOC_UNICED)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake_chocolate")));

		MineFantasyKnowledgeList.bfcake.addPages(
				new EntryPageText("knowledge.bfcake.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("chocolate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake_bf_raw")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.CAKE_BF_RAW), new ItemStack(MineFantasyItems.CAKE_BF_UNICED)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake_choc_raw")));

		MineFantasyKnowledgeList.berrypie.addPages(
				new EntryPageText("knowledge.berrypie.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("pie_berry_uncooked")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.PIE_BERRY_UNCOOKED), new ItemStack(MineFantasyItems.PIE_BERRY_COOKED)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_BERRY_RECIPE));

		MineFantasyKnowledgeList.applepie.addPages(
				new EntryPageText("knowledge.applepie.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("pie_apple_uncooked")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.PIE_APPLE_UNCOOKED), new ItemStack(MineFantasyItems.PIE_APPLE_COOKED)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_APPLE_RECIPE));

		MineFantasyKnowledgeList.eclair.addPages(
				new EntryPageText("knowledge.eclair.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("eclair_raw")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.ECLAIR_RAW), new ItemStack(MineFantasyItems.ECLAIR_UNICED)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("chocolate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("eclair_empty")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("custard")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("eclair")));

		MineFantasyKnowledgeList.cheese.addPages(
				new EntryPageText("knowledge.cheese.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("curds")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.CURDS), new ItemStack(MineFantasyItems.CHEESE_POT)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.CHEESE_WHEEL_RECIPE));

		MineFantasyKnowledgeList.cheeseroll.addPages(
				new EntryPageText("knowledge.cheeseroll.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cheese_roll")));

		MineFantasyKnowledgeList.bandage.addPages(
				new EntryPageText("knowledge.bandage.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bandage_crude")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bandage_crude-2")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bandage_crude-3")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bandage_wool")));

		MineFantasyKnowledgeList.bandageadv.addPages(
				new EntryPageText("knowledge.bandageadv.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bandage_tough")));

		// MASTERY
		MineFantasyKnowledgeList.toughness.addPages(new EntryPageText("knowledge.toughness.1"));
		MineFantasyKnowledgeList.fitness.addPages(new EntryPageText("knowledge.fitness.1"));
		MineFantasyKnowledgeList.armourpro.addPages(new EntryPageText("knowledge.armourpro.1"));
		MineFantasyKnowledgeList.parrypro.addPages(new EntryPageText("knowledge.parrypro.1"));

		MineFantasyKnowledgeList.counteratt.addPages(
				new EntryPageText("knowledge.counteratt.1"),
				new EntryPageText("knowledge.counteratt.2"));

		MineFantasyKnowledgeList.autoparry.addPages(new EntryPageText("knowledge.autoparry.1"));
		MineFantasyKnowledgeList.firstaid.addPages(new EntryPageText("knowledge.firstaid.1"));
		MineFantasyKnowledgeList.doctor.addPages(new EntryPageText("knowledge.doctor.1"));
		MineFantasyKnowledgeList.scrapper.addPages(new EntryPageText("knowledge.scrapper.1"));
	}

	private static EntryPage[] assembleOreDesc(String orename, Block ore, Item ingot) {
		return new EntryPage[] {new EntryPageImage("textures/gui/knowledge/image/" + orename + ".png", 96, 96,
				"knowledge.ores." + orename), new EntryPageSmelting(new ItemStack(ore), new ItemStack(ingot))};
	}

	private static EntryPage[] assembleOreDescHC(String orename, Block ore, Item ingot) {
		if (ConfigHardcore.HCCreduceIngots) {
			return new EntryPage[] {
					new EntryPageImage("textures/gui/knowledge/image/" + orename + ".png", 96, 96,
							"knowledge.ores." + orename),
					new EntryPageRecipeBloom(new ItemStack(ore), new ItemStack(ingot))};
		}
		return assembleOreDesc(orename, ore, ingot);
	}

	private static EntryPage[] assembleOreDesc(String orename, Block ore) {
		return new EntryPage[] {new EntryPageImage("textures/gui/knowledge/image/" + orename + ".png", 96, 96,
				"knowledge.ores." + orename)};
	}

	private static EntryPage[] assembleMineralDesc(String orename, Block ore) {
		return new EntryPage[] {new EntryPageImage("textures/gui/knowledge/image/" + orename + ".png", 96, 96,
				"knowledge.minerals." + orename)};
	}

	private static EntryPage[] assembleImgPage(String name, Block blockname) {
		return new EntryPage[] {new EntryPageImage("textures/gui/knowledge/image/" + name + ".png", 96, 96,
				"knowledge." + name + ".1")};
	}

	private static EntryPage[] assembleMobDesc(String name) {
		return new EntryPage[] {new EntryPageImage("textures/gui/knowledge/image/mob/" + name + ".png", 96, 96,
				"knowledge." + name + ".1")};
	}

	private static EntryPage assembleSimpleImgPage(String name, String text) {
		return new EntryPageImage("textures/gui/knowledge/image/" + name + ".png", 96, 96, text);
	}
}
