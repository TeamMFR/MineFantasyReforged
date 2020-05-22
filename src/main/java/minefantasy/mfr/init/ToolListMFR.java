package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReborn;
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
import minefantasy.mfr.item.tool.ItemAxe;
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
import minefantasy.mfr.item.weapon.ItemMace;
import minefantasy.mfr.item.weapon.ItemSpear;
import minefantasy.mfr.item.weapon.ItemSword;
import minefantasy.mfr.item.weapon.ItemWaraxe;
import minefantasy.mfr.material.BaseMaterialMFR;
import minefantasy.mfr.util.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;


@ObjectHolder(MineFantasyReborn.MOD_ID)
@Mod.EventBusSubscriber(modid = MineFantasyReborn.MOD_ID)
public class ToolListMFR {
    public static EnumRarity POOR;
    public static EnumRarity UNIQUE;
    public static EnumRarity RARE;

    public static EnumRarity[] RARITY;

    public static final Item TRAINING_SWORD = Utils.nullValue();
    public static final Item TRAINING_WARAXE = Utils.nullValue();
    public static final Item TRAINING_MACE = Utils.nullValue();
    public static final Item TRAINING_SPEAR = Utils.nullValue();

    public static final Item STONE_KNIFE = Utils.nullValue();
    public static final Item STONE_HAMMER = Utils.nullValue();
    public static final Item STONE_TONGS = Utils.nullValue();
    public static final Item BONE_NEEDLE = Utils.nullValue();
    public static final Item STONE_PICK = Utils.nullValue();
    public static final Item STONE_AXE = Utils.nullValue();
    public static final Item STONE_SPADE = Utils.nullValue();
    public static final Item STONE_HOE = Utils.nullValue();
    public static final Item STONE_SWORD = Utils.nullValue();
    public static final Item STONE_MACE = Utils.nullValue();
    public static final Item STONE_WARAXE = Utils.nullValue();
    public static final Item STONE_SPEAR = Utils.nullValue();

    public static final Item BANDAGE_CRUDE = Utils.nullValue();
    public static final Item BANDAGE_WOOL = Utils.nullValue();
    public static final Item BANDAGE_TOUGH = Utils.nullValue();

    public static final ItemCrudeBomb BOMB_CRUDE = Utils.nullValue();
    public static final ItemBomb BOMB_CUSTOM = Utils.nullValue();
    public static final ItemMine MINE_CUSTOM = Utils.nullValue();

    public static final ItemResearchBook RESEARCH_BOOK = Utils.nullValue();

    public static final Item DRY_ROCKS = Utils.nullValue();
    public static final Item TINDERBOX = Utils.nullValue();

    public static final Item SKILLBOOK_ARTISANRY = Utils.nullValue();
    public static final Item SKILLBOOK_CONSTRUCTION = Utils.nullValue();
    public static final Item SKILLBOOK_PROVISIONING = Utils.nullValue();
    public static final Item SKILLBOOK_ENGINEERING = Utils.nullValue();
    public static final Item SKILLBOOK_COMBAT = Utils.nullValue();

    public static final Item SKILLBOOK_ARTISANRY_MAX = Utils.nullValue();
    public static final Item SKILLBOOK_CONSTRUCTION_MAX = Utils.nullValue();
    public static final Item SKILLBOOK_PROVISIONING_MAX = Utils.nullValue();
    public static final Item SKILLBOOK_ENGINEERING_MAX = Utils.nullValue();
    public static final Item SKILLBOOK_COMBAT_MAX = Utils.nullValue();

    public static final Item ENGIN_ANVIL_TOOLS = Utils.nullValue();

    public static final Item EXPLODING_ARROW = Utils.nullValue();
    public static final Item SPYGLASS = Utils.nullValue();
    public static final Item CLIMBING_PICK_BASIC = Utils.nullValue();
    public static final Item PARACHUTE = Utils.nullValue();

    public static final Item SYRINGE = Utils.nullValue();
    public static final Item SYRINGE_EMPTY = Utils.nullValue();

    public static final Item LOOT_SACK = Utils.nullValue();
    public static final Item LOOT_SACK_UC = Utils.nullValue();
    public static final Item LOOT_SACK_RARE = Utils.nullValue();

    public static final ItemCrossbow CROSSBOW_CUSTOM = Utils.nullValue();
    public static final Item EXPLODING_BOLT = Utils.nullValue();

    public static final Item PAINT_BRUSH = Utils.nullValue();

    public static final Item DEBUG_PLACE = Utils.nullValue();
    public static final Item DEBUG_MOB = Utils.nullValue();

    public static void init() {

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(new ItemSword("training_sword", ToolMaterial.WOOD, -1, 0.8F));
        registry.register(new ItemWaraxe("training_waraxe", ToolMaterial.WOOD, -1, 0.8F));
        registry.register(new ItemMace("training_mace", ToolMaterial.WOOD, -1, 0.8F));
        registry.register(new ItemSpear("training_spear", ToolMaterial.WOOD, -1, 0.8F));

        registry.register(new ItemKnifeMFR("stone_knife", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 3.5F, 0));
        registry.register(new ItemHammer("stone_hammer", BaseMaterialMFR.getMaterial("stone").getToolConversion(), false, -1, 0));
        registry.register(new ItemTongs("stone_tongs", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1));
        registry.register(new ItemNeedle("bone_needle", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 0));
        registry.register(new ItemPickMF("stone_pick", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1));
        registry.register(new ItemAxe("stone_axe", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1));
        registry.register(new ItemSpadeMF("stone_spade", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1));
        registry.register(new ItemHoeMF("stone_hoe", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1));
        registry.register(new ItemSword("stone_sword", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F));
        registry.register(new ItemMace("stone_mace", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F));
        registry.register(new ItemWaraxe("stone_waraxe", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F));
        registry.register(new ItemSpear("stone_spear", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F));

        registry.register(new ItemBandage("bandage_crude", 5F));
        registry.register(new ItemBandage("bandage_wool", 8F));
        registry.register(new ItemBandage("bandage_tough", 12F));

        registry.register(new ItemCrudeBomb("bomb_crude"));
        registry.register(new ItemBomb("bomb_basic"));
        registry.register(new ItemMine("mine_basic"));

        registry.register(new ItemResearchBook());

        registry.register(new ItemLighterMF("dryrocks", 0.1F, 16));
        registry.register(new ItemLighterMF("tinderbox", 0.5F, 100));

        registry.register(new ItemSkillBook("skillbook_artisanry", SkillList.artisanry));
        registry.register(new ItemSkillBook("skillbook_construction", SkillList.construction));
        registry.register(new ItemSkillBook("skillbook_provisioning", SkillList.provisioning));
        registry.register(new ItemSkillBook("skillbook_engineering", SkillList.engineering));
        registry.register(new ItemSkillBook("skillbook_combat", SkillList.combat));

        registry.register(new ItemSkillBook("skillbook_artisanry_max", SkillList.artisanry).setMax());
        registry.register(new ItemSkillBook("skillbook_construction_max", SkillList.construction).setMax());
        registry.register(new ItemSkillBook("skillbook_provisioning_max", SkillList.provisioning).setMax());
        registry.register(new ItemSkillBook("skillbook_engineering_max", SkillList.engineering).setMax());
        registry.register(new ItemSkillBook("skillbook_combat2", SkillList.combat).setMax());

        registry.register(new ItemEAnvilTools("engin_anvil_tools", 64));

        registry.register(new ItemExplodingArrow());
        registry.register(new ItemSpyglass());
        registry.register(new ItemClimbingPick("climbing_pick_basic", ToolMaterial.IRON, 0));
        registry.register(new ItemParachute());

        registry.register(new ItemSyringe());
        registry.register(new ItemComponentMFR("syringe_empty").setCreativeTab(CreativeTabMFR.tabGadget));

        registry.register(new ItemLootSack("loot_sack", 8, 0));
        registry.register(new ItemLootSack("loot_sack_uc", 8, 1));
        registry.register(new ItemLootSack("loot_sack_rare", 12, 2));

        registry.register(new ItemCrossbow());
        registry.register(new ItemExplodingBolt());

        registry.register(new ItemPaintBrush("paint_brush", 256));

        registry.register(new ItemWorldGenPlacer());
        registry.register(new MobSpawnerMF());
    }

    public static void load() {
        POOR = EnumHelper.addRarity("Poor", TextFormatting.DARK_GRAY, "poor");
        UNIQUE = EnumHelper.addRarity("Unique", TextFormatting.DARK_GREEN, "unique");
        RARE = EnumHelper.addRarity("Rare", TextFormatting.DARK_BLUE, "rare");

        RARITY = new EnumRarity[]{ToolListMFR.POOR, EnumRarity.COMMON, EnumRarity.UNCOMMON, EnumRarity.RARE, EnumRarity.EPIC};

        if (ConfigHardcore.HCCWeakItems) {
            weakenItems();
        }
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
