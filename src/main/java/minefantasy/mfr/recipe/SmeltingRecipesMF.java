package minefantasy.mfr.recipe;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.MineFantasyReforgedAPI;
import minefantasy.mfr.api.refine.BigFurnaceRecipes;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyKnowledgeList;
import minefantasy.mfr.init.MineFantasyMaterials;
import minefantasy.mfr.recipe.refine.BloomRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SmeltingRecipesMF {
	public static void init() {
		ItemStack copper = MineFantasyItems.bar(MineFantasyMaterials.Names.COPPER);
		ItemStack tin = MineFantasyItems.bar(MineFantasyMaterials.Names.TIN);
		ItemStack bronze = MineFantasyItems.bar(MineFantasyMaterials.Names.BRONZE);
		ItemStack iron = MineFantasyItems.bar(MineFantasyMaterials.Names.IRON);
		ItemStack pig_iron = MineFantasyItems.bar(MineFantasyMaterials.Names.PIG_IRON);
		ItemStack steel = MineFantasyItems.bar(MineFantasyMaterials.Names.STEEL);
		ItemStack diamond = MineFantasyItems.bar(MineFantasyMaterials.Names.ENCRUSTED);
		ItemStack tungsten = MineFantasyItems.bar(MineFantasyMaterials.Names.TUNGSTEN);
		ItemStack obsidian = MineFantasyItems.bar(MineFantasyMaterials.Names.OBSIDIAN);
		ItemStack black_steel = MineFantasyItems.bar(MineFantasyMaterials.Names.BLACK_STEEL);
		ItemStack red_steel = MineFantasyItems.bar(MineFantasyMaterials.Names.RED_STEEL);
		ItemStack blue_steel = MineFantasyItems.bar(MineFantasyMaterials.Names.BLUE_STEEL);
		ItemStack gold = MineFantasyItems.bar(MineFantasyMaterials.Names.GOLD);
		ItemStack silver = MineFantasyItems.bar(MineFantasyMaterials.Names.SILVER);
		ItemStack mithril = MineFantasyItems.bar(MineFantasyMaterials.Names.MITHRIL);
		ItemStack adamant = MineFantasyItems.bar(MineFantasyMaterials.Names.ADAMANTIUM);
		ItemStack mithium = MineFantasyItems.bar(MineFantasyMaterials.Names.MITHIUM);
		ItemStack ignotumite = MineFantasyItems.bar(MineFantasyMaterials.Names.IGNOTUMITE);
		ItemStack enderforge = MineFantasyItems.bar(MineFantasyMaterials.Names.ENDER);

		MineFantasyKnowledgeList.reinforced_stone_alloy = MineFantasyReforgedAPI.addRatioAlloy(1, new ItemStack(MineFantasyBlocks.REINFORCED_STONE, 4), 1,
				new Object[]{Blocks.STONE, Blocks.STONE, Blocks.STONE, Blocks.STONE, MineFantasyItems.FIRECLAY, iron,
						MineFantasyItems.OBSIDIAN_ROCK});

		if (ConfigHardcore.HCCreduceIngots) {
			if (MineFantasyReforgedAPI.removeSmelting(Blocks.IRON_ORE) && MineFantasyReforgedAPI.removeSmelting(Blocks.GOLD_ORE)) {
				MineFantasyReforged.LOG.debug("Removed Ore Smelting (Hardcore Ingots");
			} else {
				MineFantasyReforged.LOG.warn("Failed to remove Ore smelting!");
			}

			for (ItemStack ore: OreDictionary.getOres("oreIron")) {
				BloomRecipe.addRecipe(ore, iron);
			}
			for (ItemStack ore: OreDictionary.getOres("oreGold")) {
				BloomRecipe.addRecipe(ore, gold);
			}

			for (ItemStack ore: OreDictionary.getOres("oreIron")) {
				MineFantasyReforgedAPI.addFurnaceRecipe(ore, iron, 0);
			}
			for (ItemStack ore: OreDictionary.getOres("oreGold")) {
				MineFantasyReforgedAPI.addFurnaceRecipe(ore, gold, 0);
			}
		}

		refineRawOre(new ItemStack(MineFantasyItems.ORE_COPPER), copper);
		refineRawOre(new ItemStack(MineFantasyItems.ORE_TIN), tin);
		refineRawOre(new ItemStack(MineFantasyItems.ORE_IRON), iron);
		refineRawOre(new ItemStack(MineFantasyItems.ORE_SILVER), silver);
		refineRawOre(new ItemStack(MineFantasyItems.ORE_GOLD), gold);

		for (ItemStack ore: OreDictionary.getOres("oreCopper")) {
			refineRawOre(ore, copper);
		}
		for (ItemStack ore: OreDictionary.getOres("oreTin")) {
			refineRawOre(ore, tin);
		}
		for (ItemStack ore: OreDictionary.getOres("oreSilver")) {
			refineRawOre(ore, silver);
		}

		// ALLOY
		MineFantasyKnowledgeList.bronze_alloy = MineFantasyReforgedAPI.addRatioAlloy(3, MineFantasyItems.bar("bronze", 3),
				new Object[] {copper, copper, tin});

		MineFantasyKnowledgeList.obsidian_alloy = MineFantasyReforgedAPI.addRatioAlloy(1, obsidian, 1,
				new Object[] {steel, MineFantasyItems.OBSIDIAN_ROCK, MineFantasyItems.OBSIDIAN_ROCK,
						MineFantasyItems.OBSIDIAN_ROCK, MineFantasyItems.OBSIDIAN_ROCK, MineFantasyItems.FLUX_STRONG});
		MineFantasyKnowledgeList.black_steel_alloy = MineFantasyReforgedAPI.addRatioAlloy(1, new ItemStack(MineFantasyItems.BLACK_STEEL_WEAK_INGOT, 2), 1,
				new Object[] {steel, steel, bronze, bronze, MineFantasyItems.OBSIDIAN_ROCK});

		MineFantasyKnowledgeList.wolframite_raw_alloy = MineFantasyReforgedAPI.addRatioAlloy(1, tungsten, 1,
				new Object[] {Items.COAL, Items.COAL, Items.COAL, Items.COAL, MineFantasyItems.ORE_TUNGSTEN,
						MineFantasyItems.FLUX_STRONG, MineFantasyItems.FLUX_STRONG, MineFantasyItems.FLUX_STRONG,
						MineFantasyItems.FLUX_STRONG});
		MineFantasyReforgedAPI.addRatioAlloy(1, tungsten, 1,
				new Object[] {Items.COAL, Items.COAL, Items.COAL, Items.COAL, MineFantasyBlocks.TUNGSTEN_ORE,
						MineFantasyItems.FLUX_STRONG, MineFantasyItems.FLUX_STRONG, MineFantasyItems.FLUX_STRONG,
						MineFantasyItems.FLUX_STRONG});

		if (!ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.steel_alloy = MineFantasyReforgedAPI.addRatioAlloy(9, steel, 1, new Object[] {pig_iron});
		}
		MineFantasyKnowledgeList.red_steel_alloy = MineFantasyReforgedAPI.addRatioAlloy(1, new ItemStack(MineFantasyItems.RED_STEEL_WEAK_INGOT), 1,
				new Object[] {black_steel, gold, Items.REDSTONE, MineFantasyItems.FLUX_STRONG, Items.BLAZE_POWDER});

		MineFantasyKnowledgeList.blue_steel_alloy = MineFantasyReforgedAPI.addRatioAlloy(1, new ItemStack(MineFantasyItems.BLUE_STEEL_WEAK_INGOT), 1,
				new Object[] {black_steel, silver, new ItemStack(Items.DYE, 1, 4), MineFantasyItems.FLUX_STRONG,
						Items.BLAZE_POWDER});

		MineFantasyKnowledgeList.adamantium_alloy = MineFantasyReforgedAPI.addRatioAlloy(2, MineFantasyItems.bar("adamantium", 2), 2,
				new Object[] {MineFantasyBlocks.MYTHIC_ORE, gold, gold});

		MineFantasyKnowledgeList.mithril_alloy = MineFantasyReforgedAPI.addRatioAlloy(2, MineFantasyItems.bar("mithril", 2), 2,
				new Object[] {MineFantasyBlocks.MYTHIC_ORE, silver, silver});

		MineFantasyKnowledgeList.ignotumite_alloy = MineFantasyReforgedAPI.addRatioAlloy(2, ignotumite, 3,
				new Object[] {adamant, adamant, Items.EMERALD, Items.BLAZE_POWDER});

		MineFantasyKnowledgeList.mithium_alloy = MineFantasyReforgedAPI.addRatioAlloy(2, mithium, 3,
				new Object[] {mithril, mithril, MineFantasyItems.DIAMOND_SHARDS, Items.GHAST_TEAR});

		MineFantasyKnowledgeList.enderforge_alloy = MineFantasyReforgedAPI.addRatioAlloy(2, enderforge, 3,
				new Object[] {adamant, mithril, Items.ENDER_PEARL, Items.ENDER_PEARL});

		MineFantasyReforgedAPI.addBlastFurnaceRecipe(MineFantasyItems.PREPARED_IRON, pig_iron);
		MineFantasyReforgedAPI.addBlastFurnaceRecipe(MineFantasyItems.BLACK_STEEL_WEAK_INGOT, black_steel);
		MineFantasyReforgedAPI.addBlastFurnaceRecipe(MineFantasyItems.RED_STEEL_WEAK_INGOT, red_steel);
		MineFantasyReforgedAPI.addBlastFurnaceRecipe(MineFantasyItems.BLUE_STEEL_WEAK_INGOT, blue_steel);
	}

	private static void refineRawOre(ItemStack ore, ItemStack bar) {
		if (ConfigHardcore.HCCreduceIngots) {
			BloomRecipe.addRecipe(ore, bar);
		}
		BigFurnaceRecipes.addRecipe(ore, bar, 0);
	}
}
