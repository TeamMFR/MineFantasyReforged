package minefantasy.mfr.recipe;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.refine.BigFurnaceRecipes;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyKnowledgeList;
import minefantasy.mfr.init.MineFantasyMaterials;
import minefantasy.mfr.recipe.refine.BloomRecipe;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SmeltingRecipesMF {
	public static void init() {
		ItemStack copper = MineFantasyItems.bar(MineFantasyMaterials.Names.COPPER);
		ItemStack tin = MineFantasyItems.bar(MineFantasyMaterials.Names.TIN);
		ItemStack bronze = MineFantasyItems.bar(MineFantasyMaterials.Names.BRONZE);
		ItemStack iron = MineFantasyItems.bar(MineFantasyMaterials.Names.IRON);
		ItemStack pigiron = MineFantasyItems.bar(MineFantasyMaterials.Names.PIG_IRON);
		ItemStack steel = MineFantasyItems.bar(MineFantasyMaterials.Names.STEEL);
		ItemStack diamond = MineFantasyItems.bar(MineFantasyMaterials.Names.ENCRUSTED);
		ItemStack tungsten = MineFantasyItems.bar(MineFantasyMaterials.Names.TUNGSTEN);
		ItemStack obsidian = MineFantasyItems.bar(MineFantasyMaterials.Names.OBSIDIAN);
		ItemStack black = MineFantasyItems.bar(MineFantasyMaterials.Names.BLACK_STEEL);
		ItemStack red = MineFantasyItems.bar(MineFantasyMaterials.Names.RED_STEEL);
		ItemStack blue = MineFantasyItems.bar(MineFantasyMaterials.Names.BLUE_STEEL);
		ItemStack gold = MineFantasyItems.bar(MineFantasyMaterials.Names.GOLD);
		ItemStack silver = MineFantasyItems.bar(MineFantasyMaterials.Names.SILVER);
		ItemStack mithril = MineFantasyItems.bar(MineFantasyMaterials.Names.MITHRIL);
		ItemStack adamant = MineFantasyItems.bar(MineFantasyMaterials.Names.ADAMANTIUM);
		ItemStack mithium = MineFantasyItems.bar(MineFantasyMaterials.Names.MITHIUM);
		ItemStack ignotumite = MineFantasyItems.bar(MineFantasyMaterials.Names.IGNOTUMITE);
		ItemStack enderforge = MineFantasyItems.bar(MineFantasyMaterials.Names.ENDER);

		//        KnowledgeListMFR.reStone = MineFantasyRebornAPI.addRatioAlloy(1, new ItemStack(MineFantasyBlocks.REINFORCED_STONE, 4), 1,
		//                new Object[]{Blocks.STONE, Blocks.STONE, Blocks.STONE, Blocks.STONE, ComponentListMFR.FIRECLAY, iron,
		//                        ComponentListMFR.OBSIDIAN_ROCK});

		if (ConfigHardcore.HCCreduceIngots) {
			if (MineFantasyRebornAPI.removeSmelting(Blocks.IRON_ORE) && MineFantasyRebornAPI.removeSmelting(Blocks.GOLD_ORE)) {
				MineFantasyReborn.LOG.debug("Removed Ore Smelting (Hardcore Ingots");
			} else {
				MineFantasyReborn.LOG.warn("Failed to remove Ore smelting!");
			}
			BloomRecipe.addRecipe(new ItemStack(Blocks.IRON_ORE), iron);
			BloomRecipe.addRecipe(new ItemStack(Blocks.GOLD_ORE), gold);

			MineFantasyRebornAPI.addFurnaceRecipe(new ItemStack(Blocks.IRON_ORE), iron, 0);
			MineFantasyRebornAPI.addFurnaceRecipe(new ItemStack(Blocks.GOLD_ORE), gold, 0);
		}

		refineRawOre(MineFantasyItems.ORE_COPPER, copper);
		refineRawOre(MineFantasyItems.ORE_TIN, tin);
		refineRawOre(MineFantasyItems.ORE_IRON, iron);
		refineRawOre(MineFantasyItems.ORE_SILVER, silver);
		refineRawOre(MineFantasyItems.ORE_GOLD, gold);

		refineRawOre(MineFantasyBlocks.COPPER_ORE, copper, 0.4F);
		refineRawOre(MineFantasyBlocks.TIN_ORE, tin, 0.5F);
		refineRawOre(MineFantasyBlocks.SILVER_ORE, silver, 0.9F);

		GameRegistry.addSmelting(MineFantasyBlocks.BORAX_ORE, new ItemStack(MineFantasyItems.FLUX_STRONG, 4), 0.25F);
		GameRegistry.addSmelting(MineFantasyBlocks.TUNGSTEN_ORE, new ItemStack(MineFantasyItems.ORE_TUNGSTEN, 1), 0.25F);
		GameRegistry.addSmelting(MineFantasyBlocks.KAOLINITE_ORE, new ItemStack(MineFantasyItems.KAOLINITE), 0.25F);
		GameRegistry.addSmelting(MineFantasyBlocks.NITRE_ORE, new ItemStack(MineFantasyItems.NITRE, 4), 0.25F);
		GameRegistry.addSmelting(MineFantasyBlocks.SULFUR_ORE, new ItemStack(MineFantasyItems.SULFUR, 4), 0.25F);
		GameRegistry.addSmelting(MineFantasyBlocks.CLAY_ORE, new ItemStack(Items.CLAY_BALL, 4), 0.25F);

		// ALLOY
		MineFantasyKnowledgeList.bronze = MineFantasyRebornAPI.addRatioAlloy(3, MineFantasyItems.bar("bronze", 3),
				new Object[] {copper, copper, tin});

		MineFantasyKnowledgeList.obsidalloy = MineFantasyRebornAPI.addRatioAlloy(1, obsidian, 1,
				new Object[] {steel, MineFantasyItems.OBSIDIAN_ROCK, MineFantasyItems.OBSIDIAN_ROCK,
						MineFantasyItems.OBSIDIAN_ROCK, MineFantasyItems.OBSIDIAN_ROCK, MineFantasyItems.FLUX_STRONG});
		MineFantasyKnowledgeList.black = MineFantasyRebornAPI.addRatioAlloy(1, new ItemStack(MineFantasyItems.BLACK_STEEL_WEAK_INGOT, 2), 1,
				new Object[] {steel, steel, bronze, bronze, MineFantasyItems.OBSIDIAN_ROCK});

		MineFantasyKnowledgeList.wolframiteR = MineFantasyRebornAPI.addRatioAlloy(1, tungsten, 1,
				new Object[] {Items.COAL, Items.COAL, Items.COAL, Items.COAL, MineFantasyItems.ORE_TUNGSTEN,
						MineFantasyItems.FLUX_STRONG, MineFantasyItems.FLUX_STRONG, MineFantasyItems.FLUX_STRONG,
						MineFantasyItems.FLUX_STRONG});
		MineFantasyRebornAPI.addRatioAlloy(1, tungsten, 1,
				new Object[] {Items.COAL, Items.COAL, Items.COAL, Items.COAL, MineFantasyBlocks.TUNGSTEN_ORE,
						MineFantasyItems.FLUX_STRONG, MineFantasyItems.FLUX_STRONG, MineFantasyItems.FLUX_STRONG,
						MineFantasyItems.FLUX_STRONG});

		if (!ConfigHardcore.HCCreduceIngots) {
			MineFantasyKnowledgeList.steel = MineFantasyRebornAPI.addRatioAlloy(9, steel, 1, new Object[] {pigiron});
		}
		MineFantasyKnowledgeList.red = MineFantasyRebornAPI.addRatioAlloy(1, new ItemStack(MineFantasyItems.RED_STEEL_WEAK_INGOT), 1,
				new Object[] {steel, gold, Items.REDSTONE, MineFantasyItems.FLUX_STRONG, Items.BLAZE_POWDER});

		MineFantasyKnowledgeList.blue = MineFantasyRebornAPI.addRatioAlloy(1, new ItemStack(MineFantasyItems.BLACK_STEEL_WEAK_INGOT), 1,
				new Object[] {steel, silver, new ItemStack(Items.DYE, 1, 4), MineFantasyItems.FLUX_STRONG,
						Items.BLAZE_POWDER});

		MineFantasyKnowledgeList.adamantium = MineFantasyRebornAPI.addRatioAlloy(2, MineFantasyItems.bar("adamantium", 2), 2,
				new Object[] {MineFantasyBlocks.MYTHIC_ORE, gold, gold});

		MineFantasyKnowledgeList.mithril = MineFantasyRebornAPI.addRatioAlloy(2, MineFantasyItems.bar("mithril", 2),
				new Object[] {MineFantasyBlocks.MYTHIC_ORE, silver, silver});

		MineFantasyKnowledgeList.ignotumite = MineFantasyRebornAPI.addRatioAlloy(2, ignotumite, 3,
				new Object[] {adamant, adamant, Items.EMERALD, Items.BLAZE_POWDER});

		MineFantasyKnowledgeList.mithium = MineFantasyRebornAPI.addRatioAlloy(2, mithium, 3,
				new Object[] {mithril, mithril, MineFantasyItems.DIAMOND_SHARDS, Items.GHAST_TEAR});

		MineFantasyKnowledgeList.enderforge = MineFantasyRebornAPI.addRatioAlloy(2, enderforge, 3,
				new Object[] {adamant, mithril, Items.ENDER_PEARL, Items.ENDER_PEARL});

		MineFantasyRebornAPI.addBlastFurnaceRecipe(MineFantasyItems.PREPARED_IRON, MineFantasyItems.bar("PigIron"));
		MineFantasyRebornAPI.addBlastFurnaceRecipe(MineFantasyItems.BLACK_STEEL_WEAK_INGOT, black);
		MineFantasyRebornAPI.addBlastFurnaceRecipe(MineFantasyItems.RED_STEEL_WEAK_INGOT, red);
		MineFantasyRebornAPI.addBlastFurnaceRecipe(MineFantasyItems.BLUE_STEEL_WEAK_INGOT, blue);
	}

	private static void refineRawOre(Item ore, ItemStack bar) {
		refineRawOre(ore, bar, 0F);
	}

	private static void refineRawOre(Block ore, ItemStack bar) {
		refineRawOre(ore, bar, 0F);
	}

	private static void refineRawOre(Block ore, ItemStack bar, float xp) {
		refineRawOre(Item.getItemFromBlock(ore), bar, xp);
	}

	private static void refineRawOre(Item ore, ItemStack bar, float xp) {
		if (ConfigHardcore.HCCreduceIngots) {
			BloomRecipe.addRecipe(ore, bar);
		} else {
			GameRegistry.addSmelting(ore, bar, xp);
		}
		BigFurnaceRecipes.addRecipe(new ItemStack(ore), bar, 0);
	}

}
