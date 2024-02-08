package minefantasy.mfr.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.config.Constants;
import mezz.jei.gui.GuiHelper;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.recipe.AlloyRatioRecipe;
import minefantasy.mfr.recipe.AlloyRecipeBase;
import minefantasy.mfr.recipe.CraftingManagerAlloy;
import minefantasy.mfr.util.RecipeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JEIAlloyRecipeCategory implements IRecipeCategory<JEIAlloyRecipe> {
	static final String UID = "minefantasyreforged:crucible";

	static final ResourceLocation TEXTURE = new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/integration/jei/crucible_background.png");
	private final IDrawable icon;

	static final int WIDTH = 165;
	static final int HEIGHT = 56;

	private final IDrawable background;
	protected final IDrawableStatic staticFlame;
	protected final IDrawableAnimated animatedFlame;
	protected final IDrawableAnimated arrow;

	public JEIAlloyRecipeCategory(IRecipeCategoryRegistration registry, GuiHelper guiHelper) {
		IGuiHelper iGuiHelper = registry.getJeiHelpers().getGuiHelper();
		background = iGuiHelper.createDrawable(TEXTURE, 0, 0, WIDTH, HEIGHT);
		icon = iGuiHelper.createDrawableIngredient(new ItemStack(MineFantasyBlocks.CRUCIBLE_STONE));
		staticFlame = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 82, 114, 14, 14);
		animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);

		arrow = guiHelper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17)
				.buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		animatedFlame.draw(minecraft, 66, 37);
		arrow.draw(minecraft, 59, 20);
	}

	/**
	 * Set the {@link IRecipeLayout} properties from the {@link IRecipeWrapper} and {@link IIngredients}.
	 *
	 * @param recipeLayout  the layout that needs its properties set.
	 * @param recipeWrapper the recipeWrapper, for extra information.
	 * @param ingredients   the ingredients, already set by the recipeWrapper
	 * @since JEI 3.11.0
	 */
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, JEIAlloyRecipe recipeWrapper, IIngredients ingredients) {

		// Okay, they're not technically *slots* but to all intents and purposes, that's how they behave
		IGuiItemStackGroup slots = recipeLayout.getItemStacks();

		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		List<List<ItemStack>> outputList = ingredients.getOutputs(VanillaTypes.ITEM);

		// Init ingredient slots, 3x3 grid
		if (recipeWrapper.recipe instanceof AlloyRatioRecipe) {
			for (int x = 0; x < AlloyRecipeBase.MAX_WIDTH; x++) {
				for (int y = 0; y < AlloyRecipeBase.MAX_HEIGHT; y++) {
					int slot = y * AlloyRecipeBase.MAX_WIDTH + x;
					slots.init(slot, true, 1 + x * 18, 1 + (2 - y) * 18);
				}
			}
		}
		else {
			for (int x = 0; x < AlloyRecipeBase.MAX_WIDTH; x++) {
				for (int y = 0; y < AlloyRecipeBase.MAX_HEIGHT; y++) {
					int slot = y * AlloyRecipeBase.MAX_WIDTH + x;
					slots.init(slot, true, 1 + x * 18, 1 + y * 18);
				}
			}
		}

		// Init output slot
		slots.init(9, false, 93, 18);

		slots.set(ingredients);

		// Assign ingredients to slots
		for (int j = 0; j < inputs.size(); j++) {
			slots.set(j, inputs.get(j));
		}
		// Assign outputs to slot
		for (List<ItemStack> stacks : outputList) {
			slots.set(9, stacks);
		}
	}

	/**
	 * Generates all the MFR Alloy recipes for JEI.
	 */
	public static Collection<JEIAlloyRecipe> generateRecipes(IStackHelper stackHelper) {
		return new ArrayList<>(generateAlloyRecipes(stackHelper));
	}

	public static Collection<JEIAlloyRecipe> generateAlloyRecipes(IStackHelper stackHelper) {

		List<JEIAlloyRecipe> recipes = new ArrayList<>();
		Collection<AlloyRecipeBase> alloyRecipes = CraftingManagerAlloy.getRecipes();

		for (AlloyRecipeBase alloyRecipe : alloyRecipes) {

			if (alloyRecipe instanceof AlloyRatioRecipe) {
				for (int currentRepeatAmount = 1; currentRepeatAmount <= ((AlloyRatioRecipe) alloyRecipe).getRepeatAmount(); currentRepeatAmount++) {
					List<List<ItemStack>> ingredients = stackHelper.expandRecipeItemStackInputs(RecipeHelper
							.expandPattern(RecipeHelper
									.duplicateList(alloyRecipe.getInputs(), currentRepeatAmount),
									alloyRecipe.getWidth(), alloyRecipe.getHeight(),
									AlloyRecipeBase.MAX_WIDTH, AlloyRecipeBase.MAX_HEIGHT));
					ItemStack outputCopy = alloyRecipe.getAlloyRecipeOutput().copy();
					outputCopy.setCount(currentRepeatAmount);
					recipes.add(new JEIAlloyRatioRecipe((AlloyRatioRecipe) alloyRecipe, ingredients, outputCopy, stackHelper));
				}

			}
			else {
				recipes.add(new JEIAlloyRecipe(alloyRecipe, stackHelper));
			}
		}

		return recipes;

	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return I18n.format("integration.jei.category." + UID);
	}

	@Override
	public String getModName() {
		return MineFantasyReforged.NAME;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Nullable
	@Override
	public IDrawable getIcon() {
		return icon;
	}
}
