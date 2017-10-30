package minefantasy.mf2.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.entity.EntityParachute;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderParachute extends Render {
    protected ModelBase modelParachute;

    public RenderParachute() {
        this.shadowSize = 1F;
        this.modelParachute = new ModelParachute();
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method,
     * always casting down its argument and then handing it off to a worker function
     * which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void
     * func_76986_a(T entity, double d, double d1, double d2, float f, float f1).
     * But JAD is pre 1.5 so doesn't do that.
     */
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
        this.modelParachute.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityParachute p_110775_1_) {
        return TextureHelperMF.getResource("textures/models/object/parachute.png");
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return this.getEntityTexture((EntityParachute) p_110775_1_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method,
     * always casting down its argument and then handing it off to a worker function
     * which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void
     * func_76986_a(T entity, double d, double d1, double d2, float f, float f1).
     * But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
                         float p_76986_9_) {
        this.doRender((EntityParachute) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}