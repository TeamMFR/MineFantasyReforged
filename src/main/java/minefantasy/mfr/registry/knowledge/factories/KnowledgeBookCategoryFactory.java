package minefantasy.mfr.registry.knowledge.factories;

import com.google.gson.JsonObject;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.registry.knowledge.KnowledgeBookCategoryBase;
import minefantasy.mfr.registry.knowledge.KnowledgeManagerKnowledgeBook;
import minefantasy.mfr.registry.knowledge.types.KnowledgeBookCategoryType;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;

@SideOnly(Side.CLIENT)
public class KnowledgeBookCategoryFactory {
	public KnowledgeBookCategoryBase parse(JsonObject json) {
		String type = JsonUtils.getString(json, "type");
		KnowledgeBookCategoryType categoryType = KnowledgeBookCategoryType.deserialize(type);
		if (categoryType == KnowledgeBookCategoryType.KNOWLEDGE_BOOK_CATEGORY) {
			return parseCategory(json);
		}
		return KnowledgeManagerKnowledgeBook.CATEGORY_NONE;
	}

	private KnowledgeBookCategoryBase parseCategory(JsonObject json) {
		String name = JsonUtils.getString(json, "name", "");
		Skill skill = Skill.fromName(JsonUtils.getString(json, "skill", "none"));

		return new KnowledgeBookCategoryBase(Collections.emptyList(), skill, name);
	}
}
