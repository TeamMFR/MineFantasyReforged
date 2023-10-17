package minefantasy.mfr.client.knowledge;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.recipe.BlastFurnaceRecipeBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntryPageBlastFurnace extends EntryPage {
	private final Minecraft mc = Minecraft.getMinecraft();
	private final List<BlastFurnaceRecipeBase> recipes;
	private final CycleTimer cycleTimer = new CycleTimer((int) ((Math.random() * 10000) % Integer.MAX_VALUE));

	public EntryPageBlastFurnace(BlastFurnaceRecipeBase recipe) {
		this.recipes = Collections.singletonList(recipe);
	}

	public EntryPageBlastFurnace(List<BlastFurnaceRecipeBase> recipes) {
		this.recipes = recipes;
	}

	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
		if (onTick) {
			cycleTimer.onDraw();
		}

		this.mc.getTextureManager().bindTexture(new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/gui/knowledge/blast_furnace_grid.png"));
		parent.drawTexturedModalRect(posX, posY, 0, 0, universalBookImageWidth, universalBookImageHeight);

		String cft = "<" + I18n.format("method.blastfurnace") + ">";
		mc.fontRenderer.drawSplitString(cft,
				posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(cft) / 2), posY + 150, 117, 0);

		BlastFurnaceRecipeBase recipe = cycleTimer.getCycledItem(recipes);

		renderRecipe(parent, x, y, posX, posY, recipe);
	}

	private void renderRecipe(GuiScreen parent, int mx, int my, int posX, int posY, BlastFurnaceRecipeBase recipe) {
		if (recipe == null) {
			return;
		}
		ItemStack input = cycleTimer.getCycledItem(Arrays.asList(recipe.getInputs().get(0).getMatchingStacks()));
		if (input == null) {
			return;
		}
		renderItemStack(parent, input, false, posX, posY, mx, my);

		renderItemStack(parent, recipe.getBlastFurnaceRecipeOutput(), false, posX, posY + 74, mx, my);

	}

	public void renderItemStack(GuiScreen gui, ItemStack stack, boolean accountForContainer, int xOrigin, int yOrigin, int mx, int my) {
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
