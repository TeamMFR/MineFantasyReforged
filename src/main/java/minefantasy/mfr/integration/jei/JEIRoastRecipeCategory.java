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
import minefantasy.mfr.recipe.CraftingManagerRoast;
import minefantasy.mfr.recipe.RoastRecipeBase;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JEIRoastRecipeCategory implements IRecipeCategory<JEIRoastRecipe> {
	final String UID;

	static final ResourceLocation TEXTURE = new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/integration/jei/roast_background.png");
	private final IDrawable icon;

	private final IDrawable background;
	protected final IDrawableStatic staticFlame;
	protected final IDrawableAnimated animatedFlameOne;
	protected final IDrawableAnimated animatedFlameTwo;
	protected final IDrawableAnimated arrow;

	public JEIRoastRecipeCategory(
			IRecipeCategoryRegistration registry, GuiHelper guiHelper,
			int startHeight, Block iconItem, String UID) {
		IGuiHelper iGuiHelper = registry.getJeiHelpers().getGuiHelper();
		this.UID = "minefantasyreforged:" + UID;

		background = iGuiHelper.createDrawable(TEXTURE, 0, startHeight, 92, 52);
		icon = iGuiHelper.createDrawableIngredient(new ItemStack(iconItem));

		staticFlame = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 82, 114, 14, 14);
		animatedFlameOne = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
		animatedFlameTwo = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);

		arrow = guiHelper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17)
				.buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
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

	@Override
	public void drawExtras(Minecraft minecraft) {
		animatedFlameOne.draw(minecraft, 10, 37);
		animatedFlameTwo.draw(minecraft, 68, 37);
		arrow.draw(minecraft, 35, 9);
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
	public void setRecipe(IRecipeLayout recipeLayout, JEIRoastRecipe recipeWrapper, IIngredients ingredients) {
		// Okay, they're not technically *slots* but to all intents and purposes, that's how they behave
		IGuiItemStackGroup slots = recipeLayout.getItemStacks();

		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		List<List<ItemStack>> outputList = ingredients.getOutputs(VanillaTypes.ITEM);

		slots.init(0, true, 8, 7);//inputs
		slots.init(1, false, 66, 7);//outputs, also, nice

		slots.set(0, inputs.get(0));
		slots.set(1, outputList.get(0));
	}

	@Nullable
	@Override
	public IDrawable getIcon() {
		return icon;
	}

	/**
	 * Generates all the MFR Roast recipes for JEI.
	 */
	public static Collection<JEIRoastRecipe> generateRecipes(IStackHelper stackHelper, boolean isOven) {
		return new ArrayList<>(generateRoastRecipes(stackHelper, isOven));
	}

	private static Collection<JEIRoastRecipe> generateRoastRecipes(IStackHelper stackHelper, boolean isOven) {
		List<JEIRoastRecipe> recipes = new ArrayList<>();
		Collection<RoastRecipeBase> roastRecipes = CraftingManagerRoast.getRecipes()
				.stream()
				.filter(r -> r.isOvenRecipe() == isOven)
				.collect(Collectors.toList());

		for (RoastRecipeBase recipe : roastRecipes) {
			recipes.add(new JEIRoastRecipe(recipe, stackHelper));
		}

		return recipes;
	}
}
