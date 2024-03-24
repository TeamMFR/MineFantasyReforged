package minefantasy.mfr.client.knowledge;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.recipe.CarpenterDynamicRecipe;
import minefantasy.mfr.recipe.CarpenterRecipeBase;
import minefantasy.mfr.recipe.CarpenterShapedCustomMaterialRecipe;
import minefantasy.mfr.recipe.CarpenterShapedRecipe;
import minefantasy.mfr.recipe.CarpenterShapelessCustomMaterialRecipe;
import minefantasy.mfr.recipe.CarpenterShapelessRecipe;
import minefantasy.mfr.util.GuiHelper;
import minefantasy.mfr.util.RecipeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntryPageRecipeCarpenter extends EntryPage {
	private final Minecraft mc = Minecraft.getMinecraft();
	private final List<CarpenterRecipeBase> recipes;
	private final CycleTimer cycleTimer = new CycleTimer((int) ((Math.random() * 10000) % Integer.MAX_VALUE));

	public EntryPageRecipeCarpenter(List<CarpenterRecipeBase> recipes) {
		this.recipes = recipes;
	}

	public EntryPageRecipeCarpenter(CarpenterRecipeBase recipe) {
		this.recipes = Collections.singletonList(recipe);
	}

	@Override
	public void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {

	}

	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
		if (onTick) {
			cycleTimer.onDraw();
		}

		this.mc.getTextureManager().bindTexture(new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/gui/knowledge/carpenter_grid.png"));
		parent.drawTexturedModalRect(posX, posY, 0, 0, universalBookImageWidth, universalBookImageHeight);

		CarpenterRecipeBase recipe = cycleTimer.getCycledItem(recipes);
		String cft = "<" + I18n.format("method.carpenter") + ">";
		mc.fontRenderer.drawSplitString(cft,
				posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(cft) / 2),
				posY + 175, 117, 0);
		renderRecipe(parent, x, y, posX, posY, recipe);

	}

	private void renderRecipe(GuiScreen parent, int mx, int my, int posX, int posY, CarpenterRecipeBase recipe) {
		if (parent == null)
			return;
		if (recipe == null)
			return;

		GL11.glColor3f(255, 255, 255);
		GuiHelper.renderToolIcon(parent, recipe.getToolType().getName(), recipe.getToolTier(), posX + 34, posY + 51, true,
				true);
		GuiHelper.renderToolIcon(parent, "carpenter", recipe.getCarpenterTier(), posX + 124, posY + 51, true, true);

		List<Ingredient> inputs = RecipeHelper.expandPattern(recipe.getIngredients(), recipe.getWidth(), recipe.getHeight(), CarpenterRecipeBase.MAX_WIDTH, CarpenterRecipeBase.MAX_HEIGHT);
		if (recipe instanceof CarpenterShapedRecipe || recipe instanceof CarpenterShapedCustomMaterialRecipe || recipe instanceof CarpenterDynamicRecipe) {

			for (int y = 0; y < CarpenterRecipeBase.MAX_HEIGHT; y++) {
				for (int x = 0; x < CarpenterRecipeBase.MAX_WIDTH; x++) {
					int index = y * CarpenterRecipeBase.MAX_WIDTH + x;
					ItemStack stack = cycleTimer.getCycledItem(Arrays.asList(inputs.get(index).getMatchingStacks()));
					renderItemAtGridPos(parent, 1 + x, 1 + y, stack, true, posX, posY, mx, my);
				}
			}
		} else if (recipe instanceof CarpenterShapelessRecipe || recipe instanceof CarpenterShapelessCustomMaterialRecipe) {

			drawGrid:
			{
				for (int y = 0; y < 4; y++) {
					for (int x = 0; x < 4; x++) {
						int index = y * 4 + x;

						if (index >= recipe.getIngredients().size())
							break drawGrid;

						ItemStack stack = cycleTimer.getCycledItem(Arrays.asList(inputs.get(index).getMatchingStacks()));
						renderItemAtGridPos(parent, 1 + x, 1 + y, stack, true, posX, posY, mx, my);
					}
				}
			}

		}
		List<ItemStack> outputs = new ArrayList<>();
		if (recipe instanceof CarpenterDynamicRecipe) {
			//outputs = AnvilDynamicRecipe.getOutputsFromIngredients(inputs, ((CarpenterDynamicRecipe) recipe).modifyOutput, recipe.getCarpenterRecipeOutput());
		}

		if (outputs.isEmpty()) {
			renderResult(parent, recipe.getCarpenterRecipeOutput(), false, posX, posY, mx, my);
		}
		else {
			renderResult(parent, cycleTimer.getCycledItem(outputs), false, posX, posY, mx, my);
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
		if (stack == null) {// fixes GUI crash with missing or incorrect recipes
			return;
		}

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
