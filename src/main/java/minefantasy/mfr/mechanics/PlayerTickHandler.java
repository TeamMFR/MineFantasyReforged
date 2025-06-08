package minefantasy.mfr.mechanics;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.stamina.CustomFoodEntry;
import minefantasy.mfr.config.ConfigClient;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.config.ConfigMobs;
import minefantasy.mfr.config.ConfigSpecials;
import minefantasy.mfr.config.ConfigStamina;
import minefantasy.mfr.config.ConfigWeapon;
import minefantasy.mfr.data.IStoredVariable;
import minefantasy.mfr.data.Persistence;
import minefantasy.mfr.data.PlayerData;
import minefantasy.mfr.entity.mob.EntityDragon;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.item.ItemApron;
import minefantasy.mfr.item.ItemFoodMFR;
import minefantasy.mfr.item.ItemSyringe;
import minefantasy.mfr.item.ItemWeaponMFR;
import minefantasy.mfr.util.ArmourCalculator;
import minefantasy.mfr.util.MFRLogUtil;
import minefantasy.mfr.util.PlayerUtils;
import minefantasy.mfr.util.TacticalManager;
import minefantasy.mfr.util.XSTRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber
public class PlayerTickHandler {
	public static final IStoredVariable<Boolean> HAS_BOOK_KEY = IStoredVariable.StoredVariable.ofBoolean("hasBook", Persistence.ALWAYS);
	public static final IStoredVariable<Float> BALANCE_YAW_KEY = IStoredVariable.StoredVariable.ofFloat("balanceYaw", Persistence.DIMENSION_CHANGE);
	public static final IStoredVariable<Float> BALANCE_PITCH_KEY = IStoredVariable.StoredVariable.ofFloat("balancePitch", Persistence.DIMENSION_CHANGE);
	public static final IStoredVariable<Integer> DRAGON_KILLS_KEY = IStoredVariable.StoredVariable.ofInt("dragonKills", Persistence.ALWAYS);
	public static final IStoredVariable<BlockPos> LAST_POS = IStoredVariable.StoredVariable.ofBlockPos("lastPos", Persistence.NEVER);

	private static final String chunkCoords = "MF_BedPos";
	private static final String resetBed = "MF_Resetbed";

	private static final XSTRandom random = new XSTRandom();

	static {
		PlayerData.registerStoredVariables(HAS_BOOK_KEY, BALANCE_YAW_KEY, BALANCE_PITCH_KEY, DRAGON_KILLS_KEY, LAST_POS);
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
			TextComponentTranslation dragonNearText = new TextComponentTranslation("event.dragonnear.name");
			dragonNearText.getStyle().setColor(TextFormatting.GOLD);

			if (ConfigMobs.dragonMSG && !player.world.isRemote) {
				player.sendMessage(dragonNearText);

				List<?> list = player.world.playerEntities;
				for (Object instance : list) {
					if (instance instanceof EntityPlayer) {
						if (((EntityPlayer) instance).getDistance(player) < 256D && instance != player) {
							((EntityPlayer) instance).sendMessage(dragonNearText);
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
		if (data != null) {
			if (data.getVariable(DRAGON_KILLS_KEY) == null) {
				data.setVariable(DRAGON_KILLS_KEY, 0);
			}
			return data.getVariable(DRAGON_KILLS_KEY);
		}
		return 0;
	}

	public static void wakeUp(EntityPlayer player) {
		if (ConfigStamina.isSystemActive) {
			StaminaBar.setStaminaValue(PlayerData.get(player), StaminaBar.getBaseMaxStamina(player));
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

			if ((!event.player.isRiding() || event.player.getRidingEntity() instanceof EntityLivingBase) && hasPosChanged(event.player)) {
				onStep(event.player);
			}
			if (event.player.isPotionActive(MobEffects.HUNGER)){
				ItemFoodMFR.decrementFatAccumulation(event.player, 0.05F);
			}

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
			EntityPlayer player = event.player;
			applyBalance(player);
			ItemFoodMFR.onTick(player);

			//Burn player for having hot items on them
			int tickHotItemCheck = player.isWet() ? 20 : 100;
			if (!player.world.isRemote
					&& !(!ConfigHardcore.HCChotBurn && ItemApron.isUserProtected(player))
					&& player.ticksExisted % tickHotItemCheck == 0) {
				if (!player.isCreative() && player.inventory.hasItemStack(new ItemStack(MineFantasyItems.HOT_ITEM))) {
					if (player.getLastDamageSource() != DamageSource.ON_FIRE) {
						if (!player.world.isRemote) {
							player.sendMessage(new TextComponentTranslation("info.noHeatProtection.message"));
						}
					}
					player.setFire(5);
					player.attackEntityFrom(DamageSource.ON_FIRE, player.isWet() ? 3 : 1);
				}
			}

			ArmourCalculator.updateWeights(player);
		}
	}

	@SubscribeEvent
	public void onPlayerHitEntity(final AttackEntityEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.getItem() instanceof ItemSyringe) {
			if (player.getCooledAttackStrength(0) < 0.9) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onRightClickItem(final PlayerInteractEvent.RightClickItem evt) {

		EntityPlayer player = evt.getEntityPlayer();
		if (PlayerUtils.shouldItemStackBlock(evt.getItemStack(), player.getHeldItemOffhand())) {

			EnumAction action = player.getHeldItemOffhand().getItemUseAction();
			if (action == EnumAction.BLOCK) {
				player.setActiveHand(EnumHand.OFF_HAND);
				// Don't cause reequip animation, AND don't swing hand
				evt.setCancellationResult(EnumActionResult.FAIL);
				evt.setCanceled(true);
			}
			if (action == EnumAction.NONE || action == EnumAction.EAT && !player.canEat(false)) {

				player.setActiveHand(evt.getHand());
				// Don't cause reequip animation, AND don't swing hand
				evt.setCancellationResult(EnumActionResult.FAIL);
				evt.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onItemUseStart(final LivingEntityUseItemEvent.Start evt) {

		if (evt.getEntityLiving() instanceof EntityPlayer && PlayerUtils.shouldItemStackBlock(evt.getItem(), evt.getEntityLiving().getHeldItemOffhand())) {

			evt.setDuration(evt.getItem().getMaxItemUseDuration());
		}
	}


	@SubscribeEvent
	public static void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
		ItemStack stack = event.getItem();
		if (stack.getItem() instanceof ItemFood) {
			CustomFoodEntry foodEntry = CustomFoodEntry.getEntry(stack);
			if (foodEntry != null && event.getEntity() instanceof EntityPlayer) {
				ItemFoodMFR.onCustomFoodEaten((EntityPlayer) event.getEntity(),
						foodEntry.staminaRestore,
						foodEntry.staminaSeconds, foodEntry.staminaBuff,
						foodEntry.staminaRegenSeconds, foodEntry.staminaRegenBuff,
						foodEntry.eatDelay, foodEntry.fatAccumulation);
			}
			//Handle non MFR Food repeated food reset
			if (!(stack.getItem() instanceof ItemFoodMFR) && event.getEntityLiving() instanceof EntityPlayer) {
				PlayerData data = PlayerData.get((EntityPlayer) event.getEntityLiving());
				if (data != null && data.getVariable(ItemFoodMFR.REPEATED_FOOD_NBT) != null) {
					NBTTagCompound nbt = data.getVariable(ItemFoodMFR.REPEATED_FOOD_NBT);
					if (nbt != null && nbt.getInteger("repeat_count") != 0) {
						nbt.setInteger("repeat_count", 0);
						event.getItem().writeToNBT(nbt);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void jump(LivingEvent.LivingJumpEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			ItemFoodMFR.decrementFatAccumulation((EntityPlayer) event.getEntity(), 0.5F);
		}
	}

	private void onStep(EntityPlayer player) {
		if (!player.world.isRemote) {
			PlayerData data = PlayerData.get(player);
			if (data != null) {
				float fat_accumulation = ItemFoodMFR.getFatAccumulation(player);
				float constant_max_stamina = StaminaBar.getDefaultMax() + StaminaBar.getBonusStamina(player);
				if (fat_accumulation > ConfigStamina.fatThreshold || constant_max_stamina != StaminaBar.getTotalMaxStamina(player)) {
					StaminaBar.setMaxStamina(data, StaminaBar.getDefaultMax() - (fat_accumulation / 10));
				}
			}
			if (player.isRiding()) {
				ItemFoodMFR.decrementFatAccumulation(player, 0.1F);
			}
			else if (player.isSprinting()) {
				ItemFoodMFR.decrementFatAccumulation(player, 0.5F);
			}
			else {
				ItemFoodMFR.decrementFatAccumulation(player, 0.25F);
			}
		}
		else {
			MineFantasyReforged.LOG.debug("Weight: " + ArmourCalculator.getTotalWeightOfWorn(player, false));
			if (ConfigClient.playArmorSound && ArmourCalculator.getTotalWeightOfWorn(player, false) >= 50) {
				player.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
			}
		}
	}

	public static boolean hasPosChanged(EntityPlayer player) {
		PlayerData data = PlayerData.get(player);
		BlockPos pos = data.getVariable(LAST_POS);
		data.setVariable(LAST_POS, player.getPosition());
		if (pos == null) {
			return true;
		} else {
			return !pos.equals(player.getPosition());
		}
	}

	private void tickDragonSpawner(EntityPlayer player) {
		if (player.world.getDifficulty() != EnumDifficulty.PEACEFUL && Arrays.stream(ConfigMobs.dragonDimensionID).anyMatch(dim -> dim == player.dimension)) {
			int i = ConfigMobs.dragonInterval;
			float chance = ConfigMobs.dragonChance;

			if (PlayerTickHandler.getDragonEnemyPoints(player) >= 100) {
				i /= 2;// twice as frequent
			}
			if (PlayerTickHandler.getDragonEnemyPoints(player) >= 50) {
				chance *= 2;// twice the chance
			}
			if (!player.world.isRemote && player.world.getTotalWorldTime() % i == 0 && random.nextFloat() * 100F < chance) {
				spawnDragon(player);
			}
		}
	}

	private void applyBalance(EntityPlayer player) {
		PlayerData data = PlayerData.get(player);
		float weight = 2.0F;

		float pitchBalance = 0;
		if (data.getVariable(BALANCE_PITCH_KEY) == null) {
			data.setVariable(BALANCE_PITCH_KEY, 0F);
		} else {
			pitchBalance = data.getVariable(BALANCE_PITCH_KEY);
		}

		float yawBalance = 0;
		if (data.getVariable(BALANCE_YAW_KEY) == null) {
			data.setVariable(BALANCE_YAW_KEY, 0F);
		} else {
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
				player.rotationYaw -= yawBalance > 0 ? weight : -weight;
			}

			if (yawBalance < 0)
				yawBalance = 0;
		}
		data.setVariable(BALANCE_PITCH_KEY, pitchBalance);
		data.setVariable(BALANCE_YAW_KEY, yawBalance);
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			onPlayerEnterWorld((EntityPlayer) event.getEntity());
		}
	}

	public static void onPlayerEnterWorld(EntityPlayer player) {
		if (player.world.isRemote) {
			return;
		}

		PlayerData data = PlayerData.get(player);
		if (data != null) {
			if (data.getVariable(HAS_BOOK_KEY) == null) {
				data.setVariable(HAS_BOOK_KEY, false);
			}
			if (!data.getVariable(HAS_BOOK_KEY)) {
				data.setVariable(HAS_BOOK_KEY, true);
				if (player.capabilities.isCreativeMode || !ConfigSpecials.shouldResearchBookSpawn)
					return;
				player.inventory.addItemStackToInventory(new ItemStack(MineFantasyItems.RESEARCH_BOOK));
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
