package minefantasy.mfr.integration.jei;

import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.startup.StackHelper;
import minefantasy.mfr.api.crafting.IMaterialComponent;
import minefantasy.mfr.registry.recipe.SalvageRecipeBase;
import minefantasy.mfr.registry.recipe.SalvageRecipeShared;
import minefantasy.mfr.registry.recipe.ingredients.IngredientMaterial;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a JEI "recipe" for the salvage bench.
 */
public class JEISalvageRecipe implements IRecipeWrapper {

	private final List<List<ItemStack>> inputs;

	private final List<List<ItemStack>> outputs;

	private final SalvageRecipeBase recipe;

	public JEISalvageRecipe(SalvageRecipeBase recipe, StackHelper stackHelper) {
		List<ItemStack> inputs;
		if (!recipe.getInput().hasTagCompound()) {
			if (recipe instanceof SalvageRecipeShared) {
				if (recipe.getInput().getItem() instanceof IMaterialComponent) {
					List<List<ItemStack>> allItemVariants = new ArrayList<>();
					allItemVariants.add(CustomToolHelper.constructAllVariants(recipe.getInput()));
					((SalvageRecipeShared) recipe).getShared()
							.forEach(stack -> allItemVariants.add(CustomToolHelper.constructAllVariants(stack)));
					inputs = new ArrayList<>();
					for (int i = 0; i < Utils.findSmallestListSize(allItemVariants.stream()); i ++) {
						int localIndex = i;
						allItemVariants.forEach(stacks -> inputs.add(stacks.get(localIndex)));
					}
				}
				else {
					inputs = Lists.asList(recipe.getInput(), ((SalvageRecipeShared) recipe).getShared().toArray(new ItemStack[0]));
				}
			}
			else {
				if (recipe.getInput().getItem() instanceof IMaterialComponent) {
					inputs = CustomToolHelper.constructAllVariants(recipe.getInput());
				}
				else {
					inputs = Collections.singletonList(recipe.getInput());
				}
			}
		}
		else {
			inputs = Collections.singletonList(recipe.getInput());
		}

		List<List<ItemStack>> outputs = new ArrayList<>();
		for (Ingredient outputIngredient : recipe.getOutputs()) {
			if (outputIngredient instanceof IngredientMaterial) {
				IngredientMaterial outputIngredientMaterial = (IngredientMaterial) outputIngredient;
				outputs.add(CustomToolHelper.constructAllVariants(outputIngredientMaterial.getMatchingStacks()[0]));
			}
			else {
				outputs.add(stackHelper.toItemStackList(outputIngredient, true));
			}
		}

		this.recipe = recipe;
		this.inputs = Collections.singletonList(inputs);
		this.outputs = outputs;
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

	public SalvageRecipeBase getRecipe() {
		return recipe;
	}
}
