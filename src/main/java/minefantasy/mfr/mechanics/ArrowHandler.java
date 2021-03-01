package minefantasy.mfr.mechanics;

import minefantasy.mfr.config.ConfigStamina;
import minefantasy.mfr.init.MineFantasySounds;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ArrowHandler {
	/**
	 * This method adds arrows to the bow
	 */
	public static void loadArrow(EntityPlayer player, ItemStack bow, ItemStack arrow) {
		if (player.world.isRemote) {
			return;
		}
		NBTTagCompound nbt = getOrApplyNBT(bow);

		if (arrow.isEmpty()) {
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

	/**
	 * When the arrow is pulled back it initiates
	 */
	@SubscribeEvent
	public void readyBow(ArrowNockEvent event) {
		EntityPlayer user = event.getEntityPlayer();
		ItemStack bow = event.getBow();

		/*
		 * Checks over registered arrows and finds one to load The Quiver can be used to
		 * determine this
		 */
		ItemStack arrowToFire = AmmoMechanics.reloadBow(bow);
		if (!arrowToFire.isEmpty()) {
			loadArrow(user, bow, arrowToFire);// adds the arrow to NBT for rendering and later use
		}
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
		if (!AmmoMechanics.getArrowOnBow(bow).isEmpty()) {
			// if an arrow is on the bow, it uses that
			arrow = AmmoMechanics.getArrowOnBow(bow);
		}
		if (AmmoMechanics.handlers != null && AmmoMechanics.handlers.size() > 0) {
			for (int a = 0; a < AmmoMechanics.handlers.size(); a++) {
				// If the Arrow handler succeeds at firing an arrow
				if (AmmoMechanics.handlers.get(a).onFireArrow(world, arrow, bow, user, charge, creative)) {
					if (!user.capabilities.isCreativeMode) {
						bow.damageItem(1, user);
					}
					world.playSound(user, user.getPosition(), MineFantasySounds.BOW_FIRE, SoundCategory.NEUTRAL, 0.5F, 1.0F / (world.rand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);
					loadArrow(user, bow, ItemStack.EMPTY);
					AmmoMechanics.consumeAmmo(user, bow);
					break;
				}
			}
		}
	}

	private boolean getIsInfinite(EntityPlayer user, ItemStack bow) {
		return user.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow) > 0;
	}

	// Used to take an item/subId from the inventory
	private boolean consumePlayerItem(EntityPlayer player, ItemStack item) {
		for (int a = 0; a < player.inventory.getSizeInventory(); a++) {
			ItemStack stackInSlot = player.inventory.getStackInSlot(a);
			if (!stackInSlot.isEmpty() && stackInSlot.isItemEqual(item)) {
				player.inventory.decrStackSize(a, 1);
				return true;
			}
		}
		return false;
	}

}
