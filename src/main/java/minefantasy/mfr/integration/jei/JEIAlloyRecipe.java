package minefantasy.mfr.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import minefantasy.mfr.client.knowledge.EntryPageCrucible;
import minefantasy.mfr.recipe.AlloyRecipeBase;
import minefantasy.mfr.util.RecipeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.List;

public class JEIAlloyRecipe implements IRecipeWrapper {
	private final ItemStack result;
	protected final AlloyRecipeBase recipe;
	private final List<List<ItemStack>> ingredients;

	public JEIAlloyRecipe(AlloyRecipeBase recipe, IStackHelper stackHelper) {
		List<List<ItemStack>> ingredients = stackHelper.expandRecipeItemStackInputs(RecipeHelper.expandPattern(
				recipe.getInputs(),
				recipe.getWidth(), recipe.getHeight(),
				AlloyRecipeBase.MAX_WIDTH, AlloyRecipeBase.MAX_HEIGHT));
		this.recipe = recipe;
		this.result = recipe.getAlloyRecipeOutput();
		this.ingredients = ingredients;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, this.ingredients);
		ingredients.setOutput(VanillaTypes.ITEM, result);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

		// Just display the required Crucible Tier of for this recipe
		String crucibleTier = EntryPageCrucible.getRequiredCrucibleName(recipe).replaceAll("[<>]", "");
		minecraft.fontRenderer.drawStringWithShadow(crucibleTier, (float) ((recipeWidth - 53) - minecraft.fontRenderer.getStringWidth(crucibleTier) / 2), (float) 1, 16777215);
	}

	/**
	 * TODO?: This could be used to open the page in the recipe book where we have this recipe
	 */
	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}
}
