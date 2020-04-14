package minefantasy.mfr.entity;

import minefantasy.mfr.config.ConfigMobs;
import minefantasy.mfr.entity.mob.EntityDragon;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Objects;

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
                        BlockPos breathPos = new BlockPos(i, j, k);
                        if (!world.isRemote && canBlockCatchFire(breathPos.add(0, -1,0)) && world.isAirBlock(breathPos)
                                && rand.nextFloat() < getPyro()) {
                            this.world.setBlockState(breathPos, (IBlockState) Blocks.FIRE);
                        }
                    }
                }
            }
        }
        AxisAlignedBB bb = Objects.requireNonNull(this.getCollisionBoundingBox()).expand(1D, 1D, 1D);
        if (!world.isRemote)
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
    protected void onImpact(RayTraceResult rayTraceResult) {
        if (!this.world.isRemote) {
            if (rayTraceResult.entityHit != null) {
                if (!rayTraceResult.entityHit.isImmuneToFire()) {
                    rayTraceResult.entityHit.setFire(isPreset("Dragon") ? 2 : 4);
                    rayTraceResult.entityHit.attackEntityFrom(causeFireblastDamage(), getDamage());
                }
            } else {
                BlockPos pos = rayTraceResult.getBlockPos();

                switch (rayTraceResult.sideHit) {
                    case DOWN:
                        pos.add(0,-1,0);
                        break;
                    case UP:
                        pos.add(0,1,0);
                        break;
                    case NORTH:
                        pos.add(0,0,-1);
                        break;
                    case SOUTH:
                        pos.add(0,0,1);
                        break;
                    case WEST:
                        pos.add(-1,0,0);
                        break;
                    case EAST:
                        pos.add(1,0,0);
                }

                if (!world.isRemote && this.world.isAirBlock(pos) && rand.nextFloat() < getPyro()) {
                    this.world.setBlockState(pos, (IBlockState) Blocks.FIRE);
                }
                boolean tnt = world.getBlockState(pos).getMaterial() == Material.TNT;
                if (isPreset("BlastFurnace") && (rand.nextInt(50) == 0) || tnt) {
                    boolean solid = world.isBlockFullCube(pos);
                    world.newExplosion(this, posX, posY, posZ, solid ? 1.5F : 0.5F, true, true);
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

    public boolean canBlockCatchFire(BlockPos pos) {
        return canCatchFire(pos, EnumFacing.UP);
    }

    public boolean canCatchFire(BlockPos pos, EnumFacing facing) {
        return world.getBlockState(pos).getBlock().isFlammable(world, pos, facing);
    }

    public EntityFireBlast preset(String string) {
        getEntityData().setBoolean(string, true);
        return this;
    }

    private boolean destroyBlocksInAABB(AxisAlignedBB box) {
        int var2 = MathHelper.floor(box.minX);
        int var3 = MathHelper.floor(box.minY);
        int var4 = MathHelper.floor(box.minZ);
        int var5 = MathHelper.floor(box.maxX);
        int var6 = MathHelper.floor(box.maxY);
        int var7 = MathHelper.floor(box.maxZ);
        boolean var8 = false;
        boolean var9 = false;

        for (int var10 = var2; var10 <= var5; ++var10) {
            for (int var11 = var3; var11 <= var6; ++var11) {
                for (int var12 = var4; var12 <= var7; ++var12) {
                    BlockPos pos = new BlockPos(var10, var11, var12);
                    Material var13 = this.world.getBlockState(pos).getMaterial();

                    if (var13 != null) {
                        if (var13 == Material.GLASS) {
                            var9 = true;
                            this.world.setBlockToAir(pos);
                        } else if (var13 == Material.TNT) {
                            var9 = true;
                            this.world.setBlockToAir(pos);
                            this.world.createExplosion(this, var10, var11, var12, 4.0F, true);
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
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, var16, var17, var14, 0.0D, 0.0D, 0.0D);
            for (int a = 0; a < 1 + rand.nextInt(4); a++)
                this.world.playSound(var16, var17, var14, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.AMBIENT, 1F, 1F, true);
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