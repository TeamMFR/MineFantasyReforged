package minefantasy.mf2.client.render.block;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.block.tileentity.TileEntityTanningRack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityTanningRackRenderer extends TileEntitySpecialRenderer {

    private ModelTanningRack model;
    private ModelEngTanningRack engmodel;

    public TileEntityTanningRackRenderer() {
        engmodel = new ModelEngTanningRack();
        model = new ModelTanningRack();
    }

    public void renderAModelAt(TileEntityTanningRack tile, double d, double d1, double d2, float f) {
        if (tile != null)
            ;
        int i = 0;
        if (tile.getWorldObj() != null) {
            i = tile.blockMetadata;
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
        bindTextureByName("textures/models/tileentity/tanner" + tile.tex + ".png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1.45F, (float) d2 + 0.5F);
        GL11.glRotatef(j, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(1.0F, -1F, -1F);
        if (tile.isAutomated()) {
            engmodel.renderModel(0.0625F);
            GL11.glPushMatrix();
            GL11.glTranslatef(0F, tile.acTime, 0F);
            engmodel.renderBlade(0.0625F);
            GL11.glPopMatrix();
            engmodel.rotateLever(tile.acTime);
            engmodel.renderLever(0.0625F);
        } else {
            model.renderModel(0.0625F);
        }
        renderHungItem(tile, d, d1, d2, f);
        GL11.glPopMatrix();

    }

    public void renderInvModel(String tex, boolean isAuto, double d, double d1, double d2, float f) {
        int j = 90;
        bindTextureByName("textures/models/tileentity/tanner" + tex + ".png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1.45F, (float) d2 + 0.5F);
        GL11.glRotatef(j, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(1.0F, -1F, -1F);
        if (isAuto) {
            engmodel.renderModel(0.0625F);
            GL11.glPushMatrix();
            engmodel.renderBlade(0.0625F);
            GL11.glPopMatrix();
            engmodel.renderLever(0.0625F);
        } else {
            model.renderModel(0.0625F);
        }
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
        renderAModelAt((TileEntityTanningRack) tileentity, d, d1, d2, f);
    }

    private void renderHungItem(TileEntityTanningRack tile, double d, double d1, double d2, float f) {
        Minecraft mc = Minecraft.getMinecraft();
        ItemStack itemstack = tile.getStackInSlot(0);
        if (itemstack != null && itemstack.getItem().getIconFromDamage(itemstack.getItemDamage()) != null) {
            Item item = itemstack.getItem();
            mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);

            Tessellator image = Tessellator.instance;
            IIcon index = item.getIconFromDamage(itemstack.getItemDamage());
            float x1 = index.getMinU();
            float x2 = index.getMaxU();
            float y1 = index.getMinV();
            float y2 = index.getMaxV();
            float xPos = 0.5F;
            float yPos = -0.5F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glTranslatef(-xPos, -yPos, 0.0F);
            float var13 = 1F;
            GL11.glScalef(var13, var13, var13);
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-1F, -1F, 0.0F);
            ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, index.getIconWidth(), index.getIconHeight(), 0.0625F);
        }

    }
}