package minefantasy.mfr.api.weapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IWeaponSpeed {
    public int modifyHitTime(EntityLivingBase user, ItemStack item);
}
