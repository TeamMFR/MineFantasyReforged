package minefantasy.mfr.registry.knowledge;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.config.ConfigResearch;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.registry.knowledge.factories.ResearchFactory;
import minefantasy.mfr.registry.knowledge.types.ResearchType;
import minefantasy.mfr.util.FileUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class KnowledgeManagerResearch {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final String RESEARCH_FOLDER_PATH = Constants.ASSET_DIRECTORY + "/knowledge_mfr/researches";
	public static final String CONFIG_RESEARCH_DIRECTORY = "config/"
			+ Constants.CONFIG_DIRECTORY
			+ "/custom/knowledge/researches";
	private final ResearchFactory researchFactory = new ResearchFactory();

	public static final ResearchBase NONE = new ResearchBase("none", Collections.emptyList(), 0,Collections.emptyList(), false);
	public static final ResearchBase TIER = new ResearchBase("tier", Collections.emptyList(), 0, Collections.emptyList(), false);

	public void init() {
		//call this so that the static final gets initialized at proper time
	}

	private static final IForgeRegistry<ResearchBase> RESEARCHES = (new RegistryBuilder<ResearchBase>())
			.setName(new ResourceLocation(MineFantasyReforged.MOD_ID, "researches"))
			.setType(ResearchBase.class).setMaxID(Integer.MAX_VALUE >> 5)
			.disableSaving().allowModification().create();

	public static HashMap<ResearchBase, List<ResourceLocation>> PARENT_RESEARCH_KEY_MAP = new HashMap<>();

	public void addResearch(ResearchBase research, ResourceLocation key, boolean checkForExistence) {
		if (ConfigResearch.isResearchEnabled(key)) {

			research.setRegistryName(key);
			if (!checkForExistence || !RESEARCHES.containsKey(research.getRegistryName())) {
				RESEARCHES.register(research);
			}
		}
	}

	public static Collection<ResearchBase> getResearches() {
		return RESEARCHES.getValuesCollection();
	}

	public static List<ResearchBase> getResearchesByKeys(boolean checkForExistence, ResourceLocation... keys) {
		List<ResearchBase> researches = new ArrayList<>();
		for (ResourceLocation key : keys) {
			researches.add(getResearchByKey(key, checkForExistence));
		}
		return researches;
	}

	public static List<ResearchBase> getResearchesByNames(boolean checkForExistence, String modId, String... names) {
		List<ResearchBase> researches = new ArrayList<>();
		for (String name : names) {
			researches.add(getResearchByName(modId, name, checkForExistence));
		}
		return researches;
	}

	public static ResearchBase getResearchByName(String modId, String name, boolean checkForExistence) {
		return getResearchByKey(new ResourceLocation(modId, name), checkForExistence);
	}

	public static ResearchBase getResearchByKey(String key, boolean checkForExistence) {
		if (key.equals("none")) {
			return NONE;
		}
		else {
			return getResearchByKey(new ResourceLocation(key), checkForExistence);
		}
	}
	public static ResearchBase getResearchByKey(ResourceLocation key, boolean checkForExistence) {
		if (checkForExistence && !RESEARCHES.containsKey(key)) {
			MineFantasyReforged.LOG.error("Research Registry does not contain research: {}", key);
		}
		ResearchBase research = RESEARCHES.getValue(key);
		if (research == null) {
			return NONE;
		}
		return research;
	}

	public static List<ResearchBase> getResearchesByItemStack(ItemStack stack) {
		if (stack.isEmpty()) {
			return Collections.emptyList();
		}
 		List<ResearchBase> researches = new ArrayList<>();
		for (ResearchBase research : getResearches()) {
			if (research.getArtifacts().stream().anyMatch(artifact -> artifact.apply(stack))) {
				researches.add(research);
			}
		}
		return !researches.isEmpty() ? researches : Collections.emptyList();
	}

	public void loadResearches() {
		ModContainer modContainer = Loader.instance().activeModContainer();

		FileUtils.createCustomDataDirectory(CONFIG_RESEARCH_DIRECTORY);
		//noinspection ConstantConditions
		loadResearches(modContainer, new File(CONFIG_RESEARCH_DIRECTORY), "");
		Loader.instance().getActiveModList().forEach(m ->
				loadResearches(m, m.getSource(), String.format(RESEARCH_FOLDER_PATH, m.getModId())));

		Loader.instance().setActiveModContainer(modContainer);
	}

	private void loadResearches(ModContainer mod, File source, String base) {
		JsonContext ctx = new JsonContext(mod.getModId());

		FileUtils.findFiles(source, base, root -> FileUtils.loadConstants(source, base, ctx), (root, file) -> {
			Path relative = root.relativize(file);
			if (relative.getNameCount() > 1) {
				String extension = FilenameUtils.getExtension(file.toString());

				if (!extension.equals(Constants.JSON_FILE_EXT)) {
					return;
				}

				String modName = relative.getName(relative.getNameCount() - 2).toString();
				String fileName = FilenameUtils.removeExtension(relative.getFileName().toString());

				if (!Loader.isModLoaded(modName) || fileName.startsWith("_")) {
					return;
				}

				Loader.instance().setActiveModContainer(mod);

				if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.toString().startsWith("_"))
					return;

				ResourceLocation key = new ResourceLocation(ctx.getModId(), fileName);

				BufferedReader reader = null;
				try {
					reader = Files.newBufferedReader(file);
					JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);

					String type = ctx.appendModId(JsonUtils.getString(json, "type"));
					if (Loader.isModLoaded(mod.getModId())) {
						if (ResearchType.getByNameWithModId(type, mod.getModId()) != ResearchType.NONE) {
							ResearchBase research = researchFactory.parse(ctx, json);
							key = new ResourceLocation(ctx.getModId(), research.getName());
							addResearch(research, key, mod.getModId().equals(MineFantasyReforged.MOD_ID));
						} else {
							MineFantasyReforged.LOG.info("Skipping research {} of type {} because it's not a MFR research", key, type);
						}
					}
					else {
						MineFantasyReforged.LOG.info("Skipping research {} of type {} because it the mod it depends on is not loaded", key, type);
					}
				}
				catch (JsonParseException e) {
					MineFantasyReforged.LOG.error("Parsing error loading research {}", key, e);
				}
				catch (IOException e) {
					MineFantasyReforged.LOG.error("Couldn't read research {} from {}", key, file, e);
				}
				finally {
					IOUtils.closeQuietly(reader);
				}
			}
		});
	}

	public void addParentResearches() {
		addResearch(TIER, new ResourceLocation(MineFantasyReforged.MOD_ID, "tier"), false);
		for (ResearchBase research : getResearches()) {
			List<ResourceLocation> parentResearchKeyList = PARENT_RESEARCH_KEY_MAP.get(research);
			if (parentResearchKeyList != null && !parentResearchKeyList.isEmpty()) {
				List<ResearchBase> parentResearches = new ArrayList<>();
				for (ResourceLocation parentResearchKey : parentResearchKeyList) {
					parentResearches.add(getResearchByKey(parentResearchKey, true));
				}

				research.setParentResearches(parentResearches);
			}
		}
	}
}
