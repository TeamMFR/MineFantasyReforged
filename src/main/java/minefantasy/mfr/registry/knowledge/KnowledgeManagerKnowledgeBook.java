package minefantasy.mfr.registry.knowledge;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.config.ConfigKnowledgeBook;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.registry.knowledge.factories.KnowledgeBookCategoryFactory;
import minefantasy.mfr.registry.knowledge.factories.KnowledgeBookEntryFactory;
import minefantasy.mfr.registry.knowledge.types.KnowledgeBookCategoryType;
import minefantasy.mfr.registry.knowledge.types.KnowledgeBookEntryType;
import minefantasy.mfr.util.FileUtils;
import minefantasy.mfr.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

@SideOnly(Side.CLIENT)
public class KnowledgeManagerKnowledgeBook {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final String KNOWLEDGE_BOOK_FOLDER_PATH = Constants.ASSET_DIRECTORY + "/knowledge_mfr/knowledge_book/%s";
	public static final String KNOWLEDGE_BOOK_CATEGORIES_PATH = "/categories";
	public static final String KNOWLEDGE_BOOK_ENTRIES_PATH = "/entries";

	public static final String CONFIG_KNOWLEDGE_BOOK_DIRECTORY = "config/"
			+ Constants.CONFIG_DIRECTORY
			+ "/custom/knowledge/knowledge_book/%s";

	private final KnowledgeBookCategoryFactory knowledgeBookCategoryFactory = new KnowledgeBookCategoryFactory();
	private final KnowledgeBookEntryFactory knowledgeBookEntryFactory = new KnowledgeBookEntryFactory();

	public void init() {
		//call this so that the static final gets initialized at proper time
	}

	public static final KnowledgeBookCategoryBase CATEGORY_NONE =
			new KnowledgeBookCategoryBase(Utils.emptyLinkedList(), Skill.NONE, "");

	private static final IForgeRegistry<KnowledgeBookCategoryBase> KNOWLEDGE_BOOK_CATEGORIES = (new RegistryBuilder<KnowledgeBookCategoryBase>())
			.setName(new ResourceLocation(MineFantasyReforged.MOD_ID, "research_book_categories"))
			.setType(KnowledgeBookCategoryBase.class).setMaxID(Integer.MAX_VALUE >> 5)
			.disableSaving().allowModification().create();

	public static final KnowledgeBookEntryBase ENTRY_NONE = new KnowledgeBookEntryBase("", CATEGORY_NONE, 0, 0,
			ItemStack.EMPTY, "", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
			Collections.emptyList(), false, false);

	private static final IForgeRegistry<KnowledgeBookEntryBase> KNOWLEDGE_BOOK_ENTRIES = (new RegistryBuilder<KnowledgeBookEntryBase>())
			.setName(new ResourceLocation(MineFantasyReforged.MOD_ID, "research_book_entries"))
			.setType(KnowledgeBookEntryBase.class).setMaxID(Integer.MAX_VALUE >> 5)
			.disableSaving().allowModification().create();

	public static HashMap<KnowledgeBookEntryBase, List<ResourceLocation>> PARENT_KNOWLEDGE_BOOK_ENTRY_KEY_MAP = new HashMap<>();

	public void addKnowledgeBookCategory(KnowledgeBookCategoryBase category, ResourceLocation key, boolean checkForExistence) {
		if (ConfigKnowledgeBook.isKnowledgeBookCategoryEnabled(key)) {

			category.setRegistryName(key);
			if (!checkForExistence || !KNOWLEDGE_BOOK_CATEGORIES.containsKey(category.getRegistryName())) {
				KNOWLEDGE_BOOK_CATEGORIES.register(category);
			}
		}
	}

	public void addKnowledgeBookEntry(KnowledgeBookEntryBase entry, ResourceLocation key, boolean checkForExistence) {
		if (ConfigKnowledgeBook.isKnowledgeBookEntryEnabled(key)) {

			entry.setRegistryName(key);
			if (!checkForExistence || !KNOWLEDGE_BOOK_ENTRIES.containsKey(entry.getRegistryName())) {
				KNOWLEDGE_BOOK_ENTRIES.register(entry);
			}
		}
	}

	public static Collection<KnowledgeBookCategoryBase> getKnowledgeBookCategories() {
		return KNOWLEDGE_BOOK_CATEGORIES.getValuesCollection();
	}

	public static List<KnowledgeBookCategoryBase> getCategoriesByKeys(boolean checkForExistence, ResourceLocation... keys) {
		List<KnowledgeBookCategoryBase> categories = new ArrayList<>();
		for (ResourceLocation key : keys) {
			categories.add(getCategoryByKey(key, checkForExistence));
		}
		return categories;
	}

	public static KnowledgeBookCategoryBase getCategoryByKey(String key, boolean checkForExistence) {
		if (key.equals("none")) {
			return CATEGORY_NONE;
		}
		else {
			return getCategoryByKey(new ResourceLocation(key), checkForExistence);
		}
	}
	public static KnowledgeBookCategoryBase getCategoryByKey(ResourceLocation key, boolean checkForExistence) {
		if (checkForExistence && !KNOWLEDGE_BOOK_CATEGORIES.containsKey(key)) {
			MineFantasyReforged.LOG.error("Knowledge Book Category Registry does not contain category: {}", key);
		}
		KnowledgeBookCategoryBase category = KNOWLEDGE_BOOK_CATEGORIES.getValue(key);
		if (category == null) {
			return CATEGORY_NONE;
		}
		return category;
	}

	public static Collection<KnowledgeBookEntryBase> getKnowledgeBookEntries() {
		return KNOWLEDGE_BOOK_ENTRIES.getValuesCollection();
	}

	public static List<KnowledgeBookEntryBase> getEntriesByKeys(boolean checkForExistence, ResourceLocation... keys) {
		List<KnowledgeBookEntryBase> entries = new ArrayList<>();
		for (ResourceLocation key : keys) {
			entries.add(getEntryByKey(key, checkForExistence));
		}
		return entries;
	}

	public static KnowledgeBookEntryBase getEntryByKey(String key, boolean checkForExistence) {
		if (key.equals("none")) {
			return ENTRY_NONE;
		}
		else {
			return getEntryByKey(new ResourceLocation(key), checkForExistence);
		}
	}
	public static KnowledgeBookEntryBase getEntryByKey(ResourceLocation key, boolean checkForExistence) {
		if (checkForExistence && !KNOWLEDGE_BOOK_ENTRIES.containsKey(key)) {
			MineFantasyReforged.LOG.error("Knowledge Book Entry Registry does not contain category: {}", key);
		}
		KnowledgeBookEntryBase category = KNOWLEDGE_BOOK_ENTRIES.getValue(key);
		if (category == null) {
			return ENTRY_NONE;
		}
		return category;
	}

	public void loadKnowledgeBookCategories() {
		ModContainer modContainer = Loader.instance().activeModContainer();

		Language currentLanguage = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage();

		String configFilePath = String.format(CONFIG_KNOWLEDGE_BOOK_DIRECTORY + KNOWLEDGE_BOOK_CATEGORIES_PATH,
				currentLanguage.getLanguageCode());
		FileUtils.createCustomDataDirectory(configFilePath);
		//noinspection ConstantConditions
		loadKnowledgeBookCategories(modContainer, new File(configFilePath), "");
		Loader.instance().getActiveModList().forEach(m ->
				loadKnowledgeBookCategories(m, m.getSource(),
						String.format(KNOWLEDGE_BOOK_FOLDER_PATH + KNOWLEDGE_BOOK_CATEGORIES_PATH,
								m.getModId(), currentLanguage.getLanguageCode())));

		Loader.instance().setActiveModContainer(modContainer);
	}

	private void loadKnowledgeBookCategories(ModContainer mod, File source, String base) {
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
						if (KnowledgeBookCategoryType.getByNameWithModId(type, mod.getModId()) != KnowledgeBookCategoryType.NONE) {
							KnowledgeBookCategoryBase category = knowledgeBookCategoryFactory.parse(json);
							key = new ResourceLocation(ctx.getModId(), category.getName());
							addKnowledgeBookCategory(category, key, mod.getModId().equals(MineFantasyReforged.MOD_ID));
						} else {
							MineFantasyReforged.LOG.info("Skipping knowledge book category {} of type {} because it's not a MFR knowledge book category", key, type);
						}
					}
					else {
						MineFantasyReforged.LOG.info("Skipping knowledge book category {} of type {} because it the mod it depends on is not loaded", key, type);
					}
				}
				catch (JsonParseException e) {
					MineFantasyReforged.LOG.error("Parsing error loading knowledge book category {}", key, e);
				}
				catch (IOException e) {
					MineFantasyReforged.LOG.error("Couldn't read knowledge book category {} from {}", key, file, e);
				}
				finally {
					IOUtils.closeQuietly(reader);
				}
			}
		});
	}

	public void loadKnowledgeBookEntries() {
		ModContainer modContainer = Loader.instance().activeModContainer();

		Language currentLanguage = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage();

		String configFilePath = String.format(CONFIG_KNOWLEDGE_BOOK_DIRECTORY + KNOWLEDGE_BOOK_ENTRIES_PATH,
				currentLanguage.getLanguageCode());
		FileUtils.createCustomDataDirectory(configFilePath);
		//noinspection ConstantConditions
		loadKnowledgeBookEntries(modContainer, new File(configFilePath), "");
		Loader.instance().getActiveModList().forEach(m ->
				loadKnowledgeBookEntries(m, m.getSource(),
						String.format(KNOWLEDGE_BOOK_FOLDER_PATH + KNOWLEDGE_BOOK_ENTRIES_PATH,
								m.getModId(), currentLanguage.getLanguageCode())));

		Loader.instance().setActiveModContainer(modContainer);
	}

	private void loadKnowledgeBookEntries(ModContainer mod, File source, String base) {
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
						if (KnowledgeBookEntryType.getByNameWithModId(type, mod.getModId()) != KnowledgeBookEntryType.NONE) {
							KnowledgeBookEntryBase category = knowledgeBookEntryFactory.parse(ctx, json);
							key = new ResourceLocation(ctx.getModId(), category.getName());
							addKnowledgeBookEntry(category, key, mod.getModId().equals(MineFantasyReforged.MOD_ID));
						} else {
							MineFantasyReforged.LOG.info("Skipping knowledge book entry {} of type {} because it's not a MFR knowledge book entry", key, type);
						}
					}
					else {
						MineFantasyReforged.LOG.info("Skipping knowledge book entry {} of type {} because it the mod it depends on is not loaded", key, type);
					}
				}
				catch (JsonParseException e) {
					MineFantasyReforged.LOG.error("Parsing error loading knowledge book entry {}", key, e);
				}
				catch (IOException e) {
					MineFantasyReforged.LOG.error("Couldn't read knowledge book entry {} from {}", key, file, e);
				}
				finally {
					IOUtils.closeQuietly(reader);
				}
			}
		});
	}

	public void addKnowledgeBookParentEntries() {
		for (KnowledgeBookEntryBase entry : getKnowledgeBookEntries()) {
			List<ResourceLocation> parentEntryKeyList = PARENT_KNOWLEDGE_BOOK_ENTRY_KEY_MAP.get(entry);
			if (parentEntryKeyList != null && !parentEntryKeyList.isEmpty()) {
				List<KnowledgeBookEntryBase> parentEntries = new ArrayList<>();
				for (ResourceLocation parentEntryKey : parentEntryKeyList) {
					parentEntries.add(getEntryByKey(parentEntryKey, true));
				}

				entry.setParentEntries(parentEntries);
			}
		}
	}
}
