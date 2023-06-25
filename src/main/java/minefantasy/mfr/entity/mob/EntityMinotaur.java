package minefantasy.mfr.entity.mob;

import com.google.common.base.Predicate;
import minefantasy.mfr.api.armour.IArmourPenetrationMob;
import minefantasy.mfr.api.weapon.ISpecialCombatMob;
import minefantasy.mfr.config.ConfigMobs;
import minefantasy.mfr.entity.EntityBomb;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyMaterials;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.item.ItemBomb;
import minefantasy.mfr.item.ItemWeaponMFR;
import minefantasy.mfr.util.ArmourCalculator;
import minefantasy.mfr.util.PowerArmour;
import minefantasy.mfr.util.TacticalManager;
import net.minecraft.block.BlockCrops;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityMinotaur extends EntityCreature implements IArmourPenetrationMob, ISpecialCombatMob, IMob {
	private static final DataParameter<Byte> ATTACK = EntityDataManager.createKey(EntityMinotaur.class, DataSerializers.BYTE);
	private static final DataParameter<Integer> SPECIES = EntityDataManager.createKey(EntityMinotaur.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> TIER = EntityDataManager.createKey(EntityMinotaur.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> RAGE = EntityDataManager.createKey(EntityMinotaur.class, DataSerializers.VARINT);
	public int swing;
	private int grabCooldown;
	private int hitCooldownTime;
	private int specialAttackTime;
	private boolean isBreakDoorsTaskSet;
	private final float[] punch = new float[] {0F, 1F, 0F};
	private final float[] headbutt = new float[] {0F, 1F, 4F};
	private final EntityAIBreakDoor breakDoor = new EntityAIBreakDoor(this);

	public EntityMinotaur(World world) {
		super(world);
		this.setSize(1.5F, 2.5F);
		this.stepHeight = 1.0F;
		this.experienceValue = 40;
	}

	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 0.5D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));

	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(24.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35F);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ConfigMobs.minotaurMD);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ConfigMobs.minotaurHP);
	}

	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ATTACK, Byte.valueOf((byte) 0));
		this.dataManager.register(SPECIES, Integer.valueOf((byte) 0));
		this.dataManager.register(TIER, Integer.valueOf((byte) 0));
		this.dataManager.register(RAGE, Integer.valueOf((byte) 0));
	}

	public void worldGenTier(int species, int tier) {
		setMob(species, tier);
		setLoadout();
		this.setDropChance(EntityEquipmentSlot.MAINHAND, 1.0F);
		if (tier > 0) {
			this.setHomePosAndDistance(new BlockPos((int) posX, (int) posY, (int) posZ), getRange(tier));
		}
	}

	private int getRange(int tier) {
		return tier > 1 ? 12 : 24;
	}

	/**
	 * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
	 * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
	 */
	@Override
	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		float f = difficulty.getClampedAdditionalDifficulty();
		int species = MinotaurBreed.getEnvironment(this);

		setMob(species, 0);
		setLoadout();
		this.setDropChance(EntityEquipmentSlot.MAINHAND, 1.0F);

		this.setBreakDoorsAItask(this.rand.nextFloat() < f * 0.1F);

		return livingdata;
	}

	public void setLoadout() {
		String tier = getMinotaur().weaponTier;
		if (tier != null) {
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, getRandomWeapon().construct(tier, MineFantasyMaterials.Names.OAK_WOOD));
		}
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("entity." + getMinotaur().name + ".name");
	}

	@Override
	public String getName() {
		return new TextComponentTranslation("entity." + getMinotaur().name + ".name").getFormattedText();
	}

	public ItemWeaponMFR getRandomWeapon() {
		if (getTier() == 3) {
			return MineFantasyItems.ORNATE_GREATSWORD;
		}
		ItemWeaponMFR[] list = new ItemWeaponMFR[] {MineFantasyItems.STANDARD_GREATSWORD,
				MineFantasyItems.STANDARD_KATANA,
				MineFantasyItems.STANDARD_BATTLEAXE,
				MineFantasyItems.STANDARD_WARHAMMER,
				MineFantasyItems.STANDARD_SPEAR,
				MineFantasyItems.STANDARD_HALBEARD};
		return list[rand.nextInt(list.length)];
	}

	public boolean isBreakDoorsTaskSet() {
		return this.isBreakDoorsTaskSet;
	}

	/**
	 * Sets or removes EntityAIBreakDoor task
	 */
	public void setBreakDoorsAItask(boolean enabled) {
		if (this.isBreakDoorsTaskSet != enabled) {
			this.isBreakDoorsTaskSet = enabled;
			((PathNavigateGround) this.getNavigator()).setBreakDoors(enabled);

			if (enabled) {
				this.tasks.addTask(1, this.breakDoor);
			} else {
				this.tasks.removeTask(this.breakDoor);
			}
		}
	}

	@Override
	public float getBlockPathWeight(BlockPos pos) {
		return super.getBlockPathWeight(pos);
	}

	/**
	 * Checks to make sure the light is not too bright where the mob is spawning
	 */
	protected boolean isValidLightLevel() {
		BlockPos pos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY), MathHelper.floor(this.posZ));

		if (this.world.getLightFromNeighbors(pos) > this.rand.nextInt(32)) {
			return false;
		} else {
			int l = this.world.getLight(pos);

			if (this.world.isThundering()) {
				int i1 = this.world.getSkylightSubtracted();
				this.world.setSkylightSubtracted(10);
				l = this.world.getLight(pos);
				this.world.setSkylightSubtracted(i1);
			}

			return l <= this.rand.nextInt(8);
		}
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.isValidLightLevel() && super.getCanSpawnHere();
	}

	/**
	 * Called frequently so the entity can update its state every tick as required.
	 * For example, zombies and skeletons use this to react to sunlight and start to
	 * burn.
	 */
	@Override
	public void onLivingUpdate() {
		this.updateArmSwingProgress();
		float f = this.getBrightness();

		if (f > 0.5F) {
			this.idleTime += 2;
		}

		super.onLivingUpdate();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (this.canDespawn() && !this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
			this.setDead();
		}

		if (!world.isRemote) {
			if (getRageLevel() == 0 && getAttackTarget() == null) {
				if (ticksExisted % 40 == 0) {
					forage();
				}
			}
			if (ticksExisted % 20 == 0) {
				if (!isBloodied()) {
					if (getRageVariable() > 0) {
						addRage(-1);
						if (getRageVariable() > 50) {
							setRage(50);
						}
					}
				}
			}
			if (hitCooldownTime > 0)
				--hitCooldownTime;
			if (grabCooldown > 0) {
				--grabCooldown;
			}
			if (specialAttackTime > 0) {
				--specialAttackTime;
			}
			if ((getAttack() == (byte) 1 || getAttack() == (byte) 3) && specialAttackTime == 0) {
				initBasicAttack();
			}
			if (this.getAttackTarget() != null) {
				if (getRageLevel() > 20 && getAttack() != (byte) 2 && getHeldItemMainhand().isEmpty() && rand.nextInt(100) == 0) {
					this.initHeadbutt();
				}
				EntityLivingBase target = getAttackTarget();
				int intLvl = getIntLvl();

				if (intLvl > 0)// Smarter means more likely
				{
					if (getAttack() != 2 && rand.nextInt(10) < intLvl && onGround && target instanceof EntityPlayer && (target.isActiveItemStackBlocking() || target.getActiveItemStack().getItemUseAction() == EnumAction.valueOf("mfr_block")) && this.getDistance(target) < 4F) {
						this.jump();
						this.initPowerAttack();
					}
				} else {
					if (getRageLevel() > 40 && getAttack() != 2 && rand.nextInt(50) == 0 && onGround && target instanceof EntityPlayer && (target.isActiveItemStackBlocking() || target.getActiveItemStack().getItemUseAction() == EnumAction.valueOf("mfr_block")) && this.getDistance(target) < 4F) {
						this.jump();
						this.initPowerAttack();
					}
				}
				double distance = this.getDistanceSq(target);
				if (!TacticalManager.isFlankedBy(target, this, 270) && distance > 6 && distance < 60
						&& rand.nextInt(50) == 0 && this.getAttack() == (byte) 0 && this.getMinotaur().throwsBombs) {
					this.throwBomb(target, 1.0F);
				}

				if (this.ticksExisted % 20 == 0 && (getRageLevel() <= 0 || !canEntityBeSeen(getAttackTarget()))) {
					if (rand.nextInt(50 + (50 * getIntLvl())) == 0) {
						this.setAttackTarget(null);
					}
				}
			}

			boolean inBeserk = getRageLevel() >= 100 && isBloodied();
			if (getAttack() == (byte) 2) {
				if (!inBeserk) {
					initBasicAttack();
				} else {
					this.swingArm(EnumHand.MAIN_HAND);
				}
			}
			if (getAttack() != (byte) 2 && inBeserk) {
				initBeserk();
			}
		}
	}

	private void forage() {
		if (rand.nextInt(100) == 0) {
			int radius = 8;
			for (int x = -radius; x <= radius; x++) {
				for (int y = -2; y <= 2; y++) {
					for (int z = -radius; z <= radius; z++) {
						BlockPos pos = new BlockPos(posX + x, posY + y, posZ + z);
						if (rand.nextInt(10) == 0) {
							if (world.getBlockState(pos).getBlock() == MineFantasyBlocks.BERRY_BUSH && world.getBlockState(pos).getBlock() == Blocks.AIR) {
								getNavigator().tryMoveToXYZ(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0.6F);
								return;
							}
							if (world.getBlockState(pos) instanceof BlockCrops) {
								getNavigator().tryMoveToXYZ(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0.6F);
								return;
							}
						}
					}
				}
			}
			return;
		}
		BlockPos pos = new BlockPos(posX, posY, posZ);
		if (world.getBlockState(pos).getBlock() == MineFantasyBlocks.BERRY_BUSH && world.getBlockState(pos).getBlock() == Blocks.AIR) {
			world.playSound(posX, posY + getEyeHeight(), posZ, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.AMBIENT, 1.0F, 0.75F, true);
			swingArm(EnumHand.MAIN_HAND);
			heal(10);
			world.setBlockState(pos, (Blocks.DIRT).getDefaultState(), 2);
		} else if (world.getBlockState(pos) instanceof BlockCrops && world.getBlockState(pos).getBlock() != Blocks.AIR) {
			world.playSound(posX, posY + getEyeHeight(), posZ, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.AMBIENT, 1.0F, 0.75F, true);
			world.playSound(posX, posY + getEyeHeight(), posZ, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.AMBIENT, 1.0F, 1.00F, true);
			swingArm(EnumHand.MAIN_HAND);
			heal(5);
			world.setBlockToAir(pos);
		}
	}

	@Override
	protected void dropFewItems(boolean playerKill, int looting) {
		int count = getLootCount() == 1 ? 1 : rand.nextInt(looting + getLootCount() + 1);
		for (int a = 0; a < count; a++) {
			Item drop = getLoot();
			this.dropItem(drop, 1);
		}
		count = rand.nextInt(looting + 4) + 1;
		for (int a = 0; a < count; a++) {
			this.dropItem(MineFantasyItems.RAWHIDE_LARGE, 1);
		}
		count = rand.nextInt(looting + 2) + 1;
		for (int a = 0; a < count; a++) {
			this.dropItem(isBurning() ? Items.COOKED_BEEF : Items.BEEF, 1);
		}
	}

	private Item getLoot() {
		int breed = getTier();
		if (breed > 2) {
			return MineFantasyItems.LOOT_SACK_EXQUISITE;
		}
		if (breed > 0) {
			return MineFantasyItems.LOOT_SACK_VALUABLE;
		}
		return MineFantasyItems.LOOT_SACK_COMMON;
	}

	private int getLootCount() {
		int breed = getTier();
		if (breed > 2) {
			return 1;
		}
		return 2 + breed;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {
		//Frost Breeds are slightly more vulnerable to fire damage
		if (source.isFireDamage() && MinotaurBreed.FROST_BREEDS.contains(getMinotaur())) {
			damage *= 2;
		}
		if (!super.attackEntityFrom(source, damage)) {
			return false;
		} else {
			Entity entity = source.getImmediateSource();
			if (entity != null) {
				addRage(this.getIntLvl() >= 1 ? (int) (damage / 2) : (int) damage);
			}
		}
		return true;
	}

	public void initBeserk() {
		TacticalManager.tryDisarm(this);
		// this.playSound("minefantasy2:mob.minotaur.beserk", 1.0F, 1.0F);
		setSprinting(true);
		setAttack((byte) 2);
	}

	public void initPowerAttack() {
		setSprinting(true);
		specialAttackTime = 30;
		hitCooldownTime = 0;
		setAttack((byte) 3);
	}

	public void initHeadbutt() {
		setSprinting(true);
		specialAttackTime = 20;
		this.hitCooldownTime = 0;
		setAttack((byte) 1);
	}

	public void initBasicAttack() {
		setSprinting(false);
		specialAttackTime = 0;
		setAttack((byte) 0);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_COW_AMBIENT;
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_COW_HURT;
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected SoundEvent getDeathSound() {
		return MineFantasySounds.MINOTAUR_DEATH;
	}

	@Override
	public float getSoundPitch() {
		return 0.5F;
	}

	protected Item getDropItem() {
		return MineFantasyItems.HIDE_LARGE;
	}

	public double getHeadChargeAngle() {
		return getAttack() == (byte) 1 ? 80F : 0F;
	}

	public float getArmourRating(DamageSource src) {
		float[] armour = getValueResistences();
		return (getMinotaur().armour_rating / 100F)
				* ArmourCalculator.adjustArmorClassForDamage(src, getDT(), armour[0], armour[1], armour[2]);
	}

	@Override
	protected void damageEntity(DamageSource source, float dam) {
		float AR = 1.0F + Math.max(0.0F, getArmourRating(source));

		super.damageEntity(source, dam / AR);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);

		nbt.setInteger("Attack", getAttack());
		nbt.setInteger("Species", getSpecies());
		nbt.setInteger("Breed", getTier());
		nbt.setInteger("Rage", getRageVariable());
		nbt.setBoolean("CanBreakDoors", this.isBreakDoorsTaskSet());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);

		setAttack((byte) nbt.getInteger("Attack"));
		int species = nbt.getInteger("Species");
		int breed = nbt.getInteger("Breed");
		setMob(species, breed);
		this.setBreakDoorsAItask(nbt.getBoolean("CanBreakDoors"));
		setRage(nbt.getInteger("Rage"));
	}

	private float getDT() {
		byte att = getAttack();
		if (att == (byte) 2) {
			return 5F;
		}
		return 2F;
	}

	/**
	 * Cutting, Blunt, Piercing
	 */
	private float[] getValueResistences() {
		return new float[] {0.75F, 1F, 0.5F};
	}

	/**
	 * 0=Normal, 1=Headbutt, 2=Beserk, 3=Power
	 */
	public byte getAttack() {
		return dataManager.get(ATTACK);
	}

	/**
	 * 0=Normal, 1=Headbutt, 2=Beserk, 3=Power
	 */
	public void setAttack(byte type) {
		dataManager.set(ATTACK, type);
	}

	/**
	 * @param species Brown, Snow, Nether
	 * @param tier    the tier such as normal, warlord, etc
	 */
	public void setMob(int species, int tier) {
		MinotaurBreed minotaur = MinotaurBreed.getBreed(species, tier);

		setSpecies(species);
		setTier(tier);

		this.preventEntitySpawning = minotaur.isSpecial;
		this.isImmuneToFire = species == 1;
		this.experienceValue = minotaur.experienceValue;

		if (tier == 0) {
			this.targetTasks.addTask(2, new AIAttackNearest(this));
		}
		if (tier >= 3) {
			this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
		}
	}

	@Override
	protected boolean canDespawn() {
		return !getMinotaur().isSpecial;
	}

	/**
	 * 0=Normal, 1=Nether
	 */
	public int getSpecies() {
		return dataManager.get(SPECIES);
	}

	/**
	 * 0=Normal, 1=Nether
	 */
	public void setSpecies(int type) {
		dataManager.set(SPECIES, type);

	}

	public int getTier() {
		return dataManager.get(TIER);
	}

	public void setTier(int type) {
		dataManager.set(TIER, type);
	}

	@Override
	public float[] getHitTraits() {
		byte att = getAttack();
		if (att == (byte) 1) {
			return headbutt;
		}
		return punch;
	}

	@Override
	public boolean attackEntityAsMob(Entity target) {
		if (world.getDifficulty() == EnumDifficulty.PEACEFUL && target instanceof EntityPlayer) {
			return false;
		}
		// if(hitCooldownTime > 0)return false;

		if (!canEntityBeSeen(target)) {
			return false;
		}
		if (getAttack() == 3 && motionY > 0 && target.posY < posY) {
			return false;
		}

		float dam = getHitDamage();

		if (getRageLevel() > 50) {
			addRage((int) (dam * 1.5));
		}
		if (getAttack() == 2 && this.isBeingRidden() && this.getPassengers().contains(target)) {
			if (target instanceof EntityLivingBase && rand.nextInt(4) == 0) {
				ArmourCalculator.damageArmour((EntityLivingBase) target, (int) (dam * 0.25F));
			}
		}
		if (!this.isBeingRidden()) {
			if (grabCooldown <= 0 && rand.nextInt(100) < getGrabChance() && canPickUp(target)) {
				target.startRiding(this);
			}
		} else if (rand.nextInt(100) < ConfigMobs.minotaurTC && this.getPassengers().contains(target)) {
			getPassengers().forEach(Entity::dismountRidingEntity);
			grabCooldown = 60;
			TacticalManager.knockbackEntity(target, this, 4F, 1F);
		}
		if (getAttack() == 3 && fallDistance > 0 && target instanceof EntityPlayer
				&& (((EntityPlayer) target).isActiveItemStackBlocking() || ((EntityPlayer) target).getActiveItemStack().getItemUseAction() == EnumAction.valueOf("mfr_block")) && rand.nextInt(100) < getDisarmChance()) {
			TacticalManager.tryDisarm(this, (EntityLivingBase) target, true);
		}

		int i = getKnockbackBoost();

		if (target instanceof EntityLivingBase) {
			dam += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase) target).getCreatureAttribute());
			i += EnchantmentHelper.getKnockbackModifier(this);
		}

		boolean flag = target.attackEntityFrom(new DamageSourceMobMF(getAttackType(), this), dam);

		if (flag) {
			this.hitCooldownTime = getAttackSpeed();
			this.swingArm(EnumHand.MAIN_HAND);
			if (i > 0) {
				target.addVelocity(-MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F) * i * 0.5F, 0.1D, MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F) * i * 0.5F);
				this.motionX *= 0.6D;
				this.motionZ *= 0.6D;
			}

			int j = EnchantmentHelper.getFireAspectModifier(this);

			if (j > 0) {
				target.setFire(j * 4);
			}

			if (target instanceof EntityLivingBase) {
				EnchantmentHelper.applyThornEnchantments((EntityLivingBase) target, this);
			}

			EnchantmentHelper.applyArthropodEnchantments(this, target);
		}

		return flag;
	}

	private boolean canPickUp(Entity target) {
		if (target instanceof EntityLivingBase && PowerArmour.isWearingCogwork((EntityLivingBase) target)) {
			return false;
		}
		float minotaurSize = getVolume(this);
		float targetSize = getVolume(target);
		return minotaurSize > targetSize;
	}

	private float getVolume(Entity entity) {
		return entity.width * entity.width * entity.height;
	}

	private int getAttackSpeed() {
		return 0;
		/*
		 * if(rage >= 100) { return 10; } if(rage > 80) { return 15; } if(rage > 50) {
		 * return 20; } if(rage > 25) { return 30; } return 35;
		 */
	}

	@Override
	public void onKillEntity(EntityLivingBase victim) {
		super.onKillEntity(victim);
		if (!isBloodied()) {
			addRage(-20);
		}
		if (getHealth() < getMaxHealth() * 0.75F) {
			this.heal(5);
		}
	}

	public MinotaurBreed getMinotaur() {
		return MinotaurBreed.getBreed(getSpecies(), getTier());
	}

	private int getDisarmChance() {
		return getMinotaur().disarmChance;
	}

	private int getGrabChance() {
		return (getAttack() == 2 ? getMinotaur().grabChanceBeserk : getMinotaur().grabChance);
	}

	private int getThrowChance() {
		return getMinotaur().throwChance;
	}

	private String getAttackType() {
		byte att = getAttack();
		if (att == (byte) 1) {
			return "gore";
		}
		if (att == (byte) 2) {
			return "beserk";
		}
		return "pound";
	}

	private int getKnockbackBoost() {
		byte att = getAttack();
		if (att == (byte) 1) {
			return getSpecies() == 1 ? 4 : 2;
		}
		return 0;
	}

	private float getHitDamage() {
		MinotaurBreed breed = getMinotaur();
		byte att = getAttack();

		if (att != (byte) 1 && !getHeldItemMainhand().isEmpty())// no weapon dam on gore
		{
			float melee = att == (byte) 2 ? breed.beserkDamage : breed.poundDamage;

			return Math.max(2F, melee - 2F);// Weapon Dam
		}

		if (att == (byte) 1) {
			return breed.goreDamage;
		}
		if (att == (byte) 2) {
			return breed.beserkDamage;
		}
		return breed.poundDamage;
	}

	public boolean isBloodied() {
		float t = this.getMinotaur().beserkThreshold / 100F;
		return getHealth() < (getMaxHealth() * t);
	}

	@SideOnly(Side.CLIENT)
	public String getTexture() {
		String tex = "minotaur" + getMinotaur().tex;
		if (isBloodied()) {
			tex += "_bloodied";
		}
		return tex;
	}

	public int getRageVariable() {
		return dataManager.get(RAGE);
	}

	public void addRage(int level) {
		setRage(getRageVariable() + level);
	}

	public void setRage(int level) {
		if (!world.isRemote)
			dataManager.set(RAGE, Math.max(0, level));
	}

	public int getRageLevel() {
		return isBloodied() ? 100 : getRageVariable();
	}

	private int getIntLvl() {
		return this.getMinotaur().intelligenceLvl;
	}

	public boolean isDocile() {
		return getIntLvl() <= 0;
	}

	@Override
	public boolean canParry(DamageSource source) {
		int intLvl = this.getIntLvl();
		if (intLvl == 0) {
			return rand.nextInt(8) == 0;
		}
		return this.getAttack() == (byte) 0;
	}

	public void throwBomb(EntityLivingBase attackTarget, float spread) {
		ItemStack itemBomb = new ItemStack(MineFantasyItems.BOMB_CUSTOM);
		ItemBomb.setFilling(itemBomb, "1");
		ItemBomb.setFuse(itemBomb, "basic");
		ItemBomb.setCasing(itemBomb, "ceramic");
		ItemBomb.setPowder(itemBomb, "black_powder");

		EntityBomb bomb = new EntityBomb(world, this, itemBomb).setType("shrapnel", "ceramic", "basic", "black_powder");
		world.spawnEntity(bomb);
		this.swingArm(EnumHand.MAIN_HAND);
	}

	@Override
	public void onParry(DamageSource source, Entity attacker, float dam) {
		Entity target = this.getAttackTarget();
		if (getIntLvl() > 1 && target != null && attacker == target && this.getDistanceSq(attacker) < 4) {
			this.setAttack((byte) 1);
			TacticalManager.lungeEntity(this, attacker, 2.0F, 0.2F);
		}
	}

	class AIAttackNearest extends EntityAINearestAttackableTarget<EntityLiving> {
		public AIAttackNearest(EntityMinotaur minotaur) {
			super(minotaur, EntityLiving.class, 10, true, false, (Predicate<EntityLivingBase>) p_apply_1_ -> !(p_apply_1_ instanceof EntityMinotaur));
		}
	}
}
