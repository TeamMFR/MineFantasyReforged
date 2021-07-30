package minefantasy.mfr.recipe;

import minefantasy.mfr.api.MineFantasyReforgedAPI;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.MineFantasyItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CookingRecipes {
	public static void init() {
		cookMeat(MineFantasyItems.HORSE_RAW, MineFantasyItems.HORSE_COOKED);
		cookMeat(MineFantasyItems.WOLF_RAW, MineFantasyItems.WOLF_COOKED);
		cookMeat(MineFantasyItems.SAUSAGE_RAW, MineFantasyItems.SAUSAGE_COOKED);
		cookMeat(MineFantasyItems.GENERIC_MEAT_UNCOOKED, MineFantasyItems.GENERIC_MEAT_COOKED);
		cookMeat(MineFantasyItems.GENERIC_MEAT_STRIP_UNCOOKED, MineFantasyItems.GENERIC_MEAT_STRIP_COOKED);
		cookMeat(MineFantasyItems.GENERIC_MEAT_CHUNK_UNCOOKED, MineFantasyItems.GENERIC_MEAT_CHUNK_COOKED);

		bake(MineFantasyItems.DOUGH, MineFantasyItems.BREADROLL, 150, 300, 10, 20, true);
		bake(MineFantasyItems.RAW_BREAD, Items.BREAD, 150, 300, 20, 40, true);
		bake(MineFantasyItems.CURDS, MineFantasyItems.CHEESE_POT, 100, 300, 60, 20, false);
		bake(MineFantasyItems.SWEETROLL_RAW, MineFantasyItems.SWEETROLL_UNICED, 150, 250, 20, 10, true);

		bake(MineFantasyItems.PIE_PUMPKIN_UNCOOKED, MineFantasyItems.PIE_PUMPKIN_COOKED, 150, 300, 20, 20, MineFantasyItems.BURNT_PIE);
		bake(MineFantasyItems.PIE_APPLE_UNCOOKED, MineFantasyItems.PIE_APPLE_COOKED, 150, 300, 25, 20, MineFantasyItems.BURNT_PIE);
		bake(MineFantasyItems.PIE_BERRY_UNCOOKED, MineFantasyItems.PIE_BERRY_COOKED, 150, 300, 25, 20, MineFantasyItems.BURNT_PIE);
		bake(MineFantasyItems.PIE_MEAT_UNCOOKED, MineFantasyItems.PIE_MEAT_COOKED, 150, 300, 30, 20, MineFantasyItems.BURNT_PIE);
		bake(MineFantasyItems.PIE_SHEPARD_UNCOOKED, MineFantasyItems.PIE_SHEPARD_COOKED, 150, 300, 30, 20, MineFantasyItems.BURNT_PIE);

		bake(MineFantasyItems.CAKE_SIMPLE_RAW, MineFantasyItems.CAKE_SIMPLE_UNICED, 150, 250, 30, 30, MineFantasyItems.BURNT_CAKE);
		bake(MineFantasyItems.CAKE_RAW, MineFantasyItems.CAKE_UNICED, 150, 250, 40, 20, MineFantasyItems.BURNT_CAKE);
		bake(MineFantasyItems.CAKE_CHOC_RAW, MineFantasyItems.CAKE_CHOC_UNICED, 150, 250, 40, 20, MineFantasyItems.BURNT_CAKE);
		bake(MineFantasyItems.CAKE_CARROT_RAW, MineFantasyItems.CAKE_CARROT_UNICED, 150, 250, 40, 20, MineFantasyItems.BURNT_CAKE);
		bake(MineFantasyItems.CAKE_BF_RAW, MineFantasyItems.CAKE_BF_UNICED, 150, 250, 50, 10, MineFantasyItems.BURNT_CAKE);
		bake(MineFantasyItems.ECLAIR_RAW, MineFantasyItems.ECLAIR_UNICED, 150, 250, 60, 5, true);

		addCeramics();
		MineFantasyReforgedAPI.addCookingRecipe(new ItemStack(MineFantasyItems.GENERIC_MEAT_MINCE_UNCOOKED),
				new ItemStack(MineFantasyItems.GENERIC_MEAT_MINCE_COOKED), new ItemStack(MineFantasyItems.BURNT_POT), 100, 200, 10,
				false);

		MineFantasyReforgedAPI.addCookingRecipe(new ItemStack(MineFantasyItems.BOWL_WATER_SALT), new ItemStack(MineFantasyItems.SALT), 100,
				200, 2, false, false);

		if (!ConfigHardcore.preventCook) {
			SmeltingRecipesMF.smeltFood();
		}
	}

	/**
	 * Cook in for out on anything (100C-200C, for ~15s)
	 */
	private static CookRecipe cookMeat(Item in, Item out) {
		return MineFantasyReforgedAPI.addCookingRecipe(new ItemStack(in), new ItemStack(out), 100, 200, 15, false);
	}

	/*
	 * Cook in for out enclosed in ovens
	 *
	 * @param temp   how hot the oven is
	 * @param offset the room for error +/-
	 * @param time   how much time roughly
	 */

	private static CookRecipe bake(Item in, Item out, int mint, int maxt, int time, int burn_time, boolean burn) {
		return MineFantasyReforgedAPI.addCookingRecipe(new ItemStack(in), new ItemStack(out),
				new ItemStack(MineFantasyItems.BURNT_FOOD), mint, maxt, time, burn_time, true, burn);
	}

	/*
	 * Cook in for out enclosed in ovens
	 *
	 * @param temp   how hot the oven is
	 * @param offset the room for error +/-
	 * @param time   how much time roughly
	 */

	private static CookRecipe bake(Item in, Item out, int mint, int maxt, int time, int burn_time, Item burn) {
		return MineFantasyReforgedAPI.addCookingRecipe(new ItemStack(in), new ItemStack(out), new ItemStack(burn), mint, maxt,
				time, burn_time, true);
	}

	private static void addCeramics() {
		bakeCeramic(MineFantasyItems.CLAY_POT_UNCOOKED, MineFantasyItems.CLAY_POT, 1000, 5);
		bakeCeramic(MineFantasyItems.JUG_UNCOOKED, MineFantasyItems.JUG_EMPTY, 1000, 5);
		bakeCeramic(MineFantasyItems.PIE_TRAY_UNCOOKED, MineFantasyItems.PIE_TRAY, 1000, 10);
		bakeCeramic(MineFantasyItems.INGOT_MOULD_UNCOOKED, MineFantasyItems.INGOT_MOULD, 1000, 5);
		bakeCeramic(MineFantasyItems.MINE_CASING_UNCOOKED, MineFantasyItems.MINE_CASING_CERAMIC, 1000, 10);
		bakeCeramic(MineFantasyItems.BOMB_CASING_UNCOOKED, MineFantasyItems.BOMB_CASING_CERAMIC, 1000, 10);

		bakeCeramic(MineFantasyItems.FIRECLAY_BRICK, MineFantasyItems.STRONG_BRICK, 1500, 5);
	}

	private static CookRecipe bakeCeramic(Item clay, Item ceramic, int temp, int time) {
		if (!ConfigHardcore.preventCook) {
			GameRegistry.addSmelting(clay, new ItemStack(ceramic), 0F);
		}
		return MineFantasyReforgedAPI.addCookingRecipe(new ItemStack(clay), new ItemStack(ceramic), null, temp, 1000, time, 0,
				true, false);

	}
}
