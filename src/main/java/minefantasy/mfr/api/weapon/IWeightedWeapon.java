package minefantasy.mfr.api.weapon;

import net.minecraft.entity.EntityLivingBase;

public interface IWeightedWeapon {
	/**
	 * Gets the amount that you are thrown off balance, <= 0 means no effect Spears
	 * use 0.5
	 */
	float getBalance(EntityLivingBase user);
}
