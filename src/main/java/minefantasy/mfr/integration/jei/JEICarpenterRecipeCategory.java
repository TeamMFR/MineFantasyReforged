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
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.recipe.CraftingManagerCarpenter;
import minefantasy.mfr.recipe.ShapedCarpenterRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * JEI recipe category implementation for all "recipes" in the carpenter bench.
 */
public class JEICarpenterRecipeCategory implements IRecipeCategory<JEICarpenterRecipe> {

	static final String UID = "minefantasyreborn:carpenter";

	static final ResourceLocation TEXTURE = new ResourceLocation(MineFantasyReborn.MOD_ID, "textures/integration/jei/carpenter_background.png");
	private final IDrawable icon;

	static final int WIDTH = 134;
	static final int HEIGHT = 74;
	// Annoyingly, these seem to be for 18x18 slots rather than the actual highlighted area, unlike container slots
	static final int OUTPUT_SLOT_X = 112;
	static final int OUTPUT_SLOT_Y = 28;

	private final IDrawable background;

	public JEICarpenterRecipeCategory(IRecipeCategoryRegistration registry) {
		IGuiHelper iGuiHelper = registry.getJeiHelpers().getGuiHelper();
		background = iGuiHelper.createDrawable(TEXTURE, 0, 0, WIDTH, HEIGHT);
		icon = iGuiHelper.createDrawableIngredient(new ItemStack(MineFantasyBlocks.CARPENTER));
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
		return MineFantasyReborn.NAME;
	}

	/**
	 * Draw any extra elements that might be necessary, icons or extra slots.
	 *
	 * @see IDrawable for a simple class for drawing things.
	 * @see IGuiHelper for useful functions.
	 */
	@Override
	public void drawExtras(Minecraft minecraft) {
		/** see also {@link IRecipeWrapper#drawInfo(net.minecraft.client.Minecraft, int, int, int, int)}*/
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
	public void setRecipe(IRecipeLayout recipeLayout, JEICarpenterRecipe recipeWrapper, IIngredients ingredients) {

		// Okay, they're not technically *slots* but to all intents and purposes, that's how they behave
		IGuiItemStackGroup slots = recipeLayout.getItemStacks();

		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);

		// TODO: finish implementation!
		// Slot initialisation
		int i = 0;

		while (i < 15) {
			slots.init(i++, true, i * 15, 0);
		}
		slots.init(i++, false, OUTPUT_SLOT_X, OUTPUT_SLOT_Y); // Output

		// Assign ingredients to slots
		for (int j = 0; j < inputs.size(); j++)
			slots.set(j, inputs.get(j));
		for (int k = 0; k < outputs.size(); k++)
			slots.set(inputs.size() + k, outputs.get(k));
	}

	/**
	 * Generates all the MFR carpenter recipes for JEI.
	 */
	public static Collection<JEICarpenterRecipe> generateRecipes() {

		List<JEICarpenterRecipe> recipes = new ArrayList<>();

		recipes.addAll(generateRecipeCategory1());

		return recipes;

	}

	@Nullable
	@Override
	public IDrawable getIcon() {
		return icon;
	}

	private static Collection<JEICarpenterRecipe> generateRecipeCategory1() {

		List<JEICarpenterRecipe> recipes = new ArrayList<>();

		// TODO: loop over all recipes once we are done
		recipes.add(new JEICarpenterRecipe((ShapedCarpenterRecipes) CraftingManagerCarpenter.getInstance().recipes.get(2))); // example
		recipes.add(new JEICarpenterRecipe((ShapedCarpenterRecipes) CraftingManagerCarpenter.getInstance().recipes.get(3))); // example
		recipes.add(new JEICarpenterRecipe((ShapedCarpenterRecipes) CraftingManagerCarpenter.getInstance().recipes.get(4))); // example
		recipes.add(new JEICarpenterRecipe((ShapedCarpenterRecipes) CraftingManagerCarpenter.getInstance().recipes.get(5))); // example

		return recipes;

	}
}
