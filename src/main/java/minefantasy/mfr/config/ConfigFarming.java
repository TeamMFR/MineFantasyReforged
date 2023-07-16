package minefantasy.mfr.config;

import minefantasy.mfr.api.farming.FarmingHelper;

public class ConfigFarming extends ConfigurationBaseMF {

	public static final String CATEGORY_PENALTIES = "Penalties";

	public ConfigFarming(String name) {
		super(name);
	}

	@Override
	protected void initializeCategories() {
		config.addCustomCategoryComment(CATEGORY_PENALTIES, "Controls farming penalties");
	}

	@Override
	protected void initializeValues() {
		FarmingHelper.isEnabled = Boolean.parseBoolean(
				config.get("##Enable System##", "isEnabled", true, "This toggles the farming mechanics").getString());

		FarmingHelper.hoeFailChanceCfg = Float.parseFloat(config
				.get(CATEGORY_PENALTIES, "Hoe Fail Modifier", 1.0F, "Modifies the rate for hoes to fail making fields")
				.getString());
		FarmingHelper.farmBreakCfg = Float.parseFloat(config
				.get(CATEGORY_PENALTIES, "Harvest Ruin Modifier", 1.0F,
						"Modifies the rate for ruining fields when harvesting (scythes increase this chance)")
				.getString());
	}

}
