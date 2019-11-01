package minefantasy.mf2;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.armour.ArmourDesign;
import minefantasy.mf2.api.armour.CustomArmourEntry;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.commands.CommandMF;
import minefantasy.mf2.config.*;
import minefantasy.mf2.integration.minetweaker.MTCompat;
import minefantasy.mf2.item.gadget.ItemLootSack;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.knowledge.ArtefactListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import minefantasy.mf2.material.BaseMaterialMF;
import minefantasy.mf2.material.MetalMaterial;
import minefantasy.mf2.mechanics.worldGen.WorldGenBiological;
import minefantasy.mf2.mechanics.worldGen.WorldGenMFBase;
import minefantasy.mf2.network.CommonProxyMF;
import minefantasy.mf2.network.packet.PacketHandlerMF;
import minefantasy.mf2.recipe.BasicRecipesMF;
import minefantasy.mf2.recipe.RecipeRemover;
import minefantasy.mf2.util.BukkitUtils;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * @author Anonymous Productions
 */
@Mod(modid = MineFantasyII.MODID, name = MineFantasyII.NAME, dependencies = "required-after:Forge@[7.0,);" + "required-after:FML@[5.0.5,);" + "after:NotEnoughItems;" + "after:MineTweaker3;" + "after:BuildCraft|Core;" + "after:CoFHCore;" + "after:battlegear2", version = MineFantasyII.VERSION)
public class MineFantasyII {
    public static final String MODID = "minefantasy2";
    public static final String NAME = "MineFantasyII";
    public static final String VERSION = "@VERSION@";
    public static final WorldGenMFBase worldGenManager = new WorldGenMFBase();

    @SidedProxy(clientSide = "minefantasy.mf2.network.ClientProxyMF", serverSide = "minefantasy.mf2.network.CommonProxyMF")
    public static CommonProxyMF proxy;
    public static PacketHandlerMF packetHandler;

    @Instance(MODID)
    public static MineFantasyII instance;

    private static boolean isBukkitServer;

    private static Configuration getCfg(FMLPreInitializationEvent event, String name) {
        return new Configuration(new File(event.getModConfigurationDirectory(), "MineFantasyII/" + name + ".cfg"));
    }

    public static boolean isBukkitServer() {
        return isBukkitServer;
    }

    public static boolean setIsBukkitServer() {
        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
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
    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            new ConfigClient().setConfig(getCfg(event, "Client"));
        }
        new ConfigArmour().setConfig(getCfg(event, "Armours"));
        new ConfigExperiment().setConfig(getCfg(event, "Specials"));
        new ConfigHardcore().setConfig(getCfg(event, "Hardcore"));
        new ConfigIntegration().setConfig(getCfg(event, "Integration"));
        new ConfigTools().setConfig(getCfg(event, "Tools"));
        new ConfigWeapon().setConfig(getCfg(event, "Weapons"));
        new ConfigStamina().setConfig(getCfg(event, "Stamina_System"));
        new ConfigItemRegistry().setConfig(getCfg(event, "Item_Registry"));
        new ConfigFarming().setConfig(getCfg(event, "Farming"));
        new ConfigWorldGen().setConfig(getCfg(event, "WorldGen"));
        new ConfigCrafting().setConfig(getCfg(event, "Crafting"));
        new ConfigMobs().setConfig(getCfg(event, "Mobs"));

        BaseMaterialMF.init();
        MineFantasyAPI.isInDebugMode = isDebug();
        MFLogUtil.log("API Debug mode updated: " + MineFantasyAPI.isInDebugMode);

        addModFlags();
        proxy.preInit();

        RecipeRemover.removeRecipes();
        ToolListMF.load();
        ComponentListMF.load();
    }

    @EventHandler
    public void load(FMLInitializationEvent evt) {
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
    public void modsLoaded(FMLPostInitializationEvent evt) {
        CustomArmourEntry.registerItem(Items.leather_helmet, ArmourDesign.LEATHER);
        CustomArmourEntry.registerItem(Items.leather_chestplate, ArmourDesign.LEATHER);
        CustomArmourEntry.registerItem(Items.leather_leggings, ArmourDesign.LEATHER);
        CustomArmourEntry.registerItem(Items.leather_boots, ArmourDesign.LEATHER);

        CustomArmourEntry.registerItem(Items.chainmail_helmet, ArmourDesign.MAIL);
        CustomArmourEntry.registerItem(Items.chainmail_chestplate, ArmourDesign.MAIL);
        CustomArmourEntry.registerItem(Items.chainmail_leggings, ArmourDesign.MAIL);
        CustomArmourEntry.registerItem(Items.chainmail_boots, ArmourDesign.MAIL);

        CustomArmourEntry.registerItem(Items.golden_helmet, ArmourDesign.SOLID, 1.5F, "medium");
        CustomArmourEntry.registerItem(Items.golden_chestplate, ArmourDesign.SOLID, 1.5F, "medium");
        CustomArmourEntry.registerItem(Items.golden_leggings, ArmourDesign.SOLID, 1.5F, "medium");
        CustomArmourEntry.registerItem(Items.golden_boots, ArmourDesign.SOLID, 1.5F, "medium");

        ConfigItemRegistry.readCustoms();

        for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
            registerBiomeStuff(biome);
        }
        KnowledgeListMF.init();
        ArtefactListMF.init();
        BasicRecipesMF.init();
        ItemLootSack.addItems();
        proxy.postInit();
        proxy.registerTickHandlers();
        MetalMaterial.addHeatables();
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandMF());
    }

    @EventHandler
    public final void serverStarted(FMLServerStartedEvent event) {
        if (event.getSide() != Side.CLIENT && isBukkitServer()) {
            MFLogUtil.log("Bukkit has been detected");
            BukkitUtils.onServerStarted();
        }

        if (Loader.isModLoaded("MineTweaker3") && ConfigIntegration.mtIntegration) {
            MTCompat.registerCommands();
        }
    }

    private void registerBiomeStuff(BiomeGenBase biome) {
        if (WorldGenBiological.isBiomeInConstraint(biome, ConfigWorldGen.berryMinTemp, ConfigWorldGen.berryMaxTemp,
                ConfigWorldGen.berryMinRain, ConfigWorldGen.berryMaxRain)) {
            biome.addFlower(BlockListMF.berryBush, 0, 5);
        }
    }

    private void addModFlags() {
        isBukkitServer = setIsBukkitServer();
    }
}
