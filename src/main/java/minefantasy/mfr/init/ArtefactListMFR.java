package minefantasy.mfr.init;

import minefantasy.mfr.api.knowledge.InformationBase;
import minefantasy.mfr.api.knowledge.ResearchArtefacts;
import minefantasy.mfr.item.ItemArtefact;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ArtefactListMFR {
    public static void init() {
        addArtisanry();
        addConstruction();
        addProvisioning();
        addEngineering();
    }

    private static void addEngineering() {
        add(KnowledgeListMFR.blackpowder, ComponentListMFR.NITRE, ComponentListMFR.SULFUR, Items.COAL, Items.GUNPOWDER);
        add(KnowledgeListMFR.advblackpowder, Items.GLOWSTONE_DUST, Items.REDSTONE);
        add(KnowledgeListMFR.tungsten, ComponentListMFR.ORE_TUNGSTEN, BlockListMFR.ORE_TUNGSTEN);
        add(KnowledgeListMFR.coke, Items.COAL, Items.REDSTONE);
        add(KnowledgeListMFR.spyglass, ComponentListMFR.BRONZE_GEARS, Blocks.GLASS);
        add(KnowledgeListMFR.parachute, Items.FEATHER, Blocks.WOOL);
        add(KnowledgeListMFR.syringe, Items.POTIONITEM);
        add(KnowledgeListMFR.engTanner, ComponentListMFR.BRONZE_GEARS);
        add(KnowledgeListMFR.bombarrow, Items.FEATHER, ComponentListMFR.BLACKPOWDER);
        add(KnowledgeListMFR.bpress, ComponentListMFR.BRONZE_GEARS, Blocks.LEVER);
        add(KnowledgeListMFR.bombs, ComponentListMFR.BLACKPOWDER, Items.REDSTONE, Items.STRING);
        add(KnowledgeListMFR.shrapnel, Items.FLINT);
        add(KnowledgeListMFR.firebomb, ComponentListMFR.DRAGON_HEART, Items.MAGMA_CREAM);
        add(KnowledgeListMFR.stickybomb, Items.SLIME_BALL);
        add(KnowledgeListMFR.mineCeramic, ComponentListMFR.BLACKPOWDER, Blocks.STONE_PRESSURE_PLATE);
        add(KnowledgeListMFR.bombIron, Items.IRON_INGOT);
        add(KnowledgeListMFR.mineIron, Items.IRON_INGOT);
        add(KnowledgeListMFR.bombObsidian, Blocks.OBSIDIAN);
        add(KnowledgeListMFR.mineObsidian, Blocks.OBSIDIAN);
        add(KnowledgeListMFR.bombCrystal, Items.DIAMOND);
        add(KnowledgeListMFR.mineCrystal, Items.DIAMOND);

        add(KnowledgeListMFR.crossbows, Items.STRING, ComponentListMFR.PLANK, Blocks.LEVER);
        add(KnowledgeListMFR.crossShaftAdvanced, ComponentListMFR.TUNGSTEN_GEARS);
        add(KnowledgeListMFR.crossHeadAdvanced, ComponentListMFR.TUNGSTEN_GEARS);
        add(KnowledgeListMFR.crossAmmo, ComponentListMFR.TUNGSTEN_GEARS);
        add(KnowledgeListMFR.crossScope, ToolListMFR.SPYGLASS);
        add(KnowledgeListMFR.crossBayonet, CustomToolListMFR.STANDARD_DAGGER);
    }

    private static void addProvisioning() {
        add(KnowledgeListMFR.jerky, FoodListMFR.GENERIC_MEAT_UNCOOKED);
        add(KnowledgeListMFR.saussage, FoodListMFR.GENERIC_MEAT_UNCOOKED, FoodListMFR.GUTS);
        add(KnowledgeListMFR.sandwitch, FoodListMFR.GENERIC_MEAT_UNCOOKED, FoodListMFR.CHEESE_SLICE, Items.BREAD);
        add(KnowledgeListMFR.sandwitchBig, FoodListMFR.GENERIC_MEAT_UNCOOKED, FoodListMFR.CHEESE_SLICE, Items.BREAD);

        add(KnowledgeListMFR.meatpie, FoodListMFR.GENERIC_MEAT_UNCOOKED, FoodListMFR.PASTRY);
        add(KnowledgeListMFR.shepardpie, FoodListMFR.GENERIC_MEAT_UNCOOKED, Items.POTATO, FoodListMFR.PASTRY);
        add(KnowledgeListMFR.berrypie, FoodListMFR.BERRIES, FoodListMFR.PASTRY);
        add(KnowledgeListMFR.applepie, Items.APPLE, FoodListMFR.PASTRY);

        add(KnowledgeListMFR.sweetroll, Items.SUGAR, FoodListMFR.BERRIES, FoodListMFR.SUGAR_POT);
        add(KnowledgeListMFR.eclair, Items.EGG, new ItemStack(Items.DYE, 1, 3), FoodListMFR.PASTRY);
        add(KnowledgeListMFR.cheeseroll, Items.BREAD, FoodListMFR.CHEESE_SLICE);

        add(KnowledgeListMFR.cake, FoodListMFR.FLOUR, Items.EGG);
        add(KnowledgeListMFR.carrotcake, FoodListMFR.FLOUR, Items.EGG, Items.CARROT);
        add(KnowledgeListMFR.chococake, FoodListMFR.FLOUR, Items.EGG, new ItemStack(Items.DYE, 1, 3));
        add(KnowledgeListMFR.bfcake, FoodListMFR.FLOUR, Items.EGG, new ItemStack(Items.DYE, 1, 3),
                FoodListMFR.BERRIES_JUICY);

        add(KnowledgeListMFR.bandageadv, Blocks.WOOL, Items.LEATHER);
    }

    private static void addConstruction() {
        add(KnowledgeListMFR.refined_planks, ComponentListMFR.NAIL);
        add(KnowledgeListMFR.clay_wall, Items.CLAY_BALL, ComponentListMFR.NAIL);
        add(KnowledgeListMFR.paint_brush, Blocks.WOOL);
        add(KnowledgeListMFR.decorated_stone, Items.IRON_INGOT, BlockListMFR.REINFORCED_STONE);
        add(KnowledgeListMFR.bed_roll, Items.BED);
        add(KnowledgeListMFR.ammo_box, Blocks.CHEST);
        add(KnowledgeListMFR.big_box, Blocks.CHEST);

    }

    private static void addArtisanry() {
        for (ItemStack copper : OreDictionary.getOres("ingotCopper")) {
            for (ItemStack tin : OreDictionary.getOres("ingotTin")) {
                add(KnowledgeListMFR.smeltBronze, copper, tin);
            }
        }
        add(KnowledgeListMFR.coalflux, Items.COAL, ComponentListMFR.FLUX);
        add(KnowledgeListMFR.smeltIron, Blocks.IRON_ORE);
        add(KnowledgeListMFR.crucible2, ComponentListMFR.FIRECLAY);
        add(KnowledgeListMFR.blastfurn, Items.IRON_INGOT, Blocks.IRON_ORE, Blocks.FURNACE, BlockListMFR.BLOOMERY,
                BlockListMFR.LIMESTONE, ComponentListMFR.KAOLINITE);
        add(KnowledgeListMFR.bigfurn, Items.IRON_INGOT, Blocks.FURNACE, BlockListMFR.BLOOMERY, ComponentListMFR.KAOLINITE,
                Items.COAL);
        for (ItemStack pig : OreDictionary.getOres("ingotPigIron")) {
            add(KnowledgeListMFR.smeltSteel, pig);
        }
        for (ItemStack steel : OreDictionary.getOres("ingotSteel")) {
            add(KnowledgeListMFR.encrusted, steel, Items.DIAMOND);
            add(KnowledgeListMFR.obsidian, steel, Blocks.OBSIDIAN);
            for (ItemStack bronze : OreDictionary.getOres("ingotBronze")) {
                add(KnowledgeListMFR.smeltBlackSteel, Blocks.OBSIDIAN, bronze, steel);
            }
        }
        for (ItemStack black : OreDictionary.getOres("ingotBlackSteel")) {
            for (ItemStack silver : OreDictionary.getOres("ingotSilver")) {
                add(KnowledgeListMFR.smeltBlueSteel, Items.BLAZE_POWDER, silver, black, new ItemStack(Items.DYE, 1, 4),
                        ComponentListMFR.FLUX_STRONG);
            }
            add(KnowledgeListMFR.smeltRedSteel, Items.BLAZE_POWDER, Items.GOLD_INGOT, Items.REDSTONE, black,
                    ComponentListMFR.FLUX_STRONG);
        }

        for (ItemStack silver : OreDictionary.getOres("ingotSilver")) {
            add(KnowledgeListMFR.smeltMithril, BlockListMFR.ORE_MYTHIC, silver);
        }
        add(KnowledgeListMFR.smeltAdamant, BlockListMFR.ORE_MYTHIC, Items.GOLD_INGOT);

        for (ItemStack mithril : OreDictionary.getOres("ingotMithril")) {
            add(KnowledgeListMFR.smeltMithium, mithril, Items.GHAST_TEAR, Items.DIAMOND);
            for (ItemStack adamant : OreDictionary.getOres("ingotAdamantium")) {
                add(KnowledgeListMFR.smeltIgnotumite, adamant, Items.EMERALD, Items.BLAZE_POWDER);
                add(KnowledgeListMFR.smeltEnderforge, adamant, mithril, Items.ENDER_PEARL);
            }
        }
        add(KnowledgeListMFR.craftArmourMedium, Items.LEATHER);
        add(KnowledgeListMFR.craftArmourHeavy, Items.LEATHER, Blocks.WOOL, Items.FEATHER);
        add(KnowledgeListMFR.smeltDragonforge, ComponentListMFR.DRAGON_HEART);

        add(KnowledgeListMFR.craftOrnate, new ItemStack(Items.DYE, 1, 4));

        add(KnowledgeListMFR.arrowsBodkin, Items.FEATHER);
        add(KnowledgeListMFR.arrowsBroad, Items.FEATHER, Items.FLINT);

        add(KnowledgeListMFR.repair_basic, Items.LEATHER, Items.FLINT, ComponentListMFR.NAIL);
        add(KnowledgeListMFR.repair_advanced, BlockListMFR.REPAIR_BASIC, Items.SLIME_BALL, Items.STRING);
        add(KnowledgeListMFR.repair_ornate, Items.DIAMOND, Items.GOLD_INGOT, BlockListMFR.REPAIR_ADVANCED);
    }

    private static void add(InformationBase info, Object... artifacts) {
        for (Object artifact : artifacts) {
            ResearchArtefacts.addArtefact(artifact, info);
        }
    }
}
