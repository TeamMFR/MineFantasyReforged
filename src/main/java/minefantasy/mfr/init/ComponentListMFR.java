package minefantasy.mfr.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.MineFantasyFuels;
import minefantasy.mfr.api.mining.RandomDigs;
import minefantasy.mfr.api.mining.RandomOre;
import minefantasy.mfr.item.*;
import minefantasy.mfr.item.custom.ItemCustomComponent;
import minefantasy.mfr.item.gadget.ItemBombComponent;
import minefantasy.mfr.item.gadget.ItemCrossbowPart;
import minefantasy.mfr.item.heatable.ItemHeated;
import minefantasy.mfr.material.BaseMaterialMFR;
import minefantasy.mfr.material.WoodMaterial;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * @author Anonymous Productions
 */
public class ComponentListMFR {
	public static final String[] ingotMats = new String[]{"copper", "tin", "bronze", "pigiron", "steel", "encrusted", "blacksteelweak", "blacksteel", "silver", "redsteelweak", "redsteel", "bluesteelweak", "bluesteel", "adamantium", "mithril", "ignotumite", "mithium", "ender", "tungsten", "obsidian"};

	public static Item clay_pot = new ItemMFBowl("clay_pot").setStoragePlacement("pot", "pot");
	public static Item clay_pot_uncooked = new ItemComponentMFR("clay_pot_uncooked", 0);
	public static Item ingot_mould = new ItemComponentMFR("ingot_mould").setStoragePlacement("bar", "mould");
	public static Item ingot_mould_uncooked = new ItemComponentMFR("ingot_mould_uncooked", 0);
	public static Item pie_tray_uncooked = new ItemComponentMFR("pie_tray_uncooked", 0);

	public static ItemComponentMFR[] ingots = new ItemComponentMFR[ingotMats.length];

	public static ItemComponentMFR plank = new ItemComponentMFR("plank").setCustom(1, "wood").setStoragePlacement("plank","plank");
	public static Item vine = new ItemComponentMFR("vine", -1);
	public static Item sharp_rock = new ItemComponentMFR("sharp_rock", -1);

	public static Item flux = new ItemComponentMFR("flux", 0);
	public static Item flux_strong = new ItemComponentMFR("flux_strong", 0);

	public static Item coalDust = new ItemComponentMFR("coalDust", 0).setContainerItem(clay_pot);
	public static Item nitre = new ItemComponentMFR("nitre", 0);
	public static Item sulfur = new ItemComponentMFR("sulfur", 0);
	public static Item iron_prep = new ItemComponentMFR("iron_prep", 0);
	public static Item blackpowder = new ItemBombComponent("blackpowder", 0, "powder", 0).setContainerItem(clay_pot);
	public static Item blackpowder_advanced = new ItemBombComponent("blackpowder_advanced", 1, "powder", 1).setContainerItem(clay_pot);
	public static Item fletching = new ItemComponentMFR("fletching", 0);
	public static Item shrapnel = new ItemBombComponent("shrapnel", 0, "filling", 1).setContainerItem(ComponentListMFR.clay_pot);
	public static Item magma_cream_refined = new ItemBombComponent("magma_cream_refined", 1, "filling", 2).setContainerItem(clay_pot);
	public static Item bomb_fuse = new ItemBombComponent("bomb_fuse", 0, "fuse", 0);
	public static Item bomb_fuse_long = new ItemBombComponent("bomb_fuse_long", 0, "fuse", 1);
	public static Item bomb_casing_uncooked = new ItemComponentMFR("bomb_casing_uncooked", 0);
	public static Item bomb_casing = new ItemBombComponent("bomb_casing", 0, "bombcase", 0);
	public static Item mine_casing_uncooked = new ItemComponentMFR("mine_casing_uncooked", 0);
	public static Item mine_casing = new ItemBombComponent("mine_casing", 0, "minecase", 0);
	public static Item bomb_casing_iron = new ItemBombComponent("bomb_casing_iron", 0, "bombcase", 1);
	public static Item mine_casing_iron = new ItemBombComponent("mine_casing_iron", 0, "minecase", 1);
	public static Item bomb_casing_obsidian = new ItemBombComponent("bomb_casing_obsidian", 1, "bombcase", 2);
	public static Item mine_casing_obsidian = new ItemBombComponent("mine_casing_obsidian", 1, "minecase", 2);
	public static Item bomb_casing_crystal = new ItemBombComponent("bomb_casing_crystal", 1, "bombcase", 3);
	public static Item mine_casing_crystal = new ItemBombComponent("mine_casing_crystal", 1, "minecase", 3);
	public static Item bomb_casing_arrow = new ItemBombComponent("bomb_casing_arrow", 1, "arrow", 0);
	public static Item bomb_casing_bolt = new ItemBombComponent("bomb_casing_bolt", 1, "bolt", 0);

	public static Item coke = new ItemComponentMFR("coke", 1);
	public static Item diamond_shards = new ItemComponentMFR("diamond_shards", 0);

	public static Item clay_brick = new ItemComponentMFR("clay_brick", 0);
	public static Item kaolinite = new ItemComponentMFR("kaolinite", 0);
	public static Item kaolinite_dust = new ItemComponentMFR("kaolinite_dust", 0).setContainerItem(clay_pot);
	public static Item fireclay = new ItemComponentMFR("fireclay", 0);
	public static Item fireclay_brick = new ItemComponentMFR("fireclay_brick", 0);
	public static Item strong_brick = new ItemComponentMFR("strong_brick", 0).setStoragePlacement("bar", "firebrick");

	public static Item hideSmall = new ItemComponentMFR("hideSmall", 0);
	public static Item hideMedium = new ItemComponentMFR("hideMedium", 0);
	public static Item hideLarge = new ItemComponentMFR("hideLarge", 0);
	public static Item rawhideSmall = new ItemHide("rawhideSmall", hideSmall, 1.0F);
	public static Item rawhideMedium = new ItemHide("rawhideMedium", hideMedium, 1.5F);
	public static Item rawhideLarge = new ItemHide("rawhideLarge", hideLarge, 3.0F);

	public static Item dragon_heart = new ItemSpecialDesign("dragon_heart", 1, "dragon");

	public static Item leather_strip = new ItemComponentMFR("leather_strip", 0);
	public static Item nail = new ItemComponentMFR("nail", 0);
	public static Item rivet = new ItemComponentMFR("rivet", 0);
	public static Item thread = new ItemComponentMFR("thread", 0);
	public static Item obsidian_rock = new ItemComponentMFR("obsidian_rock", 0);

	public static Item oreCopper = new ItemRawOreMF("oreCopper", -1);
	public static Item oreTin = new ItemRawOreMF("oreTin", -1);
	public static Item oreIron = new ItemRawOreMF("oreIron", 0);
	public static Item oreSilver = new ItemRawOreMF("oreSilver", 0);
	public static Item oreGold = new ItemRawOreMF("oreGold", 0);
	public static Item oreTungsten = new ItemRawOreMF("oreTungsten", 1);

	public static Item hotItem = new ItemHeated();

	public static Item plant_oil = new ItemComponentMFR("plant_oil", 0).setStoragePlacement("jug", "jugoil")
			.setContainerItem(FoodListMFR.jug_empty);

	public static Item talisman_lesser = new ItemComponentMFR("talisman_lesser", 1);
	public static Item talisman_greater = new ItemComponentMFR("talisman_greater", 3);

	public static Item bolt = new ItemComponentMFR("bolt", 0);
	public static Item iron_frame = new ItemComponentMFR("iron_frame", 0);
	public static Item iron_strut = new ItemComponentMFR("iron_strut", 0);
	public static Item bronze_gears = new ItemComponentMFR("bronze_gears", 0);
	public static Item tungsten_gears = new ItemComponentMFR("tungsten_gears", 1);
	public static Item steel_tube = new ItemComponentMFR("steel_tube", 0);
	public static Item cogwork_shaft = new ItemComponentMFR("cogwork_shaft", 1);
	public static Item ingotCompositeAlloy = new ItemComponentMFR("ingotCompositeAlloy", 1);
	public static Item coal_prep = new ItemComponentMFR("coal_prep", 0);

	public static Item ingot_mould_filled = new ItemFilledMould();

	public static Item crossbow_stock_wood = new ItemCrossbowPart("cross_stock_wood", "stock").addSpeed(1.0F).addRecoil(0F);
	public static Item crossbow_stock_iron = new ItemCrossbowPart("cross_stock_iron", "stock").addSpeed(1.0F).addRecoil(-2F).addDurability(150);
	public static Item crossbow_handle_wood = new ItemCrossbowPart("cross_handle_wood", "stock").addSpeed(0.5F).addRecoil(2F).addSpread(1.0F).setHandCrossbow(true);

	public static Item cross_arms_basic = new ItemCrossbowPart("cross_arms_basic", "mechanism").addPower(1.00F).addSpeed(0.50F).addRecoil(4F).addSpread(1.00F);
	public static Item cross_arms_light = new ItemCrossbowPart("cross_arms_light", "mechanism").addPower(0.85F).addSpeed(0.25F).addRecoil(2F).addSpread(0.50F);
	public static Item cross_arms_heavy = new ItemCrossbowPart("cross_arms_heavy", "mechanism").addPower(1.15F).addSpeed(1.00F).addRecoil(8F).addSpread(2.00F);
	public static Item cross_arms_advanced = new ItemCrossbowPart("cross_arms_advanced", "mechanism").addPower(1.15F).addSpeed(1.00F).addRecoil(6F).addSpread(0.25F).addDurability(150);

	public static Item cross_bayonet = new ItemCrossbowPart("cross_bayonet", "muzzle").addBash(4.0F).addRecoil(-1F).addSpeed(0.5F);
	public static Item cross_ammo = new ItemCrossbowPart("cross_ammo", "mod").addCapacity(5).addSpread(2.00F);
	public static Item cross_scope = new ItemCrossbowPart("cross_scope", "mod").setScope(0.75F);

	public static ItemCustomComponent chainmesh = new ItemCustomComponent("chainmesh", 1F, "metal").setStoragePlacement("sheet", "mail");
	public static ItemCustomComponent scalemesh = new ItemCustomComponent("scalemesh", 1F, "metal").setStoragePlacement("sheet", "scale");
	public static ItemCustomComponent splintmesh = new ItemCustomComponent("splintmesh", 1F, "metal").setStoragePlacement("sheet", "splint");
	public static ItemCustomComponent plate = new ItemCustomComponent("plate", 2F, "metal").setStoragePlacement("sheet", "plate");
	public static ItemCustomComponent plate_huge = new ItemCustomComponent("plate_huge", 8F, "metal").setStoragePlacement("bigplate", "bigplate");
	public static ItemCustomComponent metalHunk = new ItemCustomComponent("hunk", 0.25F, "metal");
	public static ItemCustomComponent arrowhead = new ItemCustomComponent("arrowhead", 1 / 4F, "metal");
	public static ItemCustomComponent bodkinhead = new ItemCustomComponent("bodkinhead", 1 / 4F, "metal");
	public static ItemCustomComponent broadhead = new ItemCustomComponent("broadhead", 1 / 4F, "metal");
	public static ItemCustomComponent cogwork_armour = (ItemCustomComponent) new ItemCustomComponent("cogwork_armour", 30F, "metal").setCanDamage().setCreativeTab(CreativeTabMFR.tabGadget).setMaxStackSize(1);
	public static ItemCustomComponent bar = (ItemCustomComponent) new ItemCustomComponent("bar", 1F, "metal").setStoragePlacement("bar", "bar").setCreativeTab(CreativeTabMFR.tabMaterialsMFR);

	public static Item flux_pot = new ItemComponentMFR("flux_pot", 0).setContainerItem(clay_pot);
	public static Item coal_flux = new ItemComponentMFR("coal_flux", 0);

	public static Item copper_coin = new ItemComponentMFR("copper_coin", 0);
	public static Item silver_coin = new ItemComponentMFR("silver_coin", 0);
	public static Item gold_coin = new ItemComponentMFR("gold_coin", 0);

	public static Item hinge = new ItemComponentMFR("hinge", 0);
	public static Item plank_cut = new ItemComponentMFR("plank_cut").setCustom(1, "wood").setStoragePlacement("plank", "plankcut");
	public static Item plank_pane = new ItemComponentMFR("plank_pane").setCustom(6, "wood").setStoragePlacement("sheet", "woodpane");

	public static Item cogwork_pulley = new ItemComponentMFR("cogwork_pulley", 1).setCreativeTab(CreativeTabMFR.tabGadget);

	public static Item artefacts = new ItemArtefact("artefact_any");

	public static Item ornate_items = new ItemSpecialDesign("ornate_items", 1, "ornate");

	public static void load() {
		WoodMaterial.init();
		Items.POTIONITEM.setContainerItem(Items.GLASS_BOTTLE);
		GameRegistry.registerFuelHandler(new FuelHandlerMF());
		MineFantasyRebornAPI.registerFuelHandler(new AdvancedFuelHandlerMF());
		for (int a = 0; a < ingotMats.length; a++) {
			BaseMaterialMFR mat = BaseMaterialMFR.getMaterial(ingotMats[a]);
			String name = mat.name;
			int rarity = mat.rarity;

			ingots[a] = new ItemComponentMFR("ingot" + name, rarity);
			OreDictionary.registerOre("ingot" + name, ingots[a]);
		}

		addRandomDrops();
		initFuels();
		OreDictionary.registerOre("ingotCompositeAlloy", ingotCompositeAlloy);
		OreDictionary.registerOre("ingotIron", Items.IRON_INGOT);
		OreDictionary.registerOre("ingotGold", Items.GOLD_INGOT);

		AdvancedFuelHandlerMF.registerItems();
	}

	private static void initFuels() {
		MineFantasyFuels.addForgeFuel(new ItemStack(Items.COAL, 1, 0), 900, 1500);// 1500C , 45s
		MineFantasyFuels.addForgeFuel(new ItemStack(Items.COAL, 1, 1), 1200, 1800);// 1800C , 1m
		MineFantasyFuels.addForgeFuel(Items.BLAZE_POWDER, 200, 3000, true);// 3000C , 10s
		MineFantasyFuels.addForgeFuel(Items.BLAZE_ROD, 300, 3000, true);// 3000C , 15s
		MineFantasyFuels.addForgeFuel(Items.FIRE_CHARGE, 1200, 3500, true);// 3500C , 1m
		MineFantasyFuels.addForgeFuel(Items.LAVA_BUCKET, 2400, 5000, true);// 5000C , 2m
		MineFantasyFuels.addForgeFuel(Items.MAGMA_CREAM, 2400, 4000, true, true);// 4000C , 2m

		MineFantasyFuels.addForgeFuel(ComponentListMFR.coke, 1200, 2500, false, true);// 2500C , 1m
		MineFantasyFuels.addForgeFuel(ComponentListMFR.magma_cream_refined, 2400, 5000, true, true);// 5000C , 2m

		MineFantasyFuels.addForgeFuel(ComponentListMFR.coalDust, 1200, 180);// 180C , 60s
	}

	private static void addRandomDrops() {
		RandomOre.addOre(new ItemStack(kaolinite), 1.5F, Blocks.STONE, -1, 32, 128, false);
		RandomOre.addOre(new ItemStack(flux), 2F, Blocks.STONE, -1, 0, 128, false);
		RandomOre.addOre(new ItemStack(flux_strong), 1F, Blocks.STONE, 2, 0, 128, false);
		RandomOre.addOre(new ItemStack(flux), 20F, BlockListMFR.LIMESTONE, 0, -1, 0, 256, true);
		RandomOre.addOre(new ItemStack(flux_strong), 10F, BlockListMFR.LIMESTONE, 0, 2, 0, 256, true);
		RandomOre.addOre(new ItemStack(Items.COAL), 2F, Blocks.STONE, -1, 0, 128, false);
		RandomOre.addOre(new ItemStack(sulfur), 2F, Blocks.STONE, -1, 0, 16, false);
		RandomOre.addOre(new ItemStack(nitre), 3F, Blocks.STONE, -1, 0, 64, false);
		RandomOre.addOre(new ItemStack(Items.REDSTONE), 5F, Blocks.STONE, 2, 0, 16, false);
		RandomOre.addOre(new ItemStack(Items.FLINT), 1F, Blocks.STONE, -1, 0, 64, false);
		RandomOre.addOre(new ItemStack(diamond_shards), 0.2F, Blocks.STONE, 2, 0, 16, false);
		RandomOre.addOre(new ItemStack(Items.QUARTZ), 0.5F, Blocks.STONE, 3, 0, 16, false);

		RandomOre.addOre(new ItemStack(sulfur), 10F, Blocks.NETHERRACK, -1, 0, 512, false);
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

		RandomOre.addOre(new ItemStack(oreCopper), 4F, Blocks.STONE, 0, 48, 96, false);
		RandomOre.addOre(new ItemStack(oreTin), 2F, Blocks.STONE, 0, 48, 96, false);
		RandomOre.addOre(new ItemStack(oreIron), 5F, Blocks.STONE, 0, 0, 64, false);
		RandomOre.addOre(new ItemStack(oreSilver), 1.5F, Blocks.STONE, 0, 0, 32, false);
		RandomOre.addOre(new ItemStack(oreGold), 1F, Blocks.STONE, 0, 0, 32, false);

		RandomOre.addOre(new ItemStack(oreTungsten), 2F, Blocks.STONE, 3, 0, 16, false, "tungsten");
	}

	public static ItemStack bar(String material) {
		return bar(material, 1);
	}

	public static ItemStack bar(String material, int stackSize) {
		return bar.createComm(material, stackSize);
	}
}

