package minefantasy.mfr.client.render;

import minefantasy.mfr.entity.EntityArrowMFR;
import minefantasy.mfr.entity.EntityBomb;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderBomb extends Render<EntityBomb> {
    private BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

    public RenderBomb() {
        super(Minecraft.getMinecraft().getRenderManager());
        this.shadowSize = 0.25F;
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method,
     * always casting down its argument and then handing it off to a worker function
     * which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void
     * func_76986_a(T entity, double d, double d1, double d2, float f, float f1).
     * But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityBomb mine, double x, double y, double z, float f, float f1) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        this.blockrendererdispatcher.renderBlockBrightness(Blocks.HARDENED_CLAY.getDefaultState(), mine.getBrightness());
        GL11.glPopMatrix();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(EntityBomb entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

}