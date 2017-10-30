package minefantasy.mf2.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.armour.IGasProtector;
import minefantasy.mf2.api.helpers.PowerArmour;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class EntitySmoke extends Entity {
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

    public EntitySmoke(World world) {
        super(world);
        this.setSize(0.2F, 0.2F);
    }

    public EntitySmoke(World world, double x, double y, double z, double xv, double yv, double zv) {
        super(world);
        noClip = false;
        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
        this.setPosition(x, y, z);
        double d6 = MathHelper.sqrt_double(xv * xv + yv * yv + zv * zv);
        this.accelerationX = xv / d6 * 0.1D;
        this.accelerationY = yv / d6 * 0.1D;
        this.accelerationZ = zv / d6 * 0.1D;
    }

    public static boolean canPoison(Entity hit, Random rand) {
        if (!(hit instanceof EntityLivingBase))
            return false;

        ItemStack helmet = ((EntityLivingBase) hit).getEquipmentInSlot(4);
        if (helmet != null && helmet.getItem() instanceof IGasProtector) {
            float prot = ((IGasProtector) helmet.getItem()).getGasProtection(helmet);
            if (prot >= 100F || rand.nextFloat() * 100 < prot) {
                return false;
            }
        }
        if (PowerArmour.isWearingCogwork((EntityLivingBase) hit)) {
            return false;
        }
        return ((EntityLivingBase) hit).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean canAttackWithItem() {
        return false;
    }

    protected void entityInit() {
    }

    /**
     * Checks if the entity is in range to render by using the past in distance and
     * comparing it to its average edge length * 64 * renderDistanceWeight Args:
     * distance
     */
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double dist) {
        double d1 = this.boundingBox.getAverageEdgeLength() * 4.0D;
        d1 *= 64.0D;
        return dist < d1 * d1;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        if (!this.worldObj.isRemote && (this.shootingEntity != null && this.shootingEntity.isDead
                || !this.worldObj.blockExists((int) this.posX, (int) this.posY, (int) this.posZ))) {
            this.setDead();
        } else {
            super.onUpdate();

            if (this.inGround) {
                if (this.worldObj.getBlock(this.field_145795_e, this.field_145793_f,
                        this.field_145794_g) == this.field_145796_h) {
                    ++this.ticksAlive;

                    if (this.ticksAlive == 600) {
                        this.setDead();
                    }

                    return;
                }

                this.inGround = false;
                this.motionX *= this.rand.nextFloat() * 0.2F;
                this.motionY *= this.rand.nextFloat() * 0.2F;
                this.motionZ *= this.rand.nextFloat() * 0.2F;
                this.ticksAlive = 0;
                this.ticksInAir = 0;
            } else {
                ++this.ticksInAir;
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
                f2 = 0.8F;
            }

            double floating = 0.01F;
            this.motionX += this.accelerationX;
            this.motionY += this.accelerationY + floating;
            this.motionZ += this.accelerationZ;

            double slowdownRate = 0.5F;
            this.accelerationX *= slowdownRate;
            this.accelerationY *= slowdownRate;
            this.accelerationZ *= slowdownRate;

            this.motionX *= f2;
            this.motionY *= f2;
            this.motionZ *= f2;
            this.worldObj.spawnParticle("largesmoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
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
    protected void onImpact(MovingObjectPosition pos) {
        if (!this.worldObj.isRemote) {
            if (pos.entityHit != null) {
                if (pos.entityHit instanceof EntityLivingBase && !worldObj.isRemote) {
                    EntityLivingBase hit = (EntityLivingBase) pos.entityHit;

                    if (canPoison(hit, rand)) {
                        hit.addPotionEffect(new PotionEffect(Potion.hunger.id, 20, 0));
                        if (rand.nextInt(20) == 0) {
                            if (hit.getActivePotionEffect(Potion.blindness) != null) {
                                hit.addPotionEffect(new PotionEffect(Potion.wither.id, 200, 0));
                            } else if (hit.getActivePotionEffect(Potion.confusion) != null) {
                                hit.addPotionEffect(new PotionEffect(Potion.blindness.id, 200, 0));
                            } else {
                                hit.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
                            }
                        }
                    }
                }
            }
            if (pos.typeOfHit == MovingObjectType.BLOCK) {
                setDead();
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setShort("xTile", (short) this.field_145795_e);
        nbt.setShort("yTile", (short) this.field_145793_f);
        nbt.setShort("zTile", (short) this.field_145794_g);
        nbt.setByte("inTile", (byte) Block.getIdFromBlock(this.field_145796_h));
        nbt.setByte("inGround", (byte) (this.inGround ? 1 : 0));
        nbt.setTag("direction", this.newDoubleNBTList(new double[]{this.motionX, this.motionY, this.motionZ}));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbt) {
        this.field_145795_e = nbt.getShort("xTile");
        this.field_145793_f = nbt.getShort("yTile");
        this.field_145794_g = nbt.getShort("zTile");
        this.field_145796_h = Block.getBlockById(nbt.getByte("inTile") & 255);
        this.inGround = nbt.getByte("inGround") == 1;

        if (nbt.hasKey("direction", 9)) {
            NBTTagList nbttaglist = nbt.getTagList("direction", 6);
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
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 1.0F;
    }

    @Override
    public void setFire(int i) {
    }
}