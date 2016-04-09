package minefantasy.mf2.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.crafting.Salvage;
import minefantasy.mf2.api.crafting.tanning.TanningRecipe;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.block.basic.ConstructionBlockMF;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.config.ConfigHardcore;
import minefantasy.mf2.item.ItemComponentMF;
import minefantasy.mf2.item.armour.ItemArmourMF;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.ArmourListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CustomArmourListMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import minefantasy.mf2.material.BaseMaterialMF;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class BasicRecipesMF
{
	public static void init()
	{
		TempRecipesMF.init();//TODO remove temp recipes
		ForgingRecipes.init();
		CarpenterRecipes.init();
		SmeltingRecipesMF.init();
		SalvageRecipes.init();
		GameRegistry.addRecipe(new RecipeArmourDyeMF());
		
		ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
		Iterator iteratorWood = wood.iterator();
		
		KnowledgeListMF.stickRecipe =
		GameRegistry.addShapedRecipe(new ItemStack(Items.stick, 2), new Object[]
		{
			"S",
			'S', ComponentListMF.plank
		});
		
		GameRegistry.addShapelessRecipe(new ItemStack(BlockListMF.yew_planks, 4), new Object[]
		{
			BlockListMF.log_yew
		});
		GameRegistry.addShapelessRecipe(new ItemStack(BlockListMF.ironbark_planks, 4), new Object[]
		{
			BlockListMF.log_ironbark
		});
		GameRegistry.addShapelessRecipe(new ItemStack(BlockListMF.ebony_planks, 4), new Object[]
		{
			BlockListMF.log_ebony
		});
		
		while(iteratorWood.hasNext())
    	{
    		CustomMaterial customMat = (CustomMaterial) iteratorWood.next();	
    		assembleWoodVariations(customMat);
    	}
		
		KnowledgeListMF.plantOilR = 
		GameRegistry.addShapedRecipe(new ItemStack(ComponentListMF.plant_oil, 4), new Object[]
		{
			" B ",
			"BFB",
			" B ",
			'F', Items.wheat_seeds,
			'B', FoodListMF.jug_empty
		});
		KnowledgeListMF.waterJugR = 
		GameRegistry.addShapedRecipe(new ItemStack(FoodListMF.jug_water, 4), new Object[]
		{
			" B ",
			"BWB",
			" B ",
			'W', Items.water_bucket,
			'B', FoodListMF.jug_empty
		});
		KnowledgeListMF.sugarRecipe = 
		GameRegistry.addShapedRecipe(new ItemStack(FoodListMF.sugarpot), new Object[]
		{
			"S",
			"S",
			"B",
			'S', Items.sugar,
			'B', ComponentListMF.clay_pot,
		});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.sugar, 2), new Object[]
		{
			FoodListMF.sugarpot,
		});
		KnowledgeListMF.milkJugR = 
		GameRegistry.addShapedRecipe(new ItemStack(FoodListMF.jug_milk, 4), new Object[]
		{
			" B ",
			"BMB",
			" B ",
			'M', Items.milk_bucket,
			'B', FoodListMF.jug_empty
		});
		GameRegistry.addShapedRecipe(new ItemStack(Items.milk_bucket), new Object[]
		{
			" B ",
			"BMB",
			" B ",
			'M', Items.bucket,
			'B', FoodListMF.jug_milk
		});
		GameRegistry.addShapedRecipe(new ItemStack(Items.water_bucket), new Object[]
		{
			" B ",
			"BMB",
			" B ",
			'M', Items.bucket,
			'B', FoodListMF.jug_water
		});
		
		
		GameRegistry.addRecipe(new RecipeSyringe());
		//Just a way on making the overpowered gunpowder from black powder
		GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder), new Object[]
		{
			new ItemStack(ComponentListMF.blackpowder),
			new ItemStack(ComponentListMF.blackpowder),
			new ItemStack(ComponentListMF.nitre),
		});
		
		
		
		for(int id = 0; id < BlockListMF.metalBlocks.length; id ++)
		{
			BaseMaterialMF material = BaseMaterialMF.getMaterial(BlockListMF.metalBlocks[id]);
			
			for(ItemStack ingot: OreDictionary.getOres("ingot"+material.name))
			{
				GameRegistry.addRecipe(new ItemStack(BlockListMF.storage[id]), new Object[]
				{
					"III",
					"III",
					"III",
					'I', ingot
				});
				GameRegistry.addShapelessRecipe(new ItemStack(ingot.getItem(), 9), new Object[]
				{
					BlockListMF.storage[id]
				});
			}
		}
		
		MineFantasyAPI.addBlastFurnaceRecipe(ComponentListMF.iron_prep, new ItemStack(ComponentListMF.ingots[3]));
		
		KnowledgeListMF.fireclayR = 
		MineFantasyAPI.addBasicCarpenterRecipe(new ItemStack(ComponentListMF.fireclay, 4), new Object[]
		{
			" C ",
			"CDC",
			" C ",
			
			'D', ComponentListMF.kaolinite_dust,
			'C', Items.clay_ball
		});
		KnowledgeListMF.fireBrickR = 
		MineFantasyAPI.addBasicCarpenterRecipe(new ItemStack(ComponentListMF.fireclay_brick) , new Object[]
		{
			"C",
			
			'C', ComponentListMF.fireclay
		});
		KnowledgeListMF.fireBricksR = 
		MineFantasyAPI.addBasicCarpenterRecipe(new ItemStack(BlockListMF.firebricks) , new Object[]
		{
			"BB",
			"BB",
			
			'B', ComponentListMF.strong_brick
		});
		KnowledgeListMF.fireBrickStairR=
		MineFantasyAPI.addBasicCarpenterRecipe(new ItemStack(BlockListMF.firebrick_stair) , new Object[]
		{
			"B ",
			"BB",
			
			'B', ComponentListMF.strong_brick
		});
		BaseMaterialMF mat = BaseMaterialMF.iron;
		
		GameRegistry.addShapelessRecipe(new ItemStack(ComponentListMF.hideSmall), new Object[]{
			ComponentListMF.rawhideSmall, ComponentListMF.flux
		});
		GameRegistry.addShapelessRecipe(new ItemStack(ComponentListMF.hideMedium), new Object[]{
			ComponentListMF.rawhideMedium, ComponentListMF.flux
		});
		GameRegistry.addShapelessRecipe(new ItemStack(ComponentListMF.hideLarge), new Object[]{
			ComponentListMF.rawhideLarge, ComponentListMF.flux
		});
		
		TanningRecipe.addRecipe(ComponentListMF.hideSmall, mat.craftTimeModifier*2F, -1, new ItemStack(Items.leather));
		TanningRecipe.addRecipe(ComponentListMF.hideMedium, mat.craftTimeModifier*3F, -1, new ItemStack(Items.leather, 3));
		TanningRecipe.addRecipe(ComponentListMF.hideLarge, mat.craftTimeModifier*4F, -1, new ItemStack(Items.leather, 5));
		TanningRecipe.addRecipe(Items.leather, mat.craftTimeModifier*2F, -1, "shears", new ItemStack(ComponentListMF.leather_strip, 4));
		
		KnowledgeListMF.artBookR =
		MineFantasyAPI.addBasicCarpenterRecipe(new ItemStack(ToolListMF.skillbook_artisanry), new Object[]
		{
			"T",
			"D",
			"B",
			'T', ComponentListMF.talisman_lesser,
			'D', new ItemStack(Items.dye, 1, 1),
			'B', Items.book,
		});
		KnowledgeListMF.conBookR =
		MineFantasyAPI.addBasicCarpenterRecipe(new ItemStack(ToolListMF.skillbook_construction), new Object[]
		{
			"T",
			"D",
			"B",
			'T', ComponentListMF.talisman_lesser,
			'D', new ItemStack(Items.dye, 1, 14),
			'B', Items.book,
		});
		KnowledgeListMF.proBookR =
		MineFantasyAPI.addBasicCarpenterRecipe(new ItemStack(ToolListMF.skillbook_provisioning), new Object[]
		{
			"T",
			"D",
			"B",
			'T', ComponentListMF.talisman_lesser,
			'D', new ItemStack(Items.dye, 1, 2),
			'B', Items.book,
		});
		KnowledgeListMF.engBookR =
		MineFantasyAPI.addBasicCarpenterRecipe(new ItemStack(ToolListMF.skillbook_engineering), new Object[]
		{
			"T",
			"D",
			"B",
			'T', ComponentListMF.talisman_lesser,
			'D', new ItemStack(Items.dye, 1, 12),
			'B', Items.book,
		});
		KnowledgeListMF.comBookR =
		MineFantasyAPI.addBasicCarpenterRecipe(new ItemStack(ToolListMF.skillbook_combat), new Object[]
		{
			"T",
			"D",
			"B",
			'T', ComponentListMF.talisman_lesser,
			'D', new ItemStack(Items.dye, 1, 5),
			'B', Items.book,
		});
		
		
		KnowledgeListMF.artBook2R =
		MineFantasyAPI.addBasicCarpenterRecipe(new ItemStack(ToolListMF.skillbook_artisanry2), new Object[]
		{
			"T",
			"B",
			'T', ComponentListMF.talisman_greater,
			'B', ToolListMF.skillbook_artisanry,
		});
		KnowledgeListMF.conBook2R =
		MineFantasyAPI.addBasicCarpenterRecipe(new ItemStack(ToolListMF.skillbook_construction2), new Object[]
		{
			"T",
			"B",
			'T', ComponentListMF.talisman_greater,
			'B', ToolListMF.skillbook_construction,
		});
		KnowledgeListMF.proBook2R =
		MineFantasyAPI.addBasicCarpenterRecipe(new ItemStack(ToolListMF.skillbook_provisioning2), new Object[]
		{
			"T",
			"B",
			'T', ComponentListMF.talisman_greater,
			'B', ToolListMF.skillbook_provisioning,
		});
		KnowledgeListMF.engBook2R =
		MineFantasyAPI.addBasicCarpenterRecipe(new ItemStack(ToolListMF.skillbook_engineering2), new Object[]
		{
			"T",
			"B",
			'T', ComponentListMF.talisman_greater,
			'B', ToolListMF.skillbook_engineering,
		});
		KnowledgeListMF.comBook2R =
		MineFantasyAPI.addBasicCarpenterRecipe(new ItemStack(ToolListMF.skillbook_combat2), new Object[]
		{
			"T",
			"B",
			'T', ComponentListMF.talisman_greater,
			'B', ToolListMF.skillbook_combat,
		});
		GameRegistry.addShapedRecipe(((ItemComponentMF)ComponentListMF.plank).construct("ScrapWood"), new Object[]
		{
			"S",
			"S",
			'S', Items.stick,
		});
		
		Object rock = ConfigHardcore.HCCallowRocks ? ComponentListMF.sharp_rock : Blocks.cobblestone;
		KnowledgeListMF.dryrocksR =
		GameRegistry.addShapedRecipe(new ItemStack(ToolListMF.dryrocks), new Object[]{
			"R ",
			" R",
			'R', rock
		});
		
		((ConstructionBlockMF.StairsConstBlock)BlockListMF.mud_brick_stair).addRecipe();
		((ConstructionBlockMF.StairsConstBlock)BlockListMF.mud_pavement_stair).addRecipe();
		((ConstructionBlockMF.StairsConstBlock)BlockListMF.cobble_brick_stair).addRecipe();
		((ConstructionBlockMF.StairsConstBlock)BlockListMF.cobble_pavement_stair).addRecipe();
		((ConstructionBlockMF.StairsConstBlock)BlockListMF.reinforced_stone_brick_stair).addRecipe();
		((ConstructionBlockMF.StairsConstBlock)BlockListMF.reinforced_stone_stair).addRecipe();
		
		((ConstructionBlockMF.StairsConstBlock)BlockListMF.yew_stair).addRecipe();
		((ConstructionBlockMF.StairsConstBlock)BlockListMF.ironbark_stair).addRecipe();
		((ConstructionBlockMF.StairsConstBlock)BlockListMF.ebony_stair).addRecipe();
	}
	

	private static void assembleWoodVariations(CustomMaterial material)
	{
		// TODO 
		if(material.name != "RefinedWood")
		{
			ArrayList<ItemStack> list = OreDictionary.getOres("planks"+material.name);
			if(list.isEmpty())
			{
				for(ItemStack planks: OreDictionary.getOres("plankWood"))
				{
					if(planks.getItemDamage() == OreDictionary.WILDCARD_VALUE)
					{
						ItemStack item = planks.copy();
						for(int i = 0; i < 16; i++)
						{
							item.setItemDamage(i);
							tryAddWoodPlanks(item, material);
							CarpenterRecipes.tryAddSawPlanks(item, material);
						}
					}
					else
					{
						tryAddWoodPlanks(planks, material);
						CarpenterRecipes.tryAddSawPlanks(planks, material);
					}
				}
			}
			else
			for(ItemStack block : list)
			{
				KnowledgeListMF.plankRecipe.add(
				GameRegistry.addShapedRecipe(((ItemComponentMF)ComponentListMF.plank).construct(material.name, 4), new Object[]{
					"P",
					"P",
					'P', block
				}));
				CarpenterRecipes.addSawPlanks(block, material);
			}
		}
	}
	
	private static void tryAddWoodPlanks(ItemStack planks, CustomMaterial material)
	{
		String sub = material.name.substring(0, material.name.length()-4).toLowerCase();
		
		if(planks.getUnlocalizedName().toLowerCase().contains(sub))
		{
			KnowledgeListMF.plankRecipe.add(
			GameRegistry.addShapedRecipe(((ItemComponentMF)ComponentListMF.plank).construct(material.name, 4), new Object[]{
				"P",
				"P",
				'P', planks.copy()
			}));
		}
	}
}
