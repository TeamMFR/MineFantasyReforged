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
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.recipe.AnvilRecipeBase;
import minefantasy.mfr.recipe.CraftingManagerSpecial;
import minefantasy.mfr.recipe.SpecialRecipeBase;
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
public class JEISpecialRecipeCategory implements IRecipeCategory<JEISpecialRecipe> {

	static final String UID = "minefantasyreforged:special";

	static final ResourceLocation TEXTURE = new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/integration/jei/anvil_special_background.png");
	private final IDrawable icon;

	static final int WIDTH = 179;
	static final int HEIGHT = 100;

	private final IDrawable background;

	public JEISpecialRecipeCategory(IRecipeCategoryRegistration registry) {
		IGuiHelper iGuiHelper = registry.getJeiHelpers().getGuiHelper();
		background = iGuiHelper.createDrawable(TEXTURE, 0, 0, WIDTH, HEIGHT);
		icon = iGuiHelper.createDrawableIngredient(new ItemStack(MineFantasyItems.ORNATE_ITEMS));
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
	public void setRecipe(IRecipeLayout recipeLayout, JEISpecialRecipe recipeWrapper, IIngredients ingredients) {

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
		slots.init(24, false, 157, 28);

		// Init Special Slot
		slots.init(25, false, 111, 28);

		// Assign ingredients to slots
		for (int j = 0; j < inputs.size(); j++) {
			slots.set(j, inputs.get(j));
		}
		// Assign outputs to slot
		for (List<ItemStack> stacks : outputList) {
			slots.set(24, stacks);
		}

		//Assign special to slot
		for (ItemStack specialStack : recipeWrapper.recipe.getSpecialInput().getMatchingStacks()) {
			slots.set(25, specialStack);
		}
	}

	/**
	 * Generates all the MFR anvil special recipes for JEI.
	 */
	public static Collection<JEISpecialRecipe> generateRecipes(IStackHelper stackHelper) {
		return new ArrayList<>(generateSpecialRecipes(stackHelper));
	}

	@Nullable
	@Override
	public IDrawable getIcon() {
		return icon;
	}

	public static Collection<JEISpecialRecipe> generateSpecialRecipes(IStackHelper stackHelper) {

		List<JEISpecialRecipe> recipes = new ArrayList<>();
		Collection<SpecialRecipeBase> specialRecipes = CraftingManagerSpecial.getRecipes();

		for (SpecialRecipeBase specialRecipe : specialRecipes) {
			recipes.add(new JEISpecialRecipe(specialRecipe, stackHelper));
		}

		return recipes;

	}
}
