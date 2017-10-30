package minefantasy.mf2.knowledge;

import minefantasy.mf2.api.knowledge.InformationBase;
import minefantasy.mf2.api.knowledge.ResearchArtefacts;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.item.ItemArtefact;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CustomToolListMF;
import minefantasy.mf2.item.list.ToolListMF;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ArtefactListMF {
    public static void init() {
        ((ItemArtefact) ComponentListMF.artefacts).registerAll();
        addArtisanry();
        addConstruction();
        addProvisioning();
        addEngineering();
    }

    private static void addEngineering() {
        add(KnowledgeListMF.blackpowder, ComponentListMF.nitre, ComponentListMF.sulfur, Items.coal, Items.gunpowder);
        add(KnowledgeListMF.advblackpowder, Items.glowstone_dust, Items.redstone);
        add(KnowledgeListMF.tungsten, ComponentListMF.oreTungsten, BlockListMF.oreTungsten);
        add(KnowledgeListMF.coke, Items.coal, Items.redstone);
        add(KnowledgeListMF.spyglass, ComponentListMF.bronze_gears, Blocks.glass);
        add(KnowledgeListMF.parachute, Items.feather, Blocks.wool);
        add(KnowledgeListMF.syringe, Items.potionitem);
        add(KnowledgeListMF.engTanner, ComponentListMF.bronze_gears);
        add(KnowledgeListMF.bombarrow, Items.feather, ComponentListMF.blackpowder);
        add(KnowledgeListMF.bpress, ComponentListMF.bronze_gears, Blocks.lever);
        add(KnowledgeListMF.bombs, ComponentListMF.blackpowder, Items.redstone, Items.string);
        add(KnowledgeListMF.shrapnel, Items.flint);
        add(KnowledgeListMF.firebomb, ComponentListMF.dragon_heart, Items.magma_cream);
        add(KnowledgeListMF.stickybomb, Items.slime_ball);
        add(KnowledgeListMF.mineCeramic, ComponentListMF.blackpowder, Blocks.stone_pressure_plate);
        add(KnowledgeListMF.bombIron, Items.iron_ingot);
        add(KnowledgeListMF.mineIron, Items.iron_ingot);
        add(KnowledgeListMF.bombObsidian, Blocks.obsidian);
        add(KnowledgeListMF.mineObsidian, Blocks.obsidian);
        add(KnowledgeListMF.bombCrystal, Items.diamond);
        add(KnowledgeListMF.mineCrystal, Items.diamond);

        add(KnowledgeListMF.crossbows, Items.string, ComponentListMF.plank, Blocks.lever);
        add(KnowledgeListMF.crossShaftAdvanced, ComponentListMF.tungsten_gears);
        add(KnowledgeListMF.crossHeadAdvanced, ComponentListMF.tungsten_gears);
        add(KnowledgeListMF.crossAmmo, ComponentListMF.tungsten_gears);
        add(KnowledgeListMF.crossScope, ToolListMF.spyglass);
        add(KnowledgeListMF.crossBayonet, CustomToolListMF.standard_dagger);
    }

    private static void addProvisioning() {
        add(KnowledgeListMF.jerky, FoodListMF.generic_meat_uncooked);
        add(KnowledgeListMF.saussage, FoodListMF.generic_meat_uncooked, FoodListMF.guts);
        add(KnowledgeListMF.sandwitch, FoodListMF.generic_meat_uncooked, FoodListMF.cheese_slice, Items.bread);
        add(KnowledgeListMF.sandwitchBig, FoodListMF.generic_meat_uncooked, FoodListMF.cheese_slice, Items.bread);

        add(KnowledgeListMF.meatpie, FoodListMF.generic_meat_uncooked, FoodListMF.pastry);
        add(KnowledgeListMF.shepardpie, FoodListMF.generic_meat_uncooked, Items.potato, FoodListMF.pastry);
        add(KnowledgeListMF.berrypie, FoodListMF.berries, FoodListMF.pastry);
        add(KnowledgeListMF.applepie, Items.apple, FoodListMF.pastry);

        add(KnowledgeListMF.sweetroll, Items.sugar, FoodListMF.berries, FoodListMF.sugarpot);
        add(KnowledgeListMF.eclair, Items.egg, new ItemStack(Items.dye, 1, 3), FoodListMF.pastry);
        add(KnowledgeListMF.cheeseroll, Items.bread, FoodListMF.cheese_slice);

        add(KnowledgeListMF.cake, FoodListMF.flour, Items.egg);
        add(KnowledgeListMF.carrotcake, FoodListMF.flour, Items.egg, Items.carrot);
        add(KnowledgeListMF.chococake, FoodListMF.flour, Items.egg, new ItemStack(Items.dye, 1, 3));
        add(KnowledgeListMF.bfcake, FoodListMF.flour, Items.egg, new ItemStack(Items.dye, 1, 3),
                FoodListMF.berriesJuicy);

        add(KnowledgeListMF.bandageadv, Blocks.wool, Items.leather);
    }

    private static void addConstruction() {
        add(KnowledgeListMF.refined_planks, ComponentListMF.nail);
        add(KnowledgeListMF.clay_wall, Items.clay_ball, ComponentListMF.nail);
        add(KnowledgeListMF.paint_brush, Blocks.wool);
        add(KnowledgeListMF.decorated_stone, Items.iron_ingot, BlockListMF.reinforced_stone);
        add(KnowledgeListMF.bed_roll, Items.bed);
        add(KnowledgeListMF.ammo_box, Blocks.chest);
        add(KnowledgeListMF.big_box, Blocks.chest);

    }

    private static void addArtisanry() {
        for (ItemStack copper : OreDictionary.getOres("ingotCopper")) {
            for (ItemStack tin : OreDictionary.getOres("ingotTin")) {
                add(KnowledgeListMF.smeltBronze, copper, tin);
            }
        }
        add(KnowledgeListMF.coalflux, Items.coal, ComponentListMF.flux);
        add(KnowledgeListMF.smeltIron, Blocks.iron_ore);
        add(KnowledgeListMF.crucible2, ComponentListMF.fireclay);
        add(KnowledgeListMF.blastfurn, Items.iron_ingot, Blocks.iron_ore, Blocks.furnace, BlockListMF.bloomery,
                BlockListMF.limestone, ComponentListMF.kaolinite);
        add(KnowledgeListMF.bigfurn, Items.iron_ingot, Blocks.furnace, BlockListMF.bloomery, ComponentListMF.kaolinite,
                Items.coal);
        for (ItemStack pig : OreDictionary.getOres("ingotPigIron")) {
            add(KnowledgeListMF.smeltSteel, pig);
        }
        for (ItemStack steel : OreDictionary.getOres("ingotSteel")) {
            add(KnowledgeListMF.encrusted, steel, Items.diamond);
            add(KnowledgeListMF.obsidian, steel, Blocks.obsidian);
            for (ItemStack bronze : OreDictionary.getOres("ingotBronze")) {
                add(KnowledgeListMF.smeltBlackSteel, Blocks.obsidian, bronze, steel);
            }
        }
        for (ItemStack black : OreDictionary.getOres("ingotBlackSteel")) {
            for (ItemStack silver : OreDictionary.getOres("ingotSilver")) {
                add(KnowledgeListMF.smeltBlueSteel, Items.blaze_powder, silver, black, new ItemStack(Items.dye, 1, 4),
                        ComponentListMF.flux_strong);
            }
            add(KnowledgeListMF.smeltRedSteel, Items.blaze_powder, Items.gold_ingot, Items.redstone, black,
                    ComponentListMF.flux_strong);
        }

        for (ItemStack silver : OreDictionary.getOres("ingotSilver")) {
            add(KnowledgeListMF.smeltMithril, BlockListMF.oreMythic, silver);
        }
        add(KnowledgeListMF.smeltAdamant, BlockListMF.oreMythic, Items.gold_ingot);

        for (ItemStack mithril : OreDictionary.getOres("ingotMithril")) {
            add(KnowledgeListMF.smeltMithium, mithril, Items.ghast_tear, Items.diamond);
            for (ItemStack adamant : OreDictionary.getOres("ingotAdamantium")) {
                add(KnowledgeListMF.smeltIgnotumite, adamant, Items.emerald, Items.blaze_powder);
                add(KnowledgeListMF.smeltEnderforge, adamant, mithril, Items.ender_pearl);
            }
        }
        add(KnowledgeListMF.craftArmourMedium, Items.leather);
        add(KnowledgeListMF.craftArmourHeavy, Items.leather, Blocks.wool, Items.feather);
        add(KnowledgeListMF.smeltDragonforge, ComponentListMF.dragon_heart);

        add(KnowledgeListMF.craftOrnate, new ItemStack(Items.dye, 1, 4));

        add(KnowledgeListMF.arrowsBodkin, Items.feather);
        add(KnowledgeListMF.arrowsBroad, Items.feather, Items.flint);

        add(KnowledgeListMF.repair_basic, Items.leather, Items.flint, ComponentListMF.nail);
        add(KnowledgeListMF.repair_advanced, BlockListMF.repair_basic, Items.slime_ball, Items.string);
        add(KnowledgeListMF.repair_ornate, Items.diamond, Items.gold_ingot, BlockListMF.repair_advanced);
    }

    private static void add(InformationBase info, Object... artifacts) {
        for (Object artifact : artifacts) {
            ResearchArtefacts.addArtefact(artifact, info);
        }
    }
}
