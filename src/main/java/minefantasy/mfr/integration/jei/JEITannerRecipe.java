package minefantasy.mfr.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import minefantasy.mfr.recipe.TannerRecipeBase;
import minefantasy.mfr.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.List;

public class JEITannerRecipe implements IRecipeWrapper {
	private final ItemStack result;
	protected final TannerRecipeBase recipe;
	private final List<List<ItemStack>> ingredients;

	public JEITannerRecipe(TannerRecipeBase recipe, IStackHelper stackHelper) {
		this.result = recipe.getTannerRecipeOutput();
		this.recipe = recipe;
		this.ingredients = stackHelper.expandRecipeItemStackInputs(recipe.getInputs());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, this.ingredients);
		ingredients.setOutput(VanillaTypes.ITEM, result);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		// draw tool icon with required tier int
		GuiHelper.renderToolIcon(minecraft.currentScreen, recipe.getToolType().getName(), recipe.getTannerTier(), recipeWidth - 20, recipeHeight - 20, true, true);

		// draw bench icon with required tier int
		GuiHelper.renderToolIcon(minecraft.currentScreen, "tanner", recipe.getTannerTier(), recipeWidth - 65, recipeHeight - 20, true, true);

		if (GuiHelper.isPointInRegion(recipeWidth - 20, recipeHeight - 20, 20, 20, mouseX, mouseY, 0, 0)) {
			// Shows the tool tooltip text with the name of the tool and the minimum tier
			String s2 = I18n.format("tooltype." + recipe.getToolType().getName()) + ", " + (recipe.getTannerTier() > -1
					? I18n.format("attribute.mfcrafttier.name") + " " + recipe.getTannerTier()
					: I18n.format("attribute.nomfcrafttier.name"));
			minecraft.fontRenderer.drawStringWithShadow(s2, (float) ((recipeWidth / 2) - minecraft.fontRenderer.getStringWidth(s2) / 2), (float) 50, 16777215);
		}
		else if (GuiHelper.isPointInRegion(recipeWidth - 65, recipeHeight - 20, 20, 20, mouseX, mouseY, 0, 0)) {
			// Shows the carpenter tooltip text with the minimum carpenter tier
			String s2 = I18n.format("tooltype.tanner") + ", " + (recipe.getTannerTier() > -1
					? I18n.format("attribute.mfcrafttier.name") + " " + recipe.getTannerTier()
					: I18n.format("attribute.nomfcrafttier.name"));
			minecraft.fontRenderer.drawStringWithShadow(s2, (float) ((recipeWidth / 2) - minecraft.fontRenderer.getStringWidth(s2) / 2), (float) 50, 16777215);
		}
	}

	/**
	 * TODO?: This could be used to open the page in the recipe book where we have this recipe
	 */
	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}
}
