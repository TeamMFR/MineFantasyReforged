package minefantasy.mfr.block.basic;

import minefantasy.mfr.init.MineFantasySounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockMythicOre extends BlockOreMF {
    private boolean isPure;

    public BlockMythicOre(String name, boolean pure) {
        super(name, 4, pure ? 3 : 2);
        isPure = pure;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (rand.nextInt(20) == 0 && world.isRemote) {
            // "minefantasy2:block.mythicore"
            world.playSound(null, pos, (isPure ? MineFantasySounds.MYTHIC_ORE : SoundEvents.ENTITY_PLAYER_LEVELUP), SoundCategory.NEUTRAL, 1.0F, rand.nextFloat() * 0.4F + 1.1F);
        }
    }
}
