package minefantasy.mfr.block.tree;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.init.BlockListMFR;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.mechanics.worldGen.WorldGenMFTree;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BlockSaplingMF extends BlockBush implements IGrowable {
    private final Block log, leaves;
    private float growthModifier;
    private String name;

    public BlockSaplingMF(String baseWood, Block log, Block leaves, float growthModifier) {
        super(Material.PLANTS);
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + name);
        setSoundType(SoundType.GROUND);
        float f = 0.4F;
        new AxisAlignedBB(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.growthModifier = growthModifier;
        name = baseWood.toLowerCase() + "_sapling";
        this.log = log;
        this.leaves = leaves;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!world.isRemote) {
            super.updateTick(world, pos, state, rand);

            if (world.getLight(pos.add(0,1,0)) >= 9 && rand.nextFloat() * 100F < (14.30F) / growthModifier) {
                this.initGrow(world, pos, rand);
            }
        }
    }

    public void initGrow(World world, BlockPos pos, Random rand) {
        IBlockState state = world.getBlockState(pos);

        if (state == this.getDefaultState()) {
            world.setBlockState(pos, state, 4);
        } else {
            this.tryGrow(world, pos, rand);
        }
    }

    public void tryGrow(World world, BlockPos pos, Random rand) {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, pos))
            return;
        IBlockState state = world.getBlockState(pos);
        Object treegen = new WorldGenMFTree(true, log, leaves);

        boolean flag = false;

        IBlockState newBlock = (Blocks.AIR).getDefaultState();
        world.setBlockState(pos, newBlock, 1);

        if (!((WorldGenerator) treegen).generate(world, rand, pos)) {
            if (flag) {
                world.setBlockState(pos, state, 4);
                world.setBlockState(pos, state, 4);
                world.setBlockState(pos.add(0,0,1), state, 4);
                world.setBlockState(pos.add(1,0,1), state, 4);
            } else {
                world.setBlockState(pos, state, 4);
            }
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(IBlockState state) {
        return MathHelper.clamp(state.hashCode() & 7, 0, 5);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return false;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {

    }
}