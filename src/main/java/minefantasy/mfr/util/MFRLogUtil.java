package minefantasy.mfr.util;

import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Logger;

public class MFRLogUtil {

	public static final Logger MFR_LOGGER = FMLLog.getLogger();

	public static final String PREFIX = "[MineFantasyReforged]: ";

	public static void log(String mes) {
		FMLLog.info(PREFIX + mes);
		// MF_LOGGER.log(Level.INFO, PREFIX + mes);
	}

	public static void logWarn(String mes) {
		FMLLog.warning(PREFIX + "(warning) " + mes);
		// MF_LOGGER.log(Level.WARN, PREFIX + mes);
	}

	public static void logDebug(String mes) {
		FMLLog.info(PREFIX + "(debug) " + mes);
		// MF_LOGGER.debug(PREFIX + mes);
	}

}
