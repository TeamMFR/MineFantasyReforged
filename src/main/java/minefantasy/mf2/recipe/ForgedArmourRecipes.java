package minefantasy.mf2.recipe;

import java.util.ArrayList;
import java.util.Iterator;

import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.crafting.Salvage;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.item.list.ArmourListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CustomArmourListMF;
import minefantasy.mf2.item.list.CustomToolListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ForgedArmourRecipes 
{
	private static final Skill artisanry = SkillList.artisanry;
	private static final Skill engineering = SkillList.engineering;
	private static final Skill construction = SkillList.construction;
	public static void init() 
	{
		ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
		Iterator iteratorMetal = metal.iterator();
		while(iteratorMetal.hasNext())
    	{
    		CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
    		
    		for(ItemStack ingot: OreDictionary.getOres("ingot"+customMat.name))
    		{
    			addMetalComponents(customMat, ingot);
    		}
    		addMetalComponents(customMat);
    		assembleChainmail(customMat);
    		assembleScalemail(customMat);
    		assembleSplintmail(customMat);
    		assembleFieldplate(customMat);
    	}
	}
	
	private static void assembleChainmail(CustomMaterial material)
	{
		Item helm = ArmourListMF.armourItem(ArmourListMF.leather, 2, 0);
		Item chest = ArmourListMF.armourItem(ArmourListMF.leather, 2, 1);
		Item legs = ArmourListMF.armourItem(ArmourListMF.leather, 2, 2);
		Item boots = ArmourListMF.armourItem(ArmourListMF.leather, 2, 3);
		
		ItemStack mail = ComponentListMF.chainmesh.createComm(material.name);
		Item rivet = ComponentListMF.rivet;
		
		int time = 20;
		KnowledgeListMF.mailHelmetR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_chain_helmet.construct(material.name), "craftArmourMedium", true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RMR",
			"MPM",
			"RMR",
			
			'R', rivet,
			'M', mail,
			'P', new ItemStack(helm, 1, 0)
		}));
		time = 30;
		KnowledgeListMF.mailChestR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_chain_chest.construct(material.name), "craftArmourMedium", true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RM MR",
			"RMPMR",
			"RM MR",
			
			'R', rivet,
			'M', mail,
			'P', new ItemStack(chest, 1, 0)
		}));
		time = 20;
		KnowledgeListMF.mailLegsR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_chain_legs.construct(material.name), "craftArmourMedium", true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RMPMR",
			"RM MR",
			
			'R', rivet,
			'M', mail,
			'P', new ItemStack(legs, 1, 0)
		}));
		time = 10;
		KnowledgeListMF.mailBootsR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_chain_boots.construct(material.name), "craftArmourMedium", true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"R R",
			"MPM",
			
			'R', rivet,
			'M', mail,
			'P', new ItemStack(boots, 1, 0)
		}));
		Salvage.addSalvage(CustomArmourListMF.standard_chain_helmet.construct(material.name),helm,
				mail, mail, mail, mail,//4 Mail
				rivet, rivet, rivet, rivet);//4 Rivet
		Salvage.addSalvage(CustomArmourListMF.standard_chain_chest.construct(material.name),chest,
				mail, mail, mail, mail, mail, mail,//6 Mail
				rivet, rivet, rivet, rivet, rivet, rivet);// 6 Rivet
		Salvage.addSalvage(CustomArmourListMF.standard_chain_legs.construct(material.name),legs,
				mail, mail, mail, mail,//4 Mail
				rivet, rivet, rivet, rivet);//4 Rivet
		Salvage.addSalvage(CustomArmourListMF.standard_chain_boots.construct(material.name),boots,
				mail, mail,//2 Mail
				rivet, rivet);//2 Rivet
	}
	
	private static void assembleScalemail(CustomMaterial material)
	{
		Item helm = ArmourListMF.armourItem(ArmourListMF.leather, 2, 0);
		Item chest = ArmourListMF.armourItem(ArmourListMF.leather, 2, 1);
		Item legs = ArmourListMF.armourItem(ArmourListMF.leather, 2, 2);
		Item boots = ArmourListMF.armourItem(ArmourListMF.leather, 2, 3);
		
		ItemStack mail = ComponentListMF.scalemesh.createComm(material.name);
		Item rivet = ComponentListMF.rivet;
		
		int time = 20;
		KnowledgeListMF.scaleHelmetR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_scale_helmet.construct(material.name), "craftArmourMedium", true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RMR",
			"MPM",
			"RMR",
			
			'R', rivet,
			'M', mail,
			'P', new ItemStack(helm, 1, 0)
		}));
		time = 30;
		KnowledgeListMF.scaleChestR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_scale_chest.construct(material.name), "craftArmourMedium", true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RM MR",
			"RMPMR",
			"RM MR",
			
			'R', rivet,
			'M', mail,
			'P', new ItemStack(chest, 1, 0)
		}));
		time = 20;
		KnowledgeListMF.scaleLegsR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_scale_legs.construct(material.name), "craftArmourMedium", true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RMPMR",
			"RM MR",
			
			'R', rivet,
			'M', mail,
			'P', new ItemStack(legs, 1, 0)
		}));
		time = 10;
		KnowledgeListMF.scaleBootsR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_scale_boots.construct(material.name), "craftArmourMedium", true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"R R",
			"MPM",
			
			'R', rivet,
			'M', mail,
			'P', new ItemStack(boots, 1, 0)
		}));
		Salvage.addSalvage(CustomArmourListMF.standard_scale_helmet.construct(material.name),helm,
				mail, mail, mail, mail,//4 Mail
				rivet, rivet, rivet, rivet);//4 Rivet
		Salvage.addSalvage(CustomArmourListMF.standard_scale_chest.construct(material.name),chest,
				mail, mail, mail, mail, mail, mail,//6 Mail
				rivet, rivet, rivet, rivet, rivet, rivet);// 6 Rivet
		Salvage.addSalvage(CustomArmourListMF.standard_scale_legs.construct(material.name),legs,
				mail, mail, mail, mail,//4 Mail
				rivet, rivet, rivet, rivet);//4 Rivet
		Salvage.addSalvage(CustomArmourListMF.standard_scale_boots.construct(material.name),boots,
				mail, mail,//2 Mail
				rivet, rivet);//2 Rivet
	}
	
	private static void assembleSplintmail(CustomMaterial material)
	{
		Item helm = ArmourListMF.armourItem(ArmourListMF.leather, 2, 0);
		Item chest = ArmourListMF.armourItem(ArmourListMF.leather, 2, 1);
		Item legs = ArmourListMF.armourItem(ArmourListMF.leather, 2, 2);
		Item boots = ArmourListMF.armourItem(ArmourListMF.leather, 2, 3);
		
		ItemStack mail = ComponentListMF.splintmesh.createComm(material.name);
		Item rivet = ComponentListMF.rivet;
		
		int time = 20;
		KnowledgeListMF.splintHelmetR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_splint_helmet.construct(material.name), "craftArmourMedium", true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RMR",
			"MPM",
			"RMR",
			
			'R', rivet,
			'M', mail,
			'P', new ItemStack(helm, 1, 0)
		}));
		time = 30;
		KnowledgeListMF.splintChestR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_splint_chest.construct(material.name), "craftArmourMedium", true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RM MR",
			"RMPMR",
			"RM MR",
			
			'R', rivet,
			'M', mail,
			'P', new ItemStack(chest, 1, 0)
		}));
		time = 20;
		KnowledgeListMF.splintLegsR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_splint_legs.construct(material.name), "craftArmourMedium", true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RMPMR",
			"RM MR",
			
			'R', rivet,
			'M', mail,
			'P', new ItemStack(legs, 1, 0)
		}));
		time = 10;
		KnowledgeListMF.splintBootsR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_splint_boots.construct(material.name), "craftArmourMedium", true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"R R",
			"MPM",
			
			'R', rivet,
			'M', mail,
			'P', new ItemStack(boots, 1, 0)
		}));
		Salvage.addSalvage(CustomArmourListMF.standard_splint_helmet.construct(material.name),helm,
				mail, mail, mail, mail,//4 Mail
				rivet, rivet, rivet, rivet);//4 Rivet
		Salvage.addSalvage(CustomArmourListMF.standard_splint_chest.construct(material.name),chest,
				mail, mail, mail, mail, mail, mail,//6 Mail
				rivet, rivet, rivet, rivet, rivet, rivet);// 6 Rivet
		Salvage.addSalvage(CustomArmourListMF.standard_splint_legs.construct(material.name),legs,
				mail, mail, mail, mail,//4 Mail
				rivet, rivet, rivet, rivet);//4 Rivet
		Salvage.addSalvage(CustomArmourListMF.standard_splint_boots.construct(material.name),boots,
				mail, mail,//2 Mail
				rivet, rivet);//2 Rivet
	}
	
	private static void assembleFieldplate(CustomMaterial material)
	{
		Item helm = ArmourListMF.armourItem(ArmourListMF.leather, 5, 0);
		Item chest = ArmourListMF.armourItem(ArmourListMF.leather, 5, 1);
		Item legs = ArmourListMF.armourItem(ArmourListMF.leather, 5, 2);
		Item boots = ArmourListMF.armourItem(ArmourListMF.leather, 5, 3);
		
		ItemStack mail = ComponentListMF.chainmesh.createComm(material.name);
		ItemStack plate = ComponentListMF.plate.createComm(material.name);
		Item rivet = ComponentListMF.rivet;
		
		int time = 40;
		KnowledgeListMF.plateHelmetR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_plate_helmet.construct(material.name), "craftArmourHeavy", true, "hvyhammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RMR",
			"PHP",
			"RMR",
			
			'M', mail,
			'R', rivet,
			'P', plate,
			'H', new ItemStack(helm, 1, 0),
		}));
		time = 60;
		KnowledgeListMF.plateChestR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_plate_chest.construct(material.name), "craftArmourHeavy", true, "hvyhammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RPMPR",
			"RMCMR",
			" RPR ",
			
			'M', mail,
			'R', rivet,
			'P', plate,
			'C', new ItemStack(chest, 1, 0),
		}));
		time = 40;
		KnowledgeListMF.plateLegsR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_plate_legs.construct(material.name), "craftArmourHeavy", true, "hvyhammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RPLPR",
			"RM MR",
			
			'M', mail,
			'R', rivet,
			'P', plate,
			'L', new ItemStack(legs, 1, 0),
		}));
		time = 20;
		KnowledgeListMF.plateBootsR.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, CustomArmourListMF.standard_plate_boots.construct(material.name), "craftArmourHeavy", true, "hvyhammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			" P ",
			"RBR",
			" M ",
			
			'M', mail,
			'R', rivet,
			'P', plate,
			'B', new ItemStack(boots, 1, 0),
		}));
		
		
		Salvage.addSalvage(CustomArmourListMF.standard_plate_helmet.construct(material.name),helm,
				mail, mail, plate, plate,//2 Mail, 2 Plate
				rivet, rivet, rivet, rivet);//4 Rivet
		Salvage.addSalvage(CustomArmourListMF.standard_plate_chest.construct(material.name),chest,
				mail, mail, mail, plate, plate, plate,//3 Mail, 3 Plate
				rivet, rivet, rivet, rivet, rivet, rivet);// 6 Rivet
		Salvage.addSalvage(CustomArmourListMF.standard_plate_legs.construct(material.name),legs,
				mail, mail, plate, plate,//2 Mail, 2 Plate
				rivet, rivet, rivet, rivet);//4 Rivet
		Salvage.addSalvage(CustomArmourListMF.standard_plate_boots.construct(material.name),boots,
				mail, plate,//1 Mail, 1 Plate
				rivet, rivet);//2 Rivet
	}
	
	private static void addMetalComponents(CustomMaterial material)
	{
		ItemStack salvage = material.getItem();
		
		int time = 4;
		KnowledgeListMF.mailRecipes.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, ComponentListMF.chainmesh.createComm(material.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			" H ",
			"H H",
			" H ",
			
			'H', ComponentListMF.metalHunk.createComm(material.name)
		}));
		time = 6;
		KnowledgeListMF.scaleRecipes.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, ComponentListMF.scalemesh.createComm(material.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"HHH",
			" H ",
			
			'H', ComponentListMF.metalHunk.createComm(material.name)
		}));
		time = 8;
		KnowledgeListMF.splintRecipes.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, ComponentListMF.splintmesh.createComm(material.name), "smelt"+material.name, true, "hammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RHR",
			" H ",
			" H ",
			" H ",
			
			'H', ComponentListMF.metalHunk.createComm(material.name),
			'R', ComponentListMF.rivet,
		}));
		
		Salvage.addSalvage(ComponentListMF.chainmesh.createComm(material.name), salvage);
		Salvage.addSalvage(ComponentListMF.scalemesh.createComm(material.name), salvage);
		Salvage.addSalvage(ComponentListMF.splintmesh.createComm(material.name), salvage, ComponentListMF.rivet, ComponentListMF.rivet);
	}
	private static void addMetalComponents(CustomMaterial material, ItemStack ingot)
	{
		ItemStack salvage = material.getItem();
		
		int time = 10;
		KnowledgeListMF.plateRecipes.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, ComponentListMF.plate.createComm(material.name), "smelt"+material.name, true, "hvyhammer", material.crafterTier, material.crafterAnvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"II",
			'I', ingot
		}));
		
		Salvage.addSalvage(ComponentListMF.chainmesh.createComm(material.name), salvage, salvage);
	}
}