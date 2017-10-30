package minefantasy.mf2.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.archery.IArrowMF;
import minefantasy.mf2.api.archery.IArrowRetrieve;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.weapon.IDamageType;
import minefantasy.mf2.config.ConfigExperiment;
import minefantasy.mf2.config.ConfigWeapon;
import minefantasy.mf2.item.archery.ArrowType;
import minefantasy.mf2.item.gadget.EnumExplosiveType;
import minefantasy.mf2.item.gadget.EnumPowderType;
import minefantasy.mf2.mechanics.CombatMechanics;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntityArrowMF extends EntityArrow implements IProjectile, IDamageType, IArrowRetrieve {
    private final int texture_dw = 18;
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

    public EntityArrowMF(World world) {
        super(world);
        this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
    }

    public EntityArrowMF(World world, double x, double y, double z) {
        super(world);
        this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
        this.setPosition(x, y, z);
        this.yOffset = 0.0F;
    }

    /**
     * This is for shooting an arrow from a shooting entity to a target(Like
     * skeletons and such)
     */
    public EntityArrowMF(World world, EntityLivingBase shooter, EntityLivingBase target, float accuracy, float power) {
        super(world);
        this.renderDistanceWeight = 10.0D;
        this.shootingEntity = shooter;

        this.firepower = power;
        if (shooter instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }

        this.posY = shooter.posY + shooter.getEyeHeight() - 0.10000000149011612D;
        double d0 = target.posX - shooter.posX;
        double d1 = target.boundingBox.minY + target.height / 3.0F - this.posY;
        double d2 = target.posZ - shooter.posZ;
        double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);

        if (d3 >= 1.0E-7D) {
            float f2 = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            float f3 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
            double d4 = d0 / d3;
            double d5 = d2 / d3;
            this.setLocationAndAngles(shooter.posX + d4, this.posY, shooter.posZ + d5, f2, f3);
            this.yOffset = 0.0F;
            float f4 = (float) d3 * 0.2F;
            this.setThrowableHeading(d0, d1 + f4, d2, accuracy, power);
        }
    }

    /**
     * This method is for firing an arrow from an entity (Mostly player shooting)
     */
    public EntityArrowMF(World world, EntityLivingBase shooter, float power) {
        this(world, shooter, 1.0F, power);
    }

    public EntityArrowMF(World world, EntityLivingBase shooter, float spread, float power) {
        super(world);
        this.firepower = power / 2F;
        this.renderDistanceWeight = 10.0D;
        this.shootingEntity = shooter;

        if (shooter instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }

        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ,
                shooter.rotationYaw, shooter.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.posY -= 0.10000000149011612D;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
                * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
                * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, power * 1.5F, spread);
    }

    public static DamageSource causeBombDamage(Entity bomb, Entity user) {
        return (new EntityDamageSourceBomb(bomb, user)).setProjectile();
    }

    public EntityArrowMF setDeisgn(ArrowType type) {
        this.arrowtype = type;
        return this;
    }

    /**
     * Implement this with the constructor
     */
    public EntityArrowMF setArrow(ItemStack arrow) {
        ItemStack arrowStack = arrow.copy();
        arrowStack.stackSize = 1;
        setArrowStack(arrowStack);
        return this;
    }

    public EntityArrowMF setArrowTex(String tex) {
        updateTex(tex);
        return this;
    }

    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));// Critical
        this.dataWatcher.addObject(texture_dw, "steel_arrow");
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
     * direction.
     */
    @Override
    public void setThrowableHeading(double x, double y, double z, float power, float spread) {
        float f2 = MathHelper.sqrt_double(x * x + y * y + z * z);
        x /= f2;
        y /= f2;
        z /= f2;
        x += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * spread;
        y += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * spread;
        z += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * spread;
        x *= power;
        y *= power;
        z *= power;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f3 = MathHelper.sqrt_double(x * x + z * z);
        this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, f3) * 180.0D / Math.PI);
        this.ticksInGround = 0;
    }

    /**
     * Sets the position and rotation. Only difference from the other one is no
     * bounding on the rotation. Args: posX, posY, posZ, yaw, pitch
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int i) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    /**
     * Sets the position and rotation. Only difference from the other one is no
     * bounding on the rotation. Args: posX, posY, posZ, yaw, pitch
     */
    public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch, int i) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void setVelocity(double xv, double yv, double zv) {
        this.motionX = xv;
        this.motionY = yv;
        this.motionZ = zv;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(xv * xv + zv * zv);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(xv, zv) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(yv, f) * 180.0D / Math.PI);
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

        if (ConfigExperiment.dynamicArrows && worldObj.isRemote) {
            return;
        }
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D
                    / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, f) * 180.0D / Math.PI);
        }

        Block block = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);

        if (block.getMaterial() != Material.air) {
            block.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
            AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile,
                    this.zTile);

            if (axisalignedbb != null
                    && axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))) {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0) {
            --this.arrowShake;
        }

        if (this.inGround) {
            int j = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);

            if (block == this.inBlock && j == this.inData) {
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
            Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            Vec3 vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY,
                    this.posZ + this.motionZ);
            MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec31, vec3, false, true, false);
            vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY,
                    this.posZ + this.motionZ);

            if (movingobjectposition != null) {
                vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord,
                        movingobjectposition.hitVec.zCoord);
            }

            Entity entity = null;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
                    this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            int i;
            float f1;

            for (i = 0; i < list.size(); ++i) {
                Entity entity1 = (Entity) list.get(i);

                if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5)) {
                    f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f1, f1, f1);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

                    if (movingobjectposition1 != null) {
                        double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

                        if (d1 < d0 || d0 == 0.0D) {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null) {
                movingobjectposition = new MovingObjectPosition(entity);
            }

            if (movingobjectposition != null && movingobjectposition.entityHit != null
                    && movingobjectposition.entityHit instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;

                if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer
                        && !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer)) {
                    movingobjectposition = null;
                }
            }

            float f2;
            float f4;

            if (movingobjectposition != null) {
                if (isExplosive()) {
                    explode();
                }
                if (movingobjectposition.entityHit != null) {
                    f2 = MathHelper.sqrt_double(
                            this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ)
                            / velocityModifier;

                    float dam = Math.max(0.1F, this.getHitDamage() * firepower);// (getDamageModifier()*power) / 10F *
                    // (float)k;

                    dam *= getDamageModifier(movingobjectposition.entityHit);

                    if (this.getIsCritical()) {
                        dam *= (rand.nextFloat() * 0.5F) + 1.0F;
                    }

                    DamageSource damagesource = null;

                    if (this.shootingEntity == null) {
                        damagesource = DamageSource.causeArrowDamage(this, this);
                    } else {
                        damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
                    }

                    if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman)) {
                        movingobjectposition.entityHit.setFire(5);
                    }
                    if (isExplosive()) {
                        dam = this.getExplosionDamage();
                        damagesource.setExplosion();
                        if (this.getFilling() == 2) {
                            damagesource.setFireDamage();
                        }
                    }
                    if (movingobjectposition.entityHit.attackEntityFrom(damagesource, dam)) {
                        onHitEntity(movingobjectposition.entityHit, dam);
                        if (movingobjectposition.entityHit instanceof EntityLivingBase) {
                            EntityLivingBase entitylivingbase = (EntityLivingBase) movingobjectposition.entityHit;

                            if (!this.worldObj.isRemote) {
                                entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
                            }

                            if (this.knockbackStrength > 0) {
                                f4 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

                                if (f4 > 0.0F) {
                                    movingobjectposition.entityHit.addVelocity(
                                            this.motionX * this.knockbackStrength * 0.6000000238418579D / f4, 0.1D,
                                            this.motionZ * this.knockbackStrength * 0.6000000238418579D / f4);
                                }
                            }

                            if (this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase) {
                                EnchantmentHelper.func_151384_a(entitylivingbase, this.shootingEntity);
                                EnchantmentHelper.func_151385_b((EntityLivingBase) this.shootingEntity,
                                        entitylivingbase);
                            }

                            if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity
                                    && movingobjectposition.entityHit instanceof EntityPlayer
                                    && this.shootingEntity instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) this.shootingEntity).playerNetServerHandler
                                        .sendPacket(new S2BPacketChangeGameState(6, 0.0F));
                            }
                        }

                        playHitSound();

                        if (!(movingobjectposition.entityHit instanceof EntityEnderman)) {
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
                    this.xTile = movingobjectposition.blockX;
                    this.yTile = movingobjectposition.blockY;
                    this.zTile = movingobjectposition.blockZ;
                    this.inBlock = block;
                    this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                    this.motionX = ((float) (movingobjectposition.hitVec.xCoord - this.posX));
                    this.motionY = ((float) (movingobjectposition.hitVec.yCoord - this.posY));
                    this.motionZ = ((float) (movingobjectposition.hitVec.zCoord - this.posZ));
                    f2 = MathHelper.sqrt_double(
                            this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / f2 * 0.05000000074505806D;
                    this.posY -= this.motionY / f2 * 0.05000000074505806D;
                    this.posZ -= this.motionZ / f2 * 0.05000000074505806D;
                    playHitSound();
                    this.inGround = true;
                    this.arrowShake = 7;
                    this.setIsCritical(false);

                    if (this.inBlock.getMaterial() != Material.air) {
                        this.inBlock.onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
                    }
                    if (isMagicArrow()) {
                        setDead();
                    }
                    if (ConfigWeapon.breakArrowsGround && didArrowBreak()) {
                        breakArrow();
                    }
                }
                if (getEntityData().hasKey("hasBroken_MFArrow")) {
                    worldObj.playSoundAtEntity(this, "random.break", 1.0F, 1.0F);
                    setDead();
                }
            }

            if (isMagicArrow()) {
                for (i = 0; i < 4; ++i) {
                    this.worldObj.spawnParticle("reddust", this.posX + this.motionX * i / 4.0D,
                            this.posY + this.motionY * i / 4.0D, this.posZ + this.motionZ * i / 4.0D, -this.motionX,
                            -this.motionY + 0.2D, -this.motionZ);
                }
            }

            if (this.getIsCritical()) {
                for (i = 0; i < 4; ++i) {
                    this.worldObj.spawnParticle("crit", this.posX + this.motionX * i / 4.0D,
                            this.posY + this.motionY * i / 4.0D, this.posZ + this.motionZ * i / 4.0D, -this.motionX,
                            -this.motionY + 0.2D, -this.motionZ);
                }
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            for (this.rotationPitch = (float) (Math.atan2(this.motionY, f2) * 180.0D / Math.PI); this.rotationPitch
                    - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
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
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * f4, this.posY - this.motionY * f4,
                            this.posZ - this.motionZ * f4, this.motionX, this.motionY, this.motionZ);
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
            this.func_145775_I();
        }
    }

    private float getDamageModifier(Entity target) {
        CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(getArrowStack());
        return CombatMechanics.getSpecialModifier(material, "standard", target, true);
    }

    private float getModifiedVelocity() {
        String s = "MF_Bow_Velocity";
        if (getEntityData().hasKey(s)) {
            return getEntityData().getFloat(s);
        }
        return 1.0F;
    }

    private boolean isExplosive() {
        return getEntityData().hasKey("Explosive");
    }

    private void playHitSound() {
        if (worldObj.isRemote || playedSound)
            return;
        float pitch = 1.0F / (this.rand.nextFloat() * 0.2F + 0.9F);
        if (this.arrowtype != null && this.arrowtype == ArrowType.BROADHEAD) {
            pitch *= 0.75F;
        }
        this.playSound("minefantasy2:weapon.arrowHit", 0.5F, pitch);
        playedSound = true;
    }

    private void onHitEntity(Entity entityHit, float dam) {
        ItemStack arrow = getArrowStack();
        if (arrow != null && arrow.getItem() instanceof IArrowMF) {
            ((IArrowMF) arrow.getItem()).onHitEntity(this, shootingEntity, entityHit, dam);
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
        if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0) {
            boolean canPickUp = this.canBePickedUp == 1
                    || this.canBePickedUp == 2 && player.capabilities.isCreativeMode;

            if (this.canBePickedUp == 1 && !player.inventory.addItemStackToInventory(getPickedUpItem())) {
                canPickUp = false;
            }

            if (canPickUp) {
                this.playSound("random.pop", 0.2F,
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
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0F;
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
     * If returns false, the item will not inflict any damage against entities.
     */
    @Override
    public boolean canAttackWithItem() {
        return false;
    }

    /**
     * [New Method] Whether the arrow has a stream of critical hit particles flying
     * behind it. Called by chance
     */
    public void setCritical(boolean flag) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (flag) {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 1)));
        } else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -2)));
        }
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    @Override
    public boolean getIsCritical() {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
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
        if (arrowStack != null) {
            if (arrowStack.getItem() instanceof IArrowMF) {
                dam = ((IArrowMF) arrowStack.getItem()).getDamageModifier(arrowStack);
            }
        }
        if (getEntityData().hasKey("MF_Bow_Damage")) {
            dam *= getEntityData().getFloat("MF_Bow_Damage");
        }
        return dam;
    }

    public float getGravityModifier() {
        ItemStack arrowStack = getArrowStack();
        if (arrowStack != null) {
            if (arrowStack.getItem() instanceof IArrowMF) {
                return ((IArrowMF) arrowStack.getItem()).getGravityModifier(arrowStack);
            }
        }
        return 1.0F;
    }

    public float getBreakChance() {
        ItemStack arrowStack = getArrowStack();
        if (arrowStack != null) {
            if (arrowStack.getItem() instanceof IArrowMF) {
                return ((IArrowMF) arrowStack.getItem()).getBreakChance(this, arrowStack);
            }
        }
        return 1.0F;
    }

    @SideOnly(Side.CLIENT)
    public String getTexture() {
        return "textures/projectile/" + getCustomTex();
    }

    public ItemStack getPickedUpItem() {
        return getArrowStack();
    }

    private ItemStack getArrowStack() {
        if (getEntityData().hasKey("MF_ArrowItem")) {
            NBTTagCompound heldArrow = getEntityData().getCompoundTag("MF_ArrowItem");
            return ItemStack.loadItemStackFromNBT(heldArrow);
        }
        return new ItemStack(Items.arrow);
    }

    /**
     * Saves the arrow to the NBT
     *
     * @param arrow
     */
    private void setArrowStack(ItemStack arrow) {
        NBTTagCompound arrowNBT = new NBTTagCompound();
        arrow.writeToNBT(arrowNBT);
        getEntityData().setTag("MF_ArrowItem", arrowNBT);
    }

    private void updateTex(String tex) {
        dataWatcher.updateObject(texture_dw, tex);
    }

    private String getCustomTex() {
        try {
            return dataWatcher.getWatchableObjectString(texture_dw);
        } catch (Exception e) {
            MFLogUtil.logWarn("Arrow Failed To Load Texture");
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
            return new float[]{0, 1, 0};
        }
        if (arrowtype != null) {
            return arrowtype.ratio;
        }
        return new float[]{0, 0, 1};
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

    public EntityArrowMF setBombStats(int powder, int filling) {
        getEntityData().setInteger("powder", powder);
        getEntityData().setInteger("filling", filling);
        getEntityData().setBoolean("Explosive", true);
        return this;
    }

    public void explode() {
        worldObj.playSoundAtEntity(this, "random.explode", 0.3F, 10F - 5F);
        worldObj.createExplosion(this, posX, posY, posZ, 0, false);
        if (!this.worldObj.isRemote) {
            double area = getRangeOfBlast() * 2D;
            AxisAlignedBB var3 = this.boundingBox.expand(area, area / 2, area);
            List var4 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var3);

            if (var4 != null && !var4.isEmpty()) {
                Iterator splashDamage = var4.iterator();

                while (splashDamage.hasNext()) {
                    Entity entityHit = (Entity) splashDamage.next();
                    double distanceToEntity = this.getDistanceToEntity(entityHit);

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
                            if (getFilling() == 2) {
                                source.setFireDamage();
                            }
                            entityHit.attackEntityFrom(source, dam);
                        }
                    }
                }
            }
            this.setDead();
        }

        int filling = getFilling();
        if (filling > 0) {
            for (int a = 0; a < 16; a++) {
                float range = 0.6F;
                EntityShrapnel shrapnel = new EntityShrapnel(worldObj, posX, posY + 0.5D, posZ,
                        (rand.nextDouble() - 0.5) * range, (rand.nextDouble() - 0.5) * range,
                        (rand.nextDouble() - 0.5) * range);

                if (filling == 2) {
                    shrapnel.setFire(10);
                }
                worldObj.spawnEntityInWorld(shrapnel);
            }
        }
    }

    private int getFilling() {
        return getEntityData().getInteger("filling");
    }

    private int getPowder() {
        return getEntityData().getInteger("powder");
    }

    private EnumExplosiveType getBlast() {
        return EnumExplosiveType.getType((byte) getFilling());
    }

    private EnumPowderType getPowderType() {
        return EnumPowderType.getType((byte) getPowder());
    }

    private double getRangeOfBlast() {
        return getBlast().range * getPowderType().rangeModifier * 0.5F;
    }

    private int getExplosionDamage() {
        return (int) (getBlast().damage * getPowderType().damageModifier * 0.5F);
    }

    public boolean isMagicArrow() {
        CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(getArrowStack());
        return material != null && material.type.equalsIgnoreCase("magic");
    }
}