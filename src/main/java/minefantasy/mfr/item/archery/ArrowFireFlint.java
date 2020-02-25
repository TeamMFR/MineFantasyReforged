package minefantasy.mfr.item.archery;

import minefantasy.mfr.api.archery.IArrowHandler;
import minefantasy.mfr.api.archery.ISpecialBow;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * This class is an example used to fire custom arrows.
 */
public class ArrowFireFlint implements IArrowHandler {

    @Override
    public boolean onFireArrow(World world, ItemStack arrow, ItemStack bow, EntityPlayer user, float charge,
                               boolean infinite) {
        if (infinite || arrow.getItem() != Items.ARROW) {
            return false;
        }
        float maxCharge = 20F;
        if (bow != null && bow.getItem() instanceof ISpecialBow) {
            maxCharge = ((ISpecialBow) bow.getItem()).getMaxCharge();
        }
        float firepower = charge / maxCharge;

        if (firepower < 0.1D) {
            return false;
        }
        if (firepower > 1.0F) {
            firepower = 1.0F;
        }

        EntityArrow entArrow = new EntityTippedArrow(world, user);

        int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(48), bow);

        if (var9 > 0) {
            entArrow.setDamage(entArrow.getDamage() + var9 * 0.5D + 0.5D);
        }

        int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(49), bow);

        if (var10 > 0) {
            entArrow.setKnockbackStrength(var10);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(50), bow) > 0) {
            entArrow.setFire(100);
        }

        if (infinite) {
            entArrow.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
        }

        if (bow != null && bow.getItem() != null && bow.getItem() instanceof ISpecialBow) {
            entArrow = (EntityArrow) ((ISpecialBow) bow.getItem()).modifyArrow(bow, entArrow);
        }
        if (!world.isRemote) {
            world.spawnEntity(entArrow);
        }

        return true;
    }

}
