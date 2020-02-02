package minefantasy.mfr.init;

import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.item.armour.ItemArmourMFR;
import minefantasy.mfr.item.armour.ItemCustomArmour;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;

public class CustomArmourListMFR {
    public static ItemArmourMFR standard_scale_helmet, standard_scale_chest, standard_scale_legs, standard_scale_boots;
    public static ItemArmourMFR standard_chain_helmet, standard_chain_chest, standard_chain_legs, standard_chain_boots;
    public static ItemArmourMFR standard_splint_helmet, standard_splint_chest, standard_splint_legs,
            standard_splint_boots;
    public static ItemArmourMFR standard_plate_helmet, standard_plate_chest, standard_plate_legs, standard_plate_boots;

    //public static ItemHorseArmorMF standart_plate_horse_armor;

    public static void load() {
        String design = "standard";
        CreativeTabs tab = CreativeTabMFR.tabArmour;
        standard_scale_helmet = (ItemArmourMFR) new ItemCustomArmour(design, "scale_helmet", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.HEAD,
                "scale_layer_1", 0).setCreativeTab(tab);
        standard_scale_chest = (ItemArmourMFR) new ItemCustomArmour(design, "scale_chest", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.CHEST,
                "scale_layer_1", 0).setCreativeTab(tab);
        standard_scale_legs = (ItemArmourMFR) new ItemCustomArmour(design, "scale_legs", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.LEGS,
                "scale_layer_2", 0).setCreativeTab(tab);
        standard_scale_boots = (ItemArmourMFR) new ItemCustomArmour(design, "scale_boots", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.FEET,
                "scale_layer_1", 0).setCreativeTab(tab);

        standard_chain_helmet = (ItemArmourMFR) new ItemCustomArmour(design, "chain_helmet", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.HEAD,
                "chain_layer_1", 0).setCreativeTab(tab);
        standard_chain_chest = (ItemArmourMFR) new ItemCustomArmour(design, "chain_chest", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.CHEST,
                "chain_layer_1", 0).setCreativeTab(tab);
        standard_chain_legs = (ItemArmourMFR) new ItemCustomArmour(design, "chain_legs", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.LEGS,
                "chain_layer_2", 0).setCreativeTab(tab);
        standard_chain_boots = (ItemArmourMFR) new ItemCustomArmour(design, "chain_boots", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.FEET,
                "chain_layer_1", 0).setCreativeTab(tab);

        standard_splint_helmet = (ItemArmourMFR) new ItemCustomArmour(design, "splint_helmet", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.HEAD,
                "splint_layer_1", 0).setCreativeTab(tab);
        standard_splint_chest = (ItemArmourMFR) new ItemCustomArmour(design, "splint_chest", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.CHEST,
                "splint_layer_1", 0).setCreativeTab(tab);
        standard_splint_legs = (ItemArmourMFR) new ItemCustomArmour(design, "splint_legs", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.LEGS,
                "splint_layer_2", 0).setCreativeTab(tab);
        standard_splint_boots = (ItemArmourMFR) new ItemCustomArmour(design, "splint_boots", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.FEET,
                "splint_layer_1", 0).setCreativeTab(tab);

        standard_plate_helmet = (ItemArmourMFR) new ItemCustomArmour(design, "plate_helmet", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.HEAD,
                "plate_layer_1", 0).setCreativeTab(tab);
        standard_plate_chest = (ItemArmourMFR) new ItemCustomArmour(design, "plate_chest", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.CHEST,
                "plate_layer_1", 0).setCreativeTab(tab);
        standard_plate_legs = (ItemArmourMFR) new ItemCustomArmour(design, "plate_legs", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.LEGS,
                "plate_layer_2", 0).setCreativeTab(tab);
        standard_plate_boots = (ItemArmourMFR) new ItemCustomArmour(design, "plate_boots", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.FEET,
                "plate_layer_1", 0).setCreativeTab(tab);

        //standart_plate_horse_armor = new ItemHorseArmorMF("horse_armor", BaseMaterialMF.getMaterial("steel"), ArmourDesign.FIELDPLATE, "plate_layer_1", 0);
    }
}
