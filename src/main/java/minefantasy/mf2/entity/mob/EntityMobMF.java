package minefantasy.mf2.entity.mob;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public abstract class EntityMobMF extends EntityCreature implements IMob {
    public EntityMobMF(World world) {
        super(world);
        this.experienceValue = 5;
    }

    /**
     * Called frequently so the entity can update its state every tick as required.
     * For example, zombies and skeletons use this to react to sunlight and start to
     * burn.
     */
    @Override
    public void onLivingUpdate() {
        this.updateArmSwingProgress();
        float f = this.getBrightness(1.0F);

        if (f > 0.5F) {
            this.entityAge += 2;
        }

        super.onLivingUpdate();
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.canDespawn() && !this.worldObj.isRemote
                && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }
    }

    @Override
    protected String getSwimSound() {
        return "game.hostile.swim";
    }

    @Override
    protected String getSplashSound() {
        return "game.hostile.swim.splash";
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity
     * isn't interested in attacking (Animals, Spiders at day, peaceful PigZombies).
     */
    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
        return entityplayer != null && this.canEntityBeSeen(entityplayer) ? entityplayer : null;
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float dam) {
        if (this.isEntityInvulnerable()) {
            return false;
        } else if (super.attackEntityFrom(source, dam)) {
            Entity entity = source.getEntity();

            if (this.riddenByEntity != entity && this.ridingEntity != entity) {
                if (entity != this) {
                    this.entityToAttack = entity;
                }

                return true;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound() {
        return "game.hostile.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound() {
        return "game.hostile.die";
    }

    @Override
    protected String func_146067_o(int distance) {
        return distance > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
    }

    @Override
    public boolean attackEntityAsMob(Entity target) {
        if (worldObj.difficultySetting == EnumDifficulty.PEACEFUL && target instanceof EntityPlayer) {
            return false;
        }

        float f = (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        int i = 0;

        if (target instanceof EntityLivingBase) {
            f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase) target);
            i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase) target);
        }

        boolean flag = target.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag) {
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

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by
     * each mob to define their attack.
     */
    @Override
    protected void attackEntity(Entity target, float dam) {
        if (this.attackTime <= 0 && dam < 2.0F && target.boundingBox.maxY > this.boundingBox.minY
                && target.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            this.attackEntityAsMob(target);
        }
    }

    /**
     * Takes a coordinate in and returns a weight to determine how likely this
     * creature will try to path to the block. Args: x, y, z
     */
    @Override
    public float getBlockPathWeight(int x, int y, int z) {
        return 0.5F - this.worldObj.getLightBrightness(x, y, z);
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);

        if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, i, j, k) > this.rand.nextInt(32)) {
            return false;
        } else {
            int l = this.worldObj.getBlockLightValue(i, j, k);

            if (this.worldObj.isThundering()) {
                int i1 = this.worldObj.skylightSubtracted;
                this.worldObj.skylightSubtracted = 10;
                l = this.worldObj.getBlockLightValue(i, j, k);
                this.worldObj.skylightSubtracted = i1;
            }

            return l <= this.rand.nextInt(8);
        }
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this
     * entity.
     */
    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.isValidLightLevel()
                && super.getCanSpawnHere();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    }

    @Override
    protected boolean func_146066_aG() {
        return true;
    }
}