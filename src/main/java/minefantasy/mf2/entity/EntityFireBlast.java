package minefantasy.mf2.entity;

import minefantasy.mf2.config.ConfigMobs;
import minefantasy.mf2.entity.mob.EntityDragon;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import static net.minecraftforge.common.util.ForgeDirection.UP;

public class EntityFireBlast extends EntityFireball {
    private static final float size = 0.75F;
    public static DamageSource blastDamage = (new DamageSource("blastfurn")).setFireDamage();
    public static DamageSource basicDamage = (new DamageSource("fireblastBase")).setFireDamage();

    public EntityFireBlast(World world) {
        super(world);
        this.setInvisible(true);
        this.setSize(size, size);
    }

    public EntityFireBlast(World world, EntityLivingBase shooter, double xv, double yv, double zv) {
        super(world, shooter, xv, yv, zv);
        this.setInvisible(true);
        this.setSize(size, size);
    }

    public EntityFireBlast(World world, double x, double y, double z, double xv, double yv, double zv) {
        super(world, x, y, z, xv, yv, zv);
        this.setInvisible(true);
        this.setSize(size, size);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted >= getLifeSpan()) {
            setDead();
        }
        if (ticksExisted % 5 == 0) {
            int lifeScale = (int) Math.floor(ticksExisted / 5F);
            float newSize = size + (size / 4 * lifeScale);
            this.setSize(newSize, newSize);
        }
        if (ticksExisted % 10 == 0) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        int i = (int) posX + x;
                        int j = (int) posY + y;
                        int k = (int) posZ + z;

                        if (!worldObj.isRemote && canBlockCatchFire(i, j - 1, k) && worldObj.isAirBlock(i, j, k)
                                && rand.nextFloat() < getPyro()) {
                            this.worldObj.setBlock(i, j, k, Blocks.fire);
                        }
                    }
                }
            }
        }
        AxisAlignedBB bb = this.boundingBox.expand(1D, 1D, 1D);
        if (!worldObj.isRemote)
            this.destroyBlocksInAABB(bb);
    }

    private int getLifeSpan() {
        if (isPreset("BlastFurnace")) {
            return 15;
        }
        return 30;
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition pos) {
        if (!this.worldObj.isRemote) {
            if (pos.entityHit != null) {
                if (!pos.entityHit.isImmuneToFire()) {
                    pos.entityHit.setFire(isPreset("Dragon") ? 2 : 4);
                    pos.entityHit.attackEntityFrom(causeFireblastDamage(), getDamage());
                }
            } else {
                int i = pos.blockX;
                int j = pos.blockY;
                int k = pos.blockZ;

                switch (pos.sideHit) {
                    case 0:
                        --j;
                        break;
                    case 1:
                        ++j;
                        break;
                    case 2:
                        --k;
                        break;
                    case 3:
                        ++k;
                        break;
                    case 4:
                        --i;
                        break;
                    case 5:
                        ++i;
                }

                if (!worldObj.isRemote && this.worldObj.isAirBlock(i, j, k) && rand.nextFloat() < getPyro()) {
                    this.worldObj.setBlock(i, j, k, Blocks.fire);
                }
                boolean tnt = worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ).getMaterial() == Material.tnt;
                if (isPreset("BlastFurnace") && (rand.nextInt(50) == 0) || tnt) {
                    boolean solid = worldObj.isBlockNormalCubeDefault(pos.blockX, pos.blockY, pos.blockZ, false);
                    worldObj.newExplosion(this, posX, posY, posZ, solid ? 1.5F : 0.5F, true, true);
                }
            }

            this.setDead();
        }
    }

    private float getPyro() {

        if (!ConfigMobs.dragonGriefFire && shootingEntity != null && shootingEntity instanceof EntityDragon) {
            return 0F;
        }

        if (getEntityData().hasKey("Pyro")) {
            return getEntityData().getFloat("Pyro");
        }

        return 0.75F;
    }

    public EntityFireBlast setPyro(float dam) {
        getEntityData().setFloat("Pyro", dam);
        return this;
    }

    private float getDamage() {
        if (getEntityData().hasKey("Damage")) {
            return getEntityData().getFloat("Damage");
        }

        if (isPreset("BlastFurnace")) {
            return 8F;
        }
        return 2.0F;
    }

    public EntityFireBlast setDamage(float dam) {
        getEntityData().setFloat("Damage", dam);
        return this;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this
     * Entity.
     */
    public boolean canBeCollidedWith() {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource src, float power) {
        return false;
    }

    @Override
    public boolean isInvisible() {
        return true;
    }

    public void modifySpeed(float mod) {
        this.accelerationX *= mod;
        this.accelerationY *= mod;
        this.accelerationZ *= mod;
    }

    public boolean isPreset(String s) {
        if (getEntityData().hasKey("Preset")) {
            return getEntityData().getString("Preset").equalsIgnoreCase(s);
        }
        return false;
    }

    public boolean canBlockCatchFire(int x, int y, int z) {
        return canCatchFire(x, y, z, UP);
    }

    public boolean canCatchFire(int x, int y, int z, ForgeDirection face) {
        return worldObj.getBlock(x, y, z).isFlammable(worldObj, x, y, z, face);
    }

    public EntityFireBlast preset(String string) {
        getEntityData().setBoolean(string, true);
        return this;
    }

    private boolean destroyBlocksInAABB(AxisAlignedBB box) {
        int var2 = MathHelper.floor_double(box.minX);
        int var3 = MathHelper.floor_double(box.minY);
        int var4 = MathHelper.floor_double(box.minZ);
        int var5 = MathHelper.floor_double(box.maxX);
        int var6 = MathHelper.floor_double(box.maxY);
        int var7 = MathHelper.floor_double(box.maxZ);
        boolean var8 = false;
        boolean var9 = false;

        for (int var10 = var2; var10 <= var5; ++var10) {
            for (int var11 = var3; var11 <= var6; ++var11) {
                for (int var12 = var4; var12 <= var7; ++var12) {
                    Material var13 = this.worldObj.getBlock(var10, var11, var12).getMaterial();

                    if (var13 != null) {
                        if (var13 == Material.glass) {
                            var9 = true;
                            this.worldObj.setBlockToAir(var10, var11, var12);
                        } else if (var13 == Material.tnt) {
                            var9 = true;
                            this.worldObj.setBlockToAir(var10, var11, var12);
                            this.worldObj.createExplosion(this, var10, var11, var12, 4.0F, true);
                        } else {
                            var8 = true;
                        }
                    }
                }
            }
        }

        if (var9) {
            double var16 = box.minX + (box.maxX - box.minX) * this.rand.nextFloat();
            double var17 = box.minY + (box.maxY - box.minY) * this.rand.nextFloat();
            double var14 = box.minZ + (box.maxZ - box.minZ) * this.rand.nextFloat();
            this.worldObj.spawnParticle("largeexplode", var16, var17, var14, 0.0D, 0.0D, 0.0D);
            for (int a = 0; a < 1 + rand.nextInt(4); a++)
                this.worldObj.playSoundAtEntity(this, "dig.glass", 1F, 1F);
        }

        return var8;
    }

    public DamageSource causeFireblastDamage() {
        if (isPreset("BlastFurnace")) {
            return blastDamage;
        }
        return shootingEntity == null ? basicDamage
                : (new EntityDamageSourceIndirect("fireblast", this, shootingEntity)).setFireDamage().setProjectile();
    }
}