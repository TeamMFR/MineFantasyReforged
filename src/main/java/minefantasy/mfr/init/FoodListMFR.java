package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.item.ItemBurntFood;
import minefantasy.mfr.item.ItemComponentMFR;
import minefantasy.mfr.item.ItemFoodMFR;
import minefantasy.mfr.item.ItemJug;
import minefantasy.mfr.item.ItemMultiFood;
import minefantasy.mfr.item.ItemUnfinishedFood;
import minefantasy.mfr.recipe.CookRecipe;
import minefantasy.mfr.util.Utils;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(MineFantasyReborn.MOD_ID)
@Mod.EventBusSubscriber(modid = MineFantasyReborn.MOD_ID)
public class FoodListMFR {
    public static float SAT_MODIFIER = 1.0F;
    
    // MORSELS
    public static Item WOLF_RAW = Utils.nullValue();
    public static Item WOLF_COOKED = Utils.nullValue();
    public static Item HORSE_RAW = Utils.nullValue();
    public static Item HORSE_COOKED = Utils.nullValue();
    public static Item GENERIC_MEAT_UNCOOKED = Utils.nullValue();
    public static Item GENERIC_MEAT_COOKED = Utils.nullValue();
    public static Item GENERIC_MEAT_STRIP_UNCOOKED = Utils.nullValue();
    public static Item GENERIC_MEAT_STRIP_COOKED = Utils.nullValue();
    public static Item GENERIC_MEAT_CHUNK_UNCOOKED = Utils.nullValue();
    public static Item GENERIC_MEAT_CHUNK_COOKED = Utils.nullValue();
    public static Item GENERIC_MEAT_MINCE_UNCOOKED = Utils.nullValue();
    public static Item GENERIC_MEAT_MINCE_COOKED = Utils.nullValue();
    public static Item FLOUR = Utils.nullValue();
    public static Item BREADCRUMBS = Utils.nullValue();
    public static Item GUTS = Utils.nullValue();
    public static Item BREADROLL = Utils.nullValue();
    public static Item BREAD_SLICE = Utils.nullValue();
    public static Item CURDS = Utils.nullValue();
    public static Item CHEESE_POT = Utils.nullValue();
    public static Item CHEESE_SLICE = Utils.nullValue();

    // T1 (basic mixing)
    // Util: Roast, Prep Block (Stone-Bronze Age)
    public static Item STEW = Utils.nullValue();
    public static Item OATS = Utils.nullValue();

    // T2 (Basic baking, stone oven, processed mixing)
    // Util: Stone Oven, Prep Block (Bronze Age - Early Iron Age)
    public static Item CHEESE_ROLL = Utils.nullValue();
    public static Item JERKY = Utils.nullValue();
    public static Item SAUSAGE_RAW = Utils.nullValue();
    public static Item SAUSAGE_COOKED = Utils.nullValue();
    public static Item SWEETROLL_UNICED = Utils.nullValue();
    public static Item SWEETROLL = Utils.nullValue();
    public static Item SANDWITCH_MEAT = Utils.nullValue();
    public static Item SANDWITCH_BIG = Utils.nullValue();

    // T3 (Quality baking, metal oven)
    // Util: Metal Oven, Prep Block, Steel Tools (Mid Iron Age)
    public static Item MEATPIE_SLICE = Utils.nullValue();
    public static Item PIESLICE_APPLE = Utils.nullValue();
    public static Item PIESLICE_BERRY = Utils.nullValue();

    // T4 (Advanced baking, multiple processes, temperature regulation)
    // Util : Metal Oven, Prep Block, Full tool set, Proper kitchen setup (Mid Iron
    // Age)
    public static Item PIESLICE_SHEPARDS = Utils.nullValue();
    public static Item CAKE_SLICE = Utils.nullValue();
    public static Item CARROTCAKE_SLICE = Utils.nullValue();

    // T5 (Advanced baking, multiple process, temperature regulation, many
    // ingreedients)
    public static Item CHOCCAKE_SLICE = Utils.nullValue();

    // T6 (Perfected meals, extremely difficylt to create)
    public static Item BFCAKE_SLICE = Utils.nullValue();
    public static Item ECLAIR_UNICED = Utils.nullValue();
    public static Item ECLAIR_EMPTY = Utils.nullValue();
    public static Item ECLAIR = Utils.nullValue();

    // MISC
    public static Item CAKE_TIN = Utils.nullValue();
    public static Item PIE_TRAY = Utils.nullValue();
    public static Item ICING = Utils.nullValue();
    public static Item CUSTARD = Utils.nullValue();
    public static Item COCA_POWDER = Utils.nullValue();
    public static Item CHOCOLATE = Utils.nullValue();
    public static Item CHOC_CHIPS = Utils.nullValue();
    public static Item BERRIES = Utils.nullValue();
    public static Item BERRIES_JUICY = Utils.nullValue();
    public static Item SWEETROLL_RAW = Utils.nullValue();
    public static Item ECLAIR_RAW = Utils.nullValue();
    public static Item CAKE_RAW = Utils.nullValue();
    public static Item CAKE_SIMPLE_RAW = Utils.nullValue();
    public static Item CAKE_CARROT_RAW = Utils.nullValue();
    public static Item CAKE_CHOC_RAW = Utils.nullValue();
    public static Item CAKE_BF_RAW = Utils.nullValue();
    public static Item CAKE_UNICED = Utils.nullValue();
    public static Item CAKE_SIMPLE_UNICED = Utils.nullValue();
    public static Item CAKE_CARROT_UNICED = Utils.nullValue();
    public static Item CAKE_CHOC_UNICED = Utils.nullValue();
    public static Item CAKE_BF_UNICED = Utils.nullValue();
    public static Item PIE_MEAT_UNCOOKED = Utils.nullValue();
    public static Item PIE_APPLE_UNCOOKED = Utils.nullValue();
    public static Item PIE_BERRY_UNCOOKED = Utils.nullValue();
    public static Item PIE_SHEPARD_UNCOOKED = Utils.nullValue();
    public static Item PIE_PUMPKIN_UNCOOKED = Utils.nullValue();
    public static Item PIE_MEAT_COOKED = Utils.nullValue();
    public static Item PIE_APPLE_COOKED = Utils.nullValue();
    public static Item PIE_BERRY_COOKED = Utils.nullValue();
    public static Item PIE_SHEPARD_COOKED = Utils.nullValue();
    public static Item PIE_PUMPKIN_COOKED = Utils.nullValue();
    public static Item SALT = Utils.nullValue();
    public static Item SUGAR_POT = Utils.nullValue();
    public static Item BOWL_WATER_SALT = Utils.nullValue();
    public static Item DOUGH = Utils.nullValue();
    public static Item PASTRY = Utils.nullValue();
    public static Item RAW_BREAD = Utils.nullValue();
    public static Item JUG_UNCOOKED = Utils.nullValue();
    public static Item JUG_EMPTY = Utils.nullValue();
    public static Item JUG_WATER = Utils.nullValue();
    public static Item JUG_MILK = Utils.nullValue();
    public static Item BURNT_FOOD = Utils.nullValue();
    public static Item BURNT_POT = Utils.nullValue();
    public static Item BURNT_PIE = Utils.nullValue();

    // SPECIAL RECIPES
    public static Item BURNT_CAKE = Utils.nullValue();
    
    public static void init() {
        // MORSELS
        WOLF_RAW = new ItemFoodMFR("wolf_raw", 2, 0.2F, true);
        WOLF_COOKED = new ItemFoodMFR("wolf_cooked", 6, 0.6F, true);
        HORSE_RAW = new ItemFoodMFR("horse_raw", 4, 0.4F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER,500,0), 0.5F);
        HORSE_COOKED = new ItemFoodMFR("horse_cooked", 10, 1.0F, true);
        GENERIC_MEAT_UNCOOKED = new ItemFoodMFR("generic_meat_uncooked", 2, 0.2F, true);
        GENERIC_MEAT_COOKED = new ItemFoodMFR("generic_meat_cooked", 5, 0.5F, true);
        GENERIC_MEAT_STRIP_UNCOOKED = new ItemFoodMFR("generic_meat_strip_uncooked", 2, 0.2F, true);
        GENERIC_MEAT_STRIP_COOKED = new ItemFoodMFR("generic_meat_strip_cooked", 5, 0.5F, true);
        GENERIC_MEAT_CHUNK_UNCOOKED = new ItemFoodMFR("generic_meat_chunk_uncooked", 2, 0.2F, true);
        GENERIC_MEAT_CHUNK_COOKED = new ItemFoodMFR("generic_meat_chunk_cooked", 5, 0.5F, true);
        GENERIC_MEAT_MINCE_UNCOOKED = new ItemFoodMFR("generic_meat_mince_uncooked", 2, 0.2F, true).setContainerItem(ComponentListMFR.CLAY_POT);
        GENERIC_MEAT_MINCE_COOKED = new ItemFoodMFR("generic_meat_mince_cooked", 5, 0.5F, true).setContainerItem(ComponentListMFR.CLAY_POT);
        FLOUR = new ItemComponentMFR("flour", 0).setCreativeTab(MineFantasyTabs.tabFood).setContainerItem(ComponentListMFR.CLAY_POT);
        BREADCRUMBS = new ItemComponentMFR("breadcrumbs", 0).setCreativeTab(MineFantasyTabs.tabFood).setContainerItem(ComponentListMFR.CLAY_POT);
        GUTS = new ItemComponentMFR("guts", 0).setCreativeTab(MineFantasyTabs.tabFood);
        BREADROLL = new ItemFoodMFR("breadroll", 5, 1.0F, false);
        BREAD_SLICE = new ItemFoodMFR("bread_slice", 2, 1.0F, false);
        CURDS = new ItemUnfinishedFood("curds");
        CHEESE_POT = new ItemUnfinishedFood("cheese_pot").setContainerItem(ComponentListMFR.CLAY_POT);
        CHEESE_SLICE = new ItemFoodMFR("cheese_slice", 4, 1.0F, false, 0).setFoodStats(1, 0.0F, 0.0F, 1.0F);

        // T1 (basic mixing)
        // Util: Roast, Prep Block (Stone-Bronze Age)
        STEW = new ItemFoodMFR("stew", 5, 1.0F, false, 0).setFoodStats(1, 0.0F, 0.0F, 1.0F).setReturnItem(Items.BOWL).setMaxStackSize(1);
        OATS = new ItemFoodMFR("oats", 5, 1.0F, false, 0).setFoodStats(1, 0.0F, 0.8F, 0.2F).setReturnItem(Items.BOWL).setMaxStackSize(1);

        // T2 (Basic baking, stone oven, processed mixing)
        // Util: Stone Oven, Prep Block (Bronze Age - Early Iron Age)
        CHEESE_ROLL = new ItemFoodMFR("cheese_roll", 6, 1.0F, false, 0).setFoodStats(2, 0.0F, 0.4F, 0.6F).setMaxStackSize(1);
        JERKY = new ItemFoodMFR("jerky", 6, 1.0F, true, 0).setFoodStats(2, 0.0F, 0.0F, 1.0F).setMaxStackSize(8);
        SAUSAGE_RAW = new ItemFoodMFR("saussage_raw", 4, 1.0F, true, 0).setFoodStats(2, 0.0F, 0.1F, 0.6F).setMaxStackSize(16);
        SAUSAGE_COOKED = new ItemFoodMFR("saussage_cooked", 8, 1.0F, true, 0).setFoodStats(2, 0.0F, 0.2F, 0.8F).setMaxStackSize(16);
        SWEETROLL_UNICED = new ItemFoodMFR("sweetroll_uniced", 5, 1.0F, false, 0).setFoodStats(2, 0.5F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(64);
        SWEETROLL = new ItemMultiFood("sweetroll", 2, 3, 1.0F, false, 0).setFoodStats(2, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible();
        SANDWITCH_MEAT = new ItemMultiFood("sandwitch_meat", 2, 6, 1.0F, false, 0).setFoodStats(2, 0.0F, 0.5F, 0.5F).setUnlocalizedName("sandwitch");
        SANDWITCH_BIG = new ItemMultiFood("sandwitch_big", 4, 6, 1.0F, false, 1).setFoodStats(2, 0.0F, 0.5F, 0.5F);

        // T3 (Quality baking, metal oven)
        // Util: Metal Oven, Prep Block, Steel Tools (Mid Iron Age)
        MEATPIE_SLICE = new ItemFoodMFR("meatpie_slice", 8, 1.0F, false, 0).setFoodStats(3, 0.0F, 0.2F, 0.8F).setMaxStackSize(1);
        PIESLICE_APPLE = new ItemFoodMFR("pieslice_apple", 5, 1.0F, false, 0).setFoodStats(3, 0.8F, 0.2F, 0.0F).setEatTime(16).setAlwaysEdible().setAlwaysEdible();
        PIESLICE_BERRY = new ItemFoodMFR("pieslice_berry", 5, 1.0F, false, 0).setFoodStats(3, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setAlwaysEdible();

        // T4 (Advanced baking, multiple processes, temperature regulation)
        // Util : Metal Oven, Prep Block, Full tool set, Proper kitchen setup (Mid Iron
        // Age)
        PIESLICE_SHEPARDS = new ItemFoodMFR("pieslice_shepards", 10, 1.0F, false, 1).setFoodStats(4, 0.0F, 0.5F, 0.5F);
        CAKE_SLICE = new ItemFoodMFR("cake_slice", 3, 0.8F, false, 0).setFoodStats(3, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1);
        CARROTCAKE_SLICE = new ItemFoodMFR("carrotcake_slice", 4, 0.8F, false, 0).setFoodStats(4, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1);

        // T5 (Advanced baking, multiple process, temperature regulation, many
        // ingreedients)
        CHOCCAKE_SLICE = new ItemFoodMFR("choccake_slice", 4, 0.8F, false, 0).setFoodStats(4, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1);

        // T6 (Perfected meals, extremely difficylt to create)
        BFCAKE_SLICE = new ItemFoodMFR("bfcake_slice", 6, 1.0F, false, 1).setFoodStats(5, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1);
        ECLAIR_UNICED = new ItemFoodMFR("eclair_uniced", 5, 1.0F, false, 0).setFoodStats(5, 0.3F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(64);
        ECLAIR_EMPTY = new ItemFoodMFR("eclair_empty", 5, 1.0F, false, 0).setFoodStats(5, 0.5F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(64);
        ECLAIR = new ItemMultiFood("eclair", 4, 4, 1.0F, false, 1).setFoodStats(5, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1);

        // MISC
        CAKE_TIN = new ItemComponentMFR("cake_tin", 0).setCreativeTab(MineFantasyTabs.tabFood);
        PIE_TRAY = new ItemComponentMFR("pie_tray", 0).setStoragePlacement("bigplate", "tray").setCreativeTab(MineFantasyTabs.tabFood);
        ICING = new ItemComponentMFR("icing", 0).setCreativeTab(MineFantasyTabs.tabFood).setContainerItem(ComponentListMFR.CLAY_POT);
        CUSTARD = new ItemComponentMFR("custard", 0).setCreativeTab(MineFantasyTabs.tabFood).setContainerItem(ComponentListMFR.CLAY_POT);
        COCA_POWDER = new ItemComponentMFR("coca_powder", 0).setCreativeTab(MineFantasyTabs.tabFood).setContainerItem(ComponentListMFR.CLAY_POT);
        CHOCOLATE = new ItemComponentMFR("chocolate", 0).setCreativeTab(MineFantasyTabs.tabFood).setContainerItem(ComponentListMFR.CLAY_POT);
        CHOC_CHIPS = new ItemComponentMFR("choc_chips", 0).setCreativeTab(MineFantasyTabs.tabFood).setContainerItem(ComponentListMFR.CLAY_POT);
        BERRIES = new ItemFoodMFR("berries", 2, 2.0F, false).setEatTime(10).setStaminaRestore(10F).setAlwaysEdible();
        BERRIES_JUICY = new ItemFoodMFR("berries_juicy", 3, 5.0F, false).setEatTime(10).setStaminaRestore(25F).setRarity(1).setAlwaysEdible();
        SWEETROLL_RAW = new ItemUnfinishedFood("sweetroll_raw").setMaxStackSize(64);
        ECLAIR_RAW = new ItemUnfinishedFood("eclair_raw").setMaxStackSize(64);
        CAKE_RAW = new ItemUnfinishedFood("cake_raw");
        CAKE_SIMPLE_RAW = new ItemUnfinishedFood("cake_simple_raw");
        CAKE_CARROT_RAW = new ItemUnfinishedFood("cake_carrot_raw");
        CAKE_CHOC_RAW = new ItemUnfinishedFood("cake_choc_raw");
        CAKE_BF_RAW = new ItemUnfinishedFood("cake_bf_raw");
        CAKE_UNICED = new ItemUnfinishedFood("cake_uniced").setContainerItem(CAKE_TIN);
        CAKE_SIMPLE_UNICED = new ItemUnfinishedFood("cake_simple_uniced").setContainerItem(CAKE_TIN);
        CAKE_CARROT_UNICED = new ItemUnfinishedFood("cake_carrot_uniced").setContainerItem(CAKE_TIN);
        CAKE_CHOC_UNICED = new ItemUnfinishedFood("cake_choc_uniced").setContainerItem(CAKE_TIN);
        CAKE_BF_UNICED = new ItemUnfinishedFood("cake_bf_uniced").setContainerItem(CAKE_TIN);
        PIE_MEAT_UNCOOKED = new ItemUnfinishedFood("pie_meat_uncooked");
        PIE_APPLE_UNCOOKED = new ItemUnfinishedFood("pie_apple_uncooked");
        PIE_BERRY_UNCOOKED = new ItemUnfinishedFood("pie_berry_uncooked");
        PIE_SHEPARD_UNCOOKED = new ItemUnfinishedFood("pie_shepard_uncooked");
        PIE_PUMPKIN_UNCOOKED = new ItemUnfinishedFood("pie_pumpkin_uncooked");
        PIE_MEAT_COOKED = new ItemUnfinishedFood("pie_meat_cooked").setContainerItem(PIE_TRAY);
        PIE_APPLE_COOKED = new ItemUnfinishedFood("pie_apple_cooked").setContainerItem(PIE_TRAY);
        PIE_BERRY_COOKED = new ItemUnfinishedFood("pie_berry_cooked").setContainerItem(PIE_TRAY);
        PIE_SHEPARD_COOKED = new ItemUnfinishedFood("pie_shepard_cooked").setContainerItem(PIE_TRAY);
        PIE_PUMPKIN_COOKED = new ItemUnfinishedFood("pie_pumpkin_cooked").setContainerItem(PIE_TRAY);
        SALT = new ItemComponentMFR("salt", 0).setCreativeTab(MineFantasyTabs.tabFood).setContainerItem(ComponentListMFR.CLAY_POT);
        SUGAR_POT = new ItemComponentMFR("sugar_pot", 0).setCreativeTab(MineFantasyTabs.tabFood).setContainerItem(ComponentListMFR.CLAY_POT);
        BOWL_WATER_SALT = new ItemComponentMFR("bowl_water_salt", 0).setCreativeTab(MineFantasyTabs.tabFood);
        DOUGH = new ItemUnfinishedFood("dough").setMaxStackSize(64);
        PASTRY = new ItemUnfinishedFood("pastry").setMaxStackSize(64);
        RAW_BREAD = new ItemUnfinishedFood("raw_bread").setMaxStackSize(64);
        JUG_UNCOOKED = new ItemJug("uncooked");
        JUG_EMPTY = new ItemJug("empty").setStoragePlacement("jug", "jug");
        JUG_WATER = new ItemJug("water").setStoragePlacement("jug", "jugwater").setContainerItem(JUG_EMPTY);
        JUG_MILK = new ItemJug("milk").setStoragePlacement("jug", "jugmilk").setContainerItem(JUG_EMPTY);
        BURNT_FOOD = new ItemBurntFood("burnt_food");
        BURNT_POT = new ItemBurntFood("burnt_pot").setContainerItem(ComponentListMFR.CLAY_POT);
        BURNT_PIE = new ItemBurntFood("burnt_pie").setContainerItem(PIE_TRAY);

        //SPECIAL RECIPES
        BURNT_CAKE = new ItemBurntFood("burnt_cake").setContainerItem(CAKE_TIN);
    }
    
    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        // MORSELS
        registry.register(WOLF_RAW);
        registry.register(WOLF_COOKED);
        registry.register(HORSE_RAW);
        registry.register(HORSE_COOKED);
        registry.register(GENERIC_MEAT_UNCOOKED);
        registry.register(GENERIC_MEAT_COOKED);
        registry.register(GENERIC_MEAT_STRIP_UNCOOKED);
        registry.register(GENERIC_MEAT_STRIP_COOKED);
        registry.register(GENERIC_MEAT_CHUNK_UNCOOKED);
        registry.register(GENERIC_MEAT_CHUNK_COOKED);
        registry.register(GENERIC_MEAT_MINCE_UNCOOKED);
        registry.register(GENERIC_MEAT_MINCE_COOKED);
        registry.register(FLOUR);
        registry.register(BREADCRUMBS);
        registry.register(GUTS);
        registry.register(BREADROLL);
        registry.register(BREAD_SLICE);
        registry.register(CURDS);
        registry.register(CHEESE_POT);
        registry.register(CHEESE_SLICE);

        // T1 (basic mixing)
        // Util: Roast, Prep Block (Stone-Bronze Age)
        registry.register(STEW);
        registry.register(OATS);

        // T2 (Basic baking, stone oven, processed mixing)
        // Util: Stone Oven, Prep Block (Bronze Age - Early Iron Age)
        registry.register(CHEESE_ROLL);
        registry.register(JERKY);
        registry.register(SAUSAGE_RAW);
        registry.register(SAUSAGE_COOKED);
        registry.register(SWEETROLL_UNICED);

        // TODO: fix item texture to change with metadata
        registry.register(SWEETROLL);
        registry.register(SANDWITCH_MEAT);
        registry.register(SANDWITCH_BIG);

        // T3 (Quality baking, metal oven)
        // Util: Metal Oven, Prep Block, Steel Tools (Mid Iron Age)
        registry.register(MEATPIE_SLICE);
        registry.register(PIESLICE_APPLE);
        registry.register(PIESLICE_BERRY);

        // T4 (Advanced baking, multiple processes, temperature regulation)
        // Util : Metal Oven, Prep Block, Full tool set, Proper kitchen setup (Mid Iron
        // Age)
        registry.register(PIESLICE_SHEPARDS);
        registry.register(CAKE_SLICE);
        registry.register(CARROTCAKE_SLICE);

        // T5 (Advanced baking, multiple process, temperature regulation, many
        // ingreedients)
        registry.register(CHOCCAKE_SLICE);

        // T6 (Perfeted meals, extremely difficylt to create)
        registry.register(BFCAKE_SLICE);
        registry.register(ECLAIR_UNICED);
        registry.register(ECLAIR_EMPTY);

        // TODO: fix item texture to change with metadata
        registry.register(ECLAIR);

        // MISC
        registry.register(CAKE_TIN);
        registry.register(PIE_TRAY);
        registry.register(ICING);
        registry.register(CUSTARD);
        registry.register(COCA_POWDER);
        registry.register(CHOCOLATE);
        registry.register(CHOC_CHIPS);
        registry.register(BERRIES);
        registry.register(BERRIES_JUICY);
        registry.register(SWEETROLL_RAW);
        registry.register(ECLAIR_RAW);
        registry.register(CAKE_RAW);
        registry.register(CAKE_SIMPLE_RAW);
        registry.register(CAKE_CARROT_RAW);
        registry.register(CAKE_CHOC_RAW);
        registry.register(CAKE_BF_RAW);
        registry.register(CAKE_UNICED);
        registry.register(CAKE_SIMPLE_UNICED);
        registry.register(CAKE_CARROT_UNICED);
        registry.register(CAKE_CHOC_UNICED);
        registry.register(CAKE_BF_UNICED);
        registry.register(PIE_MEAT_UNCOOKED);
        registry.register(PIE_APPLE_UNCOOKED);
        registry.register(PIE_BERRY_UNCOOKED);
        registry.register(PIE_SHEPARD_UNCOOKED);
        registry.register(PIE_PUMPKIN_UNCOOKED);
        registry.register(PIE_MEAT_COOKED);
        registry.register(PIE_APPLE_COOKED);
        registry.register(PIE_BERRY_COOKED);
        registry.register(PIE_SHEPARD_COOKED);
        registry.register(PIE_PUMPKIN_COOKED);
        registry.register(SALT);
        registry.register(SUGAR_POT);
        registry.register(BOWL_WATER_SALT);
        registry.register(DOUGH);
        registry.register(PASTRY);
        registry.register(RAW_BREAD);
        registry.register(JUG_UNCOOKED);
        registry.register(JUG_EMPTY);
        registry.register(JUG_WATER);
        registry.register(JUG_MILK);
        registry.register(BURNT_FOOD);
        registry.register(BURNT_POT);
        registry.register(BURNT_PIE);

        // SPECIAL RECIPES
        registry.register(BURNT_CAKE);
    }

    public static void load() {
        CookRecipe.burnt_food = BURNT_FOOD;
    }

}
