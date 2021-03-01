package minefantasy.mfr.client.knowledge;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.refine.Alloy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EntryPageCrucible extends EntryPage {
	private Minecraft mc = Minecraft.getMinecraft();
	private Alloy[] recipes;
	private int recipeID;
	private int currTier;

	public EntryPageCrucible(Alloy... recipes) {
		this.recipes = recipes;
	}

	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
		if (onTick) {
			tickRecipes();
		}

		this.mc.getTextureManager().bindTexture(new ResourceLocation(MineFantasyReborn.MOD_ID, "textures/gui/knowledge/crucible.png"));
		parent.drawTexturedModalRect(posX, posY, 0, 0, this.universalBookImageWidth, this.universalBookImageHeight);

		Alloy recipe = (recipeID < 0 || recipeID >= recipes.length) ? null : recipes[recipeID];
		String cft = "<" + I18n.format("method." + getName() + "") + ">";
		mc.fontRenderer.drawSplitString(cft,
				posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(cft) / 2), posY + 175, 117, 0);
		if (recipe != null) {
			currTier = recipe.getLevel();
		}
		renderRecipe(parent, x, y, f, posX, posY, recipe);
	}

	protected String getName() {
		return currTier == 3 ? "crucibleT4" : currTier == 2 ? "crucibleT3" : currTier == 1 ? "crucibleT2" : "crucible";
	}

	private void renderRecipe(GuiScreen parent, int mx, int my, float f, int posX, int posY, Alloy recipe) {
		if (recipe == null) {
			return;
		}
		drawGrid:
		{
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < 3; x++) {
					int index = y * 3 + x;

					if (index >= recipe.recipeItems.size()) {
						break drawGrid;
					}

					renderItemAtGridPos(parent, 1 + x, (3 - y), (ItemStack) recipe.recipeItems.get(index), true, posX,
							posY, mx, my);
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
		int yPos = yOrigin + 144;
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

		int xPos = xOrigin + x * 29 + 7 + (y == 0 && x == 3 ? 10 : 0);
		int yPos = yOrigin + y * 29 - 9 - (y == 0 ? 7 : 0);
		ItemStack stack1 = stack.copy();
		if (stack1.getItemDamage() == -1)
			stack1.setItemDamage(0);

		renderItem(gui, xPos + 15, yPos + 22, stack1, accountForContainer, mx, my);
	}

	@Override
	public void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
	}
}
