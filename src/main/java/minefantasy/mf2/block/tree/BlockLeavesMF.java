package minefantasy.mf2.block.tree;

import java.util.Random;

import minefantasy.mf2.block.list.BlockListMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLeavesMF extends BlockLeaves implements IShearable
{
	private String name;
	private Block sapling;
	private float dropRate;
    public BlockLeavesMF(String baseWood)
    {
   		this(baseWood, 5F);
    }
    public BlockLeavesMF(String baseWood, float droprate)
    {
        super();
        this.setTickRandomly(true);
        name = baseWood.toLowerCase() + "_leaves";
		GameRegistry.registerBlock(this, name);
		this.dropRate = droprate;
		setBlockName(name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderColor(int id)
    {
        return ColorizerFoliage.getFoliageColorBasic();
    }
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        return Blocks.leaves.shouldSideBeRendered(world, x, y, z, side);
    }
    @Override
    public boolean isOpaqueCube()
    {
        return Blocks.leaves.isOpaqueCube();
    }

     @Override
    public int quantityDropped(Random rand)
    {
        return rand.nextFloat()*100F < dropRate ? 1 : 0;
    }

	@Override
    public Item getItemDropped(int meta, Random rand, int fort)
    {
        return Item.getItemFromBlock(getBlockDrop());
    }

	@Override
	protected int func_150123_b(int meta)
    {
        return meta == 15 ? 40 : 20;
    }
	
	private Block getBlockDrop()
	{
		return this == BlockListMF.leaves_ebony ? BlockListMF.sapling_ebony : this == BlockListMF.leaves_ironbark ? BlockListMF.sapling_ironbark : BlockListMF.sapling_yew;
	}
	@Override
    public int damageDropped(int meta)
    {
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
    	return Blocks.leaves.getIcon(side, meta);
    }
	@Override
	public String[] func_150125_e() 
	{
		return new String[]{""};
	}
}