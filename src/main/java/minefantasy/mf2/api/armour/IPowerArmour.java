package minefantasy.mf2.api.armour;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public interface IPowerArmour {

    boolean isFullyArmoured();

    float modifyDamage(EntityLivingBase user, float damage, DamageSource src);

    boolean isPowered();

    /**
     * Is armour covering a particular limb
     *
     * @param limb (head, body, left_arm, right_arm, left_leg, right_leg)
     */
    boolean isArmoured(String limb);

}
