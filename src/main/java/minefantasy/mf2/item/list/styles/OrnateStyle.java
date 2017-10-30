package minefantasy.mf2.item.list.styles;

import minefantasy.mf2.api.armour.ArmourDesign;
import minefantasy.mf2.api.crafting.exotic.SpecialForging;
import minefantasy.mf2.item.archery.EnumBowType;
import minefantasy.mf2.item.archery.ItemBowMF;
import minefantasy.mf2.item.armour.ItemCustomArmour;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.CustomArmourListMF;
import minefantasy.mf2.item.list.CustomToolListMF;
import minefantasy.mf2.item.tool.*;
import minefantasy.mf2.item.tool.advanced.*;
import minefantasy.mf2.item.tool.crafting.*;
import minefantasy.mf2.item.weapon.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class OrnateStyle {
    public static ToolMaterial ornate = EnumHelper.addToolMaterial("ornate", 2, 250, 6.0F, 2.0F, 100);

    public static ItemWeaponMF ornate_sword, ornate_waraxe, ornate_mace, ornate_dagger, ornate_spear;
    public static ItemWeaponMF ornate_greatsword, ornate_battleaxe, ornate_warhammer, ornate_katana, ornate_halbeard,
            ornate_lance;
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
    public static ItemShearsMF ornate_shears;
    public static ItemKnifeMF ornate_knife;
    public static ItemNeedle ornate_needle;
    public static ItemSaw ornate_saw;
    public static ItemSpanner ornate_spanner;

    public static ItemBowMF ornate_bow;

    public static ItemCustomArmour ornate_scale_helmet, ornate_scale_chest, ornate_scale_legs, ornate_scale_boots;
    public static ItemCustomArmour ornate_chain_helmet, ornate_chain_chest, ornate_chain_legs, ornate_chain_boots;
    public static ItemCustomArmour ornate_splint_helmet, ornate_splint_chest, ornate_splint_legs, ornate_splint_boots;
    public static ItemCustomArmour ornate_plate_helmet, ornate_plate_chest, ornate_plate_legs, ornate_plate_boots;

    public static void load() {
        String design = "ornate";
        CreativeTabs tab = CreativeTabMF.tabOrnate;
        ToolMaterial mat = ornate;
        float ratingMod = 0.8F;

        ornate_dagger = new ItemDagger(design + "_dagger", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_sword = new ItemSwordMF(design + "_sword", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_waraxe = new ItemWaraxeMF(design + "_waraxe", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_mace = new ItemMaceMF(design + "_mace", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1);
        ornate_spear = new ItemSpearMF(design + "_spear", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_katana = new ItemKatanaMF(design + "_katana", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_greatsword = new ItemGreatswordMF(design + "_greatsword", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_battleaxe = new ItemBattleaxeMF(design + "_battleaxe", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_warhammer = new ItemWarhammerMF(design + "_warhammer", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_halbeard = new ItemHalbeardMF(design + "_halbeard", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(-1);
        ornate_lance = new ItemLance(design + "_lance", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1);

        ornate_bow = (ItemBowMF) new ItemBowMF(design + "_bow", mat, EnumBowType.SHORTBOW, 1).setCustom(design)
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
        ornate_shears = (ItemShearsMF) new ItemShearsMF(design + "_shears", mat, 0, 0).setCustom(design)
                .setCreativeTab(tab);
        ornate_knife = (ItemKnifeMF) new ItemKnifeMF(design + "_knife", mat, 0, 1F, 0).setCustom(design)
                .setCreativeTab(tab);
        ornate_needle = (ItemNeedle) new ItemNeedle(design + "_needle", mat, 0, 0).setCustom(design)
                .setCreativeTab(tab);
        ornate_saw = (ItemSaw) new ItemSaw(design + "_saw", mat, 0, 0).setCustom(design).setCreativeTab(tab);
        ornate_tongs = (ItemTongs) new ItemTongs(design + "_tongs", mat, 0).setCustom(design).setCreativeTab(tab);
        ornate_spanner = (ItemSpanner) new ItemSpanner(design + "_spanner", 0, 0).setCustom(design).setCreativeTab(tab);

        ornate_chain_helmet = (ItemCustomArmour) new ItemCustomArmour(design, "chain_helmet", ArmourDesign.CHAINMAIL, 0,
                "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_chain_chest = (ItemCustomArmour) new ItemCustomArmour(design, "chain_chest", ArmourDesign.CHAINMAIL, 1,
                "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_chain_legs = (ItemCustomArmour) new ItemCustomArmour(design, "chain_legs", ArmourDesign.CHAINMAIL, 2,
                "chain_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_chain_boots = (ItemCustomArmour) new ItemCustomArmour(design, "chain_boots", ArmourDesign.CHAINMAIL, 3,
                "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);

        ornate_scale_helmet = (ItemCustomArmour) new ItemCustomArmour(design, "scale_helmet", ArmourDesign.SCALEMAIL, 0,
                "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_scale_chest = (ItemCustomArmour) new ItemCustomArmour(design, "scale_chest", ArmourDesign.SCALEMAIL, 1,
                "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_scale_legs = (ItemCustomArmour) new ItemCustomArmour(design, "scale_legs", ArmourDesign.SCALEMAIL, 2,
                "scale_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_scale_boots = (ItemCustomArmour) new ItemCustomArmour(design, "scale_boots", ArmourDesign.SCALEMAIL, 3,
                "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);

        ornate_splint_helmet = (ItemCustomArmour) new ItemCustomArmour(design, "splint_helmet", ArmourDesign.SPLINTMAIL,
                0, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_splint_chest = (ItemCustomArmour) new ItemCustomArmour(design, "splint_chest", ArmourDesign.SPLINTMAIL,
                1, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_splint_legs = (ItemCustomArmour) new ItemCustomArmour(design, "splint_legs", ArmourDesign.SPLINTMAIL, 2,
                "splint_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_splint_boots = (ItemCustomArmour) new ItemCustomArmour(design, "splint_boots", ArmourDesign.SPLINTMAIL,
                3, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);

        ornate_plate_helmet = (ItemCustomArmour) new ItemCustomArmour(design, "plate_helmet", ArmourDesign.FIELDPLATE,
                0, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_plate_chest = (ItemCustomArmour) new ItemCustomArmour(design, "plate_chest", ArmourDesign.FIELDPLATE, 1,
                "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_plate_legs = (ItemCustomArmour) new ItemCustomArmour(design, "plate_legs", ArmourDesign.FIELDPLATE, 2,
                "plate_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        ornate_plate_boots = (ItemCustomArmour) new ItemCustomArmour(design, "plate_boots", ArmourDesign.FIELDPLATE, 3,
                "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
    }

    public static void loadCrafting() {
        String id = "ornate";

        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_dagger, ornate_dagger);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_sword, ornate_sword);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_mace, ornate_mace);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_waraxe, ornate_waraxe);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_spear, ornate_spear);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_katana, ornate_katana);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_greatsword, ornate_greatsword);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_warhammer, ornate_warhammer);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_battleaxe, ornate_battleaxe);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_halbeard, ornate_halbeard);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_lance, ornate_lance);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_bow, ornate_bow);

        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_pick, ornate_pick);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_axe, ornate_axe);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_spade, ornate_spade);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_hoe, ornate_hoe);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_shears, ornate_shears);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_hvypick, ornate_hvypick);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_hvyshovel, ornate_hvyshovel);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_trow, ornate_trow);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_handpick, ornate_handpick);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_mattock, ornate_mattock);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_spade, ornate_spade);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_scythe, ornate_scythe);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_spanner, ornate_spanner);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_lumber, ornate_lumber);

        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_hammer, ornate_hammer);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_hvyhammer, ornate_hvyhammer);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_tongs, ornate_tongs);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_saw, ornate_saw);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_needle, ornate_needle);
        SpecialForging.addSpecialCraft(id, CustomToolListMF.standard_knife, ornate_knife);

        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_chain_boots, ornate_chain_boots);
        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_chain_legs, ornate_chain_legs);
        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_chain_chest, ornate_chain_chest);
        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_chain_helmet, ornate_chain_helmet);

        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_scale_boots, ornate_scale_boots);
        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_scale_legs, ornate_scale_legs);
        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_scale_chest, ornate_scale_chest);
        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_scale_helmet, ornate_scale_helmet);

        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_splint_boots, ornate_splint_boots);
        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_splint_legs, ornate_splint_legs);
        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_splint_chest, ornate_splint_chest);
        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_splint_helmet, ornate_splint_helmet);

        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_plate_boots, ornate_plate_boots);
        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_plate_legs, ornate_plate_legs);
        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_plate_chest, ornate_plate_chest);
        SpecialForging.addSpecialCraft(id, CustomArmourListMF.standard_plate_helmet, ornate_plate_helmet);
    }
}
