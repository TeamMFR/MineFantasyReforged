package minefantasy.mfr.entity.mob;

import minefantasy.mfr.config.ConfigMobs;
import minefantasy.mfr.entity.EntityDragonBreath;
import minefantasy.mfr.entity.Shockwave;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.SoundsMFR;
import minefantasy.mfr.init.ToolListMFR;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

public class EntityDragon extends EntityMob implements IRangedAttackMob {

	private static final DataParameter<Boolean> TERRESTRIAL = EntityDataManager.<Boolean>createKey(EntityDragon.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> DISENGAGE_TIME = EntityDataManager
			.<Integer>createKey(EntityDragon.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> BREED = EntityDataManager.<Integer>createKey(EntityDragon.class,
			DataSerializers.VARINT);
	private static final DataParameter<Integer> TIER = EntityDataManager.<Integer>createKey(EntityDragon.class,
			DataSerializers.VARINT);
	private final Predicate<Entity> ATTACKABLE = new Predicate<Entity>() {
		public boolean apply(@Nullable Entity entity) {
			boolean flag = entity instanceof EntityLivingBase;
			
			if(entity instanceof EntityDragon) {
				flag = !((EntityDragon)entity).getType().breedName.equals(EntityDragon.this.getType().breedName);
			} else {
				
			}
			
			return !flag;
		}
	};
	public static int interestTimeSeconds = 90;
	private Entity lastEnemy;
	public static float heartChance = 1.0F;

	public EntityDragon(World worldIn) {
		super(worldIn);
		this.isImmuneToFire = true;
		((PathNavigateGround) this.getNavigator()).setCanSwim(true);
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
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(1, new EntityAIAttackRanged(this, 1.0D, getType().coolTimer, 50));
		this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
		this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6D, true));
		this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 50.0F, 120));
		this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, ATTACKABLE));
	}

	@Override
	public void onUpdate() {

		if (this.world.isRemote) {
			this.width = 3.0F * getScale();
			this.height = 2.0F * getScale();
		}
		this.motionY *= 0.6000000238418579D;

		
		if (!this.world.isRemote) {
			Entity entity = getAttackTarget();

			if (entity != null) {
				if (this.posY < entity.posY) {
					if (this.motionY < 0.0D) {
						this.motionY = 0.0D;
					}

					this.motionY += (0.5D - this.motionY) * 0.6000000238418579D;
				}
				double d0 = entity.posX - this.posX;
				double d1 = entity.posZ - this.posZ;
				double d3 = d0 * d0 + d1 * d1;

				if (d3 > 9.0D) {
					double d5 = (double) MathHelper.sqrt(d3);
					this.motionX += (d0 / d5 * 0.5D - this.motionX) * 0.6000000238418579D;
					this.motionZ += (d1 / d5 * 0.5D - this.motionZ) * 0.6000000238418579D;
				}
			} else {
				if (this.posY < 90) {
					if (this.motionY < 0.0D) {
						this.motionY = 0.0D;
					}

					this.motionY += (0.5D - this.motionY) * 0.6000000238418579D;
				} else {
					if (this.motionY < 0.0D) {
						this.motionY = 0.0D;
					}
				}
			}
		}


        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05000000074505806D)
        {
            this.rotationYaw = (float)MathHelper.atan2(this.motionZ, this.motionX) * (180F / (float)Math.PI) - 90.0F;
        }

		super.onUpdate();
	}

	private float getFlightSpeed() {
		return 0.8F;
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		setDragon(getRandomTier());
		return super.onInitialSpawn(difficulty, livingdata);
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getType().health);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(getType().meleeDamage);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.75D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(256.0D);
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

	public String getCommandSenderName() {
		String tierName = I18n.format(("entity." + getType().name + ".name"));
		String breedName = I18n.format(("entity.dragonbreed." + getType().breedName + ".name"));
		return I18n.format(tierName, breedName);
	}

	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(TERRESTRIAL, false);
		this.dataManager.register(DISENGAGE_TIME, 0);
		this.dataManager.register(BREED, 0);
		this.dataManager.register(TIER, rand.nextInt(5));
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

		nbt.setInteger("Breed", getBreed());
		nbt.setInteger("Tier", getTier());
//        nbt.setInteger("interestTime", interestTime);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("Breed")) {
//        fireBreathTick = nbt.getInteger("FireBreath");
//			fireBreathCooldown = nbt.getInteger("FireBreathCooldown");

			setBreed(nbt.getInteger("Breed"));
			setTier(nbt.getInteger("Tier"));
			this.setSize(3.0F * getScale(), 2.0F * getScale());
			stepHeight = 1.25F + (getTier() * 0.25F);
			this.experienceValue = 50 * (getTier() + 1);

//        interestTime = nbt.getInteger("interestTime");

			if (this.hasCustomName()) {
//            this.bossInfo.setName(this.getDisplayName());
			}
		}

	}
	


    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsMFR.DRAGON_SAY;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block floor) {
        this.playSound(SoundsMFR.DRAGON_STEP, 1.0F, 1.0F);
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundsMFR.DRAGON_HURT;
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
        return ComponentListMFR.DRAGON_HEART;
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
            this.dropItem(ComponentListMFR.DRAGON_HEART, 1);
        }
    }

    private Item getLoot(int tier) {
        if (tier == 4)// Ancient
        {
            return ToolListMFR.LOOT_SACK_RARE;
        }
        return ToolListMFR.LOOT_SACK_UC;// Any
    }

    private Entity getBreath(double xAngle, double yAngle, double zAngle) {
        return new EntityDragonBreath(this.world, this, xAngle, yAngle, zAngle, 1.0F).setDamage(getType().fireDamage)
                .setType(getType().rangedAttack.id);
    }
    
	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		Entity entity = getBreath(this.getLookVec().x, getLookVec().y, getLookVec().z);
		entity.setPosition(posX, posY, posZ);
		world.spawnEntity(entity);
	}

	@Override
	public void setSwingingArms(boolean swingingArms) {
		// TODO Auto-generated method stub

	}
}
