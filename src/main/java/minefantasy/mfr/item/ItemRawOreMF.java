package minefantasy.mfr.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.init.CreativeTabMFR;

public class ItemRawOreMF extends ItemComponentMFR {
    public ItemRawOreMF(String name, int rarity) {
        super(rarity);
        this.setCreativeTab(CreativeTabMFR.tabMaterialsMFR);
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + name);
        GameRegistry.findRegistry(Item.class).register(this);
    }
}
