package minefantasy.mf2.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.entity.EntityCogwork;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class RenderPowerArmour extends RendererLivingEntity {
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation(
            "textures/misc/enchanted_item_glint.png");
    protected ModelBiped cogworkSuit;

    public RenderPowerArmour() {
        super(new ModelCogwork(1F), 1.5F);
        this.cogworkSuit = (ModelBiped) this.mainModel;
    }

    public static boolean isFirstPersonRender(EntityLivingBase entity) {
        Minecraft mc = Minecraft.getMinecraft();
        return mc.gameSettings.thirdPersonView == 0 && entity == mc.thePlayer;
    }

    @Override
    protected boolean func_110813_b(EntityLivingBase entity) {
        return false;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(int layer) {
        String tex = layer == 0 ? "base" : "plating";
        return TextureHelperMF.getResource("textures/models/armour/cogwork/cogwork_suit_" + tex + ".png");
    }

    protected void bindEntityTexture(int layer) {
        this.bindTexture(this.getEntityTexture(layer));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless
     * you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return this.getEntityTexture(0);
    }

    protected void offsetHeldItem() {
        GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method,
     * always casting down its argument and then handing it off to a worker function
     * which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void
     * func_76986_a(T entity, double d, double d1, double d2, float f, float f1).
     * But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(Entity entity, double x, double y, double z, float f, float f1) {
        EntityLivingBase user = (EntityLivingBase) entity;

        if (entity.riddenByEntity != null && entity.riddenByEntity instanceof EntityLivingBase) {
            user = (EntityLivingBase) entity.riddenByEntity;
        }
        this.doRender(user, x, y, z, f, f1);
    }

    @Override
    public void doRender(EntityLivingBase entity, double x, double y, double z, float f, float f1) {
        if (MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Pre(entity, this, x, y, z)))
            return;

        boolean isFrame = true;
        EntityCogwork suit = this.getCogwork(entity);
        if (suit != null) {
            isFrame = !suit.isFullyArmoured();
        }

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        this.mainModel.onGround = this.renderSwingProgress(entity, f1);

        if (this.renderPassModel != null) {
            this.renderPassModel.onGround = this.mainModel.onGround;
        }

        this.mainModel.isRiding = entity.isRiding();

        if (this.renderPassModel != null) {
            this.renderPassModel.isRiding = this.mainModel.isRiding;
        }

        this.mainModel.isChild = entity.isChild();

        if (this.renderPassModel != null) {
            this.renderPassModel.isChild = this.mainModel.isChild;
        }

        try {
            float f2 = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, f1);
            float f3 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, f1);
            float f4;

            if (entity.isRiding() && entity.ridingEntity instanceof EntityLivingBase) {
                EntityLivingBase entitylivingbase1 = (EntityLivingBase) entity.ridingEntity;
                f2 = this.interpolateRotation(entitylivingbase1.prevRenderYawOffset, entitylivingbase1.renderYawOffset,
                        f1);
                f4 = MathHelper.wrapAngleTo180_float(f3 - f2);

                if (f4 < -85.0F) {
                    f4 = -85.0F;
                }

                if (f4 >= 85.0F) {
                    f4 = 85.0F;
                }

                f2 = f3 - f4;

                if (f4 * f4 > 2500.0F) {
                    f2 += f4 * 0.2F;
                }
            }

            float f13 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f1;
            this.renderLivingAt(entity, x, y, z);
            f4 = this.handleRotationFloat(entity, f1);
            this.rotateCorpse(entity, f4, f2, f1);
            float f5 = 0.0625F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(-1.0F, -1.0F, 1.0F);
            this.preRenderCallback(entity, f1);
            GL11.glTranslatef(0.0F, -24.0F * f5 - 0.0078125F, 0.0F);
            float f6 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * f1;
            float f7 = entity.limbSwing - entity.limbSwingAmount * (1.0F - f1);

            if (entity.isChild()) {
                f7 *= 3.0F;
            }

            if (f6 > 1.0F) {
                f6 = 1.0F;
            }
            if (entity instanceof EntityCogwork && entity.riddenByEntity == null
                    && ((EntityCogwork) entity).isUnderRepairFrame()) {
                GL11.glTranslatef(0F, -0.5F, 0F);
            }
            if (suit != null) {
                GL11.glTranslatef(0F, -0.15625F, 0F);
            }

            GL11.glEnable(GL11.GL_ALPHA_TEST);
            this.mainModel.setLivingAnimations(entity, f7, f6, f1);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            this.renderModel(0, entity, f7, f6, f4, f3 - f2, f13, f5);

            CustomMaterial plating = getPlating(entity);
            if (plating != null) {
                colourPlating(plating);
                this.renderModel(1, entity, f7, f6, f4, f3 - f2, f13, f5);
            }
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            int j;
            float f8;
            float f9;
            float f10;

            for (int i = 0; i < 4; ++i) {
                j = this.shouldRenderPass(entity, i, f1);

                if (j > 0) {
                    this.renderPassModel.setLivingAnimations(entity, f7, f6, f1);
                    this.renderPassModel.render(entity, f7, f6, f4, f3 - f2, f13, f5);

                    if ((j & 240) == 16) {
                        this.func_82408_c(entity, i, f1);
                        this.renderPassModel.render(entity, f7, f6, f4, f3 - f2, f13, f5);
                    }

                    if ((j & 15) == 15) {
                        f8 = entity.ticksExisted + f1;
                        this.bindTexture(RES_ITEM_GLINT);
                        GL11.glEnable(GL11.GL_BLEND);
                        f9 = 0.5F;
                        GL11.glColor4f(f9, f9, f9, 1.0F);
                        GL11.glDepthFunc(GL11.GL_EQUAL);
                        GL11.glDepthMask(false);

                        for (int k = 0; k < 2; ++k) {
                            GL11.glDisable(GL11.GL_LIGHTING);
                            f10 = 0.76F;
                            GL11.glColor4f(0.5F * f10, 0.25F * f10, 0.8F * f10, 1.0F);
                            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                            GL11.glMatrixMode(GL11.GL_TEXTURE);
                            GL11.glLoadIdentity();
                            float f11 = f8 * (0.001F + k * 0.003F) * 20.0F;
                            float f12 = 0.33333334F;
                            GL11.glScalef(f12, f12, f12);
                            GL11.glRotatef(30.0F - k * 60.0F, 0.0F, 0.0F, 1.0F);
                            GL11.glTranslatef(0.0F, f11, 0.0F);
                            GL11.glMatrixMode(GL11.GL_MODELVIEW);
                            this.renderPassModel.render(entity, f7, f6, f4, f3 - f2, f13, f5);
                        }

                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glMatrixMode(GL11.GL_TEXTURE);
                        GL11.glDepthMask(true);
                        GL11.glLoadIdentity();
                        GL11.glMatrixMode(GL11.GL_MODELVIEW);
                        GL11.glEnable(GL11.GL_LIGHTING);
                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glDepthFunc(GL11.GL_LEQUAL);
                    }

                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_ALPHA_TEST);
                }
            }

            GL11.glDepthMask(true);
            if (!isFrame && !isFirstPersonRender(entity)) {
                this.renderEquippedItems(entity, f1);
            }
            float f14 = entity.getBrightness(f1);
            j = this.getColorMultiplier(entity, f14, f1);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

            if ((j >> 24 & 255) > 0 || entity.hurtTime > 0 || entity.deathTime > 0) {
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glDepthFunc(GL11.GL_EQUAL);

                if (entity.hurtTime > 0 || entity.deathTime > 0) {
                    GL11.glColor4f(f14, 0.0F, 0.0F, 0.4F);
                    this.mainModel.render(entity, f7, f6, f4, f3 - f2, f13, f5);

                    for (int l = 0; l < 4; ++l) {
                        if (this.inheritRenderPass(entity, l, f1) >= 0) {
                            GL11.glColor4f(f14, 0.0F, 0.0F, 0.4F);
                            this.renderPassModel.render(entity, f7, f6, f4, f3 - f2, f13, f5);
                        }
                    }
                }

                if ((j >> 24 & 255) > 0) {
                    f8 = (j >> 16 & 255) / 255.0F;
                    f9 = (j >> 8 & 255) / 255.0F;
                    float f15 = (j & 255) / 255.0F;
                    f10 = (j >> 24 & 255) / 255.0F;
                    GL11.glColor4f(f8, f9, f15, f10);
                    this.mainModel.render(entity, f7, f6, f4, f3 - f2, f13, f5);

                    for (int i1 = 0; i1 < 4; ++i1) {
                        if (this.inheritRenderPass(entity, i1, f1) >= 0) {
                            GL11.glColor4f(f8, f9, f15, f10);
                            this.renderPassModel.render(entity, f7, f6, f4, f3 - f2, f13, f5);
                        }
                    }
                }

                GL11.glDepthFunc(GL11.GL_LEQUAL);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        } catch (Exception exception) {
        }

        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
        this.passSpecialRender(entity, x, y, z);
        MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Post(entity, this, x, y, z));
    }

    private CustomMaterial getPlating(EntityLivingBase base) {
        EntityCogwork suit = getCogwork(base);
        if (suit != null) {
            return suit.getPlating();
        }
        return null;
    }

    private EntityCogwork getCogwork(EntityLivingBase base) {
        if (base instanceof EntityCogwork) {
            return (EntityCogwork) base;
        }
        if (base.ridingEntity != null && base.ridingEntity instanceof EntityCogwork) {
            return (EntityCogwork) base.ridingEntity;
        }
        return null;
    }

    private void colourPlating(CustomMaterial material) {
        GL11.glColor3f(material.colourRGB[0] / 255F, material.colourRGB[1] / 255F, material.colourRGB[2] / 255F);
    }

    @Override
    protected void renderModel(EntityLivingBase p_77036_1_, float p_77036_2_, float p_77036_3_, float p_77036_4_,
                               float p_77036_5_, float p_77036_6_, float p_77036_7_) {
        this.renderModel(0, p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
    }

    protected void renderModel(int layer, EntityLivingBase p_77036_1_, float p_77036_2_, float p_77036_3_,
                               float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_) {
        this.bindEntityTexture(layer);

        if (!p_77036_1_.isInvisible()) {
            this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
        } else if (!p_77036_1_.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer)) {
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
            this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glPopMatrix();
            GL11.glDepthMask(true);
        } else {
            this.mainModel.setRotationAngles(p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_,
                    p_77036_1_);
        }
    }

    private float interpolateRotation(float p_77034_1_, float p_77034_2_, float p_77034_3_) {
        float f3;

        for (f3 = p_77034_2_ - p_77034_1_; f3 < -180.0F; f3 += 360.0F) {
            ;
        }

        while (f3 >= 180.0F) {
            f3 -= 360.0F;
        }

        return p_77034_1_ + p_77034_3_ * f3;
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase entity, float offset) {
        if (entity instanceof AbstractClientPlayer) {
            renderPlayerItems((AbstractClientPlayer) entity, offset);
            return;
        }
        renderCogworkItems(entity, offset);
    }

    protected void renderCogworkItems(EntityLivingBase entity, float offset) {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        // RenderBiped
        ItemStack itemstack = entity.getHeldItem();
        Item item;
        float f1;

        if (itemstack != null && itemstack.getItem() != null) {
            item = itemstack.getItem();
            GL11.glPushMatrix();

            if (this.mainModel.isChild) {
                f1 = 0.5F;
                GL11.glTranslatef(0.0F, 0.625F, 0.0F);
                GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
                GL11.glScalef(f1, f1, f1);
            }

            this.cogworkSuit.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

            net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient
                    .getItemRenderer(itemstack, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(
                    net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, itemstack,
                    net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));

            if (item instanceof ItemBlock
                    && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType()))) {
                f1 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                f1 *= 0.75F;
                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f1, -f1, f1);
            } else if (item == Items.bow) {
                f1 = 0.625F;
                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f1, -f1, f1);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            } else if (item.isFull3D()) {
                f1 = 0.625F;

                if (item.shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                }

                this.offsetHeldItem();
                GL11.glScalef(f1, -f1, f1);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            } else {
                f1 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(f1, f1, f1);
                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }

            float f2;
            int i;
            float f5;

            if (itemstack.getItem().requiresMultipleRenderPasses()) {
                for (i = 0; i < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); ++i) {
                    int j = itemstack.getItem().getColorFromItemStack(itemstack, i);
                    f5 = (j >> 16 & 255) / 255.0F;
                    f2 = (j >> 8 & 255) / 255.0F;
                    float f3 = (j & 255) / 255.0F;
                    GL11.glColor4f(f5, f2, f3, 1.0F);
                    this.renderManager.itemRenderer.renderItem(entity, itemstack, i);
                }
            } else {
                i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
                float f4 = (i >> 16 & 255) / 255.0F;
                f5 = (i >> 8 & 255) / 255.0F;
                f2 = (i & 255) / 255.0F;
                GL11.glColor4f(f4, f5, f2, 1.0F);
                this.renderManager.itemRenderer.renderItem(entity, itemstack, 0);
            }

            GL11.glPopMatrix();
        }
    }

    protected void renderPlayerItems(AbstractClientPlayer player, float offset) {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        super.renderArrowsStuckInEntity(player, offset);
        float f2;
        float f4;

        ItemStack held = player.inventory.getCurrentItem();
        if (held != null) {
            GL11.glPushMatrix();
            this.cogworkSuit.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.5F, -0.0625F);

            if (player.fishEntity != null) {
                held = new ItemStack(Items.stick);
            }

            EnumAction enumaction = null;

            if (player.getItemInUseCount() > 0) {
                enumaction = held.getItemUseAction();
            }

            net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient
                    .getItemRenderer(held, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(
                    net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, held,
                    net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));

            if (is3D || held.getItem() instanceof ItemBlock
                    && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(held.getItem()).getRenderType())) {
                f2 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                f2 *= 0.75F;
                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f2, -f2, f2);
            } else if (held.getItem() == Items.bow) {
                f2 = 0.625F;
                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f2, -f2, f2);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            } else if (held.getItem().isFull3D()) {
                f2 = 0.625F;

                if (held.getItem().shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                }

                if (player.getItemInUseCount() > 0 && enumaction == EnumAction.block) {
                    GL11.glTranslatef(0.05F, 0.0F, -0.1F);
                    GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
                }

                GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                GL11.glScalef(f2, -f2, f2);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            } else {
                f2 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(f2, f2, f2);
                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }

            float f3;
            int k;
            float f12;

            if (held.getItem().requiresMultipleRenderPasses()) {
                for (k = 0; k < held.getItem().getRenderPasses(held.getItemDamage()); ++k) {
                    int i = held.getItem().getColorFromItemStack(held, k);
                    f12 = (i >> 16 & 255) / 255.0F;
                    f3 = (i >> 8 & 255) / 255.0F;
                    f4 = (i & 255) / 255.0F;
                    GL11.glColor4f(f12, f3, f4, 1.0F);
                    this.renderManager.itemRenderer.renderItem(player, held, k);
                }
            } else {
                k = held.getItem().getColorFromItemStack(held, 0);
                float f11 = (k >> 16 & 255) / 255.0F;
                f12 = (k >> 8 & 255) / 255.0F;
                f3 = (k & 255) / 255.0F;
                GL11.glColor4f(f11, f12, f3, 1.0F);
                this.renderManager.itemRenderer.renderItem(player, held, 0);
            }

            GL11.glPopMatrix();
        }
    }
}