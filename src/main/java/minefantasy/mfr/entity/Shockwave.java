package minefantasy.mfr.entity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Shockwave {// Explosion
    /**
     * whether or not the explosion sets fire to blocks around it
     */
    public boolean isFlaming;
    /**
     * whether or not this explosion spawns smoke particles
     */
    public boolean isSmoking = true;
    public boolean isGriefing = true;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;
    /**
     * A list of ChunkPositions of blocks affected by this explosion
     */
    public List affectedBlockPositions = new ArrayList();
    private int maxRange = 16;
    private Random explosionRNG = new Random();
    private World world;
    private Map field_77288_k = new HashMap();
    private String type;

    public Shockwave(String type, World world, Entity cause, double x, double y, double z, float power) {
        this.world = world;
        this.exploder = cause;
        this.explosionSize = power;
        this.explosionX = x;
        this.explosionY = y;
        this.explosionZ = z;
        this.type = type;
    }

    /**
     * Does the first part of the explosion (destroy blocks)
     */
    public void initiate() {
        float f = this.explosionSize;
        HashSet hashset = new HashSet();
        int i;
        int j;
        int k;
        double d5;
        double d6;
        double d7;

        for (i = 0; i < this.maxRange; ++i) {
            for (j = 0; j < this.maxRange; ++j) {
                for (k = 0; k < this.maxRange; ++k) {
                    if (i == 0 || i == this.maxRange - 1 || j == 0 || j == this.maxRange - 1 || k == 0
                            || k == this.maxRange - 1) {
                        double d0 = i / (this.maxRange - 1.0F) * 2.0F - 1.0F;
                        double d1 = j / (this.maxRange - 1.0F) * 2.0F - 1.0F;
                        double d2 = k / (this.maxRange - 1.0F) * 2.0F - 1.0F;
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f1 = this.explosionSize * (0.7F + this.world.rand.nextFloat() * 0.6F);
                        d5 = this.explosionX;
                        d6 = this.explosionY;
                        d7 = this.explosionZ;

                        for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F) {
                            int j1 = MathHelper.floor(d5);
                            int k1 = MathHelper.floor(d6);
                            int l1 = MathHelper.floor(d7);
                            Block block = this.world.getBlock(j1, k1, l1);

                            if (block.getMaterial() == Material.glass) {
                                hashset.add(new ChunkPosition(j1, k1, l1));
                            }

                            d5 += d0 * f2;
                            d6 += d1 * f2;
                            d7 += d2 * f2;
                        }
                    }
                }
            }
        }

        this.affectedBlockPositions.addAll(hashset);
        this.explosionSize *= 2.0F;
        i = MathHelper.floor(this.explosionX - this.explosionSize - 1.0D);
        j = MathHelper.floor(this.explosionX + this.explosionSize + 1.0D);
        k = MathHelper.floor(this.explosionY - this.explosionSize - 1.0D);
        int i2 = MathHelper.floor(this.explosionY + this.explosionSize + 1.0D);
        int l = MathHelper.floor(this.explosionZ - this.explosionSize - 1.0D);
        int j2 = MathHelper.floor(this.explosionZ + this.explosionSize + 1.0D);
        List list = this.world.getEntitiesWithinAABBExcludingEntity(this.exploder,
                new AxisAlignedBB(i, k, l, j, i2, j2));
        Vec3 vec3 = Vec3.createVectorHelper(this.explosionX, this.explosionY, this.explosionZ);

        for (int i1 = 0; i1 < list.size(); ++i1) {
            Entity entity = (Entity) list.get(i1);
            double d4 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / this.explosionSize;

            if (d4 <= 1.0D && !(entity instanceof EntityItem)) {
                d5 = entity.posX - this.explosionX;
                d6 = entity.posY + entity.getEyeHeight() - this.explosionY;
                d7 = entity.posZ - this.explosionZ;
                double d9 = MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);

                if (d9 != 0.0D) {
                    d5 /= d9;
                    d6 /= d9;
                    d7 /= d9;
                    double d10 = this.world.getBlockDensity(vec3, entity.getCollisionBoundingBox());
                    double d11 = (1.0D - d4) * d10;
                    float damage = ((int) ((d11 * d11 + d11) / 2.0D * 8.0D * this.explosionSize + 1.0D));
                    entity.attackEntityFrom(getExplosionSource(), Math.min(64F, 4F + (damage / 2F)));
                    double d8 = EnchantmentProtection.func_92092_a(entity, d11);
                    entity.motionX += d5 * d8;
                    entity.motionY += d6 * d8;
                    entity.motionZ += d7 * d8;

                    if (entity instanceof EntityPlayer) {
                        this.field_77288_k.put(entity, Vec3.createVectorHelper(d5 * d11, d6 * d11, d7 * d11));
                    }
                }
            }
        }

        this.explosionSize = f;
    }

    /**
     * Does the second part of the explosion (sound, particles, drop spawn)
     */
    public void decorateWave(boolean flag) {
        this.world.playSound(this.explosionX, this.explosionY, this.explosionZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 4.0F,
                (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, true);

        if (this.explosionSize >= 2.0F && this.isSmoking) {
            this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D,
                    0.0D);
        } else {
            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }

        Iterator iterator;
        ChunkPosition chunkposition;
        int i;
        int j;
        int k;
        Block block;

        if (this.isSmoking) {
            iterator = this.affectedBlockPositions.iterator();

            while (iterator.hasNext()) {
                chunkposition = (ChunkPosition) iterator.next();
                i = chunkposition.chunkPosX;
                j = chunkposition.chunkPosY;
                k = chunkposition.chunkPosZ;
                block = this.world.getBlock(i, j, k);

                if (flag) {
                    double d0 = i + this.world.rand.nextFloat();
                    double d1 = j + this.world.rand.nextFloat();
                    double d2 = k + this.world.rand.nextFloat();
                    double d3 = d0 - this.explosionX;
                    double d4 = d1 - this.explosionY;
                    double d5 = d2 - this.explosionZ;
                    double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    double d7 = 0.5D / (d6 / this.explosionSize + 0.1D);
                    d7 *= this.world.rand.nextFloat() * this.world.rand.nextFloat() + 0.3F;
                    d3 *= d7;
                    d4 *= d7;
                    d5 *= d7;
                    this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + this.explosionX * 1.0D) / 2.0D,
                            (d1 + this.explosionY * 1.0D) / 2.0D, (d2 + this.explosionZ * 1.0D) / 2.0D, d3, d4, d5);
                    this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5);
                }

                if (isGriefing && block.getMaterial() == Material.glass) {
                    this.world.setBlockToAir(i, j, k);
                    this.world.playSoundEffect(i, j, k, "break.glass", 1.0F,
                            0.75F + (explosionRNG.nextFloat() * 0.5F));
                }
            }
        }

        if (this.isFlaming) {
            iterator = this.affectedBlockPositions.iterator();

            while (iterator.hasNext()) {
                chunkposition = (ChunkPosition) iterator.next();
                i = chunkposition.chunkPosX;
                j = chunkposition.chunkPosY;
                k = chunkposition.chunkPosZ;
                block = this.world.getBlock(i, j, k);
                Block block1 = this.world.getBlock(i, j - 1, k);

                if (block.getMaterial() == Material.air && block1.func_149730_j()
                        && this.explosionRNG.nextInt(3) == 0) {
                    this.world.setBlock(i, j, k, Blocks.fire);
                }
            }
        }
    }

    public Map func_77277_b() {
        return this.field_77288_k;
    }

    public DamageSource getExplosionSource() {
        if (exploder != null) {
            return new EntityDamageSource(type, exploder).setExplosion();
        }
        return new DamageSource(type).setExplosion();
    }
}