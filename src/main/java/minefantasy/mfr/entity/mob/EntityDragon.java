package minefantasy.mfr.entity.mob;

import com.google.common.base.Predicate;
import minefantasy.mfr.config.ConfigMobs;
import minefantasy.mfr.entity.EntityDragonBreath;
import minefantasy.mfr.entity.Shockwave;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasySounds;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityDragon extends EntityMob implements IRangedAttackMob {

	private static final String BREED_TAG = "Breed";
	private static final String TIER_TAG = "Tier";

	private static final DataParameter<Byte> CHARGING_FLAG = EntityDataManager.createKey(EntityDragon.class, DataSerializers.BYTE);
	private static final DataParameter<Boolean> TERRESTRIAL = EntityDataManager.createKey(EntityDragon.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> DISENGAGE_TIME = EntityDataManager.createKey(EntityDragon.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> BREED = EntityDataManager.createKey(EntityDragon.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> TIER = EntityDataManager.createKey(EntityDragon.class, DataSerializers.VARINT);

	private final BossInfoServer bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS);

	private final Predicate<Entity> ATTACKABLE = entity -> entity instanceof EntityDragon ? ((EntityDragon) entity).getBreed() == this.getBreed() : entity instanceof EntityLivingBase;

	private BlockPos boundOrigin;
	public static int interestTimeSeconds = 90;
	private Entity lastEnemy;
	public static float heartChance = 1.0F;

	public EntityDragon(World worldIn) {
		super(worldIn);
		this.isImmuneToFire = true;
		((PathNavigateGround) this.getNavigator()).setCanSwim(true);
		this.moveHelper = new AIMoveControl(this);
	}

	public void disengage(int time) {
		if (getAttackTarget() != null && getAttackTarget() instanceof EntityPlayer) {
			lastEnemy = getAttackTarget();
		}
		setDisengageTime(time);
		setTerrestrial(false);
		double waypointX = this.posX + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
		double waypointY = this.posY + (this.rand.nextFloat()) * 16.0F;
		double waypointZ = this.posZ + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;

		navigator.setPath(navigator.getPathToXYZ(waypointX, waypointY, waypointZ), this.getFlightSpeed());

		setAttackTarget(null);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(4, new AIChargeAttack());
		this.tasks.addTask(8, new AIMoveRandom());
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
		this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityDragon.class}));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	public void onUpdate() {

		if (this.world.isRemote) {
			this.width = (3.0F * getScale()) + 3.0F;
			this.height = 2.0F * getScale();
		}

		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
		super.onUpdate();
		this.setNoGravity(true);
	}

	private float getFlightSpeed() {
		return 0.8F;
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		setDragon(getRandomTier());
		bossInfo.setName(getDisplayName());
		return super.onInitialSpawn(difficulty, livingdata);
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getType().health);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(getType().meleeDamage);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(256.0D);
	}

	private boolean getChargingFlag(int mask) {
		int i = this.dataManager.get(CHARGING_FLAG).byteValue();
		return (i & mask) != 0;
	}

	private void setChargingFlag(int mask, boolean value) {
		int i = this.dataManager.get(CHARGING_FLAG);

		if (value) {
			i = i | mask;
		} else {
			i = i & ~mask;
		}

		this.dataManager.set(CHARGING_FLAG, (byte) (i & 255));
	}

	public boolean isCharging() {
		return this.getChargingFlag(1);
	}

	public void setCharging(boolean charging) {
		this.setChargingFlag(1, charging);
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
		if (isTerrestrial()) {
			float power = Math.min(distance / 2F, 3F) * getScale();
			createShockwave(posX, posY, posZ, power / 2F, ConfigMobs.dragonGriefGeneral);
		}
	}

	public int getDisengageTime() {
		return dataManager.get(DISENGAGE_TIME);
	}

	public void setDisengageTime(int i) {
		dataManager.set(DISENGAGE_TIME, i);
	}

	public boolean isTerrestrial() {
		return dataManager.get(TERRESTRIAL);
	}

	public void setTerrestrial(boolean flag) {
		dataManager.set(TERRESTRIAL, flag);
	}

	public void setDragon(int tier) {
		setTier(tier);
		setBreed(DragonBreed.getRandomDragon(this, tier));
		setHealth(getMaxHealth());
		this.width = 3.0F * getScale();
		this.height = 2.0F * getScale();
		stepHeight = 1.25F + (tier * 0.25F);
		this.experienceValue = 50 * (tier + 1);
	}

	public float getScale() {
		return 0.6F + (getTier() * 0.2F);
	}

	private int getRandomTier() {
		float f = rand.nextFloat() * 100F;

		if (f < 25F) {
			return 0;// 0-25 (25%)
		}
		if (f < 50F) {
			return 2;// 25-50 (25%)
		}
		if (f < 59F) {
			return 3;// 50-59 (9%)
		}
		if (f < 60F)// 59-60 (1%)
		{
			return 4;// 55-60
		}
		return 1;// 60-100 40%
	}

	public DragonBreed getType() {
		return DragonBreed.getBreed(getTier(), getBreed());
	}

	public int getBreed() {
		return dataManager.get(BREED);
	}

	public void setBreed(int i) {
		dataManager.set(BREED, i);
	}

	public int getTier() {
		return dataManager.get(TIER);
	}

	public void setTier(int i) {
		dataManager.set(TIER, i);
	}

	@Override
	public ITextComponent getDisplayName() {
		String tierName = "entity." + getType().name + ".name";
		String breedName = I18n.format(("entity.dragonbreed." + getType().breedName + ".name"));
		return new TextComponentString(I18n.format(tierName, breedName));
	}

	/**
	 * Sets the custom name tag for this entity
	 */
	@Override
	public void setCustomNameTag(String name)
	{
		super.setCustomNameTag(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(TERRESTRIAL, false);
		this.dataManager.register(DISENGAGE_TIME, 0);
		this.dataManager.register(BREED, 0);
		this.dataManager.register(TIER, rand.nextInt(5));
		this.dataManager.register(CHARGING_FLAG, (byte) 0);
	}

	public Shockwave createShockwave(double x, double y, double z, float power, boolean grief) {
		return newShockwave(x, y, z, power, false, grief);
	}

	/**
	 * returns a new explosion. Does initiation (at time of writing Explosion is not
	 * finished)
	 */
	public Shockwave newShockwave(double x, double y, double z, float power, boolean fire, boolean smoke) {
		Shockwave explosion = new Shockwave("dragonstomp", world, this, x, y, z, power);
		explosion.isFlaming = fire;
		explosion.isGriefing = world.getGameRules().getBoolean("mobGriefing");
		explosion.isSmoking = smoke;
		explosion.initiate();
		explosion.decorateWave(true);
		return explosion;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);

		//        nbt.setInteger("FireBreath", fireBreathTick);
		//		nbt.setInteger("FireBreathCooldown", fireBreathCooldown);

		nbt.setInteger(BREED_TAG, getBreed());
		nbt.setInteger(TIER_TAG, getTier());
		//        nbt.setInteger("interestTime", interestTime);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey(BREED_TAG)) {
			//        fireBreathTick = nbt.getInteger("FireBreath");
			//			fireBreathCooldown = nbt.getInteger("FireBreathCooldown");

			setBreed(nbt.getInteger(BREED_TAG));
			setTier(nbt.getInteger(TIER_TAG));
			this.setSize(3.0F * getScale(), 2.0F * getScale());
			stepHeight = 1.25F + (getTier() * 0.25F);
			this.experienceValue = 50 * (getTier() + 1);

			//        interestTime = nbt.getInteger("interestTime");

			if (this.hasCustomName()) {
				this.bossInfo.setName(this.getDisplayName());
			}
		}

	}

	@Override
	protected SoundEvent getAmbientSound() {
		return MineFantasySounds.DRAGON_SAY;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block floor) {
		this.playSound(MineFantasySounds.DRAGON_STEP, 1.0F, 1.0F);
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return MineFantasySounds.DRAGON_HURT;
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 2.0F;
	}

	@Override
	protected float getSoundPitch() {
		return 1.0F;
	}

	protected Item getDropItem() {
		return MineFantasyItems.DRAGON_HEART;
	}

	private boolean didDropHeart(int tier) {
		if (tier == 4)// Ancient
		{
			return true;
		}
		if (tier == 3)// Elder
		{
			return true;
		}
		if (tier == 2)// Mature
		{
			return rand.nextFloat() * heartChance > 0.25F;// 75% chance
		}
		if (tier == 1)// Adult
		{
			return rand.nextFloat() * heartChance > 0.85F;// 15% chance
		}
		return false;// Young
	}

	private int getLootCount(int tier) {
		if (tier == 4)// Ancient
		{
			return 1;// 1 Exquisite
		}
		if (tier == 3)// Elder
		{
			return 2 + rand.nextInt(4);// 2-5 Valuable
		}
		if (tier == 2)// Mature
		{
			return 2 + rand.nextInt(1);// 2-3 Valuable
		}
		if (tier == 1)// Adult
		{
			return 1 + rand.nextInt(1);// 1-2 Valuable
		}
		return 1;// Young, 1 Valuable
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return super.getCanSpawnHere() && this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	public int getMaxSpawnedInChunk() {
		return 1;
	}

	/**
	 * Drop 0-2 items of this living's type. @param par1 - Whether this entity has
	 * recently been hit by a player. @param par2 - Level of Looting used to kill
	 * this mob.
	 */
	@Override
	protected void dropFewItems(boolean playerKill, int looting) {
		int count = getLootCount(getTier()) + rand.nextInt(looting + 1);
		for (int a = 0; a < count; a++) {
			Item drop = getLoot(getTier());
			this.dropItem(drop, 1);
		}
		if (getTier() == 4)// ANCIENT
		{
			this.dropItem(Items.NETHER_STAR, 1);
		}
		if (didDropHeart(this.getTier())) {
			this.dropItem(MineFantasyItems.DRAGON_HEART, 1);
		}
	}

	@Nullable
	public BlockPos getBoundOrigin() {
		return this.boundOrigin;
	}

	public void setBoundOrigin(@Nullable BlockPos boundOriginIn) {
		this.boundOrigin = boundOriginIn;
	}

	private Item getLoot(int tier) {
		return tier == 4 ? MineFantasyItems.LOOT_SACK_EXQUISITE : MineFantasyItems.LOOT_SACK_VALUABLE;
	}

	private Entity getBreath(double xAngle, double yAngle, double zAngle) {
		return new EntityDragonBreath(this.world, this, xAngle, yAngle, zAngle, 1.0F).setDamage(getType().fireDamage).setType(getType().rangedAttack.id);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		Entity entity = getBreath(this.getLookVec().x, getLookVec().y, getLookVec().z);
		entity.setPosition(posX, posY, posZ);
		world.spawnEntity(entity);
	}

	/**
	 * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in
	 * order to view its associated boss bar.
	 */
	public void addTrackingPlayer(EntityPlayerMP player)
	{
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	/**
	 * Removes the given player from the list of players tracking this entity. See {@link Entity#addTrackingPlayer} for
	 * more information on tracking.
	 */
	public void removeTrackingPlayer(EntityPlayerMP player)
	{
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	public void setSwingingArms(boolean swingingArms) {
		// TODO Auto-generated method stub
	}

    // net.minecraft.entity.monster.EntityVex.AIChargeAttack class.
	class AIChargeAttack extends EntityAIBase {

		public AIChargeAttack() {
			this.setMutexBits(1);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute() {
			if (EntityDragon.this.getAttackTarget() != null && !EntityDragon.this.getMoveHelper().isUpdating() && EntityDragon.this.rand.nextInt(7) == 0) {
				return EntityDragon.this.getDistanceSq(EntityDragon.this.getAttackTarget()) > 4.0D;
			} else {
				return false;
			}
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldContinueExecuting() {
			return EntityDragon.this.getMoveHelper().isUpdating() && EntityDragon.this.isCharging() && EntityDragon.this.getAttackTarget() != null && EntityDragon.this.getAttackTarget().isEntityAlive();
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting() {
			EntityLivingBase entitylivingbase = EntityDragon.this.getAttackTarget();
			Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
			EntityDragon.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
			EntityDragon.this.setCharging(true);
			EntityDragon.this.playSound(MineFantasySounds.DRAGON_FIRE, 1.0F, 1.0F);
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		public void resetTask() {
			EntityDragon.this.setCharging(false);
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void updateTask() {
			EntityLivingBase entitylivingbase = EntityDragon.this.getAttackTarget();
			double d0 = EntityDragon.this.getPositionVector().distanceTo(entitylivingbase.getPositionVector());
			if (d0 < 3) {
				EntityDragon.this.attackEntityAsMob(entitylivingbase);
				EntityDragon.this.setCharging(false);
			} else {
				if (d0 < 9.0D) {
					Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
					EntityDragon.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
				}
				if (d0 < 16.0D) {
					double xAngle = EntityDragon.this.getAttackTarget().posX - EntityDragon.this.posX;
					double yAngle = EntityDragon.this.getAttackTarget().getEntityBoundingBox().minY + EntityDragon.this.getAttackTarget().height / 2.0F
							- (EntityDragon.this.posY + EntityDragon.this.height / 2.0F);
					double zAngle = EntityDragon.this.getAttackTarget().posZ - EntityDragon.this.posZ;
					double power = 1.0D;
					Vec3d var20 = EntityDragon.this.getLook(1.0F);
					Entity breath = EntityDragon.this.getBreath(xAngle, yAngle, zAngle);
					breath.posX = EntityDragon.this.posX + var20.x * power;
					breath.posY = EntityDragon.this.posY + EntityDragon.this.height / 2.0F + 0.5D;
					breath.posZ = EntityDragon.this.posZ + var20.z * power;
					EntityDragon.this.world.spawnEntity(breath);
					System.out.println("");
				}
			}
		}
	}

	// net.minecraft.entity.monster.EntityVex.AIMoveControl
	class AIMoveControl extends EntityMoveHelper {
		public AIMoveControl(EntityDragon dragon) {
			super(dragon);
		}

		public void onUpdateMoveHelper() {
			if (this.action == EntityMoveHelper.Action.MOVE_TO) {
				double d0 = this.posX - EntityDragon.this.posX;
				double d1 = this.posY - EntityDragon.this.posY;
				double d2 = this.posZ - EntityDragon.this.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				d3 = (double) MathHelper.sqrt(d3);

				if (d3 < EntityDragon.this.getEntityBoundingBox().getAverageEdgeLength()) {
					this.action = EntityMoveHelper.Action.WAIT;
					EntityDragon.this.motionX *= 0.5D;
					EntityDragon.this.motionY *= 0.5D;
					EntityDragon.this.motionZ *= 0.5D;
				} else {
					EntityDragon.this.motionX += d0 / d3 * 0.05D * this.speed;
					EntityDragon.this.motionY += d1 / d3 * 0.05D * this.speed;
					EntityDragon.this.motionZ += d2 / d3 * 0.05D * this.speed;

					if (EntityDragon.this.getAttackTarget() == null) {
						EntityDragon.this.rotationYaw = -((float) MathHelper.atan2(EntityDragon.this.motionX, EntityDragon.this.motionZ)) * (180F / (float) Math.PI);
						EntityDragon.this.renderYawOffset = EntityDragon.this.rotationYaw;
					} else {
						double d4 = EntityDragon.this.getAttackTarget().posX - EntityDragon.this.posX;
						double d5 = EntityDragon.this.getAttackTarget().posZ - EntityDragon.this.posZ;
						EntityDragon.this.rotationYaw = -((float) MathHelper.atan2(d4, d5)) * (180F / (float) Math.PI);
						EntityDragon.this.renderYawOffset = EntityDragon.this.rotationYaw;
					}
				}
			}
		}
	}

	// net.minecraft.entity.monster.EntityVex.AIMoveRandom
	class AIMoveRandom extends EntityAIBase {
		public AIMoveRandom() {
			this.setMutexBits(1);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute() {
			return !EntityDragon.this.getMoveHelper().isUpdating() && EntityDragon.this.rand.nextInt(7) == 0;
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldContinueExecuting() {
			return false;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void updateTask() {
			BlockPos blockpos = EntityDragon.this.getBoundOrigin();

			if (blockpos == null) {
				blockpos = new BlockPos(EntityDragon.this);
			}

			for (int i = 0; i < 3; ++i) {
				BlockPos blockpos1 = blockpos.add(EntityDragon.this.rand.nextInt(15) - 7, EntityDragon.this.rand.nextInt(11) - 5, EntityDragon.this.rand.nextInt(15) - 7);

				if (EntityDragon.this.world.isAirBlock(blockpos1)) {
					EntityDragon.this.moveHelper.setMoveTo((double) blockpos1.getX() + 0.5D, (double) blockpos1.getY() + 0.5D, (double) blockpos1.getZ() + 0.5D, 0.25D);

					if (EntityDragon.this.getAttackTarget() == null) {
						EntityDragon.this.getLookHelper().setLookPosition((double) blockpos1.getX() + 0.5D, (double) blockpos1.getY() + 0.5D, (double) blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
					}

					break;
				}
			}
		}
	}
}
