package minefantasy.mfr.recipe;

import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.cooking.CookRecipe;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CookingRecipes {
    public static void init() {
        cookMeat(FoodListMFR.horse_raw, FoodListMFR.horse_cooked);
        cookMeat(FoodListMFR.wolf_raw, FoodListMFR.wolf_cooked);
        cookMeat(FoodListMFR.saussage_raw, FoodListMFR.saussage_cooked);
        cookMeat(FoodListMFR.generic_meat_uncooked, FoodListMFR.generic_meat_cooked);
        cookMeat(FoodListMFR.generic_meat_strip_uncooked, FoodListMFR.generic_meat_strip_cooked);
        cookMeat(FoodListMFR.generic_meat_chunk_uncooked, FoodListMFR.generic_meat_chunk_cooked);

        bake(FoodListMFR.dough, FoodListMFR.breadroll, 150, 300, 10, 20, true);
        bake(FoodListMFR.raw_bread, Items.BREAD, 150, 300, 20, 40, true);
        bake(FoodListMFR.curds, FoodListMFR.cheese_pot, 100, 300, 60, 20, false);
        bake(FoodListMFR.sweetroll_raw, FoodListMFR.sweetroll_uniced, 150, 250, 20, 10, true);

        bake(FoodListMFR.pie_pumpkin_uncooked, FoodListMFR.pie_pumpkin_cooked, 150, 300, 20, 20, FoodListMFR.burnt_pie);
        bake(FoodListMFR.pie_apple_uncooked, FoodListMFR.pie_apple_cooked, 150, 300, 25, 20, FoodListMFR.burnt_pie);
        bake(FoodListMFR.pie_berry_uncooked, FoodListMFR.pie_berry_cooked, 150, 300, 25, 20, FoodListMFR.burnt_pie);
        bake(FoodListMFR.pie_meat_uncooked, FoodListMFR.pie_meat_cooked, 150, 300, 30, 20, FoodListMFR.burnt_pie);
        bake(FoodListMFR.pie_shepard_uncooked, FoodListMFR.pie_shepard_cooked, 150, 300, 30, 20, FoodListMFR.burnt_pie);

        bake(FoodListMFR.cake_simple_raw, FoodListMFR.cake_simple_uniced, 150, 250, 30, 30, FoodListMFR.burnt_cake);
        bake(FoodListMFR.cake_raw, FoodListMFR.cake_uniced, 150, 250, 40, 20, FoodListMFR.burnt_cake);
        bake(FoodListMFR.cake_choc_raw, FoodListMFR.cake_choc_uniced, 150, 250, 40, 20, FoodListMFR.burnt_cake);
        bake(FoodListMFR.cake_carrot_raw, FoodListMFR.cake_carrot_uniced, 150, 250, 40, 20, FoodListMFR.burnt_cake);
        bake(FoodListMFR.cake_bf_raw, FoodListMFR.cake_bf_uniced, 150, 250, 50, 10, FoodListMFR.burnt_cake);
        bake(FoodListMFR.eclair_raw, FoodListMFR.eclair_uniced, 150, 250, 60, 5, true);

        addCeramics();
        MineFantasyRebornAPI.addCookingRecipe(new ItemStack(FoodListMFR.generic_meat_mince_uncooked),
                new ItemStack(FoodListMFR.generic_meat_mince_cooked), new ItemStack(FoodListMFR.burnt_pot), 100, 200, 10,
                false);

        MineFantasyRebornAPI.addCookingRecipe(new ItemStack(FoodListMFR.bowl_water_salt), new ItemStack(FoodListMFR.salt), 100,
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
                new ItemStack(FoodListMFR.burnt_food), mint, maxt, time, burn_time, true, burn);
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
        GameRegistry.addSmelting(FoodListMFR.horse_raw, new ItemStack(FoodListMFR.horse_cooked), 0.2F);
        GameRegistry.addSmelting(FoodListMFR.wolf_raw, new ItemStack(FoodListMFR.wolf_cooked), 0.2F);
        GameRegistry.addSmelting(FoodListMFR.generic_meat_uncooked, new ItemStack(FoodListMFR.generic_meat_cooked), 0);
        GameRegistry.addSmelting(FoodListMFR.generic_meat_strip_uncooked,
                new ItemStack(FoodListMFR.generic_meat_strip_cooked), 0);
        GameRegistry.addSmelting(FoodListMFR.generic_meat_chunk_uncooked,
                new ItemStack(FoodListMFR.generic_meat_chunk_cooked), 0);
        GameRegistry.addSmelting(FoodListMFR.generic_meat_mince_uncooked,
                new ItemStack(FoodListMFR.generic_meat_mince_cooked), 0);
        GameRegistry.addSmelting(FoodListMFR.bowl_water_salt, new ItemStack(FoodListMFR.salt), 0);
        GameRegistry.addSmelting(FoodListMFR.saussage_raw, new ItemStack(FoodListMFR.saussage_cooked), 0);
    }

    private static void addCeramics() {
        bakeCeramic(ComponentListMFR.clay_pot_uncooked, ComponentListMFR.clay_pot, 1000, 5);
        bakeCeramic(FoodListMFR.jug_uncooked, FoodListMFR.jug_empty, 1000, 5);
        bakeCeramic(ComponentListMFR.pie_tray_uncooked, FoodListMFR.pie_tray, 1000, 10);
        bakeCeramic(ComponentListMFR.ingot_mould_uncooked, ComponentListMFR.ingot_mould, 1000, 5);
        bakeCeramic(ComponentListMFR.mine_casing_uncooked, ComponentListMFR.mine_casing, 1000, 10);
        bakeCeramic(ComponentListMFR.bomb_casing_uncooked, ComponentListMFR.bomb_casing, 1000, 10);

        bakeCeramic(ComponentListMFR.fireclay_brick, ComponentListMFR.strong_brick, 1500, 5);
    }

    private static CookRecipe bakeCeramic(Item clay, Item ceramic, int temp, int time) {
        if (!ConfigHardcore.preventCook) {
            GameRegistry.addSmelting(clay, new ItemStack(ceramic), 0F);
        }
        return MineFantasyRebornAPI.addCookingRecipe(new ItemStack(clay), new ItemStack(ceramic), null, temp, 1000, time, 0,
                true, false);

    }
}
