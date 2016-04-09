package minefantasy.mf2.item.list;

import java.util.ArrayList;
import java.util.Iterator;

import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.heating.Heatable;
import minefantasy.mf2.api.knowledge.InformationBase;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.mining.RandomDigs;
import minefantasy.mf2.api.mining.RandomOre;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.item.AdvancedFuelHandlerMF;
import minefantasy.mf2.item.FuelHandlerMF;
import minefantasy.mf2.item.ItemComponentMF;
import minefantasy.mf2.item.ItemFilledMould;
import minefantasy.mf2.item.ItemHide;
import minefantasy.mf2.item.ItemMFBowl;
import minefantasy.mf2.item.ItemRawOreMF;
import minefantasy.mf2.item.custom.*;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.gadget.ItemBombComponent;
import minefantasy.mf2.item.gadget.ItemCrossbowPart;
import minefantasy.mf2.item.heatable.ItemHeated;
import minefantasy.mf2.material.BaseMaterialMF;
import minefantasy.mf2.material.WoodMaterial;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
/**
 * @author Anonymous Productions
 */
public class ComponentListMF 
{
	public static final String[] ingotMats = new String[]
	{
		"copper",
		"tin",
		"bronze",
		"pigiron",
		"steel",
		"encrusted",
		"blacksteelweak",
		"blacksteel",
		"silver",
		"redsteelweak",
		"redsteel",
		"bluesteelweak",
		"bluesteel",
		"adamantium",
		"mithril",
		"ignotumite",
		"mithium",
		"ender",
		"tungsten",
		"obsidian",
	};
	

	
	public static Item clay_pot = new ItemMFBowl("clay_pot");
	public static Item clay_pot_uncooked = new ItemComponentMF("clay_pot_uncooked", 0);
	public static Item ingot_mould = new ItemComponentMF("ingot_mould");
	public static Item ingot_mould_uncooked = new ItemComponentMF("ingot_mould_uncooked", 0);
	public static Item pie_tray_uncooked = new ItemComponentMF("pie_tray_uncooked", 0);
	
	public static ItemComponentMF[] ingots = new ItemComponentMF[ingotMats.length];
	
	
	public static Item plank = new ItemComponentMF("plank").setCustom("yes");
	public static Item vine = new ItemComponentMF("vine", -1);
	public static Item sharp_rock = new ItemComponentMF("sharp_rock", -1);
	
	public static Item flux = new ItemComponentMF("flux", 0);
	public static Item flux_strong = new ItemComponentMF("flux_strong", 0);
	
	public static Item coalDust = new ItemComponentMF("coalDust", 0).setContainerItem(clay_pot);
	public static Item nitre = new ItemComponentMF("nitre", 0);
	public static Item sulfur = new ItemComponentMF("sulfur", 0);
	public static Item iron_prep = new ItemComponentMF("iron_prep", 0);
	public static Item blackpowder = new ItemBombComponent("blackpowder", 0, "powder", 0).setContainerItem(clay_pot);
	public static Item blackpowder_advanced = new ItemBombComponent("blackpowder_advanced", 1, "powder", 1).setContainerItem(clay_pot);
	public static Item fletching = new ItemComponentMF("fletching", 0);
	public static Item shrapnel = new ItemBombComponent("shrapnel", 0, "filling", 1).setContainerItem(ComponentListMF.clay_pot);
	public static Item magma_cream_refined = new ItemBombComponent("magma_cream_refined", 1, "filling", 2).setContainerItem(clay_pot);
	public static Item bomb_fuse = new ItemBombComponent("bomb_fuse", 0, "fuse", 0);
	public static Item bomb_fuse_long = new ItemBombComponent("bomb_fuse_long", 0, "fuse", 1);
	public static Item bomb_casing_uncooked = new ItemComponentMF("bomb_casing_uncooked", 0);
	public static Item bomb_casing = new ItemBombComponent("bomb_casing", 0, "bombcase", 0);
	public static Item mine_casing_uncooked = new ItemComponentMF("mine_casing_uncooked", 0);
	public static Item mine_casing = new ItemBombComponent("mine_casing", 0, "minecase", 0);
	public static Item bomb_casing_iron = new ItemBombComponent("bomb_casing_iron", 0, "bombcase", 1);
	public static Item mine_casing_iron = new ItemBombComponent("mine_casing_iron", 0, "minecase", 1);
	public static Item bomb_casing_obsidian = new ItemBombComponent("bomb_casing_obsidian", 1, "bombcase", 2);
	public static Item mine_casing_obsidian = new ItemBombComponent("mine_casing_obsidian", 1, "minecase", 2);
	public static Item bomb_casing_crystal = new ItemBombComponent("bomb_casing_crystal", 1, "bombcase", 3);
	public static Item mine_casing_crystal = new ItemBombComponent("mine_casing_crystal", 1, "minecase", 3);
	public static Item bomb_casing_arrow = new ItemBombComponent("bomb_casing_arrow", 1, "arrow", 0);
	public static Item bomb_casing_bolt = new ItemBombComponent("bomb_casing_bolt", 1, "bolt", 0);
	
	public static Item coke = new ItemComponentMF("coke", 1);
	public static Item diamond_shards = new ItemComponentMF("diamond_shards", 0);
	
	public static Item clay_brick = new ItemComponentMF("clay_brick", 0);
	public static Item kaolinite = new ItemComponentMF("kaolinite", 0);
	public static Item kaolinite_dust = new ItemComponentMF("kaolinite_dust", 0).setContainerItem(clay_pot);
	public static Item fireclay = new ItemComponentMF("fireclay", 0);
	public static Item fireclay_brick = new ItemComponentMF("fireclay_brick", 0);
	public static Item strong_brick = new ItemComponentMF("strong_brick", 0);
	
	public static Item hideSmall = new ItemComponentMF("hideSmall", 0);
	public static Item hideMedium = new ItemComponentMF("hideMedium", 0);
	public static Item hideLarge = new ItemComponentMF("hideLarge", 0);
	public static Item rawhideSmall = new ItemHide("rawhideSmall", hideSmall, 1.0F);
	public static Item rawhideMedium = new ItemHide("rawhideMedium", hideMedium, 1.5F);
	public static Item rawhideLarge = new ItemHide("rawhideLarge", hideLarge, 3.0F);
	
	public static Item dragon_heart = new ItemComponentMF("dragon_heart", 1);
	
	public static Item leather_strip = new ItemComponentMF("leather_strip", 0);
	public static Item nail = new ItemComponentMF("nail", 0);
	public static Item rivet = new ItemComponentMF("rivet", 0);
	public static Item thread = new ItemComponentMF("thread", 0);
	public static Item obsidian_rock = new ItemComponentMF("obsidian_rock", 0);
	
	public static Item oreCopper = new ItemRawOreMF("oreCopper", -1);
	public static Item oreTin = new ItemRawOreMF("oreTin", -1);
	public static Item oreIron = new ItemRawOreMF("oreIron", 0);
	public static Item oreSilver = new ItemRawOreMF("oreSilver", 0);
	public static Item oreGold = new ItemRawOreMF("oreGold", 0);
	public static Item oreTungsten = new ItemRawOreMF("oreTungsten", 1);
	
	public static Item hotItem = new ItemHeated();
	
	public static Item plant_oil = new ItemComponentMF("plant_oil", 0).setContainerItem(FoodListMF.jug_empty);
	
	public static Item talisman_lesser= new ItemComponentMF("talisman_lesser", 1);
	public static Item talisman_greater= new ItemComponentMF("talisman_greater", 3);
	
	public static Item bolt = new ItemComponentMF("bolt", 0);
	public static Item iron_frame = new ItemComponentMF("iron_frame", 0);
	public static Item iron_strut = new ItemComponentMF("iron_strut", 0);
	public static Item bronze_gears = new ItemComponentMF("bronze_gears", 0);
	public static Item tungsten_gears = new ItemComponentMF("tungsten_gears", 1);
	public static Item steel_tube = new ItemComponentMF("steel_tube", 0);
	public static Item cogwork_shaft = new ItemComponentMF("cogwork_shaft", 1);
	public static Item ingotCompositeAlloy = new ItemComponentMF("ingotCompositeAlloy", 1);
	public static Item coal_prep = new ItemComponentMF("coal_prep", 0);
	
	public static Item ingot_mould_filled = new ItemFilledMould();
	
	public static Item crossbow_stock_wood = new ItemCrossbowPart("cross_stock_wood",   "stock").addSpeed(1.0F).addRecoil(0.5F);
	public static Item crossbow_stock_iron = new ItemCrossbowPart("cross_stock_iron",   "stock").addSpeed(1.0F).addRecoil(-0.5F).addDurability(150);
	public static Item crossbow_handle_wood = new ItemCrossbowPart("cross_handle_wood", "stock").addSpeed(0.5F).addRecoil(1.5F).addSpread(1.0F);
	
	public static Item cross_arms_basic = new ItemCrossbowPart("cross_arms_basic", 		 "mechanism").addPower(1.00F).addSpeed(0.50F).addRecoil(1.00F).addSpread(1.00F);
	public static Item cross_arms_light = new ItemCrossbowPart("cross_arms_light", 		 "mechanism").addPower(0.85F).addSpeed(0.25F).addRecoil(0.50F).addSpread(0.50F);
	public static Item cross_arms_heavy = new ItemCrossbowPart("cross_arms_heavy", 		 "mechanism").addPower(1.15F).addSpeed(1.00F).addRecoil(1.50F).addSpread(2.00F);
	public static Item cross_arms_advanced = new ItemCrossbowPart("cross_arms_advanced", "mechanism").addPower(1.15F).addSpeed(1.00F).addRecoil(2.50F).addSpread(0.25F).addDurability(150);
	
	public static Item cross_bayonet = new ItemCrossbowPart("cross_bayonet", "muzzle").addBash(4.0F).addRecoil(-0.5F).addSpeed(0.5F);
	public static Item cross_ammo = new ItemCrossbowPart("cross_ammo", "mod").addCapacity(5).addSpread(2.00F);
	public static Item cross_scope = new ItemCrossbowPart("cross_scope", "mod").setScope(0.75F);
	
	//public static ItemHaft hilt_custom = new ItemHaft("hilt");
	//public static ItemHaft longhilt_custom = new ItemHaft("longhilt");
	//public static ItemHaft haft_custom = new ItemHaft("haft");
	//public static ItemHaft longhaft_custom = new ItemHaft("longhaft");
	//public static ItemHaft spearhaft_custom = new ItemHaft("spearhaft");
	//public static ItemCustomComponent plankCustom = new ItemCustomComponent("plank", 1F);
	//public static ItemCustomComponent plateHeavy = new ItemCustomComponent("plateheavy", 3F);
	public static ItemCustomComponent chainmesh = new ItemCustomComponent("chainmesh", 1F);
	public static ItemCustomComponent scalemesh = new ItemCustomComponent("scalemesh", 1F);
	public static ItemCustomComponent splintmesh = new ItemCustomComponent("splintmesh", 1F);
	public static ItemCustomComponent plate = new ItemCustomComponent("plate", 2F);
	//public static ItemCustomComponent scrapMetal = new ItemCustomComponent("scrap", 1F);
	public static ItemCustomComponent metalHunk = new ItemCustomComponent("hunk", 0.25F);
	public static ItemCustomComponent arrowhead = new ItemCustomComponent("arrowhead", 1/4F);
	public static ItemCustomComponent bodkinhead = new ItemCustomComponent("bodkinhead", 1/4F);
	public static ItemCustomComponent broadhead = new ItemCustomComponent("broadhead", 1/4F);
	
	public static Item flux_pot = new ItemComponentMF("flux_pot", 0).setContainerItem(clay_pot);
	public static Item coal_flux = new ItemComponentMF("coal_flux", 0);
	
	//public static Item lime_rock = new ItemComponentMF("lime_rock", 0);
	//public static Item borax_rock = new ItemComponentMF("borax_rock", 0);
	//public static Item sulfur_rock = new ItemComponentMF("sulfur_rock", 0);
	//public static Item nitre_rock = new ItemComponentMF("nitre_rock", 0);
	
	public static void load() 
	{
		WoodMaterial.init();
		Items.potionitem.setContainerItem(Items.glass_bottle);
		GameRegistry.registerFuelHandler(new FuelHandlerMF());
		MineFantasyAPI.registerFuelHandler(new AdvancedFuelHandlerMF());
		Items.iron_ingot.setTextureName("minefantasy2:component/ingotWroughtIron");
		Blocks.iron_block.setBlockTextureName("minefantasy2:metal/iron_block");
		Blocks.iron_bars.setBlockTextureName("minefantasy2:metal/iron_bars");
		for(int a = 0; a < ingotMats.length; a ++)
		{
			BaseMaterialMF mat = BaseMaterialMF.getMaterial(ingotMats[a]);
			String name = mat.name;
			int rarity = mat.rarity;
			
			ingots[a] = new ItemComponentMF("ingot"+name, rarity);
			OreDictionary.registerOre("ingot"+name, ingots[a]);
			
			if(name.equalsIgnoreCase("PigIron"))
			{
				OreDictionary.registerOre("ingotRefinedIron", ingots[a]);
			}
		}
		
		/*for(int a = 0; a < woodMats.length; a ++)
		{
			CustomMaterial mat = CustomMaterial.getMaterial(woodMats[a]);
			String name = mat.name;
    			planks[a] = new ItemComponentMF("plank"+name, 0).setCustom("This is irrelevant text,but i'm lazy now");
    			OreDictionary.registerOre("MFplank2"+name, planks[a]);
    		
		}*/
		
		
		
		addRandomDrops();
		initFuels();
		OreDictionary.registerOre("ingotCompositeAlloy", ingotCompositeAlloy);
		OreDictionary.registerOre("ingotIron", Items.iron_ingot);
		OreDictionary.registerOre("ingotGold", Items.gold_ingot);
		OreDictionary.registerOre("carbon", new ItemStack(Items.coal, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("carbon", coke);
		OreDictionary.registerOre("carbon", coal_flux);
	}

	private static void initFuels() 
	{
		MineFantasyAPI.addForgeFuel(new ItemStack(Items.coal, 1, 0), 900, 150);//	150* , 45s
		MineFantasyAPI.addForgeFuel(new ItemStack(Items.coal, 1, 1), 1200, 170);//	170* , 1m
		MineFantasyAPI.addForgeFuel(Items.blaze_powder, 200, 300, true);//			300* , 10s
		MineFantasyAPI.addForgeFuel(Items.blaze_rod,    300, 300, true);//			300* , 15s
		MineFantasyAPI.addForgeFuel(Items.fire_charge,  1200, 350,true);//			350* , 1m
		MineFantasyAPI.addForgeFuel(Items.lava_bucket,  2400, 500, true);//			500* , 2m
		MineFantasyAPI.addForgeFuel(Items.magma_cream,  2400, 400);//				400* , 2m
		
		MineFantasyAPI.addForgeFuel(ComponentListMF.coalDust, 200, 150);//				150* , 10s
		MineFantasyAPI.addForgeFuel(ComponentListMF.coke, 1200, 250);//					250* , 1m
		MineFantasyAPI.addForgeFuel(ComponentListMF.magma_cream_refined, 2400, 200);//	500* , 2m
	}

	private static void addRandomDrops()
	{
		RandomOre.addOre(new ItemStack(kaolinite),    1.5F,  Blocks.stone,         	  -1, 32,128, false);
		RandomOre.addOre(new ItemStack(flux),       	2F,  Blocks.stone,         	  -1, 0, 128, false);
		RandomOre.addOre(new ItemStack(flux_strong),    1F,  Blocks.stone,         	   2, 0, 128, false);
		RandomOre.addOre(new ItemStack(flux),       	20F, BlockListMF.limestone, 0,   -1, 0, 256, true);
		RandomOre.addOre(new ItemStack(flux_strong),    10F, BlockListMF.limestone, 0,    2, 0, 256, true);
		RandomOre.addOre(new ItemStack(Items.coal), 	2F,  Blocks.stone, 			  -1, 0, 128, false);
		RandomOre.addOre(new ItemStack(sulfur),     	2F,  Blocks.stone, 			  -1, 0, 16,  false);
		RandomOre.addOre(new ItemStack(nitre),      	3F,  Blocks.stone, 			  -1, 0, 64,  false);
		RandomOre.addOre(new ItemStack(Items.redstone), 5F,  Blocks.stone, 			   2, 0, 16,  false);
		RandomOre.addOre(new ItemStack(Items.flint),    1F,  Blocks.stone, 			  -1, 0, 64,  false);
		RandomOre.addOre(new ItemStack(diamond_shards), 0.2F,Blocks.stone, 		       2, 0, 16,  false);
		RandomOre.addOre(new ItemStack(Items.quartz),   0.5F,Blocks.stone, 		       3, 0, 16,  false);
		
		RandomOre.addOre(new ItemStack(sulfur), 			 	10F,Blocks.netherrack, 	 -1, 0, 512,  	false);
		RandomOre.addOre(new ItemStack(Items.glowstone_dust),	5F,Blocks.netherrack, 	 -1, 0, 512,  	false);
		RandomOre.addOre(new ItemStack(Items.quartz), 			5F, Blocks.netherrack, 	 -1, 0, 512,  	false);
		RandomOre.addOre(new ItemStack(Items.blaze_powder), 	5F, Blocks.netherrack,   -1, 0, 512,  	false);
		RandomOre.addOre(new ItemStack(Items.nether_wart), 		1F, Blocks.netherrack,   -1, 0, 512,  	false);
		RandomOre.addOre(new ItemStack(Items.nether_star), 	    0.01F, Blocks.netherrack,   -1, 0, 512,false);
		
		RandomDigs.addOre(new ItemStack(Blocks.skull, 1, 1), 0.1F, Blocks.soul_sand,3, 0, 256,  false);
		RandomDigs.addOre(new ItemStack(Items.bone),   		 5F, Blocks.dirt,  -1, 0, 256,  false);
		RandomDigs.addOre(new ItemStack(Items.rotten_flesh), 2F, Blocks.dirt,  -1, 0, 256,  false);
		RandomDigs.addOre(new ItemStack(Items.coal, 1, 1),   1F, Blocks.dirt,  -1, 32, 64,  false);
		
		RandomDigs.addOre(new ItemStack(Items.melon_seeds),   5F, Blocks.grass,  -1, 0, 256,  false);
		RandomDigs.addOre(new ItemStack(Items.pumpkin_seeds), 8F, Blocks.grass,  -1, 0, 256,  false);
		
		RandomOre.addOre(new ItemStack(oreCopper),            4F, Blocks.stone, 0, 48, 96,  false);
		RandomOre.addOre(new ItemStack(oreTin),               2F,Blocks.stone, 0, 48, 96,  false);
		RandomOre.addOre(new ItemStack(oreIron),              5F,   Blocks.stone, 0, 0, 64,  false);
		RandomOre.addOre(new ItemStack(oreSilver),            1.5F, Blocks.stone, 0, 0, 32,  false);
		RandomOre.addOre(new ItemStack(oreGold),              1F, Blocks.stone, 0, 0, 32,  false);
		
		RandomOre.addOre(new ItemStack(oreTungsten),          1F, Blocks.stone, 3, 0, 16,  false, "tungsten");
	}
}
