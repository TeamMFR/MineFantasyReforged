package minefantasy.mf2.mechanics.worldGen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenDuelMinable extends WorldGenerator {
    private Block ore, specialOre;
    /**
     * The number of blocks to generate.
     */
    private int veinSize;
    private Block bed;
    private int oreMeta, specialMeta;
    private float chanceForSpecial;

    public WorldGenDuelMinable(Block main, int number, Block extra, float chance) {
        this(main, 0, number, Blocks.stone, extra, 0, chance);
    }

    public WorldGenDuelMinable(Block main, int mainMeta, int number, Block target, Block extra, int extraMeta,
                               float chance) {
        this.ore = main;
        this.oreMeta = mainMeta;
        this.veinSize = number;
        this.bed = target;
        this.specialOre = extra;
        this.specialMeta = extraMeta;
        this.chanceForSpecial = chance;
    }

    public boolean generate(World world, Random seed, int x, int y, int z) {
        float f = seed.nextFloat() * (float) Math.PI;
        double d0 = x + 8 + MathHelper.sin(f) * this.veinSize / 8.0F;
        double d1 = x + 8 - MathHelper.sin(f) * this.veinSize / 8.0F;
        double d2 = z + 8 + MathHelper.cos(f) * this.veinSize / 8.0F;
        double d3 = z + 8 - MathHelper.cos(f) * this.veinSize / 8.0F;
        double d4 = y + seed.nextInt(3) - 2;
        double d5 = y + seed.nextInt(3) - 2;

        for (int l = 0; l <= this.veinSize; ++l) {
            double d6 = d0 + (d1 - d0) * l / this.veinSize;
            double d7 = d4 + (d5 - d4) * l / this.veinSize;
            double d8 = d2 + (d3 - d2) * l / this.veinSize;
            double d9 = seed.nextDouble() * this.veinSize / 16.0D;
            double d10 = (MathHelper.sin(l * (float) Math.PI / this.veinSize) + 1.0F) * d9 + 1.0D;
            double d11 = (MathHelper.sin(l * (float) Math.PI / this.veinSize) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
            int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
            int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
            int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
            int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
            int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

            for (int k2 = i1; k2 <= l1; ++k2) {
                double d12 = (k2 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D) {
                    for (int l2 = j1; l2 <= i2; ++l2) {
                        double d13 = (l2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D) {
                            for (int i3 = k1; i3 <= j2; ++i3) {
                                double d14 = (i3 + 0.5D - d8) / (d10 / 2.0D);

                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D
                                        && world.getBlock(k2, l2, i3).isReplaceableOreGen(world, k2, l2, i3, bed)) {
                                    if (seed.nextFloat() < chanceForSpecial) {
                                        world.setBlock(k2, l2, i3, this.specialOre, specialMeta, 2);// Special Block
                                    } else {
                                        world.setBlock(k2, l2, i3, this.ore, oreMeta, 2);// Ore
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}