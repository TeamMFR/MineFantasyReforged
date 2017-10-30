package minefantasy.mf2.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.item.gadget.EnumCasingType;
import minefantasy.mf2.item.gadget.EnumExplosiveType;
import minefantasy.mf2.item.gadget.EnumFuseType;
import minefantasy.mf2.item.gadget.EnumPowderType;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.util.BukkitUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntityMine extends Entity {
    private static DamageSource mineDmg = new DamageSource("mine").setExplosion();
    private static DamageSource mineFireDmg = new DamageSource("mine").setExplosion().setFireDamage();
    private final int typeId = 2;
    /**
     * How long the fuse is
     */
    public int fuse;
    private EntityLivingBase thrower;

    public EntityMine(World world) {
        super(world);
        this.preventEntitySpawning = true;
        this.setSize(0.5F, 0.25F);
        this.yOffset = this.height / 2.0F;
    }

    public EntityMine(World world, EntityLivingBase thrower) {
        this(world);
        this.thrower = thrower;
        this.fuse = 40;

        this.setLocationAndAngles(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ,
                thrower.rotationYaw, thrower.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.posY -= 0.10000000149011612D;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);

        float f = 0.4F;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
                * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * f;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
                * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * f;
        this.motionY = -MathHelper.sin((this.rotationPitch + this.getThrownOffset()) / 180.0F * (float) Math.PI) * f;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.getThrownForce(), 1.0F);

        this.prevPosX = posX;
        this.prevPosY = posY;
        this.prevPosZ = posZ;
    }

    @Override
    public boolean interactFirst(EntityPlayer user) {
        if (user.isSneaking()) {
            // DISARM
            setDead();
            user.swingItem();
            if (!worldObj.isRemote) {
                ItemStack item = ToolListMF.mine_custom.createMine(getCasing(), getFilling(), getFuse(), getPowder(),
                        1);
                if (!user.inventory.addItemStackToInventory(item)) {
                    this.entityDropItem(item, 0.0F);
                }
            }
        }
        return super.interactFirst(user);
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
        return 0.5F;
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
        return !this.isDead;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= (0.03999999910593033D * getCase().weightModifier);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.onGround) {
            this.motionX = 0;
            this.motionZ = 0;
            this.motionY *= -0.99D;
        }

        if (fuse > 0) {
            --fuse;
        }
        double radius = Math.max(1.0D, getRangeOfBlast() - 1.5D);

        if (ticksExisted % 5 == 0) {
            List<EntityLivingBase> list = worldObj.getEntitiesWithinAABB(EntityLivingBase.class,
                    boundingBox.expand(radius * 2, radius, radius * 2));
            if (fuse == 0 && !list.isEmpty()) {
                boolean detonate = false;
                Iterator i = list.iterator();
                while (i.hasNext()) {
                    EntityLivingBase e = (EntityLivingBase) i.next();
                    if (e.getDistanceToEntity(this) < radius) {
                        detonate = true;
                        break;
                    }
                }

                if (detonate) {
                    worldObj.playSoundEffect(posX, posY, posZ, "game.tnt.primed", 1.0F, 0.75F);
                    --fuse;
                }
            }
        }
        if (fuse < 0) {
            fuse--;

            if (fuse < -10) {
                this.setDead();

                if (!this.worldObj.isRemote) {
                    this.explode();
                }
            }
        }
    }

    private void explode2() {
        float power = 5.0F;
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

    public void explode() {
        worldObj.playSoundAtEntity(this, "random.explode", 0.3F, 10F - 5F);
        worldObj.createExplosion(this, posX, posY, posZ, 0, false);
        if (!this.worldObj.isRemote) {
            double area = getRangeOfBlast() * 2D * getPowderType().rangeModifier;
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

                    double radius = getRangeOfBlast() * getPowderType().rangeModifier;
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
                            DamageSource source = mineDmg;
                            if (getFilling() == 2) {
                                source = mineFireDmg;
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
                        (rand.nextDouble() - 0.5) * (range / 2), (rand.nextDouble()) * range,
                        (rand.nextDouble() - 0.5) * (range / 2));

                if (t == 2) {
                    shrapnel.setFire(10);
                }
                worldObj.spawnEntityInWorld(shrapnel);
            }
        }
    }

    private void applyEffects(Entity hit) {
        if (getFilling() == 2) {
            hit.setFire(8);
        }
        if (hit instanceof EntityLivingBase) {
            EntityLivingBase live = (EntityLivingBase) hit;
        }
    }

    private double getRangeOfBlast() {
        return getBlast().range * getCase().rangeModifier;
    }

    private float getDamage() {
        return getBlast().damage * getCase().damageModifier * getPowderType().damageModifier;
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

    public EntityMine setType(byte fill, byte casing, byte fuse, byte powder) {
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
}