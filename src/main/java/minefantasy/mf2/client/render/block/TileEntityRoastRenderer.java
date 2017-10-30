package minefantasy.mf2.client.render.block;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.block.tileentity.TileEntityRoast;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityRoastRenderer extends TileEntitySpecialRenderer {
    private final RenderBlocks blockrender = new RenderBlocks();
    private ModelOven ovenModel;

    public TileEntityRoastRenderer() {
        ovenModel = new ModelOven();
    }

    public void renderAModelAt(TileEntityRoast tile, double d, double d1, double d2, float f) {
        int i = 0;
        if (tile.getWorldObj() != null) {
            i = tile.blockMetadata;
        }
        int j = 360 - (90 * i);
        float pixelHeight = tile.isOven() ? 3F : 5F;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + (pixelHeight / 32), (float) d2 + 0.5F);
        GL11.glRotatef(j, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(1.0F, -1F, -1F);
        if (tile.isOven()) {
            GL11.glPushMatrix();
            GL11.glRotatef(180, 0, 1, 0);
            bindTextureByName("textures/models/tileentity/oven_" + tile.getTexName() + ".png");
            ovenModel.render(0.0625F);
            GL11.glPopMatrix();
        }

        GL11.glRotatef(90, 1.0F, 0F, 0F);
        renderHungItem(tile, d, d1, d2, f);
        GL11.glPopMatrix();
    }

    public void renderInvModel(String tex, boolean oven, double d, double d1, double d2, float f) {

        float pixelHeight = oven ? 3F : 5F;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + (pixelHeight / 32), (float) d2 + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);
        if (oven) {
            GL11.glPushMatrix();
            GL11.glRotatef(180, 0, 1, 0);
            bindTextureByName("textures/models/tileentity/oven_" + tex + ".png");
            ovenModel.render(0.0625F);
            GL11.glPopMatrix();
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
        renderAModelAt((TileEntityRoast) tileentity, d, d1, d2, f);
    }

    private void renderHungItem(TileEntityRoast tile, double d, double d1, double d2, float f) {
        Minecraft mc = Minecraft.getMinecraft();
        ItemStack itemstack = tile.getStackInSlot(0);
        if (itemstack == null)
            return;
        // Block
        if (itemstack.getItemSpriteNumber() == 0 && itemstack.getItem() instanceof ItemBlock
                && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemstack.getItem()).getRenderType())) {
            Block block = Block.getBlockFromItem(itemstack.getItem());
            if (block != null) {
                // this.blockrender.renderBlockAsItem(block, itemstack.getItemDamage(), 1.0F);
            }
        }
        // Item
        else if (itemstack.getItem().getIconFromDamage(itemstack.getItemDamage()) != null) {
            Item item = itemstack.getItem();
            mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);

            Tessellator image = Tessellator.instance;
            IIcon index = item.getIconFromDamage(itemstack.getItemDamage());
            float x1 = index.getMaxU();
            float x2 = index.getMinU();
            float y1 = index.getMinV();
            float y2 = index.getMaxV();
            float scale = 0.5F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glTranslatef(-scale / 2, -scale / 2, 0.0F);
            GL11.glScalef(scale, scale, scale);
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-1F, -1F, 0.0F);
            ItemRenderer.renderItemIn2D(image, x2, y1, x1, y2, index.getIconWidth(), index.getIconHeight(), 0.0625F);
        }
    }
}