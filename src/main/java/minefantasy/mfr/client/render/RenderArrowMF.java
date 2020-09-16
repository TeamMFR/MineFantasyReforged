package minefantasy.mfr.client.render;

import minefantasy.mfr.entity.EntityArrowMFR;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderArrowMF extends RenderArrow<EntityArrowMFR> {
    public RenderArrowMF(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    @Nullable
    protected ResourceLocation getEntityTexture(EntityArrowMFR arrow){
        return new ResourceLocation("minefantasyreborn:textures/projectile/" + arrow.getTexture() + ".png");
    }
}
