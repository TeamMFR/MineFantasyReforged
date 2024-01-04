package minefantasy.mfr.client.render.block;

import minefantasy.mfr.tile.TileEntityKitchenBench;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;
@Mod.EventBusSubscriber(Side.CLIENT)
public class TileEntityKitchenBenchRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T> {
	static TextureAtlasSprite TEXTURE;

	public TileEntityKitchenBenchRenderer() {}

	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (te instanceof TileEntityKitchenBench) {
			render((TileEntityKitchenBench) te, x, y, z);
		}
	}

	void render(TileEntityKitchenBench tile, double x, double y, double z) {
		GlStateManager.pushMatrix();

		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();

		buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);

		GlStateManager.color(1, 1, 1, tile.getDirtyProgress() / 100F);

		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		float u0 = TEXTURE.getInterpolatedU(0);
		float u1 = TEXTURE.getInterpolatedU(16);
		float v0 = TEXTURE.getInterpolatedV(0);
		float v1 = TEXTURE.getInterpolatedV(16);
		double yOffset = 1.0005;
		buffer.pos(x + 0, y + yOffset, z + 0).tex(u0,v0).endVertex(); // Triangle 1: 0,1,0
		buffer.pos(x + 0, y + yOffset, z + 1).tex(u1,v0).endVertex(); // 0,1,1
		buffer.pos(x + 1, y + yOffset, z + 0).tex(u0,v1).endVertex(); // 1,1,0
		buffer.pos(x + 0, y + yOffset, z + 1).tex(u1,v0).endVertex(); // Triangle 2: 0,1,1
		buffer.pos(x + 1, y + yOffset, z + 1).tex(u1,v1).endVertex(); // 1,1,1
		buffer.pos(x + 1, y + yOffset, z + 0).tex(u0,v1).endVertex(); // 1,1,0
		tessellator.draw();

		GlStateManager.enableLighting();
		GlStateManager.disableBlend();

		GlStateManager.popMatrix();
	}

	@SubscribeEvent
	public static void onTextureStitchEvent(TextureStitchEvent.Pre event){
		TEXTURE = event.getMap().registerSprite(new ResourceLocation("minefantasyreforged:blocks/dirty_overlay"));
	}
}
