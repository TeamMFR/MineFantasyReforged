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

public class DragonforgedStyle {
    public static ToolMaterial dragonforged = EnumHelper.addToolMaterial("dragonforged", 2, 250, 6.0F, 2.0F, 15);

    public static ItemWeaponMF dragonforged_sword, dragonforged_waraxe, dragonforged_mace, dragonforged_dagger,
            dragonforged_spear;
    public static ItemWeaponMF dragonforged_greatsword, dragonforged_battleaxe, dragonforged_warhammer,
            dragonforged_katana, dragonforged_halbeard, dragonforged_lance;
    public static ItemPickMF dragonforged_pick;
    public static ItemAxeMF dragonforged_axe;
    public static ItemSpadeMF dragonforged_spade;
    public static ItemHoeMF dragonforged_hoe;
    public static ItemHvyPick dragonforged_hvypick;
    public static ItemHvyShovel dragonforged_hvyshovel;
    public static ItemHandpick dragonforged_handpick;
    public static ItemTrowMF dragonforged_trow;
    public static ItemScythe dragonforged_scythe;
    public static ItemMattock dragonforged_mattock;
    public static ItemLumberAxe dragonforged_lumber;

    public static ItemHammer dragonforged_hammer, dragonforged_hvyhammer;
    public static ItemTongs dragonforged_tongs;
    public static ItemShearsMF dragonforged_shears;
    public static ItemKnifeMF dragonforged_knife;
    public static ItemNeedle dragonforged_needle;
    public static ItemSaw dragonforged_saw;
    public static ItemSpanner dragonforged_spanner;

    public static ItemBowMF dragonforged_bow;

    public static ItemCustomArmour dragonforged_scale_helmet, dragonforged_scale_chest, dragonforged_scale_legs,
            dragonforged_scale_boots;
    public static ItemCustomArmour dragonforged_chain_helmet, dragonforged_chain_chest, dragonforged_chain_legs,
            dragonforged_chain_boots;
    public static ItemCustomArmour dragonforged_splint_helmet, dragonforged_splint_chest, dragonforged_splint_legs,
            dragonforged_splint_boots;
    public static ItemCustomArmour dragonforged_plate_helmet, dragonforged_plate_chest, dragonforged_plate_legs,
            dragonforged_plate_boots;

    public static void load() {
        String design = "dragonforged";
        CreativeTabs tab = CreativeTabMF.tabDragonforged;
        ToolMaterial mat = dragonforged;
        float ratingMod = 1.2F;

        dragonforged_dagger = new ItemDagger(design + "_dagger", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(1);
        dragonforged_sword = new ItemSwordMF(design + "_sword", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(1);
        dragonforged_waraxe = new ItemWaraxeMF(design + "_waraxe", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(1);
        dragonforged_mace = new ItemMaceMF(design + "_mace", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(1);
        dragonforged_spear = new ItemSpearMF(design + "_spear", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(1);
        dragonforged_katana = new ItemKatanaMF(design + "_katana", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(1);
        dragonforged_greatsword = new ItemGreatswordMF(design + "_greatsword", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(1);
        dragonforged_battleaxe = new ItemBattleaxeMF(design + "_battleaxe", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(1);
        dragonforged_warhammer = new ItemWarhammerMF(design + "_warhammer", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(1);
        dragonforged_halbeard = new ItemHalbeardMF(design + "_halbeard", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(1);
        dragonforged_lance = new ItemLance(design + "_lance", mat, 0, 1F).setCustom(design).setTab(tab)
                .modifyBaseDamage(1);

        dragonforged_bow = (ItemBowMF) new ItemBowMF(design + "_bow", mat, EnumBowType.SHORTBOW, 1).setCustom(design)
                .setCreativeTab(tab);

        // Tools
        dragonforged_pick = (ItemPickMF) new ItemPickMF(design + "_pick", mat, 0).setCustom(design).setCreativeTab(tab);
        dragonforged_axe = (ItemAxeMF) new ItemAxeMF(design + "_axe", mat, 0).setCustom(design).setCreativeTab(tab);
        dragonforged_spade = (ItemSpadeMF) new ItemSpadeMF(design + "_spade", mat, 0).setCustom(design)
                .setCreativeTab(tab);
        dragonforged_hoe = (ItemHoeMF) new ItemHoeMF(design + "_hoe", mat, 0).setCustom(design).setCreativeTab(tab);

        dragonforged_handpick = (ItemHandpick) new ItemHandpick(design + "_handpick", mat, 0).setCustom(design)
                .setCreativeTab(tab);
        dragonforged_hvypick = (ItemHvyPick) new ItemHvyPick(design + "_hvypick", mat, 0).setCustom(design)
                .setCreativeTab(tab);
        dragonforged_trow = (ItemTrowMF) new ItemTrowMF(design + "_trow", mat, 0).setCustom(design).setCreativeTab(tab);
        dragonforged_hvyshovel = (ItemHvyShovel) new ItemHvyShovel(design + "_hvyshovel", mat, 0).setCustom(design)
                .setCreativeTab(tab);
        dragonforged_scythe = (ItemScythe) new ItemScythe(design + "_scythe", mat, 0).setCustom(design)
                .setCreativeTab(tab);
        dragonforged_mattock = (ItemMattock) new ItemMattock(design + "_mattock", mat, 0).setCustom(design)
                .setCreativeTab(tab);
        dragonforged_lumber = (ItemLumberAxe) new ItemLumberAxe(design + "_lumber", mat, 0).setCustom(design)
                .setCreativeTab(tab);

        // Crafters
        dragonforged_hammer = (ItemHammer) new ItemHammer(design + "_hammer", mat, false, 0, 0).setCustom(design)
                .setCreativeTab(tab);
        dragonforged_hvyhammer = (ItemHammer) new ItemHammer(design + "_hvyhammer", mat, true, 0, 0).setCustom(design)
                .setCreativeTab(tab);
        dragonforged_shears = (ItemShearsMF) new ItemShearsMF(design + "_shears", mat, 0, 0).setCustom(design)
                .setCreativeTab(tab);
        dragonforged_knife = (ItemKnifeMF) new ItemKnifeMF(design + "_knife", mat, 0, 1F, 0).setCustom(design)
                .setCreativeTab(tab);
        dragonforged_needle = (ItemNeedle) new ItemNeedle(design + "_needle", mat, 0, 0).setCustom(design)
                .setCreativeTab(tab);
        dragonforged_saw = (ItemSaw) new ItemSaw(design + "_saw", mat, 0, 0).setCustom(design).setCreativeTab(tab);
        dragonforged_tongs = (ItemTongs) new ItemTongs(design + "_tongs", mat, 0).setCustom(design).setCreativeTab(tab);
        dragonforged_spanner = (ItemSpanner) new ItemSpanner(design + "_spanner", 0, 0).setCustom(design)
                .setCreativeTab(tab);

        dragonforged_chain_helmet = (ItemCustomArmour) new ItemCustomArmour(design, "chain_helmet",
                ArmourDesign.CHAINMAIL, 0, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        dragonforged_chain_chest = (ItemCustomArmour) new ItemCustomArmour(design, "chain_chest",
                ArmourDesign.CHAINMAIL, 1, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        dragonforged_chain_legs = (ItemCustomArmour) new ItemCustomArmour(design, "chain_legs", ArmourDesign.CHAINMAIL,
                2, "chain_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        dragonforged_chain_boots = (ItemCustomArmour) new ItemCustomArmour(design, "chain_boots",
                ArmourDesign.CHAINMAIL, 3, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);

        dragonforged_scale_helmet = (ItemCustomArmour) new ItemCustomArmour(design, "scale_helmet",
                ArmourDesign.SCALEMAIL, 0, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        dragonforged_scale_chest = (ItemCustomArmour) new ItemCustomArmour(design, "scale_chest",
                ArmourDesign.SCALEMAIL, 1, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        dragonforged_scale_legs = (ItemCustomArmour) new ItemCustomArmour(design, "scale_legs", ArmourDesign.SCALEMAIL,
                2, "scale_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        dragonforged_scale_boots = (ItemCustomArmour) new ItemCustomArmour(design, "scale_boots",
                ArmourDesign.SCALEMAIL, 3, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);

        dragonforged_splint_helmet = (ItemCustomArmour) new ItemCustomArmour(design, "splint_helmet",
                ArmourDesign.SPLINTMAIL, 0, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        dragonforged_splint_chest = (ItemCustomArmour) new ItemCustomArmour(design, "splint_chest",
                ArmourDesign.SPLINTMAIL, 1, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        dragonforged_splint_legs = (ItemCustomArmour) new ItemCustomArmour(design, "splint_legs",
                ArmourDesign.SPLINTMAIL, 2, "splint_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        dragonforged_splint_boots = (ItemCustomArmour) new ItemCustomArmour(design, "splint_boots",
                ArmourDesign.SPLINTMAIL, 3, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);

        dragonforged_plate_helmet = (ItemCustomArmour) new ItemCustomArmour(design, "plate_helmet",
                ArmourDesign.FIELDPLATE, 0, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        dragonforged_plate_chest = (ItemCustomArmour) new ItemCustomArmour(design, "plate_chest",
                ArmourDesign.FIELDPLATE, 1, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
        dragonforged_plate_legs = (ItemCustomArmour) new ItemCustomArmour(design, "plate_legs", ArmourDesign.FIELDPLATE,
                2, "plate_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
        dragonforged_plate_boots = (ItemCustomArmour) new ItemCustomArmour(design, "plate_boots",
                ArmourDesign.FIELDPLATE, 3, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
    }

    public static void loadCrafting() {
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_dagger, dragonforged_dagger);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_sword, dragonforged_sword);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_mace, dragonforged_mace);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_waraxe, dragonforged_waraxe);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_spear, dragonforged_spear);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_katana, dragonforged_katana);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_greatsword, dragonforged_greatsword);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_warhammer, dragonforged_warhammer);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_battleaxe, dragonforged_battleaxe);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_halbeard, dragonforged_halbeard);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_lance, dragonforged_lance);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_bow, dragonforged_bow);

        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_pick, dragonforged_pick);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_axe, dragonforged_axe);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_spade, dragonforged_spade);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_hoe, dragonforged_hoe);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_shears, dragonforged_shears);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_hvypick, dragonforged_hvypick);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_hvyshovel, dragonforged_hvyshovel);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_trow, dragonforged_trow);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_handpick, dragonforged_handpick);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_mattock, dragonforged_mattock);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_spade, dragonforged_spade);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_scythe, dragonforged_scythe);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_spanner, dragonforged_spanner);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_lumber, dragonforged_lumber);

        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_hammer, dragonforged_hammer);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_hvyhammer, dragonforged_hvyhammer);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_tongs, dragonforged_tongs);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_saw, dragonforged_saw);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_needle, dragonforged_needle);
        SpecialForging.addDragonforgeCraft(CustomToolListMF.standard_knife, dragonforged_knife);

        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_chain_boots, dragonforged_chain_boots);
        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_chain_legs, dragonforged_chain_legs);
        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_chain_chest, dragonforged_chain_chest);
        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_chain_helmet, dragonforged_chain_helmet);

        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_scale_boots, dragonforged_scale_boots);
        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_scale_legs, dragonforged_scale_legs);
        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_scale_chest, dragonforged_scale_chest);
        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_scale_helmet, dragonforged_scale_helmet);

        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_splint_boots, dragonforged_splint_boots);
        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_splint_legs, dragonforged_splint_legs);
        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_splint_chest, dragonforged_splint_chest);
        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_splint_helmet, dragonforged_splint_helmet);

        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_plate_boots, dragonforged_plate_boots);
        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_plate_legs, dragonforged_plate_legs);
        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_plate_chest, dragonforged_plate_chest);
        SpecialForging.addDragonforgeCraft(CustomArmourListMF.standard_plate_helmet, dragonforged_plate_helmet);
    }
}
