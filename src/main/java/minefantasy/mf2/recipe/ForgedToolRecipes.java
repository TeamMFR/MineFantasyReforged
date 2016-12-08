package minefantasy.mf2.recipe;

import java.util.ArrayList;
import java.util.Iterator;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.crafting.BasicTierRecipe;
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

public class ForgedToolRecipes 
{
	public static boolean easyBars = false;
	
	private static final Skill artisanry = SkillList.artisanry;
	private static final Skill engineering = SkillList.engineering;
	private static final Skill construction = SkillList.construction;
	public static void init() 
	{
		CarpenterRecipes.initTierWood();
		addStandardTools();
		addStandardCrafters();
		addStandardWeapons();
		addComponentTools();
		addMetalComponents();
		
		ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
		ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
		
		Iterator iteratorMetal = metal.iterator();
		
		while(iteratorMetal.hasNext())
    	{
    		CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
    		ItemStack bar =  ComponentListMF.bar.createComm(customMat.name);
    		for(ItemStack ingot: OreDictionary.getOres("ingot"+customMat.name))
    		{
    			if(easyBars)
    			{
    				KnowledgeListMF.barRE.add(
	    			GameRegistry.addShapedRecipe( ComponentListMF.bar.createComm(customMat.name), new Object[]
					{
						"I",
						'I', ingot,
					}));
    			}
    			else
    			{
	    			KnowledgeListMF.barR.add(
	    			MineFantasyAPI.addAnvilRecipe(artisanry, bar, "smelt"+customMat.name, true, "hammer", customMat.crafterTier, customMat.crafterAnvilTier, (int)(customMat.craftTimeModifier/2F), new Object[]
					{
						"I",
						'I', ingot,
					}));
    			}
    		}
    		
    		ItemStack defaultIngot = customMat.getItem();
    		if(defaultIngot != null)
    		{
    			if(easyBars)
    			{
    				KnowledgeListMF.baringotRE.add(
	    			BasicTierRecipe.add(defaultIngot, new Object[]
					{
						"I",
						'I', ComponentListMF.bar.createComm(customMat.name),
					}));
    			}
    			else
    			{
	    			KnowledgeListMF.baringotR.add(
	    			MineFantasyAPI.addAnvilRecipe(artisanry, defaultIngot, "", true, "hammer", customMat.crafterTier, customMat.crafterAnvilTier, (int)(customMat.craftTimeModifier/2F), new Object[]
					{
						"F", 
						"I",
						'I', bar,
						'F', ComponentListMF.flux
					}));
    			}
    		}
    	}
		
		KnowledgeListMF.tinderboxR =
		MineFantasyAPI.addAnvilRecipe(null, new ItemStack(ToolListMF.tinderbox), "", true, "hammer", 0, 0, 10, new Object[]{
			" F ",
			"SWS",
			" I ",
			'F', Items.flint,
			'S', Items.stick,
			'W', Blocks.wool,
			'I', Items.iron_ingot
		});
		for(ItemStack ingot: OreDictionary.getOres("ingotSteel"))
		{
			KnowledgeListMF.flintAndSteelR =
			MineFantasyAPI.addAnvilRecipe(null, new ItemStack(Items.flint_and_steel), "", true, "hammer", 0, 0, 10, new Object[]{
				"  F",
				"IC ",
				" I ",
				'F', Items.flint,
				'C', Items.coal,
				'I', ingot
			});
		}
	}
	
	private static void addMetalComponents() 
	{
		int time = 2;
		Item bar = ComponentListMF.bar;
		Item hunk = ComponentListMF.metalHunk;
		
		KnowledgeListMF.hunkR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, new ItemStack(hunk, 4), "", true, "hammer", 0, 0, time, new Object[]
		{
			"I",
			'I', bar,
		});
		
		KnowledgeListMF.ingotR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, bar, "", true, "hammer", 0, 0, time, new Object[]
		{
			"II",
			"II",
			'I', hunk
		});
		
		time = 8;
		int count = 1;
		KnowledgeListMF.bucketR =
		MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(Items.bucket, count), "", true, "hammer", 0, 0, time, new Object[]
		{
			"I I",
			" I ",
			'I', bar,
		});
	}

	private static void addComponentTools()
	{
		Item bar = ComponentListMF.bar;
		Item plank = ComponentListMF.plank;
		Item strip = ComponentListMF.leather_strip;
		Item rivet = ComponentListMF.rivet;
		Item hunk = ComponentListMF.metalHunk;
		
		int time = 10;
		
		KnowledgeListMF.nailR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, new ItemStack(ComponentListMF.nail, 16), "", true, "hammer", -1, -1, time, new Object[]
		{
			"HH",
			" H",
			" H",
			
			'H', hunk
		});
		KnowledgeListMF.rivetR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, new ItemStack(ComponentListMF.rivet, 8), "", true, "hammer", -1, -1, time, new Object[]
		{
			"H H",
			" H ",
			" H ",
			
			'H', hunk
		});
		
		KnowledgeListMF.needleR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_needle, "", true, "hammer", -1, -1, time, new Object[]
		{
			"H",
			"H",
			"H",
			"H",
			
			'H', hunk
		});
		Salvage.addSalvage(CustomToolListMF.standard_needle, bar);
		
		time = 3;
		KnowledgeListMF.crossBoltR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_bolt, "", true, "hammer", 0, 0, time, new Object[]
		{
			"H",
			"F",
			
			'F', ComponentListMF.fletching,
			'H', hunk
		});
		time = 2;
		KnowledgeListMF.arrowheadR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, new ItemStack(ComponentListMF.arrowhead, 4), "", true, "hammer", 0, 0, time, new Object[]
		{
			"H ",
			"HH",
			"H ",
			
			'H', hunk
		});
		time = 5;
		KnowledgeListMF.bodkinheadR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, new ItemStack(ComponentListMF.bodkinhead, 4), "", true, "hammer", 0, 0, time, new Object[]
		{
			"H  ",
			" HH",
			"H  ",
			
			'H', hunk
		});
		time = 5;
		KnowledgeListMF.broadheadR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, new ItemStack(ComponentListMF.broadhead, 4), "", true, "hammer", 0, 0, time, new Object[]
		{
			"H ",
			" H",
			" H",
			"H ",
			
			'H', hunk
		});
		Salvage.addSalvage(CustomToolListMF.standard_bolt, ComponentListMF.fletching, hunk);
		Salvage.addSalvage(ComponentListMF.arrowhead, hunk);
		Salvage.addSalvage(ComponentListMF.bodkinhead, hunk);
		Salvage.addSalvage(ComponentListMF.broadhead, hunk);
		
		
		time = 1;
		KnowledgeListMF.arrowR.add(
		MineFantasyAPI.addCarpenterToolRecipe(artisanry, CustomToolListMF.standard_arrow, "arrows", "dig.wood", 1, new Object[]
		{
			"H",
			"S",
			"F",
			
			'S', Items.stick,
			'F', ComponentListMF.fletching,
			'H', ComponentListMF.arrowhead
		}));
		KnowledgeListMF.arrowR.add(
		MineFantasyAPI.addCarpenterToolRecipe(artisanry, CustomToolListMF.standard_arrow_bodkin, "arrowsBodkin", "dig.wood", 1, new Object[]
		{
			"H",
			"S",
			"F",
			
			'S', Items.stick,
			'F', ComponentListMF.fletching,
			'H', ComponentListMF.bodkinhead
		}));
		KnowledgeListMF.arrowR.add(
		MineFantasyAPI.addCarpenterToolRecipe(artisanry, CustomToolListMF.standard_arrow_broad, "arrowsBroad", "dig.wood", 1, new Object[]
		{
			"H",
			"S",
			"F",
			
			'S', Items.stick,
			'F', ComponentListMF.fletching,
			'H', ComponentListMF.broadhead
		}));
		Salvage.addSalvage(CustomToolListMF.standard_arrow, ComponentListMF.arrowhead, Items.stick, ComponentListMF.fletching);
		Salvage.addSalvage(CustomToolListMF.standard_arrow_bodkin, ComponentListMF.bodkinhead, Items.stick, ComponentListMF.fletching);
		Salvage.addSalvage(CustomToolListMF.standard_arrow_broad, ComponentListMF.broadhead, Items.stick, ComponentListMF.fletching);
		
	}

	private static void addStandardTools() 
	{
		Item bar = ComponentListMF.bar;
		Item plank = ComponentListMF.plank;
		Item strip = ComponentListMF.leather_strip;
		Item rivet = ComponentListMF.rivet;
		
		int time = 15;
		KnowledgeListMF.pickR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_pick, "", true, "hammer", 0, 0, time, new Object[]
		{
			"L I",
			"PPI",
			"L I",
			
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_pick,
				bar, bar, bar,
				plank, plank,
				strip, strip);
		
		time = 15;
		KnowledgeListMF.axeR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_axe, "", true, "hammer", 0, 0, time, new Object[]
		{
			"LII",
			"PPI",
			"L  ",
			
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_axe,
				bar, bar, bar,
				plank, plank,
				strip, strip);
		
		time = 12;
		KnowledgeListMF.hoeR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_hoe, "", true, "hammer", 0, 0, time, new Object[]
		{
			"L I",
			"PPI",
			"L  ",
			
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_hoe,
				bar, bar,
				plank, plank,
				strip, strip);
		
		time = 10;
		KnowledgeListMF.spadeR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_spade, "", true, "hammer", 0, 0, time, new Object[]
		{
			"L  ",
			"PPI",
			"L  ",
			
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_spade,
				bar,
				plank, plank,
				strip, strip);
		
		//ADVANCED
		time = 25;
		KnowledgeListMF.hvyPickR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_hvypick, "", true, "hvyhammer", 0, 0, time, new Object[]
		{
			"LR I",
			"PPII",
			"LRII",
			
			'R', rivet,
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_hvypick,
				bar, bar, bar, bar, bar,
				plank, plank,
				strip, strip,
				rivet, rivet);
		
		time = 15;
		KnowledgeListMF.handpickR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_handpick, "", true, "hammer", 0, 0, time, new Object[]
		{
			"LI ",
			"PIR",
			"L  ",
			
			'R', rivet,
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_handpick,
				bar, bar,
				plank,
				strip, strip,
				rivet);
		
		time = 25;
		KnowledgeListMF.hvyShovelR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_hvyshovel, "", true, "hvyhammer", 0, 0, time, new Object[]
		{
			"LRII",
			"PPII",
			"LRII",
			
			'R', rivet,
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_hvyshovel,
				bar, bar, bar, bar, bar, bar,
				plank, plank,
				strip, strip,
				rivet, rivet);
		
		time = 15;
		KnowledgeListMF.trowR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_trow, "", true, "hammer", 0, 0, time, new Object[]
		{
			"L  ",
			"PIR",
			"L  ",
			
			'R', rivet,
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_trow,
				bar,
				plank,
				strip, strip,
				rivet);
		
		time = 30;
		KnowledgeListMF.scytheR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_scythe, "", true, "hvyhammer", 0, 0, time, new Object[]
		{
			"   I ",
			"L PIR",
			"PPPIR",
			
			'R', rivet,
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_scythe,
				bar, bar, bar,
				plank, plank, plank, plank,
				strip,
				rivet, rivet);
		
		time = 14;
		KnowledgeListMF.mattockR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_mattock, "", true, "hammer", 0, 0, time, new Object[]
		{
			"L I",
			"PPI",
			"LIR",
			
			'I', bar,
			'P', plank,
			'L', strip,
			'R', rivet
		});
		Salvage.addSalvage(CustomToolListMF.standard_mattock,
				bar, bar, bar, rivet,
				plank, plank,
				strip, strip);
	}
	
	
	private static void addStandardCrafters() 
	{
		Item bar = ComponentListMF.bar;
		Item plank = ComponentListMF.plank;
		Item strip = ComponentListMF.leather_strip;
		Item rivet = ComponentListMF.rivet;
		
		int time = 10;
		KnowledgeListMF.hammerR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_hammer, "", true, "hammer", 0, 0, time, new Object[]
		{
			"I",
			"L",
			"P",
			
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_hammer,
				bar,
				plank,
				strip);
		
		time = 15;
		KnowledgeListMF.hvyHammerR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_hvyhammer, "", true, "hammer", -1, -1, time, new Object[]
		{
			" II",
			"RLI",
			" P ",
			
			'R', rivet,
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_hvyhammer,
				bar, bar, bar,
				plank,
				strip,
				rivet);
		
		time = 10;
		KnowledgeListMF.tongsR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_tongs, "", true, "hammer", -1, -1, time, new Object[]
		{
			"I ",
			" I",
			
			'I', bar,
		});
		Salvage.addSalvage(CustomToolListMF.standard_tongs,
				bar, bar);
		
		time = 10;
		KnowledgeListMF.knifeR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_knife, "", true, "hammer", 0, 0, time, new Object[]
		{
			"I ",
			"PL",
			
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_knife,
				bar, bar,
				plank,
				strip);
		
		time = 12;
		KnowledgeListMF.shearsR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_shears, "", true, "hammer", 0, 0, time, new Object[]
		{
			" I ",
			"PLI",
			" P ",
			
			'I', bar,
			'P', plank,
			'L', Items.leather,
		});
		Salvage.addSalvage(CustomToolListMF.standard_shears,
				bar, bar,
				plank, plank,
				Items.leather);
		
		time = 20;
		KnowledgeListMF.sawsR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_saw, "", true, "hammer", 0, 0, time, new Object[]
		{
			"PIII",
			"LI  ",
			
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_saw,
				bar, bar, bar, bar,
				plank,
				strip);
		
		time = 15;
		KnowledgeListMF.spannerR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_spanner, "", true, "hammer", 0, 0, time, new Object[]
		{
			"  I ",
			"  II",
			"LP  ",
			" L  ",
			
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_spanner,
				bar, bar, bar,
				plank,
				strip, strip);
	}
	private static void addStandardWeapons() 
	{
		Item bar = ComponentListMF.bar;
		Item plank = ComponentListMF.plank;
		Item strip = ComponentListMF.leather_strip;
		Item rivet = ComponentListMF.rivet;
		
		int time = 15;
		KnowledgeListMF.daggerR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_dagger, "", true, "hammer", 0, 0, time, new Object[]
		{
			"L  ",
			"PII",
			"L  ",
			
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_dagger,
				bar, bar,
				plank,
				strip, strip);
		Salvage.addSalvage(CustomToolListMF.dragonforged_dagger,
				bar, bar,
				plank,
				strip, strip);
		
		
		time = 25;
		KnowledgeListMF.swordR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_sword, "", true, "hammer", 0, 0, time, new Object[]
		{
			"LI  ",
			"PIII",
			"LI  ",
			
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_sword,
				bar, bar, bar, bar, bar,
				plank,
				strip, strip);
		Salvage.addSalvage(CustomToolListMF.dragonforged_sword,
				bar, bar, bar, bar, bar,
				plank,
				strip, strip);
		
		time = 20;
		KnowledgeListMF.waraxeR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_waraxe, "", true, "hammer", 0, 0, time, new Object[]
		{
			"LII",
			"PPI",
			"L I",
			
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_waraxe,
				bar, bar, bar, bar,
				plank, plank,
				strip, strip);
		Salvage.addSalvage(CustomToolListMF.dragonforged_waraxe,
				bar, bar, bar, bar,
				plank, plank,
				strip, strip);
		
		KnowledgeListMF.maceR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_mace, "", true, "hammer", 0, 0, time, new Object[]
		{
			"L II",
			"PPII",
			"L   ",
			
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_mace,
				bar, bar, bar, bar,
				plank, plank,
				strip, strip);
		Salvage.addSalvage(CustomToolListMF.dragonforged_mace,
				bar, bar, bar, bar,
				plank, plank,
				strip, strip);
		
		KnowledgeListMF.spearR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_spear, "", true, "hammer", 0, 0, time, new Object[]
		{
			" LLI ",
			"PPPPI",
			" LLI ",
			
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_spear,
				bar, bar, bar,
				plank, plank, plank, plank,
				strip, strip, strip, strip);
		Salvage.addSalvage(CustomToolListMF.dragonforged_spear,
				bar, bar, bar,
				plank, plank, plank, plank,
				strip, strip, strip, strip);
		
		//HEAVY
		time = 30;
		KnowledgeListMF.katanaR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_katana, "", true, "hvyhammer", 0, 0, time, new Object[]
		{
			"LR   I",
			"PIIII ",
			"LI    ",
			
			'R', rivet,
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_katana,
				bar, bar, bar, bar, bar, bar,
				plank,
				strip, strip,
				rivet);
		Salvage.addSalvage(CustomToolListMF.dragonforged_katana,
				bar, bar, bar, bar, bar, bar,
				plank,
				strip, strip,
				rivet);
		
		time = 40;
		KnowledgeListMF.gswordR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_greatsword, "", true, "hvyhammer", 0, 0, time, new Object[]
		{
			"LIR   ",
			"PIIIII",
			"LIR   ",
			
			'R', rivet,
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_greatsword,
				bar, bar, bar, bar, bar, bar, bar,
				plank,
				strip, strip,
				rivet, rivet);
		Salvage.addSalvage(CustomToolListMF.dragonforged_greatsword,
				bar, bar, bar, bar, bar, bar, bar,
				plank,
				strip, strip,
				rivet, rivet);
		
		time = 30;
		KnowledgeListMF.battleaxeR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_battleaxe, "", true, "hvyhammer", 0, 0, time, new Object[]
		{
			"LLIIR",
			"PPPIR",
			"LLIIR",
			
			'R', rivet,
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_battleaxe,
				bar, bar, bar, bar, bar,
				plank, plank, plank,
				strip, strip, strip, strip,
				rivet, rivet, rivet);
		Salvage.addSalvage(CustomToolListMF.dragonforged_battleaxe,
				bar, bar, bar, bar, bar,
				plank, plank, plank,
				strip, strip, strip, strip,
				rivet, rivet, rivet);
		
		KnowledgeListMF.whammerR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_warhammer, "", true, "hvyhammer", 0, 0, time, new Object[]
		{
			"LL IIR",
			"PPPIIR",
			"LL  IR",
			
			'R', rivet,
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_warhammer,
				bar, bar, bar, bar, bar,
				plank, plank, plank,
				strip, strip, strip, strip,
				rivet, rivet, rivet);
		Salvage.addSalvage(CustomToolListMF.dragonforged_warhammer,
				bar, bar, bar, bar, bar,
				plank, plank, plank,
				strip, strip, strip, strip,
				rivet, rivet, rivet);
		
		KnowledgeListMF.halbeardR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_halbeard, "", true, "hvyhammer", 0, 0, time, new Object[]
		{
			"LLRII",
			"PPPPI",
			"LLRI ",
			
			'R', rivet,
			'I', bar,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_halbeard,
				bar, bar, bar, bar,
				plank, plank, plank, plank,
				strip, strip, strip, strip,
				rivet, rivet);
		Salvage.addSalvage(CustomToolListMF.dragonforged_halbeard,
				bar, bar, bar, bar,
				plank, plank, plank, plank,
				strip, strip, strip, strip,
				rivet, rivet);
		
		time = 25;
		KnowledgeListMF.bowR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_bow, "", true, "hammer", 0, 0, time, new Object[]
		{
			"PSSSP",
			" PLP ",
			
			'I', bar,
			'S', Items.string,
			'P', plank,
			'L', strip,
		});
		Salvage.addSalvage(CustomToolListMF.standard_bow,
				plank, plank, plank, plank,
				strip,
				Items.string, Items.string, Items.string);
		
		time = 60;
		KnowledgeListMF.lanceR =
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.standard_lance, "", true, "hvyhammer", 0, 0, time, new Object[]
		{
			"IR    ",
			"IIIIII",
			"IR    ",
			
			'R', rivet,
			'I', bar,
		});
		Salvage.addSalvage(CustomToolListMF.standard_lance,
				bar, bar, bar, bar, bar, bar, bar, bar,
				rivet, rivet);
		Salvage.addSalvage(CustomToolListMF.dragonforged_lance,
				bar, bar, bar, bar, bar, bar, bar, bar,
				rivet, rivet);
	}
	/*
	private static void addDwarven(CustomMaterial material, ItemStack ingot, Object specialComponent) 
	{
		ItemStack bar = material.getItem();
		Item plank = ComponentListMF.plankRefined;
		Item strip = ComponentListMF.leather_strip;
		Item rivet = ComponentListMF.rivet;
		
		int time = 25;
		
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.dwarven_sword, "", true, "hammer", 0, 0, time, new Object[]
		{
			"LI  ",
			"OIII",
			"LI  ",
			
			'O', specialComponent,
			'I', bar,
			'L', strip,
		});
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.dwarven_waraxe, "", true, "hammer", 0, 0, time, new Object[]
		{
			"LII",
			"POI",
			"L I",
			
			'O', specialComponent,
			'I', bar,
			'P', plank,
			'L', strip,
		});
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.dwarven_mace, "", true, "hammer", 0, 0, time, new Object[]
		{
			"L II",
			"POII",
			"L   ",
			
			'O', specialComponent,
			'I', bar,
			'P', plank,
			'L', strip,
		});
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.dwarven_dagger, "", true, "hammer", 0, 0, time, new Object[]
		{
			"L  ",
			"OII",
			"L  ",
			
			'O', specialComponent,
			'I', bar,
			'L', strip,
		});
		MineFantasyAPI.addAnvilToolRecipe(artisanry, CustomToolListMF.dwarven_spear, "", true, "hammer", 0, 0, time, new Object[]
		{
			" L I ",
			"PPPOI",
			" L I ",
			
			'O', specialComponent,
			'I', bar,
			'P', plank,
			'L', strip,
		});
	}
	*/
}
