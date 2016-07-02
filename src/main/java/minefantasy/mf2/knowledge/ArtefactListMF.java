package minefantasy.mf2.knowledge;

import minefantasy.mf2.api.knowledge.InformationBase;
import minefantasy.mf2.api.knowledge.ResearchArtefacts;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.item.ItemArtefact;
import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ArtefactListMF 
{
	public static void init()
	{
		((ItemArtefact)ComponentListMF.artefacts).registerAll();
		addArtisanry();
		addConstruction();
		addProvisioning();
		addEngineering();
	}

	private static void addEngineering() 
	{
	}

	private static void addProvisioning() 
	{
	}

	private static void addConstruction() 
	{
	}

	private static void addArtisanry() 
	{
		for(ItemStack copper: OreDictionary.getOres("ingotCopper"))
		{
			for(ItemStack tin: OreDictionary.getOres("ingotTin"))
			{
				add(KnowledgeListMF.smeltBronze, copper, tin);
			}
		}
		add(KnowledgeListMF.coalflux, Items.coal, ComponentListMF.flux);
		add(KnowledgeListMF.smeltIron, Blocks.iron_ore);
		add(KnowledgeListMF.crucible2, ComponentListMF.fireclay);
		add(KnowledgeListMF.blastfurn, Items.iron_ingot, Blocks.iron_ore, Blocks.furnace, BlockListMF.bloomery, BlockListMF.limestone, ComponentListMF.kaolinite);
		add(KnowledgeListMF.bigfurn, Items.iron_ingot, Blocks.furnace, BlockListMF.bloomery, ComponentListMF.kaolinite, Items.coal);
		for(ItemStack pig: OreDictionary.getOres("ingotRefinedIron"))
		{
			add(KnowledgeListMF.smeltSteel, pig);
		}
		for(ItemStack steel: OreDictionary.getOres("ingotSteel"))
		{
			add(KnowledgeListMF.encrusted, steel, Items.diamond);
			add(KnowledgeListMF.obsidian, steel, Blocks.obsidian);
			for(ItemStack bronze: OreDictionary.getOres("ingotBronze"))
			{
				add(KnowledgeListMF.smeltBlackSteel, Blocks.obsidian, bronze, steel);
			}
		}
		for(ItemStack black: OreDictionary.getOres("ingotBlackSteel"))
		{
			for(ItemStack silver: OreDictionary.getOres("ingotSilver"))
			{
				add(KnowledgeListMF.smeltBlueSteel, Items.blaze_powder, silver, black, new ItemStack(Items.dye, 1, 4), ComponentListMF.flux_strong);
			}
			add(KnowledgeListMF.smeltRedSteel, Items.blaze_powder, Items.gold_ingot, Items.redstone, black, ComponentListMF.flux_strong);
		}
		
		
		for(ItemStack silver: OreDictionary.getOres("ingotSilver"))
		{
			add(KnowledgeListMF.smeltMithril, BlockListMF.oreMythic, silver);
		}
		add(KnowledgeListMF.smeltAdamant, BlockListMF.oreMythic, Items.gold_ingot);
		
		for(ItemStack mithril: OreDictionary.getOres("ingotMithril"))
		{
			add(KnowledgeListMF.smeltMithium, mithril, Items.ghast_tear, Items.diamond);
			for(ItemStack adamant: OreDictionary.getOres("ingotAdamantium"))
			{
				add(KnowledgeListMF.smeltIgnotumite, adamant, Items.emerald, Items.blaze_powder);
				add(KnowledgeListMF.smeltEnderforge, adamant, mithril, Items.ender_pearl);
			}
		}
		add(KnowledgeListMF.craftArmourMedium, Items.leather);
		add(KnowledgeListMF.craftArmourHeavy, Items.leather, Blocks.wool, Items.feather);
		add(KnowledgeListMF.smeltDragonforge, ComponentListMF.dragon_heart);
		
		add(KnowledgeListMF.arrowsBodkin, Items.feather);
		add(KnowledgeListMF.arrowsBroad, Items.feather, Items.flint);
		
		add(KnowledgeListMF.repair_basic, Items.leather, Items.flint, ComponentListMF.nail);
		add(KnowledgeListMF.repair_advanced, BlockListMF.repair_basic, Items.slime_ball, Items.string);
		add(KnowledgeListMF.repair_ornate, Items.diamond, Items.gold_ingot, BlockListMF.repair_advanced);
	}
	
	private static void add(InformationBase info, Object... artifacts) 
	{
		for(Object artifact : artifacts)
		{
			ResearchArtefacts.addArtefact(artifact, info);
		}
	}
}
