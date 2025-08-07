package minefantasy.mfr.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IStackHelper;
import minefantasy.mfr.registry.recipe.AlloyRatioRecipe;
import minefantasy.mfr.registry.recipe.AlloyRecipeBase;
import minefantasy.mfr.util.RecipeHelper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JEIAlloyRatioRecipe extends JEIAlloyRecipe {
	private final List<List<ItemStack>> results;
	private final List<List<ItemStack>> ingredients;
	protected final AlloyRecipeBase recipe;

	public JEIAlloyRatioRecipe(AlloyRatioRecipe recipe, IStackHelper stackHelper) {
		super(recipe, stackHelper);
		this.recipe = recipe;

		List<List<ItemStack>> inputs = stackHelper
				.expandRecipeItemStackInputs(RecipeHelper
						.duplicateList(recipe.getInputs(), recipe.getRepeatAmount()))
				.stream()
				.map(RecipeHelper::convertNonNullList)
				.collect(Collectors.toList());

		generateRatioIngredients(recipe, getStackCounts(recipe), inputs);

		this.ingredients = inputs;
		this.results = Collections.singletonList(getOutputModifiedCounts(recipe));
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, this.ingredients);
		ingredients.setOutputLists(VanillaTypes.ITEM, this.results);
	}

	private static void generateRatioIngredients(
			AlloyRatioRecipe recipe, List<Integer> stackCounts, List<List<ItemStack>> inputs) {

		for (int i = 0; i < inputs.size(); i++) {
			int stackCount = stackCounts.get(i);
			List<ItemStack> stacks = inputs.get(i);
			stacks.addAll(Collections.nCopies(stackCount, stacks.get(0)));
			if (stacks.size() < recipe.getRepeatAmount()) {
				int missing = recipe.getRepeatAmount() - stacks.size();
				stacks.addAll(Collections.nCopies(missing, null));
			}
		}
	}

	private static List<Integer> getStackCounts(AlloyRatioRecipe recipe) {
		List<Integer> stackCounts = new ArrayList<>();
		for (int i = recipe.getInputs().size() * recipe.getRepeatAmount() - 1; i >= 0; i--) {
			int stackCount = (i / recipe.getInputs().size());
			stackCounts.add(stackCount);
		}
		return stackCounts;
	}

	private static List<ItemStack> getOutputModifiedCounts(AlloyRatioRecipe recipe) {
		List<ItemStack> results = new ArrayList<>();
		for (int currentRepeatAmount = recipe.getRepeatAmount(); currentRepeatAmount > 0; currentRepeatAmount--) {
			ItemStack outputCopy = recipe.getAlloyRecipeOutput().copy();
			outputCopy.setCount(outputCopy.getCount() * currentRepeatAmount);
			results.add(outputCopy);
		}
		return results;
	}
}
