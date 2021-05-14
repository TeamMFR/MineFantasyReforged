package minefantasy.mfr.api.armour;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ISpecialArmourMFR {
	float getDamageTypeValue(EntityLivingBase user, ItemStack armour, DamageSource src);


	/**
	 * DamageType: 0=cutting, 1=blunt, 2=piercing
	 */
	@SideOnly(Side.CLIENT)
	float getDamageTypeDisplay(ItemStack armour, int damageType);

	float getDamageRatingValue(EntityLivingBase user, ItemStack armour, DamageSource src);

	/**
	 * DamageType: 0=cutting, 1=blunt, 2=piercing
	 */
	@SideOnly(Side.CLIENT)
	float getDamageRatingDisplay(ItemStack armour, int damageType);
}
