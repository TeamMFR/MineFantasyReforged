package minefantasy.mfr.knowledge;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.constants.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class KnowledgeManagerResearchBook {
	public static final String RESEARCH_BOOK_PATH = "/research/research_book";
	public static final String RESEARCH_BOOK_CATEGORIES_PATH = "/categories";
	public static final String RESEARCH_BOOK_ENTRIES_PATH = "/entries";

	public static final String CONFIG_RESEARCH_BOOK_DIRECTORY = "config/"
			+ Constants.CONFIG_DIRECTORY
			+ "/custom/research/research_book";

	public KnowledgeManagerResearchBook() {
	}

	private static final IForgeRegistry<ResearchBookCategoryBase> RESEARCH_BOOK_CATEGORIES = (new RegistryBuilder<ResearchBookCategoryBase>())
			.setName(new ResourceLocation(MineFantasyReforged.MOD_ID, "research_book_categories"))
			.setType(ResearchBookCategoryBase.class).setMaxID(Integer.MAX_VALUE >> 5)
			.disableSaving().allowModification().create();

	private static final IForgeRegistry<ResearchBookEntryBase> RESEARCH_BOOK_ENTRIES = (new RegistryBuilder<ResearchBookEntryBase>())
			.setName(new ResourceLocation(MineFantasyReforged.MOD_ID, "research_book_entries"))
			.setType(ResearchBookEntryBase.class).setMaxID(Integer.MAX_VALUE >> 5)
			.disableSaving().allowModification().create();


}
