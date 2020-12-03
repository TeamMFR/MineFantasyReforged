package minefantasy.mfr.mechanics;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.heating.IHotItem;
import minefantasy.mfr.api.helpers.ArmourCalculator;
import minefantasy.mfr.api.helpers.PlayerTagData;
import minefantasy.mfr.api.helpers.TacticalManager;
import minefantasy.mfr.api.knowledge.ResearchLogic;
import minefantasy.mfr.api.rpg.RPGElements;
import minefantasy.mfr.api.stamina.StaminaBar;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.config.ConfigMobs;
import minefantasy.mfr.config.ConfigWeapon;
import minefantasy.mfr.entity.mob.EntityDragon;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.item.armour.ItemApron;
import minefantasy.mfr.item.food.ItemFoodMF;
import minefantasy.mfr.item.gadget.ItemCrossbow;
import minefantasy.mfr.item.weapon.ItemWeaponMFR;
import minefantasy.mfr.util.MFRLogUtil;
import minefantasy.mfr.util.XSTRandom;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class PlayerTickHandlerMF {
	private static String stepNBT = "MF_LastStep";
	private static String chunkCoords = "MF_BedPos";
	private static String resetBed = "MF_Resetbed";

	private static XSTRandom random = new XSTRandom();

	public static void spawnDragon(EntityPlayer player) {
		spawnDragon(player, 64);
	}

	public static void spawnDragon(EntityPlayer player, int offset) {
		int y = (int) (player.posY + offset);
		boolean canMobSpawn = !player.world.isRemote && canDragonSpawnOnPlayer(player, y);
		if (canMobSpawn && !player.world.isRemote) {
			int tier = getDragonTier(player);// Gets tier on kills
			EntityDragon dragon = new EntityDragon(player.world);
			dragon.setPosition(player.posX, y, player.posZ);
			player.world.spawnEntity(dragon);
			dragon.setDragon(tier);
			dragon.disengage(100);
			player.world.playSound(dragon.posX, dragon.posY - 16D, dragon.posZ, SoundEvents.ENTITY_ENDERDRAGON_GROWL, SoundCategory.AMBIENT, 3.0F, 1.5F, true);

			if (ConfigMobs.dragonMSG && !player.world.isRemote) {
				player.sendMessage(new TextComponentString(TextFormatting.GOLD + I18n.format("event.dragonnear.name")));

				List<?> list = player.world.playerEntities;
				for (Object instance : list) {
					if (instance instanceof EntityPlayer) {
						if (((EntityPlayer) instance).getDistance(player) < 256D && instance != player) {
							((EntityPlayer) instance).sendMessage(new TextComponentString(TextFormatting.GOLD + I18n.format("event.dragonnear.name")));
						}
					}
				}
			}
		}
	}

	private static boolean canDragonSpawnOnPlayer(EntityPlayer player, int y) {
		for (int x = -3; x <= 3; x++) {
			for (int z = -3; z <= 3; z++) {
				if (!player.world.canBlockSeeSky(player.getPosition().add(x, 0, z))) {
					return false;
				}
			}
		}
		return true;
	}

	public static int getDragonTier(EntityPlayer player) {
		int kills = getDragonEnemyPoints(player);
		if (kills < 5) {
			return 0;// Young 100%
		}
		if (kills < 10) {
			if (random.nextInt(5) == 0)
				return 0;// 20% chance for Young
			return 1;// Adult
		}
		if (kills < 15) {
			if (random.nextInt(10) == 0)
				return 0;// 10% chance for Young
			return 1;// Adult
		}
		if (kills < 25) {
			if (random.nextInt(20) == 0)
				return 0;// 5% chance for Young
			if (random.nextInt(10) == 0)
				return 2;// 10% chance for Mature
			return 1;// Adult
		}
		if (kills < 35) {
			if (random.nextInt(4) == 0)
				return 2;// 25% chance for Mature
			return 1;// Adult
		}
		if (kills >= 50) {
			if (random.nextInt(10) == 0)
				return 1;// 10% chance for Adult
			if (random.nextInt(5) == 0)
				return 3;// 20% chance Elder
			return 2;// Mature
		}
		if (kills > 75) {
			if (random.nextInt(100) == 0)
				return 4;// 1% chance Ancient
			if (random.nextInt(2) == 0)
				return 3;// 50% chance Elder
			return 2;// Mature
		}

		return 0;// Young 100%
	}

	public static void addDragonKill(EntityPlayer player) {
		addDragonEnemyPts(player, 1);
	}

	public static void addDragonEnemyPts(EntityPlayer player, int i) {
		setDragonEnemyPts(player, getDragonEnemyPoints(player) + i);
	}

	public static void setDragonEnemyPts(EntityPlayer player, int i) {
		if (i < 0) {
			i = 0;
		}
		NBTTagCompound nbt = PlayerTagData.getPersistedData(player);
		nbt.setInteger("MF_DragonKills", i);
	}

	public static int getDragonEnemyPoints(EntityPlayer player) {
		NBTTagCompound nbt = PlayerTagData.getPersistedData(player);
		return nbt.getInteger("MF_DragonKills");
	}

	public static void wakeUp(EntityPlayer player) {
		if (StaminaBar.isSystemActive) {
			StaminaBar.setStaminaValue(player, StaminaBar.getBaseMaxStamina(player));
		}
		if (player.getEntityData().hasKey(chunkCoords + "_x")) {
			player.getEntityData().setBoolean(resetBed, true);
		}
	}

	public static void readyToResetBedPosition(EntityPlayer player) {
		BlockPos coords = player.getBedLocation(player.dimension);
		if (coords != null) {
			player.getEntityData().setInteger(chunkCoords + "_x", coords.getX());
			player.getEntityData().setInteger(chunkCoords + "_y", coords.getY());
			player.getEntityData().setInteger(chunkCoords + "_z", coords.getZ());
		}
	}

	// SPRINT JUMPING
	// DEFAULT:= 0:22 (50seconds till starve, 35s till nosprint) (16m in MC time for
	// 4 missing bars)
	// SLOW=5: = 2:20 (5mins till starve, 3:30 till nosprint) (1h 40m in MC time for
	// 4 missing bars)
	// EXHAUSTION SCALE = 3.0F = 1hunger
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			ItemStack held = event.player.getHeldItemMainhand();
			if (!held.isEmpty()) {
				int parry = ItemWeaponMFR.getParry(held);
				if (parry > 0) {
					ItemWeaponMFR.setParry(held, parry - 1);
				}
			}

			// COMMON
			TacticalManager.applyArmourWeight(event.player);

			if (event.player.world.isRemote) {
				if (isNextStep(event.player)) {
					onStep(event.player, event.player.getEntityData().getInteger(stepNBT) % 2 == 0);
				}
			}
			/*
			 * if(RPGElements.isSystemActive) { if(event.player.isSprinting() &&
			 * event.player.ticksExisted % 10 == 0) {
			 * SkillList.athletics.addXP(event.player, 1); } else
			 * if(event.player.isSneaking() && TacticalManager.isEntityMoving(event.player)
			 * && event.player.ticksExisted % 10 == 0) { SkillList.sneak.addXP(event.player,
			 * 1); } }
			 */
			// DRAGON EVENT
			if (!event.player.world.isRemote) {
				tickDragonSpawner(event.player);
			}
			if (!event.player.world.isRemote) {
				tryResetBed(event.player);
			}
			// updatePitch(event.player); (This keeps track of player camera angles, could
			// make a mechanic based on swinging the camera)
		}

		if (event.phase == TickEvent.Phase.START) {
			applyBalance(event.player);
			ItemFoodMF.onTick(event.player);

			if (!event.player.world.isRemote
					&& !(!ConfigHardcore.HCChotBurn && ItemApron.isUserProtected(event.player))
					&& event.player.ticksExisted % 100 == 0) {
				for (int a = 0; a < event.player.inventory.getSizeInventory(); a++) {
					ItemStack item = event.player.inventory.getStackInSlot(a);
					if (!item.isEmpty() && item.getItem() instanceof IHotItem) {
						event.player.setFire(5);
						event.player.attackEntityFrom(DamageSource.ON_FIRE, 1.0F);
					}
				}
			}


			// TODO: move this to minefantasy.mfr.item.gadget.ItemCrossbow
			if (!event.player.getHeldItemMainhand().isEmpty()) {
				ItemStack stack = event.player.getHeldItemMainhand();
				if (stack.getItem() instanceof ItemCrossbow) {
					if (stack.hasTagCompound()) {
						NBTTagCompound compound = stack.getTagCompound();
						if (compound.hasKey(ItemCrossbow.useTypeNBT) && compound.getString(ItemCrossbow.useTypeNBT).equalsIgnoreCase("fire")) {
							compound.setString(ItemCrossbow.useTypeNBT, "null");
						}
					}
				}

			}

			ArmourCalculator.updateWeights(event.player);
			float weight = ArmourCalculator.getTotalWeightOfWorn(event.player, false);
			if (weight > 100F) {
				if (event.player.isInWater()) {
					event.player.motionY -= (weight / 20000F);
				}
			}
		}
	}

	private void onStep(EntityPlayer player, boolean alternateStep) {
		MineFantasyReborn.LOG.debug("Weight: " + ArmourCalculator.getTotalWeightOfWorn(player, false));
		if (ArmourCalculator.getTotalWeightOfWorn(player, false) >= 50) {
			player.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
		}
	}

	private boolean isNextStep(EntityPlayer player) {
		int prevStep = player.getEntityData().getInteger(stepNBT);
		int stepcount = (int) player.distanceWalkedOnStepModified;
		player.getEntityData().setInteger(stepNBT, stepcount);

		return prevStep != stepcount;
	}

	/*
	 * private static String lastPitchNBT = "MF_last_AimPitch"; public static void
	 * setLastPitch(EntityPlayer user, float value) {
	 * user.getEntityData().setFloat(lastPitchNBT, value); } public static void
	 * updatePitch(EntityPlayer user) { user.getEntityData().setFloat(lastPitchNBT,
	 * user.rotationPitch); } public static float getPitchMovement(EntityPlayer
	 * user) { float lastPitch = user.getEntityData().getFloat(lastPitchNBT) +
	 * 1000F; float nowPitch = user.rotationPitch + 1000F;
	 *
	 * return nowPitch - lastPitch; }
	 */
	private void tickDragonSpawner(EntityPlayer player) {
		if (player.world.getDifficulty() != EnumDifficulty.PEACEFUL && player.dimension == 0) {
			int i = ConfigMobs.dragonInterval;
			float chance = ConfigMobs.dragonChance;

			if (PlayerTickHandlerMF.getDragonEnemyPoints(player) >= 100) {
				i /= 2;// twice as frequent
			}
			if (PlayerTickHandlerMF.getDragonEnemyPoints(player) >= 50) {
				chance *= 2;// twice the chance
			}
			if (!player.world.isRemote && player.world.getTotalWorldTime() % i == 0
					&& random.nextFloat() * 100F < chance) {
				spawnDragon(player);
			}
		}
	}

	private void applyBalance(EntityPlayer entityPlayer) {
		float weight = 2.0F;
		float pitchBalance = entityPlayer.getEntityData().hasKey("MF_Balance_Pitch")
				? entityPlayer.getEntityData().getFloat("MF_Balance_Pitch")
				: 0F;
		float yawBalance = entityPlayer.getEntityData().hasKey("MF_Balance_Yaw")
				? entityPlayer.getEntityData().getFloat("MF_Balance_Yaw")
				: 0F;

		if (pitchBalance > 0) {
			if (pitchBalance < 1.0F && pitchBalance > -1.0F)
				weight = pitchBalance;
			pitchBalance -= weight;

			if (ConfigWeapon.useBalance) {
				entityPlayer.rotationPitch += pitchBalance > 0 ? weight : -weight;
			}

			if (pitchBalance < 0)
				pitchBalance = 0;
		}
		if (yawBalance > 0) {
			if (yawBalance < 1.0F && yawBalance > -1.0F)
				weight = yawBalance;
			yawBalance -= weight;

			if (ConfigWeapon.useBalance) {
				MFRLogUtil.logDebug("Weapon Balance Move");
				entityPlayer.rotationYaw += yawBalance > 0 ? weight : -weight;
			}

			if (yawBalance < 0)
				yawBalance = 0;
		}
		entityPlayer.getEntityData().setFloat("MF_Balance_Pitch", pitchBalance);
		entityPlayer.getEntityData().setFloat("MF_Balance_Yaw", yawBalance);
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		onPlayerEnterWorld(event.player);
	}

	@SubscribeEvent
	public void onPlayerTravel(PlayerEvent.PlayerChangedDimensionEvent event) {
		onPlayerEnterWorld(event.player);
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		onPlayerEnterWorld(event.player);
	}

	public void onPlayerEnterWorld(EntityPlayer player) {
		if (player.world.isRemote)
			return;

		NBTTagCompound persist = PlayerTagData.getPersistedData(player);
		MFRLogUtil.logDebug("Sync data");
		ResearchLogic.syncData(player);

		if (!persist.hasKey("MF_HasBook")) {
			persist.setBoolean("MF_HasBook", true);
			if (player.capabilities.isCreativeMode)
				return;

			player.inventory.addItemStackToInventory(new ItemStack(ToolListMFR.RESEARCH_BOOK));
		}
		if (RPGElements.isSystemActive) {
			RPGElements.initSkills(player);
		}
	}

	private void tryResetBed(EntityPlayer player) {
		if (player.getEntityData().hasKey(resetBed)) {
			player.getEntityData().removeTag(resetBed);
			resetBedPosition(player);
		}
	}

	private void resetBedPosition(EntityPlayer player) {
		if (player.getEntityData().hasKey(chunkCoords + "_x")) {
			MFRLogUtil.logDebug("Reset bed data for " + player.getCommandSenderEntity());
			int x = player.getEntityData().getInteger(chunkCoords + "_x");
			int y = player.getEntityData().getInteger(chunkCoords + "_y");
			int z = player.getEntityData().getInteger(chunkCoords + "_z");
			BlockPos coords = new BlockPos(x, y, z);

			player.getEntityData().removeTag(chunkCoords + "_x");
			player.getEntityData().removeTag(chunkCoords + "_y");
			player.getEntityData().removeTag(chunkCoords + "_z");

			player.setSpawnChunk(coords, false, 0);
		}
	}
}
