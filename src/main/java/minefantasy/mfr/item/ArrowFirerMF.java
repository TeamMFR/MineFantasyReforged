package minefantasy.mfr.item;

import minefantasy.mfr.api.archery.IArrowHandler;
import minefantasy.mfr.api.archery.ISpecialBow;
import minefantasy.mfr.entity.EntityArrowMFR;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * This class is an example used to fire custom arrows.
 */
public class ArrowFirerMF implements IArrowHandler {

	@Override
	public boolean onFireArrow(World world, ItemStack arrow, ItemStack bow, EntityPlayer user, float charge, boolean infinite) {
		if (arrow.isEmpty() || !(arrow.getItem() instanceof ItemArrowMFR)) {
			return false;
		}

		float maxCharge = 20F;
		if (!bow.isEmpty() && bow.getItem() instanceof ISpecialBow) {
			// maxCharge = ((ISpecialBow)bow.getItem()).getMaxCharge();
		} else {
			return false;
		}
		float firepower = charge / maxCharge * 20F;

		if (firepower < 0.1D) {
			return false;
		}
		if (firepower > 1.0F) {
			firepower = 1.0F;
		}

		ItemArrowMFR ammo = (ItemArrowMFR) arrow.getItem();
		float spread = 1.0F;
		if (!bow.isEmpty() && bow.getItem() instanceof ISpecialBow) {
			firepower *= ((ISpecialBow) bow.getItem()).getVelocity(bow);
			spread = ((ISpecialBow) bow.getItem()).getSpread(bow);
		}

		EntityArrowMFR entArrow = ammo.getFiredArrow(new EntityArrowMFR(world, user, spread, firepower * 2.0F), arrow);

		int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bow);
		entArrow.setPower(1 + (0.25F * power));

		int punch = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, bow);

		if (punch > 0) {
			entArrow.setKnockbackStrength(punch);
		}

		if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bow) > 0) {
			entArrow.setFire(100);
		}

		if (infinite) {
			entArrow.canBePickedUp = 2;
		}

		if (!bow.isEmpty() && bow.getItem() instanceof ISpecialBow) {
			entArrow = (EntityArrowMFR) ((ISpecialBow) bow.getItem()).modifyArrow(bow, entArrow);
		}
		if (!world.isRemote) {
			world.spawnEntity(entArrow);
		}

		return true;
	}

}
