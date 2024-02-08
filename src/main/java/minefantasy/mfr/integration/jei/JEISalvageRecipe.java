package minefantasy.mfr.integration.jei;

import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import minefantasy.mfr.recipe.SalvageRecipeBase;
import minefantasy.mfr.recipe.SalvageRecipeShared;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

/**
 * Represents a JEI "recipe" for the salvage bench.
 */
public class JEISalvageRecipe implements IRecipeWrapper {

	private final List<List<ItemStack>> inputs;

	private final List<List<ItemStack>> outputs;

	public JEISalvageRecipe(SalvageRecipeBase recipe, IStackHelper stackHelper) {
		List<ItemStack> inputs;
		if (recipe instanceof SalvageRecipeShared) {
			inputs = Lists.asList(recipe.getInput(), ((SalvageRecipeShared) recipe).getShared().toArray(new ItemStack[0]));
		}
		else {
			inputs = Collections.singletonList(recipe.getInput());
		}
		this.inputs = Collections.singletonList(inputs);
		this.outputs = stackHelper.expandRecipeItemStackInputs(recipe.getOutputs());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, this.inputs);
		ingredients.setOutputLists(VanillaTypes.ITEM, outputs);
	}

	/**
	 * TODO?: This could be used to open the page in the recipe book where we have this recipe
	 */
	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}

}
