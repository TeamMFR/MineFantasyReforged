package minefantasy.mfr.client.knowledge;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.recipe.AlloyRatioRecipe;
import minefantasy.mfr.recipe.AlloyRecipeBase;
import minefantasy.mfr.util.RecipeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EntryPageCrucible extends EntryPage {
	private final Minecraft mc = Minecraft.getMinecraft();
	private final List<AlloyRecipeBase> recipes;
	private final CycleTimer cycleTimer = new CycleTimer((int) ((Math.random() * 10000) % Integer.MAX_VALUE));

	public EntryPageCrucible(AlloyRecipeBase recipe) {
		this.recipes = Collections.singletonList(recipe);
	}

	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
		if (onTick) {
			cycleTimer.onDraw();
		}

		this.mc.getTextureManager().bindTexture(new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/gui/knowledge/crucible.png"));
		parent.drawTexturedModalRect(posX, posY, 0, 0, universalBookImageWidth, universalBookImageHeight);

		AlloyRecipeBase recipe = cycleTimer.getCycledItem(recipes);
		String cft = getRequiredCrucibleName(recipe);
		mc.fontRenderer.drawSplitString(cft,
				posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(cft) / 2), posY + 175, 117, 0);

		renderRecipe(parent, x, y, posX, posY, recipe);
	}

	public static String getRequiredCrucibleName(AlloyRecipeBase recipeBase) {
		if (recipeBase == null) {
			return "";
		}
		switch (recipeBase.getTier()) {
			case 1:
				return "<" + I18n.format("method.crucibleT2") + ">";
			case 2:
				return "<" + I18n.format("method.crucibleT3") + ">";
			case 3:
				return "<" + I18n.format("method.crucibleT4") + ">";
			default:
				return "<" + I18n.format("method.crucible") + ">";
		}
	}

	private void renderRecipe(GuiScreen parent, int mx, int my, int posX, int posY, AlloyRecipeBase recipe) {
		if (recipe == null) {
			return;
		}
		ItemStack output = recipe.getAlloyRecipeOutput().copy();
		int outputCount = output.getCount();
		drawGrid:
		{
			if (recipe instanceof AlloyRatioRecipe) {
				List<Integer> repeatAmounts = IntStream
						.rangeClosed(1, ((AlloyRatioRecipe) recipe).getRepeatAmount())
						.boxed().collect(Collectors.toList());

				Integer currentRepeatAmount = cycleTimer.getCycledItem(repeatAmounts);
				NonNullList<Ingredient> inputsDuped = RecipeHelper.duplicateList(recipe.getInputs(), currentRepeatAmount);
				for (int y = 0; y < AlloyRecipeBase.MAX_HEIGHT; y++) {
					for (int x = 0; x < AlloyRecipeBase.MAX_WIDTH; x++) {
						int index = y * AlloyRecipeBase.MAX_WIDTH + x;

						if (index >= inputsDuped.size()) {
							break drawGrid;
						}
						List<Ingredient> inputs = RecipeHelper.expandPattern(inputsDuped, recipe.getWidth(), recipe.getHeight(), AlloyRecipeBase.MAX_WIDTH, AlloyRecipeBase.MAX_HEIGHT);
						ItemStack stack = cycleTimer.getCycledItem(Arrays.asList(inputs.get(index).getMatchingStacks()));
						renderItemAtGridPos(parent, 1 + x, (3 - y), stack, true, posX, posY, mx, my);
						output.setCount(currentRepeatAmount == null ? 0 : currentRepeatAmount * outputCount);
					}
				}
			}
			else {
				for (int y = 0; y < AlloyRecipeBase.MAX_HEIGHT; y++) {
					for (int x = 0; x < AlloyRecipeBase.MAX_WIDTH; x++) {
						int index = y * AlloyRecipeBase.MAX_WIDTH + x;

						if (index >= recipe.getInputs().size()) {
							break drawGrid;
						}
						List<Ingredient> inputs = RecipeHelper.expandPattern(recipe.getInputs(), recipe.getWidth(), recipe.getHeight(), AlloyRecipeBase.MAX_WIDTH, AlloyRecipeBase.MAX_HEIGHT);
						ItemStack stack = cycleTimer.getCycledItem(Arrays.asList(inputs.get(index).getMatchingStacks()));
						renderItemAtGridPos(parent, 1 + x, 1 + y, stack, true, posX, posY, mx, my);
					}
				}
			}
		}

		renderResult(parent, output, false, posX, posY, mx, my);
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

	public void renderItemAtGridPos(GuiScreen gui, int x, int y, ItemStack stack, boolean accountForContainer, int xOrigin, int yOrigin, int mx, int my) {
		if (stack == null) {// fixes GUI crash with missing or incorrect recipes
			return;
		}
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
