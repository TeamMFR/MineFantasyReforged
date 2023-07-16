package minefantasy.mfr.recipe;

import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.HashMap;

public class CookRecipe {
	public static final HashMap<String, CookRecipe> recipeList = new HashMap<>();
	public static Item burnt_food = null;
	public final int maxTemperature, minTemperature, time;
	public final boolean isBaking;
	public final ItemStack output, burnt;
	public final boolean canBurn;

	public CookRecipe(ItemStack output, ItemStack burnt, int min, int max, int time, boolean bake, boolean burn) {
		this.output = output;
		this.burnt = burnt;
		this.minTemperature = min;
		this.maxTemperature = max;
		this.time = time;
		this.isBaking = bake;
		this.canBurn = burn;
	}

	public static CookRecipe addRecipe(ItemStack in, ItemStack out, int min, int max, int time, boolean bake,
			boolean canBurn) {
		return addRecipe(in, out, new ItemStack(burnt_food), min, max, time, bake, canBurn);
	}

	/*
	 * Add a recipe
	 *
	 * @param output The result item
	 * @param min    the limit temperature for cooking
	 * @param max    the max temperature before burning
	 * @param time   the time in ticks taken
	 * @param bake   whether it needs to be enclosed in an oven
	 */

	public static CookRecipe addRecipe(ItemStack in, ItemStack out, ItemStack burnt, int min, int max, int time,
			boolean bake, boolean canBurn) {
		return addRecipe(in, out, burnt, min, max, time, time / 2, bake, canBurn);
	}

	/*
	 * Add a recipe
	 *
	 * @param output The result item
	 * @param min    the limit temperature for cooking
	 * @param max    the max temperature before burning
	 * @param time   the time in ticks taken
	 * @param bake   whether it needs to be enclosed in an oven
	 */

	public static CookRecipe addRecipe(ItemStack in, ItemStack out, ItemStack burnt, int min, int max, int time,
			int burntime, boolean bake, boolean canBurn) {
		CookRecipe recipe = new CookRecipe(out, burnt, min, max, time, bake, canBurn);
		recipeList.put(CustomToolHelper.getReferenceName(in), recipe);

		if (canBurn) {
			addRecipe(out, burnt, ItemStack.EMPTY, min, max, burntime, bake, false);
		}
		return recipe;
	}

	public static CookRecipe getResult(ItemStack item, boolean oven) {
		if (item.isEmpty())
			return null;

		CookRecipe result = recipeList.get(CustomToolHelper.getReferenceName(item));
		if (result != null && result.isBaking == oven) {
			return result;
		}

		if (ConfigCrafting.canCookBasics) {
			ItemStack recipe = FurnaceRecipes.instance().getSmeltingResult(item);
			if (!recipe.isEmpty() && recipe.getItem() instanceof ItemFood) {
				return new CookRecipe(recipe, new ItemStack(burnt_food), 100, 300, 20, false, true);
			}
		}
		return null;
	}
}
