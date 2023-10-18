package minefantasy.mfr.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import minefantasy.mfr.recipe.QuernRecipeBase;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.List;

public class JEIQuernRecipe implements IRecipeWrapper {
	private final ItemStack result;
	protected final QuernRecipeBase recipe;
	private final List<List<ItemStack>> ingredients;
	private final List<List<ItemStack>> potInputs;

	public JEIQuernRecipe(QuernRecipeBase recipe, IStackHelper stackHelper) {
		this.result = recipe.getQuernRecipeOutput();
		this.recipe = recipe;
		this.ingredients = stackHelper.expandRecipeItemStackInputs(recipe.getInputs());
		this.potInputs = stackHelper.expandRecipeItemStackInputs(recipe.getPotInputs());
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

	public List<List<ItemStack>> getPotInputs() {
		return potInputs;
	}
}
