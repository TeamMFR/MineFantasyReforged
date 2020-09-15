package minefantasy.mfr.mechanics;

import minefantasy.mfr.api.weapon.IExtendedReachWeapon;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class ExtendedReachMFR {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            tickEnd(event.player);
        }
    }

    public void tickEnd(EntityPlayer entityPlayer) {
        // If we JUST swung an Item
        if (entityPlayer.swingProgressInt == 1) {
            ItemStack mainhand = entityPlayer.getHeldItemMainhand();
            if (!mainhand.isEmpty() && mainhand.getItem() instanceof IExtendedReachWeapon) {
                float extendedReach = ((IExtendedReachWeapon) mainhand.getItem()).getReachModifierInBlocks(mainhand);
                if (extendedReach > 0) {
                    RayTraceResult mouseOver = getMouseOver(0, extendedReach + 4);
                    if (mouseOver != null && mouseOver.typeOfHit == RayTraceResult.Type.ENTITY){
                        Entity target = mouseOver.entityHit;
                        if (target instanceof EntityLiving && target != entityPlayer) {
                            if (target.hurtResistantTime != ((EntityLiving) target).maxHurtResistantTime) {
                                FMLClientHandler.instance().getClient().playerController.attackEntity(entityPlayer, target);
                            }
                        }
                    }
                }
            }
        }
    }

    public RayTraceResult getMouseOver(float tickPart, float maxDist) {
        Minecraft mc = FMLClientHandler.instance().getClient();
        if (mc.getRenderViewEntity() != null) {
            if (mc.world != null) {
                mc.pointedEntity = null;
                double d0 = maxDist;
                RayTraceResult objectMouseOver = mc.getRenderViewEntity().rayTrace(d0, tickPart);
                double d1 = d0;
                Vec3d vec3 = new Vec3d (mc.getRenderViewEntity().getPosition());

                if (objectMouseOver != null) {
                    d1 = objectMouseOver.hitVec.distanceTo(vec3);
                }

                Vec3d vec31 = mc.getRenderViewEntity().getLook(tickPart);
                Vec3d vec32 = vec3.addVector(vec31.x * d0, vec31.y * d0, vec31.z * d0);
                Entity pointedEntity = null;
                float f1 = 1.0F;
                List<Entity> list = mc.world.getEntitiesWithinAABBExcludingEntity(mc.getRenderViewEntity(),
                        mc.getRenderViewEntity().getEntityBoundingBox().grow(vec31.x * d0, vec31.y * d0, vec31.z * d0).expand(f1, f1, f1));
                double d2 = d1;

                for (Entity value : list) {

                    if (value.canBeCollidedWith()) {
                        float f2 = value.getCollisionBorderSize();
                        AxisAlignedBB axisalignedbb = value.getEntityBoundingBox().expand(f2, f2, f2);
                        RayTraceResult rayTraceResult = axisalignedbb.calculateIntercept(vec3, vec32);

                        if (axisalignedbb.contains(vec3)) {
                            if (0.0D < d2 || d2 == 0.0D) {
                                pointedEntity = value;
                                d2 = 0.0D;
                            }
                        } else if (rayTraceResult != null) {
                            double d3 = vec3.distanceTo(rayTraceResult.hitVec);

                            if (d3 < d2 || d2 == 0.0D) {
                                pointedEntity = value;
                                d2 = d3;
                            }
                        }
                    }
                }

                if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
                    objectMouseOver = new RayTraceResult(pointedEntity);
                }

                return objectMouseOver;
            }
        }
        return null;
    }

}
