package minefantasy.mfr.mechanics.worldGen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
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
    public boolean generate(World world, Random rand, BlockPos position) {
        for (int l = 0; l < 8; ++l) {
            BlockPos pos = new BlockPos( position.getX() + rand.nextInt(8) - rand.nextInt(8),
                    position.getY() + rand.nextInt(4) - rand.nextInt(4),
                    position.getZ() + rand.nextInt(8) - rand.nextInt(8));

            if (world.isAirBlock(pos) && world.getBlockState(pos.add(0,-1,0)).getBlock() == Blocks.GRASS
                    && block.canPlaceBlockAt(world, pos)) {
                world.setBlockState(pos, block.getDefaultState(), meta);
            }
        }

        return true;
    }
}