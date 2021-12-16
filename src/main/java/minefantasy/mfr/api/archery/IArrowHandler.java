package minefantasy.mfr.api.archery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IArrowHandler {
	/**
	 * Fire an Arrow
	 * @param world The Minecraft world variable
	 * @param arrow The Itemstack of the Arrow
	 * @param bow The Itemstack of the Bow
	 * @param user The EntityPlayer using the bow
	 * @param charge The amount of charge (how fire the bowstring was pulled back)
	 * @param infinite Does the bow have Infinity Enchantment or not
	 * @return true if the arrow has been fired
	 */
	boolean onFireArrow(World world, ItemStack arrow, ItemStack bow, EntityPlayer user, float charge, boolean infinite);
}
