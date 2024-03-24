package minefantasy.mfr.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.util.Translator;
import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.recipe.AnvilRecipeBase;
import minefantasy.mfr.recipe.CraftingManagerAnvil;
import minefantasy.mfr.recipe.SpecialRecipeBase;
import minefantasy.mfr.util.GuiHelper;
import minefantasy.mfr.util.RecipeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a JEI "recipe" for the Special Recipe in the MFR anvil.
 */
public class JEISpecialRecipe implements IRecipeWrapper {

	private ItemStack result;
	protected SpecialRecipeBase recipe;
	protected AnvilRecipeBase anvilRecipe;
	private List<List<ItemStack>> ingredients;

	public JEISpecialRecipe(SpecialRecipeBase recipe, IStackHelper stackHelper) {
		AnvilRecipeBase anvilRecipe = CraftingManagerAnvil.findRecipeByOutput(recipe.getInput());
		if (anvilRecipe != null) {
			this.anvilRecipe = anvilRecipe;
			List<List<ItemStack>> ingredients = stackHelper.expandRecipeItemStackInputs(RecipeHelper.expandPattern(
					anvilRecipe.getIngredients(),
					anvilRecipe.getWidth(), anvilRecipe.getHeight(),
					AnvilRecipeBase.MAX_WIDTH, AnvilRecipeBase.MAX_HEIGHT));
			this.recipe = recipe;
			this.result = recipe.getOutput();
			this.ingredients = ingredients;
		}
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, this.ingredients);
		ingredients.setOutput(VanillaTypes.ITEM, result);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		AnvilRecipeBase recipe = this.anvilRecipe;

		if (minecraft.currentScreen != null) {
			// add hot output icon
			if (recipe.isHotOutput()) {
				GuiHelper.drawHotItemIcon(minecraft,157, 28);
			}

			// add hot input icons
			Map<Integer, Map<Integer, Integer>> ingredientMap = new HashMap<>();

			int width = AnvilRecipeBase.MAX_WIDTH;
			int height = AnvilRecipeBase.MAX_HEIGHT;
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					int slot = y * width + x;
					Map<Integer, Integer> posMap = new HashMap<>();
					// x and y pos of the current ingredient
					posMap.put(1 + x * 18, 1 + y * 18);
					ingredientMap.put(slot, posMap);
				}
			}

			for (int j = 0; j < ingredients.size(); j++) {
				List<ItemStack> stacks = ingredients.get(j);
				if (!stacks.isEmpty()) {
					ItemStack stack = (stacks.get(0));
					if (stack != null && !stack.isEmpty() && Heatable.canHeatItem(ingredients.get(j).get(0))) {
						GuiHelper.drawHotItemIcon(minecraft, ingredientMap.get(j).keySet().iterator().next(), ingredientMap.get(j).values().iterator().next());
					}
				}
			}
		}

		// draw tool icon with required tier int
		GuiHelper.renderToolIcon(minecraft.currentScreen, recipe.getToolType().getName(), recipe.getToolTier(), recipeWidth - 23, recipeHeight - 98, true, true);

		// draw bench icon with required tier int
		GuiHelper.renderToolIcon(minecraft.currentScreen, "anvil", recipe.getAnvilTier(), recipeWidth - 23, recipeHeight - 48, true, true);

		//		minecraft.fontRenderer.drawString("X:" + mouseX + ", Y: " + mouseY, mouseX, mouseY, 16777215);

		if (GuiHelper.isPointInRegion(recipeWidth - 23, recipeHeight - 98, 20, 20, mouseX, mouseY, 0, 0)) {
			// Shows the tool tooltip text with the name of the tool and the minimum tier
			String s2 = I18n.format("tooltype." + recipe.getToolType()) + ", " + (recipe.getToolTier() > -1
					? I18n.format("attribute.mfcrafttier.name") + " " + recipe.getToolTier()
					: I18n.format("attribute.nomfcrafttier.name"));
			minecraft.fontRenderer.drawStringWithShadow(s2, (float) ((recipeWidth / 2) - minecraft.fontRenderer.getStringWidth(s2) / 2), (float) 84, 16777215);
		} else if (GuiHelper.isPointInRegion(recipeWidth - 23, recipeHeight - 48, 20, 20, mouseX, mouseY, 0, 0)) {
			// Shows the anvil tooltip text with the minimum anvil tier
			String s2 = I18n.format("tooltype.anvil") + ", " + (recipe.getAnvilTier() > -1
					? I18n.format("attribute.mfcrafttier.name") + " " + recipe.getAnvilTier()
					: I18n.format("attribute.nomfcrafttier.name"));
			minecraft.fontRenderer.drawStringWithShadow(s2, (float) ((recipeWidth / 2) - minecraft.fontRenderer.getStringWidth(s2) / 2), (float) 84, 16777215);
		} else {
			// Just display the required Skill type of for this recipe
			minecraft.fontRenderer.drawStringWithShadow(recipe.getSkill().getDisplayName(), (float) ((recipeWidth / 2) - minecraft.fontRenderer.getStringWidth(recipe.getSkill().getDisplayName()) / 2), (float) 84, 16777215);
		}
		if (recipe.isTierModifyOutputCount()) {
			minecraft.fontRenderer.drawStringWithShadow(
					Translator.translateToLocal("recipe.tier.modify.output.desc"),
					(float) (-4), (float) -16, 16777215);
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
