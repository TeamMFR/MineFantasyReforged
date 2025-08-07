package minefantasy.mfr.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.registry.recipe.BlastFurnaceRecipeBase;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JEIBlastFurnaceRecipe implements IRecipeWrapper {
	private final ItemStack result;
	private final BlastFurnaceRecipeBase recipe;
	private final List<List<ItemStack>> ingredients;
	private final List<ItemStack> fuelItemStacks;

	public JEIBlastFurnaceRecipe(BlastFurnaceRecipeBase recipe, IStackHelper stackHelper, List<ItemStack> fuelItemStacks) {
		this.result = recipe.getBlastFurnaceRecipeOutput();
		this.recipe = recipe;
		this.ingredients = stackHelper.expandRecipeItemStackInputs(recipe.getInputs());
		this.fuelItemStacks = fuelItemStacks;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ItemStack coalFlux = new ItemStack(MineFantasyItems.COAL_FLUX);
		List<ItemStack> coalFluxList = new ArrayList<>();
		coalFluxList.add(coalFlux);
		if (!this.ingredients.contains(coalFluxList)) {
			this.ingredients.add(coalFluxList);
		}

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

	public BlastFurnaceRecipeBase getRecipe() {
		return recipe;
	}
}
