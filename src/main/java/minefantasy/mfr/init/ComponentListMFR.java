package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.MineFantasyFuels;
import minefantasy.mfr.api.mining.RandomDigs;
import minefantasy.mfr.api.mining.RandomOre;
import minefantasy.mfr.item.AdvancedFuelHandlerMF;
import minefantasy.mfr.item.FuelHandlerMF;
import minefantasy.mfr.item.ItemArtefact;
import minefantasy.mfr.item.ItemComponentMFR;
import minefantasy.mfr.item.ItemFilledMould;
import minefantasy.mfr.item.ItemHide;
import minefantasy.mfr.item.ItemBowl;
import minefantasy.mfr.item.ItemRawOre;
import minefantasy.mfr.item.ItemSpecialDesign;
import minefantasy.mfr.item.custom.ItemCustomComponent;
import minefantasy.mfr.item.gadget.ItemBombComponent;
import minefantasy.mfr.item.gadget.ItemCrossbowPart;
import minefantasy.mfr.item.heatable.ItemHeated;
import minefantasy.mfr.material.BaseMaterialMFR;
import minefantasy.mfr.material.WoodMaterial;
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

	public static final Item CLAY_POT = Utils.nullValue();
	public static final Item CLAY_POT_UNCOOKED = Utils.nullValue();
	public static final Item INGOT_MOULD = Utils.nullValue();
	public static final Item INGOT_MOULD_UNCOOKED = Utils.nullValue();
	public static final Item PIE_TRAY_UNCOOKED = Utils.nullValue();

	public static final ItemComponentMFR COPPER_INGOT = Utils.nullValue();
	public static final ItemComponentMFR TIN_INGOT = Utils.nullValue();
	public static final ItemComponentMFR BRONZE_INGOT = Utils.nullValue();
	public static final ItemComponentMFR PIG_IRON_INGOT = Utils.nullValue();
	public static final ItemComponentMFR STEEL_INGOT = Utils.nullValue();
	public static final ItemComponentMFR ENCRUSTED_INGOT = Utils.nullValue();
	public static final ItemComponentMFR BLACK_STEEL_WEAK_INGOT = Utils.nullValue();
	public static final ItemComponentMFR BLACK_STEEL_INGOT = Utils.nullValue();
	public static final ItemComponentMFR SILVER_INGOT = Utils.nullValue();
	public static final ItemComponentMFR RED_STEEL_WEAK_INGOT = Utils.nullValue();
	public static final ItemComponentMFR RED_STEEL_INGOT = Utils.nullValue();
	public static final ItemComponentMFR BLUE_STEEL_WEAK_INGOT = Utils.nullValue();
	public static final ItemComponentMFR BLUE_STEEL_INGOT = Utils.nullValue();
	public static final ItemComponentMFR ADAMANTIUM_INGOT = Utils.nullValue();
	public static final ItemComponentMFR MITHRIL_INGOT = Utils.nullValue();
	public static final ItemComponentMFR IGNOTUMITE_INGOT = Utils.nullValue();
	public static final ItemComponentMFR MITHIUM_INGOT = Utils.nullValue();
	public static final ItemComponentMFR ENDER_INGOT = Utils.nullValue();
	public static final ItemComponentMFR TUNGSTEN_INGOT = Utils.nullValue();
	public static final ItemComponentMFR OBSIDIAN_INGOT = Utils.nullValue();
	
	public static final Item INGOT_COMPOSITE_ALLOY = Utils.nullValue();

	public static final ItemComponentMFR PLANK = Utils.nullValue();
	public static final Item VINE = Utils.nullValue();
	public static final Item SHARP_ROCK = Utils.nullValue();

	public static final Item FLUX = Utils.nullValue();
	public static final Item FLUX_STRONG = Utils.nullValue();

	public static final Item COAL_DUST = Utils.nullValue();
	public static final Item NITRE = Utils.nullValue();
	public static final Item SULFUR = Utils.nullValue();
	public static final Item IRON_PREP = Utils.nullValue();
	public static final Item BLACKPOWDER = Utils.nullValue();
	public static final Item BLACKPOWDER_ADVANCED = Utils.nullValue();
	public static final Item FLETCHING = Utils.nullValue();
	public static final Item SHRAPNEL = Utils.nullValue();
	public static final Item MAGMA_CREAM_REFINED = Utils.nullValue();
	public static final Item BOMB_FUSE = Utils.nullValue();
	public static final Item BOMB_FUSE_LONG = Utils.nullValue();
	public static final Item BOMB_CASING_UNCOOKED = Utils.nullValue();
	public static final Item BOMB_CASING = Utils.nullValue();
	public static final Item MINE_CASING_UNCOOKED = Utils.nullValue();
	public static final Item MINE_CASING = Utils.nullValue();
	public static final Item BOMB_CASING_IRON = Utils.nullValue();
	public static final Item MINE_CASING_IRON = Utils.nullValue();
	public static final Item BOMB_CASING_OBSIDIAN = Utils.nullValue();
	public static final Item MINE_CASING_OBSIDIAN = Utils.nullValue();
	public static final Item BOMB_CASING_CRYSTAL = Utils.nullValue();
	public static final Item MINE_CASING_CRYSTAL = Utils.nullValue();
	public static final Item BOMB_CASING_ARROW = Utils.nullValue();
	public static final Item BOMB_CASING_BOLT = Utils.nullValue();

	public static final Item COKE = Utils.nullValue();
	public static final Item DIAMOND_SHARDS = Utils.nullValue();

	public static final Item CLAY_BRICK = Utils.nullValue();
	public static final Item KAOLINITE = Utils.nullValue();
	public static final Item KAOLINITE_DUST = Utils.nullValue();
	public static final Item FIRECLAY = Utils.nullValue();
	public static final Item FIRECLAY_BRICK = Utils.nullValue();
	public static final Item STRONG_BRICK = Utils.nullValue();

	public static final Item HIDE_SMALL = Utils.nullValue();
	public static final Item HIDE_MEDIUM = Utils.nullValue();
	public static final Item HIDE_LARGE = Utils.nullValue();
	public static final Item RAWHIDE_SMALL = Utils.nullValue();
	public static final Item RAWHIDE_MEDIUM = Utils.nullValue();
	public static final Item RAWHIDE_LARGE = Utils.nullValue();

	public static final Item DRAGON_HEART = Utils.nullValue();

	public static final Item LEATHER_STRIP = Utils.nullValue();
	public static final Item NAIL = Utils.nullValue();
	public static final Item RIVET = Utils.nullValue();
	public static final Item THREAD = Utils.nullValue();
	public static final Item OBSIDIAN_ROCK = Utils.nullValue();

	public static final Item ORE_COPPER = Utils.nullValue();
	public static final Item ORE_TIN = Utils.nullValue();
	public static final Item ORE_IRON = Utils.nullValue();
	public static final Item ORE_SILVER = Utils.nullValue();
	public static final Item ORE_GOLD = Utils.nullValue();
	public static final Item ORE_TUNGSTEN = Utils.nullValue();

	public static final Item HOT_ITEM = Utils.nullValue();

	public static final Item PLANT_OIL = Utils.nullValue();

	public static final Item TALISMAN_LESSER = Utils.nullValue();
	public static final Item TALISMAN_GREATER = Utils.nullValue();

	public static final Item BOLT = Utils.nullValue();
	public static final Item IRON_FRAME = Utils.nullValue();
	public static final Item IRON_STRUT = Utils.nullValue();
	public static final Item BRONZE_GEARS = Utils.nullValue();
	public static final Item TUNGSTEN_GEARS = Utils.nullValue();
	public static final Item STEEL_TUBE = Utils.nullValue();
	public static final Item COGWORK_SHAFT = Utils.nullValue();

	public static final Item COAL_PREP = Utils.nullValue();

	public static final Item INGOT_MOULD_FILLED = Utils.nullValue();

	public static final Item CROSSBOW_STOCK_WOOD = Utils.nullValue();
	public static final Item CROSSBOW_STOCK_IRON = Utils.nullValue();
	public static final Item CROSSBOW_HANDLE_WOOD = Utils.nullValue();

	public static final Item CROSS_ARMS_BASIC = Utils.nullValue();
	public static final Item CROSS_ARMS_LIGHT = Utils.nullValue();
	public static final Item CROSS_ARMS_HEAVY = Utils.nullValue();
	public static final Item CROSS_ARMS_ADVANCED = Utils.nullValue();

	public static final Item CROSS_BAYONET = Utils.nullValue();
	public static final Item CROSS_AMMO = Utils.nullValue();
	public static final Item CROSS_SCOPE = Utils.nullValue();

	public static final ItemCustomComponent CHAIN_MESH = Utils.nullValue();
	public static final ItemCustomComponent SCALE_MESH = Utils.nullValue();
	public static final ItemCustomComponent SPLINT_MESH = Utils.nullValue();
	public static final ItemCustomComponent PLATE = Utils.nullValue();
	public static final ItemCustomComponent PLATE_HUGE = Utils.nullValue();
	public static final ItemCustomComponent METAL_HUNK = Utils.nullValue();
	public static final ItemCustomComponent ARROWHEAD = Utils.nullValue();
	public static final ItemCustomComponent BODKIN_HEAD = Utils.nullValue();
	public static final ItemCustomComponent BROAD_HEAD = Utils.nullValue();
	public static final ItemCustomComponent COGWORK_ARMOUR = Utils.nullValue();
	public static final ItemCustomComponent BAR = Utils.nullValue();

	public static final Item FLUX_POT = Utils.nullValue();
	public static final Item COAL_FLUX = Utils.nullValue();

	public static final Item COPPER_COIN = Utils.nullValue();
	public static final Item SILVER_COIN = Utils.nullValue();
	public static final Item GOLD_COIN = Utils.nullValue();

	public static final Item HINGE = Utils.nullValue();
	public static final Item PLANK_CUT = Utils.nullValue();
	public static final Item PLANK_PANE = Utils.nullValue();

	public static final Item COGWORK_PULLEY = Utils.nullValue();

	public static final Item ARTEFACTS = Utils.nullValue();

	public static final Item ORNATE_ITEMS = Utils.nullValue();

	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		registry.register(new ItemBowl("clay_pot").setStoragePlacement("pot", "pot"));
		registry.register(new ItemComponentMFR("clay_pot_uncooked", 0));
		registry.register(new ItemComponentMFR("ingot_mould").setStoragePlacement("bar", "mould"));
		registry.register(new ItemComponentMFR("ingot_mould_uncooked", 0));
		registry.register(new ItemComponentMFR("pie_tray_uncooked", 0));

		registry.register(new ItemComponentMFR("copper_ingot", BaseMaterialMFR.getMaterial("copper").rarity));
		registry.register(new ItemComponentMFR("tin_ingot", BaseMaterialMFR.getMaterial("tin").rarity));
		registry.register(new ItemComponentMFR("bronze_ingot", BaseMaterialMFR.getMaterial("bronze").rarity));
		registry.register(new ItemComponentMFR("pig_iron_ingot", BaseMaterialMFR.getMaterial("pig_iron").rarity));
		registry.register(new ItemComponentMFR("steel_ingot", BaseMaterialMFR.getMaterial("steel").rarity));
		registry.register(new ItemComponentMFR("encrusted_ingot", BaseMaterialMFR.getMaterial("encrusted").rarity));
		registry.register(new ItemComponentMFR("black_steel_weak_ingot", BaseMaterialMFR.getMaterial("weak_black_steel").rarity));
		registry.register(new ItemComponentMFR("black_steel_ingot", BaseMaterialMFR.getMaterial("black_steel").rarity));
		registry.register(new ItemComponentMFR("silver_ingot", BaseMaterialMFR.getMaterial("silver").rarity));
		registry.register(new ItemComponentMFR("red_steel_weak_ingot", BaseMaterialMFR.getMaterial("weak_red_steel").rarity));
		registry.register(new ItemComponentMFR("red_steel_ingot", BaseMaterialMFR.getMaterial("red_steel").rarity));
		registry.register(new ItemComponentMFR("blue_steel_weak_ingot", BaseMaterialMFR.getMaterial("weak_blue_steel").rarity));
		registry.register(new ItemComponentMFR("blue_steel_ingot", BaseMaterialMFR.getMaterial("blue_steel").rarity));
		registry.register(new ItemComponentMFR("adamantium_ingot", BaseMaterialMFR.getMaterial("adamantium").rarity));
		registry.register(new ItemComponentMFR("mithril_ingot", BaseMaterialMFR.getMaterial("mithril").rarity));
		registry.register(new ItemComponentMFR("ignotumite_ingot", BaseMaterialMFR.getMaterial("ignotumite").rarity));
		registry.register(new ItemComponentMFR("mithium_ingot", BaseMaterialMFR.getMaterial("mithium").rarity));
		registry.register(new ItemComponentMFR("ender_ingot", BaseMaterialMFR.getMaterial("enderforge").rarity));
		registry.register(new ItemComponentMFR("tungsten_ingot", BaseMaterialMFR.getMaterial("tungsten").rarity));
		registry.register(new ItemComponentMFR("obsidian_ingot", BaseMaterialMFR.getMaterial("obsidian").rarity));

		registry.register(new ItemComponentMFR("ingot_composite_alloy", 1));

		registry.register(new ItemComponentMFR("plank").setCustom(1, "wood").setStoragePlacement("plank","plank"));
		registry.register(new ItemComponentMFR("vine", -1));
		registry.register(new ItemComponentMFR("sharp_rock", -1));

		registry.register(new ItemComponentMFR("flux", 0));
		registry.register(new ItemComponentMFR("flux_strong", 0));

		registry.register(new ItemComponentMFR("coal_dust", 0).setContainerItem(CLAY_POT));
		registry.register(new ItemComponentMFR("nitre", 0));
		registry.register(new ItemComponentMFR("sulfur", 0));
		registry.register(new ItemComponentMFR("iron_prep", 0));
		registry.register(new ItemBombComponent("blackpowder", 0, "powder", 0).setContainerItem(CLAY_POT));
		registry.register(new ItemBombComponent("blackpowder_advanced", 1, "powder", 1).setContainerItem(CLAY_POT));
		registry.register(new ItemComponentMFR("fletching", 0));
		registry.register(new ItemBombComponent("shrapnel", 0, "filling", 1).setContainerItem(ComponentListMFR.CLAY_POT));
		registry.register(new ItemBombComponent("magma_cream_refined", 1, "filling", 2).setContainerItem(CLAY_POT));
		registry.register(new ItemBombComponent("bomb_fuse", 0, "fuse", 0));
		registry.register(new ItemBombComponent("bomb_fuse_long", 0, "fuse", 1));
		registry.register(new ItemComponentMFR("bomb_casing_uncooked", 0));
		registry.register(new ItemBombComponent("bomb_casing", 0, "bombcase", 0));
		registry.register(new ItemComponentMFR("mine_casing_uncooked", 0));
		registry.register(new ItemBombComponent("mine_casing", 0, "minecase", 0));
		registry.register(new ItemBombComponent("bomb_casing_iron", 0, "bombcase", 1));
		registry.register(new ItemBombComponent("mine_casing_iron", 0, "minecase", 1));
		registry.register(new ItemBombComponent("bomb_casing_obsidian", 1, "bombcase", 2));
		registry.register(new ItemBombComponent("mine_casing_obsidian", 1, "minecase", 2));
		registry.register(new ItemBombComponent("bomb_casing_crystal", 1, "bombcase", 3));
		registry.register(new ItemBombComponent("mine_casing_crystal", 1, "minecase", 3));
		registry.register(new ItemBombComponent("bomb_casing_arrow", 1, "arrow", 0));
		registry.register(new ItemBombComponent("bomb_casing_bolt", 1, "bolt", 0));

		registry.register(new ItemComponentMFR("coke", 1));
		registry.register(new ItemComponentMFR("diamond_shards", 0));

		registry.register(new ItemComponentMFR("clay_brick", 0));
		registry.register(new ItemComponentMFR("kaolinite", 0));
		registry.register(new ItemComponentMFR("kaolinite_dust", 0).setContainerItem(CLAY_POT));
		registry.register(new ItemComponentMFR("fireclay", 0));
		registry.register(new ItemComponentMFR("fireclay_brick", 0));
		registry.register(new ItemComponentMFR("strong_brick", 0).setStoragePlacement("bar", "firebrick"));

		registry.register(new ItemComponentMFR("hide_small", 0));
		registry.register(new ItemComponentMFR("hide_medium", 0));
		registry.register(new ItemComponentMFR("hide_large", 0));
		registry.register(new ItemHide("rawhide_small", HIDE_SMALL, 1.0F));
		registry.register(new ItemHide("rawhide_medium", HIDE_MEDIUM, 1.5F));
		registry.register(new ItemHide("rawhide_large", HIDE_LARGE, 3.0F));

		registry.register(new ItemSpecialDesign("dragon_heart", 1, "dragon"));

		registry.register(new ItemComponentMFR("leather_strip", 0));
		registry.register(new ItemComponentMFR("nail", 0));
		registry.register(new ItemComponentMFR("rivet", 0));
		registry.register(new ItemComponentMFR("thread", 0));
		registry.register(new ItemComponentMFR("obsidian_rock", 0));

		registry.register(new ItemRawOre("ore_copper", -1));
		registry.register(new ItemRawOre("ore_tin", -1));
		registry.register(new ItemRawOre("ore_iron", 0));
		registry.register(new ItemRawOre("ore_silver", 0));
		registry.register(new ItemRawOre("ore_gold", 0));
		registry.register(new ItemRawOre("ore_tungsten", 1));

		registry.register(new ItemHeated());

		registry.register(new ItemComponentMFR("plant_oil", 0).setStoragePlacement("jug", "jugoil").setContainerItem(FoodListMFR.JUG_EMPTY));

		registry.register(new ItemComponentMFR("talisman_lesser", 1));
		registry.register(new ItemComponentMFR("talisman_greater", 3));

		registry.register(new ItemComponentMFR("bolt", 0));
		registry.register(new ItemComponentMFR("iron_frame", 0));
		registry.register(new ItemComponentMFR("iron_strut", 0));
		registry.register(new ItemComponentMFR("bronze_gears", 0));
		registry.register(new ItemComponentMFR("tungsten_gears", 1));
		registry.register(new ItemComponentMFR("steel_tube", 0));
		registry.register(new ItemComponentMFR("cogwork_shaft", 1));

		registry.register(new ItemComponentMFR("coal_prep", 0));

		registry.register(new ItemFilledMould());

		registry.register(new ItemCrossbowPart("crossbow_stock_wood", "stock").addSpeed(1.0F).addRecoil(0F));
		registry.register(new ItemCrossbowPart("crossbow_stock_iron", "stock").addSpeed(1.0F).addRecoil(-2F).addDurability(150));
		registry.register(new ItemCrossbowPart("crossbow_handle_wood", "stock").addSpeed(0.5F).addRecoil(2F).addSpread(1.0F).setHandCrossbow(true));

		registry.register(new ItemCrossbowPart("cross_arms_basic", "mechanism").addPower(1.00F).addSpeed(0.50F).addRecoil(4F).addSpread(1.00F));
		registry.register(new ItemCrossbowPart("cross_arms_light", "mechanism").addPower(0.85F).addSpeed(0.25F).addRecoil(2F).addSpread(0.50F));
		registry.register(new ItemCrossbowPart("cross_arms_heavy", "mechanism").addPower(1.15F).addSpeed(1.00F).addRecoil(8F).addSpread(2.00F));
		registry.register(new ItemCrossbowPart("cross_arms_advanced", "mechanism").addPower(1.15F).addSpeed(1.00F).addRecoil(6F).addSpread(0.25F).addDurability(150));

		registry.register(new ItemCrossbowPart("cross_bayonet", "muzzle").addBash(4.0F).addRecoil(-1F).addSpeed(0.5F));
		registry.register(new ItemCrossbowPart("cross_ammo", "mod").addCapacity(5).addSpread(2.00F));
		registry.register(new ItemCrossbowPart("cross_scope", "mod").setScope(0.75F));

		registry.register(new ItemCustomComponent("chain_mesh", 1F, "metal").setStoragePlacement("sheet", "mail"));
		registry.register(new ItemCustomComponent("scale_mesh", 1F, "metal").setStoragePlacement("sheet", "scale"));
		registry.register(new ItemCustomComponent("splint_mesh", 1F, "metal").setStoragePlacement("sheet", "splint"));
		registry.register(new ItemCustomComponent("plate", 2F, "metal").setStoragePlacement("sheet", "plate"));
		registry.register(new ItemCustomComponent("plate_huge", 8F, "metal").setStoragePlacement("bigplate", "bigplate"));
		registry.register(new ItemCustomComponent("metal_hunk", 0.25F, "metal"));
		registry.register(new ItemCustomComponent("arrowhead", 1 / 4F, "metal"));
		registry.register(new ItemCustomComponent("bodkin_head", 1 / 4F, "metal"));
		registry.register(new ItemCustomComponent("broad_head", 1 / 4F, "metal"));
		registry.register(new ItemCustomComponent("cogwork_armour", 30F, "metal").setCanDamage().setCreativeTab(CreativeTabMFR.tabGadget).setMaxStackSize(1));
		registry.register(new ItemCustomComponent("bar", 1F, "metal").setStoragePlacement("bar", "bar").setCreativeTab(CreativeTabMFR.tabMaterialsMFR));

		registry.register(new ItemComponentMFR("flux_pot", 0).setContainerItem(CLAY_POT));
		registry.register(new ItemComponentMFR("coal_flux", 0));

		registry.register(new ItemComponentMFR("copper_coin", 0));
		registry.register(new ItemComponentMFR("silver_coin", 0));
		registry.register(new ItemComponentMFR("gold_coin", 0));

		registry.register(new ItemComponentMFR("hinge", 0));
		registry.register(new ItemComponentMFR("plank_cut").setCustom(1, "wood").setStoragePlacement("plank", "plankcut"));
		registry.register(new ItemComponentMFR("plank_pane").setCustom(6, "wood").setStoragePlacement("sheet", "woodpane"));

		registry.register(new ItemComponentMFR("cogwork_pulley", 1).setCreativeTab(CreativeTabMFR.tabGadget));

		registry.register(new ItemArtefact("artefacts"));

		registry.register(new ItemSpecialDesign("ornate_items", 1, "ornate"));
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

		WoodMaterial.init();
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

