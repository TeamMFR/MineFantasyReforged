package minefantasy.mf2.recipe;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.crafting.refine.BloomRecipe;
import minefantasy.mf2.api.refine.Alloy;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.config.ConfigHardcore;
import minefantasy.mf2.item.ItemComponentMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

public class SmeltingRecipesMF {

	public static void init() 
	{
		KnowledgeListMF.reStone =
		MineFantasyAPI.addRatioAlloy(1, new ItemStack(BlockListMF.reinforced_stone, 4), 1, new Object[]
		{
			Blocks.stone,Blocks.stone,Blocks.stone,Blocks.stone, ComponentListMF.fireclay, Items.iron_ingot, ComponentListMF.obsidian_rock
		});
		if(ConfigHardcore.HCCreduceIngots)
		{
			if(MineFantasyAPI.removeSmelting(Blocks.iron_ore) && MineFantasyAPI.removeSmelting(Blocks.gold_ore))
			{
				MFLogUtil.logDebug("Removed Ore Smelting (Hardcore Ingots");
			}
			else
			{
				MFLogUtil.logWarn("Failed to remove Ore smelting!");
			}
			BloomRecipe.addRecipe(new ItemStack(Blocks.iron_ore), new ItemStack(Items.iron_ingot));
			BloomRecipe.addRecipe(new ItemStack(Blocks.gold_ore), new ItemStack(Items.gold_ingot));
		}
		
		refineRawOre(ComponentListMF.oreCopper, ComponentListMF.ingots[0]);
		refineRawOre(ComponentListMF.oreTin, ComponentListMF.ingots[1]);
		refineRawOre(ComponentListMF.oreIron, Items.iron_ingot);
		refineRawOre(ComponentListMF.oreSilver, ComponentListMF.ingots[8]);
		refineRawOre(ComponentListMF.oreGold, Items.gold_ingot);
		
		MineFantasyAPI.addRatioAlloy(3, new ItemStack(ComponentListMF.ingots[2], 3), 0, new Object[]
		{
			ComponentListMF.oreCopper, ComponentListMF.oreCopper, ComponentListMF.oreTin
		});
		
		refineRawOre(BlockListMF.oreCopper, ComponentListMF.ingots[0], 0.4F);
		refineRawOre(BlockListMF.oreTin, ComponentListMF.ingots[1], 0.5F);
		refineRawOre(BlockListMF.oreSilver, ComponentListMF.ingots[8], 0.9F);
		
		GameRegistry.addSmelting(BlockListMF.oreBorax, new ItemStack(ComponentListMF.flux_strong, 4), 0.25F);
		GameRegistry.addSmelting(BlockListMF.oreTungsten, new ItemStack(ComponentListMF.oreTungsten, 1), 0.25F);
		GameRegistry.addSmelting(BlockListMF.oreKaolinite, new ItemStack(ComponentListMF.kaolinite), 0.25F);
		GameRegistry.addSmelting(BlockListMF.oreNitre, new ItemStack(ComponentListMF.nitre, 4), 0.25F);
		GameRegistry.addSmelting(BlockListMF.oreSulfur, new ItemStack(ComponentListMF.sulfur, 4), 0.25F);
		GameRegistry.addSmelting(BlockListMF.oreClay, new ItemStack(Items.clay_ball, 4), 0.25F);
		
		GameRegistry.addSmelting(ComponentListMF.fireclay_brick, new ItemStack(ComponentListMF.strong_brick), 0.1F);
		GameRegistry.addSmelting(ComponentListMF.bomb_casing_uncooked, new ItemStack(ComponentListMF.bomb_casing), 0F);
		GameRegistry.addSmelting(ComponentListMF.mine_casing_uncooked, new ItemStack(ComponentListMF.mine_casing), 0F);
		
		//ALLOY
		KnowledgeListMF.bronze = 
		MineFantasyAPI.addRatioAlloy(3, new ItemStack(ComponentListMF.ingots[2], 3), new Object[]{
			ComponentListMF.ingots[0], ComponentListMF.ingots[0], ComponentListMF.ingots[1]
		});
		
		ArrayList<ItemStack> pigs = OreDictionary.getOres("ingotPigIron");
		ArrayList<ItemStack> steels = OreDictionary.getOres("ingotSteel");
		ArrayList<ItemStack> bronzes = OreDictionary.getOres("ingotBronze");
		ArrayList<ItemStack> silvers = OreDictionary.getOres("ingotSilver");
		ArrayList<ItemStack> blacks = OreDictionary.getOres("ingotBlackSteel");
		
		for(ItemStack steel: steels)
		{
			KnowledgeListMF.obsidalloy =
			MineFantasyAPI.addRatioAlloy(1, new ItemStack(ComponentListMF.ingots[19]), 1, new Object[]{
				steel, 
				ComponentListMF.obsidian_rock, ComponentListMF.obsidian_rock, ComponentListMF.obsidian_rock, ComponentListMF.obsidian_rock, 
				ComponentListMF.flux_strong
			});
			
			for(ItemStack bronze: bronzes)
			{
				Alloy[] alloy =
				MineFantasyAPI.addRatioAlloy(1, new ItemStack(ComponentListMF.ingots[6], 2), 1, new Object[]{
					steel, steel, bronze, bronze, ComponentListMF.obsidian_rock
				});
				if(KnowledgeListMF.black == null)
				{
					KnowledgeListMF.black = alloy;
				}
			}
		}
		KnowledgeListMF.wolframiteR =
		MineFantasyAPI.addRatioAlloy(1, new ItemStack(ComponentListMF.ingots[18]), 1, new Object[]{
			Items.coal, Items.coal, Items.coal, Items.coal, ComponentListMF.oreTungsten, ComponentListMF.flux_strong, ComponentListMF.flux_strong, ComponentListMF.flux_strong, ComponentListMF.flux_strong
		});
		MineFantasyAPI.addRatioAlloy(1, new ItemStack(ComponentListMF.ingots[18]), 1, new Object[]{
			Items.coal, Items.coal, Items.coal, Items.coal, BlockListMF.oreTungsten, ComponentListMF.flux_strong, ComponentListMF.flux_strong, ComponentListMF.flux_strong, ComponentListMF.flux_strong
		});
				
		if(!ConfigHardcore.HCCreduceIngots)
		{
			for(ItemStack ingot: pigs)
			{
				Alloy[] alloy = 
				MineFantasyAPI.addRatioAlloy(9, new ItemStack(ComponentListMF.ingots[4]), 1, new Object[]
				{
					ingot
				});
				if(KnowledgeListMF.steel == null)
				{
					KnowledgeListMF.steel = alloy;
				}
			}
		}
		for(ItemStack steel: blacks)
		{
			Alloy[] alloy = 
			MineFantasyAPI.addRatioAlloy(1, new ItemStack(ComponentListMF.ingots[9]), 1, new Object[]
			{
				steel, Items.gold_ingot, Items.redstone, ComponentListMF.flux_strong, Items.blaze_powder
			});
			if(KnowledgeListMF.red == null)
			{
				KnowledgeListMF.red = alloy;
			}
			for(ItemStack silver: silvers)
			{
				alloy = 
				MineFantasyAPI.addRatioAlloy(1, new ItemStack(ComponentListMF.ingots[11]), 1, new Object[]
				{
					steel, silver, new ItemStack(Items.dye, 1, 4), ComponentListMF.flux_strong, Items.blaze_powder
				});
				if(KnowledgeListMF.blue == null)
				{
					KnowledgeListMF.blue = alloy;
				}
			}
		}
		KnowledgeListMF.adamantium = 
		MineFantasyAPI.addRatioAlloy(2, new ItemStack(ComponentListMF.ingots[13], 2), 1, new Object[]
		{
			ComponentListMF.ingots[10], BlockListMF.oreMythic, Items.gold_ingot, Items.gold_ingot
		});
		for(ItemStack silver: silvers)
		{
			Alloy[] alloy = 
			MineFantasyAPI.addRatioAlloy(2, new ItemStack(ComponentListMF.ingots[14], 2), 1, new Object[]
			{
				ComponentListMF.ingots[12], BlockListMF.oreMythic, silver, silver
			});
			if(KnowledgeListMF.mithril == null)
			{
				KnowledgeListMF.mithril = alloy;
			}
		}
		KnowledgeListMF.ignotumite = 
		MineFantasyAPI.addRatioAlloy(2, new ItemStack(ComponentListMF.ingots[15], 1), 1, new Object[]
		{
			ComponentListMF.ingots[13], ComponentListMF.ingots[13], Items.gold_ingot, Items.emerald
		});
		KnowledgeListMF.mithium = 
		MineFantasyAPI.addRatioAlloy(2, new ItemStack(ComponentListMF.ingots[16], 1), 1, new Object[]
		{
			ComponentListMF.ingots[14], ComponentListMF.ingots[14], ComponentListMF.diamond_shards, Items.ghast_tear
		});
		KnowledgeListMF.enderforge = 
		MineFantasyAPI.addRatioAlloy(2, new ItemStack(ComponentListMF.ingots[17], 1), 1, new Object[]
		{
			ComponentListMF.ingots[13], ComponentListMF.ingots[14], Items.ender_pearl, Items.ender_pearl
		});
		MineFantasyAPI.addBlastFurnaceRecipe(ComponentListMF.ingots[6], new ItemStack(ComponentListMF.ingots[7]));
		MineFantasyAPI.addBlastFurnaceRecipe(ComponentListMF.ingots[9], new ItemStack(ComponentListMF.ingots[10]));
		MineFantasyAPI.addBlastFurnaceRecipe(ComponentListMF.ingots[11], new ItemStack(ComponentListMF.ingots[12]));
	}

	private static void refineRawOre(Item ore, Item ingot)
	{
		refineRawOre(ore, ingot, 0F);
	}
	private static void refineRawOre(Block ore, Item ingot)
	{
		refineRawOre(ore, ingot, 0F);
	}
	private static void refineRawOre(Block ore, Item ingot, float xp)
	{
		refineRawOre(Item.getItemFromBlock(ore), ingot, xp);
	}
	private static void refineRawOre(Item ore, Item ingot, float xp)
	{
		if(ConfigHardcore.HCCreduceIngots)
		{
			BloomRecipe.addRecipe(ore, new ItemStack(ingot));
		}
		else
		{
			GameRegistry.addSmelting(ore, new ItemStack(ingot), xp);
		}
	}

}
