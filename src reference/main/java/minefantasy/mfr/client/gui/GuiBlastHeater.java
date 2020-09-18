package minefantasy.mfr.client.gui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.api.helpers.TextureHelperMFR;
import minefantasy.mfr.tile.blastfurnace.TileEntityBlastFH;
import minefantasy.mfr.container.ContainerBlastHeater;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiBlastHeater extends GuiContainer {
    private TileEntityBlastFH tile;

    public GuiBlastHeater(InventoryPlayer user, TileEntityBlastFH tile) {
        super(new ContainerBlastHeater(user, tile));
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
        this.mc.getTextureManager().bindTexture(TextureHelperMFR.getResource("textures/gui/blast_heater.png"));
        int xPoint = (this.width - this.xSize) / 2;
        int yPoint = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.xSize, this.ySize);

        if (this.tile.isBurning()) {
            int i1 = this.tile.getBurnTimeRemainingScaled(13);
            this.drawTexturedModalRect(xPoint + 82, yPoint + 42 + 12 - i1, 243, 12 - i1, 14, i1 + 1);
        }
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
    }
}