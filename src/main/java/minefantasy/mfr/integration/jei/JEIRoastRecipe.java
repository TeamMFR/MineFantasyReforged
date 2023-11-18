package minefantasy.mfr.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import minefantasy.mfr.recipe.RoastRecipeBase;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.List;

public class JEIRoastRecipe implements IRecipeWrapper {
	private final ItemStack result;
	protected final RoastRecipeBase recipe;
	private final List<List<ItemStack>> ingredients;

	public JEIRoastRecipe(RoastRecipeBase recipe, IStackHelper stackHelper) {
		this.result = recipe.getRoastRecipeOutput();
		this.recipe = recipe;
		this.ingredients = stackHelper.expandRecipeItemStackInputs(recipe.getInputs());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, this.ingredients);
		ingredients.setOutput(VanillaTypes.ITEM, result);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		String max = recipe.getMaxTemperature() + "℃";
		String min = recipe.getMinTemperature() + "℃";
		int maxAdjust = max.length() > 4 ? -1 : 2;
		int minAdjust = min.length() > 4 ? -1 : 2;

		minecraft.fontRenderer.drawStringWithShadow(max,
				(float) 34 + maxAdjust, (float) 0, 16777215);

		minecraft.fontRenderer.drawStringWithShadow(
				min,
				(float) 34 + minAdjust, (float) 45, 16777215);
	}

	/**
	 * TODO?: This could be used to open the page in the recipe book where we have this recipe
	 */
	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}
}
