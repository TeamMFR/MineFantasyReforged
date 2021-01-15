package minefantasy.mfr.mechanics;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.heating.IHotItem;
import minefantasy.mfr.api.helpers.ArmourCalculator;
import minefantasy.mfr.api.helpers.TacticalManager;
import minefantasy.mfr.api.stamina.StaminaBar;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.config.ConfigMobs;
import minefantasy.mfr.config.ConfigWeapon;
import minefantasy.mfr.data.IStoredVariable;
import minefantasy.mfr.data.Persistence;
import minefantasy.mfr.data.PlayerData;
import minefantasy.mfr.entity.mob.EntityDragon;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.item.ItemApron;
import minefantasy.mfr.item.ItemCrossbow;
import minefantasy.mfr.item.ItemFoodMF;
import minefantasy.mfr.item.ItemWeaponMFR;
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
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class PlayerTickHandlerMF {
	public static final IStoredVariable<Boolean> HAS_BOOK_KEY = IStoredVariable.StoredVariable.ofBoolean("hasBook", Persistence.ALWAYS);
	public static final IStoredVariable<Float> BALANCE_YAW_KEY = IStoredVariable.StoredVariable.ofFloat("balanceYaw", Persistence.DIMENSION_CHANGE);
	public static final IStoredVariable<Float> BALANCE_PITCH_KEY = IStoredVariable.StoredVariable.ofFloat("balancePitch", Persistence.DIMENSION_CHANGE);
	public static final IStoredVariable<Integer> LAST_STEP_KEY = IStoredVariable.StoredVariable.ofInt("lastStep", Persistence.DIMENSION_CHANGE);
	public static final IStoredVariable<Integer> DRAGON_KILLS_KEY = IStoredVariable.StoredVariable.ofInt("dragonKills", Persistence.ALWAYS);

	private static String chunkCoords = "MF_BedPos";
	private static String resetBed = "MF_Resetbed";

	private static XSTRandom random = new XSTRandom();

	static {
		PlayerData.registerStoredVariables(HAS_BOOK_KEY, BALANCE_YAW_KEY, BALANCE_PITCH_KEY, LAST_STEP_KEY, DRAGON_KILLS_KEY);
	}

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
		PlayerData.get(player).setVariable(DRAGON_KILLS_KEY, i);
	}

	public static int getDragonEnemyPoints(EntityPlayer player) {
		PlayerData data = PlayerData.get(player);
		if (data != null){
			if (data.getVariable(DRAGON_KILLS_KEY) == null){
				data.setVariable(DRAGON_KILLS_KEY, 0);
			}
			return data.getVariable(DRAGON_KILLS_KEY);
		}
		return 0;
	}

	public static void wakeUp(EntityPlayer player) {
		if (StaminaBar.isSystemActive) {
			StaminaBar.setStaminaValue(PlayerData.get(player), StaminaBar.getBaseMaxStamina(player));
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
					onStep(event.player);
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

	private void onStep(EntityPlayer player) {
		MineFantasyReborn.LOG.debug("Weight: " + ArmourCalculator.getTotalWeightOfWorn(player, false));
		if (ArmourCalculator.getTotalWeightOfWorn(player, false) >= 50) {
			player.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
		}
	}

	private boolean isNextStep(EntityPlayer player) {
		PlayerData data = PlayerData.get(player);
		if (data != null ) {
			if (data.getVariable(LAST_STEP_KEY) == null) {
				data.setVariable(LAST_STEP_KEY, 0);
			}else {
				int prevStep = data.getVariable(LAST_STEP_KEY);
				int stepcount = (int) player.distanceWalkedOnStepModified;
				data.setVariable(LAST_STEP_KEY, stepcount);
				return prevStep != stepcount;
			}
		}
		return false;
	}

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

	private void applyBalance(EntityPlayer player) {
		PlayerData data = PlayerData.get(player);
		float weight = 2.0F;

		float pitchBalance = 0;
		if(data.getVariable(BALANCE_PITCH_KEY) == null){
			data.setVariable(BALANCE_PITCH_KEY, 0F);
		} else{
			pitchBalance = data.getVariable(BALANCE_PITCH_KEY);
		}

		float yawBalance = 0;
		if(data.getVariable(BALANCE_YAW_KEY) == null){
			data.setVariable(BALANCE_YAW_KEY, 0F);
		} else{
			yawBalance = data.getVariable(BALANCE_YAW_KEY);
		}

		if (pitchBalance > 0) {
			if (pitchBalance < 1.0F && pitchBalance > -1.0F)
				weight = pitchBalance;
			pitchBalance -= weight;

			if (ConfigWeapon.useBalance) {
				player.rotationPitch += pitchBalance > 0 ? weight : -weight;
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
				player.rotationYaw += yawBalance > 0 ? weight : -weight;
			}

			if (yawBalance < 0)
				yawBalance = 0;
		}
		data.setVariable(BALANCE_PITCH_KEY, pitchBalance);
		data.setVariable(BALANCE_YAW_KEY, yawBalance);
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event){
		if (event.getEntity() instanceof EntityPlayer){
			onPlayerEnterWorld((EntityPlayer) event.getEntity());
		}
	}

	public static void onPlayerEnterWorld(EntityPlayer player) {
		if (player.world.isRemote){
			return;
		}

		PlayerData data = PlayerData.get(player);
		if (data != null){
			if (data.getVariable(HAS_BOOK_KEY) == null){
				data.setVariable(HAS_BOOK_KEY, false);
			}
			if (!data.getVariable(HAS_BOOK_KEY)) {
				data.setVariable(HAS_BOOK_KEY, true);
				if (player.capabilities.isCreativeMode)
					return;
				player.inventory.addItemStackToInventory(new ItemStack(ToolListMFR.RESEARCH_BOOK));
			}
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
