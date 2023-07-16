package minefantasy.mfr.config;

import minefantasy.mfr.constants.Constants;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public abstract class ConfigurationBaseMF {
	protected final Configuration config;

	public ConfigurationBaseMF(Configuration config) {
		this.config = config;
		load();
	}

	public ConfigurationBaseMF(String name) {
		this(getConfigFor(name));
	}

	public void load() {
		initializeCategories();
		initializeValues();
		save();
	}

	public void save() {
		if (config.hasChanged())
			config.save();
	}

	protected abstract void initializeCategories();

	protected abstract void initializeValues();

	public static Configuration getConfigFor(String name) {
		return new Configuration(new File("config/" + Constants.CONFIG_DIRECTORY + "/",
				name + ".cfg"));
	}

	public Configuration getConfig() {
		return this.config;
	}
}
