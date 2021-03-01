package minefantasy.mfr.client.gui;

import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.tile.blastfurnace.TileEntityBlastChamber;
import minefantasy.mfr.util.TextureHelperMFR;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.lwjgl.opengl.GL11;


public class GuiBlastChamber extends GuiContainer {
    private TileEntityBlastChamber tile;

    public GuiBlastChamber(final ContainerBase container, TileEntityBlastChamber tile) {
        super(container);
        this.ySize = 208;
        this.tile = tile;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
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
        this.mc.getTextureManager().bindTexture(TextureHelperMFR.getResource("textures/gui/blast_chamber.png"));
        int xPoint = (this.width - this.xSize) / 2;
        int yPoint = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.xSize, this.ySize);
    }
}