package minefantasy.mf2.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.block.tileentity.TileEntityBloomery;
import minefantasy.mf2.container.ContainerBloomery;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiBloomery extends GuiContainer {
    private TileEntityBloomery tile;

    public GuiBloomery(InventoryPlayer user, TileEntityBloomery tile) {
        super(new ContainerBloomery(user, tile));
        this.ySize = 208;
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
        this.mc.getTextureManager().bindTexture(TextureHelperMF.getResource("textures/gui/bloomery.png"));
        int xPoint = (this.width - this.xSize) / 2;
        int yPoint = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
    }
}