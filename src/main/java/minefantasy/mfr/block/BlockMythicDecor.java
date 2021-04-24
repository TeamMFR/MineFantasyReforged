package minefantasy.mfr.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockMythicDecor extends BasicBlockMF {

	public BlockMythicDecor(String name) {
		super(name, Material.ROCK);
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(2.0F);
		this.setResistance(2.0F);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}
}
