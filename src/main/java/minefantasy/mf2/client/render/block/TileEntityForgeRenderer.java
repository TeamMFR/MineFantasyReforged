package minefantasy.mf2.client.render.block;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.block.tileentity.TileEntityForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class TileEntityForgeRenderer extends TileEntitySpecialRenderer {
    private ModelForge model;
    private ModelForgeTop topModel;
    private Random random = new Random();

    public TileEntityForgeRenderer() {
        model = new ModelForge();
        topModel = new ModelForgeTop();
    }

    public void renderAModelAt(TileEntityForge tile, double d, double d1, double d2, float f) {
        int i = 0;
        if (tile.getWorldObj() != null) {
            i = tile.getBlockMetadata();
        }
        this.renderModelAt(tile, tile.getTextureName(), i, d, d1, d2, f, tile.getWorldObj() == null, tile.hasFuel());
    }

    public void renderModelAt(TileEntityForge tile, String tex, int meta, double d, double d1, double d2, float f,
                              boolean inv, boolean hasFuel) {
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
        float yOffset = inv ? 1.75F : 1.5F;
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
        GL11.glRotatef(j - 90F, 0.0F, 1.0F, 0.0F); // rotate based on metadata
        GL11.glScalef(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();
        float level = 0F;
        if (tile != null && tile.fuel > 0 && tile.maxFuel > 0) {
            level = tile.fuel / tile.maxFuel;
        }
        model.renderModel(tile, 0.0625F, hasFuel, level);

        if (tile != null && tile.hasBlockAbove()) {
            bindTextureByName("textures/models/tileentity/forge_top.png"); // texture
            topModel.render(0.0625F);
        }
        GL11.glPopMatrix();
        GL11.glColor3f(255, 255, 255);
        GL11.glPopMatrix(); // end

    }

    public void renderInvModel(String tex, double d, double d1, double d2, float f) {
        int j = 90;

        bindTextureByName("textures/models/tileentity/" + tex + ".png"); // texture

        GL11.glPushMatrix(); // start
        float scale = 1.0F;
        float yOffset = 1.75F;
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
        GL11.glRotatef(j - 90F, 0.0F, 1.0F, 0.0F); // rotate based on metadata
        GL11.glScalef(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();
        float level = 0F;
        model.renderModel(null, 0.0625F, false, level);

        GL11.glPopMatrix();
        GL11.glColor3f(255, 255, 255);
        GL11.glPopMatrix(); // end

    }

    private void bindTextureByName(String image) {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureHelperMF.getResource(image));
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityForge) tileentity, d, d1, d2, f); // where to render
    }

    private boolean shouldRender(TileEntityForge tile, int p) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer sp = mc.thePlayer;
        if (p == 1)// GRID
        {
            return false;
        }
        return true;
    }
}
