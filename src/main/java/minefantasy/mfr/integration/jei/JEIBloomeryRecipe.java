package minefantasy.mfr.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import minefantasy.mfr.recipe.BloomeryRecipeBase;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.List;

public class JEIBloomeryRecipe implements IRecipeWrapper {
	private final ItemStack result;
	protected final BloomeryRecipeBase recipe;
	private final List<List<ItemStack>> ingredients;
	private final List<ItemStack> fuelItemStacks;

	public JEIBloomeryRecipe(BloomeryRecipeBase recipe, IStackHelper stackHelper, List<ItemStack> fuelItemStacks) {
		this.result = recipe.getBloomeryRecipeOutput();
		this.recipe = recipe;
		this.ingredients = stackHelper.expandRecipeItemStackInputs(recipe.getInputs());
		this.fuelItemStacks = fuelItemStacks;
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

	public List<ItemStack> getFuelItemStacks() {
		return fuelItemStacks;
	}
}
