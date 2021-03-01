package minefantasy.mfr.client.gui;

import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.tile.TileEntityBloomery;
import minefantasy.mfr.util.TextureHelperMFR;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiBloomery extends GuiContainer {

	public GuiBloomery(ContainerBase container, TileEntityBloomery tile) {
		super(container);
		this.ySize = 208;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TextureHelperMFR.getResource("textures/gui/bloomery.png"));
		int xPoint = (this.width - this.xSize) / 2;
		int yPoint = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.xSize, this.ySize);
	}
}