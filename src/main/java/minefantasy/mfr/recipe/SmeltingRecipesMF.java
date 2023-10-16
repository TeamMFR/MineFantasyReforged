package minefantasy.mfr.recipe;

import minefantasy.mfr.api.MineFantasyReforgedAPI;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyMaterials;
import net.minecraft.item.ItemStack;

public class SmeltingRecipesMF {
	public static void init() {
		ItemStack pig_iron = MineFantasyItems.bar(MineFantasyMaterials.Names.PIG_IRON);
		ItemStack black_steel = MineFantasyItems.bar(MineFantasyMaterials.Names.BLACK_STEEL);
		ItemStack red_steel = MineFantasyItems.bar(MineFantasyMaterials.Names.RED_STEEL);
		ItemStack blue_steel = MineFantasyItems.bar(MineFantasyMaterials.Names.BLUE_STEEL);

		MineFantasyReforgedAPI.addBlastFurnaceRecipe(MineFantasyItems.PREPARED_IRON, pig_iron);
		MineFantasyReforgedAPI.addBlastFurnaceRecipe(MineFantasyItems.BLACK_STEEL_WEAK_INGOT, black_steel);
		MineFantasyReforgedAPI.addBlastFurnaceRecipe(MineFantasyItems.RED_STEEL_WEAK_INGOT, red_steel);
		MineFantasyReforgedAPI.addBlastFurnaceRecipe(MineFantasyItems.BLUE_STEEL_WEAK_INGOT, blue_steel);
	}
}
