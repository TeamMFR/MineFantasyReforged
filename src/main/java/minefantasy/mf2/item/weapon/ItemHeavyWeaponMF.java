package minefantasy.mf2.item.weapon;

import mods.battlegear2.api.weapons.IExtendedReachWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public abstract class ItemHeavyWeaponMF extends ItemWeaponMF implements IExtendedReachWeapon {

    /**
     * Heavy weapons are larger varients of their own counterparts(sword, waraxe,
     * mace and spear). These have 2x the durability, have a wider parry arc, and do
     * 50% more damage.
     * <p>
     * Heavy weapons weigh more and throw you off balance when used.
     */
    public ItemHeavyWeaponMF(ToolMaterial material, String named, int rarity, float weight) {
        super(material, named, rarity, weight);
        setMaxDamage((int) (getMaxDamage() * 1.5F));
    }

    @Override
    public boolean sheatheOnBack(ItemStack item) {
        return true;
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
    public boolean allowOffhand(ItemStack mainhand, ItemStack offhand) {
        return false;
    }

    public int getParryCooldown(EntityLivingBase user) {
        return 18;
    }

    /**
     * Gets the angle the weapon can parry
     */
    @Override
    public float getParryAngleModifier(EntityLivingBase user) {
        return 1.0F;
    }

    @Override
    public float getBalance() {
        return 0.50F;
    }

    @Override
    protected float getKnockbackStrength() {
        return 1.5F;
    }

    @Override
    public float getDecayModifier(EntityLivingBase user, ItemStack item) {
        return 1.25F;
    }

    @Override
    protected boolean canAnyMobParry() {
        return false;
    }

    @Override
    public int modifyHitTime(EntityLivingBase user, ItemStack item) {
        return super.modifyHitTime(user, item) + speedModHeavy;
    }

    @Override
    protected float[] getWeaponRatio(ItemStack implement) {
        return hvySlashingDamage;
    }

    @Override
    public float getParryStaminaDecay(DamageSource source, ItemStack weapon) {
        return heavyParryFatigue;
    }

    /**
     * gets the time after being hit your guard will be let down
     */
    @Override
    public int getParryCooldown(DamageSource source, float dam, ItemStack weapon) {
        return heavyParryTime;
    }

    @Override
    public int getParryModifier(ItemStack weapon, EntityLivingBase user, Entity target) {
        return 50;
    }

    @Override
    protected float getStaminaMod() {
        return heavyStaminaCost;
    }

    @Override
    public float getReachModifierInBlocks(ItemStack stack) {
        return 2.0F;
    }

    @Override
    protected float getMeleeDamage(ItemStack item) {
        return super.getMeleeDamage(item) * 1.5F;
    }

    @Override
    public float getScale(ItemStack itemstack) {
        return 2.0F;
    }
}
