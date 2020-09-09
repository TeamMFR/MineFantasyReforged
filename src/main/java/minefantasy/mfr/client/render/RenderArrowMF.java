package minefantasy.mfr.client.render;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.api.helpers.TextureHelperMFR;
import minefantasy.mfr.entity.EntityArrowMFR;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderArrowMF extends Render<EntityArrowMFR> {
    public RenderArrowMF(RenderManager renderManager) {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityArrowMFR entity) {
        return null;
    }

    public void renderArrow(EntityArrowMFR arrow, double x, double y, double z, float xr, float yr) {
        this.loadTexture(arrow.getTexture() + ".png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glRotatef(arrow.prevRotationYaw + (arrow.rotationYaw - arrow.prevRotationYaw) * yr - 90.0F, 0.0F, 1.0F,
                0.0F);
        GL11.glRotatef(arrow.prevRotationPitch + (arrow.rotationPitch - arrow.prevRotationPitch) * yr, 0.0F, 0.0F,
                1.0F);
        Tessellator var10 = Tessellator.getInstance();
        byte var11 = 0;
        float var12 = 0.0F;
        float var13 = 0.5F;
        float var14 = (0 + var11 * 10) / 32.0F;
        float var15 = (5 + var11 * 10) / 32.0F;
        float var16 = 0.0F;
        float var17 = 0.15625F;
        float var18 = (5 + var11 * 10) / 32.0F;
        float var19 = (10 + var11 * 10) / 32.0F;
        float var20 = 0.05625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float var21 = arrow.arrowShake - yr;

        if (var21 > 0.0F) {
            float var22 = -MathHelper.sin(var21 * 3.0F) * var21;
            GL11.glRotatef(var22, 0.0F, 0.0F, 1.0F);
        }

        GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(var20, var20, var20);
        GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
        GL11.glNormal3f(var20, 0.0F, 0.0F);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(-7.0D, -2.0D, -2.0D).tex(var16, var18);
        bufferBuilder.pos(-7.0D, -2.0D, 2.0D).tex(var17, var18);
        bufferBuilder.pos(-7.0D, 2.0D, 2.0D).tex(var17, var19);
        bufferBuilder.pos(-7.0D, 2.0D, -2.0D).tex(var16, var19);
        var10.draw();
        GL11.glNormal3f(-var20, 0.0F, 0.0F);
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(-7.0D, 2.0D, -2.0D).tex(var16, var18);
        bufferBuilder.pos(-7.0D, 2.0D, 2.0D).tex(var17, var18);
        bufferBuilder.pos(-7.0D, -2.0D, 2.0D).tex(var17, var19);
        bufferBuilder.pos(-7.0D, -2.0D, -2.0D).tex(var16, var19);
        var10.draw();

        for (int var23 = 0; var23 < 4; ++var23) {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, var20);
            bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            bufferBuilder.pos(-8.0D, -2.0D, 0.0D).tex(var12, var14);
            bufferBuilder.pos(8.0D, -2.0D, 0.0D).tex(var13, var14);
            bufferBuilder.pos(8.0D, 2.0D, 0.0D).tex(var13, var15);
            bufferBuilder.pos(-8.0D, 2.0D, 0.0D).tex(var12, var15);
            var10.draw();
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    private void loadTexture(String image) {
        bindTexture(TextureHelperMFR.getResource(image));
    }
}
