package minefantasy.mf2.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.helpers.ArmourCalculator;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.ToolListMF;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class EntityParachute extends Entity {
    /**
     * true if no player in parachute
     */
    private boolean isParachuteEmpty;
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
        this.setPosition(x, y + this.yOffset, z);
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
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(1));
        this.dataWatcher.addObject(19, new Float(0.0F));
    }

    /**
     * Returns a boundingBox used to collide the entity with other entities and
     * blocks. This enables the entity to be pushable on contact, like parachutes or
     * minecarts.
     */
    public AxisAlignedBB getCollisionBox(Entity entity) {
        return entity.boundingBox;
    }

    /**
     * returns the bounding box for this entity
     */
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
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
        if (this.isEntityInvulnerable()) {
            return false;
        } else if (!this.worldObj.isRemote && !this.isDead) {
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + dam * 10.0F);
            this.setBeenAttacked();
            boolean flag = src.getEntity() instanceof EntityPlayer
                    && ((EntityPlayer) src.getEntity()).capabilities.isCreativeMode;

            if (flag || this.getDamageTaken() > 20.0F) {
                if (this.riddenByEntity != null) {
                    this.riddenByEntity.mountEntity(this);
                }

                if (!flag) {
                    this.func_145778_a(ToolListMF.parachute, 1, 0.0F);
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
        if (this.riddenByEntity != null) {
            riddenByEntity.fallDistance = 0;
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
            double d1 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i + 0) / b0 - 0.125D;
            double d3 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i + 1) / b0 - 0.125D;
            AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(this.boundingBox.minX, d1, this.boundingBox.minZ,
                    this.boundingBox.maxX, d3, this.boundingBox.maxZ);

            if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
                d0 += 1.0D / b0;
            }
        }

        double d10 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        double d2;
        double d4;
        double d11;
        double d12;

        if (this.worldObj.isRemote && this.isParachuteEmpty) {
            if (this.parachutePosRotationIncrements > 0) {
                d2 = this.posX + (this.parachuteX - this.posX) / this.parachutePosRotationIncrements;
                d4 = this.posY + (this.parachuteY - this.posY) / this.parachutePosRotationIncrements;
                d11 = this.posZ + (this.parachuteZ - this.posZ) / this.parachutePosRotationIncrements;
                d12 = MathHelper.wrapAngleTo180_double(this.parachuteYaw - this.rotationYaw);
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

            if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
                EntityLivingBase entitylivingbase = (EntityLivingBase) this.riddenByEntity;
                float f = this.riddenByEntity.rotationYaw + -entitylivingbase.moveStrafing * 90.0F;
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

            this.moveEntity(this.motionX, this.motionY, this.motionZ);

            if ((this.isCollidedHorizontally && d10 > 0D) || isTooHeavy()) {
                if (!this.worldObj.isRemote && !this.isDead) {
                    this.setDead();

                    for (l = 0; l < 3; ++l) {
                        this.func_145778_a(Item.getItemFromBlock(Blocks.wool), 1, 0.0F);
                    }

                    for (l = 0; l < 2; ++l) {
                        this.func_145778_a(ComponentListMF.leather_strip, 1, 0.0F);
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

            double d7 = MathHelper.wrapAngleTo180_double(d4 - this.rotationYaw);

            if (d7 > 20.0D) {
                d7 = 20.0D;
            }

            if (d7 < -20.0D) {
                d7 = -20.0D;
            }

            this.rotationYaw = (float) (this.rotationYaw + d7);
            this.setRotation(this.rotationYaw, this.rotationPitch);

            if (!this.worldObj.isRemote) {
                List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
                        this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

                if (list != null && !list.isEmpty()) {
                    for (int k1 = 0; k1 < list.size(); ++k1) {
                        Entity entity = (Entity) list.get(k1);

                        if (entity != this.riddenByEntity && entity.canBePushed()
                                && entity instanceof EntityParachute) {
                            entity.applyEntityCollision(this);
                        }
                    }
                }

                if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                    this.riddenByEntity = null;
                }
            }
        }
    }

    private boolean isTooHeavy() {
        if (riddenByEntity != null && riddenByEntity instanceof EntityLivingBase) {
            return ArmourCalculator.getTotalWeightOfWorn((EntityLivingBase) riddenByEntity, false) > 100F;
        }
        return false;
    }

    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            double d0 = Math.cos(this.rotationYaw * Math.PI / 180.0D) * 0.4D;
            double d1 = Math.sin(this.rotationYaw * Math.PI / 180.0D) * 0.4D;
            this.riddenByEntity.setPosition(this.posX + d0,
                    this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + d1);
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
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.posY);
        int k = MathHelper.floor_double(this.posZ);

        if (hitGound) {
            if (this.fallDistance > 0.0F || onGround) {
                if (!this.worldObj.isRemote && !this.isDead) {
                    this.setDead();
                    this.func_145778_a(ToolListMF.parachute, 1, 0.0F);
                }

                this.fallDistance = 0.0F;
            }
        } else if (this.worldObj.getBlock(i, j - 1, k).getMaterial() != Material.water && dist < 0.0D) {
            this.fallDistance = (float) (this.fallDistance - dist);
        }
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamageTaken() {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }

    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamageTaken(float dam) {
        this.dataWatcher.updateObject(19, Float.valueOf(dam));
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setTimeSinceHit(int time) {
        this.dataWatcher.updateObject(17, Integer.valueOf(time));
    }

    /**
     * Gets the forward direction of the entity.
     */
    public int getForwardDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    /**
     * Sets the forward direction of the entity.
     */
    public void setForwardDirection(int dir) {
        this.dataWatcher.updateObject(18, Integer.valueOf(dir));
    }

    /**
     * true if no player in parachute
     */
    @SideOnly(Side.CLIENT)
    public void setIsParachuteEmpty(boolean flag) {
        this.isParachuteEmpty = flag;
    }
}