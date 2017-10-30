package minefantasy.mf2.block.list;

import minefantasy.mf2.block.basic.*;
import minefantasy.mf2.block.crafting.*;
import minefantasy.mf2.block.decor.*;
import minefantasy.mf2.block.food.BlockBerryBush;
import minefantasy.mf2.block.food.BlockCakeMF;
import minefantasy.mf2.block.food.BlockPie;
import minefantasy.mf2.block.refining.*;
import minefantasy.mf2.block.tree.BlockLeavesMF;
import minefantasy.mf2.block.tree.BlockLogMF;
import minefantasy.mf2.block.tree.BlockSaplingMF;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BlockListMF {
    public static final String[] metalBlocks = new String[]{"copper", "tin", "silver", "bronze", "pigiron", "steel",
            "blacksteel", "redsteel", "bluesteel", "adamantium", "mithril", "ignotumite", "mithium", "ender"};
    public static final String[] specialMetalBlocks = new String[]{"bronze", "iron", "steel", "blacksteel",
            "redsteel", "bluesteel"};
    public static final String[] anvils = new String[]{"bronze", "iron", "steel", "blacksteel", "bluesteel",
            "redsteel"};

    public static Block oreCopper = new BlockOreMF("oreCopper", 0, -1).setHardness(2.0F).setResistance(3.0F);
    public static Block oreTin = new BlockOreMF("oreTin", 0).setHardness(2.5F).setResistance(4.0F);
    public static Block oreSilver = new BlockOreMF("oreSilver", 2).setHardness(3.0F).setResistance(5.0F);
    public static Block oreMythic = new BlockMythicOre("oreMythic", false).setHardness(10.0F).setResistance(100.0F);

    public static Block oreKaolinite = new BlockOreMF("oreKaolinite", 1, 0, ComponentListMF.kaolinite, 1, 1, 1)
            .setHardness(3.0F).setResistance(5.0F);
    public static Block oreNitre = new BlockOreMF("oreNitre", 2, 0, ComponentListMF.nitre, 1, 2, 1).setHardness(3.0F)
            .setResistance(5.0F);
    public static Block oreSulfur = new BlockOreMF("oreSulfur", 2, 0, ComponentListMF.sulfur, 1, 4, 2).setHardness(3.0F)
            .setResistance(2.0F);
    public static Block oreBorax = new BlockOreMF("oreBorax", 2, 1, ComponentListMF.flux_strong, 1, 8, 4)
            .setHardness(3.0F).setResistance(2.0F);
    public static Block oreTungsten = new BlockOreMF("oreTungsten", 3, 1, ComponentListMF.oreTungsten, 1, 1, 4)
            .setHardness(4.0F).setResistance(2.5F);
    public static Block oreClay = new BlockOreMF("oreClay", 0, 0, Items.clay_ball, 1, 4, 1, Material.ground)
            .setHardness(0.5F).setStepSound(Block.soundTypeGravel);
    public static Block oreCoalRich = new BlockOreMF("oreCoalRich", 2, 1, Items.coal, 2, 6, 2).setHardness(5.0F)
            .setResistance(10.0F);

    public static Block mud_brick = new BasicBlockMF("mud_brick", Material.ground).setHardness(1.0F)
            .setResistance(0.5F);
    public static Block mud_pavement = new BasicBlockMF("mud_pavement", Material.ground).setHardness(0.5F);

    public static Block cobble_brick = new BasicBlockMF("cobble_brick", Material.rock).setHardness(2.5F)
            .setResistance(12.0F).setStepSound(Block.soundTypePiston);
    public static Block cobble_pavement = new BasicBlockMF("cobble_pavement", Material.rock).setHardness(2.0F)
            .setResistance(10.0F).setStepSound(Block.soundTypePiston);

    public static Block window = new BasicBlockMF("window", Material.glass).setHardness(0.9F).setResistance(0.1F)
            .setStepSound(Block.soundTypeGlass);
    public static Block framed_glass = new BasicBlockMF("framed_glass", Material.glass).setHardness(0.6F)
            .setResistance(0.2F).setStepSound(Block.soundTypeGlass);
    public static Block framed_pane = new BlockPaneMF("framed_pane", "framed_glass", "framed_glass_pane",
            Material.glass, true).setHardness(0.6F).setResistance(0.1F).setStepSound(Block.soundTypeGlass);
    public static Block window_pane = new BlockPaneMF("window_pane", "window", "framed_glass_pane", Material.glass,
            true).setHardness(0.9F).setResistance(0.2F).setStepSound(Block.soundTypeGlass);

    public static Block thatch = new BasicBlockMF("thatch", Material.leaves).setHardness(1.0F)
            .setStepSound(Block.soundTypeGrass);
    public static Block thatch_stair = new ConstructionBlockMF.StairsConstBlock("thatch_stair", thatch)
            .register("thatch_stair");

    // public static Block limestone_cobblestone = new
    // BasicBlockMF("limestone_cobblestone",
    // Material.rock).setHardness(0.8F).setResistance(4.0F).setStepSound(Block.soundTypePiston);
    // public static Block limestone = new BasicBlockMF("limestone", Material.rock,
    // limestone_cobblestone).setHardness(1.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone);

    public static Block limestone = new ConstructionBlockMF("limestone").setHardness(1.2F).setResistance(8F);

    public static Block firebricks = new BasicBlockMF("firebricks", Material.rock).setHardness(5.0F)
            .setResistance(15.0F).setStepSound(Block.soundTypePiston);
    public static Block clayWall = new BasicBlockMF("clayWall", Material.wood).setHardness(1.0F).setResistance(1.0F)
            .setStepSound(Block.soundTypeWood);

    public static BlockMetalBarsMF[] bars = new BlockMetalBarsMF[specialMetalBlocks.length];
    public static BlockMetalMF[] storage = new BlockMetalMF[metalBlocks.length];
    public static BlockAnvilMF anvilStone;
    public static BlockAnvilMF[] anvil = new BlockAnvilMF[anvils.length];
    public static BlockCarpenter carpenter = new BlockCarpenter();
    public static BlockBombBench bombBench = new BlockBombBench();
    public static BlockCrossbowBench crossbowBench = new BlockCrossbowBench();

    public static Block cheese_wheel = new BlockCakeMF("cheese", FoodListMF.cheese_slice).setCheese();

    public static Block cake_vanilla = new BlockCakeMF("cake_vanilla", FoodListMF.cake_slice);
    public static Block cake_carrot = new BlockCakeMF("cake_carrot", FoodListMF.carrotcake_slice);
    public static Block cake_chocolate = new BlockCakeMF("cake_chocolate", FoodListMF.choccake_slice);
    public static Block cake_bf = new BlockCakeMF("cake_bf", FoodListMF.bfcake_slice);

    public static Block pie_meat = new BlockPie("pie_meat", FoodListMF.meatpie_slice);

    public static Block pie_apple = new BlockPie("pie_apple", FoodListMF.pieslice_apple);
    public static Block pie_berry = new BlockPie("pie_berry", FoodListMF.pieslice_berry);

    public static Block pie_shepards = new BlockPie("pie_shepards", FoodListMF.pieslice_shepards);

    public static Block berryBush = new BlockBerryBush("berries");
    public static Block blast_chamber = new BlockBFC();
    public static Block blast_heater = new BlockBFH(false);
    public static Block blast_heater_active = new BlockBFH(true).setLightLevel(10F);

    public static Block crucible = new BlockCrucible("stone", 0, false);
    public static Block crucible_active = new BlockCrucible("stone", 0, true).setLightLevel(12F);
    public static Block crucibleadv = new BlockCrucible("fireclay", 1, false);
    public static Block crucibleadv_active = new BlockCrucible("fireclay", 1, true).setLightLevel(12F);
    public static Block crucibleauto = new BlockCrucible("auto", 1, false).setAuto().setHardness(12F);
    public static Block crucibleauto_active = new BlockCrucible("auto", 1, true).setAuto().setHardness(12F)
            .setLightLevel(12F);

    public static Block chimney_stone = new BlockChimney("stone", false, false, 5);
    public static Block chimney_stone_wide = new BlockChimney("stone", true, false, 10);
    public static Block chimney_stone_extractor = new BlockChimney("stone_extractor", true, true, 15);
    public static Block chimney_pipe = new BlockChimney("pipe", false, false, 10).setPipe();

    public static Block tanner = new BlockTanningRack(0, "");

    public static Block forge = new BlockForge("stone", 0, false);
    public static Block forge_active = new BlockForge("stone", 0, true);
    public static Block forge_metal = new BlockForge("metal", 1, false);
    public static Block forge_metal_active = new BlockForge("metal", 1, true);

    public static Block repair_basic = new BlockRepairKit("basic", 0.25F, 0.05F, 0.2F);
    public static Block repair_advanced = new BlockRepairKit("advanced", 1.0F, 0.2F, 0.05F);
    public static Block repair_ornate = new BlockRepairKit("ornate", 1.0F, 0.25F, 0.03F).setOrnate(0.5F);

    public static Block bellows = new BlockBellows();

    public static Block refined_planks = new BasicBlockMF("refined_planks", Material.wood).setHardness(2.5F)
            .setResistance(10F).setStepSound(Block.soundTypeWood);
    public static Block nailed_planks = new BasicBlockMF("nailed_planks", Material.wood).setHardness(1.5F)
            .setResistance(7F).setStepSound(Block.soundTypeWood);
    public static Block refined_planks_stair = new ConstructionBlockMF.StairsConstBlock("refined_planks_stair",
            refined_planks).register("refined_planks_stair");
    public static Block nailed_planks_stair = new ConstructionBlockMF.StairsConstBlock("nailed_planks_stair",
            nailed_planks).register("nailed_planks_stair");

    public static Block reinforced_stone = new BlockReinforcedStone("reinforced_stone", "base", "engraved", "dshall_0",
            "dshall_1", "dshall_2").setHardness(2.0F).setResistance(15F).setStepSound(Block.soundTypeStone);
    public static Block reinforced_stone_bricks = new BlockReinforcedStone("reinforced_stone_bricks", "base", "mossy",
            "cracked").setHardness(2.0F).setResistance(15F).setStepSound(Block.soundTypeStone);
    public static Block reinforced_stone_framed = new BasicBlockMF("reinforced_stone_framed", Material.rock)
            .setHardness(2.5F).setResistance(20F).setStepSound(Block.soundTypeStone);
    public static Block reinforced_stone_framediron = new BasicBlockMF("reinforced_stone_framediron", Material.rock)
            .setHardness(2.5F).setResistance(20F).setStepSound(Block.soundTypeStone)
            .setBlockName("reinforced_stone_framed");

    public static Block advTanner = new BlockTanningRack(1, "Strong");
    public static Block research = new BlockResearchStation();
    public static Block trough_wood = new BlockTrough("trough_wood");
    public static Block engTanner = new BlockEngineerTanner(2, "Metal");

    public static Block bombPress = new BlockBombPress();

    public static Block road = new BlockRoad("road_mf", 14F);
    public static Block lowroad = new BlockRoad("road_mf_short", 7F);

    public static Block salvage_basic = new BlockSalvage("basic", 1.0F);
    public static Block bloomery = new BlockBloomery();
    public static Block log_yew = new BlockLogMF("yew").setHardness(2F).setResistance(6F);
    public static Block log_ironbark = new BlockLogMF("ironbark").setHardness(3F).setResistance(8F);
    public static Block log_ebony = new BlockLogMF("ebony").setHardness(5F).setResistance(10F);

    public static Block leaves_yew = new BlockLeavesMF("yew", 100);
    public static Block leaves_ironbark = new BlockLeavesMF("ironbark", 40).setHardness(0.3F);
    public static Block leaves_ebony = new BlockLeavesMF("ebony", 1000).setHardness(0.5F);

    public static Block sapling_yew = new BlockSaplingMF("yew", log_yew, leaves_yew, 4F);
    public static Block sapling_ironbark = new BlockSaplingMF("ironbark", log_ironbark, leaves_ironbark, 8F);
    public static Block sapling_ebony = new BlockSaplingMF("ebony", log_ebony, leaves_ebony, 10F);

    public static Block yew_planks = new BasicBlockMF("yew_planks", Material.wood).setHardness(3F).setResistance(6F)
            .setStepSound(Block.soundTypeWood);
    public static Block ironbark_planks = new BasicBlockMF("ironbark_planks", Material.wood).setHardness(4F)
            .setResistance(10F).setStepSound(Block.soundTypeWood);
    public static Block ebony_planks = new BasicBlockMF("ebony_planks", Material.wood).setHardness(6F)
            .setResistance(12F).setStepSound(Block.soundTypeWood);

    public static Block quern = new BlockQuern("quern");

    public static Block mud_brick_stair = new ConstructionBlockMF.StairsConstBlock("mud_brick_stair", mud_brick)
            .register("mud_brick_stair");
    public static Block mud_pavement_stair = new ConstructionBlockMF.StairsConstBlock("mud_pavement_stair",
            mud_pavement).register("mud_pavement_stair");
    public static Block cobble_brick_stair = new ConstructionBlockMF.StairsConstBlock("cobble_brick_stair",
            cobble_brick).register("cobble_brick_stair");
    public static Block cobble_pavement_stair = new ConstructionBlockMF.StairsConstBlock("cobble_pavement_stair",
            cobble_pavement).register("cobble_pavement_stair");
    public static Block firebrick_stair = new ConstructionBlockMF.StairsConstBlock("firebrick_stair", firebricks)
            .register("firebrick_stair");
    public static Block reinforced_stone_stair = new ConstructionBlockMF.StairsConstBlock("reinforced_stone_stair",
            reinforced_stone).register("reinforced_stone_stair");
    public static Block reinforced_stone_brick_stair = new ConstructionBlockMF.StairsConstBlock(
            "reinforced_stone_brick_stair", reinforced_stone_bricks).register("reinforced_stone_brick_stair");

    public static Block yew_stair = new ConstructionBlockMF.StairsConstBlock("yew_stair", yew_planks)
            .register("yew_stair");
    public static Block ironbark_stair = new ConstructionBlockMF.StairsConstBlock("ironbark_stair", ironbark_planks)
            .register("ironbark_stair");
    public static Block ebony_stair = new ConstructionBlockMF.StairsConstBlock("ebony_stair", ebony_planks)
            .register("ebony_stair");

    public static Block firepit = new BlockFirepit();
    public static Block roast = new BlockRoast(0, "basic", false);
    public static Block oven_stone = new BlockRoast(0, "basic", true);

    public static Block furnace_heater = new BlockBigFurnace("furnace_heater", true, -1);
    public static Block furnace_stone = new BlockBigFurnace("furnace_stone", false, 0);

    public static Block rack_wood = new BlockRack("rack_wood");
    public static Block food_box_basic = new BlockAmmoBox("food_box_basic", (byte) 0);
    public static Block ammo_box_basic = new BlockAmmoBox("ammo_box_basic", (byte) 1);
    public static Block crate_basic = new BlockAmmoBox("crate_basic", (byte) 2);

    public static Block bedroll = new BlockBedMF("bedroll");
    public static Block cogwork_helm = new BlockCogwork("cogwork_helm", false);
    public static Block cogwork_legs = new BlockCogwork("cogwork_legs", false);
    public static Block cogwork_chest = new BlockCogwork("cogwork_chest", true);
    public static Block frame_block = new BlockFrame("frame_block");
    public static Block cogwork_builder = new BlockFrame("cogwork_builder", frame_block).setCogworkHolder();

    public static Block cruciblemythic = new BlockCrucible("mythic", 2, false).setAuto().setBlockUnbreakable();
    public static Block cruciblemythic_active = new BlockCrucible("mythic", 2, true).setAuto().setBlockUnbreakable()
            .setLightLevel(12F);
    public static Block cruciblemaster = new BlockCrucible("master", 3, false).setAuto().setBlockUnbreakable();
    public static Block cruciblemaster_active = new BlockCrucible("master", 3, true).setAuto().setBlockUnbreakable()
            .setLightLevel(12F);

    public static Block mythic_decor = new BlockMythicDecor();
    public static Block WG_Mark = new BlockWorldGenMarker();
    public static Block components = new BlockComponent();
    public static Block schematic_general = new BlockSchematic("schematic_general");

    public static void load() {
        anvilStone = new BlockAnvilMF(BaseMaterialMF.getMaterial("stone"));
        // 5:20 default planks
        Blocks.fire.setFireInfo(refined_planks, 3, 10);
        Blocks.fire.setFireInfo(log_ironbark, 2, 8);// IRONBARK: Logs are resistant to fire but raw planks are not
        Blocks.fire.setFireInfo(ironbark_planks, 5, 30);
        Blocks.fire.setFireInfo(log_ebony, 3, 10);// EBONY: Resistant
        Blocks.fire.setFireInfo(ebony_planks, 3, 10);

        for (int a = 0; a < specialMetalBlocks.length; a++) {
            BaseMaterialMF material = BaseMaterialMF.getMaterial(specialMetalBlocks[a]);
            if (material != null) {
                bars[a] = new BlockMetalBarsMF(material);
            }
        }
        for (int a = 0; a < metalBlocks.length; a++) {
            BaseMaterialMF material = BaseMaterialMF.getMaterial(metalBlocks[a]);
            if (material != null) {
                storage[a] = new BlockMetalMF(material);
            }
        }
        for (int a = 0; a < anvils.length; a++) {
            BaseMaterialMF material = BaseMaterialMF.getMaterial(anvils[a]);
            if (material != null) {
                anvil[a] = new BlockAnvilMF(material);
            }
        }

        OreDictionary.registerOre("cobblestone", new ItemStack(limestone, 1, 1));
        OreDictionary.registerOre("stone", new ItemStack(limestone, 1, 0));
        OreDictionary.registerOre("limestone", new ItemStack(limestone, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("cobblestone", new ItemStack(cobble_brick, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("cobblestone", new ItemStack(cobble_pavement, 1, OreDictionary.WILDCARD_VALUE));

        OreDictionary.registerOre("blockGlass", new ItemStack(window, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("blockGlass", new ItemStack(framed_glass, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("paneGlass", new ItemStack(window_pane, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("paneGlass", new ItemStack(framed_pane, 1, OreDictionary.WILDCARD_VALUE));

        OreDictionary.registerOre("planksOakWood", new ItemStack(Blocks.planks, 1, 0));
        OreDictionary.registerOre("planksSpruceWood", new ItemStack(Blocks.planks, 1, 1));
        OreDictionary.registerOre("planksBirchWood", new ItemStack(Blocks.planks, 1, 2));
        OreDictionary.registerOre("planksJungleWood", new ItemStack(Blocks.planks, 1, 3));
        OreDictionary.registerOre("planksAcaciaWood", new ItemStack(Blocks.planks, 1, 4));
        OreDictionary.registerOre("planksDarkOakWood", new ItemStack(Blocks.planks, 1, 5));

        OreDictionary.registerOre("planksIronbarkWood", ironbark_planks);
        OreDictionary.registerOre("planksEbonyWood", ebony_planks);
        OreDictionary.registerOre("planksYewWood", yew_planks);
        OreDictionary.registerOre("planksIronbarkWood", ironbark_planks);
        OreDictionary.registerOre("planksEbonyWood", ebony_planks);
        OreDictionary.registerOre("planksYewWood", yew_planks);

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
