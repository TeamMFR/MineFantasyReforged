package minefantasy.mfr.recipe;

import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.refine.BloomRecipe;
import minefantasy.mfr.api.refine.BigFurnaceRecipes;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.knowledge.KnowledgeListMFR;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SmeltingRecipesMF {
    public static void init() {
        ItemStack copper = ComponentListMFR.bar("Copper");
        ItemStack tin = ComponentListMFR.bar("Tin");
        ItemStack bronze = ComponentListMFR.bar("Bronze");
        ItemStack iron = ComponentListMFR.bar("Iron");
        ItemStack pigiron = ComponentListMFR.bar("PigIron");
        ItemStack steel = ComponentListMFR.bar("Steel");
        ItemStack diamond = ComponentListMFR.bar("Encrusted");
        ItemStack tungsten = ComponentListMFR.bar("Tungsten");
        ItemStack obsidian = ComponentListMFR.bar("Obsidian");
        ItemStack black = ComponentListMFR.bar("BlackSteel");
        ItemStack red = ComponentListMFR.bar("RedSteel");
        ItemStack blue = ComponentListMFR.bar("BlueSteel");
        ItemStack gold = ComponentListMFR.bar("Gold");
        ItemStack silver = ComponentListMFR.bar("Silver");
        ItemStack mithril = ComponentListMFR.bar("Mithril");
        ItemStack adamant = ComponentListMFR.bar("Adamantium");
        ItemStack mithium = ComponentListMFR.bar("Mithium");
        ItemStack ignotumite = ComponentListMFR.bar("Ignotumite");
        ItemStack enderforge = ComponentListMFR.bar("Ender");

        KnowledgeListMFR.reStone = MineFantasyRebornAPI.addRatioAlloy(1, new ItemStack(BlockListMFR.REINFORCED_STONE, 4), 1,
                new Object[]{Blocks.STONE, Blocks.STONE, Blocks.STONE, Blocks.STONE, ComponentListMFR.fireclay, iron,
                        ComponentListMFR.obsidian_rock});
        if (ConfigHardcore.HCCreduceIngots) {
            if (MineFantasyRebornAPI.removeSmelting(Blocks.IRON_ORE) && MineFantasyRebornAPI.removeSmelting(Blocks.GOLD_ORE)) {
                MFRLogUtil.logDebug("Removed Ore Smelting (Hardcore Ingots");
            } else {
                MFRLogUtil.logWarn("Failed to remove Ore smelting!");
            }
            BloomRecipe.addRecipe(new ItemStack(Blocks.IRON_ORE), iron);
            BloomRecipe.addRecipe(new ItemStack(Blocks.GOLD_ORE), gold);

            MineFantasyRebornAPI.addFurnaceRecipe(new ItemStack(Blocks.IRON_ORE), iron, 0);
            MineFantasyRebornAPI.addFurnaceRecipe(new ItemStack(Blocks.GOLD_ORE), gold, 0);
        }

        refineRawOre(ComponentListMFR.oreCopper, copper);
        refineRawOre(ComponentListMFR.oreTin, tin);
        refineRawOre(ComponentListMFR.oreIron, iron);
        refineRawOre(ComponentListMFR.oreSilver, silver);
        refineRawOre(ComponentListMFR.oreGold, gold);

        refineRawOre(BlockListMFR.ORE_COPPER, copper, 0.4F);
        refineRawOre(BlockListMFR.ORE_TIN, tin, 0.5F);
        refineRawOre(BlockListMFR.ORE_SILVER, silver, 0.9F);

        GameRegistry.addSmelting(BlockListMFR.ORE_BORAX, new ItemStack(ComponentListMFR.flux_strong, 4), 0.25F);
        GameRegistry.addSmelting(BlockListMFR.ORE_TUNGSTEN, new ItemStack(ComponentListMFR.oreTungsten, 1), 0.25F);
        GameRegistry.addSmelting(BlockListMFR.ORE_KAOLINITE, new ItemStack(ComponentListMFR.kaolinite), 0.25F);
        GameRegistry.addSmelting(BlockListMFR.ORE_NITRE, new ItemStack(ComponentListMFR.nitre, 4), 0.25F);
        GameRegistry.addSmelting(BlockListMFR.ORE_SULFUR, new ItemStack(ComponentListMFR.sulfur, 4), 0.25F);
        GameRegistry.addSmelting(BlockListMFR.ORE_CLAY, new ItemStack(Items.CLAY_BALL, 4), 0.25F);

        // ALLOY
        KnowledgeListMFR.bronze = MineFantasyRebornAPI.addRatioAlloy(3, ComponentListMFR.bar("bronze", 3),
                new Object[]{copper, copper, tin});

        KnowledgeListMFR.obsidalloy = MineFantasyRebornAPI.addRatioAlloy(1, obsidian, 1,
                new Object[]{steel, ComponentListMFR.obsidian_rock, ComponentListMFR.obsidian_rock,
                        ComponentListMFR.obsidian_rock, ComponentListMFR.obsidian_rock, ComponentListMFR.flux_strong});
        KnowledgeListMFR.black = MineFantasyRebornAPI.addRatioAlloy(1, new ItemStack(ComponentListMFR.ingots[6], 2), 1,
                new Object[]{steel, steel, bronze, bronze, ComponentListMFR.obsidian_rock});

        KnowledgeListMFR.wolframiteR = MineFantasyRebornAPI.addRatioAlloy(1, tungsten, 1,
                new Object[]{Items.COAL, Items.COAL, Items.COAL, Items.COAL, ComponentListMFR.oreTungsten,
                        ComponentListMFR.flux_strong, ComponentListMFR.flux_strong, ComponentListMFR.flux_strong,
                        ComponentListMFR.flux_strong});
        MineFantasyRebornAPI.addRatioAlloy(1, tungsten, 1,
                new Object[]{Items.COAL, Items.COAL, Items.COAL, Items.COAL, BlockListMFR.ORE_TUNGSTEN,
                        ComponentListMFR.flux_strong, ComponentListMFR.flux_strong, ComponentListMFR.flux_strong,
                        ComponentListMFR.flux_strong});

        if (!ConfigHardcore.HCCreduceIngots) {
            KnowledgeListMFR.steel = MineFantasyRebornAPI.addRatioAlloy(9, steel, 1, new Object[]{pigiron});
        }
        KnowledgeListMFR.red = MineFantasyRebornAPI.addRatioAlloy(1, new ItemStack(ComponentListMFR.ingots[9]), 1,
                new Object[]{steel, gold, Items.REDSTONE, ComponentListMFR.flux_strong, Items.BLAZE_POWDER});

        KnowledgeListMFR.blue = MineFantasyRebornAPI.addRatioAlloy(1, new ItemStack(ComponentListMFR.ingots[11]), 1,
                new Object[]{steel, silver, new ItemStack(Items.DYE, 1, 4), ComponentListMFR.flux_strong,
                        Items.BLAZE_POWDER});

        KnowledgeListMFR.adamantium = MineFantasyRebornAPI.addRatioAlloy(2, ComponentListMFR.bar("adamantium", 2), 2,
                new Object[]{BlockListMFR.ORE_MYTHIC, gold, gold});

        KnowledgeListMFR.mithril = MineFantasyRebornAPI.addRatioAlloy(2, ComponentListMFR.bar("mithril", 2),
                new Object[]{BlockListMFR.ORE_MYTHIC, silver, silver});

        KnowledgeListMFR.ignotumite = MineFantasyRebornAPI.addRatioAlloy(2, ignotumite, 3,
                new Object[]{adamant, adamant, Items.EMERALD, Items.BLAZE_POWDER});

        KnowledgeListMFR.mithium = MineFantasyRebornAPI.addRatioAlloy(2, mithium, 3,
                new Object[]{mithril, mithril, ComponentListMFR.diamond_shards, Items.GHAST_TEAR});

        KnowledgeListMFR.enderforge = MineFantasyRebornAPI.addRatioAlloy(2, enderforge, 3,
                new Object[]{adamant, mithril, Items.ENDER_PEARL, Items.ENDER_PEARL});

        MineFantasyRebornAPI.addBlastFurnaceRecipe(ComponentListMFR.iron_prep, ComponentListMFR.bar("PigIron"));
        MineFantasyRebornAPI.addBlastFurnaceRecipe(ComponentListMFR.ingots[6], black);
        MineFantasyRebornAPI.addBlastFurnaceRecipe(ComponentListMFR.ingots[9], red);
        MineFantasyRebornAPI.addBlastFurnaceRecipe(ComponentListMFR.ingots[11], blue);
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
