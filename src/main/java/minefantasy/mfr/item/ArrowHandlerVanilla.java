package minefantasy.mfr.item;

import minefantasy.mfr.api.archery.IArrowHandler;
import minefantasy.mfr.api.archery.ISpecialBow;
import minefantasy.mfr.config.ConfigWeapon;
import minefantasy.mfr.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemSpectralArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTippedArrow;

/**
 * This class is an example used to fire custom arrows.
 */
public class ArrowHandlerVanilla implements IArrowHandler {

	@Override
	public EntityArrow onFireArrow(EntityArrow entityArrow, ItemStack arrow, ItemStack bow, float charge, EntityPlayer player) {
		if (!Utils.isVanillaArrow(arrow)) {
			return null;
		}

		boolean isSpecial = arrow.getItem() instanceof ItemTippedArrow || arrow.getItem() instanceof ItemSpectralArrow;

		float maxCharge = 20F;
		if (!bow.isEmpty() && bow.getItem() instanceof ISpecialBow) {
			maxCharge = ((ISpecialBow) bow.getItem()).getMaxCharge(bow);
		}
		float firepower = charge / maxCharge * 20F;

		if (firepower < 0.1D) {
			return null;
		}
		if (firepower > 1.0F) {
			firepower = 1.0F;
		}

		float spread = isSpecial ? ConfigWeapon.vanillaSpecialArrowSpreadDefault : ConfigWeapon.vanillaRegularArrowSpreadDefault;
		if (!bow.isEmpty() && bow.getItem() instanceof ISpecialBow) {
			firepower *= ((ISpecialBow) bow.getItem()).getVelocity(bow);
			spread *= ((ISpecialBow) bow.getItem()).getSpread(bow);
		}

		float firepowerMod = (isSpecial ? ConfigWeapon.vanillaSpecialArrowFirepowerMod : ConfigWeapon.vanillaRegularArrowFirepowerMod);

		entityArrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, firepower * firepowerMod, spread);

		return entityArrow;
	}

}
