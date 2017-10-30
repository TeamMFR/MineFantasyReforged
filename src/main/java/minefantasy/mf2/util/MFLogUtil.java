package minefantasy.mf2.util;

import cpw.mods.fml.common.FMLLog;
import minefantasy.mf2.MineFantasyII;
import org.apache.logging.log4j.Logger;

public class MFLogUtil {

    public static final Logger MF_LOGGER = FMLLog.getLogger();

    public static final String PREFIX = "[MineFantasyII]: ";

    public static void log(String mes) {
        FMLLog.info(PREFIX + mes);
        // MF_LOGGER.log(Level.INFO, PREFIX + mes);
    }

    public static void logWarn(String mes) {
        FMLLog.warning(PREFIX + "(warning) " + mes);
        // MF_LOGGER.log(Level.WARN, PREFIX + mes);
    }

    public static void logDebug(String mes) {
        if (MineFantasyII.isDebug()) {
            FMLLog.info(PREFIX + "(debug) " + mes);
            // MF_LOGGER.debug(PREFIX + mes);
        }
    }

}
