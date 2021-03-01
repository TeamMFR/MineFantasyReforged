package minefantasy.mfr.init;

import com.google.common.base.CaseFormat;
import minefantasy.mfr.item.AdvancedFuelHandlerMF;
import minefantasy.mfr.material.CustomMaterial;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictListMFR {

	public static void registerOreDictEntries() {
		OreDictionary.registerOre("oreCopper", MineFantasyBlocks.COPPER_ORE);
		OreDictionary.registerOre("oreTin", MineFantasyBlocks.TIN_ORE);
		OreDictionary.registerOre("oreSilver", MineFantasyBlocks.SILVER_ORE);
		OreDictionary.registerOre("oreMythic", MineFantasyBlocks.MYTHIC_ORE);
		OreDictionary.registerOre("oreKaolinite", MineFantasyBlocks.KAOLINITE_ORE);
		OreDictionary.registerOre("oreNitre", MineFantasyBlocks.NITRE_ORE);
		OreDictionary.registerOre("oreSulfur", MineFantasyBlocks.SULFUR_ORE);
		OreDictionary.registerOre("oreBorax", MineFantasyBlocks.BORAX_ORE);
		OreDictionary.registerOre("oreTungsten", MineFantasyBlocks.TUNGSTEN_ORE);
		OreDictionary.registerOre("oreClay", MineFantasyBlocks.CLAY_ORE);
		OreDictionary.registerOre("oreRichCoal", MineFantasyBlocks.COAL_RICH_ORE);

		OreDictionary.registerOre("cobblestone", new ItemStack(MineFantasyBlocks.LIMESTONE, 1, 1));
		OreDictionary.registerOre("stone", new ItemStack(MineFantasyBlocks.LIMESTONE, 1, 0));
		OreDictionary.registerOre("limestone", new ItemStack(MineFantasyBlocks.LIMESTONE, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("cobblestone", new ItemStack(MineFantasyBlocks.COBBLE_BRICK, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("cobblestone", new ItemStack(MineFantasyBlocks.COBBLESTONE_ROAD, 1, OreDictionary.WILDCARD_VALUE));

		OreDictionary.registerOre("blockGlass", new ItemStack(MineFantasyBlocks.WINDOW, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockGlass", new ItemStack(MineFantasyBlocks.FRAMED_GLASS, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("paneGlass", new ItemStack(MineFantasyBlocks.WINDOW_PANE, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("paneGlass", new ItemStack(MineFantasyBlocks.FRAMED_GLASS_PANE, 1, OreDictionary.WILDCARD_VALUE));

		OreDictionary.registerOre("plankWood", new ItemStack(Blocks.PLANKS, 1 , 0));
		OreDictionary.registerOre("plankWood", new ItemStack(Blocks.PLANKS, 1 , 1));
		OreDictionary.registerOre("plankWood", new ItemStack(Blocks.PLANKS, 1 , 2));
		OreDictionary.registerOre("plankWood", new ItemStack(Blocks.PLANKS, 1 , 3));
		OreDictionary.registerOre("plankWood", new ItemStack(Blocks.PLANKS, 1 , 4));
		OreDictionary.registerOre("plankWood", new ItemStack(Blocks.PLANKS, 1 , 5));
		OreDictionary.registerOre("plankWood", new ItemStack(MineFantasyBlocks.IRONBARK_PLANKS, 1, 0));
		OreDictionary.registerOre("plankWood", new ItemStack(MineFantasyBlocks.EBONY_PLANKS, 1, 0));
		OreDictionary.registerOre("plankWood", new ItemStack(MineFantasyBlocks.YEW_PLANKS, 1, 0));
		OreDictionary.registerOre("plankWood", new ItemStack(MineFantasyBlocks.REFINED_PLANKS, 1, 0));

		NonNullList<ItemStack> plankWoodList = OreDictionary.getOres("plankWood");
		plankWoodList.remove(0);

		for (ItemStack plank : plankWoodList) {
			for (CustomMaterial material : CustomMaterial.getList("wood")){
				if (plank.getDisplayName().replaceAll(" ", "").replaceFirst("Planks", "").equals(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, material.getName()))){
					OreDictionary.registerOre("planks" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, material.getName()), plank);
					break;
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

