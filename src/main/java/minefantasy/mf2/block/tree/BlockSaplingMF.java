package minefantasy.mf2.block.tree;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.mechanics.worldGen.WorldGenMFTree;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BlockSaplingMF extends BlockBush implements IGrowable {
    private final Block log, leaves;
    private float growthModifier;
    private String name;

    public BlockSaplingMF(String baseWood, Block log, Block leaves, float growthModifier) {
        super(Material.plants);
        setStepSound(Block.soundTypeGrass);
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.growthModifier = growthModifier;
        name = baseWood.toLowerCase() + "_sapling";
        this.log = log;
        this.leaves = leaves;

        setBlockTextureName("minefantasy2:tree/" + name);
        GameRegistry.registerBlock(this, name);
        setBlockName(name);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isRemote) {
            super.updateTick(world, x, y, z, rand);

            if (world.getBlockLightValue(x, y + 1, z) >= 9 && rand.nextFloat() * 100F < (14.30F) / growthModifier) {
                this.initGrow(world, x, y, z, rand);
            }
        }
    }

    public void initGrow(World world, int x, int y, int z, Random rand) {
        int l = world.getBlockMetadata(x, y, z);

        if ((l & 8) == 0) {
            world.setBlockMetadataWithNotify(x, y, z, l | 8, 4);
        } else {
            this.tryGrow(world, x, y, z, rand);
        }
    }

    public void tryGrow(World world, int x, int y, int z, Random rand) {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, x, y, z))
            return;
        int l = world.getBlockMetadata(x, y, z) & 7;
        Object treegen = new WorldGenMFTree(true, log, leaves);
        int i1 = 0;
        int j1 = 0;
        boolean flag = false;

        Block block = Blocks.air;
        world.setBlock(x, y, z, block, 0, 4);

        if (!((WorldGenerator) treegen).generate(world, rand, x + i1, y, z + j1)) {
            if (flag) {
                world.setBlock(x + i1, y, z + j1, this, l, 4);
                world.setBlock(x + i1 + 1, y, z + j1, this, l, 4);
                world.setBlock(x + i1, y, z + j1 + 1, this, l, 4);
                world.setBlock(x + i1 + 1, y, z + j1 + 1, this, l, 4);
            } else {
                world.setBlock(x, y, z, this, l, 4);
            }
        }
    }

    public boolean func_149880_a(World p_149880_1_, int p_149880_2_, int p_149880_3_, int p_149880_4_,
                                 int p_149880_5_) {
        return p_149880_1_.getBlock(p_149880_2_, p_149880_3_, p_149880_4_) == this
                && (p_149880_1_.getBlockMetadata(p_149880_2_, p_149880_3_, p_149880_4_) & 7) == p_149880_5_;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int p_149692_1_) {
        return MathHelper.clamp_int(p_149692_1_ & 7, 0, 5);
    }

    public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_,
                                 boolean p_149851_5_) {
        return true;
    }

    public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_,
                                 int p_149852_5_) {
        return p_149852_1_.rand.nextFloat() < (0.45D) / growthModifier;
    }

    public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_,
                              int p_149853_5_) {
        this.initGrow(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, p_149853_2_);
    }
}