package minefantasy.mf2.config;

import net.minecraftforge.common.config.Configuration;

public abstract class ConfigurationBaseMF {
    protected Configuration config;

    public void setConfig(Configuration configuration) {
        configuration.load();
        config = configuration;
        loadConfig();
        configuration.save();
    }

    protected abstract void loadConfig();
}
