package minefantasy.mfr.entity.mob;

import com.google.common.base.Predicate;
import minefantasy.mfr.entity.mob.ai.AI_CommonSprint;
import minefantasy.mfr.entity.mob.ai.hound.AI_HoundBeg;
import minefantasy.mfr.entity.mob.ai.hound.AI_HoundFollow;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityHound extends EntityTameable {
	// Animations
	public float jawMove;
	public int eatAnimation;
	private float headRotationCourse;
	private float headRotationCourseOld;
	/**
	 * true is the wolf is wet else false
	 */
	private static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.<Float>createKey(EntityHound.class, DataSerializers.FLOAT);
	private static final DataParameter<Boolean> BEGGING = EntityDataManager.<Boolean>createKey(EntityHound.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.<Integer>createKey(EntityHound.class, DataSerializers.VARINT);
	private boolean isShaking;
	private boolean isWet;
	/**
	 *
	 */
	private float timeWolfIsShaking;
	private float prevTimeWolfIsShaking;

	public EntityHound(World world) {
		super(world);
		this.setSize(0.6F, 0.8F);
		this.aiSit = new EntityAISit(this);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(1, new AI_CommonSprint(this, 16.0F));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(5, new AI_HoundFollow(this, 1.0D, 6.0F, 2.0F));
		this.tasks.addTask(6, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new AI_HoundBeg(this, 8.0F));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityAnimal.class, false, new Predicate<Entity>() {
			public boolean apply(@Nullable Entity p_apply_1_) {
				return p_apply_1_ instanceof EntitySheep || p_apply_1_ instanceof EntityRabbit;
			}
		}));
		this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, AbstractSkeleton.class, false));
		this.setTamed(false);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);

		if (this.isTamed()) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		} else {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		}
	}

	/**
	 * Sets the active target the Task system uses for tracking
	 */
	@Override
	public void setAttackTarget(EntityLivingBase target) {
		super.setAttackTarget(target);

		if (target == null) {
			this.setAngry(false);
		} else if (!this.isTamed()) {
			this.setAngry(true);
		}
	}

	/**
	 * main AI tick function, replaces updateEntityActionState
	 */
	@Override
	protected void updateAITasks() {
		this.dataManager.set(DATA_HEALTH_ID, this.getHealth());
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(DATA_HEALTH_ID, this.getHealth());
		this.dataManager.register(BEGGING, Boolean.FALSE);
		this.dataManager.register(COLLAR_COLOR, EnumDyeColor.RED.getDyeDamage());
	}

	@Override
	protected void playStepSound(BlockPos pos, Block floor) {
		this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("Angry", this.isAngry());
		nbt.setByte("CollarColor", (byte) this.getCollarColor().getColorValue());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.setAngry(nbt.getBoolean("Angry"));

		if (nbt.hasKey("CollarColor", 99)) {
			this.setCollarColor(EnumDyeColor.byDyeDamage(nbt.getByte("CollarColor")));
		}
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected SoundEvent getAmbientSound() {
		return this.isAngry() ? SoundEvents.ENTITY_WOLF_GROWL : (this.rand.nextInt(3) == 0 ? (this.isTamed() && this.dataManager.get(DATA_HEALTH_ID) < 10.0F ? SoundEvents.ENTITY_WOLF_WHINE : SoundEvents.ENTITY_WOLF_PANT) : SoundEvents.ENTITY_WOLF_HOWL);
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_WOLF_HURT;
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_WOLF_DEATH;
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	protected Item getDropItem() {
		return Item.getItemById(-1);
	}

	/**
	 * Called frequently so the entity can update its state every tick as required.
	 * For example, zombies and skeletons use this to react to sunlight and start to
	 * burn.
	 */
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (!this.world.isRemote && this.isShaking && !this.isWet && !this.hasPath() && this.onGround) {
			this.isWet = true;
			this.timeWolfIsShaking = 0.0F;
			this.prevTimeWolfIsShaking = 0.0F;
			this.world.setEntityState(this, (byte) 8);
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();
		this.headRotationCourseOld = this.headRotationCourse;

		if (this.isBegging()) {
			this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
		} else {
			this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
		}

		if (this.isWet()) {
			this.isShaking = true;
			this.isWet = false;
			this.timeWolfIsShaking = 0.0F;
			this.prevTimeWolfIsShaking = 0.0F;
		} else if ((this.isShaking || this.isWet) && this.isWet) {
			if (this.timeWolfIsShaking == 0.0F) {
				this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(),
						(this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			}

			this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
			this.timeWolfIsShaking += 0.05F;

			if (this.prevTimeWolfIsShaking >= 2.0F) {
				this.isShaking = false;
				this.isWet = false;
				this.prevTimeWolfIsShaking = 0.0F;
				this.timeWolfIsShaking = 0.0F;
			}

			if (this.timeWolfIsShaking > 0.4F) {
				float f = (float) this.getCollisionBoundingBox().minY;
				int i = (int) (MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float) Math.PI) * 7.0F);

				for (int j = 0; j < i; ++j) {
					float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
					float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
					this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + f1, f + 0.8F, this.posZ + f2, this.motionX,
							this.motionY, this.motionZ);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public boolean getWolfShaking() {
		return this.isShaking;
	}

	/**
	 * Used when calculating the amount of shading to apply while the wolf is
	 * shaking.
	 */
	@SideOnly(Side.CLIENT)
	public float getShadingWhileShaking(float p_70915_1_) {
		return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70915_1_)
				/ 2.0F * 0.25F;
	}

	@SideOnly(Side.CLIENT)
	public float getShakeAngle(float p_70923_1_, float p_70923_2_) {
		float f2 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70923_1_
				+ p_70923_2_) / 1.8F;

		if (f2 < 0.0F) {
			f2 = 0.0F;
		} else if (f2 > 1.0F) {
			f2 = 1.0F;
		}

		return MathHelper.sin(f2 * (float) Math.PI) * MathHelper.sin(f2 * (float) Math.PI * 11.0F) * 0.15F
				* (float) Math.PI;
	}

	@Override
	public float getEyeHeight() {
		return this.height * 0.8F;
	}

	@SideOnly(Side.CLIENT)
	public float getInterestedAngle(float p_70917_1_) {
		return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * p_70917_1_) * 0.15F * (float) Math.PI;
	}

	/**
	 * The speed it takes to move the entityliving's rotationPitch through the
	 * faceEntity method. This is only currently use in wolves.
	 */
	@Override
	public int getVerticalFaceSpeed() {
		return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float dam) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else {
			Entity entity = source.getImmediateSource();
			this.aiSit.setSitting(false);

			if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
				dam = (dam + 1.0F) / 2.0F;
			}

			return super.attackEntityFrom(source, dam);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity target) {
		int i = this.isTamed() ? 4 : 2;
		return target.attackEntityFrom(DamageSource.causeMobDamage(this), i);
	}

	@Override
	public void setTamed(boolean flag) {
		super.setTamed(flag);

		if (flag) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		} else {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		}
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets
	 * into the saddle on a pig.
	 */
	@Override
	public boolean processInteract(EntityPlayer user, EnumHand hand) {
		ItemStack itemstack = user.inventory.getCurrentItem();

		if (this.isTamed()) {
			if (!itemstack.isEmpty()) {
				if (itemstack.getItem() instanceof ItemFood) {
					ItemFood itemfood = (ItemFood) itemstack.getItem();

					if (itemfood.isWolfsFavoriteMeat() && this.dataManager.get(DATA_HEALTH_ID) < 20.0F) {
						if (!user.capabilities.isCreativeMode) {
							itemstack.shrink(1);
						}

						this.heal(itemfood.getHealAmount(itemstack));

						if (itemstack.getCount() <= 0) {
							user.inventory.setInventorySlotContents(user.inventory.currentItem, ItemStack.EMPTY);
						}

						return true;
					}
				} else if (itemstack.getItem() == Items.DYE) {
					EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(itemstack.getMetadata());

					if (enumdyecolor != this.getCollarColor()) {
						this.setCollarColor(enumdyecolor);

						if (!user.capabilities.isCreativeMode) {
							itemstack.shrink(1);
						}

						return true;
					}
				}
			}

			if (this.isOwner(user) && !this.world.isRemote && !this.isBreedingItem(itemstack)) {
				this.aiSit.setSitting(!this.isSitting());
				this.isJumping = false;
				this.navigator.clearPath();
				this.setAttackTarget((EntityLivingBase) null);
			}
		} else if (!itemstack.isEmpty() && itemstack.getItem() == Items.BONE && !this.isAngry()) {
			if (!user.capabilities.isCreativeMode) {
				itemstack.shrink(1);
			}

			if (itemstack.getCount() <= 0) {
				user.inventory.setInventorySlotContents(user.inventory.currentItem, ItemStack.EMPTY);
			}

			if (!this.world.isRemote) {
				if (this.rand.nextInt(3) == 0) {
					this.setTamedBy(user);
					this.navigator.clearPath();
					this.setAttackTarget((EntityLivingBase) null);
					this.aiSit.setSitting(true);
					this.setHealth(20.0F);
					this.playTameEffect(true);
					this.world.setEntityState(this, (byte) 7);

				} else {
					this.playTameEffect(false);
					this.world.setEntityState(this, (byte) 6);
				}
			}

			return true;
		}

		return super.processInteract(user, hand);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleStatusUpdate(byte id) {
		if (id == 8) {
			this.isShaking = true;
			this.timeWolfIsShaking = 0.0F;
			this.prevTimeWolfIsShaking = 0.0F;
		} else {
			super.handleStatusUpdate(id);
		}
	}

	@SideOnly(Side.CLIENT)
	public float getTailRotation() {
		return this.isAngry() ? 1.5393804F : (this.isTamed() ? (0.55F - (20.0F - ((Float) this.dataManager.get(DATA_HEALTH_ID)).floatValue()) * (float) Math.PI) : ((float) Math.PI / 5F));
	}

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed it
	 * (wheat, carrots or seeds depending on the animal type)
	 */
	@Override
	public boolean isBreedingItem(ItemStack item) {
		return !item.isEmpty() && (item.getItem() instanceof ItemFood && ((ItemFood) item.getItem()).isWolfsFavoriteMeat());
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	@Override
	public int getMaxSpawnedInChunk() {
		return 8;
	}

	/**
	 * Determines whether this wolf is angry or not.
	 */
	public boolean isAngry() {
		return (((Byte) this.dataManager.get(TAMED)).byteValue() & 2) != 0;
	}

	/**
	 * Sets whether this wolf is angry or not.
	 */
	public void setAngry(boolean angry) {
		byte b0 = ((Byte) this.dataManager.get(TAMED)).byteValue();

		if (angry) {
			this.dataManager.set(TAMED, Byte.valueOf((byte) (b0 | 2)));
		} else {
			this.dataManager.set(TAMED, Byte.valueOf((byte) (b0 & -3)));
		}
	}

	/**
	 * Return this wolf's collar color.
	 */
	public EnumDyeColor getCollarColor() {
		return EnumDyeColor.byDyeDamage(((Integer) this.dataManager.get(COLLAR_COLOR)).intValue() & 15);
	}

	/**
	 * Set this wolf's collar color.
	 */
	public void setCollarColor(EnumDyeColor colour) {
		this.dataManager.set(COLLAR_COLOR, Integer.valueOf(colour.getDyeDamage()));
	}

	@Override
	public EntityHound createChild(EntityAgeable parent) {
		EntityHound entitywolf = new EntityHound(this.world);
		UUID uuid = this.getOwnerId();

		if (uuid != null) {
			entitywolf.setOwnerId(uuid);
			entitywolf.setTamed(true);
		}

		return entitywolf;
	}

	public void setBegging(boolean beg) {
		this.dataManager.set(BEGGING, Boolean.valueOf(beg));
	}

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	@Override
	public boolean canMateWith(EntityAnimal mate) {
		if (mate == this) {
			return false;
		} else if (!this.isTamed()) {
			return false;
		} else if (!(mate instanceof EntityHound)) {
			return false;
		} else {
			EntityHound entitywolf = (EntityHound) mate;
			return entitywolf.isTamed() && (entitywolf.isSitting() ? false : this.isInLove() && entitywolf.isInLove());
		}
	}

	public boolean isBegging() {
		return ((Boolean) this.dataManager.get(BEGGING)).booleanValue();
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn() {
		return !this.isTamed() && this.ticksExisted > 2400;
	}

	@Override
	public boolean shouldAttackEntity(EntityLivingBase target, EntityLivingBase owner) {
		if (!(target instanceof EntityCreeper) && !(target instanceof EntityGhast)) {
			if (target instanceof EntityHound) {
				EntityHound entitywolf = (EntityHound) target;

				if (entitywolf.isTamed() && entitywolf.getOwner() == owner) {
					return false;
				}
			}

			return (!(target instanceof EntityPlayer) || !(owner instanceof EntityPlayer)
					|| ((EntityPlayer) owner).canAttackPlayer((EntityPlayer) target)) && (!(target instanceof EntityHorse) || !((EntityHorse) target).isTame());
		} else {
			return false;
		}
	}

	public String getTexture() {
		return "hound";
	}

	public boolean shouldFollowOwner() {
		return true;
	}
}