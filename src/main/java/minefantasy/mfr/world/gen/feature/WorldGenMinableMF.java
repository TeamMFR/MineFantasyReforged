package minefantasy.mfr.world.gen.feature;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenMinableMF extends WorldGenerator {
	private static final String __OBFID = "CL_00000426";
	private Block oreBlock;
	/**
	 * The number of blocks to generate.
	 */
	private int numberOfBlocks;
	private Predicate<IBlockState> beddingBlock;
	private int mineableBlockMeta;

	public WorldGenMinableMF(Block ore, int size) {
		this(ore, size, BlockMatcher.forBlock(Blocks.STONE));
	}

	public WorldGenMinableMF(Block ore, int size, Predicate<IBlockState> bed) {
		this.oreBlock = ore;
		this.numberOfBlocks = size;
		this.beddingBlock = bed;
	}

	public WorldGenMinableMF(Block block, int meta, int number, Predicate<IBlockState> target) {
		this(block, number, target);
		this.mineableBlockMeta = meta;
	}

	@Override
	public boolean generate(World world, Random seed, BlockPos pos) {
		boolean success = false;

		float f = seed.nextFloat() * (float) Math.PI;
		double d0 = pos.getX() + 8 + MathHelper.sin(f) * this.numberOfBlocks / 8.0F;
		double d1 = pos.getX() + 8 - MathHelper.sin(f) * this.numberOfBlocks / 8.0F;
		double d2 = pos.getZ() + 8 + MathHelper.cos(f) * this.numberOfBlocks / 8.0F;
		double d3 = pos.getZ() + 8 - MathHelper.cos(f) * this.numberOfBlocks / 8.0F;
		double d4 = pos.getY() + seed.nextInt(3) - 2;
		double d5 = pos.getY() + seed.nextInt(3) - 2;

		for (int l = 0; l <= this.numberOfBlocks; ++l) {
			double d6 = d0 + (d1 - d0) * l / this.numberOfBlocks;
			double d7 = d4 + (d5 - d4) * l / this.numberOfBlocks;
			double d8 = d2 + (d3 - d2) * l / this.numberOfBlocks;
			double d9 = seed.nextDouble() * this.numberOfBlocks / 16.0D;
			double d10 = (MathHelper.sin(l * (float) Math.PI / this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
			double d11 = (MathHelper.sin(l * (float) Math.PI / this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
			int i1 = MathHelper.floor(d6 - d10 / 2.0D);
			int j1 = MathHelper.floor(d7 - d11 / 2.0D);
			int k1 = MathHelper.floor(d8 - d10 / 2.0D);
			int l1 = MathHelper.floor(d6 + d10 / 2.0D);
			int i2 = MathHelper.floor(d7 + d11 / 2.0D);
			int j2 = MathHelper.floor(d8 + d10 / 2.0D);

			for (int oreX = i1; oreX <= l1; ++oreX) {
				double d12 = (oreX + 0.5D - d6) / (d10 / 2.0D);

				if (d12 * d12 < 1.0D) {
					for (int oreY = j1; oreY <= i2; ++oreY) {
						double d13 = (oreY + 0.5D - d7) / (d11 / 2.0D);

						if (d12 * d12 + d13 * d13 < 1.0D) {
							for (int oreZ = k1; oreZ <= j2; ++oreZ) {
								BlockPos orePos = new BlockPos(oreX, oreY, oreZ);
								double d14 = (oreZ + 0.5D - d8) / (d10 / 2.0D);

								if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && world.getBlockState(orePos).getBlock().isReplaceableOreGen(world.getBlockState(orePos), world, orePos, beddingBlock)) {
									success = true;
									world.setBlockState(orePos, this.oreBlock.getDefaultState(), 2);
								}
							}
						}
					}
				}
			}
		}

		return success;
	}
}