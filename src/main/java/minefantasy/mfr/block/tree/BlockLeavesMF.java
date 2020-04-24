package minefantasy.mfr.block.tree;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.init.BlockListMFR;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + name);
        this.dropRate = droprate;
        this.setTickRandomly(true);
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return Blocks.LEAVES.shouldSideBeRendered(state, blockAccess, pos, side);
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
        return this == BlockListMFR.LEAVES_EBONY ? BlockListMFR.SAPLING_EBONY : this == BlockListMFR.LEAVES_IRONBARK ? BlockListMFR.SAPLING_IRONBARK : BlockListMFR.SAPLING_YEW;
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