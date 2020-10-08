package minefantasy.mfr.recipe;

import minefantasy.mfr.MineFantasyReborn;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class RecipeHelper {

	private RecipeHelper() {} // no instances!

	public static IRecipe getRecipeFromRegistry(String resourceName) {
		return CraftingManager.getRecipe(new ResourceLocation(resourceName));
	}

	public static IRecipe getMFRRecipe(String fileName) {
		return getRecipeFromRegistry(MineFantasyReborn.MOD_ID + ":" + fileName);
	}
}
