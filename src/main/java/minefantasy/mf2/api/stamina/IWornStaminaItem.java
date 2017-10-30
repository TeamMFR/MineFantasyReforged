package minefantasy.mf2.api.stamina;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IWornStaminaItem {
    /**
     * This adds up between armour pieces: a - value takes off points while a + adds
     * to them
     */
    float getDecayModifier(EntityLivingBase user, ItemStack item);

    /**
     * This adds up between armour pieces: a - value takes off points while a + adds
     * to them
     */
    float getRegenModifier(EntityLivingBase user, ItemStack item);

    /**
     * This adds up between armour pieces: a - value takes off points while a + adds
     * to them
     */
    float getIdleModifier(EntityLivingBase user, ItemStack item);
}
