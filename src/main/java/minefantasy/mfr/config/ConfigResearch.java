package minefantasy.mfr.config;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.mechanics.knowledge.InformationBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

public class ConfigResearch extends ConfigurationBaseMF{

	public static final String CATEGORY_GENERAL = "1: General Settings";
	public static final String CATEGORY_RESEARCH = "2: Researches";
	public static int KNOWLEDGE_LAYER;
	public static boolean UNLOCK_ALL;


	public ConfigResearch(String name) {
		super(name);
	}

	@Override
	protected void initializeCategories() {
		config.addCustomCategoryComment(CATEGORY_GENERAL, "Controls Research settings");
		config.addCustomCategoryComment(CATEGORY_RESEARCH, "Controls if the Research will be loaded");
	}

	@Override
	protected void initializeValues() {
		KNOWLEDGE_LAYER = Integer.parseInt(config.get(CATEGORY_GENERAL, "###CHANGE RESEARCH ID###", 0,
				"This changes the research ID, removing all entries").getString());
		UNLOCK_ALL = Boolean.parseBoolean(config.get(CATEGORY_GENERAL, "Unlock entries", false,
				"If you don't want to research, this will unlock all entries.").getString());
		InformationBase.easyResearch = Boolean.parseBoolean(config.get(CATEGORY_GENERAL, "Baby-Mode Research", false,
				"This removes the process of examining artefacts, research is unlocked by clicking entries in the book.").getString());
	}

	public static boolean isResearchEnabled(ResourceLocation key) {
		//Checks if the given Research should be loaded by default.
		//If an entry for it does not exist, it will be added when queried, defaulting to try
		String name = String.valueOf(key);
		return get().getBoolean(name, CATEGORY_RESEARCH, true, "");
	}

	public static Configuration get() {
		return MineFantasyReforged.configResearch.getConfig();
	}
}
