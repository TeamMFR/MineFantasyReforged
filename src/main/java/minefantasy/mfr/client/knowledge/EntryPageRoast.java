package minefantasy.mfr.client.knowledge;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.recipe.RoastRecipeBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntryPageRoast extends EntryPage {
	private final Minecraft mc = Minecraft.getMinecraft();
	private final List<RoastRecipeBase> recipes;
	private final CycleTimer cycleTimer = new CycleTimer((int) ((Math.random() * 10000) % Integer.MAX_VALUE));

	public EntryPageRoast(RoastRecipeBase recipe) {
		this.recipes = Collections.singletonList(recipe);
	}

	public EntryPageRoast(List<RoastRecipeBase> recipes) {
		this.recipes = recipes;
	}

	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
		if (onTick) {
			cycleTimer.onDraw();
		}
		RoastRecipeBase recipe = cycleTimer.getCycledItem(recipes);
		if (recipe == null) {
			return;
		}

		String texture = recipe.isOvenRecipe() ? "textures/gui/knowledge/baking_grid.png" : "textures/gui/knowledge/stovetop_grid.png";
		this.mc.getTextureManager().bindTexture(new ResourceLocation(MineFantasyReforged.MOD_ID, texture));
		parent.drawTexturedModalRect(posX, posY, 0, 0, universalBookImageWidth, universalBookImageHeight);

		String cft = recipe.isOvenRecipe() ? "<" + I18n.format("method.baking") + ">" : "<" + I18n.format("method.roasting") + ">";
		mc.fontRenderer.drawSplitString(cft,
				posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(cft) / 2), posY + 175, 114, 0);

		String maxTemp = "Max Temp: " + recipe.getMaxTemperature() + "℃";
		String minTemp = "Min Temp: " + recipe.getMinTemperature() + "℃";
		int maxAdjust = recipe.isOvenRecipe() ? 10 : 15;
		int minAdjust = recipe.isOvenRecipe() ? 19 : 25;

		mc.fontRenderer.drawSplitString(maxTemp,
				posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(maxTemp) / 2), posY + maxAdjust, 114, 0);
		mc.fontRenderer.drawSplitString(minTemp,
				posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(minTemp) / 2), posY + minAdjust, 114, 0);

		renderRecipe(parent, x, y, posX, posY, recipe);
	}

	private void renderRecipe(GuiScreen parent, int mx, int my, int posX, int posY, RoastRecipeBase recipe) {
		if (recipe == null) {
			return;
		}
		ItemStack input = cycleTimer.getCycledItem(Arrays.asList(recipe.getInputs().get(0).getMatchingStacks()));
		if (input == null) {
			return;
		}

		renderResult(parent, input, false, posX, posY, mx, my);
		renderResult(parent, recipe.getRoastRecipeOutput(), false, posX, posY + 90, mx, my);
	}

	public void renderResult(GuiScreen gui, ItemStack stack, boolean accountForContainer, int xOrigin, int yOrigin, int mx, int my) {
		if (stack.isEmpty())
			return;
		stack = stack.copy();

		if (stack.getItemDamage() == Short.MAX_VALUE)
			stack.setItemDamage(0);

		int xPos = xOrigin + 80;
		int yPos = yOrigin + 41;
		ItemStack stack1 = stack.copy();
		if (stack1.getItemDamage() == -1)
			stack1.setItemDamage(0);

		renderItem(gui, xPos, yPos, stack1, accountForContainer, mx, my);
	}

	@Override
	public void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
	}
}
