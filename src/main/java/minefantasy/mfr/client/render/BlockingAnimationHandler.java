package minefantasy.mfr.client.render;

import minefantasy.mfr.MFREventHandler;
import minefantasy.mfr.config.ConfigClient;
import minefantasy.mfr.item.ItemWeaponMFR;
import minefantasy.mfr.util.PlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Author Credit: Fuzs_ in the Golden Age Combat mod/Sword Blocking Mechanics mod
 * Github Link: <a href="https://github.com/Fuzss/swordblockingmechanics/tree/1.12">https://github.com/Fuzss/swordblockingmechanics/tree/1.12</a>
 * Modified and used under the free use public license, thank you so much to Fuzs_!
 */

@SideOnly(Side.CLIENT)
public class BlockingAnimationHandler {

    private final Minecraft mc = Minecraft.getMinecraft();

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onRenderLiving(final RenderLivingEvent.Pre<AbstractClientPlayer> evt) {

        if (evt.getEntity() instanceof AbstractClientPlayer) {

            AbstractClientPlayer player = (AbstractClientPlayer) evt.getEntity();
            if (player.isHandActive() && PlayerUtils.shouldItemStackBlock(player.getActiveItemStack(), player.getHeldItemOffhand())) {
                ModelBiped model = (ModelBiped) evt.getRenderer().getMainModel();
                boolean left1 = player.getActiveHand() == EnumHand.OFF_HAND && player.getPrimaryHand() == EnumHandSide.RIGHT;
                boolean left2 = player.getActiveHand() == EnumHand.MAIN_HAND && player.getPrimaryHand() == EnumHandSide.LEFT;
                if (left1 || left2) {

                    if (model.leftArmPose == ModelBiped.ArmPose.ITEM) {
                        model.leftArmPose = ModelBiped.ArmPose.BLOCK;
                    }
                } else {

                    if (model.rightArmPose == ModelBiped.ArmPose.ITEM) {
                        model.rightArmPose = ModelBiped.ArmPose.BLOCK;
                    }
                }
            }
        }
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onRenderHand(final RenderSpecificHandEvent evt) {

        EntityPlayerSP player = this.mc.player;
        if (player != null) {
            boolean rightHanded = (evt.getHand() == EnumHand.MAIN_HAND ? player.getPrimaryHand() : player.getPrimaryHand().opposite()) == EnumHandSide.RIGHT;
            if (player.isHandActive() && player.getActiveHand() == evt.getHand()) {
                ItemStack stack = evt.getItemStack();
                if (PlayerUtils.shouldItemStackBlock(stack, player.getHeldItemOffhand())) {
                    GlStateManager.pushMatrix();
                    this.transformSideFirstPerson(rightHanded ? 1.0F : -1.0F, evt.getEquipProgress());
                    this.mc.getItemRenderer().renderItemSide(player, stack, rightHanded ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !rightHanded);
                    GlStateManager.popMatrix();
                    evt.setCanceled(true);
                }
            }

            if (ConfigClient.shouldUseMfrCustomAnimations) {
                performCounterAttackAnimation(player, evt.getItemStack(), rightHanded);
            }
        }

    }

    @SubscribeEvent(priority= EventPriority.LOWEST)
    public void onEvent(FOVUpdateEvent event) {
        EntityPlayer player = event.getEntity();
        IAttributeInstance speedAttribute = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

        if (speedAttribute.getModifier(MFREventHandler.BLOCK_SPEED_MODIFIER_UUID) != null) {
            event.setNewfov(1.0F);
        }
    }

    /**
     * values taken from Minecraft snapshot 15w33b
     */
    private void transformSideFirstPerson(float side, float equippedProgress) {
        GlStateManager.translate(side * 0.56F, -0.52F + equippedProgress * -0.6F, -0.72F);
        GlStateManager.translate(side * -0.14142136F, 0.08F, 0.14142136F);
        GlStateManager.rotate(-102.25F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(side * 13.365F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(side * 78.05F, 0.0F, 0.0F, 1.0F);
    }

    private void performCounterAttackAnimation(EntityPlayer player, ItemStack stack, boolean rightHand) {
        boolean hasParried = false;
        //Parry Animations
        if (player != null && !stack.isEmpty()) {
            hasParried = ItemWeaponMFR.canCounter(player, stack) == 1;
        }

        if (hasParried) {
            if (rightHand) {
                GlStateManager.translate(0F, 0.35F, -0.5F);
                GlStateManager.rotate(-45F, 1F, 0F,0F);
            }
        }
    }
}
