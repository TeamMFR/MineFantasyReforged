package minefantasy.mf2.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.item.gadget.EnumCasingType;
import minefantasy.mf2.item.gadget.EnumExplosiveType;
import minefantasy.mf2.item.gadget.EnumFuseType;
import minefantasy.mf2.item.gadget.EnumPowderType;
import minefantasy.mf2.mechanics.CombatMechanics;
import minefantasy.mf2.util.BukkitUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntityBomb extends Entity {
    private final int typeId = 2;
    /**
     * How long the fuse is
     */
    public int fuse;
    private EntityLivingBase thrower;

    public EntityBomb(World world) {
        super(world);
        this.fuse = getFuseTime();
        this.preventEntitySpawning = true;
        this.setSize(0.5F, 0.5F);
        this.yOffset = this.height / 2.0F;
    }

    public EntityBomb(World world, EntityLivingBase thrower) {
        this(world);
        this.thrower = thrower;

        this.setLocationAndAngles(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ,
                thrower.rotationYaw, thrower.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.posY -= 0.10000000149011612D;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);

        float force = this.getThrownForce() + (CombatMechanics.getStrengthEnhancement(thrower) / 4);

        float f = 0.4F;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
                * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * f;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
                * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * f;
        this.motionY = -MathHelper.sin((this.rotationPitch + this.getThrownOffset()) / 180.0F * (float) Math.PI) * f;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, force, 1.0F);

        this.prevPosX = posX;
        this.prevPosY = posY;
        this.prevPosZ = posZ;
    }

    public static DamageSource causeBombDamage(Entity bomb, Entity user) {
        return (new EntityDamageSourceBomb(bomb, user)).setProjectile();
    }

    private int getFuseTime() {
        return (this.fuse = getFuseType().time) + (isSticky() ? 20 : 0);
    }

    public void setThrowableHeading(double x, double y, double z, float offset, float force) {
        float f2 = MathHelper.sqrt_double(x * x + y * y + z * z);
        x /= f2;
        y /= f2;
        z /= f2;
        x += this.rand.nextGaussian() * 0.007499999832361937D * force;
        y += this.rand.nextGaussian() * 0.007499999832361937D * force;
        z += this.rand.nextGaussian() * 0.007499999832361937D * force;
        x *= offset;
        y *= offset;
        z *= offset;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f3 = MathHelper.sqrt_double(x * x + z * z);
        this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, f3) * 180.0D / Math.PI);
    }

    protected float getThrownOffset() {
        return 0.0F;
    }

    protected float getThrownForce() {
        return 1.5F;
    }

    @Override
    protected void entityInit() {
        dataWatcher.addObject(typeId, (byte) 0);
        dataWatcher.addObject(typeId + 1, (byte) 0);
        dataWatcher.addObject(typeId + 2, (byte) 0);
        dataWatcher.addObject(typeId + 3, (byte) 0);
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk
     * on. used for spiders and wolves to prevent them from trampling crops
     */
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this
     * Entity.
     */
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= (0.03999999910593033D * getGravityModifier());
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.onGround) {
            double d = 0.75D;
            this.motionX *= d;
            this.motionZ *= d;
            this.motionY *= -0.99D;
        }
        List collide = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox);
        if (!collide.isEmpty() && !(thrower != null && collide.contains(thrower))) {
            if (isSticky()) {
                Object object = collide.get(0);
                if (object instanceof Entity && object != this) {
                    if (this.ridingEntity == null && ((Entity) object).riddenByEntity == null
                            && canStick((Entity) object)) {
                        this.mountEntity((Entity) object);
                        this.fuse = getFuseTime();
                    }
                }
            }
            this.motionX = motionZ = 0;
        }

        if (this.fuse-- <= 0) {
            this.setDead();

            if (!this.worldObj.isRemote) {
                this.explode();
            }
        } else {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.125D, this.posZ, 0.0D, 0.0D, 0.0D);
            this.worldObj.spawnParticle("flame", this.posX, this.posY + 0.125D, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!worldObj.isRemote && this.ridingEntity != null && ridingEntity instanceof EntityLivingBase) {
            if (!(ridingEntity instanceof EntityChicken)) {
                CombatMechanics.panic((EntityLivingBase) ridingEntity, 1.0F, 30);
            }
        }
        if (isStuckInBlock()) {
            motionX = motionY = motionZ = 0;
        }
    }

    private boolean canStick(Entity entity) {
        return !(entity instanceof EntityCogwork);
    }

    @Override
    public void mountEntity(Entity object) {
        super.mountEntity(object);

        if (object instanceof EntityChicken) {
            EntityChicken chook = (EntityChicken) object;
            Entity target = worldObj.findNearestEntityWithinAABB(IMob.class, chook.boundingBox.expand(64, 8, 64),
                    chook);
            if (target != null) {
                if (target instanceof EntityLivingBase) {
                    chook.setCustomNameTag("Suicide Chook!");
                    fuse = getFuseTime() * 2;
                    chook.getNavigator().clearPathEntity();
                    chook.setAttackTarget((EntityLivingBase) target);
                    chook.targetTasks.addTask(1, new EntityAIAttackOnCollide(chook, IMob.class, 1.25D, true));
                    chook.setAIMoveSpeed(0.5F);
                }
            }
        }
    }

    private boolean isStuckInBlock() {
        return false;// isSticky() && isAABBInSolid(boundingBox.expand(0.5D, 0.5D, 0.5D));
    }

    private boolean isSticky() {
        return getEntityData().hasKey("stickyBomb");
    }

    private double getGravityModifier() {
        return isStuckInBlock() ? 0F : getCase().weightModifier;
    }

    private void explode2() {
        float power = 3.5F;
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, power, false);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setByte("Fuse", (byte) this.fuse);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        this.fuse = nbt.getByte("Fuse");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.25F;
    }

    /**
     * returns null or the entityliving it was placed or ignited by
     */
    public EntityLivingBase getTntPlacedBy() {
        return this.thrower;
    }

    @Override
    public void applyEntityCollision(Entity entity) {
        if (!(thrower != null && thrower == entity)) {
            this.motionX = 0;
            this.motionZ = 0;
        }
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

                    if (MineFantasyII.isBukkitServer()
                            && BukkitUtils.cantDamage(thrower != null ? thrower : this, entityHit)) {
                        continue;
                    }

                    double distanceToEntity = this.getDistanceToEntity(entityHit);
                    double radius = getRangeOfBlast();
                    if (distanceToEntity < radius) {
                        float dam = getDamage();

                        if (getCasing() != 2 && distanceToEntity > radius / 2) {
                            double sc = distanceToEntity - (radius / 2);
                            if (sc < 0)
                                sc = 0;
                            if (sc > (radius / 2))
                                sc = (radius / 2);
                            dam *= (sc / (radius / 2));
                        }
                        if (!(entityHit instanceof EntityItem)) {

                            DamageSource source = causeBombDamage(this, thrower != null ? thrower : this);
                            source.setExplosion();
                            if (getFilling() == 2) {
                                source.setFireDamage();
                            }
                            if (ridingEntity != null && entityHit == ridingEntity) {
                                dam *= 1.5F;
                            }
                            if (entityHit.attackEntityFrom(source, dam)) {
                                applyEffects(entityHit);
                            }
                        }
                    }
                }
            }
            this.setDead();
        }

        int t = getFilling();
        if (t > 0) {
            for (int a = 0; a < 16; a++) {
                float range = 0.6F;
                EntityShrapnel shrapnel = new EntityShrapnel(worldObj, posX, posY + 0.5D, posZ,
                        (rand.nextDouble() - 0.5) * range, (rand.nextDouble() - 0.5F) * range,
                        (rand.nextDouble() - 0.5) * range);

                if (t == 2) {
                    shrapnel.setFire(10);
                }
                worldObj.spawnEntityInWorld(shrapnel);
            }
        }
    }

    private void applyEffects(Entity hit) {
        if (getFilling() == 2) {
            hit.setFire(5);
        }
        if (hit instanceof EntityLivingBase) {
            EntityLivingBase live = (EntityLivingBase) hit;
        }
    }

    private double getRangeOfBlast() {
        return getBlast().range * getCase().rangeModifier * getPowderType().rangeModifier;
    }

    private int getDamage() {
        return (int) (getBlast().damage * getCase().damageModifier * getPowderType().damageModifier);
    }

    public boolean canEntityBeSeen(Entity entity) {
        return this.worldObj.rayTraceBlocks(
                Vec3.createVectorHelper(this.posX, this.posY + this.getEyeHeight(), this.posZ),
                Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ)) == null;
    }

    public byte getFilling() {
        return dataWatcher.getWatchableObjectByte(typeId);
    }

    public byte getCasing() {
        return dataWatcher.getWatchableObjectByte(typeId + 1);
    }

    public byte getFuse() {
        return dataWatcher.getWatchableObjectByte(typeId + 2);
    }

    public byte getPowder() {
        return dataWatcher.getWatchableObjectByte(typeId + 3);
    }

    public EntityBomb setType(byte fill, byte casing, byte fuse, byte powder) {
        dataWatcher.updateObject(typeId, fill);
        dataWatcher.updateObject(typeId + 1, casing);
        dataWatcher.updateObject(typeId + 2, fuse);
        dataWatcher.updateObject(typeId + 3, powder);

        this.fuse = getFuseType().time;

        return this;
    }

    private EnumExplosiveType getBlast() {
        return EnumExplosiveType.getType(getFilling());
    }

    private EnumCasingType getCase() {
        return EnumCasingType.getType(getCasing());
    }

    private EnumFuseType getFuseType() {
        return EnumFuseType.getType(getFuse());
    }

    private EnumPowderType getPowderType() {
        return EnumPowderType.getType(getPowder());
    }

    public boolean isAABBInSolid(AxisAlignedBB field) {
        int i = MathHelper.floor_double(field.minX);
        int j = MathHelper.floor_double(field.maxX + 1.0D);
        int k = MathHelper.floor_double(field.minY);
        int l = MathHelper.floor_double(field.maxY + 1.0D);
        int i1 = MathHelper.floor_double(field.minZ);
        int j1 = MathHelper.floor_double(field.maxZ + 1.0D);

        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = k; l1 < l; ++l1) {
                for (int i2 = i1; i2 < j1; ++i2) {
                    Block block = worldObj.getBlock(k1, l1, i2);

                    if (block.getMaterial().isSolid()) {
                        int j2 = worldObj.getBlockMetadata(k1, l1, i2);
                        double d0 = l1 + 1;

                        if (j2 < 8) {
                            d0 = l1 + 1 - j2 / 8.0D;
                        }

                        if (d0 >= field.minY) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}