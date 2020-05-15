package minefantasy.mfr.entity;

import net.minecraft.entity.MoverType;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.api.helpers.ArmourCalculator;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.ToolListMFR;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class EntityParachute extends Entity {
	/**
	 * true if no player in parachute
	 */
	private boolean isParachuteEmpty;
	private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.<Float>createKey(EntityParachute.class,
			DataSerializers.FLOAT);
	private static final DataParameter<Integer> TIME_SINCE_HIT_TAKEN = EntityDataManager
			.<Integer>createKey(EntityParachute.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager
			.<Integer>createKey(EntityParachute.class, DataSerializers.VARINT);
	private double speedMultiplier;
	private int parachutePosRotationIncrements;
	private double parachuteX;
	private double parachuteY;
	private double parachuteZ;
	private double parachuteYaw;
	private double parachutePitch;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;

	public EntityParachute(World world) {
		super(world);
		this.isParachuteEmpty = true;
		this.speedMultiplier = 0.07D;
		this.preventEntitySpawning = true;
		this.setSize(2F, 4F);
	}

	public EntityParachute(World world, double x, double y, double z) {
		this(world);
		this.setPosition(x, y + this.posY, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk
	 * on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public boolean canRiderInteract() {
		return false;
	}

	protected void entityInit() {
		this.dataManager.register(TIME_SINCE_HIT_TAKEN, new Integer(0));
		this.dataManager.register(FORWARD_DIRECTION, new Integer(1));
		this.dataManager.register(DAMAGE_TAKEN, new Float(0.0F));
	}

	/**
	 * Returns a boundingBox used to collide the entity with other entities and
	 * blocks. This enables the entity to be pushable on contact, like parachutes or
	 * minecarts.
	 */
	public AxisAlignedBB getCollisionBox(Entity entity) {
		return entity.getCollisionBoundingBox();
	}

	/**
	 * returns the bounding box for this entity
	 */
	public AxisAlignedBB getBoundingBox() {
		return this.getCollisionBoundingBox();
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities when
	 * colliding.
	 */
	public boolean canBePushed() {
		return true;
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding this
	 * one.
	 */
	public double getMountedYOffset() {
		return 1D;
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource src, float dam) {
		if (this.isEntityInvulnerable(src)) {
			return false;
		} else if (!this.world.isRemote && !this.isDead) {
			this.setForwardDirection(-this.getForwardDirection());
			this.setTimeSinceHit(10);
			this.setDamageTaken(this.getDamageTaken() + dam * 10.0F);
			this.setDamageTaken(dam);
			boolean flag = src.getImmediateSource() instanceof EntityPlayer
					&& ((EntityPlayer) src.getImmediateSource()).capabilities.isCreativeMode;

			if (flag || this.getDamageTaken() > 20.0F) {
				if (isBeingRidden()) {
					this.getRidingEntity().startRiding(this);
				}

				if (!flag) {
					this.dropItemWithOffset(ToolListMFR.PARACHUTE, 1, 0.0F);
				}

				this.setDead();
			}

			return true;
		} else {
			return true;
		}
	}

	/**
	 * Setups the entity to do the hurt animation. Only used by packets in
	 * multiplayer.
	 */
	@SideOnly(Side.CLIENT)
	public void performHurtAnimation() {
		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() * 11.0F);
	}

	/**
	 * Returns true if other Entities should be prevented from moving through this
	 * Entity.
	 */
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	/**
	 * Sets the position and rotation. Only difference from the other one is no
	 * bounding on the rotation. Args: posX, posY, posZ, yaw, pitch
	 */
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float xr, float yr, int zr) {
		if (this.isParachuteEmpty) {
			this.parachutePosRotationIncrements = zr + 5;
		} else {
			double d3 = x - this.posX;
			double d4 = y - this.posY;
			double d5 = z - this.posZ;
			double d6 = d3 * d3 + d4 * d4 + d5 * d5;

			if (d6 <= 1.0D) {
				return;
			}

			this.parachutePosRotationIncrements = 3;
		}

		this.parachuteX = x;
		this.parachuteY = y;
		this.parachuteZ = z;
		this.parachuteYaw = xr;
		this.parachutePitch = yr;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	@SideOnly(Side.CLIENT)
	public void setVelocity(double xv, double yv, double zv) {
		this.velocityX = this.motionX = xv;
		this.velocityY = this.motionY = yv;
		this.velocityZ = this.motionZ = zv;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	protected void fall(float distance) {
	}

	public void onUpdate() {
		if (isBeingRidden()) {
			getRidingEntity().fallDistance = 0;
			// this.rotationYaw = riddenByEntity.rotationYaw;
		}
		super.onUpdate();

		if (this.getTimeSinceHit() > 0) {
			this.setTimeSinceHit(this.getTimeSinceHit() - 1);
		}

		if (this.getDamageTaken() > 0.0F) {
			this.setDamageTaken(this.getDamageTaken() - 1.0F);
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		byte b0 = 5;
		double d0 = 0.0D;

		for (int i = 0; i < b0; ++i) {
			double d1 = this.getBoundingBox().minY
					+ (this.getBoundingBox().maxY - this.getBoundingBox().minY) * (i + 0) / b0 - 0.125D;
			double d3 = this.getBoundingBox().minY
					+ (this.getBoundingBox().maxY - this.getBoundingBox().minY) * (i + 1) / b0 - 0.125D;
			AxisAlignedBB axisalignedbb = new AxisAlignedBB(this.getBoundingBox().minX, d1, this.getBoundingBox().minZ,
					this.getBoundingBox().maxX, d3, this.getBoundingBox().maxZ);
		}

		double d10 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		double d2;
		double d4;
		double d11;
		double d12;

		if (this.world.isRemote && this.isParachuteEmpty) {
			if (this.parachutePosRotationIncrements > 0) {
				d2 = this.posX + (this.parachuteX - this.posX) / this.parachutePosRotationIncrements;
				d4 = this.posY + (this.parachuteY - this.posY) / this.parachutePosRotationIncrements;
				d11 = this.posZ + (this.parachuteZ - this.posZ) / this.parachutePosRotationIncrements;
				d12 = MathHelper.wrapDegrees(this.parachuteYaw - this.rotationYaw);
				this.rotationYaw = (float) (this.rotationYaw + d12 / this.parachutePosRotationIncrements);
				this.rotationPitch = (float) (this.rotationPitch
						+ (this.parachutePitch - this.rotationPitch) / this.parachutePosRotationIncrements);
				--this.parachutePosRotationIncrements;
				this.setPosition(d2, d4, d11);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			} else {
				d2 = this.posX + this.motionX;
				d4 = this.posY + this.motionY;
				d11 = this.posZ + this.motionZ;
				this.setPosition(d2, d4, d11);

				if (this.onGround) {
					this.motionX *= 0.5D;
					this.motionY *= 0.5D;
					this.motionZ *= 0.5D;
				}

				this.motionX *= 0.9900000095367432D;
				this.motionY *= 0.949999988079071D;
				this.motionZ *= 0.9900000095367432D;
			}
		} else {
			if (d0 < 1.0D) {
				d2 = d0 * 2.0D - 1.0D;
				this.motionY += 0.03999999910593033D * d2;
			} else {
				if (this.motionY < 0.0D) {
					this.motionY /= 2.0D;
				}

				this.motionY += 0.007000000216066837D;
			}

			if (this.getRidingEntity() != null && this.getRidingEntity() instanceof EntityLivingBase) {
				EntityLivingBase entitylivingbase = (EntityLivingBase) this.getRidingEntity();
				float f = this.getRidingEntity().rotationYaw + -entitylivingbase.moveStrafing * 90.0F;
				this.motionX += -Math.sin(f * (float) Math.PI / 180.0F) * this.speedMultiplier
						* entitylivingbase.moveForward * 0.05000000074505806D;
				this.motionZ += Math.cos(f * (float) Math.PI / 180.0F) * this.speedMultiplier
						* entitylivingbase.moveForward * 0.05000000074505806D;
			}

			d2 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

			if (d2 > 0.35D) {
				d4 = 0.35D / d2;
				this.motionX *= d4;
				this.motionZ *= d4;
				d2 = 0.35D;
			}

			if (d2 > d10 && this.speedMultiplier < 0.35D) {
				this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;

				if (this.speedMultiplier > 0.35D) {
					this.speedMultiplier = 0.35D;
				}
			} else {
				this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;

				if (this.speedMultiplier < 0.07D) {
					this.speedMultiplier = 0.07D;
				}
			}

			int l;

			this.motionY *= 0.5D;
			if (this.onGround) {
				this.motionX *= 0.5D;
				this.motionZ *= 0.5D;
			}

			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

			if ((this.collidedHorizontally && d10 > 0D) || isTooHeavy()) {
				if (!this.world.isRemote && !this.isDead) {
					this.setDead();

					for (l = 0; l < 3; ++l) {
						this.dropItemWithOffset(Item.getItemFromBlock(Blocks.WOOL), 1, 0.0F);
					}

					for (l = 0; l < 2; ++l) {
						this.dropItemWithOffset(ComponentListMFR.LEATHER_STRIP, 1, 0.0F);
					}
				}
			} else {
				this.motionX *= 0.9900000095367432D;
				this.motionY *= 0.949999988079071D;
				this.motionZ *= 0.9900000095367432D;
			}

			this.rotationPitch = 0.0F;
			d4 = this.rotationYaw;
			d11 = this.prevPosX - this.posX;
			d12 = this.prevPosZ - this.posZ;

			if (d11 * d11 + d12 * d12 > 0.001D) {
				d4 = ((float) (Math.atan2(d12, d11) * 180.0D / Math.PI));
			}

			double d7 = MathHelper.wrapDegrees(d4 - this.rotationYaw);

			if (d7 > 20.0D) {
				d7 = 20.0D;
			}

			if (d7 < -20.0D) {
				d7 = -20.0D;
			}

			this.rotationYaw = (float) (this.rotationYaw + d7);
			this.setRotation(this.rotationYaw, this.rotationPitch);

			if (!this.world.isRemote) {
				List list = this.world.getEntitiesWithinAABBExcludingEntity(this,
						this.getBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

				if (list != null && !list.isEmpty()) {
					for (int k1 = 0; k1 < list.size(); ++k1) {
						Entity entity = (Entity) list.get(k1);

						if (entity != this.getRidingEntity() && entity.canBePushed()
								&& entity instanceof EntityParachute) {
							entity.applyEntityCollision(this);
						}
					}
				}

				if (isBeingRidden() && this.getRidingEntity().isDead) {
					this.dismountRidingEntity();
				}
			}
		}
	}

	private boolean isTooHeavy() {
		if (getRidingEntity() != null && getRidingEntity() instanceof EntityLivingBase) {
			return ArmourCalculator.getTotalWeightOfWorn((EntityLivingBase) getRidingEntity(), false) > 100F;
		}
		return false;
	}

	public void updateRiderPosition() {
		if (isBeingRidden()) {
			double d0 = Math.cos(this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			double d1 = Math.sin(this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			this.getRidingEntity().setPosition(this.posX + d0,
					this.posY + this.getMountedYOffset() + this.getRidingEntity().getYOffset(), this.posZ + d1);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	protected void writeEntityToNBT(NBTTagCompound nbt) {
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	protected void readEntityFromNBT(NBTTagCompound nbt) {
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	/**
	 * Takes in the distance the entity has fallen this tick and whether its on the
	 * ground to update the fall distance and deal fall damage if landing on the
	 * ground. Args: distanceFallenThisTick, onGround
	 */
	protected void updateFallState(double dist, boolean hitGound) {

		if (hitGound) {
			if (this.fallDistance > 0.0F || onGround) {
				if (!this.world.isRemote && !this.isDead) {
					this.setDead();
					this.dropItemWithOffset(ToolListMFR.PARACHUTE, 1, 0.0F);
				}

				this.fallDistance = 0.0F;
			}
		} else if (this.world.getBlockState(
				new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY) - 1, MathHelper.floor(this.posZ)))
				.getMaterial() != Material.WATER && dist < 0.0D) {
			this.fallDistance = (float) (this.fallDistance - dist);
		}
	}

	/**
	 * Gets the damage taken from the last hit.
	 */
	public float getDamageTaken() {
		return this.dataManager.get(DAMAGE_TAKEN);
	}

	/**
	 * Sets the damage taken from the last hit.
	 */
	public void setDamageTaken(float dam) {
		this.dataManager.set(DAMAGE_TAKEN, Float.valueOf(dam));
	}

	/**
	 * Gets the time since the last hit.
	 */
	public int getTimeSinceHit() {
		return this.dataManager.get(TIME_SINCE_HIT_TAKEN);
	}

	/**
	 * Sets the time to count down from since the last time entity was hit.
	 */
	public void setTimeSinceHit(int time) {
		this.dataManager.set(TIME_SINCE_HIT_TAKEN, Integer.valueOf(time));
	}

	/**
	 * Gets the forward direction of the entity.
	 */
	public int getForwardDirection() {
		return this.dataManager.get(FORWARD_DIRECTION);
	}

	/**
	 * Sets the forward direction of the entity.
	 */
	public void setForwardDirection(int dir) {
		this.dataManager.set(FORWARD_DIRECTION, Integer.valueOf(dir));
	}

	/**
	 * true if no player in parachute
	 */
	@SideOnly(Side.CLIENT)
	public void setIsParachuteEmpty(boolean flag) {
		this.isParachuteEmpty = flag;
	}
}