package minefantasy.mf2.api.archery;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public interface IArrowMF {
    /**
     * Gets the damage modifier (1.0 = Flint arrow)
     */
    public abstract float getDamageModifier(ItemStack arrow);

    /**
     * Gets the weight (1.0 = regular fall speed)
     */
    public abstract float getGravityModifier(ItemStack arrow);

    /**
     * Gets the chance the arrow will break on impace (0.0 = no chance, 1.0 = 100%
     * chance)
     */
    public abstract float getBreakChance(Entity entityArrow, ItemStack arrow);

    public abstract void onHitEntity(Entity arrowInstance, Entity shooter, Entity hit, float damage);
}
