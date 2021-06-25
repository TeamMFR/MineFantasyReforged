package minefantasy.mfr.recipe;

import minefantasy.mfr.api.MineFantasyReforgedAPI;
import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyKnowledgeList;
import minefantasy.mfr.item.ItemMetalComponent;
import minefantasy.mfr.material.CustomMaterial;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class ForgedToolRecipes {

	public static void init() {
		OtherRecipes.initTierWood();
		addStandardTools();
		addStandardCrafters();
		addStandardWeapons();
		addComponentTools();
		addMetalComponents();

		ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");

		for (CustomMaterial customMat : metal) {
			ItemStack bar = ((ItemMetalComponent)MineFantasyItems.BAR).createComponentItemStack(customMat.name);
			for (ItemStack ingot : OreDictionary.getOres("ingot" + customMat.name)) {
				MineFantasyKnowledgeList.barR.add(MineFantasyReforgedAPI.addAnvilRecipe(null, bar, "", true, "hammer", -1, -1,
						(int) (customMat.craftTimeModifier / 2F), "I", 'I', ingot));
			}

			ItemStack defaultIngot = customMat.getItemStack();
			if (defaultIngot != null) {
				MineFantasyKnowledgeList.baringotR.add(MineFantasyReforgedAPI.addAnvilRecipe(null, defaultIngot, "", true, "hammer", -1, -1, (int) (customMat.craftTimeModifier / 2F), "I", 'I', bar));
			}
		}

		MineFantasyKnowledgeList.tinderboxR = MineFantasyReforgedAPI.addAnvilRecipe(null, new ItemStack(MineFantasyItems.TINDERBOX), "", true, "hammer", 0, 0, 10, " F ", "SWS", " I ", 'F', Items.FLINT, 'S', Items.STICK, 'W', Blocks.WOOL, 'I', MineFantasyItems.bar("Iron"));
		MineFantasyKnowledgeList.flintAndSteelR = MineFantasyReforgedAPI.addAnvilRecipe(null, new ItemStack(Items.FLINT_AND_STEEL), "", true, "hammer", 0, 0, 10, "  F", "IC ", " I ", 'F', Items.FLINT, 'C', Items.COAL, 'I', MineFantasyItems.bar("Steel"));
		Salvage.addSalvage(MineFantasyItems.TINDERBOX, Items.FLINT, Items.STICK, Blocks.WOOL, MineFantasyItems.bar("Iron"));
		Salvage.addSalvage(Items.FLINT_AND_STEEL, Items.FLINT, MineFantasyItems.bar("Steel"));
	}

	private static void addMetalComponents() {
		int time = 2;
		Item bar = MineFantasyItems.BAR;
		Item hunk = MineFantasyItems.METAL_HUNK;

		MineFantasyKnowledgeList.hunkR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(hunk, 4), "", true, "hammer", 0, 0, time, "F", "I", 'F', MineFantasyItems.FLUX, 'I', bar);

		MineFantasyKnowledgeList.ingotR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, bar, "", true, "hammer", 0, 0, time, "II", "II", 'I', hunk);

		time = 8;
		int count = 1;
		MineFantasyKnowledgeList.bucketR = MineFantasyReforgedAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(Items.BUCKET, count), "", true, "hammer", 0, 0, time, "I I", " I ", 'I', bar);
	}

	private static void addComponentTools() {
		Item bar = MineFantasyItems.BAR;
		Item hunk = MineFantasyItems.METAL_HUNK;

		int time = 10;

		MineFantasyKnowledgeList.nailR = MineFantasyReforgedAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.NAIL, 16), "", true, "hammer", -1, -1, time, "HH", " H", " H", 'H', hunk);
		MineFantasyKnowledgeList.rivetR = MineFantasyReforgedAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.RIVET, 8), "", true, "hammer", -1, -1, time, "H H", " H ", " H ", 'H', hunk);

		MineFantasyKnowledgeList.needleR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_NEEDLE, "tier", true, "hammer", -1, -1, time, "H", "H", "H", "H", 'H', hunk);
		Salvage.addSalvage(MineFantasyItems.STANDARD_NEEDLE, bar);

		time = 3;
		MineFantasyKnowledgeList.crossBoltR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_BOLT, "tier", true, "hammer", -1, -1, time, "H", "F", 'F', MineFantasyItems.FLETCHING, 'H', hunk);
		time = 2;
		MineFantasyKnowledgeList.arrowheadR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.ARROWHEAD, 4), "tier", true, "hammer", -1, -1, time, "H ", "HH", "H ", 'H', hunk);
		time = 5;
		MineFantasyKnowledgeList.bodkinheadR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.BODKIN_HEAD, 4), "tier", true, "hammer", -1, -1, time, "H  ", " HH", "H  ", 'H', hunk);
		time = 5;
		MineFantasyKnowledgeList.broadheadR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.BROAD_HEAD, 4), "tier", true, "hammer", -1, -1, time, "H ", " H", " H", "H ", 'H', hunk);
		Salvage.addSalvage(MineFantasyItems.STANDARD_BOLT, MineFantasyItems.FLETCHING, hunk);
		Salvage.addSalvage(MineFantasyItems.ARROWHEAD, hunk);
		Salvage.addSalvage(MineFantasyItems.BODKIN_HEAD, hunk);
		Salvage.addSalvage(MineFantasyItems.BROAD_HEAD, hunk);

		Salvage.addSalvage(MineFantasyItems.STANDARD_ARROW, MineFantasyItems.ARROWHEAD, MineFantasyItems.FLETCHING);
		Salvage.addSalvage(MineFantasyItems.STANDARD_ARROW_BODKIN, MineFantasyItems.BODKIN_HEAD, MineFantasyItems.FLETCHING);
		Salvage.addSalvage(MineFantasyItems.STANDARD_ARROW_BROAD, MineFantasyItems.BROAD_HEAD, MineFantasyItems.FLETCHING);

	}

	private static void addStandardTools() {
		Item bar = MineFantasyItems.BAR;
		Item plank = MineFantasyItems.TIMBER;
		Item strip = MineFantasyItems.LEATHER_STRIP;
		Item rivet = MineFantasyItems.RIVET;

		int time = 15;
		MineFantasyKnowledgeList.pickR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_PICK, "tier", true, "hammer", -1, -1, time, "L I", "PPI", "L I", 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_PICK, bar, bar, bar, plank, plank, strip, strip);

		time = 15;
		MineFantasyKnowledgeList.axeR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_AXE, "tier", true, "hammer", -1, -1, time, "LII", "PPI", "L  ", 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_AXE, bar, bar, bar, plank, plank, strip, strip);

		time = 12;
		MineFantasyKnowledgeList.hoeR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_HOE, "tier", true, "hammer", -1, -1, time, "L I", "PPI", "L  ", 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_HOE, bar, bar, plank, plank, strip, strip);

		time = 10;
		MineFantasyKnowledgeList.spadeR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_SPADE, "tier", true, "hammer", -1, -1, time, "L  ", "PPI", "L  ", 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_SPADE, bar, plank, plank, strip, strip);

		// ADVANCED
		time = 25;
		MineFantasyKnowledgeList.heavyPickR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_HEAVY_PICK, "tier", true, "heavy_hammer", -1, -1, time, "LR I", "PPII", "LRII", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_HEAVY_PICK, bar, bar, bar, bar, bar, plank, plank, strip, strip, rivet, rivet);

		time = 15;
		MineFantasyKnowledgeList.handpickR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_HANDPICK, "tier", true, "hammer", -1, -1, time, "LI ", "PIR", "L  ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_HANDPICK, bar, bar, plank, strip, strip, rivet);

		time = 25;
		MineFantasyKnowledgeList.heavyShovelR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_HEAVY_SHOVEL, "tier", true, "heavy_hammer", -1, -1, time, "LRII", "PPII", "LRII", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_HEAVY_SHOVEL, bar, bar, bar, bar, bar, bar, plank, plank, strip, strip, rivet, rivet);

		time = 15;
		MineFantasyKnowledgeList.trowR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_TROW, "tier", true, "hammer", -1, -1, time, "L  ", "PIR", "L  ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_TROW, bar, plank, strip, strip, rivet);

		time = 30;
		MineFantasyKnowledgeList.scytheR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_SCYTHE, "tier", true, "heavy_hammer", -1, -1, time, "   I ", "L PIR", "PPPIR", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_SCYTHE, bar, bar, bar, plank, plank, plank, plank, strip, rivet, rivet);

		time = 14;
		MineFantasyKnowledgeList.mattockR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_MATTOCK, "tier", true, "hammer", -1, -1, time, "L I", "PPI", "LIR", 'I', bar, 'P', plank, 'L', strip, 'R', rivet);
		Salvage.addSalvage(MineFantasyItems.STANDARD_MATTOCK, bar, bar, bar, rivet, plank, plank, strip, strip);

		time = 15;
		MineFantasyKnowledgeList.lumberR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_LUMBER, "tier", true, "heavy_hammer", -1, -1, time, "L IIR", "PPPIR", "L   R", 'I', bar, 'P', plank, 'L', strip, 'R', rivet);
		Salvage.addSalvage(MineFantasyItems.STANDARD_LUMBER, bar, bar, bar, rivet, rivet, rivet, plank, plank, plank, strip, strip);
	}

	private static void addStandardCrafters() {
		Item bar = MineFantasyItems.BAR;
		Item plank = MineFantasyItems.TIMBER;
		Item strip = MineFantasyItems.LEATHER_STRIP;
		Item rivet = MineFantasyItems.RIVET;

		int time = 10;
		MineFantasyKnowledgeList.hammerR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_HAMMER, "tier", true, "hammer", 0, 0, time, "I", "L", "P", 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_HAMMER, bar, plank, strip);

		time = 15;
		MineFantasyKnowledgeList.heavyHammerR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_HEAVY_HAMMER, "tier", true, "hammer", -1, -1, time, " II", "RLI", " P ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_HEAVY_HAMMER, bar, bar, bar, plank, strip, rivet);

		time = 10;
		MineFantasyKnowledgeList.tongsR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_TONGS, "tier", true, "hammer", -1, -1, time, "I ", " I", 'I', bar);
		Salvage.addSalvage(MineFantasyItems.STANDARD_TONGS, bar, bar);

		time = 10;
		MineFantasyKnowledgeList.knifeR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_KNIFE, "tier", true, "hammer", -1, -1, time, "I ", "PL", 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_KNIFE, bar, plank, strip);

		time = 12;
		MineFantasyKnowledgeList.shearsR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_SHEARS, "tier", true, "hammer", -1, -1, time, " I ", "PLI", " P ", 'I', bar, 'P', plank, 'L', Items.LEATHER);
		Salvage.addSalvage(MineFantasyItems.STANDARD_SHEARS, bar, bar, plank, plank, Items.LEATHER);

		time = 20;
		MineFantasyKnowledgeList.sawsR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_SAW, "tier", true, "hammer", -1, -1, time, "PIII", "LI  ", 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_SAW, bar, bar, bar, bar, plank, strip);

		time = 15;
		MineFantasyKnowledgeList.spannerR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_SPANNER,
				"tier", true, "hammer", -1, -1, time, new Object[] {"  I ", "  II", "LP  ", " L  ",

						'I', bar, 'P', plank, 'L', strip,});
		Salvage.addSalvage(MineFantasyItems.STANDARD_SPANNER, bar, bar, bar, plank, strip, strip);
	}

	private static void addStandardWeapons() {
		Item bar = MineFantasyItems.BAR;
		Item plank = MineFantasyItems.TIMBER;
		Item strip = MineFantasyItems.LEATHER_STRIP;
		Item rivet = MineFantasyItems.RIVET;

		int time = 15;
		MineFantasyKnowledgeList.daggerR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_DAGGER, "tier", true, "hammer", -1, -1, time, "L  ", "PII", "L  ", 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_DAGGER, bar, bar, plank, strip, strip);

		time = 25;
		MineFantasyKnowledgeList.swordR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_SWORD, "tier", true, "hammer", -1, -1, time, "LI  ", "PIII", "LI  ", 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_SWORD, bar, bar, bar, bar, bar, plank, strip, strip);

		time = 20;
		MineFantasyKnowledgeList.waraxeR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_WARAXE, "tier", true, "hammer", -1, -1, time, "LII", "PPI", "L I", 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_WARAXE, bar, bar, bar, bar, plank, plank, strip, strip);

		MineFantasyKnowledgeList.maceR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_MACE, "tier", true, "hammer", -1, -1, time, "L II", "PPII", "L   ", 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_MACE, bar, bar, bar, bar, plank, plank, strip, strip);

		MineFantasyKnowledgeList.spearR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_SPEAR, "tier", true, "hammer", -1, -1, time, " LLI ", "PPPPI", " LLI ", 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_SPEAR, bar, bar, bar, plank, plank, plank, plank, strip, strip, strip, strip);

		// HEAVY
		time = 30;
		MineFantasyKnowledgeList.katanaR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_KATANA, "tier", true, "heavy_hammer", -1, -1, time, "LR   I", "PIIII ", "LI    ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_KATANA, bar, bar, bar, bar, bar, bar, plank, strip, strip, rivet);

		time = 40;
		MineFantasyKnowledgeList.gswordR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_GREATSWORD, "tier", true, "heavy_hammer", -1, -1, time, "LIR   ", "PIIIII", "LIR   ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_GREATSWORD, bar, bar, bar, bar, bar, bar, bar, plank, strip, strip, rivet, rivet);

		time = 30;
		MineFantasyKnowledgeList.battleaxeR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_BATTLEAXE, "tier", true, "heavy_hammer", -1, -1, time, "LLIIR", "PPPIR", "LLIIR", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_BATTLEAXE, bar, bar, bar, bar, bar, plank, plank, plank, strip, strip, strip, strip, rivet, rivet, rivet);

		MineFantasyKnowledgeList.whammerR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_WARHAMMER, "tier", true, "heavy_hammer", -1, -1, time, "LL IIR", "PPPIIR", "LL  IR", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_WARHAMMER, bar, bar, bar, bar, bar, plank, plank, plank, strip, strip, strip, strip, rivet, rivet, rivet);

		MineFantasyKnowledgeList.halbeardR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_HALBEARD, "tier", true, "heavy_hammer", -1, -1, time, "LLRII", "PPPPI", "LLRI ", 'R', rivet, 'I', bar, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_HALBEARD, bar, bar, bar, bar, plank, plank, plank, plank, strip, strip, strip, strip, rivet, rivet);

		time = 25;
		MineFantasyKnowledgeList.bowR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_BOW, "tier", true, "hammer", -1, -1, time, "PSSSP", " PLP ", 'I', bar, 'S', Items.STRING, 'P', plank, 'L', strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_BOW, plank, plank, plank, plank, strip, Items.STRING, Items.STRING, Items.STRING);

		time = 60;
		MineFantasyKnowledgeList.lanceR = MineFantasyReforgedAPI.addAnvilToolRecipe(Skill.ARTISANRY, MineFantasyItems.STANDARD_LANCE, "tier", true, "heavy_hammer", -1, -1, time, "IR    ", "IIIIII", "IR    ", 'R', rivet, 'I', bar);
		Salvage.addSalvage(MineFantasyItems.STANDARD_LANCE, bar, bar, bar, bar, bar, bar, bar, bar, rivet, rivet);
	}
}