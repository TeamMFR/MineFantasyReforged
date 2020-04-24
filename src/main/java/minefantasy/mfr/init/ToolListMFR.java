package minefantasy.mfr.init;

import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.item.ItemBandage;
import minefantasy.mfr.item.ItemComponentMFR;
import minefantasy.mfr.item.ItemResearchBook;
import minefantasy.mfr.item.ItemSkillBook;
import minefantasy.mfr.item.ItemWorldGenPlacer;
import minefantasy.mfr.item.gadget.ItemBomb;
import minefantasy.mfr.item.gadget.ItemClimbingPick;
import minefantasy.mfr.item.gadget.ItemCrossbow;
import minefantasy.mfr.item.gadget.ItemCrudeBomb;
import minefantasy.mfr.item.gadget.ItemExplodingArrow;
import minefantasy.mfr.item.gadget.ItemExplodingBolt;
import minefantasy.mfr.item.gadget.ItemLootSack;
import minefantasy.mfr.item.gadget.ItemMine;
import minefantasy.mfr.item.gadget.ItemParachute;
import minefantasy.mfr.item.gadget.ItemSpyglass;
import minefantasy.mfr.item.gadget.ItemSyringe;
import minefantasy.mfr.item.gadget.MobSpawnerMF;
import minefantasy.mfr.item.tool.ItemAxeMF;
import minefantasy.mfr.item.tool.ItemHoeMF;
import minefantasy.mfr.item.tool.ItemLighterMF;
import minefantasy.mfr.item.tool.ItemPickMF;
import minefantasy.mfr.item.tool.ItemSpadeMF;
import minefantasy.mfr.item.tool.crafting.ItemEAnvilTools;
import minefantasy.mfr.item.tool.crafting.ItemHammer;
import minefantasy.mfr.item.tool.crafting.ItemKnifeMFR;
import minefantasy.mfr.item.tool.crafting.ItemNeedle;
import minefantasy.mfr.item.tool.crafting.ItemPaintBrush;
import minefantasy.mfr.item.tool.crafting.ItemTongs;
import minefantasy.mfr.item.weapon.ItemMaceMFR;
import minefantasy.mfr.item.weapon.ItemSpearMFR;
import minefantasy.mfr.item.weapon.ItemSwordMF;
import minefantasy.mfr.item.weapon.ItemWaraxeMFR;
import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;

/**
 * @author Anonymous Productions
 */
public class ToolListMFR {
    public static EnumRarity poor = EnumHelper.addRarity("Poor", TextFormatting.DARK_GRAY, "poor");
    public static EnumRarity unique = EnumHelper.addRarity("Unique", TextFormatting.DARK_GREEN, "unique");
    public static EnumRarity rare = EnumHelper.addRarity("Rare", TextFormatting.DARK_BLUE, "rare");

    public static EnumRarity[] rarity = new EnumRarity[]{ToolListMFR.poor, EnumRarity.COMMON, EnumRarity.UNCOMMON,
            EnumRarity.RARE, EnumRarity.EPIC};

    public static Item swordTraining = new ItemSwordMF("training_sword", ToolMaterial.WOOD, -1, 0.8F);
    public static Item waraxeTraining = new ItemWaraxeMFR("training_waraxe", ToolMaterial.WOOD, -1, 0.8F);
    public static Item maceTraining = new ItemMaceMFR("training_mace", ToolMaterial.WOOD, -1, 0.8F);
    public static Item spearTraining = new ItemSpearMFR("training_spear", ToolMaterial.WOOD, -1, 0.8F);
    public static Item knifeStone = new ItemKnifeMFR("stone_knife", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 3.5F, 0);
    public static Item hammerStone = new ItemHammer("stone_hammer", BaseMaterialMFR.getMaterial("stone").getToolConversion(), false, -1, 0);
    public static Item tongsStone = new ItemTongs("stone_tongs", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1);
    public static Item needleBone = new ItemNeedle("bone_needle", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 0);

    public static Item pickStone = new ItemPickMF("stone_pick", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1);
    public static Item axeStone = new ItemAxeMF("stone_axe", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1);
    public static Item spadeStone = new ItemSpadeMF("stone_spade", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1);
    public static Item hoeStone = new ItemHoeMF("stone_hoe", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1);
    public static Item swordStone = new ItemSwordMF("stone_sword", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F);
    public static Item maceStone = new ItemMaceMFR("stone_mace", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F);
    public static Item waraxeStone = new ItemWaraxeMFR("stone_waraxe", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F);
    public static Item spearStone = new ItemSpearMFR("stone_spear", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F);

    public static Item bandage_crude = new ItemBandage("bandage_crude", 5F);
    public static Item bandage_wool = new ItemBandage("bandage_wool", 8F);
    public static Item bandage_tough = new ItemBandage("bandage_tough", 12F);

    public static ItemCrudeBomb bomb_crude = new ItemCrudeBomb("bomb_crude");
    public static ItemBomb bomb_custom = new ItemBomb("bomb_basic");
    public static ItemMine mine_custom = new ItemMine("mine_basic");

    public static ItemResearchBook researchBook = new ItemResearchBook();

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

    public static Item engin_anvil_tools = new ItemEAnvilTools("engin_anvil_tools", 64);

    public static Item exploding_arrow = new ItemExplodingArrow();
    public static Item spyglass = new ItemSpyglass();
    public static Item climbing_pick_basic;
    public static Item parachute = new ItemParachute();

    public static Item syringe = new ItemSyringe();
    public static Item syringe_empty = new ItemComponentMFR("syringe_empty").setCreativeTab(CreativeTabMFR.tabGadget);
    public static Item loot_sack = new ItemLootSack("loot_sack", 8, 0);
    public static Item loot_sack_uc = new ItemLootSack("loot_sack_uc", 8, 1);
    public static Item loot_sack_rare = new ItemLootSack("loot_sack_rare", 12, 2);
    public static ItemCrossbow crossbow_custom = new ItemCrossbow();
    public static Item exploding_bolt = new ItemExplodingBolt();
    public static Item paint_brush = new ItemPaintBrush("paint_brush", 256);

    public static Item debug_place = new ItemWorldGenPlacer();
    public static Item debug_mob = new MobSpawnerMF();

    public static void load() {
        if (ConfigHardcore.HCCWeakItems) {
            weakenItems();
        }
        climbing_pick_basic = new ItemClimbingPick("climbing_pick_basic", ToolMaterial.IRON, 0);
        BlockListMFR.load();
        ArmourListMFR.load();
        FoodListMFR.load();
        CustomToolListMFR.load();
        CustomArmourListMFR.load();
        LootRegistryMFR.load();
    }

    private static void weakenItems() {
        weakenItem(Items.WOODEN_PICKAXE, 5);
        weakenItem(Items.WOODEN_AXE, 5);
        weakenItem(Items.WOODEN_SHOVEL, 5);
        weakenItem(Items.WOODEN_SWORD, 5);
        weakenItem(Items.WOODEN_HOE, 5);

        weakenItem(Items.LEATHER_HELMET);
        weakenItem(Items.LEATHER_CHESTPLATE);
        weakenItem(Items.LEATHER_LEGGINGS);
        weakenItem(Items.LEATHER_BOOTS);

        weakenItem(Items.STONE_PICKAXE, 10);
        weakenItem(Items.STONE_AXE, 10);
        weakenItem(Items.STONE_SHOVEL, 10);
        weakenItem(Items.STONE_SWORD, 10);
        weakenItem(Items.STONE_HOE, 10);

        weakenItem(Items.IRON_PICKAXE, 25);
        weakenItem(Items.IRON_AXE, 25);
        weakenItem(Items.IRON_SHOVEL, 25);
        weakenItem(Items.IRON_SWORD, 25);
        weakenItem(Items.IRON_HOE, 25);
        weakenItem(Items.IRON_HELMET);
        weakenItem(Items.IRON_CHESTPLATE);
        weakenItem(Items.IRON_LEGGINGS);
        weakenItem(Items.IRON_BOOTS);

        weakenItem(Items.GOLDEN_PICKAXE, 1);
        weakenItem(Items.GOLDEN_AXE, 1);
        weakenItem(Items.GOLDEN_SHOVEL, 1);
        weakenItem(Items.GOLDEN_SWORD, 1);
        weakenItem(Items.GOLDEN_HOE, 1);
        weakenItem(Items.GOLDEN_HELMET);
        weakenItem(Items.GOLDEN_CHESTPLATE);
        weakenItem(Items.GOLDEN_LEGGINGS);
        weakenItem(Items.GOLDEN_BOOTS);

        weakenItem(Items.DIAMOND_PICKAXE, 100);
        weakenItem(Items.DIAMOND_AXE, 100);
        weakenItem(Items.DIAMOND_SHOVEL, 100);
        weakenItem(Items.DIAMOND_SWORD, 100);
        weakenItem(Items.DIAMOND_HOE, 100);
        weakenItem(Items.DIAMOND_HELMET);
        weakenItem(Items.DIAMOND_CHESTPLATE);
        weakenItem(Items.DIAMOND_LEGGINGS);
        weakenItem(Items.DIAMOND_BOOTS);
    }

    private static void weakenItem(Item item) {
        weakenItem(item, (item.getMaxDamage() / 10) + 1);
    }

    private static void weakenItem(Item item, int hp) {
        if (item.isDamageable()) {
            item.setMaxDamage(hp);
        }
    }

}
