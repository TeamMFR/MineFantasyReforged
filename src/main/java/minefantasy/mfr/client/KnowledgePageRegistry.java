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
			MineFantasyKnowledgeList.getting_started.addPages(
					new EntryPageText("knowledge.getting_started.hcc"),
					new EntryPageRecipeBase(MineFantasyKnowledgeList.CARPENTER_RECIPE),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("sharp_rock")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_pick")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_hammer")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("sharp_rock-2")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_spear")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_axe")));
		}

		MineFantasyKnowledgeList.getting_started.addPages(
				new EntryPageText("knowledge.getting_started.1"),
				new EntryPageText("knowledge.getting_started.2"),
				new EntryPageText("knowledge.getting_started.carpenter"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.CARPENTER_RECIPE),
				new EntryPageText("knowledge.getting_started.fire"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.FIREPIT_RECIPE),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.DRYROCKS_RECIPE),
				new EntryPageText("knowledge.getting_started.food"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.STOVE_RECIPE),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("oven")),
				new EntryPageText("knowledge.getting_started.tanning"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("tanner")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_knife")),
				new EntryPageText("knowledge.getting_started.forging"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("anvil_stone")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("forge_stone")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_hammer")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_tongs")),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.DRYROCKS_RECIPE),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("leather_apron")),
				new EntryPageText("knowledge.getting_started.forging_bars"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName( "ingot")),
				new EntryPageText("knowledge.getting_started.flux"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("flux"), CraftingManagerAnvil.getRecipeByName("flux_1")),
				new EntryPageText("knowledge.getting_started.3"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_shears")),
				new EntryPageText("knowledge.getting_started.4"),
				new EntryPageText("knowledge.getting_started.5"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_hammer")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_tongs")),
				new EntryPageText("knowledge.getting_started.6"),
				new EntryPageText("knowledge.getting_started.upgrade"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("research_bench")),
				new EntryPageText("knowledge.getting_started.7"),
				new EntryPageText("knowledge.getting_started.10"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("clay_pot_uncooked")),
				new EntryPageSmelting(MineFantasyItems.CLAY_POT_UNCOOKED, MineFantasyItems.CLAY_POT),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("quern")),
				new EntryPageText("knowledge.getting_started.11"),
				new EntryPageText("knowledge.getting_started.12"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("salvage_basic")),
				new EntryPageText("knowledge.getting_started.13"));

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
				new EntryPageText("knowledge.advanced_parry.info"),
				new EntryPageText("knowledge.power_attack.info"),
				new EntryPageText("knowledge.dodge.info"),
				new EntryPageText("knowledge.armour.info"));

		MineFantasyKnowledgeList.craft_armour_basic.addPages(
				new EntryPageText("knowledge.craft_armour_basic.1"),
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
				new EntryPageRecipeBase(MineFantasyKnowledgeList.TIMBER_RECIPE),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.STICK_RECIPE),
				new EntryPageText("knowledge.commodities.refinedplank"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("jug_uncooked")),
				new EntryPageSmelting(MineFantasyItems.JUG_UNCOOKED, MineFantasyItems.JUG_EMPTY),
				new EntryPageRecipeBase(RecipeHelper.getMFRRecipe("jug_plant_oil")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("refined_timber")),
				new EntryPageText("knowledge.commodities.flux"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("flux"), CraftingManagerAnvil.getRecipeByName("flux_1")),
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
		MineFantasyKnowledgeList.ores.addPages(assembleOreDesc("wolframite"));
		MineFantasyKnowledgeList.ores.addPages(new EntryPageCrucible(MineFantasyKnowledgeList.wolframite_raw_alloy));
		MineFantasyKnowledgeList.ores.addPages(assembleOreDesc("mythic"));
		MineFantasyKnowledgeList.ores.addPages(new EntryPageText("knowledge.ores.2"));
		MineFantasyKnowledgeList.ores.addPages(assembleMineralDesc("clay"));
		MineFantasyKnowledgeList.ores.addPages(assembleMineralDesc("kaolinite"));
		MineFantasyKnowledgeList.ores.addPages(assembleMineralDesc("limestone"));
		MineFantasyKnowledgeList.ores.addPages(assembleMineralDesc("borax"));
		MineFantasyKnowledgeList.ores.addPages(assembleMineralDesc("nitre"));
		MineFantasyKnowledgeList.ores.addPages(assembleMineralDesc("sulfur"));

		MineFantasyKnowledgeList.plants.addPages(new EntryPageText("knowledge.plants.1"));
		MineFantasyKnowledgeList.plants.addPages(assembleImgPage("berry"));
		MineFantasyKnowledgeList.plants.addPages(new EntryPageText("knowledge.plants.2"));
		MineFantasyKnowledgeList.plants.addPages(assembleImgPage("yew"));
		MineFantasyKnowledgeList.plants.addPages(assembleImgPage("ironbark"));
		MineFantasyKnowledgeList.plants.addPages(assembleImgPage("ebony"));

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

		MineFantasyKnowledgeList.firebrick_crucible.addPages(
				assembleSimpleImgPage("fire_crucible_example", "knowledge.firebrick_crucible.1"),
				new EntryPageGrind(new ItemStack(MineFantasyItems.KAOLINITE), new ItemStack(MineFantasyItems.KAOLINITE_DUST)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fireclay")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crucible_fireclay")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebricks")),
				assembleSimpleImgPage("fire_crucible", "knowledge.firebrick_crucible.blocks"));

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
		MineFantasyKnowledgeList.smelt_bronze.addPages(new EntryPageCrucible(MineFantasyKnowledgeList.bronze_alloy));
		MineFantasyKnowledgeList.smelt_iron.addPages(new EntryPageText("knowledge.smelt_iron.2"));

		MineFantasyKnowledgeList.smelt_pig_iron.addPages(
				new EntryPageText("knowledge.smelt_pig_iron.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("prepared_iron")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("prepared_iron-2")),
				new EntryPageBlastFurnace(MineFantasyItems.PREPARED_IRON, pig_iron),
				new EntryPageText("knowledge.blast_furnace.9"));

		MineFantasyKnowledgeList.smelt_steel.addPages(
				new EntryPageText("knowledge.smelt_steel.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar_steel")));

		if (!ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.smelt_steel.addPages(new EntryPageCrucible(MineFantasyKnowledgeList.steel_alloy));
		}

		MineFantasyKnowledgeList.smelt_encrusted.addPages(
				new EntryPageText("knowledge.smelt_encrusted.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("diamond_shards")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar_encrusted")));

		MineFantasyKnowledgeList.smelt_obsidian.addPages(
				new EntryPageText("knowledge.smelt_obsidian.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("obsidian_rock")),
				new EntryPageCrucible(MineFantasyKnowledgeList.obsidian_alloy));

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

		MineFantasyKnowledgeList.smelt_dragonforged.addPages(
				new EntryPageText("knowledge.smelt_dragonforged.1"),
				new EntryPageText("knowledge.smelt_dragonforged.2"));

		MineFantasyKnowledgeList.craft_ornate.addPages(
				new EntryPageText("knowledge.craft_ornate.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("ornate_items")));

		MineFantasyKnowledgeList.craft_armour_light.addPages(
				new EntryPageText("knowledge.craft_armour_light.1"),
				new EntryPageText("knowledge.craft_armour_light.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_boots")),
				new EntryPageText("knowledge.craft_armour_light.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_boots")),
				new EntryPageText("knowledge.craft_armour_light.4"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("strong_leather_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("strong_leather_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("strong_leather_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("strong_leather_boots")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("stud_leather_helmet")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("stud_leather_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("stud_leather_leggings")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("stud_leather_boots")));

		MineFantasyKnowledgeList.craft_armour_medium.addPages(
				new EntryPageText("knowledge.craft_armour_medium.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("chain_mesh")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_chain_helmet")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_chain_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_chain_leggings")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_chain_boots")),
				new EntryPageText("knowledge.craft_armour_medium.scale"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("scale_mesh")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_scale_helmet")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_scale_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_scale_leggings")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_scale_boots")));

		MineFantasyKnowledgeList.craft_armour_heavy.addPages(
				new EntryPageText("knowledge.craft_armour_heavy.1"),
				new EntryPageText("knowledge.craft_armour_heavy.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_boots")),
				new EntryPageText("knowledge.craft_armour_heavy.splint"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("splint_mesh")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_splint_helmet")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_splint_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_splint_leggings")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_splint_boots")),
				new EntryPageText("knowledge.craft_armour_heavy.plate"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("plate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_plate_helmet")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_plate_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_plate_leggings")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_plate_boots")));

		MineFantasyKnowledgeList.coal_flux.addPages(
				new EntryPageText("knowledge.coal_flux.1"),
				new EntryPageGrind(new ItemStack(MineFantasyItems.FLUX), new ItemStack(MineFantasyItems.FLUX_POT)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("coal_flux")));

		MineFantasyKnowledgeList.big_furnace.addPages(
				assembleSimpleImgPage("furnace_example", "knowledge.big_furnace.1"),
				new EntryPageText("knowledge.big_furnace.2"),
				new EntryPageGrind(new ItemStack(MineFantasyItems.KAOLINITE), new ItemStack(MineFantasyItems.KAOLINITE_DUST)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fireclay")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fireclay_brick")),
				new EntryPageSmelting(new ItemStack(MineFantasyItems.FIRECLAY_BRICK), new ItemStack(MineFantasyItems.STRONG_BRICK)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebricks")),
				new EntryPageText("knowledge.big_furnace.heater"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("furnace_heater")),
				assembleSimpleImgPage("furnace_heater", MineFantasyBlocks.FURNACE_HEATER.getUnlocalizedName() + ".name"),
				new EntryPageText("knowledge.big_furnace.top"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("furnace_stone")),
				assembleSimpleImgPage("furnace_full", "knowledge.big_furnace.structure"));

		MineFantasyKnowledgeList.blast_furnace.addPages(
				new EntryPageText("knowledge.blast_furnace.1"),
				new EntryPageText("knowledge.blast_furnace.2"),
				new EntryPageGrind(new ItemStack(MineFantasyItems.KAOLINITE), new ItemStack(MineFantasyItems.KAOLINITE_DUST)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fireclay")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fireclay_brick")),
				new EntryPageSmelting(new ItemStack(MineFantasyItems.FIRECLAY_BRICK), new ItemStack(MineFantasyItems.STRONG_BRICK)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebricks")),
				new EntryPageText("knowledge.blast_furnace.3"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("blast_chamber")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("blast_heater")),
				new EntryPageText("knowledge.blast_furnace.4"),
				new EntryPageText("knowledge.blast_furnace.5"),
				new EntryPageImage("textures/gui/knowledge/blast_example.png", 96, 96, "knowledge.blast_furnace.6"),
				new EntryPageText("knowledge.blast_furnace.7"));

		if (ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.blast_furnace.addPages(new EntryPageText("knowledge.blast_furnace.hcc"));
		}

		MineFantasyKnowledgeList.engineering_tools.addPages(
				new EntryPageText("knowledge.engineering_tools.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_spanner")), new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("engin_anvil_tools")));

		MineFantasyKnowledgeList.engineering_components.addPages(
				new EntryPageText("knowledge.engineering_components.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bolt")),
				new EntryPageText("knowledge.engineering_components.2"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("iron_frame")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bronze_gears")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("tungsten_gears")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("iron_strut")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("steel_tube")));

		MineFantasyKnowledgeList.tungsten.addPages(
				new EntryPageText("knowledge.tungsten.1"),
				new EntryPageText("knowledge.tungsten.2"),
				new EntryPageText("knowledge.tungsten.3"),
				new EntryPageCrucible(MineFantasyKnowledgeList.wolframite_raw_alloy));

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

		MineFantasyKnowledgeList.engineering_tanner.addPages(
				new EntryPageText("knowledge.engineering_tanner.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("tanner_metal")));

		MineFantasyKnowledgeList.advanced_crucible.addPages(
				assembleSimpleImgPage("auto_crucible_example", MineFantasyBlocks.CRUCIBLE_AUTO.getUnlocalizedName() + ".name"),
				new EntryPageText("knowledge.advanced_crucible.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crucible_auto")),
				new EntryPageText("knowledge.firebrick_crucible.blocks"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebricks")),
				assembleSimpleImgPage("auto_crucible", "knowledge.basicstructure"));

		MineFantasyKnowledgeList.advanced_forge.addPages(
				new EntryPageText("knowledge.advanced_forge.1"),
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

		MineFantasyKnowledgeList.advanced_blackpowder.addPages(
				new EntryPageText("knowledge.advanced_blackpowder.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("blackpowder_advanced")));

		MineFantasyKnowledgeList.bombs.addPages(
				new EntryPageText("knowledge.bombs.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_bench")),
				new EntryPageText("knowledge.bombs.2"),
				new EntryPageImage("textures/gui/knowledge/bomb_gui_example.png", 128, 128, "knowledge.guiSubtitle"),
				new EntryPageText("knowledge.bombs.3"),
				new EntryPageText("knowledge.bombs.4"));

		MineFantasyKnowledgeList.bomb_press.addPages(
				new EntryPageText("knowledge.bomb_press.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_press")));

		MineFantasyKnowledgeList.bomb_arrow.addPages(
				new EntryPageText("knowledge.bomb_arrow.1"),
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

		MineFantasyKnowledgeList.bomb_ceramic.addPages(
				new EntryPageText("knowledge.bomb_ceramic.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_casing_uncooked")),
				new EntryPageSmelting(MineFantasyItems.BOMB_CASING_UNCOOKED, MineFantasyItems.BOMB_CASING_CERAMIC));

		MineFantasyKnowledgeList.bomb_iron.addPages(
				new EntryPageText("knowledge.bomb_iron.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bomb_casing_iron")));

		MineFantasyKnowledgeList.bomb_obsidian.addPages(
				new EntryPageText("knowledge.bomb_obsidian.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bomb_casing_obsidian")));

		MineFantasyKnowledgeList.bomb_crystal.addPages(
				new EntryPageText("knowledge.bomb_crystal.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_casing_crystal")));

		MineFantasyKnowledgeList.mine_ceramic.addPages(
				new EntryPageText("knowledge.mine_ceramic.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("mine_casing_uncooked")),
				new EntryPageSmelting(MineFantasyItems.MINE_CASING_UNCOOKED, MineFantasyItems.MINE_CASING_CERAMIC));

		MineFantasyKnowledgeList.mine_iron.addPages(
				new EntryPageText("knowledge.mine_iron.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("mine_casing_iron")));

		MineFantasyKnowledgeList.mine_obsidian.addPages(
				new EntryPageText("knowledge.mine_obsidian.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("mine_casing_obsidian")));

		MineFantasyKnowledgeList.mine_crystal.addPages(
				new EntryPageText("knowledge.mine_crystal.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("mine_casing_crystal")));

		MineFantasyKnowledgeList.bomb_fuse.addPages(
				new EntryPageText("knowledge.bomb_fuse.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_fuse")),
				new EntryPageText("knowledge.bomb_fuse.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_fuse_long")));

		MineFantasyKnowledgeList.sticky_bomb.addPages(
				new EntryPageText("knowledge.sticky_bomb.1"),
				new EntryPageText("knowledge.sticky_bomb.2"));

		MineFantasyKnowledgeList.crossbows.addPages(
				new EntryPageText("knowledge.crossbows.1"),
				new EntryPageText("knowledge.crossbows.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_bench")),
				new EntryPageText("knowledge.crossbows.3"),
				new EntryPageText("knowledge.crossbows.4"),
				new EntryPageText("knowledge.crossbows.5"),
				new EntryPageText("knowledge.crossbows.6"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_bolt")));

		MineFantasyKnowledgeList.crossbow_shafts.addPages(
				new EntryPageText("knowledge.crossbow_shafts.handle"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_handle_wood")),
				new EntryPageText("knowledge.crossbow_shafts.stock"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_stock_wood")));

		MineFantasyKnowledgeList.crossbow_heads.addPages(
				new EntryPageText("knowledge.crossbow_heads.light"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_arms_light")),
				new EntryPageText("knowledge.crossbow_heads.medium"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_arms_basic")),
				new EntryPageText("knowledge.crossbow_heads.heavy"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_arms_heavy")));

		MineFantasyKnowledgeList.crossbow_head_advanced.addPages(
				new EntryPageText("knowledge.crossbow_head_advanced.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_arms_advanced")));

		MineFantasyKnowledgeList.crossbow_shaft_advanced.addPages(
				new EntryPageText("knowledge.crossbow_shaft_advanced.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_stock_iron")));

		MineFantasyKnowledgeList.crossbow_scope.addPages(
				new EntryPageText("knowledge.crossbow_scope.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_scope")));

		MineFantasyKnowledgeList.crossbow_ammo.addPages(
				new EntryPageText("knowledge.crossbow_ammo.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_ammo")));

		MineFantasyKnowledgeList.crossbow_bayonet.addPages(
				new EntryPageText("knowledge.crossbow_bayonet.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("crossbow_bayonet")));

		MineFantasyKnowledgeList.cogwork_armour.addPages(
				new EntryPageText("knowledge.cogwork_armour.1"),
				new EntryPageText("knowledge.cogwork_armour.2"),
				new EntryPageText("knowledge.cogwork_armour.station.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("frame_block")),
				assembleSimpleImgPage("cogwork_station", "knowledge.cogwork_station"),
				new EntryPageText("knowledge.cogwork_armour.station.2"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("cogwork_pulley")),
				assembleSimpleImgPage("cogwork_station_2", "knowledge.rightclickspanner"),
				new EntryPageText("knowledge.cogwork_armour.crafting"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cogwork_shaft")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("block_cogwork_helm")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("block_cogwork_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("block_cogwork_legs")),
				assembleSimpleImgPage("cogwork_suit_craft", "knowledge.rightclickspanner"),
				new EntryPageText("knowledge.cogwork_armour.armour"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("plate_huge")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("cogwork_armour")),
				new EntryPageText("knowledge.cogwork_armour.advantage"),
				new EntryPageText("knowledge.cogwork_armour.advantage.2"),
				new EntryPageText("knowledge.cogwork_armour.advantage.3"),
				new EntryPageText("knowledge.cogwork_armour.disadvantage"),
				new EntryPageText("knowledge.cogwork_armour.disadvantage.2"),
				new EntryPageText("knowledge.cogwork_armour.removal"));

		MineFantasyKnowledgeList.composite_alloy.addPages(
				new EntryPageText("knowledge.composite_alloy.1"),
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
				new EntryPageCrucible(MineFantasyKnowledgeList.reinforced_stone_alloy));

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

		MineFantasyKnowledgeList.construction_parts.addPages(
				new EntryPageText("knowledge.construction_parts.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("timber_cut")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("timber_pane")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("hinge")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("jug_uncooked")),
				new EntryPageSmelting(MineFantasyItems.JUG_UNCOOKED, MineFantasyItems.JUG_EMPTY),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.JUG_PLANT_OIL_RECIPE),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("refined_timber")));

		if (ConfigHardcore.HCCallowRocks) {
			MineFantasyKnowledgeList.crafting_HCC_tools.addPages(
					new EntryPageText("knowledge.crafting_HCC_tools.1"),
					new EntryPageText("knowledge.crafting_HCC_tools.2"),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("sharp_rock")),
					new EntryPageText("knowledge.crafting_HCC_tools.3"),
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

		MineFantasyKnowledgeList.craft_tools.addPages(
				new EntryPageText("knowledge.craft_tools.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_pick")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_axe")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_spade")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_hoe")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_shears")));

		MineFantasyKnowledgeList.craft_advanced_tools.addPages(
				new EntryPageText("knowledge.craft_advanced_tools.1"),
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

		MineFantasyKnowledgeList.craft_crafters.addPages(
				new EntryPageText("knowledge.craft_crafters.1"),
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

		MineFantasyKnowledgeList.craft_weapons.addPages(
				new EntryPageText("knowledge.craft_weapons.1"),
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

		MineFantasyKnowledgeList.craft_advanced_weapons.addPages(
				new EntryPageText("knowledge.craft_advanced_weapons.1"),
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

		MineFantasyKnowledgeList.arrows_bodkin.addPages(
				new EntryPageText("knowledge.arrows_bodkin.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bodkin_head")));

		MineFantasyKnowledgeList.arrows_broad.addPages(
				new EntryPageText("knowledge.arrows_broad.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("broad_head")));

		MineFantasyKnowledgeList.smelt_black_steel.addPages(
				new EntryPageText("knowledge.smelt_black_steel.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("obsidian_rock")),
				new EntryPageCrucible(MineFantasyKnowledgeList.black_steel_alloy),
				new EntryPageBlastFurnace(MineFantasyItems.BLACK_STEEL_WEAK_INGOT, black));

		MineFantasyKnowledgeList.smelt_red_steel.addPages(
				new EntryPageText("knowledge.smelt_red_steel.1"),
				new EntryPageCrucible(MineFantasyKnowledgeList.red_steel_alloy),
				new EntryPageBlastFurnace(MineFantasyItems.RED_STEEL_WEAK_INGOT, red));

		MineFantasyKnowledgeList.smelt_blue_steel.addPages(
				new EntryPageText("knowledge.smelt_blue_steel.1"),
				new EntryPageCrucible(MineFantasyKnowledgeList.blue_steel_alloy),
				new EntryPageBlastFurnace(MineFantasyItems.BLUE_STEEL_WEAK_INGOT, blue));

		MineFantasyKnowledgeList.smelt_adamantium.addPages(
				new EntryPageText("knowledge.smelt_adamantium.1"),
				new EntryPageText("knowledge.smelt_adamantium.2"),
				new EntryPageCrucible(MineFantasyKnowledgeList.adamantium_alloy));

		MineFantasyKnowledgeList.smelt_mithril.addPages(
				new EntryPageText("knowledge.smelt_mithril.1"),
				new EntryPageText("knowledge.smelt_mithril.2"),
				new EntryPageCrucible(MineFantasyKnowledgeList.mithril_alloy));

		MineFantasyKnowledgeList.smelt_master.addPages(
				new EntryPageText("knowledge.smelt_master.1"),
				new EntryPageText("knowledge.smelt_master.2"),
				new EntryPageText("knowledge.smelt_master.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("trilogy_jewel")));

		MineFantasyKnowledgeList.smelt_ignotumite.addPages(
				new EntryPageText("knowledge.smelt_ignotumite.1"),
				new EntryPageCrucible(MineFantasyKnowledgeList.ignotumite_alloy));

		MineFantasyKnowledgeList.smelt_mithium.addPages(
				new EntryPageText("knowledge.smelt_mithium.1"),
				new EntryPageCrucible(MineFantasyKnowledgeList.mithium_alloy));

		MineFantasyKnowledgeList.smelt_ender.addPages(
				new EntryPageText("knowledge.smelt_ender.1"),
				new EntryPageCrucible(MineFantasyKnowledgeList.enderforge_alloy));

		MineFantasyKnowledgeList.firepit.addPages(
				new EntryPageText("knowledge.firepit.1"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.FIREPIT_RECIPE),
				new EntryPageText("knowledge.firepit.2"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.STOVE_RECIPE),
				new EntryPageText("knowledge.firepit.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("oven")),
				new EntryPageText("knowledge.firepit.4"));

		MineFantasyKnowledgeList.cooking_utensils.addPages(
				new EntryPageText("knowledge.cooking_utensils.1"),
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

		MineFantasyKnowledgeList.sausage.addPages(
				new EntryPageText("knowledge.sausage.1"),
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

		MineFantasyKnowledgeList.shepard_pie.addPages(
				new EntryPageText("knowledge.shepard_pie.1"),
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

		MineFantasyKnowledgeList.berry.addPages(assembleImgPage("berry"));

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

		MineFantasyKnowledgeList.carrot_cake.addPages(
				new EntryPageText("knowledge.carrot_cake.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake_carrot_raw")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.CAKE_CARROT_RAW), new ItemStack(MineFantasyItems.CAKE_CARROT_UNICED)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake_carrot")));

		MineFantasyKnowledgeList.chocolate_cake.addPages(
				new EntryPageText("knowledge.chocolate_cake.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("chocolate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake_choc_raw")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.CAKE_CHOC_RAW), new ItemStack(MineFantasyItems.CAKE_CHOC_UNICED)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake_chocolate")));

		MineFantasyKnowledgeList.black_forest_cake.addPages(
				new EntryPageText("knowledge.black_forest_cake.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("chocolate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake_bf_raw")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.CAKE_BF_RAW), new ItemStack(MineFantasyItems.CAKE_BF_UNICED)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cake_choc_raw")));

		MineFantasyKnowledgeList.berry_pie.addPages(
				new EntryPageText("knowledge.berry_pie.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("pie_berry_uncooked")),
				new EntryPageBaking(new ItemStack(MineFantasyItems.PIE_BERRY_UNCOOKED), new ItemStack(MineFantasyItems.PIE_BERRY_COOKED)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_BERRY_RECIPE));

		MineFantasyKnowledgeList.apple_pie.addPages(
				new EntryPageText("knowledge.apple_pie.1"),
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

		MineFantasyKnowledgeList.cheese_roll.addPages(
				new EntryPageText("knowledge.cheese_roll.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cheese_roll")));

		MineFantasyKnowledgeList.bandage.addPages(
				new EntryPageText("knowledge.bandage.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bandage_crude")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bandage_crude-2")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bandage_crude-3")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bandage_wool")));

		MineFantasyKnowledgeList.bandage_advanced.addPages(
				new EntryPageText("knowledge.bandage_advanced.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bandage_tough")));

		// MASTERY
		MineFantasyKnowledgeList.toughness.addPages(new EntryPageText("knowledge.toughness.1"));
		MineFantasyKnowledgeList.fitness.addPages(new EntryPageText("knowledge.fitness.1"));
		MineFantasyKnowledgeList.armour_pro.addPages(new EntryPageText("knowledge.armour_pro.1"));
		MineFantasyKnowledgeList.parry_pro.addPages(new EntryPageText("knowledge.parry_pro.1"));

		MineFantasyKnowledgeList.counter_attack.addPages(
				new EntryPageText("knowledge.counter_attack.1"),
				new EntryPageText("knowledge.counter_attack.2"));

		MineFantasyKnowledgeList.auto_parry.addPages(new EntryPageText("knowledge.auto_parry.1"));
		MineFantasyKnowledgeList.first_aid.addPages(new EntryPageText("knowledge.first_aid.1"));
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

	private static EntryPage[] assembleOreDesc(String orename) {
		return new EntryPage[] {new EntryPageImage("textures/gui/knowledge/image/" + orename + ".png", 96, 96,
				"knowledge.ores." + orename)};
	}

	private static EntryPage[] assembleMineralDesc(String orename) {
		return new EntryPage[] {new EntryPageImage("textures/gui/knowledge/image/" + orename + ".png", 96, 96,
				"knowledge.minerals." + orename)};
	}

	private static EntryPage[] assembleImgPage(String name) {
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
