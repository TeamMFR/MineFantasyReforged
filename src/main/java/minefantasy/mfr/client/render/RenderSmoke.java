package minefantasy.mfr.client.render;

import minefantasy.mfr.entity.EntitySmoke;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderSmoke extends Render<EntitySmoke> {
    public RenderSmoke(RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0F;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySmoke entity) {
        return null;
    }
}