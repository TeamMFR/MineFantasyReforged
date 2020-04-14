package minefantasy.mfr.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.api.helpers.TextureHelperMFR;
import minefantasy.mfr.entity.EntityShrapnel;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class RenderShrapnel extends Render {
    private String tex;

    public RenderShrapnel(String tex) {
        super(Minecraft.getMinecraft().getRenderManager());
        this.tex = tex;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float xr, float yr) {
        if (entity instanceof EntityShrapnel) {
            render(entity, ((EntityShrapnel) entity).getTexture(), x, y, z, xr, yr);
        } else {
            render(entity, this.tex, x, y, z, xr, yr);
        }
    }

    public void render(Entity entity, String tex, double x, double y, double z, float xr, float yr) {
        this.loadTexture("textures/projectile/" + tex + ".png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * yr - 90.0F, 0.0F, 1.0F,
                0.0F);
        GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * yr, 0.0F, 0.0F,
                1.0F);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        byte var11 = 0;

        float u1 = 0.0F;
        float u2 = 0.15625F;
        float u3 = 0.0F;
        float u4 = 0.5F;
        float v1 = (5 + var11 * 10) / 32.0F;
        float v2 = (10 + var11 * 10) / 32.0F;
        float v3 = (0 + var11 * 10) / 32.0F;
        float v4 = (5 + var11 * 10) / 32.0F;
        float var20 = 0.05625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float var21 = -yr;

        if (var21 > 0.0F) {
            float var22 = -MathHelper.sin(var21 * 3.0F) * var21;
            GL11.glRotatef(var22, 0.0F, 0.0F, 1.0F);
        }

        GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(var20, var20, var20);
        GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
        GL11.glNormal3f(var20, 0.0F, 0.0F);
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(-7.0D, -2.0D, -2.0D).tex(u1, v1);
        bufferBuilder.pos(-7.0D, -2.0D, 2.0D).tex(u2, v1);
        bufferBuilder.pos(-7.0D, 2.0D, 2.0D).tex( u2, v2);
        bufferBuilder.pos(-7.0D, 2.0D, -2.0D).tex(u1, v2);
        bufferBuilder.finishDrawing();
        GL11.glNormal3f(-var20, 0.0F, 0.0F);
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(-7.0D, 2.0D, -2.0D).tex(u1, v1);
        bufferBuilder.pos(-7.0D, 2.0D, 2.0D).tex(u2, v1);
        bufferBuilder.pos(-7.0D, -2.0D, 2.0D).tex(u2, v2);
        bufferBuilder.pos(-7.0D, -2.0D, -2.0D).tex(u1, v2);
        bufferBuilder.finishDrawing();

        for (int var23 = 0; var23 < 4; ++var23) {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, var20);
            bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            bufferBuilder.pos(-8.0D, -2.0D, 0.0D).tex(u3, v3);
            bufferBuilder.pos(8.0D, -2.0D, 0.0D).tex(u4, v3);
            bufferBuilder.pos(8.0D, 2.0D, 0.0D).tex(u4, v4);
            bufferBuilder.pos(-8.0D, 2.0D, 0.0D).tex(u3, v4);
            bufferBuilder.finishDrawing();
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }

    private void loadTexture(String image) {
        bindTexture(TextureHelperMFR.getResource(image));
    }
}
