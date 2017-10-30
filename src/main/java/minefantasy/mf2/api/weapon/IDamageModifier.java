package minefantasy.mf2.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IDamageModifier {
    public float modifyDamage(ItemStack item, EntityLivingBase wielder, Entity hit, float initialDam,
                              boolean properHit);
}
