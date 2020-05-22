package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.cooking.CookRecipe;
import minefantasy.mfr.item.ItemBurntFood;
import minefantasy.mfr.item.ItemComponentMFR;
import minefantasy.mfr.item.food.ItemFoodMF;
import minefantasy.mfr.item.food.ItemMultiFood;
import minefantasy.mfr.item.food.ItemUnfinishedFood;
import minefantasy.mfr.item.gadget.ItemJug;
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
    public static final Item WOLF_RAW = Utils.nullValue();
    public static final Item WOLF_COOKED = Utils.nullValue();
    public static final Item HORSE_RAW = Utils.nullValue();
    public static final Item HORSE_COOKED = Utils.nullValue();
    public static final Item GENERIC_MEAT_UNCOOKED = Utils.nullValue();
    public static final Item GENERIC_MEAT_COOKED = Utils.nullValue();
    public static final Item GENERIC_MEAT_STRIP_UNCOOKED = Utils.nullValue();
    public static final Item GENERIC_MEAT_STRIP_COOKED = Utils.nullValue();
    public static final Item GENERIC_MEAT_CHUNK_UNCOOKED = Utils.nullValue();
    public static final Item GENERIC_MEAT_CHUNK_COOKED = Utils.nullValue();
    public static final Item GENERIC_MEAT_MINCE_UNCOOKED = Utils.nullValue();
    public static final Item GENERIC_MEAT_MINCE_COOKED = Utils.nullValue();
    public static final Item FLOUR = Utils.nullValue();
    public static final Item BREADCRUMBS = Utils.nullValue();
    public static final Item GUTS = Utils.nullValue();
    public static final Item BREADROLL = Utils.nullValue();
    public static final Item BREAD_SLICE = Utils.nullValue();
    public static final Item CURDS = Utils.nullValue();
    public static final Item CHEESE_POT = Utils.nullValue();
    public static final Item CHEESE_SLICE = Utils.nullValue();

    // T1 (basic mixing)
    // Util: Roast, Prep Block (Stone-Bronze Age)
    public static final Item STEW = Utils.nullValue();
    public static final Item OATS = Utils.nullValue();

    // T2 (Basic baking, stone oven, processed mixing)
    // Util: Stone Oven, Prep Block (Bronze Age - Early Iron Age)
    public static final Item CHEESE_ROLL = Utils.nullValue();
    public static final Item JERKY = Utils.nullValue();
    public static final Item SAUSAGE_RAW = Utils.nullValue();
    public static final Item SAUSAGE_COOKED = Utils.nullValue();
    public static final Item SWEETROLL_UNICED = Utils.nullValue();
    public static final Item SWEETROLL = Utils.nullValue();
    public static final Item SANDWITCH_MEAT = Utils.nullValue();
    public static final Item SANDWITCH_BIG = Utils.nullValue();

    // T3 (Quality baking, metal oven)
    // Util: Metal Oven, Prep Block, Steel Tools (Mid Iron Age)
    public static final Item MEATPIE_SLICE = Utils.nullValue();
    public static final Item PIESLICE_APPLE = Utils.nullValue();
    public static final Item PIESLICE_BERRY = Utils.nullValue();

    // T4 (Advanced baking, multiple processes, temperature regulation)
    // Util : Metal Oven, Prep Block, Full tool set, Proper kitchen setup (Mid Iron
    // Age)
    public static final Item PIESLICE_SHEPARDS = Utils.nullValue();
    public static final Item CAKE_SLICE = Utils.nullValue();
    public static final Item CARROTCAKE_SLICE = Utils.nullValue();

    // T5 (Advanced baking, multiple process, temperature regulation, many
    // ingreedients)
    public static final Item CHOCCAKE_SLICE = Utils.nullValue();

    // T6 (Perfeted meals, extremely difficylt to create)
    public static final Item BFCAKE_SLICE = Utils.nullValue();
    public static final Item ECLAIR_UNICED = Utils.nullValue();
    public static final Item ECLAIR_EMPTY = Utils.nullValue();
    public static final Item ECLAIR = Utils.nullValue();

    // MISC
    public static final Item CAKE_TIN = Utils.nullValue();
    public static final Item PIE_TRAY = Utils.nullValue();
    public static final Item ICING = Utils.nullValue();
    public static final Item CUSTARD = Utils.nullValue();
    public static final Item COCA_POWDER = Utils.nullValue();
    public static final Item CHOCOLATE = Utils.nullValue();
    public static final Item CHOC_CHIPS = Utils.nullValue();
    public static final Item BERRIES = Utils.nullValue();
    public static final Item BERRIES_JUICY = Utils.nullValue();
    public static final Item SWEETROLL_RAW = Utils.nullValue();
    public static final Item ECLAIR_RAW = Utils.nullValue();
    public static final Item CAKE_RAW = Utils.nullValue();
    public static final Item CAKE_SIMPLE_RAW = Utils.nullValue();
    public static final Item CAKE_CARROT_RAW = Utils.nullValue();
    public static final Item CAKE_CHOC_RAW = Utils.nullValue();
    public static final Item CAKE_BF_RAW = Utils.nullValue();
    public static final Item CAKE_UNICED = Utils.nullValue();
    public static final Item CAKE_SIMPLE_UNICED = Utils.nullValue();
    public static final Item CAKE_CARROT_UNICED = Utils.nullValue();
    public static final Item CAKE_CHOC_UNICED = Utils.nullValue();
    public static final Item CAKE_BF_UNICED = Utils.nullValue();
    public static final Item PIE_MEAT_UNCOOKED = Utils.nullValue();
    public static final Item PIE_APPLE_UNCOOKED = Utils.nullValue();
    public static final Item PIE_BERRY_UNCOOKED = Utils.nullValue();
    public static final Item PIE_SHEPARD_UNCOOKED = Utils.nullValue();
    public static final Item PIE_PUMPKIN_UNCOOKED = Utils.nullValue();
    public static final Item PIE_MEAT_COOKED = Utils.nullValue();
    public static final Item PIE_APPLE_COOKED = Utils.nullValue();
    public static final Item PIE_BERRY_COOKED = Utils.nullValue();
    public static final Item PIE_SHEPARD_COOKED = Utils.nullValue();
    public static final Item PIE_PUMPKIN_COOKED = Utils.nullValue();
    public static final Item SALT = Utils.nullValue();
    public static final Item SUGAR_POT = Utils.nullValue();
    public static final Item BOWL_WATER_SALT = Utils.nullValue();
    public static final Item DOUGH = Utils.nullValue();
    public static final Item PASTRY = Utils.nullValue();
    public static final Item RAW_BREAD = Utils.nullValue();
    public static final Item JUG_UNCOOKED = Utils.nullValue();
    public static final Item JUG_EMPTY = Utils.nullValue();
    public static final Item JUG_WATER = Utils.nullValue();
    public static final Item JUG_MILK = Utils.nullValue();
    public static final Item BURNT_FOOD = Utils.nullValue();
    public static final Item BURNT_POT = Utils.nullValue();
    public static final Item BURNT_PIE = Utils.nullValue();

    // SPECIAL RECIPES
    public static final Item BURNT_CAKE = Utils.nullValue();

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        // MORSELS
        registry.register(new ItemFoodMF("wolf_raw", 2, 0.2F, true));
        registry.register(new ItemFoodMF("wolf_cooked", 6, 0.6F, true));
        registry.register(new ItemFoodMF("horse_raw", 4, 0.4F, true).setPotionEffect(new PotionEffect(MobEffects.HUNGER,500,0), 0.5F));
        registry.register(new ItemFoodMF("horse_cooked", 10, 1.0F, true));
        registry.register(new ItemFoodMF("generic_meat_uncooked", 2, 0.2F, true));
        registry.register(new ItemFoodMF("generic_meat_cooked", 5, 0.5F, true));
        registry.register(new ItemFoodMF("generic_meat_strip_uncooked", 2, 0.2F, true));
        registry.register(new ItemFoodMF("generic_meat_strip_cooked", 5, 0.5F, true));
        registry.register(new ItemFoodMF("generic_meat_chunk_uncooked", 2, 0.2F, true));
        registry.register(new ItemFoodMF("generic_meat_chunk_cooked", 5, 0.5F, true));
        registry.register(new ItemFoodMF("generic_meat_mince_uncooked", 2, 0.2F, true).setContainerItem(ComponentListMFR.CLAY_POT));
        registry.register(new ItemFoodMF("generic_meat_mince_cooked", 5, 0.5F, true).setContainerItem(ComponentListMFR.CLAY_POT));
        registry.register(new ItemComponentMFR("flour", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
        registry.register(new ItemComponentMFR("breadcrumbs", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
        registry.register(new ItemComponentMFR("guts", 0).setCreativeTab(CreativeTabMFR.tabFood));
        registry.register(new ItemFoodMF("breadroll", 5, 1.0F, false));
        registry.register(new ItemFoodMF("bread_slice", 2, 1.0F, false));
        registry.register(new ItemUnfinishedFood("curds"));
        registry.register(new ItemUnfinishedFood("cheese_pot").setContainerItem(ComponentListMFR.CLAY_POT));
        registry.register(new ItemFoodMF("cheese_slice", 4, 1.0F, false, 0).setFoodStats(1, 0.0F, 0.0F, 1.0F));

        // T1 (basic mixing)
        // Util: Roast, Prep Block (Stone-Bronze Age)
        registry.register(new ItemFoodMF("stew", 5, 1.0F, false, 0).setFoodStats(1, 0.0F, 0.0F, 1.0F).setReturnItem(Items.BOWL).setMaxStackSize(1));
        registry.register(new ItemFoodMF("oats", 5, 1.0F, false, 0).setFoodStats(1, 0.0F, 0.8F, 0.2F).setReturnItem(Items.BOWL).setMaxStackSize(1));

        // T2 (Basic baking, stone oven, processed mixing)
        // Util: Stone Oven, Prep Block (Bronze Age - Early Iron Age)
        registry.register(new ItemFoodMF("cheese_roll", 6, 1.0F, false, 0).setFoodStats(2, 0.0F, 0.4F, 0.6F).setMaxStackSize(1));
        registry.register(new ItemFoodMF("jerky", 6, 1.0F, true, 0).setFoodStats(2, 0.0F, 0.0F, 1.0F).setMaxStackSize(8));
        registry.register(new ItemFoodMF("saussage_raw", 4, 1.0F, true, 0).setFoodStats(2, 0.0F, 0.1F, 0.6F).setMaxStackSize(16));
        registry.register(new ItemFoodMF("saussage_cooked", 8, 1.0F, true, 0).setFoodStats(2, 0.0F, 0.2F, 0.8F).setMaxStackSize(16));
        registry.register(new ItemFoodMF("sweetroll_uniced", 5, 1.0F, false, 0).setFoodStats(2, 0.5F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(64));

        // TODO: fix item texture to change with metadata
        registry.register(new ItemMultiFood("sweetroll", 2, 3, 1.0F, false, 0).setFoodStats(2, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible());
        registry.register(new ItemMultiFood("sandwitch_meat", 2, 6, 1.0F, false, 0).setFoodStats(2, 0.0F, 0.5F, 0.5F).setUnlocalizedName("sandwitch"));
        registry.register(new ItemMultiFood("sandwitch_big", 4, 6, 1.0F, false, 1).setFoodStats(2, 0.0F, 0.5F, 0.5F));

        // T3 (Quality baking, metal oven)
        // Util: Metal Oven, Prep Block, Steel Tools (Mid Iron Age)
        registry.register(new ItemFoodMF("meatpie_slice", 8, 1.0F, false, 0).setFoodStats(3, 0.0F, 0.2F, 0.8F).setMaxStackSize(1));
        registry.register(new ItemFoodMF("pieslice_apple", 5, 1.0F, false, 0).setFoodStats(3, 0.8F, 0.2F, 0.0F).setEatTime(16).setAlwaysEdible().setAlwaysEdible());
        registry.register(new ItemFoodMF("pieslice_berry", 5, 1.0F, false, 0).setFoodStats(3, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setAlwaysEdible());

        // T4 (Advanced baking, multiple processes, temperature regulation)
        // Util : Metal Oven, Prep Block, Full tool set, Proper kitchen setup (Mid Iron
        // Age)
        registry.register(new ItemFoodMF("pieslice_shepards", 10, 1.0F, false, 1).setFoodStats(4, 0.0F, 0.5F, 0.5F));
        registry.register(new ItemFoodMF("cake_slice", 3, 0.8F, false, 0).setFoodStats(3, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1));
        registry.register(new ItemFoodMF("carrotcake_slice", 4, 0.8F, false, 0).setFoodStats(4, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1));

        // T5 (Advanced baking, multiple process, temperature regulation, many
        // ingreedients)
        registry.register(new ItemFoodMF("choccake_slice", 4, 0.8F, false, 0).setFoodStats(4, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1));

        // T6 (Perfeted meals, extremely difficylt to create)
        registry.register(new ItemFoodMF("bfcake_slice", 6, 1.0F, false, 1).setFoodStats(5, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1));
        registry.register(new ItemFoodMF("eclair_uniced", 5, 1.0F, false, 0).setFoodStats(5, 0.3F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(64));
        registry.register(new ItemFoodMF("eclair_empty", 5, 1.0F, false, 0).setFoodStats(5, 0.5F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(64));

        // TODO: fix item texture to change with metadata
        registry.register(new ItemMultiFood("eclair", 4, 4, 1.0F, false, 1).setFoodStats(5, 1.0F, 0.0F, 0.0F).setEatTime(16).setAlwaysEdible().setMaxStackSize(1));

        // MISC
        registry.register(new ItemComponentMFR("cake_tin", 0).setCreativeTab(CreativeTabMFR.tabFood));
        registry.register(new ItemComponentMFR("pie_tray", 0).setStoragePlacement("bigplate", "tray").setCreativeTab(CreativeTabMFR.tabFood));
        registry.register(new ItemComponentMFR("icing", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
        registry.register(new ItemComponentMFR("custard", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
        registry.register(new ItemComponentMFR("coca_powder", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
        registry.register(new ItemComponentMFR("chocolate", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
        registry.register(new ItemComponentMFR("choc_chips", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
        registry.register(new ItemFoodMF("berries", 2, 2.0F, false).setEatTime(10).setStaminaRestore(10F).setAlwaysEdible());
        registry.register(new ItemFoodMF("berries_juicy", 3, 5.0F, false).setEatTime(10).setStaminaRestore(25F).setRarity(1).setAlwaysEdible());
        registry.register(new ItemUnfinishedFood("sweetroll_raw").setMaxStackSize(64));
        registry.register(new ItemUnfinishedFood("eclair_raw").setMaxStackSize(64));
        registry.register(new ItemUnfinishedFood("cake_raw"));
        registry.register(new ItemUnfinishedFood("cake_simple_raw"));
        registry.register(new ItemUnfinishedFood("cake_carrot_raw"));
        registry.register(new ItemUnfinishedFood("cake_choc_raw"));
        registry.register(new ItemUnfinishedFood("cake_bf_raw"));
        registry.register(new ItemUnfinishedFood("cake_uniced").setContainerItem(CAKE_TIN));
        registry.register(new ItemUnfinishedFood("cake_simple_uniced").setContainerItem(CAKE_TIN));
        registry.register(new ItemUnfinishedFood("cake_carrot_uniced").setContainerItem(CAKE_TIN));
        registry.register(new ItemUnfinishedFood("cake_choc_uniced").setContainerItem(CAKE_TIN));
        registry.register(new ItemUnfinishedFood("cake_bf_uniced").setContainerItem(CAKE_TIN));
        registry.register(new ItemUnfinishedFood("pie_meat_uncooked"));
        registry.register(new ItemUnfinishedFood("pie_apple_uncooked"));
        registry.register(new ItemUnfinishedFood("pie_berry_uncooked"));
        registry.register(new ItemUnfinishedFood("pie_shepard_uncooked"));
        registry.register(new ItemUnfinishedFood("pie_pumpkin_uncooked"));
        registry.register(new ItemUnfinishedFood("pie_meat_cooked").setContainerItem(PIE_TRAY));
        registry.register(new ItemUnfinishedFood("pie_apple_cooked").setContainerItem(PIE_TRAY));
        registry.register(new ItemUnfinishedFood("pie_berry_cooked").setContainerItem(PIE_TRAY));
        registry.register(new ItemUnfinishedFood("pie_shepard_cooked").setContainerItem(PIE_TRAY));
        registry.register(new ItemUnfinishedFood("pie_pumpkin_cooked").setContainerItem(PIE_TRAY));
        registry.register(new ItemComponentMFR("salt", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
        registry.register(new ItemComponentMFR("sugar_pot", 0).setCreativeTab(CreativeTabMFR.tabFood).setContainerItem(ComponentListMFR.CLAY_POT));
        registry.register(new ItemComponentMFR("bowl_water_salt", 0).setCreativeTab(CreativeTabMFR.tabFood));
        registry.register(new ItemUnfinishedFood("dough").setMaxStackSize(64));
        registry.register(new ItemUnfinishedFood("pastry").setMaxStackSize(64));
        registry.register(new ItemUnfinishedFood("raw_bread").setMaxStackSize(64));
        registry.register(new ItemJug("uncooked"));
        registry.register(new ItemJug("empty").setStoragePlacement("jug", "jug"));
        registry.register(new ItemJug("water").setStoragePlacement("jug", "jugwater").setContainerItem(JUG_EMPTY));
        registry.register(new ItemJug("milk").setStoragePlacement("jug", "jugmilk").setContainerItem(JUG_EMPTY));
        registry.register(new ItemBurntFood("burnt_food"));
        registry.register(new ItemBurntFood("burnt_pot").setContainerItem(ComponentListMFR.CLAY_POT));
        registry.register(new ItemBurntFood("burnt_pie").setContainerItem(PIE_TRAY));

        // SPECIAL RECIPES
        registry.register(new ItemBurntFood("burnt_cake").setContainerItem(CAKE_TIN));
    }

    public static void load() {
        CookRecipe.burnt_food = BURNT_FOOD;
    }

}
