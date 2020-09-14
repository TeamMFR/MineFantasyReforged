//package minefantasy.mfr.init;
//
//import minefantasy.mfr.MineFantasyReborn;
//import minefantasy.mfr.api.MineFantasyRebornAPI;
//import minefantasy.mfr.api.armour.ArmourDesign;
//import minefantasy.mfr.api.armour.ArmourMaterialMFR;
//import minefantasy.mfr.api.cooking.CookRecipe;
//import minefantasy.mfr.api.crafting.MineFantasyFuels;
//import minefantasy.mfr.api.crafting.exotic.SpecialForging;
//import minefantasy.mfr.api.mining.RandomDigs;
//import minefantasy.mfr.api.mining.RandomOre;
//import minefantasy.mfr.api.rpg.SkillList;
//import minefantasy.mfr.config.ConfigHardcore;
//import minefantasy.mfr.item.AdvancedFuelHandlerMF;
//import minefantasy.mfr.item.FuelHandlerMF;
//import minefantasy.mfr.item.ItemArtefact;
//import minefantasy.mfr.item.ItemBandage;
//import minefantasy.mfr.item.ItemBowl;
//import minefantasy.mfr.item.ItemBurntFood;
//import minefantasy.mfr.item.ItemComponentMFR;
//import minefantasy.mfr.item.ItemFilledMould;
//import minefantasy.mfr.item.ItemHide;
//import minefantasy.mfr.item.ItemRawOre;
//import minefantasy.mfr.item.ItemResearchBook;
//import minefantasy.mfr.item.ItemSkillBook;
//import minefantasy.mfr.item.ItemSpecialDesign;
//import minefantasy.mfr.item.ItemWorldGenPlacer;
//import minefantasy.mfr.item.archery.ArrowType;
//import minefantasy.mfr.item.archery.EnumBowType;
//import minefantasy.mfr.item.archery.ItemArrowMFR;
//import minefantasy.mfr.item.archery.ItemBowMFR;
//import minefantasy.mfr.item.armour.ItemApron;
//import minefantasy.mfr.item.armour.ItemArmourMFR;
//import minefantasy.mfr.item.armour.ItemCustomArmour;
//import minefantasy.mfr.item.custom.ItemCustomComponent;
//import minefantasy.mfr.item.food.ItemFoodMF;
//import minefantasy.mfr.item.food.ItemMultiFood;
//import minefantasy.mfr.item.food.ItemUnfinishedFood;
//import minefantasy.mfr.item.gadget.ItemBomb;
//import minefantasy.mfr.item.gadget.ItemBombComponent;
//import minefantasy.mfr.item.gadget.ItemClimbingPick;
//import minefantasy.mfr.item.gadget.ItemCrossbow;
//import minefantasy.mfr.item.gadget.ItemCrossbowPart;
//import minefantasy.mfr.item.gadget.ItemCrudeBomb;
//import minefantasy.mfr.item.gadget.ItemExplodingArrow;
//import minefantasy.mfr.item.gadget.ItemExplodingBolt;
//import minefantasy.mfr.item.gadget.ItemJug;
//import minefantasy.mfr.item.gadget.ItemLootSack;
//import minefantasy.mfr.item.gadget.ItemMine;
//import minefantasy.mfr.item.gadget.ItemParachute;
//import minefantasy.mfr.item.gadget.ItemSpyglass;
//import minefantasy.mfr.item.gadget.ItemSyringe;
//import minefantasy.mfr.item.gadget.MobSpawnerMF;
//import minefantasy.mfr.item.heatable.ItemHeated;
//import minefantasy.mfr.item.tool.ItemAxe;
//import minefantasy.mfr.item.tool.ItemHoeMF;
//import minefantasy.mfr.item.tool.ItemLighterMF;
//import minefantasy.mfr.item.tool.ItemPickMF;
//import minefantasy.mfr.item.tool.ItemShears;
//import minefantasy.mfr.item.tool.ItemSpadeMF;
//import minefantasy.mfr.item.tool.advanced.ItemHandpick;
//import minefantasy.mfr.item.tool.advanced.ItemHvyPick;
//import minefantasy.mfr.item.tool.advanced.ItemHvyShovel;
//import minefantasy.mfr.item.tool.advanced.ItemLumberAxe;
//import minefantasy.mfr.item.tool.advanced.ItemMattock;
//import minefantasy.mfr.item.tool.advanced.ItemScythe;
//import minefantasy.mfr.item.tool.advanced.ItemTrowMF;
//import minefantasy.mfr.item.tool.crafting.ItemBasicCraftTool;
//import minefantasy.mfr.item.tool.crafting.ItemEAnvilTools;
//import minefantasy.mfr.item.tool.crafting.ItemHammer;
//import minefantasy.mfr.item.tool.crafting.ItemKnifeMFR;
//import minefantasy.mfr.item.tool.crafting.ItemNeedle;
//import minefantasy.mfr.item.tool.crafting.ItemPaintBrush;
//import minefantasy.mfr.item.tool.crafting.ItemSaw;
//import minefantasy.mfr.item.tool.crafting.ItemSpanner;
//import minefantasy.mfr.item.tool.crafting.ItemTongs;
//import minefantasy.mfr.item.weapon.ItemBattleaxe;
//import minefantasy.mfr.item.weapon.ItemDagger;
//import minefantasy.mfr.item.weapon.ItemGreatsword;
//import minefantasy.mfr.item.weapon.ItemHalbeard;
//import minefantasy.mfr.item.weapon.ItemKatana;
//import minefantasy.mfr.item.weapon.ItemLance;
//import minefantasy.mfr.item.weapon.ItemMace;
//import minefantasy.mfr.item.weapon.ItemSpear;
//import minefantasy.mfr.item.weapon.ItemSword;
//import minefantasy.mfr.item.weapon.ItemWaraxe;
//import minefantasy.mfr.item.weapon.ItemWarhammer;
//import minefantasy.mfr.item.weapon.ItemWeaponMFR;
//import minefantasy.mfr.material.BaseMaterialMFR;
//import minefantasy.mfr.material.WoodMaterial;
//import minefantasy.mfr.util.Utils;
//import net.minecraft.creativetab.CreativeTabs;
//import net.minecraft.init.Blocks;
//import net.minecraft.init.Items;
//import net.minecraft.init.MobEffects;
//import net.minecraft.inventory.EntityEquipmentSlot;
//import net.minecraft.item.EnumRarity;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.potion.PotionEffect;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraftforge.common.util.EnumHelper;
//import net.minecraftforge.event.RegistryEvent;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.registry.GameRegistry;
//import net.minecraftforge.registries.IForgeRegistry;
//
//@GameRegistry.ObjectHolder(MineFantasyReborn.MOD_ID)
//@Mod.EventBusSubscriber(modid = MineFantasyReborn.MOD_ID)
//public class ItemListMFR {
//
//	//ComponentListMFR
//	public static ItemComponentMFR[] INGOTS;
//
//	public static final Item CLAY_POT = Utils.nullValue();
//	public static final Item CLAY_POT_UNCOOKED = Utils.nullValue();
//	public static final Item INGOT_MOULD = Utils.nullValue();
//	public static final Item INGOT_MOULD_UNCOOKED = Utils.nullValue();
//	public static final Item PIE_TRAY_UNCOOKED = Utils.nullValue();
//
//	public static final ItemComponentMFR COPPER_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR TIN_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR BRONZE_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR PIG_IRON_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR STEEL_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR ENCRUSTED_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR BLACK_STEEL_WEAK_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR BLACK_STEEL_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR SILVER_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR RED_STEEL_WEAK_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR RED_STEEL_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR BLUE_STEEL_WEAK_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR BLUE_STEEL_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR ADAMANTIUM_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR MITHRIL_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR IGNOTUMITE_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR MITHIUM_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR ENDER_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR TUNGSTEN_INGOT = Utils.nullValue();
//	public static final ItemComponentMFR OBSIDIAN_INGOT = Utils.nullValue();
//
//	public static final Item COMPOSITE_ALLOY_INGOT = Utils.nullValue();
//
//	public static final ItemComponentMFR PLANK = Utils.nullValue();
//	public static final Item VINE = Utils.nullValue();
//	public static final Item SHARP_ROCK = Utils.nullValue();
//
//	public static final Item FLUX = Utils.nullValue();
//	public static final Item FLUX_STRONG = Utils.nullValue();
//
//	public static final Item COAL_DUST = Utils.nullValue();
//	public static final Item NITRE = Utils.nullValue();
//	public static final Item SULFUR = Utils.nullValue();
//	public static final Item IRON_PREP = Utils.nullValue();
//	public static final Item BLACKPOWDER = Utils.nullValue();
//	public static final Item BLACKPOWDER_ADVANCED = Utils.nullValue();
//	public static final Item FLETCHING = Utils.nullValue();
//	public static final Item SHRAPNEL = Utils.nullValue();
//	public static final Item MAGMA_CREAM_REFINED = Utils.nullValue();
//	public static final Item BOMB_FUSE = Utils.nullValue();
//	public static final Item BOMB_FUSE_LONG = Utils.nullValue();
//	public static final Item BOMB_CASING_UNCOOKED = Utils.nullValue();
//	public static final Item BOMB_CASING = Utils.nullValue();
//	public static final Item MINE_CASING_UNCOOKED = Utils.nullValue();
//	public static final Item MINE_CASING = Utils.nullValue();
//	public static final Item BOMB_CASING_IRON = Utils.nullValue();
//	public static final Item MINE_CASING_IRON = Utils.nullValue();
//	public static final Item BOMB_CASING_OBSIDIAN = Utils.nullValue();
//	public static final Item MINE_CASING_OBSIDIAN = Utils.nullValue();
//	public static final Item BOMB_CASING_CRYSTAL = Utils.nullValue();
//	public static final Item MINE_CASING_CRYSTAL = Utils.nullValue();
//	public static final Item BOMB_CASING_ARROW = Utils.nullValue();
//	public static final Item BOMB_CASING_BOLT = Utils.nullValue();
//
//	public static final Item COKE = Utils.nullValue();
//	public static final Item DIAMOND_SHARDS = Utils.nullValue();
//
//	public static final Item CLAY_BRICK = Utils.nullValue();
//	public static final Item KAOLINITE = Utils.nullValue();
//	public static final Item KAOLINITE_DUST = Utils.nullValue();
//	public static final Item FIRECLAY = Utils.nullValue();
//	public static final Item FIRECLAY_BRICK = Utils.nullValue();
//	public static final Item STRONG_BRICK = Utils.nullValue();
//
//	public static final Item HIDE_SMALL = Utils.nullValue();
//	public static final Item HIDE_MEDIUM = Utils.nullValue();
//	public static final Item HIDE_LARGE = Utils.nullValue();
//	public static final Item RAWHIDE_SMALL = Utils.nullValue();
//	public static final Item RAWHIDE_MEDIUM = Utils.nullValue();
//	public static final Item RAWHIDE_LARGE = Utils.nullValue();
//
//	public static final Item DRAGON_HEART = Utils.nullValue();
//
//	public static final Item LEATHER_STRIP = Utils.nullValue();
//	public static final Item NAIL = Utils.nullValue();
//	public static final Item RIVET = Utils.nullValue();
//	public static final Item THREAD = Utils.nullValue();
//	public static final Item OBSIDIAN_ROCK = Utils.nullValue();
//
//	public static final Item ORE_COPPER = Utils.nullValue();
//	public static final Item ORE_TIN = Utils.nullValue();
//	public static final Item ORE_IRON = Utils.nullValue();
//	public static final Item ORE_SILVER = Utils.nullValue();
//	public static final Item ORE_GOLD = Utils.nullValue();
//	public static final Item ORE_TUNGSTEN = Utils.nullValue();
//
//	public static final Item HOT_ITEM = Utils.nullValue();
//
//	public static final Item PLANT_OIL = Utils.nullValue();
//
//	public static final Item TALISMAN_LESSER = Utils.nullValue();
//	public static final Item TALISMAN_GREATER = Utils.nullValue();
//
//	public static final Item BOLT = Utils.nullValue();
//	public static final Item IRON_FRAME = Utils.nullValue();
//	public static final Item IRON_STRUT = Utils.nullValue();
//	public static final Item BRONZE_GEARS = Utils.nullValue();
//	public static final Item TUNGSTEN_GEARS = Utils.nullValue();
//	public static final Item STEEL_TUBE = Utils.nullValue();
//	public static final Item COGWORK_SHAFT = Utils.nullValue();
//
//	public static final Item COAL_PREP = Utils.nullValue();
//
//	public static final Item INGOT_MOULD_FILLED = Utils.nullValue();
//
//	public static final Item CROSSBOW_STOCK_WOOD = Utils.nullValue();
//	public static final Item CROSSBOW_STOCK_IRON = Utils.nullValue();
//	public static final Item CROSSBOW_HANDLE_WOOD = Utils.nullValue();
//
//	public static final Item CROSS_ARMS_BASIC = Utils.nullValue();
//	public static final Item CROSS_ARMS_LIGHT = Utils.nullValue();
//	public static final Item CROSS_ARMS_HEAVY = Utils.nullValue();
//	public static final Item CROSS_ARMS_ADVANCED = Utils.nullValue();
//
//	public static final Item CROSS_BAYONET = Utils.nullValue();
//	public static final Item CROSS_AMMO = Utils.nullValue();
//	public static final Item CROSS_SCOPE = Utils.nullValue();
//
//	public static final ItemCustomComponent CHAIN_MESH = Utils.nullValue();
//	public static final ItemCustomComponent SCALE_MESH = Utils.nullValue();
//	public static final ItemCustomComponent SPLINT_MESH = Utils.nullValue();
//	public static final ItemCustomComponent PLATE = Utils.nullValue();
//	public static final ItemCustomComponent PLATE_HUGE = Utils.nullValue();
//	public static final ItemCustomComponent METAL_HUNK = Utils.nullValue();
//	public static final ItemCustomComponent ARROWHEAD = Utils.nullValue();
//	public static final ItemCustomComponent BODKIN_HEAD = Utils.nullValue();
//	public static final ItemCustomComponent BROAD_HEAD = Utils.nullValue();
//	public static final ItemCustomComponent COGWORK_ARMOUR = Utils.nullValue();
//	public static final ItemCustomComponent BAR = Utils.nullValue();
//
//	public static final Item FLUX_POT = Utils.nullValue();
//	public static final Item COAL_FLUX = Utils.nullValue();
//
//	public static final Item COPPER_COIN = Utils.nullValue();
//	public static final Item SILVER_COIN = Utils.nullValue();
//	public static final Item GOLD_COIN = Utils.nullValue();
//
//	public static final Item HINGE = Utils.nullValue();
//	public static final Item PLANK_CUT = Utils.nullValue();
//	public static final Item PLANK_PANE = Utils.nullValue();
//
//	public static final Item COGWORK_PULLEY = Utils.nullValue();
//
//	public static final Item ARTEFACTS = Utils.nullValue();
//
//	public static final Item ORNATE_ITEMS = Utils.nullValue();
//
//	//ToolListMFR
//	public static EnumRarity POOR;
//	public static EnumRarity UNIQUE;
//	public static EnumRarity RARE;
//
//	public static EnumRarity[] RARITY;
//
//	public static final Item TRAINING_SWORD = Utils.nullValue();
//	public static final Item TRAINING_WARAXE = Utils.nullValue();
//	public static final Item TRAINING_MACE = Utils.nullValue();
//	public static final Item TRAINING_SPEAR = Utils.nullValue();
//
//	public static final Item STONE_KNIFE = Utils.nullValue();
//	public static final Item STONE_HAMMER = Utils.nullValue();
//	public static final Item STONE_TONGS = Utils.nullValue();
//	public static final Item BONE_NEEDLE = Utils.nullValue();
//	public static final Item STONE_PICK = Utils.nullValue();
//	public static final Item STONE_AXE = Utils.nullValue();
//	public static final Item STONE_SPADE = Utils.nullValue();
//	public static final Item STONE_HOE = Utils.nullValue();
//	public static final Item STONE_SWORD = Utils.nullValue();
//	public static final Item STONE_MACE = Utils.nullValue();
//	public static final Item STONE_WARAXE = Utils.nullValue();
//	public static final Item STONE_SPEAR = Utils.nullValue();
//
//	public static final Item BANDAGE_CRUDE = Utils.nullValue();
//	public static final Item BANDAGE_WOOL = Utils.nullValue();
//	public static final Item BANDAGE_TOUGH = Utils.nullValue();
//
//	public static final ItemCrudeBomb BOMB_CRUDE = Utils.nullValue();
//	public static final ItemBomb BOMB_CUSTOM = Utils.nullValue();
//	public static final ItemMine MINE_CUSTOM = Utils.nullValue();
//
//	public static final ItemResearchBook RESEARCH_BOOK = Utils.nullValue();
//
//	public static final Item DRY_ROCKS = Utils.nullValue();
//	public static final Item TINDERBOX = Utils.nullValue();
//
//	public static final Item SKILLBOOK_ARTISANRY = Utils.nullValue();
//	public static final Item SKILLBOOK_CONSTRUCTION = Utils.nullValue();
//	public static final Item SKILLBOOK_PROVISIONING = Utils.nullValue();
//	public static final Item SKILLBOOK_ENGINEERING = Utils.nullValue();
//	public static final Item SKILLBOOK_COMBAT = Utils.nullValue();
//
//	public static final Item SKILLBOOK_ARTISANRY_MAX = Utils.nullValue();
//	public static final Item SKILLBOOK_CONSTRUCTION_MAX = Utils.nullValue();
//	public static final Item SKILLBOOK_PROVISIONING_MAX = Utils.nullValue();
//	public static final Item SKILLBOOK_ENGINEERING_MAX = Utils.nullValue();
//	public static final Item SKILLBOOK_COMBAT_MAX = Utils.nullValue();
//
//	public static final Item ENGIN_ANVIL_TOOLS = Utils.nullValue();
//
//	public static final Item EXPLODING_ARROW = Utils.nullValue();
//	public static final Item SPYGLASS = Utils.nullValue();
//	public static final Item CLIMBING_PICK_BASIC = Utils.nullValue();
//	public static final Item PARACHUTE = Utils.nullValue();
//
//	public static final Item SYRINGE = Utils.nullValue();
//	public static final Item SYRINGE_EMPTY = Utils.nullValue();
//
//	public static final Item LOOT_SACK = Utils.nullValue();
//	public static final Item LOOT_SACK_UC = Utils.nullValue();
//	public static final Item LOOT_SACK_RARE = Utils.nullValue();
//
//	public static final ItemCrossbow CROSSBOW_CUSTOM = Utils.nullValue();
//	public static final Item EXPLODING_BOLT = Utils.nullValue();
//
//	public static final Item PAINT_BRUSH = Utils.nullValue();
//
//	public static final Item DEBUG_PLACE_ANCIENT_FORGE = Utils.nullValue();
//	public static final Item DEBUG_MOB = Utils.nullValue();
//
//	//FoodListMFR
//	public static float SAT_MODIFIER = 1.0F;
//
//	// MORSELS
//	public static final Item WOLF_RAW = Utils.nullValue();
//	public static final Item WOLF_COOKED = Utils.nullValue();
//	public static final Item HORSE_RAW = Utils.nullValue();
//	public static final Item HORSE_COOKED = Utils.nullValue();
//	public static final Item GENERIC_MEAT_UNCOOKED = Utils.nullValue();
//	public static final Item GENERIC_MEAT_COOKED = Utils.nullValue();
//	public static final Item GENERIC_MEAT_STRIP_UNCOOKED = Utils.nullValue();
//	public static final Item GENERIC_MEAT_STRIP_COOKED = Utils.nullValue();
//	public static final Item GENERIC_MEAT_CHUNK_UNCOOKED = Utils.nullValue();
//	public static final Item GENERIC_MEAT_CHUNK_COOKED = Utils.nullValue();
//	public static final Item GENERIC_MEAT_MINCE_UNCOOKED = Utils.nullValue();
//	public static final Item GENERIC_MEAT_MINCE_COOKED = Utils.nullValue();
//	public static final Item FLOUR = Utils.nullValue();
//	public static final Item BREADCRUMBS = Utils.nullValue();
//	public static final Item GUTS = Utils.nullValue();
//	public static final Item BREADROLL = Utils.nullValue();
//	public static final Item BREAD_SLICE = Utils.nullValue();
//	public static final Item CURDS = Utils.nullValue();
//	public static final Item CHEESE_POT = Utils.nullValue();
//	public static final Item CHEESE_SLICE = Utils.nullValue();
//
//	// T1 (basic mixing)
//	// Util: Roast, Prep Block (Stone-Bronze Age)
//	public static final Item STEW = Utils.nullValue();
//	public static final Item OATS = Utils.nullValue();
//
//	// T2 (Basic baking, stone oven, processed mixing)
//	// Util: Stone Oven, Prep Block (Bronze Age - Early Iron Age)
//	public static final Item CHEESE_ROLL = Utils.nullValue();
//	public static final Item JERKY = Utils.nullValue();
//	public static final Item SAUSAGE_RAW = Utils.nullValue();
//	public static final Item SAUSAGE_COOKED = Utils.nullValue();
//	public static final Item SWEETROLL_UNICED = Utils.nullValue();
//	public static final Item SWEETROLL = Utils.nullValue();
//	public static final Item SANDWITCH_MEAT = Utils.nullValue();
//	public static final Item SANDWITCH_BIG = Utils.nullValue();
//
//	// T3 (Quality baking, metal oven)
//	// Util: Metal Oven, Prep Block, Steel Tools (Mid Iron Age)
//	public static final Item MEATPIE_SLICE = Utils.nullValue();
//	public static final Item PIESLICE_APPLE = Utils.nullValue();
//	public static final Item PIESLICE_BERRY = Utils.nullValue();
//
//	// T4 (Advanced baking, multiple processes, temperature regulation)
//	// Util : Metal Oven, Prep Block, Full tool set, Proper kitchen setup (Mid Iron
//	// Age)
//	public static final Item PIESLICE_SHEPARDS = Utils.nullValue();
//	public static final Item CAKE_SLICE = Utils.nullValue();
//	public static final Item CARROTCAKE_SLICE = Utils.nullValue();
//
//	// T5 (Advanced baking, multiple process, temperature regulation, many
//	// ingreedients)
//	public static final Item CHOCCAKE_SLICE = Utils.nullValue();
//
//	// T6 (Perfeted meals, extremely difficylt to create)
//	public static final Item BFCAKE_SLICE = Utils.nullValue();
//	public static final Item ECLAIR_UNICED = Utils.nullValue();
//	public static final Item ECLAIR_EMPTY = Utils.nullValue();
//	public static final Item ECLAIR = Utils.nullValue();
//
//	// MISC
//	public static final Item CAKE_TIN = Utils.nullValue();
//	public static final Item PIE_TRAY = Utils.nullValue();
//	public static final Item ICING = Utils.nullValue();
//	public static final Item CUSTARD = Utils.nullValue();
//	public static final Item COCA_POWDER = Utils.nullValue();
//	public static final Item CHOCOLATE = Utils.nullValue();
//	public static final Item CHOC_CHIPS = Utils.nullValue();
//	public static final Item BERRIES = Utils.nullValue();
//	public static final Item BERRIES_JUICY = Utils.nullValue();
//	public static final Item SWEETROLL_RAW = Utils.nullValue();
//	public static final Item ECLAIR_RAW = Utils.nullValue();
//	public static final Item CAKE_RAW = Utils.nullValue();
//	public static final Item CAKE_SIMPLE_RAW = Utils.nullValue();
//	public static final Item CAKE_CARROT_RAW = Utils.nullValue();
//	public static final Item CAKE_CHOC_RAW = Utils.nullValue();
//	public static final Item CAKE_BF_RAW = Utils.nullValue();
//	public static final Item CAKE_UNICED = Utils.nullValue();
//	public static final Item CAKE_SIMPLE_UNICED = Utils.nullValue();
//	public static final Item CAKE_CARROT_UNICED = Utils.nullValue();
//	public static final Item CAKE_CHOC_UNICED = Utils.nullValue();
//	public static final Item CAKE_BF_UNICED = Utils.nullValue();
//	public static final Item PIE_MEAT_UNCOOKED = Utils.nullValue();
//	public static final Item PIE_APPLE_UNCOOKED = Utils.nullValue();
//	public static final Item PIE_BERRY_UNCOOKED = Utils.nullValue();
//	public static final Item PIE_SHEPARD_UNCOOKED = Utils.nullValue();
//	public static final Item PIE_PUMPKIN_UNCOOKED = Utils.nullValue();
//	public static final Item PIE_MEAT_COOKED = Utils.nullValue();
//	public static final Item PIE_APPLE_COOKED = Utils.nullValue();
//	public static final Item PIE_BERRY_COOKED = Utils.nullValue();
//	public static final Item PIE_SHEPARD_COOKED = Utils.nullValue();
//	public static final Item PIE_PUMPKIN_COOKED = Utils.nullValue();
//	public static final Item SALT = Utils.nullValue();
//	public static final Item SUGAR_POT = Utils.nullValue();
//	public static final Item BOWL_WATER_SALT = Utils.nullValue();
//	public static final Item DOUGH = Utils.nullValue();
//	public static final Item PASTRY = Utils.nullValue();
//	public static final Item RAW_BREAD = Utils.nullValue();
//	public static final Item JUG_UNCOOKED = Utils.nullValue();
//	public static final Item JUG_EMPTY = Utils.nullValue();
//	public static final Item JUG_WATER = Utils.nullValue();
//	public static final Item JUG_MILK = Utils.nullValue();
//	public static final Item BURNT_FOOD = Utils.nullValue();
//	public static final Item BURNT_POT = Utils.nullValue();
//	public static final Item BURNT_PIE = Utils.nullValue();
//
//	// SPECIAL RECIPES
//	public static final Item BURNT_CAKE = Utils.nullValue();
//
//	//ArmourListMFR
//	public static final String[] leathermats = new String[]{"hide", "rough_leather", "strong_leather", "stud_leather", "padded",};
//	public static ArmourMaterialMFR LEATHER_MAT;
//	public static ArmourMaterialMFR APRON;
//	public static ItemArmourMFR[] LEATHER;
//	public static ItemArmourMFR LEATHER_APRON;
//
//	//CustomToolListMFR
//	// STANDARD
//	public static final ItemWeaponMFR STANDARD_SWORD = Utils.nullValue();
//	public static final ItemWeaponMFR STANDARD_WARAXE = Utils.nullValue();
//	public static final ItemWeaponMFR STANDARD_MACE = Utils.nullValue();
//	public static final ItemWeaponMFR STANDARD_DAGGER = Utils.nullValue();
//	public static final ItemWeaponMFR STANDARD_SPEAR = Utils.nullValue();
//	public static final ItemWeaponMFR STANDARD_GREATSWORD = Utils.nullValue();
//	public static final ItemWeaponMFR STANDARD_BATTLEAXE = Utils.nullValue();
//	public static final ItemWeaponMFR STANDARD_WARHAMMER = Utils.nullValue();
//	public static final ItemWeaponMFR STANDARD_KATANA = Utils.nullValue();
//	public static final ItemWeaponMFR STANDARD_HALBEARD = Utils.nullValue();
//	public static final ItemWeaponMFR STANDARD_LANCE = Utils.nullValue();
//	public static final ItemPickMF STANDARD_PICK = Utils.nullValue();
//	public static final ItemAxe STANDARD_AXE = Utils.nullValue();
//	public static final ItemSpadeMF STANDARD_SPADE = Utils.nullValue();
//	public static final ItemHoeMF STANDARD_HOE = Utils.nullValue();
//	public static final ItemHvyPick STANDARD_HVYPICK = Utils.nullValue();
//	public static final ItemHvyShovel STANDARD_HVYSHOVEL = Utils.nullValue();
//	public static final ItemHandpick STANDARD_HANDPICK = Utils.nullValue();
//	public static final ItemTrowMF STANDARD_TROW = Utils.nullValue();
//	public static final ItemScythe STANDARD_SCYTHE = Utils.nullValue();
//	public static final ItemMattock STANDARD_MATTOCK = Utils.nullValue();
//	public static final ItemLumberAxe STANDARD_LUMBER = Utils.nullValue();
//
//	public static final ItemHammer STANDARD_HAMMER = Utils.nullValue();
//	public static final ItemHammer STANDARD_HVYHAMMER = Utils.nullValue();
//	public static final ItemTongs STANDARD_TONGS = Utils.nullValue();
//	public static final ItemShears STANDARD_SHEARS = Utils.nullValue();
//	public static final ItemKnifeMFR STANDARD_KNIFE = Utils.nullValue();
//	public static final ItemNeedle STANDARD_NEEDLE = Utils.nullValue();
//	public static final ItemSaw STANDARD_SAW = Utils.nullValue();
//	public static final ItemBasicCraftTool STANDARD_SPOON = Utils.nullValue();
//	public static final ItemBasicCraftTool STANDARD_MALLET = Utils.nullValue();
//	public static final ItemSpanner STANDARD_SPANNER = Utils.nullValue();
//
//	public static final ItemBowMFR STANDARD_BOW = Utils.nullValue();
//	public static final ItemArrowMFR STANDARD_ARROW = Utils.nullValue();
//	public static final ItemArrowMFR STANDARD_BOLT = Utils.nullValue();
//	public static final ItemArrowMFR STANDARD_ARROW_BODKIN = Utils.nullValue();
//	public static final ItemArrowMFR STANDARD_ARROW_BROAD = Utils.nullValue();
//
//	//CustomArmourListMFR
//	public static final ItemArmourMFR STANDARD_SCALE_HELMET = Utils.nullValue();
//	public static final ItemArmourMFR STANDARD_SCALE_CHEST = Utils.nullValue();
//	public static final ItemArmourMFR STANDARD_SCALE_LEGS = Utils.nullValue();
//	public static final ItemArmourMFR STANDARD_SCALE_BOOTS = Utils.nullValue();
//
//	public static final ItemArmourMFR STANDARD_CHAIN_HELMET = Utils.nullValue();
//	public static final ItemArmourMFR STANDARD_CHAIN_CHEST = Utils.nullValue();
//	public static final ItemArmourMFR STANDARD_CHAIN_LEGS = Utils.nullValue();
//	public static final ItemArmourMFR STANDARD_CHAIN_BOOTS = Utils.nullValue();
//
//	public static final ItemArmourMFR STANDARD_SPLINT_HELMET = Utils.nullValue();
//	public static final ItemArmourMFR STANDARD_SPLINT_CHEST = Utils.nullValue();
//	public static final ItemArmourMFR STANDARD_SPLINT_LEGS = Utils.nullValue();
//	public static final ItemArmourMFR STANDARD_SPLINT_BOOTS = Utils.nullValue();
//
//	public static final ItemArmourMFR STANDARD_PLATE_HELMET = Utils.nullValue();
//	public static final ItemArmourMFR STANDARD_PLATE_CHEST = Utils.nullValue();
//	public static final ItemArmourMFR STANDARD_PLATE_LEGS = Utils.nullValue();
//	public static final ItemArmourMFR STANDARD_PLATE_BOOTS = Utils.nullValue();
//
//	//public static ItemHorseArmorMF standard_plate_horse_armor;
//
//	//DragonforgedStyle
//	public static Item.ToolMaterial DRAGONFORGED = EnumHelper.addToolMaterial("dragonforged", 2, 250, 6.0F, 2.0F, 15);
//
//	public static final ItemWeaponMFR DRAGONFORGED_SWORD = Utils.nullValue();
//	public static final ItemWeaponMFR DRAGONFORGED_WARAXE = Utils.nullValue();
//	public static final ItemWeaponMFR DRAGONFORGED_MACE = Utils.nullValue();
//	public static final ItemWeaponMFR DRAGONFORGED_DAGGER = Utils.nullValue();
//	public static final ItemWeaponMFR DRAGONFORGED_SPEAR = Utils.nullValue();
//	public static final ItemWeaponMFR DRAGONFORGED_GREATSWORD = Utils.nullValue();
//	public static final ItemWeaponMFR DRAGONFORGED_BATTLEAXE = Utils.nullValue();
//	public static final ItemWeaponMFR DRAGONFORGED_WARHAMMER = Utils.nullValue();
//	public static final ItemWeaponMFR DRAGONFORGED_KATANA = Utils.nullValue();
//	public static final ItemWeaponMFR DRAGONFORGED_HALBEARD = Utils.nullValue();
//	public static final ItemWeaponMFR DRAGONFORGED_LANCE = Utils.nullValue();
//	public static final ItemPickMF DRAGONFORGED_PICK = Utils.nullValue();
//	public static final ItemAxe DRAGONFORGED_AXE = Utils.nullValue();
//	public static final ItemSpadeMF DRAGONFORGED_SPADE = Utils.nullValue();
//	public static final ItemHoeMF DRAGONFORGED_HOE = Utils.nullValue();
//	public static final ItemHvyPick DRAGONFORGED_HVYPICK = Utils.nullValue();
//	public static final ItemHvyShovel DRAGONFORGED_HVYSHOVEL = Utils.nullValue();
//	public static final ItemHandpick DRAGONFORGED_HANDPICK = Utils.nullValue();
//	public static final ItemTrowMF DRAGONFORGED_TROW = Utils.nullValue();
//	public static final ItemScythe DRAGONFORGED_SCYTHE = Utils.nullValue();
//	public static final ItemMattock DRAGONFORGED_MATTOCK = Utils.nullValue();
//	public static final ItemLumberAxe DRAGONFORGED_LUMBER = Utils.nullValue();
//
//	public static final ItemHammer DRAGONFORGED_HAMMER = Utils.nullValue();
//	public static final ItemHammer DRAGONFORGED_HVYHAMMER = Utils.nullValue();
//	public static final ItemTongs DRAGONFORGED_TONGS = Utils.nullValue();
//	public static final ItemShears DRAGONFORGED_SHEARS = Utils.nullValue();
//	public static final ItemKnifeMFR DRAGONFORGED_KNIFE = Utils.nullValue();
//	public static final ItemNeedle DRAGONFORGED_NEEDLE = Utils.nullValue();
//	public static final ItemSaw DRAGONFORGED_SAW = Utils.nullValue();
//	public static final ItemSpanner DRAGONFORGED_SPANNER = Utils.nullValue();
//
//	public static final ItemBowMFR DRAGONFORGED_BOW = Utils.nullValue();
//
//	public static final ItemCustomArmour DRAGONFORGED_SCALE_HELMET = Utils.nullValue();
//	public static final ItemCustomArmour DRAGONFORGED_SCALE_CHEST = Utils.nullValue();
//	public static final ItemCustomArmour DRAGONFORGED_SCALE_LEGS = Utils.nullValue();
//	public static final ItemCustomArmour DRAGONFORGED_SCALE_BOOTS = Utils.nullValue();
//	public static final ItemCustomArmour DRAGONFORGED_CHAIN_HELMET = Utils.nullValue();
//	public static final ItemCustomArmour DRAGONFORGED_CHAIN_CHEST = Utils.nullValue();
//	public static final ItemCustomArmour DRAGONFORGED_CHAIN_LEGS = Utils.nullValue();
//	public static final ItemCustomArmour DRAGONFORGED_CHAIN_BOOTS = Utils.nullValue();
//	public static final ItemCustomArmour DRAGONFORGED_SPLINT_HELMET = Utils.nullValue();
//	public static final ItemCustomArmour DRAGONFORGED_SPLINT_CHEST = Utils.nullValue();
//	public static final ItemCustomArmour DRAGONFORGED_SPLINT_LEGS = Utils.nullValue();
//	public static final ItemCustomArmour DRAGONFORGED_SPLINT_BOOTS = Utils.nullValue();
//	public static final ItemCustomArmour DRAGONFORGED_PLATE_HELMET = Utils.nullValue();
//	public static final ItemCustomArmour DRAGONFORGED_PLATE_CHEST = Utils.nullValue();
//	public static final ItemCustomArmour DRAGONFORGED_PLATE_LEGS = Utils.nullValue();
//	public static final ItemCustomArmour DRAGONFORGED_PLATE_BOOTS = Utils.nullValue();
//
//	//OrnateStyle
//	public static Item.ToolMaterial ORNATE = EnumHelper.addToolMaterial("ornate", 2, 250, 6.0F, 2.0F, 100);
//
//	public static final ItemWeaponMFR ORNATE_SWORD = Utils.nullValue();
//	public static final ItemWeaponMFR ORNATE_WARAXE = Utils.nullValue();
//	public static final ItemWeaponMFR ORNATE_MACE = Utils.nullValue();
//	public static final ItemWeaponMFR ORNATE_DAGGER = Utils.nullValue();
//	public static final ItemWeaponMFR ORNATE_SPEAR = Utils.nullValue();
//	public static final ItemWeaponMFR ORNATE_GREATSWORD = Utils.nullValue();
//	public static final ItemWeaponMFR ORNATE_BATTLEAXE = Utils.nullValue();
//	public static final ItemWeaponMFR ORNATE_WARHAMMER = Utils.nullValue();
//	public static final ItemWeaponMFR ORNATE_KATANA = Utils.nullValue();
//	public static final ItemWeaponMFR ORNATE_HALBEARD = Utils.nullValue();
//	public static final ItemWeaponMFR ORNATE_LANCE = Utils.nullValue();
//	public static final ItemPickMF ORNATE_PICK = Utils.nullValue();
//	public static final ItemAxe ORNATE_AXE = Utils.nullValue();
//	public static final ItemSpadeMF ORNATE_SPADE = Utils.nullValue();
//	public static final ItemHoeMF ORNATE_HOE = Utils.nullValue();
//	public static final ItemHvyPick ORNATE_HVYPICK = Utils.nullValue();
//	public static final ItemHvyShovel ORNATE_HVYSHOVEL = Utils.nullValue();
//	public static final ItemHandpick ORNATE_HANDPICK = Utils.nullValue();
//	public static final ItemTrowMF ORNATE_TROW = Utils.nullValue();
//	public static final ItemScythe ORNATE_SCYTHE = Utils.nullValue();
//	public static final ItemMattock ORNATE_MATTOCK = Utils.nullValue();
//	public static final ItemLumberAxe ORNATE_LUMBER = Utils.nullValue();
//
//	public static final ItemHammer ORNATE_HAMMER = Utils.nullValue();
//	public static final ItemHammer ORNATE_HVYHAMMER = Utils.nullValue();
//	public static final ItemTongs ORNATE_TONGS = Utils.nullValue();
//	public static final ItemShears ORNATE_SHEARS = Utils.nullValue();
//	public static final ItemKnifeMFR ORNATE_KNIFE = Utils.nullValue();
//	public static final ItemNeedle ORNATE_NEEDLE = Utils.nullValue();
//	public static final ItemSaw ORNATE_SAW = Utils.nullValue();
//	public static final ItemSpanner ORNATE_SPANNER = Utils.nullValue();
//
//	public static final ItemBowMFR ORNATE_BOW = Utils.nullValue();
//
//	public static final ItemCustomArmour ORNATE_SCALE_HELMET = Utils.nullValue();
//	public static final ItemCustomArmour ORNATE_SCALE_CHEST = Utils.nullValue();
//	public static final ItemCustomArmour ORNATE_SCALE_LEGS = Utils.nullValue();
//	public static final ItemCustomArmour ORNATE_SCALE_BOOTS = Utils.nullValue();
//	public static final ItemCustomArmour ORNATE_CHAIN_HELMET = Utils.nullValue();
//	public static final ItemCustomArmour ORNATE_CHAIN_CHEST = Utils.nullValue();
//	public static final ItemCustomArmour ORNATE_CHAIN_LEGS = Utils.nullValue();
//	public static final ItemCustomArmour ORNATE_CHAIN_BOOTS = Utils.nullValue();
//	public static final ItemCustomArmour ORNATE_SPLINT_HELMET = Utils.nullValue();
//	public static final ItemCustomArmour ORNATE_SPLINT_CHEST = Utils.nullValue();
//	public static final ItemCustomArmour ORNATE_SPLINT_LEGS = Utils.nullValue();
//	public static final ItemCustomArmour ORNATE_SPLINT_BOOTS = Utils.nullValue();
//	public static final ItemCustomArmour ORNATE_PLATE_HELMET = Utils.nullValue();
//	public static final ItemCustomArmour ORNATE_PLATE_CHEST = Utils.nullValue();
//	public static final ItemCustomArmour ORNATE_PLATE_LEGS = Utils.nullValue();
//	public static final ItemCustomArmour ORNATE_PLATE_BOOTS = Utils.nullValue();
//
//	@SubscribeEvent
//	public static void register(RegistryEvent.Register<Item> event) {
//		IForgeRegistry<Item> registry = event.getRegistry();
//
//		//ComponentListMFR
//		registry.register(new ItemBowl("clay_pot").setStoragePlacement("pot", "pot"));
//		registry.register(new ItemComponentMFR("clay_pot_uncooked", 0));
//		registry.register(new ItemComponentMFR("ingot_mould").setStoragePlacement("bar", "mould"));
//		registry.register(new ItemComponentMFR("ingot_mould_uncooked", 0));
//		registry.register(new ItemComponentMFR("pie_tray_uncooked", 0));
//
//		registry.register(new ItemComponentMFR("copper_ingot", BaseMaterialMFR.getMaterial("copper").rarity));
//		registry.register(new ItemComponentMFR("tin_ingot", BaseMaterialMFR.getMaterial("tin").rarity));
//		registry.register(new ItemComponentMFR("bronze_ingot", BaseMaterialMFR.getMaterial("bronze").rarity));
//		registry.register(new ItemComponentMFR("pig_iron_ingot", BaseMaterialMFR.getMaterial("pig_iron").rarity));
//		registry.register(new ItemComponentMFR("steel_ingot", BaseMaterialMFR.getMaterial("steel").rarity));
//		registry.register(new ItemComponentMFR("encrusted_ingot", BaseMaterialMFR.getMaterial("encrusted").rarity));
//		registry.register(new ItemComponentMFR("black_steel_weak_ingot", BaseMaterialMFR.getMaterial("weak_black_steel").rarity));
//		registry.register(new ItemComponentMFR("black_steel_ingot", BaseMaterialMFR.getMaterial("black_steel").rarity));
//		registry.register(new ItemComponentMFR("silver_ingot", BaseMaterialMFR.getMaterial("silver").rarity));
//		registry.register(new ItemComponentMFR("red_steel_weak_ingot", BaseMaterialMFR.getMaterial("weak_red_steel").rarity));
//		registry.register(new ItemComponentMFR("red_steel_ingot", BaseMaterialMFR.getMaterial("red_steel").rarity));
//		registry.register(new ItemComponentMFR("blue_steel_weak_ingot", BaseMaterialMFR.getMaterial("weak_blue_steel").rarity));
//		registry.register(new ItemComponentMFR("blue_steel_ingot", BaseMaterialMFR.getMaterial("blue_steel").rarity));
//		registry.register(new ItemComponentMFR("adamantium_ingot", BaseMaterialMFR.getMaterial("adamantium").rarity));
//		registry.register(new ItemComponentMFR("mithril_ingot", BaseMaterialMFR.getMaterial("mithril").rarity));
//		registry.register(new ItemComponentMFR("ignotumite_ingot", BaseMaterialMFR.getMaterial("ignotumite").rarity));
//		registry.register(new ItemComponentMFR("mithium_ingot", BaseMaterialMFR.getMaterial("mithium").rarity));
//		registry.register(new ItemComponentMFR("ender_ingot", BaseMaterialMFR.getMaterial("enderforge").rarity));
//		registry.register(new ItemComponentMFR("tungsten_ingot", BaseMaterialMFR.getMaterial("tungsten").rarity));
//		registry.register(new ItemComponentMFR("obsidian_ingot", BaseMaterialMFR.getMaterial("obsidian").rarity));
//
//		registry.register(new ItemComponentMFR("ingot_composite_alloy", 1));
//
//		registry.register(new ItemComponentMFR("plank").setCustom(1, "wood").setStoragePlacement("plank","plank"));
//		registry.register(new ItemComponentMFR("vine", -1));
//		registry.register(new ItemComponentMFR("sharp_rock", -1));
//
//		registry.register(new ItemComponentMFR("flux", 0));
//		registry.register(new ItemComponentMFR("flux_strong", 0));
//
//		registry.register(new ItemComponentMFR("coal_dust", 0).setContainerItem(CLAY_POT));
//		registry.register(new ItemComponentMFR("nitre", 0));
//		registry.register(new ItemComponentMFR("sulfur", 0));
//		registry.register(new ItemComponentMFR("iron_prep", 0));
//		registry.register(new ItemBombComponent("blackpowder", 0, "powder", 0).setContainerItem(CLAY_POT));
//		registry.register(new ItemBombComponent("blackpowder_advanced", 1, "powder", 1).setContainerItem(CLAY_POT));
//		registry.register(new ItemComponentMFR("fletching", 0));
//		registry.register(new ItemBombComponent("shrapnel", 0, "filling", 1).setContainerItem(ComponentListMFR.CLAY_POT));
//		registry.register(new ItemBombComponent("magma_cream_refined", 1, "filling", 2).setContainerItem(CLAY_POT));
//		registry.register(new ItemBombComponent("bomb_fuse", 0, "fuse", 0));
//		registry.register(new ItemBombComponent("bomb_fuse_long", 0, "fuse", 1));
//		registry.register(new ItemComponentMFR("bomb_casing_uncooked", 0));
//		registry.register(new ItemBombComponent("bomb_casing", 0, "bombcase", 0));
//		registry.register(new ItemComponentMFR("mine_casing_uncooked", 0));
//		registry.register(new ItemBombComponent("mine_casing", 0, "minecase", 0));
//		registry.register(new ItemBombComponent("bomb_casing_iron", 0, "bombcase", 1));
//		registry.register(new ItemBombComponent("mine_casing_iron", 0, "minecase", 1));
//		registry.register(new ItemBombComponent("bomb_casing_obsidian", 1, "bombcase", 2));
//		registry.register(new ItemBombComponent("mine_casing_obsidian", 1, "minecase", 2));
//		registry.register(new ItemBombComponent("bomb_casing_crystal", 1, "bombcase", 3));
//		registry.register(new ItemBombComponent("mine_casing_crystal", 1, "minecase", 3));
//		registry.register(new ItemBombComponent("bomb_casing_arrow", 1, "arrow", 0));
//		registry.register(new ItemBombComponent("bomb_casing_bolt", 1, "bolt", 0));
//
//		registry.register(new ItemComponentMFR("coke", 1));
//		registry.register(new ItemComponentMFR("diamond_shards", 0));
//
//		registry.register(new ItemComponentMFR("clay_brick", 0));
//		registry.register(new ItemComponentMFR("kaolinite", 0));
//		registry.register(new ItemComponentMFR("kaolinite_dust", 0).setContainerItem(CLAY_POT));
//		registry.register(new ItemComponentMFR("fireclay", 0));
//		registry.register(new ItemComponentMFR("fireclay_brick", 0));
//		registry.register(new ItemComponentMFR("strong_brick", 0).setStoragePlacement("bar", "firebrick"));
//
//		registry.register(new ItemComponentMFR("hide_small", 0));
//		registry.register(new ItemComponentMFR("hide_medium", 0));
//		registry.register(new ItemComponentMFR("hide_large", 0));
//		registry.register(new ItemHide("rawhide_small", HIDE_SMALL, 1.0F));
//		registry.register(new ItemHide("rawhide_medium", HIDE_MEDIUM, 1.5F));
//		registry.register(new ItemHide("rawhide_large", HIDE_LARGE, 3.0F));
//
//		registry.register(new ItemSpecialDesign("dragon_heart", 1, "dragon"));
//
//		registry.register(new ItemComponentMFR("leather_strip", 0));
//		registry.register(new ItemComponentMFR("nail", 0));
//		registry.register(new ItemComponentMFR("rivet", 0));
//		registry.register(new ItemComponentMFR("thread", 0));
//		registry.register(new ItemComponentMFR("obsidian_rock", 0));
//
//		registry.register(new ItemRawOre("ore_copper", -1));
//		registry.register(new ItemRawOre("ore_tin", -1));
//		registry.register(new ItemRawOre("ore_iron", 0));
//		registry.register(new ItemRawOre("ore_silver", 0));
//		registry.register(new ItemRawOre("ore_gold", 0));
//		registry.register(new ItemRawOre("ore_tungsten", 1));
//
//		registry.register(new ItemHeated());
//
//		registry.register(new ItemComponentMFR("plant_oil", 0).setStoragePlacement("jug", "jugoil").setContainerItem(FoodListMFR.JUG_EMPTY));
//
//		registry.register(new ItemComponentMFR("talisman_lesser", 1));
//		registry.register(new ItemComponentMFR("talisman_greater", 3));
//
//		registry.register(new ItemComponentMFR("bolt", 0));
//		registry.register(new ItemComponentMFR("iron_frame", 0));
//		registry.register(new ItemComponentMFR("iron_strut", 0));
//		registry.register(new ItemComponentMFR("bronze_gears", 0));
//		registry.register(new ItemComponentMFR("tungsten_gears", 1));
//		registry.register(new ItemComponentMFR("steel_tube", 0));
//		registry.register(new ItemComponentMFR("cogwork_shaft", 1));
//
//		registry.register(new ItemComponentMFR("coal_prep", 0));
//
//		registry.register(new ItemFilledMould());
//
//		registry.register(new ItemCrossbowPart("crossbow_stock_wood", "stock").addSpeed(1.0F).addRecoil(0F));
//		registry.register(new ItemCrossbowPart("crossbow_stock_iron", "stock").addSpeed(1.0F).addRecoil(-2F).addDurability(150));
//		registry.register(new ItemCrossbowPart("crossbow_handle_wood", "stock").addSpeed(0.5F).addRecoil(2F).addSpread(1.0F).setHandCrossbow(true));
//
//		registry.register(new ItemCrossbowPart("cross_arms_basic", "mechanism").addPower(1.00F).addSpeed(0.50F).addRecoil(4F).addSpread(1.00F));
//		registry.register(new ItemCrossbowPart("cross_arms_light", "mechanism").addPower(0.85F).addSpeed(0.25F).addRecoil(2F).addSpread(0.50F));
//		registry.register(new ItemCrossbowPart("cross_arms_heavy", "mechanism").addPower(1.15F).addSpeed(1.00F).addRecoil(8F).addSpread(2.00F));
//		registry.register(new ItemCrossbowPart("cross_arms_advanced", "mechanism").addPower(1.15F).addSpeed(1.00F).addRecoil(6F).addSpread(0.25F).addDurability(150));
//
//		registry.register(new ItemCrossbowPart("cross_bayonet", "muzzle").addBash(4.0F).addRecoil(-1F).addSpeed(0.5F));
//		registry.register(new ItemCrossbowPart("cross_ammo", "mod").addCapacity(5).addSpread(2.00F));
//		registry.register(new ItemCrossbowPart("cross_scope", "mod").setScope(0.75F));
//
//		registry.register(new ItemCustomComponent("chain_mesh", 1F, "metal").setStoragePlacement("sheet", "mail"));
//		registry.register(new ItemCustomComponent("scale_mesh", 1F, "metal").setStoragePlacement("sheet", "scale"));
//		registry.register(new ItemCustomComponent("splint_mesh", 1F, "metal").setStoragePlacement("sheet", "splint"));
//		registry.register(new ItemCustomComponent("plate", 2F, "metal").setStoragePlacement("sheet", "plate"));
//		registry.register(new ItemCustomComponent("plate_huge", 8F, "metal").setStoragePlacement("bigplate", "bigplate"));
//		registry.register(new ItemCustomComponent("metal_hunk", 0.25F, "metal"));
//		registry.register(new ItemCustomComponent("arrowhead", 1 / 4F, "metal"));
//		registry.register(new ItemCustomComponent("bodkin_head", 1 / 4F, "metal"));
//		registry.register(new ItemCustomComponent("broad_head", 1 / 4F, "metal"));
//		registry.register(new ItemCustomComponent("cogwork_armour", 30F, "metal").setCanDamage().setCreativeTab(CreativeTabMFR.tabGadget).setMaxStackSize(1));
//		registry.register(new ItemCustomComponent("bar", 1F, "metal").setStoragePlacement("bar", "bar").setCreativeTab(CreativeTabMFR.tabMaterialsMFR));
//
//		registry.register(new ItemComponentMFR("flux_pot", 0).setContainerItem(CLAY_POT));
//		registry.register(new ItemComponentMFR("coal_flux", 0));
//
//		registry.register(new ItemComponentMFR("copper_coin", 0));
//		registry.register(new ItemComponentMFR("silver_coin", 0));
//		registry.register(new ItemComponentMFR("gold_coin", 0));
//
//		registry.register(new ItemComponentMFR("hinge", 0));
//		registry.register(new ItemComponentMFR("plank_cut").setCustom(1, "wood").setStoragePlacement("plank", "plankcut"));
//		registry.register(new ItemComponentMFR("plank_pane").setCustom(6, "wood").setStoragePlacement("sheet", "woodpane"));
//
//		registry.register(new ItemComponentMFR("cogwork_pulley", 1).setCreativeTab(CreativeTabMFR.tabGadget));
//
//		registry.register(new ItemArtefact("artefacts"));
//
//		registry.register(new ItemSpecialDesign("ornate_items", 1, "ornate"));
//
//		//ToolListMFR
//		registry.register(new ItemSword("training_sword", Item.ToolMaterial.WOOD, -1, 0.8F));
//		registry.register(new ItemWaraxe("training_waraxe", Item.ToolMaterial.WOOD, -1, 0.8F));
//		registry.register(new ItemMace("training_mace", Item.ToolMaterial.WOOD, -1, 0.8F));
//		registry.register(new ItemSpear("training_spear", Item.ToolMaterial.WOOD, -1, 0.8F));
//
//		registry.register(new ItemKnifeMFR("stone_knife", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 3.5F, 0));
//		registry.register(new ItemHammer("stone_hammer", BaseMaterialMFR.getMaterial("stone").getToolConversion(), false, -1, 0));
//		registry.register(new ItemTongs("stone_tongs", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1));
//		registry.register(new ItemNeedle("bone_needle", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 0));
//		registry.register(new ItemPickMF("stone_pick", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1));
//		registry.register(new ItemAxe("stone_axe", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1));
//		registry.register(new ItemSpadeMF("stone_spade", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1));
//		registry.register(new ItemHoeMF("stone_hoe", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1));
//		registry.register(new ItemSword("stone_sword", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F));
//		registry.register(new ItemMace("stone_mace", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F));
//		registry.register(new ItemWaraxe("stone_waraxe", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F));
//		registry.register(new ItemSpear("stone_spear", BaseMaterialMFR.getMaterial("stone").getToolConversion(), -1, 2.0F));
//
//		registry.register(new ItemBandage("bandage_crude", 5F));
//		registry.register(new ItemBandage("bandage_wool", 8F));
//		registry.register(new ItemBandage("bandage_tough", 12F));
//
//		registry.register(new ItemCrudeBomb("bomb_crude"));
//		registry.register(new ItemBomb("bomb_basic"));
//		registry.register(new ItemMine("mine_basic"));
//
//		registry.register(new ItemResearchBook());
//
//		registry.register(new ItemLighterMF("dryrocks", 0.1F, 16));
//		registry.register(new ItemLighterMF("tinderbox", 0.5F, 100));
//
//		registry.register(new ItemSkillBook("skillbook_artisanry", SkillList.artisanry));
//		registry.register(new ItemSkillBook("skillbook_construction", SkillList.construction));
//		registry.register(new ItemSkillBook("skillbook_provisioning", SkillList.provisioning));
//		registry.register(new ItemSkillBook("skillbook_engineering", SkillList.engineering));
//		registry.register(new ItemSkillBook("skillbook_combat", SkillList.combat));
//
//		registry.register(new ItemSkillBook("skillbook_artisanry_max", SkillList.artisanry).setMax());
//		registry.register(new ItemSkillBook("skillbook_construction_max", SkillList.construction).setMax());
//		registry.register(new ItemSkillBook("skillbook_provisioning_max", SkillList.provisioning).setMax());
//		registry.register(new ItemSkillBook("skillbook_engineering_max", SkillList.engineering).setMax());
//		registry.register(new ItemSkillBook("skillbook_combat2", SkillList.combat).setMax());
//
//		registry.register(new ItemEAnvilTools("engin_anvil_tools", 64));
//
//		registry.register(new ItemExplodingArrow());
//		registry.register(new ItemSpyglass());
//		registry.register(new ItemClimbingPick("climbing_pick_basic", Item.ToolMaterial.IRON, 0));
//		registry.register(new ItemParachute());
//
//		registry.register(new ItemSyringe());
//		registry.register(new ItemComponentMFR("syringe_empty").setCreativeTab(CreativeTabMFR.tabGadget));
//
//		registry.register(new ItemLootSack("loot_sack", 8, 0));
//		registry.register(new ItemLootSack("loot_sack_uc", 8, 1));
//		registry.register(new ItemLootSack("loot_sack_rare", 12, 2));
//
//		registry.register(new ItemCrossbow());
//		registry.register(new ItemExplodingBolt());
//
//		registry.register(new ItemPaintBrush("paint_brush", 256));
//
//		registry.register(new ItemWorldGenPlacer());
//		registry.register(new MobSpawnerMF());
//
//		//FoodListMFR
//		// MORSELS
//		registry.register(new ItemFoodMF("wolf_raw", 2, 0.2F, true));
//		registry.register(new ItemFoodMF("wolf_cooked", 6, 0.6F, true));
//		registry.register(new ItemFoodMF("horse_raw", 4, 0.4F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER,500,0), 0.5F));
//		registry.register(new ItemFoodMF("horse_cooked", 10, 1.0F, true));
//		registry.register(new ItemFoodMF("generic_meat_uncooked", 2, 0.2F, true));
//		registry.register(new ItemFoodMF("generic_meat_cooked", 5, 0.5F, true));
//		registry.register(new ItemFoodMF("generic_meat_strip_uncooked", 2, 0.2F, true));
//		registry.register(new ItemFoodMF("generic_meat_strip_cooked", 5, 0.5F, true));
//		registry.register(new ItemFoodMF("generic_meat_chunk_uncooked", 2, 0.2F, true));
//		registry.register(new ItemFoodMF("generic_meat_chunk_cooked", 5, 0.5F, true));
//		registry.register(new ItemFoodMF("generic_meat_mince_uncooked", 2, 0.2F, true).setContainerItem(ComponentListMFR.CLAY_POT));
//		registry.register(new ItemFoodMF("generic_meat_mince_cooked", 5, 0.5F, true).setContainerItem(ComponentListMFR.CLAY_POT));
//		registry.register(new ItemComponentMFR("flour", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
//		registry.register(new ItemComponentMFR("breadcrumbs", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
//		registry.register(new ItemComponentMFR("guts", 0).setCreativeTab(CreativeTabMFR.tabFood));
//		registry.register(new ItemFoodMF("breadroll", 5, 1.0F, false));
//		registry.register(new ItemFoodMF("bread_slice", 2, 1.0F, false));
//		registry.register(new ItemUnfinishedFood("curds"));
//		registry.register(new ItemUnfinishedFood("cheese_pot").setContainerItem(ComponentListMFR.CLAY_POT));
//		registry.register(new ItemFoodMF("cheese_slice", 4, 1.0F, false, 0).setFoodStats(1, 0.0F, 0.0F, 1.0F));
//
//		// T1 (basic mixing)
//		// Util: Roast, Prep Block (Stone-Bronze Age)
//		registry.register(new ItemFoodMF("stew", 5, 1.0F, false, 0).setFoodStats(1, 0.0F, 0.0F, 1.0F).setReturnItem(Items.BOWL).setMaxStackSize(1));
//		registry.register(new ItemFoodMF("oats", 5, 1.0F, false, 0).setFoodStats(1, 0.0F, 0.8F, 0.2F).setReturnItem(Items.BOWL).setMaxStackSize(1));
//
//		// T2 (Basic baking, stone oven, processed mixing)
//		// Util: Stone Oven, Prep Block (Bronze Age - Early Iron Age)
//		registry.register(new ItemFoodMF("cheese_roll", 6, 1.0F, false, 0).setFoodStats(2, 0.0F, 0.4F, 0.6F).setMaxStackSize(1));
//		registry.register(new ItemFoodMF("jerky", 6, 1.0F, true, 0).setFoodStats(2, 0.0F, 0.0F, 1.0F).setMaxStackSize(8));
//		registry.register(new ItemFoodMF("saussage_raw", 4, 1.0F, true, 0).setFoodStats(2, 0.0F, 0.1F, 0.6F).setMaxStackSize(16));
//		registry.register(new ItemFoodMF("saussage_cooked", 8, 1.0F, true, 0).setFoodStats(2, 0.0F, 0.2F, 0.8F).setMaxStackSize(16));
//		registry.register(new ItemFoodMF("sweetroll_uniced", 5, 1.0F, false, 0).setFoodStats(2, 0.5F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(64));
//
//		// TODO: fix item texture to change with metadata
//		registry.register(new ItemMultiFood("sweetroll", 2, 3, 1.0F, false, 0).setFoodStats(2, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible());
//		registry.register(new ItemMultiFood("sandwitch_meat", 2, 6, 1.0F, false, 0).setFoodStats(2, 0.0F, 0.5F, 0.5F).setUnlocalizedName("sandwitch"));
//		registry.register(new ItemMultiFood("sandwitch_big", 4, 6, 1.0F, false, 1).setFoodStats(2, 0.0F, 0.5F, 0.5F));
//
//		// T3 (Quality baking, metal oven)
//		// Util: Metal Oven, Prep Block, Steel Tools (Mid Iron Age)
//		registry.register(new ItemFoodMF("meatpie_slice", 8, 1.0F, false, 0).setFoodStats(3, 0.0F, 0.2F, 0.8F).setMaxStackSize(1));
//		registry.register(new ItemFoodMF("pieslice_apple", 5, 1.0F, false, 0).setFoodStats(3, 0.8F, 0.2F, 0.0F).setEatTime(16).setAlwaysEdible().setAlwaysEdible());
//		registry.register(new ItemFoodMF("pieslice_berry", 5, 1.0F, false, 0).setFoodStats(3, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setAlwaysEdible());
//
//		// T4 (Advanced baking, multiple processes, temperature regulation)
//		// Util : Metal Oven, Prep Block, Full tool set, Proper kitchen setup (Mid Iron
//		// Age)
//		registry.register(new ItemFoodMF("pieslice_shepards", 10, 1.0F, false, 1).setFoodStats(4, 0.0F, 0.5F, 0.5F));
//		registry.register(new ItemFoodMF("cake_slice", 3, 0.8F, false, 0).setFoodStats(3, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1));
//		registry.register(new ItemFoodMF("carrotcake_slice", 4, 0.8F, false, 0).setFoodStats(4, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1));
//
//		// T5 (Advanced baking, multiple process, temperature regulation, many
//		// ingreedients)
//		registry.register(new ItemFoodMF("choccake_slice", 4, 0.8F, false, 0).setFoodStats(4, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1));
//
//		// T6 (Perfeted meals, extremely difficylt to create)
//		registry.register(new ItemFoodMF("bfcake_slice", 6, 1.0F, false, 1).setFoodStats(5, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1));
//		registry.register(new ItemFoodMF("eclair_uniced", 5, 1.0F, false, 0).setFoodStats(5, 0.3F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(64));
//		registry.register(new ItemFoodMF("eclair_empty", 5, 1.0F, false, 0).setFoodStats(5, 0.5F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(64));
//
//		// TODO: fix item texture to change with metadata
//		registry.register(new ItemMultiFood("eclair", 4, 4, 1.0F, false, 1).setFoodStats(5, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1));
//
//		// MISC
//		registry.register(new ItemComponentMFR("cake_tin", 0).setCreativeTab(CreativeTabMFR.tabFood));
//		registry.register(new ItemComponentMFR("pie_tray", 0).setStoragePlacement("bigplate", "tray").setCreativeTab(CreativeTabMFR.tabFood));
//		registry.register(new ItemComponentMFR("icing", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
//		registry.register(new ItemComponentMFR("custard", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
//		registry.register(new ItemComponentMFR("coca_powder", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
//		registry.register(new ItemComponentMFR("chocolate", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
//		registry.register(new ItemComponentMFR("choc_chips", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
//		registry.register(new ItemFoodMF("berries", 2, 2.0F, false).setEatTime(10).setStaminaRestore(10F).setAlwaysEdible());
//		registry.register(new ItemFoodMF("berries_juicy", 3, 5.0F, false).setEatTime(10).setStaminaRestore(25F).setRarity(1).setAlwaysEdible());
//		registry.register(new ItemUnfinishedFood("sweetroll_raw").setMaxStackSize(64));
//		registry.register(new ItemUnfinishedFood("eclair_raw").setMaxStackSize(64));
//		registry.register(new ItemUnfinishedFood("cake_raw"));
//		registry.register(new ItemUnfinishedFood("cake_simple_raw"));
//		registry.register(new ItemUnfinishedFood("cake_carrot_raw"));
//		registry.register(new ItemUnfinishedFood("cake_choc_raw"));
//		registry.register(new ItemUnfinishedFood("cake_bf_raw"));
//		registry.register(new ItemUnfinishedFood("cake_uniced").setContainerItem(CAKE_TIN));
//		registry.register(new ItemUnfinishedFood("cake_simple_uniced").setContainerItem(CAKE_TIN));
//		registry.register(new ItemUnfinishedFood("cake_carrot_uniced").setContainerItem(CAKE_TIN));
//		registry.register(new ItemUnfinishedFood("cake_choc_uniced").setContainerItem(CAKE_TIN));
//		registry.register(new ItemUnfinishedFood("cake_bf_uniced").setContainerItem(CAKE_TIN));
//		registry.register(new ItemUnfinishedFood("pie_meat_uncooked"));
//		registry.register(new ItemUnfinishedFood("pie_apple_uncooked"));
//		registry.register(new ItemUnfinishedFood("pie_berry_uncooked"));
//		registry.register(new ItemUnfinishedFood("pie_shepard_uncooked"));
//		registry.register(new ItemUnfinishedFood("pie_pumpkin_uncooked"));
//		registry.register(new ItemUnfinishedFood("pie_meat_cooked").setContainerItem(PIE_TRAY));
//		registry.register(new ItemUnfinishedFood("pie_apple_cooked").setContainerItem(PIE_TRAY));
//		registry.register(new ItemUnfinishedFood("pie_berry_cooked").setContainerItem(PIE_TRAY));
//		registry.register(new ItemUnfinishedFood("pie_shepard_cooked").setContainerItem(PIE_TRAY));
//		registry.register(new ItemUnfinishedFood("pie_pumpkin_cooked").setContainerItem(PIE_TRAY));
//		registry.register(new ItemComponentMFR("salt", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
//		registry.register(new ItemComponentMFR("sugar_pot", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
//		registry.register(new ItemComponentMFR("bowl_water_salt", 0).setCreativeTab(CreativeTabMFR.tabFood));
//		registry.register(new ItemUnfinishedFood("dough").setMaxStackSize(64));
//		registry.register(new ItemUnfinishedFood("pastry").setMaxStackSize(64));
//		registry.register(new ItemUnfinishedFood("raw_bread").setMaxStackSize(64));
//		registry.register(new ItemJug("uncooked"));
//		registry.register(new ItemJug("empty").setStoragePlacement("jug", "jug"));
//		registry.register(new ItemJug("water").setStoragePlacement("jug", "jugwater").setContainerItem(JUG_EMPTY));
//		registry.register(new ItemJug("milk").setStoragePlacement("jug", "jugmilk").setContainerItem(JUG_EMPTY));
//		registry.register(new ItemBurntFood("burnt_food"));
//		registry.register(new ItemBurntFood("burnt_pot").setContainerItem(ComponentListMFR.CLAY_POT));
//		registry.register(new ItemBurntFood("burnt_pie").setContainerItem(PIE_TRAY));
//
//		// SPECIAL RECIPES
//		registry.register(new ItemBurntFood("burnt_cake").setContainerItem(CAKE_TIN));
//
//		//ArmourListMFR
//		registry.registerAll(LEATHER);
//		registry.register(LEATHER_APRON);
//
//		//CustomToolListMFR
//		String design = "standard";
//		CreativeTabs tab = CreativeTabMFR.tabWeapon;
//
//		// Standard Weapons
//		registry.register(new ItemDagger(design + "_dagger", Item.ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
//		registry.register(new ItemSword(design + "_sword", Item.ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
//		registry.register(new ItemWaraxe(design + "_waraxe", Item.ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
//		registry.register(new ItemMace(design + "_mace", Item.ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
//		registry.register(new ItemSpear(design + "_spear", Item.ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
//		registry.register(new ItemKatana(design + "_katana", Item.ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
//		registry.register(new ItemGreatsword(design + "_greatsword", Item.ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
//		registry.register(new ItemBattleaxe(design + "_battleaxe", Item.ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
//		registry.register(new ItemWarhammer(design + "_warhammer", Item.ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
//		registry.register(new ItemHalbeard(design + "_halbeard", Item.ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
//		registry.register(new ItemLance(design + "_lance", Item.ToolMaterial.IRON, 0, 1F).setCustom(design).setTab(tab));
//
//		tab = CreativeTabMFR.tabArcher;
//		registry.register(new ItemBowMFR(design + "_bow", EnumBowType.SHORTBOW).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemArrowMFR(design + "_bolt", ArrowType.BOLT, 20).setCustom(design).setAmmoType("bolt").setCreativeTab(tab));
//		registry.register(new ItemArrowMFR(design + "_arrow", ArrowType.NORMAL, 16).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemArrowMFR(design + "_arrow_bodkin", ArrowType.BODKIN, 16).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemArrowMFR(design + "_arrow_broad", ArrowType.BROADHEAD, 16).setCustom(design).setCreativeTab(tab));
//
//		// Standard Tools
//		tab = CreativeTabMFR.tabTool;
//		registry.register(new ItemPickMF(design + "_pick", Item.ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemAxe(design + "_axe", Item.ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemSpadeMF(design + "_spade", Item.ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemHoeMF(design + "_hoe", Item.ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
//
//		tab = CreativeTabMFR.tabToolAdvanced;
//		registry.register(new ItemHandpick(design + "_handpick", Item.ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemHvyPick(design + "_hvypick", Item.ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemTrowMF(design + "_trow", Item.ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemHvyShovel(design + "_hvyshovel", Item.ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemScythe(design + "_scythe", Item.ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemMattock(design + "_mattock", Item.ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemLumberAxe(design + "_lumber", Item.ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
//
//		// Standard Crafters
//		tab = CreativeTabMFR.tabCraftTool;
//		registry.register(new ItemHammer(design + "_hammer", Item.ToolMaterial.IRON, false, 0, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemHammer(design + "_hvyhammer", Item.ToolMaterial.IRON, true, 0, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemShears(design + "_shears", Item.ToolMaterial.IRON, 0, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemKnifeMFR(design + "_knife", Item.ToolMaterial.IRON, 0, 1F, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemNeedle(design + "_needle", Item.ToolMaterial.IRON, 0, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemSaw(design + "_saw", Item.ToolMaterial.IRON, 0, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemTongs(design + "_tongs", Item.ToolMaterial.IRON, 0).setCustom(design).setCreativeTab(tab));
//
//		registry.register(new ItemBasicCraftTool(design + "_spoon", "spoon", 0, 64).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemBasicCraftTool(design + "_mallet", "mallet", 0, 64).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemSpanner(design + "_spanner", 0, 0).setCustom(design).setCreativeTab(tab));
//
//		//CustomArmourListMFR
//		design = "standard";
//		tab = CreativeTabMFR.tabArmour;
//
//		registry.register(new ItemCustomArmour(design, "scale_helmet", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.HEAD, "scale_layer_1", 0).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "scale_chest", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.CHEST, "scale_layer_1", 0).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "scale_legs", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.LEGS, "scale_layer_2", 0).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "scale_boots", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.FEET, "scale_layer_1", 0).setCreativeTab(tab));
//
//		registry.register(new ItemCustomArmour(design, "chain_helmet", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.HEAD, "chain_layer_1", 0).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "chain_chest", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.CHEST, "chain_layer_1", 0).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "chain_legs", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.LEGS, "chain_layer_2", 0).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "chain_boots", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.FEET, "chain_layer_1", 0).setCreativeTab(tab));
//
//		registry.register(new ItemCustomArmour(design, "splint_helmet", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.HEAD, "splint_layer_1", 0).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "splint_chest", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.CHEST, "splint_layer_1", 0).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "splint_legs", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.LEGS, "splint_layer_2", 0).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "splint_boots", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.FEET, "splint_layer_1", 0).setCreativeTab(tab));
//
//		registry.register(new ItemCustomArmour(design, "plate_helmet", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.HEAD, "plate_layer_1", 0).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "plate_chest", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.CHEST, "plate_layer_1", 0).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "plate_legs", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.LEGS, "plate_layer_2", 0).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "plate_boots", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.FEET, "plate_layer_1", 0).setCreativeTab(tab));
//
//
//		//DragonforgedStyle
//		design = "dragonforged";
//		tab = CreativeTabMFR.tabDragonforged;
//		Item.ToolMaterial mat = DRAGONFORGED;
//		float ratingMod = 1.2F;
//
//		// Weapons
//		registry.register(new ItemDagger(design + "_dagger", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
//		registry.register(new ItemSword(design + "_sword", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
//		registry.register(new ItemWaraxe(design + "_waraxe", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
//		registry.register(new ItemMace(design + "_mace", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
//		registry.register(new ItemSpear(design + "_spear", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
//		registry.register(new ItemKatana(design + "_katana", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
//		registry.register(new ItemGreatsword(design + "_greatsword", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
//		registry.register(new ItemBattleaxe(design + "_battleaxe", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
//		registry.register(new ItemWarhammer(design + "_warhammer", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
//		registry.register(new ItemHalbeard(design + "_halbeard", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
//		registry.register(new ItemLance(design + "_lance", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(1));
//
//		registry.register(new ItemBowMFR(design + "_bow", mat, EnumBowType.SHORTBOW, 1).setCustom(design).setCreativeTab(tab));
//
//		// Tools
//		registry.register(new ItemPickMF(design + "_pick", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemAxe(design + "_axe", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemSpadeMF(design + "_spade", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemHoeMF(design + "_hoe", mat, 0).setCustom(design).setCreativeTab(tab));
//
//		registry.register(new ItemHandpick(design + "_handpick", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemHvyPick(design + "_hvypick", mat, 0).setCustom(design).setCreativeTab(tab));
//
//		registry.register(new ItemTrowMF(design + "_trow", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemHvyShovel(design + "_hvyshovel", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemScythe(design + "_scythe", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemMattock(design + "_mattock", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemLumberAxe(design + "_lumber", mat, 0).setCustom(design).setCreativeTab(tab));
//
//		// Crafters
//		registry.register(new ItemHammer(design + "_hammer", mat, false, 0, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemHammer(design + "_hvyhammer", mat, true, 0, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemShears(design + "_shears", mat, 0, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemKnifeMFR(design + "_knife", mat, 0, 1F, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemNeedle(design + "_needle", mat, 0, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemSaw(design + "_saw", mat, 0, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemTongs(design + "_tongs", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemSpanner(design + "_spanner", 0, 0).setCustom(design).setCreativeTab(tab));
//
//		// Armours
//		registry.register(new ItemCustomArmour(design, "chain_helmet", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.HEAD, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "chain_chest", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.CHEST, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "chain_legs", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.LEGS, "chain_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "chain_boots", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.FEET, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//
//		registry.register(new ItemCustomArmour(design, "scale_helmet", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.HEAD, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "scale_chest", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.CHEST, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "scale_legs", ArmourDesign.SCALEMAIL,EntityEquipmentSlot.LEGS, "scale_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "scale_boots", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.FEET, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//
//		registry.register(new ItemCustomArmour(design, "splint_helmet", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.HEAD, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "splint_chest", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.CHEST, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "splint_legs", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.LEGS, "splint_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "splint_boots", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.FEET, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//
//		registry.register(new ItemCustomArmour(design, "plate_helmet", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.HEAD, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "plate_chest", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.CHEST, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "plate_legs", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.LEGS, "plate_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "plate_boots", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.FEET, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//
//		//OrnateStyle
//		design = "ornate";
//		tab = CreativeTabMFR.tabOrnate;
//		mat = ORNATE;
//		ratingMod = 0.8F;
//
//		// Weapons
//		registry.register(new ItemDagger(design + "_dagger", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
//		registry.register(new ItemSword(design + "_sword", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
//		registry.register(new ItemWaraxe(design + "_waraxe", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
//		registry.register(new ItemMace(design + "_mace", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
//		registry.register(new ItemSpear(design + "_spear", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
//		registry.register(new ItemKatana(design + "_katana", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
//		registry.register(new ItemGreatsword(design + "_greatsword", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
//		registry.register(new ItemBattleaxe(design + "_battleaxe", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
//		registry.register(new ItemWarhammer(design + "_warhammer", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
//		registry.register(new ItemHalbeard(design + "_halbeard", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
//		registry.register(new ItemLance(design + "_lance", mat, 0, 1F).setCustom(design).setTab(tab).modifyBaseDamage(-1));
//
//		registry.register(new ItemBowMFR(design + "_bow", mat, EnumBowType.SHORTBOW, 1).setCustom(design).setCreativeTab(tab));
//
//		// Tools
//		registry.register(new ItemPickMF(design + "_pick", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemAxe(design + "_axe", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemSpadeMF(design + "_spade", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemHoeMF(design + "_hoe", mat, 0).setCustom(design).setCreativeTab(tab));
//
//		registry.register(new ItemHandpick(design + "_handpick", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemHvyPick(design + "_hvypick", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemTrowMF(design + "_trow", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemHvyShovel(design + "_hvyshovel", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemScythe(design + "_scythe", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemMattock(design + "_mattock", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemLumberAxe(design + "_lumber", mat, 0).setCustom(design).setCreativeTab(tab));
//
//		// Crafters
//		registry.register(new ItemHammer(design + "_hammer", mat, false, 0, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemHammer(design + "_hvyhammer", mat, true, 0, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemShears(design + "_shears", mat, 0, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemKnifeMFR(design + "_knife", mat, 0, 1F, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemNeedle(design + "_needle", mat, 0, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemSaw(design + "_saw", mat, 0, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemTongs(design + "_tongs", mat, 0).setCustom(design).setCreativeTab(tab));
//		registry.register(new ItemSpanner(design + "_spanner", 0, 0).setCustom(design).setCreativeTab(tab));
//
//		// Armours
//		registry.register(new ItemCustomArmour(design, "chain_helmet", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.HEAD, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "chain_chest", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.CHEST, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "chain_legs", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.LEGS, "chain_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "chain_boots", ArmourDesign.CHAINMAIL, EntityEquipmentSlot.FEET, "chain_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//
//		registry.register(new ItemCustomArmour(design, "scale_helmet", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.HEAD, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "scale_chest", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.CHEST, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "scale_legs", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.LEGS, "scale_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "scale_boots", ArmourDesign.SCALEMAIL, EntityEquipmentSlot.FEET, "scale_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//
//		registry.register(new ItemCustomArmour(design, "splint_helmet", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.CHEST, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "splint_chest", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.CHEST, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "splint_legs", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.LEGS, "splint_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "splint_boots", ArmourDesign.SPLINTMAIL, EntityEquipmentSlot.FEET, "splint_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//
//		registry.register(new ItemCustomArmour(design, "plate_helmet", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.HEAD, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "plate_chest", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.CHEST, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "plate_legs", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.LEGS, "plate_layer_2", 0).modifyRating(ratingMod).setCreativeTab(tab));
//		registry.register(new ItemCustomArmour(design, "plate_boots", ArmourDesign.FIELDPLATE, EntityEquipmentSlot.FEET, "plate_layer_1", 0).modifyRating(ratingMod).setCreativeTab(tab));
//	}
//
//	public static void load() {
//		INGOTS = new ItemComponentMFR[]{
//				COPPER_INGOT,
//				TIN_INGOT,
//				BRONZE_INGOT,
//				PIG_IRON_INGOT,
//				STEEL_INGOT,
//				ENCRUSTED_INGOT,
//				BLACK_STEEL_WEAK_INGOT,
//				BLACK_STEEL_INGOT,
//				SILVER_INGOT,
//				RED_STEEL_WEAK_INGOT,
//				RED_STEEL_INGOT,
//				BLUE_STEEL_WEAK_INGOT,
//				BLUE_STEEL_INGOT,
//				ADAMANTIUM_INGOT,
//				MITHRIL_INGOT,
//				IGNOTUMITE_INGOT,
//				MITHIUM_INGOT,
//				ENDER_INGOT,
//				TUNGSTEN_INGOT,
//				OBSIDIAN_INGOT
//		};
//
//		WoodMaterial.init();
//		Items.POTIONITEM.setContainerItem(Items.GLASS_BOTTLE);
//		GameRegistry.registerFuelHandler(new FuelHandlerMF());
//		MineFantasyRebornAPI.registerFuelHandler(new AdvancedFuelHandlerMF());
//
//		addRandomDrops();
//		initFuels();
//
//		//ToolListMFR
//		POOR = EnumHelper.addRarity("Poor", TextFormatting.DARK_GRAY, "poor");
//		UNIQUE = EnumHelper.addRarity("Unique", TextFormatting.DARK_GREEN, "unique");
//		RARE = EnumHelper.addRarity("Rare", TextFormatting.DARK_BLUE, "rare");
//
//		RARITY = new EnumRarity[]{ToolListMFR.POOR, EnumRarity.COMMON, EnumRarity.UNCOMMON, EnumRarity.RARE, EnumRarity.EPIC};
//
//		if (ConfigHardcore.HCCWeakItems) {
//			weakenItems();
//		}
//
//		//FoodListMFR
//		CookRecipe.burnt_food = BURNT_FOOD;
//
//		//ArmourListMFR
//		LEATHER_MAT = new ArmourMaterialMFR("leather", 5, 0.30F, 18, 1.00F);
//		APRON = new ArmourMaterialMFR("apron", 6, 0.30F, 0, 1.00F);
//		LEATHER = new ItemArmourMFR[leathermats.length * 4];
//		LEATHER_APRON = new ItemApron("leather_apron", BaseMaterialMFR.LEATHER_APRON, "leatherapron_layer_1", 0);
//
//		for (int a = 0; a < leathermats.length; a++) {
//			BaseMaterialMFR baseMat = BaseMaterialMFR.getMaterial(leathermats[a]);
//			String matName = baseMat.name;
//			int rarity = baseMat.rarity;
//			int id = a * 4;
//			float bulk = baseMat.weight;
//			ArmourDesign design = baseMat == BaseMaterialMFR.PADDING ? ArmourDesign.PADDING : ArmourDesign.LEATHER;
//
//			LEATHER[id + 0] = new ItemArmourMFR(matName.toLowerCase() + "_helmet", baseMat, design, EntityEquipmentSlot.HEAD, matName.toLowerCase() + "_layer_1", rarity, bulk);
//			LEATHER[id + 1] = new ItemArmourMFR(matName.toLowerCase() + "_chest", baseMat, design, EntityEquipmentSlot.CHEST, matName.toLowerCase() + "_layer_1", rarity, bulk);
//			LEATHER[id + 2] = new ItemArmourMFR(matName.toLowerCase() + "_legs", baseMat, design, EntityEquipmentSlot.LEGS, matName.toLowerCase() + "_layer_2", rarity, bulk);
//			LEATHER[id + 3] = new ItemArmourMFR(matName.toLowerCase() + "_boots", baseMat, design, EntityEquipmentSlot.FEET, matName.toLowerCase() + "_layer_1", rarity, bulk);
//		}
//
//
//	}
//
//	//ComponentListMFR
//	private static void initFuels() {
//		MineFantasyFuels.addForgeFuel(new ItemStack(Items.COAL, 1, 0), 900, 1500);// 1500C , 45s
//		MineFantasyFuels.addForgeFuel(new ItemStack(Items.COAL, 1, 1), 1200, 1800);// 1800C , 1m
//		MineFantasyFuels.addForgeFuel(Items.BLAZE_POWDER, 200, 3000, true);// 3000C , 10s
//		MineFantasyFuels.addForgeFuel(Items.BLAZE_ROD, 300, 3000, true);// 3000C , 15s
//		MineFantasyFuels.addForgeFuel(Items.FIRE_CHARGE, 1200, 3500, true);// 3500C , 1m
//		MineFantasyFuels.addForgeFuel(Items.LAVA_BUCKET, 2400, 5000, true);// 5000C , 2m
//		MineFantasyFuels.addForgeFuel(Items.MAGMA_CREAM, 2400, 4000, true, true);// 4000C , 2m
//
//		MineFantasyFuels.addForgeFuel(COKE, 1200, 2500, false, true);// 2500C , 1m
//		MineFantasyFuels.addForgeFuel(MAGMA_CREAM_REFINED, 2400, 5000, true, true);// 5000C , 2m
//
//		MineFantasyFuels.addForgeFuel(COAL_DUST, 1200, 180);// 180C , 60s
//	}
//
//	private static void addRandomDrops() {
//		RandomOre.addOre(new ItemStack(KAOLINITE), 1.5F, Blocks.STONE, -1, 32, 128, false);
//		RandomOre.addOre(new ItemStack(FLUX), 2F, Blocks.STONE, -1, 0, 128, false);
//		RandomOre.addOre(new ItemStack(FLUX_STRONG), 1F, Blocks.STONE, 2, 0, 128, false);
//		RandomOre.addOre(new ItemStack(FLUX), 20F, BlockListMFR.LIMESTONE, 0, -1, 0, 256, true);
//		RandomOre.addOre(new ItemStack(FLUX_STRONG), 10F, BlockListMFR.LIMESTONE, 0, 2, 0, 256, true);
//		RandomOre.addOre(new ItemStack(Items.COAL), 2F, Blocks.STONE, -1, 0, 128, false);
//		RandomOre.addOre(new ItemStack(SULFUR), 2F, Blocks.STONE, -1, 0, 16, false);
//		RandomOre.addOre(new ItemStack(NITRE), 3F, Blocks.STONE, -1, 0, 64, false);
//		RandomOre.addOre(new ItemStack(Items.REDSTONE), 5F, Blocks.STONE, 2, 0, 16, false);
//		RandomOre.addOre(new ItemStack(Items.FLINT), 1F, Blocks.STONE, -1, 0, 64, false);
//		RandomOre.addOre(new ItemStack(DIAMOND_SHARDS), 0.2F, Blocks.STONE, 2, 0, 16, false);
//		RandomOre.addOre(new ItemStack(Items.QUARTZ), 0.5F, Blocks.STONE, 3, 0, 16, false);
//
//		RandomOre.addOre(new ItemStack(SULFUR), 10F, Blocks.NETHERRACK, -1, 0, 512, false);
//		RandomOre.addOre(new ItemStack(Items.GLOWSTONE_DUST), 5F, Blocks.NETHERRACK, -1, 0, 512, false);
//		RandomOre.addOre(new ItemStack(Items.QUARTZ), 5F, Blocks.NETHERRACK, -1, 0, 512, false);
//		RandomOre.addOre(new ItemStack(Items.BLAZE_POWDER), 5F, Blocks.NETHERRACK, -1, 0, 512, false);
//		RandomOre.addOre(new ItemStack(Items.NETHER_WART), 1F, Blocks.NETHERRACK, -1, 0, 512, false);
//		RandomOre.addOre(new ItemStack(Items.NETHER_STAR), 0.01F, Blocks.NETHERRACK, -1, 0, 512, false);
//
//		RandomDigs.addOre(new ItemStack(Blocks.SKULL, 1, 1), 0.1F, Blocks.SOUL_SAND, 3, 0, 256, false);
//		RandomDigs.addOre(new ItemStack(Items.BONE), 5F, Blocks.DIRT, -1, 0, 256, false);
//		RandomDigs.addOre(new ItemStack(Items.ROTTEN_FLESH), 2F, Blocks.DIRT, -1, 0, 256, false);
//		RandomDigs.addOre(new ItemStack(Items.COAL, 1, 1), 1F, Blocks.DIRT, -1, 32, 64, false);
//
//		RandomDigs.addOre(new ItemStack(Items.MELON_SEEDS), 5F, Blocks.GRASS, -1, 0, 256, false);
//		RandomDigs.addOre(new ItemStack(Items.PUMPKIN_SEEDS), 8F, Blocks.GRASS, -1, 0, 256, false);
//
//		RandomOre.addOre(new ItemStack(ORE_COPPER), 4F, Blocks.STONE, 0, 48, 96, false);
//		RandomOre.addOre(new ItemStack(ORE_TIN), 2F, Blocks.STONE, 0, 48, 96, false);
//		RandomOre.addOre(new ItemStack(ORE_IRON), 5F, Blocks.STONE, 0, 0, 64, false);
//		RandomOre.addOre(new ItemStack(ORE_SILVER), 1.5F, Blocks.STONE, 0, 0, 32, false);
//		RandomOre.addOre(new ItemStack(ORE_GOLD), 1F, Blocks.STONE, 0, 0, 32, false);
//
//		RandomOre.addOre(new ItemStack(ORE_TUNGSTEN), 2F, Blocks.STONE, 3, 0, 16, false, "tungsten");
//	}
//
//	public static ItemStack bar(String material) {
//		return bar(material, 1);
//	}
//
//	public static ItemStack bar(String material, int stackSize) {
//		return BAR.createComm(material, stackSize);
//	}
//
//	//ToolListMFR
//	private static void weakenItems() {
//		weakenItem(Items.WOODEN_PICKAXE, 5);
//		weakenItem(Items.WOODEN_AXE, 5);
//		weakenItem(Items.WOODEN_SHOVEL, 5);
//		weakenItem(Items.WOODEN_SWORD, 5);
//		weakenItem(Items.WOODEN_HOE, 5);
//
//		weakenItem(Items.LEATHER_HELMET);
//		weakenItem(Items.LEATHER_CHESTPLATE);
//		weakenItem(Items.LEATHER_LEGGINGS);
//		weakenItem(Items.LEATHER_BOOTS);
//
//		weakenItem(Items.STONE_PICKAXE, 10);
//		weakenItem(Items.STONE_AXE, 10);
//		weakenItem(Items.STONE_SHOVEL, 10);
//		weakenItem(Items.STONE_SWORD, 10);
//		weakenItem(Items.STONE_HOE, 10);
//
//		weakenItem(Items.IRON_PICKAXE, 25);
//		weakenItem(Items.IRON_AXE, 25);
//		weakenItem(Items.IRON_SHOVEL, 25);
//		weakenItem(Items.IRON_SWORD, 25);
//		weakenItem(Items.IRON_HOE, 25);
//		weakenItem(Items.IRON_HELMET);
//		weakenItem(Items.IRON_CHESTPLATE);
//		weakenItem(Items.IRON_LEGGINGS);
//		weakenItem(Items.IRON_BOOTS);
//
//		weakenItem(Items.GOLDEN_PICKAXE, 1);
//		weakenItem(Items.GOLDEN_AXE, 1);
//		weakenItem(Items.GOLDEN_SHOVEL, 1);
//		weakenItem(Items.GOLDEN_SWORD, 1);
//		weakenItem(Items.GOLDEN_HOE, 1);
//		weakenItem(Items.GOLDEN_HELMET);
//		weakenItem(Items.GOLDEN_CHESTPLATE);
//		weakenItem(Items.GOLDEN_LEGGINGS);
//		weakenItem(Items.GOLDEN_BOOTS);
//
//		weakenItem(Items.DIAMOND_PICKAXE, 100);
//		weakenItem(Items.DIAMOND_AXE, 100);
//		weakenItem(Items.DIAMOND_SHOVEL, 100);
//		weakenItem(Items.DIAMOND_SWORD, 100);
//		weakenItem(Items.DIAMOND_HOE, 100);
//		weakenItem(Items.DIAMOND_HELMET);
//		weakenItem(Items.DIAMOND_CHESTPLATE);
//		weakenItem(Items.DIAMOND_LEGGINGS);
//		weakenItem(Items.DIAMOND_BOOTS);
//	}
//
//	private static void weakenItem(Item item) {
//		weakenItem(item, (item.getMaxDamage() / 10) + 1);
//	}
//
//	private static void weakenItem(Item item, int hp) {
//		if (item.isDamageable()) {
//			item.setMaxDamage(hp);
//		}
//	}
//
//	//Dragonforged and Ornate
//	public static void loadCrafting() {
//
//		//Dragonforged
//		SpecialForging.addDragonforgeCraft(STANDARD_DAGGER, DRAGONFORGED_DAGGER);
//		SpecialForging.addDragonforgeCraft(STANDARD_SWORD, DRAGONFORGED_SWORD);
//		SpecialForging.addDragonforgeCraft(STANDARD_MACE, DRAGONFORGED_MACE);
//		SpecialForging.addDragonforgeCraft(STANDARD_WARAXE, DRAGONFORGED_WARAXE);
//		SpecialForging.addDragonforgeCraft(STANDARD_SPEAR, DRAGONFORGED_SPEAR);
//		SpecialForging.addDragonforgeCraft(STANDARD_KATANA, DRAGONFORGED_KATANA);
//		SpecialForging.addDragonforgeCraft(STANDARD_GREATSWORD, DRAGONFORGED_GREATSWORD);
//		SpecialForging.addDragonforgeCraft(STANDARD_WARHAMMER, DRAGONFORGED_WARHAMMER);
//		SpecialForging.addDragonforgeCraft(STANDARD_BATTLEAXE, DRAGONFORGED_BATTLEAXE);
//		SpecialForging.addDragonforgeCraft(STANDARD_HALBEARD, DRAGONFORGED_HALBEARD);
//		SpecialForging.addDragonforgeCraft(STANDARD_LANCE, DRAGONFORGED_LANCE);
//		SpecialForging.addDragonforgeCraft(STANDARD_BOW, DRAGONFORGED_BOW);
//
//		SpecialForging.addDragonforgeCraft(STANDARD_PICK, DRAGONFORGED_PICK);
//		SpecialForging.addDragonforgeCraft(STANDARD_AXE, DRAGONFORGED_AXE);
//		SpecialForging.addDragonforgeCraft(STANDARD_SPADE, DRAGONFORGED_SPADE);
//		SpecialForging.addDragonforgeCraft(STANDARD_HOE, DRAGONFORGED_HOE);
//		SpecialForging.addDragonforgeCraft(STANDARD_SHEARS, DRAGONFORGED_SHEARS);
//		SpecialForging.addDragonforgeCraft(STANDARD_HVYPICK, DRAGONFORGED_HVYPICK);
//		SpecialForging.addDragonforgeCraft(STANDARD_HVYSHOVEL, DRAGONFORGED_HVYSHOVEL);
//		SpecialForging.addDragonforgeCraft(STANDARD_TROW, DRAGONFORGED_TROW);
//		SpecialForging.addDragonforgeCraft(STANDARD_HANDPICK, DRAGONFORGED_HANDPICK);
//		SpecialForging.addDragonforgeCraft(STANDARD_MATTOCK, DRAGONFORGED_MATTOCK);
//		SpecialForging.addDragonforgeCraft(STANDARD_SPADE, DRAGONFORGED_SPADE);
//		SpecialForging.addDragonforgeCraft(STANDARD_SCYTHE, DRAGONFORGED_SCYTHE);
//		SpecialForging.addDragonforgeCraft(STANDARD_SPANNER, DRAGONFORGED_SPANNER);
//		SpecialForging.addDragonforgeCraft(STANDARD_LUMBER, DRAGONFORGED_LUMBER);
//
//		SpecialForging.addDragonforgeCraft(STANDARD_HAMMER, DRAGONFORGED_HAMMER);
//		SpecialForging.addDragonforgeCraft(STANDARD_HVYHAMMER, DRAGONFORGED_HVYHAMMER);
//		SpecialForging.addDragonforgeCraft(STANDARD_TONGS, DRAGONFORGED_TONGS);
//		SpecialForging.addDragonforgeCraft(STANDARD_SAW, DRAGONFORGED_SAW);
//		SpecialForging.addDragonforgeCraft(STANDARD_NEEDLE, DRAGONFORGED_NEEDLE);
//		SpecialForging.addDragonforgeCraft(STANDARD_KNIFE, DRAGONFORGED_KNIFE);
//
//		SpecialForging.addDragonforgeCraft(STANDARD_CHAIN_BOOTS, DRAGONFORGED_CHAIN_BOOTS);
//		SpecialForging.addDragonforgeCraft(STANDARD_CHAIN_LEGS, DRAGONFORGED_CHAIN_LEGS);
//		SpecialForging.addDragonforgeCraft(STANDARD_CHAIN_CHEST, DRAGONFORGED_CHAIN_CHEST);
//		SpecialForging.addDragonforgeCraft(STANDARD_CHAIN_HELMET, DRAGONFORGED_CHAIN_HELMET);
//
//		SpecialForging.addDragonforgeCraft(STANDARD_SCALE_BOOTS, DRAGONFORGED_SCALE_BOOTS);
//		SpecialForging.addDragonforgeCraft(STANDARD_SCALE_LEGS, DRAGONFORGED_SCALE_LEGS);
//		SpecialForging.addDragonforgeCraft(STANDARD_SCALE_CHEST, DRAGONFORGED_SCALE_CHEST);
//		SpecialForging.addDragonforgeCraft(STANDARD_SCALE_HELMET, DRAGONFORGED_SCALE_HELMET);
//
//		SpecialForging.addDragonforgeCraft(STANDARD_SPLINT_BOOTS, DRAGONFORGED_SPLINT_BOOTS);
//		SpecialForging.addDragonforgeCraft(STANDARD_SPLINT_LEGS, DRAGONFORGED_SPLINT_LEGS);
//		SpecialForging.addDragonforgeCraft(STANDARD_SPLINT_CHEST, DRAGONFORGED_SPLINT_CHEST);
//		SpecialForging.addDragonforgeCraft(STANDARD_SPLINT_HELMET, DRAGONFORGED_SPLINT_HELMET);
//
//		SpecialForging.addDragonforgeCraft(STANDARD_PLATE_BOOTS, DRAGONFORGED_PLATE_BOOTS);
//		SpecialForging.addDragonforgeCraft(STANDARD_PLATE_LEGS, DRAGONFORGED_PLATE_LEGS);
//		SpecialForging.addDragonforgeCraft(STANDARD_PLATE_CHEST, DRAGONFORGED_PLATE_CHEST);
//		SpecialForging.addDragonforgeCraft(STANDARD_PLATE_HELMET, DRAGONFORGED_PLATE_HELMET);
//
//		//Ornate
//		String id = "ornate";
//
//		SpecialForging.addSpecialCraft(id, STANDARD_DAGGER, ORNATE_DAGGER);
//		SpecialForging.addSpecialCraft(id, STANDARD_SWORD, ORNATE_SWORD);
//		SpecialForging.addSpecialCraft(id, STANDARD_MACE, ORNATE_MACE);
//		SpecialForging.addSpecialCraft(id, STANDARD_WARAXE, ORNATE_WARAXE);
//		SpecialForging.addSpecialCraft(id, STANDARD_SPEAR, ORNATE_SPEAR);
//		SpecialForging.addSpecialCraft(id, STANDARD_KATANA, ORNATE_KATANA);
//		SpecialForging.addSpecialCraft(id, STANDARD_GREATSWORD, ORNATE_GREATSWORD);
//		SpecialForging.addSpecialCraft(id, STANDARD_WARHAMMER, ORNATE_WARHAMMER);
//		SpecialForging.addSpecialCraft(id, STANDARD_BATTLEAXE, ORNATE_BATTLEAXE);
//		SpecialForging.addSpecialCraft(id, STANDARD_HALBEARD, ORNATE_HALBEARD);
//		SpecialForging.addSpecialCraft(id, STANDARD_LANCE, ORNATE_LANCE);
//		SpecialForging.addSpecialCraft(id, STANDARD_BOW, ORNATE_BOW);
//
//		SpecialForging.addSpecialCraft(id, STANDARD_PICK, ORNATE_PICK);
//		SpecialForging.addSpecialCraft(id, STANDARD_AXE, ORNATE_AXE);
//		SpecialForging.addSpecialCraft(id, STANDARD_SPADE, ORNATE_SPADE);
//		SpecialForging.addSpecialCraft(id, STANDARD_HOE, ORNATE_HOE);
//		SpecialForging.addSpecialCraft(id, STANDARD_SHEARS, ORNATE_SHEARS);
//		SpecialForging.addSpecialCraft(id, STANDARD_HVYPICK, ORNATE_HVYPICK);
//		SpecialForging.addSpecialCraft(id, STANDARD_HVYSHOVEL, ORNATE_HVYSHOVEL);
//		SpecialForging.addSpecialCraft(id, STANDARD_TROW, ORNATE_TROW);
//		SpecialForging.addSpecialCraft(id, STANDARD_HANDPICK, ORNATE_HANDPICK);
//		SpecialForging.addSpecialCraft(id, STANDARD_MATTOCK, ORNATE_MATTOCK);
//		SpecialForging.addSpecialCraft(id, STANDARD_SPADE, ORNATE_SPADE);
//		SpecialForging.addSpecialCraft(id, STANDARD_SCYTHE, ORNATE_SCYTHE);
//		SpecialForging.addSpecialCraft(id, STANDARD_SPANNER, ORNATE_SPANNER);
//		SpecialForging.addSpecialCraft(id, STANDARD_LUMBER, ORNATE_LUMBER);
//
//		SpecialForging.addSpecialCraft(id, STANDARD_HAMMER, ORNATE_HAMMER);
//		SpecialForging.addSpecialCraft(id, STANDARD_HVYHAMMER, ORNATE_HVYHAMMER);
//		SpecialForging.addSpecialCraft(id, STANDARD_TONGS, ORNATE_TONGS);
//		SpecialForging.addSpecialCraft(id, STANDARD_SAW, ORNATE_SAW);
//		SpecialForging.addSpecialCraft(id, STANDARD_NEEDLE, ORNATE_NEEDLE);
//		SpecialForging.addSpecialCraft(id, STANDARD_KNIFE, ORNATE_KNIFE);
//
//		SpecialForging.addSpecialCraft(id, STANDARD_CHAIN_BOOTS, ORNATE_CHAIN_BOOTS);
//		SpecialForging.addSpecialCraft(id, STANDARD_CHAIN_LEGS, ORNATE_CHAIN_LEGS);
//		SpecialForging.addSpecialCraft(id, STANDARD_CHAIN_CHEST, ORNATE_CHAIN_CHEST);
//		SpecialForging.addSpecialCraft(id, STANDARD_CHAIN_HELMET, ORNATE_CHAIN_HELMET);
//
//		SpecialForging.addSpecialCraft(id, STANDARD_SCALE_BOOTS, ORNATE_SCALE_BOOTS);
//		SpecialForging.addSpecialCraft(id, STANDARD_SCALE_LEGS, ORNATE_SCALE_LEGS);
//		SpecialForging.addSpecialCraft(id, STANDARD_SCALE_CHEST, ORNATE_SCALE_CHEST);
//		SpecialForging.addSpecialCraft(id, STANDARD_SCALE_HELMET, ORNATE_SCALE_HELMET);
//
//		SpecialForging.addSpecialCraft(id, STANDARD_SPLINT_BOOTS, ORNATE_SPLINT_BOOTS);
//		SpecialForging.addSpecialCraft(id, STANDARD_SPLINT_LEGS, ORNATE_SPLINT_LEGS);
//		SpecialForging.addSpecialCraft(id, STANDARD_SPLINT_CHEST, ORNATE_SPLINT_CHEST);
//		SpecialForging.addSpecialCraft(id, STANDARD_SPLINT_HELMET, ORNATE_SPLINT_HELMET);
//
//		SpecialForging.addSpecialCraft(id, STANDARD_PLATE_BOOTS, ORNATE_PLATE_BOOTS);
//		SpecialForging.addSpecialCraft(id, STANDARD_PLATE_LEGS, ORNATE_PLATE_LEGS);
//		SpecialForging.addSpecialCraft(id, STANDARD_PLATE_CHEST, ORNATE_PLATE_CHEST);
//		SpecialForging.addSpecialCraft(id, STANDARD_PLATE_HELMET, ORNATE_PLATE_HELMET);
//	}
//}
