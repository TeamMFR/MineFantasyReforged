package minefantasy.mfr;

import codechicken.lib.CodeChickenLib;
import minefantasy.mfr.config.ConfigExperiment;
import minefantasy.mfr.proxy.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = MineFantasyReborn.MODID, name = MineFantasyReborn.NAME, version = MineFantasyReborn.VERSION, dependencies = "required:forge@[0.000.000.001,);" + CodeChickenLib.MOD_VERSION_DEP)
public class MineFantasyReborn {
    public static final String MODID = "minefantasyreborn";
    public static final String NAME = "MineFantasyReborn";
    public static final String VERSION = "1.0.0";

    @SidedProxy(clientSide = "minefantasy.mfr.proxy.ClientProxy", serverSide = "minefantasy.mfr.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static MineFantasyReborn instance;

    private static Logger logger;
    public static boolean isDebug() {
        return ConfigExperiment.debug.equals("AU32-Db42-Acf6-Ggh9-9E8d");
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
