package minefantasy.mf2.entity.mob;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.armour.IArmourPenetrationMob;
import minefantasy.mf2.api.helpers.ArmourCalculator;
import minefantasy.mf2.api.helpers.PowerArmour;
import minefantasy.mf2.api.helpers.TacticalManager;
import minefantasy.mf2.api.weapon.ISpecialCombatMob;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.config.ConfigMobs;
import minefantasy.mf2.entity.EntityBomb;
import minefantasy.mf2.entity.mob.ai.AI_MinotaurFindTarget;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CustomToolListMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.item.list.styles.OrnateStyle;
import minefantasy.mf2.item.weapon.ItemWeaponMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMinotaur extends EntityMobMF implements IArmourPenetrationMob, ISpecialCombatMob {
    private static final int dataID = 12;
    public int swing;
    private int grabCooldown;
    private int hitCooldownTime;
    private int specialAttackTime;
    private float[] punch = new float[]{0F, 1F, 0F};
    private float[] headbutt = new float[]{0F, 1F, 4F};

    public EntityMinotaur(World world) {
        super(world);
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 1.0D, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 0.5D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new AI_MinotaurFindTarget(this, EntityPlayer.class, 0, true));

        this.setSize(1.5F, 2.5F);
        this.stepHeight = 1.0F;
        this.experienceValue = 40;

        for (int i = 0; i < this.equipmentDropChances.length; ++i) {
            this.equipmentDropChances[i] = 1F;
        }
    }

    public void worldGenTier(int species, int tier) {
        setMob(species, tier);
        setLoadout();
        if (tier > 0) {
            this.setHomeArea((int) posX, (int) posY, (int) posZ, getRange(tier));
        }
    }

    private int getRange(int tier) {
        return tier > 1 ? 12 : 24;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        int species = MinotaurBreed.getEnvironment(this);

        setMob(species, 0);
        setLoadout();
        return super.onSpawnWithEgg(data);
    }

    public void setLoadout() {
        String tier = getMinotaur().weaponTier;
        if (tier != null) {
            this.setCurrentItemOrArmor(0, getRandomWeapon().construct(tier, "OakWood"));
        }
    }

    public ItemWeaponMF getRandomWeapon() {
        if (getTier() == 3) {
            return OrnateStyle.ornate_greatsword;
        }
        ItemWeaponMF[] list = new ItemWeaponMF[]{CustomToolListMF.standard_greatsword,
                CustomToolListMF.standard_katana, CustomToolListMF.standard_battleaxe,
                CustomToolListMF.standard_warhammer, CustomToolListMF.standard_spear,
                CustomToolListMF.standard_halbeard};
        return list[rand.nextInt(list.length)];
    }

    @Override
    public String getCommandSenderName() {
        return StatCollector.translateToLocal("entity." + getMinotaur().name + ".name");
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35F);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(ConfigMobs.minotaurMD);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(ConfigMobs.minotaurHP);
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(dataID, Byte.valueOf((byte) 0));
        this.dataWatcher.addObject(dataID + 1, Integer.valueOf((byte) 1));
        this.dataWatcher.addObject(dataID + 2, Integer.valueOf(2));
        this.dataWatcher.addObject(dataID + 3, Integer.valueOf(0));
    }

    @Override
    public float getBlockPathWeight(int x, int y, int z) {
        return super.getBlockPathWeight(x, y, z);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!worldObj.isRemote) {
            if (getRageLevel() == 0 && getAttackTarget() == null) {
                if (ticksExisted % 40 == 0) {
                    forage();
                }
            }
            if (ticksExisted % 20 == 0) {
                if (!isBloodied()) {
                    if (getRageVariable() > 0) {
                        addRage(-1);
                        if (getRageVariable() > 50) {
                            setRage(50);
                        }
                    }
                }
            }
            if (hitCooldownTime > 0)
                --hitCooldownTime;
            if (grabCooldown > 0) {
                --grabCooldown;
            }
            if (specialAttackTime > 0) {
                --specialAttackTime;
            }
            if ((getAttack() == (byte) 1 || getAttack() == (byte) 3) && specialAttackTime == 0) {
                initBasicAttack();
            }
            if (this.getAttackTarget() != null) {
                if (getRageLevel() > 20 && getAttack() != (byte) 2 && getHeldItem() == null && rand.nextInt(100) == 0) {
                    this.initHeadbutt();
                }
                EntityLivingBase target = getAttackTarget();
                int intLvl = getIntLvl();

                if (intLvl > 0)// Smarter means more likely
                {
                    if (getAttack() != 2 && rand.nextInt(10) < intLvl && onGround && target instanceof EntityPlayer
                            && ((EntityPlayer) target).isBlocking() && this.getDistanceToEntity(target) < 4F) {
                        this.jump();
                        this.initPowerAttack();
                    }
                } else {
                    if (getRageLevel() > 40 && getAttack() != 2 && rand.nextInt(50) == 0 && onGround
                            && target instanceof EntityPlayer && ((EntityPlayer) target).isBlocking()
                            && this.getDistanceToEntity(target) < 4F) {
                        this.jump();
                        this.initPowerAttack();
                    }
                }
                double distance = this.getDistanceSqToEntity(target);
                if (!TacticalManager.isFlankedBy(target, this, 270) && distance > 6 && distance < 12
                        && rand.nextInt(50) == 0 && this.getAttack() == (byte) 0 && this.getMinotaur().throwsBombs) {
                    this.throwBomb(target, 1.0F);
                }

                if (this.ticksExisted % 20 == 0 && (getRageLevel() <= 0 || !canEntityBeSeen(getAttackTarget()))) {
                    if (rand.nextInt(50 + (50 * getIntLvl())) == 0) {
                        this.setAttackTarget(null);
                    }
                }
            }

            boolean inBeserk = getRageLevel() >= 100 && isBloodied();
            if (getAttack() == (byte) 2) {
                if (!inBeserk) {
                    initBasicAttack();
                } else {
                    this.swingItem();
                }
            }
            if (getAttack() != (byte) 2 && inBeserk) {
                initBeserk();
            }
        }
    }

    private void forage() {
        if (rand.nextInt(100) == 0) {
            int radius = 8;
            for (int x = -radius; x <= radius; x++) {
                for (int y = -2; y <= 2; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        int xPos = (int) posX + x;
                        int yPos = (int) posY + y;
                        int zPos = (int) posZ + z;
                        if (rand.nextInt(10) == 0) {
                            if (worldObj.getBlock(xPos, yPos, zPos) == BlockListMF.berryBush
                                    && worldObj.getBlockMetadata(xPos, yPos, zPos) == 0) {
                                getNavigator().tryMoveToXYZ(xPos + 0.5, yPos + 1, zPos + 0.5, 0.6F);
                                return;
                            }
                            if (worldObj.getBlock(xPos, yPos, zPos) instanceof BlockCrops
                                    && worldObj.getBlockMetadata(xPos, yPos, zPos) >= 4) {
                                getNavigator().tryMoveToXYZ(xPos + 0.5, yPos + 1, zPos + 0.5, 0.6F);
                                return;
                            }
                        }
                    }
                }
            }
            return;
        }
        if (worldObj.getBlock((int) posX, (int) posY, (int) posZ) == BlockListMF.berryBush
                && worldObj.getBlockMetadata((int) posX, (int) posY, (int) posZ) == 0) {
            worldObj.playSoundEffect(posX, posY + getEyeHeight(), posZ, "random.eat", 1.0F, 0.75F);
            swingItem();
            heal(10);
            worldObj.setBlockMetadataWithNotify((int) posX, (int) posY, (int) posZ, 1, 2);
        } else if (worldObj.getBlock((int) posX, (int) posY, (int) posZ) instanceof BlockCrops
                && worldObj.getBlockMetadata((int) posX, (int) posY, (int) posZ) > 0) {
            worldObj.playSoundEffect(posX, posY + getEyeHeight(), posZ, "random.eat", 1.0F, 0.75F);
            worldObj.playSoundEffect(posX, posY + getEyeHeight(), posZ, "dig.grass", 1.0F, 1.00F);
            swingItem();
            heal(5);
            worldObj.setBlockToAir((int) posX, (int) posY, (int) posZ);
        }
    }

    @Override
    protected void dropFewItems(boolean playerKill, int looting) {
        int count = getLootCount() == 1 ? 1 : rand.nextInt(looting + getLootCount() + 1);
        for (int a = 0; a < count; a++) {
            Item drop = getLoot();
            this.dropItem(drop, 1);
        }
        count = rand.nextInt(looting + 4) + 1;
        for (int a = 0; a < count; a++) {
            this.dropItem(ComponentListMF.rawhideLarge, 1);
        }
        count = rand.nextInt(looting + 2) + 1;
        for (int a = 0; a < count; a++) {
            this.dropItem(isBurning() ? Items.cooked_beef : Items.beef, 1);
        }
    }

    private Item getLoot() {
        int breed = getTier();
        if (breed > 2) {
            return ToolListMF.loot_sack_rare;
        }
        if (breed > 0) {
            return ToolListMF.loot_sack_uc;
        }
        return ToolListMF.loot_sack;
    }

    private int getLootCount() {
        int breed = getTier();
        if (breed > 2) {
            return 1;
        }
        return 2 + breed;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if (!super.attackEntityFrom(source, damage)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            if (entity != null) {
                addRage(this.getIntLvl() >= 1 ? (int) (damage / 2) : (int) damage);
            }
        }
        return true;
    }

    public void initBeserk() {
        TacticalManager.tryDisarm(this);
        // this.playSound("minefantasy2:mob.minotaur.beserk", 1.0F, 1.0F);
        setSprinting(true);
        setAttack((byte) 2);
    }

    public void initPowerAttack() {
        setSprinting(true);
        specialAttackTime = 30;
        hitCooldownTime = 0;
        setAttack((byte) 3);
    }

    public void initHeadbutt() {
        setSprinting(true);
        specialAttackTime = 20;
        this.hitCooldownTime = 0;
        setAttack((byte) 1);
    }

    public void initBasicAttack() {
        setSprinting(false);
        specialAttackTime = 0;
        setAttack((byte) 0);
    }

    @Override
    protected String getLivingSound() {
        return "mob.cow.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound() {
        return "mob.cow.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound() {
        return "minefantasy2:mob.minotaur.death";
    }

    @Override
    public float getSoundPitch() {
        return 0.5F;
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        this.playSound("mob.cow.step", 0.75F, 0.9F);
    }

    protected Item getDropItem() {
        return ComponentListMF.hideLarge;
    }

    public double getHeadChargeAngle() {
        return getAttack() == (byte) 1 ? 80F : 0F;
    }

    public float getArmourRating(DamageSource src) {
        float[] armour = getValueResistences();
        return (getMinotaur().armour_rating / 100F)
                * ArmourCalculator.adjustACForDamage(src, getDT(), armour[0], armour[1], armour[2]);
    }

    @Override
    protected void damageEntity(DamageSource source, float dam) {
        float AR = 1.0F + Math.max(0.0F, getArmourRating(source));

        super.damageEntity(source, dam / AR);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);

        nbt.setInteger("Attack", getAttack());
        nbt.setInteger("Species", getSpecies());
        nbt.setInteger("Breed", getTier());
        nbt.setInteger("Rage", getRageVariable());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);

        setAttack((byte) nbt.getInteger("Attack"));
        int species = nbt.getInteger("Species");
        int breed = nbt.getInteger("Breed");
        setMob(species, breed);

        setRage(nbt.getInteger("Rage"));
    }

    private float getDT() {
        byte att = getAttack();
        if (att == (byte) 2) {
            return 5F;
        }
        return 2F;
    }

    /**
     * Cutting, Blunt, Piercing
     */
    private float[] getValueResistences() {
        return new float[]{0.75F, 1F, 0.5F};
    }

    /**
     * 0=Normal, 1=Headbutt, 2=Beserk, 3=Power
     */
    public byte getAttack() {
        return dataWatcher.getWatchableObjectByte(dataID);
    }

    /**
     * 0=Normal, 1=Headbutt, 2=Beserk, 3=Power
     */
    public void setAttack(byte type) {
        dataWatcher.updateObject(dataID, type);
    }

    /**
     * @param species    Brown, Snow, Nether
     * @param subspecies the tier such as normal, warlord, etc
     */
    public void setMob(int species, int subspecies) {
        MinotaurBreed minotaur = MinotaurBreed.getBreed(species, subspecies);

        setSpecies(species);
        setTier(subspecies);

        this.preventEntitySpawning = minotaur.isSpecial;
        this.isImmuneToFire = species == 1;
        this.experienceValue = minotaur.experienceValue;

        if (subspecies == 0) {
            this.targetTasks.addTask(2, new AI_MinotaurFindTarget(this, EntityLiving.class, 0, false));
        }
        if (subspecies >= 3) {
            this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
        }
    }

    @Override
    protected boolean canDespawn() {
        return !getMinotaur().isSpecial;
    }

    /**
     * 0=Normal, 1=Nether
     */
    public int getSpecies() {
        return dataWatcher.getWatchableObjectInt(dataID + 1);
    }

    /**
     * 0=Normal, 1=Nether
     */
    public void setSpecies(int type) {
        dataWatcher.updateObject(dataID + 1, type);

    }

    public int getTier() {
        return dataWatcher.getWatchableObjectInt(dataID + 2);
    }

    public void setTier(int type) {
        dataWatcher.updateObject(dataID + 2, type);
    }

    @Override
    public float[] getHitTraits() {
        byte att = getAttack();
        if (att == (byte) 1) {
            return headbutt;
        }
        return punch;
    }

    @Override
    public boolean attackEntityAsMob(Entity target) {
        if (worldObj.difficultySetting == EnumDifficulty.PEACEFUL && target instanceof EntityPlayer) {
            return false;
        }
        // if(hitCooldownTime > 0)return false;

        if (!canEntityBeSeen(target)) {
            return false;
        }
        if (getAttack() == 3 && motionY > 0 && target.posY < posY) {
            return false;
        }

        float dam = getHitDamage();

        if (getRageLevel() > 50) {
            addRage((int) (dam * 1.5));
        }
        if (getAttack() == 2 && this.riddenByEntity != null && this.riddenByEntity == target) {
            if (target instanceof EntityLivingBase && rand.nextInt(4) == 0) {
                ArmourCalculator.damageArmour((EntityLivingBase) target, (int) (dam * 0.25F));
            }
        }
        if (this.riddenByEntity == null) {
            if (grabCooldown <= 0 && rand.nextInt(100) < getGrabChance() && canPickUp(target)) {
                target.mountEntity(this);
            }
        } else if (rand.nextInt(100) < ConfigMobs.minotaurTC && riddenByEntity == target) {
            riddenByEntity.mountEntity(null);
            grabCooldown = 60;
            TacticalManager.knockbackEntity(target, this, 4F, 1F);
        }
        if (getAttack() == 3 && fallDistance > 0 && target instanceof EntityPlayer
                && ((EntityPlayer) target).isBlocking() && rand.nextInt(100) < getDisarmChance()) {
            TacticalManager.tryDisarm(this, (EntityLivingBase) target, true);
        }

        int i = getKnockbackBoost();

        if (target instanceof EntityLivingBase) {
            dam += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase) target);
            i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase) target);
        }

        boolean flag = target.attackEntityFrom(new DamageSourceMobMF(getAttackType(), this), dam);

        if (flag) {
            this.hitCooldownTime = getAttackSpeed();
            this.swingItem();
            if (i > 0) {
                target.addVelocity(-MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F) * i * 0.5F, 0.1D,
                        MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F) * i * 0.5F);
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0) {
                target.setFire(j * 4);
            }

            if (target instanceof EntityLivingBase) {
                EnchantmentHelper.func_151384_a((EntityLivingBase) target, this);
            }

            EnchantmentHelper.func_151385_b(this, target);
        }

        return flag;
    }

    private boolean canPickUp(Entity target) {
        if (target instanceof EntityLivingBase && PowerArmour.isWearingCogwork((EntityLivingBase) target)) {
            return false;
        }
        float mysize = getVolume(this);
        float theirsize = getVolume(target);
        return mysize > theirsize;
    }

    private float getVolume(Entity entity) {
        return entity.width * entity.width * entity.height;
    }

    private int getAttackSpeed() {
        return 0;
        /*
         * if(rage >= 100) { return 10; } if(rage > 80) { return 15; } if(rage > 50) {
         * return 20; } if(rage > 25) { return 30; } return 35;
         */
    }

    @Override
    public void onKillEntity(EntityLivingBase victim) {
        super.onKillEntity(victim);
        if (!isBloodied()) {
            addRage(-20);
        }
        if (getHealth() < getMaxHealth() * 0.75F) {
            this.heal(5);
        }
    }

    public MinotaurBreed getMinotaur() {
        return MinotaurBreed.getBreed(getSpecies(), getTier());
    }

    private int getDisarmChance() {
        return getMinotaur().disarmChance;
    }

    private int getGrabChance() {
        return (getAttack() == 2 ? getMinotaur().grabChanceBeserk : getMinotaur().grabChance);
    }

    private int getThrowChance() {
        return getMinotaur().throwChance;
    }

    private String getAttackType() {
        byte att = getAttack();
        if (att == (byte) 1) {
            return "gore";
        }
        if (att == (byte) 2) {
            return "beserk";
        }
        return "pound";
    }

    private int getKnockbackBoost() {
        byte att = getAttack();
        if (att == (byte) 1) {
            return getSpecies() == 1 ? 4 : 2;
        }
        return 0;
    }

    private float getHitDamage() {
        MinotaurBreed breed = getMinotaur();
        byte att = getAttack();

        if (att != (byte) 1 && getHeldItem() != null)// no weapon dam on gore
        {
            float melee = att == (byte) 2 ? breed.beserkDamage : breed.poundDamage;

            return Math.max(2F, melee - 2F);// Weapon Dam
        }

        if (att == (byte) 1) {
            return breed.goreDamage;
        }
        if (att == (byte) 2) {
            return breed.beserkDamage;
        }
        return breed.poundDamage;
    }

    public boolean isBloodied() {
        float t = this.getMinotaur().beserkThreshold / 100F;
        return getHealth() < (getMaxHealth() * t);
    }

    @SideOnly(Side.CLIENT)
    public String getTexture() {
        String tex = "minotaur" + getMinotaur().tex;
        if (isBloodied()) {
            tex += "_bloodied";
        }
        return tex;
    }

    public int getRageVariable() {
        return dataWatcher.getWatchableObjectInt(dataID + 3);
    }

    public void addRage(int level) {
        setRage(getRageVariable() + level);
    }

    public void setRage(int level) {
        if (!worldObj.isRemote)
            dataWatcher.updateObject(dataID + 3, Math.max(0, level));
    }

    public int getRageLevel() {
        return isBloodied() ? 100 : getRageVariable();
    }

    private int getIntLvl() {
        return this.getMinotaur().intelligenceLvl;
    }

    public boolean isDocile() {
        return getIntLvl() <= 0;
    }

    @Override
    public boolean canParry(DamageSource source) {
        int intLvl = this.getIntLvl();
        if (intLvl == 0) {
            return rand.nextInt(8) == 0;
        }
        return this.getAttack() == (byte) 0;
    }

    public void throwBomb(EntityLivingBase attackTarget, float spread) {
        EntityBomb bomb = new EntityBomb(worldObj, this).setType((byte) 1, (byte) 0, (byte) 0, (byte) 0);
        worldObj.spawnEntityInWorld(bomb);
        this.swingItem();
    }

    @Override
    public void onParry(DamageSource source, Entity attacker, float dam) {
        Entity target = this.getEntityToAttack();
        if (getIntLvl() > 1 && target != null && attacker == target && this.getDistanceSqToEntity(attacker) < 4) {
            this.setAttack((byte) 1);
            TacticalManager.lungeEntity(this, attacker, 2.0F, 0.2F);
        }
    }
}
