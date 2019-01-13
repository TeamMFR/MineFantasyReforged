package minefantasy.mf2.recipe;

import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.crafting.Salvage;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CustomToolListMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;

public class ForgedToolRecipes {
    private static final Skill artisanry = SkillList.artisanry;
    private static final Skill engineering = SkillList.engineering;
    private static final Skill construction = SkillList.construction;

    public static void init() {
        CarpenterRecipes.initTierWood();
        addStandardTools();
        addStandardCrafters();
        addStandardWeapons();
        addComponentTools();
        addMetalComponents();

        ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
        ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");

        Iterator iteratorMetal = metal.iterator();

        while (iteratorMetal.hasNext()) {
            CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
            ItemStack bar = ComponentListMF.bar.createComm(customMat.name);
            for (ItemStack ingot : OreDictionary.getOres("ingot" + customMat.name)) {
                KnowledgeListMF.barR.add(MineFantasyAPI.addAnvilRecipe(null, bar, "", true, "hammer", -1, -1,
                        (int) (customMat.craftTimeModifier / 2F), new Object[]{"I", 'I', ingot,}));
            }

            ItemStack defaultIngot = customMat.getItem();
            if (defaultIngot != null) {
                KnowledgeListMF.baringotR.add(MineFantasyAPI.addAnvilRecipe(null, defaultIngot, "", true, "hammer",
                        -1, -1, (int) (customMat.craftTimeModifier / 2F), new Object[]{"I", 'I', bar,}));
            }
        }

        KnowledgeListMF.tinderboxR = MineFantasyAPI.addAnvilRecipe(null, new ItemStack(ToolListMF.tinderbox), "", true,
                "hammer", 0, 0, 10, new Object[]{" F ", "SWS", " I ", 'F', Items.flint, 'S', Items.stick, 'W',
                        Blocks.wool, 'I', ComponentListMF.bar("Iron"),});
        KnowledgeListMF.flintAndSteelR = MineFantasyAPI.addAnvilRecipe(null, new ItemStack(Items.flint_and_steel), "",
                true, "hammer", 0, 0, 10, new Object[]{"  F", "IC ", " I ", 'F', Items.flint, 'C', Items.coal, 'I',
                        ComponentListMF.bar("Steel"),});
        Salvage.addSalvage(ToolListMF.tinderbox, Items.flint, Items.stick, Blocks.wool, ComponentListMF.bar("Iron"));
        Salvage.addSalvage(Items.flint_and_steel, Items.flint, ComponentListMF.bar("Steel"));
    }

    private static void addMetalComponents() {
        int time = 2;
        Item bar = ComponentListMF.bar;
        Item hunk = ComponentListMF.metalHunk;

        KnowledgeListMF.hunkR = MineFantasyAPI.addAnvilToolRecipe(artisanry, new ItemStack(hunk, 4), "", true, "hammer",
                0, 0, time, new Object[]{"F", "I", 'F', ComponentListMF.flux, 'I', bar,});

        KnowledgeListMF.ingotR = MineFantasyAPI.addAnvilToolRecipe(artisanry, bar, "", true, "hammer", 0, 0, time,
                new Object[]{"II", "II", 'I', hunk});

        time = 8;
        int count = 1;
        KnowledgeListMF.bucketR = MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(Items.bucket, count), "", true,
                "hammer", 0, 0, time, new Object[]{"I I", " I ", 'I', bar,});
    }

    private static void addComponentTools() {
        Item bar = ComponentListMF.bar;
        Item plank = ComponentListMF.plank;
        Item strip = ComponentListMF.leather_strip;
        Item rivet = ComponentListMF.rivet;
        Item hunk = ComponentListMF.metalHunk;

        int time = 10;

        KnowledgeListMF.nailR = MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.nail, 16), "",
                true, "hammer", -1, -1, time, new Object[]{"HH", " H", " H",

                        'H', hunk});
        KnowledgeListMF.rivetR = MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.rivet, 8), "",
                true, "hammer", -1, -1, time, new Object[]{"H H", " H ", " H ",

                        'H', hunk});

        KnowledgeListMF.needleR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_needle, "tier",
                true, "hammer", -1, -1, time, new Object[]{"H", "H", "H", "H",

                        'H', hunk});
        Salvage.addSalvage(CustomToolListMF.standard_needle, bar);

        time = 3;
        KnowledgeListMF.crossBoltR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_bolt,
                "tier", true, "hammer", -1, -1, time, new Object[]{"H", "F",

                        'F', ComponentListMF.fletching, 'H', hunk});
        time = 2;
        KnowledgeListMF.arrowheadR = MineFantasyAPI.addAnvilToolRecipe(artisanry,
                new ItemStack(ComponentListMF.arrowhead, 4), "tier", true, "hammer", -1, -1, time,
                new Object[]{"H ", "HH", "H ",

                        'H', hunk});
        time = 5;
        KnowledgeListMF.bodkinheadR = MineFantasyAPI.addAnvilToolRecipe(artisanry,
                new ItemStack(ComponentListMF.bodkinhead, 4), "tier", true, "hammer", -1, -1, time,
                new Object[]{"H  ", " HH", "H  ",

                        'H', hunk});
        time = 5;
        KnowledgeListMF.broadheadR = MineFantasyAPI.addAnvilToolRecipe(artisanry,
                new ItemStack(ComponentListMF.broadhead, 4), "tier", true, "hammer", -1, -1, time,
                new Object[]{"H ", " H", " H", "H ",

                        'H', hunk});
        Salvage.addSalvage(CustomToolListMF.standard_bolt, ComponentListMF.fletching, hunk);
        Salvage.addSalvage(ComponentListMF.arrowhead, hunk);
        Salvage.addSalvage(ComponentListMF.bodkinhead, hunk);
        Salvage.addSalvage(ComponentListMF.broadhead, hunk);

        time = 1;
        KnowledgeListMF.arrowR.add(MineFantasyAPI.addCarpenterToolRecipe(artisanry, CustomToolListMF.standard_arrow,
                "arrows", "dig.wood", 1, new Object[]{"H", "F",

                        'F', ComponentListMF.fletching, 'H', ComponentListMF.arrowhead}));
        KnowledgeListMF.arrowR.add(MineFantasyAPI.addCarpenterToolRecipe(artisanry,
                CustomToolListMF.standard_arrow_bodkin, "arrowsBodkin", "dig.wood", 1, new Object[]{"H", "F",

                        'F', ComponentListMF.fletching, 'H', ComponentListMF.bodkinhead}));
        KnowledgeListMF.arrowR.add(MineFantasyAPI.addCarpenterToolRecipe(artisanry,
                CustomToolListMF.standard_arrow_broad, "arrowsBroad", "dig.wood", 1, new Object[]{"H", "F",

                        'F', ComponentListMF.fletching, 'H', ComponentListMF.broadhead}));
        Salvage.addSalvage(CustomToolListMF.standard_arrow, ComponentListMF.arrowhead, ComponentListMF.fletching);
        Salvage.addSalvage(CustomToolListMF.standard_arrow_bodkin, ComponentListMF.bodkinhead,
                ComponentListMF.fletching);
        Salvage.addSalvage(CustomToolListMF.standard_arrow_broad, ComponentListMF.broadhead, ComponentListMF.fletching);

    }

    private static void addStandardTools() {
        Item bar = ComponentListMF.bar;
        Item plank = ComponentListMF.plank;
        Item strip = ComponentListMF.leather_strip;
        Item rivet = ComponentListMF.rivet;

        int time = 15;
        KnowledgeListMF.pickR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_pick, "tier",
                true, "hammer", -1, -1, time, new Object[]{"L I", "PPI", "L I",

                        'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_pick, bar, bar, bar, plank, plank, strip, strip);

        time = 15;
        KnowledgeListMF.axeR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_axe, "tier", true,
                "hammer", -1, -1, time, new Object[]{"LII", "PPI", "L  ",

                        'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_axe, bar, bar, bar, plank, plank, strip, strip);

        time = 12;
        KnowledgeListMF.hoeR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_hoe, "tier", true,
                "hammer", -1, -1, time, new Object[]{"L I", "PPI", "L  ",

                        'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_hoe, bar, bar, plank, plank, strip, strip);

        time = 10;
        KnowledgeListMF.spadeR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_spade, "tier",
                true, "hammer", -1, -1, time, new Object[]{"L  ", "PPI", "L  ",

                        'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_spade, bar, plank, plank, strip, strip);

        // ADVANCED
        time = 25;
        KnowledgeListMF.hvyPickR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_hvypick,
                "tier", true, "hvyhammer", -1, -1, time, new Object[]{"LR I", "PPII", "LRII",

                        'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_hvypick, bar, bar, bar, bar, bar, plank, plank, strip, strip,
                rivet, rivet);

        time = 15;
        KnowledgeListMF.handpickR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_handpick,
                "tier", true, "hammer", -1, -1, time, new Object[]{"LI ", "PIR", "L  ",

                        'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_handpick, bar, bar, plank, strip, strip, rivet);

        time = 25;
        KnowledgeListMF.hvyShovelR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_hvyshovel,
                "tier", true, "hvyhammer", -1, -1, time, new Object[]{"LRII", "PPII", "LRII",

                        'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_hvyshovel, bar, bar, bar, bar, bar, bar, plank, plank, strip,
                strip, rivet, rivet);

        time = 15;
        KnowledgeListMF.trowR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_trow, "tier",
                true, "hammer", -1, -1, time, new Object[]{"L  ", "PIR", "L  ",

                        'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_trow, bar, plank, strip, strip, rivet);

        time = 30;
        KnowledgeListMF.scytheR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_scythe, "tier",
                true, "hvyhammer", -1, -1, time, new Object[]{"   I ", "L PIR", "PPPIR",

                        'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_scythe, bar, bar, bar, plank, plank, plank, plank, strip, rivet,
                rivet);

        time = 14;
        KnowledgeListMF.mattockR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_mattock,
                "tier", true, "hammer", -1, -1, time, new Object[]{"L I", "PPI", "LIR",

                        'I', bar, 'P', plank, 'L', strip, 'R', rivet});
        Salvage.addSalvage(CustomToolListMF.standard_mattock, bar, bar, bar, rivet, plank, plank, strip, strip);

        time = 15;
        KnowledgeListMF.lumberR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_lumber, "tier",
                true, "hvyHammer", -1, -1, time, new Object[]{"L IIR", "PPPIR", "L   R",

                        'I', bar, 'P', plank, 'L', strip, 'R', rivet});
        Salvage.addSalvage(CustomToolListMF.standard_lumber, bar, bar, bar, rivet, rivet, rivet, plank, plank, plank,
                strip, strip);
    }

    private static void addStandardCrafters() {
        Item bar = ComponentListMF.bar;
        Item plank = ComponentListMF.plank;
        Item strip = ComponentListMF.leather_strip;
        Item rivet = ComponentListMF.rivet;

        int time = 10;
        KnowledgeListMF.hammerR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_hammer, "tier",
                true, "hammer", 0, 0, time, new Object[]{"I", "L", "P",

                        'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_hammer, bar, plank, strip);

        time = 15;
        KnowledgeListMF.hvyHammerR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_hvyhammer,
                "tier", true, "hammer", -1, -1, time, new Object[]{" II", "RLI", " P ",

                        'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_hvyhammer, bar, bar, bar, plank, strip, rivet);

        time = 10;
        KnowledgeListMF.tongsR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_tongs, "tier",
                true, "hammer", -1, -1, time, new Object[]{"I ", " I",

                        'I', bar,});
        Salvage.addSalvage(CustomToolListMF.standard_tongs, bar, bar);

        time = 10;
        KnowledgeListMF.knifeR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_knife, "tier",
                true, "hammer", -1, -1, time, new Object[]{"I ", "PL",

                        'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_knife, bar, plank, strip);

        time = 12;
        KnowledgeListMF.shearsR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_shears, "tier",
                true, "hammer", -1, -1, time, new Object[]{" I ", "PLI", " P ",

                        'I', bar, 'P', plank, 'L', Items.leather,});
        Salvage.addSalvage(CustomToolListMF.standard_shears, bar, bar, plank, plank, Items.leather);

        time = 20;
        KnowledgeListMF.sawsR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_saw, "tier",
                true, "hammer", -1, -1, time, new Object[]{"PIII", "LI  ",

                        'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_saw, bar, bar, bar, bar, plank, strip);

        time = 15;
        KnowledgeListMF.spannerR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_spanner,
                "tier", true, "hammer", -1, -1, time, new Object[]{"  I ", "  II", "LP  ", " L  ",

                        'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_spanner, bar, bar, bar, plank, strip, strip);
    }

    private static void addStandardWeapons() {
        Item bar = ComponentListMF.bar;
        Item plank = ComponentListMF.plank;
        Item strip = ComponentListMF.leather_strip;
        Item rivet = ComponentListMF.rivet;

        int time = 15;
        KnowledgeListMF.daggerR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_dagger, "tier",
                true, "hammer", -1, -1, time, new Object[]{"L  ", "PII", "L  ",

                        'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_dagger, bar, bar, plank, strip, strip);

        time = 25;
        KnowledgeListMF.swordR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_sword, "tier",
                true, "hammer", -1, -1, time, new Object[]{"LI  ", "PIII", "LI  ",

                        'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_sword, bar, bar, bar, bar, bar, plank, strip, strip);

        time = 20;
        KnowledgeListMF.waraxeR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_waraxe, "tier",
                true, "hammer", -1, -1, time, new Object[]{"LII", "PPI", "L I",

                        'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_waraxe, bar, bar, bar, bar, plank, plank, strip, strip);

        KnowledgeListMF.maceR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_mace, "tier",
                true, "hammer", -1, -1, time, new Object[]{"L II", "PPII", "L   ",

                        'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_mace, bar, bar, bar, bar, plank, plank, strip, strip);

        KnowledgeListMF.spearR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_spear, "tier",
                true, "hammer", -1, -1, time, new Object[]{" LLI ", "PPPPI", " LLI ",

                        'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_spear, bar, bar, bar, plank, plank, plank, plank, strip, strip,
                strip, strip);

        // HEAVY
        time = 30;
        KnowledgeListMF.katanaR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_katana, "tier",
                true, "hvyhammer", -1, -1, time, new Object[]{"LR   I", "PIIII ", "LI    ",

                        'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_katana, bar, bar, bar, bar, bar, bar, plank, strip, strip, rivet);

        time = 40;
        KnowledgeListMF.gswordR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_greatsword,
                "tier", true, "hvyhammer", -1, -1, time, new Object[]{"LIR   ", "PIIIII", "LIR   ",

                        'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_greatsword, bar, bar, bar, bar, bar, bar, bar, plank, strip, strip,
                rivet, rivet);

        time = 30;
        KnowledgeListMF.battleaxeR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_battleaxe,
                "tier", true, "hvyhammer", -1, -1, time, new Object[]{"LLIIR", "PPPIR", "LLIIR",

                        'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_battleaxe, bar, bar, bar, bar, bar, plank, plank, plank, strip,
                strip, strip, strip, rivet, rivet, rivet);

        KnowledgeListMF.whammerR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_warhammer,
                "tier", true, "hvyhammer", -1, -1, time, new Object[]{"LL IIR", "PPPIIR", "LL  IR",

                        'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_warhammer, bar, bar, bar, bar, bar, plank, plank, plank, strip,
                strip, strip, strip, rivet, rivet, rivet);

        KnowledgeListMF.halbeardR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_halbeard,
                "tier", true, "hvyhammer", -1, -1, time, new Object[]{"LLRII", "PPPPI", "LLRI ",

                        'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_halbeard, bar, bar, bar, bar, plank, plank, plank, plank, strip,
                strip, strip, strip, rivet, rivet);

        time = 25;
        KnowledgeListMF.bowR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_bow, "tier", true,
                "hammer", -1, -1, time, new Object[]{"PSSSP", " PLP ",

                        'I', bar, 'S', Items.string, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMF.standard_bow, plank, plank, plank, plank, strip, Items.string, Items.string,
                Items.string);

        time = 60;
        KnowledgeListMF.lanceR = MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_lance, "tier",
                true, "hvyhammer", -1, -1, time, new Object[]{"IR    ", "IIIIII", "IR    ",

                        'R', rivet, 'I', bar,});
        Salvage.addSalvage(CustomToolListMF.standard_lance, bar, bar, bar, bar, bar, bar, bar, bar, rivet, rivet);
    }
}