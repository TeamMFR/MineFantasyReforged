package minefantasy.mfr.recipe;

import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.recipe.refine.PaintOilRecipe;
import minefantasy.mfr.recipe.refine.QuernRecipes;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CustomToolListMFR;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.init.LeatherArmourListMFR;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.init.ToolListMFR;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;

public class CarpenterRecipes {
    public static final SoundEvent basic = SoundEvents.BLOCK_WOOD_STEP;
	public static final SoundEvent spanner = MineFantasySounds.TWIST_BOLT;

    public static void init() {

        assembleWoodBasic();
        CustomWoodRecipes.init();
        addDusts();
        addWoodworks();
        addStonemason();
        addMisc();
        addEngineering();
        if (ConfigHardcore.HCCallowRocks) {
            addPrimitive();
        } else {
            addNonPrimitiveStone();
        }

        Salvage.addSalvage(ToolListMFR.DRY_ROCKS, Blocks.COBBLESTONE);

        ItemStack scrapWood = ComponentListMFR.TIMBER.construct("ScrapWood");
        Salvage.addSalvage(ToolListMFR.TRAINING_SWORD, new ItemStack(Blocks.PLANKS, 5),
                new ItemStack(ComponentListMFR.NAIL, 2), scrapWood);
        Salvage.addSalvage(ToolListMFR.TRAINING_WARAXE, new ItemStack(Blocks.PLANKS, 4), ComponentListMFR.NAIL, scrapWood,
                scrapWood);
        Salvage.addSalvage(ToolListMFR.TRAINING_MACE, new ItemStack(Blocks.PLANKS, 4), ComponentListMFR.NAIL, scrapWood,
                scrapWood);
        Salvage.addSalvage(ToolListMFR.TRAINING_WARAXE, Blocks.PLANKS, new ItemStack(ComponentListMFR.NAIL, 2), scrapWood,
                scrapWood, scrapWood);

        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 0),
                new ItemStack(ComponentListMFR.THREAD, 2), new ItemStack(ComponentListMFR.LEATHER_STRIP, 2),
                Items.LEATHER);
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 1),
                new ItemStack(ComponentListMFR.THREAD, 4), new ItemStack(ComponentListMFR.LEATHER_STRIP, 2),
                new ItemStack(Items.LEATHER, 4));
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 2),
                new ItemStack(ComponentListMFR.THREAD, 4), new ItemStack(ComponentListMFR.LEATHER_STRIP, 2),
                new ItemStack(Items.LEATHER, 3));
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 3),
                new ItemStack(ComponentListMFR.THREAD, 4), new ItemStack(ComponentListMFR.LEATHER_STRIP, 2));

        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 0),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 0), new ItemStack(ComponentListMFR.THREAD, 3),
                new ItemStack(Items.LEATHER, 2));
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 1),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 1), new ItemStack(ComponentListMFR.THREAD, 3),
                new ItemStack(Items.LEATHER, 2));
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 2),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 2), new ItemStack(ComponentListMFR.THREAD, 3),
                new ItemStack(Items.LEATHER, 2));
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 3),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 3), new ItemStack(ComponentListMFR.THREAD, 3),
                new ItemStack(Items.LEATHER, 2));

        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 0),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 0), new ItemStack(ComponentListMFR.THREAD, 3),
                Blocks.WOOL);
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 1),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 1), new ItemStack(ComponentListMFR.THREAD, 3),
                Blocks.WOOL);
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 2),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 2), new ItemStack(ComponentListMFR.THREAD, 3),
                Blocks.WOOL);
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 3),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 3), new ItemStack(ComponentListMFR.THREAD, 3),
                Blocks.WOOL);

        ItemStack bronzePlate = ComponentListMFR.PLATE.createComm("bronze");

        Salvage.addSalvage(MineFantasyBlocks.REPAIR_BASIC, new ItemStack(ComponentListMFR.THREAD, 3), ComponentListMFR.NAIL,
                Items.FLINT, Items.LEATHER, new ItemStack(ComponentListMFR.LEATHER_STRIP, 2));
        Salvage.addSalvage(MineFantasyBlocks.REPAIR_ADVANCED, MineFantasyBlocks.REPAIR_BASIC, bronzePlate,
                new ItemStack(Items.SLIME_BALL, 3), new ItemStack(Items.STRING, 3));
        Salvage.addSalvage(MineFantasyBlocks.REPAIR_ORNATE, MineFantasyBlocks.REPAIR_ADVANCED, new ItemStack(Items.GOLD_INGOT, 4),
                Items.DIAMOND, new ItemStack(Items.DYE, 3, 4));
    }

    private static void assembleWoodBasic() {
        Salvage.addSalvage(MineFantasyBlocks.CARPENTER, ComponentListMFR.TIMBER.construct("ScrapWood", 4), Blocks.CRAFTING_TABLE);

        Salvage.addSalvage(MineFantasyBlocks.FRAMED_GLASS, ComponentListMFR.TIMBER.construct("ScrapWood", 2), Blocks.GLASS);
        Salvage.addSalvage(MineFantasyBlocks.WINDOW, ComponentListMFR.TIMBER.construct("ScrapWood", 4), Blocks.GLASS);
        Salvage.addSalvage(MineFantasyBlocks.CLAY_WALL, ComponentListMFR.NAIL, ComponentListMFR.TIMBER.construct("ScrapWood"),
                Items.CLAY_BALL);
        Salvage.addSalvage(MineFantasyBlocks.TANNER, ComponentListMFR.TIMBER.construct("ScrapWood", 8));
        Salvage.addSalvage(MineFantasyBlocks.RESEARCH, MineFantasyBlocks.CARPENTER);
        Salvage.addSalvage(MineFantasyBlocks.SALVAGE_BASIC, Items.FLINT, new ItemStack(Blocks.STONE, 2),
                ComponentListMFR.TIMBER.construct("ScrapWood", 2), Blocks.CRAFTING_TABLE);
    }

    private static void addDusts() {
        QuernRecipes.addRecipe(new ItemStack(Items.DYE, 1, 3), new ItemStack(FoodListMFR
                .COCA_POWDER), 0, true);// ItemDye
        QuernRecipes.addRecipe(Items.WHEAT, new ItemStack(FoodListMFR
                .FLOUR), 0, true);
        QuernRecipes.addRecipe(Items.REEDS, new ItemStack(FoodListMFR
                .SUGAR_POT), 0, true);
        QuernRecipes.addRecipe(FoodListMFR
                .BREADROLL, new ItemStack(FoodListMFR
                .BREADCRUMBS), 0, true);

        QuernRecipes.addRecipe(FoodListMFR
                        .GENERIC_MEAT_UNCOOKED, new ItemStack(FoodListMFR
                        .GENERIC_MEAT_MINCE_UNCOOKED),
                0, true);
        QuernRecipes.addRecipe(FoodListMFR
                        .GENERIC_MEAT_STRIP_UNCOOKED,
                new ItemStack(FoodListMFR
                        .GENERIC_MEAT_MINCE_UNCOOKED), 0, true);
        QuernRecipes.addRecipe(FoodListMFR
                        .GENERIC_MEAT_CHUNK_UNCOOKED,
                new ItemStack(FoodListMFR
                        .GENERIC_MEAT_MINCE_UNCOOKED), 0, true);
        QuernRecipes.addRecipe(FoodListMFR
                .GENERIC_MEAT_COOKED, new ItemStack(FoodListMFR
                .GENERIC_MEAT_MINCE_COOKED), 0, true);
        QuernRecipes.addRecipe(FoodListMFR
                        .GENERIC_MEAT_STRIP_COOKED,
                new ItemStack(FoodListMFR
                        .GENERIC_MEAT_MINCE_COOKED), 0, true);
        QuernRecipes.addRecipe(FoodListMFR
                        .GENERIC_MEAT_CHUNK_COOKED,
                new ItemStack(FoodListMFR
                        .GENERIC_MEAT_MINCE_COOKED), 0, true);

        QuernRecipes.addRecipe(Items.COAL, new ItemStack(ComponentListMFR.COAL_DUST), 0, true);
        QuernRecipes.addRecipe(new ItemStack(Items.COAL, 1, 1), new ItemStack(ComponentListMFR.COAL_DUST), 0, true);
        QuernRecipes.addRecipe(ComponentListMFR.KAOLINITE, new ItemStack(ComponentListMFR.KAOLINITE_DUST), 0, true);
        QuernRecipes.addRecipe(Items.FLINT, new ItemStack(ComponentListMFR.SHRAPNEL), 0, true);

        QuernRecipes.addRecipe(ComponentListMFR.FLUX, new ItemStack(ComponentListMFR.FLUX_POT), 0, true);

        Salvage.addSalvage(ComponentListMFR.MAGMA_CREAM_REFINED, ComponentListMFR.DRAGON_HEART, Items.BLAZE_POWDER,
                Items.MAGMA_CREAM, ComponentListMFR.CLAY_POT);
    }

    private static void addWoodworks() {
        Salvage.addSalvage(MineFantasyBlocks.NAILED_PLANKS, ComponentListMFR.NAIL,
                ComponentListMFR.TIMBER.construct("ScrapWood", 4));
        Salvage.addSalvage(MineFantasyBlocks.REFINED_PLANKS, ComponentListMFR.NAIL,
                ComponentListMFR.TIMBER.construct("RefinedWood", 4));
        Salvage.addSalvage(MineFantasyBlocks.NAILED_PLANKS_STAIR, ComponentListMFR.NAIL,
                ComponentListMFR.TIMBER.construct("ScrapWood", 3));
        Salvage.addSalvage(MineFantasyBlocks.REFINED_PLANKS_STAIR, ComponentListMFR.NAIL,
                ComponentListMFR.TIMBER.construct("RefinedWood", 3));

        Salvage.addSalvage(MineFantasyBlocks.BELLOWS, new ItemStack(ComponentListMFR.NAIL, 3),
                ComponentListMFR.TIMBER.construct("RefinedWood", 5), new ItemStack(Items.LEATHER, 2));



        Salvage.addSalvage(MineFantasyBlocks.TANNER_REFINED, ComponentListMFR.TIMBER.construct("RefinedWood", 8),
                new ItemStack(ComponentListMFR.NAIL, 3));

        PaintOilRecipe.addRecipe(MineFantasyBlocks.NAILED_PLANKS, MineFantasyBlocks.REFINED_PLANKS);
        PaintOilRecipe.addRecipe(MineFantasyBlocks.NAILED_PLANKS_STAIR, MineFantasyBlocks.REFINED_PLANKS_STAIR);

    }

    private static void addStonemason() {
        Salvage.addSalvage(MineFantasyBlocks.CRUCIBLE_STONE, new ItemStack(Blocks.STONE, 8));
        Salvage.addSalvage(MineFantasyBlocks.CRUCIBLE_FIRECLAY, new ItemStack(ComponentListMFR.FIRECLAY, 8), MineFantasyBlocks.CRUCIBLE_STONE);

        Salvage.addSalvage(MineFantasyBlocks.FORGE, new ItemStack(Blocks.STONE, 4), Items.COAL);
        Salvage.addSalvage(MineFantasyBlocks.ANVIL_STONE, new ItemStack(Blocks.STONE, 6));

        Salvage.addSalvage(MineFantasyBlocks.CHIMNEY_STONE, Blocks.STONE);
        Salvage.addSalvage(MineFantasyBlocks.CHIMNEY_STONE_WIDE, MineFantasyBlocks.CHIMNEY_STONE, Blocks.STONE);
        Salvage.addSalvage(MineFantasyBlocks.CHIMNEY_STONE_EXTRACTOR, MineFantasyBlocks.CHIMNEY_STONE_WIDE);
        Salvage.addSalvage(MineFantasyBlocks.QUERN, new ItemStack(Items.FLINT, 2), new ItemStack(Blocks.STONE, 4));
    }

    private static void addMisc() {
        Salvage.addSalvage(ComponentListMFR.BOMB_CASING_UNCOOKED, new ItemStack(Items.CLAY_BALL, 2));
        Salvage.addSalvage(ComponentListMFR.MINE_CASING_UNCOOKED, new ItemStack(Items.CLAY_BALL, 3),
                Blocks.STONE_PRESSURE_PLATE);

        Salvage.addSalvage(ComponentListMFR.BOMB_CASING_CRYSTAL, Items.GLASS_BOTTLE, ComponentListMFR.DIAMOND_SHARDS,
                new ItemStack(Items.REDSTONE, 2));
        Salvage.addSalvage(ComponentListMFR.MINE_CASING_CRYSTAL, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
                Items.GLASS_BOTTLE, ComponentListMFR.DIAMOND_SHARDS, new ItemStack(Items.REDSTONE, 2));

        Salvage.addSalvage(ComponentListMFR.BOMB_FUSE_LONG, new ItemStack(ComponentListMFR.BOMB_FUSE, 2), Items.REDSTONE);

        Salvage.addSalvage(MineFantasyBlocks.THATCH_STAIR, new ItemStack(Blocks.TALLGRASS, 3, 1));
        Salvage.addSalvage(MineFantasyBlocks.THATCH, new ItemStack(Blocks.TALLGRASS, 4, 1));

        Salvage.addSalvage(LeatherArmourListMFR.LEATHER_APRON, new ItemStack(Items.LEATHER, 3), Items.COAL);


        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 0),
                new ItemStack(ComponentListMFR.HIDE_SMALL, 2), Blocks.WOOL);
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 1), ComponentListMFR.HIDE_LARGE, Blocks.WOOL);
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 2), ComponentListMFR.HIDE_MEDIUM,
                Blocks.WOOL);
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 3), ComponentListMFR.HIDE_SMALL, Blocks.WOOL);
    }

    private static void addCrossbows() {
        Salvage.addSalvage(ComponentListMFR.CROSSBOW_ARMS_LIGHT, new ItemStack(ComponentListMFR.NAIL, 2), Items.STRING,
                ComponentListMFR.TIMBER.construct("RefinedWood", 4));
        Salvage.addSalvage(ComponentListMFR.CROSSBOW_ARMS_BASIC, new ItemStack(ComponentListMFR.NAIL, 3),
                ComponentListMFR.CROSSBOW_ARMS_LIGHT, ComponentListMFR.TIMBER.construct("RefinedWood", 2));
        Salvage.addSalvage(ComponentListMFR.CROSSBOW_ARMS_HEAVY, new ItemStack(ComponentListMFR.NAIL, 3),
                ComponentListMFR.CROSSBOW_ARMS_BASIC, ComponentListMFR.TIMBER.construct("RefinedWood", 2));
        Salvage.addSalvage(ComponentListMFR.CROSSBOW_ARMS_ADVANCED, ComponentListMFR.TUNGSTEN_GEARS,
                new ItemStack(ComponentListMFR.NAIL, 2), ComponentListMFR.CROSSBOW_ARMS_BASIC,
                new ItemStack(ComponentListMFR.STEEL_TUBE, 3));

        Salvage.addSalvage(ComponentListMFR.CROSSBOW_SCOPE, ComponentListMFR.TUNGSTEN_GEARS, ToolListMFR.SPYGLASS,
                new ItemStack(ComponentListMFR.BOLT, 2), ComponentListMFR.TIMBER.construct("RefinedWood"));
        Salvage.addSalvage(ComponentListMFR.CROSSBOW_AMMO, ComponentListMFR.TUNGSTEN_GEARS,
                new ItemStack(ComponentListMFR.NAIL, 3), ComponentListMFR.TIMBER.construct("RefinedWood", 7));
        Salvage.addSalvage(ComponentListMFR.CROSSBOW_HANDLE_WOOD, new ItemStack(ComponentListMFR.NAIL, 2),
                ComponentListMFR.TIMBER.construct("RefinedWood", 3));
        Salvage.addSalvage(ComponentListMFR.CROSSBOW_STOCK_WOOD, new ItemStack(ComponentListMFR.NAIL, 3),
                ComponentListMFR.TIMBER.construct("RefinedWood", 7));
        Salvage.addSalvage(ComponentListMFR.CROSSBOW_STOCK_IRON, new ItemStack(ComponentListMFR.TUNGSTEN_GEARS, 2),
                Blocks.OBSIDIAN, new ItemStack(ComponentListMFR.BOLT, 4), new ItemStack(ComponentListMFR.IRON_STRUT, 3),
                ComponentListMFR.CROSSBOW_STOCK_WOOD);
    }

    private static void addEngineering() {
        addCrossbows();

        ItemStack blackPlate = ComponentListMFR.PLATE.createComm("blackSteel");
        ItemStack steelPlate = ComponentListMFR.PLATE.createComm("steel");

        Salvage.addSalvage(ComponentListMFR.COGWORK_SHAFT, new ItemStack(ComponentListMFR.IRON_STRUT, 2),
                new ItemStack(ComponentListMFR.BOLT, 4), ComponentListMFR.IRON_FRAME, Blocks.PISTON,
                ComponentListMFR.TUNGSTEN_GEARS);

        Salvage.addSalvage(MineFantasyBlocks.CRUCIBLE_AUTO, new ItemStack(ComponentListMFR.BOLT, 3),
                ComponentListMFR.TUNGSTEN_GEARS, MineFantasyBlocks.CRUCIBLE_FIRECLAY, steelPlate, steelPlate, steelPlate,
                steelPlate);
        Salvage.addSalvage(MineFantasyBlocks.BOMB_BENCH, new ItemStack(ComponentListMFR.BOLT, 4), ComponentListMFR.IRON_FRAME,
                MineFantasyBlocks.CARPENTER);
        Salvage.addSalvage(MineFantasyBlocks.CROSSBOW_BENCH, new ItemStack(ComponentListMFR.NAIL, 2),
                ComponentListMFR.TIMBER.construct("ScrapWood", 2), Items.STRING, MineFantasyBlocks.CARPENTER);
        Salvage.addSalvage(MineFantasyBlocks.BOMB_PRESS, new ItemStack(ComponentListMFR.IRON_STRUT, 2),
                new ItemStack(ComponentListMFR.BOLT, 2), new ItemStack(ComponentListMFR.BRONZE_GEARS, 2), Blocks.LEVER,
                ComponentListMFR.IRON_FRAME);
        Salvage.addSalvage(MineFantasyBlocks.TANNER_METAL, new ItemStack(ComponentListMFR.IRON_STRUT, 4),
                new ItemStack(ComponentListMFR.BOLT, 2), new ItemStack(ComponentListMFR.BRONZE_GEARS, 3),
                CustomToolListMFR.STANDARD_NEEDLE, Blocks.LEVER, ComponentListMFR.IRON_FRAME);
        Salvage.addSalvage(MineFantasyBlocks.FORGE_METAL, new ItemStack(ComponentListMFR.BOLT, 4), blackPlate, blackPlate,
                blackPlate, blackPlate, new ItemStack(ComponentListMFR.IRON_FRAME, 2),
                new ItemStack(Blocks.REDSTONE_BLOCK, 2));
        Salvage.addSalvage(ToolListMFR.SPYGLASS, new ItemStack(ComponentListMFR.BOLT, 2), new ItemStack(Blocks.GLASS, 2),
                ComponentListMFR.STEEL_TUBE, ComponentListMFR.BRONZE_GEARS);
        Salvage.addSalvage(ToolListMFR.SYRINGE_EMPTY, Items.GLASS_BOTTLE, CustomToolListMFR.STANDARD_NEEDLE,
                ComponentListMFR.STEEL_TUBE);
        Salvage.addSalvage(ToolListMFR.PARACHUTE, new ItemStack(ComponentListMFR.THREAD, 3),
                new ItemStack(Blocks.WOOL, 3), new ItemStack(ComponentListMFR.LEATHER_STRIP, 4), Items.LEATHER);
    }

    private static void addNonPrimitiveStone() {
        Salvage.addSalvage(ToolListMFR.STONE_KNIFE, new ItemStack(Blocks.COBBLESTONE, 2),
                ComponentListMFR.TIMBER.construct("ScrapWood"));
        Salvage.addSalvage(ToolListMFR.STONE_HAMMER, Blocks.COBBLESTONE, ComponentListMFR.TIMBER.construct("ScrapWood"));
        Salvage.addSalvage(ToolListMFR.STONE_TONGS, new ItemStack(Blocks.COBBLESTONE, 2),
                ComponentListMFR.TIMBER.construct("ScrapWood"));
        Salvage.addSalvage(ToolListMFR.BONE_NEEDLE, Items.BONE);
    }

    private static void addPrimitive() {

        Salvage.addSalvage(ToolListMFR.STONE_PICK, new ItemStack(ComponentListMFR.SHARP_ROCK, 2),
                new ItemStack(Items.STICK, 2), ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_AXE, new ItemStack(ComponentListMFR.SHARP_ROCK, 2),
                new ItemStack(Items.STICK, 2), ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_SPADE, ComponentListMFR.SHARP_ROCK, new ItemStack(Items.STICK, 2),
                ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_HOE, ComponentListMFR.SHARP_ROCK, new ItemStack(Items.STICK, 2),
                ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_SWORD, Items.STICK, new ItemStack(ComponentListMFR.SHARP_ROCK, 2),
                ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_WARAXE, new ItemStack(ComponentListMFR.SHARP_ROCK, 2),
                new ItemStack(Items.STICK, 2), new ItemStack(ComponentListMFR.VINE, 2));
        Salvage.addSalvage(ToolListMFR.STONE_MACE, new ItemStack(ComponentListMFR.SHARP_ROCK, 2),
                new ItemStack(Items.STICK, 2), ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_SPEAR, ComponentListMFR.SHARP_ROCK, new ItemStack(Items.STICK, 2),
                ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_KNIFE, ComponentListMFR.SHARP_ROCK, Items.STICK, ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_HAMMER, ComponentListMFR.SHARP_ROCK, Items.STICK, ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_TONGS, ComponentListMFR.SHARP_ROCK, Items.STICK, ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.BONE_NEEDLE, Items.BONE);
    }

    static void initTierWood() {
		Item plank = ComponentListMFR.TIMBER;

        // TODO: fix salvage
//        Salvage.addSalvage(CustomToolListMFR.STANDARD_SPOON, plank, Items.STICK);
//        KnowledgeListMFR.malletR = MineFantasyRebornAPI.addCarpenterToolRecipe(Skill.ARTISANRY, CustomToolListMFR.STANDARD_MALLET, "",
//                basic, "hands", -1, 1 + (int) (2 * time), new Object[]{"WW", " S", 'W', plank, 'S', Items.STICK});

        Salvage.addSalvage(CustomToolListMFR.STANDARD_MALLET, plank, plank, Items.STICK);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_SPOON, plank, Items.STICK);
    }

}
