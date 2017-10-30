package minefantasy.mf2.block.tree;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.block.list.BlockListMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;

import java.util.Random;

public class BlockLeavesMF extends BlockLeaves implements IShearable {
    private String name;
    private Block sapling;
    private int dropRate;
    @SideOnly(Side.CLIENT)
    private IIcon opaque_icon;

    public BlockLeavesMF(String baseWood) {
        this(baseWood, 20);
    }

    public BlockLeavesMF(String baseWood, int droprate) {
        this.name = baseWood.toLowerCase() + "_leaves";
        GameRegistry.registerBlock(this, name);
        this.dropRate = droprate;
        this.setTickRandomly(true);
        this.setBlockName(name);
        this.setBlockTextureName("minefantasy2:tree/" + name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderColor(int id) {
        return ColorizerFoliage.getFoliageColorBasic();
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return Blocks.leaves.shouldSideBeRendered(world, x, y, z, side);
    }

    @Override
    public boolean isOpaqueCube() {
        return Blocks.leaves.isOpaqueCube();
    }

    @Override
    public int quantityDropped(Random rand) {
        return 1;
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int fort) {
        return Item.getItemFromBlock(getBlockDrop());
    }

    @Override
    protected int func_150123_b(int meta) {
        return meta == 15 ? dropRate * 2 : dropRate;
    }

    private Block getBlockDrop() {
        return this == BlockListMF.leaves_ebony ? BlockListMF.sapling_ebony
                : this == BlockListMF.leaves_ironbark ? BlockListMF.sapling_ironbark : BlockListMF.sapling_yew;
    }

    @Override
    public int damageDropped(int meta) {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        return Blocks.leaves.isOpaqueCube() ? opaque_icon : blockIcon;
    }

    @Override
    public String[] func_150125_e() {
        return new String[]{""};
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
        super.registerBlockIcons(reg);
        this.opaque_icon = reg.registerIcon(this.getTextureName() + "_opaque");
    }
}