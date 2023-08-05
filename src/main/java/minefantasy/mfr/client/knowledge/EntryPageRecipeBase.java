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
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.Arrays;
import java.util.List;

public class EntryPageRecipeBase extends EntryPage {
	private final Minecraft mc = Minecraft.getMinecraft();
	private final List<IRecipe> recipes;
	private final CycleTimer cycleTimer = new CycleTimer((int) ((Math.random() * 10000) % Integer.MAX_VALUE));

	public EntryPageRecipeBase(List<IRecipe> recipes) {
		this.recipes = recipes;
	}

	public EntryPageRecipeBase(IRecipe... recipes) {
		this.recipes = Arrays.asList(recipes);
	}

	@Override
	public void render(GuiScreen parent, int x, int y, float ticks, int posX, int posY, boolean onTick) {
		if (onTick) {
			cycleTimer.onDraw();
		}

		this.mc.getTextureManager().bindTexture(new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/gui/knowledge/craft_grid.png"));
		parent.drawTexturedModalRect(posX, posY, 0, 0, universalBookImageWidth, universalBookImageHeight);

		IRecipe recipe = cycleTimer.getCycledItem(recipes);
		String cft = "<" + I18n.format("method.workbench") + ">";
		mc.fontRenderer.drawSplitString(cft, posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(cft) / 2), posY + 175, 117, 0);

		renderRecipe(parent, x, y, posX, posY, recipe);
	}

	private void renderRecipe(GuiScreen parent, int mx, int my, int posX, int posY, IRecipe recipe) {
		if (recipe == null) {
			return;
		}

		if (recipe instanceof ShapedRecipes || recipe instanceof ShapedOreRecipe) {

			for (int y = 0; y < ((IShapedRecipe) recipe).getRecipeHeight(); y++) {
				for (int x = 0; x < ((IShapedRecipe) recipe).getRecipeWidth(); x++) {
					int index = y * ((IShapedRecipe) recipe).getRecipeWidth() + x;
					ItemStack stack = cycleTimer.getCycledItem(Arrays.asList(recipe.getIngredients().get(index).getMatchingStacks()));
					if (stack != null) {
						renderItemAtGridPos(parent, x, y, stack, true, posX, posY, mx, my);
					}
				}
			}
		}
		else if (recipe instanceof ShapelessRecipes || recipe instanceof ShapelessOreRecipe) {

			drawGrid:
			{
				for (int y = 0; y < 3; y++) {
					for (int x = 0; x < 3; x++) {
						int index = y * 3 + x;

						if (index >= recipe.getIngredients().size())
							break drawGrid;
						ItemStack stack = cycleTimer.getCycledItem(Arrays.asList(recipe.getIngredients().get(index).getMatchingStacks()));
						if (stack != null) {
							renderItemAtGridPos(parent, x, y, stack, true, posX, posY, mx, my);
						}
					}
				}
			}
		}

		renderResult(parent, recipe.getRecipeOutput(), false, posX, posY, mx, my);
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

	@Override
	public void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
	}
}