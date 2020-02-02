package minefantasy.mfr.item.archery;

import minefantasy.mfr.api.archery.IArrowHandler;
import minefantasy.mfr.api.archery.ISpecialBow;
import minefantasy.mfr.entity.EntityArrowMFR;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * This class is an example used to fire custom arrows.
 */
public class ArrowFirerMF implements IArrowHandler {

    @Override
    public boolean onFireArrow(World world, ItemStack arrow, ItemStack bow, EntityPlayer user, float charge,
                               boolean infinite) {
        if (arrow == null || !(arrow.getItem() instanceof ItemArrowMFR)) {
            return false;
        }

        float maxCharge = 20F;
        if (bow != null && bow.getItem() instanceof ISpecialBow) {
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
        if (bow != null && bow.getItem() instanceof ISpecialBow) {
            firepower *= ((ISpecialBow) bow.getItem()).getRange(bow);
            spread = ((ISpecialBow) bow.getItem()).getSpread(bow);
        }

        EntityArrowMFR entArrow = ammo.getFiredArrow(new EntityArrowMFR(world, user, spread, firepower * 2.0F), arrow);

        int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(48), bow);
        entArrow.setPower(1 + (0.25F * var9));

        int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(49), bow);

        if (var10 > 0) {
            entArrow.setKnockbackStrength(var10);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(50), bow) > 0) {
            entArrow.setFire(100);
        }

        if (infinite) {
            entArrow.canBePickedUp = 2;
        }

        if (bow != null && bow.getItem() != null && bow.getItem() instanceof ISpecialBow) {
            entArrow = (EntityArrowMFR) ((ISpecialBow) bow.getItem()).modifyArrow(bow, entArrow);
        }
        if (!world.isRemote) {
            world.spawnEntity(entArrow);
        }

        return true;
    }

}
