package minefantasy.mfr.recipe;

import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.block.BlockWoodDecor;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.init.LeatherArmourListMFR;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyMaterials;
import minefantasy.mfr.item.ItemComponentMFR;
import minefantasy.mfr.item.ItemMetalComponent;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.recipe.refine.PaintOilRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class SalvageRecipes {


	public static void init() {

		ItemStack bronzeHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack("bronze");
		ItemStack ironHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack(MineFantasyMaterials.IRON.name);
		ItemStack steelHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack(MineFantasyBlocks.STEEL_BARS.material.name);
		ItemStack blackSteelHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack(MineFantasyBlocks.BLACK_STEEL_BARS.material.name);
		ItemStack blueSteelHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack(MineFantasyBlocks.BLUE_STEEL_BARS.material.name);
		ItemStack redSteelHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack(MineFantasyBlocks.RED_STEEL_BARS.material.name);
		ItemStack tungstenHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack("tungsten");
		ItemStack obsidianHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack("obsidian");

		ItemStack copper = MineFantasyItems.bar("Copper");
		ItemStack iron = MineFantasyItems.bar(MineFantasyMaterials.IRON.name);
		ItemStack steel = MineFantasyItems.bar("Steel");
		ItemStack plate = ((ItemMetalComponent)MineFantasyItems.PLATE).createComponentItemStack(MineFantasyMaterials.IRON.name);

		Item bar = MineFantasyItems.BAR;
		Item hunk = MineFantasyItems.METAL_HUNK;
		Item plank = MineFantasyItems.TIMBER;
		Item strip = MineFantasyItems.LEATHER_STRIP;
		Item rivet = MineFantasyItems.RIVET;

		Salvage.addSalvage(Items.WOODEN_PICKAXE, new ItemStack(Items.STICK, 2), new ItemStack(Blocks.PLANKS, 3));
		Salvage.addSalvage(Items.WOODEN_AXE, new ItemStack(Items.STICK, 2), new ItemStack(Blocks.PLANKS, 3));
		Salvage.addSalvage(Items.WOODEN_SHOVEL, new ItemStack(Items.STICK, 2), new ItemStack(Blocks.PLANKS, 1));
		Salvage.addSalvage(Items.WOODEN_HOE, new ItemStack(Items.STICK, 2), new ItemStack(Blocks.PLANKS, 2));
		Salvage.addSalvage(Items.WOODEN_SWORD, new ItemStack(Items.STICK, 1), new ItemStack(Blocks.PLANKS, 2));

		Salvage.addSalvage(Items.STONE_PICKAXE, new ItemStack(Items.STICK, 2), new ItemStack(Blocks.COBBLESTONE, 3));
		Salvage.addSalvage(Items.STONE_AXE, new ItemStack(Items.STICK, 2), new ItemStack(Blocks.COBBLESTONE, 3));
		Salvage.addSalvage(Items.STONE_SHOVEL, new ItemStack(Items.STICK, 2), new ItemStack(Blocks.COBBLESTONE, 1));
		Salvage.addSalvage(Items.STONE_HOE, new ItemStack(Items.STICK, 2), new ItemStack(Blocks.COBBLESTONE, 2));
		Salvage.addSalvage(Items.STONE_SWORD, new ItemStack(Items.STICK, 1), new ItemStack(Blocks.COBBLESTONE, 2));

		Salvage.addSalvage(Items.LEATHER_HELMET, new ItemStack(Items.LEATHER, 5));
		Salvage.addSalvage(Items.LEATHER_CHESTPLATE, new ItemStack(Items.LEATHER, 8));
		Salvage.addSalvage(Items.LEATHER_LEGGINGS, new ItemStack(Items.LEATHER, 7));
		Salvage.addSalvage(Items.LEATHER_BOOTS, new ItemStack(Items.LEATHER, 4));

		Salvage.addSalvage(Items.IRON_PICKAXE, new ItemStack(Items.STICK, 2), new ItemStack(Items.IRON_INGOT, 3));
		Salvage.addSalvage(Items.IRON_AXE, new ItemStack(Items.STICK, 2), new ItemStack(Items.IRON_INGOT, 3));
		Salvage.addSalvage(Items.IRON_SHOVEL, new ItemStack(Items.STICK, 2), new ItemStack(Items.IRON_INGOT, 1));
		Salvage.addSalvage(Items.IRON_HOE, new ItemStack(Items.STICK, 2), new ItemStack(Items.IRON_INGOT, 2));
		Salvage.addSalvage(Items.IRON_SWORD, new ItemStack(Items.STICK, 1), new ItemStack(Items.IRON_INGOT, 2));
		Salvage.addSalvage(Items.CHAINMAIL_HELMET, new ItemStack(Items.IRON_INGOT, 2));
		Salvage.addSalvage(Items.CHAINMAIL_CHESTPLATE, new ItemStack(Items.IRON_INGOT, 4));
		Salvage.addSalvage(Items.CHAINMAIL_LEGGINGS, new ItemStack(Items.IRON_INGOT, 4));
		Salvage.addSalvage(Items.CHAINMAIL_BOOTS, new ItemStack(Items.IRON_INGOT, 2));
		Salvage.addSalvage(Items.IRON_HELMET, new ItemStack(Items.IRON_INGOT, 5));
		Salvage.addSalvage(Items.IRON_CHESTPLATE, new ItemStack(Items.IRON_INGOT, 8));
		Salvage.addSalvage(Items.IRON_LEGGINGS, new ItemStack(Items.IRON_INGOT, 7));
		Salvage.addSalvage(Items.IRON_BOOTS, new ItemStack(Items.IRON_INGOT, 4));
		Salvage.addSalvage(Items.IRON_HORSE_ARMOR, Items.LEATHER, new ItemStack(Items.IRON_INGOT, 8));

		Salvage.addSalvage(Items.GOLDEN_PICKAXE, new ItemStack(Items.STICK, 2), new ItemStack(Items.GOLD_INGOT, 3));
		Salvage.addSalvage(Items.GOLDEN_AXE, new ItemStack(Items.STICK, 2), new ItemStack(Items.GOLD_INGOT, 3));
		Salvage.addSalvage(Items.GOLDEN_SHOVEL, new ItemStack(Items.STICK, 2), new ItemStack(Items.GOLD_INGOT, 1));
		Salvage.addSalvage(Items.GOLDEN_HOE, new ItemStack(Items.STICK, 2), new ItemStack(Items.GOLD_INGOT, 2));
		Salvage.addSalvage(Items.GOLDEN_SWORD, new ItemStack(Items.STICK, 1), new ItemStack(Items.GOLD_INGOT, 2));
		Salvage.addSalvage(Items.GOLDEN_HELMET, new ItemStack(Items.GOLD_INGOT, 5));
		Salvage.addSalvage(Items.GOLDEN_CHESTPLATE, new ItemStack(Items.GOLD_INGOT, 8));
		Salvage.addSalvage(Items.GOLDEN_LEGGINGS, new ItemStack(Items.GOLD_INGOT, 7));
		Salvage.addSalvage(Items.GOLDEN_BOOTS, new ItemStack(Items.GOLD_INGOT, 4));
		Salvage.addSalvage(Items.GOLDEN_HORSE_ARMOR, Items.LEATHER, new ItemStack(Items.GOLD_INGOT, 8));

		Salvage.addSalvage(Items.DIAMOND_PICKAXE, new ItemStack(Items.STICK, 2), new ItemStack(Items.DIAMOND, 3));
		Salvage.addSalvage(Items.DIAMOND_AXE, new ItemStack(Items.STICK, 2), new ItemStack(Items.DIAMOND, 3));
		Salvage.addSalvage(Items.DIAMOND_SHOVEL, new ItemStack(Items.STICK, 2), new ItemStack(Items.DIAMOND, 1));
		Salvage.addSalvage(Items.DIAMOND_HOE, new ItemStack(Items.STICK, 2), new ItemStack(Items.DIAMOND, 2));
		Salvage.addSalvage(Items.DIAMOND_SWORD, new ItemStack(Items.STICK, 1), new ItemStack(Items.DIAMOND, 2));
		Salvage.addSalvage(Items.DIAMOND_HELMET, new ItemStack(Items.DIAMOND, 5));
		Salvage.addSalvage(Items.DIAMOND_CHESTPLATE, new ItemStack(Items.DIAMOND, 8));
		Salvage.addSalvage(Items.DIAMOND_LEGGINGS, new ItemStack(Items.DIAMOND, 7));
		Salvage.addSalvage(Items.DIAMOND_BOOTS, new ItemStack(Items.DIAMOND, 4));
		Salvage.addSalvage(Items.DIAMOND_HORSE_ARMOR, Items.LEATHER, new ItemStack(Items.DIAMOND, 8));

		Salvage.addSalvage(Items.FLINT_AND_STEEL, Items.FLINT, Items.IRON_INGOT);
		Salvage.addSalvage(Items.COMPASS, Items.REDSTONE, new ItemStack(Items.IRON_INGOT, 4));
		Salvage.addSalvage(Items.CLOCK, Items.REDSTONE, new ItemStack(Items.GOLD_INGOT, 4));
		Salvage.addSalvage(Items.BOW, new ItemStack(Items.STICK, 3), new ItemStack(Items.STRING, 3));
		Salvage.addSalvage(Items.FISHING_ROD, new ItemStack(Items.STICK, 3), new ItemStack(Items.STRING, 2));
		Salvage.addSalvage(Items.SHEARS, new ItemStack(Items.IRON_INGOT, 2));
		Salvage.addSalvage(Items.MINECART, new ItemStack(Items.IRON_INGOT, 5));
		Salvage.addSalvage(Items.BOAT, new ItemStack(Blocks.PLANKS, 5));

		Salvage.addSalvage(Blocks.FURNACE, new ItemStack(Blocks.COBBLESTONE, 8));
		Salvage.addSalvage(Blocks.DISPENSER, Items.BOW, Items.REDSTONE, new ItemStack(Blocks.COBBLESTONE, 7));
		Salvage.addSalvage(Blocks.DROPPER, Items.REDSTONE, new ItemStack(Blocks.COBBLESTONE, 7));
		Salvage.addSalvage(Blocks.CHEST, new ItemStack(Blocks.PLANKS, 8));
		Salvage.addSalvage(Blocks.PISTON, Items.REDSTONE, Items.IRON_INGOT, new ItemStack(Blocks.COBBLESTONE, 4), new ItemStack(Blocks.PLANKS, 3));
		Salvage.addSalvage(Blocks.STICKY_PISTON, Items.SLIME_BALL, Blocks.PISTON);
		Salvage.addSalvage(Items.OAK_DOOR, new ItemStack(Blocks.PLANKS, 6));
		Salvage.addSalvage(Items.SPRUCE_DOOR, new ItemStack(Blocks.PLANKS, 6));
		Salvage.addSalvage(Items.BIRCH_DOOR, new ItemStack(Blocks.PLANKS, 6));
		Salvage.addSalvage(Items.DARK_OAK_DOOR, new ItemStack(Blocks.PLANKS, 6));
		Salvage.addSalvage(Items.JUNGLE_DOOR, new ItemStack(Blocks.PLANKS, 6));
		Salvage.addSalvage(Items.ACACIA_DOOR, new ItemStack(Blocks.PLANKS, 6));
		Salvage.addSalvage(Items.IRON_DOOR, new ItemStack(Items.IRON_INGOT, 6));
		Salvage.addSalvage(Blocks.TRAPDOOR, new ItemStack(Blocks.PLANKS, 3));
		Salvage.addSalvage(Blocks.HOPPER, Blocks.CHEST, new ItemStack(Items.IRON_INGOT, 5));
		Salvage.addSalvage(Items.CAULDRON, new ItemStack(Items.IRON_INGOT, 7));
		Salvage.addSalvage(Items.BED, new ItemStack(Blocks.PLANKS, 3), new ItemStack(Blocks.WOOL, 3));
		Salvage.addSalvage(Items.BOOK, new ItemStack(Items.PAPER, 3), Items.LEATHER);
		Salvage.addSalvage(Items.BREWING_STAND, new ItemStack(Blocks.COBBLESTONE, 3), Items.BLAZE_ROD);
		Salvage.addSalvage(Items.CARROT_ON_A_STICK, Items.CARROT, Items.FISHING_ROD);
		Salvage.addSalvage(Items.COMPARATOR, new ItemStack(Blocks.REDSTONE_TORCH, 3), new ItemStack(Blocks.STONE, 3), Items.QUARTZ);
		Salvage.addSalvage(Items.ENDER_EYE, Items.ENDER_PEARL, Items.BLAZE_POWDER);
		Salvage.addSalvage(Items.MAGMA_CREAM, Items.BLAZE_POWDER, Items.SLIME_BALL);
		Salvage.addSalvage(Items.PAINTING, new ItemStack(Items.STICK, 8), Blocks.WOOL);
		Salvage.addSalvage(Items.REPEATER, new ItemStack(Blocks.STONE, 3), new ItemStack(Blocks.REDSTONE_TORCH, 2), Items.REDSTONE);
		Salvage.addSalvage(Items.SADDLE, Items.LEATHER, 5);
		Salvage.addSalvage(Items.SIGN, new ItemStack(Blocks.PLANKS, 2));
		Salvage.addSalvage(Items.SPECKLED_MELON, new ItemStack(Items.GOLD_NUGGET, 8), Items.MELON);
		Salvage.addSalvage(Blocks.ACTIVATOR_RAIL, Items.IRON_INGOT, Items.REDSTONE);
		Salvage.addSalvage(Blocks.ANVIL, new ItemStack(Items.IRON_INGOT, 31));
		Salvage.addSalvage(Blocks.BEACON, Items.NETHER_STAR, new ItemStack(Blocks.GLASS, 5), new ItemStack(Blocks.OBSIDIAN, 3));
		Salvage.addSalvage(Blocks.BOOKSHELF, new ItemStack(Blocks.PLANKS, 6), new ItemStack(Items.BOOK, 3));
		Salvage.addSalvage(Blocks.CRAFTING_TABLE, new ItemStack(Blocks.PLANKS, 4));
		Salvage.addSalvage(Blocks.DAYLIGHT_DETECTOR, new ItemStack(Blocks.GLASS, 3), new ItemStack(Blocks.WOODEN_SLAB, 3), new ItemStack(Items.QUARTZ, 3));
		Salvage.addSalvage(Blocks.DEADBUSH, new ItemStack(Items.STICK, 4));
		Salvage.addSalvage(Blocks.DETECTOR_RAIL, Blocks.RAIL, Blocks.STONE_PRESSURE_PLATE);
		Salvage.addSalvage(Blocks.ENCHANTING_TABLE, new ItemStack(Blocks.OBSIDIAN, 4), new ItemStack(Items.DIAMOND, 2), Items.BOOK);
		Salvage.addSalvage(Blocks.ENDER_CHEST, new ItemStack(Blocks.OBSIDIAN, 8), Items.ENDER_EYE);
		Salvage.addSalvage(Blocks.OAK_FENCE, new ItemStack(Items.STICK, 3));
		Salvage.addSalvage(Blocks.SPRUCE_FENCE, new ItemStack(Items.STICK, 3));
		Salvage.addSalvage(Blocks.BIRCH_FENCE, new ItemStack(Items.STICK, 3));
		Salvage.addSalvage(Blocks.JUNGLE_FENCE, new ItemStack(Items.STICK, 3));
		Salvage.addSalvage(Blocks.DARK_OAK_FENCE, new ItemStack(Items.STICK, 3));
		Salvage.addSalvage(Blocks.ACACIA_FENCE, new ItemStack(Items.STICK, 3));
		Salvage.addSalvage(Blocks.OAK_FENCE_GATE, new ItemStack(Items.STICK, 3));
		Salvage.addSalvage(Blocks.SPRUCE_FENCE_GATE, new ItemStack(Items.STICK, 3));
		Salvage.addSalvage(Blocks.BIRCH_FENCE_GATE, new ItemStack(Items.STICK, 3));
		Salvage.addSalvage(Blocks.JUNGLE_FENCE_GATE, new ItemStack(Items.STICK, 3));
		Salvage.addSalvage(Blocks.DARK_OAK_FENCE_GATE, new ItemStack(Items.STICK, 3));
		Salvage.addSalvage(Blocks.ACACIA_FENCE_GATE, new ItemStack(Items.STICK, 3));
		Salvage.addSalvage(Blocks.GOLDEN_RAIL, Items.GOLD_INGOT);
		Salvage.addSalvage(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, new ItemStack(Items.IRON_INGOT, 2));
		Salvage.addSalvage(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, new ItemStack(Items.GOLD_INGOT, 2));
		Salvage.addSalvage(Blocks.JUKEBOX, new ItemStack(Blocks.PLANKS, 8), Items.DIAMOND);
		Salvage.addSalvage(Blocks.LADDER, new ItemStack(Items.STICK, 2));
		Salvage.addSalvage(Blocks.LEVER, Items.STICK, Blocks.COBBLESTONE);
		Salvage.addSalvage(Blocks.LIT_PUMPKIN, Blocks.PUMPKIN, Blocks.TORCH);
		Salvage.addSalvage(Blocks.NOTEBLOCK, new ItemStack(Blocks.PLANKS, 8), Items.REDSTONE);
		Salvage.addSalvage(Blocks.RAIL, ironHunk);
		Salvage.addSalvage(Blocks.REDSTONE_LAMP, new ItemStack(Items.REDSTONE, 4), Blocks.GLOWSTONE);
		Salvage.addSalvage(Blocks.REDSTONE_TORCH, Items.STICK, Items.REDSTONE);
		Salvage.addSalvage(Blocks.SANDSTONE, new ItemStack(Blocks.SAND, 4));
		Salvage.addSalvage(Blocks.STONEBRICK, Blocks.STONE);
		Salvage.addSalvage(Blocks.STONE_BUTTON, Blocks.STONE);
		Salvage.addSalvage(Blocks.STONE_PRESSURE_PLATE, new ItemStack(Blocks.STONE, 2));
		Salvage.addSalvage(Blocks.TNT, new ItemStack(Blocks.SAND, 4), Items.GUNPOWDER, 5);
		Salvage.addSalvage(Blocks.TRAPPED_CHEST, Blocks.CHEST, Blocks.TRIPWIRE_HOOK);
		Salvage.addSalvage(Blocks.TRIPWIRE_HOOK, ironHunk, ironHunk);
		Salvage.addSalvage(Blocks.WOODEN_BUTTON, Blocks.PLANKS);
		Salvage.addSalvage(Blocks.WOODEN_PRESSURE_PLATE, new ItemStack(Blocks.PLANKS, 2));
		Salvage.addSalvage(Blocks.COBBLESTONE_WALL, Blocks.COBBLESTONE);
		Salvage.addSalvage(Blocks.IRON_BARS, ironHunk, ironHunk);
		Salvage.addSalvage(Items.ITEM_FRAME, new ItemStack(Items.STICK, 8), Items.LEATHER);

		Salvage.addSalvage(MineFantasyItems.ENCRUSTED_INGOT, MineFantasyItems.STEEL_INGOT, MineFantasyItems.DIAMOND_SHARDS);
		Salvage.addSalvage(MineFantasyItems.bar("Encrusted"), MineFantasyItems.STEEL_INGOT, MineFantasyItems.DIAMOND_SHARDS);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 3, 0), LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 0), new ItemStack(MineFantasyItems.RIVET, 4));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 3, 1), LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 1), new ItemStack(MineFantasyItems.RIVET, 4));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 3, 2), LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 2), new ItemStack(MineFantasyItems.RIVET, 4));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 3, 3), LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 3), new ItemStack(MineFantasyItems.RIVET, 4));
		Salvage.addSalvage(MineFantasyBlocks.BLAST_HEATER, new ItemStack(MineFantasyItems.RIVET, 8), plate, plate, plate, plate, plate, plate, plate, plate, Blocks.FURNACE);
		Salvage.addSalvage(MineFantasyBlocks.BLAST_CHAMBER, new ItemStack(MineFantasyItems.RIVET, 8), plate, plate, plate, plate, plate, plate, plate, plate);
		Salvage.addSalvage(MineFantasyBlocks.FURNACE_HEATER, new ItemStack(MineFantasyItems.RIVET, 2), plate, plate, MineFantasyBlocks.FIREBRICKS, MineFantasyBlocks.FORGE);
		Salvage.addSalvage(MineFantasyBlocks.FURNACE_STONE, new ItemStack(MineFantasyItems.RIVET, 3), plate, plate, plate, plate, MineFantasyBlocks.FIREBRICKS, Blocks.FURNACE);
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_BRONZE, MineFantasyItems.bar(MineFantasyMaterials.BRONZE.name, 6));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_IRON, MineFantasyItems.bar(MineFantasyMaterials.IRON.name, 6));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_STEEL, MineFantasyItems.bar(MineFantasyMaterials.STEEL.name, 6));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_BLACK_STEEL, MineFantasyItems.bar(MineFantasyMaterials.BLACK_STEEL.name, 6));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_BLUE_STEEL, MineFantasyItems.bar(MineFantasyMaterials.BLUE_STEEL.name, 6));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_RED_STEEL, MineFantasyItems.bar(MineFantasyMaterials.RED_STEEL.name, 6));
		Salvage.addSalvage(MineFantasyBlocks.REINFORCED_STONE_FRAMED, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk, MineFantasyBlocks.REINFORCED_STONE);
		Salvage.addSalvage(MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, ironHunk, ironHunk, ironHunk, ironHunk, MineFantasyBlocks.REINFORCED_STONE);
		Salvage.addSalvage(MineFantasyBlocks.CHIMNEY_PIPE, MineFantasyBlocks.CHIMNEY_STONE, bronzeHunk);
		Salvage.addSalvage(MineFantasyBlocks.BRONZE_BARS, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk);
		Salvage.addSalvage(MineFantasyBlocks.IRON_BARS, ironHunk, ironHunk, ironHunk, ironHunk);
		Salvage.addSalvage(MineFantasyBlocks.STEEL_BARS, steelHunk, steelHunk, steelHunk, steelHunk);
		Salvage.addSalvage(MineFantasyBlocks.BLACK_STEEL_BARS, blackSteelHunk, blackSteelHunk, blackSteelHunk, blackSteelHunk);
		Salvage.addSalvage(MineFantasyBlocks.BLUE_STEEL_BARS, blueSteelHunk, blueSteelHunk, blueSteelHunk, blueSteelHunk);
		Salvage.addSalvage(MineFantasyBlocks.RED_STEEL_BARS, redSteelHunk, redSteelHunk, redSteelHunk, redSteelHunk);

		Salvage.addSalvage(MineFantasyItems.CAKE_TIN, MineFantasyItems.bar(MineFantasyMaterials.IRON.name, 3), MineFantasyItems.RIVET);
		Salvage.addSalvage(MineFantasyItems.HINGE, MineFantasyItems.LEATHER_STRIP, MineFantasyItems.RIVET);
		Salvage.addSalvage(MineFantasyItems.ENGIN_ANVIL_TOOLS, steelHunk, steelHunk, steelHunk, steelHunk, new ItemStack(MineFantasyItems.LEATHER_STRIP, 4));
		Salvage.addSalvage(MineFantasyItems.IRON_FRAME, steelHunk, steelHunk, steelHunk, steelHunk, ironHunk, ironHunk, ironHunk, ironHunk, new ItemStack(MineFantasyItems.RIVET, 3));
		Salvage.addSalvage(MineFantasyItems.IRON_STRUT, steelHunk, steelHunk, steelHunk, steelHunk, MineFantasyItems.bar(MineFantasyMaterials.IRON.name, 2), new ItemStack(MineFantasyItems.RIVET, 2));
		Salvage.addSalvage(MineFantasyItems.STEEL_TUBE, steelHunk, steelHunk, steelHunk, steelHunk, new ItemStack(MineFantasyItems.RIVET, 2));
		Salvage.addSalvage(MineFantasyItems.CLIMBING_PICK_BASIC, MineFantasyItems.bar(MineFantasyMaterials.IRON.name, 2), MineFantasyItems.bar("Steel", 2), new ItemStack(MineFantasyItems.RIVET, 2), new ItemStack(MineFantasyItems.LEATHER_STRIP, 2));
		Salvage.addSalvage(MineFantasyItems.BRONZE_GEARS, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk, iron);
		Salvage.addSalvage(MineFantasyItems.TUNGSTEN_GEARS, tungstenHunk, tungstenHunk, tungstenHunk, tungstenHunk, MineFantasyItems.BRONZE_GEARS);
		Salvage.addSalvage(MineFantasyItems.bar(MineFantasyMaterials.COMPOSITE_ALLOY.name), copper, steel, tungstenHunk, new ItemStack(MineFantasyItems.RIVET, 2));
		Salvage.addSalvage(MineFantasyItems.COMPOSITE_ALLOY_INGOT, copper, steel, tungstenHunk, new ItemStack(MineFantasyItems.RIVET, 2));
		Salvage.addSalvage(MineFantasyItems.BOMB_CASING_IRON, ironHunk, ironHunk);
		Salvage.addSalvage(MineFantasyItems.MINE_CASING_IRON, ironHunk, ironHunk, MineFantasyItems.RIVET, iron);
		Salvage.addSalvage(MineFantasyItems.BOMB_CASING_ARROW, ironHunk, ironHunk, ironHunk, ironHunk, new ItemStack(Items.REDSTONE, 2), MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG), MineFantasyItems.FLETCHING);
		Salvage.addSalvage(MineFantasyItems.BOMB_CASING_BOLT, ironHunk, ironHunk, ironHunk, ironHunk, new ItemStack(Items.REDSTONE, 2), MineFantasyItems.FLETCHING);
		Salvage.addSalvage(MineFantasyItems.BOMB_CASING_OBSIDIAN, obsidianHunk, obsidianHunk, new ItemStack(MineFantasyItems.RIVET, 2));
		Salvage.addSalvage(MineFantasyItems.MINE_CASING_OBSIDIAN, obsidianHunk, obsidianHunk, iron, MineFantasyItems.RIVET);
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_BAYONET, ironHunk, ironHunk, ironHunk, ironHunk, MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG), new ItemStack(MineFantasyItems.RIVET, 2));
		Salvage.addSalvage(MineFantasyItems.PAINT_BRUSH, Blocks.WOOL, MineFantasyItems.bar(MineFantasyMaterials.TIN.name), MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG));
		Salvage.addSalvage(MineFantasyBlocks.FRAME_BLOCK, MineFantasyItems.IRON_FRAME, MineFantasyItems.RIVET);
		Salvage.addSalvage(MineFantasyItems.COGWORK_PULLEY, Blocks.REDSTONE_BLOCK, new ItemStack(MineFantasyItems.IRON_FRAME, 2), new ItemStack(MineFantasyItems.RIVET, 2), new ItemStack(MineFantasyItems.TUNGSTEN_GEARS, 2));
		Salvage.addSalvage(MineFantasyBlocks.FRAME_BLOCK, MineFantasyItems.IRON_FRAME, MineFantasyItems.RIVET);
		Salvage.addSalvage(MineFantasyBlocks.BLOCK_COGWORK_HELM, new ItemStack(Items.ENDER_EYE, 2), new ItemStack(MineFantasyItems.IRON_FRAME, 2), new ItemStack(MineFantasyItems.COGWORK_SHAFT, 2), new ItemStack(MineFantasyItems.RIVET, 4));
		Salvage.addSalvage(MineFantasyBlocks.BLOCK_COGWORK_CHESTPLATE, Blocks.FURNACE, new ItemStack(MineFantasyItems.IRON_FRAME, 5), new ItemStack(MineFantasyItems.COGWORK_SHAFT, 4), new ItemStack(MineFantasyItems.RIVET, 6));
		Salvage.addSalvage(MineFantasyBlocks.BLOCK_COGWORK_LEGS, new ItemStack(MineFantasyItems.IRON_FRAME, 5), new ItemStack(MineFantasyItems.COGWORK_SHAFT, 4), new ItemStack(MineFantasyItems.RIVET, 4));



		Salvage.addSalvage(MineFantasyItems.TINDERBOX, Items.FLINT, Items.STICK, Blocks.WOOL, MineFantasyItems.bar("Iron"));
		Salvage.addSalvage(Items.FLINT_AND_STEEL, Items.FLINT, MineFantasyItems.bar("Steel"));
		Salvage.addSalvage(MineFantasyItems.STANDARD_NEEDLE, bar);
		Salvage.addSalvage(MineFantasyItems.STANDARD_BOLT, MineFantasyItems.FLETCHING, hunk);
		Salvage.addSalvage(MineFantasyItems.ARROWHEAD, hunk);
		Salvage.addSalvage(MineFantasyItems.BODKIN_HEAD, hunk);
		Salvage.addSalvage(MineFantasyItems.BROAD_HEAD, hunk);
		Salvage.addSalvage(MineFantasyItems.STANDARD_ARROW, MineFantasyItems.ARROWHEAD, MineFantasyItems.FLETCHING);
		Salvage.addSalvage(MineFantasyItems.STANDARD_ARROW_BODKIN, MineFantasyItems.BODKIN_HEAD, MineFantasyItems.FLETCHING);
		Salvage.addSalvage(MineFantasyItems.STANDARD_ARROW_BROAD, MineFantasyItems.BROAD_HEAD, MineFantasyItems.FLETCHING);
		Salvage.addSalvage(MineFantasyItems.STANDARD_PICK, bar, bar, bar, plank, plank, strip, strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_AXE, bar, bar, bar, plank, plank, strip, strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_HOE, bar, bar, plank, plank, strip, strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_SPADE, bar, plank, plank, strip, strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_HEAVY_PICK, bar, bar, bar, bar, bar, plank, plank, strip, strip, rivet, rivet);
		Salvage.addSalvage(MineFantasyItems.STANDARD_HANDPICK, bar, bar, plank, strip, strip, rivet);
		Salvage.addSalvage(MineFantasyItems.STANDARD_HEAVY_SHOVEL, bar, bar, bar, bar, bar, bar, plank, plank, strip, strip, rivet, rivet);
		Salvage.addSalvage(MineFantasyItems.STANDARD_TROW, bar, plank, strip, strip, rivet);
		Salvage.addSalvage(MineFantasyItems.STANDARD_SCYTHE, bar, bar, bar, plank, plank, plank, plank, strip, rivet, rivet);
		Salvage.addSalvage(MineFantasyItems.STANDARD_MATTOCK, bar, bar, bar, rivet, plank, plank, strip, strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_LUMBER, bar, bar, bar, rivet, rivet, rivet, plank, plank, plank, strip, strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_HAMMER, bar, plank, strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_HEAVY_HAMMER, bar, bar, bar, plank, strip, rivet);
		Salvage.addSalvage(MineFantasyItems.STANDARD_TONGS, bar, bar);
		Salvage.addSalvage(MineFantasyItems.STANDARD_KNIFE, bar, plank, strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_SHEARS, bar, bar, plank, plank, Items.LEATHER);
		Salvage.addSalvage(MineFantasyItems.STANDARD_SAW, bar, bar, bar, bar, plank, strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_SPANNER, bar, bar, bar, plank, strip, strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_DAGGER, bar, bar, plank, strip, strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_SWORD, bar, bar, bar, bar, bar, plank, strip, strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_WARAXE, bar, bar, bar, bar, plank, plank, strip, strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_MACE, bar, bar, bar, bar, plank, plank, strip, strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_SPEAR, bar, bar, bar, plank, plank, plank, plank, strip, strip, strip, strip);
		Salvage.addSalvage(MineFantasyItems.STANDARD_KATANA, bar, bar, bar, bar, bar, bar, plank, strip, strip, rivet);
		Salvage.addSalvage(MineFantasyItems.STANDARD_GREATSWORD, bar, bar, bar, bar, bar, bar, bar, plank, strip, strip, rivet, rivet);
		Salvage.addSalvage(MineFantasyItems.STANDARD_BATTLEAXE, bar, bar, bar, bar, bar, plank, plank, plank, strip, strip, strip, strip, rivet, rivet, rivet);
		Salvage.addSalvage(MineFantasyItems.STANDARD_WARHAMMER, bar, bar, bar, bar, bar, plank, plank, plank, strip, strip, strip, strip, rivet, rivet, rivet);
		Salvage.addSalvage(MineFantasyItems.STANDARD_HALBEARD, bar, bar, bar, bar, plank, plank, plank, plank, strip, strip, strip, strip, rivet, rivet);
		Salvage.addSalvage(MineFantasyItems.STANDARD_BOW, plank, plank, plank, plank, strip, Items.STRING, Items.STRING, Items.STRING);
		Salvage.addSalvage(MineFantasyItems.STANDARD_LANCE, bar, bar, bar, bar, bar, bar, bar, bar, rivet, rivet);

		Salvage.addSalvage(MineFantasyItems.STONE_PICK, new ItemStack(MineFantasyItems.SHARP_ROCK, 2), new ItemStack(Items.STICK, 2), MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_AXE, new ItemStack(MineFantasyItems.SHARP_ROCK, 2),				new ItemStack(Items.STICK, 2), MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_SPADE, MineFantasyItems.SHARP_ROCK, new ItemStack(Items.STICK, 2),				MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_HOE, MineFantasyItems.SHARP_ROCK, new ItemStack(Items.STICK, 2),				MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_SWORD, Items.STICK, new ItemStack(MineFantasyItems.SHARP_ROCK, 2),				MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_WARAXE, new ItemStack(MineFantasyItems.SHARP_ROCK, 2),				new ItemStack(Items.STICK, 2), new ItemStack(MineFantasyItems.VINE, 2));
		Salvage.addSalvage(MineFantasyItems.STONE_MACE, new ItemStack(MineFantasyItems.SHARP_ROCK, 2),				new ItemStack(Items.STICK, 2), MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_SPEAR, MineFantasyItems.SHARP_ROCK, new ItemStack(Items.STICK, 2),				MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_KNIFE, MineFantasyItems.SHARP_ROCK, Items.STICK, MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_HAMMER, MineFantasyItems.SHARP_ROCK, Items.STICK, MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.STONE_TONGS, MineFantasyItems.SHARP_ROCK, Items.STICK, MineFantasyItems.VINE);
		Salvage.addSalvage(MineFantasyItems.BONE_NEEDLE, Items.BONE);
		Salvage.addSalvage(MineFantasyItems.STONE_KNIFE, new ItemStack(Blocks.COBBLESTONE, 2),				MineFantasyItems.TIMBER.construct(Constants.SCRAP_WOOD_TAG));
		Salvage.addSalvage(MineFantasyItems.STONE_HAMMER, Blocks.COBBLESTONE, MineFantasyItems.TIMBER.construct(Constants.SCRAP_WOOD_TAG));
		Salvage.addSalvage(MineFantasyItems.STONE_TONGS, new ItemStack(Blocks.COBBLESTONE, 2),				MineFantasyItems.TIMBER.construct(Constants.SCRAP_WOOD_TAG));
		Salvage.addSalvage(MineFantasyItems.BONE_NEEDLE, Items.BONE);

		ItemStack blackPlate = ((ItemMetalComponent) MineFantasyItems.PLATE).createComponentItemStack("blackSteel");
		ItemStack steelPlate = ((ItemMetalComponent) MineFantasyItems.PLATE).createComponentItemStack("steel");

		Salvage.addSalvage(MineFantasyItems.BOMB_CASING_UNCOOKED, new ItemStack(Items.CLAY_BALL, 2));
		Salvage.addSalvage(MineFantasyItems.MINE_CASING_UNCOOKED, new ItemStack(Items.CLAY_BALL, 3), Blocks.STONE_PRESSURE_PLATE);
		Salvage.addSalvage(MineFantasyItems.BOMB_CASING_CRYSTAL, Items.GLASS_BOTTLE, MineFantasyItems.DIAMOND_SHARDS, new ItemStack(Items.REDSTONE, 2));
		Salvage.addSalvage(MineFantasyItems.MINE_CASING_CRYSTAL, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Items.GLASS_BOTTLE, MineFantasyItems.DIAMOND_SHARDS, new ItemStack(Items.REDSTONE, 2));
		Salvage.addSalvage(MineFantasyItems.BOMB_FUSE_LONG, new ItemStack(MineFantasyItems.BOMB_FUSE, 2), Items.REDSTONE);
		Salvage.addSalvage(MineFantasyBlocks.THATCH_STAIR, new ItemStack(Blocks.TALLGRASS, 3, 1));
		Salvage.addSalvage(MineFantasyBlocks.THATCH, new ItemStack(Blocks.TALLGRASS, 4, 1));
		Salvage.addSalvage(LeatherArmourListMFR.LEATHER_APRON, new ItemStack(Items.LEATHER, 3), Items.COAL);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 0), new ItemStack(MineFantasyItems.HIDE_SMALL, 2), Blocks.WOOL);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 1), MineFantasyItems.HIDE_LARGE, Blocks.WOOL);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 2), MineFantasyItems.HIDE_MEDIUM, Blocks.WOOL);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 3), MineFantasyItems.HIDE_SMALL, Blocks.WOOL);
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_ARMS_LIGHT, new ItemStack(MineFantasyItems.NAIL, 2), Items.STRING, MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG, 4));
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_ARMS_BASIC, new ItemStack(MineFantasyItems.NAIL, 3), MineFantasyItems.CROSSBOW_ARMS_LIGHT, MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG, 2));
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_ARMS_HEAVY, new ItemStack(MineFantasyItems.NAIL, 3), MineFantasyItems.CROSSBOW_ARMS_BASIC, MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG, 2));
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_ARMS_ADVANCED, MineFantasyItems.TUNGSTEN_GEARS, new ItemStack(MineFantasyItems.NAIL, 2), MineFantasyItems.CROSSBOW_ARMS_BASIC, new ItemStack(MineFantasyItems.STEEL_TUBE, 3));
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_SCOPE, MineFantasyItems.TUNGSTEN_GEARS, MineFantasyItems.SPYGLASS, new ItemStack(MineFantasyItems.BOLT, 2), MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG));
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_AMMO, MineFantasyItems.TUNGSTEN_GEARS, new ItemStack(MineFantasyItems.NAIL, 3), MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG, 7));
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_HANDLE_WOOD, new ItemStack(MineFantasyItems.NAIL, 2), MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG, 3));
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_STOCK_WOOD, new ItemStack(MineFantasyItems.NAIL, 3), MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG, 7));
		Salvage.addSalvage(MineFantasyItems.CROSSBOW_STOCK_IRON, new ItemStack(MineFantasyItems.TUNGSTEN_GEARS, 2), Blocks.OBSIDIAN, new ItemStack(MineFantasyItems.BOLT, 4), new ItemStack(MineFantasyItems.IRON_STRUT, 3), MineFantasyItems.CROSSBOW_STOCK_WOOD);
		Salvage.addSalvage(MineFantasyItems.COGWORK_SHAFT, new ItemStack(MineFantasyItems.IRON_STRUT, 2), new ItemStack(MineFantasyItems.BOLT, 4), MineFantasyItems.IRON_FRAME, Blocks.PISTON, MineFantasyItems.TUNGSTEN_GEARS);
		Salvage.addSalvage(MineFantasyBlocks.CRUCIBLE_AUTO, new ItemStack(MineFantasyItems.BOLT, 3), MineFantasyItems.TUNGSTEN_GEARS, MineFantasyBlocks.CRUCIBLE_FIRECLAY, steelPlate, steelPlate, steelPlate, steelPlate);
		Salvage.addSalvage(MineFantasyBlocks.BOMB_BENCH, new ItemStack(MineFantasyItems.BOLT, 4), MineFantasyItems.IRON_FRAME, MineFantasyBlocks.CARPENTER);
		Salvage.addSalvage(MineFantasyBlocks.CROSSBOW_BENCH, new ItemStack(MineFantasyItems.NAIL, 2), MineFantasyItems.TIMBER.construct(Constants.SCRAP_WOOD_TAG, 2), Items.STRING, MineFantasyBlocks.CARPENTER);
		Salvage.addSalvage(MineFantasyBlocks.BOMB_PRESS, new ItemStack(MineFantasyItems.IRON_STRUT, 2), new ItemStack(MineFantasyItems.BOLT, 2), new ItemStack(MineFantasyItems.BRONZE_GEARS, 2), Blocks.LEVER, MineFantasyItems.IRON_FRAME);
		Salvage.addSalvage(MineFantasyBlocks.TANNER_METAL, new ItemStack(MineFantasyItems.IRON_STRUT, 4), new ItemStack(MineFantasyItems.BOLT, 2), new ItemStack(MineFantasyItems.BRONZE_GEARS, 3), MineFantasyItems.STANDARD_NEEDLE, Blocks.LEVER, MineFantasyItems.IRON_FRAME);
		Salvage.addSalvage(MineFantasyBlocks.FORGE_METAL, new ItemStack(MineFantasyItems.BOLT, 4), blackPlate, blackPlate, blackPlate, blackPlate, new ItemStack(MineFantasyItems.IRON_FRAME, 2), new ItemStack(Blocks.REDSTONE_BLOCK, 2));
		Salvage.addSalvage(MineFantasyItems.SPYGLASS, new ItemStack(MineFantasyItems.BOLT, 2), new ItemStack(Blocks.GLASS, 2), MineFantasyItems.STEEL_TUBE, MineFantasyItems.BRONZE_GEARS);
		Salvage.addSalvage(MineFantasyItems.SYRINGE_EMPTY, Items.GLASS_BOTTLE, MineFantasyItems.STANDARD_NEEDLE, MineFantasyItems.STEEL_TUBE);
		Salvage.addSalvage(MineFantasyItems.PARACHUTE, new ItemStack(MineFantasyItems.THREAD, 3), new ItemStack(Blocks.WOOL, 3), new ItemStack(MineFantasyItems.LEATHER_STRIP, 4), Items.LEATHER);

		Salvage.addSalvage(MineFantasyBlocks.CRUCIBLE_STONE, new ItemStack(Blocks.STONE, 8));
		Salvage.addSalvage(MineFantasyBlocks.CRUCIBLE_FIRECLAY, new ItemStack(MineFantasyItems.FIRECLAY, 8), MineFantasyBlocks.CRUCIBLE_STONE);

		Salvage.addSalvage(MineFantasyBlocks.FORGE, new ItemStack(Blocks.STONE, 4), Items.COAL);
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_STONE, new ItemStack(Blocks.STONE, 6));

		Salvage.addSalvage(MineFantasyBlocks.CHIMNEY_STONE, Blocks.STONE);
		Salvage.addSalvage(MineFantasyBlocks.CHIMNEY_STONE_WIDE, MineFantasyBlocks.CHIMNEY_STONE, Blocks.STONE);
		Salvage.addSalvage(MineFantasyBlocks.CHIMNEY_STONE_EXTRACTOR, MineFantasyBlocks.CHIMNEY_STONE_WIDE);
		Salvage.addSalvage(MineFantasyBlocks.QUERN, new ItemStack(Items.FLINT, 2), new ItemStack(Blocks.STONE, 4));
		Salvage.addSalvage(MineFantasyBlocks.NAILED_PLANKS, MineFantasyItems.NAIL, MineFantasyItems.TIMBER.construct(Constants.SCRAP_WOOD_TAG, 4));
		Salvage.addSalvage(MineFantasyBlocks.REFINED_PLANKS, MineFantasyItems.NAIL, MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG, 4));
		Salvage.addSalvage(MineFantasyBlocks.NAILED_PLANKS_STAIR, MineFantasyItems.NAIL, MineFantasyItems.TIMBER.construct(Constants.SCRAP_WOOD_TAG, 3));
		Salvage.addSalvage(MineFantasyBlocks.REFINED_PLANKS_STAIR, MineFantasyItems.NAIL, MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG, 3));
		Salvage.addSalvage(MineFantasyBlocks.BELLOWS, new ItemStack(MineFantasyItems.NAIL, 3), MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG, 5), new ItemStack(Items.LEATHER, 2));
		Salvage.addSalvage(MineFantasyBlocks.TANNER_REFINED, MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG, 8), new ItemStack(MineFantasyItems.NAIL, 3));

		// TODO: move these two
		PaintOilRecipe.addRecipe(MineFantasyBlocks.NAILED_PLANKS, MineFantasyBlocks.REFINED_PLANKS);
		PaintOilRecipe.addRecipe(MineFantasyBlocks.NAILED_PLANKS_STAIR, MineFantasyBlocks.REFINED_PLANKS_STAIR);

		Salvage.addSalvage(MineFantasyItems.MAGMA_CREAM_REFINED, MineFantasyItems.DRAGON_HEART, Items.BLAZE_POWDER, Items.MAGMA_CREAM, MineFantasyItems.CLAY_POT);

		Salvage.addSalvage(MineFantasyBlocks.CARPENTER, MineFantasyItems.TIMBER.construct(Constants.SCRAP_WOOD_TAG, 4), Blocks.CRAFTING_TABLE);
		Salvage.addSalvage(MineFantasyBlocks.FRAMED_GLASS, MineFantasyItems.TIMBER.construct(Constants.SCRAP_WOOD_TAG, 2), Blocks.GLASS);
		Salvage.addSalvage(MineFantasyBlocks.WINDOW, MineFantasyItems.TIMBER.construct(Constants.SCRAP_WOOD_TAG, 4), Blocks.GLASS);
		Salvage.addSalvage(MineFantasyBlocks.CLAY_WALL, MineFantasyItems.NAIL, MineFantasyItems.TIMBER.construct(Constants.SCRAP_WOOD_TAG), Items.CLAY_BALL);
		Salvage.addSalvage(MineFantasyBlocks.TANNER, MineFantasyItems.TIMBER.construct(Constants.SCRAP_WOOD_TAG, 8));
		Salvage.addSalvage(MineFantasyBlocks.RESEARCH, MineFantasyBlocks.CARPENTER);
		Salvage.addSalvage(MineFantasyBlocks.SALVAGE_BASIC, Items.FLINT, new ItemStack(Blocks.STONE, 2), MineFantasyItems.TIMBER.construct(Constants.SCRAP_WOOD_TAG, 2), Blocks.CRAFTING_TABLE);

		ItemStack scrapWood = MineFantasyItems.TIMBER.construct(Constants.SCRAP_WOOD_TAG);

		Salvage.addSalvage(MineFantasyItems.DRY_ROCKS, Blocks.COBBLESTONE);
		Salvage.addSalvage(MineFantasyItems.TRAINING_SWORD, new ItemStack(Blocks.PLANKS, 5), new ItemStack(MineFantasyItems.NAIL, 2), scrapWood);
		Salvage.addSalvage(MineFantasyItems.TRAINING_WARAXE, new ItemStack(Blocks.PLANKS, 4), MineFantasyItems.NAIL, scrapWood, scrapWood);
		Salvage.addSalvage(MineFantasyItems.TRAINING_MACE, new ItemStack(Blocks.PLANKS, 4), MineFantasyItems.NAIL, scrapWood, scrapWood);
		Salvage.addSalvage(MineFantasyItems.TRAINING_WARAXE, Blocks.PLANKS, new ItemStack(MineFantasyItems.NAIL, 2), scrapWood, scrapWood, scrapWood);

		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 0), new ItemStack(MineFantasyItems.THREAD, 2), new ItemStack(MineFantasyItems.LEATHER_STRIP, 2), Items.LEATHER);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 1), new ItemStack(MineFantasyItems.THREAD, 4), new ItemStack(MineFantasyItems.LEATHER_STRIP, 2), new ItemStack(Items.LEATHER, 4));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 2), new ItemStack(MineFantasyItems.THREAD, 4), new ItemStack(MineFantasyItems.LEATHER_STRIP, 2), new ItemStack(Items.LEATHER, 3));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 3), new ItemStack(MineFantasyItems.THREAD, 4), new ItemStack(MineFantasyItems.LEATHER_STRIP, 2));

		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 0), LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 0), new ItemStack(MineFantasyItems.THREAD, 3), new ItemStack(Items.LEATHER, 2));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 1), LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 1), new ItemStack(MineFantasyItems.THREAD, 3), new ItemStack(Items.LEATHER, 2));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 2), LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 2), new ItemStack(MineFantasyItems.THREAD, 3), new ItemStack(Items.LEATHER, 2));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 3), LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 3), new ItemStack(MineFantasyItems.THREAD, 3), new ItemStack(Items.LEATHER, 2));

		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 0), LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 0), new ItemStack(MineFantasyItems.THREAD, 3), Blocks.WOOL);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 1), LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 1), new ItemStack(MineFantasyItems.THREAD, 3), Blocks.WOOL);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 2), LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 2), new ItemStack(MineFantasyItems.THREAD, 3), Blocks.WOOL);
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 3), LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 3), new ItemStack(MineFantasyItems.THREAD, 3), Blocks.WOOL);
		ItemStack bronzePlate = ((ItemMetalComponent) MineFantasyItems.PLATE).createComponentItemStack("bronze");

		Salvage.addSalvage(MineFantasyBlocks.REPAIR_BASIC, new ItemStack(MineFantasyItems.THREAD, 3), MineFantasyItems.NAIL, Items.FLINT, Items.LEATHER, new ItemStack(MineFantasyItems.LEATHER_STRIP, 2));
		Salvage.addSalvage(MineFantasyBlocks.REPAIR_ADVANCED, MineFantasyBlocks.REPAIR_BASIC, bronzePlate, new ItemStack(Items.SLIME_BALL, 3), new ItemStack(Items.STRING, 3));
		Salvage.addSalvage(MineFantasyBlocks.REPAIR_ORNATE, MineFantasyBlocks.REPAIR_ADVANCED, new ItemStack(Items.GOLD_INGOT, 4), Items.DIAMOND, new ItemStack(Items.DYE, 3, 4));
		Salvage.addSalvage(MineFantasyItems.STANDARD_MALLET, plank, plank, Items.STICK);
		Salvage.addSalvage(MineFantasyItems.STANDARD_SPOON, plank, Items.STICK);


		// TODO: fix salvage
		//        Salvage.addSalvage(CustomToolListMFR.STANDARD_SPOON, plank, Items.STICK);
		//        KnowledgeListMFR.malletR = MineFantasyReforgedAPI.addCarpenterToolRecipe(Skill.ARTISANRY, CustomToolListMFR.STANDARD_MALLET, "",
		//                basic, "hands", -1, 1 + (int) (2 * time), new Object[]{"WW", " S", 'W', plank, 'S', Items.STICK});
		{
			Item helm = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 0);
			Item chest = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 1);
			Item legs = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 2);
			Item boots = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 3);

			Item mail = MineFantasyItems.CHAIN_MESH;

			Salvage.addSalvage(MineFantasyItems.STANDARD_CHAIN_HELMET, helm, mail, mail, mail, mail, rivet, rivet, rivet, rivet);//4 Mail, 4 Rivet
			Salvage.addSalvage(MineFantasyItems.STANDARD_CHAIN_CHESTPLATE, chest, mail, mail, mail, mail, mail, mail, rivet, rivet, rivet, rivet, rivet, rivet);// 6 Mail 6 Rivet
			Salvage.addSalvage(MineFantasyItems.STANDARD_CHAIN_LEGGINGS, legs, mail, mail, mail, mail, rivet, rivet, rivet, rivet);// 4 Mail, 4 Rivet
			Salvage.addSalvage(MineFantasyItems.STANDARD_CHAIN_BOOTS, boots, mail, mail, rivet, rivet);// 2 Mail, 2 Rivet
		}
		{
			Item helm = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 0);
			Item chest = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 1);
			Item legs = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 2);
			Item boots = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 3);

			ItemStack mail = new ItemStack(MineFantasyItems.SCALE_MESH);

			Salvage.addSalvage(MineFantasyItems.STANDARD_SCALE_HELMET, helm, mail, mail, mail, mail, rivet, rivet, rivet, rivet);//4 Mail, 4 Rivet
			Salvage.addSalvage(MineFantasyItems.STANDARD_SCALE_CHESTPLATE, chest, mail, mail, mail, mail, mail, mail, rivet, rivet, rivet, rivet, rivet, rivet);//6 Mail 6 Rivet
			Salvage.addSalvage(MineFantasyItems.STANDARD_SCALE_LEGGINGS, legs, mail, mail, mail, mail, rivet, rivet, rivet, rivet);//4 Mail, 4 Rivet
			Salvage.addSalvage(MineFantasyItems.STANDARD_SCALE_BOOTS, boots, mail, mail, rivet, rivet);//2 Mail, 2 Rivet
		}
		{
			Item helm = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 0);
			Item chest = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 1);
			Item legs = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 2);
			Item boots = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 3);

			ItemStack mail = new ItemStack(MineFantasyItems.SPLINT_MESH);

			Salvage.addSalvage(MineFantasyItems.STANDARD_SPLINT_HELMET, helm, mail, mail, mail, mail, rivet, rivet, rivet, rivet);//4 Mail, 4 Rivet
			Salvage.addSalvage(MineFantasyItems.STANDARD_SPLINT_CHESTPLATE, chest, mail, mail, mail, mail, mail, mail, rivet, rivet, rivet, rivet, rivet, rivet);//6 Mail, 6 Rivet
			Salvage.addSalvage(MineFantasyItems.STANDARD_SPLINT_LEGGINGS, legs, mail, mail, mail, mail, rivet, rivet, rivet, rivet);//4 Mail, 4 Rivet
			Salvage.addSalvage(MineFantasyItems.STANDARD_SPLINT_BOOTS, boots, mail, mail, rivet, rivet);//2 Mail 2 Rivet
		}
		{
			Item helm = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 0);
			Item chest = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 1);
			Item legs = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 2);
			Item boots = LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 3);

			ItemStack plate1 = new ItemStack(MineFantasyItems.PLATE);

			Salvage.addSalvage(MineFantasyItems.STANDARD_PLATE_HELMET, helm, plate1, plate1, rivet, rivet);//2 plate1, Rivet
			Salvage.addSalvage(MineFantasyItems.STANDARD_PLATE_CHESTPLATE, chest, plate1, plate1, plate1, plate1, rivet, rivet, rivet, rivet);//4 plate1, 4 Rivet
			Salvage.addSalvage(MineFantasyItems.STANDARD_PLATE_LEGGINGS, legs, plate1, plate1, plate1, plate1, rivet, rivet, rivet, rivet);//4 plate1, 4 Rivet
			Salvage.addSalvage(MineFantasyItems.STANDARD_PLATE_BOOTS, boots, plate1, plate1, rivet, rivet);//2 Plate, 2 Rivet
		}
		{
			ItemStack minorPiece = new ItemStack(MineFantasyItems.PLATE);
			ItemStack majorPiece = new ItemStack(MineFantasyItems.PLATE_HUGE);

			Salvage.addSalvage(majorPiece, minorPiece, minorPiece, new ItemStack(MineFantasyItems.RIVET, 4));
			Salvage.addSalvage(MineFantasyItems.COGWORK_ARMOUR, minorPiece, minorPiece, minorPiece, minorPiece, minorPiece, minorPiece, majorPiece, majorPiece, majorPiece, majorPiece, majorPiece, majorPiece);
		}

		Salvage.addSalvage(MineFantasyItems.CHAIN_MESH, hunk);
		Salvage.addSalvage(MineFantasyItems.SCALE_MESH, hunk);
		Salvage.addSalvage(MineFantasyItems.SPLINT_MESH, hunk, MineFantasyItems.RIVET, MineFantasyItems.RIVET);
		Salvage.addSalvage(MineFantasyItems.PLATE, bar, bar);


		ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
		for (CustomMaterial material : wood) {
			ItemStack timber = MineFantasyItems.TIMBER.construct(material.name);
			ItemStack cutPlank = ((ItemComponentMFR) MineFantasyItems.TIMBER_CUT).construct(material.name);
			ItemStack woodpane = ((ItemComponentMFR) MineFantasyItems.TIMBER_PANE).construct(material.name);

			ItemStack result;

			result = ((BlockWoodDecor) MineFantasyBlocks.TOOL_RACK_WOOD).construct(material.name);
			Salvage.addSalvage(result, cutPlank, cutPlank, cutPlank, timber, timber, timber, timber, new ItemStack(MineFantasyItems.NAIL, 2));

			result = MineFantasyBlocks.FOOD_BOX_BASIC.construct(material.name);
			Salvage.addSalvage(result, cutPlank, cutPlank, cutPlank, cutPlank, new ItemStack(MineFantasyItems.NAIL, 2), MineFantasyItems.HINGE);

			result = MineFantasyBlocks.AMMO_BOX_BASIC.construct(material.name);
			Salvage.addSalvage(result, cutPlank, cutPlank, woodpane, woodpane, new ItemStack(MineFantasyItems.NAIL, 2), new ItemStack(MineFantasyItems.HINGE, 2));

			result = MineFantasyBlocks.CRATE_BASIC.construct(material.name);
			Salvage.addSalvage(result, woodpane, woodpane, woodpane, woodpane, woodpane, woodpane, new ItemStack(MineFantasyItems.NAIL, 4), new ItemStack(MineFantasyItems.HINGE, 2));

			result = ((BlockWoodDecor) MineFantasyBlocks.TROUGH_WOOD).construct(material.name);
			Salvage.addSalvage(result, timber, timber, timber, timber, timber, new ItemStack(MineFantasyItems.NAIL, 3));
		}
	}
}
