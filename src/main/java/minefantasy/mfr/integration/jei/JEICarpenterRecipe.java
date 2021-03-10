package minefantasy.mfr.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import minefantasy.mfr.recipe.ShapedCarpenterRecipes;
import minefantasy.mfr.util.GuiHelper;
import minefantasy.mfr.util.RecipeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a JEI "recipe" for the carpenter bench.
 */
public class JEICarpenterRecipe implements IRecipeWrapper {

	private final ItemStack result;
	private final ShapedCarpenterRecipes recipe;

	private final List<List<ItemStack>> ingredients;

	public JEICarpenterRecipe(ShapedCarpenterRecipes recipe) {
		this.recipe = recipe;
		this.result = recipe.getRecipeOutput();
		this.ingredients = new ArrayList<>();

		// JEI requires empty stacks in the missing slots, and our recipes are shrinked by default so we must expand them first to the full grid size
		List<ItemStack> expandedList = RecipeHelper.expandPattern(Arrays.asList(recipe.recipeItems), recipe.recipeWidth, recipe.recipeHeight, 4, 4);
		for (int i = 0; i < 16; i++) {
			this.ingredients.add(Collections.singletonList(expandedList.get(i)));
		}
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, this.ingredients);
		ingredients.setOutput(VanillaTypes.ITEM, result);
	}

	/**
	 * TODO?: Draw carpenter and tool tier requirements, craft time requirements and anything else we need
	 */
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

		// draw tool icon with required tier int
		GuiHelper.renderToolIcon(minecraft.currentScreen, recipe.toolType, recipe.recipeHammer, recipeWidth - 23, recipeHeight - 98, true);

		// draw bench icon with required tier int
		GuiHelper.renderToolIcon(minecraft.currentScreen, "carpenter", recipe.blockTier, recipeWidth - 23, recipeHeight - 48, true);

		//		minecraft.fontRenderer.drawString("X:" + mouseX + ", Y: " + mouseY, mouseX, mouseY, 16777215);

		if (isPointInRegion(recipeWidth - 23, recipeHeight - 98, 20, 20, mouseX, mouseY, 0, 0)) {
			// Shows the tool tooltip text with the name of the tool and the minimum tier
			String s2 = I18n.format("tooltype." + recipe.toolType) + ", " + (recipe.recipeHammer > -1
					? I18n.format("attribute.mfcrafttier.name") + " " + recipe.recipeHammer
					: I18n.format("attribute.nomfcrafttier.name"));
			minecraft.fontRenderer.drawStringWithShadow(s2, (float) ((recipeWidth / 2) - minecraft.fontRenderer.getStringWidth(s2) / 2), (float) 84, 16777215);
		} else if (isPointInRegion(recipeWidth + -23, recipeHeight - 48, 20, 20, mouseX, mouseY, 0, 0)) {
			// Shows the carpenter tooltip text with the minimum carpenter tier
			String s2 = I18n.format("tooltype.carpenter") + ", " + (recipe.blockTier > -1
					? I18n.format("attribute.mfcrafttier.name") + " " + recipe.blockTier
					: I18n.format("attribute.nomfcrafttier.name"));
			minecraft.fontRenderer.drawStringWithShadow(s2, (float) ((recipeWidth / 2) - minecraft.fontRenderer.getStringWidth(s2) / 2), (float) 84, 16777215);
		} else {
			// Just display the required Skill type of for this recipe
			minecraft.fontRenderer.drawStringWithShadow(recipe.skillUsed.getDisplayName(), (float) ((recipeWidth / 2) - minecraft.fontRenderer.getStringWidth(recipe.skillUsed.getDisplayName()) / 2), (float) 84, 16777215);
		}
	}

	/**
	 * TODO?: This could be used to show the tier and time requirement tooltips, if any
	 */
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return null;
	}

	/**
	 * TODO?: This could be used to open the page in the recipe book where we have this recipe
	 */
	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}

	/**
	 * Test if the 2D point is in a rectangle (relative to the GUI). Args : rectX, rectY, rectWidth, rectHeight, pointX,
	 * pointY
	 */
	protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY, int guiLeft, int guiTop) {
		int i = guiLeft;
		int j = guiTop;
		pointX = pointX - i;
		pointY = pointY - j;
		return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
	}

}
