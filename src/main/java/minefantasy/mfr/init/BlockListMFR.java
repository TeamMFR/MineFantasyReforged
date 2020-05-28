package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.block.basic.BasicBlockMF;
import minefantasy.mfr.block.basic.BlockMetalBarsMF;
import minefantasy.mfr.block.basic.BlockMetalMF;
import minefantasy.mfr.block.basic.BlockMythicDecor;
import minefantasy.mfr.block.basic.BlockMythicOre;
import minefantasy.mfr.block.basic.BlockOreMF;
import minefantasy.mfr.block.basic.BlockPaneMF;
import minefantasy.mfr.block.basic.BlockReinforcedStone;
import minefantasy.mfr.block.basic.BlockWorldGenMarker;
import minefantasy.mfr.block.basic.ConstructionBlockMF;
import minefantasy.mfr.block.crafting.BlockAnvilMF;
import minefantasy.mfr.block.crafting.BlockBombBench;
import minefantasy.mfr.block.crafting.BlockBombPress;
import minefantasy.mfr.block.crafting.BlockCarpenter;
import minefantasy.mfr.block.crafting.BlockCogwork;
import minefantasy.mfr.block.crafting.BlockCrossbowBench;
import minefantasy.mfr.block.crafting.BlockEngineerTanner;
import minefantasy.mfr.block.crafting.BlockFirepit;
import minefantasy.mfr.block.crafting.BlockFrame;
import minefantasy.mfr.block.crafting.BlockRepairKit;
import minefantasy.mfr.block.crafting.BlockResearchStation;
import minefantasy.mfr.block.crafting.BlockRoast;
import minefantasy.mfr.block.crafting.BlockSalvage;
import minefantasy.mfr.block.crafting.BlockTanningRack;
import minefantasy.mfr.block.decor.BlockAmmoBox;
import minefantasy.mfr.block.decor.BlockComponent;
import minefantasy.mfr.block.decor.BlockRack;
import minefantasy.mfr.block.decor.BlockRoad;
import minefantasy.mfr.block.decor.BlockSchematic;
import minefantasy.mfr.block.decor.BlockTrough;
import minefantasy.mfr.block.food.BlockBerryBush;
import minefantasy.mfr.block.food.BlockCakeMF;
import minefantasy.mfr.block.food.BlockPie;
import minefantasy.mfr.block.refining.BlockBFC;
import minefantasy.mfr.block.refining.BlockBFH;
import minefantasy.mfr.block.refining.BlockBellows;
import minefantasy.mfr.block.refining.BlockBigFurnace;
import minefantasy.mfr.block.refining.BlockBloomery;
import minefantasy.mfr.block.refining.BlockChimney;
import minefantasy.mfr.block.refining.BlockCrucible;
import minefantasy.mfr.block.refining.BlockForge;
import minefantasy.mfr.block.refining.BlockQuern;
import minefantasy.mfr.block.tile.TileEntityAnvilMFR;
import minefantasy.mfr.block.tile.TileEntityBellows;
import minefantasy.mfr.block.tile.TileEntityBerryBush;
import minefantasy.mfr.block.tile.TileEntityBigFurnace;
import minefantasy.mfr.block.tile.TileEntityBloomery;
import minefantasy.mfr.block.tile.TileEntityBombBench;
import minefantasy.mfr.block.tile.TileEntityBombPress;
import minefantasy.mfr.block.tile.TileEntityCarpenterMFR;
import minefantasy.mfr.block.tile.TileEntityChimney;
import minefantasy.mfr.block.tile.TileEntityComponent;
import minefantasy.mfr.block.tile.TileEntityCrossbowBench;
import minefantasy.mfr.block.tile.TileEntityCrucible;
import minefantasy.mfr.block.tile.TileEntityFirepit;
import minefantasy.mfr.block.tile.TileEntityForge;
import minefantasy.mfr.block.tile.TileEntityQuern;
import minefantasy.mfr.block.tile.TileEntityResearch;
import minefantasy.mfr.block.tile.TileEntityRoad;
import minefantasy.mfr.block.tile.TileEntityRoast;
import minefantasy.mfr.block.tile.TileEntityTanningRack;
import minefantasy.mfr.block.tile.TileEntityWorldGenMarker;
import minefantasy.mfr.block.tile.blastfurnace.TileEntityBlastFC;
import minefantasy.mfr.block.tile.blastfurnace.TileEntityBlastFH;
import minefantasy.mfr.block.tile.decor.TileEntityAmmoBox;
import minefantasy.mfr.block.tile.decor.TileEntityRack;
import minefantasy.mfr.block.tile.decor.TileEntityTrough;
import minefantasy.mfr.block.tree.BlockLeavesMF;
import minefantasy.mfr.block.tree.BlockLogMF;
import minefantasy.mfr.block.tree.BlockSaplingMF;
import minefantasy.mfr.itemblock.ItemBlockAmmoBox;
import minefantasy.mfr.itemblock.ItemBlockAnvilMF;
import minefantasy.mfr.itemblock.ItemBlockBase;
import minefantasy.mfr.itemblock.ItemBlockBerries;
import minefantasy.mfr.itemblock.ItemBlockCake;
import minefantasy.mfr.itemblock.ItemBlockOreMF;
import minefantasy.mfr.itemblock.ItemBlockRepairKit;
import minefantasy.mfr.itemblock.ItemBlockSalvage;
import minefantasy.mfr.itemblock.ItemBlockToolRack;
import minefantasy.mfr.itemblock.ItemBlockTrough;
import minefantasy.mfr.material.BaseMaterialMFR;
import minefantasy.mfr.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid=MineFantasyReborn.MOD_ID)
public class BlockListMFR {

	public static Block COPPER_ORE = Utils.nullValue();
	public static Block TIN_ORE = Utils.nullValue();
	public static Block SILVER_ORE = Utils.nullValue();
	public static Block MYTHIC_ORE = Utils.nullValue();

	public static Block KAOLINITE_ORE = Utils.nullValue();
	public static Block NITRE_ORE = Utils.nullValue();
	public static Block SULFUR_ORE = Utils.nullValue();
	public static Block BORAX_ORE = Utils.nullValue();
	public static Block TUNGSTEN_ORE = Utils.nullValue();
	public static Block CLAY_ORE = Utils.nullValue();
	public static Block COAL_RICH_ORE = Utils.nullValue();

	public static Block MUD_BRICK = Utils.nullValue();
	public static Block MUD_PAVEMENT = Utils.nullValue();

	public static Block COBBLE_BRICK = Utils.nullValue();
	public static Block COBBLE_PAVEMENT = Utils.nullValue();

	public static Block WINDOW = Utils.nullValue();
	public static Block FRAMED_GLASS = Utils.nullValue();
	public static Block FRAMED_GLASS_PANE = Utils.nullValue();
	public static Block WINDOW_PANE = Utils.nullValue();

	public static Block THATCH = Utils.nullValue();
	public static Block THATCH_STAIR = Utils.nullValue();

	public static Block LIMESTONE = Utils.nullValue();
	public static Block LIMESTONE_COBBLE = Utils.nullValue();
	public static Block LIMESTONE_BRICK = Utils.nullValue();
	public static Block LIMESTONE_PAVEMENT = Utils.nullValue();
	public static Block LIMESTONE_STAIRS = Utils.nullValue();
	public static Block LIMESTONE_COBBLE_STAIRS = Utils.nullValue();
	public static Block LIMESTONE_BRICK_STAIRS = Utils.nullValue();
	public static Block LIMESTONE_PAVEMENT_STAIRS = Utils.nullValue();

	public static Block FIREBRICKS = Utils.nullValue();
	public static Block CLAY_WALL = Utils.nullValue();
	
	public static BlockMetalBarsMF IRON_BARS = Utils.nullValue();
	public static BlockMetalBarsMF BRONZE_BARS = Utils.nullValue();
	public static BlockMetalBarsMF STEEL_BARS = Utils.nullValue();
	public static BlockMetalBarsMF BLACK_STEEL_BARS = Utils.nullValue();
	public static BlockMetalBarsMF RED_STEEL_BARS = Utils.nullValue();
	public static BlockMetalBarsMF BLUE_STEEL_BARS = Utils.nullValue();

	public static BlockMetalMF COPPER_STORAGE = Utils.nullValue();
	public static BlockMetalMF TIN_STORAGE = Utils.nullValue();
	public static BlockMetalMF SILVER_STORAGE = Utils.nullValue();
	public static BlockMetalMF BRONZE_STORAGE = Utils.nullValue();
	public static BlockMetalMF PIG_IRON_STORAGE = Utils.nullValue();
	public static BlockMetalMF STEEL_STORAGE = Utils.nullValue();
	public static BlockMetalMF BLACK_STEEL_STORAGE = Utils.nullValue();
	public static BlockMetalMF RED_STEEL_STORAGE = Utils.nullValue();
	public static BlockMetalMF BLUE_STEEL_STORAGE = Utils.nullValue();
	public static BlockMetalMF ADAMANTIUM_STORAGE = Utils.nullValue();
	public static BlockMetalMF MITHRIL_STORAGE = Utils.nullValue();
	public static BlockMetalMF IGNOTUMITE_STORAGE = Utils.nullValue();
	public static BlockMetalMF MITHIUM_STORAGE = Utils.nullValue();
	public static BlockMetalMF ENDER_STORAGE = Utils.nullValue();
	
	public static BlockAnvilMF ANVIL_STONE = Utils.nullValue();
	public static BlockAnvilMF ANVIL_BRONZE = Utils.nullValue();
	public static BlockAnvilMF ANVIL_IRON = Utils.nullValue();
	public static BlockAnvilMF ANVIL_STEEL = Utils.nullValue();
	public static BlockAnvilMF ANVIL_BLACK_STEEL = Utils.nullValue();
	public static BlockAnvilMF ANVIL_BLUE_STEEL = Utils.nullValue();
	public static BlockAnvilMF ANVIL_RED_STEEL = Utils.nullValue();
	
	public static BlockCarpenter CARPENTER = Utils.nullValue();
	public static BlockBombBench BOMB_BENCH = Utils.nullValue();
	public static BlockCrossbowBench CROSSBOW_BENCH = Utils.nullValue();

	public static Block CHEESE_WHEEL = Utils.nullValue();

	public static Block CAKE_VANILLA = Utils.nullValue();
	public static Block CAKE_CARROT = Utils.nullValue();
	public static Block CAKE_CHOCOLATE = Utils.nullValue();
	public static Block CAKE_BF = Utils.nullValue();

	public static Block PIE_MEAT = Utils.nullValue();

	public static Block PIE_APPLE = Utils.nullValue();
	public static Block PIE_BERRY = Utils.nullValue();

	public static Block PIE_SHEPARDS = Utils.nullValue();

	public static Block BERRY_BUSH = Utils.nullValue();

	public static Block BLAST_CHAMBER = Utils.nullValue();
	public static Block BLAST_HEATER = Utils.nullValue();
	public static Block BLAST_HEATER_ACTIVE = Utils.nullValue();

	public static Block CRUCIBLE = Utils.nullValue();
	public static Block CRUCIBLE_ACTIVE = Utils.nullValue();
	public static Block CRUCIBLE_ADV = Utils.nullValue();
	public static Block CRUCIBLE_ADV_ACTIVE = Utils.nullValue();
	public static Block CRUCIBLE_AUTO = Utils.nullValue();
	public static Block CRUCIBLE_AUTO_ACTIVE = Utils.nullValue();

	public static Block CHIMNEY_STONE = Utils.nullValue();
	public static Block CHIMNEY_STONE_WIDE = Utils.nullValue();
	public static Block CHIMNEY_STONE_EXTRACTOR = Utils.nullValue();
	public static Block CHIMNEY_PIPE = Utils.nullValue();

	public static Block TANNER = Utils.nullValue();

	public static Block FORGE = Utils.nullValue();
	public static Block FORGE_ACTIVE = Utils.nullValue();
	public static Block FORGE_METAL = Utils.nullValue();
	public static Block FORGE_METAL_ACTIVE = Utils.nullValue();

	public static Block REPAIR_BASIC = Utils.nullValue();
	public static Block REPAIR_ADVANCED = Utils.nullValue();
	public static Block REPAIR_ORNATE = Utils.nullValue();

	public static Block BELLOWS = Utils.nullValue();

	public static Block REFINED_PLANKS = Utils.nullValue();
	public static Block NAILED_PLANKS = Utils.nullValue();
	public static Block REFINED_PLANKS_STAIR = Utils.nullValue();
	public static Block NAILED_PLANKS_STAIR = Utils.nullValue();

	public static Block REINFORCED_STONE = Utils.nullValue();
	public static Block REINFORCED_STONE_BRICKS = Utils.nullValue();
	public static Block REINFORCED_STONE_FRAMED = Utils.nullValue();
	public static Block REINFORCED_STONE_FRAMED_IRON = Utils.nullValue();

	public static Block ADV_TANNER = Utils.nullValue();
	public static Block RESEARCH = Utils.nullValue();
	public static Block TROUGH_WOOD = Utils.nullValue();
	public static Block ENG_TANNER = Utils.nullValue();

	public static Block BOMB_PRESS = Utils.nullValue();

	public static Block ROAD = Utils.nullValue();
	public static Block LOW_ROAD = Utils.nullValue();

	public static Block SALVAGE_BASIC = Utils.nullValue();
	public static Block BLOOMERY = Utils.nullValue();
	public static Block LOG_YEW = Utils.nullValue();
	public static Block LOG_IRONBARK = Utils.nullValue();
	public static Block LOG_EBONY = Utils.nullValue();

	public static Block LEAVES_YEW = Utils.nullValue();
	public static Block LEAVES_IRONBARK = Utils.nullValue();
	public static Block LEAVES_EBONY = Utils.nullValue();

	public static Block SAPLING_YEW = Utils.nullValue();
	public static Block SAPLING_IRONBARK = Utils.nullValue();
	public static Block SAPLING_EBONY = Utils.nullValue();

	public static Block YEW_PLANKS = Utils.nullValue();
	public static Block IRONBARK_PLANKS = Utils.nullValue();
	public static Block EBONY_PLANKS = Utils.nullValue();

	public static Block QUERN = Utils.nullValue();

	public static Block MUD_BRICK_STAIR = Utils.nullValue();
	public static Block MUD_PAVEMENT_STAIR = Utils.nullValue();
	public static Block COBBLE_BRICK_STAIR = Utils.nullValue();
	public static Block COBBLE_PAVEMENT_STAIR = Utils.nullValue();
	public static Block FIREBRICK_STAIR = Utils.nullValue();
	public static Block REINFORCED_STONE_STAIR = Utils.nullValue();
	public static Block REINFORCED_STONE_BRICK_STAIR = Utils.nullValue();

	public static Block YEW_STAIR = Utils.nullValue();
	public static Block IRONBARK_STAIR = Utils.nullValue();
	public static Block EBONY_STAIR = Utils.nullValue();

	public static Block FIREPIT = Utils.nullValue();
	public static Block ROAST = Utils.nullValue();
	public static Block OVEN_STONE = Utils.nullValue();

	public static Block FURNACE_HEATER = Utils.nullValue();
	public static Block FURNACE_STONE = Utils.nullValue();

	public static Block TOOL_RACK_WOOD = Utils.nullValue();
	public static Block FOOD_BOX_BASIC = Utils.nullValue();
	public static Block AMMO_BOX_BASIC = Utils.nullValue();
	public static Block CRATE_BASIC = Utils.nullValue();

	public static Block COGWORK_HELM = Utils.nullValue();
	public static Block COGWORK_LEGS = Utils.nullValue();
	public static Block COGWORK_CHEST = Utils.nullValue();
	public static Block FRAME_BLOCK = Utils.nullValue();
	public static Block COGWORK_BUILDER = Utils.nullValue();

	public static Block CRUCIBLE_MYTHIC = Utils.nullValue();
	public static Block CRUCIBLE_MYTHIC_ACTIVE = Utils.nullValue();
	public static Block CRUCIBLE_MASTER = Utils.nullValue();
	public static Block CRUCIBLE_MASTER_ACTIVE = Utils.nullValue();

	public static Block MYTHIC_DECOR = Utils.nullValue();
	public static Block WG_MARK = Utils.nullValue();
	public static Block COMPONENTS = Utils.nullValue();
	public static Block SCHEMATIC_GENERAL = Utils.nullValue();


	public static void init() {

		COPPER_ORE = new BlockOreMF("copper_ore", 0, -1).setHardness(2.0F).setResistance(3.0F);
		TIN_ORE = new BlockOreMF("tin_ore", 0).setHardness(2.5F).setResistance(4.0F);
		SILVER_ORE = new BlockOreMF("silver_ore", 2).setHardness(3.0F).setResistance(5.0F);
		MYTHIC_ORE = new BlockMythicOre("mythic_ore", false).setHardness(10.0F).setResistance(100.0F);

		KAOLINITE_ORE = new BlockOreMF("kaolinite_ore", 1, 0, ComponentListMFR.KAOLINITE, 1, 1, 1).setHardness(3.0F).setResistance(5.0F);
		NITRE_ORE = new BlockOreMF("nitre_ore", 2, 0, ComponentListMFR.NITRE, 1, 2, 1).setHardness(3.0F).setResistance(5.0F);
		SULFUR_ORE = new BlockOreMF("sulfur_ore", 2, 0, ComponentListMFR.SULFUR, 1, 4, 2).setHardness(3.0F).setResistance(2.0F);
		BORAX_ORE = new BlockOreMF("borax_ore", 2, 1, ComponentListMFR.FLUX_STRONG, 1, 8, 4).setHardness(3.0F).setResistance(2.0F);
		TUNGSTEN_ORE = new BlockOreMF("tungsten_ore", 3, 1, ComponentListMFR.ORE_TUNGSTEN, 1, 1, 4).setHardness(4.0F).setResistance(2.5F);
		CLAY_ORE = new BlockOreMF("clay_ore", 0, 0, Items.CLAY_BALL, 1, 4, 1, Material.GROUND).setBlockSoundType(SoundType.GROUND).setHardness(0.5F);
		COAL_RICH_ORE = new BlockOreMF("coal_rich_ore", 2, 1, Items.COAL, 2, 6, 2).setHardness(5.0F).setResistance(10.0F);

		MUD_BRICK = new BasicBlockMF("mud_brick", Material.GROUND).setHardness(1.0F).setResistance(0.5F);
		MUD_PAVEMENT = new BasicBlockMF("mud_pavement", Material.GROUND).setHardness(0.5F);

		COBBLE_BRICK = new BasicBlockMF("cobble_brick", Material.ROCK).setBlockSoundType(SoundType.STONE).setHardness(2.5F).setResistance(12.0F);
		COBBLE_PAVEMENT = new BasicBlockMF("cobble_pavement", Material.ROCK).setBlockSoundType(SoundType.STONE).setHardness(2.0F).setResistance(10.0F);

		WINDOW = new BasicBlockMF("window", Material.GLASS).setBlockSoundType(SoundType.GLASS).setHardness(0.9F).setResistance(0.1F);
		FRAMED_GLASS = new BasicBlockMF("framed_glass", Material.GLASS).setBlockSoundType(SoundType.GLASS).setHardness(0.6F).setResistance(0.2F);
		FRAMED_GLASS_PANE = new BlockPaneMF("framed_glass_pane", Material.GLASS, true).setBlockSoundType(SoundType.GLASS).setHardness(0.6F).setResistance(0.1F);
		WINDOW_PANE = new BlockPaneMF("window_pane",  Material.GLASS,true).setBlockSoundType(SoundType.GLASS).setHardness(0.9F).setResistance(0.2F);

		THATCH = new BasicBlockMF("thatch", Material.LEAVES).setBlockSoundType(SoundType.GROUND).setHardness(1.0F);
		THATCH_STAIR = new ConstructionBlockMF.StairsConstBlock("thatch_stairs", THATCH);

		LIMESTONE = new BasicBlockMF("limestone", Material.ROCK).setBlockSoundType(SoundType.STONE).setHardness(1.2F).setResistance(8F);
		LIMESTONE_COBBLE = new BasicBlockMF("limestone_cobble", Material.ROCK).setBlockSoundType(SoundType.STONE).setHardness(1.2F).setResistance(8F);
		LIMESTONE_BRICK = new BasicBlockMF("limestone_brick", Material.ROCK).setBlockSoundType(SoundType.STONE).setHardness(1.2F).setResistance(8F);
		LIMESTONE_PAVEMENT = new BasicBlockMF("limestone_pavement", Material.ROCK).setBlockSoundType(SoundType.STONE).setHardness(1.2F).setResistance(8F);
		
		LIMESTONE_STAIRS = new ConstructionBlockMF.StairsConstBlock("limestone_stairs", LIMESTONE);
		LIMESTONE_COBBLE_STAIRS = new ConstructionBlockMF.StairsConstBlock("limestone_cobble_stairs", LIMESTONE_COBBLE);
		LIMESTONE_BRICK_STAIRS = new ConstructionBlockMF.StairsConstBlock("limestone_brick_stairs", LIMESTONE_BRICK);
		LIMESTONE_PAVEMENT_STAIRS = new ConstructionBlockMF.StairsConstBlock("limestone_pavement_stairs", LIMESTONE_PAVEMENT);

		FIREBRICKS = new BasicBlockMF("firebricks", Material.ROCK).setBlockSoundType(SoundType.STONE).setHardness(5.0F).setResistance(15.0F);
		CLAY_WALL = new BasicBlockMF("clay_wall", Material.WOOD).setBlockSoundType(SoundType.WOOD).setHardness(1.0F).setResistance(1.0F);

		IRON_BARS = new BlockMetalBarsMF(BaseMaterialMFR.IRON);
		BRONZE_BARS = new BlockMetalBarsMF(BaseMaterialMFR.BRONZE);
		STEEL_BARS = new BlockMetalBarsMF(BaseMaterialMFR.STEEL);
		BLACK_STEEL_BARS = new BlockMetalBarsMF(BaseMaterialMFR.BLACK_STEEL);
		RED_STEEL_BARS = new BlockMetalBarsMF(BaseMaterialMFR.RED_STEEL);
		BLUE_STEEL_BARS = new BlockMetalBarsMF(BaseMaterialMFR.BLUE_STEEL);

		COPPER_STORAGE = new BlockMetalMF(BaseMaterialMFR.COPPER);
		TIN_STORAGE = new BlockMetalMF(BaseMaterialMFR.TIN);
		SILVER_STORAGE = new BlockMetalMF(BaseMaterialMFR.SILVER);
		BRONZE_STORAGE = new BlockMetalMF(BaseMaterialMFR.BRONZE);
		PIG_IRON_STORAGE = new BlockMetalMF(BaseMaterialMFR.PIG_IRON);
		STEEL_STORAGE = new BlockMetalMF(BaseMaterialMFR.STEEL);
		BLACK_STEEL_STORAGE = new BlockMetalMF(BaseMaterialMFR.BLACK_STEEL);
		RED_STEEL_STORAGE = new BlockMetalMF(BaseMaterialMFR.RED_STEEL);
		BLUE_STEEL_STORAGE = new BlockMetalMF(BaseMaterialMFR.BLUE_STEEL);
		ADAMANTIUM_STORAGE = new BlockMetalMF(BaseMaterialMFR.ADAMANTIUM);
		MITHRIL_STORAGE = new BlockMetalMF(BaseMaterialMFR.MITHRIL);
		IGNOTUMITE_STORAGE = new BlockMetalMF(BaseMaterialMFR.IGNOTUMITE);
		MITHIUM_STORAGE = new BlockMetalMF(BaseMaterialMFR.MITHIUM);
		ENDER_STORAGE = new BlockMetalMF(BaseMaterialMFR.ENDERFORGE);

		ANVIL_STONE = new BlockAnvilMF(BaseMaterialMFR.STONE);
		ANVIL_BRONZE = new BlockAnvilMF(BaseMaterialMFR.BRONZE);
		ANVIL_IRON = new BlockAnvilMF(BaseMaterialMFR.IRON);
		ANVIL_STEEL = new BlockAnvilMF(BaseMaterialMFR.STEEL);
		ANVIL_BLACK_STEEL = new BlockAnvilMF(BaseMaterialMFR.BLACK_STEEL);
		ANVIL_BLUE_STEEL = new BlockAnvilMF(BaseMaterialMFR.BLUE_STEEL);
		ANVIL_RED_STEEL = new BlockAnvilMF(BaseMaterialMFR.RED_STEEL);
		
		CARPENTER = new BlockCarpenter();
		BOMB_BENCH = new BlockBombBench();
		CROSSBOW_BENCH = new BlockCrossbowBench();

		CHEESE_WHEEL = new BlockCakeMF("cheese", FoodListMFR.CHEESE_SLICE).setCheese();

		CAKE_VANILLA = new BlockCakeMF("cake_vanilla", FoodListMFR.CAKE_SLICE);
		CAKE_CARROT = new BlockCakeMF("cake_carrot", FoodListMFR.CARROTCAKE_SLICE);
		CAKE_CHOCOLATE = new BlockCakeMF("cake_chocolate", FoodListMFR.CHOCCAKE_SLICE);
		CAKE_BF = new BlockCakeMF("cake_bf", FoodListMFR.BFCAKE_SLICE);

		PIE_MEAT = new BlockPie("pie_meat", FoodListMFR.MEATPIE_SLICE);

		PIE_APPLE = new BlockPie("pie_apple", FoodListMFR.PIESLICE_APPLE);
		PIE_BERRY = new BlockPie("pie_berry", FoodListMFR.PIESLICE_BERRY);

		PIE_SHEPARDS = new BlockPie("pie_shepards", FoodListMFR.PIESLICE_SHEPARDS);

		BERRY_BUSH = new BlockBerryBush("berry_bush");
		BLAST_CHAMBER = new BlockBFC();
		BLAST_HEATER = new BlockBFH(false);
		BLAST_HEATER_ACTIVE = new BlockBFH(true).setLightLevel(10F);

		CRUCIBLE = new BlockCrucible("stone", 0, false);
		CRUCIBLE_ACTIVE = new BlockCrucible("stone", 0, true).setLightLevel(12F);
		CRUCIBLE_ADV = new BlockCrucible("fireclay", 1, false);
		CRUCIBLE_ADV_ACTIVE = new BlockCrucible("fireclay", 1, true).setLightLevel(12F);
		CRUCIBLE_AUTO = new BlockCrucible("auto", 1, false).setAuto().setHardness(12F);
		CRUCIBLE_AUTO_ACTIVE = new BlockCrucible("auto", 1, true).setAuto().setHardness(12F).setLightLevel(12F);

		CHIMNEY_STONE = new BlockChimney("stone", false, false, 5);
		CHIMNEY_STONE_WIDE = new BlockChimney("stone", true, false, 10);
		CHIMNEY_STONE_EXTRACTOR = new BlockChimney("stone_extractor", true, true, 15);
		CHIMNEY_PIPE = new BlockChimney("pipe", false, false, 10).setPipe();

		TANNER = new BlockTanningRack(0, "");

		FORGE = new BlockForge("stone", 0, false);
		FORGE_ACTIVE = new BlockForge("stone", 0, true);
		FORGE_METAL = new BlockForge("metal", 1, false);
		FORGE_METAL_ACTIVE = new BlockForge("metal", 1, true);

		REPAIR_BASIC = new BlockRepairKit("basic", 0.25F, 0.05F, 0.2F);
		REPAIR_ADVANCED = new BlockRepairKit("advanced", 1.0F, 0.2F, 0.05F);
		REPAIR_ORNATE = new BlockRepairKit("ornate", 1.0F, 0.25F, 0.03F).setOrnate(0.5F);

		BELLOWS = new BlockBellows();

		REFINED_PLANKS = new BasicBlockMF("refined_planks", Material.WOOD).setBlockSoundType(SoundType.WOOD).setHardness(2.5F).setResistance(10F);
		NAILED_PLANKS = new BasicBlockMF("nailed_planks", Material.WOOD).setBlockSoundType(SoundType.WOOD).setHardness(1.5F).setResistance(7F);
		REFINED_PLANKS_STAIR = new ConstructionBlockMF.StairsConstBlock("refined_planks_stairs", REFINED_PLANKS);
		NAILED_PLANKS_STAIR = new ConstructionBlockMF.StairsConstBlock("nailed_planks_stairs", NAILED_PLANKS);

		REINFORCED_STONE = new BlockReinforcedStone("reinforced_stone", "base", "engraved", "dshall_0", "dshall_1", "dshall_2").setBlockSoundType(SoundType.STONE).setHardness(2.0F).setResistance(15F);
		REINFORCED_STONE_BRICKS = new BlockReinforcedStone("reinforced_stone_bricks", "base", "mossy", "cracked").setBlockSoundType(SoundType.STONE).setHardness(2.0F).setResistance(15F);
		REINFORCED_STONE_FRAMED = new BasicBlockMF("reinforced_stone_framed", Material.ROCK).setBlockSoundType(SoundType.STONE).setHardness(2.5F).setResistance(20F);
		REINFORCED_STONE_FRAMED_IRON = new BasicBlockMF("reinforced_stone_framed_iron", Material.ROCK).setBlockSoundType(SoundType.STONE).setHardness(2.5F).setResistance(20F);

		ADV_TANNER = new BlockTanningRack(1, "Strong");
		RESEARCH = new BlockResearchStation();
		TROUGH_WOOD = new BlockTrough("trough_wood");
		ENG_TANNER = new BlockEngineerTanner(2, "Metal");

		BOMB_PRESS = new BlockBombPress();

		ROAD = new BlockRoad("road_mf", 14F);
		LOW_ROAD = new BlockRoad("road_mf_short", 7F);

		SALVAGE_BASIC = new BlockSalvage("basic", 1.0F);
		BLOOMERY = new BlockBloomery();

		LOG_YEW = new BlockLogMF("yew").setHardness(2F).setResistance(6F);
		LOG_IRONBARK = new BlockLogMF("ironbark").setHardness(3F).setResistance(8F);
		LOG_EBONY = new BlockLogMF("ebony").setHardness(5F).setResistance(10F);

		LEAVES_YEW = new BlockLeavesMF("yew", 100);
		LEAVES_IRONBARK = new BlockLeavesMF("ironbark", 40).setHardness(0.3F);
		LEAVES_EBONY = new BlockLeavesMF("ebony", 1000).setHardness(0.5F);

		SAPLING_YEW = new BlockSaplingMF("yew", LOG_YEW, LEAVES_YEW, 4F);
		SAPLING_IRONBARK = new BlockSaplingMF("ironbark", LOG_IRONBARK, LEAVES_IRONBARK, 8F);
		SAPLING_EBONY = new BlockSaplingMF("ebony", LOG_EBONY, LEAVES_EBONY, 10F);

		YEW_PLANKS = new BasicBlockMF("yew_planks", Material.WOOD).setBlockSoundType(SoundType.WOOD).setHardness(3F).setResistance(6F);
		IRONBARK_PLANKS = new BasicBlockMF("ironbark_planks", Material.WOOD).setBlockSoundType(SoundType.WOOD).setHardness(4F).setResistance(10F);
		EBONY_PLANKS = new BasicBlockMF("ebony_planks", Material.WOOD).setBlockSoundType(SoundType.WOOD).setHardness(6F).setResistance(12F);

		QUERN = new BlockQuern("quern");

		MUD_BRICK_STAIR = new ConstructionBlockMF.StairsConstBlock("mud_brick_stairs", MUD_BRICK);
		MUD_PAVEMENT_STAIR = new ConstructionBlockMF.StairsConstBlock("mud_pavement_stairs", MUD_PAVEMENT);
		COBBLE_BRICK_STAIR = new ConstructionBlockMF.StairsConstBlock("cobble_brick_stairs", COBBLE_BRICK);
		COBBLE_PAVEMENT_STAIR = new ConstructionBlockMF.StairsConstBlock("cobble_pavement_stairs", COBBLE_PAVEMENT);
		FIREBRICK_STAIR = new ConstructionBlockMF.StairsConstBlock("firebrick_stairs", FIREBRICKS);
		REINFORCED_STONE_STAIR = new ConstructionBlockMF.StairsConstBlock("reinforced_stone_stairs", REINFORCED_STONE);
		REINFORCED_STONE_BRICK_STAIR = new ConstructionBlockMF.StairsConstBlock("reinforced_stone_brick_stairs", REINFORCED_STONE_BRICKS);

		YEW_STAIR = new ConstructionBlockMF.StairsConstBlock("yew_stairs", YEW_PLANKS);
		IRONBARK_STAIR = new ConstructionBlockMF.StairsConstBlock("ironbark_stairs", IRONBARK_PLANKS);
		EBONY_STAIR = new ConstructionBlockMF.StairsConstBlock("ebony_stairs", EBONY_PLANKS);

		FIREPIT = new BlockFirepit();
		ROAST = new BlockRoast(0, "basic", false);
		OVEN_STONE = new BlockRoast(0, "basic", true);

		FURNACE_HEATER = new BlockBigFurnace("furnace_heater", true, -1);
		FURNACE_STONE = new BlockBigFurnace("furnace_stone", false, 0);

		TOOL_RACK_WOOD = new BlockRack("rack_wood");
		FOOD_BOX_BASIC = new BlockAmmoBox("food_box_basic", (byte) 0);
		AMMO_BOX_BASIC = new BlockAmmoBox("ammo_box_basic", (byte) 1);
		CRATE_BASIC = new BlockAmmoBox("crate_basic", (byte) 2);

		COGWORK_HELM = new BlockCogwork("cogwork_helm", false);
		COGWORK_LEGS = new BlockCogwork("cogwork_legs", false);
		COGWORK_CHEST = new BlockCogwork("cogwork_chest", true);
		FRAME_BLOCK = new BlockFrame("frame_block");
		COGWORK_BUILDER = new BlockFrame("cogwork_builder", FRAME_BLOCK).setCogworkHolder();

		CRUCIBLE_MYTHIC = new BlockCrucible("mythic", 2, false).setAuto().setBlockUnbreakable();
		CRUCIBLE_MYTHIC_ACTIVE = new BlockCrucible("mythic", 2, true).setAuto().setBlockUnbreakable().setLightLevel(12F);
		CRUCIBLE_MASTER = new BlockCrucible("master", 3, false).setAuto().setBlockUnbreakable();
		CRUCIBLE_MASTER_ACTIVE = new BlockCrucible("master", 3, true).setAuto().setBlockUnbreakable().setLightLevel(12F);

		MYTHIC_DECOR = new BlockMythicDecor();
		WG_MARK = new BlockWorldGenMarker("world_gen_flag");
		COMPONENTS = new BlockComponent();
		SCHEMATIC_GENERAL = new BlockSchematic("schematic_general");
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();

		registry.register(COPPER_ORE);
		registry.register(TIN_ORE);
		registry.register(SILVER_ORE);
		registry.register(MYTHIC_ORE);

		registry.register(KAOLINITE_ORE);
		registry.register(NITRE_ORE);
		registry.register(SULFUR_ORE);
		registry.register(BORAX_ORE);
		registry.register(TUNGSTEN_ORE);
		registry.register(CLAY_ORE);
		registry.register(COAL_RICH_ORE);

		registry.register(MUD_BRICK);
		registry.register(MUD_PAVEMENT);

		registry.register(COBBLE_BRICK);
		registry.register(COBBLE_PAVEMENT);

		registry.register(WINDOW);
		registry.register(FRAMED_GLASS);
		registry.register(FRAMED_GLASS_PANE);
		registry.register(WINDOW_PANE);

		registry.register(THATCH);
		registry.register(THATCH_STAIR);

		registry.register(LIMESTONE);
		registry.register(LIMESTONE_COBBLE);
		registry.register(LIMESTONE_BRICK);
		registry.register(LIMESTONE_PAVEMENT);

		registry.register(LIMESTONE_STAIRS);
		registry.register(LIMESTONE_COBBLE_STAIRS);
		registry.register(LIMESTONE_BRICK_STAIRS);
		registry.register(LIMESTONE_PAVEMENT_STAIRS);

		registry.register(FIREBRICKS);
		registry.register(CLAY_WALL);

		registry.register(IRON_BARS);
		registry.register(BRONZE_BARS);
		registry.register(STEEL_BARS);
		registry.register(BLACK_STEEL_BARS);
		registry.register(RED_STEEL_BARS);
		registry.register(BLUE_STEEL_BARS);

		registry.register(COPPER_STORAGE);
		registry.register(TIN_STORAGE);
		registry.register(SILVER_STORAGE);
		registry.register(BRONZE_STORAGE);
		registry.register(PIG_IRON_STORAGE);
		registry.register(STEEL_STORAGE);
		registry.register(BLACK_STEEL_STORAGE);
		registry.register(RED_STEEL_STORAGE);
		registry.register(BLUE_STEEL_STORAGE);
		registry.register(ADAMANTIUM_STORAGE);
		registry.register(MITHRIL_STORAGE);
		registry.register(IGNOTUMITE_STORAGE);
		registry.register(MITHIUM_STORAGE);
		registry.register(ENDER_STORAGE);

		registry.register(CHEESE_WHEEL);

		registry.register(CAKE_VANILLA);
		registry.register(CAKE_CARROT);
		registry.register(CAKE_CHOCOLATE);
		registry.register(CAKE_BF);

		registry.register(PIE_MEAT);

		registry.register(PIE_APPLE);
		registry.register(PIE_BERRY);

		registry.register(PIE_SHEPARDS);

		registry.register(REPAIR_BASIC);
		registry.register(REPAIR_ADVANCED);
		registry.register(REPAIR_ORNATE);

		registry.register(REFINED_PLANKS);
		registry.register(NAILED_PLANKS);
		registry.register(REFINED_PLANKS_STAIR);
		registry.register(NAILED_PLANKS_STAIR);

		registry.register(REINFORCED_STONE);
		registry.register(REINFORCED_STONE_BRICKS);
		registry.register(REINFORCED_STONE_FRAMED);
		registry.register(REINFORCED_STONE_FRAMED_IRON);

		registry.register(LOG_YEW);
		registry.register(LOG_IRONBARK);
		registry.register(LOG_EBONY);

		registry.register(LEAVES_YEW);
		registry.register(LEAVES_IRONBARK);
		registry.register(LEAVES_EBONY);

		registry.register(SAPLING_YEW);
		registry.register(SAPLING_IRONBARK);
		registry.register(SAPLING_EBONY);

		registry.register(YEW_PLANKS);
		registry.register(IRONBARK_PLANKS);
		registry.register(EBONY_PLANKS);

		registry.register(MUD_BRICK_STAIR);
		registry.register(MUD_PAVEMENT_STAIR);
		registry.register(COBBLE_BRICK_STAIR);
		registry.register(COBBLE_PAVEMENT_STAIR);
		registry.register(FIREBRICK_STAIR);
		registry.register(REINFORCED_STONE_STAIR);
		registry.register(REINFORCED_STONE_BRICK_STAIR);

		registry.register(YEW_STAIR);
		registry.register(IRONBARK_STAIR);
		registry.register(EBONY_STAIR);

		registry.register(COGWORK_HELM);
		registry.register(COGWORK_LEGS);
		registry.register(COGWORK_CHEST);
		registry.register(FRAME_BLOCK);
		registry.register(COGWORK_BUILDER);

		registry.register(MYTHIC_DECOR);
		registry.register(SCHEMATIC_GENERAL);

		registry.register(SALVAGE_BASIC);

		registry.register(OVEN_STONE);

		//Tile Entities

		registry.register(ANVIL_STONE);
		registry.register(ANVIL_BRONZE);
		registry.register(ANVIL_IRON);
		registry.register(ANVIL_STEEL);
		registry.register(ANVIL_BLACK_STEEL);
		registry.register(ANVIL_BLUE_STEEL);
		registry.register(ANVIL_RED_STEEL);
		registerTile(TileEntityAnvilMFR.class, "anvil_tile");
		registry.register(CARPENTER);
		registerTile(TileEntityCarpenterMFR.class, "carpenter_tile");
		registry.register(BOMB_BENCH);
		registerTile(TileEntityBombBench.class, "bomb_bench_tile");
		registry.register(CROSSBOW_BENCH);
		registerTile(TileEntityCrossbowBench.class, "crossbow_bench_tile");


		registry.register(BERRY_BUSH);
		registerTile(TileEntityBerryBush.class, "berry_bush_tile");

		registry.register(BLAST_CHAMBER);
		registerTile(TileEntityBlastFC.class, "blast_furnace_chamber_tile");
		registry.register(BLAST_HEATER);
		registry.register(BLAST_HEATER_ACTIVE);
		registerTile(TileEntityBlastFH.class, "blast_furnace_heater_tile");

		registry.register(CRUCIBLE);
		registry.register(CRUCIBLE_ACTIVE);
		registry.register(CRUCIBLE_ADV);
		registry.register(CRUCIBLE_ADV_ACTIVE);
		registry.register(CRUCIBLE_AUTO);
		registry.register(CRUCIBLE_AUTO_ACTIVE);
		registry.register(CRUCIBLE_MYTHIC);
		registry.register(CRUCIBLE_MYTHIC_ACTIVE);
		registry.register(CRUCIBLE_MASTER);
		registry.register(CRUCIBLE_MASTER_ACTIVE);
		registerTile(TileEntityCrucible.class, "crucible_tile");

		registry.register(CHIMNEY_STONE);
		registry.register(CHIMNEY_STONE_WIDE);
		registry.register(CHIMNEY_STONE_EXTRACTOR);
		registry.register(CHIMNEY_PIPE);
		registerTile(TileEntityChimney.class, "chimney_tile");

		registry.register(TANNER);
		registry.register(ADV_TANNER);
		registry.register(ENG_TANNER);
		registerTile(TileEntityTanningRack.class, "tanning_rack_tile");

		registry.register(FORGE);
		registry.register(FORGE_ACTIVE);
		registry.register(FORGE_METAL);
		registry.register(FORGE_METAL_ACTIVE);
		registerTile(TileEntityForge.class, "forge_tile");

		registry.register(BELLOWS);
		registerTile(TileEntityBellows.class, "bellows_tile");

		registry.register(RESEARCH);
		registerTile(TileEntityResearch.class, "research_table_tile");
		registry.register(BOMB_PRESS);
		registerTile(TileEntityBombPress.class, "bomb_press_tile");

		registry.register(ROAD);
		registry.register(LOW_ROAD);
		registerTile(TileEntityRoad.class, "road_tile");

		registry.register(BLOOMERY);
		registerTile(TileEntityBloomery.class, "bloomery_tile");

		registry.register(QUERN);
		registerTile(TileEntityQuern.class, "quern_tile");

		registry.register(FIREPIT);
		registerTile(TileEntityFirepit.class, "firepit_tile");
		registry.register(ROAST);
		registerTile(TileEntityRoast.class, "roast_tile");

		registry.register(FURNACE_HEATER);
		registry.register(FURNACE_STONE);
		registerTile(TileEntityBigFurnace.class, "big_furnace_tile");

		registry.register(TOOL_RACK_WOOD);
		registerTile(TileEntityRack.class, "tool_rack_tile");
		registry.register(TROUGH_WOOD);
		registerTile(TileEntityTrough.class, "trough_tile");
		registry.register(FOOD_BOX_BASIC);
		registry.register(AMMO_BOX_BASIC);
		registry.register(CRATE_BASIC);
		registerTile(TileEntityAmmoBox.class, "ammo_box_tile");

		registry.register(WG_MARK);
		registerTile(TileEntityWorldGenMarker.class, "world_gen_marker_tile");
		registry.register(COMPONENTS);
		registerTile(TileEntityComponent.class, "component_tile");

	}

	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();

		registry.register(new ItemBlockOreMF(COPPER_ORE));
		registry.register(new ItemBlockOreMF(TIN_ORE));
		registry.register(new ItemBlockOreMF(SILVER_ORE));
		registry.register(new ItemBlockOreMF(MYTHIC_ORE));

		registry.register(new ItemBlockOreMF(KAOLINITE_ORE));
		registry.register(new ItemBlockOreMF(NITRE_ORE));
		registry.register(new ItemBlockOreMF(SULFUR_ORE));
		registry.register(new ItemBlockOreMF(BORAX_ORE));
		registry.register(new ItemBlockOreMF(TUNGSTEN_ORE));
		registry.register(new ItemBlockOreMF(CLAY_ORE));
		registry.register(new ItemBlockOreMF(COAL_RICH_ORE));

		registry.register(new ItemBlockBase(MUD_BRICK));
		registry.register(new ItemBlockBase(MUD_PAVEMENT));

		registry.register(new ItemBlockBase(COBBLE_BRICK));
		registry.register(new ItemBlockBase(COBBLE_PAVEMENT));

		registry.register(new ItemBlockBase(WINDOW));
		registry.register(new ItemBlockBase(FRAMED_GLASS));
		registry.register(new ItemBlockBase(FRAMED_GLASS_PANE));
		registry.register(new ItemBlockBase(WINDOW_PANE));

		registry.register(new ItemBlockBase(THATCH));
		registry.register(new ItemBlockBase(THATCH_STAIR));

		registry.register(new ItemBlockBase(LIMESTONE));
		registry.register(new ItemBlockBase(LIMESTONE_COBBLE));
		registry.register(new ItemBlockBase(LIMESTONE_BRICK));
		registry.register(new ItemBlockBase(LIMESTONE_PAVEMENT));

		registry.register(new ItemBlockBase(LIMESTONE_STAIRS));
		registry.register(new ItemBlockBase(LIMESTONE_COBBLE_STAIRS));
		registry.register(new ItemBlockBase(LIMESTONE_BRICK_STAIRS));
		registry.register(new ItemBlockBase(LIMESTONE_PAVEMENT_STAIRS));

		registry.register(new ItemBlockBase(FIREBRICKS));
		registry.register(new ItemBlockBase(CLAY_WALL));

		registry.register(new ItemBlockBase(IRON_BARS));
		registry.register(new ItemBlockBase(BRONZE_BARS));
		registry.register(new ItemBlockBase(STEEL_BARS));
		registry.register(new ItemBlockBase(BLACK_STEEL_BARS));
		registry.register(new ItemBlockBase(RED_STEEL_BARS));
		registry.register(new ItemBlockBase(BLUE_STEEL_BARS));

		registry.register(new ItemBlockBase(COPPER_STORAGE));
		registry.register(new ItemBlockBase(TIN_STORAGE));
		registry.register(new ItemBlockBase(SILVER_STORAGE));
		registry.register(new ItemBlockBase(BRONZE_STORAGE));
		registry.register(new ItemBlockBase(PIG_IRON_STORAGE));
		registry.register(new ItemBlockBase(STEEL_STORAGE));
		registry.register(new ItemBlockBase(BLACK_STEEL_STORAGE));
		registry.register(new ItemBlockBase(RED_STEEL_STORAGE));
		registry.register(new ItemBlockBase(BLUE_STEEL_STORAGE));
		registry.register(new ItemBlockBase(ADAMANTIUM_STORAGE));
		registry.register(new ItemBlockBase(MITHRIL_STORAGE));
		registry.register(new ItemBlockBase(IGNOTUMITE_STORAGE));
		registry.register(new ItemBlockBase(MITHIUM_STORAGE));
		registry.register(new ItemBlockBase(ENDER_STORAGE));

		registry.register(new ItemBlockAnvilMF(ANVIL_STONE));
		registry.register(new ItemBlockAnvilMF(ANVIL_BRONZE));
		registry.register(new ItemBlockAnvilMF(ANVIL_IRON));
		registry.register(new ItemBlockAnvilMF(ANVIL_STEEL));
		registry.register(new ItemBlockAnvilMF(ANVIL_BLACK_STEEL));
		registry.register(new ItemBlockAnvilMF(ANVIL_BLUE_STEEL));
		registry.register(new ItemBlockAnvilMF(ANVIL_RED_STEEL));

		registry.register(new ItemBlockBase(CARPENTER));
		registry.register(new ItemBlockBase(BOMB_BENCH));
		registry.register(new ItemBlockBase(CROSSBOW_BENCH));

		registry.register(new ItemBlockCake(CHEESE_WHEEL));
		registry.register(new ItemBlockCake(CAKE_VANILLA));
		registry.register(new ItemBlockCake(CAKE_CARROT));
		registry.register(new ItemBlockCake(CAKE_CHOCOLATE));
		registry.register(new ItemBlockCake(CAKE_BF));

		registry.register(new ItemBlockBase(PIE_MEAT));

		registry.register(new ItemBlockBase(PIE_APPLE));
		registry.register(new ItemBlockBase(PIE_BERRY));

		registry.register(new ItemBlockBase(PIE_SHEPARDS));

		registry.register(new ItemBlockBerries(BERRY_BUSH));

		registry.register(new ItemBlockBase(BLAST_CHAMBER));
		registry.register(new ItemBlockBase(BLAST_HEATER));
		registry.register(new ItemBlockBase(BLAST_HEATER_ACTIVE));

		registry.register(new ItemBlockBase(CRUCIBLE));
		registry.register(new ItemBlockBase(CRUCIBLE_ACTIVE));
		registry.register(new ItemBlockBase(CRUCIBLE_ADV));
		registry.register(new ItemBlockBase(CRUCIBLE_ADV_ACTIVE));
		registry.register(new ItemBlockBase(CRUCIBLE_AUTO));
		registry.register(new ItemBlockBase(CRUCIBLE_AUTO_ACTIVE));

		registry.register(new ItemBlockBase(CHIMNEY_STONE));
		registry.register(new ItemBlockBase(CHIMNEY_STONE_WIDE));
		registry.register(new ItemBlockBase(CHIMNEY_STONE_EXTRACTOR));
		registry.register(new ItemBlockBase(CHIMNEY_PIPE));

		registry.register(new ItemBlockBase(TANNER));

		registry.register(new ItemBlockBase(FORGE));
		registry.register(new ItemBlockBase(FORGE_ACTIVE));
		registry.register(new ItemBlockBase(FORGE_METAL));
		registry.register(new ItemBlockBase(FORGE_METAL_ACTIVE));

		registry.register(new ItemBlockRepairKit(REPAIR_BASIC));
		registry.register(new ItemBlockRepairKit(REPAIR_ADVANCED));
		registry.register(new ItemBlockRepairKit(REPAIR_ORNATE));

		registry.register(new ItemBlockBase(BELLOWS));

		registry.register(new ItemBlockBase(REFINED_PLANKS));
		registry.register(new ItemBlockBase(NAILED_PLANKS));
		registry.register(new ItemBlockBase(REFINED_PLANKS_STAIR));
		registry.register(new ItemBlockBase(NAILED_PLANKS_STAIR));

		registry.register(new ItemBlockBase(REINFORCED_STONE));
		registry.register(new ItemBlockBase(REINFORCED_STONE_BRICKS));
		registry.register(new ItemBlockBase(REINFORCED_STONE_FRAMED));
		registry.register(new ItemBlockBase(REINFORCED_STONE_FRAMED_IRON));

		registry.register(new ItemBlockBase(ADV_TANNER));
		registry.register(new ItemBlockBase(RESEARCH));
		registry.register(new ItemBlockTrough(TROUGH_WOOD));
		registry.register(new ItemBlockBase(ENG_TANNER));

		registry.register(new ItemBlockBase(BOMB_PRESS));

		registry.register(new ItemBlockBase(ROAD));
		registry.register(new ItemBlockBase(LOW_ROAD));

		registry.register(new ItemBlockSalvage(SALVAGE_BASIC));

		registry.register(new ItemBlockBase(BLOOMERY));
		registry.register(new ItemBlockBase(LOG_YEW));
		registry.register(new ItemBlockBase(LOG_IRONBARK));
		registry.register(new ItemBlockBase(LOG_EBONY));

		registry.register(new ItemBlockBase(LEAVES_YEW));
		registry.register(new ItemBlockBase(LEAVES_IRONBARK));
		registry.register(new ItemBlockBase(LEAVES_EBONY));

		registry.register(new ItemBlockBase(SAPLING_YEW));
		registry.register(new ItemBlockBase(SAPLING_IRONBARK));
		registry.register(new ItemBlockBase(SAPLING_EBONY));

		registry.register(new ItemBlockBase(YEW_PLANKS));
		registry.register(new ItemBlockBase(IRONBARK_PLANKS));
		registry.register(new ItemBlockBase(EBONY_PLANKS));

		registry.register(new ItemBlockBase(QUERN));

		registry.register(new ItemBlockBase(MUD_BRICK_STAIR));
		registry.register(new ItemBlockBase(MUD_PAVEMENT_STAIR));
		registry.register(new ItemBlockBase(COBBLE_BRICK_STAIR));
		registry.register(new ItemBlockBase(COBBLE_PAVEMENT_STAIR));
		registry.register(new ItemBlockBase(FIREBRICK_STAIR));
		registry.register(new ItemBlockBase(REINFORCED_STONE_STAIR));
		registry.register(new ItemBlockBase(REINFORCED_STONE_BRICK_STAIR));

		registry.register(new ItemBlockBase(YEW_STAIR));
		registry.register(new ItemBlockBase(IRONBARK_STAIR));
		registry.register(new ItemBlockBase(EBONY_STAIR));

		registry.register(new ItemBlockBase(FIREPIT));
		registry.register(new ItemBlockBase(ROAST));
		registry.register(new ItemBlockBase(OVEN_STONE));

		registry.register(new ItemBlockBase(FURNACE_HEATER));
		registry.register(new ItemBlockBase(FURNACE_STONE));

		registry.register(new ItemBlockToolRack(TOOL_RACK_WOOD));
		registry.register(new ItemBlockAmmoBox(FOOD_BOX_BASIC));
		registry.register(new ItemBlockAmmoBox(AMMO_BOX_BASIC));
		registry.register(new ItemBlockAmmoBox(CRATE_BASIC));

		registry.register(new ItemBlockBase(COGWORK_HELM));
		registry.register(new ItemBlockBase(COGWORK_LEGS));
		registry.register(new ItemBlockBase(COGWORK_CHEST));
		registry.register(new ItemBlockBase(FRAME_BLOCK));
		registry.register(new ItemBlockBase(COGWORK_BUILDER));

		registry.register(new ItemBlockBase(CRUCIBLE_MYTHIC));
		registry.register(new ItemBlockBase(CRUCIBLE_MYTHIC_ACTIVE));
		registry.register(new ItemBlockBase(CRUCIBLE_MASTER));
		registry.register(new ItemBlockBase(CRUCIBLE_MASTER_ACTIVE));

		registry.register(new ItemBlockBase(MYTHIC_DECOR));
		registry.register(new ItemBlockBase(WG_MARK));
		registry.register(new ItemBlockBase(COMPONENTS));
		registry.register(new ItemBlockBase(SCHEMATIC_GENERAL));
	}

	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {

	}

	public static void load() {
		// 5:20 default planks
		Blocks.FIRE.setFireInfo(REFINED_PLANKS, 3, 10);
		Blocks.FIRE.setFireInfo(LOG_IRONBARK, 2, 8);// IRONBARK: Logs are resistant to fire but raw planks are not
		Blocks.FIRE.setFireInfo(IRONBARK_PLANKS, 5, 30);
		Blocks.FIRE.setFireInfo(LOG_EBONY, 3, 10);// EBONY: Resistant
		Blocks.FIRE.setFireInfo(EBONY_PLANKS, 3, 10);


	}

	private static void registerTile(Class<? extends TileEntity> teClass, String teId) {
		GameRegistry.registerTileEntity(teClass, new ResourceLocation(MineFantasyReborn.MOD_ID, teId));
	}
}


