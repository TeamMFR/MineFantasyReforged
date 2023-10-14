package minefantasy.mfr.recipe;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.MineFantasyReforgedAPI;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyMaterials;
import minefantasy.mfr.recipe.refine.BloomRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SmeltingRecipesMF {
	public static void init() {
		ItemStack copper = MineFantasyItems.bar(MineFantasyMaterials.Names.COPPER);
		ItemStack tin = MineFantasyItems.bar(MineFantasyMaterials.Names.TIN);
		ItemStack iron = MineFantasyItems.bar(MineFantasyMaterials.Names.IRON);
		ItemStack pig_iron = MineFantasyItems.bar(MineFantasyMaterials.Names.PIG_IRON);
		ItemStack black_steel = MineFantasyItems.bar(MineFantasyMaterials.Names.BLACK_STEEL);
		ItemStack red_steel = MineFantasyItems.bar(MineFantasyMaterials.Names.RED_STEEL);
		ItemStack blue_steel = MineFantasyItems.bar(MineFantasyMaterials.Names.BLUE_STEEL);
		ItemStack gold = MineFantasyItems.bar(MineFantasyMaterials.Names.GOLD);
		ItemStack silver = MineFantasyItems.bar(MineFantasyMaterials.Names.SILVER);

		if (ConfigHardcore.HCCreduceIngots) {
			if (MineFantasyReforgedAPI.removeSmelting(Blocks.IRON_ORE) && MineFantasyReforgedAPI.removeSmelting(Blocks.GOLD_ORE)) {
				MineFantasyReforged.LOG.debug("Removed Ore Smelting (Hardcore Ingots");
			} else {
				MineFantasyReforged.LOG.warn("Failed to remove Ore smelting!");
			}

			for (ItemStack ore: OreDictionary.getOres("oreIron")) {
				BloomRecipe.addRecipe(ore, iron);
			}
			for (ItemStack ore: OreDictionary.getOres("oreGold")) {
				BloomRecipe.addRecipe(ore, gold);
			}
		}

		refineRawOre(new ItemStack(MineFantasyItems.ORE_COPPER), copper);
		refineRawOre(new ItemStack(MineFantasyItems.ORE_TIN), tin);
		refineRawOre(new ItemStack(MineFantasyItems.ORE_IRON), iron);
		refineRawOre(new ItemStack(MineFantasyItems.ORE_SILVER), silver);
		refineRawOre(new ItemStack(MineFantasyItems.ORE_GOLD), gold);

		for (ItemStack ore: OreDictionary.getOres("oreCopper")) {
			refineRawOre(ore, copper);
		}
		for (ItemStack ore: OreDictionary.getOres("oreTin")) {
			refineRawOre(ore, tin);
		}
		for (ItemStack ore: OreDictionary.getOres("oreSilver")) {
			refineRawOre(ore, silver);
		}

		MineFantasyReforgedAPI.addBlastFurnaceRecipe(MineFantasyItems.PREPARED_IRON, pig_iron);
		MineFantasyReforgedAPI.addBlastFurnaceRecipe(MineFantasyItems.BLACK_STEEL_WEAK_INGOT, black_steel);
		MineFantasyReforgedAPI.addBlastFurnaceRecipe(MineFantasyItems.RED_STEEL_WEAK_INGOT, red_steel);
		MineFantasyReforgedAPI.addBlastFurnaceRecipe(MineFantasyItems.BLUE_STEEL_WEAK_INGOT, blue_steel);
	}

	private static void refineRawOre(ItemStack ore, ItemStack bar) {
		if (ConfigHardcore.HCCreduceIngots) {
			BloomRecipe.addRecipe(ore, bar);
		}
	}
}
