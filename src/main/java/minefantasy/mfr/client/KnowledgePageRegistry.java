package minefantasy.mfr.client;

import minefantasy.mfr.MineFantasyReforged;
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
		String modId = MineFantasyReforged.MOD_ID;

		if (ConfigHardcore.HCCallowRocks) {
			MineFantasyKnowledgeList.getting_started.addPages(
					new EntryPageText("knowledge.getting_started.hcc"),
					new EntryPageRecipeBase(MineFantasyKnowledgeList.CARPENTER_RECIPE),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "sharp_rock")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_pick")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_hammer")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "sharp_rock-2")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_spear")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_axe")));
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
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "oven")),
				new EntryPageText("knowledge.getting_started.tanning"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "tanner")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_knife")),
				new EntryPageText("knowledge.getting_started.forging"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "anvil_stone")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "forge_stone")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_hammer")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_tongs")),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.DRYROCKS_RECIPE),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "leather_apron")),
				new EntryPageText("knowledge.getting_started.forging_bars"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bar")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "ingot")),
				new EntryPageText("knowledge.getting_started.flux"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "flux")),
				new EntryPageText("knowledge.getting_started.3"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_shears")),
				new EntryPageText("knowledge.getting_started.4"),
				new EntryPageText("knowledge.getting_started.5"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_hammer")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_tongs")),
				new EntryPageText("knowledge.getting_started.6"),
				new EntryPageText("knowledge.getting_started.upgrade"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "research_bench")),
				new EntryPageText("knowledge.getting_started.7"),
				new EntryPageText("knowledge.getting_started.10"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "clay_pot_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "clay_pot")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "quern")),
				new EntryPageText("knowledge.getting_started.11"),
				new EntryPageText("knowledge.getting_started.12"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "salvage_basic")),
				new EntryPageText("knowledge.getting_started.13"));

		MineFantasyKnowledgeList.research.addPages(
				new EntryPageText("knowledge.research.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "research_bench")),
				new EntryPageText("knowledge.research.2"));

		//IDKH to make it look not ugly without significant code changes
		MineFantasyKnowledgeList.talisman.addPages(new EntryPageText("knowledge.talisman.1"));
		if (!ConfigHardcore.HCCRemoveTalismansCraft) {
			MineFantasyKnowledgeList.talisman.addPages(new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "talisman_lesser")));
		}

		if (!ConfigHardcore.HCCRemoveBooksCraft) {
			MineFantasyKnowledgeList.talisman.addPages(
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "skillbook_artisanry")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "skillbook_construction")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "skillbook_provisioning")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "skillbook_engineering")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "skillbook_combat")));
		}

		MineFantasyKnowledgeList.talisman.addPages(new EntryPageText("knowledge.talisman.2"));

		if (!ConfigHardcore.HCCRemoveTalismansCraft) {
			MineFantasyKnowledgeList.talisman.addPages(new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "talisman_greater")));
		}

		if (!ConfigHardcore.HCCRemoveBooksCraft) {
			MineFantasyKnowledgeList.talisman.addPages(
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "skillbook_artisanry_max")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "skillbook_construction_max")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "skillbook_provisioning_max")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "skillbook_engineering_max")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "skillbook_combat_max")));
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
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "hide_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "hide_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "hide_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "hide_boots")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "rough_leather_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "rough_leather_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "rough_leather_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "rough_leather_boots")));

		MineFantasyKnowledgeList.carpenter.addPages(
				new EntryPageText("knowledge.carpenter.1"),
				new EntryPageRecipeBase(RecipeHelper.getMFRRecipe("carpenter")));

		MineFantasyKnowledgeList.salvage.addPages(
				new EntryPageText("knowledge.salvage.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "salvage_basic")),
				new EntryPageText("knowledge.salvage.2"));

		MineFantasyKnowledgeList.commodities.addPages(
				new EntryPageText("knowledge.commodities.1"),
				new EntryPageText("knowledge.commodities.plank"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.TIMBER_RECIPE),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.STICK_RECIPE),
				new EntryPageText("knowledge.commodities.refinedplank"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "jug_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "jug_empty")),
				new EntryPageRecipeBase(RecipeHelper.getMFRRecipe("jug_plant_oil")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "refined_timber")),
				new EntryPageText("knowledge.commodities.flux"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "flux")),
				new EntryPageText("knowledge.bar.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "ingot")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bar")),
				new EntryPageText("knowledge.commodities.hunks"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "metal_hunk")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bar_hunks")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "plate")),
				new EntryPageText("knowledge.commodities.nail"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "nail")),
				new EntryPageText("knowledge.commodities.rivet"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "rivet")),
				new EntryPageText("knowledge.commodities.leatherstrip"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "leather_strip")),
				new EntryPageText("knowledge.commodities.thread"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "thread")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "string")),
				new EntryPageText("knowledge.commodities.bucket"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bucket")));

		MineFantasyKnowledgeList.dust.addPages(
				new EntryPageText("knowledge.dust.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "clay_pot_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "clay_pot")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "quern")),
				new EntryPageText("knowledge.dust.quern"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "coal_dust")),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "kaolinite_dust")),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "flour")),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "coca_powder")),
				new EntryPageText("knowledge.dust.icing"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "standard_spoon")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "icing")));

		MineFantasyKnowledgeList.ores.addPages(
				new EntryPageText("knowledge.ores.1"),
				new EntryPageText(""));

		MineFantasyKnowledgeList.ores.addPages(assembleOreDescHC("copper", MineFantasyBlocks.COPPER_ORE, MineFantasyItems.COPPER_INGOT));
		MineFantasyKnowledgeList.ores.addPages(assembleOreDescHC("tin", MineFantasyBlocks.TIN_ORE, MineFantasyItems.TIN_INGOT));
		MineFantasyKnowledgeList.ores.addPages(assembleOreDescHC("silver", MineFantasyBlocks.SILVER_ORE, MineFantasyItems.SILVER_INGOT));
		MineFantasyKnowledgeList.ores.addPages(assembleOreDesc("wolframite"));
		MineFantasyKnowledgeList.ores.addPages(new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName(modId, "tungsten_bar")));
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
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "chimney_stone_thin")),
				new EntryPageText("knowledge.chimney.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "chimney_stone_wide")),
				new EntryPageText("knowledge.chimney.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "chimney_stone_extractor_wide")),
				new EntryPageText("knowledge.chimney.pipe"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "chimney_pipe_thin")),
				assembleSimpleImgPage("smoke_pipe_example", "knowledge.chimney.pipe.2"));

		MineFantasyKnowledgeList.tanning.addPages(
				new EntryPageText("knowledge.tanning.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "tanner")),
				new EntryPageText("knowledge.tanning.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "tanner_refined")),
				new EntryPageRecipeTanner(CraftingManagerTanner.getRecipesByName(modId, "leather_small", "leather_medium", "leather_large")),
				new EntryPageRecipeTanner(CraftingManagerTanner.getRecipeByName(modId, "leather_strip")));

		MineFantasyKnowledgeList.bloomery.addPages(
				new EntryPageText("knowledge.bloomery.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bloomery")),
				new EntryPageText("knowledge.bloomery.2"),
				new EntryPageText("knowledge.bloomery.3"));

		MineFantasyKnowledgeList.crucible.addPages(
				assembleSimpleImgPage("crucible_example", "knowledge.crucible.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "crucible_stone")),
				new EntryPageText("knowledge.crucible.2"));

		if (ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.crucible.addPages(
					new EntryPageText("knowledge.crucible.hcc"),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "ingot_mould_uncooked")),
					new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "ingot_mould")));
		}

		MineFantasyKnowledgeList.firebrick_crucible.addPages(
				assembleSimpleImgPage("fire_crucible_example", "knowledge.firebrick_crucible.1"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "kaolinite_dust")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "fireclay")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "crucible_fireclay")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "firebricks")),
				assembleSimpleImgPage("fire_crucible", "knowledge.firebrick_crucible.blocks"));

		MineFantasyKnowledgeList.bar.addPages(
				new EntryPageText("knowledge.bar.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bar_hunks")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bar")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "ingot")));

		MineFantasyKnowledgeList.smelt_copper.addPages(new EntryPageText("knowledge.smelt_copper.1"));
		MineFantasyKnowledgeList.smelt_bronze.addPages(new EntryPageText("knowledge.smelt_bronze.1"));

		if (ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.smelt_iron.addPages(new EntryPageText("knowledge.smelt_iron.1"));
			MineFantasyKnowledgeList.smelt_copper.addPages(new EntryPageRecipeBloom(CraftingManagerBloomery.getRecipeByName(modId, "copper_bar")));
			MineFantasyKnowledgeList.smelt_bronze.addPages(new EntryPageRecipeBloom(CraftingManagerBloomery.getRecipeByName(modId, "tin_bar")));
		} else {
			MineFantasyKnowledgeList.smelt_copper.addPages(new EntryPageSmelting(new ItemStack(MineFantasyBlocks.COPPER_ORE), new ItemStack(MineFantasyItems.COPPER_INGOT)));
			MineFantasyKnowledgeList.smelt_bronze.addPages(new EntryPageSmelting(new ItemStack(MineFantasyBlocks.TIN_ORE), new ItemStack(MineFantasyItems.TIN_INGOT)));
		}
		MineFantasyKnowledgeList.smelt_bronze.addPages(new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName(modId, "bronze_bar")));
		MineFantasyKnowledgeList.smelt_iron.addPages(new EntryPageText("knowledge.smelt_iron.2"));

		MineFantasyKnowledgeList.smelt_pig_iron.addPages(
				new EntryPageText("knowledge.smelt_pig_iron.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "prepared_iron")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "prepared_iron-2")),
				new EntryPageBlastFurnace(CraftingManagerBlastFurnace.getRecipeByName(modId, "pig_iron_bar")),
				new EntryPageText("knowledge.blast_furnace.9"));

		MineFantasyKnowledgeList.smelt_steel.addPages(
				new EntryPageText("knowledge.smelt_steel.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bar_steel")));

		if (!ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.smelt_steel.addPages(new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName(modId, "steel_bar")));
		}

		MineFantasyKnowledgeList.smelt_encrusted.addPages(
				new EntryPageText("knowledge.smelt_encrusted.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "diamond_shards")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bar_encrusted")));

		MineFantasyKnowledgeList.smelt_obsidian.addPages(
				new EntryPageText("knowledge.smelt_obsidian.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "obsidian_rock")),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName(modId, "obsidian_bar")));

		if (ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.smelt_iron.addPages(
					new EntryPageRecipeBloom(CraftingManagerBloomery.getRecipeByName(modId, "iron_bar")));
		} else {
			MineFantasyKnowledgeList.smelt_iron
					.addPages(new EntryPageSmelting(new ItemStack(Blocks.IRON_ORE), new ItemStack(Items.IRON_INGOT)));
		}

		MineFantasyKnowledgeList.apron.addPages(
				new EntryPageText("knowledge.apron.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "leather_apron")));

		MineFantasyKnowledgeList.bellows.addPages(
				new EntryPageText("knowledge.bellows.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bellows")));

		MineFantasyKnowledgeList.trough.addPages(
				new EntryPageText("knowledge.trough.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "trough_wood_scrap")),
				new EntryPageText("knowledge.trough.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "trough_wood")));

		MineFantasyKnowledgeList.forge.addPages(
				assembleSimpleImgPage("forge_example", "knowledge.forge.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "forge_stone")),
				new EntryPageText("knowledge.forge.2"),
				new EntryPageText("knowledge.forge.3"));

		MineFantasyKnowledgeList.anvil.addPages(
				assembleSimpleImgPage("smithy_example", "knowledge.anvil.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "anvil_stone")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "anvil_bronze")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "anvil_iron")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "anvil_red_steel")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "anvil_black_steel")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "anvil_blue_steel")),
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
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "ornate_items")));

		MineFantasyKnowledgeList.craft_armour_light.addPages(
				new EntryPageText("knowledge.craft_armour_light.1"),
				new EntryPageText("knowledge.craft_armour_light.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "rough_leather_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "rough_leather_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "rough_leather_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "rough_leather_boots")),
				new EntryPageText("knowledge.craft_armour_light.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "padded_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "padded_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "padded_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "padded_boots")),
				new EntryPageText("knowledge.craft_armour_light.4"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "strong_leather_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "strong_leather_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "strong_leather_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "strong_leather_boots")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "stud_leather_helmet")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "stud_leather_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "stud_leather_leggings")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "stud_leather_boots")));

		MineFantasyKnowledgeList.craft_armour_medium.addPages(
				new EntryPageText("knowledge.craft_armour_medium.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "chain_mesh")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_chain_helmet")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_chain_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_chain_leggings")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_chain_boots")),
				new EntryPageText("knowledge.craft_armour_medium.scale"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "scale_mesh")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_scale_helmet")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_scale_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_scale_leggings")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_scale_boots")));

		MineFantasyKnowledgeList.craft_armour_heavy.addPages(
				new EntryPageText("knowledge.craft_armour_heavy.1"),
				new EntryPageText("knowledge.craft_armour_heavy.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "padded_helmet")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "padded_chestplate")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "padded_leggings")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "padded_boots")),
				new EntryPageText("knowledge.craft_armour_heavy.splint"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "splint_mesh")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_splint_helmet")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_splint_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_splint_leggings")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_splint_boots")),
				new EntryPageText("knowledge.craft_armour_heavy.plate"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "plate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_plate_helmet")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_plate_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_plate_leggings")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_plate_boots")));

		MineFantasyKnowledgeList.coal_flux.addPages(
				new EntryPageText("knowledge.coal_flux.1"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "flux_pot")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "coal_flux")));

		MineFantasyKnowledgeList.big_furnace.addPages(
				assembleSimpleImgPage("furnace_example", "knowledge.big_furnace.1"),
				new EntryPageText("knowledge.big_furnace.2"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "kaolinite_dust")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "fireclay")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "fireclay_brick")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "strong_brick")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "firebricks")),
				new EntryPageText("knowledge.big_furnace.heater"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "furnace_heater")),
				assembleSimpleImgPage("furnace_heater", MineFantasyBlocks.FURNACE_HEATER.getTranslationKey() + ".name"),
				new EntryPageText("knowledge.big_furnace.top"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "furnace_stone")),
				assembleSimpleImgPage("furnace_full", "knowledge.big_furnace.structure"));

		MineFantasyKnowledgeList.blast_furnace.addPages(
				new EntryPageText("knowledge.blast_furnace.1"),
				new EntryPageText("knowledge.blast_furnace.2"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "kaolinite_dust")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "fireclay")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "fireclay_brick")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "strong_brick")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "firebricks")),
				new EntryPageText("knowledge.blast_furnace.3"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "blast_chamber")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "blast_heater")),
				new EntryPageText("knowledge.blast_furnace.4"),
				new EntryPageText("knowledge.blast_furnace.5"),
				new EntryPageImage("textures/gui/knowledge/blast_example.png", 96, 96, "knowledge.blast_furnace.6"),
				new EntryPageText("knowledge.blast_furnace.7"));

		if (ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.blast_furnace.addPages(new EntryPageText("knowledge.blast_furnace.hcc"));
		}

		MineFantasyKnowledgeList.engineering_tools.addPages(
				new EntryPageText("knowledge.engineering_tools.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_spanner")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "engin_anvil_tools")));

		MineFantasyKnowledgeList.engineering_components.addPages(
				new EntryPageText("knowledge.engineering_components.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bolt")),
				new EntryPageText("knowledge.engineering_components.2"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "iron_frame")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bronze_gears")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "tungsten_gears")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "iron_strut")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "steel_tube")));

		MineFantasyKnowledgeList.tungsten.addPages(
				new EntryPageText("knowledge.tungsten.1"),
				new EntryPageText("knowledge.tungsten.2"),
				new EntryPageText("knowledge.tungsten.3"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName(modId, "tungsten_bar")));

		MineFantasyKnowledgeList.climber.addPages(
				new EntryPageText("knowledge.climber.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "climbing_pick_basic")),
				new EntryPageText("knowledge.climber.2"));

		MineFantasyKnowledgeList.spyglass.addPages(
				new EntryPageText("knowledge.spyglass.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "spyglass")));

		MineFantasyKnowledgeList.parachute.addPages(
				new EntryPageText("knowledge.parachute.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "parachute")));

		MineFantasyKnowledgeList.syringe.addPages(
				new EntryPageText("knowledge.syringe.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "syringe_empty")));

		MineFantasyKnowledgeList.engineering_tanner.addPages(
				new EntryPageText("knowledge.engineering_tanner.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "tanner_metal")));

		MineFantasyKnowledgeList.advanced_crucible.addPages(
				assembleSimpleImgPage("auto_crucible_example", MineFantasyBlocks.CRUCIBLE_AUTO.getTranslationKey() + ".name"),
				new EntryPageText("knowledge.advanced_crucible.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "crucible_auto")),
				new EntryPageText("knowledge.firebrick_crucible.blocks"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "firebricks")),
				assembleSimpleImgPage("auto_crucible", "knowledge.basicstructure"));

		MineFantasyKnowledgeList.advanced_forge.addPages(
				new EntryPageText("knowledge.advanced_forge.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "forge_metal")));

		MineFantasyKnowledgeList.coke.addPages(
				new EntryPageText("knowledge.coke.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "prepared_coal")),
				new EntryPageSmelting(MineFantasyItems.PREPARED_COAL, MineFantasyItems.COKE));

		MineFantasyKnowledgeList.blackpowder.addPages(
				new EntryPageText("knowledge.blackpowder.1"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "coal_dust")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "blackpowder")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bomb_crude")));

		MineFantasyKnowledgeList.advanced_blackpowder.addPages(
				new EntryPageText("knowledge.advanced_blackpowder.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "blackpowder_advanced")));

		MineFantasyKnowledgeList.bombs.addPages(
				new EntryPageText("knowledge.bombs.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bomb_bench")),
				new EntryPageText("knowledge.bombs.2"),
				new EntryPageImage("textures/gui/knowledge/bomb_gui_example.png", 128, 128, "knowledge.guiSubtitle"),
				new EntryPageText("knowledge.bombs.3"),
				new EntryPageText("knowledge.bombs.4"));

		MineFantasyKnowledgeList.bomb_press.addPages(
				new EntryPageText("knowledge.bomb_press.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bomb_press")));

		MineFantasyKnowledgeList.bomb_arrow.addPages(
				new EntryPageText("knowledge.bomb_arrow.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bomb_casing_arrow")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bomb_casing_bolt")));

		MineFantasyKnowledgeList.shrapnel.addPages(
				new EntryPageText("knowledge.shrapnel.1"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "shrapnel")));

		MineFantasyKnowledgeList.firebomb.addPages(
				new EntryPageText("knowledge.firebomb.1"),
				new EntryPageText("knowledge.firebomb.2"),
				new EntryPageText("knowledge.firebomb.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "magma_cream_refined")));

		MineFantasyKnowledgeList.bomb_ceramic.addPages(
				new EntryPageText("knowledge.bomb_ceramic.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bomb_casing_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "bomb_casing_ceramic")));

		MineFantasyKnowledgeList.bomb_iron.addPages(
				new EntryPageText("knowledge.bomb_iron.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bomb_casing_iron")));

		MineFantasyKnowledgeList.bomb_obsidian.addPages(
				new EntryPageText("knowledge.bomb_obsidian.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bomb_casing_obsidian")));

		MineFantasyKnowledgeList.bomb_crystal.addPages(
				new EntryPageText("knowledge.bomb_crystal.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bomb_casing_crystal")));

		MineFantasyKnowledgeList.mine_ceramic.addPages(
				new EntryPageText("knowledge.mine_ceramic.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "mine_casing_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "mine_casing_ceramic")));

		MineFantasyKnowledgeList.mine_iron.addPages(
				new EntryPageText("knowledge.mine_iron.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "mine_casing_iron")));

		MineFantasyKnowledgeList.mine_obsidian.addPages(
				new EntryPageText("knowledge.mine_obsidian.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "mine_casing_obsidian")));

		MineFantasyKnowledgeList.mine_crystal.addPages(
				new EntryPageText("knowledge.mine_crystal.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "mine_casing_crystal")));

		MineFantasyKnowledgeList.bomb_fuse.addPages(
				new EntryPageText("knowledge.bomb_fuse.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bomb_fuse")),
				new EntryPageText("knowledge.bomb_fuse.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bomb_fuse_long")));

		MineFantasyKnowledgeList.sticky_bomb.addPages(
				new EntryPageText("knowledge.sticky_bomb.1"),
				new EntryPageText("knowledge.sticky_bomb.2"));

		MineFantasyKnowledgeList.crossbows.addPages(
				new EntryPageText("knowledge.crossbows.1"),
				new EntryPageText("knowledge.crossbows.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "crossbow_bench")),
				new EntryPageText("knowledge.crossbows.3"),
				new EntryPageText("knowledge.crossbows.4"),
				new EntryPageText("knowledge.crossbows.5"),
				new EntryPageText("knowledge.crossbows.6"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_bolt")));

		MineFantasyKnowledgeList.crossbow_shafts.addPages(
				new EntryPageText("knowledge.crossbow_shafts.handle"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "crossbow_handle_wood")),
				new EntryPageText("knowledge.crossbow_shafts.stock"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "crossbow_stock_wood")));

		MineFantasyKnowledgeList.crossbow_heads.addPages(
				new EntryPageText("knowledge.crossbow_heads.light"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "crossbow_arms_light")),
				new EntryPageText("knowledge.crossbow_heads.medium"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "crossbow_arms_basic")),
				new EntryPageText("knowledge.crossbow_heads.heavy"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "crossbow_arms_heavy")));

		MineFantasyKnowledgeList.crossbow_head_advanced.addPages(
				new EntryPageText("knowledge.crossbow_head_advanced.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "crossbow_arms_advanced")));

		MineFantasyKnowledgeList.crossbow_shaft_advanced.addPages(
				new EntryPageText("knowledge.crossbow_shaft_advanced.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "crossbow_stock_iron")));

		MineFantasyKnowledgeList.crossbow_scope.addPages(
				new EntryPageText("knowledge.crossbow_scope.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "crossbow_scope")));

		MineFantasyKnowledgeList.crossbow_ammo.addPages(
				new EntryPageText("knowledge.crossbow_ammo.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "crossbow_ammo")));

		MineFantasyKnowledgeList.crossbow_bayonet.addPages(
				new EntryPageText("knowledge.crossbow_bayonet.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "crossbow_bayonet")));

		MineFantasyKnowledgeList.cogwork_armour.addPages(
				new EntryPageText("knowledge.cogwork_armour.1"),
				new EntryPageText("knowledge.cogwork_armour.2"),
				new EntryPageText("knowledge.cogwork_armour.station.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "frame_block")),
				assembleSimpleImgPage("cogwork_station", "knowledge.cogwork_station"),
				new EntryPageText("knowledge.cogwork_armour.station.2"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "cogwork_pulley")),
				assembleSimpleImgPage("cogwork_station_2", "knowledge.rightclickspanner"),
				new EntryPageText("knowledge.cogwork_armour.crafting"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "cogwork_shaft")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "block_cogwork_helm")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "block_cogwork_chestplate")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "block_cogwork_legs")),
				assembleSimpleImgPage("cogwork_suit_craft", "knowledge.rightclickspanner"),
				new EntryPageText("knowledge.cogwork_armour.armour"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "plate_huge")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "cogwork_armour")),
				new EntryPageText("knowledge.cogwork_armour.advantage"),
				new EntryPageText("knowledge.cogwork_armour.advantage.2"),
				new EntryPageText("knowledge.cogwork_armour.advantage.3"),
				new EntryPageText("knowledge.cogwork_armour.disadvantage"),
				new EntryPageText("knowledge.cogwork_armour.disadvantage.2"),
				new EntryPageText("knowledge.cogwork_armour.removal"));

		MineFantasyKnowledgeList.composite_alloy.addPages(
				new EntryPageText("knowledge.composite_alloy.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bar_composite_alloy")));

		MineFantasyKnowledgeList.repair_basic.addPages(
				new EntryPageText("knowledge.repair_basic.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "repair_kit_basic")));

		MineFantasyKnowledgeList.repair_advanced.addPages(
				new EntryPageText("knowledge.repair_advanced.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "repair_kit_advanced")));

		MineFantasyKnowledgeList.repair_ornate.addPages(
				new EntryPageText("knowledge.repair_ornate.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "repair_kit_ornate")));

		MineFantasyKnowledgeList.refined_planks.addPages(
				new EntryPageText("knowledge.refined_planks.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "nailed_planks")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "refined_planks")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "nailed_planks_stairs")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "refined_planks_stairs")));

		MineFantasyKnowledgeList.reinforced_stone.addPages(
				new EntryPageText("knowledge.reinforced_stone.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "obsidian_rock")),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName(modId, "reinforced_stone")));

		MineFantasyKnowledgeList.brickworks.addPages(
				new EntryPageText("knowledge.brickworks.1"),
				new EntryPageRecipeBase(RecipeHelper.getMFRRecipe("cobble_brick"),
						RecipeHelper.getMFRRecipe("reinforced_stone_bricks"),
						RecipeHelper.getMFRRecipe("mud_brick")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "firebricks")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "firebrick_stairs")));

		MineFantasyKnowledgeList.clay_wall.addPages(
				new EntryPageText("knowledge.clay_wall.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "clay_wall")));

		MineFantasyKnowledgeList.glass.addPages(
				new EntryPageText("knowledge.glass.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "framed_glass")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "window")));

		MineFantasyKnowledgeList.thatch.addPages(
				new EntryPageText("knowledge.thatch.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "thatch")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "thatch_stairs")));

		MineFantasyKnowledgeList.bars.addPages(
				new EntryPageText("knowledge.bars.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipesByName(modId, "bronze_bars", "iron_bars",
						"steel_bars", "black_steel_bars", "red_steel_bars", "blue_steel_bars")));

		MineFantasyKnowledgeList.paint_brush.addPages(
				new EntryPageText("knowledge.paint_brush.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "paint_brush")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "refined_timber_brush")));

		MineFantasyKnowledgeList.decorated_stone.addPages(
				new EntryPageText("knowledge.decorated_stone.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "reinforced_stone_framed")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "reinforced_stone_framed_iron")));

		//        KnowledgeListMFR.bed_roll.addPages(new EntryPageText("knowledge.bed_roll.1"),
		//                new EntryPageRecipeCarpenter(KnowledgeListMFR.bedrollR));

		MineFantasyKnowledgeList.tool_rack.addPages(
				new EntryPageText("knowledge.tool_rack.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "rack_wood")),
				new EntryPageText("knowledge.tool_rack.rules"));

		MineFantasyKnowledgeList.food_box.addPages(
				new EntryPageText("knowledge.food_box.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "food_box_basic")),
				new EntryPageText("knowledge.ammo_box.2"));

		MineFantasyKnowledgeList.ammo_box.addPages(
				new EntryPageText("knowledge.ammo_box.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "ammo_box_basic")),
				new EntryPageText("knowledge.ammo_box.2"));

		MineFantasyKnowledgeList.big_box.addPages(
				new EntryPageText("knowledge.big_box.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "crate_basic")),
				new EntryPageText("knowledge.ammo_box.2"));

		MineFantasyKnowledgeList.construction_parts.addPages(
				new EntryPageText("knowledge.construction_parts.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "timber_cut")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "timber_pane")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "hinge")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "jug_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "jug_empty")),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.JUG_PLANT_OIL_RECIPE),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "refined_timber")));

		if (ConfigHardcore.HCCallowRocks) {
			MineFantasyKnowledgeList.crafting_HCC_tools.addPages(
					new EntryPageText("knowledge.crafting_HCC_tools.1"),
					new EntryPageText("knowledge.crafting_HCC_tools.2"),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "sharp_rock")),
					new EntryPageText("knowledge.crafting_HCC_tools.3"),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_pick")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_spade")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_axe")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_hoe")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_knife")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_hammer")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_tongs")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bone_needle")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_sword")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_mace")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_waraxe")),
					new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_spear")));
		}

		MineFantasyKnowledgeList.craft_tools.addPages(
				new EntryPageText("knowledge.craft_tools.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_pick")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_axe")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_spade")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_hoe")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_shears")));

		MineFantasyKnowledgeList.craft_advanced_tools.addPages(
				new EntryPageText("knowledge.craft_advanced_tools.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_handpick")),
				new EntryPageText("knowledge.heavy_pick.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_heavy_pick")),
				new EntryPageText("knowledge.trow.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_trow")),
				new EntryPageText("knowledge.heavy_shovel.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_heavy_shovel")),
				new EntryPageText("knowledge.scythe.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_scythe")),
				new EntryPageText("knowledge.mattock.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_mattock")),
				new EntryPageText("knowledge.mattock.use"),
				new EntryPageText("knowledge.lumber.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_lumber")));

		MineFantasyKnowledgeList.firemaker.addPages(
				new EntryPageText("knowledge.dryrocks.info"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.DRYROCKS_RECIPE),
				new EntryPageText("knowledge.tinderbox.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "tinderbox")),
				new EntryPageText("knowledge.flintsteel.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "flint_and_steel")));

		MineFantasyKnowledgeList.craft_crafters.addPages(
				new EntryPageText("knowledge.craft_crafters.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_hammer")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_hammer")),
				new EntryPageText(""),
				new EntryPageText("knowledge.heavy_hammer.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_heavy_hammer")),
				new EntryPageText("knowledge.tongs.info"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_tongs")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_tongs")),
				new EntryPageText(""),
				new EntryPageText("knowledge.knife.info"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "stone_knife")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_knife")),
				new EntryPageText(""),
				new EntryPageText("knowledge.saw.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_saw")),
				new EntryPageText("knowledge.needle.info"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bone_needle")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_needle")),
				new EntryPageText(""),
				new EntryPageText("knowledge.mallet.info"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "standard_mallet")),
				new EntryPageText("knowledge.spoon.info"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "standard_spoon")));

		MineFantasyKnowledgeList.craft_weapons.addPages(
				new EntryPageText("knowledge.craft_weapons.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_dagger")),
				new EntryPageText("knowledge.sword.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_sword")),
				new EntryPageText("knowledge.waraxe.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_waraxe")),
				new EntryPageText("knowledge.mace.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_mace")),
				new EntryPageText("knowledge.spear.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_spear")),
				new EntryPageText("knowledge.bow.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_bow")));

		MineFantasyKnowledgeList.craft_advanced_weapons.addPages(
				new EntryPageText("knowledge.craft_advanced_weapons.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_katana")),
				new EntryPageText("knowledge.greatsword.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_greatsword")),
				new EntryPageText("knowledge.battleaxe.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_battleaxe")),
				new EntryPageText("knowledge.warhammer.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_warhammer")),
				new EntryPageText("knowledge.halbeard.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_halbeard")),
				new EntryPageText("knowledge.lance.info"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "standard_lance")));

		MineFantasyKnowledgeList.arrows.addPages(
				new EntryPageText("knowledge.arrows.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "fletching")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "fletching-2")),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "arrowhead")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipesByName(
						modId, "standard_arrow",
						"standard_arrow_broad",
						"standard_arrow_bodkin")));

		MineFantasyKnowledgeList.arrows_bodkin.addPages(
				new EntryPageText("knowledge.arrows_bodkin.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "bodkin_head")));

		MineFantasyKnowledgeList.arrows_broad.addPages(
				new EntryPageText("knowledge.arrows_broad.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "broad_head")));

		MineFantasyKnowledgeList.smelt_black_steel.addPages(
				new EntryPageText("knowledge.smelt_black_steel.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "obsidian_rock")),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName(modId, "black_steel_weak_ingot")),
				new EntryPageBlastFurnace(CraftingManagerBlastFurnace.getRecipeByName(modId, "black_steel_bar")));

		MineFantasyKnowledgeList.smelt_red_steel.addPages(
				new EntryPageText("knowledge.smelt_red_steel.1"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName(modId, "red_steel_weak_ingot")),
				new EntryPageBlastFurnace(CraftingManagerBlastFurnace.getRecipeByName(modId, "red_steel_bar")));

		MineFantasyKnowledgeList.smelt_blue_steel.addPages(
				new EntryPageText("knowledge.smelt_blue_steel.1"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName(modId, "blue_steel_weak_ingot")),
				new EntryPageBlastFurnace(CraftingManagerBlastFurnace.getRecipeByName(modId, "blue_steel_bar")));

		MineFantasyKnowledgeList.smelt_adamantium.addPages(
				new EntryPageText("knowledge.smelt_adamantium.1"),
				new EntryPageText("knowledge.smelt_adamantium.2"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName(modId, "adamantium_bar")));

		MineFantasyKnowledgeList.smelt_mithril.addPages(
				new EntryPageText("knowledge.smelt_mithril.1"),
				new EntryPageText("knowledge.smelt_mithril.2"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName(modId, "mithril_bar")));

		MineFantasyKnowledgeList.smelt_master.addPages(
				new EntryPageText("knowledge.smelt_master.1"),
				new EntryPageText("knowledge.smelt_master.2"),
				new EntryPageText("knowledge.smelt_master.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "trilogy_jewel")));

		MineFantasyKnowledgeList.smelt_ignotumite.addPages(
				new EntryPageText("knowledge.smelt_ignotumite.1"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName(modId, "ignotumite_bar")));

		MineFantasyKnowledgeList.smelt_mithium.addPages(
				new EntryPageText("knowledge.smelt_mithium.1"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName(modId, "mithium_bar")));

		MineFantasyKnowledgeList.smelt_ender.addPages(
				new EntryPageText("knowledge.smelt_ender.1"),
				new EntryPageCrucible(CraftingManagerAlloy.getRecipeByName(modId, "ender_bar")));

		MineFantasyKnowledgeList.kitchen_bench.addPages(
				new EntryPageText("knowledge.kitchen_bench.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipesByName(
						modId, "kitchen_bench_andesite",
						"kitchen_bench_diorite",
						"kitchen_bench_granite")),
				new EntryPageText("knowledge.kitchen_bench.2"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "wash_cloth_wool"))
		);

		MineFantasyKnowledgeList.firepit.addPages(
				new EntryPageText("knowledge.firepit.1"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.FIREPIT_RECIPE),
				new EntryPageText("knowledge.firepit.2"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.STOVE_RECIPE),
				new EntryPageText("knowledge.firepit.3"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "oven")),
				new EntryPageText("knowledge.firepit.4"));

		MineFantasyKnowledgeList.cooking_utensils.addPages(
				new EntryPageText("knowledge.cooking_utensils.1"),
				new EntryPageRecipeAnvil(CraftingManagerAnvil.getRecipeByName(modId, "cake_tin")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "pie_tray_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "pie_tray")));

		MineFantasyKnowledgeList.salt.addPages(
				new EntryPageText("knowledge.salt.1"),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "salt")));

		MineFantasyKnowledgeList.jug.addPages(
				new EntryPageText("knowledge.jug.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "jug_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "jug_empty")),
				new EntryPageText("knowledge.jug.2"),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.JUG_WATER_RECIPE),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.JUG_MILK_RECIPE));

		MineFantasyKnowledgeList.generic_meat.addPages(
				new EntryPageText("knowledge.generic_meat.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "generic_meat_uncooked")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "generic_meat_strip_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "generic_meat_strip_cooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "generic_meat_cooked")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "generic_meat_strip_cooked")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "generic_meat_chunk_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "generic_meat_chunk_cooked")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "generic_meat_chunk_cooked")),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "generic_meat_mince_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "generic_meat_mince_cooked")),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "generic_meat_mince_cooked")));

		MineFantasyKnowledgeList.wild_meat.addPages(
				new EntryPageText("knowledge.wild_meat.1"),
				new EntryPageRoast(CraftingManagerRoast.getRecipesByName(modId, "horse_cooked", "wolf_cooked")));

		MineFantasyKnowledgeList.stew.addPages(
				new EntryPageText("knowledge.stew.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "stew")));

		MineFantasyKnowledgeList.jerky.addPages(
				new EntryPageText("knowledge.jerky.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "jerky")));

		MineFantasyKnowledgeList.sausage.addPages(
				new EntryPageText("knowledge.sausage.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "guts")),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "breadcrumbs")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "saussage_raw")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "saussage_cooked")));

		MineFantasyKnowledgeList.sandwitch.addPages(
				new EntryPageText("knowledge.sandwitch.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "bread_slice")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "sandwitch_meat")));

		MineFantasyKnowledgeList.sandwitch_big.addPages(
				new EntryPageText("knowledge.sandwitch_big.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "sandwitch_big")));

		MineFantasyKnowledgeList.meatpie.addPages(
				new EntryPageText("knowledge.meatpie.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "pie_meat_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "pie_meat_cooked")),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_MEAT_RECIPE));

		MineFantasyKnowledgeList.shepard_pie.addPages(
				new EntryPageText("knowledge.shepard_pie.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "pie_shepard_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "pie_shepard_cooked")),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_SHEPARDS_RECIPE));

		MineFantasyKnowledgeList.bread.addPages(
				new EntryPageText("knowledge.bread.1"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "flour")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "dough")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "breadroll")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "raw_bread")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "bread")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "pastry")),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "breadcrumbs")),
				new EntryPageText("knowledge.bread.other"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "pie_pumpkin_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "pie_pumpkin_cooked")),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_PUMPKIN_RECIPE),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "cake_simple_raw")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "cake_simple_uniced")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "cake")));

		MineFantasyKnowledgeList.oats.addPages(
				new EntryPageText("knowledge.oats.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "oats")));

		MineFantasyKnowledgeList.berry.addPages(assembleImgPage("berry"));

		MineFantasyKnowledgeList.icing.addPages(
				new EntryPageText("knowledge.icing.1"),
				new EntryPageGrind(CraftingManagerQuern.getRecipeByName(modId, "sugar_pot")),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.SUGAR_POT_RECIPE),
				new EntryPageText("knowledge.icing.2"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "icing")));

		MineFantasyKnowledgeList.sweetroll.addPages(
				new EntryPageText("knowledge.sweetroll.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "sweetroll_raw")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "sweetroll_uniced")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "sweetroll")));

		MineFantasyKnowledgeList.cake.addPages(
				new EntryPageText("knowledge.cake.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "cake_raw")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "cake_uniced")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "cake_vanilla")));

		MineFantasyKnowledgeList.carrot_cake.addPages(
				new EntryPageText("knowledge.carrot_cake.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "cake_carrot_raw")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "cake_carrot_uniced")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "cake_carrot")));

		MineFantasyKnowledgeList.chocolate_cake.addPages(
				new EntryPageText("knowledge.chocolate_cake.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "chocolate")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "cake_choc_raw")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "cake_choc_uniced")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "cake_chocolate")));

		MineFantasyKnowledgeList.black_forest_cake.addPages(
				new EntryPageText("knowledge.black_forest_cake.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "chocolate")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "cake_bf_raw")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "cake_bf_uniced")));

		MineFantasyKnowledgeList.berry_pie.addPages(
				new EntryPageText("knowledge.berry_pie.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "pie_berry_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "pie_berry_cooked")),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_BERRY_RECIPE));

		MineFantasyKnowledgeList.apple_pie.addPages(
				new EntryPageText("knowledge.apple_pie.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "pie_apple_uncooked")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "pie_apple_cooked")),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.PIE_APPLE_RECIPE));

		MineFantasyKnowledgeList.eclair.addPages(
				new EntryPageText("knowledge.eclair.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "eclair_raw")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "eclair_uniced")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "chocolate")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "eclair_empty")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "custard")),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "eclair")));

		MineFantasyKnowledgeList.cheese.addPages(
				new EntryPageText("knowledge.cheese.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "curds")),
				new EntryPageRoast(CraftingManagerRoast.getRecipeByName(modId, "cheese_pot")),
				new EntryPageRecipeBase(MineFantasyKnowledgeList.CHEESE_WHEEL_RECIPE));

		MineFantasyKnowledgeList.cheese_roll.addPages(
				new EntryPageText("knowledge.cheese_roll.1"),
				new EntryPageRecipeKitchenBench(CraftingManagerKitchenBench.getRecipeByName(modId, "cheese_roll")));

		MineFantasyKnowledgeList.bandage.addPages(
				new EntryPageText("knowledge.bandage.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bandage_crude")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bandage_crude-2")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bandage_crude-3")),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bandage_wool")));

		MineFantasyKnowledgeList.bandage_advanced.addPages(
				new EntryPageText("knowledge.bandage_advanced.1"),
				new EntryPageRecipeCarpenter(CraftingManagerCarpenter.getRecipeByName(modId, "bandage_tough")));

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
					new EntryPageRecipeBloom(CraftingManagerBloomery.getRecipeByName(MineFantasyReforged.MOD_ID, orename + "_bar"))};
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
