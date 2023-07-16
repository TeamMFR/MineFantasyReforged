package minefantasy.mfr.mechanics;

import minefantasy.mfr.api.stamina.IStaminaWeapon;
import minefantasy.mfr.config.ConfigClient;
import minefantasy.mfr.config.ConfigStamina;
import minefantasy.mfr.data.PlayerData;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.network.StaminaPacket;
import minefantasy.mfr.util.ArmourCalculator;
import minefantasy.mfr.util.TacticalManager;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;

public class StaminaMechanics {
	private static boolean hurtAdreneline = true;

	public static void tickEntity(EntityPlayer player) {
		PlayerData data = PlayerData.get(player);
		if (player.ticksExisted % 20 == 0) {
			ArmourCalculator.setTotalWeightOfWorn(player, true);
			ArmourCalculator.setTotalWeightOfWorn(player, false);
		}

		float[] packet = new float[] {StaminaBar.getStaminaValue(player), StaminaBar.getBaseMaxStamina(player),
				StaminaBar.getFlashTime(player), StaminaBar.getBonusStamina(player)};
		float[] values = new float[] {StaminaBar.getStaminaValue(player), StaminaBar.getTotalMaxStamina(player)};
		if (player.world.isRemote && ConfigClient.playBreath) {
			if (!StaminaBar.isPercentStamAvailable(player, 0.2F, false) && !player.isInsideOfMaterial(Material.WATER)) {
				if (player.ticksExisted % 20 == 0) {
					player.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, 0.1F, 1.8F);
				}
				if (player.ticksExisted + 5 % 20 == 0) {
					player.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, 0.15F, 1.5F);
				}
			}
			return;
		}
		float decay = getConstantDecay(player);
		// Decay
		if (decay > 0) {
			if (values[0] > 0) {
				StaminaBar.modifyStaminaValue(player, -decay);
			}
			StaminaBar.setIdleTime(data, 60F * getIdleRate(player));
		}

		// Regen
		if (decay <= 0 && StaminaBar.getIdleTime(player) <= 0) {
			float regen = getRegenRate(player);
			if (values[0] < values[1]) {
				StaminaBar.modifyStaminaValue(player, regen);
			}
		}
		// Bonus Regen
		if (decay <= 0) {
			float regen = StaminaBar.getRegenBonus(player);
			if (values[0] < values[1]) {
				StaminaBar.modifyStaminaValue(player, regen);
			}
		}

		// float[] stam = new float[]{StaminaBar.getStaminaValue(player),
		// StaminaBar.getMaxStamina(player)};

		// out of stamina
		if (values[0] <= 0) {
			if (ConfigStamina.affectSpeed) {
				player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 40));
			}
			player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 40));
			// Drain air when exhausted, you will drown faster
			if (isInWater(player) && player.getAir() > -20 && player.ticksExisted % 20 == 0) {
				player.setAir(player.getAir() - 1);
			}
			if (TacticalManager.shouldStaminaBlock) {
				if (player.isHandActive() && (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItemUseAction() == EnumAction.valueOf("mfr_block"))) {
					player.stopActiveHand();
				}
			}
		}
		// This phase decreases the cooldown
		if (StaminaBar.getIdleTime(player) > 0) {
			StaminaBar.ModifyIdleTime(player, -1);
		}
		if (StaminaBar.getFlashTime(player) > 0) {
			StaminaBar.ModifyFlashTime(data, player, -1);
		}

		// REFRESH
		StaminaBar.refreshSystem(player);

		StaminaBar.tickBonus(player);
		StaminaBar.tickBonusRegen(player);

		// if changed, resync
		// if((StaminaBar.getStaminaValue(player) != stam[0] ||
		// StaminaBar.getMaxStamina(player) != stam[1]) || player.ticksExisted % 20 ==
		// 0)
		syncStamina(player, packet);
	}

	private static void syncStamina(EntityPlayer player, float[] stam) {
		if (!player.world.isRemote) {
			NetworkHandler.sendToPlayer((EntityPlayerMP) player, new StaminaPacket(stam, player));
		}
	}

	public static float getConstantDecay(EntityPlayer player) {
		if (player.capabilities.isCreativeMode) {
			return 0F;
		}
		float value = 0.0F;
		boolean countArmour = false;

		if (player.isSprinting() && ConfigStamina.sprintModifier > 0 && player.getRidingEntity() == null) {
			float sprintingSeconds = 60F;
			countArmour = true;// Armour gets factored in with sprinting
			value += value += (5F / sprintingSeconds) * ConfigStamina.sprintModifier;
		}
		if (!player.getHeldItem(EnumHand.MAIN_HAND).isEmpty() && player.isHandActive() && ConfigStamina.bowModifier > 0) {
			if (player.getHeldItem(EnumHand.MAIN_HAND).getItemUseAction() == EnumAction.BOW) {
				// Item gets factored in with bow
				int bowSeconds = 30;
				value += StaminaBar.getDefaultMax() / 20F / bowSeconds * ConfigStamina.bowModifier;
			}
		}
		return value * StaminaBar.getBaseDecayModifier(player, countArmour, true);
	}

	public static float getRegenRate(EntityPlayer player) {
		if (player.capabilities.isCreativeMode) {
			return 100F;
		}
		if (StaminaBar.getIdleTime(player) > 0) {
			return 0;
		}
		if (isInWater(player) && player.getActivePotionEffect(MobEffects.WATER_BREATHING) == null) {
			return 0;// can't catch your breath when underwater
		}
		// The base time it takes to regen
		float value = StaminaBar.getTotalMaxStamina(player) / 20 / ConfigStamina.fullRegenSeconds;
		if (hurtAdreneline && player.getHealth() <= 2F) {
			value *= 1.5F;
		}
		return value * StaminaBar.getBaseRegenModifier(player, true, true);
	}

	private static boolean isInWater(EntityLivingBase user) {
		return user.isInsideOfMaterial(Material.WATER);
	}

	public static float getIdleRate(EntityLivingBase user) {
		float value = 1.0F;

		if (hurtAdreneline && user.getHealth() <= 4F) {
			value *= 0.2F;
		}

		return value * StaminaBar.getBaseIdleModifier(user, true, true);
	}

	public static void onJump(EntityPlayer player) {
		if (player.world.isRemote) {
			return;
		}
		float stam = StaminaBar.getStaminaValue(player);
		if (stam > 0) {
			StaminaBar.modifyStaminaValue(player, -getJumpDecay(player));
		}
		StaminaBar.setIdleTime(PlayerData.get(player), 40F * getIdleRate(player));
		// syncStamina(player, new float[]{stam, StaminaBar.getMaxStamina(player)});
	}

	public static float getJumpDecay(EntityPlayer user) {
		float value = 2.0F;

		if (user.isSprinting()) {
			value = 3.0F;
		}
		return value * StaminaBar.getBaseDecayModifier(user, true, false);
	}

	public static void onAttack(EntityPlayer user, Entity target) {
		applyHitFatigue(user, 1.0F * TacticalManager.getHighgroundModifier(target, user, 1.5F));
	}

	public static void applyHitFatigue(EntityPlayer player, float modifier) {
		float stam = StaminaBar.getStaminaValue(player);

		if (stam > 0) {
			StaminaBar.modifyStaminaValue(player, -getWeaponModifier(player) * modifier);
		}
		StaminaBar.setIdleTime(PlayerData.get(player), 50F * getIdleRate(player));
		// syncStamina(player, new float[]{stam, StaminaBar.getMaxStamina(player)});
	}

	public static float getWeaponModifier(EntityPlayer user) {
		ItemStack weapon = user.getHeldItemMainhand();
		float value = 0.0F;

		if (!weapon.isEmpty()) {
			value = 2F;

			if (weapon.getItem() instanceof IStaminaWeapon) {
				value = ((IStaminaWeapon) weapon.getItem()).getStaminaDrainOnHit(user, weapon);
			} else {
				if (weapon.getItem() instanceof ItemSword) {
					value = 5;
				}
				if (weapon.getItem() instanceof ItemTool) {
					value = 10;
				}
			}
		}

		return value * ConfigStamina.weaponModifier * StaminaBar.getBaseDecayModifier(user, true, true);
	}

	public static void tirePlayer(EntityPlayer player, float points) {
		if (ConfigStamina.isSystemActive) {
			StaminaBar.modifyStaminaValue(player, -StaminaBar.getBaseDecayModifier(player, true, true) * points);
			StaminaBar.ModifyIdleTime(player, 5F * points);
		}
	}

	public static boolean canAcceptCost(EntityLivingBase user) {
		return canAcceptCost(user, 0.1F);
	}

	public static boolean canAcceptCost(EntityLivingBase user, float cost) {
		if (user instanceof EntityPlayer && ConfigStamina.isSystemActive) {
			return StaminaBar.isPercentStamAvailable(user, cost, true);
		}
		return true;
	}
}
