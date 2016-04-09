package minefantasy.mf2.item.list;

import minefantasy.mf2.api.crafting.exotic.SpecialForging;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.config.ConfigHardcore;
import minefantasy.mf2.item.ItemBandage;
import minefantasy.mf2.item.ItemComponentMF;
import minefantasy.mf2.item.ItemResearchBook;
import minefantasy.mf2.item.ItemResearchScroll;
import minefantasy.mf2.item.ItemSkillBook;
import minefantasy.mf2.item.ItemSpecialKnowledge;
import minefantasy.mf2.item.archery.ArrowType;
import minefantasy.mf2.item.archery.EnumBowType;
import minefantasy.mf2.item.archery.ItemArrowMF;
import minefantasy.mf2.item.archery.ItemBowMF;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.gadget.ItemBomb;
import minefantasy.mf2.item.gadget.ItemClimbingPick;
import minefantasy.mf2.item.gadget.ItemCrossbow;
import minefantasy.mf2.item.gadget.ItemCrudeBomb;
import minefantasy.mf2.item.gadget.ItemExplodingArrow;
import minefantasy.mf2.item.gadget.ItemExplodingBolt;
import minefantasy.mf2.item.gadget.ItemLootSack;
import minefantasy.mf2.item.gadget.ItemMine;
import minefantasy.mf2.item.gadget.ItemParachute;
import minefantasy.mf2.item.gadget.ItemSpyglass;
import minefantasy.mf2.item.gadget.ItemSyringe;
import minefantasy.mf2.item.tool.ItemAxeMF;
import minefantasy.mf2.item.tool.ItemHoeMF;
import minefantasy.mf2.item.tool.ItemLighterMF;
import minefantasy.mf2.item.tool.ItemPickMF;
import minefantasy.mf2.item.tool.ItemShearsMF;
import minefantasy.mf2.item.tool.ItemSpadeMF;
import minefantasy.mf2.item.tool.advanced.ItemHandpick;
import minefantasy.mf2.item.tool.advanced.ItemHvyPick;
import minefantasy.mf2.item.tool.advanced.ItemHvyShovel;
import minefantasy.mf2.item.tool.advanced.ItemMattock;
import minefantasy.mf2.item.tool.advanced.ItemScythe;
import minefantasy.mf2.item.tool.advanced.ItemTrowMF;
import minefantasy.mf2.item.tool.crafting.ItemBasicCraftTool;
import minefantasy.mf2.item.tool.crafting.ItemEAnvilTools;
import minefantasy.mf2.item.tool.crafting.ItemEngineerTool;
import minefantasy.mf2.item.tool.crafting.ItemHammer;
import minefantasy.mf2.item.tool.crafting.ItemKnifeMF;
import minefantasy.mf2.item.tool.crafting.ItemNeedle;
import minefantasy.mf2.item.tool.crafting.ItemPaintBrush;
import minefantasy.mf2.item.tool.crafting.ItemSaw;
import minefantasy.mf2.item.tool.crafting.ItemTongs;
import minefantasy.mf2.item.weapon.ItemBattleaxeMF;
import minefantasy.mf2.item.weapon.ItemDagger;
import minefantasy.mf2.item.weapon.ItemGreatswordMF;
import minefantasy.mf2.item.weapon.ItemHalbeardMF;
import minefantasy.mf2.item.weapon.ItemHeavyWeaponMF;
import minefantasy.mf2.item.weapon.ItemKatanaMF;
import minefantasy.mf2.item.weapon.ItemLance;
import minefantasy.mf2.item.weapon.ItemMaceMF;
import minefantasy.mf2.item.weapon.ItemSpearMF;
import minefantasy.mf2.item.weapon.ItemSwordMF;
import minefantasy.mf2.item.weapon.ItemWaraxeMF;
import minefantasy.mf2.item.weapon.ItemWarhammerMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.EnumHelper;

/**
 * @author Anonymous Productions
 */
public class ToolListMF 
{
	public static EnumRarity poor = EnumHelper.addRarity("Poor", EnumChatFormatting.DARK_GRAY, "poor");
	public static EnumRarity unique = EnumHelper.addRarity("Unique", EnumChatFormatting.DARK_GREEN, "unique");
	public static EnumRarity rare = EnumHelper.addRarity("Rare", EnumChatFormatting.DARK_BLUE, "rare");
	
	public static EnumRarity[] rarity = new EnumRarity[]{ToolListMF.poor, EnumRarity.common, EnumRarity.uncommon, EnumRarity.rare, EnumRarity.epic};

	/*
	public static final String[] mats = new String[]
	{
		"copper",
		"bronze",
		"iron",
		"steel",
		"encrusted",
		"blacksteel",
		"dragonforge",
		"redsteel",
		"bluesteel",
		"adamantium",
		"mithril",
		"ignotumite",
		"mithium",
		"ender",
	};
			
	public static final String[] weaponMats = new String[]
	{
		"copper",
		"bronze",
		"iron",
		"ornate",
		"steel",
		"encrusted",
		"blacksteel",
		"dragonforge",
		"redsteel",
		"bluesteel",
		"adamantium",
		"mithril",
		"ignotumite",
		"mithium",
		"ender",
	};
			
			
			
	private static ItemPickMF[] picks = new ItemPickMF[mats.length];
	private static ItemAxeMF[] axes = new ItemAxeMF[mats.length];
	private static ItemSpadeMF[] spades = new ItemSpadeMF[mats.length];
	private static ItemShearsMF[] shears = new ItemShearsMF[mats.length];
	private static ItemHoeMF[] hoes = new ItemHoeMF[mats.length];
	private static ItemKnifeMF[] knives = new ItemKnifeMF[mats.length];
	private static ItemHammer[] hammers = new ItemHammer[mats.length];
	private static ItemTongs[] tongs = new ItemTongs[mats.length];
	private static ItemNeedle[] needles = new ItemNeedle[mats.length];
	private static ItemSaw[] saws = new ItemSaw[mats.length];
	private static ItemSwordMF[] swords = new ItemSwordMF[weaponMats.length];
	private static ItemWaraxeMF[] waraxes = new ItemWaraxeMF[weaponMats.length];
	private static ItemMaceMF[] maces = new ItemMaceMF[weaponMats.length];
	private static ItemDagger[] daggers = new ItemDagger[weaponMats.length];
	private static ItemArrowMF[] bolts = new ItemArrowMF[weaponMats.length];
	private static ItemArrowMF[] arrows = new ItemArrowMF[weaponMats.length];
	private static ItemArrowMF[] bodkinArrows = new ItemArrowMF[weaponMats.length-1];
	private static ItemArrowMF[] broadArrows = new ItemArrowMF[weaponMats.length-1];
	private static ItemBowMF[] bows = new ItemBowMF[weaponMats.length];
	private static ItemSpearMF[] spears = new ItemSpearMF[weaponMats.length];
	//public static Item dwarven_waraxe = new ItemWaraxeMF("dwarven_waraxe", ToolMaterial.IRON, 0, 1.0F).setCustom();
	
	private static ItemHeavyWeaponMF[] battleaxes = new ItemBattleaxeMF[weaponMats.length-1];
	private static ItemHeavyWeaponMF[] warhammers = new ItemWarhammerMF[weaponMats.length-1];
	private static ItemHeavyWeaponMF[] greatswords = new ItemGreatswordMF[weaponMats.length-1];
	private static ItemHeavyWeaponMF[] katanas = new ItemKatanaMF[weaponMats.length-1];
	private static ItemHalbeardMF[] halbeards = new ItemHalbeardMF[weaponMats.length-1];
	private static ItemLance[] lances = new ItemLance[weaponMats.length-1];
	
	private static ItemHammer[] hvyHammers = new ItemHammer[mats.length-1];
	private static ItemHvyPick[] hvypicks = new ItemHvyPick[mats.length-1];
	private static ItemHandpick[] handpicks = new ItemHandpick[mats.length-1];
	private static ItemTrowMF[] trows = new ItemTrowMF[mats.length-1];
	private static ItemScythe[] scythes = new ItemScythe[mats.length-1];
	private static ItemHvyShovel[] hvyshovels = new ItemHvyShovel[mats.length-1];
	private static ItemMattock[] mattocks = new ItemMattock[mats.length-1];
	*/
	//public static ItemBasicCraftTool malletWood = new ItemBasicCraftTool("malletWood", "mallet", 0, 250);
	//public static ItemBasicCraftTool spoonWood = new ItemBasicCraftTool("spoonWood", "spoon", 0, 250);
	
	public static Item swordTraining = new ItemSwordMF("training_sword", ToolMaterial.WOOD, -1, 0.8F);
	public static Item waraxeTraining = new ItemWaraxeMF("training_waraxe", ToolMaterial.WOOD, -1, 0.8F);
	public static Item maceTraining = new ItemMaceMF("training_mace", ToolMaterial.WOOD, -1, 0.8F);
	public static Item spearTraining = new ItemSpearMF("training_spear", ToolMaterial.WOOD, -1, 0.8F);
	public static Item knifeStone = new ItemKnifeMF("stone_knife", BaseMaterialMF.getMaterial("stone").getToolConversion(), -1, 3.5F, 0);
	public static Item hammerStone = new ItemHammer("stone_hammer", BaseMaterialMF.getMaterial("stone").getToolConversion(),false, -1, 0);
	public static Item tongsStone = new ItemTongs("stone_tongs", BaseMaterialMF.getMaterial("stone").getToolConversion(), -1);
	public static Item needleBone = new ItemNeedle("bone_needle", BaseMaterialMF.getMaterial("stone").getToolConversion(), -1, 0);
	
	public static Item pickStone = new ItemPickMF("stone_pick", BaseMaterialMF.getMaterial("stone").getToolConversion(), -1);
	public static Item axeStone = new ItemAxeMF("stone_axe", BaseMaterialMF.getMaterial("stone").getToolConversion(), -1);
	public static Item spadeStone = new ItemSpadeMF("stone_spade", BaseMaterialMF.getMaterial("stone").getToolConversion(), -1);
	public static Item hoeStone = new ItemHoeMF("stone_hoe", BaseMaterialMF.getMaterial("stone").getToolConversion(), -1);
	
	public static Item swordStone = new ItemSwordMF("stone_sword", BaseMaterialMF.getMaterial("stone").getToolConversion(), -1, 3.5F);
	public static Item maceStone = new ItemMaceMF("stone_mace", BaseMaterialMF.getMaterial("stone").getToolConversion(), -1, 3.5F);
	public static Item waraxeStone = new ItemWaraxeMF("stone_waraxe", BaseMaterialMF.getMaterial("stone").getToolConversion(), -1, 3.5F);
	public static Item spearStone = new ItemSpearMF("stone_spear", BaseMaterialMF.getMaterial("stone").getToolConversion(), -1, 3.5F);
	
	public static Item bandage_crude = new ItemBandage("bandage_crude", 5F);
	public static Item bandage_wool = new ItemBandage("bandage_wool", 8F);
	public static Item bandage_tough = new ItemBandage("bandage_tough", 12F);
	
	public static ItemCrudeBomb bomb_crude = new ItemCrudeBomb("bomb_crude");
	public static ItemBomb bomb_custom = new ItemBomb("bomb_basic");
	public static ItemMine mine_custom = new ItemMine("mine_basic");
	
	public static ItemResearchBook researchBook = new ItemResearchBook();
	public static Item research_scroll = new ItemResearchScroll("research_scroll", false);
	public static Item research_scroll_complete = new ItemResearchScroll("research_scroll_complete", true);
	
	public static Item dryrocks = new ItemLighterMF("dryrocks", 0.1F, 16);
	public static Item tinderbox = new ItemLighterMF("tinderbox", 0.5F, 100);
	
	public static Item skillbook_artisanry = new ItemSkillBook("skillbook_artisanry", SkillList.artisanry);
	public static Item skillbook_construction = new ItemSkillBook("skillbook_construction", SkillList.construction);
	public static Item skillbook_provisioning = new ItemSkillBook("skillbook_provisioning", SkillList.provisioning);
	public static Item skillbook_engineering = new ItemSkillBook("skillbook_engineering", SkillList.engineering);
	public static Item skillbook_combat = new ItemSkillBook("skillbook_combat", SkillList.combat);
	
	public static Item skillbook_artisanry2 = new ItemSkillBook("skillbook_artisanry2", SkillList.artisanry).setMax();
	public static Item skillbook_construction2 = new ItemSkillBook("skillbook_construction2", SkillList.construction).setMax();
	public static Item skillbook_provisioning2 = new ItemSkillBook("skillbook_provisioning2", SkillList.provisioning).setMax();
	public static Item skillbook_engineering2 = new ItemSkillBook("skillbook_engineering2", SkillList.engineering).setMax();
	public static Item skillbook_combat2 = new ItemSkillBook("skillbook_combat2", SkillList.combat).setMax();
	
	public static Item skillbook_dwarvern = new ItemSpecialKnowledge("dwarvern");
	public static Item skillbook_gnomish = new ItemSpecialKnowledge("gnomish");
	
	public static Item spanner;
	public static Item engin_anvil_tools = new ItemEAnvilTools("engin_anvil_tools", 64);
	public static Item spanner_blk;
	
	public static Item exploding_arrow = new ItemExplodingArrow();
	public static Item spyglass = new ItemSpyglass();
	public static Item climbing_pick_basic;
	public static Item parachute = new ItemParachute();
	
	public static Item syringe = new ItemSyringe();
	public static Item syringe_empty = new ItemComponentMF("syringe_empty").setTextureName("minefantasy2:Other/syringe").setCreativeTab(CreativeTabMF.tabGadget);
	
	public static Item loot_sack = new ItemLootSack("loot_sack", 8, 0);
	public static Item loot_sack_uc = new ItemLootSack("loot_sack_uc", 8, 1);
	public static Item loot_sack_rare = new ItemLootSack("loot_sack_rare", 12, 2);
	public static ItemCrossbow crossbow_custom = new ItemCrossbow();
	public static Item exploding_bolt = new ItemExplodingBolt();
	public static Item paint_brush = new ItemPaintBrush("paint_brush", 256);
	
	public static void load() 
	{
		if(ConfigHardcore.HCCWeakItems)
		{
			weakenItems();
		}
		spanner = new ItemEngineerTool("spanner", BaseMaterialMF.getMaterial("steel").getToolConversion(), 0, "spanner", 1);
		spanner_blk = new ItemEngineerTool("spanner_blk", BaseMaterialMF.getMaterial("blacksteel").getToolConversion(), 1, "spanner", 3);
		climbing_pick_basic = new ItemClimbingPick("climbing_pick_basic", BaseMaterialMF.getMaterial("iron").getToolConversion(), 0);
		BlockListMF.load();
		ArmourListMF.load();
		FoodListMF.load();
		CustomToolListMF.load();
		CustomArmourListMF.load();
		/*
		for(int a = 0; a < mats.length; a ++)
		{
			BaseMaterialMF baseMat = BaseMaterialMF.getMaterial(mats[a]);
			
			ToolMaterial mat = baseMat.getToolConversion();
			String matName = baseMat.name.toLowerCase();
			int rarity = baseMat.rarity;
			float weight = baseMat.weight;
			int tier = baseMat.tier;
			
			picks[a] = new ItemPickMF(matName+"_pick", mat, rarity);
			axes[a] = new ItemAxeMF(matName+"_axe", mat, rarity);
			spades[a] = new ItemSpadeMF(matName+"_spade", mat, rarity);
			hoes[a] = new ItemHoeMF(matName+"_hoe", mat, rarity);
			shears[a] = new ItemShearsMF(matName+"_shears", mat, rarity, tier);
			knives[a] = new ItemKnifeMF(matName+"_knife", mat, rarity, weight, tier);
			hammers[a] = new ItemHammer(matName+"_hammer", mat, false, rarity, tier);
			tongs[a] = new ItemTongs(matName+"_tongs", mat, rarity);
			needles[a] = new ItemNeedle(matName+"_needle", mat, rarity, tier);
			saws[a] = new ItemSaw(matName+"_saw", mat, rarity, tier);
			
			if(a > 0)
			{
				hvyHammers[a-1] = new ItemHammer(matName+"_hvyHammer", mat, true, rarity, tier);
				hvypicks[a-1] = new ItemHvyPick(matName+"_hvypick", mat, rarity);
				handpicks[a-1] = new ItemHandpick(matName+"_handpick", mat, rarity);
				trows[a-1] = new ItemTrowMF(matName+"_trow", mat, rarity);
				scythes[a-1] = new ItemScythe(matName+"_scythe", mat, rarity);
				hvyshovels[a-1] = new ItemHvyShovel(matName+"_hvyShovel", mat, rarity);
				mattocks[a-1] = new ItemMattock(matName+"_mattock", mat, rarity);
			}
			SpecialForging.addDragonforgeCraft(picks[5], picks[6]);
			SpecialForging.addDragonforgeCraft(axes[5], axes[6]);
			SpecialForging.addDragonforgeCraft(spades[5], spades[6]);
			SpecialForging.addDragonforgeCraft(hoes[5], hoes[6]);
			
			SpecialForging.addDragonforgeCraft(shears[5], shears[6]);
			SpecialForging.addDragonforgeCraft(knives[5], knives[6]);
			SpecialForging.addDragonforgeCraft(hammers[5], hammers[6]);
			SpecialForging.addDragonforgeCraft(tongs[5], tongs[6]);
			SpecialForging.addDragonforgeCraft(needles[5], needles[6]);
			SpecialForging.addDragonforgeCraft(saws[5], saws[6]);
			
			SpecialForging.addDragonforgeCraft(hvyHammers[4], hvyHammers[5]);
			SpecialForging.addDragonforgeCraft(hvypicks[4], hvypicks[5]);
			SpecialForging.addDragonforgeCraft(handpicks[4], handpicks[5]);
			SpecialForging.addDragonforgeCraft(trows[4], trows[5]);
			SpecialForging.addDragonforgeCraft(scythes[4], scythes[5]);
			SpecialForging.addDragonforgeCraft(hvyshovels[4], hvyshovels[5]);
			SpecialForging.addDragonforgeCraft(mattocks[4], mattocks[5]);
		}
		
		
		for(int a = 0; a < weaponMats.length; a ++)
		{
			BaseMaterialMF baseMat = BaseMaterialMF.getMaterial(weaponMats[a]);
			ToolMaterial mat = baseMat.getToolConversion();
			String matName = baseMat.name.toLowerCase();
			int rarity = baseMat.rarity;
			float weight = baseMat.weight;
			int tier = baseMat.tier;
			
			swords[a] = new ItemSwordMF(matName+"_sword", mat, rarity, weight);
			waraxes[a] = new ItemWaraxeMF(matName+"_waraxe", mat, rarity, weight);
			maces[a] = new ItemMaceMF(matName+"_mace", mat, rarity, weight);
			daggers[a] = new ItemDagger(matName+"_dagger", mat, rarity, weight);
			spears[a] = new ItemSpearMF(matName+"_spear", mat, rarity, weight);
			
			bolts[a] = new ItemArrowMF(matName, rarity, mat, ArrowType.BOLT).setAmmoType("bolt");
			arrows[a] = new ItemArrowMF(matName, rarity, mat);
			bows[a] = new ItemBowMF(matName+"_bow", mat, EnumBowType.RECURVE, rarity);
			
			if(a > 0)
			{
				warhammers[a-1] = new ItemWarhammerMF(matName+"_warhammer", mat, rarity, weight);
				battleaxes[a-1] = new ItemBattleaxeMF(matName+"_battleaxe", mat, rarity, weight);
				greatswords[a-1] = new ItemGreatswordMF(matName+"_greatsword", mat, rarity, weight);
				katanas[a-1] = new ItemKatanaMF(matName+"_katana", mat, rarity, weight);
				halbeards[a-1] = new ItemHalbeardMF(matName+"_halbeard", mat, rarity, weight);
				lances[a-1] = new ItemLance(matName+"_lance", mat, rarity, weight);
				
				bodkinArrows[a-1] = new ItemArrowMF(matName, rarity, mat, ArrowType.BODKIN);
				broadArrows[a-1] = new ItemArrowMF(matName, rarity, mat, ArrowType.BROADHEAD);
			}
			
			SpecialForging.addDragonforgeCraft(swords[6], swords[7]);
			SpecialForging.addDragonforgeCraft(waraxes[6], waraxes[7]);
			SpecialForging.addDragonforgeCraft(maces[6], maces[7]);
			SpecialForging.addDragonforgeCraft(spears[6], spears[7]);
			SpecialForging.addDragonforgeCraft(bows[6], bows[7]);
			SpecialForging.addDragonforgeCraft(arrows[6], arrows[7]);
			SpecialForging.addDragonforgeCraft(ComponentListMF.arrowheads[6], ComponentListMF.arrowheads[7]);
			SpecialForging.addDragonforgeCraft(bolts[6], bolts[7]);
			
			SpecialForging.addDragonforgeCraft(greatswords[5], greatswords[6]);
			SpecialForging.addDragonforgeCraft(battleaxes[5], battleaxes[6]);
			SpecialForging.addDragonforgeCraft(warhammers[5], warhammers[6]);
			SpecialForging.addDragonforgeCraft(halbeards[5], halbeards[6]);
			SpecialForging.addDragonforgeCraft(lances[5], lances[6]);
			SpecialForging.addDragonforgeCraft(ComponentListMF.bodkinheads[5], ComponentListMF.bodkinheads[6]);
			SpecialForging.addDragonforgeCraft(ComponentListMF.broadheads[5], ComponentListMF.broadheads[6]);
			SpecialForging.addDragonforgeCraft(BlockListMF.bars[3], BlockListMF.bars[4]);
		}
		*/
		
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(skillbook_artisanry), 1, 5, 50));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(skillbook_construction), 1, 1, 10));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ArmourListMF.leatherapron), 1, 1, 10));
		
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ComponentListMF.talisman_lesser), 1, 1, 2));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(new ItemStack(ComponentListMF.talisman_lesser), 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ComponentListMF.talisman_lesser), 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(ComponentListMF.talisman_lesser), 1, 1, 3));
		
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(loot_sack), 1, 4, 10));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(loot_sack_uc), 1, 2, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(loot_sack_rare), 1, 1, 1));
		
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(loot_sack), 1, 4, 10));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(loot_sack_uc), 1, 2, 5));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(loot_sack_rare), 1, 1, 1));
		
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(loot_sack), 1, 4, 10));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(loot_sack_uc), 1, 2, 5));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(loot_sack_rare), 1, 1, 1));
		
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(loot_sack), 1, 6, 20));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(loot_sack_uc), 1, 1, 2));
		
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(loot_sack), 1, 4, 5));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, new WeightedRandomChestContent(new ItemStack(loot_sack_uc), 1, 2, 3));
		
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(new ItemStack(loot_sack), 1, 3, 20));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(new ItemStack(loot_sack_uc), 1, 2, 10));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, new WeightedRandomChestContent(new ItemStack(loot_sack_rare), 1, 1, 2));
		
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(new ItemStack(skillbook_artisanry), 2, 6, 10));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(new ItemStack(skillbook_construction), 2, 6, 10));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(new ItemStack(skillbook_provisioning), 2, 6, 10));
		ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(new ItemStack(skillbook_engineering), 1, 4, 8));
	}

	private static void weakenItems() 
	{
		weakenItem(Items.wooden_pickaxe, 5);
		weakenItem(Items.wooden_axe, 5);
		weakenItem(Items.wooden_shovel, 5);
		weakenItem(Items.wooden_sword, 5);
		weakenItem(Items.wooden_hoe, 5);
		weakenItem(Items.leather_helmet);
		weakenItem(Items.leather_chestplate);
		weakenItem(Items.leather_leggings);
		weakenItem(Items.leather_boots);
		
		weakenItem(Items.stone_pickaxe, 10);
		weakenItem(Items.stone_axe, 10);
		weakenItem(Items.stone_shovel, 10);
		weakenItem(Items.stone_sword, 10);
		weakenItem(Items.stone_hoe, 10);
		
		weakenItem(Items.iron_pickaxe, 25);
		weakenItem(Items.iron_axe, 25);
		weakenItem(Items.iron_shovel, 25);
		weakenItem(Items.iron_sword, 25);
		weakenItem(Items.iron_hoe, 25);
		weakenItem(Items.iron_helmet);
		weakenItem(Items.iron_chestplate);
		weakenItem(Items.iron_leggings);
		weakenItem(Items.iron_boots);
		
		weakenItem(Items.golden_pickaxe, 1);
		weakenItem(Items.golden_axe, 1);
		weakenItem(Items.golden_shovel, 1);
		weakenItem(Items.golden_sword, 1);
		weakenItem(Items.golden_hoe, 1);
		weakenItem(Items.golden_helmet);
		weakenItem(Items.golden_chestplate);
		weakenItem(Items.golden_leggings);
		weakenItem(Items.golden_boots);
		
		weakenItem(Items.diamond_pickaxe, 100);
		weakenItem(Items.diamond_axe, 100);
		weakenItem(Items.diamond_shovel, 100);
		weakenItem(Items.diamond_sword, 100);
		weakenItem(Items.diamond_hoe, 100);
		weakenItem(Items.diamond_helmet);
		weakenItem(Items.diamond_chestplate);
		weakenItem(Items.diamond_leggings);
		weakenItem(Items.diamond_boots);
	}
	private static void weakenItem(Item item)
	{
		weakenItem(item, (item.getMaxDamage()/10)+1);
	}
	private static void weakenItem(Item item, int hp)
	{
		if(item.isDamageable())
		{
			item.setMaxDamage(hp);
		}
	}
	
}
