package minefantasy.mf2.api.knowledge.client;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;
import java.util.List;

public class EntryPageGrind extends EntryPage {
    private Minecraft mc = Minecraft.getMinecraft();
    private ItemStack input, output;
    private ItemStack tooltipStack;

    public EntryPageGrind(ItemStack in, ItemStack out) {
        this.input = in;
        this.output = out;
    }

    public EntryPageGrind(Item in, Item out) {
        this(new ItemStack(in), new ItemStack(out));
    }

    @Override
    public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
        tooltipStack = null;

        this.mc.getTextureManager().bindTexture(TextureHelperMF.getResource("textures/gui/knowledge/grindGrid.png"));
        parent.drawTexturedModalRect(posX, posY, 0, 0, this.universalBookImageWidth, this.universalBookImageHeight);

        String cft = "<" + StatCollector.translateToLocal("method.quern") + ">";
        mc.fontRenderer.drawSplitString(cft,
                posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(cft) / 2), posY + 150, 117, 0);

        renderRecipe(parent, x, y, f, posX, posY);
        if (tooltipStack != null) {
            List<String> tooltipData = tooltipStack.getTooltip(Minecraft.getMinecraft().thePlayer, false);
            List<String> parsedTooltip = new ArrayList();
            boolean first = true;

            for (String s : tooltipData) {
                String s_ = s;
                if (!first)
                    s_ = EnumChatFormatting.GRAY + s;
                parsedTooltip.add(s_);
                first = false;
            }

            minefantasy.mf2.api.helpers.RenderHelper.renderTooltip(x, y, parsedTooltip);
        }

    }

    private void renderRecipe(GuiScreen parent, int mx, int my, float f, int posX, int posY) {
        renderResult(parent, output, false, posX, posY + 74, mx, my);
        renderResult(parent, input, false, posX, posY, mx, my);
    }

    public void renderResult(GuiScreen gui, ItemStack stack, boolean accountForContainer, int xOrigin, int yOrigin,
                             int mx, int my) {
        if (stack == null || stack.getItem() == null)
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

    public void renderItem(GuiScreen gui, int xPos, int yPos, ItemStack stack, boolean accountForContainer, int mx,
                           int my) {
        RenderItem render = new RenderItem();
        if (mx > xPos && mx < (xPos + 16) && my > yPos && my < (yPos + 16)) {
            tooltipStack = stack;
        }

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        render.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer,
                Minecraft.getMinecraft().getTextureManager(), stack, xPos, yPos);
        render.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer,
                Minecraft.getMinecraft().getTextureManager(), stack, xPos, yPos);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();

        GL11.glDisable(GL11.GL_LIGHTING);
    }

    @Override
    public void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
    }
}
