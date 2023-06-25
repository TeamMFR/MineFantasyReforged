package minefantasy.mfr.item;

import minefantasy.mfr.api.archery.IArrowHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;

public class BoltHandlerMFR implements IArrowHandler {

	@Override
	public EntityArrow onFireArrow(EntityArrow entityArrow, ItemStack bolt, ItemStack crossbow, float firepower, EntityPlayer player) {
		if (bolt.isEmpty() || !(bolt.getItem() instanceof ItemArrowMFR)) {
			return null;
		}

		if (!(((ItemArrowMFR) bolt.getItem()).getAmmoType(bolt).equalsIgnoreCase("bolt"))) {
			return null;
		}

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
		if (!crossbow.isEmpty() && crossbow.getItem() instanceof ItemCrossbow) {
			spread = ((ItemCrossbow) crossbow.getItem()).getFullValue(crossbow, "spread");
		}

		entityArrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, firepower * 3F, spread);

		return entityArrow;
	}
}
