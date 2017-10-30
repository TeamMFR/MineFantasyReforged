package minefantasy.mf2.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.block.tileentity.TileEntityForge;
import minefantasy.mf2.container.ContainerForge;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiForge extends GuiContainer {
    private TileEntityForge tile;

    public GuiForge(InventoryPlayer user, TileEntityForge tile) {
        super(new ContainerForge(user, tile));
        this.ySize = 175;
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
        this.mc.getTextureManager().bindTexture(TextureHelperMF.getResource(getTex()));
        int xPoint = (this.width - this.xSize) / 2;
        int yPoint = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.xSize, this.ySize);

        if (this.tile.fuel > 0 && tile.maxFuel > 0) {
            int scale = tile.getMetreScale(12);
            this.drawTexturedModalRect(xPoint + 33, yPoint + 63 + 11 - scale, 176, 11 - scale + 8, 14, scale + 1);
        }
        int[] scale = tile.getTempsScaled(53);

        if (this.tile.temperature > 0) {
            if (this.tile.isLit) {
                int heatScM = scale[1];
                this.drawTexturedModalRect(xPoint + 32, yPoint + 58 - heatScM, 176, 0, 16, 3);
            }
            int heatSc = scale[0];
            this.drawTexturedModalRect(xPoint + 35, yPoint + 58 - heatSc, 176, 3, 10, 5);
        }
    }

    private String getTex() {
        return "textures/gui/" + tile.getTextureName() + ".png";
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
    }
}