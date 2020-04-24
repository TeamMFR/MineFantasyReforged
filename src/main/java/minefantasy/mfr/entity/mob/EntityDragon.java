package minefantasy.mfr.entity.mob;

import minefantasy.mfr.api.armour.IArmourPenetrationMob;
import minefantasy.mfr.api.armour.IArmouredEntity;
import minefantasy.mfr.api.helpers.ArmourCalculator;
import minefantasy.mfr.api.helpers.PowerArmour;
import minefantasy.mfr.config.ConfigMobs;
import minefantasy.mfr.entity.EntityDragonBreath;
import minefantasy.mfr.entity.Shockwave;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.SoundsMFR;
import minefantasy.mfr.init.ToolListMFR;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class EntityDragon extends EntityFlyingMF implements IMob, IArmouredEntity, IArmourPenetrationMob {
    private static final DataParameter<Byte> TERRESTRIAL = EntityDataManager.<Byte>createKey(EntityDragon.class, DataSerializers.BYTE);
    private static final DataParameter<Float> JAW_MOVEMENT = EntityDataManager.<Float>createKey(EntityDragon.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> NECK_ANGLE = EntityDataManager.<Float>createKey(EntityDragon.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> DISENGAGE_TIME = EntityDataManager.<Integer>createKey(EntityDragon.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> BREED = EntityDataManager.<Integer>createKey(EntityDragon.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TIER = EntityDataManager.<Integer>createKey(EntityDragon.class, DataSerializers.VARINT);
    public static int interestTimeSeconds = 90;
    public static float heartChance = 1.0F;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    public int fireBreathCooldown;
    private Entity targetedEntity;
    private int fireBreathTick;
    private Entity lastEnemy;
    private int interestTime;
    @SideOnly(Side.CLIENT)
    private int wingFlap, wingTick;
    private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);

    public EntityDragon(World world) {
        super(world);
        this.setSize(4.0F, 3.0F);
        this.isImmuneToFire = true;
        this.experienceValue = 50;
        stepHeight = 1.25F;
    }

    @Override
    protected void fall(float distance) {
        if (isTerrestrial()) {
            float power = Math.min(distance / 2F, 3F) * getScale();
            createShockwave(posX, posY, posZ, power / 2F, ConfigMobs.dragonGriefGeneral);
        }
    }

    public void setDragon(int tier) {
        setTier(tier);
        setBreed(DragonBreed.getRandomDragon(this, tier));
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getType().health);
        setHealth(getMaxHealth());
        this.setSize(3.0F * getScale(), 2.0F * getScale());
        stepHeight = 1.25F + (tier * 0.25F);
        this.experienceValue = 50 * (tier + 1);
    }

    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance instance, IEntityLivingData data) {
        setDragon(getRandomTier());
        return super.onInitialSpawn(instance, data);
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

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(TERRESTRIAL, Byte.valueOf((byte)0));
        this.dataManager.register(JAW_MOVEMENT, Float.valueOf(0F));
        this.dataManager.register(NECK_ANGLE, Float.valueOf(0F));
        this.dataManager.register(DISENGAGE_TIME, Integer.valueOf(0));
        this.dataManager.register(BREED, Integer.valueOf(0));
        this.dataManager.register(TIER, Integer.valueOf(rand.nextInt(5)));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ConfigMobs.dragonHP);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.5D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (fireBreathTick > 0)
            --fireBreathTick;
        if (fireBreathCooldown > 0)
            --fireBreathCooldown;

        if (!isTerrestrial()) {
            this.fallDistance = 0;
        }
        if (!world.isRemote) {
            if (isInWater()) {
                if (rand.nextFloat() < 0.8F) {
                    getJumpHelper().setJumping();
                }
            }

            int disengageTime = getDisengageTime();
            if (disengageTime > 0) {
                if (targetedEntity != null)
                    targetedEntity = null;
                --disengageTime;
            }
            if (disengageTime == 1 && lastEnemy != null && this.canAttackEntity(lastEnemy)) {
                setTarget(lastEnemy);
                lastEnemy = null;
            }
            setDisengageTime(disengageTime);
            if (rand.nextInt(this.getDisengageChance()) == 0 && disengageTime <= 0) {
                disengage(100);
            }

            if (onGround)// Ground begin walk
            {
                setTerrestrial(true);
            }
            if (targetedEntity != null && targetedEntity.posY < (posY - 5D)
                    && this.getDistance(targetedEntity.posX, posY, targetedEntity.posZ) < 2D)// Slam to enemy
            {
                setTerrestrial(true);
            }
            if (rand.nextInt(100) == 0 && targetedEntity == null)// Idle take off
            {
                setTerrestrial(false);
                jump();
            }
            if (targetedEntity != null && targetedEntity.posY > (posY + 2D))// Combat take off
            {
                setTerrestrial(false);
                jump();
            }

            if (fireBreathTick > 0 && targetedEntity != null) {
                this.faceEntity(targetedEntity, 1F, 1F);
                breatheFire();
            }

            if (targetedEntity == null && (rand.nextInt(100) == 0) && disengageTime <= 0) {
                this.waypointX = this.posX + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
                this.waypointY = this.posY + (this.rand.nextFloat() * 1.5F - 1.0F) * 12.0F;
                this.waypointZ = this.posZ + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
            }
            if (targetedEntity == null && (rand.nextInt(100) == 0) && disengageTime > 0) {
                this.waypointX = this.posX + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
                this.waypointY = this.posY - 4D;
                this.waypointZ = this.posZ + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
            }
            if (getHealth() < getMaxHealth() && this.ticksExisted % 100 == 0 && getType().regenRate > 0) {
                heal(getType().regenRate);
                if (getHealth() > getMaxHealth()) {
                    setHealth(getMaxHealth());
                }
            }
            int time = getInterestTime();
            int maxTime = ((getHealth() > (getMaxHealth() * 0.75F)) ? time : time * 2) * 20;
            if (dimension != -1) {
                ++interestTime;
                if (interestTime > maxTime && world.canBlockSeeSky(new BlockPos(posX, posY,  posZ))
                        && posY > 128F) {
                    if (ConfigMobs.dragonMSG) {
                        List list = world.playerEntities;
                        Iterator players = list.iterator();
                        while (players.hasNext()) {
                            Object instance = players.next();
                            if (instance != null && instance instanceof EntityPlayer) {
                                if (((EntityPlayer) instance).getDistance(this) < 128D) {
                                    ((EntityPlayer) instance).sendMessage(new TextComponentString(I18n.translateToLocal("event.dragonaway.name")) {});
                                }
                            }
                        }
                    }
                    this.setDead();
                }
                if (interestTime > maxTime - 60 && world.canBlockSeeSky(new BlockPos(posX, posY,  posZ))) {
                    this.waypointY = this.posY + 4D;
                }
            }
        } else {
            float jawMove = getJawMove();
            if (jawMove > 0) {
                --jawMove;
            }
            setJawMove(jawMove);
            float neckAngle = getNeckAngle();
            if (neckAngle > 0) {
                --neckAngle;
            }
            setNeckAngle(neckAngle);

            wingTick++;
            if (wingTick == 20) {
                wingTick = 0;
                if (!isTerrestrial())
                    world.playSound(posX, posY, posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.HOSTILE, 1, 1, true);
            }
            int i = (120 / 20) * wingTick;
            wingFlap = -40 + i;
        }

        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    private int getInterestTime() {
        return interestTimeSeconds;
    }

    private void breatheFire() {
        int spread = 4;

        for (int a = 0; a < spread; a++) {
            double xAngle = this.targetedEntity.posX - this.posX;
            double yAngle = this.targetedEntity.getCollisionBoundingBox().minY + this.targetedEntity.height / 2.0F
                    - (this.posY + this.height / 2.0F);
            double zAngle = this.targetedEntity.posZ - this.posZ;
            double power = 1.0D;
            Vec3d var20 = this.getLook(1.0F);
            Entity breath = getBreath(xAngle, yAngle, zAngle);
            breath.posX = this.posX + var20.x * power;
            breath.posY = this.posY + this.height / 2.0F + 0.5D;
            breath.posZ = this.posZ + var20.z * power;
            this.world.spawnEntity(breath);
        }
    }

    private Entity getBreath(double xAngle, double yAngle, double zAngle) {
        return new EntityDragonBreath(this.world, this, xAngle, yAngle, zAngle, 1.0F).setDamage(getType().fireDamage)
                .setType(getType().rangedAttack.id);
    }

    /**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in
     * order to view its associated boss bar.
     */
    public void addTrackingPlayer(EntityPlayerMP player)
    {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    /**
     * Removes the given player from the list of players tracking this entity. See {@link Entity#addTrackingPlayer} for
     * more information on tracking.
     */
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

//    @Override
//    protected void updateEntityActionState() {
//        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
//            this.setDead();
//        }
//
//        this.despawnEntity();
//        double d0 = this.waypointX - this.posX;
//        double d1 = this.waypointY - this.posY;
//        double d2 = this.waypointZ - this.posZ;
//        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
//
//        if (d3 < 1.0D || d3 > 3600.0D) {
//            this.waypointX = this.posX + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
//            this.waypointY = this.posY + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
//            this.waypointZ = this.posZ + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
//        }
//
//        if (this.courseChangeCooldown-- <= 0) {
//            this.courseChangeCooldown += this.rand.nextInt(5) + 2;
//            d3 = MathHelper.sqrt(d3);
//            double flyspd = getFlightSpeed();
//            if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3)) {
//                this.motionX += d0 / d3 * 0.5D * flyspd;
//                this.motionY += d1 / d3 * 0.5D * flyspd;
//                this.motionZ += d2 / d3 * 0.5D * flyspd;
//            } else {
//                this.waypointX = this.posX;
//                this.waypointY = this.posY;
//                this.waypointZ = this.posZ;
//            }
//        }
//
//        if (this.targetedEntity != null && this.targetedEntity.isDead) {
//            this.targetedEntity = null;
//        }
//
//        if (this.targetedEntity == null) {
//            EntityPlayer closest = this.world.getNearestAttackablePlayer(this, 100.0D, 100.0D);
//            if (closest != null && canAttackEntity(closest)) {
//                targetedEntity = closest;
//                closest = null;
//            }
//            if (this.targetedEntity != null) {
//                this.moveToTarget();
//            }
//        }
//
//        if (this.targetedEntity != null && this.targetedEntity.isDead) {
//            this.targetedEntity = null;
//        }
//
//        if (this.targetedEntity == null) {
//            this.setEntityToAttack(EntityPlayer.class);
//        }
//        if (ConfigMobs.dragonKillNPC && this.targetedEntity == null) {
//            this.setEntityToAttack(EntityLiving.class);
//        }
//
//        double var9 = 200.0D;
//
//        if (this.targetedEntity != null && getDisengageTime() <= 0
//                && this.targetedEntity.getDistanceSq(this) < var9 * var9) {
//            if (!world.isRemote && !this.canEntityBeSeen(this.targetedEntity) && getDisengageTime() <= 0
//                    && (targetedEntity instanceof EntityPlayer ? rand.nextInt(40) == 0 : true)) {
//                disengage(200);
//                return;
//            }
//            float range = getAttackRange();
//
//            double var11 = this.targetedEntity.posX - this.posX;
//            double var13 = Objects.requireNonNull(this.targetedEntity.getCollisionBoundingBox()).minY + this.targetedEntity.height / 2.0F
//                    - (this.posY + this.height / 2.0F);
//            double var15 = this.targetedEntity.posZ - this.posZ;
//            this.renderYawOffset = this.rotationYaw = -((float) Math.atan2(var11, var15)) * 180.0F / (float) Math.PI;
//
//            boolean inRangeOfAttack = this.targetedEntity.getDistance(this) < (range * getScale())
//                    && this.canEntityBeSeen(targetedEntity);
//            boolean inRangeOfLeap = onGround && this.targetedEntity.getDistance(this) > range * 2
//                    && this.targetedEntity.getDistance(this) < range * 8;
//            boolean inRangeOfFire = this.targetedEntity.getDistance(this) > range
//                    && this.targetedEntity.getDistance(this) < range * 6 && fireBreathCooldown <= 0;
//
//            if (this.canEntityBeSeen(this.targetedEntity) && inRangeOfFire) {
//                this.fireBreathTick = getType().fireTimer;
//
//                fireBreathCooldown = 600 + getBreathCooldown() + rand.nextInt(getBreathCooldown());
//                world.playSoundAtEntity(this, "minefantasy2:mob.dragon.fire", 1, 1);
//                setJawMove(20);
//            }
//            if (this.canEntityBeSeen(this.targetedEntity) && inRangeOfLeap && rand.nextInt(100) == 0) {
//                TacticalManager.lungeEntity(this, targetedEntity, 3.0F, 1.5F);
//            }
//            if (fallDistance <= 0 && this.canEntityBeSeen(this.targetedEntity) && inRangeOfAttack) {
//                if ((targetedEntity.isSneaking() && !targetedEntity.onGround) || this.attackCounter <= 0) {
//                    if (targetedEntity.isRiding() && posY > (targetedEntity.posY)) {
//                        targetedEntity.dismountRidingEntity();
//                        targetedEntity.motionY = 1.0F;
//                        targetedEntity.motionX = this.motionX;
//                        targetedEntity.motionZ = this.motionZ;
//                        if (targetedEntity instanceof EntityLivingBase) {
//                            ((EntityLivingBase) targetedEntity).addPotionEffect(new PotionEffect(Potion.getPotionById(9), 200, 5));
//                            ((EntityLivingBase) targetedEntity).addPotionEffect(new PotionEffect(Potion.getPotionById(2), 200, 5));
//                            ((EntityLivingBase) targetedEntity).addPotionEffect(new PotionEffect(Potion.getPotionById(18), 200, 2));
//                        }
//                    }
//                    attackEntity(targetedEntity, this.getAttackStrength(targetedEntity));
//                    this.attackCounter = getAttackTime();
//                    setJawMove(40);
//                    if (getHealth() <= this.getLowHPThreshold()) {
//                        disengage(50);
//                    }
//                    world.playSoundAtEntity(this, "minefantasy2:mob.dragon.bite", 1, 1);
//                }
//            }
//            if (this.attackCounter > 0) {
//                --this.attackCounter;
//            }
//            if (this.canEntityBeSeen(this.targetedEntity) && !inRangeOfAttack) {
//                this.faceEntity(targetedEntity, 1F, 1F);
//                waypointX = targetedEntity.posX;
//                waypointY = targetedEntity.posY;
//                waypointZ = targetedEntity.posZ;
//            }
//        } else {
//            this.renderYawOffset = this.rotationYaw = -((float) Math.atan2(this.motionX, this.motionZ)) * 180.0F
//                    / (float) Math.PI;
//
//            if (this.attackCounter > 0) {
//                --this.attackCounter;
//            }
//        }
//    }

    private float getAttackRange() {
        return 6.0F;
    }

    private float getFlightSpeed() {
        return 0.8F;
    }

    @Override
    public boolean canEntityBeSeen(Entity entity) {
        return entity != null && super.canEntityBeSeen(entity);
    }

    private int getBreathCooldown() {
        return getType().coolTimer;
    }

    private int getAttackTime() {
        return getType().meleeSpeed;
    }

    /**
     * True if the ghast has an unobstructed line of travel to the waypoint.
     */
    private boolean isCourseTraversable(double x, double y, double z, double distance) {
        double d4 = (this.waypointX - this.posX) / distance;
        double d5 = (this.waypointY - this.posY) / distance;
        double d6 = (this.waypointZ - this.posZ) / distance;
        AxisAlignedBB axisalignedbb = this.getCollisionBoundingBox();

        for (int i = 1; i < distance; ++i) {
            axisalignedbb.offset(d4, d5, d6);

            if (!this.world.getCollisionBoxes(this, axisalignedbb).isEmpty()) {
                return false;
            }
            IBlockState blockState = world.getBlockState(new BlockPos(d4,  d5, d6));
            if (blockState.getMaterial().isLiquid()) {
                return false;
            }
        }

        return true;
    }

    protected void attackEntity(Entity entity, float f) {
        float damage = this.getAttackStrength(entity);

        if (rand.nextInt(3) == 0) {
            targetedEntity.motionY = 2;
            jump();
            setNeckAngle(10);
            damage = 2.0F;
        }
        world.playSound(posX, posY, posZ, SoundsMFR.DRAGON_BITE, SoundCategory.HOSTILE, 1, 1, true);
        entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
    }

    @Override
    public void playLivingSound() {
        super.playLivingSound();
        setJawMove(20);
    }

    private float getAttackStrength(Entity entity) {
        return getType().meleeDamage;
    }

    public void setEntityToAttack(Class enClass) {
        List list = world.getEntitiesWithinAABB(enClass, new AxisAlignedBB(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(getAggro(), getAggro(), getAggro()));
        while (!list.isEmpty()) {
            Entity target = (Entity) list.get(0);
            if (canAttackEntity(target)) {
                double r = getAggro();
                boolean inRange = this.getDistance(target) <= r;
                if (getDisengageTime() <= 0 && inRange) {
                    setTarget(target);
                    list.clear();
                } else {
                    list.remove(0);
                }
            } else {
                list.remove(0);
            }
        }
    }

    public void setTarget(Entity target) {
        this.targetedEntity = target;
    }

    private boolean canAttackEntity(Entity target) {
        if (this.getType().isBlind() && !this.canHearEntity(target)) {
            return false;
        }
        if (getDisengageTime() > 0) {
            return false;
        }
        if (!this.canEntityBeSeen(target))
            return false;
        if (target instanceof EntityDragon || this.isBeingRidden()) {
            return false;
        }
        if (target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) target;
            return !player.capabilities.isCreativeMode;
        }
        return canEntityBeSeen(target);
    }

    private double getAggro() {
        return 256D;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if (source.getTrueSource() != null) {
            this.interestTime = 0;
        }
        if (getDisengageTime() <= 0 && this.getHealth() < getLowHPThreshold()) {
            disengage(100);
        }
        if (source.isFireDamage())
            return false;

        if (source == DamageSource.IN_WALL)
            return false;

        if (source.getTrueSource() != null && source.getTrueSource() instanceof EntityPlayer) {
            if (getDisengageTime() <= 0 && getAttackTarget() == null || damage > 16
                    || (targetedEntity != null && !(targetedEntity instanceof EntityPlayer)))
                setTarget(source.getTrueSource());
        }

        if (source.getTrueSource() != null) {
            if (getDisengageTime() <= 0 && getAttackTarget() == null)
                setTarget(source.getTrueSource());
        }
        return super.attackEntityFrom(source, damage);
    }

    /**
     * @return The health threshold where dragons constantly retreat
     */
    private float getLowHPThreshold() {
        return getType().health / 4;
    }

    private int getDisengageChance() {
        return getType().disengageChance;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
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
        return ComponentListMFR.dragon_heart;
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
            this.dropItem(ComponentListMFR.dragon_heart, 1);
        }
    }

    private Item getLoot(int tier) {
        if (tier == 4)// Ancient
        {
            return ToolListMFR.loot_sack_rare;
        }
        return ToolListMFR.loot_sack_uc;// Any
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
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);

        nbt.setInteger("FireBreath", fireBreathTick);
        nbt.setInteger("FireBreathCooldown", fireBreathTick);

        nbt.setInteger("Breed", getBreed());
        nbt.setInteger("Tier", getTier());
        nbt.setInteger("interestTime", interestTime);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);

        fireBreathTick = nbt.getInteger("FireBreath");
        fireBreathCooldown = nbt.getInteger("FireBreathCooldown");

        setBreed(nbt.getInteger("Breed"));
        setTier(nbt.getInteger("Tier"));
        interestTime = nbt.getInteger("interestTime");

        if (this.hasCustomName())
        {
            this.bossInfo.setName(this.getDisplayName());
        }

    }

    /**
     * Sets the custom name tag for this entity
     */
    public void setCustomNameTag(String name)
    {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @SideOnly(Side.CLIENT)
    public String getTexture() {
        return getType().tex;
    }

    @SideOnly(Side.CLIENT)
    public double wingFlap() {
        return wingFlap;
    }

    @SideOnly(Side.CLIENT)
    public float getJawMove() {
        return dataManager.get(JAW_MOVEMENT);
    }

    public void setJawMove(float i) {
        dataManager.set(JAW_MOVEMENT, i);
    }

    @SideOnly(Side.CLIENT)
    public float getNeckAngle() {
        return dataManager.get(NECK_ANGLE);
    }

    public void setNeckAngle(float i) {
        dataManager.set(NECK_ANGLE, i);
    }

    public int getDisengageTime() {
        return dataManager.get(DISENGAGE_TIME);
    }

    public void setDisengageTime(int i) {
        dataManager.set(DISENGAGE_TIME, i);
    }

    @Override
    public boolean isTerrestrial() {
        return dataManager.get(TERRESTRIAL) == 1;
    }

    public void setTerrestrial(boolean flag) {
        dataManager.set(TERRESTRIAL, (byte) (flag ? 1 : 0));
    }

    public double wingAngle() {
        return isTerrestrial() ? 0D : 45D;
    }

    private void moveToTarget() {
        if (getDisengageTime() <= 0 && targetedEntity != null) {
            this.faceEntity(targetedEntity, 1F, 1F);
            this.waypointX = targetedEntity.posX;
            this.waypointY = targetedEntity.posY;
            this.waypointZ = targetedEntity.posZ;
        }
    }

    public void disengage(int time) {
        if (targetedEntity != null && targetedEntity instanceof EntityPlayer) {
            lastEnemy = targetedEntity;
        }
        setDisengageTime(time);
        setTerrestrial(false);
        this.waypointX = this.posX + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
        this.waypointY = this.posY + (this.rand.nextFloat()) * 16.0F;
        this.waypointZ = this.posZ + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
        targetedEntity = null;
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
        String tierName = I18n.translateToLocal("entity." + getType().name + ".name");
        String breedName = I18n.translateToLocal("entity.dragonbreed." + getType().breedName + ".name");
        return I18n.translateToLocalFormatted(tierName, breedName);
    }

    public float getScale() {
        return 0.6F + (getTier() * 0.2F);
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

    public float getFirePyro() {
        if (!ConfigMobs.dragonGriefFire) {
            return 0F;
        }
        return getType().pyro;
    }

    @Override
    public float getThreshold(DamageSource src) {
        if (!src.isUnblockable()) {
            return getType().DT;
        }
        return 0;
    }

    @Override
    public float[] getHitTraits() {
        return new float[]{0, 1, 9};// 90% Piercing, 10% BLunt
    }

    @SideOnly(Side.CLIENT)
    public float getVertTailAngle() {
        if (this.motionY > 0.05F || this.motionY < -0.05F)// accend or decend
        {
            if (!(this.motionX == 0 && this.motionZ == 0))// moving
            {
                float angle = (float) (45F * this.motionY) * 5;
                if (angle < -45)
                    angle = -45;
                if (angle > 45)
                    angle = 45;

                return angle;
            }
        }
        return 0;
    }

    private boolean canHearEntity(Entity target) {
        if (target instanceof EntityLivingBase && PowerArmour.isWearingCogwork((EntityLivingBase) target))
            return true;

        double distance = getDistance(target);

        if (distance < width / 2) {
            return true;// touching
        }
        if (distance < 16 && this.getAttackTarget() != null && getAttackTarget() == target) {
            return true;// Smell
        }
        float tarSpeed = (float) Math.hypot(target.motionX, target.motionZ);

        if (tarSpeed < 0.1F) {
            return false;
        }
        if (target instanceof EntityLivingBase) {
            return getSound((EntityLivingBase) target, tarSpeed) >= distance;
        }
        return true;
    }

    private double getSound(EntityLivingBase target, float speed) {
        float value = 10F * (60F + ArmourCalculator.getTotalWeightOfWorn(target, false)) * speed;
        return value;
    }
}