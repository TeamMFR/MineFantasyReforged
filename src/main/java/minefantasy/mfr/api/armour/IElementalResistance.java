package minefantasy.mfr.api.armour;

import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public interface IElementalResistance {
	/**
	 * @return this determines the chance an item is to deflect arrows. more damage
	 * means less chance
	 */
	float getArrowDeflection(ItemStack item, DamageSource source);

	/**
	 * @return the Magic resistance percent (0-100) (for the whole suit) -values
	 * mean weakness
	 */
	float getMagicResistance(ItemStack item, DamageSource source);

	/**
	 * @return the Magic resistance percent (0-100) (for the whole suit) -values
	 * mean weakness
	 */
	float getFireResistance(ItemStack item, DamageSource source);

	/**
	 * @return the Base resistance percent for non magic/fire(0-100) (for the whole
	 * suit) -values mean weakness
	 */
	float getBaseResistance(ItemStack item, DamageSource source);
}
