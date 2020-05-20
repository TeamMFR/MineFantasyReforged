package minefantasy.mfr.block.basic;

import minefantasy.mfr.MineFantasyReborn;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockMeta extends Block {
    protected final String[] names;
    @SideOnly(Side.CLIENT)

    public BlockMeta(String name, Material material, String... names) {
        super(material);
        this.names = names;

        setRegistryName(name);
        setUnlocalizedName(name);
        if (material == Material.ROCK) {
            this.setHarvestLevel("pickaxe", 0);
        }
        this.setHardness(2.0F);
        this.setResistance(2.0F);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    public String getUnlocalisedName(int meta) {
        return "tile." + names[Math.min(meta, names.length - 1)];
    }

    public int getCount() {
        return names.length;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getBlock().damageDropped(state);
    }
}
