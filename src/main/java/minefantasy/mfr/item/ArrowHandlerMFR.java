package minefantasy.mfr.item;

import minefantasy.mfr.api.archery.IArrowHandler;
import minefantasy.mfr.api.archery.ISpecialBow;
import minefantasy.mfr.entity.EntityArrowMFR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;

/**
 * This class is an example used to fire custom arrows.
 */
public class ArrowHandlerMFR implements IArrowHandler {

	@Override
	public EntityArrow onFireArrow(EntityArrow entityArrow, ItemStack arrow, ItemStack bow, float charge, EntityPlayer player) {
		if (arrow.isEmpty() || !(arrow.getItem() instanceof ItemArrowMFR)) {
			return null;
		}

		float maxCharge;
		if (!bow.isEmpty() && bow.getItem() instanceof ISpecialBow) {
			maxCharge = ((ISpecialBow)bow.getItem()).getMaxCharge(bow);
		} else {
			return null;
		}
		float firepower = charge / maxCharge * 20F;

		if (firepower < 0.1D) {
			return null;
		}
		if (firepower > 1.0F) {
			firepower = 1.0F;
		}

		if (firepower == 1.0f) {
			entityArrow.setIsCritical(true);
		}

		float spread = 1.0F;
		if (!bow.isEmpty() && bow.getItem() instanceof ISpecialBow) {
			firepower *= ((ISpecialBow) bow.getItem()).getVelocity(bow);
			spread = ((ISpecialBow) bow.getItem()).getSpread(bow);
		}

		if (!bow.isEmpty() && bow.getItem() instanceof ISpecialBow) {
			entityArrow = (EntityArrowMFR) ((ISpecialBow) bow.getItem()).modifyArrow(bow, entityArrow);
		}

		entityArrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, firepower * 3.0F, spread);

		return entityArrow;
	}

}
