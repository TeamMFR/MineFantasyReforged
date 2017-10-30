package minefantasy.mf2.client.render.block;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.block.tileentity.decor.TileEntityAmmoBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class TileEntityAmmoBoxRenderer extends TileEntitySpecialRenderer {
    private ModelAmmoBox modelbig, model, modelsml;
    private Random random = new Random();

    public TileEntityAmmoBoxRenderer() {
        model = new ModelAmmoBox();
        modelbig = new ModelSmallCrate();
        modelsml = new ModelFoodBox();
    }

    public void renderAModelAt(TileEntityAmmoBox tile, double d, double d1, double d2, float f) {
        int i = 0;
        if (tile.getWorldObj() != null) {
            i = tile.getBlockMetadata();
        }
        this.renderModelAt(tile, i, d, d1, d2, f);
    }

    public void renderInvModel(String tex, byte type, CustomMaterial material, double d, double d1, double d2,
                               float f) {
        ModelAmmoBox baseMdl = type == 0 ? modelsml : type == 1 ? model : modelbig;
        int i = 2;
        int j = 450 - (90 * i);

        GL11.glPushMatrix(); // start
        float scale = 1.0F;
        float yOffset = 1 / 16F;
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
        GL11.glRotatef(j + 90, 0.0F, 1.0F, 0.0F); // rotate based on metadata
        GL11.glScalef(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();
        float level = 0F;

        GL11.glColor3f(material.colourRGB[0] / 255F, material.colourRGB[1] / 255F, material.colourRGB[2] / 255F);

        bindTextureByName("textures/models/tileentity/" + tex + "_base.png"); // texture
        baseMdl.renderModel(0.0625F);
        baseMdl.renderLid(0.0625F, 0F);

        GL11.glColor3f(1F, 1F, 1F);

        bindTextureByName("textures/models/tileentity/" + tex + "_detail.png"); // texture
        baseMdl.renderModel(0.0625F);
        baseMdl.renderLid(0.0625F, 0F);
        GL11.glPopMatrix();
        GL11.glColor3f(1F, 1F, 1F);
        GL11.glPopMatrix(); // end

    }

    public void renderModelAt(TileEntityAmmoBox tile, int meta, double d, double d1, double d2, float f) {
        ModelAmmoBox baseMdl = tile.getStorageType() == 0 ? modelsml : tile.getStorageType() == 1 ? model : modelbig;
        int i = meta;
        if (!tile.hasWorldObj()) {
            i = 2;
        }

        int j = 450 - (90 * i);

        GL11.glPushMatrix(); // start
        float scale = 1.0F;
        float yOffset = 1 / 16F;
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
        GL11.glRotatef(j + 90, 0.0F, 1.0F, 0.0F); // rotate based on metadata
        GL11.glScalef(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();
        float level = 0F;

        CustomMaterial material = tile.getMaterial();
        GL11.glColor3f(material.colourRGB[0] / 255F, material.colourRGB[1] / 255F, material.colourRGB[2] / 255F);

        bindTextureByName("textures/models/tileentity/" + tile.getTexName() + "_base.png"); // texture
        baseMdl.renderModel(0.0625F);
        baseMdl.renderLid(0.0625F, tile.angle);

        GL11.glColor3f(1F, 1F, 1F);

        bindTextureByName("textures/models/tileentity/" + tile.getTexName() + "_detail.png"); // texture
        baseMdl.renderModel(0.0625F);
        baseMdl.renderLid(0.0625F, tile.angle);
        GL11.glPopMatrix();
        GL11.glColor3f(1F, 1F, 1F);
        GL11.glPopMatrix(); // end

    }

    private void bindTextureByName(String image) {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureHelperMF.getResource(image));
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        renderAModelAt((TileEntityAmmoBox) tileentity, d, d1, d2, f); // where to render
    }
}
