package minefantasy.mfr.client.knowledge;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

public class EntryPageRecipeBase extends EntryPage {
	private Minecraft mc = Minecraft.getMinecraft();
	private IRecipe[] recipes;
	private int recipeID;

	public EntryPageRecipeBase(List<IRecipe> recipes) {
		IRecipe[] array = new IRecipe[recipes.size()];
		for (int a = 0; a < recipes.size(); a++) {
			array[a] = recipes.get(a);
		}
		this.recipes = array;

	}

	public EntryPageRecipeBase(IRecipe... recipes) {
		this.recipes = recipes;
	}

	@Override
	public void render(GuiScreen parent, int x, int y, float ticks, int posX, int posY, boolean onTick) {
		if (onTick) {
			tickRecipes();
		}

		this.mc.getTextureManager().bindTexture(new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/gui/knowledge/craft_grid.png"));
		parent.drawTexturedModalRect(posX, posY, 0, 0, this.universalBookImageWidth, this.universalBookImageHeight);

		IRecipe recipe = (recipeID < 0 || recipeID >= recipes.length) ? null : recipes[recipeID];
		String cft = "<" + I18n.format("method.workbench") + ">";
		mc.fontRenderer.drawSplitString(cft,
				posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(cft) / 2), posY + 175, 117, 0);
		renderRecipe(parent, x, y, ticks, posX, posY, recipe);
	}

	private void renderRecipe(GuiScreen parent, int mx, int my, float f, int posX, int posY, IRecipe recipe) {
		if (recipe == null) {
			return;
		}

		ItemStack[] ingredients;

		if (recipe instanceof ShapedRecipes) {
			ShapedRecipes shaped = (ShapedRecipes) recipe;

			for (int y = 0; y < shaped.recipeHeight; y++) {
				for (int x = 0; x < shaped.recipeWidth; x++) {
					ingredients = shaped.recipeItems.get(y * shaped.recipeWidth + x).getMatchingStacks();
					if (ingredients.length != 0) { // fixes crash with mod operation with 0 divisior
						renderItemAtGridPos(parent, x, y, ingredients[recipeID % ingredients.length], true, posX, posY,
								mx, my);
					}
				}
			}
		} else if (recipe instanceof ShapedOreRecipe) {
			ShapedOreRecipe shaped = (ShapedOreRecipe) recipe;
			int width = shaped.getRecipeWidth();
			int height = shaped.getRecipeHeight();

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					//used to be shaped.getInput[y * width + x] Might not be correct co
					Object input = shaped.getIngredients().get(y * width + x);
					if (input != null)
						renderItemAtGridPos(parent, x, y, input instanceof ItemStack ? (ItemStack) input : ((ArrayList<ItemStack>) input).get(0),
								true, posX, posY, mx, my);
				}
			}
		} else if (recipe instanceof ShapelessRecipes) {
			ShapelessRecipes shapeless = (ShapelessRecipes) recipe;

			drawGrid:
			{
				for (int y = 0; y < 3; y++) {
					for (int x = 0; x < 3; x++) {
						int index = y * 3 + x;

						if (index >= shapeless.recipeItems.size())
							break drawGrid;
						ingredients = shapeless.recipeItems.get(index).getMatchingStacks();
						if (ingredients.length != 0) {
							renderItemAtGridPos(parent, x, y, ingredients[recipeID % ingredients.length], true, posX,
									posY, mx, my);
						}
					}
				}
			}
		} else if (recipe instanceof ShapelessOreRecipe) {
			ShapelessOreRecipe shapeless = (ShapelessOreRecipe) recipe;

			drawGrid:
			{
				for (int y = 0; y < 3; y++) {
					for (int x = 0; x < 3; x++) {
						int index = y * 3 + x;

						if (index >= shapeless.getIngredients().size())
							break drawGrid;

						Object input = shapeless.getIngredients().get(index);
						if (input != null)
							renderItemAtGridPos(parent, x, y, input instanceof ItemStack ? (ItemStack) input
									: ((ArrayList<ItemStack>) input).get(0), true, posX, posY, mx, my);
					}
				}
			}
		}

		renderResult(parent, recipe.getRecipeOutput(), false, posX, posY, mx, my);
	}

	private void tickRecipes() {
		if (recipeID < recipes.length - 1) {
			++recipeID;
		} else {
			recipeID = 0;
		}
	}

	public void renderResult(GuiScreen gui, ItemStack stack, boolean accountForContainer, int xOrigin, int yOrigin, int mx, int my) {
		if (stack.isEmpty())
			return;
		stack = stack.copy();

		if (stack.getItemDamage() == Short.MAX_VALUE)
			stack.setItemDamage(0);

		int xPos = xOrigin + 80;
		int yPos = yOrigin + 42;
		ItemStack stack1 = stack.copy();
		if (stack1.getItemDamage() == -1)
			stack1.setItemDamage(0);

		renderItem(gui, xPos, yPos, stack1, accountForContainer, mx, my);
	}

	public void renderItemAtGridPos(GuiScreen gui, int x, int y, ItemStack stack, boolean accountForContainer,
			int xOrigin, int yOrigin, int mx, int my) {
		if (stack.isEmpty())
			return;
		stack = stack.copy();

		if (stack.getItemDamage() == Short.MAX_VALUE)
			stack.setItemDamage(0);

		int xPos = xOrigin + (x * 29) + 51;
		int yPos = yOrigin + (y * 29) + 86;
		ItemStack stack1 = stack.copy();
		if (stack1.getItemDamage() == -1)
			stack1.setItemDamage(0);

		renderItem(gui, xPos, yPos, stack1, accountForContainer, mx, my);
	}

	public int getGridX() {
		return 7;
	}

	public int getGridY() {
		return 36;
	}

	@Override
	public void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
	}
}