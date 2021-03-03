package minefantasy.mfr.block;

import minefantasy.mfr.material.BaseMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockMetalMF extends Block {
	public BlockMetalMF(BaseMaterial material) {
		super(Material.IRON);

		String name = material.name.toLowerCase() + "_block";

		setRegistryName(name);
		setUnlocalizedName(name);
		this.setHarvestLevel("pickaxe", material.harvestLevel);
		this.setSoundType(SoundType.METAL);
		this.setHardness(material.hardness + 1 / 2F);
		this.setResistance(material.hardness + 1);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}
}