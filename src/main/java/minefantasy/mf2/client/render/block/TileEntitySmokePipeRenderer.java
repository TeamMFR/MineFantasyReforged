package minefantasy.mf2.client.render.block;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.block.tileentity.TileEntityChimney;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class TileEntitySmokePipeRenderer extends TileEntitySpecialRenderer {
    private static ModelSmokePipe model = new ModelSmokePipe();
    private Random random = new Random();

    public TileEntitySmokePipeRenderer() {
    }

    public void renderAModelAt(TileEntityChimney tile, double d, double d1, double d2, float f) {
        if (tile != null && !tile.isPipeChimney()) {
            return;
        }
        GL11.glPushMatrix(); // start
        float scale = 1.0F;
        float yOffset = 0.5F;

        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
        GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F); // rotate based on metadata
        GL11.glScalef(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();

        bindTextureByName("textures/models/tileentity/smoke_pipe.png"); // texture
        model.renderModel(tile, 0.0625F);

        GL11.glPopMatrix();
        GL11.glColor3f(1F, 1F, 1F);
        GL11.glPopMatrix(); // end

    }

    private void bindTextureByName(String image) {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureHelperMF.getResource(image));
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityChimney) tileentity, d, d1, d2, f); // where to render
    }
}
