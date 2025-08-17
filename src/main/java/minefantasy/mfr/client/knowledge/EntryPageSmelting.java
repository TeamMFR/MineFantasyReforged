package minefantasy.mfr.client.knowledge;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.registry.recipe.BigFurnaceRecipeBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntryPageSmelting extends EntryPage {
	private Minecraft mc = Minecraft.getMinecraft();
	private List<Ingredient> inputs;
	private ItemStack output;
	private final CycleTimer cycleTimer = new CycleTimer((int) ((Math.random() * 10000) % Integer.MAX_VALUE));

	public EntryPageSmelting(BigFurnaceRecipeBase recipe) {
		this.inputs = recipe.getInputs();
		this.output = recipe.getBigFurnaceRecipeOutput();
	}

	public EntryPageSmelting(ItemStack in, ItemStack out) {
		this.inputs = Collections.singletonList(Ingredient.fromStacks(in));
		this.output = out;
	}

	public EntryPageSmelting(Item in, Item out) {
		this(new ItemStack(in), new ItemStack(out));
	}

	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
		if (onTick) {
			cycleTimer.onDraw();
		}

		this.mc.getTextureManager().bindTexture(new ResourceLocation(MineFantasyReforged.MOD_ID, "textures/gui/knowledge/furnance_grid.png"));
		parent.drawTexturedModalRect(posX, posY, 0, 0, this.universalBookImageWidth, this.universalBookImageHeight);

		String cft = "<" + I18n.format("method.furnace") + ">";
		mc.fontRenderer.drawSplitString(cft,
				posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(cft) / 2), posY + 150, 117, 0);

		renderRecipe(parent, x, y, f, posX, posY);
	}

	private void renderRecipe(GuiScreen parent, int mx, int my, float f, int posX, int posY) {
		renderResult(parent, output, false, posX, posY + 74, mx, my);
		ItemStack stack = cycleTimer.getCycledItem(Arrays.asList(inputs.get(0).getMatchingStacks()));
		renderResult(parent, stack, false, posX, posY, mx, my);
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
