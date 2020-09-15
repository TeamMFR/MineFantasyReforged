package minefantasy.mfr.mechanics;

import minefantasy.mfr.api.helpers.ArmourCalculator;
import minefantasy.mfr.api.helpers.TacticalManager;
import minefantasy.mfr.api.stamina.IStaminaWeapon;
import minefantasy.mfr.api.stamina.StaminaBar;
import minefantasy.mfr.config.ConfigClient;
import minefantasy.mfr.config.ConfigStamina;
import minefantasy.mfr.network.HitSoundPacket;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.network.StaminaPacket;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.world.WorldServer;

public class StaminaMechanics {
    private static boolean hurtAdreneline = true;

    public static void tickEntity(EntityLivingBase entity) {
        if (entity.ticksExisted % 20 == 0) {
            ArmourCalculator.setTotalWeightOfWorn(entity, true);
            ArmourCalculator.setTotalWeightOfWorn(entity, false);
        }

        float[] packet = new float[]{StaminaBar.getStaminaValue(entity), StaminaBar.getBaseMaxStamina(entity),
                StaminaBar.getFlashTime(entity), StaminaBar.getBonusStamina(entity)};
        float[] values = new float[]{StaminaBar.getStaminaValue(entity), StaminaBar.getTotalMaxStamina(entity)};
        if (entity.world.isRemote && ConfigClient.playBreath) {
            if (!StaminaBar.isPercentStamAvailable(entity, 0.2F, false) && !entity.isInsideOfMaterial(Material.WATER)) {
                if (entity.ticksExisted % 20 == 0) {
                    entity.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, 0.1F, 1.8F);
                }
                if (entity.ticksExisted + 5 % 20 == 0) {
                    entity.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, 0.15F, 1.5F);
                }
            }
            return;
        }
        float decay = getConstantDecay(entity);
        // Decay
        if (decay > 0) {
            if (values[0] > 0) {
                StaminaBar.modifyStaminaValue(entity, -decay);
            }
            StaminaBar.setIdleTime(entity, 60F * getIdleRate(entity));
        }

        // Regen
        if (decay <= 0 && StaminaBar.getIdleTime(entity) <= 0) {
            float regen = getRegenRate(entity);
            if (values[0] < values[1]) {
                StaminaBar.modifyStaminaValue(entity, regen);
            }
        }
        // Bonus Regen
        if (decay <= 0) {
            float regen = StaminaBar.getRegenBonus(entity);
            if (values[0] < values[1]) {
                StaminaBar.modifyStaminaValue(entity, regen);
            }
        }

        // float[] stam = new float[]{StaminaBar.getStaminaValue(player),
        // StaminaBar.getMaxStamina(player)};

        // out of stamina
        if (values[0] <= 0) {
            if (ConfigStamina.affectSpeed) {
                entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2));
            }
            entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 2));
            // Drain air when exhausted, you will drown faster
            if (isInWater(entity) && entity.getAir() > -20 && entity.ticksExisted % 20 == 0) {
                entity.setAir(entity.getAir() - 1);
            }
            if (entity instanceof EntityPlayer && TacticalManager.shouldStaminaBlock) {
                if (entity.isHandActive() && (!entity.getHeldItemMainhand().isEmpty() && entity.getHeldItemMainhand().getItemUseAction() == EnumAction.BLOCK)) {
                    entity.stopActiveHand();
                }
            }
        }
        // This phase decreases the cooldown
        if (StaminaBar.getIdleTime(entity) > 0) {
            StaminaBar.ModifyIdleTime(entity, -1);
        }
        if (StaminaBar.getFlashTime(entity) > 0) {
            StaminaBar.ModifyFlashTime(entity, -1);
        }

        // REFRESH
        StaminaBar.refreshSystem(entity);

        StaminaBar.tickBonus(entity);
        StaminaBar.tickBonusRegen(entity);

        // if changed, resync
        // if((StaminaBar.getStaminaValue(player) != stam[0] ||
        // StaminaBar.getMaxStamina(player) != stam[1]) || player.ticksExisted % 20 ==
        // 0)
        if (entity instanceof EntityPlayer) {
            syncStamina((EntityPlayer) entity, packet);
        }
    }

    private static void syncStamina(EntityPlayer player, float[] stam) {
        if (!player.world.isRemote) {
            NetworkHandler.sendToPlayer((EntityPlayerMP) player, new StaminaPacket(stam, player));
        }
    }

    public static float getConstantDecay(EntityLivingBase user) {
        if (user instanceof EntityPlayer && ((EntityPlayer) user).capabilities.isCreativeMode) {
            return 0F;
        }
        float value = 0.0F;
        boolean countArmour = false;

        if (user.isSprinting() && ConfigStamina.sprintModifier > 0 && user.getRidingEntity() == null) {
            float sprintingSeconds = 60F;
            countArmour = true;// Armour gets factored in with sprinting
            value += value += (5F / sprintingSeconds) * ConfigStamina.sprintModifier;
        }
        if (user instanceof EntityPlayer) {
            if (!user.getHeldItem(EnumHand.MAIN_HAND).isEmpty() && user.isHandActive() && ConfigStamina.bowModifier > 0) {
                if (user.getHeldItem(EnumHand.MAIN_HAND).getItemUseAction() == EnumAction.BOW) {
                    // Item gets factored in with bow
                    int bowSeconds = 30;
                    value += StaminaBar.getDefaultMax(user) / 20F / bowSeconds * ConfigStamina.bowModifier;
                }
            }
        }
        return value * StaminaBar.getBaseDecayModifier(user, countArmour, true);
    }

    public static float getRegenRate(EntityLivingBase user) {
        if (user instanceof EntityPlayer && ((EntityPlayer) user).capabilities.isCreativeMode) {
            return 100F;
        }
        if (StaminaBar.getIdleTime(user) > 0) {
            return 0;
        }
        if (isInWater(user) && user.getActivePotionEffect(MobEffects.WATER_BREATHING) == null) {
            return 0;// can't catch your breath when underwater
        }
        // The base time it takes to regen
        float value = StaminaBar.getTotalMaxStamina(user) / 20 / ConfigStamina.fullRegenSeconds;
        if (hurtAdreneline && user.getHealth() <= 2F) {
            value *= 1.5F;
        }
        return value * StaminaBar.getBaseRegenModifier(user, true, true);
    }

    private static boolean isInWater(EntityLivingBase user) {
        return user.isInsideOfMaterial(Material.WATER);
    }

    public static float getIdleRate(EntityLivingBase user) {
        float value = 1.0F;

        if (hurtAdreneline && user.getHealth() <= 4F) {
            value *= 0.2F;
        }

        return value * StaminaBar.getBaseIdleModifier(user, true, true);
    }

    public static void onJump(EntityLivingBase user) {
        if (user.world.isRemote) {
            return;
        }
        float stam = StaminaBar.getStaminaValue(user);
        if (stam > 0) {
            StaminaBar.modifyStaminaValue(user, -getJumpDecay(user));
        }
        StaminaBar.setIdleTime(user, 40F * getIdleRate(user));
        // syncStamina(user, new float[]{stam, StaminaBar.getMaxStamina(user)});
    }

    public static float getJumpDecay(EntityLivingBase user) {
        float value = 2.0F;

        if (user.isSprinting()) {
            value = 3.0F;
        }
        return value * StaminaBar.getBaseDecayModifier(user, true, false);
    }

    public static void onAttack(EntityLivingBase user, Entity target) {
        applyHitFatigue(user, 1.0F * TacticalManager.getHighgroundModifier(target, user, 1.5F));
    }

    public static void applyHitFatigue(EntityLivingBase user, float modifier) {
        float stam = StaminaBar.getStaminaValue(user);

        if (stam > 0) {
            StaminaBar.modifyStaminaValue(user, -getWeaponModifier(user) * modifier);
        }
        StaminaBar.setIdleTime(user, 50F * getIdleRate(user));
        // syncStamina(user, new float[]{stam, StaminaBar.getMaxStamina(user)});
    }

    public static float getWeaponModifier(EntityLivingBase user) {
        ItemStack weapon = user.getHeldItemMainhand();
        float value = 0.0F;

        if (!weapon.isEmpty()) {
            value = 2F;

            if (weapon.getItem() instanceof IStaminaWeapon) {
                value = ((IStaminaWeapon) weapon.getItem()).getStaminaDrainOnHit(user, weapon);
            } else {
                if (weapon.getItem() instanceof ItemSword) {
                    value = 5;
                }
                if (weapon.getItem() instanceof ItemTool) {
                    value = 10;
                }
            }
        }

        return value * ConfigStamina.weaponModifier * StaminaBar.getBaseDecayModifier(user, true, true);
    }
}
