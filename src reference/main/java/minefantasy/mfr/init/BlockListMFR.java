package minefantasy.mfr.init;

import minefantasy.mfr.block.basic.*;
import minefantasy.mfr.block.crafting.*;
import minefantasy.mfr.block.decor.*;
import minefantasy.mfr.block.food.BlockBerryBush;
import minefantasy.mfr.block.food.BlockCakeMF;
import minefantasy.mfr.block.food.BlockPie;
import minefantasy.mfr.block.refining.*;
import minefantasy.mfr.block.tree.BlockLeavesMF;
import minefantasy.mfr.block.tree.BlockLogMF;
import minefantasy.mfr.block.tree.BlockSaplingMF;
import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BlockListMFR {



	public static final String[] METAL_BLOCKS = new String[]{"copper", "tin", "silver", "bronze", "pigiron", "steel", "blacksteel", "redsteel", "bluesteel", "adamantium", "mithril", "ignotumite", "mithium", "ender"};
	public static final String[] SPECIAL_METAL_BLOCKS = new String[]{"bronze", "iron", "steel", "blacksteel", "redsteel", "bluesteel"};
	public static final String[] ANVILS = new String[]{"bronze", "iron", "steel", "blacksteel", "bluesteel", "redsteel"};

	public static Block ORE_COPPER = new BlockOreMF("oreCopper", 0, -1).setHardness(2.0F).setResistance(3.0F);
	public static Block ORE_TIN = new BlockOreMF("oreTin", 0).setHardness(2.5F).setResistance(4.0F);
	public static Block ORE_SILVER = new BlockOreMF("oreSilver", 2).setHardness(3.0F).setResistance(5.0F);
	public static Block ORE_MYTHIC = new BlockMythicOre("oreMythic", false).setHardness(10.0F).setResistance(100.0F);

	public static Block ORE_KAOLINITE = new BlockOreMF("oreKaolinite", 1, 0, ComponentListMFR.kaolinite, 1, 1, 1).setHardness(3.0F).setResistance(5.0F);
	public static Block ORE_NITRE = new BlockOreMF("oreNitre", 2, 0, ComponentListMFR.nitre, 1, 2, 1).setHardness(3.0F).setResistance(5.0F);
	public static Block ORE_SULFUR = new BlockOreMF("oreSulfur", 2, 0, ComponentListMFR.sulfur, 1, 4, 2).setHardness(3.0F).setResistance(2.0F);
	public static Block ORE_BORAX = new BlockOreMF("oreBorax", 2, 1, ComponentListMFR.flux_strong, 1, 8, 4).setHardness(3.0F).setResistance(2.0F);
	public static Block ORE_TUNGSTEN = new BlockOreMF("oreTungsten", 3, 1, ComponentListMFR.oreTungsten, 1, 1, 4).setHardness(4.0F).setResistance(2.5F);
	public static Block ORE_CLAY = new BlockOreMF("oreClay", 0, 0, Items.CLAY_BALL, 1, 4, 1, Material.GROUND).setBlockSoundType(SoundType.GROUND).setHardness(0.5F);
	public static Block ORE_COAL_RICH = new BlockOreMF("oreCoalRich", 2, 1, Items.COAL, 2, 6, 2).setHardness(5.0F).setResistance(10.0F);

	public static Block MUD_BRICK = new BasicBlockMF("mud_brick", Material.GROUND).setHardness(1.0F).setResistance(0.5F);
	public static Block MUD_PAVEMENT = new BasicBlockMF("mud_pavement", Material.GROUND).setHardness(0.5F);

	public static Block COBBLE_BRICK = new BasicBlockMF("cobble_brick", Material.ROCK).setBlockSoundType(SoundType.STONE).setHardness(2.5F).setResistance(12.0F);
	public static Block COBBLE_PAVEMENT = new BasicBlockMF("cobble_pavement", Material.ROCK).setBlockSoundType(SoundType.STONE).setHardness(2.0F).setResistance(10.0F);

	public static Block WINDOW = new BasicBlockMF("window", Material.GLASS).setBlockSoundType(SoundType.GLASS).setHardness(0.9F).setResistance(0.1F);
	public static Block FRAMED_GLASS = new BasicBlockMF("framed_glass", Material.GLASS).setBlockSoundType(SoundType.GLASS).setHardness(0.6F).setResistance(0.2F);
	public static Block FRAMED_PANE = new BlockPaneMF("framed_pane", Material.GLASS, true).setBlockSoundType(SoundType.GLASS).setHardness(0.6F).setResistance(0.1F);
	public static Block WINDOW_PANE = new BlockPaneMF("window_pane",  Material.GLASS,true).setBlockSoundType(SoundType.GLASS).setHardness(0.9F).setResistance(0.2F);

	public static Block THATCH = new BasicBlockMF("thatch", Material.LEAVES).setBlockSoundType(SoundType.GROUND).setHardness(1.0F);
	public static Block THATCH_STAIR = new ConstructionBlockMF.StairsConstBlock("thatch_stair", THATCH).register("thatch_stair");

	// public static Block limestone_cobblestone = new
	// BasicBlockMF("limestone_cobblestone",
	// Material.rock).setHardness(0.8F).setResistance(4.0F).setStepSound(Block.soundTypePiston);
	// public static Block limestone = new BasicBlockMF("limestone", Material.rock,
	// limestone_cobblestone).setHardness(1.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone);

	public static Block LIMESTONE = new ConstructionBlockMF("limestone").setHardness(1.2F).setResistance(8F);

	public static Block FIREBRICKS = new BasicBlockMF("firebricks", Material.ROCK).setBlockSoundType(SoundType.STONE).setHardness(5.0F).setResistance(15.0F);
	public static Block CLAY_WALL = new BasicBlockMF("clayWall", Material.WOOD).setBlockSoundType(SoundType.WOOD).setHardness(1.0F).setResistance(1.0F);

	public static BlockMetalBarsMF[] BARS = new BlockMetalBarsMF[SPECIAL_METAL_BLOCKS.length];
	public static BlockMetalMF[] STORAGE = new BlockMetalMF[METAL_BLOCKS.length];
	public static BlockAnvilMF ANVIL_STONE;
	public static BlockAnvilMF[] ANVIL = new BlockAnvilMF[ANVILS.length];
	public static BlockCarpenter CARPENTER = new BlockCarpenter();
	public static BlockBombBench BOMB_BENCH = new BlockBombBench();
	public static BlockCrossbowBench CROSSBOW_BENCH = new BlockCrossbowBench();

	public static Block CHEESE_WHEEL = new BlockCakeMF("cheese", FoodListMFR.cheese_slice).setCheese();

	public static Block CAKE_VANILLA = new BlockCakeMF("cake_vanilla", FoodListMFR.cake_slice);
	public static Block CAKE_CARROT = new BlockCakeMF("cake_carrot", FoodListMFR.carrotcake_slice);
	public static Block CAKE_CHOCOLATE = new BlockCakeMF("cake_chocolate", FoodListMFR.choccake_slice);
	public static Block CAKE_BF = new BlockCakeMF("cake_bf", FoodListMFR.bfcake_slice);

	public static Block PIE_MEAT = new BlockPie("pie_meat", FoodListMFR.meatpie_slice);

	public static Block PIE_APPLE = new BlockPie("pie_apple", FoodListMFR.pieslice_apple);
	public static Block PIE_BERRY = new BlockPie("pie_berry", FoodListMFR.pieslice_berry);

	public static Block PIE_SHEPARDS = new BlockPie("pie_shepards", FoodListMFR.pieslice_shepards);

	public static Block BERRY_BUSH = new BlockBerryBush("berries");
	public static Block BLAST_CHAMBER = new BlockBFC();
	public static Block BLAST_HEATER = new BlockBFH(false);
	public static Block BLAST_HEATER_ACTIVE = new BlockBFH(true).setLightLevel(10F);

	public static Block CRUCIBLE = new BlockCrucible("stone", 0, false);
	public static Block CRUCIBLE_ACTIVE = new BlockCrucible("stone", 0, true).setLightLevel(12F);
	public static Block CRUCIBLE_ADV = new BlockCrucible("fireclay", 1, false);
	public static Block CRUCIBLE_ADV_ACTIVE = new BlockCrucible("fireclay", 1, true).setLightLevel(12F);
	public static Block CRUCIBLE_AUTO = new BlockCrucible("auto", 1, false).setAuto().setHardness(12F);
	public static Block CRUCIBLE_AUTO_ACTIVE = new BlockCrucible("auto", 1, true).setAuto().setHardness(12F).setLightLevel(12F);

	public static Block CHIMNEY_STONE = new BlockChimney("stone", false, false, 5);
	public static Block CHIMNEY_STONE_WIDE = new BlockChimney("stone", true, false, 10);
	public static Block CHIMNEY_STONE_EXTRACTOR = new BlockChimney("stone_extractor", true, true, 15);
	public static Block CHIMNEY_PIPE = new BlockChimney("pipe", false, false, 10).setPipe();

	public static Block TANNER = new BlockTanningRack(0, "");

	public static Block FORGE = new BlockForge("stone", 0, false);
	public static Block FORGE_ACTIVE = new BlockForge("stone", 0, true);
	public static Block FORGE_METAL = new BlockForge("metal", 1, false);
	public static Block FORGE_METAL_ACTIVE = new BlockForge("metal", 1, true);

	public static Block REPAIR_BASIC = new BlockRepairKit("basic", 0.25F, 0.05F, 0.2F);
	public static Block REPAIR_ADVANCED = new BlockRepairKit("advanced", 1.0F, 0.2F, 0.05F);
	public static Block REPAIR_ORNATE = new BlockRepairKit("ornate", 1.0F, 0.25F, 0.03F).setOrnate(0.5F);

	public static Block BELLOWS = new BlockBellows();

	public static Block REFINED_PLANKS = new BasicBlockMF("refined_planks", Material.WOOD).setBlockSoundType(SoundType.WOOD).setHardness(2.5F).setResistance(10F);
	public static Block NAILED_PLANKS = new BasicBlockMF("nailed_planks", Material.WOOD).setBlockSoundType(SoundType.WOOD).setHardness(1.5F).setResistance(7F);
	public static Block REFINED_PLANKS_STAIR = new ConstructionBlockMF.StairsConstBlock("refined_planks_stair", REFINED_PLANKS).register("refined_planks_stair");
	public static Block NAILED_PLANKS_STAIR = new ConstructionBlockMF.StairsConstBlock("nailed_planks_stair", NAILED_PLANKS).register("nailed_planks_stair");

	public static Block REINFORCED_STONE = new BlockReinforcedStone("reinforced_stone", "base", "engraved", "dshall_0", "dshall_1", "dshall_2").setBlockSoundType(SoundType.STONE).setHardness(2.0F).setResistance(15F);
	public static Block REINFORCED_STONE_BRICKS = new BlockReinforcedStone("reinforced_stone_bricks", "base", "mossy", "cracked").setBlockSoundType(SoundType.STONE).setHardness(2.0F).setResistance(15F);
	public static Block REINFORCED_STONE_FRAMED = new BasicBlockMF("reinforced_stone_framed", Material.ROCK).setBlockSoundType(SoundType.STONE).setHardness(2.5F).setResistance(20F);
	public static Block REINFORCED_STONE_FRAMED_IRON = new BasicBlockMF("reinforced_stone_framed_iron", Material.ROCK).setBlockSoundType(SoundType.STONE).setHardness(2.5F).setResistance(20F);

	public static Block ADV_TANNER = new BlockTanningRack(1, "Strong");
	public static Block RESEARCH = new BlockResearchStation();
	public static Block TROUGH_WOOD = new BlockTrough("trough_wood");
	public static Block ENG_TANNER = new BlockEngineerTanner(2, "Metal");

	public static Block BOMB_PRESS = new BlockBombPress();

	public static Block ROAD = new BlockRoad("road_mf", 14F);
	public static Block LOW_ROAD = new BlockRoad("road_mf_short", 7F);

	public static Block SALVAGE_BASIC = new BlockSalvage("basic", 1.0F);
	public static Block BLOOMERY = new BlockBloomery();
	public static Block LOG_YEW = new BlockLogMF("yew").setHardness(2F).setResistance(6F);
	public static Block LOG_IRONBARK = new BlockLogMF("ironbark").setHardness(3F).setResistance(8F);
	public static Block LOG_EBONY = new BlockLogMF("ebony").setHardness(5F).setResistance(10F);

	public static Block LEAVES_YEW = new BlockLeavesMF("yew", 100);
	public static Block LEAVES_IRONBARK = new BlockLeavesMF("ironbark", 40).setHardness(0.3F);
	public static Block LEAVES_EBONY = new BlockLeavesMF("ebony", 1000).setHardness(0.5F);

	public static Block SAPLING_YEW = new BlockSaplingMF("yew", LOG_YEW, LEAVES_YEW, 4F);
	public static Block SAPLING_IRONBARK = new BlockSaplingMF("ironbark", LOG_IRONBARK, LEAVES_IRONBARK, 8F);
	public static Block SAPLING_EBONY = new BlockSaplingMF("ebony", LOG_EBONY, LEAVES_EBONY, 10F);

	public static Block YEW_PLANKS = new BasicBlockMF("yew_planks", Material.WOOD).setBlockSoundType(SoundType.WOOD).setHardness(3F).setResistance(6F);
	public static Block IRONBARK_PLANKS = new BasicBlockMF("ironbark_planks", Material.WOOD).setBlockSoundType(SoundType.WOOD).setHardness(4F).setResistance(10F);
	public static Block EBONY_PLANKS = new BasicBlockMF("ebony_planks", Material.WOOD).setBlockSoundType(SoundType.WOOD).setHardness(6F).setResistance(12F);

	public static Block QUERN = new BlockQuern("quern");

	public static Block MUD_BRICK_STAIR = new ConstructionBlockMF.StairsConstBlock("mud_brick_stair", MUD_BRICK).register("mud_brick_stair");
	public static Block MUD_PAVEMENT_STAIR = new ConstructionBlockMF.StairsConstBlock("mud_pavement_stair", MUD_PAVEMENT).register("mud_pavement_stair");
	public static Block COBBLE_BRICK_STAIR = new ConstructionBlockMF.StairsConstBlock("cobble_brick_stair", COBBLE_BRICK).register("cobble_brick_stair");
	public static Block COBBLE_PAVEMENT_STAIR = new ConstructionBlockMF.StairsConstBlock("cobble_pavement_stair", COBBLE_PAVEMENT).register("cobble_pavement_stair");
	public static Block FIREBRICK_STAIR = new ConstructionBlockMF.StairsConstBlock("firebrick_stair", FIREBRICKS).register("firebrick_stair");
	public static Block REINFORCED_STONE_STAIR = new ConstructionBlockMF.StairsConstBlock("reinforced_stone_stair", REINFORCED_STONE).register("reinforced_stone_stair");
	public static Block REINFORCED_STONE_BRICK_STAIR = new ConstructionBlockMF.StairsConstBlock("reinforced_stone_brick_stair", REINFORCED_STONE_BRICKS).register("reinforced_stone_brick_stair");

	public static Block YEW_STAIR = new ConstructionBlockMF.StairsConstBlock("yew_stair", YEW_PLANKS).register("yew_stair");
	public static Block IRONBARK_STAIR = new ConstructionBlockMF.StairsConstBlock("ironbark_stair", IRONBARK_PLANKS).register("ironbark_stair");
	public static Block EBONY_STAIR = new ConstructionBlockMF.StairsConstBlock("ebony_stair", EBONY_PLANKS).register("ebony_stair");

	public static Block FIREPIT = new BlockFirepit();
	public static Block ROAST = new BlockRoast(0, "basic", false);
	public static Block OVEN_STONE = new BlockRoast(0, "basic", true);

	public static Block FURNACE_HEATER = new BlockBigFurnace("furnace_heater", true, -1);
	public static Block FURNACE_STONE = new BlockBigFurnace("furnace_stone", false, 0);

	public static Block RACK_WOOD = new BlockRack("rack_wood");
	public static Block FOOD_BOX_BASIC = new BlockAmmoBox("food_box_basic", (byte) 0);
	public static Block AMMO_BOX_BASIC = new BlockAmmoBox("ammo_box_basic", (byte) 1);
	public static Block CRATE_BASIC = new BlockAmmoBox("crate_basic", (byte) 2);

	public static Block COGWORK_HELM = new BlockCogwork("cogwork_helm", false);
	public static Block COGWORK_LEGS = new BlockCogwork("cogwork_legs", false);
	public static Block COGWORK_CHEST = new BlockCogwork("cogwork_chest", true);
	public static Block FRAME_BLOCK = new BlockFrame("frame_block");
	public static Block COGWORK_BUILDER = new BlockFrame("cogwork_builder", FRAME_BLOCK).setCogworkHolder();

	public static Block CRUCIBLE_MYTHIC = new BlockCrucible("mythic", 2, false).setAuto().setBlockUnbreakable();
	public static Block CRUCIBLE_MYTHIC_ACTIVE = new BlockCrucible("mythic", 2, true).setAuto().setBlockUnbreakable().setLightLevel(12F);
	public static Block CRUCIBLE_MASTER = new BlockCrucible("master", 3, false).setAuto().setBlockUnbreakable();
	public static Block CRUCIBLE_MASTER_ACTIVE = new BlockCrucible("master", 3, true).setAuto().setBlockUnbreakable().setLightLevel(12F);

	public static Block MYTHIC_DECOR = new BlockMythicDecor();
	public static Block WG_MARK = new BlockWorldGenMarker();
	public static Block COMPONENTS = new BlockComponent();
	public static Block SCHEMATIC_GENERAL = new BlockSchematic("schematic_general");

	public static void load() {
		ANVIL_STONE = new BlockAnvilMF(BaseMaterialMFR.getMaterial("stone"));
		// 5:20 default planks
		Blocks.FIRE.setFireInfo(REFINED_PLANKS, 3, 10);
		Blocks.FIRE.setFireInfo(LOG_IRONBARK, 2, 8);// IRONBARK: Logs are resistant to fire but raw planks are not
		Blocks.FIRE.setFireInfo(IRONBARK_PLANKS, 5, 30);
		Blocks.FIRE.setFireInfo(LOG_EBONY, 3, 10);// EBONY: Resistant
		Blocks.FIRE.setFireInfo(EBONY_PLANKS, 3, 10);

		for (int a = 0; a < SPECIAL_METAL_BLOCKS.length; a++) {
			BaseMaterialMFR material = BaseMaterialMFR.getMaterial(SPECIAL_METAL_BLOCKS[a]);
			if (material != null) {
				BARS[a] = new BlockMetalBarsMF(material);
			}
		}
		for (int a = 0; a < METAL_BLOCKS.length; a++) {
			BaseMaterialMFR material = BaseMaterialMFR.getMaterial(METAL_BLOCKS[a]);
			if (material != null) {
				STORAGE[a] = new BlockMetalMF(material);
			}
		}
		for (int a = 0; a < ANVILS.length; a++) {
			BaseMaterialMFR material = BaseMaterialMFR.getMaterial(ANVILS[a]);
			if (material != null) {
				ANVIL[a] = new BlockAnvilMF(material);
			}
		}

		OreDictionary.registerOre("cobblestone", new ItemStack(LIMESTONE, 1, 1));
		OreDictionary.registerOre("stone", new ItemStack(LIMESTONE, 1, 0));
		OreDictionary.registerOre("limestone", new ItemStack(LIMESTONE, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("cobblestone", new ItemStack(COBBLE_BRICK, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("cobblestone", new ItemStack(COBBLE_PAVEMENT, 1, OreDictionary.WILDCARD_VALUE));

		OreDictionary.registerOre("blockGlass", new ItemStack(WINDOW, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockGlass", new ItemStack(FRAMED_GLASS, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("paneGlass", new ItemStack(WINDOW_PANE, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("paneGlass", new ItemStack(FRAMED_PANE, 1, OreDictionary.WILDCARD_VALUE));

		OreDictionary.registerOre("planksOakWood", new ItemStack(Blocks.PLANKS, 1, 0));
		OreDictionary.registerOre("planksSpruceWood", new ItemStack(Blocks.PLANKS, 1, 1));
		OreDictionary.registerOre("planksBirchWood", new ItemStack(Blocks.PLANKS, 1, 2));
		OreDictionary.registerOre("planksJungleWood", new ItemStack(Blocks.PLANKS, 1, 3));
		OreDictionary.registerOre("planksAcaciaWood", new ItemStack(Blocks.PLANKS, 1, 4));
		OreDictionary.registerOre("planksDarkOakWood", new ItemStack(Blocks.PLANKS, 1, 5));

		OreDictionary.registerOre("planksIronbarkWood", IRONBARK_PLANKS);
		OreDictionary.registerOre("planksEbonyWood", EBONY_PLANKS);
		OreDictionary.registerOre("planksYewWood", YEW_PLANKS);
		OreDictionary.registerOre("planksIronbarkWood", IRONBARK_PLANKS);
		OreDictionary.registerOre("planksEbonyWood", EBONY_PLANKS);
		OreDictionary.registerOre("planksYewWood", YEW_PLANKS);

		for (ItemStack plank : OreDictionary.getOres("plankWood")) {
			if (plank.getItem().getClass().getName().contains("BlockWoodenDevice")) {
				if (plank.getUnlocalizedName().equalsIgnoreCase("tile.blockWoodenDevice.6")) {
					OreDictionary.registerOre("planksGreatwoodWood", plank);
				}
				if (plank.getUnlocalizedName().equalsIgnoreCase("tile.blockWoodenDevice.7")) {
					OreDictionary.registerOre("planksSilverwoodWood", plank);
				}
			}
		}
	}
}


