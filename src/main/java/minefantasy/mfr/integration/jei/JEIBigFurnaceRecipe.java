package minefantasy.mfr.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import minefantasy.mfr.recipe.BigFurnaceRecipeBase;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.List;

public class JEIBigFurnaceRecipe implements IRecipeWrapper {
	private final ItemStack result;
	protected BigFurnaceRecipeBase recipe;
	private final List<List<ItemStack>> ingredients;

	public JEIBigFurnaceRecipe(BigFurnaceRecipeBase recipe, IStackHelper stackHelper ) {
		this.result = recipe.getBigFurnaceRecipeOutput();
		this.recipe = recipe;
		this.ingredients = stackHelper.expandRecipeItemStackInputs(recipe.getInputs());
	}

	public JEIBigFurnaceRecipe(ItemStack output, List<ItemStack> inputs, IStackHelper stackHelper) {
		this.result = output;
		this.ingredients = stackHelper.expandRecipeItemStackInputs(inputs);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, this.ingredients);
		ingredients.setOutput(VanillaTypes.ITEM, result);
	}

	/**
	 * TODO?: This could be used to open the page in the recipe book where we have this recipe
	 */
	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}
}
