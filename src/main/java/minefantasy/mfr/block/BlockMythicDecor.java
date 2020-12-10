package minefantasy.mfr.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockMythicDecor extends Block {

	public BlockMythicDecor(String name) {
		super(Material.ROCK);
		setRegistryName(name);
		setUnlocalizedName(name);
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(2.0F);
		this.setResistance(2.0F);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
	}
}
