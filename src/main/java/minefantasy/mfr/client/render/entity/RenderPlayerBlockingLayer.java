package minefantasy.mfr.client.render.entity;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.util.PlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;
import java.util.Map;

/**
 * Author Credit: Fuzs_ in the Golden Age Combat mod/Sword Blocking Mechanics mod
 * Github Link: https://github.com/Fuzss/swordblockingmechanics/tree/1.12
 * Modified and used under the free use public license, thank you so much to Fuzs_!
 */

@SideOnly(Side.CLIENT)
public class RenderPlayerBlockingLayer extends LayerHeldItem {

    private static final String RENDER_LIVING_BASE_LAYER_RENDERERS = "field_177097_h";

    public RenderPlayerBlockingLayer(RenderLivingBase<?> livingEntityRendererIn) {

        super(livingEntityRendererIn);
    }

    @Override
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

        boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT;
        ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
        ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();
        if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {

            GlStateManager.pushMatrix();
            if (this.livingEntityRenderer.getMainModel().isChild) {

                GlStateManager.translate(0.0F, 0.75F, 0.0F);
                GlStateManager.scale(0.5F, 0.5F, 0.5F);
            }

            this.renderHeldItem(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
            this.renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
            GlStateManager.popMatrix();
        }
    }

    @SuppressWarnings("deprecation")
    private void renderHeldItem(EntityLivingBase entityLivingBase, ItemStack stack, ItemCameraTransforms.TransformType transform, EnumHandSide handSide) {

        if (!stack.isEmpty()) {

            GlStateManager.pushMatrix();
            boolean leftHand = handSide == EnumHandSide.LEFT;
            if (entityLivingBase.isSneaking()) {

                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            // Forge: moved this call down, fixes incorrect offset while sneaking.
            this.translateToHand(handSide);
            if (entityLivingBase.isHandActive() && PlayerUtils.shouldItemStackBlock(stack) && entityLivingBase.getActiveHand() == (leftHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND)) {
                GlStateManager.translate((float) (leftHand ? 1 : -1) / 16.0F, 0.4375F, 0.0625F);

                // blocking
                GlStateManager.translate(leftHand ? -0.035F : 0.05F, leftHand ? 0.045F : 0.0F, leftHand ? -0.135F : -0.1F);
                GlStateManager.rotate((leftHand ? -1 : 1) * -50.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-10.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate((leftHand ? -1 : 1) * -60.0F, 0.0F, 0.0F, 1.0F);

                // old item layer
                GlStateManager.translate(0.0F, 0.1875F, 0.0F);
                GlStateManager.scale(0.625F, -0.625F, 0.625F);
                GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(leftHand ? 35.0F : 45.0F, 0.0F, 1.0F, 0.0F);

                // old item renderer
                GlStateManager.translate(0.0F, -0.3F, 0.0F);
                GlStateManager.scale(1.5F, 1.5F, 1.5F);
                GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.translate(-0.9375F, -0.0625F, 0.0F);
                GlStateManager.translate(0.5F, 0.5F, 0.25F);
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(0.0F, 0.0F, 0.28125F);

                applyTransformReverse(new ItemTransformVec3f(new Vector3f(0.0F, (leftHand ? 1 : -1) * 90.0F, (leftHand ? -1 : 1) * 55.0F), new Vector3f(0.0F, 0.25F, 0.03125F), new Vector3f(0.85F, 0.85F, 0.85F)), leftHand);
            } else {

                GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate((float) (leftHand ? -1 : 1) / 16.0F, 0.125F, -0.625F);
            }

            Minecraft.getMinecraft().getItemRenderer().renderItemSide(entityLivingBase, stack, transform, leftHand);
            GlStateManager.popMatrix();
        }
    }
    public static void applyTransformReverse(net.minecraft.client.renderer.block.model.ItemTransformVec3f vec, boolean leftHand) {

        if (vec != net.minecraft.client.renderer.block.model.ItemTransformVec3f.DEFAULT) {

            int i = leftHand ? -1 : 1;
            GlStateManager.scale(1.0F / vec.scale.x, 1.0F / vec.scale.y, 1.0F / vec.scale.z);
            float x = vec.rotation.x;
            float y = vec.rotation.y;
            float z = vec.rotation.z;

            if (leftHand) {

                y = -y;
                z = -z;
            }

            Quaternion quat = makeQuaternion(x, y, z);
            GlStateManager.rotate(quat.negate(quat));
            GlStateManager.translate((float) i * (-vec.translation.x), -vec.translation.y, -vec.translation.z);
        }
    }

    private static Quaternion makeQuaternion(float p_188035_0_, float p_188035_1_, float p_188035_2_) {

        float f = p_188035_0_ * 0.017453292F;
        float f1 = p_188035_1_ * 0.017453292F;
        float f2 = p_188035_2_ * 0.017453292F;
        float f3 = MathHelper.sin(0.5F * f);
        float f4 = MathHelper.cos(0.5F * f);
        float f5 = MathHelper.sin(0.5F * f1);
        float f6 = MathHelper.cos(0.5F * f1);
        float f7 = MathHelper.sin(0.5F * f2);
        float f8 = MathHelper.cos(0.5F * f2);

        return new Quaternion(f3 * f6 * f8 + f4 * f5 * f7, f4 * f5 * f8 - f3 * f6 * f7, f3 * f5 * f8 + f4 * f6 * f7, f4 * f6 * f8 - f3 * f5 * f7);
    }

    public static void replaceHeldItemLayer() {

        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        for (RenderPlayer renderPlayer : skinMap.values()) {

            List<LayerRenderer<EntityLivingBase>> layers = getLayerRenderers(renderPlayer);
            if (layers != null) {

                layers.removeIf(it -> it instanceof LayerHeldItem);
                renderPlayer.addLayer(new RenderPlayerBlockingLayer(renderPlayer));
            }
        }
    }

    public static List<LayerRenderer<EntityLivingBase>> getLayerRenderers(RenderPlayer instance) {
        return (List<LayerRenderer<EntityLivingBase>>) getPrivateValue(RenderLivingBase.class, instance, RENDER_LIVING_BASE_LAYER_RENDERERS);
    }

    private static <T> Object getPrivateValue(Class<T> clazz, T instance, String name) {

        try {

            return ObfuscationReflectionHelper.getPrivateValue(clazz, instance, name);
        } catch (Exception e) {

            MineFantasyReforged.LOG.error("Getting field \"" + name + "\" failed", e);
        }

        return null;
    }
}
