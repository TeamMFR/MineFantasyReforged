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
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.KnowledgeListMFR;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.item.gadget.ItemLootSack;
import minefantasy.mfr.material.BaseMaterialMFR;
import minefantasy.mfr.material.MetalMaterial;
import minefantasy.mfr.mechanics.worldGen.WorldGenBiological;
import minefantasy.mfr.mechanics.worldGen.WorldGenMFBase;
import minefantasy.mfr.packet.PacketHandlerMF;
import minefantasy.mfr.proxy.CommonProxy;
import minefantasy.mfr.recipe.BasicRecipesMF;
import minefantasy.mfr.recipe.RecipeRemover;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.block.state.IBlockState;
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
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;

@Mod(modid = MineFantasyReborn.MOD_ID, name = MineFantasyReborn.NAME, version = MineFantasyReborn.VERSION, dependencies = "required:forge@[0.000.000.001,);" + CodeChickenLib.MOD_VERSION_DEP)
public class MineFantasyReborn {
    public static final String MOD_ID = "minefantasyreborn";
    public static final String NAME = "MineFantasyReborn";
    public static final String VERSION = "1.0.0";
    public static final WorldGenMFBase worldGenManager = new WorldGenMFBase();

    @SidedProxy(clientSide = "minefantasy.mfr.proxy.ClientProxy", serverSide = "minefantasy.mfr.proxy.ServerProxy")
    public static CommonProxy proxy;
    public static PacketHandlerMF packetHandler;

    @Mod.Instance
    public static MineFantasyReborn instance;

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
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
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

        BaseMaterialMFR.init();
        MineFantasyRebornAPI.isInDebugMode = isDebug();
        MFRLogUtil.log("API Debug mode updated: " + MineFantasyRebornAPI.isInDebugMode);

        proxy.preInit(preEvent);

        RecipeRemover.removeRecipes();
        ToolListMFR.load();
        ComponentListMFR.load();
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        proxy.registerMain();
        GameRegistry.registerWorldGenerator(worldGenManager, 0);

        packetHandler = new PacketHandlerMF();
        FMLEventChannel eventChannel;
        for (String channel : packetHandler.packetList.keySet()) {
            eventChannel = NetworkRegistry.INSTANCE.newEventDrivenChannel(channel);
            eventChannel.register(packetHandler);
            packetHandler.channels.put(channel, eventChannel);
        }
    }

    @EventHandler
    public void modsLoaded(FMLPostInitializationEvent postEvent) {
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
        proxy.postInit(postEvent);
        proxy.registerTickHandlers();
        MetalMaterial.addHeatables();
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
            biome.addFlower((IBlockState) BlockListMFR.BERRY_BUSH, 5);
        }
    }
}
