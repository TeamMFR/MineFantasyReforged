package minefantasy.mfr.client.gui;

import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.tile.TileEntityCrucible;
import minefantasy.mfr.util.TextureHelperMFR;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemBlock;
import org.lwjgl.opengl.GL11;

public class GuiCrucible extends GuiContainer {

    private TileEntityCrucible tile;

    public GuiCrucible(final ContainerBase container, TileEntityCrucible tile) {
        super(container);

        this.tile = tile;

        allowUserInput = false;
        this.ySize = 186;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TextureHelperMFR.getResource(getTex()));
        int xPoint = (this.width - this.xSize) / 2;
        int yPoint = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.xSize, this.ySize);

        if (!tile.inventory.getStackInSlot(tile.inventory.getSlots() - 1).isEmpty() && !(tile.inventory.getStackInSlot(tile.inventory.getSlots() - 1).getItem() instanceof ItemBlock)
                && ConfigHardcore.HCCreduceIngots && !tile.isAuto()) {
            this.drawTexturedModalRect(xPoint + 128, yPoint + 31, 225, 2, 18, 18);
        }

        if (this.tile.getTemperature() > 0) {
            this.drawTexturedModalRect(xPoint + 81, yPoint + 75, 243, 0, 14, 12);
        }
        if (this.tile.getProgress() > 0 && tile.getProgressMax() > 0) {
            int value = (int) (54F / tile.getProgressMax() * tile.getProgress());
            this.drawTexturedModalRect(xPoint + 61, yPoint + 70, 189, 0, value, 2);
        }
    }

    private String getTex() {
        if (tile.getTier() == 1) {
            return "textures/gui/crucible_advanced.png";
        }
        if (tile.getTier() >= 2) {
            return "textures/gui/crucible_mythic.png";
        }
        return "textures/gui/crucible.png";
    }
}
