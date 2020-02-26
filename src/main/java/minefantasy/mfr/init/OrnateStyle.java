package minefantasy.mfr.init;

import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.api.crafting.exotic.SpecialForging;
import minefantasy.mfr.item.archery.EnumBowType;
import minefantasy.mfr.item.archery.ItemBowMFR;
import minefantasy.mfr.item.armour.ItemCustomArmour;
import minefantasy.mfr.item.tool.*;
import minefantasy.mfr.item.tool.advanced.*;
import minefantasy.mfr.item.tool.crafting.*;
import minefantasy.mfr.item.weapon.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class OrnateStyle {
    public static ToolMaterial ornate = EnumHelper.addToolMaterial("ornate", 2, 250, 6.0F, 2.0F, 100);

    public static ItemWeaponMFR ornate_sword, ornate_waraxe, ornate_mace, ornate_dagger, ornate_spear;
    public static ItemWeaponMFR ornate_greatsword, ornate_battleaxe, ornate_warhammer, ornate_katana, ornate_halbeard, ornate_lance;
    public static ItemPickMF ornate_pick;
    public static ItemAxeMF ornate_axe;
    public static ItemSpadeMF ornate_spade;
    public static ItemHoeMF ornate_hoe;
    public static ItemHvyPick ornate_hvypick;
    public static ItemHvyShovel ornate_hvyshovel;
    public static ItemHandpick ornate_handpick;
    public static ItemTrowMF ornate_trow;
    public static ItemScythe ornate_scythe;
    public static ItemMattock ornate_mattock;
    public static ItemLumberAxe ornate_lumber;

    public static ItemHammer ornate_hammer, ornate_hvyhammer;
    public static ItemTongs ornate_tongs;
    public static ItemShearsMFR ornate_shears;
    public static ItemKnifeMFR ornate_knife;
    public static ItemNeedle ornate_needle;
    public static ItemSaw ornate_saw;

    public static ItemBowMFR ornate_bow;

    public static ItemCustomArmour ornate_scale_helmet, ornate_scale_chest, ornate_scale_legs, ornate_scale_boots;
    public static ItemCustomArmour ornate_chain_helmet, ornate_chain_chest, ornate_chain_legs, ornate_chain_boots;
    public static ItemCustomArmour ornate_splint_helmet, ornate_splint_chest, ornate_splint_legs, ornate_splint_boots;
    public static ItemCustomArmour ornate_plate_helmet, ornate_plate_chest, ornate_plate_legs, ornate_plate_boots;

    public static void load() {
        String design = "ornate";
        CreativeTabs tab = CreativeTabMFR.tabOrnate;
        ToolMaterial mat = ornate;
        float ratingMod = 0.8F;

        ornate_dagger = new ItemDagger(design + "_dagger", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_sword = new ItemSwordMF(design + "_sword", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_waraxe = new ItemWaraxeMFR(design + "_waraxe", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_mace = new ItemMaceMFR(design + "_mace", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1);
        ornate_spear = new ItemSpearMFR(design + "_spear", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_katana = new ItemKatanaMFR(design + "_katana", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_greatsword = new ItemGreatswordMFR(design + "_greatsword", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_battleaxe = new ItemBattleaxeMFR(design + "_battleaxe", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_warhammer = new ItemWarhammerMFR(design + "_warhammer", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_halbeard = new ItemHalbeardMFR(design + "_halbeard", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_lance = new ItemLance(design + "_lance", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1);

        ornate_bow = (ItemBowMFR) new ItemBowMFR(design + "_bow", mat, EnumBowType.SHORTBOW, 1).setCustom(design)
                .setCreativeTab(tab);

        // Tools
        ornate_pick = (ItemPickMF) new ItemPickMF(design + "_pick", mat, 0).setCustom(design).setCreativeTab(tab);
        ornate_axe = (ItemAxeMF) new ItemAxeMF(design + "_axe", mat, 0).setCustom(design).setCreativeTab(tab);
        ornate_spade = (ItemSpadeMF) new ItemSpadeMF(design + "_spade", mat, 0).setCustom(design).setCreativeTab(tab);
        ornate_hoe = (ItemHoeMF) new ItemHoeMF(design + "_hoe", mat, 0).setCustom(design).setCreativeTab(tab);

        ornate_handpick = (ItemHandpick) new ItemHandpick(design + "_handpick", mat, 0).setCustom(design)
                .setCreativeTab(tab);
        ornate_hvypick = (ItemHvyPick) new ItemHvyPick(design + "_hvypick", mat, 0).setCustom(design)
                .setCreativeTab(tab);
        ornate_trow = (ItemTrowMF) new ItemTrowMF(design + "_trow", mat, 0).setCustom(design).setCreativeTab(tab);
        ornate_hvyshovel = (ItemHvyShovel) new ItemHvyShovel(design + "_hvyshovel", mat, 0).setCustom(design)
                .setCreativeTab(tab);
        ornate_scythe = (ItemScythe) new ItemScythe(design + "_scythe", mat, 0).setCustom(design).setCreativeTab(tab);
        ornate_mattock = (ItemMattock) new ItemMattock(design + "_mattock", mat, 0).setCustom(design)
                .setCreativeTab(tab);
        ornate_lumber = (ItemLumberAxe) new ItemLumberAxe(design + "_lumber", mat, 0).setCustom(design)
                .setCreativeTab(tab);

        // Crafters
        ornate_hammer = (ItemHammer) new ItemHammer(design + "_hammer", mat, false, 0, 0).setCustom(design)
                .setCreativeTab(tab);
        ornate_hvyhammer = (ItemHammer) new ItemHammer(design + "_hvyhammer", mat, true, 0, 0).setCustom(design)
                .setCreativeTab(tab);
        ornate_shears = (ItemShearsMFR) new ItemShearsMFR(design + "_shears", mat, 0, 0).setCustom(design)
                .setCreativeTab(tab);
        ornate_knife = (ItemKnifeMFR) new ItemKnifeMFR(design + "_knife", mat, 0, 1F, 0).setCustom(design)
                .setCreativeTab(tab);
        ornate_needle = (ItemNeedle) new ItemNeedle(design + "_needle", mat, 0, 0).setCustom(design)
                .setCreativeTab(tab);
        ornate_saw = (ItemSaw) new ItemSaw(design + "_saw", mat, 0, 0).setCustom(design).setCreativeTab(tab);
        ornate_tongs = (ItemTongs) new ItemTongs(design + "_tongs", mat, 0).setCustom(design).setCreativeTab(tab);

        ornate_chain_helmet = (ItemCustomArmour) new ItemCustomArmour(design, "chain_helmet", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.HEAD,
                "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_chain_chest = (ItemCustomArmour) new ItemCustomArmour(design, "chain_chest", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.CHEST,
                "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_chain_legs = (ItemCustomArmour) new ItemCustomArmour(design, "chain_legs", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.LEGS,
                "chain_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_chain_boots = (ItemCustomArmour) new ItemCustomArmour(design, "chain_boots", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.FEET,
                "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);

        ornate_scale_helmet = (ItemCustomArmour) new ItemCustomArmour(design, "scale_helmet", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.HEAD,
                "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_scale_chest = (ItemCustomArmour) new ItemCustomArmour(design, "scale_chest", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.CHEST,
                "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_scale_legs = (ItemCustomArmour) new ItemCustomArmour(design, "scale_legs", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.LEGS,
                "scale_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_scale_boots = (ItemCustomArmour) new ItemCustomArmour(design, "scale_boots", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.FEET,
                "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);

        ornate_splint_helmet = (ItemCustomArmour) new ItemCustomArmour(design, "splint_helmet", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.CHEST,
                "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_splint_chest = (ItemCustomArmour) new ItemCustomArmour(design, "splint_chest", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.CHEST,
                "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_splint_legs = (ItemCustomArmour) new ItemCustomArmour(design, "splint_legs", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.LEGS,
                "splint_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_splint_boots = (ItemCustomArmour) new ItemCustomArmour(design, "splint_boots", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.FEET,
                "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);

        ornate_plate_helmet = (ItemCustomArmour) new ItemCustomArmour(design, "plate_helmet", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.HEAD,
                "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_plate_chest = (ItemCustomArmour) new ItemCustomArmour(design, "plate_chest", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.CHEST,
                "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_plate_legs = (ItemCustomArmour) new ItemCustomArmour(design, "plate_legs", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.LEGS,
                "plate_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_plate_boots = (ItemCustomArmour) new ItemCustomArmour(design, "plate_boots", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.FEET,
                "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
    }

    public static void loadCrafting() {
        String id = "ornate";

        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_dagger, ornate_dagger);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_sword, ornate_sword);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_mace, ornate_mace);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_waraxe, ornate_waraxe);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_spear, ornate_spear);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_katana, ornate_katana);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_greatsword, ornate_greatsword);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_warhammer, ornate_warhammer);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_battleaxe, ornate_battleaxe);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_halbeard, ornate_halbeard);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_lance, ornate_lance);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_bow, ornate_bow);

        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_pick, ornate_pick);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_axe, ornate_axe);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_spade, ornate_spade);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_hoe, ornate_hoe);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_shears, ornate_shears);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_hvypick, ornate_hvypick);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_hvyshovel, ornate_hvyshovel);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_trow, ornate_trow);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_handpick, ornate_handpick);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_mattock, ornate_mattock);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_spade, ornate_spade);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_scythe, ornate_scythe);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_lumber, ornate_lumber);

        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_hammer, ornate_hammer);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_hvyhammer, ornate_hvyhammer);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_tongs, ornate_tongs);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_saw, ornate_saw);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_needle, ornate_needle);
        SpecialForging.addSpecialCraft(id, CustomToolListMFR.standard_knife, ornate_knife);

        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_chain_boots, ornate_chain_boots);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_chain_legs, ornate_chain_legs);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_chain_chest, ornate_chain_chest);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_chain_helmet, ornate_chain_helmet);

        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_scale_boots, ornate_scale_boots);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_scale_legs, ornate_scale_legs);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_scale_chest, ornate_scale_chest);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_scale_helmet, ornate_scale_helmet);

        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_splint_boots, ornate_splint_boots);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_splint_legs, ornate_splint_legs);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_splint_chest, ornate_splint_chest);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_splint_helmet, ornate_splint_helmet);

        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_plate_boots, ornate_plate_boots);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_plate_legs, ornate_plate_legs);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_plate_chest, ornate_plate_chest);
        SpecialForging.addSpecialCraft(id, CustomArmourListMFR.standard_plate_helmet, ornate_plate_helmet);
    }
}
