package minefantasy.mfr.block.tree;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.init.BlockListMFR;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

public class BlockLogMF extends BlockLog {
    private String name;
    private Random rand = new Random();

    public BlockLogMF(String baseWood) {
        name = baseWood.toLowerCase() + "_log";
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MODID + "." + name);
        this.setHarvestLevel("axe", 0);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    private Block getSaplingDrop() {
        return this == BlockListMFR.LOG_EBONY ? BlockListMFR.SAPLING_EBONY
                : this == BlockListMFR.LOG_IRONBARK ? BlockListMFR.SAPLING_IRONBARK : BlockListMFR.SAPLING_YEW;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);
        if (state == this.blockState) {
            float f = this.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
            float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

            EntityItem entityitem = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, new ItemStack(getSaplingDrop(), 1));

            float f3 = 0.05F;
            entityitem.motionX = (float) this.rand.nextGaussian() * f3;
            entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
            entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
            world.spawnEntity(entityitem);
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }
}
