package minefantasy.mfr.client.render.mob;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.api.helpers.TextureHelperMFR;
import minefantasy.mfr.entity.mob.EntityHound;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHound extends RenderLiving<EntityHound> {
    public RenderHound(ModelBase modelbase) {
        super(Minecraft.getMinecraft().getRenderManager(), modelbase, 1.0F);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    @Override
    protected float handleRotationFloat(EntityHound mob, float f) {
        return mob.getTailRotation();
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityHound hound, int layer, float f) {
        if (layer == 0 && hound.getWolfShaking()) {
            float f1 = hound.getBrightness() * hound.getShadingWhileShaking(f);
            this.bindTexture("hound");
            GL11.glColor3f(f1, f1, f1);
            return 1;
        } else if (layer == 1 && hound.isTamed()) {
            this.bindTexture("collar");
            int i = hound.ticksExisted / 25 + hound.getEntityId();
            int j = EnumDyeColor.values().length;
            int k = i % j;
            EnumDyeColor collarColor = hound.getCollarColor();
            float[] afloat1 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(k));
            float[] afloat2 = EntitySheep.getDyeRgb(collarColor);
            GlStateManager.color(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
            return 1;
        } else {
            return -1;
        }
    }

    private void bindTexture(String string) {
        bindTexture(TextureHelperMFR.getResource("textures/models/animal/hound/" + string + ".png"));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(EntityHound p_110775_1_) {
        return TextureHelperMFR.getResource("textures/models/animal/hound/hound.png");
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
        return this.shouldRenderPass((EntityHound) p_77032_1_, p_77032_2_, p_77032_3_);
    }
}