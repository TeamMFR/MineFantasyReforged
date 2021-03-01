package minefantasy.mfr.api.archery;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public interface IArrowMFR {
    /**
     * Gets the damage modifier (1.0 = Flint arrow)
     */
	float getDamageModifier(ItemStack arrow);

    /**
     * Gets the weight (1.0 = regular fall speed)
     */
	float getGravityModifier(ItemStack arrow);

    /**
     * Gets the chance the arrow will break on impace (0.0 = no chance, 1.0 = 100%
     * chance)
     */
	float getBreakChance(Entity entityArrow, ItemStack arrow);

    void onHitEntity(Entity arrowInstance, Entity shooter, Entity hit, float damage);
}
