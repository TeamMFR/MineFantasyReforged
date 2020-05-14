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
public class DragonforgedStyle {
    public static ToolMaterial DRAGONFORGED = EnumHelper.addToolMaterial("dragonforged", 2, 250, 6.0F, 2.0F, 15);

    public static final ItemWeaponMFR DRAGONFORGED_SWORD = Utils.nullValue();
    public static final ItemWeaponMFR DRAGONFORGED_WARAXE = Utils.nullValue();
    public static final ItemWeaponMFR DRAGONFORGED_MACE = Utils.nullValue();
    public static final ItemWeaponMFR DRAGONFORGED_DAGGER = Utils.nullValue();
    public static final ItemWeaponMFR DRAGONFORGED_SPEAR = Utils.nullValue();
    public static final ItemWeaponMFR DRAGONFORGED_GREATSWORD = Utils.nullValue();
    public static final ItemWeaponMFR DRAGONFORGED_BATTLEAXE = Utils.nullValue();
    public static final ItemWeaponMFR DRAGONFORGED_WARHAMMER = Utils.nullValue();
    public static final ItemWeaponMFR DRAGONFORGED_KATANA = Utils.nullValue();
    public static final ItemWeaponMFR DRAGONFORGED_HALBEARD = Utils.nullValue();
    public static final ItemWeaponMFR DRAGONFORGED_LANCE = Utils.nullValue();
    public static final ItemPickMF DRAGONFORGED_PICK = Utils.nullValue();
    public static final ItemAxeMF DRAGONFORGED_AXE = Utils.nullValue();
    public static final ItemSpadeMF DRAGONFORGED_SPADE = Utils.nullValue();
    public static final ItemHoeMF DRAGONFORGED_HOE = Utils.nullValue();
    public static final ItemHvyPick DRAGONFORGED_HVYPICK = Utils.nullValue();
    public static final ItemHvyShovel DRAGONFORGED_HVYSHOVEL = Utils.nullValue();
    public static final ItemHandpick DRAGONFORGED_HANDPICK = Utils.nullValue();
    public static final ItemTrowMF DRAGONFORGED_TROW = Utils.nullValue();
    public static final ItemScythe DRAGONFORGED_SCYTHE = Utils.nullValue();
    public static final ItemMattock DRAGONFORGED_MATTOCK = Utils.nullValue();
    public static final ItemLumberAxe DRAGONFORGED_LUMBER = Utils.nullValue();

    public static final ItemHammer DRAGONFORGED_HAMMER = Utils.nullValue();
    public static final ItemHammer DRAGONFORGED_HVYHAMMER = Utils.nullValue();
    public static final ItemTongs DRAGONFORGED_TONGS = Utils.nullValue();
    public static final ItemShearsMFR DRAGONFORGED_SHEARS = Utils.nullValue();
    public static final ItemKnifeMFR DRAGONFORGED_KNIFE = Utils.nullValue();
    public static final ItemNeedle DRAGONFORGED_NEEDLE = Utils.nullValue();
    public static final ItemSaw DRAGONFORGED_SAW = Utils.nullValue();
    public static final ItemSpanner DRAGONFORGED_SPANNER = Utils.nullValue();

    public static final ItemBowMFR DRAGONFORGED_BOW = Utils.nullValue();

    public static final ItemCustomArmour DRAGONFORGED_SCALE_HELMET = Utils.nullValue();
    public static final ItemCustomArmour DRAGONFORGED_SCALE_CHEST = Utils.nullValue();
    public static final ItemCustomArmour DRAGONFORGED_SCALE_LEGS = Utils.nullValue();
    public static final ItemCustomArmour DRAGONFORGED_SCALE_BOOTS = Utils.nullValue();
    public static final ItemCustomArmour DRAGONFORGED_CHAIN_HELMET = Utils.nullValue();
    public static final ItemCustomArmour DRAGONFORGED_CHAIN_CHEST = Utils.nullValue();
    public static final ItemCustomArmour DRAGONFORGED_CHAIN_LEGS = Utils.nullValue();
    public static final ItemCustomArmour DRAGONFORGED_CHAIN_BOOTS = Utils.nullValue();
    public static final ItemCustomArmour DRAGONFORGED_SPLINT_HELMET = Utils.nullValue();
    public static final ItemCustomArmour DRAGONFORGED_SPLINT_CHEST = Utils.nullValue();
    public static final ItemCustomArmour DRAGONFORGED_SPLINT_LEGS = Utils.nullValue();
    public static final ItemCustomArmour DRAGONFORGED_SPLINT_BOOTS = Utils.nullValue();
    public static final ItemCustomArmour DRAGONFORGED_PLATE_HELMET = Utils.nullValue();
    public static final ItemCustomArmour DRAGONFORGED_PLATE_CHEST = Utils.nullValue();
    public static final ItemCustomArmour DRAGONFORGED_PLATE_LEGS = Utils.nullValue();
    public static final ItemCustomArmour DRAGONFORGED_PLATE_BOOTS = Utils.nullValue();

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        String design = "dragonforged";
        CreativeTabs tab = CreativeTabMFR.tabDragonforged;
        ToolMaterial mat = DRAGONFORGED;
        float ratingMod = 1.2F;

        // Weapons
        registry.register(new ItemDagger(design + "_dagger", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
        registry.register(new ItemSwordMF(design + "_sword", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
        registry.register(new ItemWaraxeMFR(design + "_waraxe", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
        registry.register(new ItemMaceMFR(design + "_mace", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
        registry.register(new ItemSpearMFR(design + "_spear", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
        registry.register(new ItemKatanaMFR(design + "_katana", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
        registry.register(new ItemGreatswordMFR(design + "_greatsword", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
        registry.register(new ItemBattleaxeMFR(design + "_battleaxe", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
        registry.register(new ItemWarhammerMFR(design + "_warhammer", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
        registry.register(new ItemHalbeardMFR(design + "_halbeard", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
        registry.register(new ItemLance(design + "_lance", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));

        registry.register(new ItemBowMFR(design + "_bow", mat, EnumBowType.SHORTBOW, 1).setCustom(design).setCreativeTab(tab));

        // Tools
        registry.register(new ItemPickMF(design + "_pick", mat, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemAxeMF(design + "_axe", mat, 0).setCustom(design).setCreativeTab(tab));
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
        registry.register(new ItemShearsMFR(design + "_shears", mat, 0, 0).setCustom(design).setCreativeTab(tab));
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
        registry.register(new ItemCustomArmour(design, "scale_legs", ArmourDesign.SCALEMAIL,EntityEquipmentSlot.LEGS, "scale_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "scale_boots", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.FEET, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));

        registry.register(new ItemCustomArmour(design, "splint_helmet", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.HEAD, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "splint_chest", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.CHEST, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "splint_legs", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.LEGS, "splint_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "splint_boots", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.FEET, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));

        registry.register(new ItemCustomArmour(design, "plate_helmet", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.HEAD, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "plate_chest", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.CHEST, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "plate_legs", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.LEGS, "plate_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab));
        registry.register(new ItemCustomArmour(design, "plate_boots", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.FEET, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
    }

    public static void loadCrafting() {
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_DAGGER, DRAGONFORGED_DAGGER);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_SWORD, DRAGONFORGED_SWORD);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_MACE, DRAGONFORGED_MACE);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_WARAXE, DRAGONFORGED_WARAXE);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_SPEAR, DRAGONFORGED_SPEAR);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_KATANA, DRAGONFORGED_KATANA);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_GREATSWORD, DRAGONFORGED_GREATSWORD);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_WARHAMMER, DRAGONFORGED_WARHAMMER);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_BATTLEAXE, DRAGONFORGED_BATTLEAXE);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_HALBEARD, DRAGONFORGED_HALBEARD);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_LANCE, DRAGONFORGED_LANCE);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_BOW, DRAGONFORGED_BOW);

        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_PICK, DRAGONFORGED_PICK);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_AXE, DRAGONFORGED_AXE);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_SPADE, DRAGONFORGED_SPADE);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_HOE, DRAGONFORGED_HOE);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_SHEARS, DRAGONFORGED_SHEARS);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_HVYPICK, DRAGONFORGED_HVYPICK);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_HVYSHOVEL, DRAGONFORGED_HVYSHOVEL);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_TROW, DRAGONFORGED_TROW);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_HANDPICK, DRAGONFORGED_HANDPICK);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_MATTOCK, DRAGONFORGED_MATTOCK);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_SPADE, DRAGONFORGED_SPADE);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_SCYTHE, DRAGONFORGED_SCYTHE);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_SPANNER, DRAGONFORGED_SPANNER);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_LUMBER, DRAGONFORGED_LUMBER);

        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_HAMMER, DRAGONFORGED_HAMMER);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_HVYHAMMER, DRAGONFORGED_HVYHAMMER);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_TONGS, DRAGONFORGED_TONGS);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_SAW, DRAGONFORGED_SAW);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_NEEDLE, DRAGONFORGED_NEEDLE);
        SpecialForging.addDragonforgeCraft(CustomToolListMFR.STANDARD_KNIFE, DRAGONFORGED_KNIFE);

        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_CHAIN_BOOTS, DRAGONFORGED_CHAIN_BOOTS);
        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_CHAIN_LEGS, DRAGONFORGED_CHAIN_LEGS);
        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_CHAIN_CHEST, DRAGONFORGED_CHAIN_CHEST);
        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_CHAIN_HELMET, DRAGONFORGED_CHAIN_HELMET);

        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_SCALE_BOOTS, DRAGONFORGED_SCALE_BOOTS);
        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_SCALE_LEGS, DRAGONFORGED_SCALE_LEGS);
        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_SCALE_CHEST, DRAGONFORGED_SCALE_CHEST);
        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_SCALE_HELMET, DRAGONFORGED_SCALE_HELMET);

        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_SPLINT_BOOTS, DRAGONFORGED_SPLINT_BOOTS);
        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_SPLINT_LEGS, DRAGONFORGED_SPLINT_LEGS);
        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_SPLINT_CHEST, DRAGONFORGED_SPLINT_CHEST);
        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_SPLINT_HELMET, DRAGONFORGED_SPLINT_HELMET);

        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_PLATE_BOOTS, DRAGONFORGED_PLATE_BOOTS);
        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_PLATE_LEGS, DRAGONFORGED_PLATE_LEGS);
        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_PLATE_CHEST, DRAGONFORGED_PLATE_CHEST);
        SpecialForging.addDragonforgeCraft(CustomArmourListMFR.STANDARD_PLATE_HELMET, DRAGONFORGED_PLATE_HELMET);
    }
}
