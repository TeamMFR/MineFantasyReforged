package minefantasy.mfr.registry.knowledge.factories;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import minefantasy.mfr.client.knowledge.EntryPage;
import minefantasy.mfr.registry.knowledge.KnowledgeBookCategoryBase;
import minefantasy.mfr.registry.knowledge.KnowledgeBookEntryBase;
import minefantasy.mfr.registry.knowledge.KnowledgeManagerKnowledgeBook;
import minefantasy.mfr.registry.knowledge.KnowledgeManagerResearch;
import minefantasy.mfr.registry.knowledge.ResearchBase;
import minefantasy.mfr.registry.knowledge.types.KnowledgeBookEntryType;
import minefantasy.mfr.util.JsonUtilsMFR;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class KnowledgeBookEntryFactory {

	KnowledgeBookEntryPageFactory entryPageFactory = new KnowledgeBookEntryPageFactory();

	public KnowledgeBookEntryBase parse(JsonContext ctx, JsonObject json) {
		String type = JsonUtils.getString(json, "type");
		KnowledgeBookEntryType entryType = KnowledgeBookEntryType.deserialize(type);
		if (entryType == KnowledgeBookEntryType.KNOWLEDGE_BOOK_ENTRY) {
			return parseEntry(ctx, json);
		}
		return KnowledgeManagerKnowledgeBook.ENTRY_NONE;
	}

	private KnowledgeBookEntryBase parseEntry(JsonContext ctx, JsonObject json) {
		String name = JsonUtils.getString(json, "name", "");
		ResourceLocation category_key = new ResourceLocation(JsonUtils.getString(json, "category", ""));

		KnowledgeBookCategoryBase category = KnowledgeManagerKnowledgeBook.getCategoryByKey(category_key, true);
		if (category == KnowledgeManagerKnowledgeBook.CATEGORY_NONE) {
			throw new JsonParseException("Invalid Category when parsing Knowledge Book Entry: " + name);
		}

		int display_column = JsonUtils.getInt(json, "display_column", 0);
		int display_row = JsonUtils.getInt(json, "display_row", 0);

		ItemStack display_stack = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "display_stack"), ctx);
		String description = JsonUtils.getString(json, "description", "");
		List<Object> descriptionParameters = parseDescriptionParameters(json);
		List<ResearchBase> researches = parseResearches(json, name);
		List<EntryPage> pages = parsePages(ctx, json, name);
		boolean is_unlocked = JsonUtils.getBoolean(json, "is_unlocked", true);
		boolean is_special = JsonUtils.getBoolean(json, "is_special", false);

		return new KnowledgeBookEntryBase(name, category, display_column, display_row, display_stack, description,
				descriptionParameters, Collections.emptyList(), researches, pages, is_unlocked, is_special);
	}

	private List<EntryPage> parsePages(JsonContext context, JsonObject json, String name) {
		List<EntryPage> pages = new ArrayList<>();
		for (JsonElement element : JsonUtils.getJsonArray(json, "pages")) {
			if (element.isJsonObject()) {
				EntryPage page = entryPageFactory.parse(context, element.getAsJsonObject());
				pages.add(page);
			}
			else {
				throw new JsonParseException("Invalid format for an Entry Page when parsing Knowledge Book Entry: " + name);
			}
		}

		if (pages.isEmpty()) {
			throw new JsonParseException(String.format("Knowledge Book Entry %s does not have any Entry Pages!", name));
		}

		return pages;
	}

	private List<ResearchBase> parseResearches(JsonObject json, String name) {
		List<ResearchBase> researches;
		if (JsonUtils.hasField(json, "researches")) {
			researches = new ArrayList<>();
			for (JsonElement element : JsonUtils.getJsonArray(json, "researches")) {
				if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
					ResearchBase research = KnowledgeManagerResearch
							.getResearchByKey(new ResourceLocation(element.getAsString()), true);
					if (research != KnowledgeManagerResearch.NONE) {
						researches.add(research);
					}
				}
				else {
					throw new JsonParseException("Invalid value for a Research key when parsing Knowledge Book Entry: " + name);
				}
			}
		}
		else {
			researches = Collections.emptyList();
		}

		return researches;
	}

	private static List<Object> parseDescriptionParameters(JsonObject json) {
		List<Object> descriptionParameters;
		if (JsonUtils.hasField(json, "description_parameters")) {
			descriptionParameters = new ArrayList<>();
			for (JsonElement element : JsonUtils.getJsonArray(json, "description_parameters")) {
				if (element.isJsonPrimitive()) {
					descriptionParameters.add(JsonUtilsMFR.mapJsonPrimitiveToObject(element.getAsJsonPrimitive()));
				}
				else {

				}
			}
		}
		else {
			descriptionParameters = Collections.emptyList();
		}

		return descriptionParameters;
	}
}
