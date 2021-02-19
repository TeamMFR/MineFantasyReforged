package minefantasy.mfr.client.render.entity;

import minefantasy.mfr.api.helpers.TextureHelperMFR;
import minefantasy.mfr.client.model.entity.ModelHound;
import minefantasy.mfr.entity.mob.EntityHound;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHound extends RenderLiving<EntityHound> {
    private static ModelBase houndModel = new ModelHound();

    public RenderHound(RenderManager renderManager) {
        super(renderManager, houndModel, 0.5F);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    @Override
    protected float handleRotationFloat(EntityHound mob, float f) {
        return mob.getTailRotation();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(EntityHound entity) {
        return TextureHelperMFR.getResource("textures/models/animal/hound/hound.png");
    }

}