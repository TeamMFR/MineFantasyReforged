package minefantasy.mfr.client.gui;

import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.tile.TileEntityForge;
import minefantasy.mfr.util.TextureHelperMFR;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiForge extends GuiContainer {
	private TileEntityForge tile;

	public GuiForge(ContainerBase container, TileEntityForge tile) {
		super(container);
		this.ySize = 175;
		this.tile = tile;
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
		this.mc.getTextureManager().bindTexture(TextureHelperMFR.getResource(getTex()));
		int xPoint = (this.width - this.xSize) / 2;
		int yPoint = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.xSize, this.ySize);

		if (this.tile.fuel > 0 && tile.maxFuel > 0) {
			int scale = tile.getMetreScale(12);
			this.drawTexturedModalRect(xPoint + 33, yPoint + 63 + 11 - scale, 176, 11 - scale + 8, 14, scale + 1);
		}
		int[] scale = tile.getTempsScaled(53);

		if (this.tile.temperature > 0) {
			if (this.tile.isLit()) {
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
}