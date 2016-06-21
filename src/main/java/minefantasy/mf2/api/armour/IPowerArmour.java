package minefantasy.mf2.api.armour;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public interface IPowerArmour {

	boolean isFullyArmoured();

	float modifyDamage(EntityLivingBase user, float damage, DamageSource src);

	boolean isPowered();

}
