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
 * Represents a JEI "recipe" for the carpenter bench.
 */
public class JEISalvageRecipe implements IRecipeWrapper {

	private final List<List<ItemStack>> inputs;
	private final SalvageRecipeBase recipe;

	private final List<List<ItemStack>> outputs;

	public JEISalvageRecipe(SalvageRecipeBase recipe, IStackHelper stackHelper) {
		this.recipe = recipe;
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

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

		//		// draw tool icon with required tier int
//		GuiHelper.renderToolIcon(minecraft.currentScreen, recipe.getToolType(), recipe.getToolTier(), recipeWidth - 23, recipeHeight - 98, true);
//
//		// draw bench icon with required tier int
//		GuiHelper.renderToolIcon(minecraft.currentScreen, "carpenter", recipe.getCarpenterTier(), recipeWidth - 23, recipeHeight - 48, true);
//
//		//		minecraft.fontRenderer.drawString("X:" + mouseX + ", Y: " + mouseY, mouseX, mouseY, 16777215);
//
//		if (GuiHelper.isPointInRegion(recipeWidth - 23, recipeHeight - 98, 20, 20, mouseX, mouseY, 0, 0)) {
//			// Shows the tool tooltip text with the name of the tool and the minimum tier
//			String s2 = I18n.format("tooltype." + recipe.getToolType()) + ", " + (recipe.getToolTier() > -1
//					? I18n.format("attribute.mfcrafttier.name") + " " + recipe.getToolTier()
//					: I18n.format("attribute.nomfcrafttier.name"));
//			minecraft.fontRenderer.drawStringWithShadow(s2, (float) ((recipeWidth / 2) - minecraft.fontRenderer.getStringWidth(s2) / 2), (float) 84, 16777215);
//		} else if (GuiHelper.isPointInRegion(recipeWidth - 23, recipeHeight - 48, 20, 20, mouseX, mouseY, 0, 0)) {
//			// Shows the carpenter tooltip text with the minimum carpenter tier
//			String s2 = I18n.format("tooltype.carpenter") + ", " + (recipe.getCarpenterTier() > -1
//					? I18n.format("attribute.mfcrafttier.name") + " " + recipe.getCarpenterTier()
//					: I18n.format("attribute.nomfcrafttier.name"));
//			minecraft.fontRenderer.drawStringWithShadow(s2, (float) ((recipeWidth / 2) - minecraft.fontRenderer.getStringWidth(s2) / 2), (float) 84, 16777215);
//		} else {
//			// Just display the required Skill type of for this recipe
//			minecraft.fontRenderer.drawStringWithShadow(recipe.getSkill().getDisplayName(), (float) ((recipeWidth / 2) - minecraft.fontRenderer.getStringWidth(recipe.getSkill().getDisplayName()) / 2), (float) 84, 16777215);
//		}
	}

	/**
	 * TODO?: This could be used to open the page in the recipe book where we have this recipe
	 */
	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}

}
