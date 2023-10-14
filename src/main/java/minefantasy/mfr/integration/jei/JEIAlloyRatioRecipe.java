package minefantasy.mfr.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IStackHelper;
import minefantasy.mfr.recipe.AlloyRatioRecipe;
import minefantasy.mfr.recipe.AlloyRecipeBase;
import net.minecraft.item.ItemStack;

import java.util.List;

public class JEIAlloyRatioRecipe extends JEIAlloyRecipe {
	private final ItemStack result;
	protected final AlloyRecipeBase recipe;
	private final List<List<ItemStack>> ingredients;

	public JEIAlloyRatioRecipe(AlloyRatioRecipe recipe, List<List<ItemStack>> ingredients,
			ItemStack output, IStackHelper stackHelper) {
		super(recipe, stackHelper);
		this.recipe = recipe;
		this.result = output;
		this.ingredients = ingredients;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, this.ingredients);
		ingredients.setOutput(VanillaTypes.ITEM, this.result);
	}
}
