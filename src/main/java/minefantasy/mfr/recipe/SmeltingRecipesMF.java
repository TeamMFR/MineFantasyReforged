package minefantasy.mfr.recipe;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.recipe.refine.BloomRecipe;
import minefantasy.mfr.api.refine.BigFurnaceRecipes;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.KnowledgeListMFR;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.material.MetalMaterial;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SmeltingRecipesMF {
    public static void init() {
        ItemStack copper = ComponentListMFR.bar(MetalMaterial.COPPER);
        ItemStack tin = ComponentListMFR.bar(MetalMaterial.TIN);
        ItemStack bronze = ComponentListMFR.bar(MetalMaterial.BRONZE);
        ItemStack iron = ComponentListMFR.bar(MetalMaterial.IRON);
        ItemStack pigiron = ComponentListMFR.bar(MetalMaterial.PIG_IRON);
        ItemStack steel = ComponentListMFR.bar(MetalMaterial.STEEL);
        ItemStack diamond = ComponentListMFR.bar(MetalMaterial.ENCRUSTED);
        ItemStack tungsten = ComponentListMFR.bar(MetalMaterial.TUNGSTEN);
        ItemStack obsidian = ComponentListMFR.bar(MetalMaterial.OBSIDIAN);
        ItemStack black = ComponentListMFR.bar(MetalMaterial.BLACK_STEEL);
        ItemStack red = ComponentListMFR.bar(MetalMaterial.RED_STEEL);
        ItemStack blue = ComponentListMFR.bar(MetalMaterial.BLUE_STEEL);
        ItemStack gold = ComponentListMFR.bar(MetalMaterial.GOLD);
        ItemStack silver = ComponentListMFR.bar(MetalMaterial.SILVER);
        ItemStack mithril = ComponentListMFR.bar(MetalMaterial.MITHRIL);
        ItemStack adamant = ComponentListMFR.bar(MetalMaterial.ADAMANTIUM);
        ItemStack mithium = ComponentListMFR.bar(MetalMaterial.MITHIUM);
        ItemStack ignotumite = ComponentListMFR.bar(MetalMaterial.IGNOTUMITE);
        ItemStack enderforge = ComponentListMFR.bar(MetalMaterial.ENDER);

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

        refineRawOre(ComponentListMFR.ORE_COPPER, copper);
        refineRawOre(ComponentListMFR.ORE_TIN, tin);
        refineRawOre(ComponentListMFR.ORE_IRON, iron);
        refineRawOre(ComponentListMFR.ORE_SILVER, silver);
        refineRawOre(ComponentListMFR.ORE_GOLD, gold);

        refineRawOre(MineFantasyBlocks.COPPER_ORE, copper, 0.4F);
        refineRawOre(MineFantasyBlocks.TIN_ORE, tin, 0.5F);
        refineRawOre(MineFantasyBlocks.SILVER_ORE, silver, 0.9F);

        GameRegistry.addSmelting(MineFantasyBlocks.BORAX_ORE, new ItemStack(ComponentListMFR.FLUX_STRONG, 4), 0.25F);
        GameRegistry.addSmelting(MineFantasyBlocks.TUNGSTEN_ORE, new ItemStack(ComponentListMFR.ORE_TUNGSTEN, 1), 0.25F);
        GameRegistry.addSmelting(MineFantasyBlocks.KAOLINITE_ORE, new ItemStack(ComponentListMFR.KAOLINITE), 0.25F);
        GameRegistry.addSmelting(MineFantasyBlocks.NITRE_ORE, new ItemStack(ComponentListMFR.NITRE, 4), 0.25F);
        GameRegistry.addSmelting(MineFantasyBlocks.SULFUR_ORE, new ItemStack(ComponentListMFR.SULFUR, 4), 0.25F);
        GameRegistry.addSmelting(MineFantasyBlocks.CLAY_ORE, new ItemStack(Items.CLAY_BALL, 4), 0.25F);

        // ALLOY
        KnowledgeListMFR.bronze = MineFantasyRebornAPI.addRatioAlloy(3, ComponentListMFR.bar("bronze", 3),
                new Object[]{copper, copper, tin});

        KnowledgeListMFR.obsidalloy = MineFantasyRebornAPI.addRatioAlloy(1, obsidian, 1,
                new Object[]{steel, ComponentListMFR.OBSIDIAN_ROCK, ComponentListMFR.OBSIDIAN_ROCK,
                        ComponentListMFR.OBSIDIAN_ROCK, ComponentListMFR.OBSIDIAN_ROCK, ComponentListMFR.FLUX_STRONG});
        KnowledgeListMFR.black = MineFantasyRebornAPI.addRatioAlloy(1, new ItemStack(ComponentListMFR.BLACK_STEEL_WEAK_INGOT, 2), 1,
                new Object[]{steel, steel, bronze, bronze, ComponentListMFR.OBSIDIAN_ROCK});

        KnowledgeListMFR.wolframiteR = MineFantasyRebornAPI.addRatioAlloy(1, tungsten, 1,
                new Object[]{Items.COAL, Items.COAL, Items.COAL, Items.COAL, ComponentListMFR.ORE_TUNGSTEN,
                        ComponentListMFR.FLUX_STRONG, ComponentListMFR.FLUX_STRONG, ComponentListMFR.FLUX_STRONG,
                        ComponentListMFR.FLUX_STRONG});
        MineFantasyRebornAPI.addRatioAlloy(1, tungsten, 1,
                new Object[]{Items.COAL, Items.COAL, Items.COAL, Items.COAL, MineFantasyBlocks.TUNGSTEN_ORE,
                        ComponentListMFR.FLUX_STRONG, ComponentListMFR.FLUX_STRONG, ComponentListMFR.FLUX_STRONG,
                        ComponentListMFR.FLUX_STRONG});

        if (!ConfigHardcore.HCCreduceIngots) {
            KnowledgeListMFR.steel = MineFantasyRebornAPI.addRatioAlloy(9, steel, 1, new Object[]{pigiron});
        }
        KnowledgeListMFR.red = MineFantasyRebornAPI.addRatioAlloy(1, new ItemStack(ComponentListMFR.RED_STEEL_WEAK_INGOT), 1,
                new Object[]{steel, gold, Items.REDSTONE, ComponentListMFR.FLUX_STRONG, Items.BLAZE_POWDER});

        KnowledgeListMFR.blue = MineFantasyRebornAPI.addRatioAlloy(1, new ItemStack(ComponentListMFR.BLACK_STEEL_WEAK_INGOT), 1,
                new Object[]{steel, silver, new ItemStack(Items.DYE, 1, 4), ComponentListMFR.FLUX_STRONG,
                        Items.BLAZE_POWDER});

        KnowledgeListMFR.adamantium = MineFantasyRebornAPI.addRatioAlloy(2, ComponentListMFR.bar("adamantium", 2), 2,
                new Object[]{MineFantasyBlocks.MYTHIC_ORE, gold, gold});

        KnowledgeListMFR.mithril = MineFantasyRebornAPI.addRatioAlloy(2, ComponentListMFR.bar("mithril", 2),
                new Object[]{MineFantasyBlocks.MYTHIC_ORE, silver, silver});

        KnowledgeListMFR.ignotumite = MineFantasyRebornAPI.addRatioAlloy(2, ignotumite, 3,
                new Object[]{adamant, adamant, Items.EMERALD, Items.BLAZE_POWDER});

        KnowledgeListMFR.mithium = MineFantasyRebornAPI.addRatioAlloy(2, mithium, 3,
                new Object[]{mithril, mithril, ComponentListMFR.DIAMOND_SHARDS, Items.GHAST_TEAR});

        KnowledgeListMFR.enderforge = MineFantasyRebornAPI.addRatioAlloy(2, enderforge, 3,
                new Object[]{adamant, mithril, Items.ENDER_PEARL, Items.ENDER_PEARL});

        MineFantasyRebornAPI.addBlastFurnaceRecipe(ComponentListMFR.IRON_PREP, ComponentListMFR.bar("PigIron"));
        MineFantasyRebornAPI.addBlastFurnaceRecipe(ComponentListMFR.BLACK_STEEL_WEAK_INGOT, black);
        MineFantasyRebornAPI.addBlastFurnaceRecipe(ComponentListMFR.RED_STEEL_WEAK_INGOT, red);
        MineFantasyRebornAPI.addBlastFurnaceRecipe(ComponentListMFR.BLUE_STEEL_WEAK_INGOT, blue);
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
