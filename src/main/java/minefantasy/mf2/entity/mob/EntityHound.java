package minefantasy.mf2.entity.mob;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.entity.mob.ai.AI_CommonSprint;
import minefantasy.mf2.entity.mob.ai.hound.AI_HoundBeg;
import minefantasy.mf2.entity.mob.ai.hound.AI_HoundFollow;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityHound extends EntityTameable {
    // Animations
    public float jawMove;
    public int eatAnimation;
    private float field_70926_e;
    private float field_70924_f;
    /**
     * true is the wolf is wet else false
     */
    private boolean isShaking;
    private boolean field_70928_h;
    /**
     * This time increases while wolf is shaking and emitting water particles.
     */
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;

    public EntityHound(World world) {
        super(world);
        this.setSize(0.6F, 0.8F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(1, new AI_CommonSprint(this, 16.0F));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(5, new AI_HoundFollow(this, 1.0D, 6.0F, 2.0F));
        this.tasks.addTask(6, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new AI_HoundBeg(this, 8.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntitySheep.class, 200, false));

        this.setTamed(false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);

        if (this.isTamed()) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        } else {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
        }
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
    public boolean isAIEnabled() {
        return true;
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    @Override
    public void setAttackTarget(EntityLivingBase target) {
        super.setAttackTarget(target);

        if (target == null) {
            this.setAngry(false);
        } else if (!this.isTamed()) {
            this.setAngry(true);
        }
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    @Override
    protected void updateAITick() {
        this.dataWatcher.updateObject(18, Float.valueOf(this.getHealth()));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, new Float(this.getHealth()));
        this.dataWatcher.addObject(19, new Byte((byte) 0));
        this.dataWatcher.addObject(20, new Byte((byte) BlockColored.func_150032_b(1)));
    }

    @Override
    protected void func_145780_a(int x, int y, int z, Block surface) {
        this.playSound("mob.wolf.step", 0.15F, 1.0F);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("Angry", this.isAngry());
        nbt.setByte("CollarColor", (byte) this.getCollarColor());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setAngry(nbt.getBoolean("Angry"));

        if (nbt.hasKey("CollarColor", 99)) {
            this.setCollarColor(nbt.getByte("CollarColor"));
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected String getLivingSound() {
        return this.isAngry() ? "mob.wolf.growl"
                : (this.rand.nextInt(3) == 0
                ? (this.isTamed() && this.dataWatcher.getWatchableObjectFloat(18) < 10.0F ? "mob.wolf.whine"
                : "mob.wolf.panting")
                : "mob.wolf.bark");
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound() {
        return "mob.wolf.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound() {
        return "mob.wolf.death";
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected Item getDropItem() {
        return Item.getItemById(-1);
    }

    /**
     * Called frequently so the entity can update its state every tick as required.
     * For example, zombies and skeletons use this to react to sunlight and start to
     * burn.
     */
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.worldObj.isRemote && this.isShaking && !this.field_70928_h && !this.hasPath() && this.onGround) {
            this.field_70928_h = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
            this.worldObj.setEntityState(this, (byte) 8);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.field_70924_f = this.field_70926_e;

        if (this.func_70922_bv()) {
            this.field_70926_e += (1.0F - this.field_70926_e) * 0.4F;
        } else {
            this.field_70926_e += (0.0F - this.field_70926_e) * 0.4F;
        }

        if (this.func_70922_bv()) {
            this.numTicksToChaseTarget = 10;
        }

        if (this.isWet()) {
            this.isShaking = true;
            this.field_70928_h = false;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        } else if ((this.isShaking || this.field_70928_h) && this.field_70928_h) {
            if (this.timeWolfIsShaking == 0.0F) {
                this.playSound("mob.wolf.shake", this.getSoundVolume(),
                        (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }

            this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05F;

            if (this.prevTimeWolfIsShaking >= 2.0F) {
                this.isShaking = false;
                this.field_70928_h = false;
                this.prevTimeWolfIsShaking = 0.0F;
                this.timeWolfIsShaking = 0.0F;
            }

            if (this.timeWolfIsShaking > 0.4F) {
                float f = (float) this.boundingBox.minY;
                int i = (int) (MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float) Math.PI) * 7.0F);

                for (int j = 0; j < i; ++j) {
                    float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    this.worldObj.spawnParticle("splash", this.posX + f1, f + 0.8F, this.posZ + f2, this.motionX,
                            this.motionY, this.motionZ);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean getWolfShaking() {
        return this.isShaking;
    }

    /**
     * Used when calculating the amount of shading to apply while the wolf is
     * shaking.
     */
    @SideOnly(Side.CLIENT)
    public float getShadingWhileShaking(float p_70915_1_) {
        return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70915_1_)
                / 2.0F * 0.25F;
    }

    @SideOnly(Side.CLIENT)
    public float getShakeAngle(float p_70923_1_, float p_70923_2_) {
        float f2 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70923_1_
                + p_70923_2_) / 1.8F;

        if (f2 < 0.0F) {
            f2 = 0.0F;
        } else if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        return MathHelper.sin(f2 * (float) Math.PI) * MathHelper.sin(f2 * (float) Math.PI * 11.0F) * 0.15F
                * (float) Math.PI;
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.8F;
    }

    @SideOnly(Side.CLIENT)
    public float getInterestedAngle(float p_70917_1_) {
        return (this.field_70924_f + (this.field_70926_e - this.field_70924_f) * p_70917_1_) * 0.15F * (float) Math.PI;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the
     * faceEntity method. This is only currently use in wolves.
     */
    @Override
    public int getVerticalFaceSpeed() {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float dam) {
        if (this.isEntityInvulnerable()) {
            return false;
        } else {
            Entity entity = source.getEntity();
            this.aiSit.setSitting(false);

            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
                dam = (dam + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, dam);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity target) {
        int i = this.isTamed() ? 4 : 2;
        return target.attackEntityFrom(DamageSource.causeMobDamage(this), i);
    }

    @Override
    public void setTamed(boolean flag) {
        super.setTamed(flag);

        if (flag) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        } else {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
        }
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets
     * into the saddle on a pig.
     */
    @Override
    public boolean interact(EntityPlayer user) {
        ItemStack itemstack = user.inventory.getCurrentItem();

        if (this.isTamed()) {
            if (itemstack != null) {
                if (itemstack.getItem() instanceof ItemFood) {
                    ItemFood itemfood = (ItemFood) itemstack.getItem();

                    if (itemfood.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectFloat(18) < 20.0F) {
                        if (!user.capabilities.isCreativeMode) {
                            --itemstack.stackSize;
                        }

                        this.heal(itemfood.func_150905_g(itemstack));

                        if (itemstack.stackSize <= 0) {
                            user.inventory.setInventorySlotContents(user.inventory.currentItem, (ItemStack) null);
                        }

                        return true;
                    }
                } else if (itemstack.getItem() == Items.dye) {
                    int i = BlockColored.func_150032_b(itemstack.getItemDamage());

                    if (i != this.getCollarColor()) {
                        this.setCollarColor(i);

                        if (!user.capabilities.isCreativeMode && --itemstack.stackSize <= 0) {
                            user.inventory.setInventorySlotContents(user.inventory.currentItem, (ItemStack) null);
                        }

                        return true;
                    }
                }
            }

            if (this.func_152114_e(user) && !this.worldObj.isRemote && !this.isBreedingItem(itemstack)) {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.setPathToEntity((PathEntity) null);
                this.setTarget((Entity) null);
                this.setAttackTarget((EntityLivingBase) null);
            }
        } else if (itemstack != null && itemstack.getItem() == Items.bone && !this.isAngry()) {
            if (!user.capabilities.isCreativeMode) {
                --itemstack.stackSize;
            }

            if (itemstack.stackSize <= 0) {
                user.inventory.setInventorySlotContents(user.inventory.currentItem, (ItemStack) null);
            }

            if (!this.worldObj.isRemote) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamed(true);
                    this.setPathToEntity((PathEntity) null);
                    this.setAttackTarget((EntityLivingBase) null);
                    this.aiSit.setSitting(true);
                    this.setHealth(20.0F);
                    this.func_152115_b(user.getUniqueID().toString());
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte) 7);
                } else {
                    this.playTameEffect(false);
                    this.worldObj.setEntityState(this, (byte) 6);
                }
            }

            return true;
        }

        return super.interact(user);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleHealthUpdate(byte id) {
        if (id == 8) {
            this.field_70928_h = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        } else {
            super.handleHealthUpdate(id);
        }
    }

    @SideOnly(Side.CLIENT)
    public float getTailRotation() {
        return this.isAngry() ? 1.5393804F
                : (this.isTamed()
                ? (0.55F - (20.0F - this.dataWatcher.getWatchableObjectFloat(18)) * 0.02F) * (float) Math.PI
                : ((float) Math.PI / 5F));
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it
     * (wheat, carrots or seeds depending on the animal type)
     */
    @Override
    public boolean isBreedingItem(ItemStack item) {
        return item == null ? false
                : (!(item.getItem() instanceof ItemFood) ? false : ((ItemFood) item.getItem()).isWolfsFavoriteMeat());
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }

    /**
     * Determines whether this wolf is angry or not.
     */
    public boolean isAngry() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    /**
     * Sets whether this wolf is angry or not.
     */
    public void setAngry(boolean flag) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (flag) {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 2)));
        } else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -3)));
        }
    }

    /**
     * Return this wolf's collar color.
     */
    public int getCollarColor() {
        return this.dataWatcher.getWatchableObjectByte(20) & 15;
    }

    /**
     * Set this wolf's collar color.
     */
    public void setCollarColor(int colour) {
        this.dataWatcher.updateObject(20, Byte.valueOf((byte) (colour & 15)));
    }

    @Override
    public EntityHound createChild(EntityAgeable parent) {
        EntityHound entitywolf = new EntityHound(this.worldObj);
        String s = this.func_152113_b();

        if (s != null && s.trim().length() > 0) {
            entitywolf.func_152115_b(s);
            entitywolf.setTamed(true);
        }

        return entitywolf;
    }

    public void func_70918_i(boolean flag) {
        if (flag) {
            this.dataWatcher.updateObject(19, Byte.valueOf((byte) 1));
        } else {
            this.dataWatcher.updateObject(19, Byte.valueOf((byte) 0));
        }
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    @Override
    public boolean canMateWith(EntityAnimal mate) {
        if (mate == this) {
            return false;
        } else if (!this.isTamed()) {
            return false;
        } else if (!(mate instanceof EntityHound)) {
            return false;
        } else {
            EntityHound entitywolf = (EntityHound) mate;
            return !entitywolf.isTamed() ? false
                    : (entitywolf.isSitting() ? false : this.isInLove() && entitywolf.isInLove());
        }
    }

    public boolean func_70922_bv() {
        return this.dataWatcher.getWatchableObjectByte(19) == 1;
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    @Override
    protected boolean canDespawn() {
        return !this.isTamed() && this.ticksExisted > 2400;
    }

    @Override
    public boolean func_142018_a(EntityLivingBase target, EntityLivingBase owner) {
        if (!(target instanceof EntityCreeper) && !(target instanceof EntityGhast)) {
            if (target instanceof EntityHound) {
                EntityHound entitywolf = (EntityHound) target;

                if (entitywolf.isTamed() && entitywolf.getOwner() == owner) {
                    return false;
                }
            }

            return target instanceof EntityPlayer && owner instanceof EntityPlayer
                    && !((EntityPlayer) owner).canAttackPlayer((EntityPlayer) target) ? false
                    : !(target instanceof EntityHorse) || !((EntityHorse) target).isTame();
        } else {
            return false;
        }
    }

    public String getTexture() {
        return "hound";
    }

    public boolean shouldFollowOwner() {
        return true;
    }
}