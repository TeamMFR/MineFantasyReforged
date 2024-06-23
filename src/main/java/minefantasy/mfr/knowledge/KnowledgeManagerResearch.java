package minefantasy.mfr.knowledge;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.constants.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class KnowledgeManagerResearch {

	public static final String RESEARCH_FOLDER_PATH = "/research/researches";

	public static final String CONFIG_RESEARCH_DIRECTORY = "config/"
			+ Constants.CONFIG_DIRECTORY
			+ "/custom/research/researches";

	public KnowledgeManagerResearch() {

	}

	private static final IForgeRegistry<ResearchBase> RESEARCHES = (new RegistryBuilder<ResearchBase>())
			.setName(new ResourceLocation(MineFantasyReforged.MOD_ID, "researches"))
			.setType(ResearchBase.class).setMaxID(Integer.MAX_VALUE >> 5)
			.disableSaving().allowModification().create();
}
