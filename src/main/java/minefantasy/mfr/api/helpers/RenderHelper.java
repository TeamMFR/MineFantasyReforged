package minefantasy.mfr.api.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.List;

public final class RenderHelper {

    public static void renderTooltip(int x, int y, List<String> tooltipData) {
        int color = 0x505000ff;
        int color2 = 0xf0100010;

        renderTooltip(x, y, tooltipData, color, color2);
    }

    public static void renderTooltipOrange(int x, int y, List<String> tooltipData) {
        int color = 0x50a06600;
        int color2 = 0xf01e1200;

        renderTooltip(x, y, tooltipData, color, color2);
    }

    public static void renderTooltipGreen(int x, int y, List<String> tooltipData) {
        int color = 0x5000a000;
        int color2 = 0xf0001e00;

        renderTooltip(x, y, tooltipData, color, color2);
    }

    public static void renderTooltip(int x, int y, List<String> tooltipData, int colour, int color2) {
        boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
        if (lighting)
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

        if (!tooltipData.isEmpty()) {
            int var5 = 0;
            int var6;
            int var7;
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            for (var6 = 0; var6 < tooltipData.size(); ++var6) {
                var7 = fontRenderer.getStringWidth(tooltipData.get(var6));
                if (var7 > var5)
                    var5 = var7;
            }
            var6 = x + 12;
            var7 = y - 12;
            int var9 = 8;
            if (tooltipData.size() > 1)
                var9 += 2 + (tooltipData.size() - 1) * 10;
            float z = 300F;
            drawGradientRect(var6 - 3, var7 - 4, z, var6 + var5 + 3, var7 - 3, color2, color2);
            drawGradientRect(var6 - 3, var7 + var9 + 3, z, var6 + var5 + 3, var7 + var9 + 4, color2, color2);
            drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 + var9 + 3, color2, color2);
            drawGradientRect(var6 - 4, var7 - 3, z, var6 - 3, var7 + var9 + 3, color2, color2);
            drawGradientRect(var6 + var5 + 3, var7 - 3, z, var6 + var5 + 4, var7 + var9 + 3, color2, color2);
            int var12 = (colour & 0xFFFFFF) >> 1 | colour & -16777216;
            drawGradientRect(var6 - 3, var7 - 3 + 1, z, var6 - 3 + 1, var7 + var9 + 3 - 1, colour, var12);
            drawGradientRect(var6 + var5 + 2, var7 - 3 + 1, z, var6 + var5 + 3, var7 + var9 + 3 - 1, colour, var12);
            drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 - 3 + 1, colour, colour);
            drawGradientRect(var6 - 3, var7 + var9 + 2, z, var6 + var5 + 3, var7 + var9 + 3, var12, var12);

            GL11.glDisable(GL11.GL_DEPTH_TEST);
            for (int var13 = 0; var13 < tooltipData.size(); ++var13) {
                String var14 = tooltipData.get(var13);
                fontRenderer.drawStringWithShadow(var14, var6, var7, -1);
                if (var13 == 0)
                    var7 += 2;
                var7 += 10;
            }
            GL11.glEnable(GL11.GL_DEPTH_TEST);
        }
        if (!lighting)
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GL11.glColor4f(1F, 1F, 1F, 1F);
    }

    public static void drawGradientRect(int x1, int y, float z, int x2, int color1, int color2, int color3) {
        int vertexIndex1 = (color2 >> 24 & 255) / 255;
        float red1 = (color2 >> 16 & 255) / 255F;
        float blue1 = (color2 >> 8 & 255) / 255F;
        float green1 = (color2 & 255) / 255F;
        int vertexIndex2 = (color3 >> 24 & 255) / 255;
        float red2 = (color3 >> 16 & 255) / 255F;
        float blue2 = (color3 >> 8 & 255) / 255F;
        float green2 = (color3 & 255) / 255F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.putColorRGB_F(red1, blue1, green1, vertexIndex1);
        bufferBuilder.pos(x2, y, z);
        bufferBuilder.pos(x1, y, z);
        bufferBuilder.putColorRGB_F(red2, blue2, green2, vertexIndex2);
        bufferBuilder.pos(x1, color1, z);
        bufferBuilder.pos(x2, color1, z);
        bufferBuilder.finishDrawing();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void drawTexturedModalRect(int par1, int par2, float z, int par3, int par4, int par5, int par6) {
        drawTexturedModalRect(par1, par2, z, par3, par4, par5, par6, 0.00390625F, 0.00390625F);
    }

    public static void drawTexturedModalRect(int par1, int par2, float z, int par3, int par4, int par5, int par6,
            float f, float f1) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(par1, par2 + par6, z).tex((par3) * f, (par4 + par6) * f1);
        bufferBuilder.pos(par1, par2 + par6, z).tex((par3) * f, (par4 + par6) * f1);
        bufferBuilder.pos(par1 + par5, par2 + par6, z).tex((par3 + par5) * f, (par4 + par6) * f1);
        bufferBuilder.pos(par1 + par5, par2, z).tex((par3 + par5) * f, (par4) * f1);
        bufferBuilder.pos(par1, par2, z).tex((par3) * f, (par4) * f1);
        bufferBuilder.finishDrawing();
    }

    public static String getKeyDisplayString(String keyName) {
        String key = null;
        KeyBinding[] keys = Minecraft.getMinecraft().gameSettings.keyBindings;
        for (KeyBinding otherKey : keys)
            if (otherKey.getKeyDescription().equals(keyName)) {
                key = Keyboard.getKeyName(otherKey.getKeyCode());
                break;
            }

        return key;
    }
}
