package minefantasy.mf2.item.list;

import minefantasy.mf2.api.armour.ArmourDesign;
import minefantasy.mf2.item.armour.ItemArmourMF;
import minefantasy.mf2.item.armour.ItemCustomArmour;
import net.minecraft.creativetab.CreativeTabs;

public class CustomArmourListMF {
    public static ItemArmourMF standard_scale_helmet, standard_scale_chest, standard_scale_legs, standard_scale_boots;
    public static ItemArmourMF standard_chain_helmet, standard_chain_chest, standard_chain_legs, standard_chain_boots;
    public static ItemArmourMF standard_splint_helmet, standard_splint_chest, standard_splint_legs,
            standard_splint_boots;
    public static ItemArmourMF standard_plate_helmet, standard_plate_chest, standard_plate_legs, standard_plate_boots;

    //public static ItemHorseArmorMF standart_plate_horse_armor;

    public static void load() {
        String design = "standard";
        CreativeTabs tab = CreativeTabMF.tabArmour;
        standard_scale_helmet = (ItemArmourMF) new ItemCustomArmour(design, "scale_helmet", ArmourDesign.SCALEMAIL, 0,
                "scale_layer_1", 0).setCreativeTab(tab);
        standard_scale_chest = (ItemArmourMF) new ItemCustomArmour(design, "scale_chest", ArmourDesign.SCALEMAIL, 1,
                "scale_layer_1", 0).setCreativeTab(tab);
        standard_scale_legs = (ItemArmourMF) new ItemCustomArmour(design, "scale_legs", ArmourDesign.SCALEMAIL, 2,
                "scale_layer_2", 0).setCreativeTab(tab);
        standard_scale_boots = (ItemArmourMF) new ItemCustomArmour(design, "scale_boots", ArmourDesign.SCALEMAIL, 3,
                "scale_layer_1", 0).setCreativeTab(tab);

        standard_chain_helmet = (ItemArmourMF) new ItemCustomArmour(design, "chain_helmet", ArmourDesign.CHAINMAIL, 0,
                "chain_layer_1", 0).setCreativeTab(tab);
        standard_chain_chest = (ItemArmourMF) new ItemCustomArmour(design, "chain_chest", ArmourDesign.CHAINMAIL, 1,
                "chain_layer_1", 0).setCreativeTab(tab);
        standard_chain_legs = (ItemArmourMF) new ItemCustomArmour(design, "chain_legs", ArmourDesign.CHAINMAIL, 2,
                "chain_layer_2", 0).setCreativeTab(tab);
        standard_chain_boots = (ItemArmourMF) new ItemCustomArmour(design, "chain_boots", ArmourDesign.CHAINMAIL, 3,
                "chain_layer_1", 0).setCreativeTab(tab);

        standard_splint_helmet = (ItemArmourMF) new ItemCustomArmour(design, "splint_helmet", ArmourDesign.SPLINTMAIL,
                0, "splint_layer_1", 0).setCreativeTab(tab);
        standard_splint_chest = (ItemArmourMF) new ItemCustomArmour(design, "splint_chest", ArmourDesign.SPLINTMAIL, 1,
                "splint_layer_1", 0).setCreativeTab(tab);
        standard_splint_legs = (ItemArmourMF) new ItemCustomArmour(design, "splint_legs", ArmourDesign.SPLINTMAIL, 2,
                "splint_layer_2", 0).setCreativeTab(tab);
        standard_splint_boots = (ItemArmourMF) new ItemCustomArmour(design, "splint_boots", ArmourDesign.SPLINTMAIL, 3,
                "splint_layer_1", 0).setCreativeTab(tab);

        standard_plate_helmet = (ItemArmourMF) new ItemCustomArmour(design, "plate_helmet", ArmourDesign.FIELDPLATE, 0,
                "plate_layer_1", 0).setCreativeTab(tab);
        standard_plate_chest = (ItemArmourMF) new ItemCustomArmour(design, "plate_chest", ArmourDesign.FIELDPLATE, 1,
                "plate_layer_1", 0).setCreativeTab(tab);
        standard_plate_legs = (ItemArmourMF) new ItemCustomArmour(design, "plate_legs", ArmourDesign.FIELDPLATE, 2,
                "plate_layer_2", 0).setCreativeTab(tab);
        standard_plate_boots = (ItemArmourMF) new ItemCustomArmour(design, "plate_boots", ArmourDesign.FIELDPLATE, 3,
                "plate_layer_1", 0).setCreativeTab(tab);

        //standart_plate_horse_armor = new ItemHorseArmorMF("horse_armor", BaseMaterialMF.getMaterial("steel"), ArmourDesign.FIELDPLATE, "plate_layer_1", 0);
    }
}
