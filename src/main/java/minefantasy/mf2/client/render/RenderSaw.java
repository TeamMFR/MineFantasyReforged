package minefantasy.mf2.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * @author Anonymous Productions
 */

public class RenderSaw implements IItemRenderer {
    RenderItem renderItem = new RenderItem();
    private float scale;

    public RenderSaw() {
        this(1.0F);
    }

    public RenderSaw(float Sc) {
        scale = Sc;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type.equals(ItemRenderType.EQUIPPED);
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        Minecraft mc = FMLClientHandler.instance().getClient();
        if (type.equals(ItemRenderType.EQUIPPED)) {
            renderItem(item);
        }

    }

    private void renderItem(ItemStack item) {
        GL11.glPushMatrix();

        Minecraft mc = FMLClientHandler.instance().getClient();
        mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
        Tessellator tessellator = Tessellator.instance;

        float xOffset = 0.05F + (0.5F * (scale - 1));
        float yOffset = 0.35F - (0.5F * (scale - 1));
        float xPos = 0.0F + xOffset - yOffset;
        float yPos = 0.3F - xOffset - yOffset;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-xPos, -yPos, 0.0F);
        GL11.glScalef(scale, scale, 1);

        for (int layer = 0; layer < item.getItem().getRenderPasses(item.getItemDamage()); layer++) {
            int colour = item.getItem().getColorFromItemStack(item, layer);
            float red = (colour >> 16 & 255) / 255.0F;
            float green = (colour >> 8 & 255) / 255.0F;
            float blue = (colour & 255) / 255.0F;

            GL11.glColor4f(red, green, blue, 1.0F);

            IIcon icon = item.getItem().getIcon(item, layer);

            ItemRenderer.renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(),
                    icon.getIconWidth(), icon.getIconHeight(), 1F / 16F);
        }
        if (item != null && item.hasEffect(0)) {
            TextureHelperMF.renderEnchantmentEffects(tessellator);
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);

        GL11.glPopMatrix();

    }
}