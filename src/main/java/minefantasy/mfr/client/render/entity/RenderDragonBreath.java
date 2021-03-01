package minefantasy.mfr.client.render.entity;

import minefantasy.mfr.entity.EntityDragonBreath;
import minefantasy.mfr.util.TextureHelperMFR;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderDragonBreath extends Render<EntityDragonBreath> {

	public RenderDragonBreath(RenderManager renderManager) {
		super(renderManager);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker function
	 * which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void
	 * func_76986_a(T entity, double d, double d1, double d2, float f, float f1).
	 * But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(EntityDragonBreath entity, double x, double y, double z, float f, float f1) {
		if (entity.isInvisible())
			return;

		GlStateManager.pushMatrix();
		this.bindEntityTexture(entity);

		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.enableRescaleNormal();
		float baseSize = 1.0F;
		GlStateManager.scale(baseSize + entity.width, baseSize + entity.height, baseSize + entity.width);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableLighting();
		GlStateManager.color(1, 1, 1);
		GlStateManager.depthMask(false);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		this.renderImg(entity, bufferbuilder, tessellator);
		GlStateManager.disableRescaleNormal();
		GlStateManager.enableLighting();
		GlStateManager.color(1, 1, 1);
		GlStateManager.popMatrix();
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityDragonBreath entity) {
		return TextureHelperMFR.getResource(entity.getTextureName() + ".png");
	}

	private void renderImg(EntityDragonBreath breath, BufferBuilder bufferBuilder, Tessellator tessellator) {
		float x1 = 0;
		float x2 = 1;
		float y1 = 0;
		float y2 = 1;
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(breath.ticksExisted * 36, 0.0F, 0.0F, 0.1F);
		bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		bufferBuilder.pos(0.0F - f5, 0.0F - f6, 0.0D).tex(x1, y2).normal(0.0F, 1.0F, 0.0F).endVertex();
		bufferBuilder.pos(f4 - f5, 0.0F - f6, 0.0D).tex(x2, y2).normal(0.0F, 1.0F, 0.0F).endVertex();
		bufferBuilder.pos(f4 - f5, f4 - f6, 0.0D).tex(x2, y1).normal(0.0F, 1.0F, 0.0F).endVertex();
		bufferBuilder.pos(0.0F - f5, f4 - f6, 0.0D).tex(x1, y1).normal(0.0F, 1.0F, 0.0F).endVertex();
		tessellator.draw();
	}
}