package minefantasy.mfr.client.knowledge;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.recipe.QuernRecipeBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntryPageGrind extends EntryPage {
	private final Minecraft mc = Minecraft.getMinecraft();
	private final List<QuernRecipeBase> recipes;
	private final CycleTimer cycleTimer = new CycleTimer((int) ((Math.random() * 10000) % Integer.MAX_VALUE));

	public EntryPageGrind(QuernRecipeBase recipe) {
		this.recipes = Collections.singletonList(recipe);
	}

	public EntryPageGrind(List<QuernRecipeBase> recipes) {
		this.recipes = recipes;
	}

	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
		if (onTick) {
			cycleTimer.onDraw();
		}

		this.mc.getTextureManager().bindTexture(new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/gui/knowledge/grind_grid.png"));
		parent.drawTexturedModalRect(posX, posY, 0, 0, universalBookImageWidth, universalBookImageHeight);

		String cft = "<" + I18n.format("method.quern") + ">";
		mc.fontRenderer.drawSplitString(cft,
				posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(cft) / 2), posY + 150, 117, 0);

		QuernRecipeBase recipe = cycleTimer.getCycledItem(recipes);

		renderRecipe(parent, x, y, posX, posY, recipe);
	}

	private void renderRecipe(GuiScreen parent, int mx, int my, int posX, int posY, QuernRecipeBase recipe) {
		if (recipe == null) {
			return;
		}
		ItemStack input = cycleTimer.getCycledItem(Arrays.asList(recipe.getInputs().get(0).getMatchingStacks()));
		if (input == null) {
			return;
		}
		renderResult(parent, input, false, posX, posY, mx, my);

		renderResult(parent, recipe.getQuernRecipeOutput(), false, posX, posY + 74, mx, my);
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
