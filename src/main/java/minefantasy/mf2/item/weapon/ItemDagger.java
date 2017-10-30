package minefantasy.mf2.item.weapon;

import minefantasy.mf2.api.stamina.StaminaBar;
import minefantasy.mf2.api.weapon.WeaponClass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import java.util.Random;

/**
 * @author Anonymous Productions
 */
public class ItemDagger extends ItemWeaponMF {
    private Random rand = new Random();

    /**
     * Daggers are fast and weak weapons, but are fast and light
     * <p>
     * These are for the fast player
     */
    public ItemDagger(String name, ToolMaterial material, int rarity, float weight) {
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
    protected int getParryDamage(float dam) {
        return 2;
    }

    /**
     * Gets the angle the weapon can parry
     */
    @Override
    public float getParryAngleModifier(EntityLivingBase user) {
        return 0.75F;
    }

    /**
     * Gets the multiplier for the parry threshold
     *
     * @return
     */
    @Override
    public float getParryDamageModifier(EntityLivingBase user) {
        return 0.5F;
    }

    @Override
    public boolean playCustomParrySound(EntityLivingBase blocker, Entity attacker, ItemStack weapon) {
        blocker.worldObj.playSoundAtEntity(blocker, "mob.zombie.metal", 1.0F, 1.65F + (rand.nextFloat() * 0.5F));
        return true;
    }

    @Override
    public float getDecayModifier(EntityLivingBase user, ItemStack item) {
        return 0.5F;
    }

    @Override
    public float getRegenModifier(EntityLivingBase user, ItemStack item) {
        return user.worldObj.difficultySetting.getDifficultyId() < 3 ? 1.5F : 1.0F;
    }

    @Override
    public int modifyHitTime(EntityLivingBase user, ItemStack item) {
        if (!StaminaBar.isSystemActive || StaminaBar.isAnyStamina(user, false)) {
            return speedModKatana;
        }
        return 0;
    }

    /**
     * gets the time after being hit your guard will be let down
     */
    @Override
    public int getParryCooldown(DamageSource source, float dam, ItemStack weapon) {
        return daggerParryTime;
    }

    @Override
    protected float getStaminaMod() {
        return daggerStaminaCost;
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
        return piercingDamage;
    }

    @Override
    protected float[] getWeaponRatio(ItemStack implement) {
        return piercingDamage;
    }

    @Override
    public float getCounterDamage() {
        return 1.5F;
    }

    @Override
    protected float getMeleeDamage(ItemStack item) {
        return super.getMeleeDamage(item) / 2;
    }
}
