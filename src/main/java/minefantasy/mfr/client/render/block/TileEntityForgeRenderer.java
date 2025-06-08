package minefantasy.mfr.client.render.block;

import minefantasy.mfr.client.model.block.ModelForgeFuel;
import minefantasy.mfr.tile.TileEntityForge;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(Side.CLIENT)
public class TileEntityForgeRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T> {
	public static final List<Integer> ANGLES = Arrays.asList(0, 90, 180, 270);
	private final ModelForgeFuel fuel;

	public TileEntityForgeRenderer() {
		fuel = new ModelForgeFuel();
	}

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (te instanceof TileEntityForge) {
			render((TileEntityForge) te, x, y, z);
		}
	}

	void render(TileEntityForge tile, double x, double y, double z) {
		if (tile.getFuel() <= 0) {
			return;
		}
		ResourceLocation texture;
		if (tile.isLit()) {
			texture = new ResourceLocation("minefantasyreforged:textures/blocks/coal_fuel_active.png");
		}
		else {
			texture = new ResourceLocation("minefantasyreforged:textures/blocks/coal_fuel.png");
		}

		this.bindTexture(texture);

		GlStateManager.pushMatrix(); // 1

		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GlStateManager.pushMatrix(); // 2
		float scale = 1F;
		float yOffset = 1.525F;
		GlStateManager.translate((float) x + 0.5F, (float) y + yOffset, (float) z + 0.5F); // size
		GlStateManager.scale(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
		GlStateManager.rotate(tile.getTextureAngle(), 0, 1F, 0);

		GlStateManager.pushMatrix(); // 3

		float height = (tile.getFuel() / tile.getMaxFuel()) * 0.1F - 0.05F;
		fuel.renderModel(0.0666333F, -height);

		GlStateManager.popMatrix(); // 3

		GlStateManager.popMatrix(); // 2

		GlStateManager.enableLighting();
		GlStateManager.disableBlend();

		GlStateManager.popMatrix(); // 1
	}
}
