package minefantasy.mf2.client.render.mob;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.entity.mob.EntityHound;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHound extends RenderLiving {
    public RenderHound(ModelBase modelbase) {
        super(modelbase, 1.0F);
        this.setRenderPassModel(modelbase);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityHound mob, float f) {
        return mob.getTailRotation();
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityHound hound, int layer, float f) {
        if (layer == 0 && hound.getWolfShaking()) {
            float f1 = hound.getBrightness(f) * hound.getShadingWhileShaking(f);
            this.bindTexture("hound");
            GL11.glColor3f(f1, f1, f1);
            return 1;
        } else if (layer == 1 && hound.isTamed()) {
            this.bindTexture("collar");
            int j = hound.getCollarColor();
            GL11.glColor3f(EntitySheep.fleeceColorTable[j][0], EntitySheep.fleeceColorTable[j][1],
                    EntitySheep.fleeceColorTable[j][2]);
            return 1;
        } else {
            return -1;
        }
    }

    private void bindTexture(String string) {
        bindTexture(TextureHelperMF.getResource("textures/models/animal/hound/" + string + ".png"));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityHound p_110775_1_) {
        return TextureHelperMF.getResource("textures/models/animal/hound/hound.png");
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
        return this.shouldRenderPass((EntityHound) p_77032_1_, p_77032_2_, p_77032_3_);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityLivingBase p_77044_1_, float p_77044_2_) {
        return this.handleRotationFloat((EntityHound) p_77044_1_, p_77044_2_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return this.getEntityTexture((EntityHound) p_110775_1_);
    }
}