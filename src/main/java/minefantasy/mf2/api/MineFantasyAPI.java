package minefantasy.mf2.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import minefantasy.mf2.api.crafting.anvil.CraftingManagerAnvil;
import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.crafting.carpenter.CraftingManagerCarpenter;
import minefantasy.mf2.api.crafting.carpenter.ICarpenterRecipe;
import minefantasy.mf2.api.crafting.engineer.ICrossbowPart;
import minefantasy.mf2.api.heating.ForgeFuel;
import minefantasy.mf2.api.heating.ForgeItemHandler;
import minefantasy.mf2.api.heating.Heatable;
import minefantasy.mf2.api.refine.Alloy;
import minefantasy.mf2.api.refine.AlloyRecipes;
import minefantasy.mf2.api.refine.BlastFurnaceRecipes;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.IFuelHandler;

public class MineFantasyAPI 
{
	/**
	 * This variable saves if MineFantasy is in debug mode
	 */
	public static boolean isInDebugMode;

	public static void debugMsg(String msg)
	{
		MFLogUtil.logDebug(msg);
	}

	public static void removeAllRecipes(Item result)
	{
		List recipeList = CraftingManager.getInstance().getRecipeList();
		for(int i = 0; i < recipeList.size(); i++)
		{
			IRecipe recipe = (IRecipe)recipeList.get(i);
			if(recipe != null && recipe.getRecipeOutput() != null && recipe.getRecipeOutput().getItem() == result)
			{
				debugMsg("Removed Recipe for " + recipe.getRecipeOutput().getDisplayName());
			}
		}
	}
	
	/**
	 * Adds a shaped recipe for anvils with all variables
	 * 
	 * @param result The output item
	 * @param hot True if the result is hot(Does not apply to blocks)
	 * @param experiance the experiance gained from crafting
	 * @param toolType the tool type required to hit ("hammer", "hvyHammer", etc)
	 * @param hammerType the hammer tier required for creation:
	 * @param anvil the anvil required bronze 0, iron 1, steel 2
	 * @param forgeTime The time taken to forge(default is 200. each hit is about 100)
	 * @param input The input for the item (Exactly the same as regular recipes)
	 */
	public static void addAnvilRecipe(Skill skill, ItemStack result, boolean hot, String toolType, int hammerType, int anvil, int forgeTime, Object... input)
	{
		CraftingManagerAnvil.getInstance().addRecipe(result, skill, "", hot, 0F, toolType, hammerType, anvil, forgeTime, input);
	}
	/** {@link MineFantasyAPI#addCarpenterRecipe} */
	public static void addAnvilRecipe(Skill skill, ItemStack result, boolean hot, int hammerType, int anvil, int forgeTime, Object... input)
	{
		addAnvilRecipe(skill, result, hot, "hammer", hammerType, anvil, forgeTime, input);
	}
	
	/**
	 * Adds a shaped recipe for anvils with all variables
	 * 
	 * @param result The output item
	 * @param research The research required
	 * @param hot True if the result is hot(Does not apply to blocks)
	 * @param experiance the experiance gained from crafting
	 * @param toolType the tool type required to hit ("hammer", "hvyHammer", etc)
	 * @param hammerType the hammer tier required for creation:
	 * @param anvil the anvil required bronze 0, iron 1, steel 2
	 * @param forgeTime The time taken to forge(default is 200. each hit is about 100)
	 * @param input The input for the item (Exactly the same as regular recipes)
	 */
	public static IAnvilRecipe addAnvilRecipe(Skill skill, ItemStack result, String research, boolean hot, String toolType, int hammerType, int anvil, int forgeTime, Object... input)
	{
		return CraftingManagerAnvil.getInstance().addRecipe(result, skill, research, hot, 0F, toolType, hammerType, anvil, forgeTime, input);
	}
	/** {@link MineFantasyAPI#addCarpenterRecipe} */
	public static IAnvilRecipe addAnvilRecipe(Skill skill, ItemStack result, String research, boolean hot, int hammerType, int anvil, int forgeTime, Object... input)
	{
		return addAnvilRecipe(skill, result, research, hot, "hammer", hammerType, anvil, forgeTime, input);
	}
	
	/**
	 * Adds a shaped recipe for carpenter benches with all variables
	 * 
	 * @param result The output item
	 * @param sound The sound it makes ("minefantasy2:blocks.carpentermallet"), "step.wood", etc
	 * @param experience the experience gained from crafting
	 * @param toolType the tool type required to hit
	 * @param toolTier the tools tier required for creation:
	 * @param craftTime The time taken to craft(default is 200. each hit is about 100)
	 * @param input The input for the item (Exactly the same as regular recipes)
	 */
	public static void addCarpenterRecipe(Skill skill, ItemStack result, String sound, String toolType, int toolTier, int craftTime, Object... input)
	{
		CraftingManagerCarpenter.getInstance().addRecipe(result, skill, "", sound, 0F, toolType, toolTier, -1, craftTime, input);
	}
	/** {@link MineFantasyAPI#addCarpenterRecipe} */
	public static void addCarpenterRecipe(Skill skill, ItemStack result, String sound, int craftTime, Object... input)
	{
		addCarpenterRecipe(skill, result, "", sound, "hands", -1, craftTime, input);
	}
	/** {@link MineFantasyAPI#addCarpenterRecipe} */
	public static void addShapelessCarpenterRecipe(Skill skill, ItemStack result, String sound, String toolType, int toolTier, int craftTime, Object... input)
	{
		CraftingManagerCarpenter.getInstance().addShapelessRecipe(result, skill, "", sound, 0F, toolType, toolTier, -1, craftTime, input);
	}
	/** {@link MineFantasyAPI#addCarpenterRecipe} */
	public static void addShapelessCarpenterRecipe(Skill skill, ItemStack result, String sound, int craftTime, Object... input)
	{
		addShapelessCarpenterRecipe(skill, result,  sound, "hands", -1, craftTime, input);
	}
	
	/**
	 * Adds a basic carpenter recipe similar to regular crafting
	 * This uses a single hit with bare hands
	 */
	public static ICarpenterRecipe addBasicCarpenterRecipe(ItemStack result, Object... input)
	{
		return CraftingManagerCarpenter.getInstance().addRecipe(result, null, "", "dig.wood", 0F, "hands", -1, -1, 1, input);
	}
	/**
	 * Adds a shaped recipe for carpenter benches with all variables
	 * 
	 * @param result The output item
	 * @param research The research required
	 * @param sound The sound it makes ("minefantasy2:blocks.carpentermallet"), "step.wood", etc
	 * @param experience the experience gained from crafting
	 * @param toolType the tool type required to hit
	 * @param toolTier the tools tier required for creation:
	 * @param craftTime The time taken to craft(default is 200. each hit is about 100)
	 * @param input The input for the item (Exactly the same as regular recipes)
	 */
	public static ICarpenterRecipe addCarpenterRecipe(Skill skill, ItemStack result, String research, String sound, String toolType, int toolTier, int craftTime, Object... input)
	{
		return CraftingManagerCarpenter.getInstance().addRecipe(result, skill, research, sound, 0F, toolType, toolTier, -1, craftTime, input);
	}
	/** {@link MineFantasyAPI#addCarpenterRecipe} */
	public static ICarpenterRecipe addCarpenterRecipe(Skill skill, ItemStack result, String research, String sound, int craftTime, Object... input)
	{
		return addCarpenterRecipe(skill, result, research, sound, "hands", -1, craftTime, input);
	}
	/** {@link MineFantasyAPI#addCarpenterRecipe} */
	public static ICarpenterRecipe addShapelessCarpenterRecipe(Skill skill, ItemStack result, String research, String sound, String toolType, int toolTier, int craftTime, Object... input)
	{
		return CraftingManagerCarpenter.getInstance().addShapelessRecipe(result, skill, research, sound, 0F, toolType, toolTier, -1, craftTime, input);
	}
	/** {@link MineFantasyAPI#addCarpenterRecipe} */
	public static ICarpenterRecipe addShapelessCarpenterRecipe(Skill skill, ItemStack result, String research, String sound, int craftTime, Object... input)
	{
		return addShapelessCarpenterRecipe(skill, result, research,  sound, "hands", -1, craftTime, input);
	}

	public static void addBlastFurnaceRecipe(Block input, ItemStack output) 
	{
		BlastFurnaceRecipes.smelting().addRecipe(input, output);
	}
	public static void addBlastFurnaceRecipe(Item input, ItemStack output) 
	{
		BlastFurnaceRecipes.smelting().addRecipe(input, output);
	}
	public static void addBlastFurnaceRecipe(ItemStack input, ItemStack output) 
	{
		BlastFurnaceRecipes.smelting().addRecipe(input, output);
	}
	/**
	 * This fuel handler is for MineFantasy equipment, it uses real fuel(like coal) not wood
	 */
	private static List<IFuelHandler> fuelHandlers = Lists.newArrayList();
	public static void registerFuelHandler(IFuelHandler handler)
    {
        fuelHandlers.add(handler);
    }
    public static int getFuelValue(ItemStack itemStack)
    {
        int fuelValue = 0;
        for (IFuelHandler handler : fuelHandlers)
        {
            fuelValue = Math.max(fuelValue, handler.getBurnTime(itemStack));
        }
        return fuelValue;
    }
    
    /**
	 * Adds an alloy for any Crucible
	 * @param out The result
	 * @param in The list of required items
	 */
	public static void addAlloy(ItemStack out, Object... in)
	{
		AlloyRecipes.addAlloy(out, convertList(in));
	}
	
	/**
	 * Adds an alloy with a minimal furnace level
	 * @param out The result
	 * @param level The minimal furnace level
	 * @param in The list of required items
	 */
	public static void addAlloy(ItemStack out, int level, Object... in)
	{
		AlloyRecipes.addAlloy(out, level, convertList(in));
	}
	
	
	/**
	 * Adds an alloy with a minimal furnace level
	 * @param dupe the amount of times the ratio can be added
	 * @param out The result
	 * @param level The minimal furnace level
	 * @param in The list of required items
	 */
	public static Alloy[] addRatioAlloy(int dupe, ItemStack out, int level, Object... in)
	{
		return AlloyRecipes.addRatioRecipe(out, level, convertList(in), dupe);
	}
	
	/**
	 * Adds an alloy with any smelter
	 * @param dupe the amount of times the ratio can be added
	 * @param out The result
	 * @param level The minimal furnace level
	 * @param in The list of required items
	 */
	public static Alloy[] addRatioAlloy(int dupe, ItemStack out, Object... in)
	{
		return AlloyRecipes.addRatioRecipe(out, 0, convertList(in), dupe);
	}
	
	/**
	 * Adds a custom alloy
	 * @param alloy the Alloy to add
	 * Use this if you want your alloy to have special properties
	 * @see Alloy
	 */
	public static void addAlloy(Alloy alloy)
	{
		AlloyRecipes.addAlloy(alloy);
	}
	
	private static List convertList(Object[] in) 
	{
		ArrayList arraylist = new ArrayList();
        Object[] aobject = in;
        int i = in.length;

        for (int j = 0; j < i; ++j)
        {
            Object object1 = aobject[j];

            if (object1 instanceof ItemStack)
            {
                arraylist.add(((ItemStack)object1).copy());
            }
            else if (object1 instanceof Item)
            {
                arraylist.add(new ItemStack((Item)object1));
            }
            else
            {
                if (!(object1 instanceof Block))
                {
                    throw new RuntimeException("MineFantasy: Invalid alloy!");
                }

                arraylist.add(new ItemStack((Block)object1));
            }
        }
        
        return arraylist;
	}
	
	/**
	 * Adds a fuel for forges
	 * 
	 * @param item
	 *            the item ID to be used
	 * @param meta
	 *            the item Meta to be used
	 * @param dura
	 *            the amount of ticks said item gives
	 * @param temperature
	 *            the base temperature given
	 * @param willLight
	 *            weather the fuel lights forges(like lava)
	 */
	public static void addForgeFuel(Item item, int meta, float dura, int temperature, boolean willLight) {
		addForgeFuel(new ItemStack(item, 1, meta), dura, temperature, willLight);
	}

	/**
	 * Adds a fuel for forges
	 * 
	 * @param item
	 *            the item ID to be used
	 * @param meta
	 *            the item Meta to be used
	 * @param dura
	 *            the amount of ticks said item gives
	 * @param temperature
	 *            the base temperature given
	 */
	public static void addForgeFuel(Item item, int meta, float dura, int temperature) {
		addForgeFuel(item, meta, dura, temperature, false);
	}

	/**
	 * Adds a fuel for forges
	 * 
	 * @param item
	 *            the item to be used
	 * @param dura
	 *            the amount of ticks said item gives
	 * @param temperature
	 *            the base temperature given
	 * @param willLight
	 *            weather the fuel lights forges(like lava)
	 */
	public static void addForgeFuel(ItemStack item, float dura, int temperature, boolean willLight) {
		ForgeItemHandler.forgeFuel.add(new ForgeFuel(item, dura, temperature, willLight));
		if ((int) (temperature * 1.25) > ForgeItemHandler.forgeMaxTemp) {
			ForgeItemHandler.forgeMaxTemp = (int) (temperature * 1.25);
		}
	}

	/**
	 * Adds a fuel for forges
	 * 
	 * @param item
	 *            the item to be used
	 * @param dura
	 *            the amount of ticks said item gives
	 * @param temperature
	 *            the base temperature given
	 * @param willLight
	 *            weather the fuel lights forges(like lava)
	 */
	public static void addForgeFuel(Item id, float dura, int temperature, boolean willLight) {
		addForgeFuel(new ItemStack(id, 2, OreDictionary.WILDCARD_VALUE), dura, temperature, willLight);
	}

	/**
	 * Adds a fuel for forges
	 * 
	 * @param item
	 *            the item to be used
	 * @param dura
	 *            the amount of ticks said item gives
	 * @param temperature
	 *            the base temperature given
	 */
	public static void addForgeFuel(ItemStack item, float dura, int temperature) {
		addForgeFuel(item, dura, temperature, false);
	}

	/**
	 * Adds a fuel for forges
	 * 
	 * @param item the item to be used
	 * @param dura the amount of ticks said item gives
	 * @param temperature the base temperature given
	 */
	public static void addForgeFuel(Item id, float dura, int temperature) {
		addForgeFuel(id, dura, temperature, false);
	}

	/**
	 * Allows an item to be heated
	 * 
	 * @param item the item to heat
	 * @param min the minimum heat to forge with(celcius)
	 * @param max the maximum heat until the item is ruined(celcius)
	 * @param unstable when the ingot is unstable(celcius)
	 */
	public static void setHeatableStats(ItemStack item, int min, int unstable, int max) 
	{
		Heatable.addItem(item, min, unstable, max);
	}

	/**
	 * Allows an item to be heated ignoring subId
	 * 
	 * @param id the item to heat
	 * @param min the minimum heat to forge with(celcius)
	 * @param max the maximum heat until the item is ruined(celcius)
	 * @param unstable when the ingot is unstable(celcius)
	 */
	public static void setHeatableStats(Item id, int min, int unstable, int max) 
	{
		Heatable.addItem(new ItemStack(id, 1, OreDictionary.WILDCARD_VALUE), min, unstable, max);
	}
	
	public static void setHeatableStats(String oredict, int min, int unstable, int max)
	{
		for(ItemStack item : OreDictionary.getOres(oredict))
		{
			setHeatableStats(item, min, unstable, max);
		}
	}

	private static ItemStack convertItem(Object object) 
	{
		if(object instanceof ItemStack)
		{
			return ((ItemStack)object);
		}
		if(object instanceof Item)
		{
			return new ItemStack((Item)object, 1, OreDictionary.WILDCARD_VALUE);
		}
		if(object instanceof Block)
		{
			return new ItemStack((Block)object, 1, OreDictionary.WILDCARD_VALUE);
		}
		return null;
	}

	/**
	 * Adds a crossbow part Make sure to do this when adding the item
	 */
	public static void registerCrossbowPart(ICrossbowPart part)
	{
		ICrossbowPart.components.put(part.getComponentType()+part.getID(), part);
	}
	/**
	 * For Hardcore Crafting: Can remove smelting recipes
	 * @param input can be an "Item", "Block" or "ItemStack"
	 */
	public static boolean removeSmelting(Object input)
    {
		ItemStack object = null;
		if(input instanceof Item)
		{
			object = new ItemStack((Item)input, 1, 32767);
		}
		else if(input instanceof Block)
		{
			object = new ItemStack((Block)input, 1, 32767);
		}
		else if(input instanceof ItemStack)
		{
			object = (ItemStack)input;
		}
		if(object != null)
		{
			return removeFurnaceInput(object);
		}
		return false;
    }
	private static boolean removeFurnaceInput(ItemStack input)
    {
        Iterator iterator = FurnaceRecipes.smelting().getSmeltingList().entrySet().iterator();
        Entry entry;

        do
        {
            if (!iterator.hasNext())
            {
                return false;
            }

            entry = (Entry)iterator.next();
        }
        while (!checkMatch(input, (ItemStack)entry.getKey()));

        FurnaceRecipes.smelting().getSmeltingList().remove(entry.getKey());
        return true;
    }
	private static boolean checkMatch(ItemStack item1, ItemStack item2)
    {
        return item2.getItem() == item1.getItem() && (item2.getItemDamage() == 32767 || item2.getItemDamage() == item1.getItemDamage());
    }

	public static boolean isCarbon(ItemStack item)
	{
		for(int i: OreDictionary.getOreIDs(item))
		{
			String name = OreDictionary.getOreName(i);
			if(name != null && name.equalsIgnoreCase("carbon"))
			{
				return true;
			}
		}
		return false;
	}
}
