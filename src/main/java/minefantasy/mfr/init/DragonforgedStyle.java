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
public class DragonforgedStyle {
	public static ToolMaterial DRAGONFORGED = EnumHelper.addToolMaterial("dragonforged", 2, 250, 6.0F, 2.0F, 15);

	public static ItemWeaponMFR DRAGONFORGED_SWORD = Utils.nullValue();
	public static ItemWeaponMFR DRAGONFORGED_WARAXE = Utils.nullValue();
	public static ItemWeaponMFR DRAGONFORGED_MACE = Utils.nullValue();
	public static ItemWeaponMFR DRAGONFORGED_DAGGER = Utils.nullValue();
	public static ItemWeaponMFR DRAGONFORGED_SPEAR = Utils.nullValue();
	public static ItemWeaponMFR DRAGONFORGED_GREATSWORD = Utils.nullValue();
	public static ItemWeaponMFR DRAGONFORGED_BATTLEAXE = Utils.nullValue();
	public static ItemWeaponMFR DRAGONFORGED_WARHAMMER = Utils.nullValue();
	public static ItemWeaponMFR DRAGONFORGED_KATANA = Utils.nullValue();
	public static ItemWeaponMFR DRAGONFORGED_HALBEARD = Utils.nullValue();
	public static ItemWeaponMFR DRAGONFORGED_LANCE = Utils.nullValue();
	public static ItemPickMFR DRAGONFORGED_PICK = Utils.nullValue();
	public static ItemAxeMFR DRAGONFORGED_AXE = Utils.nullValue();
	public static ItemSpade DRAGONFORGED_SPADE = Utils.nullValue();
	public static ItemHoeMFR DRAGONFORGED_HOE = Utils.nullValue();
	public static ItemHeavyPick DRAGONFORGED_HVYPICK = Utils.nullValue();
	public static ItemHeavyShovel DRAGONFORGED_HVYSHOVEL = Utils.nullValue();
	public static ItemHandpick DRAGONFORGED_HANDPICK = Utils.nullValue();
	public static ItemTrow DRAGONFORGED_TROW = Utils.nullValue();
	public static ItemScythe DRAGONFORGED_SCYTHE = Utils.nullValue();
	public static ItemMattock DRAGONFORGED_MATTOCK = Utils.nullValue();
	public static ItemLumberAxe DRAGONFORGED_LUMBER = Utils.nullValue();

	public static ItemHammer DRAGONFORGED_HAMMER = Utils.nullValue();
	public static ItemHammer DRAGONFORGED_HVYHAMMER = Utils.nullValue();
	public static ItemTongs DRAGONFORGED_TONGS = Utils.nullValue();
	public static ItemShearsMFR DRAGONFORGED_SHEARS = Utils.nullValue();
	public static ItemKnife DRAGONFORGED_KNIFE = Utils.nullValue();
	public static ItemNeedle DRAGONFORGED_NEEDLE = Utils.nullValue();
	public static ItemSaw DRAGONFORGED_SAW = Utils.nullValue();
	public static ItemSpanner DRAGONFORGED_SPANNER = Utils.nullValue();

	public static ItemBowMFR DRAGONFORGED_BOW = Utils.nullValue();

	public static ItemCustomArmour DRAGONFORGED_SCALE_HELMET = Utils.nullValue();
	public static ItemCustomArmour DRAGONFORGED_SCALE_CHEST = Utils.nullValue();
	public static ItemCustomArmour DRAGONFORGED_SCALE_LEGS = Utils.nullValue();
	public static ItemCustomArmour DRAGONFORGED_SCALE_BOOTS = Utils.nullValue();
	public static ItemCustomArmour DRAGONFORGED_CHAIN_HELMET = Utils.nullValue();
	public static ItemCustomArmour DRAGONFORGED_CHAIN_CHEST = Utils.nullValue();
	public static ItemCustomArmour DRAGONFORGED_CHAIN_LEGS = Utils.nullValue();
	public static ItemCustomArmour DRAGONFORGED_CHAIN_BOOTS = Utils.nullValue();
	public static ItemCustomArmour DRAGONFORGED_SPLINT_HELMET = Utils.nullValue();
	public static ItemCustomArmour DRAGONFORGED_SPLINT_CHEST = Utils.nullValue();
	public static ItemCustomArmour DRAGONFORGED_SPLINT_LEGS = Utils.nullValue();
	public static ItemCustomArmour DRAGONFORGED_SPLINT_BOOTS = Utils.nullValue();
	public static ItemCustomArmour DRAGONFORGED_PLATE_HELMET = Utils.nullValue();
	public static ItemCustomArmour DRAGONFORGED_PLATE_CHEST = Utils.nullValue();
	public static ItemCustomArmour DRAGONFORGED_PLATE_LEGS = Utils.nullValue();
	public static ItemCustomArmour DRAGONFORGED_PLATE_BOOTS = Utils.nullValue();

	public static void init() {
		String design = "dragonforged";
		CreativeTabs tab = MineFantasyTabs.tabDragonforged;
		ToolMaterial mat = DRAGONFORGED;
		float ratingMod = 1.2F;

		// Weapons
		DRAGONFORGED_SWORD = new ItemSword(design + "_sword", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		DRAGONFORGED_WARAXE = new ItemWaraxe(design + "_waraxe", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		DRAGONFORGED_MACE = new ItemMace(design + "_mace", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		DRAGONFORGED_DAGGER = new ItemDagger(design + "_dagger", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		DRAGONFORGED_SPEAR = new ItemSpear(design + "_spear", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		DRAGONFORGED_GREATSWORD = new ItemGreatsword(design + "_greatsword", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		DRAGONFORGED_BATTLEAXE = new ItemBattleaxe(design + "_battleaxe", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		DRAGONFORGED_WARHAMMER = new ItemWarhammer(design + "_warhammer", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		DRAGONFORGED_KATANA = new ItemKatana(design + "_katana", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		DRAGONFORGED_HALBEARD = new ItemHalbeard(design + "_halbeard", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		DRAGONFORGED_LANCE = new ItemLance(design + "_lance", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);

		DRAGONFORGED_BOW = (ItemBowMFR) new ItemBowMFR(design + "_bow", mat, EnumBowType.SHORTBOW, 1).setCustom(design).setCreativeTab(tab);

		// Tools
		DRAGONFORGED_PICK = (ItemPickMFR) new ItemPickMFR(design + "_pick", mat, 0).setCustom(design).setCreativeTab(tab);
		DRAGONFORGED_AXE = (ItemAxeMFR) new ItemAxeMFR(design + "_axe", mat, 0).setCustom(design).setCreativeTab(tab);
		DRAGONFORGED_SPADE = (ItemSpade) new ItemSpade(design + "_spade", mat, 0).setCustom(design).setCreativeTab(tab);
		DRAGONFORGED_HOE = (ItemHoeMFR) new ItemHoeMFR(design + "_hoe", mat, 0).setCustom(design).setCreativeTab(tab);

		DRAGONFORGED_HVYPICK = (ItemHeavyPick) new ItemHeavyPick(design + "_hvypick", mat, 0).setCustom(design).setCreativeTab(tab);
		DRAGONFORGED_HVYSHOVEL = (ItemHeavyShovel) new ItemHeavyShovel(design + "_hvyshovel", mat, 0).setCustom(design).setCreativeTab(tab);
		DRAGONFORGED_HANDPICK = (ItemHandpick) new ItemHandpick(design + "_handpick", mat, 0).setCustom(design).setCreativeTab(tab);
		DRAGONFORGED_TROW = (ItemTrow) new ItemTrow(design + "_trow", mat, 0).setCustom(design).setCreativeTab(tab);
		DRAGONFORGED_SCYTHE = (ItemScythe) new ItemScythe(design + "_scythe", mat, 0).setCustom(design).setCreativeTab(tab);
		DRAGONFORGED_MATTOCK = (ItemMattock) new ItemMattock(design + "_mattock", mat, 0).setCustom(design).setCreativeTab(tab);
		DRAGONFORGED_LUMBER = (ItemLumberAxe) new ItemLumberAxe(design + "_lumber", mat, 0).setCustom(design).setCreativeTab(tab);
		DRAGONFORGED_HAMMER = (ItemHammer) new ItemHammer(design + "_hammer", mat, false, 0, 0).setCustom(design).setCreativeTab(tab);
		DRAGONFORGED_HVYHAMMER = (ItemHammer) new ItemHammer(design + "_hvyhammer", mat, true, 0, 0).setCustom(design).setCreativeTab(tab);

		// Crafters
		DRAGONFORGED_TONGS = (ItemTongs) new ItemTongs(design + "_tongs", mat, 0).setCustom(design).setCreativeTab(tab);
		DRAGONFORGED_SHEARS = (ItemShearsMFR) new ItemShearsMFR(design + "_shears", mat, 0, 0).setCustom().setCreativeTab(tab);
		DRAGONFORGED_KNIFE = (ItemKnife) new ItemKnife(design + "_knife", mat, 0, 1F, 0).setCustom(design).setCreativeTab(tab);
		DRAGONFORGED_NEEDLE = (ItemNeedle) new ItemNeedle(design + "_needle", mat, 0, 0).setCustom(design).setCreativeTab(tab);
		DRAGONFORGED_SAW = (ItemSaw) new ItemSaw(design + "_saw", mat, 0, 0).setCustom(design).setCreativeTab(tab);
		DRAGONFORGED_SPANNER = (ItemSpanner) new ItemSpanner(design + "_spanner", 0, 0).setCustom(design).setCreativeTab(tab);

		// Armours
		DRAGONFORGED_SCALE_HELMET = (ItemCustomArmour) new ItemCustomArmour(design, "scale_helmet", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.HEAD, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
		DRAGONFORGED_SCALE_CHEST = (ItemCustomArmour) new ItemCustomArmour(design, "scale_chest", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.CHEST, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
		DRAGONFORGED_SCALE_LEGS = (ItemCustomArmour) new ItemCustomArmour(design, "scale_legs", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.LEGS, "scale_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
		DRAGONFORGED_SCALE_BOOTS = (ItemCustomArmour) new ItemCustomArmour(design, "scale_boots", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.FEET, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);

		DRAGONFORGED_CHAIN_HELMET = (ItemCustomArmour) new ItemCustomArmour(design, "chain_helmet", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.HEAD, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
		DRAGONFORGED_CHAIN_CHEST = (ItemCustomArmour) new ItemCustomArmour(design, "chain_chest", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.CHEST, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
		DRAGONFORGED_CHAIN_LEGS = (ItemCustomArmour) new ItemCustomArmour(design, "chain_legs", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.LEGS, "chain_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
		DRAGONFORGED_CHAIN_BOOTS = (ItemCustomArmour) new ItemCustomArmour(design, "chain_boots", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.FEET, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);

		DRAGONFORGED_SPLINT_HELMET = (ItemCustomArmour) new ItemCustomArmour(design, "splint_helmet", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.HEAD, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
		DRAGONFORGED_SPLINT_CHEST = (ItemCustomArmour) new ItemCustomArmour(design, "splint_chest", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.CHEST, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
		DRAGONFORGED_SPLINT_LEGS = (ItemCustomArmour) new ItemCustomArmour(design, "splint_legs", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.LEGS, "splint_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
		DRAGONFORGED_SPLINT_BOOTS = (ItemCustomArmour) new ItemCustomArmour(design, "splint_boots", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.FEET, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);

		DRAGONFORGED_PLATE_HELMET = (ItemCustomArmour) new ItemCustomArmour(design, "plate_helmet", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.HEAD, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
		DRAGONFORGED_PLATE_CHEST = (ItemCustomArmour) new ItemCustomArmour(design, "plate_chest", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.CHEST, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
		DRAGONFORGED_PLATE_LEGS = (ItemCustomArmour) new ItemCustomArmour(design, "plate_legs", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.LEGS, "plate_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab);
		DRAGONFORGED_PLATE_BOOTS = (ItemCustomArmour) new ItemCustomArmour(design, "plate_boots", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.FEET, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab);
	}

	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		// Weapons
		registry.register(DRAGONFORGED_SWORD);
		registry.register(DRAGONFORGED_WARAXE);
		registry.register(DRAGONFORGED_MACE);
		registry.register(DRAGONFORGED_DAGGER);
		registry.register(DRAGONFORGED_SPEAR);
		registry.register(DRAGONFORGED_GREATSWORD);
		registry.register(DRAGONFORGED_BATTLEAXE);
		registry.register(DRAGONFORGED_WARHAMMER);
		registry.register(DRAGONFORGED_KATANA);
		registry.register(DRAGONFORGED_HALBEARD);
		registry.register(DRAGONFORGED_LANCE);

		registry.register(DRAGONFORGED_BOW);

		// Tools
		registry.register(DRAGONFORGED_PICK);
		registry.register(DRAGONFORGED_AXE);
		registry.register(DRAGONFORGED_SPADE);
		registry.register(DRAGONFORGED_HOE);

		registry.register(DRAGONFORGED_HVYPICK);
		registry.register(DRAGONFORGED_HVYSHOVEL);
		registry.register(DRAGONFORGED_HANDPICK);
		registry.register(DRAGONFORGED_TROW);
		registry.register(DRAGONFORGED_SCYTHE);
		registry.register(DRAGONFORGED_MATTOCK);
		registry.register(DRAGONFORGED_LUMBER);
		registry.register(DRAGONFORGED_HAMMER);
		registry.register(DRAGONFORGED_HVYHAMMER);

		// Crafters
		registry.register(DRAGONFORGED_TONGS);
		registry.register(DRAGONFORGED_SHEARS);
		registry.register(DRAGONFORGED_KNIFE);
		registry.register(DRAGONFORGED_NEEDLE);
		registry.register(DRAGONFORGED_SAW);
		registry.register(DRAGONFORGED_SPANNER);

		// Armours
		registry.register(DRAGONFORGED_SCALE_HELMET);
		registry.register(DRAGONFORGED_SCALE_CHEST);
		registry.register(DRAGONFORGED_SCALE_LEGS);
		registry.register(DRAGONFORGED_SCALE_BOOTS);

		registry.register(DRAGONFORGED_CHAIN_HELMET);
		registry.register(DRAGONFORGED_CHAIN_CHEST);
		registry.register(DRAGONFORGED_CHAIN_LEGS);
		registry.register(DRAGONFORGED_CHAIN_BOOTS);

		registry.register(DRAGONFORGED_SPLINT_HELMET);
		registry.register(DRAGONFORGED_SPLINT_CHEST);
		registry.register(DRAGONFORGED_SPLINT_LEGS);
		registry.register(DRAGONFORGED_SPLINT_BOOTS);

		registry.register(DRAGONFORGED_PLATE_HELMET);
		registry.register(DRAGONFORGED_PLATE_CHEST);
		registry.register(DRAGONFORGED_PLATE_LEGS);
		registry.register(DRAGONFORGED_PLATE_BOOTS);
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
