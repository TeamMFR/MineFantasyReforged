package minefantasy.mfr.world.gen.feature;

import com.google.common.collect.Lists;
import minefantasy.mfr.block.BlockLogMF;
import minefantasy.mfr.init.MineFantasyBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.List;
import java.util.Random;

public class WorldGenMFTree extends WorldGenAbstractTree {
	/**
	 * Reference to the World object.
	 */
	Block LOG;
	Block LEAVES;
	World world;
	private Random rand;
	BlockPos basePos = BlockPos.ORIGIN;
	int heightLimit;
	int height;
	double heightAttenuation = 0.618D;
	double branchSlope = 0.381D;
	double scaleWidth = 1.0D;
	double leafDensity = 1.0D;
	/**
	 * Currently always 1, can be set to 2 in the class constructor to generate a
	 * double-sized tree trunk for big trees.
	 */
	int trunkSize = 1;
	/**
	 * Sets the limit of the random value used to initialize the height limit.
	 */
	int heightLimitLimit;
	/**
	 * Sets the distance limit for how far away the generator will populate leaves
	 * from the base leaf node.
	 */
	int leafDistanceLimit = 4;
	/**
	 * Contains a list of a points at which to generate groups of leaves.
	 */
	List<FoliageCoordinates> foliageCoords;

	public WorldGenMFTree(boolean update, Block log, Block leaves) {
		super(update);
		this.LOG = log;
		this.LEAVES = leaves;
		if (log == MineFantasyBlocks.LOG_IRONBARK) {
			leafDistanceLimit = 2;
		}
		heightLimitLimit = log == MineFantasyBlocks.LOG_EBONY ? 10 : 8;
	}

	/**
	 * Generates a list of leaf nodes for the tree, to be populated by generateLeaves.
	 */
	void generateLeafNodeList() {
		this.height = (int) ((double) this.heightLimit * this.heightAttenuation);

		if (this.height >= this.heightLimit) {
			this.height = this.heightLimit - 1;
		}

		int i = (int) (1.382D + Math.pow(this.leafDensity * (double) this.heightLimit / 13.0D, 2.0D));

		if (i < 1) {
			i = 1;
		}

		int j = this.basePos.getY() + this.height;
		int k = this.heightLimit - this.leafDistanceLimit;
		this.foliageCoords = Lists.<FoliageCoordinates>newArrayList();
		this.foliageCoords.add(new FoliageCoordinates(this.basePos.up(k), j));

		for (; k >= 0; --k) {
			float f = this.layerSize(k);

			if (f >= 0.0F) {
				for (int l = 0; l < i; ++l) {
					double d0 = this.scaleWidth * (double) f * ((double) this.rand.nextFloat() + 0.328D);
					double d1 = (double) (this.rand.nextFloat() * 2.0F) * Math.PI;
					double d2 = d0 * Math.sin(d1) + 0.5D;
					double d3 = d0 * Math.cos(d1) + 0.5D;
					BlockPos blockpos = this.basePos.add(d2, k - 1, d3);
					BlockPos blockpos1 = blockpos.up(this.leafDistanceLimit);

					if (this.checkBlockLine(blockpos, blockpos1) == -1) {
						int i1 = this.basePos.getX() - blockpos.getX();
						int j1 = this.basePos.getZ() - blockpos.getZ();
						double d4 = (double) blockpos.getY() - Math.sqrt(i1 * i1 + j1 * j1) * this.branchSlope;
						int k1 = d4 > (double) j ? j : (int) d4;
						BlockPos blockpos2 = new BlockPos(this.basePos.getX(), k1, this.basePos.getZ());

						if (this.checkBlockLine(blockpos2, blockpos) == -1) {
							this.foliageCoords.add(new FoliageCoordinates(blockpos, blockpos2.getY()));
						}
					}
				}
			}
		}
	}

	/**
	 * Gets the rough size of a layer of the tree.
	 */
	float layerSize(int layer) {
		if (layer < (this.heightLimit) * 0.3D) {
			return -1.618F;
		} else {
			float f = this.heightLimit / 2.0F;
			float f1 = this.heightLimit / 2.0F - layer;
			float f2;

			if (f1 == 0.0F) {
				f2 = f;
			} else if (Math.abs(f1) >= f) {
				f2 = 0.0F;
			} else {
				f2 = (float) Math.sqrt(Math.pow(Math.abs(f), 2.0D) - Math.pow(Math.abs(f1), 2.0D));
			}

			f2 *= 0.5F;
			return f2;
		}
	}

	float leafSize(int layer) {
		return layer >= 0 && layer < this.leafDistanceLimit
				? (layer != 0 && layer != this.leafDistanceLimit - 1 ? 3.0F : 2.0F)
				: -1.0F;
	}

	/**
	 * Generates the leaves surrounding an individual entry in the leafNodes list.
	 */
	void generateLeafNode(BlockPos pos, Block leaves) {
		for (int i = 0; i < this.leafDistanceLimit; ++i) {
			this.crosSection(pos.up(i), this.leafSize(i), leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE));
		}
	}

	void crosSection(BlockPos pos, float p_181631_2_, IBlockState p_181631_3_) {
		int i = (int) ((double) p_181631_2_ + 0.618D);

		for (int j = -i; j <= i; ++j) {
			for (int k = -i; k <= i; ++k) {
				if (Math.pow((double) Math.abs(j) + 0.5D, 2.0D) + Math.pow((double) Math.abs(k) + 0.5D, 2.0D) <= (double) (p_181631_2_ * p_181631_2_)) {
					BlockPos blockpos = pos.add(j, 0, k);
					IBlockState state = this.world.getBlockState(blockpos);

					if (state.getBlock().isAir(state, world, blockpos) || state.getBlock().isLeaves(state, world, blockpos)) {
						this.setBlockAndNotifyAdequately(this.world, blockpos, p_181631_3_);
					}
				}
			}
		}
	}

	void limb(BlockPos blockPos, BlockPos blockPos1, Block block) {
		BlockPos blockpos = blockPos1.add(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
		int i = this.getGreatestDistance(blockpos);
		float f = (float) blockpos.getX() / (float) i;
		float f1 = (float) blockpos.getY() / (float) i;
		float f2 = (float) blockpos.getZ() / (float) i;

		for (int j = 0; j <= i; ++j) {
			BlockPos blockpos1 = blockPos.add(0.5F + (float) j * f, 0.5F + (float) j * f1, 0.5F + (float) j * f2);
			BlockLog.EnumAxis logAxis = this.getLogAxis(blockPos, blockpos1);
			this.setBlockAndNotifyAdequately(this.world, blockpos1, block.getDefaultState().withProperty(BlockLog.LOG_AXIS, logAxis));
		}
	}

	/**
	 * Returns the absolute greatest distance in the BlockPos object.
	 */
	private int getGreatestDistance(BlockPos posIn) {
		int i = MathHelper.abs(posIn.getX());
		int j = MathHelper.abs(posIn.getY());
		int k = MathHelper.abs(posIn.getZ());

		if (k > i && k > j) {
			return k;
		} else {
			return Math.max(j, i);
		}
	}

	private BlockLog.EnumAxis getLogAxis(BlockPos blockPos, BlockPos blockPos1) {
		BlockLog.EnumAxis logAxis = BlockLog.EnumAxis.Y;
		int i = Math.abs(blockPos1.getX() - blockPos.getX());
		int j = Math.abs(blockPos1.getZ() - blockPos.getZ());
		int k = Math.max(i, j);

		if (k > 0) {
			if (i == k) {
				logAxis = BlockLog.EnumAxis.X;
			} else if (j == k) {
				logAxis = BlockLog.EnumAxis.Z;
			}
		}

		return logAxis;
	}

	/**
	 * Generates the leaf portion of the tree as specified by the leafNodes list.
	 */
	void generateLeaves(Block leaves) {
		for (FoliageCoordinates foliageCoordinates : this.foliageCoords) {
			this.generateLeafNode(foliageCoordinates, leaves);
		}
	}

	/**
	 * Indicates whether or not a leaf node requires additional wood to be added to
	 * preserve integrity.
	 */
	boolean leafNodeNeedsBase(int height) {
		return height >= this.heightLimit * 0.2D;
	}

	/**
	 * Places the trunk for the big tree that is being generated. Able to generate double-sized trunks by changing a
	 * field that is always 1 to 2.
	 */
	void generateTrunk(Block block) {
		BlockPos blockpos = this.basePos;
		BlockPos blockpos1 = this.basePos.up(this.height);
		this.limb(blockpos, blockpos1, block);

		if (this.trunkSize == 2) {
			this.limb(blockpos.east(), blockpos1.east(), block);
			this.limb(blockpos.east().south(), blockpos1.east().south(), block);
			this.limb(blockpos.south(), blockpos1.south(), block);
		}
	}

	/**
	 * Generates additional wood blocks to fill out the bases of different leaf nodes that would otherwise degrade.
	 */
	void generateLeafNodeBases(Block block) {
		for (FoliageCoordinates foliageCoordinates : this.foliageCoords) {
			int i = foliageCoordinates.getBranchBase();
			BlockPos blockpos = new BlockPos(this.basePos.getX(), i, this.basePos.getZ());

			if (!blockpos.equals(foliageCoordinates) && this.leafNodeNeedsBase(i - this.basePos.getY())) {
				this.limb(blockpos, foliageCoordinates, block);
			}
		}
	}

	/**
	 * Checks a line of blocks in the world from the first coordinate to triplet to the second, returning the distance
	 * (in blocks) before a non-air, non-leaf block is encountered and/or the end is encountered.
	 */
	int checkBlockLine(BlockPos posOne, BlockPos posTwo) {
		BlockPos blockpos = posTwo.add(-posOne.getX(), -posOne.getY(), -posOne.getZ());
		int i = this.getGreatestDistance(blockpos);
		float f = (float) blockpos.getX() / (float) i;
		float f1 = (float) blockpos.getY() / (float) i;
		float f2 = (float) blockpos.getZ() / (float) i;

		if (i == 0) {
			return -1;
		} else {
			for (int j = 0; j <= i; ++j) {
				BlockPos blockPos1 = posOne.add(0.5F + (float) j * f, 0.5F + (float) j * f1, 0.5F + (float) j * f2);

				if (!this.isReplaceable(world, blockPos1)) {
					return j;
				}
			}

			return -1;
		}
	}

	/**
	 * Returns a boolean indicating whether or not the current location for the tree, spanning basePos to to the height
	 * limit, is valid.
	 */
	private boolean validTreeLocation() {
		BlockPos down = this.basePos.down();
		net.minecraft.block.state.IBlockState state = this.world.getBlockState(down);
		boolean isSoil = state.getBlock().canSustainPlant(state, this.world, down, net.minecraft.util.EnumFacing.UP, ((net.minecraft.block.BlockSapling) Blocks.SAPLING));

		if (!isSoil) {
			return false;
		} else {
			int i = this.checkBlockLine(this.basePos, this.basePos.up(this.heightLimit - 1));

			if (i == -1) {
				return true;
			} else if (i < 6) {
				return false;
			} else {
				this.heightLimit = i;
				return true;
			}
		}
	}

	public boolean generate(World world, Random rand, BlockPos position) {
		this.world = world;
		this.basePos = position;
		this.rand = new Random(rand.nextLong());

		if (this.heightLimit == 0) {
			this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);
		}

		if (!this.validTreeLocation()) {
			this.world = null; //Fix vanilla Mem leak, holds latest world
			return false;
		} else {
			this.generateLeafNodeList();
			this.generateLeaves(LEAVES);
			this.generateTrunk(LOG);
			this.generateLeafNodeBases(LOG);
			world.setBlockState(position, LOG.getDefaultState().withProperty(BlockLogMF.TREE_BASE, true));
			this.world = null; //Fix vanilla Mem leak, holds latest world
			return true;
		}
	}

	static class FoliageCoordinates extends BlockPos {
		private final int branchBase;

		public FoliageCoordinates(BlockPos pos, int p_i45635_2_) {
			super(pos.getX(), pos.getY(), pos.getZ());
			this.branchBase = p_i45635_2_;
		}

		public int getBranchBase() {
			return this.branchBase;
		}
	}
}