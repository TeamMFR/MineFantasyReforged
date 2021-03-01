package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.api.crafting.exotic.SpecialForging;
import minefantasy.mfr.item.EnumBowType;
import minefantasy.mfr.item.ItemAxeMFR;
import minefantasy.mfr.item.ItemBattleaxe;
import minefantasy.mfr.item.ItemBowMFR;
import minefantasy.mfr.item.ItemCustomArmour;
import minefantasy.mfr.item.ItemDagger;
import minefantasy.mfr.item.ItemGreatsword;
import minefantasy.mfr.item.ItemHalbeard;
import minefantasy.mfr.item.ItemHammer;
import minefantasy.mfr.item.ItemHandpick;
import minefantasy.mfr.item.ItemHeavyPick;
import minefantasy.mfr.item.ItemHeavyShovel;
import minefantasy.mfr.item.ItemHoeMFR;
import minefantasy.mfr.item.ItemKatana;
import minefantasy.mfr.item.ItemKnife;
import minefantasy.mfr.item.ItemLance;
import minefantasy.mfr.item.ItemLumberAxe;
import minefantasy.mfr.item.ItemMace;
import minefantasy.mfr.item.ItemMattock;
import minefantasy.mfr.item.ItemNeedle;
import minefantasy.mfr.item.ItemPickMFR;
import minefantasy.mfr.item.ItemSaw;
import minefantasy.mfr.item.ItemScythe;
import minefantasy.mfr.item.ItemShearsMFR;
import minefantasy.mfr.item.ItemSpade;
import minefantasy.mfr.item.ItemSpanner;
import minefantasy.mfr.item.ItemSpear;
import minefantasy.mfr.item.ItemSword;
import minefantasy.mfr.item.ItemTongs;
import minefantasy.mfr.item.ItemTrow;
import minefantasy.mfr.item.ItemWaraxe;
import minefantasy.mfr.item.ItemWarhammer;
import minefantasy.mfr.item.ItemWeaponMFR;
import minefantasy.mfr.util.Utils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;


@ObjectHolder(MineFantasyReborn.MOD_ID)
@Mod.EventBusSubscriber(modid = MineFantasyReborn.MOD_ID)
public class OrnateStyle {
    public static ToolMaterial ORNATE = EnumHelper.addToolMaterial("ornate", 2, 250, 6.0F, 2.0F, 100);

    public static ItemWeaponMFR ORNATE_SWORD = Utils.nullValue();
    public static ItemWeaponMFR ORNATE_WARAXE = Utils.nullValue();
    public static ItemWeaponMFR ORNATE_MACE = Utils.nullValue();
    public static ItemWeaponMFR ORNATE_DAGGER = Utils.nullValue();
    public static ItemWeaponMFR ORNATE_SPEAR = Utils.nullValue();
    public static ItemWeaponMFR ORNATE_GREATSWORD = Utils.nullValue();
    public static ItemWeaponMFR ORNATE_BATTLEAXE = Utils.nullValue();
    public static ItemWeaponMFR ORNATE_WARHAMMER = Utils.nullValue();
    public static ItemWeaponMFR ORNATE_KATANA = Utils.nullValue();
    public static ItemWeaponMFR ORNATE_HALBEARD = Utils.nullValue();
    public static ItemWeaponMFR ORNATE_LANCE = Utils.nullValue();
    public static ItemPickMFR ORNATE_PICK = Utils.nullValue();
    public static ItemAxeMFR ORNATE_AXE = Utils.nullValue();
    public static ItemSpade ORNATE_SPADE = Utils.nullValue();
    public static ItemHoeMFR ORNATE_HOE = Utils.nullValue();
    public static ItemHeavyPick ORNATE_HVYPICK = Utils.nullValue();
    public static ItemHeavyShovel ORNATE_HVYSHOVEL = Utils.nullValue();
    public static ItemHandpick ORNATE_HANDPICK = Utils.nullValue();
    public static ItemTrow ORNATE_TROW = Utils.nullValue();
    public static ItemScythe ORNATE_SCYTHE = Utils.nullValue();
    public static ItemMattock ORNATE_MATTOCK = Utils.nullValue();
    public static ItemLumberAxe ORNATE_LUMBER = Utils.nullValue();

    public static ItemHammer ORNATE_HAMMER = Utils.nullValue();
    public static ItemHammer ORNATE_HVYHAMMER = Utils.nullValue();
    public static ItemTongs ORNATE_TONGS = Utils.nullValue();
    public static ItemShearsMFR ORNATE_SHEARS = Utils.nullValue();
    public static ItemKnife ORNATE_KNIFE = Utils.nullValue();
    public static ItemNeedle ORNATE_NEEDLE = Utils.nullValue();
    public static ItemSaw ORNATE_SAW = Utils.nullValue();
    public static ItemSpanner ORNATE_SPANNER = Utils.nullValue();

    public static ItemBowMFR ORNATE_BOW = Utils.nullValue();

    public static ItemCustomArmour ORNATE_SCALE_HELMET = Utils.nullValue();
    public static ItemCustomArmour ORNATE_SCALE_CHEST = Utils.nullValue();
    public static ItemCustomArmour ORNATE_SCALE_LEGS = Utils.nullValue();
    public static ItemCustomArmour ORNATE_SCALE_BOOTS = Utils.nullValue();
    public static ItemCustomArmour ORNATE_CHAIN_HELMET = Utils.nullValue();
    public static ItemCustomArmour ORNATE_CHAIN_CHEST = Utils.nullValue();
    public static ItemCustomArmour ORNATE_CHAIN_LEGS = Utils.nullValue();
    public static ItemCustomArmour ORNATE_CHAIN_BOOTS = Utils.nullValue();
    public static ItemCustomArmour ORNATE_SPLINT_HELMET = Utils.nullValue();
    public static ItemCustomArmour ORNATE_SPLINT_CHEST = Utils.nullValue();
    public static ItemCustomArmour ORNATE_SPLINT_LEGS = Utils.nullValue();
    public static ItemCustomArmour ORNATE_SPLINT_BOOTS = Utils.nullValue();
    public static ItemCustomArmour ORNATE_PLATE_HELMET = Utils.nullValue();
    public static ItemCustomArmour ORNATE_PLATE_CHEST = Utils.nullValue();
    public static ItemCustomArmour ORNATE_PLATE_LEGS = Utils.nullValue();
    public static ItemCustomArmour ORNATE_PLATE_BOOTS = Utils.nullValue();

    public static void init() {
        String design = "ornate";
        CreativeTabs tab = MineFantasyTabs.tabOrnate;
        ToolMaterial mat = ORNATE;
        float ratingMod = 0.8F;

        // Weapons
        ORNATE_SWORD = new ItemSword(design + "_sword", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
        ORNATE_WARAXE = new ItemWaraxe(design + "_waraxe", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
        ORNATE_MACE = new ItemMace(design + "_mace", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
        ORNATE_DAGGER = new ItemDagger(design + "_dagger", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
        ORNATE_SPEAR = new ItemSpear(design + "_spear", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
        ORNATE_GREATSWORD = new ItemGreatsword(design + "_greatsword", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
        ORNATE_BATTLEAXE = new ItemBattleaxe(design + "_battleaxe", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
        ORNATE_WARHAMMER = new ItemWarhammer(design + "_warhammer", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
        ORNATE_KATANA = new ItemKatana(design + "_katana", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
        ORNATE_HALBEARD = new ItemHalbeard(design + "_halbeard", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
        ORNATE_LANCE = new ItemLance(design + "_lance", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);

        ORNATE_BOW = (ItemBowMFR) new ItemBowMFR(design + "_bow", mat, EnumBowType.SHORTBOW, 1).setCustom(design).setCreativeTab(tab);

        // Tools
        ORNATE_PICK = (ItemPickMFR) new ItemPickMFR(design + "_pick", mat, 0).setCustom(design).setCreativeTab(tab);
        ORNATE_AXE = (ItemAxeMFR) new ItemAxeMFR(design + "_axe", mat, 0).setCustom(design).setCreativeTab(tab);
        ORNATE_SPADE = (ItemSpade) new ItemSpade(design + "_spade", mat, 0).setCustom(design).setCreativeTab(tab);
        ORNATE_HOE = (ItemHoeMFR) new ItemHoeMFR(design + "_hoe", mat, 0).setCustom(design).setCreativeTab(tab);

        ORNATE_HVYPICK = (ItemHeavyPick) new ItemHeavyPick(design + "_hvypick", mat, 0).setCustom(design).setCreativeTab(tab);
        ORNATE_HVYSHOVEL = (ItemHeavyShovel) new ItemHeavyShovel(design + "_hvyshovel", mat, 0).setCustom(design).setCreativeTab(tab);
        ORNATE_HANDPICK = (ItemHandpick) new ItemHandpick(design + "_handpick", mat, 0).setCustom(design).setCreativeTab(tab);
        ORNATE_TROW = (ItemTrow) new ItemTrow(design + "_trow", mat, 0).setCustom(design).setCreativeTab(tab);
        ORNATE_SCYTHE = (ItemScythe) new ItemScythe(design + "_scythe", mat, 0).setCustom(design).setCreativeTab(tab);
        ORNATE_MATTOCK = (ItemMattock) new ItemMattock(design + "_mattock", mat, 0).setCustom(design).setCreativeTab(tab);
        ORNATE_LUMBER = (ItemLumberAxe) new ItemLumberAxe(design + "_lumber", mat, 0).setCustom(design).setCreativeTab(tab);
        ORNATE_HAMMER = (ItemHammer) new ItemHammer(design + "_hammer", mat, false, 0, 0).setCustom(design).setCreativeTab(tab);
        ORNATE_HVYHAMMER = (ItemHammer) new ItemHammer(design + "_hvyhammer", mat, true, 0, 0).setCustom(design).setCreativeTab(tab);

        // Crafters
        ORNATE_TONGS = (ItemTongs) new ItemTongs(design + "_tongs", mat, 0).setCustom(design).setCreativeTab(tab);
        ORNATE_SHEARS = (ItemShearsMFR) new ItemShearsMFR(design + "_shears", mat, 0, 0).setCustom().setCreativeTab(tab);
        ORNATE_KNIFE = (ItemKnife) new ItemKnife(design + "_knife", mat, 0, 1F, 0).setCustom(design).setCreativeTab(tab);
        ORNATE_NEEDLE = (ItemNeedle) new ItemNeedle(design + "_needle", mat, 0, 0).setCustom(design).setCreativeTab(tab);
        ORNATE_SAW = (ItemSaw) new ItemSaw(design + "_saw", mat, 0, 0).setCustom(design).setCreativeTab(tab);
        ORNATE_SPANNER = (ItemSpanner) new ItemSpanner(design + "_spanner", 0, 0).setCustom(design).setCreativeTab(tab);

        // Armours
        ORNATE_SCALE_HELMET = (ItemCustomArmour) new ItemCustomArmour(design, "scale_helmet", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.HEAD, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ORNATE_SCALE_CHEST = (ItemCustomArmour) new ItemCustomArmour(design, "scale_chest", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.CHEST, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ORNATE_SCALE_LEGS = (ItemCustomArmour) new ItemCustomArmour(design, "scale_legs", ArmourDesign.SCALEMAIL,EntityEquipmentSlot.LEGS, "scale_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ORNATE_SCALE_BOOTS = (ItemCustomArmour) new ItemCustomArmour(design, "scale_boots", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.FEET, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);

        ORNATE_CHAIN_HELMET = (ItemCustomArmour) new ItemCustomArmour(design, "chain_helmet", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.HEAD, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ORNATE_CHAIN_CHEST = (ItemCustomArmour) new ItemCustomArmour(design, "chain_chest", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.CHEST, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ORNATE_CHAIN_LEGS = (ItemCustomArmour) new ItemCustomArmour(design, "chain_legs", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.LEGS, "chain_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ORNATE_CHAIN_BOOTS = (ItemCustomArmour) new ItemCustomArmour(design, "chain_boots", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.FEET, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);

        ORNATE_SPLINT_HELMET = (ItemCustomArmour) new ItemCustomArmour(design, "splint_helmet", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.HEAD, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ORNATE_SPLINT_CHEST = (ItemCustomArmour) new ItemCustomArmour(design, "splint_chest", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.CHEST, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ORNATE_SPLINT_LEGS = (ItemCustomArmour) new ItemCustomArmour(design, "splint_legs", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.LEGS, "splint_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ORNATE_SPLINT_BOOTS = (ItemCustomArmour) new ItemCustomArmour(design, "splint_boots", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.FEET, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);

        ORNATE_PLATE_HELMET = (ItemCustomArmour) new ItemCustomArmour(design, "plate_helmet", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.HEAD, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ORNATE_PLATE_CHEST = (ItemCustomArmour) new ItemCustomArmour(design, "plate_chest", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.CHEST, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ORNATE_PLATE_LEGS = (ItemCustomArmour) new ItemCustomArmour(design, "plate_legs", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.LEGS, "plate_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ORNATE_PLATE_BOOTS = (ItemCustomArmour) new ItemCustomArmour(design, "plate_boots", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.FEET, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
    }
    
    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        // Weapons
        registry.register(ORNATE_SWORD);
        registry.register(ORNATE_WARAXE);
        registry.register(ORNATE_MACE);
        registry.register(ORNATE_DAGGER);
        registry.register(ORNATE_SPEAR);
        registry.register(ORNATE_GREATSWORD);
        registry.register(ORNATE_BATTLEAXE);
        registry.register(ORNATE_WARHAMMER);
        registry.register(ORNATE_KATANA);
        registry.register(ORNATE_HALBEARD);
        registry.register(ORNATE_LANCE);

        registry.register(ORNATE_BOW);

        // Tools
        registry.register(ORNATE_PICK);
        registry.register(ORNATE_AXE);
        registry.register(ORNATE_SPADE);
        registry.register(ORNATE_HOE);

        registry.register(ORNATE_HVYPICK);
        registry.register(ORNATE_HVYSHOVEL);
        registry.register(ORNATE_HANDPICK);
        registry.register(ORNATE_TROW);
        registry.register(ORNATE_SCYTHE);
        registry.register(ORNATE_MATTOCK);
        registry.register(ORNATE_LUMBER);
        registry.register(ORNATE_HAMMER);
        registry.register(ORNATE_HVYHAMMER);

        // Crafters
        registry.register(ORNATE_TONGS);
        registry.register(ORNATE_SHEARS);
        registry.register(ORNATE_KNIFE);
        registry.register(ORNATE_NEEDLE);
        registry.register(ORNATE_SAW);
        registry.register(ORNATE_SPANNER);

        // Armours
        registry.register(ORNATE_SCALE_HELMET);
        registry.register(ORNATE_SCALE_CHEST);
        registry.register(ORNATE_SCALE_LEGS);
        registry.register(ORNATE_SCALE_BOOTS);

        registry.register(ORNATE_CHAIN_HELMET);
        registry.register(ORNATE_CHAIN_CHEST);
        registry.register(ORNATE_CHAIN_LEGS);
        registry.register(ORNATE_CHAIN_BOOTS);

        registry.register(ORNATE_SPLINT_HELMET);
        registry.register(ORNATE_SPLINT_CHEST);
        registry.register(ORNATE_SPLINT_LEGS);
        registry.register(ORNATE_SPLINT_BOOTS);

        registry.register(ORNATE_PLATE_HELMET);
        registry.register(ORNATE_PLATE_CHEST);
        registry.register(ORNATE_PLATE_LEGS);
        registry.register(ORNATE_PLATE_BOOTS);
    }

    public static void loadCrafting() {
        String id = "ornate";

        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_DAGGER, ORNATE_DAGGER);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_SWORD, ORNATE_SWORD);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_MACE, ORNATE_MACE);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_WARAXE, ORNATE_WARAXE);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_SPEAR, ORNATE_SPEAR);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_KATANA, ORNATE_KATANA);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_GREATSWORD, ORNATE_GREATSWORD);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_WARHAMMER, ORNATE_WARHAMMER);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_BATTLEAXE, ORNATE_BATTLEAXE);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_HALBEARD, ORNATE_HALBEARD);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_LANCE, ORNATE_LANCE);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_BOW, ORNATE_BOW);

        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_PICK, ORNATE_PICK);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_AXE, ORNATE_AXE);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_SPADE, ORNATE_SPADE);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_HOE, ORNATE_HOE);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_SHEARS, ORNATE_SHEARS);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_HVYPICK, ORNATE_HVYPICK);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_HVYSHOVEL, ORNATE_HVYSHOVEL);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_TROW, ORNATE_TROW);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_HANDPICK, ORNATE_HANDPICK);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_MATTOCK, ORNATE_MATTOCK);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_SPADE, ORNATE_SPADE);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_SCYTHE, ORNATE_SCYTHE);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_SPANNER, ORNATE_SPANNER);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_LUMBER, ORNATE_LUMBER);

        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_HAMMER, ORNATE_HAMMER);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_HVYHAMMER, ORNATE_HVYHAMMER);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_TONGS, ORNATE_TONGS);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_SAW, ORNATE_SAW);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_NEEDLE, ORNATE_NEEDLE);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.STANDARD_KNIFE, ORNATE_KNIFE);

        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_CHAIN_BOOTS, ORNATE_CHAIN_BOOTS);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_CHAIN_LEGS, ORNATE_CHAIN_LEGS);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_CHAIN_CHEST, ORNATE_CHAIN_CHEST);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_CHAIN_HELMET, ORNATE_CHAIN_HELMET);

        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_SCALE_BOOTS, ORNATE_SCALE_BOOTS);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_SCALE_LEGS, ORNATE_SCALE_LEGS);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_SCALE_CHEST, ORNATE_SCALE_CHEST);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_SCALE_HELMET, ORNATE_SCALE_HELMET);

        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_SPLINT_BOOTS, ORNATE_SPLINT_BOOTS);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_SPLINT_LEGS, ORNATE_SPLINT_LEGS);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_SPLINT_CHEST, ORNATE_SPLINT_CHEST);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_SPLINT_HELMET, ORNATE_SPLINT_HELMET);

        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_PLATE_BOOTS, ORNATE_PLATE_BOOTS);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_PLATE_LEGS, ORNATE_PLATE_LEGS);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_PLATE_CHEST, ORNATE_PLATE_CHEST);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.STANDARD_PLATE_HELMET, ORNATE_PLATE_HELMET);
    }
}
