package minefantasy.mfr.init;

import minefantasy.mfr.item.AdvancedFuelHandlerMF;
import minefantasy.mfr.item.ItemArtefact;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;



public class OreDictListMFR {

	public static void registerOreDictEntries() {

		// TODO: fix registry names: camelCase -> snake_case
		OreDictionary.registerOre("copper_ore", BlockListMFR.COPPER_ORE);
		OreDictionary.registerOre("tin_ore", BlockListMFR.TIN_ORE);
		OreDictionary.registerOre("silver_ore", BlockListMFR.SILVER_ORE);
		OreDictionary.registerOre("mythic_ore", BlockListMFR.MYTHIC_ORE);
		OreDictionary.registerOre("kaolinite_ore", BlockListMFR.KAOLINITE_ORE);
		OreDictionary.registerOre("borax_ore", BlockListMFR.NITRE_ORE);
		OreDictionary.registerOre("sulfur_ore", BlockListMFR.SULFUR_ORE);
		OreDictionary.registerOre("borax_ore", BlockListMFR.BORAX_ORE);
		OreDictionary.registerOre("tungsten_ore", BlockListMFR.TUNGSTEN_ORE);
		OreDictionary.registerOre("clay_ore", BlockListMFR.CLAY_ORE);
		OreDictionary.registerOre("coal_rich_ore", BlockListMFR.COAL_RICH_ORE);

		OreDictionary.registerOre("cobblestone", new ItemStack(BlockListMFR.LIMESTONE, 1, 1));
		OreDictionary.registerOre("stone", new ItemStack(BlockListMFR.LIMESTONE, 1, 0));
		OreDictionary.registerOre("limestone", new ItemStack(BlockListMFR.LIMESTONE, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("cobblestone", new ItemStack(BlockListMFR.COBBLE_BRICK, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("cobblestone", new ItemStack(BlockListMFR.COBBLE_PAVEMENT, 1, OreDictionary.WILDCARD_VALUE));

		OreDictionary.registerOre("blockGlass", new ItemStack(BlockListMFR.WINDOW, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockGlass", new ItemStack(BlockListMFR.FRAMED_GLASS, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("paneGlass", new ItemStack(BlockListMFR.WINDOW_PANE, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("paneGlass", new ItemStack(BlockListMFR.FRAMED_GLASS_PANE, 1, OreDictionary.WILDCARD_VALUE));

		OreDictionary.registerOre("planksOakWood", new ItemStack(Blocks.PLANKS, 1, 0));
		OreDictionary.registerOre("planksSpruceWood", new ItemStack(Blocks.PLANKS, 1, 1));
		OreDictionary.registerOre("planksBirchWood", new ItemStack(Blocks.PLANKS, 1, 2));
		OreDictionary.registerOre("planksJungleWood", new ItemStack(Blocks.PLANKS, 1, 3));
		OreDictionary.registerOre("planksAcaciaWood", new ItemStack(Blocks.PLANKS, 1, 4));
		OreDictionary.registerOre("planksDarkOakWood", new ItemStack(Blocks.PLANKS, 1, 5));

		OreDictionary.registerOre("planksIronbarkWood", BlockListMFR.IRONBARK_PLANKS);
		OreDictionary.registerOre("planksEbonyWood", BlockListMFR.EBONY_PLANKS);
		OreDictionary.registerOre("planksYewWood", BlockListMFR.YEW_PLANKS);
		OreDictionary.registerOre("planksIronbarkWood", BlockListMFR.IRONBARK_PLANKS);
		OreDictionary.registerOre("planksEbonyWood", BlockListMFR.EBONY_PLANKS);
		OreDictionary.registerOre("planksYewWood", BlockListMFR.YEW_PLANKS);

		for (ItemStack plank : OreDictionary.getOres("plankWood")) {
			if (plank.getItem().getClass().getName().contains("BlockWoodenDevice")) {
				if (plank.getUnlocalizedName().equalsIgnoreCase("tile.blockWoodenDevice.6")) {
					OreDictionary.registerOre("planksGreatwoodWood", plank);
				}
				if (plank.getUnlocalizedName().equalsIgnoreCase("tile.blockWoodenDevice.7")) {
					OreDictionary.registerOre("planksSilverwoodWood", plank);
				}
			}
		}

		OreDictionary.registerOre("ingot_copper", ComponentListMFR.COPPER_INGOT);
		OreDictionary.registerOre("ingot_tin", ComponentListMFR.TIN_INGOT);
		OreDictionary.registerOre("ingot_bronze", ComponentListMFR.BRONZE_INGOT);
		OreDictionary.registerOre("ingot_pig_iron", ComponentListMFR.PIG_IRON_INGOT);
		OreDictionary.registerOre("ingot_steel", ComponentListMFR.STEEL_INGOT);
		OreDictionary.registerOre("ingot_encrusted", ComponentListMFR.ENCRUSTED_INGOT);
		OreDictionary.registerOre("ingot_black_steel", ComponentListMFR.BLACK_STEEL_INGOT);
		OreDictionary.registerOre("ingot_silver", ComponentListMFR.SILVER_INGOT);
		OreDictionary.registerOre("ingot_red_steel", ComponentListMFR.RED_STEEL_INGOT);
		OreDictionary.registerOre("ingot_blue_steel", ComponentListMFR.BLUE_STEEL_INGOT);
		OreDictionary.registerOre("ingot_adamantium", ComponentListMFR.ADAMANTIUM_INGOT);
		OreDictionary.registerOre("ingot_mithril", ComponentListMFR.MITHRIL_INGOT);
		OreDictionary.registerOre("ingot_ignotumite", ComponentListMFR.IGNOTUMITE_INGOT);
		OreDictionary.registerOre("ingot_mithium", ComponentListMFR.MITHIUM_INGOT);
		OreDictionary.registerOre("ingot_ender", ComponentListMFR.ENDER_INGOT);
		OreDictionary.registerOre("ingot_tungsten", ComponentListMFR.TUNGSTEN_INGOT);
		OreDictionary.registerOre("ingot_obsidian", ComponentListMFR.OBSIDIAN_INGOT);
		OreDictionary.registerOre("ingot_composite_alloy", ComponentListMFR.INGOT_COMPOSITE_ALLOY);
		OreDictionary.registerOre("ingot_iron", Items.IRON_INGOT);
		OreDictionary.registerOre("ingot_gold", Items.GOLD_INGOT);

		((ItemArtefact) ComponentListMFR.ARTEFACTS).registerAll();

		String meatRaw = "raw_meat";
		String cookedMeat = "cooked_meat";
		OreDictionary.registerOre(cookedMeat, Items.COOKED_BEEF);
		OreDictionary.registerOre(cookedMeat, Items.COOKED_CHICKEN);
		OreDictionary.registerOre(cookedMeat, Items.COOKED_PORKCHOP);
		OreDictionary.registerOre(cookedMeat, FoodListMFR.WOLF_COOKED);
		OreDictionary.registerOre(cookedMeat, FoodListMFR.HORSE_COOKED);
		OreDictionary.registerOre(cookedMeat, Items.COOKED_FISH);
		OreDictionary.registerOre(cookedMeat, new ItemStack(Items.COOKED_FISH, 1, 1));
		addOreD("listAllporkcooked", cookedMeat);
		addOreD("listAllmuttoncooked", cookedMeat);
		addOreD("listAllbeefcooked", cookedMeat);
		addOreD("listAllchickencooked", cookedMeat);
		addOreD("listAllfishcooked", cookedMeat);

		OreDictionary.registerOre(meatRaw, FoodListMFR.GUTS);
		OreDictionary.registerOre(meatRaw, Items.BEEF);
		OreDictionary.registerOre(meatRaw, Items.CHICKEN);
		OreDictionary.registerOre(meatRaw, Items.PORKCHOP);
		OreDictionary.registerOre(meatRaw, FoodListMFR.WOLF_RAW);
		OreDictionary.registerOre(meatRaw, FoodListMFR.HORSE_RAW);
		OreDictionary.registerOre(meatRaw, Items.FISH);
		OreDictionary.registerOre(meatRaw, new ItemStack(Items.FISH, 1, 1));
		addOreD("listAllporkraw", meatRaw);
		addOreD("listAllmuttonraw", meatRaw);
		addOreD("listAllbeefraw", meatRaw);
		addOreD("listAllchickenraw", meatRaw);
		addOreD("listAllfishraw", meatRaw);

		AdvancedFuelHandlerMF.registerItems();

	}

	private static void addOreD(String list, String mfList) {
		for (ItemStack stack : OreDictionary.getOres(list)) {
			OreDictionary.registerOre(mfList, stack);
		}
	}
}

