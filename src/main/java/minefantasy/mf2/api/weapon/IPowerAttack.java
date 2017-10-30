package minefantasy.mf2.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IPowerAttack {
    public int getParryModifier(ItemStack weapon, EntityLivingBase user, Entity target);

    public void onPowerAttack(float dam, EntityLivingBase user, Entity target, boolean properHit);
}
