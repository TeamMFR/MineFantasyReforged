package minefantasy.mfr.init;

import minefantasy.mfr.item.AdvancedFuelHandlerMF;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;



public class OreDictListMFR {

	public static void registerOreDictEntries() {
		OreDictionary.registerOre("oreCopper", BlockListMFR.COPPER_ORE);
		OreDictionary.registerOre("oreTin", BlockListMFR.TIN_ORE);
		OreDictionary.registerOre("oreSilver", BlockListMFR.SILVER_ORE);
		OreDictionary.registerOre("oreMythic", BlockListMFR.MYTHIC_ORE);
		OreDictionary.registerOre("oreKaolinite", BlockListMFR.KAOLINITE_ORE);
		OreDictionary.registerOre("oreNitre", BlockListMFR.NITRE_ORE);
		OreDictionary.registerOre("oreSulfur", BlockListMFR.SULFUR_ORE);
		OreDictionary.registerOre("oreBorax", BlockListMFR.BORAX_ORE);
		OreDictionary.registerOre("oreTungsten", BlockListMFR.TUNGSTEN_ORE);
		OreDictionary.registerOre("oreClay", BlockListMFR.CLAY_ORE);
		OreDictionary.registerOre("oreRichCoal", BlockListMFR.COAL_RICH_ORE);

		OreDictionary.registerOre("cobblestone", new ItemStack(BlockListMFR.LIMESTONE, 1, 1));
		OreDictionary.registerOre("stone", new ItemStack(BlockListMFR.LIMESTONE, 1, 0));
		OreDictionary.registerOre("limestone", new ItemStack(BlockListMFR.LIMESTONE, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("cobblestone", new ItemStack(BlockListMFR.COBBLE_BRICK, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("cobblestone", new ItemStack(BlockListMFR.COBBLESTONE_ROAD, 1, OreDictionary.WILDCARD_VALUE));

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
			if (plank.getItem().getClass().getName().contains("blockWoodenDevice")) {
				if (plank.getUnlocalizedName().equalsIgnoreCase("tile.blockWoodenDevice.6")) {
					OreDictionary.registerOre("planksGreatwoodWood", plank);
				}
				if (plank.getUnlocalizedName().equalsIgnoreCase("tile.blockWoodenDevice.7")) {
					OreDictionary.registerOre("planksSilverwoodWood", plank);
				}
			}
		}

		OreDictionary.registerOre("ingotCopper", ComponentListMFR.COPPER_INGOT);
		OreDictionary.registerOre("ingotTin", ComponentListMFR.TIN_INGOT);
		OreDictionary.registerOre("ingotBronze", ComponentListMFR.BRONZE_INGOT);
		OreDictionary.registerOre("ingotPigIron", ComponentListMFR.PIG_IRON_INGOT);
		OreDictionary.registerOre("ingotSteel", ComponentListMFR.STEEL_INGOT);
		OreDictionary.registerOre("ingotEncrusted", ComponentListMFR.ENCRUSTED_INGOT);
		OreDictionary.registerOre("ingotBlackSteel", ComponentListMFR.BLACK_STEEL_INGOT);
		OreDictionary.registerOre("ingotSilver", ComponentListMFR.SILVER_INGOT);
		OreDictionary.registerOre("ingotRedSteel", ComponentListMFR.RED_STEEL_INGOT);
		OreDictionary.registerOre("ingotBlueSteel", ComponentListMFR.BLUE_STEEL_INGOT);
		OreDictionary.registerOre("ingotAdamantium", ComponentListMFR.ADAMANTIUM_INGOT);
		OreDictionary.registerOre("ingotMithril", ComponentListMFR.MITHRIL_INGOT);
		OreDictionary.registerOre("ingotIgnotumite", ComponentListMFR.IGNOTUMITE_INGOT);
		OreDictionary.registerOre("ingotMithium", ComponentListMFR.MITHIUM_INGOT);
		OreDictionary.registerOre("ingotEnder", ComponentListMFR.ENDER_INGOT);
		OreDictionary.registerOre("ingotTungsten", ComponentListMFR.TUNGSTEN_INGOT);
		OreDictionary.registerOre("ingotObsidian", ComponentListMFR.OBSIDIAN_INGOT);
		OreDictionary.registerOre("ingotCompositeAlloy", ComponentListMFR.COMPOSITE_ALLOY_INGOT);

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

