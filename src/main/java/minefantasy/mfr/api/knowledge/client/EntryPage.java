package minefantasy.mfr.api.knowledge.client;

import minefantasy.mfr.client.gui.GuiKnowledgeEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public abstract class EntryPage {
    public static final int universalBookImageWidth = 178;
    public static final int universalBookImageHeight = 227;

    public abstract void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick);

    public abstract void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick);

    public void setHoveredItem(GuiKnowledgeEntry parent, int xPos, int yPos, int mx, int my, ItemStack stack) {
        if (mx > xPos && mx < (xPos + 16) && my > yPos && my < (yPos + 16)) {
            parent.hoveredItem = stack;
            parent.hoverX = xPos;
            parent.hoverY = yPos;
        }
    }

    protected void renderItem(GuiScreen gui, int xPos, int yPos, ItemStack stack, boolean accountForContainer, int mx, int my) {
        setHoveredItem((GuiKnowledgeEntry) gui, xPos, yPos, mx, my, stack);

        RenderItem render = Minecraft.getMinecraft().getRenderItem();

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        render.renderItemAndEffectIntoGUI(stack, xPos, yPos);
        render.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, stack, xPos, yPos, null);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();

        GL11.glDisable(GL11.GL_LIGHTING);
    }

}
