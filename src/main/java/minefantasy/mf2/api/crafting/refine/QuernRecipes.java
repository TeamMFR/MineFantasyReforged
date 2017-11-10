package minefantasy.mf2.api.crafting.refine;

import minefantasy.mf2.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashSet;

public class QuernRecipes {
    public static HashSet<QuernRecipes> recipeList = new HashSet<QuernRecipes>();
    public final ItemStack input, result;
    public final boolean consumePot;
    public final int tier;

    public QuernRecipes(ItemStack input, ItemStack output, int tier, boolean consumePot) {
        this.input = input;
        this.result = output;
        this.consumePot = consumePot;
        this.tier = tier;
    }

    public static QuernRecipes addRecipe(Block input, ItemStack output, int tier, boolean consumePot) {
        return addRecipe(new ItemStack(input, 1, OreDictionary.WILDCARD_VALUE), output, tier, consumePot);
    }

    public static QuernRecipes addRecipe(Item input, ItemStack output, int tier, boolean consumePot) {
        return addRecipe(new ItemStack(input, 1, OreDictionary.WILDCARD_VALUE), output, tier, consumePot);
    }

    public static QuernRecipes addRecipe(ItemStack input, ItemStack output, int tier, boolean consumePot) {
        QuernRecipes recipe = new QuernRecipes(input, output, tier, consumePot);
        recipeList.add(recipe);
        return recipe;
    }

    public static QuernRecipes getResult(ItemStack input) {
        if (input != null) {
            for (QuernRecipes recipes : recipeList) {
                if (Utils.doesMatch(input, recipes.input)) {
                    return recipes;
                }
            }
        }
        return null;
    }
}