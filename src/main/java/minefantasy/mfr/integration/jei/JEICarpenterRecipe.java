package minefantasy.mfr.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import minefantasy.mfr.recipe.ShapedCarpenterRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a JEI "recipe" for the carpenter bench.
 */
public class JEICarpenterRecipe implements IRecipeWrapper {

	private final ItemStack result;

	private final List<List<ItemStack>> ingredients;

	public JEICarpenterRecipe(ShapedCarpenterRecipes recipe){

		this.result = recipe.getRecipeOutput();
		this.ingredients = new ArrayList<>();
		this.ingredients.addAll(Collections.singletonList(Arrays.asList(recipe.recipeItems))); // ingredients

	}

	@Override
	public void getIngredients(IIngredients ingredients){
		ingredients.setInputLists(VanillaTypes.ITEM, this.ingredients);
		ingredients.setOutput(VanillaTypes.ITEM, result);
	}

	/**
	 * TODO?: Draw carpenter and tool tier requirements, craft time requirements and anything else we need
	 */
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
	}

	/**
	 * TODO?: This could be used to show the tier and time requirement tooltips, if any
	 */
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return null;
	}

	/**
	 *  TODO?: This could be used to open the page in the recipe book where we have this recipe
	 */
	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}
}
