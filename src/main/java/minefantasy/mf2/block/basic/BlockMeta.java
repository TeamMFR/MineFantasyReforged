package minefantasy.mf2.block.basic;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

public abstract class BlockMeta extends Block {
    protected final String[] names;
    @SideOnly(Side.CLIENT)
    protected IIcon[] icons;

    public BlockMeta(String name, Material material, String... names) {
        super(material);
        this.names = names;

        GameRegistry.registerBlock(this, ItemMetaBlock.class, name);

        if (material == Material.rock) {
            this.setHarvestLevel("pickaxe", 0);
        }
        this.setHardness(2.0F);
        this.setResistance(2.0F);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icons[Math.min(meta, icons.length - 1)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        icons = new IIcon[names.length];
        for (int i = 0; i < names.length; i++) {
            icons[i] = reg.registerIcon("minefantasy2:meta/" + names[i]);
        }
    }

    public String getUnlocalisedName(int meta) {
        return "tile." + names[Math.min(meta, names.length - 1)];
    }

    public int getCount() {
        return names.length;
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }
}
