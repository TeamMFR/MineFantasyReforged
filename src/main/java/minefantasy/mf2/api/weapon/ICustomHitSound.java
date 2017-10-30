package minefantasy.mf2.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface ICustomHitSound {
    /**
     * Plays the hit sound
     *
     * @return true if it makes it's own sound
     */
    public boolean playHitSound(ItemStack item, EntityLivingBase attacker, Entity target);
}
