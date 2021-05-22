package minefantasy.mfr.client.render.entity;

import minefantasy.mfr.client.model.entity.ModelParachute;
import minefantasy.mfr.entity.EntityParachute;
import minefantasy.mfr.util.TextureHelperMFR;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderParachute extends Render<EntityParachute> {
	protected static final ModelBase modelParachute = new ModelParachute();

	public RenderParachute(RenderManager renderManager) {
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
	@Override
	public void doRender(EntityParachute entity, double x, double y, double z, float pitch, float yaw) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y + 1.5F, (float) z);
		GL11.glRotatef(180.0F - pitch, 0.0F, 1.0F, 0.0F);
		float f2 = entity.getTimeSinceHit() - yaw;
		float f3 = entity.getDamageTaken() - yaw;

		if (f3 < 0.0F) {
			f3 = 0.0F;
		}

		if (f2 > 0.0F) {
			GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0F * entity.getForwardDirection(), 1.0F, 0.0F, 0.0F);
		}

		float f4 = 1F;
		GL11.glTranslatef(-0.5F, 0F, 0F);
		GL11.glScalef(f4, f4, f4);
		GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
		this.bindEntityTexture(entity);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		modelParachute.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless
	 * you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityParachute p_110775_1_) {
		return TextureHelperMFR.getResource("textures/models/object/parachute.png");
	}
}