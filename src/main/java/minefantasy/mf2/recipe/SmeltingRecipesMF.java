package minefantasy.mf2.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.crafting.refine.BloomRecipe;
import minefantasy.mf2.api.refine.BigFurnaceRecipes;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.config.ConfigHardcore;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SmeltingRecipesMF {
    public static void init() {
        ItemStack copper = ComponentListMF.bar("Copper");
        ItemStack tin = ComponentListMF.bar("Tin");
        ItemStack bronze = ComponentListMF.bar("Bronze");
        ItemStack iron = ComponentListMF.bar("Iron");
        ItemStack pigiron = ComponentListMF.bar("PigIron");
        ItemStack steel = ComponentListMF.bar("Steel");
        ItemStack diamond = ComponentListMF.bar("Encrusted");
        ItemStack tungsten = ComponentListMF.bar("Tungsten");
        ItemStack obsidian = ComponentListMF.bar("Obsidian");
        ItemStack black = ComponentListMF.bar("BlackSteel");
        ItemStack red = ComponentListMF.bar("RedSteel");
        ItemStack blue = ComponentListMF.bar("BlueSteel");
        ItemStack gold = ComponentListMF.bar("Gold");
        ItemStack silver = ComponentListMF.bar("Silver");
        ItemStack mithril = ComponentListMF.bar("Mithril");
        ItemStack adamant = ComponentListMF.bar("Adamantium");
        ItemStack mithium = ComponentListMF.bar("Mithium");
        ItemStack ignotumite = ComponentListMF.bar("Ignotumite");
        ItemStack enderforge = ComponentListMF.bar("Ender");

        KnowledgeListMF.reStone = MineFantasyAPI.addRatioAlloy(1, new ItemStack(BlockListMF.reinforced_stone, 4), 1,
                new Object[]{Blocks.stone, Blocks.stone, Blocks.stone, Blocks.stone, ComponentListMF.fireclay, iron,
                        ComponentListMF.obsidian_rock});
        if (ConfigHardcore.HCCreduceIngots) {
            BloomRecipe.addRecipe(new ItemStack(Blocks.iron_ore), iron);
            BloomRecipe.addRecipe(new ItemStack(Blocks.gold_ore), gold);

            MineFantasyAPI.addFurnaceRecipe(new ItemStack(Blocks.iron_ore), iron, 0);
            MineFantasyAPI.addFurnaceRecipe(new ItemStack(Blocks.gold_ore), gold, 0);

            if(ConfigHardcore.HCCRemoveCraft) {
              if (MineFantasyAPI.removeSmelting(Blocks.iron_ore) && MineFantasyAPI.removeSmelting(Blocks.gold_ore)) {
                  MFLogUtil.logDebug("Removed Ore Smelting (Hardcore Ingots)");
              } else {
                  MFLogUtil.logWarn("Failed to remove Ore smelting!");
              }
            }
        }

        refineRawOre(ComponentListMF.oreCopper, "copper");
        refineRawOre(ComponentListMF.oreTin, "tin");
        refineRawOre(ComponentListMF.oreIron, "iron");
        refineRawOre(ComponentListMF.oreSilver, "silver");
        refineRawOre(ComponentListMF.oreGold, "gold");

        refineRawOre(BlockListMF.oreCopper, "copper", 0.4F);
        refineRawOre(BlockListMF.oreTin, "tin", 0.5F);
        refineRawOre(BlockListMF.oreSilver, "silver", 0.9F);

        GameRegistry.addSmelting(BlockListMF.oreBorax, new ItemStack(ComponentListMF.flux_strong, 4), 0.25F);
        GameRegistry.addSmelting(BlockListMF.oreTungsten, new ItemStack(ComponentListMF.oreTungsten, 1), 0.25F);
        GameRegistry.addSmelting(BlockListMF.oreKaolinite, new ItemStack(ComponentListMF.kaolinite), 0.25F);
        GameRegistry.addSmelting(BlockListMF.oreNitre, new ItemStack(ComponentListMF.nitre, 4), 0.25F);
        GameRegistry.addSmelting(BlockListMF.oreSulfur, new ItemStack(ComponentListMF.sulfur, 4), 0.25F);
        GameRegistry.addSmelting(BlockListMF.oreClay, new ItemStack(Items.clay_ball, 4), 0.25F);

        // ALLOY
        KnowledgeListMF.bronze = MineFantasyAPI.addRatioAlloy(3, ComponentListMF.bar("bronze", 3),
                new Object[]{copper, copper, tin});

        KnowledgeListMF.obsidalloy = MineFantasyAPI.addRatioAlloy(1, obsidian, 1,
                new Object[]{steel, ComponentListMF.obsidian_rock, ComponentListMF.obsidian_rock,
                        ComponentListMF.obsidian_rock, ComponentListMF.obsidian_rock, ComponentListMF.flux_strong});
        KnowledgeListMF.black = MineFantasyAPI.addRatioAlloy(1, new ItemStack(ComponentListMF.ingots[6], 2), 1,
                new Object[]{steel, steel, bronze, bronze, ComponentListMF.obsidian_rock});

        KnowledgeListMF.wolframiteR = MineFantasyAPI.addRatioAlloy(1, tungsten, 1,
                new Object[]{Items.coal, Items.coal, Items.coal, Items.coal, ComponentListMF.oreTungsten,
                        ComponentListMF.flux_strong, ComponentListMF.flux_strong, ComponentListMF.flux_strong,
                        ComponentListMF.flux_strong});
        MineFantasyAPI.addRatioAlloy(1, tungsten, 1,
                new Object[]{Items.coal, Items.coal, Items.coal, Items.coal, BlockListMF.oreTungsten,
                        ComponentListMF.flux_strong, ComponentListMF.flux_strong, ComponentListMF.flux_strong,
                        ComponentListMF.flux_strong});

        if (!ConfigHardcore.HCCreduceIngots) {
            KnowledgeListMF.steel = MineFantasyAPI.addRatioAlloy(9, steel, 1, new Object[]{pigiron});
        }
        KnowledgeListMF.red = MineFantasyAPI.addRatioAlloy(1, new ItemStack(ComponentListMF.ingots[9]), 1,
                new Object[]{steel, gold, Items.redstone, ComponentListMF.flux_strong, Items.blaze_powder});

        KnowledgeListMF.blue = MineFantasyAPI.addRatioAlloy(1, new ItemStack(ComponentListMF.ingots[11]), 1,
                new Object[]{steel, silver, new ItemStack(Items.dye, 1, 4), ComponentListMF.flux_strong,
                        Items.blaze_powder});

        KnowledgeListMF.adamantium = MineFantasyAPI.addRatioAlloy(2, ComponentListMF.bar("adamantium", 2), 2,
                new Object[]{BlockListMF.oreMythic, gold, gold});

        KnowledgeListMF.mithril = MineFantasyAPI.addRatioAlloy(2, ComponentListMF.bar("mithril", 2),
                new Object[]{BlockListMF.oreMythic, silver, silver});

        KnowledgeListMF.ignotumite = MineFantasyAPI.addRatioAlloy(2, ignotumite, 3,
                new Object[]{adamant, adamant, Items.emerald, Items.blaze_powder});

        KnowledgeListMF.mithium = MineFantasyAPI.addRatioAlloy(2, mithium, 3,
                new Object[]{mithril, mithril, ComponentListMF.diamond_shards, Items.ghast_tear});

        KnowledgeListMF.enderforge = MineFantasyAPI.addRatioAlloy(2, enderforge, 3,
                new Object[]{adamant, mithril, Items.ender_pearl, Items.ender_pearl});

        MineFantasyAPI.addBlastFurnaceRecipe(ComponentListMF.iron_prep, pigiron);
        MineFantasyAPI.addBlastFurnaceRecipe(ComponentListMF.ingots[6], black);
        MineFantasyAPI.addBlastFurnaceRecipe(ComponentListMF.ingots[9], red);
        MineFantasyAPI.addBlastFurnaceRecipe(ComponentListMF.ingots[11], blue);
    }

    private static void refineRawOre(Item ore, String mname) {
        refineRawOre(ore, mname, 0F);
    }

    private static void refineRawOre(Block ore, String mname) {
        refineRawOre(ore, mname, 0F);
    }

    private static void refineRawOre(Block ore, String mname, float xp) {
        refineRawOre(Item.getItemFromBlock(ore), mname, xp);
    }

    private static void refineRawOre(Item ore, String mname, float xp) {
        ItemStack bar = ComponentListMF.bar(mname);
        ItemStack ingot = ComponentListMF.ingot(mname);
        if (ConfigHardcore.HCCreduceIngots) {
            BloomRecipe.addRecipe(ore, bar);
            if(!ConfigHardcore.HCCRemoveCraft) {
              GameRegistry.addSmelting(ore, ingot, xp);
            }
        } else {
            GameRegistry.addSmelting(ore, bar, xp);
        }
        BigFurnaceRecipes.addRecipe(new ItemStack(ore), bar, 0);
    }

}
