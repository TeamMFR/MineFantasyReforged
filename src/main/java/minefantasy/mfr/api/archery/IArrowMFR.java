package minefantasy.mfr.api.archery;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public interface IArrowMFR {
	/**
	 * Gets the damage modifier (1.0 = Flint arrow)
	 * @param arrow The Itemstack of the Arrow
	 * @return the damage modifier as a float
	 */
	float getDamageModifier(ItemStack arrow);

	/**
	 * Gets the weight (1.0 = regular fall speed)
	 * @param arrow The Itemstack of the arrow
	 * @return The gravity modifier as a float
	 */
	float getGravityModifier(ItemStack arrow);

	/**
	 * Gets the chance the arrow will break on impact (0.0 = no chance, 1.0 = 100%
	 * chance)
	 * @param entityArrow The Entity of the Arrow (fired and flying through the air)
	 * @param arrow The Itemstack of the Arrow
	 * @return The break chance
	 */
	float getBreakChance(Entity entityArrow, ItemStack arrow);

	/**
	 * Allows for special effects to be applied to the hit entity on a per Arrow basis
	 * @param arrowInstance The Entity of the Arrow (fired and flying through the air)
	 * @param shooter The Entity that shot the Arrow
	 * @param hit The Entity that was hit by the Arrow
	 * @param damage The amount of damage being applied to the hit Entity
	 */
	void onHitEntity(Entity arrowInstance, Entity shooter, Entity hit, float damage);
}
