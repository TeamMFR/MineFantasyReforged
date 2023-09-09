package minefantasy.mfr.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.util.Translator;
import minefantasy.mfr.recipe.AnvilDynamicRecipe;
import minefantasy.mfr.recipe.AnvilRecipeBase;
import minefantasy.mfr.util.RecipeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

/**
 * Represents a JEI "recipe" for the MFR anvil.
 */
public class JEIAnvilDynamicRecipe extends JEIAnvilRecipe {

	private final List<List<ItemStack>> ingredients;
	private final List<List<ItemStack>> outputs;

	public JEIAnvilDynamicRecipe(AnvilDynamicRecipe recipe, IStackHelper stackHelper) {
		super(recipe, stackHelper);

		List<List<ItemStack>> ingredients = stackHelper.expandRecipeItemStackInputs(RecipeHelper.expandPattern(
				recipe.getIngredients(),
				recipe.getWidth(), recipe.getHeight(),
				AnvilRecipeBase.MAX_WIDTH, AnvilRecipeBase.MAX_HEIGHT));
		ItemStack result = recipe.getAnvilRecipeOutput();
		this.outputs = Collections.singletonList(
				AnvilDynamicRecipe.getOutputsFromGridMap(ingredients, recipe.modifyOutput, result));

		this.ingredients = ingredients;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, this.ingredients);
		ingredients.setOutputLists(VanillaTypes.ITEM, this.outputs);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);

		String recipeName = super.recipe.getRegistryName().getPath();

		minecraft.fontRenderer.drawStringWithShadow(
				Translator.translateToLocal("recipe." + recipeName + ".desc"),
				(float) (0), (float) -15, 16777215);
	}
}
