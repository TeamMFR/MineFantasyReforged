package minefantasy.mfr.block.basic;

import minefantasy.mfr.MineFantasyReborn;
import net.minecraft.block.SoundType;
import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockMetalMF extends Block {
    public BlockMetalMF(BaseMaterialMFR material) {
        super(Material.IRON);

        String name = material.name.toLowerCase() + "_block";

        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + name);
        this.setHarvestLevel("pickaxe", material.harvestLevel);
        this.setSoundType(SoundType.METAL);
        this.setHardness(material.hardness + 1 / 2F);
        this.setResistance(material.hardness + 1);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }
}