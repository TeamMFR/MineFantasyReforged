package minefantasy.mf2.api.crafting.refine;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class QuernRecipes {
    public static ArrayList<QuernRecipes> recipeList = new ArrayList<QuernRecipes>();
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
                if (doesMatch(input, recipes.input)) {
                    return recipes;
                }
            }
        }
        return null;
    }

    private static boolean doesMatch(ItemStack item1, ItemStack item2) {
        return item2.getItem() == item1.getItem() && (item2.getItemDamage() == OreDictionary.WILDCARD_VALUE
                || item2.getItemDamage() == item1.getItemDamage());
    }
}