package minefantasy.mfr.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.gui.GuiHelper;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.recipe.CraftingManagerQuern;
import minefantasy.mfr.recipe.QuernRecipeBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JEIQuernRecipeCategory implements IRecipeCategory<JEIQuernRecipe> {
	static final String UID = "minefantasyreforged:quern";

	static final ResourceLocation TEXTURE = new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/integration/jei/quern_background.png");
	static final ResourceLocation DOWN_ARROW_TEXTURE = new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/integration/jei/gui_assets.png");
	private final IDrawable icon;

	static final int WIDTH = 20;
	static final int HEIGHT = 88;

	private final IDrawable background;
	protected final IDrawableAnimated arrow;

	public JEIQuernRecipeCategory(IRecipeCategoryRegistration registry, GuiHelper guiHelper) {
		IGuiHelper iGuiHelper = registry.getJeiHelpers().getGuiHelper();
		background = iGuiHelper.createDrawable(TEXTURE, 0, 0, WIDTH, HEIGHT);
		icon = iGuiHelper.createDrawableIngredient(new ItemStack(MineFantasyBlocks.QUERN));
		arrow = guiHelper.drawableBuilder(DOWN_ARROW_TEXTURE, 0, 0, 17, 24)
				.buildAnimated(200, IDrawableAnimated.StartDirection.TOP, false);
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
		arrow.draw(minecraft, 1, 42);
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
	public void setRecipe(IRecipeLayout recipeLayout, JEIQuernRecipe recipeWrapper, IIngredients ingredients) {
		// Okay, they're not technically *slots* but to all intents and purposes, that's how they behave
		IGuiItemStackGroup slots = recipeLayout.getItemStacks();

		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		List<List<ItemStack>> outputList = ingredients.getOutputs(VanillaTypes.ITEM);
		List<List<ItemStack>> pots = recipeWrapper.getPotInputs();

		slots.init(0, true, 1, 1);//inputs
		slots.init(1, true, 1, 23);//pots
		slots.init(2, false, 1, 69);//outputs, also, nice

		slots.set(0, inputs.get(0));
		slots.set(1, pots.get(0));
		slots.set(2, outputList.get(0));
	}

	@Nullable
	@Override
	public IDrawable getIcon() {
		return icon;
	}

	/**
	 * Generates all the MFR Quern recipes for JEI.
	 */
	public static Collection<JEIQuernRecipe> generateRecipes(IStackHelper stackHelper) {
		return new ArrayList<>(generateQuernRecipes(stackHelper));
	}

	private static Collection<JEIQuernRecipe> generateQuernRecipes(IStackHelper stackHelper) {
		List<JEIQuernRecipe> recipes = new ArrayList<>();
		Collection<QuernRecipeBase> quernRecipes = CraftingManagerQuern.getRecipes();

		for (QuernRecipeBase recipe : quernRecipes) {
			recipes.add(new JEIQuernRecipe(recipe, stackHelper));
		}

		return recipes;
	}
}
