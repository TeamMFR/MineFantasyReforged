package minefantasy.mf2.config;

import java.util.Arrays;

public class ConfigIntegration extends ConfigurationBaseMF {

	public static final String CATEGORY_BUKKIT = "Bukkit Integration";

	public static String[] pluginsList = new String[0];

	@Override
	protected void loadConfig() {
		String bpDesc = "This registers listeners of Bukkit plugins (if avaliable). Here you are should list all protection-related plugins (e.g WorldGuard). You are can find correct plugin name in its plugin.yml (inside plugin's .jar file)";
		pluginsList = config.get(CATEGORY_BUKKIT, "Plugins List", new String[] { "WorldGuard" }, bpDesc)
				.getStringList();
		Arrays.sort(pluginsList);
	}
}
