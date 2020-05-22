package minefantasy.mfr.mechanics;

import minefantasy.mfr.init.SoundsMFR;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import minefantasy.mfr.api.archery.AmmoMechanicsMFR;
import minefantasy.mfr.api.stamina.StaminaBar;
import minefantasy.mfr.config.ConfigStamina;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ArrowHandlerMF {
    /**
     * This method adds arrows to the bow
     */
    public static void loadArrow(EntityPlayer player, ItemStack bow, ItemStack arrow) {
        if (player.world.isRemote) {
            return;
        }
        NBTTagCompound nbt = getOrApplyNBT(bow);

        if (arrow == null) {
            nbt.removeTag("loadedArrow");
        } else {
            NBTTagCompound loaded = new NBTTagCompound();
            arrow.writeToNBT(loaded);
            nbt.setTag("loadedArrow", loaded);
        }
    }

    /**
     * Gets the NBT, if there is none, it creates it
     */
    private static NBTTagCompound getOrApplyNBT(ItemStack bow) {
        if (!bow.hasTagCompound()) {
            bow.setTagCompound(new NBTTagCompound());
        }
        return bow.getTagCompound();
    }


    // TODO: Replace deprecated methods

    /**
     * When the arrow is pulled back it initiates
     */
    @SubscribeEvent
    public void readyBow(ArrowNockEvent event) {
        EntityPlayer user = event.getEntityPlayer();
        ItemStack bow = event.getBow();

        if (AmmoMechanicsMFR.arrows == null || AmmoMechanicsMFR.arrows.size() <= 0) {
            if (getIsInfinite(event.getEntityPlayer(), event.getBow())) {
                ReflectionHelper.setPrivateValue(EntityPlayer.class, user, event.getBow().getMaxItemUseDuration());
                user.getDataManager().set(ReflectionHelper.getPrivateValue(EntityPlayer.class, user, "HAND_STATES","field_184621_as"), (byte) 1);// Starts pullback
                event.setCanceled(true);
            }
            return;
        }

        /*
         * Checks over registered arrows and finds one to load The Quiver can be used to
         * determine this
         */
        ItemStack arrowToFire = AmmoMechanicsMFR.reloadBow(bow);
        if (arrowToFire != null) {
            ReflectionHelper.setPrivateValue(EntityPlayer.class, user, event.getBow().getMaxItemUseDuration());
            user.getDataManager().set(ReflectionHelper.getPrivateValue(EntityPlayer.class, user, "HAND_STATES","field_184621_as"), (byte) 1);// Starts pullback
            loadArrow(user, bow, arrowToFire);// adds the arrow to NBT for rendering and later use
            event.setCanceled(true);
            return;
        } else {
            if (getIsInfinite(event.getEntityPlayer(), event.getBow())) {
                ReflectionHelper.setPrivateValue(EntityPlayer.class, user, event.getBow().getMaxItemUseDuration());
                user.getDataManager().set(ReflectionHelper.getPrivateValue(EntityPlayer.class, user, "HAND_STATES","field_184621_as"), (byte) 1);// Starts pullback
                event.setCanceled(true);
                return;
            }
        }
        /*
         * for(int a = 0; a < AmmoMechanicsMF.arrows.size(); a ++) { ItemStack arrow =
         * AmmoMechanicsMF.arrows.get(a); if(user.inventory.hasItemStack(arrow)) {
         * user.setItemInUse(bow, bow.getMaxItemUseDuration());//Starts pullback
         * loadArrow(user, bow, arrow);//adds the arrow to NBT for rendering and later
         * use event.setCanceled(true); return; } }
         */
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void applyExhaustArrow(ArrowLooseEvent event) {
        if (StaminaBar.isSystemActive && !StaminaBar.isAnyStamina(event.getEntityPlayer(), false)) {
            if (ConfigStamina.weaponDrain < 1.0F)
                event.setCharge(event.getCharge() * (int) ConfigStamina.weaponDrain);
        }
    }

    /**
     * This initates when firing a bow
     *
     * @param event
     */
    @SubscribeEvent
    public void fireArrow(ArrowLooseEvent event) {
        float power = event.getCharge();
        EntityPlayer user = event.getEntityPlayer();

        ItemStack bow = event.getBow();
        World world = event.getEntity().world;
        boolean creative = user.capabilities.isCreativeMode;

        float charge = power / 20.0F;
        charge = (charge * charge + charge * 2.0F) / 3.0F;

        if (charge < 0.1D) {
            return;
        }

        if (charge > 1.0F) {
            charge = 1.0F;
        }

        // Default is flint arrow
        ItemStack arrow = new ItemStack(Items.ARROW);
        if (AmmoMechanicsMFR.getArrowOnBow(bow) != null) {
            // if an arrow is on the bow, it uses that
            arrow = AmmoMechanicsMFR.getArrowOnBow(bow);
        }
        if (AmmoMechanicsMFR.handlers != null && AmmoMechanicsMFR.handlers.size() > 0) {
            for (int a = 0; a < AmmoMechanicsMFR.handlers.size(); a++) {
                // If the Arrow handler succeeds at firing an arrow
                if (AmmoMechanicsMFR.handlers.get(a).onFireArrow(world, arrow, bow, user, charge, creative)) {
                    if (!user.capabilities.isCreativeMode) {
                        bow.damageItem(1, user);
                    }
                    world.playSound(user, user.getPosition(), SoundsMFR.BOW_FIRE, SoundCategory.NEUTRAL, 0.5F, 1.0F / (world.rand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);
                    loadArrow(user, bow, null);
                    event.setCanceled(true);
                    AmmoMechanicsMFR.consumeAmmo(user, bow);
                    break;
                }
            }
        }
    }

    private boolean getIsInfinite(EntityPlayer user, ItemStack bow) {
        return user.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(51), bow) > 0;
    }

    // Used to take an item/subId from the inventory
    private boolean consumePlayerItem(EntityPlayer player, ItemStack item) {
        for (int a = 0; a < player.inventory.getSizeInventory(); a++) {
            ItemStack i = player.inventory.getStackInSlot(a);
            if (i != null && i.isItemEqual(item)) {
                player.inventory.decrStackSize(a, 1);
                return true;
            }
        }
        return false;
    }

}
