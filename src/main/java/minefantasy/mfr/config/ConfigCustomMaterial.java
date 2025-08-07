package minefantasy.mfr.config;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

public class ConfigCustomMaterial extends ConfigurationBaseMF {
	private static final String CATEGORY_MATERIAL_SETTINGS = "01 Material Settings";

	public ConfigCustomMaterial(String name) {
		super(name);
	}

	@Override
	protected void initializeCategories() {
		config.addCustomCategoryComment(CATEGORY_MATERIAL_SETTINGS, "Controls whether or not a Custom Material should be loaded");
	}

	@Override
	protected void initializeValues() {

	}

	public static boolean isCustomMaterialEnabled(ResourceLocation key) {
		//Checks if the given Custom Material should be loaded by default
		//If an entry for it does not exist, it will be added when queried, defaulting to try
		String name = String.valueOf(key);
		return get().getBoolean(name, CATEGORY_MATERIAL_SETTINGS, true, "");
	}

	public static Configuration get() {
		return MineFantasyReforged.configCustomMaterial.getConfig();
	}
}
