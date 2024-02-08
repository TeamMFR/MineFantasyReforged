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
import minefantasy.mfr.recipe.CraftingManagerSalvage;
import minefantasy.mfr.recipe.SalvageRecipeBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * JEI recipe category implementation for all "recipes" in the Salvage bench.
 */
public class JEISalvageRecipeCategory implements IRecipeCategory<JEISalvageRecipe> {

	static final String UID = "minefantasyreforged:salvage";

	static final ResourceLocation TEXTURE = new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/integration/jei/salvage_background.png");
	private final IDrawable icon;

	static final int WIDTH = 149;
	static final int HEIGHT = 93;

	private final IDrawable background;

	public JEISalvageRecipeCategory(IRecipeCategoryRegistration registry) {
		IGuiHelper iGuiHelper = registry.getJeiHelpers().getGuiHelper();
		background = iGuiHelper.createDrawable(TEXTURE, 0, 0, WIDTH, HEIGHT);
		icon = iGuiHelper.createDrawableIngredient(new ItemStack(MineFantasyBlocks.SALVAGE_BASIC));
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
	public void setRecipe(IRecipeLayout recipeLayout, JEISalvageRecipe recipeWrapper, IIngredients ingredients) {

		// Okay, they're not technically *slots* but to all intents and purposes, that's how they behave
		IGuiItemStackGroup slots = recipeLayout.getItemStacks();

		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);

		// Init ingredient slot
		slots.init(26, true, 0, 37);

		// Init output slots
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				int slot = y * 5 + x;
				slots.init(slot, false, 59 + x * 18, 1 + y * 18);
			}
		}

		// Assign ingredients to slot
		slots.set(26, inputs.get(0));

		// Assign outputs to slots
		for (int j = 0; j < outputs.size(); j++) {
			slots.set(j, outputs.get(j));
		}
	}

	/**
	 * Generates all the MFR carpenter recipes for JEI.
	 */
	public static Collection<JEISalvageRecipe> generateRecipes(IStackHelper stackHelper) {
		return new ArrayList<>(generateSalvageRecipes(stackHelper));
	}

	@Nullable
	@Override
	public IDrawable getIcon() {
		return icon;
	}

	private static Collection<JEISalvageRecipe> generateSalvageRecipes(IStackHelper stackHelper) {

		List<JEISalvageRecipe> recipes = new ArrayList<>();
		Collection<SalvageRecipeBase> salvageRecipes = CraftingManagerSalvage.getRecipes();

		for (SalvageRecipeBase salvageRecipe : salvageRecipes) {
			recipes.add(new JEISalvageRecipe(salvageRecipe, stackHelper));
		}

		return recipes;

	}
}
