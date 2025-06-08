package minefantasy.mfr.api.crafting;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public interface IIgnitable {

    /**
     * Standardized function to handle block ignition
     * @param world World
     * @param pos BlockPos
     * @param state IBlockState
     */
    void igniteBlock(World world, BlockPos pos, IBlockState state);

    static void playIgnitionSound (World world, BlockPos pos) {
        Random rand = new Random();
        float pitch = (0.2F + (0.4F * rand.nextFloat()));
        world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.AMBIENT, 0.33F, pitch);
    }
}
