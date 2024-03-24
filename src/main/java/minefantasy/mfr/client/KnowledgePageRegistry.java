package minefantasy.mfr.client;

import minefantasy.mfr.client.knowledge.EntryPage;
import minefantasy.mfr.client.knowledge.EntryPageBlastFurnace;
import minefantasy.mfr.client.knowledge.EntryPageCrucible;
import minefantasy.mfr.client.knowledge.EntryPageGrind;
import minefantasy.mfr.client.knowledge.EntryPageImage;
import minefantasy.mfr.client.knowledge.EntryPageRecipeAnvil;
import minefantasy.mfr.client.knowledge.EntryPageRecipeBase;
import minefantasy.mfr.client.knowledge.EntryPageRecipeBloom;
import minefantasy.mfr.client.knowledge.EntryPageRecipeCarpenter;
import minefantasy.mfr.client.knowledge.EntryPageRecipeKitchenBench;
import minefantasy.mfr.client.knowledge.EntryPageRecipeTanner;
import minefantasy.mfr.client.knowledge.EntryPageRoast;
import minefantasy.mfr.client.knowledge.EntryPageSmelting;
import minefantasy.mfr.client.knowledge.EntryPageText;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyKnowledgeList;
import minefantasy.mfr.recipe.CraftingManagerAlloy;
import minefantasy.mfr.recipe.CraftingManagerAnvil;
import minefantasy.mfr.recipe.CraftingManagerBlastFurnace;
import minefantasy.mfr.recipe.CraftingManagerBloomery;
import minefantasy.mfr.recipe.CraftingManagerCarpenter;
import minefantasy.mfr.recipe.CraftingManagerKitchenBench;
import minefantasy.mfr.recipe.CraftingManagerQuern;
import minefantasy.mfr.recipe.CraftingManagerRoast;
import minefantasy.mfr.recipe.CraftingManagerTanner;
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

		if (ConfigHardcore.HCCallowRocks) {
			MineFantasyKnowledgeList.getting_started.addPages(
					new EntryPageText("knowledge.getting_started.hcc"),
					new EntryPageRecipeBase(MineFantasyKnowledgeList.CARPENTER_RECIPE),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("sharp_rock", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_pick", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_hammer", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("sharp_rock-2", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_spear", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_axe", false)));
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
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("oven", false)),
				new EntryPageText("knowledge.getting_started.tanning"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("tanner", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_knife", false)),
				new EntryPageText("knowledge.getting_started.forging"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("anvil_stone", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("forge_stone", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_hammer", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_tongs", false)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.DRYROCKS_RECIPE),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("leather_apron", false)),
				new EntryPageText("knowledge.getting_started.forging_bars"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName( "ingot", false)),
				new EntryPageText("knowledge.getting_started.flux"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("flux", false)),
				new EntryPageText("knowledge.getting_started.3"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_shears", false)),
				new EntryPageText("knowledge.getting_started.4"),
				new EntryPageText("knowledge.getting_started.5"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_hammer", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_tongs", false)),
				new EntryPageText("knowledge.getting_started.6"),
				new EntryPageText("knowledge.getting_started.upgrade"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("research_bench", false)),
				new EntryPageText("knowledge.getting_started.7"),
				new EntryPageText("knowledge.getting_started.10"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("clay_pot_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("clay_pot", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("quern", false)),
				new EntryPageText("knowledge.getting_started.11"),
				new EntryPageText("knowledge.getting_started.12"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("salvage_basic", false)),
				new EntryPageText("knowledge.getting_started.13"));

		MineFantasyKnowledgeList.research.addPages(
				new EntryPageText("knowledge.research.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("research_bench", false)),
				new EntryPageText("knowledge.research.2"));

		//IDKH to make it look not ugly without significant code changes
		MineFantasyKnowledgeList.talisman.addPages(new EntryPageText("knowledge.talisman.1"));
		if (!ConfigHardcore.HCCRemoveTalismansCraft) {
			MineFantasyKnowledgeList.talisman.addPages(new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("talisman_lesser", false)));
		}

		if (!ConfigHardcore.HCCRemoveBooksCraft) {
			MineFantasyKnowledgeList.talisman.addPages(
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_artisanry", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_construction", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_provisioning", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_engineering", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_combat", false)));
		}

		MineFantasyKnowledgeList.talisman.addPages(new EntryPageText("knowledge.talisman.2"));

		if (!ConfigHardcore.HCCRemoveTalismansCraft) {
			MineFantasyKnowledgeList.talisman.addPages(new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("talisman_greater", false)));
		}

		if (!ConfigHardcore.HCCRemoveBooksCraft) {
			MineFantasyKnowledgeList.talisman.addPages(
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_artisanry_max", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_construction_max", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_provisioning_max", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_engineering_max", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("skillbook_combat_max", false)));
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
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("hide_helmet", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("hide_chestplate", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("hide_leggings", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("hide_boots", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_helmet", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_chestplate", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_leggings", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_boots", false)));

		MineFantasyKnowledgeList.carpenter.addPages(
				new EntryPageText("knowledge.carpenter.1"),
				new EntryPageRecipeBase(RecipeHelper.getMFRRecipe("carpenter")));

		MineFantasyKnowledgeList.salvage.addPages(
				new EntryPageText("knowledge.salvage.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("salvage_basic", false)),
				new EntryPageText("knowledge.salvage.2"));

		MineFantasyKnowledgeList.commodities.addPages(
				new EntryPageText("knowledge.commodities.1"),
				new EntryPageText("knowledge.commodities.plank"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.TIMBER_RECIPE),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.STICK_RECIPE),
				new EntryPageText("knowledge.commodities.refinedplank"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("jug_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("jug_empty", false)),
				new EntryPageRecipeBase(RecipeHelper.getMFRRecipe("jug_plant_oil")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("refined_timber", false)),
				new EntryPageText("knowledge.commodities.flux"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("flux", false)),
				new EntryPageText("knowledge.bar.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("ingot", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar", false)),
				new EntryPageText("knowledge.commodities.hunks"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("metal_hunk", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar_hunks", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("plate", false)),
				new EntryPageText("knowledge.commodities.nail"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("nail", false)),
				new EntryPageText("knowledge.commodities.rivet"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("rivet", false)),
				new EntryPageText("knowledge.commodities.leatherstrip"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("leather_strip", false)),
				new EntryPageText("knowledge.commodities.thread"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("thread", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("string", false)),
				new EntryPageText("knowledge.commodities.bucket"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bucket", false)));

		MineFantasyKnowledgeList.dust.addPages(
				new EntryPageText("knowledge.dust.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("clay_pot_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("clay_pot", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("quern", false)),
				new EntryPageText("knowledge.dust.quern"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("coal_dust", false)),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("kaolinite_dust", false)),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("flour", false)),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("coca_powder", false)),
				new EntryPageText("knowledge.dust.icing"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("standard_spoon", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("icing", false)));

		MineFantasyKnowledgeList.ores.addPages(
				new EntryPageText("knowledge.ores.1"),
				new EntryPageText(""));

		MineFantasyKnowledgeList.ores.addPages(assembleOreDescHC("copper", MineFantasyBlocks.COPPER_ORE, MineFantasyItems.COPPER_INGOT));
		MineFantasyKnowledgeList.ores.addPages(assembleOreDescHC("tin", MineFantasyBlocks.TIN_ORE, MineFantasyItems.TIN_INGOT));
		MineFantasyKnowledgeList.ores.addPages(assembleOreDescHC("silver", MineFantasyBlocks.SILVER_ORE, MineFantasyItems.SILVER_INGOT));
		MineFantasyKnowledgeList.ores.addPages(assembleOreDesc("wolframite"));
		MineFantasyKnowledgeList.ores.addPages(new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName("tungsten_bar", false)));
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
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("chimney_stone_thin", false)),
				new EntryPageText("knowledge.chimney.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("chimney_stone_wide", false)),
				new EntryPageText("knowledge.chimney.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("chimney_stone_extractor_wide", false)),
				new EntryPageText("knowledge.chimney.pipe"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("chimney_pipe_thin", false)),
				assembleSimpleImgPage("smoke_pipe_example", "knowledge.chimney.pipe.2"));

		MineFantasyKnowledgeList.tanning.addPages(
				new EntryPageText("knowledge.tanning.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("tanner", false)),
				new EntryPageText("knowledge.tanning.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("tanner_refined", false)),
				new EntryPageRecipeTanner(CraftingManagerTanner.getRecipesByName("leather_small", "leather_medium", "leather_large")),
				new EntryPageRecipeTanner(CraftingManagerTanner.getRecipeByName("leather_strip", false)));

		MineFantasyKnowledgeList.bloomery.addPages(
				new EntryPageText("knowledge.bloomery.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bloomery", false)),
				new EntryPageText("knowledge.bloomery.2"),
				new EntryPageText("knowledge.bloomery.3"));

		MineFantasyKnowledgeList.crucible.addPages(
				assembleSimpleImgPage("crucible_example", "knowledge.crucible.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crucible_stone", false)),
				new EntryPageText("knowledge.crucible.2"));

		if (ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.crucible.addPages(
					new EntryPageText("knowledge.crucible.hcc"),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("ingot_mould_uncooked", false)),
					new EntryPageRoast(CraftingManagerRoast.getRecipeByName("ingot_mould", false)));
		}

		MineFantasyKnowledgeList.firebrick_crucible.addPages(
				assembleSimpleImgPage("fire_crucible_example", "knowledge.firebrick_crucible.1"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("kaolinite_dust", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fireclay", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crucible_fireclay", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebricks", false)),
				assembleSimpleImgPage("fire_crucible", "knowledge.firebrick_crucible.blocks"));

		MineFantasyKnowledgeList.bar.addPages(
				new EntryPageText("knowledge.bar.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar_hunks", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("ingot", false)));

		MineFantasyKnowledgeList.smelt_copper.addPages(new EntryPageText("knowledge.smelt_copper.1"));
		MineFantasyKnowledgeList.smelt_bronze.addPages(new EntryPageText("knowledge.smelt_bronze.1"));

		if (ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.smelt_iron.addPages(new EntryPageText("knowledge.smelt_iron.1"));
			MineFantasyKnowledgeList.smelt_copper.addPages(new EntryPageRecipeBloom(CraftingManagerBloomery.getRecipeByName("copper_bar", false)));
			MineFantasyKnowledgeList.smelt_bronze.addPages(new EntryPageRecipeBloom(CraftingManagerBloomery.getRecipeByName("tin_bar", false)));
		} else {
			MineFantasyKnowledgeList.smelt_copper.addPages(new EntryPageSmelting(new ItemStack(MineFantasyBlocks.COPPER_ORE), new ItemStack(MineFantasyItems.COPPER_INGOT)));
			MineFantasyKnowledgeList.smelt_bronze.addPages(new EntryPageSmelting(new ItemStack(MineFantasyBlocks.TIN_ORE), new ItemStack(MineFantasyItems.TIN_INGOT)));
		}
		MineFantasyKnowledgeList.smelt_bronze.addPages(new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName("bronze_bar", false)));
		MineFantasyKnowledgeList.smelt_iron.addPages(new EntryPageText("knowledge.smelt_iron.2"));

		MineFantasyKnowledgeList.smelt_pig_iron.addPages(
				new EntryPageText("knowledge.smelt_pig_iron.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("prepared_iron", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("prepared_iron-2", false)),
				new EntryPageBlastFurnace(CraftingManagerBlastFurnace.getRecipeByName("pig_iron_bar", false)),
				new EntryPageText("knowledge.blast_furnace.9"));

		MineFantasyKnowledgeList.smelt_steel.addPages(
				new EntryPageText("knowledge.smelt_steel.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar_steel", false)));

		if (!ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.smelt_steel.addPages(new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName("steel_bar", false)));
		}

		MineFantasyKnowledgeList.smelt_encrusted.addPages(
				new EntryPageText("knowledge.smelt_encrusted.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("diamond_shards", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar_encrusted", false)));

		MineFantasyKnowledgeList.smelt_obsidian.addPages(
				new EntryPageText("knowledge.smelt_obsidian.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("obsidian_rock", false)),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName("obsidian_bar", false)));

		if (ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.smelt_iron.addPages(
					new EntryPageRecipeBloom(CraftingManagerBloomery.getRecipeByName("iron_bar", false)));
		} else {
			MineFantasyKnowledgeList.smelt_iron
					.addPages(new EntryPageSmelting(new ItemStack(Blocks.IRON_ORE), new ItemStack(Items.IRON_INGOT)));
		}

		MineFantasyKnowledgeList.apron.addPages(
				new EntryPageText("knowledge.apron.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("leather_apron", false)));

		MineFantasyKnowledgeList.bellows.addPages(
				new EntryPageText("knowledge.bellows.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bellows", false)));

		MineFantasyKnowledgeList.trough.addPages(
				new EntryPageText("knowledge.trough.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("trough_wood_scrap", false)),
				new EntryPageText("knowledge.trough.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("trough_wood", false)));

		MineFantasyKnowledgeList.forge.addPages(
				assembleSimpleImgPage("forge_example", "knowledge.forge.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("forge_stone", false)),
				new EntryPageText("knowledge.forge.2"),
				new EntryPageText("knowledge.forge.3"));

		MineFantasyKnowledgeList.anvil.addPages(
				assembleSimpleImgPage("smithy_example", "knowledge.anvil.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("anvil_stone", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("anvil_bronze", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("anvil_iron", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("anvil_red_steel", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("anvil_black_steel", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("anvil_blue_steel", false)),
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
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("ornate_items", false)));

		MineFantasyKnowledgeList.craft_armour_light.addPages(
				new EntryPageText("knowledge.craft_armour_light.1"),
				new EntryPageText("knowledge.craft_armour_light.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_helmet", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_chestplate", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_leggings", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rough_leather_boots", false)),
				new EntryPageText("knowledge.craft_armour_light.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_helmet", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_chestplate", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_leggings", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_boots", false)),
				new EntryPageText("knowledge.craft_armour_light.4"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("strong_leather_helmet", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("strong_leather_chestplate", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("strong_leather_leggings", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("strong_leather_boots", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("stud_leather_helmet", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("stud_leather_chestplate", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("stud_leather_leggings", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("stud_leather_boots", false)));

		MineFantasyKnowledgeList.craft_armour_medium.addPages(
				new EntryPageText("knowledge.craft_armour_medium.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("chain_mesh", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_chain_helmet", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_chain_chestplate", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_chain_leggings", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_chain_boots", false)),
				new EntryPageText("knowledge.craft_armour_medium.scale"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("scale_mesh", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_scale_helmet", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_scale_chestplate", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_scale_leggings", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_scale_boots", false)));

		MineFantasyKnowledgeList.craft_armour_heavy.addPages(
				new EntryPageText("knowledge.craft_armour_heavy.1"),
				new EntryPageText("knowledge.craft_armour_heavy.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_helmet", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_chestplate", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_leggings", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("padded_boots", false)),
				new EntryPageText("knowledge.craft_armour_heavy.splint"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("splint_mesh", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_splint_helmet", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_splint_chestplate", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_splint_leggings", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_splint_boots", false)),
				new EntryPageText("knowledge.craft_armour_heavy.plate"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("plate", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_plate_helmet", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_plate_chestplate", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_plate_leggings", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_plate_boots", false)));

		MineFantasyKnowledgeList.coal_flux.addPages(
				new EntryPageText("knowledge.coal_flux.1"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("flux_pot", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("coal_flux", false)));

		MineFantasyKnowledgeList.big_furnace.addPages(
				assembleSimpleImgPage("furnace_example", "knowledge.big_furnace.1"),
				new EntryPageText("knowledge.big_furnace.2"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("kaolinite_dust", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fireclay", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fireclay_brick", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("strong_brick", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebricks", false)),
				new EntryPageText("knowledge.big_furnace.heater"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("furnace_heater", false)),
				assembleSimpleImgPage("furnace_heater", MineFantasyBlocks.FURNACE_HEATER.getTranslationKey() + ".name"),
				new EntryPageText("knowledge.big_furnace.top"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("furnace_stone", false)),
				assembleSimpleImgPage("furnace_full", "knowledge.big_furnace.structure"));

		MineFantasyKnowledgeList.blast_furnace.addPages(
				new EntryPageText("knowledge.blast_furnace.1"),
				new EntryPageText("knowledge.blast_furnace.2"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("kaolinite_dust", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fireclay", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fireclay_brick", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("strong_brick", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebricks", false)),
				new EntryPageText("knowledge.blast_furnace.3"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("blast_chamber", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("blast_heater", false)),
				new EntryPageText("knowledge.blast_furnace.4"),
				new EntryPageText("knowledge.blast_furnace.5"),
				new EntryPageImage("textures/gui/knowledge/blast_example.png", 96, 96, "knowledge.blast_furnace.6"),
				new EntryPageText("knowledge.blast_furnace.7"));

		if (ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.blast_furnace.addPages(new EntryPageText("knowledge.blast_furnace.hcc"));
		}

		MineFantasyKnowledgeList.engineering_tools.addPages(
				new EntryPageText("knowledge.engineering_tools.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_spanner", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("engin_anvil_tools", false)));

		MineFantasyKnowledgeList.engineering_components.addPages(
				new EntryPageText("knowledge.engineering_components.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bolt", false)),
				new EntryPageText("knowledge.engineering_components.2"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("iron_frame", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bronze_gears", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("tungsten_gears", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("iron_strut", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("steel_tube", false)));

		MineFantasyKnowledgeList.tungsten.addPages(
				new EntryPageText("knowledge.tungsten.1"),
				new EntryPageText("knowledge.tungsten.2"),
				new EntryPageText("knowledge.tungsten.3"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName("tungsten_bar", false)));

		MineFantasyKnowledgeList.climber.addPages(
				new EntryPageText("knowledge.climber.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("climbing_pick_basic", false)),
				new EntryPageText("knowledge.climber.2"));

		MineFantasyKnowledgeList.spyglass.addPages(
				new EntryPageText("knowledge.spyglass.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("spyglass", false)));

		MineFantasyKnowledgeList.parachute.addPages(
				new EntryPageText("knowledge.parachute.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("parachute", false)));

		MineFantasyKnowledgeList.syringe.addPages(
				new EntryPageText("knowledge.syringe.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("syringe_empty", false)));

		MineFantasyKnowledgeList.engineering_tanner.addPages(
				new EntryPageText("knowledge.engineering_tanner.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("tanner_metal", false)));

		MineFantasyKnowledgeList.advanced_crucible.addPages(
				assembleSimpleImgPage("auto_crucible_example", MineFantasyBlocks.CRUCIBLE_AUTO.getTranslationKey() + ".name"),
				new EntryPageText("knowledge.advanced_crucible.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crucible_auto", false)),
				new EntryPageText("knowledge.firebrick_crucible.blocks"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebricks", false)),
				assembleSimpleImgPage("auto_crucible", "knowledge.basicstructure"));

		MineFantasyKnowledgeList.advanced_forge.addPages(
				new EntryPageText("knowledge.advanced_forge.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("forge_metal", false)));

		MineFantasyKnowledgeList.coke.addPages(
				new EntryPageText("knowledge.coke.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("prepared_coal", false)),
				new EntryPageSmelting(MineFantasyItems.PREPARED_COAL, MineFantasyItems.COKE));

		MineFantasyKnowledgeList.blackpowder.addPages(
				new EntryPageText("knowledge.blackpowder.1"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("coal_dust", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("blackpowder", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_crude", false)));

		MineFantasyKnowledgeList.advanced_blackpowder.addPages(
				new EntryPageText("knowledge.advanced_blackpowder.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("blackpowder_advanced", false)));

		MineFantasyKnowledgeList.bombs.addPages(
				new EntryPageText("knowledge.bombs.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_bench", false)),
				new EntryPageText("knowledge.bombs.2"),
				new EntryPageImage("textures/gui/knowledge/bomb_gui_example.png", 128, 128, "knowledge.guiSubtitle"),
				new EntryPageText("knowledge.bombs.3"),
				new EntryPageText("knowledge.bombs.4"));

		MineFantasyKnowledgeList.bomb_press.addPages(
				new EntryPageText("knowledge.bomb_press.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_press", false)));

		MineFantasyKnowledgeList.bomb_arrow.addPages(
				new EntryPageText("knowledge.bomb_arrow.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bomb_casing_arrow", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bomb_casing_bolt", false)));

		MineFantasyKnowledgeList.shrapnel.addPages(
				new EntryPageText("knowledge.shrapnel.1"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("shrapnel", false)));

		MineFantasyKnowledgeList.firebomb.addPages(
				new EntryPageText("knowledge.firebomb.1"),
				new EntryPageText("knowledge.firebomb.2"),
				new EntryPageText("knowledge.firebomb.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("magma_cream_refined", false)));

		MineFantasyKnowledgeList.bomb_ceramic.addPages(
				new EntryPageText("knowledge.bomb_ceramic.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_casing_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("bomb_casing_ceramic", false)));

		MineFantasyKnowledgeList.bomb_iron.addPages(
				new EntryPageText("knowledge.bomb_iron.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bomb_casing_iron", false)));

		MineFantasyKnowledgeList.bomb_obsidian.addPages(
				new EntryPageText("knowledge.bomb_obsidian.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bomb_casing_obsidian", false)));

		MineFantasyKnowledgeList.bomb_crystal.addPages(
				new EntryPageText("knowledge.bomb_crystal.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_casing_crystal", false)));

		MineFantasyKnowledgeList.mine_ceramic.addPages(
				new EntryPageText("knowledge.mine_ceramic.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("mine_casing_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("mine_casing_ceramic", false)));

		MineFantasyKnowledgeList.mine_iron.addPages(
				new EntryPageText("knowledge.mine_iron.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("mine_casing_iron", false)));

		MineFantasyKnowledgeList.mine_obsidian.addPages(
				new EntryPageText("knowledge.mine_obsidian.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("mine_casing_obsidian", false)));

		MineFantasyKnowledgeList.mine_crystal.addPages(
				new EntryPageText("knowledge.mine_crystal.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("mine_casing_crystal", false)));

		MineFantasyKnowledgeList.bomb_fuse.addPages(
				new EntryPageText("knowledge.bomb_fuse.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_fuse", false)),
				new EntryPageText("knowledge.bomb_fuse.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bomb_fuse_long", false)));

		MineFantasyKnowledgeList.sticky_bomb.addPages(
				new EntryPageText("knowledge.sticky_bomb.1"),
				new EntryPageText("knowledge.sticky_bomb.2"));

		MineFantasyKnowledgeList.crossbows.addPages(
				new EntryPageText("knowledge.crossbows.1"),
				new EntryPageText("knowledge.crossbows.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_bench", false)),
				new EntryPageText("knowledge.crossbows.3"),
				new EntryPageText("knowledge.crossbows.4"),
				new EntryPageText("knowledge.crossbows.5"),
				new EntryPageText("knowledge.crossbows.6"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_bolt", false)));

		MineFantasyKnowledgeList.crossbow_shafts.addPages(
				new EntryPageText("knowledge.crossbow_shafts.handle"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_handle_wood", false)),
				new EntryPageText("knowledge.crossbow_shafts.stock"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_stock_wood", false)));

		MineFantasyKnowledgeList.crossbow_heads.addPages(
				new EntryPageText("knowledge.crossbow_heads.light"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_arms_light", false)),
				new EntryPageText("knowledge.crossbow_heads.medium"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_arms_basic", false)),
				new EntryPageText("knowledge.crossbow_heads.heavy"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_arms_heavy", false)));

		MineFantasyKnowledgeList.crossbow_head_advanced.addPages(
				new EntryPageText("knowledge.crossbow_head_advanced.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_arms_advanced", false)));

		MineFantasyKnowledgeList.crossbow_shaft_advanced.addPages(
				new EntryPageText("knowledge.crossbow_shaft_advanced.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_stock_iron", false)));

		MineFantasyKnowledgeList.crossbow_scope.addPages(
				new EntryPageText("knowledge.crossbow_scope.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_scope", false)));

		MineFantasyKnowledgeList.crossbow_ammo.addPages(
				new EntryPageText("knowledge.crossbow_ammo.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crossbow_ammo", false)));

		MineFantasyKnowledgeList.crossbow_bayonet.addPages(
				new EntryPageText("knowledge.crossbow_bayonet.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("crossbow_bayonet", false)));

		MineFantasyKnowledgeList.cogwork_armour.addPages(
				new EntryPageText("knowledge.cogwork_armour.1"),
				new EntryPageText("knowledge.cogwork_armour.2"),
				new EntryPageText("knowledge.cogwork_armour.station.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("frame_block", false)),
				assembleSimpleImgPage("cogwork_station", "knowledge.cogwork_station"),
				new EntryPageText("knowledge.cogwork_armour.station.2"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("cogwork_pulley", false)),
				assembleSimpleImgPage("cogwork_station_2", "knowledge.rightclickspanner"),
				new EntryPageText("knowledge.cogwork_armour.crafting"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("cogwork_shaft", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("block_cogwork_helm", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("block_cogwork_chestplate", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("block_cogwork_legs", false)),
				assembleSimpleImgPage("cogwork_suit_craft", "knowledge.rightclickspanner"),
				new EntryPageText("knowledge.cogwork_armour.armour"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("plate_huge", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("cogwork_armour", false)),
				new EntryPageText("knowledge.cogwork_armour.advantage"),
				new EntryPageText("knowledge.cogwork_armour.advantage.2"),
				new EntryPageText("knowledge.cogwork_armour.advantage.3"),
				new EntryPageText("knowledge.cogwork_armour.disadvantage"),
				new EntryPageText("knowledge.cogwork_armour.disadvantage.2"),
				new EntryPageText("knowledge.cogwork_armour.removal"));

		MineFantasyKnowledgeList.composite_alloy.addPages(
				new EntryPageText("knowledge.composite_alloy.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bar_composite_alloy", false)));

		MineFantasyKnowledgeList.repair_basic.addPages(
				new EntryPageText("knowledge.repair_basic.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("repair_kit_basic", false)));

		MineFantasyKnowledgeList.repair_advanced.addPages(
				new EntryPageText("knowledge.repair_advanced.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("repair_kit_advanced", false)));

		MineFantasyKnowledgeList.repair_ornate.addPages(
				new EntryPageText("knowledge.repair_ornate.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("repair_kit_ornate", false)));

		MineFantasyKnowledgeList.refined_planks.addPages(
				new EntryPageText("knowledge.refined_planks.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("nailed_planks", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("refined_planks", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("nailed_planks_stairs", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("refined_planks_stairs", false)));

		MineFantasyKnowledgeList.reinforced_stone.addPages(
				new EntryPageText("knowledge.reinforced_stone.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("obsidian_rock", false)),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName("reinforced_stone", false)));

		MineFantasyKnowledgeList.brickworks.addPages(
				new EntryPageText("knowledge.brickworks.1"),
				new EntryPageRecipeBase(RecipeHelper.getMFRRecipe("cobble_brick"),
						RecipeHelper.getMFRRecipe("reinforced_stone_bricks"),
						RecipeHelper.getMFRRecipe("mud_brick")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebricks", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("firebrick_stairs", false)));

		MineFantasyKnowledgeList.clay_wall.addPages(
				new EntryPageText("knowledge.clay_wall.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("clay_wall", false)));

		MineFantasyKnowledgeList.glass.addPages(
				new EntryPageText("knowledge.glass.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("framed_glass", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("window", false)));

		MineFantasyKnowledgeList.thatch.addPages(
				new EntryPageText("knowledge.thatch.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("thatch", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("thatch_stairs", false)));

		MineFantasyKnowledgeList.bars.addPages(
				new EntryPageText("knowledge.bars.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipesByName("bronze_bars", "iron_bars",
						"steel_bars", "black_steel_bars", "red_steel_bars", "blue_steel_bars")));

		MineFantasyKnowledgeList.paint_brush.addPages(
				new EntryPageText("knowledge.paint_brush.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("paint_brush", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("refined_timber_brush", false)));

		MineFantasyKnowledgeList.decorated_stone.addPages(
				new EntryPageText("knowledge.decorated_stone.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("reinforced_stone_framed", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("reinforced_stone_framed_iron", false)));

		//        KnowledgeListMFR.bed_roll.addPages(new EntryPageText("knowledge.bed_roll.1"),
		//                new EntryPageRecipeCarpenter(KnowledgeListMFR.bedrollR));

		MineFantasyKnowledgeList.tool_rack.addPages(
				new EntryPageText("knowledge.tool_rack.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("rack_wood", false)),
				new EntryPageText("knowledge.tool_rack.rules"));

		MineFantasyKnowledgeList.food_box.addPages(
				new EntryPageText("knowledge.food_box.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("food_box_basic", false)),
				new EntryPageText("knowledge.ammo_box.2"));

		MineFantasyKnowledgeList.ammo_box.addPages(
				new EntryPageText("knowledge.ammo_box.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("ammo_box_basic", false)),
				new EntryPageText("knowledge.ammo_box.2"));

		MineFantasyKnowledgeList.big_box.addPages(
				new EntryPageText("knowledge.big_box.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("crate_basic", false)),
				new EntryPageText("knowledge.ammo_box.2"));

		MineFantasyKnowledgeList.construction_parts.addPages(
				new EntryPageText("knowledge.construction_parts.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("timber_cut", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("timber_pane", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("hinge", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("jug_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("jug_empty", false)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.JUG_PLANT_OIL_RECIPE),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("refined_timber", false)));

		if (ConfigHardcore.HCCallowRocks) {
			MineFantasyKnowledgeList.crafting_HCC_tools.addPages(
					new EntryPageText("knowledge.crafting_HCC_tools.1"),
					new EntryPageText("knowledge.crafting_HCC_tools.2"),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("sharp_rock", false)),
					new EntryPageText("knowledge.crafting_HCC_tools.3"),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_pick", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_spade", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_axe", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_hoe", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_knife", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_hammer", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_tongs", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bone_needle", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_sword", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_mace", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_waraxe", false)),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_spear", false)));
		}

		MineFantasyKnowledgeList.craft_tools.addPages(
				new EntryPageText("knowledge.craft_tools.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_pick", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_axe", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_spade", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_hoe", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_shears", false)));

		MineFantasyKnowledgeList.craft_advanced_tools.addPages(
				new EntryPageText("knowledge.craft_advanced_tools.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_handpick", false)),
				new EntryPageText("knowledge.heavy_pick.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_heavy_pick", false)),
				new EntryPageText("knowledge.trow.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_trow", false)),
				new EntryPageText("knowledge.heavy_shovel.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_heavy_shovel", false)),
				new EntryPageText("knowledge.scythe.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_scythe", false)),
				new EntryPageText("knowledge.mattock.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_mattock", false)),
				new EntryPageText("knowledge.mattock.use"),
				new EntryPageText("knowledge.lumber.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_lumber", false)));

		MineFantasyKnowledgeList.firemaker.addPages(
				new EntryPageText("knowledge.dryrocks.info"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.DRYROCKS_RECIPE),
				new EntryPageText("knowledge.tinderbox.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("tinderbox", false)),
				new EntryPageText("knowledge.flintsteel.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("flint_and_steel", false)));

		MineFantasyKnowledgeList.craft_crafters.addPages(
				new EntryPageText("knowledge.craft_crafters.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_hammer", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_hammer", false)),
				new EntryPageText(""),
				new EntryPageText("knowledge.heavy_hammer.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_heavy_hammer", false)),
				new EntryPageText("knowledge.tongs.info"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_tongs", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_tongs", false)),
				new EntryPageText(""),
				new EntryPageText("knowledge.knife.info"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("stone_knife", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_knife", false)),
				new EntryPageText(""),
				new EntryPageText("knowledge.saw.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_saw", false)),
				new EntryPageText("knowledge.needle.info"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bone_needle", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_needle", false)),
				new EntryPageText(""),
				new EntryPageText("knowledge.mallet.info"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("standard_mallet", false)),
				new EntryPageText("knowledge.spoon.info"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("standard_spoon", false)));

		MineFantasyKnowledgeList.craft_weapons.addPages(
				new EntryPageText("knowledge.craft_weapons.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_dagger", false)),
				new EntryPageText("knowledge.sword.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_sword", false)),
				new EntryPageText("knowledge.waraxe.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_waraxe", false)),
				new EntryPageText("knowledge.mace.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_mace", false)),
				new EntryPageText("knowledge.spear.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_spear", false)),
				new EntryPageText("knowledge.bow.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_bow", false)));

		MineFantasyKnowledgeList.craft_advanced_weapons.addPages(
				new EntryPageText("knowledge.craft_advanced_weapons.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_katana", false)),
				new EntryPageText("knowledge.greatsword.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_greatsword", false)),
				new EntryPageText("knowledge.battleaxe.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_battleaxe", false)),
				new EntryPageText("knowledge.warhammer.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_warhammer", false)),
				new EntryPageText("knowledge.halbeard.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_halbeard", false)),
				new EntryPageText("knowledge.lance.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("standard_lance", false)));

		MineFantasyKnowledgeList.arrows.addPages(
				new EntryPageText("knowledge.arrows.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fletching", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("fletching-2", false)),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("arrowhead", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipesByName(
						"standard_arrow",
						"standard_arrow_broad",
						"standard_arrow_bodkin")));

		MineFantasyKnowledgeList.arrows_bodkin.addPages(
				new EntryPageText("knowledge.arrows_bodkin.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("bodkin_head", false)));

		MineFantasyKnowledgeList.arrows_broad.addPages(
				new EntryPageText("knowledge.arrows_broad.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("broad_head", false)));

		MineFantasyKnowledgeList.smelt_black_steel.addPages(
				new EntryPageText("knowledge.smelt_black_steel.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("obsidian_rock", false)),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName("black_steel_weak_ingot", false)),
				new EntryPageBlastFurnace(CraftingManagerBlastFurnace.getRecipeByName("black_steel_bar", false)));

		MineFantasyKnowledgeList.smelt_red_steel.addPages(
				new EntryPageText("knowledge.smelt_red_steel.1"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName("red_steel_weak_ingot", false)),
				new EntryPageBlastFurnace(CraftingManagerBlastFurnace.getRecipeByName("red_steel_bar", false)));

		MineFantasyKnowledgeList.smelt_blue_steel.addPages(
				new EntryPageText("knowledge.smelt_blue_steel.1"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName("blue_steel_weak_ingot", false)),
				new EntryPageBlastFurnace(CraftingManagerBlastFurnace.getRecipeByName("blue_steel_bar", false)));

		MineFantasyKnowledgeList.smelt_adamantium.addPages(
				new EntryPageText("knowledge.smelt_adamantium.1"),
				new EntryPageText("knowledge.smelt_adamantium.2"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName("adamantium_bar", false)));

		MineFantasyKnowledgeList.smelt_mithril.addPages(
				new EntryPageText("knowledge.smelt_mithril.1"),
				new EntryPageText("knowledge.smelt_mithril.2"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName("mithril_bar", false)));

		MineFantasyKnowledgeList.smelt_master.addPages(
				new EntryPageText("knowledge.smelt_master.1"),
				new EntryPageText("knowledge.smelt_master.2"),
				new EntryPageText("knowledge.smelt_master.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("trilogy_jewel", false)));

		MineFantasyKnowledgeList.smelt_ignotumite.addPages(
				new EntryPageText("knowledge.smelt_ignotumite.1"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName("ignotumite_bar", false)));

		MineFantasyKnowledgeList.smelt_mithium.addPages(
				new EntryPageText("knowledge.smelt_mithium.1"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName("mithium_bar", false)));

		MineFantasyKnowledgeList.smelt_ender.addPages(
				new EntryPageText("knowledge.smelt_ender.1"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName("ender_bar", false)));

		MineFantasyKnowledgeList.firepit.addPages(
				new EntryPageText("knowledge.firepit.1"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.FIREPIT_RECIPE),
				new EntryPageText("knowledge.firepit.2"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.STOVE_RECIPE),
				new EntryPageText("knowledge.firepit.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("oven", false)),
				new EntryPageText("knowledge.firepit.4"));

		MineFantasyKnowledgeList.cooking_utensils.addPages(
				new EntryPageText("knowledge.cooking_utensils.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName("cake_tin", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("pie_tray_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("pie_tray", false)));

		MineFantasyKnowledgeList.salt.addPages(
				new EntryPageText("knowledge.salt.1"),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("salt", false)));

		MineFantasyKnowledgeList.jug.addPages(
				new EntryPageText("knowledge.jug.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("jug_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("jug_empty", false)),
				new EntryPageText("knowledge.jug.2"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.JUG_WATER_RECIPE),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.JUG_MILK_RECIPE));

		MineFantasyKnowledgeList.generic_meat.addPages(
				new EntryPageText("knowledge.generic_meat.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("generic_meat_uncooked", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("generic_meat_strip_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("generic_meat_strip_cooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("generic_meat_cooked", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("generic_meat_strip_cooked", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("generic_meat_chunk_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("generic_meat_chunk_cooked", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("generic_meat_chunk_cooked", false)),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("generic_meat_mince_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("generic_meat_mince_cooked", false)),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("generic_meat_mince_cooked", false)));

		MineFantasyKnowledgeList.wild_meat.addPages(
				new EntryPageText("knowledge.wild_meat.1"),
				new EntryPageRoast(CraftingManagerRoast.getRecipesByName("horse_cooked", "wolf_cooked")));

		MineFantasyKnowledgeList.stew.addPages(
				new EntryPageText("knowledge.stew.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("stew", false)));

		MineFantasyKnowledgeList.jerky.addPages(
				new EntryPageText("knowledge.jerky.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("jerky", false)));

		MineFantasyKnowledgeList.sausage.addPages(
				new EntryPageText("knowledge.sausage.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("guts", false)),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("breadcrumbs", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("saussage_raw", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("saussage_cooked", false)));

		MineFantasyKnowledgeList.sandwitch.addPages(
				new EntryPageText("knowledge.sandwitch.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("bread_slice", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("sandwitch_meat", false)));

		MineFantasyKnowledgeList.sandwitch_big.addPages(
				new EntryPageText("knowledge.sandwitch_big.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("sandwitch_big", false)));

		MineFantasyKnowledgeList.meatpie.addPages(
				new EntryPageText("knowledge.meatpie.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("pie_meat_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("pie_meat_cooked", false)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_MEAT_RECIPE));

		MineFantasyKnowledgeList.shepard_pie.addPages(
				new EntryPageText("knowledge.shepard_pie.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("pie_shepard_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("pie_shepard_cooked", false)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_SHEPARDS_RECIPE));

		MineFantasyKnowledgeList.bread.addPages(
				new EntryPageText("knowledge.bread.1"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("flour", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("dough", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("breadroll", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("raw_bread", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("bread", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("pastry", false)),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("breadcrumbs", false)),
				new EntryPageText("knowledge.bread.other"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("pie_pumpkin_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("pie_pumpkin_cooked", false)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_PUMPKIN_RECIPE),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("cake_simple_raw", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("cake_simple_uniced", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("cake", false)));

		MineFantasyKnowledgeList.oats.addPages(
				new EntryPageText("knowledge.oats.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("oats", false)));

		MineFantasyKnowledgeList.berry.addPages(assembleImgPage("berry"));

		MineFantasyKnowledgeList.icing.addPages(
				new EntryPageText("knowledge.icing.1"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName("sugar_pot", false)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.SUGAR_POT_RECIPE),
				new EntryPageText("knowledge.icing.2"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("icing", false)));

		MineFantasyKnowledgeList.sweetroll.addPages(
				new EntryPageText("knowledge.sweetroll.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("sweetroll_raw", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("sweetroll_uniced", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("sweetroll", false)));

		MineFantasyKnowledgeList.cake.addPages(
				new EntryPageText("knowledge.cake.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("cake_raw", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("cake_uniced", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("cake_vanilla", false)));

		MineFantasyKnowledgeList.carrot_cake.addPages(
				new EntryPageText("knowledge.carrot_cake.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("cake_carrot_raw", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("cake_carrot_uniced", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("cake_carrot", false)));

		MineFantasyKnowledgeList.chocolate_cake.addPages(
				new EntryPageText("knowledge.chocolate_cake.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("chocolate", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("cake_choc_raw", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("cake_choc_uniced", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("cake_chocolate", false)));

		MineFantasyKnowledgeList.black_forest_cake.addPages(
				new EntryPageText("knowledge.black_forest_cake.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("chocolate", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("cake_bf_raw", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("cake_bf_uniced", false)));

		MineFantasyKnowledgeList.berry_pie.addPages(
				new EntryPageText("knowledge.berry_pie.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("pie_berry_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("pie_berry_cooked", false)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_BERRY_RECIPE));

		MineFantasyKnowledgeList.apple_pie.addPages(
				new EntryPageText("knowledge.apple_pie.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("pie_apple_uncooked", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("pie_apple_cooked", false)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_APPLE_RECIPE));

		MineFantasyKnowledgeList.eclair.addPages(
				new EntryPageText("knowledge.eclair.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("eclair_raw", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("eclair_uniced", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("chocolate", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("eclair_empty", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("custard", false)),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("eclair", false)));

		MineFantasyKnowledgeList.cheese.addPages(
				new EntryPageText("knowledge.cheese.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("curds", false)),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName("cheese_pot", false)),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.CHEESE_WHEEL_RECIPE));

		MineFantasyKnowledgeList.cheese_roll.addPages(
				new EntryPageText("knowledge.cheese_roll.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName("cheese_roll", false)));

		MineFantasyKnowledgeList.bandage.addPages(
				new EntryPageText("knowledge.bandage.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bandage_crude", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bandage_crude-2", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bandage_crude-3", false)),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bandage_wool", false)));

		MineFantasyKnowledgeList.bandage_advanced.addPages(
				new EntryPageText("knowledge.bandage_advanced.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName("bandage_tough", false)));

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
					new EntryPageRecipeBloom(CraftingManagerBloomery.getRecipeByName(orename + "_bar", false))};
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
