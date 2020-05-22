package minefantasy.mfr.item;

import minefantasy.mfr.init.CreativeTabMFR;

public class ItemRawOreMF extends ItemComponentMFR {
    public ItemRawOreMF(String name, int rarity) {
        super(name, rarity);
        this.setCreativeTab(CreativeTabMFR.tabMaterialsMFR);
    }
}
