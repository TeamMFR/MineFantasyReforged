package minefantasy.mfr.block;

import minefantasy.mfr.material.BaseMaterial;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockMetalMF extends BasicBlockMF{
	public BlockMetalMF(BaseMaterial material) {
		super(material.name.toLowerCase() + "_block", Material.IRON);

		this.setHarvestLevel("pickaxe", material.harvestLevel);
		this.setSoundType(SoundType.METAL);
		this.setHardness(material.hardness + 1 / 2F);
		this.setResistance(material.hardness + 1);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}
}