package minefantasy.mfr.recipe;

import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.cooking.CookRecipe;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.FoodListMFR;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CookingRecipes {
    public static void init() {
        cookMeat(FoodListMFR.HORSE_RAW, FoodListMFR.HORSE_COOKED);
        cookMeat(FoodListMFR.WOLF_RAW, FoodListMFR.WOLF_COOKED);
        cookMeat(FoodListMFR.SAUSAGE_RAW, FoodListMFR.SAUSAGE_COOKED);
        cookMeat(FoodListMFR.GENERIC_MEAT_UNCOOKED, FoodListMFR.GENERIC_MEAT_COOKED);
        cookMeat(FoodListMFR.GENERIC_MEAT_STRIP_UNCOOKED, FoodListMFR.GENERIC_MEAT_STRIP_COOKED);
        cookMeat(FoodListMFR.GENERIC_MEAT_CHUNK_UNCOOKED, FoodListMFR.GENERIC_MEAT_CHUNK_COOKED);

        bake(FoodListMFR.DOUGH, FoodListMFR.BREADROLL, 150, 300, 10, 20, true);
        bake(FoodListMFR.RAW_BREAD, Items.BREAD, 150, 300, 20, 40, true);
        bake(FoodListMFR.CURDS, FoodListMFR.CHEESE_POT, 100, 300, 60, 20, false);
        bake(FoodListMFR.SWEETROLL_RAW, FoodListMFR.SWEETROLL_UNICED, 150, 250, 20, 10, true);

        bake(FoodListMFR.PIE_PUMPKIN_UNCOOKED, FoodListMFR.PIE_PUMPKIN_COOKED, 150, 300, 20, 20, FoodListMFR.BURNT_PIE);
        bake(FoodListMFR.PIE_APPLE_UNCOOKED, FoodListMFR.PIE_APPLE_COOKED, 150, 300, 25, 20, FoodListMFR.BURNT_PIE);
        bake(FoodListMFR.PIE_BERRY_UNCOOKED, FoodListMFR.PIE_BERRY_COOKED, 150, 300, 25, 20, FoodListMFR.BURNT_PIE);
        bake(FoodListMFR.PIE_MEAT_UNCOOKED, FoodListMFR.PIE_MEAT_COOKED, 150, 300, 30, 20, FoodListMFR.BURNT_PIE);
        bake(FoodListMFR.PIE_SHEPARD_UNCOOKED, FoodListMFR.PIE_SHEPARD_COOKED, 150, 300, 30, 20, FoodListMFR.BURNT_PIE);

        bake(FoodListMFR.CAKE_SIMPLE_RAW, FoodListMFR.CAKE_SIMPLE_UNICED, 150, 250, 30, 30, FoodListMFR.BURNT_CAKE);
        bake(FoodListMFR.CAKE_RAW, FoodListMFR.CAKE_UNICED, 150, 250, 40, 20, FoodListMFR.BURNT_CAKE);
        bake(FoodListMFR.CAKE_CHOC_RAW, FoodListMFR.CAKE_CHOC_UNICED, 150, 250, 40, 20, FoodListMFR.BURNT_CAKE);
        bake(FoodListMFR.CAKE_CARROT_RAW, FoodListMFR.CAKE_CARROT_UNICED, 150, 250, 40, 20, FoodListMFR.BURNT_CAKE);
        bake(FoodListMFR.CAKE_BF_RAW, FoodListMFR.CAKE_BF_UNICED, 150, 250, 50, 10, FoodListMFR.BURNT_CAKE);
        bake(FoodListMFR.ECLAIR_RAW, FoodListMFR.ECLAIR_UNICED, 150, 250, 60, 5, true);

        addCeramics();
        MineFantasyRebornAPI.addCookingRecipe(new ItemStack(FoodListMFR.GENERIC_MEAT_MINCE_UNCOOKED),
                new ItemStack(FoodListMFR.GENERIC_MEAT_MINCE_COOKED), new ItemStack(FoodListMFR.BURNT_POT), 100, 200, 10,
                false);

        MineFantasyRebornAPI.addCookingRecipe(new ItemStack(FoodListMFR.BOWL_WATER_SALT), new ItemStack(FoodListMFR.SALT), 100,
                200, 2, false, false);

        if (!ConfigHardcore.preventCook) {
            smeltFood();
        }
    }

    /**
     * Cook in for out on anything (100C-200C, for ~15s)
     */
    private static CookRecipe cookMeat(Item in, Item out) {
        return MineFantasyRebornAPI.addCookingRecipe(new ItemStack(in), new ItemStack(out), 100, 200, 15, false);
    }

/*
     * Cook in for out enclosed in ovens
     *
     * @param temp   how hot the oven is
     * @param offset the room for error +/-
     * @param time   how much time roughly
     */

    private static CookRecipe bake(Item in, Item out, int mint, int maxt, int time, int burn_time, boolean burn) {
        return MineFantasyRebornAPI.addCookingRecipe(new ItemStack(in), new ItemStack(out),
                new ItemStack(FoodListMFR.BURNT_FOOD), mint, maxt, time, burn_time, true, burn);
    }

/*
     * Cook in for out enclosed in ovens
     *
     * @param temp   how hot the oven is
     * @param offset the room for error +/-
     * @param time   how much time roughly
     */

    private static CookRecipe bake(Item in, Item out, int mint, int maxt, int time, int burn_time, Item burn) {
        return MineFantasyRebornAPI.addCookingRecipe(new ItemStack(in), new ItemStack(out), new ItemStack(burn), mint, maxt,
                time, burn_time, true);
    }

    private static void smeltFood() {
        GameRegistry.addSmelting(FoodListMFR.HORSE_RAW, new ItemStack(FoodListMFR.HORSE_COOKED), 0.2F);
        GameRegistry.addSmelting(FoodListMFR.WOLF_RAW, new ItemStack(FoodListMFR.WOLF_COOKED), 0.2F);
        GameRegistry.addSmelting(FoodListMFR.GENERIC_MEAT_UNCOOKED, new ItemStack(FoodListMFR.GENERIC_MEAT_COOKED), 0);
        GameRegistry.addSmelting(FoodListMFR.GENERIC_MEAT_STRIP_UNCOOKED, new ItemStack(FoodListMFR.GENERIC_MEAT_STRIP_COOKED), 0);
        GameRegistry.addSmelting(FoodListMFR.GENERIC_MEAT_CHUNK_UNCOOKED, new ItemStack(FoodListMFR.GENERIC_MEAT_CHUNK_COOKED), 0);
        GameRegistry.addSmelting(FoodListMFR.GENERIC_MEAT_MINCE_UNCOOKED, new ItemStack(FoodListMFR.GENERIC_MEAT_MINCE_COOKED), 0);
        GameRegistry.addSmelting(FoodListMFR.BOWL_WATER_SALT, new ItemStack(FoodListMFR.SALT), 0);
        GameRegistry.addSmelting(FoodListMFR.SAUSAGE_RAW, new ItemStack(FoodListMFR.SAUSAGE_COOKED), 0);
    }

    private static void addCeramics() {
        bakeCeramic(ComponentListMFR.CLAY_POT_UNCOOKED, ComponentListMFR.CLAY_POT, 1000, 5);
        bakeCeramic(FoodListMFR.JUG_UNCOOKED, FoodListMFR.JUG_EMPTY, 1000, 5);
        bakeCeramic(ComponentListMFR.PIE_TRAY_UNCOOKED, FoodListMFR.PIE_TRAY, 1000, 10);
        bakeCeramic(ComponentListMFR.INGOT_MOULD_UNCOOKED, ComponentListMFR.INGOT_MOULD, 1000, 5);
        bakeCeramic(ComponentListMFR.MINE_CASING_UNCOOKED, ComponentListMFR.MINE_CASING, 1000, 10);
        bakeCeramic(ComponentListMFR.BOMB_CASING_UNCOOKED, ComponentListMFR.BOMB_CASING, 1000, 10);

        bakeCeramic(ComponentListMFR.FIRECLAY_BRICK, ComponentListMFR.STRONG_BRICK, 1500, 5);
    }

    private static CookRecipe bakeCeramic(Item clay, Item ceramic, int temp, int time) {
        if (!ConfigHardcore.preventCook) {
            GameRegistry.addSmelting(clay, new ItemStack(ceramic), 0F);
        }
        return MineFantasyRebornAPI.addCookingRecipe(new ItemStack(clay), new ItemStack(ceramic), null, temp, 1000, time, 0,
                true, false);

    }
}
