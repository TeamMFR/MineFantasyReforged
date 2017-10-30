package minefantasy.mf2.mechanics.worldGen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenBush extends WorldGenerator {
    private Block block;
    private int meta;

    public WorldGenBush(Block block, int meta) {
        super();
        this.block = block;
        this.meta = meta;
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        for (int l = 0; l < 8; ++l) {
            int i1 = x + rand.nextInt(8) - rand.nextInt(8);
            int j1 = y + rand.nextInt(4) - rand.nextInt(4);
            int k1 = z + rand.nextInt(8) - rand.nextInt(8);

            if (world.isAirBlock(i1, j1, k1) && world.getBlock(i1, j1 - 1, k1) == Blocks.grass
                    && block.canPlaceBlockAt(world, i1, j1, k1)) {
                world.setBlock(i1, j1, k1, block, meta, 2);
            }
        }

        return true;
    }
}