package minefantasy.mf2.api.weapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IKnockbackWeapon {
    public float getAddedKnockback(EntityLivingBase user, ItemStack item);
}
