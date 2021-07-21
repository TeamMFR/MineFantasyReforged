package minefantasy.mfr.util;

import minefantasy.mfr.MineFantasyReforged;

public class MFRLogUtil {

	public static void log(String mes) {
		MineFantasyReforged.LOG.info(mes);
	}

	public static void logWarn(String mes) {
		MineFantasyReforged.LOG.warn(mes);
	}

	public static void logDebug(String mes) {
		MineFantasyReforged.LOG.debug(mes);
	}

}
