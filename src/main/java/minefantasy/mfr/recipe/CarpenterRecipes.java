package minefantasy.mfr.recipe;

import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.LeatherArmourListMFR;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.recipe.refine.PaintOilRecipe;
import minefantasy.mfr.recipe.refine.QuernRecipes;
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

		Salvage.addSalvage(MineFantasyItems.DRY_ROCKS, Blocks.COBBLESTONE);

		ItemStack scrapWood = MineFantasyItems.TIMBER.construct("ScrapWood");
		Salvage.addSalvage(MineFantasyItems.TRAINING_SWORD, new ItemStack(Blocks.PLANKS, 5),
				new ItemStack(MineFantasyItems.NAIL, 2), scrapWood);
		Salvage.addSalvage(MineFantasyItems.TRAINING_WARAXE, new ItemStack(Blocks.PLANKS, 4), MineFantasyItems.NAIL, scrapWood,
				scrapWood);
		Salvage.addSalvage(MineFantasyItems.TRAINING_MACE, new ItemStack(Blocks.PLANKS, 4), MineFantasyItems.NAIL, scrapWood,
				scrapWood);
		Salvage.addSalvage(MineFantasyItems.TRAINING_WARAXE, Blocks.PLANKS, new ItemStack(MineFantasyItems.NAIL, 2), scrapWood,
				scrapWood, scrapWood);

		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 0),
				new ItemStack(MineFantasyItems.THREAD, 2), new ItemStack(MineFantasyItems.LEATHER_STRIP, 2),
				Items.LEATHER);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 1),
				new ItemStack(MineFantasyItems.THREAD, 4), new ItemStack(MineFantasyItems.LEATHER_STRIP, 2),
				new ItemStack(Items.LEATHER, 4));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 2),
				new ItemStack(MineFantasyItems.THREAD, 4), new ItemStack(MineFantasyItems.LEATHER_STRIP, 2),
				new ItemStack(Items.LEATHER, 3));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 3),
				new ItemStack(MineFantasyItems.THREAD, 4), new ItemStack(MineFantasyItems.LEATHER_STRIP, 2));

		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 0),
				LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 0), new ItemStack(MineFantasyItems.THREAD, 3),
				new ItemStack(Items.LEATHER, 2));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 1),
				LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 1), new ItemStack(MineFantasyItems.THREAD, 3),
				new ItemStack(Items.LEATHER, 2));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 2),
				LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 2), new ItemStack(MineFantasyItems.THREAD, 3),
				new ItemStack(Items.LEATHER, 2));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 3),
				LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 3), new ItemStack(MineFantasyItems.THREAD, 3),
				new ItemStack(Items.LEATHER, 2));

		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 0),
				LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 0), new ItemStack(MineFantasyItems.THREAD, 3),
				Blocks.WOOL);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 1),
				LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 1), new ItemStack(MineFantasyItems.THREAD, 3),
				Blocks.WOOL);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 2),
				LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 2), new ItemStack(MineFantasyItems.THREAD, 3),
				Blocks.WOOL);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 3),
				LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 3), new ItemStack(MineFantasyItems.THREAD, 3),
				Blocks.WOOL);

		ItemStack bronzePlate = MineFantasyItems.PLATE.createComm("bronze");

		Salvage.addSalvage(MineFantasyBlocks.REPAIR_BASIC, new ItemStack(MineFantasyItems.THREAD, 3), MineFantasyItems.NAIL,
				Items.FLINT, Items.LEATHER, new ItemStack(MineFantasyItems.LEATHER_STRIP, 2));
		Salvage.addSalvage(MineFantasyBlocks.REPAIR_ADVANCED, MineFantasyBlocks.REPAIR_BASIC, bronzePlate,
				new ItemStack(Items.SLIME_BALL, 3), new ItemStack(Items.STRING, 3));
		Salvage.addSalvage(MineFantasyBlocks.REPAIR_ORNATE, MineFantasyBlocks.REPAIR_ADVANCED, new ItemStack(Items.GOLD_INGOT, 4),
				Items.DIAMOND, new ItemStack(Items.DYE, 3, 4));
	}

	private static void assembleWoodBasic() {
		Salvage.addSalvage(MineFantasyBlocks.CARPENTER, MineFantasyItems.TIMBER.construct("ScrapWood", 4), Blocks.CRAFTING_TABLE);

		Salvage.addSalvage(MineFantasyBlocks.FRAMED_GLASS, MineFantasyItems.TIMBER.construct("ScrapWood", 2), Blocks.GLASS);
		Salvage.addSalvage(MineFantasyBlocks.WINDOW, MineFantasyItems.TIMBER.construct("ScrapWood", 4), Blocks.GLASS);
		Salvage.addSalvage(MineFantasyBlocks.CLAY_WALL, MineFantasyItems.NAIL, MineFantasyItems.TIMBER.construct("ScrapWood"),
				Items.CLAY_BALL);
		Salvage.addSalvage(MineFantasyBlocks.TANNER, MineFantasyItems.TIMBER.construct("ScrapWood", 8));
		Salvage.addSalvage(MineFantasyBlocks.RESEARCH, MineFantasyBlocks.CARPENTER);
		Salvage.addSalvage(MineFantasyBlocks.SALVAGE_BASIC, Items.FLINT, new ItemStack(Blocks.STONE, 2),
				MineFantasyItems.TIMBER.construct("ScrapWood", 2), Blocks.CRAFTING_TABLE);
	}

	private static void addDusts() {
		QuernRecipes.addRecipe(new ItemStack(Items.DYE, 1, 3), new ItemStack(MineFantasyItems
				.COCA_POWDER), 0, true);// ItemDye
		QuernRecipes.addRecipe(Items.WHEAT, new ItemStack(MineFantasyItems
				.FLOUR), 0, true);
		QuernRecipes.addRecipe(Items.REEDS, new ItemStack(MineFantasyItems
				.SUGAR_POT), 0, true);
		QuernRecipes.addRecipe(MineFantasyItems
				.BREADROLL, new ItemStack(MineFantasyItems
				.BREADCRUMBS), 0, true);

		QuernRecipes.addRecipe(MineFantasyItems
						.GENERIC_MEAT_UNCOOKED, new ItemStack(MineFantasyItems
						.GENERIC_MEAT_MINCE_UNCOOKED),
				0, true);
		QuernRecipes.addRecipe(MineFantasyItems
						.GENERIC_MEAT_STRIP_UNCOOKED,
				new ItemStack(MineFantasyItems
						.GENERIC_MEAT_MINCE_UNCOOKED), 0, true);
		QuernRecipes.addRecipe(MineFantasyItems
						.GENERIC_MEAT_CHUNK_UNCOOKED,
				new ItemStack(MineFantasyItems
						.GENERIC_MEAT_MINCE_UNCOOKED), 0, true);
		QuernRecipes.addRecipe(MineFantasyItems
				.GENERIC_MEAT_COOKED, new ItemStack(MineFantasyItems
				.GENERIC_MEAT_MINCE_COOKED), 0, true);
		QuernRecipes.addRecipe(MineFantasyItems
						.GENERIC_MEAT_STRIP_COOKED,
				new ItemStack(MineFantasyItems
						.GENERIC_MEAT_MINCE_COOKED), 0, true);
		QuernRecipes.addRecipe(MineFantasyItems
						.GENERIC_MEAT_CHUNK_COOKED,
				new ItemStack(MineFantasyItems
						.GENERIC_MEAT_MINCE_COOKED), 0, true);

		QuernRecipes.addRecipe(Items.COAL, new ItemStack(MineFantasyItems.COAL_DUST), 0, true);
		QuernRecipes.addRecipe(new ItemStack(Items.COAL, 1, 1), new ItemStack(MineFantasyItems.COAL_DUST), 0, true);
		QuernRecipes.addRecipe(MineFantasyItems.KAOLINITE, new ItemStack(MineFantasyItems.KAOLINITE_DUST), 0, true);
		QuernRecipes.addRecipe(Items.FLINT, new ItemStack(MineFantasyItems.SHRAPNEL), 0, true);

		QuernRecipes.addRecipe(MineFantasyItems.FLUX, new ItemStack(MineFantasyItems.FLUX_POT), 0, true);

		Salvage.addSalvage(MineFantasyItems.MAGMA_CREAM_REFINED, MineFantasyItems.DRAGON_HEART, Items.BLAZE_POWDER,
				Items.MAGMA_CREAM, MineFantasyItems.CLAY_POT);
	}

	private static void addWoodworks() {
		Salvage.addSalvage(MineFantasyBlocks.NAILED_PLANKS, MineFantasyItems.NAIL,
				MineFantasyItems.TIMBER.construct("ScrapWood", 4));
		Salvage.addSalvage(MineFantasyBlocks.REFINED_PLANKS, MineFantasyItems.NAIL,
				MineFantasyItems.TIMBER.construct("RefinedWood", 4));
		Salvage.addSalvage(MineFantasyBlocks.NAILED_PLANKS_STAIR, MineFantasyItems.NAIL,
				MineFantasyItems.TIMBER.construct("ScrapWood", 3));
		Salvage.addSalvage(MineFantasyBlocks.REFINED_PLANKS_STAIR, MineFantasyItems.NAIL,
				MineFantasyItems.TIMBER.construct("RefinedWood", 3));

		Salvage.addSalvage(MineFantasyBlocks.BELLOWS, new ItemStack(MineFantasyItems.NAIL, 3),
				MineFantasyItems.TIMBER.construct("RefinedWood", 5), new ItemStack(Items.LEATHER, 2));

		Salvage.addSalvage(MineFantasyBlocks.TANNER_REFINED, MineFantasyItems.TIMBER.construct("RefinedWood", 8),
				new ItemStack(MineFantasyItems.NAIL, 3));

		PaintOilRecipe.addRecipe(MineFantasyBlocks.NAILED_PLANKS, MineFantasyBlocks.REFINED_PLANKS);
		PaintOilRecipe.addRecipe(MineFantasyBlocks.NAILED_PLANKS_STAIR, MineFantasyBlocks.REFINED_PLANKS_STAIR);

	}

	private static void addStonemason() {
		Salvage.addSalvage(MineFantasyBlocks.CRUCIBLE_STONE, new ItemStack(Blocks.STONE, 8));
		Salvage.addSalvage(MineFantasyBlocks.CRUCIBLE_FIRECLAY, new ItemStack(MineFantasyItems.FIRECLAY, 8), MineFantasyBlocks.CRUCIBLE_STONE);

		Salvage.addSalvage(MineFantasyBlocks.FORGE, new ItemStack(Blocks.STONE, 4), Items.COAL);
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_STONE, new ItemStack(Blocks.STONE, 6));

		Salvage.addSalvage(MineFantasyBlocks.CHIMNEY_STONE, Blocks.STONE);
		Salvage.addSalvage(MineFantasyBlocks.CHIMNEY_STONE_WIDE, MineFantasyBlocks.CHIMNEY_STONE, Blocks.STONE);
		Salvage.addSalvage(MineFantasyBlocks.CHIMNEY_STONE_EXTRACTOR, MineFantasyBlocks.CHIMNEY_STONE_WIDE);
		Salvage.addSalvage(MineFantasyBlocks.QUERN, new ItemStack(Items.FLINT, 2), new ItemStack(Blocks.STONE, 4));
	}

	private static void addMisc() {
		Salvage.addSalvage(MineFantasyItems.BOMB_CASING_UNCOOKED, new ItemStack(Items.CLAY_BALL, 2));
		Salvage.addSalvage(MineFantasyItems.MINE_CASING_UNCOOKED, new ItemStack(Items.CLAY_BALL, 3),
				Blocks.STONE_PRESSURE_PLATE);

		Salvage.addSalvage(MineFantasyItems.BOMB_CASING_CRYSTAL, Items.GLASS_BOTTLE, MineFantasyItems.DIAMOND_SHARDS,
				new ItemStack(Items.REDSTONE, 2));
		Salvage.addSalvage(MineFantasyItems.MINE_CASING_CRYSTAL, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
				Items.GLASS_BOTTLE, MineFantasyItems.DIAMOND_SHARDS, new ItemStack(Items.REDSTONE, 2));

		Salvage.addSalvage(MineFantasyItems.BOMB_FUSE_LONG, new ItemStack(MineFantasyItems.BOMB_FUSE, 2), Items.REDSTONE);

		Salvage.addSalvage(MineFantasyBlocks.THATCH_STAIR, new ItemStack(Blocks.TALLGRASS, 3, 1));
		Salvage.addSalvage(MineFantasyBlocks.THATCH, new ItemStack(Blocks.TALLGRASS, 4, 1));

		Salvage.addSalvage(LeatherArmourListMFR.LEATHER_APRON, new ItemStack(Items.LEATHER, 3), Items.COAL);

		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 0),
				new ItemStack(MineFantasyItems.HIDE_SMALL, 2), Blocks.WOOL);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 1), MineFantasyItems.HIDE_LARGE, Blocks.WOOL);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 2), MineFantasyItems.HIDE_MEDIUM,
				Blocks.WOOL);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 3), MineFantasyItems.HIDE_SMALL, Blocks.WOOL);
	}

	private static void addCrossbows() {
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_ARMS_LIGHT, new ItemStack(MineFantasyItems.NAIL, 2), Items.STRING,
				MineFantasyItems.TIMBER.construct("RefinedWood", 4));
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_ARMS_BASIC, new ItemStack(MineFantasyItems.NAIL, 3),
				MineFantasyItems.CROSSBOW_ARMS_LIGHT, MineFantasyItems.TIMBER.construct("RefinedWood", 2));
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_ARMS_HEAVY, new ItemStack(MineFantasyItems.NAIL, 3),
				MineFantasyItems.CROSSBOW_ARMS_BASIC, MineFantasyItems.TIMBER.construct("RefinedWood", 2));
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_ARMS_ADVANCED, MineFantasyItems.TUNGSTEN_GEARS,
				new ItemStack(MineFantasyItems.NAIL, 2), MineFantasyItems.CROSSBOW_ARMS_BASIC,
				new ItemStack(MineFantasyItems.STEEL_TUBE, 3));

		Salvage.addSalvage(MineFantasyItems.CROSSBOW_SCOPE, MineFantasyItems.TUNGSTEN_GEARS, MineFantasyItems.SPYGLASS,
				new ItemStack(MineFantasyItems.BOLT, 2), MineFantasyItems.TIMBER.construct("RefinedWood"));
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_AMMO, MineFantasyItems.TUNGSTEN_GEARS,
				new ItemStack(MineFantasyItems.NAIL, 3), MineFantasyItems.TIMBER.construct("RefinedWood", 7));
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_HANDLE_WOOD, new ItemStack(MineFantasyItems.NAIL, 2),
				MineFantasyItems.TIMBER.construct("RefinedWood", 3));
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_STOCK_WOOD, new ItemStack(MineFantasyItems.NAIL, 3),
				MineFantasyItems.TIMBER.construct("RefinedWood", 7));
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_STOCK_IRON, new ItemStack(MineFantasyItems.TUNGSTEN_GEARS, 2),
				Blocks.OBSIDIAN, new ItemStack(MineFantasyItems.BOLT, 4), new ItemStack(MineFantasyItems.IRON_STRUT, 3),
				MineFantasyItems.CROSSBOW_STOCK_WOOD);
	}

	private static void addEngineering() {
		addCrossbows();

		ItemStack blackPlate = MineFantasyItems.PLATE.createComm("blackSteel");
		ItemStack steelPlate = MineFantasyItems.PLATE.createComm("steel");

		Salvage.addSalvage(MineFantasyItems.COGWORK_SHAFT, new ItemStack(MineFantasyItems.IRON_STRUT, 2),
				new ItemStack(MineFantasyItems.BOLT, 4), MineFantasyItems.IRON_FRAME, Blocks.PISTON,
				MineFantasyItems.TUNGSTEN_GEARS);

		Salvage.addSalvage(MineFantasyBlocks.CRUCIBLE_AUTO, new ItemStack(MineFantasyItems.BOLT, 3),
				MineFantasyItems.TUNGSTEN_GEARS, MineFantasyBlocks.CRUCIBLE_FIRECLAY, steelPlate, steelPlate, steelPlate,
				steelPlate);
		Salvage.addSalvage(MineFantasyBlocks.BOMB_BENCH, new ItemStack(MineFantasyItems.BOLT, 4), MineFantasyItems.IRON_FRAME,
				MineFantasyBlocks.CARPENTER);
		Salvage.addSalvage(MineFantasyBlocks.CROSSBOW_BENCH, new ItemStack(MineFantasyItems.NAIL, 2),
				MineFantasyItems.TIMBER.construct("ScrapWood", 2), Items.STRING, MineFantasyBlocks.CARPENTER);
		Salvage.addSalvage(MineFantasyBlocks.BOMB_PRESS, new ItemStack(MineFantasyItems.IRON_STRUT, 2),
				new ItemStack(MineFantasyItems.BOLT, 2), new ItemStack(MineFantasyItems.BRONZE_GEARS, 2), Blocks.LEVER,
				MineFantasyItems.IRON_FRAME);
		Salvage.addSalvage(MineFantasyBlocks.TANNER_METAL, new ItemStack(MineFantasyItems.IRON_STRUT, 4),
				new ItemStack(MineFantasyItems.BOLT, 2), new ItemStack(MineFantasyItems.BRONZE_GEARS, 3),
				MineFantasyItems.STANDARD_NEEDLE, Blocks.LEVER, MineFantasyItems.IRON_FRAME);
		Salvage.addSalvage(MineFantasyBlocks.FORGE_METAL, new ItemStack(MineFantasyItems.BOLT, 4), blackPlate, blackPlate,
				blackPlate, blackPlate, new ItemStack(MineFantasyItems.IRON_FRAME, 2),
				new ItemStack(Blocks.REDSTONE_BLOCK, 2));
		Salvage.addSalvage(MineFantasyItems.SPYGLASS, new ItemStack(MineFantasyItems.BOLT, 2), new ItemStack(Blocks.GLASS, 2),
				MineFantasyItems.STEEL_TUBE, MineFantasyItems.BRONZE_GEARS);
		Salvage.addSalvage(MineFantasyItems.SYRINGE_EMPTY, Items.GLASS_BOTTLE, MineFantasyItems.STANDARD_NEEDLE,
				MineFantasyItems.STEEL_TUBE);
		Salvage.addSalvage(MineFantasyItems.PARACHUTE, new ItemStack(MineFantasyItems.THREAD, 3),
				new ItemStack(Blocks.WOOL, 3), new ItemStack(MineFantasyItems.LEATHER_STRIP, 4), Items.LEATHER);
	}

	private static void addNonPrimitiveStone() {
		Salvage.addSalvage(MineFantasyItems.STONE_KNIFE, new ItemStack(Blocks.COBBLESTONE, 2),
				MineFantasyItems.TIMBER.construct("ScrapWood"));
		Salvage.addSalvage(MineFantasyItems.STONE_HAMMER, Blocks.COBBLESTONE, MineFantasyItems.TIMBER.construct("ScrapWood"));
		Salvage.addSalvage(MineFantasyItems.STONE_TONGS, new ItemStack(Blocks.COBBLESTONE, 2),
				MineFantasyItems.TIMBER.construct("ScrapWood"));
		Salvage.addSalvage(MineFantasyItems.BONE_NEEDLE, Items.BONE);
	}

	private static void addPrimitive() {

		Salvage.addSalvage(MineFantasyItems.STONE_PICK, new ItemStack(MineFantasyItems.SHARP_ROCK, 2),
				new ItemStack(Items.STICK, 2), MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_AXE, new ItemStack(MineFantasyItems.SHARP_ROCK, 2),
				new ItemStack(Items.STICK, 2), MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_SPADE, MineFantasyItems.SHARP_ROCK, new ItemStack(Items.STICK, 2),
				MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_HOE, MineFantasyItems.SHARP_ROCK, new ItemStack(Items.STICK, 2),
				MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_SWORD, Items.STICK, new ItemStack(MineFantasyItems.SHARP_ROCK, 2),
				MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_WARAXE, new ItemStack(MineFantasyItems.SHARP_ROCK, 2),
				new ItemStack(Items.STICK, 2), new ItemStack(MineFantasyItems.VINE, 2));
		Salvage.addSalvage(MineFantasyItems.STONE_MACE, new ItemStack(MineFantasyItems.SHARP_ROCK, 2),
				new ItemStack(Items.STICK, 2), MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_SPEAR, MineFantasyItems.SHARP_ROCK, new ItemStack(Items.STICK, 2),
				MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_KNIFE, MineFantasyItems.SHARP_ROCK, Items.STICK, MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_HAMMER, MineFantasyItems.SHARP_ROCK, Items.STICK, MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_TONGS, MineFantasyItems.SHARP_ROCK, Items.STICK, MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.BONE_NEEDLE, Items.BONE);
	}

	static void initTierWood() {
		Item plank = MineFantasyItems.TIMBER;

		// TODO: fix salvage
		//        Salvage.addSalvage(CustomToolListMFR.STANDARD_SPOON, plank, Items.STICK);
		//        KnowledgeListMFR.malletR = MineFantasyRebornAPI.addCarpenterToolRecipe(Skill.ARTISANRY, CustomToolListMFR.STANDARD_MALLET, "",
		//                basic, "hands", -1, 1 + (int) (2 * time), new Object[]{"WW", " S", 'W', plank, 'S', Items.STICK});

		Salvage.addSalvage(MineFantasyItems.STANDARD_MALLET, plank, plank, Items.STICK);
		Salvage.addSalvage(MineFantasyItems.STANDARD_SPOON, plank, Items.STICK);
	}

}
