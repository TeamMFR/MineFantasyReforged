package minefantasy.mf2.item.weapon;

import minefantasy.mf2.api.helpers.TacticalManager;
import minefantasy.mf2.api.weapon.WeaponClass;
import minefantasy.mf2.config.ConfigWeapon;
import mods.battlegear2.api.shield.IShield;
import mods.battlegear2.api.weapons.IExtendedReachWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemSpearMF extends ItemWeaponMF implements IExtendedReachWeapon {
    /**
     * The spear is for the defensive player, it has a long reach, knockback and can
     * be thrown. Spears are good for keeping enemies at a distance, Parrying is
     * easier when sneaking
     * <p>
     * These are for the defensive player
     */
    public ItemSpearMF(String name, ToolMaterial material, int rarity, float weight) {
        super(material, name, rarity, weight);
    }

    @Override
    public boolean allowOffhand(ItemStack mainhand, ItemStack offhand) {
        return offhand == null || offhand.getItem() instanceof IShield;
    }

    @Override
    public boolean isHeavyWeapon() {
        return true;
    }

    @Override
    public boolean isOffhandHandDual(ItemStack off) {
        return false;
    }

    @Override
    public boolean sheatheOnBack(ItemStack item) {
        return true;
    }

    @Override
    public float getReachModifierInBlocks(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean extra) {
        super.addInformation(item, user, list, extra);

        if (material != ToolMaterial.WOOD) {
            list.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted(
                    "attribute.modifier.plus." + 0, decimal_format.format(getMountedDamage()),
                    StatCollector.translateToLocal("attribute.weapon.mountedBonus")));
        }
    }

    @Override
    public float modifyDamage(ItemStack item, EntityLivingBase wielder, Entity hit, float initialDam,
                              boolean properHit) {
        float damage = super.modifyDamage(item, wielder, hit, initialDam, properHit);

        if (!(hit instanceof EntityLivingBase) || this instanceof ItemLance) {
            return damage;
        }
        EntityLivingBase target = (EntityLivingBase) hit;

        if (wielder.isRiding() && tryPerformAbility(wielder, charge_cost)) {
            ItemWaraxeMF.brutalise(wielder, target, 1.0F);
            return damage + getMountedDamage();
        }
        if (!wielder.isRiding() && wielder.isSprinting()) {
            if (this instanceof ItemHalbeardMF) {
                return Math.max(damage / 1.25F, 1.0F);
            } else {
                return damage * 1.25F;
            }
        }
        return damage;
    }

    @Override
    public boolean playCustomParrySound(EntityLivingBase blocker, Entity attacker, ItemStack weapon) {
        blocker.worldObj.playSoundAtEntity(blocker, "minefantasy2:weapon.wood_parry", 1.0F, 0.8F);
        return true;
    }

    @Override
    public void onParry(DamageSource source, EntityLivingBase user, Entity attacker, float dam) {
        super.onParry(source, user, attacker, dam);
        if (ConfigWeapon.useBalance && user instanceof EntityPlayer) {
            TacticalManager.throwPlayerOffBalance((EntityPlayer) user, getBalance(), rand.nextBoolean());
        }
    }

    private float getMountedDamage() {
        if (material == ToolMaterial.WOOD) {
            return 0;
        }
        return 4F;
    }

    @Override
    protected int getParryDamage(float dam) {
        return (int) dam;
    }

    /**
     * Gets the angle the weapon can parry
     */
    @Override
    public float getParryAngleModifier(EntityLivingBase user) {
        return user.isSneaking() ? 1.2F : 0.85F;
    }

    @Override
    public float getBalance() {
        return 0.5F;
    }

    @Override
    protected float getKnockbackStrength() {
        return 2.5F;
    }

    @Override
    protected boolean canAnyMobParry() {
        return false;
    }

    @Override
    public int modifyHitTime(EntityLivingBase user, ItemStack item) {
        return super.modifyHitTime(user, item) + speedModSpear;
    }

    /**
     * gets the time after being hit your guard will be let down
     */
    @Override
    public int getParryCooldown(DamageSource source, float dam, ItemStack weapon) {
        return spearParryTime;
    }

    @Override
    protected float getStaminaMod() {
        return spearStaminaCost;
    }

    @Override
    public WeaponClass getWeaponClass() {
        return WeaponClass.POLEARM;
    }

    @Override
    protected float[] getWeaponRatio(ItemStack implement) {
        return piercingDamage;
    }

    @Override
    public boolean canCounter() {
        return false;
    }

    @Override
    public float getScale(ItemStack itemstack) {
        return 3.0F;
    }
}
