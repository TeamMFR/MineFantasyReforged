package minefantasy.mfr.api.armour;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ISpecialArmourMFR {
	float getDTValue(EntityLivingBase user, ItemStack armour, DamageSource src);

	@SideOnly(Side.CLIENT)
	/**
	 * DamageType: 0=cutting, 1=blunt, 2=piercing
	 */
	float getDTDisplay(ItemStack armour, int damageType);

	float getDRValue(EntityLivingBase user, ItemStack armour, DamageSource src);

	@SideOnly(Side.CLIENT)
	/**
	 * DamageType: 0=cutting, 1=blunt, 2=piercing
	 */
	float getDRDisplay(ItemStack armour, int damageType);
}
