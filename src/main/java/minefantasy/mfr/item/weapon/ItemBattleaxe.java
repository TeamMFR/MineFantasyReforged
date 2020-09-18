package minefantasy.mfr.item.weapon;

import minefantasy.mfr.api.helpers.TacticalManager;
import minefantasy.mfr.api.weapon.WeaponClass;
import minefantasy.mfr.client.render.item.RenderBigTool;
import minefantasy.mfr.init.SoundsMFR;
import minefantasy.mfr.mechanics.EventManagerMFR;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;

/**
 * @author Anonymous Productions
 */
public class ItemBattleaxe extends ItemHeavyWeapon {
    private float injuryChance = 0.05F;

    /**
     * A battleaxe is a heavy counterpart to the waraxe, it does more damage and
     * knockback, can also parry easier
     */
    public ItemBattleaxe(String name, Item.ToolMaterial material, int rarity, float weight) {
        super(material, name, rarity, weight);
    }

    public static void brutalise(EntityLivingBase entityHitting, EntityLivingBase entityHit, float power) {
        if (!entityHit.world.isRemote) {
            if (entityHitting instanceof EntityPlayer) {
                ((EntityPlayer) entityHitting).onCriticalHit(entityHit);
            }
        }
    }

    @Override
    public float modifyDamage(ItemStack item, EntityLivingBase wielder, Entity hit, float initialDam,
                              boolean properHit) {
        float damage = super.modifyDamage(item, wielder, hit, initialDam, properHit);
        if (!(hit instanceof EntityLivingBase)) {
            return damage;
        }
        EntityLivingBase target = (EntityLivingBase) hit;

        if (wielder.isSprinting()) {
            if (wielder.fallDistance > 0 && canLunge(wielder)
                    && tryPerformAbility(wielder, lunge_cost, !properHit, true, true, true))// LUNGE: 20 Points
            {
                return chargeAt(wielder, target, true, damage, properHit);
            } else if (wielder.onGround && getHeightGap(target, wielder) <= 0.5F
                    && tryPerformAbility(wielder, charge_cost))// CHARGE: 10 points
            {
                return chargeAt(wielder, target, false, damage, properHit);
            }
        } else if (wielder.fallDistance > 0 && !wielder.isOnLadder() && tryPerformAbility(wielder, jump_cost))// JUMP: 2
        // points
        {
            if (properHit)
                brutalise(wielder, target, 1.0F);
            return damage * 1.25F;
        }
        return damage;
    }

    private float chargeAt(EntityLivingBase entityHitting, EntityLivingBase entityHit, boolean lunge, float dam,
                           boolean properHit) {
        if (properHit)
            brutalise(entityHitting, entityHit, 1.0F);

        if (lunge) {
            float fallBonus = (Math.min(entityHitting.fallDistance, getMeleeDamage(entityHitting.getHeldItemMainhand()))) / 2F;
            if (entityHit.onGround && !properHit) {
                entityHitting.hurtResistantTime = 20;
                TacticalManager.knockbackEntity(entityHit, entityHitting, 4F, 0.5F);
                TacticalManager.lungeEntity(entityHitting, entityHit, 2F, 0.1F);
            }
            if (properHit) {
                if (!entityHit.isPotionActive(MobEffects.SLOWNESS)) {
                    entityHit.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 400, 3));
                    entityHit.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 1));
                }

                entityHitting.fallDistance -= fallBonus;
            }
            if (fallBonus > 0) {
                dam += fallBonus;
            }
        } else {
            if (properHit) {
                if (entityHitting.onGround && entityHit.onGround && !properHit) {
                    entityHitting.hurtResistantTime = 20;
                    TacticalManager.lungeEntity(entityHitting, entityHit, 1F, 0.0F);
                }
                if (properHit) {
                    if (!entityHit.isPotionActive(MobEffects.SLOWNESS)) {
                        entityHit.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 3));
                        entityHit.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100, 0));
                    }
                }
            }
        }
        return dam * 1.25F;
    }

    @Override
    public void onProperHit(EntityLivingBase user, ItemStack weapon, Entity hit, float dam) {
        if (rand.nextFloat() < injuryChance) {
            hit.getEntityData().setInteger(EventManagerMFR.injuredNBT, 300 + rand.nextInt(1000));
        }
        super.onProperHit(user, weapon, hit, dam);
    }

    /**
     * Determines if sprint-jump lunges work
     */
    private boolean canLunge(EntityLivingBase entityHitting) {
        return true;
    }

    private double getHeightGap(EntityLivingBase entityHit, EntityLivingBase entityHitting) {
        double a = entityHitting.posY - entityHit.posY;
        return Math.max(a, -a);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        if (Items.IRON_AXE.getDestroySpeed(stack, state) > 2.0F) {
            return material.getAttackDamage() * 0.75F;
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean playCustomParrySound(EntityLivingBase blocker, Entity attacker, ItemStack weapon) {
        blocker.world.playSound(blocker.posX, blocker.posY, blocker.posZ, SoundsMFR.WOOD_PARRY, SoundCategory.NEUTRAL, 1.0F, 0.75F, true);
        return true;
    }

    @Override
    public int modifyHitTime(EntityLivingBase user, ItemStack item) {
        return super.modifyHitTime(user, item) + speedModAxe;
    }

    @Override
    public float getDamageModifier() {
        return damageModAxe;
    }

    @Override
    protected float[] getWeaponRatio(ItemStack implement) {
        return hvyHackingDamage;
    }

    @Override
    public float getPenetrationLevel(Object implement) {
        return axeAPModifier;
    }

    /**
     * gets the time after being hit your guard will be let down
     */
    @Override
    public int getParryCooldown(DamageSource source, float dam, ItemStack weapon) {
        return axeParryTime + heavyParryTime;
    }

    @Override
    protected float getStaminaMod() {
        return heavyStaminaCost * axeStaminaCost;
    }

    @Override
    public WeaponClass getWeaponClass() {
        return WeaponClass.AXE;
    }

    @Override
    public boolean canCounter() {
        return true;
    }

    @Override
    public float[] getCounterRatio() {
        return crushingDamage;
    }

    @Override
    public float getCounterDamage() {
        return 0.5F;
    }


    @Override
    public void registerClient() {
        ModelResourceLocation modelLocation = new ModelResourceLocation(getRegistryName(), "normal");
        ModelLoaderHelper.registerWrappedItemModel(this, new RenderBigTool(() -> modelLocation, 2F,-0.27F), modelLocation);
    }
}
