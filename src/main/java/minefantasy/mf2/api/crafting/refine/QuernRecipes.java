package minefantasy.mf2.api.crafting.refine;

import java.util.HashMap;

import minefantasy.mf2.api.helpers.CustomToolHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class QuernRecipes 
{
	public static HashMap<String, QuernRecipes>recipeList = new HashMap<String, QuernRecipes>();
	
	public static QuernRecipes addRecipe(Block input, ItemStack output, int tier)
	{
		return addRecipe(new ItemStack(input, 1, OreDictionary.WILDCARD_VALUE), output, tier);
	}
	public static QuernRecipes addRecipe(Item input, ItemStack output, int tier)
	{
		return addRecipe(new ItemStack(input, 1, OreDictionary.WILDCARD_VALUE), output, tier);
	}
	public static QuernRecipes addRecipe(ItemStack input, ItemStack output, int tier)
	{
		QuernRecipes recipe = new QuernRecipes(input, output, tier);
		recipeList.put(CustomToolHelper.getReferenceName(input), recipe);
		return recipe;
	}
	public static QuernRecipes getResult(ItemStack input)
	{
		if(input == null)return null;
		
		QuernRecipes specific = recipeList.get(CustomToolHelper.getReferenceName(input));
		if(specific != null)return specific;
		
		return recipeList.get(CustomToolHelper.getReferenceName(input, "any"));
	}
	
	public final ItemStack result;
	public final int tier;
	public QuernRecipes(ItemStack input, ItemStack output, int tier)
	{
		this.result = output;
		this.tier = tier;
	}
}
