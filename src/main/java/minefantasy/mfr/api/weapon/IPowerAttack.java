package minefantasy.mfr.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IPowerAttack {
	int getParryModifier(ItemStack weapon, EntityLivingBase user, Entity target);

	void onPowerAttack(float dam, EntityLivingBase user, Entity target, boolean properHit);
}
