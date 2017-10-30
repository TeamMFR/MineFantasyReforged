package minefantasy.mf2.item.list;

import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.block.decor.ItemBedMF;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.config.ConfigHardcore;
import minefantasy.mf2.item.*;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.gadget.*;
import minefantasy.mf2.item.tool.*;
import minefantasy.mf2.item.tool.crafting.*;
import minefantasy.mf2.item.weapon.ItemMaceMF;
import minefantasy.mf2.item.weapon.ItemSpearMF;
import minefantasy.mf2.item.weapon.ItemSwordMF;
import minefantasy.mf2.item.weapon.ItemWaraxeMF;
import minefantasy.mf2.material.BaseMaterialMF;
import minefantasy.mf2.mechanics.worldGen.structure.LootTypes;
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
public class ToolListMF {
    public static EnumRarity poor = EnumHelper.addRarity("Poor", EnumChatFormatting.DARK_GRAY, "poor");
    public static EnumRarity unique = EnumHelper.addRarity("Unique", EnumChatFormatting.DARK_GREEN, "unique");
    public static EnumRarity rare = EnumHelper.addRarity("Rare", EnumChatFormatting.DARK_BLUE, "rare");

    public static EnumRarity[] rarity = new EnumRarity[]{ToolListMF.poor, EnumRarity.common, EnumRarity.uncommon,
            EnumRarity.rare, EnumRarity.epic};

    public static Item swordTraining = new ItemSwordMF("training_sword", ToolMaterial.WOOD, -1, 0.8F);
    public static Item waraxeTraining = new ItemWaraxeMF("training_waraxe", ToolMaterial.WOOD, -1, 0.8F);
    public static Item maceTraining = new ItemMaceMF("training_mace", ToolMaterial.WOOD, -1, 0.8F);
    public static Item spearTraining = new ItemSpearMF("training_spear", ToolMaterial.WOOD, -1, 0.8F);
    public static Item knifeStone = new ItemKnifeMF("stone_knife",
            BaseMaterialMF.getMaterial("stone").getToolConversion(), -1, 3.5F, 0);
    public static Item hammerStone = new ItemHammer("stone_hammer",
            BaseMaterialMF.getMaterial("stone").getToolConversion(), false, -1, 0);
    public static Item tongsStone = new ItemTongs("stone_tongs",
            BaseMaterialMF.getMaterial("stone").getToolConversion(), -1);
    public static Item needleBone = new ItemNeedle("bone_needle",
            BaseMaterialMF.getMaterial("stone").getToolConversion(), -1, 0);

    public static Item pickStone = new ItemPickMF("stone_pick", BaseMaterialMF.getMaterial("stone").getToolConversion(),
            -1);
    public static Item axeStone = new ItemAxeMF("stone_axe", BaseMaterialMF.getMaterial("stone").getToolConversion(),
            -1);
    public static Item spadeStone = new ItemSpadeMF("stone_spade",
            BaseMaterialMF.getMaterial("stone").getToolConversion(), -1);
    public static Item hoeStone = new ItemHoeMF("stone_hoe", BaseMaterialMF.getMaterial("stone").getToolConversion(),
            -1);

    public static Item swordStone = new ItemSwordMF("stone_sword",
            BaseMaterialMF.getMaterial("stone").getToolConversion(), -1, 2.0F);
    public static Item maceStone = new ItemMaceMF("stone_mace", BaseMaterialMF.getMaterial("stone").getToolConversion(),
            -1, 2.0F);
    public static Item waraxeStone = new ItemWaraxeMF("stone_waraxe",
            BaseMaterialMF.getMaterial("stone").getToolConversion(), -1, 2.0F);
    public static Item spearStone = new ItemSpearMF("stone_spear",
            BaseMaterialMF.getMaterial("stone").getToolConversion(), -1, 2.0F);

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
    public static Item skillbook_construction2 = new ItemSkillBook("skillbook_construction2", SkillList.construction)
            .setMax();
    public static Item skillbook_provisioning2 = new ItemSkillBook("skillbook_provisioning2", SkillList.provisioning)
            .setMax();
    public static Item skillbook_engineering2 = new ItemSkillBook("skillbook_engineering2", SkillList.engineering)
            .setMax();
    public static Item skillbook_combat2 = new ItemSkillBook("skillbook_combat2", SkillList.combat).setMax();

    public static Item engin_anvil_tools = new ItemEAnvilTools("engin_anvil_tools", 64);

    public static Item exploding_arrow = new ItemExplodingArrow();
    public static Item spyglass = new ItemSpyglass();
    public static Item climbing_pick_basic;
    public static Item parachute = new ItemParachute();

    public static Item syringe = new ItemSyringe();
    public static Item syringe_empty = new ItemComponentMF("syringe_empty").setTextureName("minefantasy2:Other/syringe")
            .setCreativeTab(CreativeTabMF.tabGadget);

    public static Item loot_sack = new ItemLootSack("loot_sack", 8, 0);
    public static Item loot_sack_uc = new ItemLootSack("loot_sack_uc", 8, 1);
    public static Item loot_sack_rare = new ItemLootSack("loot_sack_rare", 12, 2);
    public static ItemCrossbow crossbow_custom = new ItemCrossbow();
    public static Item exploding_bolt = new ItemExplodingBolt();
    public static Item paint_brush = new ItemPaintBrush("paint_brush", 256);

    public static Item bedroll = new ItemBedMF("bedroll");

    public static Item debug_place = new ItemWorldGenPlacer();
    public static Item debug_mob = new MobSpawnerMF();

    public static void load() {
        if (ConfigHardcore.HCCWeakItems) {
            weakenItems();
        }
        climbing_pick_basic = new ItemClimbingPick("climbing_pick_basic", ToolMaterial.IRON, 0);
        BlockListMF.load();
        ArmourListMF.load();
        FoodListMF.load();
        CustomToolListMF.load();
        CustomArmourListMF.load();

        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH,
                new WeightedRandomChestContent(new ItemStack(skillbook_artisanry), 1, 5, 50));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH,
                new WeightedRandomChestContent(new ItemStack(skillbook_construction), 1, 1, 10));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH,
                new WeightedRandomChestContent(new ItemStack(ArmourListMF.leatherapron), 1, 1, 10));

        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST,
                new WeightedRandomChestContent(new ItemStack(ComponentListMF.talisman_lesser), 1, 1, 2));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY,
                new WeightedRandomChestContent(new ItemStack(ComponentListMF.talisman_lesser), 1, 1, 3));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST,
                new WeightedRandomChestContent(new ItemStack(ComponentListMF.talisman_lesser), 1, 1, 3));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST,
                new WeightedRandomChestContent(new ItemStack(ComponentListMF.talisman_lesser), 1, 1, 3));

        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST,
                new WeightedRandomChestContent(new ItemStack(loot_sack), 1, 4, 10));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST,
                new WeightedRandomChestContent(new ItemStack(loot_sack_uc), 1, 2, 5));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST,
                new WeightedRandomChestContent(new ItemStack(loot_sack_rare), 1, 1, 1));

        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST,
                new WeightedRandomChestContent(new ItemStack(loot_sack), 1, 4, 10));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST,
                new WeightedRandomChestContent(new ItemStack(loot_sack_uc), 1, 2, 5));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST,
                new WeightedRandomChestContent(new ItemStack(loot_sack_rare), 1, 1, 1));

        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST,
                new WeightedRandomChestContent(new ItemStack(loot_sack), 1, 4, 10));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST,
                new WeightedRandomChestContent(new ItemStack(loot_sack_uc), 1, 2, 5));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST,
                new WeightedRandomChestContent(new ItemStack(loot_sack_rare), 1, 1, 1));

        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR,
                new WeightedRandomChestContent(new ItemStack(loot_sack), 1, 6, 20));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR,
                new WeightedRandomChestContent(new ItemStack(loot_sack_uc), 1, 1, 2));

        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR,
                new WeightedRandomChestContent(new ItemStack(loot_sack), 1, 4, 5));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR,
                new WeightedRandomChestContent(new ItemStack(loot_sack_uc), 1, 2, 3));

        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING,
                new WeightedRandomChestContent(new ItemStack(loot_sack), 1, 3, 20));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING,
                new WeightedRandomChestContent(new ItemStack(loot_sack_uc), 1, 2, 10));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING,
                new WeightedRandomChestContent(new ItemStack(loot_sack_rare), 1, 1, 2));

        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY,
                new WeightedRandomChestContent(new ItemStack(skillbook_artisanry), 2, 6, 10));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY,
                new WeightedRandomChestContent(new ItemStack(skillbook_construction), 2, 6, 10));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY,
                new WeightedRandomChestContent(new ItemStack(skillbook_provisioning), 2, 6, 10));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY,
                new WeightedRandomChestContent(new ItemStack(skillbook_engineering), 1, 4, 8));

        ChestGenHooks.addItem(LootTypes.DWARVEN_STUDY,
                new WeightedRandomChestContent(new ItemStack(skillbook_engineering), 1, 1, 20));
        ChestGenHooks.addItem(LootTypes.DWARVEN_STUDY,
                new WeightedRandomChestContent(new ItemStack(Items.paper), 5, 36, 100));
        ChestGenHooks.addItem(LootTypes.DWARVEN_STUDY,
                new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 4, 50));
        ChestGenHooks.addItem(LootTypes.DWARVEN_STUDY,
                new WeightedRandomChestContent(new ItemStack(Items.book), 5, 26, 80));

        ChestGenHooks.addItem(LootTypes.DWARVEN_FORGE,
                new WeightedRandomChestContent(new ItemStack(skillbook_artisanry), 1, 1, 40));
        ChestGenHooks.addItem(LootTypes.DWARVEN_FORGE,
                new WeightedRandomChestContent(new ItemStack(Items.coal), 5, 17, 100));
        ChestGenHooks.addItem(LootTypes.DWARVEN_FORGE,
                new WeightedRandomChestContent(ComponentListMF.bar("Iron"), 1, 4, 50));
        ChestGenHooks.addItem(LootTypes.DWARVEN_FORGE,
                new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 80));
        ChestGenHooks.addItem(LootTypes.DWARVEN_FORGE,
                new WeightedRandomChestContent(ComponentListMF.bar("Steel"), 1, 6, 40));

        ChestGenHooks.addItem(LootTypes.DWARVEN_ARMOURY,
                new WeightedRandomChestContent(new ItemStack(BlockListMF.repair_basic), 1, 1, 100));
        ChestGenHooks.addItem(LootTypes.DWARVEN_ARMOURY,
                new WeightedRandomChestContent(new ItemStack(BlockListMF.repair_advanced), 1, 1, 50));
        ChestGenHooks.addItem(LootTypes.DWARVEN_ARMOURY,
                new WeightedRandomChestContent(new ItemStack(ComponentListMF.coke), 1, 10, 60));
        ChestGenHooks.addItem(LootTypes.DWARVEN_ARMOURY,
                new WeightedRandomChestContent(new ItemStack(skillbook_engineering), 1, 1, 40));
        ChestGenHooks.addItem(LootTypes.DWARVEN_ARMOURY,
                new WeightedRandomChestContent(new ItemStack(ComponentListMF.bronze_gears), 1, 9, 30));
        ChestGenHooks.addItem(LootTypes.DWARVEN_ARMOURY,
                new WeightedRandomChestContent(new ItemStack(ComponentListMF.tungsten_gears), 1, 1, 20));

        ChestGenHooks.addItem(LootTypes.DWARVEN_AMMO,
                new WeightedRandomChestContent(CustomToolListMF.standard_bolt.construct("Iron"), 1, 1, 100));
        ChestGenHooks.addItem(LootTypes.DWARVEN_AMMO,
                new WeightedRandomChestContent(CustomToolListMF.standard_bolt.construct("Encrusted"), 1, 1, 20));
        ChestGenHooks.addItem(LootTypes.DWARVEN_AMMO, new WeightedRandomChestContent(
                bomb_custom.createBomb((byte) 1, (byte) 1, (byte) 0, (byte) 0, 1), 1, 1, 25));
        ChestGenHooks.addItem(LootTypes.DWARVEN_AMMO, new WeightedRandomChestContent(
                mine_custom.createMine((byte) 1, (byte) 1, (byte) 0, (byte) 0, 1), 1, 1, 25));

        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME,
                new WeightedRandomChestContent(new ItemStack(loot_sack), 1, 1, 40));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME,
                new WeightedRandomChestContent(new ItemStack(loot_sack_uc), 1, 1, 10));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME,
                new WeightedRandomChestContent(new ItemStack(Items.book), 1, 4, 60));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME,
                new WeightedRandomChestContent(new ItemStack(Items.bowl), 1, 6, 100));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME,
                new WeightedRandomChestContent(new ItemStack(ComponentListMF.clay_pot), 1, 7, 100));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME,
                new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 9, 80));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME,
                new WeightedRandomChestContent(new ItemStack(Items.emerald), 1, 1, 20));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME,
                new WeightedRandomChestContent(new ItemStack(Items.diamond), 1, 1, 20));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME,
                new WeightedRandomChestContent(ComponentListMF.bar("Iron"), 1, 4, 30));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME,
                new WeightedRandomChestContent(ComponentListMF.bar("Gold"), 1, 4, 20));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME,
                new WeightedRandomChestContent(ComponentListMF.bar("Silver"), 1, 4, 20));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME,
                new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 1, 80));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME,
                new WeightedRandomChestContent(new ItemStack(Items.clock), 1, 1, 20));

        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME_RICH,
                new WeightedRandomChestContent(new ItemStack(loot_sack_uc), 1, 1, 40));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME_RICH,
                new WeightedRandomChestContent(new ItemStack(loot_sack_rare), 1, 1, 10));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME_RICH,
                new WeightedRandomChestContent(new ItemStack(Items.book), 1, 4, 20));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME_RICH,
                new WeightedRandomChestContent(new ItemStack(Items.bowl), 1, 6, 30));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME_RICH,
                new WeightedRandomChestContent(new ItemStack(ComponentListMF.clay_pot), 1, 7, 20));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME_RICH,
                new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 9, 10));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME_RICH,
                new WeightedRandomChestContent(new ItemStack(Items.emerald), 1, 1, 50));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME_RICH,
                new WeightedRandomChestContent(new ItemStack(Items.diamond), 1, 1, 50));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME_RICH,
                new WeightedRandomChestContent(ComponentListMF.bar("Iron"), 1, 4, 30));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME_RICH,
                new WeightedRandomChestContent(ComponentListMF.bar("Gold"), 1, 4, 50));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME_RICH,
                new WeightedRandomChestContent(ComponentListMF.bar("Silver"), 1, 4, 50));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME_RICH,
                new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 1, 20));
        ChestGenHooks.addItem(LootTypes.DWARVEN_HOME_RICH,
                new WeightedRandomChestContent(new ItemStack(Items.clock), 1, 1, 40));
    }

    private static void weakenItems() {
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

    private static void weakenItem(Item item) {
        weakenItem(item, (item.getMaxDamage() / 10) + 1);
    }

    private static void weakenItem(Item item, int hp) {
        if (item.isDamageable()) {
            item.setMaxDamage(hp);
        }
    }

}
