package minefantasy.mfr.world.gen.feature;

import minefantasy.mfr.init.MineFantasyBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenMFTree extends WorldGenAbstractTree {
    /**
     * Contains three sets of two values that provide complimentary indices for a
     * given 'major' index - 1 and 2 for 0, 0 and 2 for 1, and 0 and 1 for 2.
     */
    static final byte[] otherCoordPairs = new byte[]{(byte) 2, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 1};
    private final Block log, leaves;
    /**
     * Reference to the World object.
     */
    World world;
    BlockPos basePos = new BlockPos(0, 0, 0);
    int heightLimit;
    int height;
    double heightAttenuation = 0.618D;
    double branchDensity = 1.0D;
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
    int heightLimitLimit = 12;
    /**
     * Sets the distance limit for how far away the generator will populate leaves
     * from the base leaf node.
     */
    int leafDistanceLimit = 4;
    /**
     * Contains a list of a points at which to generate groups of leaves.
     */
    int[][] leafNodes;
    private int leafMeta = 0;

    public WorldGenMFTree(boolean update, Block log, Block leaves) {
        super(update);
        this.log = log;
        this.leaves = leaves;
        if (log == MineFantasyBlocks.LOG_IRONBARK) {
            leafDistanceLimit = 2;
        }
        heightLimitLimit = log == MineFantasyBlocks.LOG_EBONY ? 10 : 8;
    }

    public WorldGenMFTree setSaplingGrown() {
        leafMeta = 1;
        return this;
    }

    public WorldGenMFTree setLimits(int height, int leaf) {
        heightLimitLimit = height;
        leafDistanceLimit = leaf;
        return this;
    }

    /**
     * Generates a list of leaf nodes for the tree, to be populated by
     * generateLeaves.
     */
    void generateLeafNodeList() {
        this.height = (int) (this.heightLimit * this.heightAttenuation);

        if (this.height >= this.heightLimit) {
            this.height = this.heightLimit - 1;
        }

        int i = (int) (1.382D + Math.pow(this.leafDensity * this.heightLimit / 13.0D, 2.0D));

        if (i < 1) {
            i = 1;
        }

        int[][] aint = new int[i * this.heightLimit][4];
        int j = this.basePos.getY() + this.heightLimit - this.leafDistanceLimit;
        int k = 1;
        int l = this.basePos.getY() + this.height;
        int i1 = j - this.basePos.getY();
        aint[0][0] = this.basePos.getX();
        aint[0][1] = j;
        aint[0][2] = this.basePos.getZ();
        aint[0][3] = l;
        --j;

        while (i1 >= 0) {
            int j1 = 0;
            float f = this.layerSize(i1);

            if (f < 0.0F) {
                --j;
                --i1;
            } else {
                for (double d0 = 0.5D; j1 < i; ++j1) {
                    double d1 = this.scaleWidth * f * (world.rand.nextFloat() + 0.328D);
                    double d2 = world.rand.nextFloat() * 2.0D * Math.PI;
                    int k1 = MathHelper.floor(d1 * Math.sin(d2) + this.basePos.getX() + d0);
                    int l1 = MathHelper.floor(d1 * Math.cos(d2) + this.basePos.getZ() + d0);
                    int[] aint1 = new int[]{k1, j, l1};
                    int[] aint2 = new int[]{k1, j + this.leafDistanceLimit, l1};

                    if (this.checkBlockLine(aint1, aint2) == -1) {
                        int[] aint3 = new int[]{this.basePos.getX(), this.basePos.getY(), this.basePos.getZ()};
                        double d3 = Math.sqrt(Math.pow(Math.abs(this.basePos.getX() - aint1[0]), 2.0D)
                                + Math.pow(Math.abs(this.basePos.getZ() - aint1[2]), 2.0D));
                        double d4 = d3 * this.branchSlope;

                        if (aint1[1] - d4 > l) {
                            aint3[1] = l;
                        } else {
                            aint3[1] = (int) (aint1[1] - d4);
                        }

                        if (this.checkBlockLine(aint3, aint1) == -1) {
                            aint[k][0] = k1;
                            aint[k][1] = j;
                            aint[k][2] = l1;
                            aint[k][3] = aint3[1];
                            ++k;
                        }
                    }
                }

                --j;
                --i1;
            }
        }

        this.leafNodes = new int[k][4];
        System.arraycopy(aint, 0, this.leafNodes, 0, k);
    }

    void generateLeaf(BlockPos pos, float f, byte b, Block block) {
        int l = (int) (f + 0.618D);
        byte b1 = otherCoordPairs[b];
        byte b2 = otherCoordPairs[b + 3];
        int[] aint = new int[]{pos.getX(), pos.getY(), pos.getZ()};
        int[] aint1 = new int[]{0, 0, 0};
        int i1 = -l;
        int j1 = -l;

        for (aint1[b] = aint[b]; i1 <= l; ++i1) {
            aint1[b1] = aint[b1] + i1;
            j1 = -l;

            while (j1 <= l) {
                double d0 = Math.pow(Math.abs(i1) + 0.5D, 2.0D) + Math.pow(Math.abs(j1) + 0.5D, 2.0D);

                if (d0 > f * f) {
                    ++j1;
                } else {
                    aint1[b2] = aint[b2] + j1;
                    BlockPos aint1Pos = new BlockPos(aint1[0], aint1[1], aint1[2]);
                    IBlockState state = this.world.getBlockState(aint1Pos);
                    Block block1 = this.world.getBlockState(aint1Pos).getBlock();

                    if (!block1.isAir(state, world, aint1Pos) && !block1.isLeaves(state, world, aint1Pos)) {
                        ++j1;
                    } else {
                        this.setBlockAndNotifyAdequately(this.world, aint1Pos, state);
                        ++j1;
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
    void generateLeafNode(BlockPos pos) {
        int l = pos.getY();

        for (int i1 = pos.getY() + this.leafDistanceLimit; l < i1; ++l) {
            float f = this.leafSize(l - pos.getY());
            this.generateLeaf(pos, f, (byte) 1, this.leaves);
        }
    }

    void generateTrunk(int[] x, int[] y, Block block) {
        int[] aint2 = new int[]{0, 0, 0};
        byte b0 = 0;
        byte b1;

        for (b1 = 0; b0 < 3; ++b0) {
            aint2[b0] = y[b0] - x[b0];

            if (Math.abs(aint2[b0]) > Math.abs(aint2[b1])) {
                b1 = b0;
            }
        }

        if (aint2[b1] != 0) {
            byte b2 = otherCoordPairs[b1];
            byte b3 = otherCoordPairs[b1 + 3];
            byte b4;

            if (aint2[b1] > 0) {
                b4 = 1;
            } else {
                b4 = -1;
            }

            double d0 = (double) aint2[b2] / (double) aint2[b1];
            double d1 = (double) aint2[b3] / (double) aint2[b1];
            int[] aint3 = new int[]{0, 0, 0};
            int i = 0;

            for (int j = aint2[b1] + b4; i != j; i += b4) {
                aint3[b1] = MathHelper.floor(x[b1] + i + 0.5D);
                aint3[b2] = MathHelper.floor(x[b2] + i * d0 + 0.5D);
                aint3[b3] = MathHelper.floor(x[b3] + i * d1 + 0.5D);
                byte b5 = 0;
                int k = Math.abs(aint3[0] - x[0]);
                int l = Math.abs(aint3[2] - x[2]);
                int i1 = Math.max(k, l);

                if (i1 > 0) {
                    if (k == i1) {
                        b5 = 4;
                    } else if (l == i1) {
                        b5 = 8;
                    }
                }

                this.setBlockAndNotifyAdequately(this.world, new BlockPos(aint3[0], aint3[1], aint3[2]), block.getDefaultState());
            }
        }
    }

    /**
     * Generates the leaf portion of the tree as specified by the leafNodes list.
     */
    void generateLeaves() {
        int i = 0;

        for (int j = this.leafNodes.length; i < j; ++i) {
            int k = this.leafNodes[i][0];
            int l = this.leafNodes[i][1];
            int i1 = this.leafNodes[i][2];
            this.generateLeafNode(new BlockPos(k, l, i1));
        }
    }

    /**
     * Indicates whether or not a leaf node requires additional wood to be added to
     * preserve integrity.
     */
    boolean leafNodeNeedsBase(int p_76493_1_) {
        return p_76493_1_ >= this.heightLimit * 0.2D;
    }

    /**
     * Places the trunk for the big tree that is being generated. Able to generate
     * double-sized trunks by changing a field that is always 1 to 2.
     */
    void generateTrunk() {
        int i = this.basePos.getX();
        int j = this.basePos.getY();
        int k = this.basePos.getY() + this.height;
        int l = this.basePos.getZ();
        int[] aint = new int[]{i, j, l};
        int[] aint1 = new int[]{i, k, l};
        this.generateTrunk(aint, aint1, this.log);

        if (this.trunkSize == 2) {
            ++aint[0];
            ++aint1[0];
            this.generateTrunk(aint, aint1, this.log);
            ++aint[2];
            ++aint1[2];
            this.generateTrunk(aint, aint1, this.log);
            aint[0] += -1;
            aint1[0] += -1;
            this.generateTrunk(aint, aint1, this.log);
        }
    }

    /**
     * Generates additional wood blocks to fill out the bases of different leaf
     * nodes that would otherwise degrade.
     */
    void generateLeafNodeBases() {
        int i = 0;
        int j = this.leafNodes.length;

        for (int[] aint = new int[]{this.basePos.getX(), this.basePos.getY(), this.basePos.getZ()}; i < j; ++i) {
            int[] aint1 = this.leafNodes[i];
            int[] aint2 = new int[]{aint1[0], aint1[1], aint1[2]};
            aint[1] = aint1[3];
            int k = aint[1] - this.basePos.getY();

            if (this.leafNodeNeedsBase(k)) {
                this.generateTrunk(aint, aint2, this.log);
            }
        }
    }

    /**
     * Checks a line of blocks in the world from the first coordinate to triplet to
     * the second, returning the distance (in blocks) before a non-air, non-leaf
     * block is encountered and/or the end is encountered.
     */
    int checkBlockLine(int[] intArray1, int[] intArray2) {
        int[] aint2 = new int[]{0, 0, 0};
        byte b0 = 0;
        byte b1;

        for (b1 = 0; b0 < 3; ++b0) {
            aint2[b0] = intArray2[b0] - intArray1[b0];

            if (Math.abs(aint2[b0]) > Math.abs(aint2[b1])) {
                b1 = b0;
            }
        }

        if (aint2[b1] == 0) {
            return -1;
        } else {
            byte b2 = otherCoordPairs[b1];
            byte b3 = otherCoordPairs[b1 + 3];
            byte b4;

            if (aint2[b1] > 0) {
                b4 = 1;
            } else {
                b4 = -1;
            }

            double d0 = (double) aint2[b2] / (double) aint2[b1];
            double d1 = (double) aint2[b3] / (double) aint2[b1];
            int[] aint3 = new int[]{0, 0, 0};
            int i = 0;
            int j;

            for (j = aint2[b1] + b4; i != j; i += b4) {
                aint3[b1] = intArray1[b1] + i;
                aint3[b2] = MathHelper.floor(intArray1[b2] + i * d0);
                aint3[b3] = MathHelper.floor(intArray1[b3] + i * d1);

                if (!this.isReplaceable(world, new BlockPos(aint3[0], aint3[1], aint3[2]))) {
                    break;
                }
            }

            return i == j ? -1 : Math.abs(i);
        }
    }

    /**
     * Returns a boolean indicating whether or not the current location for the
     * tree, spanning basePos to to the height limit, is valid.
     */
    boolean validTreeLocation() {
        int[] aint = new int[]{this.basePos.getX(), this.basePos.getY(), this.basePos.getZ()};
        int[] aint1 = new int[]{this.basePos.getX(), this.basePos.getY() + this.heightLimit - 1, this.basePos.getZ()};
        BlockPos validateTreePos = this.basePos.add(0, -1, 0);
        IBlockState state = this.world.getBlockState(validateTreePos);

        boolean isSoil = state.getBlock().canSustainPlant(state, world, validateTreePos, EnumFacing.UP, (BlockSapling) Blocks.SAPLING);
        if (!isSoil) {
            return false;
        } else {
            int i = this.checkBlockLine(aint, aint1);

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

    /**
     * Rescales the generator settings, only used in WorldGenBigTree
     */
    public void setScale(double p_76487_1_, double p_76487_3_, double p_76487_5_) {
        this.heightLimitLimit = (int) (p_76487_1_ * 12.0D);

        if (p_76487_1_ > 0.5D) {
            this.leafDistanceLimit = 5;
        }

        this.scaleWidth = p_76487_3_;
        this.leafDensity = p_76487_5_;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        this.world = world;
        long l = rand.nextLong();
        world.rand.setSeed(l);

        if (this.heightLimit == 0) {
            this.heightLimit = 5 + world.rand.nextInt(this.heightLimitLimit);
        }

        if (!this.validTreeLocation()) {
            this.world = null; // Fix vanilla Mem leak, holds latest world
            return false;
        } else {
            this.generateLeafNodeList();
            this.generateLeaves();
            this.generateTrunk();
            this.generateLeafNodeBases();
            this.world.setBlockState(pos, log.getDefaultState());
            this.world = null; // Fix vanilla Mem leak, holds latest world
            return true;
        }
    }
}