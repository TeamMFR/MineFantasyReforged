package minefantasy.mfr.config;

public class ConfigIntegration extends ConfigurationBaseMF {

	public static final String CATEGORY_MODS = "Cross-mod Integration";
	public static boolean jeiIntegration;

	@Override
	protected void loadConfig() {
		// TODO: not implemented yet but we'll need it anyways so I'm just leaving this setting here
		jeiIntegration = Boolean.parseBoolean(
				config.get(CATEGORY_MODS, "JEI Integration", true, "Enable Just Enough Items integration").getString());
	}
}
