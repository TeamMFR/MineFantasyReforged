package minefantasy.mfr.recipe;

import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.rpg.Skill;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CustomToolListMFR;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.init.KnowledgeListMFR;
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
            ItemStack bar = ComponentListMFR.bar.createComm(customMat.name);
            for (ItemStack ingot : OreDictionary.getOres("ingot" + customMat.name)) {
                KnowledgeListMFR.barR.add(MineFantasyRebornAPI.addAnvilRecipe(null, bar, "", true, "hammer", -1, -1,
                        (int) (customMat.craftTimeModifier / 2F), new Object[]{"I", 'I', ingot,}));
            }

            ItemStack defaultIngot = customMat.getItem();
            if (defaultIngot != null) {
                KnowledgeListMFR.baringotR.add(MineFantasyRebornAPI.addAnvilRecipe(null, defaultIngot, "", true, "hammer",
                        -1, -1, (int) (customMat.craftTimeModifier / 2F), new Object[]{"I", 'I', bar,}));
            }
        }

        KnowledgeListMFR.tinderboxR = MineFantasyRebornAPI.addAnvilRecipe(null, new ItemStack(ToolListMFR.tinderbox), "", true,
                "hammer", 0, 0, 10, new Object[]{" F ", "SWS", " I ", 'F', Items.FLINT, 'S', Items.STICK, 'W',
                        Blocks.WOOL, 'I', ComponentListMFR.bar("Iron"),});
        KnowledgeListMFR.flintAndSteelR = MineFantasyRebornAPI.addAnvilRecipe(null, new ItemStack(Items.FLINT_AND_STEEL), "",
                true, "hammer", 0, 0, 10, new Object[]{"  F", "IC ", " I ", 'F', Items.FLINT, 'C', Items.COAL, 'I',
                         ComponentListMFR.bar("Steel"),});
        Salvage.addSalvage(ToolListMFR.tinderbox, Items.FLINT, Items.STICK, Blocks.WOOL, ComponentListMFR.bar("Iron"));
        Salvage.addSalvage(Items.FLINT_AND_STEEL, Items.FLINT, ComponentListMFR.bar("Steel"));
    }

    private static void addMetalComponents() {
        int time = 2;
        Item bar = ComponentListMFR.bar;
        Item hunk = ComponentListMFR.metalHunk;

        KnowledgeListMFR.hunkR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(hunk, 4), "", true, "hammer",
                0, 0, time, new Object[]{"F", "I", 'F', ComponentListMFR.flux, 'I', bar,});

        KnowledgeListMFR.ingotR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, bar, "", true, "hammer", 0, 0, time,
                new Object[]{"II", "II", 'I', hunk});

        time = 8;
        int count = 1;
        KnowledgeListMFR.bucketR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(Items.BUCKET, count), "", true,
                "hammer", 0, 0, time, new Object[]{"I I", " I ", 'I', bar,});
    }

    private static void addComponentTools() {
        Item bar = ComponentListMFR.bar;
        Item plank = ComponentListMFR.plank;
        Item strip = ComponentListMFR.leather_strip;
        Item rivet = ComponentListMFR.rivet;
        Item hunk = ComponentListMFR.metalHunk;

        int time = 10;

        KnowledgeListMFR.nailR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.nail, 16), "",
                true, "hammer", -1, -1, time, new Object[]{"HH", " H", " H", 'H', hunk});
        KnowledgeListMFR.rivetR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.rivet, 8), "",
                true, "hammer", -1, -1, time, new Object[]{"H H", " H ", " H ", 'H', hunk});

        KnowledgeListMFR.needleR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, ComponentListMFR.standard_needle, "tier",
                true, "hammer", -1, -1, time, new Object[]{"H", "H", "H", "H",

                        'H', hunk});
        Salvage.addSalvage(CustomToolListMFR.standard_needle, bar);

        time = 3;
        KnowledgeListMFR.crossBoltR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, ComponentListMFR.standard_bolt,
                "tier", true, "hammer", -1, -1, time, new Object[]{"H", "F", 'F', ComponentListMFR.fletching, 'H', hunk});
        time = 2;
        KnowledgeListMFR.arrowheadR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry,
                new ItemStack(ComponentListMFR.arrowhead, 4), "tier", true, "hammer", -1, -1, time,
                new Object[]{"H ", "HH", "H ", 'H', hunk});
        time = 5;
        KnowledgeListMFR.bodkinheadR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry,
                new ItemStack(ComponentListMFR.bodkinhead, 4), "tier", true, "hammer", -1, -1, time,
                new Object[]{"H  ", " HH", "H  ", 'H', hunk});
        time = 5;
        KnowledgeListMFR.broadheadR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry,
                new ItemStack(ComponentListMFR.broadhead, 4), "tier", true, "hammer", -1, -1, time,
                new Object[]{"H ", " H", " H", "H ", 'H', hunk});
        Salvage.addSalvage(CustomToolListMFR.standard_bolt, ComponentListMFR.fletching, hunk);
        Salvage.addSalvage(ComponentListMFR.arrowhead, hunk);
        Salvage.addSalvage(ComponentListMFR.bodkinhead, hunk);
        Salvage.addSalvage(ComponentListMFR.broadhead, hunk);

        time = 1;
        KnowledgeListMFR.arrowR.add(MineFantasyRebornAPI.addCarpenterToolRecipe(artisanry, CustomToolListMFR.standard_arrow, "arrows", "dig.wood", 1, new Object[]{"H", "F", 'F', ComponentListMFR.fletching, 'H', ComponentListMFR.arrowhead}));
        KnowledgeListMFR.arrowR.add(MineFantasyRebornAPI.addCarpenterToolRecipe(artisanry, CustomToolListMFR.standard_arrow_bodkin, "arrowsBodkin", "dig.wood", 1, new Object[]{"H", "F", 'F', ComponentListMFR.fletching, 'H', ComponentListMFR.bodkinhead}));
        KnowledgeListMFR.arrowR.add(MineFantasyRebornAPI.addCarpenterToolRecipe(artisanry, CustomToolListMFR.standard_arrow_broad, "arrowsBroad", "dig.wood", 1, new Object[]{"H", "F", 'F', ComponentListMFR.fletching, 'H', ComponentListMFR.broadhead}));
        Salvage.addSalvage(CustomToolListMFR.standard_arrow, ComponentListMFR.arrowhead, ComponentListMFR.fletching);
        Salvage.addSalvage(CustomToolListMFR.standard_arrow_bodkin, ComponentListMFR.bodkinhead, ComponentListMFR.fletching);
        Salvage.addSalvage(CustomToolListMFR.standard_arrow_broad, ComponentListMFR.broadhead, ComponentListMFR.fletching);

    }

    private static void addStandardTools() {
        Item bar = ComponentListMFR.bar;
        Item plank = ComponentListMFR.plank;
        Item strip = ComponentListMFR.leather_strip;
        Item rivet = ComponentListMFR.rivet;

        int time = 15;
        KnowledgeListMFR.pickR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_pick, "tier",
                true, "hammer", -1, -1, time, new Object[]{"L I", "PPI", "L I", 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_pick, bar, bar, bar, plank, plank, strip, strip);

        time = 15;
        KnowledgeListMFR.axeR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_axe, "tier", true,
                "hammer", -1, -1, time, new Object[]{"LII", "PPI", "L  ", 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_axe, bar, bar, bar, plank, plank, strip, strip);

        time = 12;
        KnowledgeListMFR.hoeR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_hoe, "tier", true,
                "hammer", -1, -1, time, new Object[]{"L I", "PPI", "L  ", 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_hoe, bar, bar, plank, plank, strip, strip);

        time = 10;
        KnowledgeListMFR.spadeR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_spade, "tier",
                true, "hammer", -1, -1, time, new Object[]{"L  ", "PPI", "L  ", 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_spade, bar, plank, plank, strip, strip);

        // ADVANCED
        time = 25;
        KnowledgeListMFR.hvyPickR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_hvypick,
                "tier", true, "hvyhammer", -1, -1, time, new Object[]{"LR I", "PPII", "LRII", 'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_hvypick, bar, bar, bar, bar, bar, plank, plank, strip, strip,
                rivet, rivet);

        time = 15;
        KnowledgeListMFR.handpickR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_handpick,
                "tier", true, "hammer", -1, -1, time, new Object[]{"LI ", "PIR", "L  ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_handpick, bar, bar, plank, strip, strip, rivet);

        time = 25;
        KnowledgeListMFR.hvyShovelR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_hvyshovel,
                "tier", true, "hvyhammer", -1, -1, time, new Object[]{"LRII", "PPII", "LRII", 'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_hvyshovel, bar, bar, bar, bar, bar, bar, plank, plank, strip,
                strip, rivet, rivet);

        time = 15;
        KnowledgeListMFR.trowR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_trow, "tier",
                true, "hammer", -1, -1, time, new Object[]{"L  ", "PIR", "L  ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_trow, bar, plank, strip, strip, rivet);

        time = 30;
        KnowledgeListMFR.scytheR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_scythe, "tier",
                true, "hvyhammer", -1, -1, time, new Object[]{"   I ", "L PIR", "PPPIR", 'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_scythe, bar, bar, bar, plank, plank, plank, plank, strip, rivet,
                rivet);

        time = 14;
        KnowledgeListMFR.mattockR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_mattock,
                "tier", true, "hammer", -1, -1, time, new Object[]{"L I", "PPI", "LIR", 'I', bar, 'P', plank, 'L', strip, 'R', rivet});
        Salvage.addSalvage(CustomToolListMFR.standard_mattock, bar, bar, bar, rivet, plank, plank, strip, strip);

        time = 15;
        KnowledgeListMFR.lumberR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_lumber, "tier",
                true, "hvyHammer", -1, -1, time, new Object[]{"L IIR", "PPPIR", "L   R", 'I', bar, 'P', plank, 'L', strip, 'R', rivet});
        Salvage.addSalvage(CustomToolListMFR.standard_lumber, bar, bar, bar, rivet, rivet, rivet, plank, plank, plank,
                strip, strip);
    }

    private static void addStandardCrafters() {
        Item bar = ComponentListMFR.bar;
        Item plank = ComponentListMFR.plank;
        Item strip = ComponentListMFR.leather_strip;
        Item rivet = ComponentListMFR.rivet;

        int time = 10;
        KnowledgeListMFR.hammerR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_hammer, "tier",
                true, "hammer", 0, 0, time, new Object[]{"I", "L", "P", 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_hammer, bar, plank, strip);

        time = 15;
        KnowledgeListMFR.hvyHammerR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_hvyhammer,
                "tier", true, "hammer", -1, -1, time, new Object[]{" II", "RLI", " P ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_hvyhammer, bar, bar, bar, plank, strip, rivet);

        time = 10;
        KnowledgeListMFR.tongsR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_tongs, "tier",
                true, "hammer", -1, -1, time, new Object[]{"I ", " I", 'I', bar,});
        Salvage.addSalvage(CustomToolListMFR.standard_tongs, bar, bar);

        time = 10;
        KnowledgeListMFR.knifeR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_knife, "tier",
                true, "hammer", -1, -1, time, new Object[]{"I ", "PL", 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_knife, bar, plank, strip);

        time = 12;
        KnowledgeListMFR.shearsR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_shears, "tier",
                true, "hammer", -1, -1, time, new Object[]{" I ", "PLI", " P ", 'I', bar, 'P', plank, 'L', Items.LEATHER,});
        Salvage.addSalvage(CustomToolListMFR.standard_shears, bar, bar, plank, plank, Items.LEATHER);

        time = 20;
        KnowledgeListMFR.sawsR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_saw, "tier",
                true, "hammer", -1, -1, time, new Object[]{"PIII", "LI  ", 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_saw, bar, bar, bar, bar, plank, strip);

        time = 15;
        KnowledgeListMFR.spannerR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_spanner,
                "tier", true, "hammer", -1, -1, time, new Object[]{"  I ", "  II", "LP  ", " L  ", 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_spanner, bar, bar, bar, plank, strip, strip);
    }

    private static void addStandardWeapons() {
        Item bar = ComponentListMFR.bar;
        Item plank = ComponentListMFR.plank;
        Item strip = ComponentListMFR.leather_strip;
        Item rivet = ComponentListMFR.rivet;

        int time = 15;
        KnowledgeListMFR.daggerR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_dagger, "tier",
                true, "hammer", -1, -1, time, new Object[]{"L  ", "PII", "L  ", 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_dagger, bar, bar, plank, strip, strip);

        time = 25;
        KnowledgeListMFR.swordR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_sword, "tier",
                true, "hammer", -1, -1, time, new Object[]{"LI  ", "PIII", "LI  ", 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_sword, bar, bar, bar, bar, bar, plank, strip, strip);

        time = 20;
        KnowledgeListMFR.waraxeR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_waraxe, "tier",
                true, "hammer", -1, -1, time, new Object[]{"LII", "PPI", "L I", 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_waraxe, bar, bar, bar, bar, plank, plank, strip, strip);

        KnowledgeListMFR.maceR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_mace, "tier",
                true, "hammer", -1, -1, time, new Object[]{"L II", "PPII", "L   ", 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_mace, bar, bar, bar, bar, plank, plank, strip, strip);

        KnowledgeListMFR.spearR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_spear, "tier",
                true, "hammer", -1, -1, time, new Object[]{" LLI ", "PPPPI", " LLI ", 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_spear, bar, bar, bar, plank, plank, plank, plank, strip, strip,
                strip, strip);

        // HEAVY
        time = 30;
        KnowledgeListMFR.katanaR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_katana, "tier",
                true, "hvyhammer", -1, -1, time, new Object[]{"LR   I", "PIIII ", "LI    ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_katana, bar, bar, bar, bar, bar, bar, plank, strip, strip, rivet);

        time = 40;
        KnowledgeListMFR.gswordR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_greatsword,
                "tier", true, "hvyhammer", -1, -1, time, new Object[]{"LIR   ", "PIIIII", "LIR   ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_greatsword, bar, bar, bar, bar, bar, bar, bar, plank, strip, strip,
                rivet, rivet);

        time = 30;
        KnowledgeListMFR.battleaxeR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_battleaxe,
                "tier", true, "hvyhammer", -1, -1, time, new Object[]{"LLIIR", "PPPIR", "LLIIR", 'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_battleaxe, bar, bar, bar, bar, bar, plank, plank, plank, strip,
                strip, strip, strip, rivet, rivet, rivet);

        KnowledgeListMFR.whammerR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_warhammer,
                "tier", true, "hvyhammer", -1, -1, time, new Object[]{"LL IIR", "PPPIIR", "LL  IR", 'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_warhammer, bar, bar, bar, bar, bar, plank, plank, plank, strip,
                strip, strip, strip, rivet, rivet, rivet);

        KnowledgeListMFR.halbeardR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_halbeard,
                "tier", true, "hvyhammer", -1, -1, time, new Object[]{"LLRII", "PPPPI", "LLRI ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_halbeard, bar, bar, bar, bar, plank, plank, plank, plank, strip,
                strip, strip, strip, rivet, rivet);

        time = 25;
        KnowledgeListMFR.bowR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_bow, "tier", true,
                "hammer", -1, -1, time, new Object[]{"PSSSP", " PLP ", 'I', bar, 'S', Items.STRING, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.standard_bow, plank, plank, plank, plank, strip, Items.STRING, Items.STRING,
                Items.STRING);

        time = 60;
        KnowledgeListMFR.lanceR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.standard_lance, "tier",
                true, "hvyhammer", -1, -1, time, new Object[]{"IR    ", "IIIIII", "IR    ", 'R', rivet, 'I', bar,});
        Salvage.addSalvage(CustomToolListMFR.standard_lance, bar, bar, bar, bar, bar, bar, bar, bar, rivet, rivet);
    }
}