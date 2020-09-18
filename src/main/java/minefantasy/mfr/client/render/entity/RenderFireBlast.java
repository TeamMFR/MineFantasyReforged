package minefantasy.mfr.client.render.entity;

import minefantasy.mfr.entity.EntityFireBlast;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderFireBlast extends Render<EntityFireBlast> {
    public RenderFireBlast(RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0F;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityFireBlast entity) {
        return null;
    }
}