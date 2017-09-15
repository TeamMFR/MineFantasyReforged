package minefantasy.mf2.item.armour;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.armour.ArmourDesign;
import minefantasy.mf2.api.armour.ItemHorseArmorMFBase;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.material.BaseMaterialMF;

public class ItemHorseArmorMF extends ItemHorseArmorMFBase {

    private String name;
    protected BaseMaterialMF baseMaterial;
    private ArmourDesign armourDesign;
    private int itemRarity;


    public ItemHorseArmorMF(String name, BaseMaterialMF material, ArmourDesign design, String texture, int rarity) {
        super(name, material.getArmourConversion(), design, texture);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setCreativeTab(CreativeTabMF.tabArmour);
        this.setTextureName("minefantasy2:apparel/horse/" + design.getName().toLowerCase() + "/" + name);
        baseMaterial = material;
        itemRarity = rarity;
    }
}
