package minefantasy.mf2.client.render.block;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.block.tileentity.TileEntityFirepit;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityFirepitRenderer extends TileEntitySpecialRenderer {
    private ModelForgeTop topModel;
    private ModelFirepit model;

    public TileEntityFirepitRenderer() {
        model = new ModelFirepit();
        topModel = new ModelForgeTop();
    }

    public void renderAModelAt(TileEntityFirepit tile, double d, double d1, double d2, float f) {
        bindTextureByName("textures/models/tileentity/firepit.png");
        GL11.glPushMatrix();
        float yOffset = 0.0625F * 5F;

        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);

        model.renderModel(tile, 0.0625F);

        if (tile.hasBlockAbove()) {
            GL11.glTranslatef(0F, -(1.5F - yOffset), 0F);
            bindTextureByName("textures/models/tileentity/forge_top.png"); // texture
            topModel.render(0.0625F);
        }
        GL11.glPopMatrix();

    }

    public void renderInvModel(double d, double d1, double d2, float f) {
        bindTextureByName("textures/models/tileentity/firepit.png");
        GL11.glPushMatrix();
        float yOffset = 0.0625F * 5F;

        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);

        model.renderModel(null, 0.0625F);

        GL11.glPopMatrix();

    }

    @Override
    protected void bindTexture(ResourceLocation p_147499_1_) {
        TextureManager texturemanager = TileEntityRendererDispatcher.instance.field_147553_e;

        if (texturemanager != null) {
            texturemanager.bindTexture(p_147499_1_);
        }
    }

    private void bindTextureByName(String image) {
        bindTexture(TextureHelperMF.getResource(image));
    }

    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityFirepit) tileentity, d, d1, d2, f);
    }
}