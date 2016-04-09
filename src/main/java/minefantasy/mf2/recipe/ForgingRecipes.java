package minefantasy.mf2.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import cpw.mods.fml.common.registry.GameRegistry;

import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.crafting.Salvage;
import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.crafting.exotic.SpecialForging;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.config.ConfigCrafting;
import minefantasy.mf2.item.ItemComponentMF;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.ArmourListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ForgingRecipes
{
	private static final Skill artisanry = SkillList.artisanry;
	private static final Skill engineering = SkillList.engineering;
	private static final Skill construction = SkillList.construction;
	public static void init()
	{
		ForgedToolRecipes.init();
		ForgedArmourRecipes.init();
		
		//MISC
		BaseMaterialMF material;
		int time;
		time = 1;
		material = BaseMaterialMF.encrusted;
		
		KnowledgeListMF.obsidianHunkR = 
		MineFantasyAPI.addAnvilRecipe(null, new ItemStack(ComponentListMF.obsidian_rock, 4), "", false, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"D",
			'D', Blocks.obsidian,
		});
		KnowledgeListMF.diamondR = 
		MineFantasyAPI.addAnvilRecipe(null, new ItemStack(ComponentListMF.diamond_shards), "", false, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"D",
			'D', Items.diamond,
		});
		
		time = 3;
		for(ItemStack ingot: OreDictionary.getOres("ingotSteel"))
		{
			IAnvilRecipe recipe = 
			MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.ingots[5]), "smeltEncrusted", true, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
			{
				"D",
				"I",
				'D', ComponentListMF.diamond_shards,
				'I', ingot,
			});
			if(ingot.getItem() instanceof ItemComponentMF)
			{
				Salvage.addSalvage(ComponentListMF.ingots[5], ComponentListMF.ingots[4], ComponentListMF.diamond_shards);
			}
			if(KnowledgeListMF.encrustedR == null)
			{
				KnowledgeListMF.encrustedR = recipe;
			}
		}
		
		material = BaseMaterialMF.pigiron;
		for(ItemStack ore: OreDictionary.getOres("ingotPigIron"))
		{
			KnowledgeListMF.steelR = 
			MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.ingots[4], 1), "smeltSteel", true, 1, 1, 5, new Object[]
			{
				"H",
				'H', ore
			});
		}
		KnowledgeListMF.fluxR =
		MineFantasyAPI.addAnvilRecipe(null, new ItemStack(ComponentListMF.flux, 4), "", false, -1, -1, 2, new Object[]
		{
			"H",
			'H',BlockListMF.limestone
		});
		
		//PLATE ARMOUR
		for(int id = 1; id < ArmourListMF.mats.length; id ++)
		{
			material = BaseMaterialMF.getMaterial(ArmourListMF.mats[id]);
			//STUDDED
			material = BaseMaterialMF.iron;
			//HELMET
			time = 10;
			KnowledgeListMF.studHelmetR = 
			MineFantasyAPI.addAnvilRecipe(artisanry, ArmourListMF.armour(ArmourListMF.leather, 3, 0), "craftArmourLight", false, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
			{
				" I ",
				"IAI",
				" I ",
				'I', ComponentListMF.rivet,
				'A', ArmourListMF.armourItem(ArmourListMF.leather, 2, 0),
			});
			//CHEST
			time = 20;
			KnowledgeListMF.studChestR = 
			MineFantasyAPI.addAnvilRecipe(artisanry, ArmourListMF.armour(ArmourListMF.leather, 3, 1), "craftArmourLight", false, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
			{
				" I ",
				"IAI",
				" I ",
				'I', ComponentListMF.rivet,
				'A', ArmourListMF.armourItem(ArmourListMF.leather, 2, 1),
			});
			//LEGS
			time = 15;
			KnowledgeListMF.studLegsR = 
			MineFantasyAPI.addAnvilRecipe(artisanry, ArmourListMF.armour(ArmourListMF.leather, 3, 2), "craftArmourLight", false, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
			{
				" I ",
				"IAI",
				" I ",
				'I', ComponentListMF.rivet,
				'A', ArmourListMF.armourItem(ArmourListMF.leather, 2, 2),
			});
			//BOOTS
			time = 6;
			KnowledgeListMF.studBootsR = 
			MineFantasyAPI.addAnvilRecipe(artisanry, ArmourListMF.armour(ArmourListMF.leather, 3, 3), "craftArmourLight", false, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
			{
				" I ",
				"IAI",
				" I ",
				'I', ComponentListMF.rivet,
				'A', ArmourListMF.armourItem(ArmourListMF.leather, 2, 3),
			});
			
			Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 3, 0), ArmourListMF.armour(ArmourListMF.leather, 2, 0), new ItemStack(ComponentListMF.rivet, 4));
			Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 3, 1), ArmourListMF.armour(ArmourListMF.leather, 2, 1), new ItemStack(ComponentListMF.rivet, 4));
			Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 3, 2), ArmourListMF.armour(ArmourListMF.leather, 2, 2), new ItemStack(ComponentListMF.rivet, 4));
			Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 3, 3), ArmourListMF.armour(ArmourListMF.leather, 2, 3), new ItemStack(ComponentListMF.rivet, 4));
		}
		
		time = 2;
		material = BaseMaterialMF.iron;
		if(ConfigCrafting.allowIronResmelt)
		{
			MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.iron_prep), "blastfurn", false, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]{
				"IFI",
				'I', Items.iron_ingot,
				'F', ComponentListMF.flux,
			});
			MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.iron_prep, 2), "blastfurn", false, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]{
				"IFI",
				'I', Items.iron_ingot,
				'F', ComponentListMF.flux_strong,
			});
		}
		KnowledgeListMF.coalPrepR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.coal_prep), "coke", false, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]{
			"RCR",
			"CFC",
			"RCR",
			'R', Items.redstone,
			'C', new ItemStack(Items.coal, 1, 1),
			'F', ComponentListMF.flux_strong,
		});
		GameRegistry.addSmelting(ComponentListMF.coal_prep, new ItemStack(ComponentListMF.coke), 1F);
		
		KnowledgeListMF.ironPrepR = 
		MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.iron_prep), "blastfurn", false, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]{
			"IFI",
			'I', Blocks.iron_ore,
			'F', ComponentListMF.flux,
		});
		KnowledgeListMF.ironPrepR2 = 
		MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.iron_prep, 2), "blastfurn", false, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]{
			"IFI",
			'I', Blocks.iron_ore,
			'F', ComponentListMF.flux_strong,
		});
		
		MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.iron_prep), "blastfurn", false, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]{
			"IFI",
			'I', ComponentListMF.oreIron,
			'F', ComponentListMF.flux,
		});
		MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.iron_prep, 2), "blastfurn", false, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]{
			"IFI",
			'I', ComponentListMF.oreIron,
			'F', ComponentListMF.flux_strong,
		});
		ItemStack plate = ComponentListMF.plate.createComm("iron");
		time=15;
		KnowledgeListMF.blastChamR = 
		MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMF.blast_chamber), "blastfurn", false, "hvyHammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]{
			"RP PR",
			"RP PR",
			"RP PR",
			"RP PR",
			'R', ComponentListMF.rivet,
			'P', plate,
		});
		
		time=30;
		KnowledgeListMF.blastHeatR = 
		MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMF.blast_heater), "blastfurn", false, "hvyHammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]{
			"RP PR",
			"RP PR",
			"RP PR",
			"RPFPR",
			'R', ComponentListMF.rivet,
			'P', plate,
			'F', Blocks.furnace,
		});
		
		Salvage.addSalvage(BlockListMF.blast_heater, new ItemStack(ComponentListMF.rivet, 8),
				plate, plate, plate, plate, plate, plate, plate, plate,
				Blocks.furnace);
		Salvage.addSalvage(BlockListMF.blast_chamber, new ItemStack(ComponentListMF.rivet, 8),
				plate, plate, plate, plate, plate, plate, plate, plate);
		
		for(ItemStack silver : OreDictionary.getOres("ingotSilver"))
		{
			addOrnate(silver);
		}
		addNails("Copper", 1);
		addNails("Tin", 1);
		addNails("Bronze", 2);
		addNails("Iron", 4);
		addNails("Steel", 8);
		
		IAnvilRecipe[] anvilRecs = new IAnvilRecipe[BlockListMF.anvils.length];
		for(int id = 0; id < BlockListMF.anvils.length; id ++)
		{
			time = 20;
			material = BaseMaterialMF.getMaterial(BlockListMF.anvils[id]);
			
			for(ItemStack ingot: OreDictionary.getOres("ingot"+material.name))
			{
				IAnvilRecipe recipe = 
				MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMF.anvil[id]), "smelt"+material.name, false, "hammer", material.hammerTier-1, material.anvilTier-1, (int)(time*material.craftTimeModifier), new Object[]
				{
					" II",
					"III",
					" I ",
					'I', ingot,
				});
				anvilRecs[id] = recipe;
				
				if(ingot.getItem() instanceof ItemComponentMF || ingot.getItem() == Items.iron_ingot)
				{
					Salvage.addSalvage(BlockListMF.anvil[id], new ItemStack(ingot.getItem(), 6));
				}
			}
		}
		recipeMap.put("anvilCrafting", anvilRecs);
		
		ItemStack bronzeHunk = ComponentListMF.metalHunk.createComm("bronze");
		ItemStack ironHunk = ComponentListMF.metalHunk.createComm("iron");
		
		time = 4;
		material = BaseMaterialMF.bronze;
		KnowledgeListMF.framedStoneR =
		MineFantasyAPI.addAnvilRecipe(construction, new ItemStack(BlockListMF.reinforced_stone_framed), "decorated_stone", false, "hammer", material.hammerTier-1, material.anvilTier-1, (int)(time*material.craftTimeModifier), new Object[]
		{
			" N ",
			"NSN",
			" N ",
			'N', bronzeHunk,
			'S', BlockListMF.reinforced_stone,
		});
		Salvage.addSalvage(BlockListMF.reinforced_stone_framed, 
				bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk,
				BlockListMF.reinforced_stone);
		time = 4;
		material = BaseMaterialMF.iron;
		KnowledgeListMF.iframedStoneR =
		MineFantasyAPI.addAnvilRecipe(construction, new ItemStack(BlockListMF.reinforced_stone_framediron), "decorated_stone", false, "hammer", material.hammerTier-1, material.anvilTier-1, (int)(time*material.craftTimeModifier), new Object[]
		{
			" N ",
			"NSN",
			" N ",
			'N', ironHunk,
			'S', BlockListMF.reinforced_stone,
		});
		Salvage.addSalvage(BlockListMF.reinforced_stone_framediron, 
				ironHunk, ironHunk,ironHunk, ironHunk
				, BlockListMF.reinforced_stone);
		
		for(int id = 0; id < BlockListMF.specialMetalBlocks.length; id ++)
		{
			time = 2;
			material = BaseMaterialMF.getMaterial(BlockListMF.specialMetalBlocks[id]);
			ItemStack hunk = ComponentListMF.metalHunk.createComm(material.name);
			if(hunk != null)
			{
				KnowledgeListMF.barsR.add(
				MineFantasyAPI.addAnvilRecipe(construction, new ItemStack(BlockListMF.bars[id]), "smelt"+material.name, false, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
				{
					"I I",
					"I I",
					'I', hunk,
				}));
				if(hunk.getItem() instanceof ItemComponentMF)
				{
					Salvage.addSalvage(BlockListMF.bars[id],hunk, hunk, hunk, hunk);
				}
			}
		}
		KnowledgeListMF.talismanRecipe.add(
		MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.talisman_lesser), "", true, "hammer", -1, -1, 20, new Object[]
		{
			"LGL",
			"GIG",
			" G ",
			'L', new ItemStack(Items.dye, 1, 4),
			'I', Items.iron_ingot,
			'G', Items.gold_ingot,
		}));
		for(ItemStack silver: OreDictionary.getOres("ingotSilver"))
		{
			KnowledgeListMF.talismanRecipe.add(
			MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.talisman_lesser), "", true, "hammer", -1, -1, 20, new Object[]
			{
				"LSL",
				"SIS",
				" S ",
				'L', new ItemStack(Items.dye, 1, 4),
				'I', Items.iron_ingot,
				'S', silver,
			}));
		}
		KnowledgeListMF.greatTalismanRecipe =
		MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.talisman_greater), "", true, "hammer", 1, 1, 50, new Object[]
		{
			"GSG",
			"DTD",
			"GDG",
			'G', Items.gold_ingot,
			'D', Items.diamond,
			'T', ComponentListMF.talisman_lesser,
			'S', Items.nether_star,
		});
		
		addEngineering();
		addConstruction();
		
		time = 10;
		material = BaseMaterialMF.iron;
		KnowledgeListMF.caketinRecipe =
		MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(FoodListMF.cake_tin), "", true, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			" R ",
			"I I",
			" I ",
			'I', Items.iron_ingot,
			'R', ComponentListMF.rivet,
		});
		Salvage.addSalvage(FoodListMF.cake_tin, 
				Items.iron_ingot, Items.iron_ingot, Items.iron_ingot
				, ComponentListMF.rivet);
		
		KnowledgeListMF.coalfluxR = 
		MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.coal_flux, 2), "coalflux", false, material.hammerTier, material.anvilTier, 2, new Object[]
		{
			"F",
			"C",
			'C', Items.coal,
			'F', ComponentListMF.flux_pot,
		});
	}

	private static void addOrnate(ItemStack silver)
	{
	}
	
	private static Item getStrips(BaseMaterialMF material)
	{
		return ComponentListMF.leather_strip;
	}
	private static Item getLeather(BaseMaterialMF material)
	{
		return Items.leather;
	}
	
	private static void addEngineering() 
	{	
		ItemStack bronzeHunk = ComponentListMF.metalHunk.createComm("bronze");
		ItemStack ironHunk = ComponentListMF.metalHunk.createComm("iron");
		ItemStack steelHunk = ComponentListMF.metalHunk.createComm("steel");
		ItemStack tungstenHunk = ComponentListMF.metalHunk.createComm("tungsten");
		ItemStack blacksteelHunk = ComponentListMF.metalHunk.createComm("blacksteel");
		
		BaseMaterialMF material = BaseMaterialMF.steel;
		int time = 10;
		time = 25;
		KnowledgeListMF.spannerRecipe = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ToolListMF.spanner), "etools", true, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"  S ",
			"  SS",
			"LS  ",
			"IL  ",
			'I', Items.iron_ingot,
			'S', steelHunk,
			'L', getStrips(material),
		});
		time = 15;
		KnowledgeListMF.eatoolsR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ToolListMF.engin_anvil_tools), "etools", true, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"SSLL",
			"LLSS",
			'S', steelHunk,
			'L', getStrips(material),
		});
		time = 5;
		KnowledgeListMF.iframeR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.iron_frame), "ecomponents", true, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RRR",
			"ISI",
			"STS",
			"ISI",
			'T', ToolListMF.engin_anvil_tools,
			'R', ComponentListMF.rivet,
			'I', ironHunk,
			'S', steelHunk,
		});
		time = 8;
		KnowledgeListMF.istrutR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.iron_strut), "ecomponents", true, "hvyhammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RTR",
			"SIS",
			"SIS",
			'T', ToolListMF.engin_anvil_tools,
			'R', ComponentListMF.rivet,
			'I', Items.iron_ingot,
			'S', steelHunk,
		});
		time = 8;
		KnowledgeListMF.stubeR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.steel_tube), "ecomponents", true, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"TR R",
			"SSSS",
			'T', ToolListMF.engin_anvil_tools,
			'R', ComponentListMF.rivet,
			'S', steelHunk,
		});
		time = 4;
		KnowledgeListMF.boltR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.bolt, 2), "etools", true, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			" T ",
			"SIS",
			" S ",
			" S ",
			'T', ToolListMF.engin_anvil_tools,
			'I', Items.iron_ingot,
			'S', steelHunk,
		});
		for(ItemStack steel: OreDictionary.getOres("ingotSteel"))
		{
			time = 35;
			KnowledgeListMF.climbPickbR = 
			MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ToolListMF.climbing_pick_basic), "climber", true, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
			{
				"L SR",
				"IISR",
				"L T ",
				'R', ComponentListMF.rivet,
				'T', ToolListMF.engin_anvil_tools,
				'I', Items.iron_ingot,
				'S', steel,
				'L', getStrips(material),
			});
		}
		time = 5;
		KnowledgeListMF.bgearR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.bronze_gears), "ecomponents", true, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			" T ",
			" B ",
			"BIB",
			" B ",
			'T', ToolListMF.engin_anvil_tools,
			'I', Items.iron_ingot,
			'B', bronzeHunk,
		});
		time = 8;
		material = BaseMaterialMF.tungsten;
		KnowledgeListMF.tgearR =
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.tungsten_gears), "tungsten", true, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			" T ",
			" W ",
			"WGW",
			" W ",
			'T', ToolListMF.engin_anvil_tools,
			'W', tungstenHunk,
			'G', ComponentListMF.bronze_gears,
		});
		for(ItemStack copper: OreDictionary.getOres("ingotCopper"))
		{
			for(ItemStack steel: OreDictionary.getOres("ingotSteel"))
			{
			time = 15;
			material = BaseMaterialMF.compositeAlloy;
			KnowledgeListMF.compPlateR =
			MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.ingotCompositeAlloy), "cogArmour", true, "hvyhammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
			{
				" T ",
				" S ",
				"RWR",
				" C ",
				
				'R', ComponentListMF.rivet,
				'T', ToolListMF.engin_anvil_tools,
				'C', copper,
				'W', tungstenHunk,
				'S', steel,
			});
			}
		}
		material = BaseMaterialMF.iron;
		
		time = 5;
		KnowledgeListMF.bombCaseIronR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.bomb_casing_iron, 2), "bombIron", true, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			" T ",
			" I ",
			"IRI",
			" I ",
			'T', ToolListMF.engin_anvil_tools,
			'I', ironHunk,
			'R', ComponentListMF.rivet,
		});
		KnowledgeListMF.mineCaseIronR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.mine_casing_iron, 2), "bombIron", true, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"  T  ",
			"  P  ",
			" IRI ",
			"IR RI",
			'T', ToolListMF.engin_anvil_tools,
			'P', Blocks.heavy_weighted_pressure_plate,
			'I', ironHunk,
			'R', ComponentListMF.rivet,
		});
		
		time = 5;
		KnowledgeListMF.bombarrowR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.bomb_casing_arrow), "bombarrow", true, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"   IR",
			"FPITI",
			"   IR",
			'T', ToolListMF.engin_anvil_tools,
			'I', ironHunk,
			'R', Items.redstone,
			'P', ((ItemComponentMF)ComponentListMF.plank).construct("RefinedWood"),
			'F', ComponentListMF.fletching,
		});
		KnowledgeListMF.bombBoltR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.bomb_casing_bolt), "bombarrow", true, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"  IR",
			"FITI",
			"  IR",
			'T', ToolListMF.engin_anvil_tools,
			'I', ironHunk,
			'R', Items.redstone,
			'F', ComponentListMF.fletching,
		});
		
		material = BaseMaterialMF.blacksteel;
		
		time = 5;
		KnowledgeListMF.bombCaseObsidianR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.bomb_casing_obsidian, 2), "bombObsidian", true, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			" T ",
			"RIR",
			"IOI",
			"RIR",
			'T', ToolListMF.engin_anvil_tools,
			'O', Blocks.obsidian,
			'I', blacksteelHunk,
			'R', ComponentListMF.rivet,
		});
		KnowledgeListMF.mineCaseObsidianR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.mine_casing_obsidian, 2), "mineObsidian", true, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"  T  ",
			"  P  ",
			" IRI ",
			"IRORI",
			'T', ToolListMF.engin_anvil_tools,
			'O', Blocks.obsidian,
			'P', Blocks.heavy_weighted_pressure_plate,
			'I', blacksteelHunk,
			'R', ComponentListMF.rivet,
		});
		time = 15;
		material = BaseMaterialMF.steel;
		KnowledgeListMF.crossBayonetR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.cross_bayonet), "crossBayonet", true, material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"R R I",
			"PIII ",
			'P', ((ItemComponentMF)ComponentListMF.plank).construct("RefinedWood"),
			'I', ironHunk,
			'R', ComponentListMF.rivet,
		});
				
		
		time = 80;
		material = BaseMaterialMF.blacksteel;
		KnowledgeListMF.blkspannerR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ToolListMF.spanner_blk), "etools", true, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"  SO",
			" TSS",
			"LS  ",
			"IL  ",
			'O', Blocks.obsidian,
			'I', Items.gold_ingot,
			'S', blacksteelHunk,
			'L', getStrips(material),
			'T', ToolListMF.engin_anvil_tools,
		});
		Salvage.addSalvage(ToolListMF.engin_anvil_tools,
				steelHunk, steelHunk, steelHunk, steelHunk,
				new ItemStack(ComponentListMF.leather_strip, 4));
		
		Salvage.addSalvage(ComponentListMF.iron_frame, 
				steelHunk, steelHunk, steelHunk, steelHunk,
				ironHunk, ironHunk, ironHunk, ironHunk
				,new ItemStack(ComponentListMF.rivet, 3));
		
		Salvage.addSalvage(ComponentListMF.iron_strut, 
				steelHunk, steelHunk, steelHunk, steelHunk,
				new ItemStack(Items.iron_ingot, 2), new ItemStack(ComponentListMF.rivet, 2));
		
		Salvage.addSalvage(ComponentListMF.steel_tube, 
				steelHunk, steelHunk, steelHunk, steelHunk,
				new ItemStack(ComponentListMF.rivet, 2));
		
		Salvage.addSalvage(ToolListMF.climbing_pick_basic, new ItemStack(Items.iron_ingot, 2), new ItemStack(ComponentListMF.ingots[4], 2), new ItemStack(ComponentListMF.rivet, 2), new ItemStack(ComponentListMF.leather_strip, 2));
		
		Salvage.addSalvage(ComponentListMF.bronze_gears,
				bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk
				,Items.iron_ingot);
		
		Salvage.addSalvage(ComponentListMF.tungsten_gears, 
				tungstenHunk, tungstenHunk, tungstenHunk, tungstenHunk,
				ComponentListMF.bronze_gears);
		
		Salvage.addSalvage(ComponentListMF.ingotCompositeAlloy, ComponentListMF.ingots[0], ComponentListMF.ingots[4], tungstenHunk, new ItemStack(ComponentListMF.rivet, 2));
		
		Salvage.addSalvage(ComponentListMF.bomb_casing_iron, ironHunk, ironHunk);
		Salvage.addSalvage(ComponentListMF.mine_casing_iron, ironHunk, ironHunk, ComponentListMF.rivet, Items.iron_ingot);
		Salvage.addSalvage(ComponentListMF.bomb_casing_arrow, 
				ironHunk, ironHunk, ironHunk, ironHunk, 
				new ItemStack(Items.redstone, 2), ((ItemComponentMF)ComponentListMF.plank).construct("RefinedWood"), ComponentListMF.fletching);
		
		Salvage.addSalvage(ComponentListMF.bomb_casing_bolt, 
				ironHunk, ironHunk, ironHunk, ironHunk, 
				new ItemStack(Items.redstone, 2), ComponentListMF.fletching);
		
		Salvage.addSalvage(ComponentListMF.bomb_casing_obsidian, blacksteelHunk, blacksteelHunk, new ItemStack(ComponentListMF.rivet, 2));
		Salvage.addSalvage(ComponentListMF.mine_casing_obsidian, blacksteelHunk, blacksteelHunk, Items.iron_ingot, ComponentListMF.rivet);
		
		Salvage.addSalvage(ComponentListMF.cross_bayonet, 
				ironHunk, ironHunk, ironHunk, ironHunk, 
				((ItemComponentMF)ComponentListMF.plank).construct("RefinedWood"), new ItemStack(ComponentListMF.rivet, 2));
		
		Salvage.addSalvage(ToolListMF.spanner, 
				steelHunk, steelHunk, steelHunk, steelHunk, 
				Items.iron_ingot, new ItemStack(ComponentListMF.leather_strip, 2));
		
		Salvage.addSalvage(ToolListMF.spanner_blk, 
				blacksteelHunk, blacksteelHunk, blacksteelHunk, blacksteelHunk,
				Items.gold_ingot, Blocks.obsidian, new ItemStack(ComponentListMF.leather_strip, 2));
	
		material = BaseMaterialMF.cogworks;
		time = 25;
		KnowledgeListMF.cogFrameHelmetR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ArmourListMF.cogwork_frame_helmet), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RSFSR",
			"RA AR",
			'R', ComponentListMF.rivet,
			'S', ComponentListMF.iron_strut,
			'A', ComponentListMF.cogwork_shaft,
			'F', ComponentListMF.iron_frame,
		});
		time = 40;
		KnowledgeListMF.cogFrameChestR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ArmourListMF.cogwork_frame_chest), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RS SR",
			"RAAAR",
			"RSASR",
			" RFR ",
			
			'R', ComponentListMF.rivet,
			'S', ComponentListMF.iron_strut,
			'A', ComponentListMF.cogwork_shaft,
			'F', ComponentListMF.iron_frame,
		});
		time = 30;
		KnowledgeListMF.cogFrameLegsR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ArmourListMF.cogwork_frame_legs), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RAFAR",
			"RS SR",
			"RS SR",
			
			'R', ComponentListMF.rivet,
			'S', ComponentListMF.iron_strut,
			'A', ComponentListMF.cogwork_shaft,
			'F', ComponentListMF.iron_frame,
		});
		time = 20;
		KnowledgeListMF.cogFrameBootsR = 
		MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ArmourListMF.cogwork_frame_boots), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
		{
			"RA AR",
			"RS SR",
			
			'R', ComponentListMF.rivet,
			'S', ComponentListMF.iron_strut,
			'A', ComponentListMF.cogwork_shaft,
		});
		Salvage.addSalvage(ArmourListMF.cogwork_frame_helmet, new ItemStack(ComponentListMF.rivet, 4), new ItemStack(ComponentListMF.iron_strut, 2), new ItemStack(ComponentListMF.cogwork_shaft, 2), ComponentListMF.iron_frame);
		Salvage.addSalvage(ArmourListMF.cogwork_frame_chest, new ItemStack(ComponentListMF.rivet, 8), new ItemStack(ComponentListMF.iron_strut, 4), new ItemStack(ComponentListMF.cogwork_shaft, 4), ComponentListMF.iron_frame);
		Salvage.addSalvage(ArmourListMF.cogwork_frame_legs, new ItemStack(ComponentListMF.rivet, 6), new ItemStack(ComponentListMF.iron_strut, 4), new ItemStack(ComponentListMF.cogwork_shaft, 2), ComponentListMF.iron_frame);
		Salvage.addSalvage(ArmourListMF.cogwork_frame_boots, new ItemStack(ComponentListMF.rivet, 4), new ItemStack(ComponentListMF.iron_strut, 2), new ItemStack(ComponentListMF.cogwork_shaft, 2));
		
		ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
		Iterator iteratorMetal = metal.iterator();
		while(iteratorMetal.hasNext())
    	{
    		CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
    		
    		for(ItemStack ingot: OreDictionary.getOres("ingot"+customMat.name))
    		{
    			//TOTAL: 32Ingot, 20Rivet
    			IAnvilRecipe helm = 
    			MineFantasyAPI.addAnvilRecipe(engineering, ArmourListMF.cogwork_armour_helmet.construct(customMat.name), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
				{
    				"RRR",
					"III",
					"IFI",
					"III",
					
					'I', ingot,
					'R', ComponentListMF.rivet,
					'F', new ItemStack(ArmourListMF.cogwork_frame_helmet, 1, 0),
				});
    			IAnvilRecipe chest =
    			MineFantasyAPI.addAnvilRecipe(engineering, ArmourListMF.cogwork_armour_chest.construct(customMat.name), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
				{
    				"RRR",
					"III",
					"IFI",
					"III",
					
					'I', ingot,
					'R', ComponentListMF.rivet,
					'F', new ItemStack(ArmourListMF.cogwork_frame_chest, 1, 0),
				});
    			IAnvilRecipe legs = 
    			MineFantasyAPI.addAnvilRecipe(engineering, ArmourListMF.cogwork_armour_legs.construct(customMat.name), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
				{
    				"RRR",
					"III",
					"IFI",
					"III",
					
					'I', ingot,
					'R', ComponentListMF.rivet,
					'F', new ItemStack(ArmourListMF.cogwork_frame_legs, 1, 0),
				});
    			IAnvilRecipe boots = 
    			MineFantasyAPI.addAnvilRecipe(engineering, ArmourListMF.cogwork_armour_boots.construct(customMat.name), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
				{
    				"RRR",
					"III",
					"IFI",
					"III",
					
					'I', ingot,
					'R', ComponentListMF.rivet,
					'F', new ItemStack(ArmourListMF.cogwork_frame_boots, 1, 0),
				});
    			
    			if(ingot.getItem() == ComponentListMF.ingots[4])
    			{
    				KnowledgeListMF.cogArmourHelmetR = helm;
    				KnowledgeListMF.cogArmourChestR = chest;
    				KnowledgeListMF.cogArmourLegsR = legs;
    				KnowledgeListMF.cogArmourBootsR = boots;
    			}
    		}
    	}
	}
	private static void addConstruction() 
	{
		BaseMaterialMF material = BaseMaterialMF.tin;
		int time = 10;
		for(ItemStack tin: OreDictionary.getOres("ingotTin"))
		{
			KnowledgeListMF.brushRecipe = 
			MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ToolListMF.paint_brush), "paint_brush", true, "hammer", material.hammerTier, material.anvilTier, (int)(time*material.craftTimeModifier), new Object[]
			{
				"W",
				"I",
				"P",
				
				'W', Blocks.wool,
				'I', tin,
				'P', ((ItemComponentMF)ComponentListMF.plank).construct("RefinedWood"),
			});
		}
		
		Salvage.addSalvage(ToolListMF.paint_brush, Blocks.wool, ComponentListMF.ingots[1], ((ItemComponentMF)ComponentListMF.plank).construct("RefinedWood"));
	}
	
	private static void addNails(String name, int count) 
	{
	}
	
	
	public static final HashMap<String, IAnvilRecipe[]>recipeMap = new HashMap<String, IAnvilRecipe[]>();
}
