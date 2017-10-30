package minefantasy.mf2.client.render;

import cpw.mods.fml.client.FMLClientHandler;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.api.weapon.IParryable;
import minefantasy.mf2.item.weapon.ItemWeaponMF;
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
 */

public class RenderHeavyWeapon implements IItemRenderer {
    private Minecraft mc;
    private RenderItem itemRenderer;
    private boolean rotate;
    private float scale;
    private float offset;
    private boolean doesRenderParry = false;
    private boolean willStab = false;

    public RenderHeavyWeapon() {
        this(true);
    }

    public RenderHeavyWeapon(boolean rot) {
        this(rot, 2.0F);
    }

    public RenderHeavyWeapon(boolean rot, float sc) {
        rotate = rot;
        scale = sc;
    }

    public RenderHeavyWeapon setParryable() {
        doesRenderParry = true;
        return this;
    }

    public RenderHeavyWeapon setKatana() {
        offset = 1.0F;
        willStab = true;
        return this;
    }

    public RenderHeavyWeapon setGreatsword() {
        offset = 1.0F;
        return this;
    }

    public RenderHeavyWeapon setAxe() {
        offset = 1.0F;
        return this;
    }

    public RenderHeavyWeapon setBlunt() {
        offset = 1.2F;
        return this;
    }

    public RenderHeavyWeapon setScythe() {
        offset = 1.8F;
        return this;
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

        boolean hasParried = false;
        if (mc == null) {
            mc = FMLClientHandler.instance().getClient();
            itemRenderer = new RenderItem();
        }
        EntityLivingBase user = null;
        if (doesRenderParry) {
            user = ((EntityLivingBase) data[1]);

            if (user instanceof EntityPlayer && item != null) {
                hasParried = ItemWeaponMF.getParry(item) > 0
                        && ResearchLogic.hasInfoUnlocked((EntityPlayer) user, "counteratt");
            } else if (!(user instanceof EntityPlayer) && item != null && item.getItem() instanceof IParryable) {
                hasParried = user.hurtResistantTime > 0;
            }
        }

        this.mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
        Tessellator tessellator = Tessellator.instance;

        if (type == ItemRenderType.EQUIPPED) {
            if (user != null && hasParried) {
                if (user instanceof EntityPlayer) {
                    if (willStab) {
                        GL11.glRotatef(-90, 0, 0, 1);
                        GL11.glTranslatef(-1.5F, 0.5F, 0);
                    } else {
                        GL11.glRotatef(90, 0, 0, 1);
                        GL11.glTranslatef(0F, -1.0F, 0);
                    }
                } else {
                    GL11.glRotatef(-90, -1, 0, 0);
                    GL11.glTranslatef(0F, 0F, -0.25F);
                }
            }
            GL11.glTranslatef(0.25F * offset, -0.25F * offset, 0);
            GL11.glTranslatef(-1.0F, 0F, 0);
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

        } else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            if (hasParried) {
                if (willStab) {
                    GL11.glRotatef(-90, 0, 0, 1);
                    GL11.glTranslatef(-1.5F, 0.5F, 0);
                } else {
                    GL11.glRotatef(90, 0, 0, 1);
                    GL11.glTranslatef(0F, -1.0F, 0);
                }
            }
            GL11.glTranslatef(0.05F * offset, -0.05F * offset, 0);
            GL11.glTranslatef(-0.75F, -0.25F, 0);
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
        }

        GL11.glPopMatrix();
    }
}