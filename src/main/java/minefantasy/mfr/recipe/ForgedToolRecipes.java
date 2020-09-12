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
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

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

        for (CustomMaterial customMat : metal) {
            ItemStack bar = ComponentListMFR.BAR.createComm(customMat.name);
            for (ItemStack ingot : OreDictionary.getOres("ingot" + customMat.name)) {
                KnowledgeListMFR.barR.add(MineFantasyRebornAPI.addAnvilRecipe(null, bar, "", true, "hammer", -1, -1,
                        (int) (customMat.craftTimeModifier / 2F), "I", 'I', ingot));
            }

            ItemStack defaultIngot = customMat.getItemStack();
            if (defaultIngot != null) {
                KnowledgeListMFR.baringotR.add(MineFantasyRebornAPI.addAnvilRecipe(null, defaultIngot, "", true, "hammer", -1, -1, (int) (customMat.craftTimeModifier / 2F), "I", 'I', bar));
            }
        }

        KnowledgeListMFR.tinderboxR = MineFantasyRebornAPI.addAnvilRecipe(null, new ItemStack(ToolListMFR.TINDERBOX), "", true, "hammer", 0, 0, 10, " F ", "SWS", " I ", 'F', Items.FLINT, 'S', Items.STICK, 'W', Blocks.WOOL, 'I', ComponentListMFR.bar("Iron"));
        KnowledgeListMFR.flintAndSteelR = MineFantasyRebornAPI.addAnvilRecipe(null, new ItemStack(Items.FLINT_AND_STEEL), "", true, "hammer", 0, 0, 10, "  F", "IC ", " I ", 'F', Items.FLINT, 'C', Items.COAL, 'I', ComponentListMFR.bar("Steel"));
        Salvage.addSalvage(ToolListMFR.TINDERBOX, Items.FLINT, Items.STICK, Blocks.WOOL, ComponentListMFR.bar("Iron"));
        Salvage.addSalvage(Items.FLINT_AND_STEEL, Items.FLINT, ComponentListMFR.bar("Steel"));
    }

    private static void addMetalComponents() {
        int time = 2;
        Item bar = ComponentListMFR.BAR;
        Item hunk = ComponentListMFR.METAL_HUNK;

        KnowledgeListMFR.hunkR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(hunk, 4), "", true, "hammer", 0, 0, time, "F", "I", 'F', ComponentListMFR.FLUX, 'I', bar);

        KnowledgeListMFR.ingotR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, bar, "", true, "hammer", 0, 0, time, "II", "II", 'I', hunk);

        time = 8;
        int count = 1;
        KnowledgeListMFR.bucketR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(Items.BUCKET, count), "", true, "hammer", 0, 0, time, "I I", " I ", 'I', bar);
    }

    private static void addComponentTools() {
        Item bar = ComponentListMFR.BAR;
        Item plank = ComponentListMFR.PLANK;
        Item strip = ComponentListMFR.LEATHER_STRIP;
        Item rivet = ComponentListMFR.RIVET;
        Item hunk = ComponentListMFR.METAL_HUNK;

        int time = 10;

        KnowledgeListMFR.nailR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.NAIL, 16), "", true, "hammer", -1, -1, time, "HH", " H", " H", 'H', hunk);
        KnowledgeListMFR.rivetR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.RIVET, 8), "", true, "hammer", -1, -1, time, "H H", " H ", " H ", 'H', hunk);

        KnowledgeListMFR.needleR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_NEEDLE, "tier", true, "hammer", -1, -1, time, "H", "H", "H", "H", 'H', hunk);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_NEEDLE, bar);

        time = 3;
        KnowledgeListMFR.crossBoltR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_BOLT, "tier", true, "hammer", -1, -1, time, "H", "F", 'F', ComponentListMFR.FLETCHING, 'H', hunk);
        time = 2;
        KnowledgeListMFR.arrowheadR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(ComponentListMFR.ARROWHEAD, 4), "tier", true, "hammer", -1, -1, time, "H ", "HH", "H ", 'H', hunk);
        time = 5;
        KnowledgeListMFR.bodkinheadR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(ComponentListMFR.BODKIN_HEAD, 4), "tier", true, "hammer", -1, -1, time, "H  ", " HH", "H  ", 'H', hunk);
        time = 5;
        KnowledgeListMFR.broadheadR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, new ItemStack(ComponentListMFR.BROAD_HEAD, 4), "tier", true, "hammer", -1, -1, time, "H ", " H", " H", "H ", 'H', hunk);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_BOLT, ComponentListMFR.FLETCHING, hunk);
        Salvage.addSalvage(ComponentListMFR.ARROWHEAD, hunk);
        Salvage.addSalvage(ComponentListMFR.BODKIN_HEAD, hunk);
        Salvage.addSalvage(ComponentListMFR.BROAD_HEAD, hunk);

        time = 1;
        KnowledgeListMFR.arrowR.add(MineFantasyRebornAPI.addCarpenterToolRecipe(artisanry, CustomToolListMFR.STANDARD_ARROW, "arrows", SoundEvents.BLOCK_WOOD_HIT, 1, "H", "F", 'F', ComponentListMFR.FLETCHING, 'H', ComponentListMFR.ARROWHEAD));
        KnowledgeListMFR.arrowR.add(MineFantasyRebornAPI.addCarpenterToolRecipe(artisanry, CustomToolListMFR.STANDARD_ARROW_BODKIN, "arrowsBodkin", SoundEvents.BLOCK_WOOD_HIT, 1, "H", "F", 'F', ComponentListMFR.FLETCHING, 'H', ComponentListMFR.BODKIN_HEAD));
        KnowledgeListMFR.arrowR.add(MineFantasyRebornAPI.addCarpenterToolRecipe(artisanry, CustomToolListMFR.STANDARD_ARROW_BROAD, "arrowsBroad", SoundEvents.BLOCK_WOOD_HIT, 1, "H", "F", 'F', ComponentListMFR.FLETCHING, 'H', ComponentListMFR.BROAD_HEAD));
        Salvage.addSalvage(CustomToolListMFR.STANDARD_ARROW, ComponentListMFR.ARROWHEAD, ComponentListMFR.FLETCHING);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_ARROW_BODKIN, ComponentListMFR.BODKIN_HEAD, ComponentListMFR.FLETCHING);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_ARROW_BROAD, ComponentListMFR.BROAD_HEAD, ComponentListMFR.FLETCHING);

    }

    private static void addStandardTools() {
        Item bar = ComponentListMFR.BAR;
        Item plank = ComponentListMFR.PLANK;
        Item strip = ComponentListMFR.LEATHER_STRIP;
        Item rivet = ComponentListMFR.RIVET;

        int time = 15;
        KnowledgeListMFR.pickR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_PICK, "tier", true, "hammer", -1, -1, time, "L I", "PPI", "L I", 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_PICK, bar, bar, bar, plank, plank, strip, strip);

        time = 15;
        KnowledgeListMFR.axeR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_AXE, "tier", true, "hammer", -1, -1, time, "LII", "PPI", "L  ", 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_AXE, bar, bar, bar, plank, plank, strip, strip);

        time = 12;
        KnowledgeListMFR.hoeR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_HOE, "tier", true, "hammer", -1, -1, time, "L I", "PPI", "L  ", 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_HOE, bar, bar, plank, plank, strip, strip);

        time = 10;
        KnowledgeListMFR.spadeR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_SPADE, "tier", true, "hammer", -1, -1, time, "L  ", "PPI", "L  ", 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_SPADE, bar, plank, plank, strip, strip);

        // ADVANCED
        time = 25;
        KnowledgeListMFR.hvyPickR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_HVYPICK, "tier", true, "hvyhammer", -1, -1, time, "LR I", "PPII", "LRII", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_HVYPICK, bar, bar, bar, bar, bar, plank, plank, strip, strip, rivet, rivet);

        time = 15;
        KnowledgeListMFR.handpickR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_HANDPICK, "tier", true, "hammer", -1, -1, time, "LI ", "PIR", "L  ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_HANDPICK, bar, bar, plank, strip, strip, rivet);

        time = 25;
        KnowledgeListMFR.hvyShovelR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_HVYSHOVEL, "tier", true, "hvyhammer", -1, -1, time, "LRII", "PPII", "LRII", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_HVYSHOVEL, bar, bar, bar, bar, bar, bar, plank, plank, strip, strip, rivet, rivet);

        time = 15;
        KnowledgeListMFR.trowR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_TROW, "tier", true, "hammer", -1, -1, time, "L  ", "PIR", "L  ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_TROW, bar, plank, strip, strip, rivet);

        time = 30;
        KnowledgeListMFR.scytheR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_SCYTHE, "tier", true, "hvyhammer", -1, -1, time, "   I ", "L PIR", "PPPIR", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_SCYTHE, bar, bar, bar, plank, plank, plank, plank, strip, rivet, rivet);

        time = 14;
        KnowledgeListMFR.mattockR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_MATTOCK, "tier", true, "hammer", -1, -1, time, "L I", "PPI", "LIR", 'I', bar, 'P', plank, 'L', strip, 'R', rivet);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_MATTOCK, bar, bar, bar, rivet, plank, plank, strip, strip);

        time = 15;
        KnowledgeListMFR.lumberR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_LUMBER, "tier", true, "hvyHammer", -1, -1, time, "L IIR", "PPPIR", "L   R", 'I', bar, 'P', plank, 'L', strip, 'R', rivet);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_LUMBER, bar, bar, bar, rivet, rivet, rivet, plank, plank, plank, strip, strip);
    }

    private static void addStandardCrafters() {
        Item bar = ComponentListMFR.BAR;
        Item plank = ComponentListMFR.PLANK;
        Item strip = ComponentListMFR.LEATHER_STRIP;
        Item rivet = ComponentListMFR.RIVET;

        int time = 10;
        KnowledgeListMFR.hammerR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_HAMMER, "tier", true, "hammer", 0, 0, time, "I", "L", "P", 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_HAMMER, bar, plank, strip);

        time = 15;
        KnowledgeListMFR.hvyHammerR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_HVYHAMMER, "tier", true, "hammer", -1, -1, time, " II", "RLI", " P ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_HVYHAMMER, bar, bar, bar, plank, strip, rivet);

        time = 10;
        KnowledgeListMFR.tongsR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_TONGS, "tier", true, "hammer", -1, -1, time, "I ", " I", 'I', bar);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_TONGS, bar, bar);

        time = 10;
        KnowledgeListMFR.knifeR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_KNIFE, "tier", true, "hammer", -1, -1, time, "I ", "PL", 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_KNIFE, bar, plank, strip);

        time = 12;
        KnowledgeListMFR.shearsR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_SHEARS, "tier", true, "hammer", -1, -1, time, " I ", "PLI", " P ", 'I', bar, 'P', plank, 'L', Items.LEATHER);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_SHEARS, bar, bar, plank, plank, Items.LEATHER);

        time = 20;
        KnowledgeListMFR.sawsR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_SAW, "tier", true, "hammer", -1, -1, time, "PIII", "LI  ", 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_SAW, bar, bar, bar, bar, plank, strip);

        time = 15;
        KnowledgeListMFR.spannerR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_SPANNER,
                "tier", true, "hammer", -1, -1, time, new Object[]{"  I ", "  II", "LP  ", " L  ",

                        'I', bar, 'P', plank, 'L', strip,});
        Salvage.addSalvage(CustomToolListMFR.STANDARD_SPANNER, bar, bar, bar, plank, strip, strip);
    }

    private static void addStandardWeapons() {
        Item bar = ComponentListMFR.BAR;
        Item plank = ComponentListMFR.PLANK;
        Item strip = ComponentListMFR.LEATHER_STRIP;
        Item rivet = ComponentListMFR.RIVET;

        int time = 15;
        KnowledgeListMFR.daggerR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_DAGGER, "tier", true, "hammer", -1, -1, time, "L  ", "PII", "L  ", 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_DAGGER, bar, bar, plank, strip, strip);

        time = 25;
        KnowledgeListMFR.swordR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_SWORD, "tier", true, "hammer", -1, -1, time, "LI  ", "PIII", "LI  ", 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_SWORD, bar, bar, bar, bar, bar, plank, strip, strip);

        time = 20;
        KnowledgeListMFR.waraxeR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_WARAXE, "tier", true, "hammer", -1, -1, time, "LII", "PPI", "L I", 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_WARAXE, bar, bar, bar, bar, plank, plank, strip, strip);

        KnowledgeListMFR.maceR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_MACE, "tier", true, "hammer", -1, -1, time, "L II", "PPII", "L   ", 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_MACE, bar, bar, bar, bar, plank, plank, strip, strip);

        KnowledgeListMFR.spearR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_SPEAR, "tier", true, "hammer", -1, -1, time, " LLI ", "PPPPI", " LLI ", 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_SPEAR, bar, bar, bar, plank, plank, plank, plank, strip, strip, strip, strip);

        // HEAVY
        time = 30;
        KnowledgeListMFR.katanaR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_KATANA, "tier", true, "hvyhammer", -1, -1, time, "LR   I", "PIIII ", "LI    ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_KATANA, bar, bar, bar, bar, bar, bar, plank, strip, strip, rivet);

        time = 40;
        KnowledgeListMFR.gswordR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_GREATSWORD, "tier", true, "hvyhammer", -1, -1, time, "LIR   ", "PIIIII", "LIR   ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_GREATSWORD, bar, bar, bar, bar, bar, bar, bar, plank, strip, strip, rivet, rivet);

        time = 30;
        KnowledgeListMFR.battleaxeR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_BATTLEAXE, "tier", true, "hvyhammer", -1, -1, time, "LLIIR", "PPPIR", "LLIIR", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_BATTLEAXE, bar, bar, bar, bar, bar, plank, plank, plank, strip, strip, strip, strip, rivet, rivet, rivet);

        KnowledgeListMFR.whammerR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_WARHAMMER, "tier", true, "hvyhammer", -1, -1, time, "LL IIR", "PPPIIR", "LL  IR", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_WARHAMMER, bar, bar, bar, bar, bar, plank, plank, plank, strip, strip, strip, strip, rivet, rivet, rivet);

        KnowledgeListMFR.halbeardR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_HALBEARD, "tier", true, "hvyhammer", -1, -1, time, "LLRII", "PPPPI", "LLRI ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_HALBEARD, bar, bar, bar, bar, plank, plank, plank, plank, strip, strip, strip, strip, rivet, rivet);

        time = 25;
        KnowledgeListMFR.bowR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_BOW, "tier", true, "hammer", -1, -1, time, "PSSSP", " PLP ", 'I', bar, 'S', Items.STRING, 'P', plank, 'L', strip);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_BOW, plank, plank, plank, plank, strip, Items.STRING, Items.STRING, Items.STRING);

        time = 60;
        KnowledgeListMFR.lanceR = MineFantasyRebornAPI.addAnvilToolRecipe(artisanry, CustomToolListMFR.STANDARD_LANCE, "tier", true, "hvyhammer", -1, -1, time, "IR    ", "IIIIII", "IR    ", 'R', rivet, 'I', bar);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_LANCE, bar, bar, bar, bar, bar, bar, bar, bar, rivet, rivet);
    }
}