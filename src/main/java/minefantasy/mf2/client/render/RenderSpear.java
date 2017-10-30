package minefantasy.mf2.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 * <p>
 * Code based off Battlegear Spears by Nerd Boy.
 */

public class RenderSpear implements IItemRenderer {
    private Minecraft mc;
    private RenderItem itemRenderer;
    private boolean isHalbeard;

    public RenderSpear() {
        this(false);
    }

    public RenderSpear(boolean halbeard) {
        isHalbeard = halbeard;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type.equals(ItemRenderType.EQUIPPED) || type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON);
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();

        if (mc == null) {
            mc = FMLClientHandler.instance().getClient();
            itemRenderer = new RenderItem();
        }
        this.mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
        Tessellator tessellator = Tessellator.instance;

        boolean rotate = getRotationFor(type == ItemRenderType.EQUIPPED_FIRST_PERSON, data);
        GL11.glTranslatef(-1.0F, -1.0F, 0);
        GL11.glScalef(3, 3, 1);

        GL11.glPushMatrix();

        for (int layer = 0; layer < item.getItem().getRenderPasses(item.getItemDamage()); layer++) {
            int colour = item.getItem().getColorFromItemStack(item, layer);
            float red = (colour >> 16 & 255) / 255.0F;
            float green = (colour >> 8 & 255) / 255.0F;
            float blue = (colour & 255) / 255.0F;

            GL11.glColor4f(red, green, blue, 1.0F);

            IIcon icon = item.getItem().getIcon(item, layer);
            float x = icon.getMaxU();
            float x2 = icon.getMinU();

            if (rotate) {
                x2 = icon.getMaxU();
                x = icon.getMinU();
            }

            ItemRenderer.renderItemIn2D(tessellator, x, icon.getMinV(), x2, icon.getMaxV(), icon.getIconWidth(),
                    icon.getIconHeight(), 1F / 16F);
        }
        if (item != null && item.hasEffect(0)) {
            TextureHelperMF.renderEnchantmentEffects(tessellator);
        }

        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    private boolean getRotationFor(boolean FP, Object... data) {
        boolean charge = false;
        for (int a = 0; a < data.length; a++) {
            if (data[a] instanceof EntityLivingBase) {
                if (((EntityLivingBase) data[a]).isSwingInProgress && !isHalbeard) {
                    return true;
                }
                if (((EntityLivingBase) data[a]).isSprinting() && FP) {
                    charge = true;
                }
            }
            if (data[a] instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) data[a];
                if (player.isUsingItem()) {
                    charge = false;
                }
            }
        }
        return charge;
    }
}