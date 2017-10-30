package minefantasy.mf2.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface ISpecialEffect {
    public void onProperHit(EntityLivingBase user, ItemStack weapon, Entity hit, float dam);
}
