package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.api.crafting.exotic.SpecialForging;
import minefantasy.mfr.item.archery.EnumBowType;
import minefantasy.mfr.item.archery.ItemBowMFR;
import minefantasy.mfr.item.armour.ItemCustomArmour;
import minefantasy.mfr.item.tool.*;
import minefantasy.mfr.item.tool.advanced.*;
import minefantasy.mfr.item.tool.crafting.*;
import minefantasy.mfr.item.weapon.*;
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

    public static final ItemWeaponMFR ORNATE_SWORD = Utils.nullValue();
    public static final ItemWeaponMFR ORNATE_WARAXE = Utils.nullValue();
    public static final ItemWeaponMFR ORNATE_MACE = Utils.nullValue();
    public static final ItemWeaponMFR ORNATE_DAGGER = Utils.nullValue();
    public static final ItemWeaponMFR ORNATE_SPEAR = Utils.nullValue();
    public static final ItemWeaponMFR ORNATE_GREATSWORD = Utils.nullValue();
    public static final ItemWeaponMFR ORNATE_BATTLEAXE = Utils.nullValue();
    public static final ItemWeaponMFR ORNATE_WARHAMMER = Utils.nullValue();
    public static final ItemWeaponMFR ORNATE_KATANA = Utils.nullValue();
    public static final ItemWeaponMFR ORNATE_HALBEARD = Utils.nullValue();
    public static final ItemWeaponMFR ORNATE_LANCE = Utils.nullValue();
    public static final ItemPickMF ORNATE_PICK = Utils.nullValue();
    public static final ItemAxe ORNATE_AXE = Utils.nullValue();
    public static final ItemSpadeMF ORNATE_SPADE = Utils.nullValue();
    public static final ItemHoeMF ORNATE_HOE = Utils.nullValue();
    public static final ItemHvyPick ORNATE_HVYPICK = Utils.nullValue();
    public static final ItemHvyShovel ORNATE_HVYSHOVEL = Utils.nullValue();
    public static final ItemHandpick ORNATE_HANDPICK = Utils.nullValue();
    public static final ItemTrowMF ORNATE_TROW = Utils.nullValue();
    public static final ItemScythe ORNATE_SCYTHE = Utils.nullValue();
    public static final ItemMattock ORNATE_MATTOCK = Utils.nullValue();
    public static final ItemLumberAxe ORNATE_LUMBER = Utils.nullValue();

    public static final ItemHammer ORNATE_HAMMER = Utils.nullValue();
    public static final ItemHammer ORNATE_HVYHAMMER = Utils.nullValue();
    public static final ItemTongs ORNATE_TONGS = Utils.nullValue();
    public static final ItemShears ORNATE_SHEARS = Utils.nullValue();
    public static final ItemKnifeMFR ORNATE_KNIFE = Utils.nullValue();
    public static final ItemNeedle ORNATE_NEEDLE = Utils.nullValue();
    public static final ItemSaw ORNATE_SAW = Utils.nullValue();
    public static final ItemSpanner ORNATE_SPANNER = Utils.nullValue();

    public static final ItemBowMFR ORNATE_BOW = Utils.nullValue();

    public static final ItemCustomArmour ORNATE_SCALE_HELMET = Utils.nullValue();
    public static final ItemCustomArmour ORNATE_SCALE_CHEST = Utils.nullValue();
    public static final ItemCustomArmour ORNATE_SCALE_LEGS = Utils.nullValue();
    public static final ItemCustomArmour ORNATE_SCALE_BOOTS = Utils.nullValue();
    public static final ItemCustomArmour ORNATE_CHAIN_HELMET = Utils.nullValue();
    public static final ItemCustomArmour ORNATE_CHAIN_CHEST = Utils.nullValue();
    public static final ItemCustomArmour ORNATE_CHAIN_LEGS = Utils.nullValue();
    public static final ItemCustomArmour ORNATE_CHAIN_BOOTS = Utils.nullValue();
    public static final ItemCustomArmour ORNATE_SPLINT_HELMET = Utils.nullValue();
    public static final ItemCustomArmour ORNATE_SPLINT_CHEST = Utils.nullValue();
    public static final ItemCustomArmour ORNATE_SPLINT_LEGS = Utils.nullValue();
    public static final ItemCustomArmour ORNATE_SPLINT_BOOTS = Utils.nullValue();
    public static final ItemCustomArmour ORNATE_PLATE_HELMET = Utils.nullValue();
    public static final ItemCustomArmour ORNATE_PLATE_CHEST = Utils.nullValue();
    public static final ItemCustomArmour ORNATE_PLATE_LEGS = Utils.nullValue();
    public static final ItemCustomArmour ORNATE_PLATE_BOOTS = Utils.nullValue();

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        String design = "ornate";
        CreativeTabs tab = CreativeTabMFR.tabOrnate;
        ToolMaterial mat = ORNATE;
        float ratingMod = 0.8F;
        
        // Weapons
        registry.register(new ItemDagger(design + "_dagger", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
        registry.register(new ItemSword(design + "_sword", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
        registry.register(new ItemWaraxe(design + "_waraxe", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
        registry.register(new ItemMace(design + "_mace", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
        registry.register(new ItemSpear(design + "_spear", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
        registry.register(new ItemKatana(design + "_katana", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
        registry.register(new ItemGreatsword(design + "_greatsword", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
        registry.register(new ItemBattleaxe(design + "_battleaxe", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
        registry.register(new ItemWarhammer(design + "_warhammer", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
        registry.register(new ItemHalbeard(design + "_halbeard", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
        registry.register(new ItemLance(design + "_lance", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));

        registry.register(new ItemBowMFR(design + "_bow", mat, EnumBowType.SHORTBOW, 1).setCustom(design).setCreativeTab(tab));

        // Tools
        registry.register(new ItemPickMF(design + "_pick", mat, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemAxe(design + "_axe", mat, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemSpadeMF(design + "_spade", mat, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemHoeMF(design + "_hoe", mat, 0).setCustom(design).setCreativeTab(tab));

        registry.register(new ItemHandpick(design + "_handpick", mat, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemHvyPick(design + "_hvypick", mat, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemTrowMF(design + "_trow", mat, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemHvyShovel(design + "_hvyshovel", mat, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemScythe(design + "_scythe", mat, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemMattock(design + "_mattock", mat, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemLumberAxe(design + "_lumber", mat, 0).setCustom(design).setCreativeTab(tab));

        // Crafters
        registry.register(new ItemHammer(design + "_hammer", mat, false, 0, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemHammer(design + "_hvyhammer", mat, true, 0, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemShears(design + "_shears", mat, 0, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemKnifeMFR(design + "_knife", mat, 0, 1F, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemNeedle(design + "_needle", mat, 0, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemSaw(design + "_saw", mat, 0, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemTongs(design + "_tongs", mat, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemSpanner(design + "_spanner", 0, 0).setCustom(design).setCreativeTab(tab));

        // Armours
        registry.register(new ItemCustomArmour(design, "chain_helmet", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.HEAD, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "chain_chest", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.CHEST, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "chain_legs", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.LEGS, "chain_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "chain_boots", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.FEET, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));

        registry.register(new ItemCustomArmour(design, "scale_helmet", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.HEAD, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "scale_chest", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.CHEST, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "scale_legs", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.LEGS, "scale_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "scale_boots", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.FEET, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));

        registry.register(new ItemCustomArmour(design, "splint_helmet", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.CHEST, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "splint_chest", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.CHEST, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "splint_legs", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.LEGS, "splint_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "splint_boots", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.FEET, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));

        registry.register(new ItemCustomArmour(design, "plate_helmet", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.HEAD, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "plate_chest", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.CHEST, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "plate_legs", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.LEGS, "plate_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "plate_boots", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.FEET, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
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
