package minefantasy.mfr.util;

import minefantasy.mfr.constants.Tool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class GuiHelper {
	private static final Minecraft mc = Minecraft.getMinecraft();

	public static void renderToolIcon(Gui screen, String toolType, int tier, int x, int y, boolean available, boolean button) {
		renderToolIcon(screen, toolType, tier, x, y, false, available, button);
	}

	public static void renderToolIcon(Gui screen, String toolType, int tier, int x, int y, boolean outline,
			boolean available, boolean button) {
		if (!available) {
			GL11.glColor3f(1.0F, 0.3F, 0.3F);
		}
		mc.getTextureManager().bindTexture(TextureHelperMFR.getResource("textures/gui/icons.png"));
		int[] icon = getToolTypeIcon(toolType);
		if (button) {
			screen.drawTexturedModalRect(x, y, outline ? 20 : 0, 0, 20, 20);
		}
		screen.drawTexturedModalRect(x, y, icon[0], icon[1] + 20, 20, 20);
		if (tier > -1)
			mc.fontRenderer.drawStringWithShadow("" + tier, x + 4, y + 10, 16777215);
		GL11.glColor3f(1F, 1F, 1F);
	}

	/**
	 * Test if the 2D point is in a rectangle (relative to the GUI). Args : rectX, rectY, rectWidth, rectHeight, pointX,
	 * pointY
	 */
	public static boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY, int guiLeft, int guiTop) {
		int i = guiLeft;
		int j = guiTop;
		pointX = pointX - i;
		pointY = pointY - j;
		return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
	}

	public static int[] getToolTypeIcon(String s) {
		int width = 20;
		int height = 20;
		if (s.equalsIgnoreCase(Tool.HANDS.getName())) {
			return new int[] {0, 0};
		}
		if (s.equalsIgnoreCase(Tool.KNIFE.getName())) {
			return new int[] {width * 1, height * 0};
		}
		if (s.equalsIgnoreCase(Tool.SAW.getName())) {
			return new int[] {width * 2, height * 0};
		}
		if (s.equalsIgnoreCase(Tool.MALLET.getName())) {
			return new int[] {width * 3, height * 0};
		}
		if (s.equalsIgnoreCase(Tool.NEEDLE.getName())) {
			return new int[] {width * 4, height * 0};
		}
		if (s.equalsIgnoreCase(Tool.HAMMER.getName())) {
			return new int[] {width * 5, height * 0};
		}
		if (s.equalsIgnoreCase(Tool.HEAVY_HAMMER.getName())) {
			return new int[] {width * 6, height * 0};
		}
		if (s.equalsIgnoreCase(Tool.SPOON.getName())) {
			return new int[] {width * 7, height * 0};
		}
		if (s.equalsIgnoreCase(Tool.SHEARS.getName())) {
			return new int[] {width * 8, height * 0};
		}
		if (s.equalsIgnoreCase(Tool.SPANNER.getName())) {
			return new int[] {width * 9, height * 0};
		}
		if (s.equalsIgnoreCase(Tool.BRUSH.getName())) {
			return new int[] {width * 11, height * 0};
		}

		if (s.equalsIgnoreCase("anvil")) {
			return new int[] {0, height * 2};
		}
		if (s.equalsIgnoreCase("carpenter")) {
			return new int[] {width * 1, height * 2};
		}
		if (s.equalsIgnoreCase("tanner")) {
			return new int[] {width * 2, height * 2};
		}

		if (s.equalsIgnoreCase("kitchen_bench")) {
			return new int[] {width * 3, height * 2};
		}
		return new int[] {0, 0};
	}

	public static int getColourForRGB(int red, int green, int blue) {
		return (red << 16) + (green << 8) + blue;
	}

	public static void drawHotItemIcon(Minecraft minecraft, int x, int y) {
		GlStateManager.pushMatrix();
		GlStateManager.color(255, 255, 255);
		GlStateManager.translate(0, 0, 999);
		minecraft.getTextureManager().bindTexture(TextureHelperMFR.getResource("textures/gui/knowledge/anvil_grid.png"));
		minecraft.currentScreen.drawTexturedModalRect(x, y, 248, 0, 8, 8);
		GlStateManager.popMatrix();
	}

}
