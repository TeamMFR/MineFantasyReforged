package minefantasy.mfr;

import codechicken.lib.CodeChickenLib;
import minefantasy.mfr.api.MineFantasyReforgedAPI;
import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.api.armour.CustomArmourEntry;
import minefantasy.mfr.commands.CommandMFR;
import minefantasy.mfr.config.ConfigArmour;
import minefantasy.mfr.config.ConfigClient;
import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.config.ConfigFarming;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.config.ConfigIntegration;
import minefantasy.mfr.config.ConfigItemRegistry;
import minefantasy.mfr.config.ConfigMobs;
import minefantasy.mfr.config.ConfigSpecials;
import minefantasy.mfr.config.ConfigStamina;
import minefantasy.mfr.config.ConfigTools;
import minefantasy.mfr.config.ConfigWeapon;
import minefantasy.mfr.config.ConfigWorldGen;
import minefantasy.mfr.data.PlayerData;
import minefantasy.mfr.init.LeatherArmourListMFR;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyKnowledgeList;
import minefantasy.mfr.init.MineFantasyLoot;
import minefantasy.mfr.init.MineFantasyMaterials;
import minefantasy.mfr.init.MineFantasyOreDict;
import minefantasy.mfr.material.MetalMaterial;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.proxy.CommonProxy;
import minefantasy.mfr.recipe.AnvilRecipeLoader;
import minefantasy.mfr.recipe.BasicRecipesMF;
import minefantasy.mfr.recipe.CarpenterRecipeLoader;
import minefantasy.mfr.registry.MetalMaterialRegistry;
import minefantasy.mfr.registry.WoodMaterialRegistry;
import minefantasy.mfr.world.gen.feature.WorldGenBiological;
import minefantasy.mfr.world.gen.feature.WorldGenGeological;
import minefantasy.mfr.world.gen.structure.WorldGenStructure;
import net.minecraft.init.Items;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = MineFantasyReforged.MOD_ID, name = MineFantasyReforged.NAME, version = "@VERSION@", dependencies = "required:forge@[0.000.000.001,);" + CodeChickenLib.MOD_VERSION_DEP)
public class MineFantasyReforged {
	public static final String MOD_ID = "minefantasyreforged";
	public static final String NAME = "MineFantasy Reforged";

	@SidedProxy(clientSide = "minefantasy.mfr.proxy.ClientProxy", serverSide = "minefantasy.mfr.proxy.ServerProxy")
	public static CommonProxy PROXY;

	@Mod.Instance
	public static MineFantasyReforged INSTANCE;

	public static final Logger LOG = LogManager.getLogger(MOD_ID);

	private static Configuration getCfg(FMLPreInitializationEvent event, String name) {
		return new Configuration(new File(event.getModConfigurationDirectory(), "MineFantasyReforged/" + name + ".cfg"));
	}

	public static boolean isDebug() {
		return ConfigSpecials.debug.equals("AU32-Db42-Acf6-Ggh9-9E8d");
	}

	/**
	 * Determines if a player name is that of a MF modder
	 */
	public static boolean isNameModder(String name) {
		return name.equals("Galactic_Hiker") || name.equals("tim4200") || name.equals("Sirse");
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent preEvent) {
		NetworkHandler.INSTANCE.registerNetwork();

		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			new ConfigClient().setConfig(getCfg(preEvent, "Client"));
		}
		new ConfigArmour().setConfig(getCfg(preEvent, "Armours"));
		new ConfigSpecials().setConfig(getCfg(preEvent, "Specials"));
		new ConfigHardcore().setConfig(getCfg(preEvent, "Hardcore"));
		new ConfigIntegration().setConfig(getCfg(preEvent, "Integration"));
		new ConfigTools().setConfig(getCfg(preEvent, "Tools"));
		new ConfigWeapon().setConfig(getCfg(preEvent, "Weapons"));
		new ConfigStamina().setConfig(getCfg(preEvent, "Stamina_System"));
		new ConfigItemRegistry().setConfig(getCfg(preEvent, "Item_Registry"));
		new ConfigFarming().setConfig(getCfg(preEvent, "Farming"));
		new ConfigWorldGen().setConfig(getCfg(preEvent, "WorldGen"));
		new ConfigCrafting().setConfig(getCfg(preEvent, "Crafting"));
		new ConfigMobs().setConfig(getCfg(preEvent, "Mobs"));

		PlayerData.register();
		MineFantasyItems.initEnumActions();

		//		CarpenterRecipeManager.INSTANCE.initializeAndExportDefaults();
		//		CarpenterRecipeManager.INSTANCE.preInit();

		MineFantasyReforgedAPI.isInDebugMode = isDebug();
		MineFantasyReforged.LOG.info("API Debug mode updated: " + MineFantasyReforgedAPI.isInDebugMode);

		MineFantasyMaterials.initBaseMaterials();
		WoodMaterialRegistry.INSTANCE.preInit();
		MetalMaterialRegistry.INSTANCE.preInit();
		MineFantasyMaterials.initLeatherMaterials();

		MineFantasyLoot.load();

		MineFantasyItems.initComponent();
		MineFantasyItems.initTool();
		MineFantasyItems.initCustomTool();
		MineFantasyItems.initCustomArmor();
		LeatherArmourListMFR.init();
		MineFantasyItems.initFood();
		MineFantasyItems.initDragonforged();
		MineFantasyItems.initOrnate();

		MineFantasyBlocks.init();
		MineFantasyBlocks.load();
		MineFantasyItems.loadComponent();
		MineFantasyItems.loadTool();
		MineFantasyItems.loadSpecialFood();

		PROXY.registerTickHandlers();

		PROXY.registerMain();

		PROXY.preInit(preEvent);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);

		GameRegistry.registerWorldGenerator(new WorldGenBiological(), 5);
		GameRegistry.registerWorldGenerator(new WorldGenGeological(), 5);
		GameRegistry.registerWorldGenerator(new WorldGenStructure(), 5);

		//		AnvilRecipeManager.loadRecipesFromSource(Loader.instance().activeModContainer().getSource(), AnvilRecipeManager.DEFAULT_RECIPE_DIRECTORY);

		PROXY.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent postEvent) {
		CustomArmourEntry.registerItem(Items.LEATHER_HELMET, ArmourDesign.LEATHER);
		CustomArmourEntry.registerItem(Items.LEATHER_CHESTPLATE, ArmourDesign.LEATHER);
		CustomArmourEntry.registerItem(Items.LEATHER_LEGGINGS, ArmourDesign.LEATHER);
		CustomArmourEntry.registerItem(Items.LEATHER_BOOTS, ArmourDesign.LEATHER);

		CustomArmourEntry.registerItem(Items.CHAINMAIL_HELMET, ArmourDesign.MAIL);
		CustomArmourEntry.registerItem(Items.CHAINMAIL_CHESTPLATE, ArmourDesign.MAIL);
		CustomArmourEntry.registerItem(Items.CHAINMAIL_LEGGINGS, ArmourDesign.MAIL);
		CustomArmourEntry.registerItem(Items.CHAINMAIL_BOOTS, ArmourDesign.MAIL);

		CustomArmourEntry.registerItem(Items.IRON_HELMET, ArmourDesign.SOLID, 1.5F, "medium");
		CustomArmourEntry.registerItem(Items.IRON_CHESTPLATE, ArmourDesign.SOLID, 1.5F, "medium");
		CustomArmourEntry.registerItem(Items.IRON_LEGGINGS, ArmourDesign.SOLID, 1.5F, "medium");
		CustomArmourEntry.registerItem(Items.IRON_BOOTS, ArmourDesign.SOLID, 1.5F, "medium");

		CustomArmourEntry.registerItem(Items.GOLDEN_HELMET, ArmourDesign.SOLID, 2F, "medium");
		CustomArmourEntry.registerItem(Items.GOLDEN_CHESTPLATE, ArmourDesign.SOLID, 2F, "medium");
		CustomArmourEntry.registerItem(Items.GOLDEN_LEGGINGS, ArmourDesign.SOLID, 2F, "medium");
		CustomArmourEntry.registerItem(Items.GOLDEN_BOOTS, ArmourDesign.SOLID, 2F, "medium");
		
		CustomArmourEntry.registerItem(Items.DIAMOND_HELMET, ArmourDesign.SOLID, 2.5F, "medium");
		CustomArmourEntry.registerItem(Items.DIAMOND_CHESTPLATE, ArmourDesign.SOLID, 2.5F, "medium");
		CustomArmourEntry.registerItem(Items.DIAMOND_LEGGINGS, ArmourDesign.SOLID, 2.5F, "medium");
		CustomArmourEntry.registerItem(Items.DIAMOND_BOOTS, ArmourDesign.SOLID, 2.5F, "medium");

		ConfigItemRegistry.readCustoms();

		MineFantasyOreDict.registerOreDictEntries();

		for (Biome biome : Biome.REGISTRY) {
			registerBiomeStuff(biome);
		}

		MineFantasyKnowledgeList.init();
		MineFantasyKnowledgeList.ArtefactListMFR.init();
		BasicRecipesMF.init();

		MetalMaterial.addHeatables();

		MineFantasyOreDict.registerOreDictCommonIngotEntry();

		CarpenterRecipeLoader.INSTANCE.postInit();
		AnvilRecipeLoader.INSTANCE.postInit();

		PROXY.postInit(postEvent);

		// enabling this will dump all carpenter recipes to jsons! see RecipeExporter for path
		// RecipeExporter exporter = new RecipeExporter();
		// RecipeExporterAnvil exporterAnvil = new RecipeExporterAnvil();
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandMFR());
	}

	@EventHandler
	public final void serverStarted(FMLServerStartedEvent event) {

	}

	private void registerBiomeStuff(Biome biome) {
		if (WorldGenBiological.isBiomeInConstraint(biome, ConfigWorldGen.berryMinTemp, ConfigWorldGen.berryMaxTemp,
				ConfigWorldGen.berryMinRain, ConfigWorldGen.berryMaxRain)) {
			biome.addFlower(MineFantasyBlocks.BERRY_BUSH.getDefaultState(), 5);
		}
	}
}
