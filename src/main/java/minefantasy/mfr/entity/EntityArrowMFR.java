package minefantasy.mfr.entity;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.archery.IArrowMFR;
import minefantasy.mfr.api.archery.IArrowRetrieve;
import minefantasy.mfr.api.weapon.IDamageType;
import minefantasy.mfr.config.ConfigSpecials;
import minefantasy.mfr.config.ConfigWeapon;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.item.ArrowType;
import minefantasy.mfr.item.EnumFillingType;
import minefantasy.mfr.item.EnumPowderType;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.mechanics.CombatMechanics;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityArrowMFR extends EntityArrow implements IProjectile, IDamageType, IArrowRetrieve {
	private static final DataParameter<Byte> CRITICAL = EntityDataManager.<Byte>createKey(EntityArrow.class, DataSerializers.BYTE);
	private static final DataParameter<String> TEXTURE_DW = EntityDataManager.<String>createKey(EntityArrow.class, DataSerializers.STRING);
	/**
	 * 1 if the player can pick up the arrow
	 */
	public int canBePickedUp;
	/**
	 * Seems to be some sort of timer for animating an arrow.
	 */
	public int arrowShake;
	/**
	 * The owner of this arrow.
	 */
	public Entity shootingEntity;
	public float velocityModifier = 1.0F;
	public ArrowType arrowtype = ArrowType.NORMAL;
	public float firepower = 1F;
	private boolean playedSound = false;
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private Block inBlock;
	private int inData;
	private boolean inGround;
	private int ticksInGround;
	private int ticksInAir;
	private double damage = 2.0D;
	/**
	 * The amount of knockback an arrow applies when it hits a mob.
	 */
	private int knockbackStrength;
	private float power = 0F;

	public EntityArrowMFR(World world)
	{
		super(world);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
		this.damage = 2.0D;
		this.setSize(0.5F, 0.5F);
	}

	public EntityArrowMFR(World world, double x, double y, double z)
	{
		this(world);
		this.setPosition(x, y, z);
	}

	public EntityArrowMFR(World world, EntityLivingBase shooter)
	{
		this(world, shooter.posX, shooter.posY + (double)shooter.getEyeHeight() - 0.10000000149011612D, shooter.posZ);
		this.shootingEntity = shooter;

		if (shooter instanceof EntityPlayer)
		{
			this.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
		}
	}

	/**
	 * This is for shooting an arrow from a shooting entity to a target(Like
	 * skeletons and such)
	 */
	public EntityArrowMFR(World world, EntityLivingBase shooter, EntityLivingBase target, float accuracy, float power) {
		super(world);
		this.shootingEntity = shooter;

		this.firepower = power;
		if (shooter instanceof EntityPlayer) {
			this.canBePickedUp = 1;
		}

		this.posY = shooter.posY + shooter.getEyeHeight() - 0.10000000149011612D;
		double d0 = target.posX - shooter.posX;
		double d1 = target.getEntityBoundingBox().minY + target.height / 3.0F - this.posY;
		double d2 = target.posZ - shooter.posZ;
		double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);

		if (d3 >= 1.0E-7D) {
			float f2 = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
			float f3 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			double d4 = d0 / d3;
			double d5 = d2 / d3;
			this.setLocationAndAngles(shooter.posX + d4, this.posY, shooter.posZ + d5, f2, f3);
			float f4 = (float) d3 * 0.2F;
			this.shoot(d0, d1 + f4, d2, accuracy, power);
		}
	}

	/**
	 * This method is for firing an arrow from an entity (Mostly player shooting)
	 */
	public EntityArrowMFR(World world, EntityLivingBase shooter, float power) {
		this(world, shooter, 1.0F, power);
	}

	public EntityArrowMFR(World world, EntityLivingBase shooter, float spread, float power) {
		super(world);
		this.firepower = power / 2F;
		this.shootingEntity = shooter;

		if (shooter instanceof EntityPlayer) {
			this.canBePickedUp = 1;
		}

		this.setSize(0.5F, 0.5F);
		this.setLocationAndAngles(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
		this.shoot(this.motionX, this.motionY, this.motionZ, power * 1.5F, spread);
	}

	public static DamageSource causeBombDamage(Entity bomb, Entity user) {
		return (new EntityDamageSourceBomb(bomb, user)).setProjectile();
	}

	/**
	 * Implement this with the constructor
	 */
	public EntityArrowMFR setArrow(ItemStack arrow) {
		ItemStack arrowStack = arrow.copy();
		arrowStack.setCount(1);
		setArrowStack(arrowStack);
		return this;
	}

	public EntityArrowMFR setArrowTex(String tex) {
		updateTex(tex);
		return this;
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(CRITICAL, (byte) 0);// Critical
		this.dataManager.register(TEXTURE_DW, "steel_arrow");
	}

	public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy)
	{
		float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
		float f1 = -MathHelper.sin(pitch * 0.017453292F);
		float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
		this.shoot(f, f1, f2, velocity, inaccuracy);
		this.motionX += shooter.motionX;
		this.motionZ += shooter.motionZ;

		if (!shooter.onGround)
		{
			this.motionY += shooter.motionY;
		}
	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
	 */
	public void shoot(double x, double y, double z, float velocity, float inaccuracy)
	{
		float f = MathHelper.sqrt(x * x + y * y + z * z);
		x = x / (double)f;
		y = y / (double)f;
		z = z / (double)f;
		x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		x = x * (double)velocity;
		y = y * (double)velocity;
		z = z * (double)velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f1 = MathHelper.sqrt(x * x + z * z);
		this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
		this.rotationPitch = (float)(MathHelper.atan2(y, f1) * (180D / Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
		this.ticksInGround = 0;
	}

	/**
	 * Set the position and rotation values directly without any clamping.
	 */
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
	{
		this.setPosition(x, y, z);
		this.setRotation(yaw, pitch);
	}

	/**
	 * Updates the entity motion clientside, called by packets from the server
	 */
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z)
	{
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt(x * x + z * z);
			this.rotationPitch = (float)(MathHelper.atan2(y, f) * (180D / Math.PI));
			this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.ticksInGround = 0;
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onEntityUpdate();

		if (ConfigSpecials.dynamicArrows && world.isRemote) {
			return;
		}
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
			this.rotationPitch = (float) (MathHelper.atan2(this.motionY, f) * (180D / Math.PI));
			this.prevRotationYaw = this.rotationYaw;
			this.prevRotationPitch = this.rotationPitch;
		}
		BlockPos pos = new BlockPos(this.xTile, this.yTile, this.zTile);
		IBlockState state = this.world.getBlockState(pos);
		Block block = state.getBlock();

		if (state.getMaterial() != Material.AIR) {
			AxisAlignedBB axisalignedbb = state.getBoundingBox(this.world, pos);

			if (axisalignedbb != Block.NULL_AABB && axisalignedbb.offset(pos).contains(new Vec3d(this.posX, this.posY, this.posZ))) {
				this.inGround = true;
			}
		}

		if (this.arrowShake > 0) {
			--this.arrowShake;
		}

		if (this.inGround) {
			int j = block.getMetaFromState(state);

			if (state == this.inBlock.getDefaultState() && j == this.inData) {
				++this.ticksInGround;

				if (this.ticksInGround == 1200) {
					this.setDead();
				}
			} else {
				this.inGround = false;
				this.motionX *= this.rand.nextFloat() * 0.2F;
				this.motionY *= this.rand.nextFloat() * 0.2F;
				this.motionZ *= this.rand.nextFloat() * 0.2F;
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			}
		} else {
			++this.ticksInAir;
			Vec3d vec31 = new Vec3d(this.posX, this.posY, this.posZ);
			Vec3d vec3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			RayTraceResult rayTraceResult = this.world.rayTraceBlocks(vec31, vec3, false, true, false);
			vec31 = new Vec3d(this.posX, this.posY, this.posZ);
			vec3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			if (rayTraceResult != null) {
				vec3 = new Vec3d(rayTraceResult.hitVec.x, rayTraceResult.hitVec.y, rayTraceResult.hitVec.z);
			}

			Entity entity = null;
			List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;
			int i;
			float f1;

			for (i = 0; i < list.size(); ++i) {
				Entity entity1 = list.get(i);

				if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5)) {
					f1 = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand(f1, f1, f1);
					RayTraceResult rayTraceResult1 = axisalignedbb1.calculateIntercept(vec31, vec3);

					if (rayTraceResult1 != null) {
						double d1 = vec31.distanceTo(rayTraceResult1.hitVec);

						if (d1 < d0 || d0 == 0.0D) {
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if (entity != null) {
				rayTraceResult = new RayTraceResult(entity);
			}

			if (rayTraceResult != null && rayTraceResult.entityHit instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) rayTraceResult.entityHit;

				if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer)) {
					rayTraceResult = null;
				}
			}

			float f2;
			float f4;

			if (rayTraceResult != null) {
				if (isExplosive()) {
					explode();
				}
				if (rayTraceResult.entityHit != null) {

					float dam = Math.max(0.1F, this.getHitDamage() * firepower);// (getDamageModifier()*power) / 10F *
					// (float)k;

					dam *= getDamageModifier(rayTraceResult.entityHit);

					if (this.getIsCritical()) {
						dam *= (rand.nextFloat() * 0.5F) + 1.0F;
					}

					DamageSource damagesource;

					if (this.shootingEntity == null) {
						damagesource = DamageSource.causeArrowDamage(this, this);
					} else {
						damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
					}

					if (this.isBurning() && !(rayTraceResult.entityHit instanceof EntityEnderman)) {
						rayTraceResult.entityHit.setFire(5);
					}
					if (isExplosive()) {
						dam = this.getExplosionDamage();
						damagesource.setExplosion();
						if (this.getFilling().equals("fire")) {
							damagesource.setFireDamage();
						}
					}
					if (rayTraceResult.entityHit.attackEntityFrom(damagesource, dam)) {
						onHitEntity(rayTraceResult.entityHit, dam);
						if (rayTraceResult.entityHit instanceof EntityLivingBase) {
							EntityLivingBase entitylivingbase = (EntityLivingBase) rayTraceResult.entityHit;

							if (!this.world.isRemote) {
								entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
							}

							if (this.knockbackStrength > 0) {
								f4 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

								if (f4 > 0.0F) {
									rayTraceResult.entityHit.addVelocity(
											this.motionX * this.knockbackStrength * 0.6000000238418579D / f4, 0.1D,
											this.motionZ * this.knockbackStrength * 0.6000000238418579D / f4);
								}
							}

							if (this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase) {
								EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
								EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) this.shootingEntity, entitylivingbase);
							}

							if (this.shootingEntity != null && rayTraceResult.entityHit != this.shootingEntity
									&& rayTraceResult.entityHit instanceof EntityPlayer
									&& this.shootingEntity instanceof EntityPlayerMP) {
								((EntityPlayerMP) this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
							}
						}

						playHitSound();

						if (!(rayTraceResult.entityHit instanceof EntityEnderman)) {
							this.setDead();
						}
					} else {
						this.motionX *= -0.10000000149011612D;
						this.motionY *= -0.10000000149011612D;
						this.motionZ *= -0.10000000149011612D;
						this.rotationYaw += 180.0F;
						this.prevRotationYaw += 180.0F;
						this.ticksInAir = 0;
					}
					if (isMagicArrow()) {
						setDead();
					}
				} else {
					this.xTile = rayTraceResult.getBlockPos().getX();
					this.yTile = rayTraceResult.getBlockPos().getY();
					this.zTile = rayTraceResult.getBlockPos().getZ();
					this.inBlock = state.getBlock();
					this.inData = block.getMetaFromState(state);
					this.motionX = ((float) (rayTraceResult.hitVec.x - this.posX));
					this.motionY = ((float) (rayTraceResult.hitVec.y - this.posY));
					this.motionZ = ((float) (rayTraceResult.hitVec.z - this.posZ));
					f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					this.posX -= this.motionX / f2 * 0.05000000074505806D;
					this.posY -= this.motionY / f2 * 0.05000000074505806D;
					this.posZ -= this.motionZ / f2 * 0.05000000074505806D;
					playHitSound();
					this.inGround = true;
					this.arrowShake = 7;
					this.setIsCritical(false);

					if (state.getMaterial() != Material.AIR) {
						this.inBlock.onEntityCollidedWithBlock(this.world, pos, state, this);
					}
					if (isMagicArrow()) {
						setDead();
					}
					if (ConfigWeapon.breakArrowsGround && didArrowBreak()) {
						breakArrow();
					}
				}
				if (getEntityData().hasKey("hasBroken_MFArrow")) {
					this.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
					setDead();
				}
			}

			if (isMagicArrow()) {
				for (i = 0; i < 4; ++i) {
					this.world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX + this.motionX * i / 4.0D,
							this.posY + this.motionY * i / 4.0D, this.posZ + this.motionZ * i / 4.0D, -this.motionX,
							-this.motionY + 0.2D, -this.motionZ);
				}
			}

			if (this.getIsCritical()) {
				for (i = 0; i < 4; ++i) {
					this.world.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * i / 4.0D,
							this.posY + this.motionY * i / 4.0D, this.posZ + this.motionZ * i / 4.0D, -this.motionX,
							-this.motionY + 0.2D, -this.motionZ);
				}
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			f4 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

			//I know this is empty, but if you remove it, an arrow fired straight up won't flip over when gravity takes over
			for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f4) * (180D / Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
				;
			}
			while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
				this.prevRotationPitch += 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
				this.prevRotationYaw -= 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
				this.prevRotationYaw += 360.0F;
			}

			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			float f3 = 0.99F;
			f1 = 0.05F;

			if (this.isInWater()) {
				for (int l = 0; l < 4; ++l) {
					f4 = 0.25F;
					this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f4, this.posY - this.motionY * f4, this.posZ - this.motionZ * f4, this.motionX, this.motionY, this.motionZ);
				}

				f3 = 0.8F;
			}

			if (this.isWet()) {
				this.extinguish();
			}

			this.motionX *= f3;
			this.motionY *= f3;
			this.motionZ *= f3;
			this.motionY -= f1 * getGravityModifier();
			this.setPosition(this.posX, this.posY, this.posZ);
			this.doBlockCollisions();
		}
	}

	private float getDamageModifier(Entity target) {
		CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(getArrowStack());
		return CombatMechanics.getSpecialModifier(material, "standard", target, true);
	}

	private boolean isExplosive() {
		return getEntityData().hasKey("Explosive");
	}

	private void playHitSound() {
		if (world.isRemote || playedSound)
			return;
		float pitch = 1.0F / (this.rand.nextFloat() * 0.2F + 0.9F);
		if (this.arrowtype != null && this.arrowtype == ArrowType.BROADHEAD) {
			pitch *= 0.75F;
		}
		this.playSound(MineFantasySounds.ARROW_HIT, 0.5F, pitch);
		playedSound = true;
	}

	private void onHitEntity(Entity entityHit, float dam) {
		ItemStack arrow = getArrowStack();
		if (!arrow.isEmpty() && arrow.getItem() instanceof IArrowMFR) {
			((IArrowMFR) arrow.getItem()).onHitEntity(this, shootingEntity, entityHit, dam);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setFloat("firepower", firepower);
		nbt.setBoolean("PlayedSound", playedSound);
		nbt.setFloat("pierceChance", velocityModifier);
		nbt.setShort("xTile", (short) this.xTile);
		nbt.setShort("yTile", (short) this.yTile);
		nbt.setShort("zTile", (short) this.zTile);
		nbt.setShort("life", (short) this.ticksInGround);
		nbt.setByte("inTile", (byte) Block.getIdFromBlock(this.inBlock));
		nbt.setByte("inData", (byte) this.inData);
		nbt.setByte("shake", (byte) this.arrowShake);
		nbt.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		nbt.setByte("pickup", (byte) this.canBePickedUp);
		nbt.setDouble("damage", this.damage);

		nbt.setString("arrowTexture", this.getCustomTex());
		nbt.setString("arrowtype", arrowtype.name);
		nbt.setFloat("FirePower", power);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		firepower = nbt.getFloat("firepower");
		playedSound = nbt.getBoolean("PlayedSound");
		velocityModifier = nbt.getFloat("pierceChance");
		this.xTile = nbt.getShort("xTile");
		this.yTile = nbt.getShort("yTile");
		this.zTile = nbt.getShort("zTile");
		this.ticksInGround = nbt.getShort("life");
		this.inBlock = Block.getBlockById(nbt.getByte("inTile") & 255);
		this.inData = nbt.getByte("inData") & 255;
		this.arrowShake = nbt.getByte("shake") & 255;
		this.inGround = nbt.getByte("inGround") == 1;

		if (nbt.hasKey("damage", 99)) {
			this.damage = nbt.getDouble("damage");
		}

		if (nbt.hasKey("pickup", 99)) {
			this.canBePickedUp = nbt.getByte("pickup");
		} else if (nbt.hasKey("player", 99)) {
			this.canBePickedUp = nbt.getBoolean("player") ? 1 : 0;
		}

		if (nbt.hasKey("arrowTexture")) {
			updateTex(nbt.getString("arrowTexture"));
		}
		if (nbt.hasKey("arrowtype")) {
			ArrowType arrow = ArrowType.arrowMap.get(nbt.getString("arrowtype"));
			if (arrow != null) {
				arrowtype = arrow;
			}
		}
		power = nbt.getFloat("FirePower");
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void onCollideWithPlayer(EntityPlayer player) {
		if (!this.world.isRemote && this.inGround && this.arrowShake <= 0) {
			boolean canPickUp = this.canBePickedUp == 1
					|| this.canBePickedUp == 2 && player.capabilities.isCreativeMode;

			if (this.canBePickedUp == 1 && !player.inventory.addItemStackToInventory(getPickedUpItem())) {
				canPickUp = false;
			}

			if (canPickUp) {
				this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F,
						((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				player.onItemPickup(this, 1);
				this.setDead();
			}
		}
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
	public double getDamage() {
		return this.damage;
	}

	@Override
	public void setDamage(double dam) {
		this.damage = dam;
	}

	/**
	 * Sets the amount of knockback the arrow applies when it hits a mob.
	 */
	@Override
	public void setKnockbackStrength(int str) {
		this.knockbackStrength = str;
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind it.
	 */
	@Override
	public boolean getIsCritical() {
		byte b0 = this.dataManager.get(CRITICAL);
		return (b0 & 1) != 0;
	}

	/**
	 * The old critical method called on full charge, I use my own
	 */
	@Override
	public void setIsCritical(boolean flag) {

	}

	public float getHitDamage() {
		float dam = 2.0F;
		ItemStack arrowStack = getArrowStack();
		if (!arrowStack.isEmpty()) {
			if (arrowStack.getItem() instanceof IArrowMFR) {
				dam = ((IArrowMFR) arrowStack.getItem()).getDamageModifier(arrowStack);
			}
		}
		if (getEntityData().hasKey("MF_Bow_Damage")) {
			dam *= getEntityData().getFloat("MF_Bow_Damage");
		}
		return dam;
	}

	public float getGravityModifier() {
		ItemStack arrowStack = getArrowStack();
		if (!arrowStack.isEmpty()) {
			if (arrowStack.getItem() instanceof IArrowMFR) {
				return ((IArrowMFR) arrowStack.getItem()).getGravityModifier(arrowStack);
			}
		}
		return 1.0F;
	}

	public float getBreakChance() {
		ItemStack arrowStack = getArrowStack();
		if (!arrowStack.isEmpty()) {
			if (arrowStack.getItem() instanceof IArrowMFR) {
				return ((IArrowMFR) arrowStack.getItem()).getBreakChance(this, arrowStack);
			}
		}
		return 1.0F;
	}

	@SideOnly(Side.CLIENT)
	public String getTexture() {
		return getCustomTex();
	}

	public ItemStack getPickedUpItem() {
		return getArrowStack();
	}

	@Override
	protected ItemStack getArrowStack() {
		if (getEntityData().hasKey("MF_ArrowItem")) {
			NBTTagCompound heldArrow = getEntityData().getCompoundTag("MF_ArrowItem");
			return new ItemStack(heldArrow);
		}
		return new ItemStack(Items.ARROW);
	}

	/**
	 * Saves the arrow to the NBT
	 *
	 * @param arrow the arrow ItemStack
	 */
	private void setArrowStack(ItemStack arrow) {
		NBTTagCompound arrowNBT = new NBTTagCompound();
		arrow.writeToNBT(arrowNBT);
		getEntityData().setTag("MF_ArrowItem", arrowNBT);
	}

	private void updateTex(String tex) {
		dataManager.set(TEXTURE_DW, tex);
	}

	private String getCustomTex() {
		try {
			return dataManager.get(TEXTURE_DW);
		}
		catch (Exception e) {
			MineFantasyReforged.LOG.warn("Arrow Failed To Load Texture");
			return "steel_arrow";
		}
	}

	@Override
	public boolean canBePickedUp() {
		if (isMagicArrow()) {
			return false;
		}
		if (didArrowBreak()) {
			breakArrow();
			return false;
		}
		return canBePickedUp == 1;
	}

	private void breakArrow() {
		getEntityData().setBoolean("hasBroken_MFArrow", true);
	}

	private boolean didArrowBreak() {
		if (isExplosive()) {
			return false;
		}
		float c = rand.nextFloat();
		float br = getBreakChance() * ConfigWeapon.arrowBreakMod;
		return c < br;
	}

	@Override
	public ItemStack getDroppedItem() {
		return getArrowStack();
	}

	@Override
	public float[] getDamageRatio(Object... implement) {
		if (isExplosive()) {
			return new float[] {0, 1, 0};
		}
		if (arrowtype != null) {
			return arrowtype.ratio;
		}
		return new float[] {0, 0, 1};
	}

	public void modifyVelocity(float velocity) {
		this.velocityModifier = velocity;
		motionX *= velocity;
		motionY *= velocity;
		motionZ *= velocity;
	}

	@Override
	public float getPenetrationLevel(Object implement) {
		return arrowtype == ArrowType.BROADHEAD ? -0.5F : 0F;
	}

	public void setPower(float f) {
		power = f;
	}

	public EntityArrowMFR setBombStats(String powder, String filling) {
		getEntityData().setString("powder", powder);
		getEntityData().setString("filling", filling);
		getEntityData().setBoolean("Explosive", true);
		return this;
	}

	public void explode() {
		world.playSound(null, new BlockPos(posX, posY, posZ), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.3F, 10F - 5F);
		world.createExplosion(this, posX, posY, posZ, 0, false);
		if (!this.world.isRemote) {
			double area = getRangeOfBlast() * 2D;
			AxisAlignedBB entityBoundBox = this.getEntityBoundingBox().expand(area, area / 2, area);
			List<Entity> entitiesWithinAABB = this.world.getEntitiesWithinAABB(EntityLivingBase.class, entityBoundBox);

			if (entitiesWithinAABB != null && !entitiesWithinAABB.isEmpty()) {

				for (Entity entityHit : entitiesWithinAABB) {
					double distanceToEntity = this.getDistance(entityHit);

					double radius = getRangeOfBlast();
					if (distanceToEntity < radius) {
						float dam = getExplosionDamage();

						if (distanceToEntity > radius / 2) {
							double sc = distanceToEntity - (radius / 2);
							if (sc < 0)
								sc = 0;
							if (sc > (radius / 2))
								sc = (radius / 2);
							dam *= (sc / (radius / 2));
						}
						if (!(entityHit instanceof EntityItem)) {
							DamageSource source = causeBombDamage(this, shootingEntity != null ? shootingEntity : this);
							source.setExplosion();
							if (getFilling().equals("fire")) {
								source.setFireDamage();
							}
							entityHit.attackEntityFrom(source, dam);
						}
					}
				}
			}
			this.setDead();
		}

		String filling = getFilling();
		if (!filling.equals("basic")) {
			for (int a = 0; a < 16; a++) {
				float range = 0.6F;
				EntityShrapnel shrapnel = new EntityShrapnel(world, posX, posY + 0.5D, posZ,
						(rand.nextDouble() - 0.5) * range, (rand.nextDouble() - 0.5) * range,
						(rand.nextDouble() - 0.5) * range);

				if (filling.equals("fire")) {
					shrapnel.setFire(10);
				}
				world.spawnEntity(shrapnel);
			}
		}
	}

	private String getFilling() {
		return getEntityData().getString("filling");
	}

	private String getPowder() {
		return getEntityData().getString("powder");
	}

	private EnumFillingType getBlast() {
		return EnumFillingType.getType(getFilling());
	}

	private EnumPowderType getPowderType() {
		return EnumPowderType.getType(getPowder());
	}

	private double getRangeOfBlast() {
		return getBlast().range * getPowderType().rangeModifier * 0.5F;
	}

	private int getExplosionDamage() {
		return (int) (getBlast().damage * getPowderType().damageModifier * 0.5F);
	}

	public boolean isMagicArrow() {
		CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(getArrowStack());
		return material != CustomMaterial.NONE && material.type.equalsIgnoreCase("magic");
	}
}