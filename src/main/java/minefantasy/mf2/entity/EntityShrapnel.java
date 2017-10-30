package minefantasy.mf2.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.weapon.IDamageType;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class EntityShrapnel extends Entity implements IDamageType {
    public EntityLivingBase shootingEntity;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;
    private int field_145795_e = -1;
    private int field_145793_f = -1;
    private int field_145794_g = -1;
    private Block field_145796_h;
    private boolean inGround;
    private int ticksAlive;
    private int ticksInAir;

    public EntityShrapnel(World world) {
        super(world);
        this.setSize(0.5F, 0.5F);
    }

    public EntityShrapnel(World world, double x, double y, double z, double xv, double yv, double zv) {
        super(world);
        this.setSize(1.0F, 1.0F);
        this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
        this.setPosition(x, y, z);
        double d6 = MathHelper.sqrt_double(xv * xv + yv * yv + zv * zv);
        this.accelerationX = xv / d6 * 0.1D;
        this.accelerationY = yv / d6 * 0.1D;
        this.accelerationZ = zv / d6 * 0.1D;
    }

    public EntityShrapnel(World world, EntityLivingBase shooter, double x, double y, double z) {
        super(world);
        this.shootingEntity = shooter;
        this.setSize(1.0F, 1.0F);
        this.setLocationAndAngles(shooter.posX, shooter.posY, shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        this.motionX = this.motionY = this.motionZ = 0.0D;
        x += this.rand.nextGaussian() * 0.4D;
        y += this.rand.nextGaussian() * 0.4D;
        z += this.rand.nextGaussian() * 0.4D;
        double d3 = MathHelper.sqrt_double(x * x + y * y + z * z);
        this.accelerationX = x / d3 * 0.1D;
        this.accelerationY = y / d3 * 0.1D;
        this.accelerationZ = z / d3 * 0.1D;
    }

    @Override
    protected void entityInit() {
    }

    /**
     * Checks if the entity is in range to render by using the past in distance and
     * comparing it to its average edge length * 64 * renderDistanceWeight Args:
     * distance
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double dist) {
        double d1 = this.boundingBox.getAverageEdgeLength() * 4.0D;
        d1 *= 64.0D;
        return dist < d1 * d1;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        if (!this.worldObj.isRemote && (this.shootingEntity != null && this.shootingEntity.isDead
                || !this.worldObj.blockExists((int) this.posX, (int) this.posY, (int) this.posZ))) {
            this.setDead();
        } else {
            super.onUpdate();

            if (this.inGround) {
                this.setDead();
                this.inGround = false;
                this.motionX *= this.rand.nextFloat() * 0.2F;
                this.motionY *= this.rand.nextFloat() * 0.2F;
                this.motionZ *= this.rand.nextFloat() * 0.2F;
                this.ticksAlive = 0;
                this.ticksInAir = 0;
                return;
            } else {
                ++this.ticksInAir;
                if (ticksInAir >= 10)
                    setDead();
            }

            Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            Vec3 vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY,
                    this.posZ + this.motionZ);
            MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
            vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY,
                    this.posZ + this.motionZ);

            if (movingobjectposition != null) {
                vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord,
                        movingobjectposition.hitVec.zCoord);
            }

            Entity entity = null;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
                    this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;

            for (int i = 0; i < list.size(); ++i) {
                Entity entity1 = (Entity) list.get(i);

                if (entity1.canBeCollidedWith()
                        && (!entity1.isEntityEqual(this.shootingEntity) || this.ticksInAir >= 25)) {
                    float f = 0.3F;
                    AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f, f, f);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

                    if (movingobjectposition1 != null) {
                        double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

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

            if (movingobjectposition != null) {
                this.onImpact(movingobjectposition);
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) + 90.0F;

            for (this.rotationPitch = (float) (Math.atan2(f1, this.motionY) * 180.0D / Math.PI)
                    - 90.0F; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
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
            float f2 = this.getMotionFactor();

            if (this.isInWater()) {
                for (int j = 0; j < 4; ++j) {
                    float f3 = 0.25F;
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * f3, this.posY - this.motionY * f3,
                            this.posZ - this.motionZ * f3, this.motionX, this.motionY, this.motionZ);
                }

                f2 = 0.8F;
            }

            this.motionX += this.accelerationX;
            this.motionY += this.accelerationY;
            this.motionZ += this.accelerationZ;
            this.motionX *= f2;
            this.motionY *= f2;
            this.motionZ *= f2;
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
            this.setPosition(this.posX, this.posY, this.posZ);
        }
    }

    /**
     * Return the motion factor for this projectile. The factor is multiplied by the
     * original motion.
     */
    protected float getMotionFactor() {
        return 0.95F;
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition hitPos) {
        if (isBurning() && hitPos.entityHit != null) {
            hitPos.entityHit.setFire(1);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        p_70014_1_.setShort("xTile", (short) this.field_145795_e);
        p_70014_1_.setShort("yTile", (short) this.field_145793_f);
        p_70014_1_.setShort("zTile", (short) this.field_145794_g);
        p_70014_1_.setByte("inTile", (byte) Block.getIdFromBlock(this.field_145796_h));
        p_70014_1_.setByte("inGround", (byte) (this.inGround ? 1 : 0));
        p_70014_1_.setTag("direction",
                this.newDoubleNBTList(new double[]{this.motionX, this.motionY, this.motionZ}));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        this.field_145795_e = p_70037_1_.getShort("xTile");
        this.field_145793_f = p_70037_1_.getShort("yTile");
        this.field_145794_g = p_70037_1_.getShort("zTile");
        this.field_145796_h = Block.getBlockById(p_70037_1_.getByte("inTile") & 255);
        this.inGround = p_70037_1_.getByte("inGround") == 1;

        if (p_70037_1_.hasKey("direction", 9)) {
            NBTTagList nbttaglist = p_70037_1_.getTagList("direction", 6);
            this.motionX = nbttaglist.func_150309_d(0);
            this.motionY = nbttaglist.func_150309_d(1);
            this.motionZ = nbttaglist.func_150309_d(2);
        } else {
            this.setDead();
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this
     * Entity.
     */
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public float getCollisionBorderSize() {
        return 1.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0F;
    }

    /**
     * Gets how bright this entity is.
     */
    @Override
    public float getBrightness(float p_70013_1_) {
        return 1.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_) {
        return 15728880;
    }

    @SideOnly(Side.CLIENT)
    public String getTexture() {
        return isBurning() ? "fireshrapnel" : "shrapnel";
    }

    @Override
    public float[] getDamageRatio(Object... implement) {
        return isBurning() ? new float[]{0, 1, 0} : new float[]{0, 1, 1};// 50/50 blunt and piercing, fire bombs
        // are full blunt
    }

    @Override
    public float getPenetrationLevel(Object implement) {
        return 0;
    }
}