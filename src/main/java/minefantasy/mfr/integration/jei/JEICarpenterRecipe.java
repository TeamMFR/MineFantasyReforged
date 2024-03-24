package minefantasy.mfr.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import minefantasy.mfr.recipe.CarpenterRecipeBase;
import minefantasy.mfr.util.GuiHelper;
import minefantasy.mfr.util.RecipeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Represents a JEI "recipe" for the carpenter bench.
 */
public class JEICarpenterRecipe implements IRecipeWrapper {

	private final ItemStack result;
	private final CarpenterRecipeBase recipe;

	private final List<List<ItemStack>> ingredients;

	public JEICarpenterRecipe(CarpenterRecipeBase recipe, IStackHelper stackHelper) {
		// JEI requires empty stacks in the missing slots, and our recipes are shrinked by default so we must expand them first to the full grid size
		List<List<ItemStack>> ingredients = stackHelper.expandRecipeItemStackInputs(RecipeHelper.expandPattern(
				recipe.getIngredients(),
				recipe.getWidth(), recipe.getHeight(),
				CarpenterRecipeBase.MAX_WIDTH, CarpenterRecipeBase.MAX_HEIGHT));
		this.recipe = recipe;
		this.result = recipe.getCarpenterRecipeOutput();
		this.ingredients = ingredients;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, this.ingredients);
		ingredients.setOutput(VanillaTypes.ITEM, result);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

		// draw tool icon with required tier int
		GuiHelper.renderToolIcon(minecraft.currentScreen, recipe.getToolType().getName(), recipe.getToolTier(), recipeWidth - 23, recipeHeight - 98, true, true);

		// draw bench icon with required tier int
		GuiHelper.renderToolIcon(minecraft.currentScreen, "carpenter", recipe.getCarpenterTier(), recipeWidth - 23, recipeHeight - 48, true, true);

		//		minecraft.fontRenderer.drawString("X:" + mouseX + ", Y: " + mouseY, mouseX, mouseY, 16777215);

		if (GuiHelper.isPointInRegion(recipeWidth - 23, recipeHeight - 98, 20, 20, mouseX, mouseY, 0, 0)) {
			// Shows the tool tooltip text with the name of the tool and the minimum tier
			String s2 = I18n.format("tooltype." + recipe.getToolType()) + ", " + (recipe.getToolTier() > -1
					? I18n.format("attribute.mfcrafttier.name") + " " + recipe.getToolTier()
					: I18n.format("attribute.nomfcrafttier.name"));
			minecraft.fontRenderer.drawStringWithShadow(s2, (float) ((recipeWidth / 2) - minecraft.fontRenderer.getStringWidth(s2) / 2), (float) 84, 16777215);
		} else if (GuiHelper.isPointInRegion(recipeWidth - 23, recipeHeight - 48, 20, 20, mouseX, mouseY, 0, 0)) {
			// Shows the carpenter tooltip text with the minimum carpenter tier
			String s2 = I18n.format("tooltype.carpenter") + ", " + (recipe.getCarpenterTier() > -1
					? I18n.format("attribute.mfcrafttier.name") + " " + recipe.getCarpenterTier()
					: I18n.format("attribute.nomfcrafttier.name"));
			minecraft.fontRenderer.drawStringWithShadow(s2, (float) ((recipeWidth / 2) - minecraft.fontRenderer.getStringWidth(s2) / 2), (float) 84, 16777215);
		} else {
			// Just display the required Skill type of for this recipe
			minecraft.fontRenderer.drawStringWithShadow(recipe.getSkill().getDisplayName(), (float) ((recipeWidth / 2) - minecraft.fontRenderer.getStringWidth(recipe.getSkill().getDisplayName()) / 2), (float) 84, 16777215);
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
