package minefantasy.mfr.util;

import minefantasy.mfr.constants.Tool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class GuiHelper {
	private static final Minecraft mc = Minecraft.getMinecraft();

	public static void renderToolIcon(Gui screen, String toolType, int tier, int x, int y, boolean available) {
		renderToolIcon(screen, toolType, tier, x, y, false, available);
	}

	public static void renderToolIcon(Gui screen, String toolType, int tier, int x, int y, boolean outline,
			boolean available) {
		if (!available) {
			GL11.glColor3f(1.0F, 0.3F, 0.3F);
		}
		mc.getTextureManager().bindTexture(TextureHelperMFR.getResource("textures/gui/icons.png"));
		int[] icon = getToolTypeIcon(toolType);
		screen.drawTexturedModalRect(x, y, outline ? 20 : 0, 0, 20, 20);
		screen.drawTexturedModalRect(x, y, icon[0], icon[1] + 20, 20, 20);
		if (tier > -1)
			mc.fontRenderer.drawStringWithShadow("" + tier, x + 4, y + 10, 16777215);
		GL11.glColor3f(1F, 1F, 1F);
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
