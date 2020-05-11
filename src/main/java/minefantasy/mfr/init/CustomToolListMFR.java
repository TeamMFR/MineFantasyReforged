package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.item.archery.ArrowType;
import minefantasy.mfr.item.archery.EnumBowType;
import minefantasy.mfr.item.archery.ItemArrowMFR;
import minefantasy.mfr.item.archery.ItemBowMFR;
import minefantasy.mfr.item.tool.*;
import minefantasy.mfr.item.tool.advanced.*;
import minefantasy.mfr.item.tool.crafting.*;
import minefantasy.mfr.item.weapon.*;
import minefantasy.mfr.util.Utils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(MineFantasyReborn.MOD_ID)
@Mod.EventBusSubscriber(modid = MineFantasyReborn.MOD_ID)
public class CustomToolListMFR {
    // STANDARD
    public static final ItemWeaponMFR STANDARD_SWORD = Utils.nullValue();
    public static final ItemWeaponMFR STANDARD_WARAXE = Utils.nullValue();
    public static final ItemWeaponMFR STANDARD_MACE = Utils.nullValue();
    public static final ItemWeaponMFR STANDARD_DAGGER = Utils.nullValue();
    public static final ItemWeaponMFR STANDARD_SPEAR = Utils.nullValue();
    public static final ItemWeaponMFR STANDARD_GREATSWORD = Utils.nullValue();
    public static final ItemWeaponMFR STANDARD_BATTLEAXE = Utils.nullValue();
    public static final ItemWeaponMFR STANDARD_WARHAMMER = Utils.nullValue();
    public static final ItemWeaponMFR STANDARD_KATANA = Utils.nullValue();
    public static final ItemWeaponMFR STANDARD_HALBEARD = Utils.nullValue();
    public static final ItemWeaponMFR STANDARD_LANCE = Utils.nullValue();
    public static final ItemPickMF STANDARD_PICK = Utils.nullValue();
    public static final ItemAxeMF STANDARD_AXE = Utils.nullValue();
    public static final ItemSpadeMF STANDARD_SPADE = Utils.nullValue();
    public static final ItemHoeMF STANDARD_HOE = Utils.nullValue();
    public static final ItemHvyPick STANDARD_HVYPICK = Utils.nullValue();
    public static final ItemHvyShovel STANDARD_HVYSHOVEL = Utils.nullValue();
    public static final ItemHandpick STANDARD_HANDPICK = Utils.nullValue();
    public static final ItemTrowMF STANDARD_TROW = Utils.nullValue();
    public static final ItemScythe STANDARD_SCYTHE = Utils.nullValue();
    public static final ItemMattock STANDARD_MATTOCK = Utils.nullValue();
    public static final ItemLumberAxe STANDARD_LUMBER = Utils.nullValue();

    public static final ItemHammer STANDARD_HAMMER = Utils.nullValue();
    public static final ItemHammer STANDARD_HVYHAMMER = Utils.nullValue();
    public static final ItemTongs STANDARD_TONGS = Utils.nullValue();
    public static final ItemShearsMFR STANDARD_SHEARS = Utils.nullValue();
    public static final ItemKnifeMFR STANDARD_KNIFE = Utils.nullValue();
    public static final ItemNeedle STANDARD_NEEDLE = Utils.nullValue();
    public static final ItemSaw STANDARD_SAW = Utils.nullValue();
    public static final ItemBasicCraftTool STANDARD_SPOON = Utils.nullValue();
    public static final ItemBasicCraftTool STANDARD_MALLET = Utils.nullValue();
    public static final ItemSpanner STANDARD_SPANNER = Utils.nullValue();

    public static final ItemBowMFR STANDARD_BOW = Utils.nullValue();
    public static final ItemArrowMFR STANDARD_ARROW = Utils.nullValue();
    public static final ItemArrowMFR STANDARD_BOLT = Utils.nullValue();
    public static final ItemArrowMFR STANDARD_ARROW_BODKIN = Utils.nullValue();
    public static final ItemArrowMFR STANDARD_ARROW_BROAD = Utils.nullValue();

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        String design = "standard";
        CreativeTabs tab = CreativeTabMFR.tabWeapon;

        // Standard Weapons
        registry.register(new ItemDagger(design + "_dagger", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
        registry.register(new ItemSwordMF(design + "_sword", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
        registry.register(new ItemWaraxeMFR(design + "_waraxe", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
        registry.register(new ItemMaceMFR(design + "_mace", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
        registry.register(new ItemSpearMFR(design + "_spear", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
        registry.register(new ItemKatanaMFR(design + "_katana", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
        registry.register(new ItemGreatswordMFR(design + "_greatsword", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
        registry.register(new ItemBattleaxeMFR(design + "_battleaxe", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
        registry.register(new ItemWarhammerMFR(design + "_warhammer", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
        registry.register(new ItemHalbeardMFR(design + "_halbeard", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
        registry.register(new ItemLance(design + "_lance", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));

        tab = CreativeTabMFR.tabArcher;
        registry.register(new ItemBowMFR(design + "_bow", EnumBowType.SHORTBOW).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemArrowMFR(design + "_bolt", ArrowType.BOLT, 20).setCustom(design).setAmmoType("bolt").setCreativeTab(tab));
        registry.register(new ItemArrowMFR(design + "_arrow", ArrowType.NORMAL, 16).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemArrowMFR(design + "_arrow_bodkin", ArrowType.BODKIN, 16).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemArrowMFR(design + "_arrow_broad", ArrowType.BROADHEAD, 16).setCustom(design).setCreativeTab(tab));

        // Standard Tools
        tab = CreativeTabMFR.tabTool;
        registry.register(new ItemPickMF(design + "_pick", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemAxeMF(design + "_axe", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemSpadeMF(design + "_spade", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemHoeMF(design + "_hoe", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));

        tab = CreativeTabMFR.tabToolAdvanced;
        registry.register(new ItemHandpick(design + "_handpick", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemHvyPick(design + "_hvypick", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemTrowMF(design + "_trow", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemHvyShovel(design + "_hvyshovel", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemScythe(design + "_scythe", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemMattock(design + "_mattock", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemLumberAxe(design + "_lumber", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));

        // Standard Crafters
        tab = CreativeTabMFR.tabCraftTool;
        registry.register(new ItemHammer(design + "_hammer", ToolMaterial.IRON, false, 0, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemHammer(design + "_hvyhammer", ToolMaterial.IRON, true, 0, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemShearsMFR(design + "_shears", ToolMaterial.IRON, 0, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemKnifeMFR(design + "_knife", ToolMaterial.IRON, 0, 1F, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemNeedle(design + "_needle", ToolMaterial.IRON, 0, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemSaw(design + "_saw", ToolMaterial.IRON, 0, 0).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemTongs(design + "_tongs", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));

        registry.register(new ItemBasicCraftTool(design + "_spoon", "spoon", 0, 64).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemBasicCraftTool(design + "_mallet", "mallet", 0, 64).setCustom(design).setCreativeTab(tab));
        registry.register(new ItemSpanner(design + "_spanner", 0, 0).setCustom(design).setCreativeTab(tab));
    }
}
