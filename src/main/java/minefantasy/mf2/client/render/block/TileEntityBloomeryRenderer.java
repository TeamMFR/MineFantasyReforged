package minefantasy.mf2.client.render.block;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.block.tileentity.TileEntityBloomery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class TileEntityBloomeryRenderer extends TileEntitySpecialRenderer {
    private ModelBloomery model;
    private Random random = new Random();

    public TileEntityBloomeryRenderer() {
        model = new ModelBloomery();
    }

    public void renderAModelAt(TileEntityBloomery tile, double d, double d1, double d2, float f) {
        int i = 0;
        if (tile.getWorldObj() != null) {
            i = tile.getBlockMetadata();
        }
        this.renderModelAt(tile, tile.getTextureName(), i, d, d1, d2, f, tile.getWorldObj() == null);
    }

    public void renderModelAt(TileEntityBloomery tile, String tex, int meta, double d, double d1, double d2, float f,
                              boolean inv) {
        int i = meta;

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

        bindTextureByName("textures/models/tileentity/" + tex + ".png"); // texture

        GL11.glPushMatrix(); // start
        float scale = 1.0F;
        float yOffset = 1.0F;
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
        GL11.glRotatef(j - 90F, 0.0F, 1.0F, 0.0F); // rotate based on metadata
        GL11.glScalef(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();
        model.renderModel(tile, 0.0625F);

        GL11.glPopMatrix();
        GL11.glColor3f(255, 255, 255);
        GL11.glPopMatrix(); // end

    }

    public void renderInvModel(String tex, double d, double d1, double d2, float f) {
        int j = 90;
        bindTextureByName("textures/models/tileentity/" + tex + ".png"); // texture

        GL11.glPushMatrix(); // start
        float scale = 1.0F;
        float yOffset = 1.0F;
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
        GL11.glRotatef(j - 90F, 0.0F, 1.0F, 0.0F); // rotate based on metadata
        GL11.glScalef(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();
        model.renderModel(null, 0.0625F);

        GL11.glPopMatrix();
        GL11.glColor3f(255, 255, 255);
        GL11.glPopMatrix(); // end

    }

    private void bindTextureByName(String image) {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureHelperMF.getResource(image));
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityBloomery) tileentity, d, d1, d2, f); // where to render
    }

}
