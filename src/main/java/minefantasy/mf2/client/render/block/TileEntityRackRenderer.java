package minefantasy.mf2.client.render.block;

import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.weapon.IRackItem;
import minefantasy.mf2.block.tileentity.decor.TileEntityRack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityRackRenderer extends TileEntitySpecialRenderer {
    private static Minecraft mc = Minecraft.getMinecraft();
    private ModelRack model;

    public TileEntityRackRenderer() {
        model = new ModelRack();
    }

    public static void renderEquippedItem(ItemRenderType type, IItemRenderer customRenderer, RenderBlocks renderBlocks,
                                          ItemStack item) {
        if (customRenderer.shouldUseRenderHelper(type, item, ItemRendererHelper.EQUIPPED_BLOCK)) {
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            customRenderer.renderItem(type, item, renderBlocks);
            GL11.glPopMatrix();
        } else {
            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glTranslatef(0.0F, -0.3F, 0.0F);

            GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
            customRenderer.renderItem(type, item, renderBlocks);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }

    public void renderAModelAt(TileEntityRack tile, double d, double d1, double d2, float f) {
        int i = 0;
        if (tile.getWorldObj() != null) {
            i = tile.getBlockMetadata() - 2;
        }
        int j = i * 90;
        if (i == 1)
            j = 180;
        if (i == 2)
            j = 90;

        GL11.glPushMatrix();// Start all
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1F, (float) d2 + 0.5F);
        GL11.glRotatef(j + 180, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(1.0F, -1F, -1F);
        float itemsStart = -(3F / 16F);
        float itemsGap = 4F / 16F;

        CustomMaterial material = tile.getMaterial();
        GL11.glColor3f(material.colourRGB[0] / 255F, material.colourRGB[1] / 255F, material.colourRGB[2] / 255F);

        bindTextureByName("textures/models/tileentity/" + tile.getTexName() + "_base.png"); // texture
        model.renderModel(0.0625F);

        GL11.glColor3f(1F, 1F, 1F);

        bindTextureByName("textures/models/tileentity/" + tile.getTexName() + "_detail.png"); // texture
        model.renderModel(0.0625F);

        for (int a = 0; a < 4; a++) {
            GL11.glPushMatrix();// Start Individual Items
            ItemStack itemstack = tile.getStackInSlot(a);
            if (itemstack != null) {
                float o = tile.yCoord % 2 == 0 ? 2F : 3F;
                float x = itemsStart + (a * itemsGap) - (o / 16F);
                float y = 0.3F;
                float z = a % 2 == 0 ? 0.4F : 0.45F;
                z -= 6F / 16F;
                float r = getRotationForItem(itemstack.getItem());
                float scale = 1.0F;
                if (itemstack.getItem() instanceof IRackItem) {
                    IRackItem rackitem = (IRackItem) itemstack.getItem();
                    scale = rackitem.getScale(itemstack);
                    x += rackitem.getOffsetX(itemstack);
                    y += rackitem.getOffsetY(itemstack);
                    z -= rackitem.getOffsetZ(itemstack);
                    r += rackitem.getRotationOffset(itemstack);
                    z -= (scale - 1) / 2 * 5.5F / 16F;
                    y -= (scale - 1) / 2F;
                }

                GL11.glTranslatef(x, y, z);

                GL11.glPushMatrix();
                GL11.glRotatef(90F, 0, 1F, 0);
                GL11.glRotatef(r, 0, 0, 1F);
                GL11.glScalef(scale, scale, 1);

                IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, ItemRenderType.EQUIPPED);
                if (isCustom(itemstack) && customRenderer != null) {
                    this.mc.getTextureManager().bindTexture(
                            this.mc.getTextureManager().getResourceLocation(itemstack.getItemSpriteNumber()));
                    renderEquippedItem(ItemRenderType.EQUIPPED, customRenderer, new RenderBlocks(), itemstack);
                } else {
                    for (int layer = 0; layer < itemstack.getItem()
                            .getRenderPasses(itemstack.getItemDamage()); layer++) {
                        IIcon icon = itemstack.getItem().getIcon(itemstack, layer);
                        if (icon != null) {
                            GL11.glPushMatrix();
                            int colour = itemstack.getItem().getColorFromItemStack(itemstack, layer);
                            float red = (colour >> 16 & 255) / 255.0F;
                            float green = (colour >> 8 & 255) / 255.0F;
                            float blue = (colour & 255) / 255.0F;

                            GL11.glColor4f(red, green, blue, 1.0F);

                            renderItem(tile, itemstack, icon, a, layer);
                            GL11.glPopMatrix();
                        }
                    }
                }
                GL11.glPopMatrix();
            }

            GL11.glPopMatrix();// END individual item placement
        }
        GL11.glPopMatrix(); // end all

    }

    public void renderInvModel(CustomMaterial material, String tex, double d, double d1, double d2, float f) {
        int j = 0;

        GL11.glPushMatrix();// Start all
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1F, (float) d2 + 0.5F);
        GL11.glRotatef(j + 180, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(1.0F, -1F, -1F);
        float itemsStart = -(3F / 16F);
        float itemsGap = 4F / 16F;

        GL11.glColor3f(material.colourRGB[0] / 255F, material.colourRGB[1] / 255F, material.colourRGB[2] / 255F);

        bindTextureByName("textures/models/tileentity/" + tex + "_base.png"); // texture
        model.renderModel(0.0625F);

        GL11.glColor3f(1F, 1F, 1F);

        bindTextureByName("textures/models/tileentity/" + tex + "_detail.png"); // texture
        model.renderModel(0.0625F);

        GL11.glPopMatrix(); // end all

    }

    private float getRotationForItem(Item item) {
        String classname = item.getClass().getName();
        if (classname.endsWith("ItemCrossbow") || classname.endsWith("ItemBlunderbuss")
                || classname.endsWith("ItemBlowgun") || classname.endsWith("ItemMusket")) {
            return 45F;
        }
        return -45F;
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
        renderAModelAt((TileEntityRack) tileentity, d, d1, d2, f); // where to render
    }

    private void renderItem(TileEntityRack tile, ItemStack itemstack, IIcon icon, int slot, int layer) {
        GL11.glPushMatrix();
        TextureManager texturemanager = this.mc.getTextureManager();
        GL11.glScalef(0.85F, 0.85F, 0.85F);

        if (icon == null) {
            GL11.glPopMatrix();
            return;
        }

        texturemanager.bindTexture(texturemanager.getResourceLocation(itemstack.getItemSpriteNumber()));
        Tessellator tessellator = Tessellator.instance;
        float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();
        float f4 = 0.0F;
        float f5 = 0.3F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-f4, -f5, 0.0F);
        GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
        ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);

        if (itemstack.hasEffect(layer)) {
            GL11.glDepthFunc(GL11.GL_EQUAL);
            GL11.glDisable(GL11.GL_LIGHTING);
            texturemanager.bindTexture(TextureHelperMF.ITEM_GLINT);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            float f7 = 0.76F;
            GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            float f8 = 0.125F;
            GL11.glScalef(f8, f8, f8);
            float f9 = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;
            GL11.glTranslatef(f9, 0.0F, 0.0F);
            GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(f8, f8, f8);
            f9 = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;
            GL11.glTranslatef(-f9, 0.0F, 0.0F);
            GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);

        GL11.glPopMatrix();
    }

    private boolean isCustom(ItemStack itemstack) {
        if (itemstack != null && itemstack.getItem() instanceof IRackItem) {
            return ((IRackItem) itemstack.getItem()).isSpecialRender(itemstack);
        }
        return false;
    }
}