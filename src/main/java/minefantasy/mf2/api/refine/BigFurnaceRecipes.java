package minefantasy.mf2.api.refine;

import java.util.HashMap;

import minefantasy.mf2.api.helpers.CustomToolHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class BigFurnaceRecipes 
{
	private static final HashMap<String, BigFurnaceRecipes>recipeList = new  HashMap<String, BigFurnaceRecipes>();
	
	public static BigFurnaceRecipes addRecipe(ItemStack in, ItemStack out, int min, int time)
	{
		BigFurnaceRecipes recipe = new BigFurnaceRecipes(out, min, time);
		recipeList.put(CustomToolHelper.getReferenceName(in), recipe);
		
		return recipe;
	}
	
	public final int minTemperature, time;
	public final ItemStack output;

	public BigFurnaceRecipes(ItemStack output, int min, int time)
	{
		this.output=output;
		this.minTemperature = min;
		this.time = time;
	}
	
	public static BigFurnaceRecipes getResult(ItemStack item)
	{
		if(item == null)return null;
		
		BigFurnaceRecipes result = recipeList.get(CustomToolHelper.getReferenceName(item));
		if(result != null)
		{
			return result;
		}
		return null;
	}
}
