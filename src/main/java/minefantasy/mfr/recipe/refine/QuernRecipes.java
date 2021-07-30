package minefantasy.mfr.recipe.refine;

import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashSet;

public class QuernRecipes {
	public static HashSet<QuernRecipes> recipeList = new HashSet<>();
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
		if (!input.isEmpty()) {
			for (QuernRecipes recipes : recipeList) {
				if (Utils.doesMatch(input, recipes.input)) {
					return recipes;
				}
			}
		}
		return null;
	}

	public static void init() {
		addDusts();
	}

	private static void addDusts() {
		addRecipe(new ItemStack(Items.DYE, 1, 3), new ItemStack(MineFantasyItems.COCA_POWDER), 0, true);// ItemDye
		addRecipe(Items.WHEAT, new ItemStack(MineFantasyItems.FLOUR), 0, true);
		addRecipe(Items.REEDS, new ItemStack(MineFantasyItems.SUGAR_POT), 0, true);
		addRecipe(MineFantasyItems.BREADROLL, new ItemStack(MineFantasyItems.BREADCRUMBS), 0, true);
		addRecipe(MineFantasyItems.GENERIC_MEAT_UNCOOKED, new ItemStack(MineFantasyItems.GENERIC_MEAT_MINCE_UNCOOKED), 0, true);
		addRecipe(MineFantasyItems.GENERIC_MEAT_STRIP_UNCOOKED, new ItemStack(MineFantasyItems.GENERIC_MEAT_MINCE_UNCOOKED), 0, true);
		addRecipe(MineFantasyItems.GENERIC_MEAT_CHUNK_UNCOOKED, new ItemStack(MineFantasyItems.GENERIC_MEAT_MINCE_UNCOOKED), 0, true);
		addRecipe(MineFantasyItems.GENERIC_MEAT_COOKED, new ItemStack(MineFantasyItems.GENERIC_MEAT_MINCE_COOKED), 0, true);
		addRecipe(MineFantasyItems.GENERIC_MEAT_STRIP_COOKED, new ItemStack(MineFantasyItems.GENERIC_MEAT_MINCE_COOKED), 0, true);
		addRecipe(MineFantasyItems.GENERIC_MEAT_CHUNK_COOKED, new ItemStack(MineFantasyItems.GENERIC_MEAT_MINCE_COOKED), 0, true);
		addRecipe(Items.COAL, new ItemStack(MineFantasyItems.COAL_DUST), 0, true);
		addRecipe(new ItemStack(Items.COAL, 1, 1), new ItemStack(MineFantasyItems.COAL_DUST), 0, true);
		addRecipe(MineFantasyItems.KAOLINITE, new ItemStack(MineFantasyItems.KAOLINITE_DUST), 0, true);
		addRecipe(Items.FLINT, new ItemStack(MineFantasyItems.SHRAPNEL), 0, true);
		addRecipe(MineFantasyItems.FLUX, new ItemStack(MineFantasyItems.FLUX_POT), 0, true);
	}
}