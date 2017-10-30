package minefantasy.mf2.block.basic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockReinforcedStone extends BlockMeta {
    private String currentName;

    public BlockReinforcedStone(String name, String... alternates) {
        super(name, Material.rock, alternates);
        this.currentName = name;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side < 2 && meta < names.length
                && (this.names[meta].startsWith("dshall") || this.names[meta].equalsIgnoreCase("engraved"))) {
            return icons[0];
        }
        return super.getIcon(side, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        icons = new IIcon[names.length];
        for (int i = 0; i < names.length; i++) {
            icons[i] = reg.registerIcon("minefantasy2:meta/" + currentName + "_" + names[i]);
        }
    }

    @Override
    public String getUnlocalisedName(int meta) {
        if (meta < names.length
                && (this.names[meta].startsWith("dshall") || this.names[meta].equalsIgnoreCase("engraved"))) {
            return "tile." + currentName + "_" + names[1];
        }
        return "tile." + currentName + "_" + names[Math.min(meta, names.length - 1)];
    }
}
