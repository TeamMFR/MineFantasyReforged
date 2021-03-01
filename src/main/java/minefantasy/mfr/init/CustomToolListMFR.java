package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.item.ArrowType;
import minefantasy.mfr.item.EnumBowType;
import minefantasy.mfr.item.ItemArrowMFR;
import minefantasy.mfr.item.ItemAxeMFR;
import minefantasy.mfr.item.ItemBasicCraftTool;
import minefantasy.mfr.item.ItemBattleaxe;
import minefantasy.mfr.item.ItemBowMFR;
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
    public static ItemWeaponMFR STANDARD_SWORD = Utils.nullValue();
    public static ItemWeaponMFR STANDARD_WARAXE = Utils.nullValue();
    public static ItemWeaponMFR STANDARD_MACE = Utils.nullValue();
    public static ItemWeaponMFR STANDARD_DAGGER = Utils.nullValue();
    public static ItemWeaponMFR STANDARD_SPEAR = Utils.nullValue();
    public static ItemWeaponMFR STANDARD_GREATSWORD = Utils.nullValue();
    public static ItemWeaponMFR STANDARD_BATTLEAXE = Utils.nullValue();
    public static ItemWeaponMFR STANDARD_WARHAMMER = Utils.nullValue();
    public static ItemWeaponMFR STANDARD_KATANA = Utils.nullValue();
    public static ItemWeaponMFR STANDARD_HALBEARD = Utils.nullValue();
    public static ItemWeaponMFR STANDARD_LANCE = Utils.nullValue();
    public static ItemPickMFR STANDARD_PICK = Utils.nullValue();
    public static ItemAxeMFR STANDARD_AXE = Utils.nullValue();
    public static ItemSpade STANDARD_SPADE = Utils.nullValue();
    public static ItemHoeMFR STANDARD_HOE = Utils.nullValue();
    public static ItemHeavyPick STANDARD_HVYPICK = Utils.nullValue();
    public static ItemHeavyShovel STANDARD_HVYSHOVEL = Utils.nullValue();
    public static ItemHandpick STANDARD_HANDPICK = Utils.nullValue();
    public static ItemTrow STANDARD_TROW = Utils.nullValue();
    public static ItemScythe STANDARD_SCYTHE = Utils.nullValue();
    public static ItemMattock STANDARD_MATTOCK = Utils.nullValue();
    public static ItemLumberAxe STANDARD_LUMBER = Utils.nullValue();

    public static ItemHammer STANDARD_HAMMER = Utils.nullValue();
    public static ItemHammer STANDARD_HVYHAMMER = Utils.nullValue();
    public static ItemTongs STANDARD_TONGS = Utils.nullValue();
    public static ItemShearsMFR STANDARD_SHEARS = Utils.nullValue();
    public static ItemKnife STANDARD_KNIFE = Utils.nullValue();
    public static ItemNeedle STANDARD_NEEDLE = Utils.nullValue();
    public static ItemSaw STANDARD_SAW = Utils.nullValue();
    public static ItemBasicCraftTool STANDARD_SPOON = Utils.nullValue();
    public static ItemBasicCraftTool STANDARD_MALLET = Utils.nullValue();
    public static ItemSpanner STANDARD_SPANNER = Utils.nullValue();

    public static ItemBowMFR STANDARD_BOW = Utils.nullValue();
    public static ItemArrowMFR STANDARD_ARROW = Utils.nullValue();
    public static ItemArrowMFR STANDARD_BOLT = Utils.nullValue();
    public static ItemArrowMFR STANDARD_ARROW_BODKIN = Utils.nullValue();
    public static ItemArrowMFR STANDARD_ARROW_BROAD = Utils.nullValue();

    public static void init() {
        String design = "standard";
        CreativeTabs tab = MineFantasyTabs.tabWeapon;

        // Standard Weapons
        STANDARD_SWORD = new ItemSword(design + "_sword", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        STANDARD_WARAXE = new ItemWaraxe(design + "_waraxe", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        STANDARD_MACE = new ItemMace(design + "_mace", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        STANDARD_DAGGER = new ItemDagger(design + "_dagger", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        STANDARD_SPEAR = new ItemSpear(design + "_spear", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        STANDARD_GREATSWORD = new ItemGreatsword(design + "_greatsword", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        STANDARD_BATTLEAXE = new ItemBattleaxe(design + "_battleaxe", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        STANDARD_WARHAMMER = new ItemWarhammer(design + "_warhammer", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        STANDARD_KATANA = new ItemKatana(design + "_katana", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        STANDARD_HALBEARD = new ItemHalbeard(design + "_halbeard", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
        STANDARD_LANCE = new ItemLance(design + "_lance", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);

        // Standard Tools
        tab = MineFantasyTabs.tabTool;
        STANDARD_PICK = (ItemPickMFR) new ItemPickMFR(design + "_pick", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
        STANDARD_AXE = (ItemAxeMFR) new ItemAxeMFR(design + "_axe", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
        STANDARD_SPADE = (ItemSpade) new ItemSpade(design + "_spade", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
        STANDARD_HOE = (ItemHoeMFR) new ItemHoeMFR(design + "_hoe", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);

        tab = MineFantasyTabs.tabToolAdvanced;
        STANDARD_HVYPICK = (ItemHeavyPick) new ItemHeavyPick(design + "_hvypick", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
        STANDARD_HVYSHOVEL = (ItemHeavyShovel) new ItemHeavyShovel(design + "_hvyshovel", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
        STANDARD_HANDPICK = (ItemHandpick) new ItemHandpick(design + "_handpick", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
        STANDARD_TROW = (ItemTrow) new ItemTrow(design + "_trow", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
        STANDARD_SCYTHE = (ItemScythe) new ItemScythe(design + "_scythe", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
        STANDARD_MATTOCK = (ItemMattock) new ItemMattock(design + "_mattock", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
        STANDARD_LUMBER = (ItemLumberAxe) new ItemLumberAxe(design + "_lumber", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);

        // Standard Crafters
        tab = MineFantasyTabs.tabCraftTool;
        STANDARD_HAMMER = (ItemHammer) new ItemHammer(design + "_hammer", ToolMaterial.IRON, false, 0, 0).setCustom(design).setCreativeTab(tab);
        STANDARD_HVYHAMMER = (ItemHammer) new ItemHammer(design + "_hvyhammer", ToolMaterial.IRON, true, 0, 0).setCustom(design).setCreativeTab(tab);
        STANDARD_TONGS = (ItemTongs) new ItemTongs(design + "_tongs", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
        STANDARD_SHEARS = (ItemShearsMFR) new ItemShearsMFR(design + "_shears", ToolMaterial.IRON, 0, 0).setCustom().setCreativeTab(tab);
        STANDARD_KNIFE = (ItemKnife) new ItemKnife(design + "_knife", ToolMaterial.IRON, 0, 1F, 0).setCustom(design).setCreativeTab(tab);
        STANDARD_NEEDLE = (ItemNeedle) new ItemNeedle(design + "_needle", ToolMaterial.IRON, 0, 0).setCustom(design).setCreativeTab(tab);
        STANDARD_SAW = (ItemSaw) new ItemSaw(design + "_saw", ToolMaterial.IRON, 0, 0).setCustom(design).setCreativeTab(tab);

        STANDARD_SPOON = (ItemBasicCraftTool) new ItemBasicCraftTool(design + "_spoon", Tool.SPOON, 0, 64).setCustom(design).setCreativeTab(tab);
        STANDARD_MALLET = (ItemBasicCraftTool) new ItemBasicCraftTool(design + "_mallet", Tool.MALLET, 0, 64).setCustom(design).setCreativeTab(tab);
        STANDARD_SPANNER = (ItemSpanner) new ItemSpanner(design + "_spanner", 0, 0).setCustom(design).setCreativeTab(tab);

        tab = MineFantasyTabs.tabArchery;
        STANDARD_BOW = (ItemBowMFR) new ItemBowMFR(design + "_bow", EnumBowType.SHORTBOW).setCustom(design).setCreativeTab(tab);
        STANDARD_ARROW = (ItemArrowMFR) new ItemArrowMFR(design, ArrowType.BOLT, 20).setCustom().setAmmoType("bolt").setCreativeTab(tab);
        STANDARD_BOLT = (ItemArrowMFR) new ItemArrowMFR(design, ArrowType.NORMAL, 16).setCustom().setCreativeTab(tab);
        STANDARD_ARROW_BODKIN = (ItemArrowMFR) new ItemArrowMFR(design, ArrowType.BODKIN, 16).setCustom().setCreativeTab(tab);
        STANDARD_ARROW_BROAD = (ItemArrowMFR) new ItemArrowMFR(design, ArrowType.BROADHEAD, 16).setCustom().setCreativeTab(tab);
    }
    
    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        // Standard Weapons
        registry.register(STANDARD_SWORD);
        registry.register(STANDARD_WARAXE);
        registry.register(STANDARD_MACE);
        registry.register(STANDARD_DAGGER);
        registry.register(STANDARD_SPEAR);
        registry.register(STANDARD_GREATSWORD);
        registry.register(STANDARD_BATTLEAXE);
        registry.register(STANDARD_WARHAMMER);
        registry.register(STANDARD_KATANA);
        registry.register(STANDARD_HALBEARD);
        registry.register(STANDARD_LANCE);

        // Standard Tools
        registry.register(STANDARD_PICK);
        registry.register(STANDARD_AXE);
        registry.register(STANDARD_SPADE);
        registry.register(STANDARD_HOE);

        registry.register(STANDARD_HVYPICK);
        registry.register(STANDARD_HVYSHOVEL);
        registry.register(STANDARD_HANDPICK);
        registry.register(STANDARD_TROW);
        registry.register(STANDARD_SCYTHE);
        registry.register(STANDARD_MATTOCK);
        registry.register(STANDARD_LUMBER);

        // Standard Crafters
        registry.register(STANDARD_HAMMER);
        registry.register(STANDARD_HVYHAMMER);
        registry.register(STANDARD_TONGS);
        registry.register(STANDARD_SHEARS);
        registry.register(STANDARD_KNIFE);
        registry.register(STANDARD_NEEDLE);
        registry.register(STANDARD_SAW);


        registry.register(STANDARD_SPOON);
        registry.register(STANDARD_MALLET);
        registry.register(STANDARD_SPANNER);

        registry.register(STANDARD_BOW);
        registry.register(STANDARD_ARROW);
        registry.register(STANDARD_BOLT);
        registry.register(STANDARD_ARROW_BODKIN);
        registry.register(STANDARD_ARROW_BROAD);

    }
}
