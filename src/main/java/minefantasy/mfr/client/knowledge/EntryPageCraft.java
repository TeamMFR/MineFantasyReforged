package minefantasy.mfr.client.knowledge;

import minefantasy.mfr.util.TextureHelperMFR;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.crafting.IRecipe;

public class EntryPageCraft extends EntryPage {
	private Minecraft mc = Minecraft.getMinecraft();
	private IRecipe[] recipes;
	private int recipeID;

	public EntryPageCraft(IRecipe... recipes) {
		this.recipes = recipes;
	}

	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
		if (onTick) {
			tickRecipes();
		}

		this.mc.getTextureManager().bindTexture(TextureHelperMFR.getResource("textures/gui/knowledge/craft_grid.png"));
		parent.drawTexturedModalRect(posX, posY, 0, 0, this.universalBookImageWidth, this.universalBookImageHeight);

		IRecipe recipe = (recipeID < 0 || recipeID >= recipes.length) ? null : recipes[recipeID];
		renderRecipe(parent, x, y, f, posX, posY, recipe);
	}

	private void renderRecipe(GuiScreen parent, int x, int y, float f, int posX, int posY, IRecipe recipe) {
		if (recipe == null) {
			return;
		}
		// TODO: Render Grid
	}

	private void tickRecipes() {
		if (recipeID < recipes.length - 1) {
			++recipeID;
		} else {
			recipeID = 0;
		}
	}

	@Override
	public void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
	}
}
