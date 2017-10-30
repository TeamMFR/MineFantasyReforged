package minefantasy.mf2.item;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.item.list.CreativeTabMF;

public class ItemRawOreMF extends ItemComponentMF {
    public ItemRawOreMF(String name, int rarity) {
        super(rarity);
        setTextureName("minefantasy2:oreItem/" + name);
        this.setCreativeTab(CreativeTabMF.tabMaterialsMF);
        GameRegistry.registerItem(this, "MF_Ore_" + name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
    }
}
