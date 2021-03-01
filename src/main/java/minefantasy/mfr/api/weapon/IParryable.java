package minefantasy.mfr.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public interface IParryable {
	/**
	 * Determines if the weapon can parry in the first place
	 */
	boolean canParry(DamageSource source, EntityLivingBase blocker, ItemStack item);

	/**
	 * This gets how accurate you need to be facing to parry
	 */
	float getParryAngle(DamageSource source, EntityLivingBase blocker, ItemStack item);

	/**
	 * Calls a method when the defender parrys the attacker
	 */
	void onParry(DamageSource source, EntityLivingBase user, Entity attacker, float dam);

	/**
	 * use this method to play custom sounds, return false if there is none
	 */
	boolean playCustomParrySound(EntityLivingBase blocker, Entity attacker, ItemStack weapon);

	/**
	 * Gets the maximum amount of damage parry can reduce
	 */
	float getMaxDamageParry(EntityLivingBase user, ItemStack weapon);

	boolean canUserParry(EntityLivingBase user);

	/**
	 * Modifies the amount stamina decays when parrying, 1.0 = default
	 */
	float getParryStaminaDecay(DamageSource source, ItemStack weapon);

	/**
	 * Gets how many ticks it takes to recover from a parry
	 */
	int getParryCooldown(DamageSource source, float dam, ItemStack weapon);
}
