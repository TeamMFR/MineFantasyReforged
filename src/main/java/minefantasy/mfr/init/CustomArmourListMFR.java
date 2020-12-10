package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.item.ItemArmourMFR;
import minefantasy.mfr.item.ItemCustomArmour;
import minefantasy.mfr.util.Utils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(MineFantasyReborn.MOD_ID)
@Mod.EventBusSubscriber(modid = MineFantasyReborn.MOD_ID)
public class CustomArmourListMFR {
    public static ItemArmourMFR STANDARD_SCALE_HELMET = Utils.nullValue();
    public static ItemArmourMFR STANDARD_SCALE_CHEST = Utils.nullValue();
    public static ItemArmourMFR STANDARD_SCALE_LEGS = Utils.nullValue();
    public static ItemArmourMFR STANDARD_SCALE_BOOTS = Utils.nullValue();
    
    public static ItemArmourMFR STANDARD_CHAIN_HELMET = Utils.nullValue();
    public static ItemArmourMFR STANDARD_CHAIN_CHEST = Utils.nullValue();
    public static ItemArmourMFR STANDARD_CHAIN_LEGS = Utils.nullValue();
    public static ItemArmourMFR STANDARD_CHAIN_BOOTS = Utils.nullValue();
    
    public static ItemArmourMFR STANDARD_SPLINT_HELMET = Utils.nullValue();
    public static ItemArmourMFR STANDARD_SPLINT_CHEST = Utils.nullValue();
    public static ItemArmourMFR STANDARD_SPLINT_LEGS = Utils.nullValue();
    public static ItemArmourMFR STANDARD_SPLINT_BOOTS = Utils.nullValue();
    
    public static ItemArmourMFR STANDARD_PLATE_HELMET = Utils.nullValue();
    public static ItemArmourMFR STANDARD_PLATE_CHEST = Utils.nullValue();
    public static ItemArmourMFR STANDARD_PLATE_LEGS = Utils.nullValue();
    public static ItemArmourMFR STANDARD_PLATE_BOOTS = Utils.nullValue();

    //public static ItemHorseArmorMF standard_plate_horse_armor;

    public static void init() {
        String design = "standard";
        CreativeTabs tab = CreativeTabMFR.tabArmour;

        STANDARD_SCALE_HELMET = (ItemArmourMFR) new ItemCustomArmour(design, "scale_helmet", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.HEAD, "scale_layer_1", 0).setCreativeTab(tab);
        STANDARD_SCALE_CHEST = (ItemArmourMFR) new ItemCustomArmour(design, "scale_chest", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.CHEST, "scale_layer_1", 0).setCreativeTab(tab);
        STANDARD_SCALE_LEGS = (ItemArmourMFR) new ItemCustomArmour(design, "scale_legs", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.LEGS, "scale_layer_2", 0).setCreativeTab(tab);
        STANDARD_SCALE_BOOTS = (ItemArmourMFR) new ItemCustomArmour(design, "scale_boots", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.FEET, "scale_layer_1", 0).setCreativeTab(tab);

        STANDARD_CHAIN_HELMET = (ItemArmourMFR) new ItemCustomArmour(design, "chain_helmet", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.HEAD, "chain_layer_1", 0).setCreativeTab(tab);
        STANDARD_CHAIN_CHEST = (ItemArmourMFR) new ItemCustomArmour(design, "chain_chest", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.CHEST, "chain_layer_1", 0).setCreativeTab(tab);
        STANDARD_CHAIN_LEGS = (ItemArmourMFR) new ItemCustomArmour(design, "chain_legs", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.LEGS, "chain_layer_2", 0).setCreativeTab(tab);
        STANDARD_CHAIN_BOOTS = (ItemArmourMFR) new ItemCustomArmour(design, "chain_boots", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.FEET, "chain_layer_1", 0).setCreativeTab(tab);

        STANDARD_SPLINT_HELMET = (ItemArmourMFR) new ItemCustomArmour(design, "splint_helmet", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.HEAD, "splint_layer_1", 0).setCreativeTab(tab);
        STANDARD_SPLINT_CHEST = (ItemArmourMFR) new ItemCustomArmour(design, "splint_chest", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.CHEST, "splint_layer_1", 0).setCreativeTab(tab);
        STANDARD_SPLINT_LEGS = (ItemArmourMFR) new ItemCustomArmour(design, "splint_legs", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.LEGS, "splint_layer_2", 0).setCreativeTab(tab);
        STANDARD_SPLINT_BOOTS = (ItemArmourMFR) new ItemCustomArmour(design, "splint_boots", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.FEET, "splint_layer_1", 0).setCreativeTab(tab);

        STANDARD_PLATE_HELMET = (ItemArmourMFR) new ItemCustomArmour(design, "plate_helmet", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.HEAD, "plate_layer_1", 0).setCreativeTab(tab);
        STANDARD_PLATE_CHEST = (ItemArmourMFR) new ItemCustomArmour(design, "plate_chest", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.CHEST, "plate_layer_1", 0).setCreativeTab(tab);
        STANDARD_PLATE_LEGS = (ItemArmourMFR) new ItemCustomArmour(design, "plate_legs", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.LEGS, "plate_layer_2", 0).setCreativeTab(tab);
        STANDARD_PLATE_BOOTS = (ItemArmourMFR) new ItemCustomArmour(design, "plate_boots", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.FEET, "plate_layer_1", 0).setCreativeTab(tab);
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(STANDARD_SCALE_HELMET);
        registry.register(STANDARD_SCALE_CHEST);
        registry.register(STANDARD_SCALE_LEGS);
        registry.register(STANDARD_SCALE_BOOTS);

        registry.register(STANDARD_CHAIN_HELMET);
        registry.register(STANDARD_CHAIN_CHEST);
        registry.register(STANDARD_CHAIN_LEGS);
        registry.register(STANDARD_CHAIN_BOOTS);

        registry.register(STANDARD_SPLINT_HELMET);
        registry.register(STANDARD_SPLINT_CHEST);
        registry.register(STANDARD_SPLINT_LEGS);
        registry.register(STANDARD_SPLINT_BOOTS);

        registry.register(STANDARD_PLATE_HELMET);
        registry.register(STANDARD_PLATE_CHEST);
        registry.register(STANDARD_PLATE_LEGS);
        registry.register(STANDARD_PLATE_BOOTS);
    }

}
