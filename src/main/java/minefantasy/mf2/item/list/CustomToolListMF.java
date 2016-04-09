package minefantasy.mf2.item.list;

import minefantasy.mf2.api.crafting.exotic.SpecialForging;
import minefantasy.mf2.item.archery.ArrowType;
import minefantasy.mf2.item.archery.EnumBowType;
import minefantasy.mf2.item.archery.ItemArrowMF;
import minefantasy.mf2.item.archery.ItemBowMF;
import minefantasy.mf2.item.tool.ItemAxeMF;
import minefantasy.mf2.item.tool.ItemHoeMF;
import minefantasy.mf2.item.tool.ItemPickMF;
import minefantasy.mf2.item.tool.ItemShearsMF;
import minefantasy.mf2.item.tool.ItemSpadeMF;
import minefantasy.mf2.item.tool.advanced.ItemHandpick;
import minefantasy.mf2.item.tool.advanced.ItemHvyPick;
import minefantasy.mf2.item.tool.advanced.ItemHvyShovel;
import minefantasy.mf2.item.tool.advanced.ItemScythe;
import minefantasy.mf2.item.tool.advanced.ItemTrowMF;
import minefantasy.mf2.item.tool.crafting.ItemBasicCraftTool;
import minefantasy.mf2.item.tool.crafting.ItemHammer;
import minefantasy.mf2.item.tool.crafting.ItemKnifeMF;
import minefantasy.mf2.item.tool.crafting.ItemNeedle;
import minefantasy.mf2.item.tool.crafting.ItemSaw;
import minefantasy.mf2.item.tool.crafting.ItemTongs;
import minefantasy.mf2.item.weapon.ItemBattleaxeMF;
import minefantasy.mf2.item.weapon.ItemDagger;
import minefantasy.mf2.item.weapon.ItemGreatswordMF;
import minefantasy.mf2.item.weapon.ItemHalbeardMF;
import minefantasy.mf2.item.weapon.ItemKatanaMF;
import minefantasy.mf2.item.weapon.ItemLance;
import minefantasy.mf2.item.weapon.ItemMaceMF;
import minefantasy.mf2.item.weapon.ItemSpearMF;
import minefantasy.mf2.item.weapon.ItemSwordMF;
import minefantasy.mf2.item.weapon.ItemWaraxeMF;
import minefantasy.mf2.item.weapon.ItemWarhammerMF;
import minefantasy.mf2.item.weapon.ItemWeaponMF;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;

public class CustomToolListMF
{
	//STANDARD
	public static ItemWeaponMF standard_sword, standard_waraxe, standard_mace, standard_dagger, standard_spear;
	public static ItemWeaponMF standard_greatsword, standard_battleaxe, standard_warhammer, standard_katana, standard_halbeard, standard_lance;
	public static ItemPickMF standard_pick;
	public static ItemAxeMF standard_axe;
	public static ItemSpadeMF standard_spade;
	public static ItemHoeMF standard_hoe;
	public static ItemHvyPick standard_hvypick;
	public static ItemHvyShovel standard_hvyshovel;
	public static ItemHandpick standard_handpick;
	public static ItemTrowMF  standard_trow;
	public static ItemScythe standard_scythe;
	
	public static ItemHammer standard_hammer, standard_hvyhammer;
	public static ItemTongs standard_tongs;
	public static ItemShearsMF standard_shears;
	public static ItemKnifeMF standard_knife;
	public static ItemNeedle standard_needle;
	public static ItemSaw standard_saw;
	public static ItemBasicCraftTool standard_spoon, standard_mallet;
	
	public static ItemBowMF standard_bow;
	public static ItemArrowMF standard_arrow, standard_bolt, standard_arrow_bodkin, standard_arrow_broad;
	
	//DRAGONFORGE
	public static ItemWeaponMF dragonforged_sword, dragonforged_waraxe, dragonforged_mace, dragonforged_dagger, dragonforged_spear;
	public static ItemWeaponMF dragonforged_greatsword, dragonforged_battleaxe, dragonforged_warhammer, dragonforged_katana, dragonforged_halbeard, dragonforged_lance;
	
	
	//DWARVEN\\
	/*
	public static Item dwarven_pick;
	public static Item dwarven_axe;
	public static Item dwarven_spade;
	public static Item dwarven_handpick;
	public static Item dwarven_hvypick;
	public static Item dwarven_hvyshovel;
	public static Item dwarven_trow;
	public static Item dwarven_hoe;
	public static Item dwarven_scythe;
	
	public static ItemWeaponMF dwarven_waraxe;
	public static ItemWeaponMF dwarven_mace;
	public static ItemWeaponMF dwarven_sword;
	public static ItemWeaponMF dwarven_dagger;
	public static ItemWeaponMF dwarven_battleaxe;
	public static ItemWeaponMF dwarven_warhammer;
	public static ItemWeaponMF dwarven_greatsword;
	public static ItemWeaponMF dwarven_katana;
	public static ItemWeaponMF dwarven_spear;
	public static ItemWeaponMF dwarven_halbeard;
	
	//GNOMISH\\
	
	public static Item gnomish_hammer;
	public static Item gnomish_shears;
	public static Item gnomish_hvyhammer;
	public static Item gnomish_knife;
	public static Item gnomish_needle;
	public static Item gnomish_saw;
	public static Item gnomish_tongs;
	*/
	
	public static void load() 
	{
		String design = "standard";
		CreativeTabs tab = CreativeTabMF.tabWeapon;
		//Standard Weapons
		standard_dagger = new ItemDagger(design+"_dagger", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
		standard_sword = new ItemSwordMF(design+"_sword", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
		standard_waraxe = new ItemWaraxeMF(design+"_waraxe", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
		standard_mace = new ItemMaceMF(design+"_mace", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
		standard_spear = new ItemSpearMF(design+"_spear", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
		standard_katana = new ItemKatanaMF(design+"_katana", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
		standard_greatsword = new ItemGreatswordMF(design+"_greatsword", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
		standard_battleaxe = new ItemBattleaxeMF(design+"_battleaxe", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
		standard_warhammer = new ItemWarhammerMF(design+"_warhammer", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
		standard_halbeard = new ItemHalbeardMF(design+"_halbeard", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
		standard_lance = new ItemLance(design+"_lance", ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab);
		
		tab = CreativeTabMF.tabArcher;
		standard_bow = (ItemBowMF) new ItemBowMF(design+"_bow", EnumBowType.COMPOSITE).setCustom(design).setCreativeTab(tab);
		standard_bolt = (ItemArrowMF) new ItemArrowMF(design, ArrowType.BOLT).setCustom(design).setAmmoType("bolt").setCreativeTab(tab);
		standard_arrow = (ItemArrowMF) new ItemArrowMF(design, ArrowType.NORMAL).setCustom(design).setCreativeTab(tab);
		standard_arrow_bodkin = (ItemArrowMF) new ItemArrowMF(design, ArrowType.BODKIN).setCustom(design).setCreativeTab(tab);
		standard_arrow_broad = (ItemArrowMF) new ItemArrowMF(design, ArrowType.BROADHEAD).setCustom(design).setCreativeTab(tab);
		
		 tab = CreativeTabMF.tabTool;
		//Standard Tools
		standard_pick = (ItemPickMF) new ItemPickMF(design+"_pick", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
		standard_axe = (ItemAxeMF) new ItemAxeMF(design+"_axe", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
		standard_spade = (ItemSpadeMF) new ItemSpadeMF(design+"_spade", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
		standard_hoe = (ItemHoeMF) new ItemHoeMF(design+"_hoe", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
		
		tab = CreativeTabMF.tabToolAdvanced;
		standard_handpick = (ItemHandpick) new ItemHandpick(design+"_handpick", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
		standard_hvypick = (ItemHvyPick) new ItemHvyPick(design+"_hvypick", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
		standard_trow = (ItemTrowMF) new ItemTrowMF(design+"_trow", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
		standard_hvyshovel = (ItemHvyShovel) new ItemHvyShovel(design+"_hvyshovel", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
		standard_scythe = (ItemScythe) new ItemScythe(design+"_scythe", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
		//Standard Crafters
		 tab = CreativeTabMF.tabCraftTool;
		standard_hammer = (ItemHammer) new ItemHammer(design+"_hammer", ToolMaterial.IRON, false, 0, 0).setCustom(design).setCreativeTab(tab);
		standard_hvyhammer = (ItemHammer) new ItemHammer(design+"_hvyhammer", ToolMaterial.IRON, true, 0, 0).setCustom(design).setCreativeTab(tab);
		standard_shears = (ItemShearsMF) new ItemShearsMF(design+"_shears", ToolMaterial.IRON, 0, 0).setCustom(design).setCreativeTab(tab);
		standard_knife = (ItemKnifeMF) new ItemKnifeMF(design+"_knife", ToolMaterial.IRON, 0, 1F, 0).setCustom(design).setCreativeTab(tab);
		standard_needle = (ItemNeedle) new ItemNeedle(design+"_needle", ToolMaterial.IRON, 0, 0).setCustom(design).setCreativeTab(tab);
		standard_saw = (ItemSaw) new ItemSaw(design+"_saw", ToolMaterial.IRON, 0, 0).setCustom(design).setCreativeTab(tab);
		standard_tongs = (ItemTongs) new ItemTongs(design+"_tongs", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
		
		standard_spoon = (ItemBasicCraftTool) new ItemBasicCraftTool(design+"_spoon", "spoon", 0, 64).setCustom(design).setCreativeTab(tab);
		standard_mallet = (ItemBasicCraftTool) new ItemBasicCraftTool(design+"_mallet", "mallet", 0, 64).setCustom(design).setCreativeTab(tab);
		
		design = "dragonforged";//
		tab = CreativeTabMF.tabDragonforged;
		ToolMaterial mat = BaseMaterialMF.dragonforge.getToolConversion();
		
		dragonforged_dagger = new ItemDagger(design+"_dagger", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		dragonforged_sword = new ItemSwordMF(design+"_sword", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		dragonforged_waraxe = new ItemWaraxeMF(design+"_waraxe", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		dragonforged_mace = new ItemMaceMF(design+"_mace", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		dragonforged_spear = new ItemSpearMF(design+"_spear", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		dragonforged_katana = new ItemKatanaMF(design+"_katana", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		dragonforged_greatsword = new ItemGreatswordMF(design+"_greatsword", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		dragonforged_battleaxe = new ItemBattleaxeMF(design+"_battleaxe", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		dragonforged_warhammer = new ItemWarhammerMF(design+"_warhammer", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		dragonforged_halbeard = new ItemHalbeardMF(design+"_halbeard", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		dragonforged_lance = new ItemLance(design+"_lance", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1);
		
		/*
		design = "dwarven";//Faster digs, Stronger/heavier weapons
		tab = CreativeTabMF.tabDwarven;
		//Dwarf Tools
		dwarven_pick = new ItemPickMF(design+"_pick", ToolMaterial.IRON, 0).setCustom(design).setEfficiencyMod(1.5F).setCreativeTab(tab);
		dwarven_axe = new ItemAxeMF(design+"_axe", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
		dwarven_spade = new ItemSpadeMF(design+"_spade", ToolMaterial.IRON, 0).setCustom(design).setEfficiencyMod(1.5F).setCreativeTab(tab);
		dwarven_hoe = new ItemHoeMF(design+"_hoe", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
		
		//Advanced Dwarf Tools
		dwarven_handpick = new ItemHandpick(design+"_handpick", ToolMaterial.IRON, 0).setCustom(design).setEfficiencyMod(1.5F).setCreativeTab(tab);
		dwarven_hvypick = new ItemHvyPick(design+"_hvypick", ToolMaterial.IRON, 0).setCustom(design).setEfficiencyMod(1.5F).setCreativeTab(tab);
		dwarven_trow = new ItemTrowMF(design+"_trow", ToolMaterial.IRON, 0).setCustom(design).setEfficiencyMod(1.5F).setCreativeTab(tab);
		dwarven_hvyshovel = new ItemHvyShovel(design+"_hvyshovel", ToolMaterial.IRON, 0).setCustom(design).setEfficiencyMod(1.5F).setCreativeTab(tab);
		dwarven_scythe = new ItemScythe(design+"_scythe", ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab);
		
		//Dwarf Weapons (Heavier but more brutal)
		dwarven_dagger = new ItemDagger(design+"_dagger", ToolMaterial.IRON, 0, 2F).setCustom(design).modifyBaseDamage(1F).setTab(tab);
		dwarven_sword = new ItemSwordMF(design+"_sword", ToolMaterial.IRON, 0, 2F).setCustom(design).modifyBaseDamage(1F).setTab(tab);
		dwarven_waraxe = new ItemWaraxeMF(design+"_waraxe", ToolMaterial.IRON, 0, 2F).setCustom(design).modifyBaseDamage(2F).setTab(tab);
		dwarven_mace = new ItemMaceMF(design+"_mace", ToolMaterial.IRON, 0, 2F).setCustom(design).modifyBaseDamage(1F).setTab(tab);
		//Heavy Dwarf Weapons (Heavier but more brutal)
		dwarven_greatsword = new ItemGreatswordMF(design+"_greatsword", ToolMaterial.IRON, 0, 2F).setCustom(design).modifyBaseDamage(1F).setTab(tab);
		dwarven_battleaxe = new ItemBattleaxeMF(design+"_battleaxe", ToolMaterial.IRON, 0,  2F).setCustom(design).modifyBaseDamage(2F).setTab(tab);
		dwarven_warhammer = new ItemWarhammerMF(design+"_warhammer", ToolMaterial.IRON, 0, 2F).setCustom(design).modifyBaseDamage(1F).setTab(tab);
		dwarven_katana = new ItemKatanaMF(design+"_katana", ToolMaterial.IRON, 0, 2F).setCustom(design).modifyBaseDamage(1F).setTab(tab);
		dwarven_spear = new ItemSpearMF(design+"_spear", ToolMaterial.IRON, 0, 2F).setCustom(design).modifyBaseDamage(1F).setTab(tab);
		dwarven_halbeard = new ItemHalbeardMF(design+"_halbeard", ToolMaterial.IRON, 0, 2F).setCustom(design).modifyBaseDamage(2F).setTab(tab);
		
		
		design = "gnomish";
		tab = CreativeTabMF.tabGnomish;
		//Gnomish Tools
		gnomish_hammer = new ItemHammer(design+"_hammer", ToolMaterial.IRON, false, 0, 0).setCustom(design).setEfficiencyMod(1.5F).setCreativeTab(tab);
		gnomish_hvyhammer = new ItemHammer(design+"_hvyhammer", ToolMaterial.IRON, false, 0, 0).setCustom(design).setEfficiencyMod(1.5F).setCreativeTab(tab);
		gnomish_shears = new ItemShearsMF(design+"_shears", ToolMaterial.IRON, 0, 0).setCustom(design).setCreativeTab(tab);
		gnomish_knife = new ItemKnifeMF(design+"_knife", ToolMaterial.IRON, 0, 1F, 0).setCustom(design).setEfficiencyMod(1.5F).setCreativeTab(tab);
		gnomish_needle = new ItemNeedle(design+"_needle", ToolMaterial.IRON, 0, 0).setCustom(design).setEfficiencyMod(1.5F).setCreativeTab(tab);
		gnomish_saw = new ItemSaw(design+"_saw", ToolMaterial.IRON, 0, 0).setCustom(design).setEfficiencyMod(1.5F).setCreativeTab(tab);
		gnomish_tongs = new ItemTongs(design+"_tongs", ToolMaterial.IRON, 0).setCustom(design).setEfficiencyMod(1.5F).setCreativeTab(tab);
		*/
		
		SpecialForging.addDragonforgeCraft(standard_dagger, dragonforged_dagger);
		SpecialForging.addDragonforgeCraft(standard_sword, dragonforged_sword);
		SpecialForging.addDragonforgeCraft(standard_mace, dragonforged_mace);
		SpecialForging.addDragonforgeCraft(standard_waraxe, dragonforged_waraxe);
		SpecialForging.addDragonforgeCraft(standard_spear, dragonforged_spear);
		SpecialForging.addDragonforgeCraft(standard_katana, dragonforged_katana);
		SpecialForging.addDragonforgeCraft(standard_greatsword, dragonforged_greatsword);
		SpecialForging.addDragonforgeCraft(standard_warhammer, dragonforged_warhammer);
		SpecialForging.addDragonforgeCraft(standard_battleaxe, dragonforged_battleaxe);
		SpecialForging.addDragonforgeCraft(standard_halbeard, dragonforged_halbeard);
		SpecialForging.addDragonforgeCraft(standard_lance, dragonforged_lance);
	}
}
