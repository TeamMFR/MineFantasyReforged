package minefantasy.mf2.api.stamina;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IHeldStaminaItem {
    float getDecayMod(EntityLivingBase user, ItemStack item);

    float getRegenModifier(EntityLivingBase user, ItemStack item);

    float getIdleModifier(EntityLivingBase user, ItemStack item);
}
