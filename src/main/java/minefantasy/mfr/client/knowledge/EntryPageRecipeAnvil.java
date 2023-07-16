package minefantasy.mfr.client.knowledge;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.recipe.IAnvilRecipe;
import minefantasy.mfr.recipe.ShapedAnvilRecipes;
import minefantasy.mfr.recipe.ShapelessAnvilRecipes;
import minefantasy.mfr.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class EntryPageRecipeAnvil extends EntryPage {
	private Minecraft mc = Minecraft.getMinecraft();
	private IAnvilRecipe[] recipes;
	private int recipeID;

	public EntryPageRecipeAnvil(List<IAnvilRecipe> recipes) {
		IAnvilRecipe[] array = new IAnvilRecipe[recipes.size()];
		for (int a = 0; a < recipes.size(); a++) {
			array[a] = recipes.get(a);
		}
		this.recipes = array;
	}

	public EntryPageRecipeAnvil(IAnvilRecipe... recipes) {
		this.recipes = recipes;
	}

	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
		if (onTick) {
			tickRecipes();
		}

		this.mc.getTextureManager().bindTexture(new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/gui/knowledge/anvil_grid.png"));
		parent.drawTexturedModalRect(posX, posY, 0, 0, this.universalBookImageWidth, this.universalBookImageHeight);

		IAnvilRecipe recipe = (recipeID < 0 || recipeID >= recipes.length) ? null : recipes[recipeID];
		String cft = "<" + I18n.format("method.anvil") + ">";
		mc.fontRenderer.drawSplitString(cft, posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(cft) / 2), posY + 150, 117, 0);
		renderRecipe(parent, x, y, f, posX, posY, recipe);
	}

	private void renderRecipe(GuiScreen parent, int mx, int my, float f, int posX, int posY, IAnvilRecipe recipe) {
		if (recipe == null)
			return;

		GlStateManager.color(255, 255, 255);
		GuiHelper.renderToolIcon(parent, recipe.getToolType(), recipe.getHammerTier(), posX + 34, posY + 51, true, true);
		GuiHelper.renderToolIcon(parent, "anvil", recipe.getAnvilTier(), posX + 124, posY + 51, true, true);

		if (recipe instanceof ShapedAnvilRecipes) {
			ShapedAnvilRecipes shaped = (ShapedAnvilRecipes) recipe;

			for (int y = 0; y < shaped.recipeHeight; y++) {
				for (int x = 0; x < shaped.recipeWidth; x++) {
					renderItemAtGridPos(parent, 1 + x, 1 + y, shaped.recipeItems[y * shaped.recipeWidth + x], true, posX, posY, mx, my);
				}
			}
		} else if (recipe instanceof ShapelessAnvilRecipes) {
			ShapelessAnvilRecipes shapeless = (ShapelessAnvilRecipes) recipe;

			drawGrid:
			{
				for (int y = 0; y < 6; y++) {
					for (int x = 0; x < 4; x++) {
						int index = y * 6 + x;

						if (index >= shapeless.recipeItems.size())
							break drawGrid;

						renderItemAtGridPos(parent, 1 + x, 1 + y, (ItemStack) shapeless.recipeItems.get(index), true,
								posX, posY, mx, my);
					}
				}
			}
		}
		renderResult(parent, recipe.getAnvilRecipeOutput(), false, posX, posY, mx, my, recipe.outputHot());
	}

	private void tickRecipes() {
		if (recipeID < recipes.length - 1) {
			++recipeID;
		} else {
			recipeID = 0;
		}
	}

	public void renderResult(GuiScreen gui, ItemStack stack, boolean accountForContainer, int xOrigin, int yOrigin, int mx, int my, boolean hot) {
		if (stack.isEmpty())
			return;
		stack = stack.copy();

		if (stack.getItemDamage() == Short.MAX_VALUE)
			stack.setItemDamage(0);
		if (stack.getItemDamage() == -1)
			stack.setItemDamage(0);

		int xPos = xOrigin + 80;
		int yPos = yOrigin + 42;

		renderItem(gui, xPos, yPos, stack, accountForContainer, mx, my, hot);
	}

	public void renderItemAtGridPos(GuiScreen gui, int x, int y, ItemStack stack, boolean accountForContainer, int xOrigin, int yOrigin, int mx, int my) {
		if (stack == null) // fixes GUI crash with missing or incorrect recipes
			return;

		boolean heatable = Heatable.canHeatItem(stack);

		stack = stack.copy();

		int gridSize = 18;

		if (stack.getItemDamage() == Short.MAX_VALUE)
			stack.setItemDamage(0);
		if (stack.getItemDamage() == -1)
			stack.setItemDamage(0);

		x -= 1;
		y -= 1;
		int xPos = xOrigin + (x * gridSize) + 36;
		int yPos = yOrigin + (y * gridSize) + 76;

		renderItem(gui, xPos, yPos, stack, accountForContainer, mx, my, heatable);
	}

	public void renderItem(GuiScreen gui, int xPos, int yPos, ItemStack stack, boolean accountForContainer, int mx, int my, boolean heatable) {
		renderItem(gui, xPos, yPos, stack, accountForContainer, mx, my);

		if (heatable) {
			GuiHelper.drawHotItemIcon(this.mc, xPos, yPos);
		}

	}

	@Override
	public void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
	}
}
