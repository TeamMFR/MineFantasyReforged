package minefantasy.mf2.recipe;

import java.util.ArrayList;
import java.util.Iterator;

import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.crafting.Salvage;
import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.item.list.ArmourListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CustomToolListMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import minefantasy.mf2.item.ItemComponentMF;

public class ForgedToolRecipes 
{
	private static final Skill artisanry = SkillList.artisanry;
	private static final Skill engineering = SkillList.engineering;
	private static final Skill construction = SkillList.construction;
	public static void init() 
	{
		CarpenterRecipes.initTierWood();
		ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
		ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
		
		Iterator iteratorMetal = metal.iterator();
		
		while(iteratorMetal.hasNext())
    	{
    		CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();

    		
    		for(ItemStack ingot: OreDictionary.getOres("ingot"+customMat.name))
    		{
    			addMetalComponents(customMat, ingot);
    			Iterator iteratorWood = wood.iterator();
    			
    			while(iteratorWood.hasNext())
    			{
    				CustomMaterial customWoodMat = (CustomMaterial) iteratorWood.next();
    				
    	    		ItemStack plank = ((ItemComponentMF)ComponentListMF.plank).construct(customWoodMat.name);
    	    			addStandardTools(customMat, ingot, customWoodMat, plank);
    	    			addStandardCrafters(customMat, ingot,customWoodMat, plank);
    	    			addStandardWeapons(customMat, ingot,customWoodMat, plank);
    	    		
    			}
    		}
    		addComponentTools(customMat);
    		
    		ItemStack defaultIngot = customMat.getItem();
    		if(defaultIngot != null)
    		{
    			KnowledgeListMF.ingotR.add(
    			MineFantasyAPI.addAnvilRecipe(artisanry, defaultIngot, "smelt"+customMat.name, true, "hammer", customMat.crafterTier, customMat.crafterAnvilTier, (int)(2*customMat.craftTimeModifier), new Object[]
				{
					"II",
					"II",
					'I', ComponentListMF.metalHunk.createComm(customMat.name),
				}));
    		}
    	}
	}
	
	private static void addMetalComponents(CustomMaterial material, ItemStack ingot) 
	{
		int time = 2;
		KnowledgeListMF.hunkR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, ComponentListMF.metalHunk.createComm(material.name, 4), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"I",
			'I', ingot,
		}));
		time = 8;
		int count = Math.max(1, material.crafterTier);
		KnowledgeListMF.bucketR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(Items.bucket, count), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"I I",
			" I ",
			'I', ingot,
		}));
	}

	private static void addComponentTools(CustomMaterial material)
	{
		ItemStack salvage = material.getItem();
		int time = 10;
		ItemStack hunk = ComponentListMF.metalHunk.createComm(material.name);
		
		KnowledgeListMF.nailR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.nail, (material.tier+1)*4), "smelt"+material.name, true, "hammer", -1, -1, (int)(time*material.craftTimeModifier), new Object[]
		{
			"HH",
			" H",
			" H",
			
			'H', hunk
		}));
		KnowledgeListMF.rivetR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.rivet, (material.tier+1)*2), "smelt"+material.name, true, "hammer", -1, -1, (int)(time*material.craftTimeModifier), new Object[]
		{
			"H H",
			" H ",
			" H ",
			
			'H', hunk
		}));
		
		KnowledgeListMF.needleR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_needle.construct(material.name), "smelt"+material.name, true, "hammer", -1, -1, (int)(time*material.craftTimeModifier), new Object[]
		{
			"H",
			"H",
			"H",
			"H",
			
			'H', hunk
		}));
		Salvage.addSalvage(CustomToolListMF.standard_needle.construct(material.name), salvage);
		
		time = 3;
		KnowledgeListMF.crossBoltR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_bolt.construct(material.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"H",
			"F",
			
			'F', ComponentListMF.fletching,
			'H', hunk
		}));
		time = 5;
		KnowledgeListMF.arrowheadR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, ComponentListMF.arrowhead.createComm(material.name, 4), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"H ",
			"HH",
			"H ",
			
			'H', hunk
		}));
		time = 6;
		KnowledgeListMF.bodkinheadR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, ComponentListMF.bodkinhead.createComm(material.name, 4), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"H  ",
			" HH",
			"H  ",
			
			'H', hunk
		}));
		time = 10;
		KnowledgeListMF.broadheadR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, ComponentListMF.broadhead.createComm(material.name, 4), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"H ",
			" H",
			" H",
			"H ",
			
			'H', hunk
		}));
		Salvage.addSalvage(CustomToolListMF.standard_bolt.construct(material.name), ComponentListMF.fletching, hunk);
		Salvage.addSalvage(ComponentListMF.arrowhead.createComm(material.name), hunk);
		Salvage.addSalvage(ComponentListMF.bodkinhead.createComm(material.name), hunk);
		Salvage.addSalvage(ComponentListMF.broadhead.createComm(material.name), hunk);
		
		time = 1;
		KnowledgeListMF.arrowR.add(
		MineFantasyAPI.addCarpenterRecipe(artisanry, CustomToolListMF.standard_arrow.construct(material.name), "arrows", "dig.wood", 1, new Object[]
		{
			"H",
			"S",
			"F",
			
			'S', Items.stick,
			'F', ComponentListMF.fletching,
			'H', ComponentListMF.arrowhead.createComm(material.name)
		}));
		KnowledgeListMF.arrowR.add(
		MineFantasyAPI.addCarpenterRecipe(artisanry, CustomToolListMF.standard_arrow_bodkin.construct(material.name), "arrowsBodkin", "dig.wood", 1, new Object[]
		{
			"H",
			"S",
			"F",
			
			'S', Items.stick,
			'F', ComponentListMF.fletching,
			'H', ComponentListMF.bodkinhead.createComm(material.name)
		}));
		KnowledgeListMF.arrowR.add(
		MineFantasyAPI.addCarpenterRecipe(artisanry, CustomToolListMF.standard_arrow_broad.construct(material.name), "arrowsBroad", "dig.wood", 1, new Object[]
		{
			"H",
			"S",
			"F",
			
			'S', Items.stick,
			'F', ComponentListMF.fletching,
			'H', ComponentListMF.broadhead.createComm(material.name)
		}));
		Salvage.addSalvage(CustomToolListMF.standard_arrow.construct(material.name), ComponentListMF.arrowhead.createComm(material.name), Items.stick, ComponentListMF.fletching);
		Salvage.addSalvage(CustomToolListMF.standard_arrow_bodkin.construct(material.name), ComponentListMF.bodkinhead.createComm(material.name), Items.stick, ComponentListMF.fletching);
		Salvage.addSalvage(CustomToolListMF.standard_arrow_broad.construct(material.name), ComponentListMF.broadhead.createComm(material.name), Items.stick, ComponentListMF.fletching);
	}

	private static void addStandardTools(CustomMaterial material, ItemStack ingot, CustomMaterial haftMaterial, ItemStack plank) 
	{
		ItemStack salvage = material.getItem();
		ItemStack scrapWood = ((ItemComponentMF) ComponentListMF.plank).construct("Scrap");
		Item strip = ComponentListMF.leather_strip;
		Item rivet = ComponentListMF.rivet;
		int time = 15;
		KnowledgeListMF.pickR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_pick.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"L I",
			"PPI",
			"L I",
			
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_pick.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage,
				plank, plank,
				strip, strip);
		
		time = 15;
		KnowledgeListMF.axeR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_axe.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"LII",
			"PPI",
			"L  ",
			
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_axe.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage,
				plank, plank,
				strip, strip);
		
		time = 12;
		KnowledgeListMF.hoeR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_hoe.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"L I",
			"PPI",
			"L  ",
			
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_hoe.construct(material.name,haftMaterial.name),
				salvage, salvage,
				plank, plank,
				strip, strip);
		
		time = 10;
		KnowledgeListMF.spadeR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_spade.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"L  ",
			"PPI",
			"L  ",
			
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_spade.construct(material.name,haftMaterial.name),
				salvage,
				plank, plank,
				strip, strip);
		
		//ADVANCED
		time = 25;
		KnowledgeListMF.hvyPickR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_hvypick.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hvyhammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"LR I",
			"PPII",
			"LRII",
			
			'R', rivet,
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_hvypick.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage, salvage,
				plank, plank,
				strip, strip,
				rivet, rivet);
		
		time = 15;
		KnowledgeListMF.handpickR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_handpick.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"LI ",
			"PIR",
			"L  ",
			
			'R', rivet,
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_handpick.construct(material.name,haftMaterial.name),
				salvage, salvage,
				plank,
				strip, strip,
				rivet);
		
		time = 25;
		KnowledgeListMF.hvyShovelR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_hvyshovel.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hvyhammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"LRII",
			"PPII",
			"LRII",
			
			'R', rivet,
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_hvyshovel.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage, salvage, salvage,
				plank, plank,
				strip, strip,
				rivet, rivet);
		
		time = 15;
		KnowledgeListMF.trowR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_trow.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"L  ",
			"PIR",
			"L  ",
			
			'R', rivet,
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_trow.construct(material.name,haftMaterial.name),
				salvage,
				plank,
				strip, strip,
				rivet);
		
		time = 30;
		KnowledgeListMF.scytheR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_scythe.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hvyhammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"   I ",
			"L PIR",
			"PPPIR",
			
			'R', rivet,
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_scythe.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage,
				plank, plank, plank, plank,
				strip,
				rivet, rivet);
		
		time = 10;
		if(material.name.equalsIgnoreCase("iron"))
		{
			KnowledgeListMF.tinderboxR =
			MineFantasyAPI.addAnvilRecipe(null, new ItemStack(ToolListMF.tinderbox), "", true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]{
				" F ",
				"SWS",
				" I ",
				'F', Items.flint,
				'S', Items.stick,
				'W', Blocks.wool,
				'I', ingot
			});
		}
		if(material.name.equalsIgnoreCase("steel"))
		{
			KnowledgeListMF.flintAndSteelR =
			MineFantasyAPI.addAnvilRecipe(null, new ItemStack(Items.flint_and_steel), "", true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]{
				"  F",
				"IC ",
				" I ",
				'F', Items.flint,
				'C', Items.coal,
				'I', ingot
			});
		}
	}
	
	
	private static void addStandardCrafters(CustomMaterial material, ItemStack ingot,CustomMaterial haftMaterial, ItemStack plank) 
	{
		ItemStack salvage = material.getItem();
		Item strip = ComponentListMF.leather_strip;
		Item rivet = ComponentListMF.rivet;
		
		int time = 10;
		KnowledgeListMF.hammerR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_hammer.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", -1, -1, (int)(time*material.craftTimeModifier), new Object[]
		{
			"I",
			"L",
			"P",
			
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_hammer.construct(material.name,haftMaterial.name),
				salvage,
				plank,
				strip);
		
		time = 15;
		KnowledgeListMF.hvyHammerR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_hvyhammer.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", -1, -1, (int)(time*material.craftTimeModifier), new Object[]
		{
			" II",
			"RLI",
			" P ",
			
			'R', rivet,
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_hvyhammer.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage,
				plank,
				strip,
				rivet);
		
		time = 10;
		KnowledgeListMF.tongsR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_tongs.construct(material.name), "smelt"+material.name, true, "hammer", -1, -1, (int)(time*material.craftTimeModifier), new Object[]
		{
			"I ",
			" I",
			
			'I', ingot,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_tongs.construct(material.name),
				salvage, salvage);
		
		time = 10;
		KnowledgeListMF.knifeR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_knife.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"I ",
			"PL",
			
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_knife.construct(material.name,haftMaterial.name),
				salvage, salvage,
				plank,
				strip);
		
		time = 12;
		KnowledgeListMF.shearsR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_shears.construct(material.name, haftMaterial.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			" I ",
			"PLI",
			" P ",
			
			'I', ingot,
			'P', plank,
			'L', Items.leather,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_shears.construct(material.name, haftMaterial.name),
				salvage, salvage,
				plank, plank,
				Items.leather);
		
		time = 20;
		KnowledgeListMF.sawsR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_saw.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"PIII",
			"LI  ",
			
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_saw.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage,
				plank,
				strip);
	}
	private static void addStandardWeapons(CustomMaterial material, ItemStack ingot,CustomMaterial haftMaterial, ItemStack plank) 
	{
		ItemStack salvage = material.getItem();
		Item strip = ComponentListMF.leather_strip;
		Item rivet = ComponentListMF.rivet;
		
		int time = 15;
		KnowledgeListMF.daggerR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_dagger.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"L  ",
			"PII",
			"L  ",
			
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_dagger.construct(material.name,haftMaterial.name),
				salvage, salvage,
				plank,
				strip, strip);
		Salvage.addSalvage(CustomToolListMF.dragonforged_dagger.construct(material.name,haftMaterial.name),
				salvage, salvage,
				plank,
				strip, strip);
		
		
		time = 25;
		KnowledgeListMF.swordR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_sword.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"LI  ",
			"PIII",
			"LI  ",
			
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_sword.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage, salvage,
				plank,
				strip, strip);
		Salvage.addSalvage(CustomToolListMF.dragonforged_sword.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage, salvage,
				plank,
				strip, strip);
		
		time = 20;
		KnowledgeListMF.waraxeR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_waraxe.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"LII",
			"PPI",
			"L I",
			
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_waraxe.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage,
				plank, plank,
				strip, strip);
		Salvage.addSalvage(CustomToolListMF.dragonforged_waraxe.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage,
				plank, plank,
				strip, strip);
		
		KnowledgeListMF.maceR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_mace.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"L II",
			"PPII",
			"L   ",
			
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_mace.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage,
				plank, plank,
				strip, strip);
		Salvage.addSalvage(CustomToolListMF.dragonforged_mace.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage,
				plank, plank,
				strip, strip);
		
		KnowledgeListMF.spearR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_spear.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			" LLI ",
			"PPPPI",
			" LLI ",
			
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_spear.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage,
				plank, plank, plank, plank,
				strip, strip, strip, strip);
		Salvage.addSalvage(CustomToolListMF.dragonforged_spear.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage,
				plank, plank, plank, plank,
				strip, strip, strip, strip);
		
		//HEAVY
		time = 30;
		KnowledgeListMF.katanaR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_katana.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hvyhammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"LR   I",
			"PIIII ",
			"LI    ",
			
			'R', rivet,
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_katana.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage, salvage, salvage,
				plank,
				strip, strip,
				rivet);
		Salvage.addSalvage(CustomToolListMF.dragonforged_katana.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage, salvage, salvage,
				plank,
				strip, strip,
				rivet);
		
		time = 40;
		KnowledgeListMF.gswordR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_greatsword.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hvyhammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"LIR   ",
			"PIIIII",
			"LIR   ",
			
			'R', rivet,
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_greatsword.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage, salvage, salvage, salvage,
				plank,
				strip, strip,
				rivet, rivet);
		Salvage.addSalvage(CustomToolListMF.dragonforged_greatsword.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage, salvage, salvage, salvage,
				plank,
				strip, strip,
				rivet, rivet);
		
		time = 30;
		KnowledgeListMF.battleaxeR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_battleaxe.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hvyhammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"LLIIR",
			"PPPIR",
			"LLIIR",
			
			'R', rivet,
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_battleaxe.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage, salvage,
				plank, plank, plank,
				strip, strip, strip, strip,
				rivet, rivet, rivet);
		Salvage.addSalvage(CustomToolListMF.dragonforged_battleaxe.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage, salvage,
				plank, plank, plank,
				strip, strip, strip, strip,
				rivet, rivet, rivet);
		
		KnowledgeListMF.whammerR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_warhammer.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hvyhammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"LL IIR",
			"PPPIIR",
			"LL  IR",
			
			'R', rivet,
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_warhammer.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage, salvage,
				plank, plank, plank,
				strip, strip, strip, strip,
				rivet, rivet, rivet);
		Salvage.addSalvage(CustomToolListMF.dragonforged_warhammer.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage, salvage,
				plank, plank, plank,
				strip, strip, strip, strip,
				rivet, rivet, rivet);
		
		KnowledgeListMF.halbeardR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_halbeard.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hvyhammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"LLRII",
			"PPPPI",
			"LLRI ",
			
			'R', rivet,
			'I', ingot,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_halbeard.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage,
				plank, plank, plank, plank,
				strip, strip, strip, strip,
				rivet, rivet);
		Salvage.addSalvage(CustomToolListMF.dragonforged_halbeard.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage,
				plank, plank, plank, plank,
				strip, strip, strip, strip,
				rivet, rivet);
		
		time = 25;
		KnowledgeListMF.bowR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_bow.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"ISSSI",
			" PLP ",
			
			'I', ingot,
			'S', Items.string,
			'P', plank,
			'L', strip,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_bow.construct(material.name,haftMaterial.name),
				salvage, salvage,
				plank, plank,
				strip,
				Items.string, Items.string, Items.string);
		
		time = 60;
		KnowledgeListMF.lanceR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.standard_lance.construct(material.name,haftMaterial.name), "smelt"+material.name, true, "hvyhammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"IR    ",
			"IIIIII",
			"IR    ",
			
			'R', rivet,
			'I', ingot,
		}));
		Salvage.addSalvage(CustomToolListMF.standard_lance.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage, salvage, salvage, salvage, salvage,
				rivet, rivet);
		Salvage.addSalvage(CustomToolListMF.dragonforged_lance.construct(material.name,haftMaterial.name),
				salvage, salvage, salvage, salvage, salvage, salvage, salvage, salvage,
				rivet, rivet);
	}
	/*
	private static void addDwarven(CustomMaterial material, ItemStack ingot, Object specialComponent) 
	{
		ItemStack salvage = material.getItem();
		Item plank = ComponentListMF.plankRefined;
		Item strip = ComponentListMF.leather_strip;
		Item rivet = ComponentListMF.rivet;
		
		int time = 25;
		
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.dwarven_sword.construct(material.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"LI  ",
			"OIII",
			"LI  ",
			
			'O', specialComponent,
			'I', ingot,
			'L', strip,
		});
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.dwarven_waraxe.construct(material.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"LII",
			"POI",
			"L I",
			
			'O', specialComponent,
			'I', ingot,
			'P', plank,
			'L', strip,
		});
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.dwarven_mace.construct(material.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"L II",
			"POII",
			"L   ",
			
			'O', specialComponent,
			'I', ingot,
			'P', plank,
			'L', strip,
		});
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.dwarven_dagger.construct(material.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"L  ",
			"OII",
			"L  ",
			
			'O', specialComponent,
			'I', ingot,
			'L', strip,
		});
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomToolListMF.dwarven_spear.construct(material.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			" L I ",
			"PPPOI",
			" L I ",
			
			'O', specialComponent,
			'I', ingot,
			'P', plank,
			'L', strip,
		});
	}
	*/
}
