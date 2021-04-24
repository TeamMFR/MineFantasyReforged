package minefantasy.mfr.block;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.material.BaseMaterial;
import minefantasy.mfr.proxy.IClientRegister;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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