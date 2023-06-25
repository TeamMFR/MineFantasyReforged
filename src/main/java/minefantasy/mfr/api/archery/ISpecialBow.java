package minefantasy.mfr.api.archery;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public interface ISpecialBow {
	/**
	 * This is called when an arrow is fired from a bow if you have your own arrows
	 * firing, call this method when spawning it in world
	 * <p>
	 * Allows to modify an arrow when fired from this bow
	 *
	 * @param bow The Itemstack of the bow that is being used
	 * @param arrow the arrow fired(use a cast to determine what class it is)
	 * @return the same arrow, but modified
	 */
	Entity modifyArrow(ItemStack bow, Entity arrow);

	/**
	 * The max charge (bow drawback length) possible on the bow
	 * @return The max charge
	 */
	float getMaxCharge(ItemStack bow);

	/**
	 *
	 * @param bow The Itemstack of the bow that is being used
	 * @return The velocity modifier of the projectile being fired
	 */
	float getVelocity(ItemStack bow);

	/**
	 *
	 * @param bow The Itemstack of the bow that is being used
	 * @return The relative variance in projectile accuracy
	 */
	float getSpread(ItemStack bow);
}
