package minefantasy.mf2.client.render.block;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.block.tileentity.TileEntityQuern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class TileEntityQuernRenderer extends TileEntitySpecialRenderer {
    private ModelQuern model;
    private Random random = new Random();

    public TileEntityQuernRenderer() {
        model = new ModelQuern();
    }

    public void renderAModelAt(TileEntityQuern tile, double d, double d1, double d2, float f) {
        this.renderModelAt(tile, tile.getTextureName(), d, d1, d2, f, tile.getWorldObj() == null);
    }

    public void renderModelAt(TileEntityQuern tile, String tex, double d, double d1, double d2, float f, boolean inv) {
        bindTextureByName("textures/models/tileentity/" + tex + ".png"); // texture

        GL11.glPushMatrix(); // start
        float scale = 1.0F;
        float yOffset = 1.0F;
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
        GL11.glScalef(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();
        model.renderModel(tile, 0.0625F);

        GL11.glPopMatrix();
        GL11.glColor3f(255, 255, 255);
        GL11.glPopMatrix(); // end

    }

    private void bindTextureByName(String image) {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureHelperMF.getResource(image));
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityQuern) tileentity, d, d1, d2, f); // where to render
    }

}
