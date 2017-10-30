package minefantasy.mf2.block.basic;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockMetalMF extends Block {
    public BlockMetalMF(BaseMaterialMF material) {
        super(Material.iron);

        String name = material.name.toLowerCase() + "_block";
        GameRegistry.registerBlock(this, name);
        setBlockName(name);

        this.setBlockTextureName("minefantasy2:metal/" + name);
        this.setHarvestLevel("pickaxe", material.harvestLevel);
        this.setStepSound(Block.soundTypeMetal);
        this.setHardness(material.hardness + 1 / 2F);
        this.setResistance(material.hardness + 1);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}