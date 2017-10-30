package minefantasy.mf2.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.block.tileentity.TileEntityResearch;
import minefantasy.mf2.container.ContainerResearch;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiResearchBlock extends GuiContainer {
    private TileEntityResearch tile;

    public GuiResearchBlock(InventoryPlayer user, TileEntityResearch tile) {
        super(new ContainerResearch(user, tile));
        this.ySize = 158;
        this.xSize = 178;
        this.tile = tile;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the
     * items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TextureHelperMF.getResource("textures/gui/knowledge/research.png"));
        int xPoint = (this.width - this.xSize) / 2;
        int yPoint = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.xSize, this.ySize);

        int scale = tile.getMetreScale(161);
        this.drawTexturedModalRect(xPoint + 10, yPoint + 33, 0, 158, scale, 3);
        fontRendererObj.drawString(tile.getLocalisedName(), xPoint + 9, yPoint + 11, 0);
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
    }
}