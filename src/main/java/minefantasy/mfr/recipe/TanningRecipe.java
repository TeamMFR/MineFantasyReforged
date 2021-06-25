package minefantasy.mfr.recipe;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class TanningRecipe {
	public static final ArrayList<TanningRecipe> recipeList = new ArrayList<>();
	public int tier;
	public float time;
	public ItemStack input, output;
	public String toolType;

	public TanningRecipe(ItemStack input, float time, int tier, String toolType, ItemStack output) {
		this.time = time;
		this.tier = tier;
		this.input = input;
		this.output = output;
		this.toolType = toolType;
	}

	public static TanningRecipe addRecipe(Object input, float time, ItemStack output) {
		return addRecipe(input, time, -1, output);
	}

	public static TanningRecipe addRecipe(Object input, float time, int tier, ItemStack output) {
		return addRecipe(input, time, tier, "knife", output);
	}

	public static TanningRecipe addRecipe(Object input, float time, int tier, String toolType, ItemStack output) {
		TanningRecipe recipe = new TanningRecipe(convertItem(input), time, tier, toolType, output);
		recipeList.add(recipe);
		return recipe;
	}

	public static ItemStack convertItem(Object input) {
		if (input instanceof ItemStack) {
			return (ItemStack) input;
		}
		if (input instanceof Item) {
			return new ItemStack((Item) input, 1, OreDictionary.WILDCARD_VALUE);
		}
		if (input instanceof Block) {
			return new ItemStack((Block) input, 1, OreDictionary.WILDCARD_VALUE);
		}
		MineFantasyReforged.LOG.warn("Tanning Recipe found invalid item!");
		return ItemStack.EMPTY;
	}

	public static TanningRecipe getRecipe(ItemStack item) {
		if (item.isEmpty())
			return null;

		for (TanningRecipe recipe : recipeList) {
			if (recipe.input.getItem() == item.getItem()) {
				if (recipe.input.getItemDamage() == OreDictionary.WILDCARD_VALUE || recipe.input.getItemDamage() == item.getItemDamage()) {
					return recipe;
				}
			}
		}
		return null;
	}
}
