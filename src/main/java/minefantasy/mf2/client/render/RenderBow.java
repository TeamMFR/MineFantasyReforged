package minefantasy.mf2.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Loader;
import minefantasy.mf2.api.archery.AmmoMechanicsMF;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.item.archery.ItemBowMF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderBow implements IItemRenderer {
    private boolean isLongbow;

    public RenderBow(boolean big) {
        isLongbow = big;
    }

    public static void renderEnchantmentEffects(Tessellator tessellator) {
        GL11.glDepthFunc(GL11.GL_EQUAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureHelperMF.ITEM_GLINT);
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

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (data.length < 2 || !(data[1] instanceof EntityLivingBase)) {
            renderEquippedBow(item, (EntityLivingBase) null, false);
        } else {
            switch (type) {
                case EQUIPPED_FIRST_PERSON:
                    renderEquippedBow(item, (EntityLivingBase) data[1], true);
                    break;
                case EQUIPPED:
                    renderEquippedBow(item, (EntityLivingBase) data[1], false);
                    break;
                default:
                    break;
            }
        }
    }

    private void renderEquippedBow(ItemStack item, EntityLivingBase entityLivingBase, boolean firstPerson) {
        GL11.glPushMatrix();
        if (firstPerson) {
            GL11.glTranslatef(0.1F, -0.1F, 0F);
        } else {
            GL11.glTranslatef(0.2F, -0.25F, 0F);
        }

        ItemBowMF bow = null;
        if (item != null && item.getItem() instanceof ItemBowMF) {
            bow = (ItemBowMF) item.getItem();
        }

        ItemStack arrowStack = AmmoMechanicsMF.getArrowOnBow(item);

        int drawAmount = -2;

        if (entityLivingBase != null && entityLivingBase instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLivingBase;

            int timer = player.getItemInUseDuration();

            if (bow != null) {
                drawAmount = bow.getDrawAmount(timer);
                // arrowStack = bow.getArrow(item);
            } else {
                if (timer >= 18)
                    drawAmount = 2;
                else if (timer > 13)
                    drawAmount = 1;
                else if (timer > 0)
                    drawAmount = 0;

            }

            if(Loader.isModLoaded("battlegear2")) {
                ItemStack quiver = mods.battlegear2.api.quiver.QuiverArrowRegistry.getArrowContainer(item, (EntityPlayer) entityLivingBase);
                if (quiver != null) {
                    arrowStack = ((mods.battlegear2.api.quiver.IArrowContainer2) quiver.getItem()).getStackInSlot(quiver,
                            ((mods.battlegear2.api.quiver.IArrowContainer2) quiver.getItem()).getSelectedSlot(quiver));
                }
            }

            if (timer == 0) {
                arrowStack = null;
            }
        } else {
            drawAmount = 2;
        }

        Tessellator tessellator = Tessellator.instance;

        GL11.glPushMatrix();
        if (isLongbow) {
            GL11.glTranslatef(-1 / 3F, -1 / 3F, 0F);
            GL11.glScalef(1.5F, 1.5F, 1.0F);
            if (entityLivingBase == null) {
                GL11.glTranslatef((0F / 16F), -(4F / 16F), 0F);
            }
        }
        for (int layer = 0; layer < item.getItem().getRenderPasses(item.getItemDamage()); layer++) {
            IIcon icon = item.getItem().getIcon(item, layer);

            if (bow != null) {
                icon = bow.getIcon(item, layer, drawAmount);
            }

            GL11.glPushMatrix();
            int colour = item.getItem().getColorFromItemStack(item, layer);
            float red = (colour >> 16 & 255) / 255.0F;
            float green = (colour >> 8 & 255) / 255.0F;
            float blue = (colour & 255) / 255.0F;

            GL11.glColor4f(red, green, blue, 1.0F);

            ItemRenderer.renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(),
                    icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
            GL11.glColor3f(1F, 1F, 1F);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();

        if (arrowStack == null && drawAmount > -2) {
            arrowStack = new ItemStack(Items.arrow);
        }
        if (arrowStack != null && entityLivingBase != null) {
            int x = -3;
            int y = -20;

            if (isLongbow) {
                x += 1;
                y += 1;
            }
            for (int layer = 0; layer < arrowStack.getItem().getRenderPasses(arrowStack.getItemDamage()); layer++) {
                IIcon icon = arrowStack.getItem().getIcon(arrowStack, layer);
                GL11.glPushMatrix();

                int colour = arrowStack.getItem().getColorFromItemStack(arrowStack, layer);
                float red = (colour >> 16 & 255) / 255.0F;
                float green = (colour >> 8 & 255) / 255.0F;
                float blue = (colour & 255) / 255.0F;

                GL11.glColor4f(red, green, blue, 1.0F);

                GL11.glTranslatef(-(x + drawAmount) / 16F, -(y + drawAmount) / 16F,
                        firstPerson ? -0.5F / 16F : 0.5F / 16F);
                GL11.glRotatef(-90, 0, 0, 1);
                ItemRenderer.renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(),
                        icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
                GL11.glColor3f(1F, 1F, 1F);
                GL11.glPopMatrix();
            }
        }

        if (item.isItemEnchanted()) {
            renderEnchantmentEffects(tessellator);
        }
        GL11.glPopMatrix();
    }
}
