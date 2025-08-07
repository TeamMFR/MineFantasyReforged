package minefantasy.mfr.config;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

public class ConfigKnowledgeBook extends ConfigurationBaseMF{

	public static final String CATEGORY_KNOWLEDGE_BOOK_CATEGORIES = "1: Knowledge Book Categories";
	public static final String CATEGORY_KNOWLEDGE_BOOK_ENTRIES = "2: Knowledge Book Entries";


	public ConfigKnowledgeBook(String name) {
		super(name);
	}

	@Override
	protected void initializeCategories() {
		config.addCustomCategoryComment(CATEGORY_KNOWLEDGE_BOOK_CATEGORIES, "Controls if the Knowledge Book Category will be loaded");
		config.addCustomCategoryComment(CATEGORY_KNOWLEDGE_BOOK_ENTRIES, "Controls if the Knowledge Book Entry will be loaded");
	}

	@Override
	protected void initializeValues() {

	}

	public static boolean isKnowledgeBookCategoryEnabled(ResourceLocation key) {
		//Checks if the given Knowledge Book Category should be loaded by default.
		//If an entry for it does not exist, it will be added when queried, defaulting to try
		String name = String.valueOf(key);
		return get().getBoolean(name, CATEGORY_KNOWLEDGE_BOOK_CATEGORIES, true, "");
	}

	public static boolean isKnowledgeBookEntryEnabled(ResourceLocation key) {
		//Checks if the given Knowledge Book Entry should be loaded by default.
		//If an entry for it does not exist, it will be added when queried, defaulting to try
		String name = String.valueOf(key);
		return get().getBoolean(name, CATEGORY_KNOWLEDGE_BOOK_ENTRIES, true, "");
	}

	public static Configuration get() {
		return MineFantasyReforged.configKnowledgeBook.getConfig();
	}
}
