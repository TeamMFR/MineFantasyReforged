package minefantasy.mfr.client.knowledge;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.material.MetalMaterial;
import minefantasy.mfr.recipe.AnvilRecipeBase;
import minefantasy.mfr.recipe.ShapedAnvilRecipes;
import minefantasy.mfr.recipe.ShapedCustomMaterialAnvilRecipe;
import minefantasy.mfr.recipe.ShapelessAnvilRecipes;
import minefantasy.mfr.recipe.ShapelessCustomMaterialAnvilRecipe;
import minefantasy.mfr.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntryPageRecipeAnvil extends EntryPage {
	private final Minecraft mc = Minecraft.getMinecraft();
	private final List<AnvilRecipeBase> recipes;
	private final CycleTimer cycleTimer = new CycleTimer((int) ((Math.random() * 10000) % Integer.MAX_VALUE));

	public EntryPageRecipeAnvil(List<AnvilRecipeBase> recipes) {
		this.recipes = recipes;
	}

	public EntryPageRecipeAnvil(AnvilRecipeBase recipe) {
		this.recipes = Collections.singletonList(recipe);
	}

	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
		if (onTick) {
			cycleTimer.onDraw();
		}

		this.mc.getTextureManager().bindTexture(new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/gui/knowledge/anvil_grid.png"));
		parent.drawTexturedModalRect(posX, posY, 0, 0, universalBookImageWidth, universalBookImageHeight);

		AnvilRecipeBase recipe = cycleTimer.getCycledItem(recipes);
		String cft = "<" + I18n.format("method.anvil") + ">";
		mc.fontRenderer.drawSplitString(cft, posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(cft) / 2), posY + 150, 117, 0);
		renderRecipe(parent, x, y, posX, posY, recipe);
	}

	private void renderRecipe(GuiScreen parent, int mx, int my, int posX, int posY, AnvilRecipeBase recipe) {
		if (recipe == null) {
			return;
		}

		GlStateManager.color(255, 255, 255);
		GuiHelper.renderToolIcon(parent, recipe.getToolType(), recipe.getHammerTier(), posX + 34, posY + 51, true, true);
		GuiHelper.renderToolIcon(parent, "anvil", recipe.getAnvilTier(), posX + 124, posY + 51, true, true);

		if (recipe instanceof ShapedAnvilRecipes || recipe instanceof ShapedCustomMaterialAnvilRecipe) {

			for (int y = 0; y < AnvilRecipeBase.HEIGHT; y++) {
				for (int x = 0; x < AnvilRecipeBase.WIDTH; x++) {
					int index = y * AnvilRecipeBase.WIDTH + x;
					ItemStack stack = cycleTimer.getCycledItem(Arrays.asList(recipe.inputs.get(index).getMatchingStacks()));
					renderItemAtGridPos(parent, 1 + x, 1 + y, stack, true, posX, posY, mx, my);
				}
			}
		} else if (recipe instanceof ShapelessAnvilRecipes || recipe instanceof ShapelessCustomMaterialAnvilRecipe) {

			drawGrid:
			{
				for (int y = 0; y < 6; y++) {
					for (int x = 0; x < 4; x++) {
						int index = y * 6 + x;

						if (index >= recipe.getIngredients().size())
							break drawGrid;

						ItemStack stack = cycleTimer.getCycledItem(Arrays.asList(recipe.inputs.get(index).getMatchingStacks()));
						renderItemAtGridPos(parent, 1 + x, 1 + y, stack, true,
								posX, posY, mx, my);
					}
				}
			}
		}
		List<ItemStack> outputs = new ArrayList<>();
		if (recipe.getAnvilRecipeOutput().getItem() == MineFantasyItems.HOT_ITEM) {//Dummy item to match in recipe
			for (CustomMaterial material : CustomMaterial.getList("metal")) {
				if (material instanceof MetalMaterial) {
					NonNullList<ItemStack> oreDictItemStacks = OreDictionary.getOres(((MetalMaterial) material).oreDictList);
					outputs.addAll(oreDictItemStacks);
				}
			}
		}

		if (outputs.isEmpty()) {
			renderResult(parent, recipe.getAnvilRecipeOutput(), false, posX, posY, mx, my, recipe.outputHot());
		}
		else {
			renderResult(parent, cycleTimer.getCycledItem(outputs), false, posX, posY, mx, my, recipe.outputHot());
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
