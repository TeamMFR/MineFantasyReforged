package minefantasy.mf2.item.weapon;

import minefantasy.mf2.api.weapon.WeaponClass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import java.util.Random;

/**
 * @author Anonymous Productions
 */
public class ItemGreatswordMF extends ItemHeavyWeaponMF {
    private Random rand = new Random();

    /**
     * Greatswords are heavy counterparts to swords, with added damage, knockback
     * and parrying arc
     */
    public ItemGreatswordMF(String name, ToolMaterial material, int rarity, float weight) {
        super(material, name, rarity, weight);
    }

    @Override
    public boolean canBlock() {
        return true;
    }

    /**
     * Determines if the weapon can parry
     */
    @Override
    public boolean canWeaponParry() {
        return true;
    }

    @Override
    public boolean canWeaponEvade() {
        return true;
    }

    @Override
    protected int getParryDamage(float dam) {
        return 1;
    }

    /**
     * gets the time after being hit your guard will be let down
     */
    @Override
    public int getParryCooldown(EntityLivingBase user) {
        return 12;
    }

    /**
     * Gets the angle the weapon can parry
     */
    @Override
    public float getParryAngleModifier(EntityLivingBase user) {
        return 1.5F;
    }

    /**
     * Gets the multiplier for the parry threshold
     *
     * @return
     */
    @Override
    public float getParryDamageModifier(EntityLivingBase user) {
        return user instanceof EntityPlayer ? 3.0F : 1.5F;
    }

    @Override
    public boolean playCustomParrySound(EntityLivingBase blocker, Entity attacker, ItemStack weapon) {
        blocker.worldObj.playSoundAtEntity(blocker, "mob.zombie.metal", 1.0F, 0.75F + (rand.nextFloat() * 0.5F));
        return true;
    }

    @Override
    public float getBalance() {
        return 0.5F;
    }

    @Override
    protected boolean canAnyMobParry() {
        return true;
    }

    @Override
    public int modifyHitTime(EntityLivingBase user, ItemStack item) {
        return super.modifyHitTime(user, item) + speedModSword;
    }

    /**
     * gets the time after being hit your guard will be let down
     */
    @Override
    public int getParryCooldown(DamageSource source, float dam, ItemStack weapon) {
        return swordParryTime + heavyParryTime;
    }

    @Override
    protected float getStaminaMod() {
        return heavyStaminaCost * swordStaminaCost;
    }

    @Override
    public WeaponClass getWeaponClass() {
        return WeaponClass.BLADE;
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
}
