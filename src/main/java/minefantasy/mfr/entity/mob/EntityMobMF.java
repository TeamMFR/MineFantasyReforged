package minefantasy.mfr.entity.mob;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class EntityMobMF extends EntityCreature implements IMob {
	/**
	 * Above zero if this Mob is Angry.
	 */
	private int angerLevel;
	private UUID angerTargetUUID;

	public EntityMobMF(World world) {
		super(world);
		this.experienceValue = 5;
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

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (this.canDespawn() && !this.world.isRemote
				&& this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
			this.setDead();
		}
	}

	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.ENTITY_HOSTILE_SWIM;
	}

	@Override
	protected SoundEvent getSplashSound() {
		return SoundEvents.ENTITY_HOSTILE_SPLASH;
	}

	/**
	 * Hint to AI tasks that we were attacked by the passed EntityLivingBase and should retaliate. Is not guaranteed to
	 * change our actual active target (for example if we are currently busy attacking someone else)
	 */
	public void setRevengeTarget(@Nullable EntityLivingBase livingBase) {
		super.setRevengeTarget(livingBase);

		if (livingBase != null) {
			this.angerTargetUUID = livingBase.getUniqueID();
		}
	}

	protected void applyEntityAI() {
		this.targetTasks.addTask(1, new EntityMobMF.AIHurtByAggressor(this));
		this.targetTasks.addTask(2, new EntityMobMF.AITargetAggressor(this));
	}

	protected void updateAITasks() {
		if (this.isAngry()) {
			--this.angerLevel;
		}

		if (this.angerLevel > 0 && this.angerTargetUUID != null && this.getRevengeTarget() == null) {
			EntityPlayer entityplayer = this.world.getPlayerEntityByUUID(this.angerTargetUUID);
			this.setRevengeTarget(entityplayer);
			this.attackingPlayer = entityplayer;
			this.recentlyHit = this.getRevengeTimer();
		}

		super.updateAITasks();
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float dam) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else if (super.attackEntityFrom(source, dam)) {
			Entity entity = source.getImmediateSource();

			if (this.getRidingEntity() != entity && this.getRidingEntity() != entity) {
				if (entity != this) {
					this.attackEntity(entity, dam);
				}

				return true;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * Causes this mob to become angry at the supplied Entity (which will be a player).
	 */
	private void becomeAngryAt(Entity entity) {
		this.angerLevel = 400 + this.rand.nextInt(400);

		if (entity instanceof EntityLivingBase) {
			this.setRevengeTarget((EntityLivingBase) entity);
		}
	}

	public boolean isAngry() {
		return this.angerLevel > 0;
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_HOSTILE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_HOSTILE_DEATH;
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
		if (distance > 4) {
			playSound(SoundEvents.ENTITY_HOSTILE_BIG_FALL, 1.0F, 1.0F);
		} else {
			playSound(SoundEvents.ENTITY_HOSTILE_SMALL_FALL, 1.0F, 1.0F);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity target) {
		if (world.getDifficulty() == EnumDifficulty.PEACEFUL && target instanceof EntityPlayer) {
			return false;
		}

		float f = (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		int i = 0;

		if (target instanceof EntityLivingBase) {
			f = EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase) target).getCreatureAttribute());
			i += EnchantmentHelper.getKnockbackModifier((EntityLivingBase) target);
		}

		boolean flag = target.attackEntityFrom(DamageSource.causeMobDamage(this), f);

		if (flag) {
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

	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden by
	 * each mob to define their attack.
	 */

	protected void attackEntity(Entity target, float dam) {
		if (dam < 2.0F && target.getEntityBoundingBox().maxY > this.getEntityBoundingBox().minY
				&& target.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY) {
			this.attackEntityAsMob(target);
		}
	}

	/**
	 * Takes a coordinate in and returns a weight to determine how likely this
	 * creature will try to path to the block. Args: x, y, z
	 */
	@Override
	public float getBlockPathWeight(BlockPos pos) {
		return 0.5F - this.world.getLightBrightness(pos);
	}

	/**
	 * Checks to make sure the light is not too bright where the mob is spawning
	 */
	protected boolean isValidLightLevel() {
		BlockPos pos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY), MathHelper.floor(this.posZ));

		if (this.world.getLightFromNeighborsFor(EnumSkyBlock.SKY, pos) > this.rand.nextInt(32)) {
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
		return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.isValidLightLevel()
				&& super.getCanSpawnHere();
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setShort("Anger", (short) this.angerLevel);

		if (this.angerTargetUUID != null) {
			compound.setString("HurtBy", this.angerTargetUUID.toString());
		} else {
			compound.setString("HurtBy", "");
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.angerLevel = compound.getShort("Anger");
		String s = compound.getString("HurtBy");

		if (!s.isEmpty()) {
			this.angerTargetUUID = UUID.fromString(s);
			EntityPlayer entityplayer = this.world.getPlayerEntityByUUID(this.angerTargetUUID);
			this.setRevengeTarget(entityplayer);

			if (entityplayer != null) {
				this.attackingPlayer = entityplayer;
				this.recentlyHit = this.getRevengeTimer();
			}
		}
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
	}

	@Override
	protected boolean canDropLoot() {
		return true;
	}

	static class AIHurtByAggressor extends EntityAIHurtByTarget {
		public AIHurtByAggressor(EntityMobMF mobMF) {
			super(mobMF, true);
		}

		protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn) {
			super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);

			if (creatureIn instanceof EntityMobMF) {
				((EntityMobMF) creatureIn).becomeAngryAt(entityLivingBaseIn);
			}
		}
	}

	static class AITargetAggressor extends EntityAINearestAttackableTarget<EntityPlayer> {
		public AITargetAggressor(EntityMobMF p_i45829_1_) {
			super(p_i45829_1_, EntityPlayer.class, true);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute() {
			return ((EntityMobMF) this.taskOwner).isAngry() && super.shouldExecute();
		}
	}
}