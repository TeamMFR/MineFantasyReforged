package minefantasy.mf2.block.tree;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.block.list.BlockListMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockLogMF extends BlockLog {
    private IIcon sideTex, topTex;
    private String name;
    private Random rand = new Random();

    public BlockLogMF(String baseWood) {
        name = baseWood.toLowerCase() + "_log";
        GameRegistry.registerBlock(this, name);
        setBlockName(name);
        this.setHarvestLevel("axe", 0);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        if (meta == 15) {
            return side <= 1 ? topTex : sideTex;
        }
        return super.getIcon(side, meta);
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected IIcon getSideIcon(int meta) {
        return sideTex;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected IIcon getTopIcon(int meta) {
        return topTex;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        sideTex = reg.registerIcon("minefantasy2:tree/" + name + "_side");
        topTex = reg.registerIcon("minefantasy2:tree/" + name + "_top");
    }

    private Block getSaplingDrop() {
        return this == BlockListMF.log_ebony ? BlockListMF.sapling_ebony
                : this == BlockListMF.log_ironbark ? BlockListMF.sapling_ironbark : BlockListMF.sapling_yew;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        super.breakBlock(world, x, y, z, block, meta);
        if (meta == 15) {
            float f = this.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
            float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

            EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(getSaplingDrop(), 1));

            float f3 = 0.05F;
            entityitem.motionX = (float) this.rand.nextGaussian() * f3;
            entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
            entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
            world.spawnEntityInWorld(entityitem);
        }
    }

    @Override
    public int damageDropped(int meta) {
        return 0;
    }
}
