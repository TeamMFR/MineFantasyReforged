package minefantasy.mfr.block.basic;

import minefantasy.mfr.init.SoundsMFR;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.world.World;

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
            world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, (isPure ? SoundsMFR.MYTHIC_ORE : SoundEvents.ENTITY_PLAYER_LEVELUP), SoundCategory.NEUTRAL, 1.0F, rand.nextFloat() * 0.4F + 1.1F, true);
        }
    }
}
