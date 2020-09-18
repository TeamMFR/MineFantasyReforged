package minefantasy.mfr.client.gui;

import minefantasy.mfr.api.helpers.TextureHelperMFR;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.tile.TileEntityBigFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.lwjgl.opengl.GL11;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 */
public class GuiBigFurnace extends GuiContainer {
    private TileEntityBigFurnace smelter;

    public GuiBigFurnace(ContainerBase container, TileEntityBigFurnace tile) {
        super(container);
        smelter = tile;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    protected void drawGuiContainerForegroundLayer(int x, int y) {
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (smelter.isHeater()) {
            this.mc.getTextureManager().bindTexture(TextureHelperMFR.getResource("textures/gui/furnace_heater.png"));
        } else {
            this.mc.getTextureManager().bindTexture(TextureHelperMFR.getResource("textures/gui/furnace_top.png"));
        }
        int posX = (width - xSize) / 2;
        int posY = (height - ySize) / 2;

        drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);

        if (this.smelter.isBurning()) {
            if (smelter.isHeater()) {
                // FUEL
                int fuelSc = this.smelter.getBurnTimeRemainingScaled(12);
                if (smelter.fuel > 0)
                    this.drawTexturedModalRect(posX + 59, posY + 27 + 12 - fuelSc, 176, 12 - fuelSc, 14, fuelSc + 2);

                // HEATMAX
                int heatScM = this.smelter.getItemHeatScaled(68);
                this.drawTexturedModalRect(posX + 104, posY + 76 - heatScM, 176, 14, 16, 3);

                // HEAT
                int heatSc = this.smelter.getHeatScaled(68);
                this.drawTexturedModalRect(posX + 107, posY + 76 - heatSc, 176, 17, 10, 5);
            }
            else {
                if (smelter.progress > 0) {
                    int prog = smelter.getCookProgressScaled(24);
                    drawTexturedModalRect(posX + 76, posY + 34, 176, 0, prog + 1, 16);
                }
            }
        }
    }
}