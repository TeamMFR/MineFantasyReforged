package minefantasy.mfr.registry.knowledge.factories;

import com.google.gson.JsonObject;
import minefantasy.mfr.client.knowledge.EntryPage;
import minefantasy.mfr.registry.knowledge.types.KnowledgeBookEntryPageType;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KnowledgeBookEntryPageFactory {
	public EntryPage parse(JsonContext ctx, JsonObject json) {
		String type = JsonUtils.getString(json, "type");
		KnowledgeBookEntryPageType pageType = KnowledgeBookEntryPageType.deserialize(type);
		switch (pageType) {
			case ENTRY_PAGE_TEXT:
				parseTextPage(ctx, json);
			case ENTRY_PAGE_IMAGE:
				parseImagePage(ctx, json);
			case ENTRY_PAGE_RECIPE:
				parseRecipePage(ctx, json);
			default:
				return null;
		}
	}

	private void parseRecipePage(JsonContext ctx, JsonObject json) {

	}

	private void parseImagePage(JsonContext ctx, JsonObject json) {

	}

	private void parseTextPage(JsonContext ctx, JsonObject json) {

	}
}
