package minefantasy.mfr.block.tree;

import minefantasy.mfr.init.MineFantasyBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class BlockLeavesMF extends BlockLeaves implements IShearable {


    private String name;
    private Block sapling;
    private int dropRate;
    @SideOnly(Side.CLIENT)

    public BlockLeavesMF(String baseWood) {
        this(baseWood, 20);
    }

    public BlockLeavesMF(String baseWood, int droprate) {
        this.name = baseWood.toLowerCase() + "_leaves";

        setRegistryName(name);
        setUnlocalizedName(name);
        this.dropRate = droprate;
        this.setTickRandomly(true);
        setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return Blocks.LEAVES.shouldSideBeRendered(state, blockAccess, pos, side);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {CHECK_DECAY, DECAYABLE});
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        if (!state.getValue(DECAYABLE).booleanValue())
        {
            i |= 4;
        }

        if (state.getValue(CHECK_DECAY).booleanValue())
        {
            i |= 8;
        }

        return i;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return null;
    }

    @Override
    public int quantityDropped(Random rand) {
        return 1;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(getBlockDrop());
    }

    @Override
    protected int getSaplingDropChance(IBlockState state) {
        return state == this.blockState ? dropRate * 2 : dropRate;
    }

    private Block getBlockDrop() {
        return this == MineFantasyBlocks.LEAVES_EBONY ? MineFantasyBlocks.SAPLING_EBONY : this == MineFantasyBlocks.LEAVES_IRONBARK ? MineFantasyBlocks.SAPLING_IRONBARK : MineFantasyBlocks.SAPLING_YEW;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return null;
    }
}