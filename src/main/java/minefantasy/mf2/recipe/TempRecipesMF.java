package minefantasy.mf2.recipe;

import java.util.ArrayList;
import java.util.Iterator;

import minefantasy.mf2.api.crafting.Salvage;
import minefantasy.mf2.api.crafting.tanning.TanningRecipe;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.item.ItemComponentMF;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class TempRecipesMF
{
	public static void init()
	{
		ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
		Iterator iteratorWood = wood.iterator();
		while(iteratorWood.hasNext())
    	{
    		CustomMaterial customMat = (CustomMaterial) iteratorWood.next();	
    		assembleWoodVariations(customMat);
    	}
		
		GameRegistry.addRecipe(new ItemStack(Items.bucket, 1), new Object[]
		{
			"I I",
			" I ",
			'I', ComponentListMF.ingots[1],
		});
		//RESOURCES
		
		ArrayList<ItemStack> steels = OreDictionary.getOres("ingotSteel");
		ArrayList<ItemStack> bronzes = OreDictionary.getOres("ingotBronze");
		ArrayList<ItemStack> silvers = OreDictionary.getOres("ingotSilver");
		ArrayList<ItemStack> blacks = OreDictionary.getOres("ingotBlackSteel");
		
		for(ItemStack steel: steels)
		{
			GameRegistry.addRecipe(new ItemStack(Blocks.rail, 64), new Object[]
			{
				"I I",
				"ISI",
				"I I",
				'I', steel,
				'S', Items.stick,
			});
		}
		
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.cobblestone), new Object[]
		{
			"C",
			'C', BlockListMF.cobble_brick
		});
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.cobblestone), new Object[]
		{
			"C",
			'C', BlockListMF.cobble_pavement
		});
		KnowledgeListMF.stoneBricksR.add(
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.cobble_brick, 4), new Object[]
		{
			"C C",
			"   ",
			"C C",
			'C', Blocks.cobblestone
		}));
		KnowledgeListMF.stoneBricksR.add(
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.cobble_pavement, 4), new Object[]
		{
			"CC",
			"CC",
			'C', Blocks.cobblestone
		}));
		
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.reinforced_stone), new Object[]
		{
			"C",
			'C', BlockListMF.reinforced_stone_bricks
		});
		KnowledgeListMF.stoneBricksR.add(
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.reinforced_stone_bricks, 4), new Object[]
		{
			"CC",
			"CC",
			'C', BlockListMF.reinforced_stone
		}));
		
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.dirt), new Object[]
		{
			"C",
			'C', BlockListMF.mud_brick
		});
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.dirt), new Object[]
		{
			"C",
			'C', BlockListMF.mud_pavement
		});
		KnowledgeListMF.stoneBricksR.add(
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.mud_brick, 4), new Object[]
		{
			"C C",
			"   ",
			"C C",
			'C', Blocks.dirt
		}));
		KnowledgeListMF.stoneBricksR.add(
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.mud_pavement, 4), new Object[]
		{
			"CC",
			"CC",
			'C', Blocks.dirt
		}));
		
		
		
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.framed_pane, 16), new Object[]
		{
			"GGG",
			"GGG",
			'G', BlockListMF.framed_glass
		});
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.window_pane, 16), new Object[]
		{
			"GGG",
			"GGG",
			'G', BlockListMF.window
		});
		addFood();
		
	}
	private static void assembleWoodVariations(CustomMaterial material) {
		
	}

	private static void addFood()
	{
		KnowledgeListMF.meatpieOut =
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.pie_meat), new Object[]
		{
			"F",
			'F', FoodListMF.pie_meat_cooked
		});
		KnowledgeListMF.shepardOut =
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.pie_shepards), new Object[]
		{
			"F",
			'F', FoodListMF.pie_shepard_cooked
		});
		KnowledgeListMF.appleOut =
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.pie_apple), new Object[]
		{
			"F",
			'F', FoodListMF.pie_apple_cooked
		});
		KnowledgeListMF.berryOut =
		GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.pie_berry), new Object[]
		{
			"F",
			'F', FoodListMF.pie_berry_cooked
		});
		KnowledgeListMF.pumpPieOut =
		GameRegistry.addShapedRecipe(new ItemStack(Items.pumpkin_pie), new Object[]
		{
			"F",
			'F', FoodListMF.pie_pumpkin_cooked
		});
	}
}
