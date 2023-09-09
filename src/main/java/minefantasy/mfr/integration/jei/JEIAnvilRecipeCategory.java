package minefantasy.mfr.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.recipe.AnvilDynamicRecipe;
import minefantasy.mfr.recipe.AnvilRecipeBase;
import minefantasy.mfr.recipe.CraftingManagerAnvil;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * JEI recipe category implementation for all "recipes" of the MFR anvil.
 */
public class JEIAnvilRecipeCategory implements IRecipeCategory<JEIAnvilRecipe> {

	static final String UID = "minefantasyreforged:anvil";

	static final ResourceLocation TEXTURE = new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/integration/jei/anvil_background.png");
	private final IDrawable icon;

	static final int WIDTH = 165;
	static final int HEIGHT = 100;

	private final IDrawable background;

	public JEIAnvilRecipeCategory(IRecipeCategoryRegistration registry) {
		IGuiHelper iGuiHelper = registry.getJeiHelpers().getGuiHelper();
		background = iGuiHelper.createDrawable(TEXTURE, 0, 0, WIDTH, HEIGHT);
		icon = iGuiHelper.createDrawableIngredient(new ItemStack(MineFantasyBlocks.ANVIL_IRON));
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

	/**
	 * Set the {@link IRecipeLayout} properties from the {@link IRecipeWrapper} and {@link IIngredients}.
	 *
	 * @param recipeLayout  the layout that needs its properties set.
	 * @param recipeWrapper the recipeWrapper, for extra information.
	 * @param ingredients   the ingredients, already set by the recipeWrapper
	 * @since JEI 3.11.0
	 */
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, JEIAnvilRecipe recipeWrapper, IIngredients ingredients) {

		// Okay, they're not technically *slots* but to all intents and purposes, that's how they behave
		IGuiItemStackGroup slots = recipeLayout.getItemStacks();

		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		List<List<ItemStack>> outputList = ingredients.getOutputs(VanillaTypes.ITEM);

		// Init ingredient slots, 5x4 grid

		for (int x = 0; x < AnvilRecipeBase.MAX_WIDTH; x++) {
			for (int y = 0; y < AnvilRecipeBase.MAX_HEIGHT; y++) {
				int slot = y * AnvilRecipeBase.MAX_WIDTH + x;
				slots.init(slot, true, 1 + x * 18, 1 + y * 18);
			}
		}

		// Init output slot
		slots.init(24, false, 143, 28);

		// Assign ingredients to slots
		for (int j = 0; j < inputs.size(); j++) {
			slots.set(j, inputs.get(j));
		}
		// Assign outputs to slot
		for (List<ItemStack> stacks : outputList) {
			slots.set(24, stacks);
		}
	}

	/**
	 * Generates all the MFR anvil recipes for JEI.
	 */
	public static Collection<JEIAnvilRecipe> generateRecipes(IStackHelper stackHelper) {
		return new ArrayList<>(generateAnvilRecipes(stackHelper));
	}

	@Nullable
	@Override
	public IDrawable getIcon() {
		return icon;
	}

	public static Collection<JEIAnvilRecipe> generateAnvilRecipes(IStackHelper stackHelper) {

		List<JEIAnvilRecipe> recipes = new ArrayList<>();
		Collection<AnvilRecipeBase> anvilRecipes = CraftingManagerAnvil.getRecipes();

		for (AnvilRecipeBase anvilRecipe : anvilRecipes) {

			if (anvilRecipe instanceof AnvilDynamicRecipe) {
				recipes.add(new JEIAnvilDynamicRecipe((AnvilDynamicRecipe) anvilRecipe, stackHelper));
			}
			else {
				recipes.add(new JEIAnvilRecipe(anvilRecipe, stackHelper));
			}
		}

		return recipes;

	}
}
