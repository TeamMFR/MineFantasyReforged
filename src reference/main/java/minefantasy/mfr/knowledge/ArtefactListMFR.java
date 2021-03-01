package minefantasy.mfr.mechanics.knowledge;

import minefantasy.mfr.mechanics.knowledge.InformationBase;
import minefantasy.mfr.mechanics.knowledge.ResearchArtefacts;
import minefantasy.mfr.init.*;
import minefantasy.mfr.item.ItemArtefact;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ArtefactListMFR {
    public static void init() {
        ((ItemArtefact) ComponentListMFR.artefacts).registerAll();
        addArtisanry();
        addConstruction();
        addProvisioning();
        addEngineering();
    }

    private static void addEngineering() {
        add(KnowledgeListMFR.blackpowder, ComponentListMFR.nitre, ComponentListMFR.sulfur, Items.COAL, Items.GUNPOWDER);
        add(KnowledgeListMFR.advblackpowder, Items.GLOWSTONE_DUST, Items.REDSTONE);
        add(KnowledgeListMFR.tungsten, ComponentListMFR.oreTungsten, BlockListMFR.ORE_TUNGSTEN);
        add(KnowledgeListMFR.coke, Items.COAL, Items.REDSTONE);
        add(KnowledgeListMFR.spyglass, ComponentListMFR.bronze_gears, Blocks.GLASS);
        add(KnowledgeListMFR.parachute, Items.FEATHER, Blocks.WOOL);
        add(KnowledgeListMFR.syringe, Items.POTIONITEM);
        add(KnowledgeListMFR.engTanner, ComponentListMFR.bronze_gears);
        add(KnowledgeListMFR.bombarrow, Items.FEATHER, ComponentListMFR.blackpowder);
        add(KnowledgeListMFR.bpress, ComponentListMFR.bronze_gears, Blocks.LEVER);
        add(KnowledgeListMFR.bombs, ComponentListMFR.blackpowder, Items.REDSTONE, Items.STRING);
        add(KnowledgeListMFR.shrapnel, Items.FLINT);
        add(KnowledgeListMFR.firebomb, ComponentListMFR.dragon_heart, Items.MAGMA_CREAM);
        add(KnowledgeListMFR.stickybomb, Items.SLIME_BALL);
        add(KnowledgeListMFR.mineCeramic, ComponentListMFR.blackpowder, Blocks.STONE_PRESSURE_PLATE);
        add(KnowledgeListMFR.bombIron, Items.IRON_INGOT);
        add(KnowledgeListMFR.mineIron, Items.IRON_INGOT);
        add(KnowledgeListMFR.bombObsidian, Blocks.OBSIDIAN);
        add(KnowledgeListMFR.mineObsidian, Blocks.OBSIDIAN);
        add(KnowledgeListMFR.bombCrystal, Items.DIAMOND);
        add(KnowledgeListMFR.mineCrystal, Items.DIAMOND);

        add(KnowledgeListMFR.crossbows, Items.STRING, ComponentListMFR.plank, Blocks.LEVER);
        add(KnowledgeListMFR.crossShaftAdvanced, ComponentListMFR.tungsten_gears);
        add(KnowledgeListMFR.crossHeadAdvanced, ComponentListMFR.tungsten_gears);
        add(KnowledgeListMFR.crossAmmo, ComponentListMFR.tungsten_gears);
        add(KnowledgeListMFR.crossScope, ToolListMFR.spyglass);
        add(KnowledgeListMFR.crossBayonet, CustomToolListMFR.standard_dagger);
    }

    private static void addProvisioning() {
        add(KnowledgeListMFR.jerky, FoodListMFR.generic_meat_uncooked);
        add(KnowledgeListMFR.saussage, FoodListMFR.generic_meat_uncooked, FoodListMFR.guts);
        add(KnowledgeListMFR.sandwitch, FoodListMFR.generic_meat_uncooked, FoodListMFR.cheese_slice, Items.BREAD);
        add(KnowledgeListMFR.sandwitchBig, FoodListMFR.generic_meat_uncooked, FoodListMFR.cheese_slice, Items.BREAD);

        add(KnowledgeListMFR.meatpie, FoodListMFR.generic_meat_uncooked, FoodListMFR.pastry);
        add(KnowledgeListMFR.shepardpie, FoodListMFR.generic_meat_uncooked, Items.POTATO, FoodListMFR.pastry);
        add(KnowledgeListMFR.berrypie, FoodListMFR.berries, FoodListMFR.pastry);
        add(KnowledgeListMFR.applepie, Items.APPLE, FoodListMFR.pastry);

        add(KnowledgeListMFR.sweetroll, Items.SUGAR, FoodListMFR.berries, FoodListMFR.sugarpot);
        add(KnowledgeListMFR.eclair, Items.EGG, new ItemStack(Items.DYE, 1, 3), FoodListMFR.pastry);
        add(KnowledgeListMFR.cheeseroll, Items.BREAD, FoodListMFR.cheese_slice);

        add(KnowledgeListMFR.cake, FoodListMFR.flour, Items.EGG);
        add(KnowledgeListMFR.carrotcake, FoodListMFR.flour, Items.EGG, Items.CARROT);
        add(KnowledgeListMFR.chococake, FoodListMFR.flour, Items.EGG, new ItemStack(Items.DYE, 1, 3));
        add(KnowledgeListMFR.bfcake, FoodListMFR.flour, Items.EGG, new ItemStack(Items.DYE, 1, 3),
                FoodListMFR.berriesJuicy);

        add(KnowledgeListMFR.bandageadv, Blocks.WOOL, Items.LEATHER);
    }

    private static void addConstruction() {
        add(KnowledgeListMFR.refined_planks, ComponentListMFR.nail);
        add(KnowledgeListMFR.clay_wall, Items.CLAY_BALL, ComponentListMFR.nail);
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
        add(KnowledgeListMFR.coalflux, Items.COAL, ComponentListMFR.flux);
        add(KnowledgeListMFR.smeltIron, Blocks.IRON_ORE);
        add(KnowledgeListMFR.crucible2, ComponentListMFR.fireclay);
        add(KnowledgeListMFR.blastfurn, Items.IRON_INGOT, Blocks.IRON_ORE, Blocks.FURNACE, BlockListMFR.BLOOMERY,
                BlockListMFR.LIMESTONE, ComponentListMFR.kaolinite);
        add(KnowledgeListMFR.bigfurn, Items.IRON_INGOT, Blocks.FURNACE, BlockListMFR.BLOOMERY, ComponentListMFR.kaolinite,
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
                        ComponentListMFR.flux_strong);
            }
            add(KnowledgeListMFR.smeltRedSteel, Items.BLAZE_POWDER, Items.GOLD_INGOT, Items.REDSTONE, black,
                    ComponentListMFR.flux_strong);
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
        add(KnowledgeListMFR.smeltDragonforge, ComponentListMFR.dragon_heart);

        add(KnowledgeListMFR.craftOrnate, new ItemStack(Items.DYE, 1, 4));

        add(KnowledgeListMFR.arrowsBodkin, Items.FEATHER);
        add(KnowledgeListMFR.arrowsBroad, Items.FEATHER, Items.FLINT);

        add(KnowledgeListMFR.repair_basic, Items.LEATHER, Items.FLINT, ComponentListMFR.nail);
        add(KnowledgeListMFR.repair_advanced, BlockListMFR.REPAIR_BASIC, Items.SLIME_BALL, Items.STRING);
        add(KnowledgeListMFR.repair_ornate, Items.DIAMOND, Items.GOLD_INGOT, BlockListMFR.REPAIR_ADVANCED);
    }

    private static void add(InformationBase info, Object... artifacts) {
        for (Object artifact : artifacts) {
            ResearchArtefacts.addArtefact(artifact, info);
        }
    }
}
