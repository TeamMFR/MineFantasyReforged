package minefantasy.mfr.client.knowledge;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.recipe.ICarpenterRecipe;
import minefantasy.mfr.recipe.ShapedCarpenterRecipes;
import minefantasy.mfr.recipe.ShapelessCarpenterRecipes;
import minefantasy.mfr.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class EntryPageRecipeCarpenter extends EntryPage {
	public static int switchRate = 15;
	private Minecraft mc = Minecraft.getMinecraft();
	private ICarpenterRecipe[] recipes = new ICarpenterRecipe[] {};
	private int recipeID;
	private boolean shapelessRecipe = false;
	private boolean oreDictRecipe = false;

	public EntryPageRecipeCarpenter(List<ICarpenterRecipe> recipes) {
		ICarpenterRecipe[] array = new ICarpenterRecipe[recipes.size()];
		for (int a = 0; a < recipes.size(); a++) {
			array[a] = recipes.get(a);
		}
		this.recipes = array;

	}

	public EntryPageRecipeCarpenter(ICarpenterRecipe... recipes) {
		this.recipes = recipes;
	}

	@Override
	public void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {

	}

	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
		if (onTick) {
			tickRecipes();
		}

		this.mc.getTextureManager().bindTexture(new ResourceLocation(MineFantasyReborn.MOD_ID, "textures/gui/knowledge/carpenter_grid.png"));
		parent.drawTexturedModalRect(posX, posY, 0, 0, this.universalBookImageWidth, this.universalBookImageHeight);

		ICarpenterRecipe recipe = (recipeID < 0 || recipeID >= recipes.length) ? null : recipes[recipeID];
		String cft = "<" + I18n.format("method.carpenter") + ">";
		mc.fontRenderer.drawSplitString(cft,
				posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(cft) / 2), posY + 175, 117, 0);
		renderRecipe(parent, x, y, f, posX, posY, recipe);

	}

	private void renderRecipe(GuiScreen parent, int mx, int my, float f, int posX, int posY, ICarpenterRecipe recipe) {
		if (parent == null)
			return;
		if (recipe == null)
			return;
		shapelessRecipe = false;
		oreDictRecipe = false;

		GL11.glColor3f(255, 255, 255);
		GuiHelper.renderToolIcon(parent, recipe.getToolType(), recipe.getRecipeHammer(), posX + 34, posY + 51, true,
				true);
		GuiHelper.renderToolIcon(parent, "carpenter", recipe.getAnvil(), posX + 124, posY + 51, true, true);

		if (recipe instanceof ShapedCarpenterRecipes) {
			ShapedCarpenterRecipes shaped = (ShapedCarpenterRecipes) recipe;

			for (int y = 0; y < shaped.recipeHeight; y++) {
				for (int x = 0; x < shaped.recipeWidth; x++) {
					renderItemAtGridPos(parent, 1 + x, 1 + y, shaped.recipeItems[y * shaped.recipeWidth + x], true,
							posX, posY, mx, my);
				}
			}
		} else if (recipe instanceof ShapelessCarpenterRecipes) {
			ShapelessCarpenterRecipes shapeless = (ShapelessCarpenterRecipes) recipe;

			drawGrid:
			{
				for (int y = 0; y < 4; y++) {
					for (int x = 0; x < 4; x++) {
						int index = y * 4 + x;

						if (index >= shapeless.recipeItems.size())
							break drawGrid;

						renderItemAtGridPos(parent, 1 + x, 1 + y, (ItemStack) shapeless.recipeItems.get(index), true,
								posX, posY, mx, my);
					}
				}
			}

			shapelessRecipe = true;
		}
		renderResult(parent, recipe.getCarpenterRecipeOutput(), false, posX, posY, mx, my);
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

	public void renderItemAtGridPos(GuiScreen parent, int x, int y, ItemStack stack, boolean accountForContainer, int xOrigin, int yOrigin, int mx, int my) {
		if (stack.isEmpty())
			return;
		stack = stack.copy();

		int gridSize = 23;

		if (stack.getItemDamage() == Short.MAX_VALUE)
			stack.setItemDamage(0);

		x -= 1;
		y -= 1;
		int xPos = xOrigin + (x * gridSize) + 46;
		int yPos = yOrigin + (y * gridSize) + 80;
		ItemStack stack1 = stack.copy();
		if (stack1.getItemDamage() == -1)
			stack1.setItemDamage(0);

		renderItem(parent, xPos, yPos, stack1, accountForContainer, mx, my);
	}
}
