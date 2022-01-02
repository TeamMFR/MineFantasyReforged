package minefantasy.mfr.mechanics;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.armour.IElementalResistance;
import minefantasy.mfr.api.weapon.IDamageModifier;
import minefantasy.mfr.api.weapon.IKnockbackWeapon;
import minefantasy.mfr.api.weapon.IParryable;
import minefantasy.mfr.api.weapon.IPowerAttack;
import minefantasy.mfr.api.weapon.ISpecialCombatMob;
import minefantasy.mfr.api.weapon.ISpecialEffect;
import minefantasy.mfr.api.weapon.IWeightedWeapon;
import minefantasy.mfr.config.ConfigArmour;
import minefantasy.mfr.config.ConfigMobs;
import minefantasy.mfr.config.ConfigSpecials;
import minefantasy.mfr.config.ConfigStamina;
import minefantasy.mfr.config.ConfigWeapon;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.entity.Shockwave;
import minefantasy.mfr.entity.mob.EntityMinotaur;
import minefantasy.mfr.init.MineFantasyKnowledgeList;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.item.ItemBattleaxe;
import minefantasy.mfr.item.ItemDagger;
import minefantasy.mfr.item.ItemKatana;
import minefantasy.mfr.item.ItemWaraxe;
import minefantasy.mfr.item.ItemWeaponMFR;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.network.DodgeCommandPacket;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.network.ParryPacket;
import minefantasy.mfr.util.ArmourCalculator;
import minefantasy.mfr.util.ArrowEffectsMF;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.MFRLogUtil;
import minefantasy.mfr.util.PowerArmour;
import minefantasy.mfr.util.TacticalManager;
import minefantasy.mfr.util.XSTRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

@Mod.EventBusSubscriber
public class CombatMechanics {
	public static final String parryCooldownNBT = "MF_Parry_Cooldown";
	public static final String posthitCooldownNBT = "MF_PostHit";
	/**
	 * Damage done by silver to undead/witches
	 */
	public static final float specialUnholyModifier = 2.0F;
	/**
	 * Damage done by silver to werewolves
	 */
	public static final float specialWerewolfModifier = 8.0F;
	/**
	 * Damage done with dragonforged design to dragons
	 */
	public static final float specialDragonModifier = 1.5F;
	/**
	 * Damage done with ornate design to undead/witches
	 */
	public static final float specialOrnateModifier = 1.5F;
	private static final float power_attack_base = 25F;
	private static final float parryFatigue = 5F;
	public static boolean swordSkeleton = true;
	private static final boolean debugParry = true;
	private static final XSTRandom random = new XSTRandom();
	protected static float jumpEvade_cost = 30;
	protected static float evade_cost = 10;

	private CombatMechanics() {} // No instances!

	/**
	 * 0 = false 1 = true -1 = failure
	 */
	private static int initPowerAttack(EntityPlayer player, Entity target, boolean properHit) {
		if (!canExecutePower(player)) {
			return 0;
		}
		if (StaminaBar.isSystemActive && StaminaBar.doesAffectEntity(player)) {
			float points = power_attack_base * (StaminaBar.getBaseDecayModifier(player, true, true) * 0.5F + 0.5F);
			if (StaminaBar.isStaminaAvailable(player, points, properHit)) {
				if (properHit) {
					ItemWeaponMFR.applyFatigue(player, points);
				}
				return getPostHitCooldown(player) > 0 ? -1 : 1;
			} else {
				return 0;
			}
		}
		return 1;
	}

	private static boolean canExecutePower(EntityLivingBase user) {
		if (user.isInWater()) {
			return false;
		}
		if (user instanceof EntityPlayer) {
			if (!isFightStance(user))
				return false;
		}
		return user.fallDistance > 0 && !user.isOnLadder();
	}

	private static boolean isFightStance(EntityLivingBase user) {
		return user.isSneaking();
	}

	public static void applyUndeadBane(EntityLivingBase living) {
		living.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.5F, 0.5F);
		living.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1200, 2));
		living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1200, 2));
		if (random.nextInt(5) == 0) {
			living.setFire(3);
		}
	}

	protected static void performEffects(Map<PotionEffect, Float> map, EntityLivingBase entityHit) {
		double roll = Math.random();
		if (map == null || map.isEmpty()) {
			return;
		}

		for (PotionEffect effect : map.keySet()) {
			// add effects if they aren't already applied, with corresponding chance factor
			if (!entityHit.isPotionActive(effect.getPotion()) && map.get(effect) > roll) {
				entityHit.addPotionEffect(new PotionEffect(effect));
			}
		}
	}

	public static void setParryCooldown(EntityLivingBase user, int ticks) {
		user.getEntityData().setInteger(parryCooldownNBT, ticks);

		if (!user.world.isRemote && user instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) user;
			NetworkHandler.sendToPlayer((EntityPlayerMP) player, new ParryPacket(ticks, player));
		}
	}

	public static int getParryCooldown(EntityLivingBase user) {
		if (user.getEntityData().hasKey(parryCooldownNBT)) {
			return user.getEntityData().getInteger(parryCooldownNBT);
		}
		return 0;
	}

	public static void tickParryCooldown(EntityLivingBase user) {
		int ticks = getParryCooldown(user);
		if (ticks > 0) {
			setParryCooldown(user, ticks - 1);
		}
	}

	public static boolean isParryAvailable(EntityLivingBase user) {
		return getParryCooldown(user) <= 0;
	}

	public static void setPostHitCooldown(EntityLivingBase user, int ticks) {
		user.getEntityData().setInteger(posthitCooldownNBT, ticks);
	}

	public static int getPostHitCooldown(EntityLivingBase user) {
		if (user.getEntityData().hasKey(posthitCooldownNBT)) {
			return user.getEntityData().getInteger(posthitCooldownNBT);
		}
		return 0;
	}

	public static void tickPostHitCooldown(EntityLivingBase user) {
		int ticks = getPostHitCooldown(user);
		if (ticks > 0) {
			setPostHitCooldown(user, ticks - 1);
		}
	}

	/**
	 * Client-sided dodge
	 */
	private static void commandDodge(EntityPlayer user, int type) {
		initDodge(user, type);
		NetworkHandler.sendToServer(new DodgeCommandPacket(user, type));
	}

	public static void initDodge(EntityPlayer user, int type) {
		float bulk = ArmourCalculator.getTotalBulk(user);
		int cost = (int) ((type == 0 ? 15 : 10) * (bulk + 1));// Medium armour cost 2x more
		if (user.getHeldItemOffhand().getItem() instanceof ItemShield){
			cost += 100; //ToDo: Add Config option for shield dodge cost
		}

		if (bulk <= 1.0F && ItemWeaponMFR.tryPerformAbility(user, cost)) {
			float force = 1.0F - (bulk * 0.25F);// Medium armour gives 75%

			float direction = user.rotationYaw;
			if (type == 0)
				direction += 180;// BACK
			if (type == 1)
				direction -= 90;// LEFT
			if (type == -1)
				direction += 90;// RIGHT
			TacticalManager.leap(user, direction, force, 0.0F);
		}
	}

	/*
	 * Causes the victim to 'Spaz out' which never stops being funny (Apply every
	 * tick)
	 */
	public static void panic(EntityLivingBase victim, float speed, int directionTimer) {
		if (!shouldPanic(victim)) {
			return;
		}
		double moveX = victim.getEntityData().getDouble("MF2_PanicX");
		double moveZ = victim.getEntityData().getDouble("MF2_PanicZ");
		victim.setJumping(true);

		if ((moveX == 0 && moveZ == 0) || random.nextInt(directionTimer) == 0) {
			moveX = (random.nextDouble() - 0.5D) * 0.85D * speed;
			moveZ = (random.nextDouble() - 0.5D) * 0.85D * speed;

			victim.getEntityData().setDouble("MF2_PanicX", moveX);
			victim.getEntityData().setDouble("MF2_PanicZ", moveZ);
			if (victim.onGround)
				victim.motionY = 0.25F;
			victim.rotationYaw = (float) (Math.atan2(moveX, moveZ));
		}
		victim.swingArm(victim.getActiveHand());
		victim.limbSwing = 1.0F;
		victim.move(MoverType.SELF, moveX, 0D, moveZ);
	}

	private static boolean shouldPanic(EntityLivingBase victim) {
		if (!ConfigMobs.shouldFireCausePanic) {
			return false;
		}
		if (victim instanceof EntityMinotaur) {
			return ((EntityMinotaur) victim).getRageLevel() < 80;
		}
		return !(victim instanceof EntityPlayer || victim instanceof EntityCogwork);
	}

	/**
	 * How much strength is added (directly adds to melee dmg)
	 */
	public static float getStrengthEnhancement(EntityLivingBase user) {
		float mod = 0F;
		if (PowerArmour.isPowered(user)) {
			mod += 3F;
		}
		return Math.max(-0.5F, mod);
	}

	public static float getSpecialModifier(CustomMaterial material, String design, Entity hit_entity, boolean addEffect) {
		if (hit_entity == null){
			return 1.0F;
		}

		float modifier = 1.0F;

		if (design != null) {
			if (design.equalsIgnoreCase("dragonforged")) {
				if (TacticalManager.isDragon(hit_entity)) {
					modifier *= specialDragonModifier;
				}
			}

			if (design.equalsIgnoreCase("ornate")) {
				if (TacticalManager.isUnholyCreature(hit_entity)) {
					modifier *= specialOrnateModifier;
				}
			}
		}

		if (material != CustomMaterial.NONE) {
			if (isSilverishMaterial(material.name) && hit_entity instanceof EntityLivingBase) {
				if (hit_entity.getClass().getName().contains("Werewolf")) {
					modifier *= specialWerewolfModifier;
					applyUndeadBane((EntityLivingBase) hit_entity);
				} else if (TacticalManager.isUnholyCreature(hit_entity)) {
					modifier *= specialUnholyModifier;
					applyUndeadBane((EntityLivingBase) hit_entity);
				}
			}
		}

		return modifier;
	}

	public static boolean isSilverishMaterial(String material) {
		return material.equalsIgnoreCase("silver");
	}

	@SubscribeEvent
	public static void initAttack(LivingAttackEvent event) {
		EntityLivingBase hitter = getHitter(event.getSource());
		DamageSource src = event.getSource();
		EntityLivingBase hit_entity = event.getEntityLiving();
		boolean powerArmour = PowerArmour.isFullyArmoured(hit_entity);
		float damage = modifyDamage(src, hit_entity, event.getAmount(), false);

		if(hitter instanceof EntityPlayer) {
			applyHeavyBalance(hitter);
		}

		if (event.getSource().isProjectile() && !event.getSource().isFireDamage()) {
			if (powerArmour || (damage < event.getAmount() && hit_entity.getTotalArmorValue() > 0))// only if dam has been reduced
			{
				if (ConfigArmour.resistArrow && !event.isCanceled() && (damage <= 0.5F))// TacticalManager.resistArrow(event.entityLiving,// event.source, damage))
				{
					if (event.getSource().getTrueSource() != null && !event.getSource().getTrueSource().getEntityData().hasKey("arrowDeflectMF") && !(event.getSource().getImmediateSource() instanceof EntityEnderPearl)) {
						event.getSource().getTrueSource().getEntityData().setBoolean("arrowDeflectMF", true);
						event.getEntityLiving().getEntityWorld().playSound(null, event.getEntity().getPosition(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.NEUTRAL, 1.0F, 0.5F);
						event.setCanceled(true);
					}
				}
			}
		}

		if (damage <= 0) {
			event.setCanceled(true);
		}
	}


	private static void applyHeavyBalance(EntityLivingBase hitter) {
		if (!ConfigWeapon.useBalance)
			return;
		if (!hitter.getHeldItemMainhand().isEmpty() && hitter.getHeldItemMainhand().getItem() instanceof ItemWeaponMFR) {
			ItemWeaponMFR hitterWeapon = ((ItemWeaponMFR) hitter.getHeldItemMainhand().getItem());
			if (hitterWeapon.isHeavyWeapon()) {
				TacticalManager.throwPlayerOffBalance((EntityPlayer) hitter, hitterWeapon.getBalance());
			}
		}
	}

	/**
	 * gets the melee hitter
	 */
	private static EntityLivingBase getHitter(DamageSource source) {
		if (source != null && source.getTrueSource() != null && source.getTrueSource() == source.getImmediateSource()
				&& source.getTrueSource() instanceof EntityLivingBase) {
			return (EntityLivingBase) source.getTrueSource();
		}
		return null;
	}

	@SubscribeEvent
	public static void onHit(LivingHurtEvent event) {
		DamageSource source = event.getSource();
		EntityLivingBase hit = event.getEntityLiving();

		if (source == null){
			return;
		}

		if (source == DamageSource.FALL && hit instanceof EntityPlayer) {
			onFall((EntityPlayer) hit, event.getAmount());
		}
		float damage = modifyDamage(source, hit, event.getAmount(), true);

		if (damage > 0 && hit.isSprinting()) {
			hit.setSprinting(false);
		}
		// TODO: Zombie armour
		if (event.getEntityLiving().getEntityData().hasKey(MonsterUpgrader.zombieArmourNBT) && event.getEntityLiving() instanceof EntityZombie) {
			damage = ISpecialArmor.ArmorProperties.applyArmor(event.getEntityLiving(), (NonNullList<ItemStack>) event.getEntityLiving().getArmorInventoryList(), event.getSource(), damage);
		}
		// TODO: Stick arrows (EXPERIMENTAL)
		if (ConfigSpecials.stickArrows && event.getSource().getImmediateSource() != null
				&& event.getSource().getImmediateSource() instanceof EntityArrow) {
			if (!event.getEntity().world.isRemote) {
				ArrowEffectsMF.stickArrowIn(event.getEntity(), ArrowEffectsMF.getDroppedArrow(event.getSource().getImmediateSource()), event.getSource().getImmediateSource());
			}
		}
		if (damage > 0) {
			onOfficialHit(source, hit, damage);

			if (event.getSource() instanceof EntityDamageSource && !(event.getSource() instanceof EntityDamageSourceIndirect)) {
				Entity entityHitter = event.getSource().getTrueSource();

				if (entityHitter instanceof EntityPlayer) {
					EntityPlayer attacker = (EntityPlayer) entityHitter;
					StaminaMechanics.onAttack(attacker, hit);
					ItemStack weapon = attacker.getHeldItemMainhand();
					HitSoundGenerator.makeHitSound(weapon, event.getEntityLiving());
				}
			}
		}
		event.setAmount(damage);
	}

	private static void onFall(EntityPlayer fallen, float height) {
		float weight = ArmourCalculator.getTotalWeightOfWorn(fallen, false);
		if (weight > 100) {
			weight -= 100F;
			float power = (height / 4F) * (weight / 100F);
			newShockwave(fallen, fallen.posX, fallen.posY, fallen.posZ, power, false, true);
		}
	}

	public static Shockwave newShockwave(Entity source, double x, double y, double z, float power, boolean fire, boolean smoke) {
		Shockwave explosion = new Shockwave("humanstomp", source.world, source, x, y, z, power);
		explosion.isFlaming = fire;
		explosion.isSmoking = smoke;
		explosion.initiate();
		explosion.decorateWave(true);
		return explosion;
	}

	private static float modifyDamage(DamageSource source, EntityLivingBase hit_entity, float damage, boolean properHit) {
		Entity attackSource = source.getImmediateSource();
		Entity hitter = source.getTrueSource();

		if (PowerArmour.allowDamageToBlock(source)) {
			damage = PowerArmour.modifyDamage(hit_entity, damage, source);
		}

		if (properHit && hit_entity instanceof EntityPlayer) {
			damage = modifyPlayerDamage((EntityPlayer) hit_entity, damage);
		}

		if (attackSource != null && hitter instanceof EntityLivingBase) {
			damage = modifyUserHitDamage(damage, (EntityLivingBase) hitter, attackSource, hitter == attackSource, hit_entity, properHit);
		}
		if (source.isExplosion() && isSkeleton(hit_entity)) {
			damage *= 5F;
		}

		// TODO: Elemental resistance
		damage *= TacticalManager.getResistance(hit_entity, source);
		if (source != DamageSource.LAVA && (source == DamageSource.ON_FIRE || source == DamageSource.IN_FIRE || source == DamageSource.HOT_FLOOR)) {
			if (damage <= 0.0F) {
				hit_entity.extinguish();
			}
		}

		return onUserHit(hit_entity, hitter, source, damage, properHit);
	}

	private static boolean isSkeleton(Entity target) {
		return target instanceof EntitySkeleton;
	}

	// TODO: damage modifier
	private static float modifyUserHitDamage(float damage, EntityLivingBase user, Entity source, boolean melee, Entity hit_entity, boolean properHit) {
		damage = modifyMobDamage(user, damage);
		// Power Attack
		if (melee && user instanceof EntityPlayer) {
			int powerAttack = initPowerAttack((EntityPlayer) user, hit_entity, properHit);
			if (powerAttack == 1) {
				damage *= (2F / 1.5F);
				onPowerAttack(damage, user, hit_entity, properHit);

				if (isSkeleton(hit_entity)) {
					damage *= 1.5F;
					if (properHit) {
						if (random.nextInt(2) == 0) {
							hit_entity.entityDropItem(new ItemStack(Items.BONE), 0.5F);
						}
					}
				}
			}
			if (powerAttack == -1) {
				damage /= 2F;
			}
		}
		if (user != null) {
			// TODO: Stamina Traits
			if (StaminaBar.isSystemActive) {
				if (StaminaBar.getStaminaValue(user) <= 0) {
					damage *= ConfigStamina.weaponDrain;
				}
			}
			if (!(user instanceof EntityPlayer)){
				if (user.hurtResistantTime > 12) {
					damage *= 0.5F;
				}
			}
			ItemStack weapon = user.getHeldItemMainhand();
			if (!weapon.isEmpty()) {
				if (weapon.getItem() instanceof IDamageModifier) {
					// TODO: IDamageModifier, this mods the damage for weapons
					damage = ((IDamageModifier) weapon.getItem()).modifyDamage(weapon, user, hit_entity, damage, properHit);
				}
				CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(weapon);
				String weaponType = CustomToolHelper.getCustomStyle(weapon);
				damage *= getSpecialModifier(material, weaponType, hit_entity, true);
			}
		}

		return damage;
	}

	private static void onPowerAttack(float dam, EntityLivingBase user, Entity target, boolean properHit) {
		ItemStack weapon = user.getHeldItemMainhand();
		int ticks = 20;
		if (!weapon.isEmpty() && weapon.getItem() instanceof IPowerAttack) {
			((IPowerAttack) weapon.getItem()).onPowerAttack(dam, user, target, properHit);
			ticks = ((IPowerAttack) weapon.getItem()).getParryModifier(weapon, user, target);
		}
		if (target instanceof EntityLivingBase) {
			if (ticks > getParryCooldown((EntityLivingBase) target)) {
				setParryCooldown((EntityLivingBase) target, ticks);
			}
		}
		if (!user.world.isRemote) {
			user.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20, 5));
			user.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 10));
		}
		if (user instanceof EntityPlayer) {
			TacticalManager.lungeEntity(user, target, 0.5F, 0F);
			TacticalManager.throwPlayerOffBalance((EntityPlayer) user, 0.5F);
		}

		target.world.playSound(null, target.getPosition(), MineFantasySounds.CRITICAL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
	}

	private static float modifyMobDamage(EntityLivingBase user, float dam) {
		if (user instanceof EntityZombie && user.isChild()) {
			dam *= 0.65F;
		}
		return dam + getStrengthEnhancement(user);
	}

	private static void onOfficialHit(DamageSource src, EntityLivingBase target, float damage) {
		Entity source = src.getImmediateSource();
		Entity hitter = src.getTrueSource();

		if (source != null && hitter instanceof EntityLivingBase) {
			if (source == hitter) {
				EntityLivingBase user = (EntityLivingBase) hitter;
				ItemStack weapon = user.getHeldItemMainhand();
				if (!weapon.isEmpty()) {
					onWeaponHit(user, weapon, target, damage);
				}
				if (RPGElements.isSystemActive && user instanceof EntityPlayer && !user.world.isRemote) {
					Skill.COMBAT.addXP((EntityPlayer) user, (int) (damage / 5F));
				}
			} else {
				if (source instanceof EntityArrow && src.isProjectile()) {
					onArrowHit(source, target, hitter, damage);
				}
			}
		}
		CombatMechanics.setPostHitCooldown(target, 10);
	}

	private static void onWeaponHit(EntityLivingBase user, ItemStack weapon, Entity target, float dam) {
		if (target instanceof EntityLivingBase && weapon.getItem() instanceof ISpecialEffect) {
			((ISpecialEffect) weapon.getItem()).onProperHit(user, weapon, target, dam);
		}
		if (weapon.getItem() instanceof IKnockbackWeapon) {
			float kb = ((IKnockbackWeapon) weapon.getItem()).getAddedKnockback(user, weapon);

			if (kb != 0) {
				TacticalManager.knockbackEntity(target, user, kb, 0F);
			}
		}
		if (ConfigWeapon.useBalance && user instanceof EntityPlayer) {
			applyBalance((EntityPlayer) user);
		}
	}

	private static void onArrowHit(Entity arrow, Entity target, Entity shooter, float damage) {
		/*
		 * if(RPGElements.isSystemActive && shooter instanceof EntityPlayer) {
		 * SkillList.archery.addXP((EntityPlayer) shooter, (int)damage); }
		 */
	}

	private static float onUserHit(EntityLivingBase user, Entity entityHitting, DamageSource source, float dam, boolean properHit) {
		ItemStack weapon = user.getHeldItemMainhand();
		if ((properHit || source.isProjectile()) && !weapon.isEmpty() && !source.isUnblockable() && !source.isExplosion()) {
			float threshold = 10;// DEFAULT PARRY THRESHOLD
			float weaponFatigue = 2.0F;// DEFAULT FATIGUE
			int ticks = 18;// DEFAULT TICKS
			IParryable parry = null;

			if (weapon.getItem() instanceof IParryable) {
				parry = (IParryable) weapon.getItem();

				ticks = parry.getParryCooldown(source, dam, weapon);
				threshold = parry.getMaxDamageParry(user, weapon);
				weaponFatigue = parry.getParryStaminaDecay(source, weapon);
			}
			if (StaminaBar.isSystemActive && !StaminaBar.isAnyStamina(user, false)) {
				threshold /= 2;
			}
			threshold *= TacticalManager.getHighgroundModifier(user, entityHitting, 1.15F);

			if (ArmourCalculator.advancedDamageTypes && !user.world.isRemote) {
				threshold = ArmourCalculator.adjustArmorClassForDamage(source, threshold, 1.0F, 0.75F, 0.5F);
			}

			if (debugParry && !user.world.isRemote) {
				MFRLogUtil.logDebug("Init Parry: Damage = " + dam + " Threshold = " + threshold);
			}

			// USED FOR PARRYING its harder to block arrows
			if (TacticalManager.canParry(source, user, entityHitting, weapon)) {
				float previousDam = dam;
				dam = Math.max(0F, dam - threshold);

				if (properHit || dam <= 0) {
					user.hurtResistantTime = user.maxHurtResistantTime;
					user.hurtTime = 0;

					int result = onParry(source, user, entityHitting, dam, previousDam, parry);

					if (result == 1) {
						dam = 0;
					}
					ticks = ArmourCalculator.modifyParryCooldown(user, ticks);

					if (StaminaBar.isSystemActive && StaminaBar.doesAffectEntity(user)
							&& !StaminaBar.isAnyStamina(user, false)) {
						ticks *= 3;
					}
					if (ticks > getParryCooldown(user)) {
						setParryCooldown(user, ticks);
					}

					ItemWeaponMFR.applyFatigue(user, TacticalManager.getHighgroundModifier(user, entityHitting, 2.0F) * (dam + 1F) * parryFatigue * weaponFatigue);
					if (parry == null) {
						user.world.playSound(null, user.getPosition(), getDefaultParrySound(weapon), SoundCategory.NEUTRAL, 1.0F, 1.25F + (random.nextFloat() * 0.5F));
					} else if (!parry.playCustomParrySound(user, entityHitting, weapon)) {
						user.world.playSound(null, user.getPosition(), SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, SoundCategory.NEUTRAL, 1.0F, 1.25F + (random.nextFloat() * 0.5F));
					}
					if (user instanceof EntityPlayer) {
						user.stopActiveHand();
						ItemWeaponMFR.setParry(weapon, 40);
					}
				}
			}
		}
		if (StaminaBar.isSystemActive && StaminaBar.doesAffectEntity(user) && !StaminaBar.isAnyStamina(user, false)) {
			dam *= Math.max(1.0F, ConfigStamina.exhaustDamage);
		}

		// Fire dura degrade
		if (properHit && source.isFireDamage() && dam > 0) {
			Iterable<ItemStack> armour = user.getArmorInventoryList();
			for (ItemStack stack : armour) {
				if (!stack.isEmpty()) {
					int dura = (int) (dam) + 1;
					if (!user.world.isRemote && !isArmourFireImmune(stack, source)) {
						MFRLogUtil.logDebug("Armour Flame Damage: " + dura);
						if (stack.getItemDamage() + dura < stack.getMaxDamage()) {
							stack.damageItem(dura, user);
						} else {
							stack.setItemDamage(stack.getMaxDamage());
						}
					}
					if (stack.getItemDamage() >= stack.getMaxDamage()) {
						user.setItemStackToSlot(EntityLiving.getSlotForItemStack(stack), ItemStack.EMPTY);
						user.world.playSound(user.posX, user.posY + user.getEyeHeight() - (0.4F), user.posZ, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.AMBIENT, 1.0F, 1.0F, true);
					}
				}
			}
		}

		if (dam > 0 && user instanceof EntityPlayer || (entityHitting instanceof EntityPlayer)) {
			if (!user.world.isRemote) {
				String type = "Mixed";
				float[] f = ArmourCalculator.getRatioForSource(source);
				if (f == null) {
					type = "Basic";
				} else {
					if (f[0] > f[1] && f[0] > f[2])
						type = "Cutting";
					if (f[2] > f[1] && f[2] > f[0])
						type = "Piercing";
					if (f[1] > f[0] && f[1] > f[2])
						type = "Blunt";
				}

				MineFantasyReforged.LOG.debug(dam + "x " + type + " Damage inflicted to: " + user.getName() + " (" + user.getEntityId() + ")");
			}
		}
		return dam;
	}

	private static boolean isArmourFireImmune(ItemStack armour, DamageSource src) {
		if (!armour.isEmpty() && armour.getItem() instanceof IElementalResistance) {
			return ((IElementalResistance) armour.getItem()).getFireResistance(armour, src) >= 100F;
		}
		return false;
	}

	private static SoundEvent getDefaultParrySound(ItemStack weapon) {
		if (weapon.getUnlocalizedName().contains("wood") || weapon.getUnlocalizedName().contains("Wood") || weapon.getUnlocalizedName().contains("stone") || weapon.getUnlocalizedName().contains("Stone")) {
			return MineFantasySounds.WOOD_PARRY;
		}
		return SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR;
	}

	/**
	 * @return 0 for normal parry and 1 for evade
	 */
	private static int onParry(DamageSource source, EntityLivingBase user, Entity attacker, float dam, float prevDam,
			IParryable parry) {
		/*
		 * if(RPGElements.isSystemActive && user instanceof EntityPlayer) {
		 * SkillList.block.addXP((EntityPlayer)user, 10 + (int)prevDam*2); }
		 */
		if (RPGElements.isSystemActive && user instanceof EntityPlayer) {
			Skill.COMBAT.addXP((EntityPlayer) user, (int) (prevDam / 3F));
		}
		if (parry != null) {
			parry.onParry(source, user, attacker, dam);
		}
		if (user instanceof ISpecialCombatMob) {
			((ISpecialCombatMob) user).onParry(source, attacker, dam);
		}

		boolean groundBlock = user.onGround;
		ItemStack weapon = user.getHeldItemMainhand();

		// Redirect
		if (!user.world.isRemote && !TacticalManager.isRanged(source)) {
			if (canEvade(user)) {
				float powerMod = attacker.isSprinting() ? 4.0F : 2.5F;

				attacker.setSprinting(false);
				TacticalManager.lungeEntity(attacker, user, powerMod, 0.0F);
				TacticalManager.lungeEntity(user, attacker, 3F, 0.0F);
				return 1;
			}
		}
		return 0;
	}

	/**
	 * Determines if an evade can be made (jump or normal)
	 */
	private static boolean canEvade(EntityLivingBase user) {
		float stamModifier = 1.0F;
		if (user instanceof EntityPlayer) {
			if (!ResearchLogic.hasInfoUnlocked((EntityPlayer) user, "parrypro")) {
				return false;
			}

			if (!isFightStance(user)) {
				return false;
			}
		} else {
			if (random.nextInt(10) != 0)// Mobs can evade
			{
				return false;
			}
		}

		if (!user.onGround && user instanceof EntityPlayer && !tryJumpEvade((EntityPlayer) user, stamModifier)) {
			return false;
		}
		if (user instanceof EntityPlayer) {
			return tryGroundEvade((EntityPlayer) user, stamModifier);
		}
		return false;
	}

	/**
	 * If the player can slip past enemies Should be any armour but heavy
	 */
	private static boolean tryGroundEvade(EntityPlayer user, float cost) {
		return ItemWeaponMFR.tryPerformAbility(user, evade_cost * cost, true, false);
	}

	/**
	 * If the player can jump over enemies in evading Only ment for
	 * unarmoured/Lightarmour
	 */
	private static boolean tryJumpEvade(EntityPlayer user, float cost) {
		return ItemWeaponMFR.tryPerformAbility(user, jumpEvade_cost * cost, true, false);
	}

	@SubscribeEvent
	public static void updateLiving(LivingUpdateEvent event) {
		EntityLivingBase living = event.getEntityLiving();

		tickParryCooldown(living);
		tickPostHitCooldown(living);
		if (living instanceof EntityLiving) {
			EntityLiving mob = (EntityLiving) living;
			ItemStack held = mob.getHeldItemMainhand();

			{
				EntityLivingBase tar = mob.getAttackTarget();

				if (tar != null && tar instanceof EntityPlayer && (tar.isActiveItemStackBlocking() || tar.getActiveItemStack().getItemUseAction() == EnumAction.valueOf("mfr_block"))) {
					double dist = mob.getDistanceSq(tar);

					if (tar instanceof EntityZombie && mob.onGround && mob.getRNG().nextInt(10) == 0 && dist > 1D
							&& dist < 4D) {
						mob.motionY = 0.5F;
					}
				}
			}
			if (isAxe(held)) {
				EntityLivingBase tar = mob.getAttackTarget();

				if (tar != null) {
					double dist = mob.getDistanceSq(tar);

					if (mob.onGround && mob.getRNG().nextInt(5) == 0 && dist > 1D && dist < 4.0D) {
						mob.motionY = 0.5F;
					}
				}
				if (mob.getRNG().nextInt(100) == 0 && !mob.isSprinting() && !mob.isChild()) {
					mob.setSprinting(true);
				}
			}
			if (isFastblade(held)) {
				EntityLivingBase tar = mob.getAttackTarget();

				if (tar != null) {
					double dist = mob.getDistanceSq(tar);

					if (mob.onGround && mob.getRNG().nextInt(20) == 0 && dist > 1D && dist < 4.0D) {
						mob.motionY = 0.5F;
					}
				}
				if (mob.getRNG().nextInt(20) == 0 && !mob.isSprinting() && !mob.isChild()) {
					mob.setSprinting(true);
				}
			}
			if (living.isBurning() && !living.isImmuneToFire()) {
				panic(living, 0.25F, 5);
			}
		}
	}

	private static boolean isAxe(ItemStack held) {
		return !held.isEmpty() && held.getItem() instanceof ItemWaraxe
				|| !held.isEmpty() && held.getItem() instanceof ItemBattleaxe;
	}

	private static boolean isFastblade(ItemStack held) {
		return !held.isEmpty() && held.getItem() instanceof ItemDagger || !held.isEmpty() && held.getItem() instanceof ItemKatana;
	}

	private static void applyBalance(EntityPlayer entityPlayer) {
		MFRLogUtil.logDebug("Weapon Balance Init");
		ItemStack weapon = entityPlayer.getHeldItemMainhand();
		float balance = 0.0F;

		if (!weapon.isEmpty() && weapon.getItem() instanceof IWeightedWeapon) {
			balance = ((IWeightedWeapon) weapon.getItem()).getBalance(entityPlayer);
		}

		if (ConfigWeapon.useBalance && balance > 0 && entityPlayer != null) {
			TacticalManager.throwPlayerOffBalance(entityPlayer, balance);
		}
	}

	@SubscribeEvent
	public static void jump(LivingJumpEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			if (StaminaBar.isSystemActive && StaminaBar.doesAffectEntity(event.getEntityLiving())) {
				StaminaMechanics.onJump((EntityPlayer) event.getEntityLiving());
			}
		}
		if (event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer) {
			tryDodge((EntityPlayer) event.getEntityLiving());
		}
	}

	private static void tryDodge(EntityPlayer user) {
		if (user.isActiveItemStackBlocking() || user.getActiveItemStack().getItemUseAction() == EnumAction.valueOf("mfr_block")) {
			float forward = user.moveForward;
			float side = user.moveStrafing;
			if (side > 0F) {
				commandDodge(user, 1);// LEFT
			} else if (side < 0) {
				commandDodge(user, -1);// RIGHT
			} else if (forward < 0) {
				commandDodge(user, 0);// BACK
			}
		}
	}

	private static float modifyPlayerDamage(EntityPlayer hit, float dam) {
		if (ResearchLogic.hasInfoUnlocked(hit, MineFantasyKnowledgeList.toughness)) {
			dam *= 0.9F;// 10% Resist
		}
		return dam;
	}
}