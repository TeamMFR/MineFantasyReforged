package minefantasy.mfr.block;

import minefantasy.mfr.init.MineFantasySounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMythicOre extends BlockOreMF {
	private boolean isPure;

	public BlockMythicOre(String name, boolean pure) {
		super(name, 4, pure ? 3 : 2);
		isPure = pure;
		setTickRandomly(true);
	}

	@Override
	public int tickRate(World worldIn) {
		return 5;
	}

	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random rand) {
		world.playSound(null, pos, (isPure ? MineFantasySounds.MYTHIC_ORE : SoundEvents.ENTITY_PLAYER_LEVELUP), SoundCategory.NEUTRAL, 1.0F, rand.nextFloat() * 0.4F + 1.1F);
	}
}
