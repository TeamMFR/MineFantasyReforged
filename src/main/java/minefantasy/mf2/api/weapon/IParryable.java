package minefantasy.mf2.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public interface IParryable {
    /**
     * Determines if the weapon can parry in the first place
     */
    public boolean canParry(DamageSource source, EntityLivingBase blocker, ItemStack item);

    /**
     * This gets how accurate you need to be facing to parry
     */
    public float getParryAngle(DamageSource source, EntityLivingBase blocker, ItemStack item);

    /**
     * Calls a method when the defender parrys the attacker
     */
    public void onParry(DamageSource source, EntityLivingBase user, Entity attacker, float dam);

    /**
     * use this method to play custom sounds, return false if there is none
     */
    public boolean playCustomParrySound(EntityLivingBase blocker, Entity attacker, ItemStack weapon);

    /**
     * Gets the maximum amount of damage parry can reduce
     */
    public float getMaxDamageParry(EntityLivingBase user, ItemStack weapon);

    public boolean canUserParry(EntityLivingBase user);

    /**
     * Modifies the amount stamina decays when parrying, 1.0 = default
     */
    public float getParryStaminaDecay(DamageSource source, ItemStack weapon);

    /**
     * Gets how many ticks it takes to recover from a parry
     */
    public int getParryCooldown(DamageSource source, float dam, ItemStack weapon);
}
