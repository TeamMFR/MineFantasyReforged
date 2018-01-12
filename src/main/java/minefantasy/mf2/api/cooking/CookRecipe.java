package minefantasy.mf2.api.cooking;

import minefantasy.mf2.api.helpers.CustomToolHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.HashMap;

public class CookRecipe {
    public static final HashMap<String, CookRecipe> recipeList = new HashMap<String, CookRecipe>();
    public static Item burnt_food = null;
    public static boolean canCookBasics = true;
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

    /**
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

    /**
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
            addRecipe(out, burnt, null, min, max, burntime, bake, false);
        }
        return recipe;
    }

    public static CookRecipe getResult(ItemStack item, boolean oven) {
        if (item == null)
            return null;

        CookRecipe result = recipeList.get(CustomToolHelper.getReferenceName(item));
        if (result != null && result.isBaking == oven) {
            return result;
        }

        if (canCookBasics) {
            ItemStack recipe = FurnaceRecipes.smelting().getSmeltingResult(item);
            if (recipe != null && recipe.getItem() instanceof ItemFood) {
                return new CookRecipe(recipe, new ItemStack(burnt_food), 100, 300, 20, false, true);
            }
        }
        return null;
    }
}
