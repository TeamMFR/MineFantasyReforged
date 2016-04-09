package minefantasy.mf2.block.basic;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BasicBlockMF extends Block 
{
	private Object drop;
	
	public BasicBlockMF(String name, Material material)
	{
		this(name, material, null);
	}
	public BasicBlockMF(String name, Material material, Object drop)
	{
		super(material);
		
		GameRegistry.registerBlock(this, name);
		setBlockName(name);
		setBlockTextureName("minefantasy2:basic/"+name);
		
		if(material == Material.rock)
		{
			this.setHarvestLevel("pickaxe", 0);
		}
		this.drop = drop;
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube()
	{
		return this.blockMaterial != Material.glass;
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int i)
    {
		if(drop != null)
		{
			if(drop instanceof Item)
			{
				return (Item) drop;
			}
			if(drop instanceof Block)
			{
				return Item.getItemFromBlock((Block) drop);
			}
		}
		return Item.getItemFromBlock(this);
    }
}
