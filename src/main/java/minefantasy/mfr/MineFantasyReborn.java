package minefantasy.mfr;

import codechicken.lib.CodeChickenLib;
import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.api.armour.CustomArmourEntry;
import minefantasy.mfr.commands.CommandMF;
import minefantasy.mfr.config.ConfigArmour;
import minefantasy.mfr.config.ConfigClient;
import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.config.ConfigExperiment;
import minefantasy.mfr.config.ConfigFarming;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.config.ConfigIntegration;
import minefantasy.mfr.config.ConfigItemRegistry;
import minefantasy.mfr.config.ConfigMobs;
import minefantasy.mfr.config.ConfigStamina;
import minefantasy.mfr.config.ConfigTools;
import minefantasy.mfr.config.ConfigWeapon;
import minefantasy.mfr.config.ConfigWorldGen;
import minefantasy.mfr.init.ArtefactListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CustomArmourListMFR;
import minefantasy.mfr.init.CustomToolListMFR;
import minefantasy.mfr.init.DragonforgedStyle;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.init.KnowledgeListMFR;
import minefantasy.mfr.init.LeatherArmourListMFR;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyLoot;
import minefantasy.mfr.init.OreDictListMFR;
import minefantasy.mfr.init.OrnateStyle;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.material.BaseMaterialMFR;
import minefantasy.mfr.material.LeatherMaterial;
import minefantasy.mfr.material.MetalMaterial;
import minefantasy.mfr.material.WoodMaterial;
import minefantasy.mfr.mechanics.worldGen.WorldGenBiological;
import minefantasy.mfr.mechanics.worldGen.WorldGenMFBase;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.proxy.CommonProxy;
import minefantasy.mfr.recipe.BasicRecipesMF;
import minefantasy.mfr.recipe.CarpenterRecipeManager;
import net.minecraft.init.Items;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
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

@Mod(modid = MineFantasyReborn.MOD_ID, name = MineFantasyReborn.NAME, version = "@VERSION@", dependencies = "required:forge@[0.000.000.001,);" + CodeChickenLib.MOD_VERSION_DEP)
public class MineFantasyReborn {
	public static final String MOD_ID = "minefantasyreborn";
	public static final String NAME = "MineFantasy Reborn";

	public static final WorldGenMFBase worldGenManager = new WorldGenMFBase();

	@SidedProxy(clientSide = "minefantasy.mfr.proxy.ClientProxy", serverSide = "minefantasy.mfr.proxy.ServerProxy")
	public static CommonProxy PROXY;

	@Mod.Instance
	public static MineFantasyReborn INSTANCE;

	public static final Logger LOG = LogManager.getLogger(MOD_ID);

	private static Configuration getCfg(FMLPreInitializationEvent event, String name) {
		return new Configuration(new File(event.getModConfigurationDirectory(), "MineFantasyReborn/" + name + ".cfg"));
	}

	public static boolean isDebug() {
		return ConfigExperiment.debug.equals("AU32-Db42-Acf6-Ggh9-9E8d");
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
		new ConfigExperiment().setConfig(getCfg(preEvent, "Specials"));
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

		CarpenterRecipeManager.INSTANCE.initializeAndExportDefaults();
		CarpenterRecipeManager.INSTANCE.loadRecipes();

		MineFantasyRebornAPI.isInDebugMode = isDebug();
		MineFantasyReborn.LOG.info("API Debug mode updated: " + MineFantasyRebornAPI.isInDebugMode);

		BaseMaterialMFR.init();
		WoodMaterial.init();
		MetalMaterial.init();
		LeatherMaterial.init();

		ComponentListMFR.init();
		ToolListMFR.init();
		CustomToolListMFR.init();
		CustomArmourListMFR.init();
		LeatherArmourListMFR.init();
		FoodListMFR.init();
		DragonforgedStyle.init();
		OrnateStyle.init();

		MineFantasyBlocks.init();


		MineFantasyLoot.load();
		MineFantasyBlocks.load();
		ComponentListMFR.load();
		ToolListMFR.load();
		FoodListMFR.load();

		PROXY.registerTickHandlers();

		PROXY.registerMain();

		PROXY.preInit(preEvent);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);


		CarpenterRecipeManager.loadRecipesFromSource(Loader.instance().activeModContainer().getSource(), CarpenterRecipeManager.DEFAULT_RECIPE_DIRECTORY);

		GameRegistry.registerWorldGenerator(worldGenManager, 0);


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

		CustomArmourEntry.registerItem(Items.GOLDEN_HELMET, ArmourDesign.SOLID, 1.5F, "medium");
		CustomArmourEntry.registerItem(Items.GOLDEN_CHESTPLATE, ArmourDesign.SOLID, 1.5F, "medium");
		CustomArmourEntry.registerItem(Items.GOLDEN_LEGGINGS, ArmourDesign.SOLID, 1.5F, "medium");
		CustomArmourEntry.registerItem(Items.GOLDEN_BOOTS, ArmourDesign.SOLID, 1.5F, "medium");

		ConfigItemRegistry.readCustoms();

		for (Biome biome : Biome.REGISTRY) {
			registerBiomeStuff(biome);
		}

		KnowledgeListMFR.init();
		ArtefactListMFR.init();
		BasicRecipesMF.init();

		OreDictListMFR.registerOreDictEntries();

		MetalMaterial.addHeatables();

		PROXY.postInit(postEvent);

		// enabling this will dump all carpenter recipes to jsons! see RecipeExporter for path
		// RecipeExporter exporter = new RecipeExporter();
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandMF());
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
