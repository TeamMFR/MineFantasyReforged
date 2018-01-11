package minefantasy.mf2.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.cooking.CookRecipe;
import minefantasy.mf2.config.ConfigHardcore;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CookingRecipes {
    public static void init() {
        cookMeat(FoodListMF.horse_raw, FoodListMF.horse_cooked);
        cookMeat(FoodListMF.wolf_raw, FoodListMF.wolf_cooked);
        cookMeat(FoodListMF.saussage_raw, FoodListMF.saussage_cooked);
        cookMeat(FoodListMF.generic_meat_uncooked, FoodListMF.generic_meat_cooked);
        cookMeat(FoodListMF.generic_meat_strip_uncooked, FoodListMF.generic_meat_strip_cooked);
        cookMeat(FoodListMF.generic_meat_chunk_uncooked, FoodListMF.generic_meat_chunk_cooked);

        bake(FoodListMF.dough, FoodListMF.breadroll, 150, 300, 10, 20, true);
        bake(FoodListMF.raw_bread, Items.bread, 150, 300, 20, 40, true);
        bake(FoodListMF.curds, FoodListMF.cheese_pot, 100, 300, 60, 20, false);
        bake(FoodListMF.sweetroll_raw, FoodListMF.sweetroll_uniced, 150, 250, 20, 10, true);

        bake(FoodListMF.pie_pumpkin_uncooked, FoodListMF.pie_pumpkin_cooked, 150, 300, 20, 20, FoodListMF.burnt_pie);
        bake(FoodListMF.pie_apple_uncooked, FoodListMF.pie_apple_cooked, 150, 300, 25, 20, FoodListMF.burnt_pie);
        bake(FoodListMF.pie_berry_uncooked, FoodListMF.pie_berry_cooked, 150, 300, 25, 20, FoodListMF.burnt_pie);
        bake(FoodListMF.pie_meat_uncooked, FoodListMF.pie_meat_cooked, 150, 300, 30, 20, FoodListMF.burnt_pie);
        bake(FoodListMF.pie_shepard_uncooked, FoodListMF.pie_shepard_cooked, 150, 300, 30, 20, FoodListMF.burnt_pie);

        bake(FoodListMF.cake_simple_raw, FoodListMF.cake_simple_uniced, 150, 250, 30, 30, FoodListMF.burnt_cake);
        bake(FoodListMF.cake_raw, FoodListMF.cake_uniced, 150, 250, 40, 20, FoodListMF.burnt_cake);
        bake(FoodListMF.cake_choc_raw, FoodListMF.cake_choc_uniced, 150, 250, 40, 20, FoodListMF.burnt_cake);
        bake(FoodListMF.cake_carrot_raw, FoodListMF.cake_carrot_uniced, 150, 250, 40, 20, FoodListMF.burnt_cake);
        bake(FoodListMF.cake_bf_raw, FoodListMF.cake_bf_uniced, 150, 250, 50, 10, FoodListMF.burnt_cake);
        bake(FoodListMF.eclair_raw, FoodListMF.eclair_uniced, 150, 250, 60, 5, true);

        addCeramics();
        MineFantasyAPI.addCookingRecipe(new ItemStack(FoodListMF.generic_meat_mince_uncooked),
                new ItemStack(FoodListMF.generic_meat_mince_cooked), new ItemStack(FoodListMF.burnt_pot), 100, 200, 10,
                false);

        MineFantasyAPI.addCookingRecipe(new ItemStack(FoodListMF.bowl_water_salt), new ItemStack(FoodListMF.salt), 100,
                200, 2, false, false);

        if (!ConfigHardcore.preventCook) {
            smeltFood();
        }
    }

    /**
     * Cook in for out on anything (100C-200C, for ~15s)
     */
    private static CookRecipe cookMeat(Item in, Item out) {
        return MineFantasyAPI.addCookingRecipe(new ItemStack(in), new ItemStack(out), 100, 200, 15, false);
    }

    /**
     * Cook in for out enclosed in ovens
     *
     * @param temp   how hot the oven is
     * @param offset the room for error +/-
     * @param time   how much time roughly
     */
    private static CookRecipe bake(Item in, Item out, int mint, int maxt, int time, int burn_time, boolean burn) {
        return MineFantasyAPI.addCookingRecipe(new ItemStack(in), new ItemStack(out),
                new ItemStack(FoodListMF.burnt_food), mint, maxt, time, burn_time, true, burn);
    }

    /**
     * Cook in for out enclosed in ovens
     *
     * @param temp   how hot the oven is
     * @param offset the room for error +/-
     * @param time   how much time roughly
     */
    private static CookRecipe bake(Item in, Item out, int mint, int maxt, int time, int burn_time, Item burn) {
        return MineFantasyAPI.addCookingRecipe(new ItemStack(in), new ItemStack(out), new ItemStack(burn), mint, maxt,
                time, burn_time, true);
    }

    private static void smeltFood() {
        GameRegistry.addSmelting(FoodListMF.horse_raw, new ItemStack(FoodListMF.horse_cooked), 0.2F);
        GameRegistry.addSmelting(FoodListMF.wolf_raw, new ItemStack(FoodListMF.wolf_cooked), 0.2F);
        GameRegistry.addSmelting(FoodListMF.generic_meat_uncooked, new ItemStack(FoodListMF.generic_meat_cooked), 0);
        GameRegistry.addSmelting(FoodListMF.generic_meat_strip_uncooked,
                new ItemStack(FoodListMF.generic_meat_strip_cooked), 0);
        GameRegistry.addSmelting(FoodListMF.generic_meat_chunk_uncooked,
                new ItemStack(FoodListMF.generic_meat_chunk_cooked), 0);
        GameRegistry.addSmelting(FoodListMF.generic_meat_mince_uncooked,
                new ItemStack(FoodListMF.generic_meat_mince_cooked), 0);
        GameRegistry.addSmelting(FoodListMF.bowl_water_salt, new ItemStack(FoodListMF.salt), 0);
        GameRegistry.addSmelting(FoodListMF.saussage_raw, new ItemStack(FoodListMF.saussage_cooked), 0);
    }

    private static void addCeramics() {
        bakeCeramic(ComponentListMF.clay_pot_uncooked, ComponentListMF.clay_pot, 1000, 5);
        bakeCeramic(FoodListMF.jug_uncooked, FoodListMF.jug_empty, 1000, 5);
        bakeCeramic(ComponentListMF.pie_tray_uncooked, FoodListMF.pie_tray, 1000, 10);
        bakeCeramic(ComponentListMF.ingot_mould_uncooked, ComponentListMF.ingot_mould, 1000, 5);
        bakeCeramic(ComponentListMF.mine_casing_uncooked, ComponentListMF.mine_casing, 1000, 10);
        bakeCeramic(ComponentListMF.bomb_casing_uncooked, ComponentListMF.bomb_casing, 1000, 10);

        bakeCeramic(ComponentListMF.fireclay_brick, ComponentListMF.strong_brick, 1500, 5);
    }

    private static CookRecipe bakeCeramic(Item clay, Item ceramic, int temp, int time) {
        if (!ConfigHardcore.preventCook) {
            GameRegistry.addSmelting(clay, new ItemStack(ceramic), 0F);
        }
        return MineFantasyAPI.addCookingRecipe(new ItemStack(clay), new ItemStack(ceramic), null, temp, 1000, time, 0,
                true, false);

    }
}
