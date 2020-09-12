package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.MineFantasyFuels;
import minefantasy.mfr.api.mining.RandomDigs;
import minefantasy.mfr.api.mining.RandomOre;
import minefantasy.mfr.item.AdvancedFuelHandlerMF;
import minefantasy.mfr.item.FuelHandlerMF;
import minefantasy.mfr.item.ItemArtefact;
import minefantasy.mfr.item.ItemBowl;
import minefantasy.mfr.item.ItemComponentMFR;
import minefantasy.mfr.item.ItemFilledMould;
import minefantasy.mfr.item.ItemHide;
import minefantasy.mfr.item.ItemRawOre;
import minefantasy.mfr.item.ItemSpecialDesign;
import minefantasy.mfr.item.custom.ItemCustomComponent;
import minefantasy.mfr.item.gadget.ItemBombComponent;
import minefantasy.mfr.item.gadget.ItemCrossbowPart;
import minefantasy.mfr.item.heatable.ItemHeated;
import minefantasy.mfr.material.BaseMaterialMFR;
import minefantasy.mfr.util.Utils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * @author Anonymous Productions
 */
@ObjectHolder(MineFantasyReborn.MOD_ID)
@Mod.EventBusSubscriber(modid = MineFantasyReborn.MOD_ID)
public class ComponentListMFR {

	public static ItemComponentMFR[] INGOTS;

	public static Item CLAY_POT = Utils.nullValue();
	public static Item CLAY_POT_UNCOOKED = Utils.nullValue();
	public static Item INGOT_MOULD = Utils.nullValue();
	public static Item INGOT_MOULD_UNCOOKED = Utils.nullValue();
	public static Item PIE_TRAY_UNCOOKED = Utils.nullValue();

	public static ItemComponentMFR COPPER_INGOT = Utils.nullValue();
	public static ItemComponentMFR TIN_INGOT = Utils.nullValue();
	public static ItemComponentMFR BRONZE_INGOT = Utils.nullValue();
	public static ItemComponentMFR PIG_IRON_INGOT = Utils.nullValue();
	public static ItemComponentMFR STEEL_INGOT = Utils.nullValue();
	public static ItemComponentMFR ENCRUSTED_INGOT = Utils.nullValue();
	public static ItemComponentMFR BLACK_STEEL_WEAK_INGOT = Utils.nullValue();
	public static ItemComponentMFR BLACK_STEEL_INGOT = Utils.nullValue();
	public static ItemComponentMFR SILVER_INGOT = Utils.nullValue();
	public static ItemComponentMFR RED_STEEL_WEAK_INGOT = Utils.nullValue();
	public static ItemComponentMFR RED_STEEL_INGOT = Utils.nullValue();
	public static ItemComponentMFR BLUE_STEEL_WEAK_INGOT = Utils.nullValue();
	public static ItemComponentMFR BLUE_STEEL_INGOT = Utils.nullValue();
	public static ItemComponentMFR ADAMANTIUM_INGOT = Utils.nullValue();
	public static ItemComponentMFR MITHRIL_INGOT = Utils.nullValue();
	public static ItemComponentMFR IGNOTUMITE_INGOT = Utils.nullValue();
	public static ItemComponentMFR MITHIUM_INGOT = Utils.nullValue();
	public static ItemComponentMFR ENDER_INGOT = Utils.nullValue();
	public static ItemComponentMFR TUNGSTEN_INGOT = Utils.nullValue();
	public static ItemComponentMFR OBSIDIAN_INGOT = Utils.nullValue();
	
	public static Item INGOT_COMPOSITE_ALLOY = Utils.nullValue();

	public static ItemComponentMFR PLANK = Utils.nullValue();
	public static Item VINE = Utils.nullValue();
	public static Item SHARP_ROCK = Utils.nullValue();

	public static Item FLUX = Utils.nullValue();
	public static Item FLUX_STRONG = Utils.nullValue();

	public static Item COAL_DUST = Utils.nullValue();
	public static Item NITRE = Utils.nullValue();
	public static Item SULFUR = Utils.nullValue();
	public static Item IRON_PREP = Utils.nullValue();
	public static Item BLACKPOWDER = Utils.nullValue();
	public static Item BLACKPOWDER_ADVANCED = Utils.nullValue();
	public static Item FLETCHING = Utils.nullValue();
	public static Item SHRAPNEL = Utils.nullValue();
	public static Item MAGMA_CREAM_REFINED = Utils.nullValue();
	public static Item BOMB_FUSE = Utils.nullValue();
	public static Item BOMB_FUSE_LONG = Utils.nullValue();
	public static Item BOMB_CASING_UNCOOKED = Utils.nullValue();
	public static Item BOMB_CASING = Utils.nullValue();
	public static Item MINE_CASING_UNCOOKED = Utils.nullValue();
	public static Item MINE_CASING = Utils.nullValue();
	public static Item BOMB_CASING_IRON = Utils.nullValue();
	public static Item MINE_CASING_IRON = Utils.nullValue();
	public static Item BOMB_CASING_OBSIDIAN = Utils.nullValue();
	public static Item MINE_CASING_OBSIDIAN = Utils.nullValue();
	public static Item BOMB_CASING_CRYSTAL = Utils.nullValue();
	public static Item MINE_CASING_CRYSTAL = Utils.nullValue();
	public static Item BOMB_CASING_ARROW = Utils.nullValue();
	public static Item BOMB_CASING_BOLT = Utils.nullValue();

	public static Item COKE = Utils.nullValue();
	public static Item DIAMOND_SHARDS = Utils.nullValue();

	public static Item CLAY_BRICK = Utils.nullValue();
	public static Item KAOLINITE = Utils.nullValue();
	public static Item KAOLINITE_DUST = Utils.nullValue();
	public static Item FIRECLAY = Utils.nullValue();
	public static Item FIRECLAY_BRICK = Utils.nullValue();
	public static Item STRONG_BRICK = Utils.nullValue();

	public static Item HIDE_SMALL = Utils.nullValue();
	public static Item HIDE_MEDIUM = Utils.nullValue();
	public static Item HIDE_LARGE = Utils.nullValue();
	public static Item RAWHIDE_SMALL = Utils.nullValue();
	public static Item RAWHIDE_MEDIUM = Utils.nullValue();
	public static Item RAWHIDE_LARGE = Utils.nullValue();

	public static Item DRAGON_HEART = Utils.nullValue();

	public static Item LEATHER_STRIP = Utils.nullValue();
	public static Item NAIL = Utils.nullValue();
	public static Item RIVET = Utils.nullValue();
	public static Item THREAD = Utils.nullValue();
	public static Item OBSIDIAN_ROCK = Utils.nullValue();

	public static Item ORE_COPPER = Utils.nullValue();
	public static Item ORE_TIN = Utils.nullValue();
	public static Item ORE_IRON = Utils.nullValue();
	public static Item ORE_SILVER = Utils.nullValue();
	public static Item ORE_GOLD = Utils.nullValue();
	public static Item ORE_TUNGSTEN = Utils.nullValue();

	public static Item HOT_ITEM = Utils.nullValue();

	public static Item PLANT_OIL = Utils.nullValue();

	public static Item TALISMAN_LESSER = Utils.nullValue();
	public static Item TALISMAN_GREATER = Utils.nullValue();

	public static Item BOLT = Utils.nullValue();
	public static Item IRON_FRAME = Utils.nullValue();
	public static Item IRON_STRUT = Utils.nullValue();
	public static Item BRONZE_GEARS = Utils.nullValue();
	public static Item TUNGSTEN_GEARS = Utils.nullValue();
	public static Item STEEL_TUBE = Utils.nullValue();
	public static Item COGWORK_SHAFT = Utils.nullValue();

	public static Item COAL_PREP = Utils.nullValue();

	public static Item INGOT_MOULD_FILLED = Utils.nullValue();

	public static Item CROSSBOW_STOCK_WOOD = Utils.nullValue();
	public static Item CROSSBOW_STOCK_IRON = Utils.nullValue();
	public static Item CROSSBOW_HANDLE_WOOD = Utils.nullValue();

	public static Item CROSS_ARMS_BASIC = Utils.nullValue();
	public static Item CROSS_ARMS_LIGHT = Utils.nullValue();
	public static Item CROSS_ARMS_HEAVY = Utils.nullValue();
	public static Item CROSS_ARMS_ADVANCED = Utils.nullValue();

	public static Item CROSS_BAYONET = Utils.nullValue();
	public static Item CROSS_AMMO = Utils.nullValue();
	public static Item CROSS_SCOPE = Utils.nullValue();

	public static ItemCustomComponent CHAIN_MESH = Utils.nullValue();
	public static ItemCustomComponent SCALE_MESH = Utils.nullValue();
	public static ItemCustomComponent SPLINT_MESH = Utils.nullValue();
	public static ItemCustomComponent PLATE = Utils.nullValue();
	public static ItemCustomComponent PLATE_HUGE = Utils.nullValue();
	public static ItemCustomComponent METAL_HUNK = Utils.nullValue();
	public static ItemCustomComponent ARROWHEAD = Utils.nullValue();
	public static ItemCustomComponent BODKIN_HEAD = Utils.nullValue();
	public static ItemCustomComponent BROAD_HEAD = Utils.nullValue();
	public static ItemCustomComponent COGWORK_ARMOUR = Utils.nullValue();
	public static ItemCustomComponent BAR = Utils.nullValue();

	public static Item FLUX_POT = Utils.nullValue();
	public static Item COAL_FLUX = Utils.nullValue();

	public static Item COPPER_COIN = Utils.nullValue();
	public static Item SILVER_COIN = Utils.nullValue();
	public static Item GOLD_COIN = Utils.nullValue();

	public static Item HINGE = Utils.nullValue();
	public static Item PLANK_CUT = Utils.nullValue();
	public static Item PLANK_PANE = Utils.nullValue();

	public static Item COGWORK_PULLEY = Utils.nullValue();

	public static ItemArtefact ANCIENT_JEWEL_MITHRIL = Utils.nullValue();
	public static ItemArtefact ANCIENT_JEWEL_ADAMANT = Utils.nullValue();
	public static ItemArtefact ANCIENT_JEWEL_MASTER = Utils.nullValue();
	public static ItemArtefact TRILOGY_JEWEL = Utils.nullValue();
	public static ItemArtefact SCHEMATIC_BOMB = Utils.nullValue();
	public static ItemArtefact SCHEMATIC_CROSSBOW = Utils.nullValue();
	public static ItemArtefact SCHEMATIC_FORGE = Utils.nullValue();
	public static ItemArtefact SCHEMATIC_GEARS = Utils.nullValue();
	public static ItemArtefact SCHEMATIC_COGWORK = Utils.nullValue();
	public static ItemArtefact SCHEMATIC_ALLOYS = Utils.nullValue();


	public static Item ORNATE_ITEMS = Utils.nullValue();

	public static void init() {
		CLAY_POT = new ItemBowl("clay_pot").setStoragePlacement("pot", "pot");
		CLAY_POT_UNCOOKED = new ItemComponentMFR("clay_pot_uncooked", 0);
		INGOT_MOULD = new ItemComponentMFR("ingot_mould").setStoragePlacement("bar", "mould");
		INGOT_MOULD_UNCOOKED = new ItemComponentMFR("ingot_mould_uncooked", 0);
		PIE_TRAY_UNCOOKED = new ItemComponentMFR("pie_tray_uncooked", 0);

		COPPER_INGOT = new ItemComponentMFR("copper_ingot", BaseMaterialMFR.getMaterial("copper").rarity);
		TIN_INGOT = new ItemComponentMFR("tin_ingot", BaseMaterialMFR.getMaterial("tin").rarity);
		BRONZE_INGOT = new ItemComponentMFR("bronze_ingot", BaseMaterialMFR.getMaterial("bronze").rarity);
		PIG_IRON_INGOT = new ItemComponentMFR("pig_iron_ingot", BaseMaterialMFR.getMaterial("pig_iron").rarity);
		STEEL_INGOT = new ItemComponentMFR("steel_ingot", BaseMaterialMFR.getMaterial("steel").rarity);
		ENCRUSTED_INGOT = new ItemComponentMFR("encrusted_ingot", BaseMaterialMFR.getMaterial("encrusted").rarity);
		BLACK_STEEL_WEAK_INGOT = new ItemComponentMFR("black_steel_weak_ingot", BaseMaterialMFR.getMaterial("weak_black_steel").rarity);
		BLACK_STEEL_INGOT = new ItemComponentMFR("black_steel_ingot", BaseMaterialMFR.getMaterial("black_steel").rarity);
		SILVER_INGOT = new ItemComponentMFR("silver_ingot", BaseMaterialMFR.getMaterial("silver").rarity);
		RED_STEEL_WEAK_INGOT = new ItemComponentMFR("red_steel_weak_ingot", BaseMaterialMFR.getMaterial("weak_red_steel").rarity);
		RED_STEEL_INGOT = new ItemComponentMFR("red_steel_ingot", BaseMaterialMFR.getMaterial("red_steel").rarity);
		BLUE_STEEL_WEAK_INGOT = new ItemComponentMFR("blue_steel_weak_ingot", BaseMaterialMFR.getMaterial("weak_blue_steel").rarity);
		BLUE_STEEL_INGOT = new ItemComponentMFR("blue_steel_ingot", BaseMaterialMFR.getMaterial("blue_steel").rarity);
		ADAMANTIUM_INGOT = new ItemComponentMFR("adamantium_ingot", BaseMaterialMFR.getMaterial("adamantium").rarity);
		MITHRIL_INGOT = new ItemComponentMFR("mithril_ingot", BaseMaterialMFR.getMaterial("mithril").rarity);
		IGNOTUMITE_INGOT = new ItemComponentMFR("ignotumite_ingot", BaseMaterialMFR.getMaterial("ignotumite").rarity);
		MITHIUM_INGOT = new ItemComponentMFR("mithium_ingot", BaseMaterialMFR.getMaterial("mithium").rarity);
		ENDER_INGOT = new ItemComponentMFR("ender_ingot", BaseMaterialMFR.getMaterial("enderforge").rarity);
		TUNGSTEN_INGOT = new ItemComponentMFR("tungsten_ingot", BaseMaterialMFR.getMaterial("tungsten").rarity);
		OBSIDIAN_INGOT = new ItemComponentMFR("obsidian_ingot", BaseMaterialMFR.getMaterial("obsidian").rarity);

		INGOT_COMPOSITE_ALLOY = new ItemComponentMFR("ingot_composite_alloy", 1);

		PLANK = new ItemComponentMFR("plank").setCustom(1, "wood").setStoragePlacement("plank","plank");
		VINE = new ItemComponentMFR("vine", -1);
		SHARP_ROCK = new ItemComponentMFR("sharp_rock", -1);

		FLUX = new ItemComponentMFR("flux", 0);
		FLUX_STRONG = new ItemComponentMFR("flux_strong", 0);

		COAL_DUST = new ItemComponentMFR("coal_dust", 0).setContainerItem(CLAY_POT);
		NITRE = new ItemComponentMFR("nitre", 0);
		SULFUR = new ItemComponentMFR("sulfur", 0);
		IRON_PREP = new ItemComponentMFR("iron_prep", 0);

		BLACKPOWDER = new ItemBombComponent("blackpowder", 0, "powder", 0).setContainerItem(CLAY_POT);
		BLACKPOWDER_ADVANCED = new ItemBombComponent("blackpowder_advanced", 1, "powder", 1).setContainerItem(CLAY_POT);
		FLETCHING = new ItemComponentMFR("fletching", 0);
		SHRAPNEL = new ItemBombComponent("shrapnel", 0, "filling", 1).setContainerItem(ComponentListMFR.CLAY_POT);
		MAGMA_CREAM_REFINED = new ItemBombComponent("magma_cream_refined", 1, "filling", 2).setContainerItem(CLAY_POT);
		BOMB_FUSE = new ItemBombComponent("bomb_fuse", 0, "fuse", 0);
		BOMB_FUSE_LONG = new ItemBombComponent("bomb_fuse_long", 0, "fuse", 1);
		BOMB_CASING_UNCOOKED = new ItemComponentMFR("bomb_casing_uncooked", 0);
		BOMB_CASING = new ItemBombComponent("bomb_casing", 0, "bombcase", 0);
		MINE_CASING_UNCOOKED = new ItemComponentMFR("mine_casing_uncooked", 0);
		MINE_CASING = new ItemBombComponent("mine_casing", 0, "minecase", 0);
		BOMB_CASING_IRON = new ItemBombComponent("bomb_casing_iron", 0, "bombcase", 1);
		MINE_CASING_IRON = new ItemBombComponent("mine_casing_iron", 0, "bombcase", 1);
		BOMB_CASING_OBSIDIAN = new ItemBombComponent("bomb_casing_obsidian", 1, "bombcase", 2);
		MINE_CASING_OBSIDIAN = new ItemBombComponent("mine_casing_obsidian", 1, "minecase", 2);
		BOMB_CASING_CRYSTAL = new ItemBombComponent("bomb_casing_crystal", 1, "bombcase", 3);
		MINE_CASING_CRYSTAL = new ItemBombComponent("mine_casing_crystal", 1, "minecase", 3);
		BOMB_CASING_ARROW = new ItemBombComponent("bomb_casing_arrow", 1, "arrow", 0);
		BOMB_CASING_BOLT = new ItemBombComponent("bomb_casing_bolt", 1, "bolt", 0);

		COKE = new ItemComponentMFR("coke", 1);
		DIAMOND_SHARDS = new ItemComponentMFR("diamond_shards", 0);

		CLAY_BRICK = new ItemComponentMFR("clay_brick", 0);
		KAOLINITE = new ItemComponentMFR("kaolinite", 0);
		KAOLINITE_DUST = new ItemComponentMFR("kaolinite_dust", 0).setContainerItem(CLAY_POT);
		FIRECLAY = new ItemComponentMFR("fireclay", 0);
		FIRECLAY_BRICK = new ItemComponentMFR("fireclay_brick", 0);
		STRONG_BRICK = new ItemComponentMFR("strong_brick", 0).setStoragePlacement("bar", "firebrick");

		HIDE_SMALL = new ItemComponentMFR("hide_small", 0);
		HIDE_MEDIUM = new ItemComponentMFR("hide_medium", 0);
		HIDE_LARGE = new ItemComponentMFR("hide_large", 0);
		RAWHIDE_SMALL = new ItemHide("rawhide_small", HIDE_SMALL, 1.0F);
		RAWHIDE_MEDIUM = new ItemHide("rawhide_medium", HIDE_MEDIUM, 1.5F);
		RAWHIDE_LARGE = new ItemHide("rawhide_large", HIDE_LARGE, 3.0F);

		DRAGON_HEART = new ItemSpecialDesign("dragon_heart", 1, "dragon");

		LEATHER_STRIP = new ItemComponentMFR("leather_strip", 0);
		NAIL = new ItemComponentMFR("nail", 0);
		RIVET = new ItemComponentMFR("rivet", 0);
		THREAD = new ItemComponentMFR("thread", 0);
		OBSIDIAN_ROCK = new ItemComponentMFR("obsidian_rock", 0);

		ORE_COPPER = new ItemRawOre("ore_copper", -1);
		ORE_TIN = new ItemRawOre("ore_tin", -1);
		ORE_IRON = new ItemRawOre("ore_iron", -1);
		ORE_SILVER = new ItemRawOre("ore_silver", 0);
		ORE_GOLD = new ItemRawOre("ore_gold", 0);
		ORE_TUNGSTEN = new ItemRawOre("ore_tungsten", 1);

		HOT_ITEM = new ItemHeated();

		PLANT_OIL = new ItemComponentMFR("plant_oil", 0).setStoragePlacement("jug", "jugoil").setContainerItem(FoodListMFR.JUG_EMPTY);

		TALISMAN_LESSER = new ItemComponentMFR("talisman_lesser", 1);
		TALISMAN_GREATER = new ItemComponentMFR("talisman_greater", 3);

		BOLT = new ItemComponentMFR("bolt", 0);
		IRON_FRAME = new ItemComponentMFR("iron_frame", 0);
		IRON_STRUT = new ItemComponentMFR("iron_strut", 0);
		BRONZE_GEARS = new ItemComponentMFR("bronze_gears", 0);
		TUNGSTEN_GEARS = new ItemComponentMFR("tungsten_gears", 1);
		STEEL_TUBE = new ItemComponentMFR("steel_tube", 0);
		COGWORK_SHAFT = new ItemComponentMFR("cogwork_shaft", 1);

		COAL_PREP = new ItemComponentMFR("coal_prep", 0);

		INGOT_MOULD_FILLED = new ItemFilledMould();

		CROSSBOW_STOCK_WOOD = new ItemCrossbowPart("crossbow_stock_wood", "stock").addSpeed(1.0F).addRecoil(0F);
		CROSSBOW_STOCK_IRON = new ItemCrossbowPart("crossbow_stock_iron", "stock").addSpeed(1.0F).addRecoil(-2F).addDurability(150);
		CROSSBOW_HANDLE_WOOD = new ItemCrossbowPart("crossbow_handle_wood", "stock").addSpeed(0.5F).addRecoil(2F).addSpread(1.0F).setHandCrossbow(true);

		CROSS_ARMS_BASIC = new ItemCrossbowPart("cross_arms_basic", "mechanism").addPower(1.00F).addSpeed(0.50F).addRecoil(4F).addSpread(1.00F);
		CROSS_ARMS_LIGHT = new ItemCrossbowPart("cross_arms_light", "mechanism").addPower(0.85F).addSpeed(0.25F).addRecoil(2F).addSpread(0.50F);
		CROSS_ARMS_HEAVY = new ItemCrossbowPart("cross_arms_heavy", "mechanism").addPower(1.15F).addSpeed(1.00F).addRecoil(8F).addSpread(2.00F);
		CROSS_ARMS_ADVANCED = new ItemCrossbowPart("cross_arms_advanced", "mechanism").addPower(1.15F).addSpeed(1.00F).addRecoil(6F).addSpread(0.25F).addDurability(150);

		CROSS_BAYONET = new ItemCrossbowPart("cross_bayonet", "muzzle").addBash(4.0F).addRecoil(-1F).addSpeed(0.5F);
		CROSS_AMMO = new ItemCrossbowPart("cross_ammo", "mod").addCapacity(5).addSpread(2.00F);
		CROSS_SCOPE = new ItemCrossbowPart("cross_scope", "mod").setScope(0.75F);

		CHAIN_MESH = new ItemCustomComponent("chain_mesh", 1F, "metal").setStoragePlacement("sheet", "mail");
		SCALE_MESH = new ItemCustomComponent("scale_mesh", 1F, "metal").setStoragePlacement("sheet", "scale");
		SPLINT_MESH = new ItemCustomComponent("splint_mesh", 1F, "metal").setStoragePlacement("sheet", "splint");
		PLATE = new ItemCustomComponent("plate", 2F, "metal").setStoragePlacement("sheet", "plate");
		PLATE_HUGE = new ItemCustomComponent("plate_huge", 8F, "metal").setStoragePlacement("bigplate", "bigplate");
		METAL_HUNK = new ItemCustomComponent("metal_hunk", 0.25F, "metal");
		ARROWHEAD = new ItemCustomComponent("arrowhead", 1 / 4F, "metal");
		BODKIN_HEAD = new ItemCustomComponent("bodkin_head", 1 / 4F, "metal");
		BROAD_HEAD = new ItemCustomComponent("broad_head", 1 / 4F, "metal");
		COGWORK_ARMOUR = (ItemCustomComponent) new ItemCustomComponent("cogwork_armour", 30F, "metal").setCanDamage().setCreativeTab(CreativeTabMFR.tabGadget).setMaxStackSize(1);
		BAR = (ItemCustomComponent) new ItemCustomComponent("bar", 1F, "metal").setStoragePlacement("bar", "bar").setCreativeTab(CreativeTabMFR.tabMaterialsMFR);

		FLUX_POT = new ItemComponentMFR("flux_pot", 0).setContainerItem(CLAY_POT);
		COAL_FLUX = new ItemComponentMFR("coal_flux", 0);

		COPPER_COIN = new ItemComponentMFR("copper_coin", 0);
		SILVER_COIN = new ItemComponentMFR("silver_coin", 0);
		GOLD_COIN = new ItemComponentMFR("gold_coin", 0);

		HINGE = new ItemComponentMFR("hinge", 0);
		PLANK_CUT = new ItemComponentMFR("plank_cut").setCustom(1, "wood").setStoragePlacement("plank", "plankcut");
		PLANK_PANE = new ItemComponentMFR("plank_pane").setCustom(6, "wood").setStoragePlacement("sheet", "woodpane");

		COGWORK_PULLEY = new ItemComponentMFR("cogwork_pulley", 1).setCreativeTab(CreativeTabMFR.tabGadget);

		ANCIENT_JEWEL_MITHRIL = new ItemArtefact("ancient_jewel_mithril", 20, 2, ItemArtefact.MYTHIC, 2, "smeltMithril", "smeltMaster");
		ANCIENT_JEWEL_ADAMANT = new ItemArtefact("ancient_jewel_adamant", 20, 2, ItemArtefact.MYTHIC, 2, "smeltAdamantium", "smeltMaster");
		ANCIENT_JEWEL_MASTER =  new ItemArtefact("ancient_jewel_master", 30, 2, ItemArtefact.MYTHIC, 1, "smeltMaster");
		TRILOGY_JEWEL = new ItemArtefact("trilogy_jewel", 3, null, 1);
		SCHEMATIC_BOMB = new ItemArtefact("schematic_bomb", 50, 2, null, 1, "bombObsidian", "mineObsidian");
		SCHEMATIC_CROSSBOW = new ItemArtefact("schematic_crossbow", 50, 2, null, 1, "crossShaftAdvanced", "crossHeadAdvanced");
		SCHEMATIC_FORGE = new ItemArtefact("schematic_forge", 50, 2, null, 1, "advforge", "advcrucible");
		SCHEMATIC_GEARS =  new ItemArtefact("schematic_gears", 50, 2, null, 1, "cogArmour");
		SCHEMATIC_COGWORK = new ItemArtefact("schematic_cogwork", 50, 2, null, 1, "cogArmour");
		SCHEMATIC_ALLOYS = new ItemArtefact("schematic_alloy",50, 2, null, 1, "compPlate");

		ORNATE_ITEMS = new ItemSpecialDesign("ornate_items", 1, "ornate");
	}
	
	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		registry.register(CLAY_POT);
		registry.register(CLAY_POT_UNCOOKED);
		registry.register(INGOT_MOULD);
		registry.register(INGOT_MOULD_UNCOOKED);
		registry.register(PIE_TRAY_UNCOOKED);

		registry.register(COPPER_INGOT);
		registry.register(TIN_INGOT);
		registry.register(BRONZE_INGOT);
		registry.register(PIG_IRON_INGOT);
		registry.register(STEEL_INGOT);
		registry.register(ENCRUSTED_INGOT);
		registry.register(BLACK_STEEL_WEAK_INGOT);
		registry.register(BLACK_STEEL_INGOT);
		registry.register(SILVER_INGOT);
		registry.register(RED_STEEL_WEAK_INGOT);
		registry.register(RED_STEEL_INGOT);
		registry.register(BLUE_STEEL_WEAK_INGOT);
		registry.register(BLUE_STEEL_INGOT);
		registry.register(ADAMANTIUM_INGOT);
		registry.register(MITHRIL_INGOT);
		registry.register(IGNOTUMITE_INGOT);
		registry.register(MITHIUM_INGOT);
		registry.register(ENDER_INGOT);
		registry.register(TUNGSTEN_INGOT);
		registry.register(OBSIDIAN_INGOT);

		registry.register(INGOT_COMPOSITE_ALLOY);

		registry.register(PLANK);
		registry.register(VINE);
		registry.register(SHARP_ROCK);

		registry.register(FLUX);
		registry.register(FLUX_STRONG);

		registry.register(COAL_DUST);
		registry.register(NITRE);
		registry.register(SULFUR);
		registry.register(IRON_PREP);
		registry.register(BLACKPOWDER);
		registry.register(BLACKPOWDER_ADVANCED);
		registry.register(FLETCHING);
		registry.register(SHRAPNEL);
		registry.register(MAGMA_CREAM_REFINED);
		registry.register(BOMB_FUSE);
		registry.register(BOMB_FUSE_LONG);
		registry.register(BOMB_CASING_UNCOOKED);
		registry.register(BOMB_CASING);
		registry.register(MINE_CASING_UNCOOKED);
		registry.register(MINE_CASING);
		registry.register(BOMB_CASING_IRON);
		registry.register(MINE_CASING_IRON);
		registry.register(BOMB_CASING_OBSIDIAN);
		registry.register(MINE_CASING_OBSIDIAN);
		registry.register(BOMB_CASING_CRYSTAL);
		registry.register(MINE_CASING_CRYSTAL);
		registry.register(BOMB_CASING_ARROW);
		registry.register(BOMB_CASING_BOLT);

		registry.register(COKE);
		registry.register(DIAMOND_SHARDS);

		registry.register(CLAY_BRICK);
		registry.register(KAOLINITE);
		registry.register(KAOLINITE_DUST);
		registry.register(FIRECLAY);
		registry.register(FIRECLAY_BRICK);
		registry.register(STRONG_BRICK);

		registry.register(HIDE_SMALL);
		registry.register(HIDE_MEDIUM);
		registry.register(HIDE_LARGE);
		registry.register(RAWHIDE_SMALL);
		registry.register(RAWHIDE_MEDIUM);
		registry.register(RAWHIDE_LARGE);

		registry.register(DRAGON_HEART);

		registry.register(LEATHER_STRIP);
		registry.register(NAIL);
		registry.register(RIVET);
		registry.register(THREAD);
		registry.register(OBSIDIAN_ROCK);

		registry.register(ORE_COPPER);
		registry.register(ORE_TIN);
		registry.register(ORE_IRON);
		registry.register(ORE_SILVER);
		registry.register(ORE_GOLD);
		registry.register(ORE_TUNGSTEN);

		registry.register(HOT_ITEM);

		registry.register(PLANT_OIL);

		registry.register(TALISMAN_LESSER);
		registry.register(TALISMAN_GREATER);

		registry.register(BOLT);
		registry.register(IRON_FRAME);
		registry.register(IRON_STRUT);
		registry.register(BRONZE_GEARS);
		registry.register(TUNGSTEN_GEARS);
		registry.register(STEEL_TUBE);
		registry.register(COGWORK_SHAFT);

		registry.register(COAL_PREP);

		registry.register(INGOT_MOULD_FILLED);

		registry.register(CROSSBOW_STOCK_WOOD);
		registry.register(CROSSBOW_STOCK_IRON);
		registry.register(CROSSBOW_HANDLE_WOOD);

		registry.register(CROSS_ARMS_BASIC);
		registry.register(CROSS_ARMS_LIGHT);
		registry.register(CROSS_ARMS_HEAVY);
		registry.register(CROSS_ARMS_ADVANCED);

		registry.register(CROSS_BAYONET);
		registry.register(CROSS_AMMO);
		registry.register(CROSS_SCOPE);

		registry.register(CHAIN_MESH);
		registry.register(SCALE_MESH);
		registry.register(SPLINT_MESH);
		registry.register(PLATE);
		registry.register(PLATE_HUGE);
		registry.register(METAL_HUNK);
		registry.register(ARROWHEAD);
		registry.register(BODKIN_HEAD);
		registry.register(BROAD_HEAD);
		registry.register(COGWORK_ARMOUR);
		registry.register(BAR);

		registry.register(FLUX_POT);
		registry.register(COAL_FLUX);

		registry.register(COPPER_COIN);
		registry.register(SILVER_COIN);
		registry.register(GOLD_COIN);

		registry.register(HINGE);
		registry.register(PLANK_CUT);
		registry.register(PLANK_PANE);

		registry.register(COGWORK_PULLEY);

		registry.register(ANCIENT_JEWEL_MITHRIL);
		registry.register(ANCIENT_JEWEL_ADAMANT);
		registry.register(ANCIENT_JEWEL_MASTER);
		registry.register(TRILOGY_JEWEL);
		registry.register(SCHEMATIC_BOMB);
		registry.register(SCHEMATIC_CROSSBOW);
		registry.register(SCHEMATIC_FORGE);
		registry.register(SCHEMATIC_GEARS);
		registry.register(SCHEMATIC_COGWORK);
		registry.register(SCHEMATIC_ALLOYS);

		registry.register(ORNATE_ITEMS);
	}
	
	
	public static void load() {
		INGOTS = new ItemComponentMFR[]{
				COPPER_INGOT,
				TIN_INGOT,
				BRONZE_INGOT,
				PIG_IRON_INGOT,
				STEEL_INGOT,
				ENCRUSTED_INGOT,
				BLACK_STEEL_WEAK_INGOT,
				BLACK_STEEL_INGOT,
				SILVER_INGOT,
				RED_STEEL_WEAK_INGOT,
				RED_STEEL_INGOT,
				BLUE_STEEL_WEAK_INGOT,
				BLUE_STEEL_INGOT,
				ADAMANTIUM_INGOT,
				MITHRIL_INGOT,
				IGNOTUMITE_INGOT,
				MITHIUM_INGOT,
				ENDER_INGOT,
				TUNGSTEN_INGOT,
				OBSIDIAN_INGOT
		};

		Items.POTIONITEM.setContainerItem(Items.GLASS_BOTTLE);
		GameRegistry.registerFuelHandler(new FuelHandlerMF());
		MineFantasyRebornAPI.registerFuelHandler(new AdvancedFuelHandlerMF());

		addRandomDrops();
		initFuels();
	}

	private static void initFuels() {
		MineFantasyFuels.addForgeFuel(new ItemStack(Items.COAL, 1, 0), 900, 1500);// 1500C , 45s
		MineFantasyFuels.addForgeFuel(new ItemStack(Items.COAL, 1, 1), 1200, 1800);// 1800C , 1m
		MineFantasyFuels.addForgeFuel(Items.BLAZE_POWDER, 200, 3000, true);// 3000C , 10s
		MineFantasyFuels.addForgeFuel(Items.BLAZE_ROD, 300, 3000, true);// 3000C , 15s
		MineFantasyFuels.addForgeFuel(Items.FIRE_CHARGE, 1200, 3500, true);// 3500C , 1m
		MineFantasyFuels.addForgeFuel(Items.LAVA_BUCKET, 2400, 5000, true);// 5000C , 2m
		MineFantasyFuels.addForgeFuel(Items.MAGMA_CREAM, 2400, 4000, true, true);// 4000C , 2m

		MineFantasyFuels.addForgeFuel(ComponentListMFR.COKE, 1200, 2500, false, true);// 2500C , 1m
		MineFantasyFuels.addForgeFuel(ComponentListMFR.MAGMA_CREAM_REFINED, 2400, 5000, true, true);// 5000C , 2m

		MineFantasyFuels.addForgeFuel(ComponentListMFR.COAL_DUST, 1200, 180);// 180C , 60s
	}

	private static void addRandomDrops() {
		RandomOre.addOre(new ItemStack(KAOLINITE), 1.5F, Blocks.STONE, -1, 32, 128, false);
		RandomOre.addOre(new ItemStack(FLUX), 2F, Blocks.STONE, -1, 0, 128, false);
		RandomOre.addOre(new ItemStack(FLUX_STRONG), 1F, Blocks.STONE, 2, 0, 128, false);
		RandomOre.addOre(new ItemStack(FLUX), 20F, BlockListMFR.LIMESTONE, 0, -1, 0, 256, true);
		RandomOre.addOre(new ItemStack(FLUX_STRONG), 10F, BlockListMFR.LIMESTONE, 0, 2, 0, 256, true);
		RandomOre.addOre(new ItemStack(Items.COAL), 2F, Blocks.STONE, -1, 0, 128, false);
		RandomOre.addOre(new ItemStack(SULFUR), 2F, Blocks.STONE, -1, 0, 16, false);
		RandomOre.addOre(new ItemStack(NITRE), 3F, Blocks.STONE, -1, 0, 64, false);
		RandomOre.addOre(new ItemStack(Items.REDSTONE), 5F, Blocks.STONE, 2, 0, 16, false);
		RandomOre.addOre(new ItemStack(Items.FLINT), 1F, Blocks.STONE, -1, 0, 64, false);
		RandomOre.addOre(new ItemStack(DIAMOND_SHARDS), 0.2F, Blocks.STONE, 2, 0, 16, false);
		RandomOre.addOre(new ItemStack(Items.QUARTZ), 0.5F, Blocks.STONE, 3, 0, 16, false);

		RandomOre.addOre(new ItemStack(SULFUR), 10F, Blocks.NETHERRACK, -1, 0, 512, false);
		RandomOre.addOre(new ItemStack(Items.GLOWSTONE_DUST), 5F, Blocks.NETHERRACK, -1, 0, 512, false);
		RandomOre.addOre(new ItemStack(Items.QUARTZ), 5F, Blocks.NETHERRACK, -1, 0, 512, false);
		RandomOre.addOre(new ItemStack(Items.BLAZE_POWDER), 5F, Blocks.NETHERRACK, -1, 0, 512, false);
		RandomOre.addOre(new ItemStack(Items.NETHER_WART), 1F, Blocks.NETHERRACK, -1, 0, 512, false);
		RandomOre.addOre(new ItemStack(Items.NETHER_STAR), 0.01F, Blocks.NETHERRACK, -1, 0, 512, false);

		RandomDigs.addOre(new ItemStack(Blocks.SKULL, 1, 1), 0.1F, Blocks.SOUL_SAND, 3, 0, 256, false);
		RandomDigs.addOre(new ItemStack(Items.BONE), 5F, Blocks.DIRT, -1, 0, 256, false);
		RandomDigs.addOre(new ItemStack(Items.ROTTEN_FLESH), 2F, Blocks.DIRT, -1, 0, 256, false);
		RandomDigs.addOre(new ItemStack(Items.COAL, 1, 1), 1F, Blocks.DIRT, -1, 32, 64, false);

		RandomDigs.addOre(new ItemStack(Items.MELON_SEEDS), 5F, Blocks.GRASS, -1, 0, 256, false);
		RandomDigs.addOre(new ItemStack(Items.PUMPKIN_SEEDS), 8F, Blocks.GRASS, -1, 0, 256, false);

		RandomOre.addOre(new ItemStack(ORE_COPPER), 4F, Blocks.STONE, 0, 48, 96, false);
		RandomOre.addOre(new ItemStack(ORE_TIN), 2F, Blocks.STONE, 0, 48, 96, false);
		RandomOre.addOre(new ItemStack(ORE_IRON), 5F, Blocks.STONE, 0, 0, 64, false);
		RandomOre.addOre(new ItemStack(ORE_SILVER), 1.5F, Blocks.STONE, 0, 0, 32, false);
		RandomOre.addOre(new ItemStack(ORE_GOLD), 1F, Blocks.STONE, 0, 0, 32, false);

		RandomOre.addOre(new ItemStack(ORE_TUNGSTEN), 2F, Blocks.STONE, 3, 0, 16, false, "tungsten");
	}

	public static ItemStack bar(String material) {
		return bar(material, 1);
	}

	public static ItemStack bar(String material, int stackSize) {
		return BAR.createComm(material, stackSize);
	}
}

