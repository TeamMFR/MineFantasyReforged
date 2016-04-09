package minefantasy.mf2.item.food;

import minefantasy.mf2.item.ItemComponentMF;
import minefantasy.mf2.item.ItemMFBowl;
import minefantasy.mf2.item.gadget.ItemJug;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;

public class FoodListMF
{
	//MORSELS
	public static Item wolf_raw = (new ItemFoodMF("wolf_raw", 2, 0.2F, true));
	public static Item wolf_cooked = (new ItemFoodMF("wolf_cooked", 6, 0.6F, true));
	public static Item horse_raw = (new ItemFoodMF("horse_raw", 4, 0.4F, true)).setPotionEffect(Potion.hunger.id, 50, 0, 0.5F);
	public static Item horse_cooked = (new ItemFoodMF("horse_cooked", 10, 1.0F, true));
	
	public static Item generic_meat_uncooked = (new ItemFoodMF("generic_meat_uncooked", 2, 0.2F, true));
	public static Item generic_meat_cooked = (new ItemFoodMF("generic_meat_cooked", 	5, 0.5F, true));
	public static Item generic_meat_strip_uncooked = (new ItemFoodMF("generic_meat_strip_uncooked", 2, 0.2F, true));
	public static Item generic_meat_strip_cooked = (new ItemFoodMF("generic_meat_strip_cooked", 	5, 0.5F, true));
	public static Item generic_meat_chunk_uncooked = (new ItemFoodMF("generic_meat_chunk_uncooked", 2, 0.2F, true));
	public static Item generic_meat_chunk_cooked = (new ItemFoodMF("generic_meat_chunk_cooked", 	5, 0.5F, true));
	public static Item generic_meat_mince_uncooked = (new ItemFoodMF("generic_meat_mince_uncooked", 2, 0.2F, true)).setContainerItem(ComponentListMF.clay_pot);
	public static Item generic_meat_mince_cooked = (new ItemFoodMF("generic_meat_mince_cooked", 	5, 0.5F, true)).setContainerItem(ComponentListMF.clay_pot);
	
	public static Item tea_leaf = (new ItemFoodMF("tea_leaf", 1, 0F, false)).setStaminaModifier(10, 1);
	public static Item spice_mild = (new ItemFoodMF("spice_mild", 1, 0F, false)).setContainerItem(ComponentListMF.clay_pot);
	public static Item flour = new ItemComponentMF("flour", 0).setCreativeTab(CreativeTabMF.tabFood).setContainerItem(ComponentListMF.clay_pot);
	public static Item breadcrumbs = new ItemComponentMF("breadcrumbs", 0).setCreativeTab(CreativeTabMF.tabFood).setContainerItem(ComponentListMF.clay_pot);
	public static Item guts = new ItemComponentMF("guts", 0).setCreativeTab(CreativeTabMF.tabFood);
	
	public static Item breadroll = (new ItemFoodMF("breadroll", 5, 1.0F, false));
	public static Item breadSlice = (new ItemFoodMF("breadslice", 2, 1.0F, false));
	
	public static Item curds = new ItemUnfinishedFood("curds");
	public static Item cheese_slice = (new ItemFoodMF("cheese_slice",       4, 1.0F, 0, false, 0));
	
	//T1 (basic mixing)
	//Util: Roast, Prep Block (Stone-Bronze Age)
	public static Item stew = (new ItemFoodMF("stew"    , 5, 1.0F,  5, false, 0)).setStaminaModifier (15F, 1.0F).setReturnItem(Items.bowl).setMaxStackSize(1);
	public static Item salad = (new ItemFoodMF("salad"  , 5,  1.0F,  5, false, 0)).setStaminaModifier (10F, 1.5F).setReturnItem(Items.bowl).setMaxStackSize(1);
	public static Item oats = (new ItemFoodMF("oats"    , 5,  1.0F,  3, false, 0)).setStaminaModifier (20F, 1.0F).setReturnItem(Items.bowl).setMaxStackSize(1);
	
	//T2 (Basic baking, stone oven, processed mixing)
	//Util: Stone Oven, Prep Block (Bronze Age - Early Iron Age)
	public static Item cheese_roll = (new ItemFoodMF("cheese_roll",				6,    1.0F, 8, false, 0)).setStaminaModifier (20F, 1.0F).setMaxStackSize(1);
	public static Item jerky = (new ItemFoodMF("jerky",            				6,    1.0F, 6, true, 0)).setStaminaModifier (20F, 1.0F).setStaminaRegenModifier(2, 10F).setMaxStackSize(8);
	public static Item saussage_raw = (new ItemFoodMF("saussage_raw",           4,    1.0F, 5, true, 0)).setStaminaModifier (10F, 1.0F).setStaminaRegenModifier(1, 10F).setMaxStackSize(16);
	public static Item saussage_cooked = (new ItemFoodMF("saussage_cooked",     8,    1.0F, 10, true, 0)).setStaminaModifier (30F, 1.0F).setStaminaRegenModifier(2, 10F).setMaxStackSize(16);
	public static Item sweetroll_uniced = (new ItemFoodMF("sweetroll_uniced", 	5,    1.0F, 0, false, 0)).setStaminaRegenModifier (5F, 1F).setStaminaRestore(5F).setEatTime(16).setAlwaysEdible().setMaxStackSize(64);
	public static Item sweetroll = (new ItemMultiFood("sweetroll",            2,3,  1.0F, 0, 0)).setStaminaRegenModifier (10F, 2F).setStaminaRestore(10F).setEatTime(16).setAlwaysEdible();
	public static Item sandwitch_meat = (new ItemMultiFood("sandwitch_meat",  2,6,  1.0F, 8, 1)).setStaminaModifier(25F, 1.0F).setTextureName("minefantasy2:food/sandwitch").setUnlocalizedName("sandwitch");
	public static Item sandwitch_big = (new ItemMultiFood("sandwitch_big",    4,6,  1.0F, 8, 1)).setStaminaModifier(25F, 1.0F).setTextureName("minefantasy2:food/sandwitchbig").setUnlocalizedName("sandwitch");
	public static Item eclair_uniced = (new ItemFoodMF("eclair_uniced", 	5,    1.0F, 1, false, 0)).setStaminaRegenModifier (10F, 1F).setStaminaRestore(20F).setEatTime(16).setAlwaysEdible().setMaxStackSize(64).setTextureName("minefantasy2:food/unfinished/eclair_uniced");
	public static Item eclair_empty = (new ItemFoodMF("eclair_empty", 	    5,    1.0F, 2, false, 0)).setStaminaRegenModifier (15F, 1F).setStaminaRestore(60F).setEatTime(16).setAlwaysEdible().setMaxStackSize(64).setTextureName("minefantasy2:food/unfinished/eclair_empty");
	
	//T3 (Quality baking, metal oven)
	//Util: Metal Oven, Prep Block, Steel Tools (Mid Iron Age)
	public static Item meatpie_slice = (new ItemFoodMF("meatpie_slice",        8,  1.0F, 10, false, 0)).setStaminaModifier(50F, 1.0F).setMaxStackSize(1);
	public static Item pieslice_apple = (new ItemFoodMF("pieslice_apple",      5,  1.0F, 4, false, 0)).setStaminaRegenModifier(5, 5F).setStaminaRestore(10F).setEatTime(16).setAlwaysEdible().setAlwaysEdible().setTextureName("minefantasy2:food/applepie_slice").setMaxStackSize(1);
	public static Item pieslice_berry = (new ItemFoodMF("pieslice_berry",      5 , 1.0F, 2, false, 0)).setStaminaRegenModifier(10, 2.5F).setStaminaRestore(25F).setEatTime(16).setAlwaysEdible().setAlwaysEdible().setTextureName("minefantasy2:food/berrypie_slice").setMaxStackSize(1);
	
	//T4 (Advanced baking, multiple processes, temperature regulation)
	//Util : Metal Oven, Prep Block, Full tool set, Proper kitchen setup (Mid Iron Age)
	public static Item pieslice_shepards = (new ItemFoodMF("pieslice_shepards",       10, 1.0F, 15, false, 1)).setStaminaModifier(100F, 1.0F).setTextureName("minefantasy2:food/shepardspie_slice").setMaxStackSize(1);
	
	public static Item cake_slice = (new ItemFoodMF("cake_slice",            3, 0.8F, 0, false, 0)).setStaminaRegenModifier(10F, 4F).setStaminaRestore(20F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1);
	public static Item carrotcake_slice = (new ItemFoodMF("carrotcake_slice",4, 0.8F, 2, false, 0)).setStaminaRegenModifier(10F, 4F).setStaminaRestore(40F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1);
	public static Item choccake_slice = (new ItemFoodMF("choccake_slice",    4, 0.8F, 2, false, 0)).setStaminaRegenModifier(20F, 2F).setStaminaRestore(50F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1);
	
	//T5 (Advanced baking, multiple process, temperature regulation, many ingreedients)
	
	//T6 (Perfeted meals, extremely difficylt to create)
	public static Item bfcake_slice = (new ItemFoodMF("bfcake_slice",      6, 1.0F, 2, false, 1)).setStaminaRegenModifier(20F, 4F).setStaminaRestore(100F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1);
	public static Item eclair = (new ItemMultiFood("eclair",             4,4, 1.0F, 2, 1)).setStaminaRegenModifier(16F, 4F).setStaminaRestore(100F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1);
	
	//MISC
	public static Item cake_tin = new ItemComponentMF("cake_tin", 0).setCreativeTab(CreativeTabMF.tabFood);
	public static Item pie_tray = new ItemComponentMF("pie_tin", 0).setCreativeTab(CreativeTabMF.tabFood);
	public static Item icing = new ItemComponentMF("icing", 0).setCreativeTab(CreativeTabMF.tabFood).setContainerItem(ComponentListMF.clay_pot);
	public static Item custard = new ItemComponentMF("custard", 0).setCreativeTab(CreativeTabMF.tabFood).setContainerItem(ComponentListMF.clay_pot);
	public static Item coca_powder = new ItemComponentMF("coca_powder", 0).setCreativeTab(CreativeTabMF.tabFood).setContainerItem(ComponentListMF.clay_pot);
	public static Item chocolate = new ItemComponentMF("chocolate", 0).setCreativeTab(CreativeTabMF.tabFood).setContainerItem(ComponentListMF.clay_pot);
	public static Item chocchips = new ItemComponentMF("chocchips", 0).setCreativeTab(CreativeTabMF.tabFood).setContainerItem(ComponentListMF.clay_pot);
	public static Item berries = (new ItemFoodMF("berries", 2, 2.0F, false)).setEatTime(10).setStaminaRestore(10F).setAlwaysEdible();
	public static Item berriesJuicy = (new ItemFoodMF("berriesJuicy", 3, 5.0F, false)).setEatTime(10).setStaminaRestore(25F).setRarity(1).setAlwaysEdible();
	
	public static Item sweetroll_raw = new ItemUnfinishedFood("sweetroll_raw").setMaxStackSize(64);
	public static Item eclair_raw = new ItemUnfinishedFood("eclair_raw").setMaxStackSize(64);
	public static Item cake_raw = new ItemUnfinishedFood("cake_raw");
	public static Item cake_simple_raw = new ItemUnfinishedFood("cake_simple_raw");
	public static Item cake_carrot_raw = new ItemUnfinishedFood("cake_carrot_raw");
	public static Item cake_choc_raw = new ItemUnfinishedFood("cake_choc_raw");
	public static Item cake_bf_raw = new ItemUnfinishedFood("cake_bf_raw");
	
	public static Item cake_uniced = new ItemUnfinishedFood("cake_uniced").setContainerItem(cake_tin);
	public static Item cake_simple_uniced = new ItemUnfinishedFood("cake_simple_uniced").setContainerItem(cake_tin);
	public static Item cake_carrot_uniced = new ItemUnfinishedFood("cake_carrot_uniced").setContainerItem(cake_tin);
	public static Item cake_choc_uniced = new ItemUnfinishedFood("cake_choc_uniced").setContainerItem(cake_tin);
	public static Item cake_bf_uniced = new ItemUnfinishedFood("cake_bf_uniced").setContainerItem(cake_tin);
	
	public static Item pie_meat_uncooked = new ItemUnfinishedFood("pie_meat_uncooked").setTextureName("minefantasy2:food/unfinished/pie_meat_uncooked");
	
	public static Item pie_apple_uncooked = new ItemUnfinishedFood("pie_apple_uncooked");
	public static Item pie_berry_uncooked = new ItemUnfinishedFood("pie_berry_uncooked");
	public static Item pie_shepard_uncooked = new ItemUnfinishedFood("pie_shepard_uncooked");
	public static Item pie_pumpkin_uncooked = new ItemUnfinishedFood("pie_pumpkin_uncooked");
	
	public static Item pie_meat_cooked = new ItemUnfinishedFood("pie_meat_cooked").setTextureName("minefantasy2:food/unfinished/pie_meat_cooked").setContainerItem(pie_tray);
	
	public static Item pie_apple_cooked = new ItemUnfinishedFood("pie_apple_cooked").setContainerItem(pie_tray);
	public static Item pie_berry_cooked = new ItemUnfinishedFood("pie_berry_cooked").setContainerItem(pie_tray);
	public static Item pie_shepard_cooked = new ItemUnfinishedFood("pie_shepard_cooked").setContainerItem(pie_tray);
	public static Item pie_pumpkin_cooked = new ItemUnfinishedFood("pie_pumpkin_cooked").setContainerItem(pie_tray);
	public static Item salt = new ItemComponentMF("salt", 0).setCreativeTab(CreativeTabMF.tabFood).setContainerItem(ComponentListMF.clay_pot);
	public static Item sugarpot = new ItemComponentMF("sugarpot", 0).setCreativeTab(CreativeTabMF.tabFood).setContainerItem(ComponentListMF.clay_pot);
	public static Item bowl_water_salt = new ItemComponentMF("bowl_water_salt", 0).setCreativeTab(CreativeTabMF.tabFood);
	
	public static Item dough = new ItemUnfinishedFood("dough").setMaxStackSize(64);
	public static Item pastry = new ItemUnfinishedFood("pastry").setMaxStackSize(64);
	public static Item raw_bread = new ItemUnfinishedFood("raw_bread").setMaxStackSize(64);
	
	public static Item jug_uncooked = new ItemJug("uncooked");
	public static Item jug_empty = new ItemJug("empty");
	public static Item jug_water = new ItemJug("water").setContainerItem(jug_empty);
	public static Item jug_milk = new ItemJug("milk").setContainerItem(jug_empty);
	//SPECIAL RECIPES
	
	public static final float satModifier = 1.0F;
	public static void load()
	{
	}
	
	/**
	 * FOOD TYPES:
	 * Sugar: Stamina Regen
	 * Dairy: Saturation
	 * Grain: Stamina Max
	 * 
	 * Protien: Saturation / Stamina Max
	 * Fruit: Stamina Max / Stamina Time
	 * Vegetable: Stamina Time / Saturation
	 */

}
