package minefantasy.mf2.client.render.block;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.block.tileentity.TileEntityBigFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 */
public class TileEntityBigFurnaceRenderer extends TileEntitySpecialRenderer {
    private ModelBigFurnace model;

    public TileEntityBigFurnaceRenderer() {
        model = new ModelBigFurnace();
    }

    public void renderAModelAt(TileEntityBigFurnace tile, double d, double d1, double d2, float f) {
        int i = 1;
        if (tile.hasWorldObj()) {
            i = tile.getBlockMetadata();
        }

        int j = 90 * i;

        if (i == 0) {
            j = 0;
        }

        if (i == 1) {
            j = 270;
        }

        if (i == 2) {
            j = 180;
        }

        if (i == 3) {
            j = 90;
        }
        if (i == 4) {
            j = 90;
        }

        String type = tile.getTexture();

        bindTextureByName("textures/models/tileentity/" + type + ".png"); // texture

        boolean display = tile.isBurning();

        GL11.glPushMatrix(); // start
        float scale = 1.0F;

        float offset = (1.0F - 0.0625F);
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + (0.0635F * 2F), (float) d2 + 0.5F); // size
        GL11.glRotatef(j - 90, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(scale, -scale, -scale);
        model.renderModel(display, 0.0625F);

        float sc = 0.75F;
        float angle = 90F / 20F * tile.doorAngle;
        GL11.glPushMatrix();
        GL11.glTranslatef(-pixel(8), -pixel(12), 0);
        if (tile.isHeater()) {
            GL11.glTranslatef(0, pixel(12), 0);
        }
        GL11.glPushMatrix();
        GL11.glScalef(sc, sc, sc);
        GL11.glRotatef(90, 0, 1, 0);
        if (tile.isHeater()) {
            GL11.glRotatef(angle, 1F, 0, 0);
            renderDoorHeater(0, 54, 12, 8, 128, 64);
        } else {
            GL11.glRotatef(-angle, 1F, 0, 0);
            renderDoor(0, 54, 12, 8, 128, 64);
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();

        GL11.glPopMatrix(); // end

    }

    public void renderInvModel(boolean heater, String type, double d, double d1, double d2, float f) {
        int j = 90;

        bindTextureByName("textures/models/tileentity/" + type + ".png"); // texture

        GL11.glPushMatrix(); // start
        float scale = 1.0F;

        float offset = (1.0F - 0.0625F);
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + (0.0635F * 2F), (float) d2 + 0.5F); // size
        GL11.glRotatef(j - 90, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(scale, -scale, -scale);
        model.renderModel(false, 0.0625F);

        float sc = 0.75F;
        float angle = 0F;
        GL11.glPushMatrix();
        GL11.glTranslatef(-pixel(8), -pixel(12), 0);
        if (heater) {
            GL11.glTranslatef(0, pixel(12), 0);
        }
        GL11.glPushMatrix();
        GL11.glScalef(sc, sc, sc);
        GL11.glRotatef(90, 0, 1, 0);
        if (heater) {
            GL11.glRotatef(angle, 1F, 0, 0);
            renderDoorHeater(0, 54, 12, 8, 128, 64);
        } else {
            GL11.glRotatef(-angle, 1F, 0, 0);
            renderDoor(0, 54, 12, 8, 128, 64);
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();

        GL11.glPopMatrix(); // end

    }

    private void bindTextureByName(String image) {
        bindTexture(TextureHelperMF.getResource(image));
    }

    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityBigFurnace) tileentity, d, d1, d2, f); // where to render
    }

    private void renderDoor(int x, int y, int w, int h, int tw, int th) {
        Minecraft mc = Minecraft.getMinecraft();

        float f = 0.01F / tw;
        float f1 = 0.01F / th;

        float x1 = (float) x / (float) tw + f;
        float x2 = (float) (x + w) / tw - f;
        float y1 = (float) y / th + f1;
        float y2 = (float) (y + h) / th - f1;

        Tessellator image = Tessellator.instance;
        float xPos = 0.5F;
        float yPos = 0.0F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-xPos, -yPos, pixel(0.5F));
        float var13 = 1F;
        GL11.glScalef(var13, var13, var13);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-1F, -1F, 0.0F);
        ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, tw, th, 0.0625F);

    }

    @Override
    protected void bindTexture(ResourceLocation p_147499_1_) {
        TextureManager texturemanager = TileEntityRendererDispatcher.instance.field_147553_e;

        if (texturemanager != null) {
            texturemanager.bindTexture(p_147499_1_);
        }
    }

    private void renderDoorHeater(int x, int y, int w, int h, int tw, int th) {
        Minecraft mc = Minecraft.getMinecraft();

        float f = 0.01F / tw;
        float f1 = 0.01F / th;

        float x1 = (float) x / (float) tw + f;
        float x2 = (float) (x + w) / tw - f;
        float y1 = (float) y / th + f1;
        float y2 = (float) (y + h) / th - f1;

        Tessellator image = Tessellator.instance;
        float xPos = 0.5F;
        float yPos = 1.0F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-xPos, -yPos, pixel(0.5F));
        float var13 = 1F;
        GL11.glScalef(var13, var13, var13);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-1F, -1F, 0.0F);
        ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, tw, th, 0.0625F);

    }

    public float pixel(float count) {
        return count * 0.0625F;
    }
}