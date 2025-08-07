package minefantasy.mfr.registry.knowledge.factories;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.registry.knowledge.KnowledgeManagerResearch;
import minefantasy.mfr.registry.knowledge.ResearchBase;
import minefantasy.mfr.registry.knowledge.SkillRequirement;
import minefantasy.mfr.registry.knowledge.types.ResearchType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.ArrayList;
import java.util.List;

public class ResearchFactory {

	public ResearchBase parse(JsonContext context, JsonObject json) {
		String type = JsonUtils.getString(json, "type");
		ResearchType recipeType = ResearchType.deserialize(type);
		switch (recipeType) {
			case ARTIFACT_RESEARCH:
				return parseArtifactResearch(context, json);
			case PERK_RESEARCH:
				return parsePerkResearch(context, json);
			default:
				return KnowledgeManagerResearch.NONE;
		}
	}

	private ResearchBase parseArtifactResearch(JsonContext context, JsonObject json) {
		String name = JsonUtils.getString(json, "name", "");

		if (name.isEmpty()) {
			throw new JsonParseException("No Name for research!");
		}

		List<Ingredient> artifacts = new ArrayList<>();
		for (JsonElement element : JsonUtils.getJsonArray(json, "artifacts")) {
			artifacts.add(CraftingHelper.getIngredient(element, context));
		}
		if (artifacts.isEmpty()) {
			throw new JsonParseException("No artifacts defined for an Artifact Research: " + name);
		}

		int required_artifact_count = JsonUtils.getInt(json, "required_artifact_count", artifacts.size());

		List<SkillRequirement> skillRequirements = new ArrayList<>();
		if (json.has("skill_requirements")) {
			for (JsonElement element : JsonUtils.getJsonArray(json, "skill_requirements")) {
				if (element.isJsonObject()) {
					skillRequirements.add(parseSkillRequirement(element.getAsJsonObject()));
				}
				else {
					throw new JsonParseException("Invalid Skill Requirement when parsing Research: " + name);
				}
			}
		}

		ResearchBase researchBase = new ResearchBase(name, artifacts, required_artifact_count, skillRequirements, false);

		List<ResourceLocation> parentResearches = new ArrayList<>();
		if (json.has("parent_researches")) {
			for (JsonElement element : JsonUtils.getJsonArray(json, "parent_researches")) {
				parentResearches.add(new ResourceLocation(element.getAsString()));
			}
		}

		if (!parentResearches.isEmpty()) {
			KnowledgeManagerResearch.PARENT_RESEARCH_KEY_MAP.put(researchBase, parentResearches);
		}

		return researchBase;
	}

	private ResearchBase parsePerkResearch(JsonContext context, JsonObject json) {
		String name = JsonUtils.getString(json, "name", "");

		if (name.isEmpty()) {
			throw new JsonParseException("No Name for research!");
		}

		List<Ingredient> artifacts = new ArrayList<>();
		if (json.has("artifacts")) {
			for (JsonElement element : JsonUtils.getJsonArray(json, "artifacts")) {
				artifacts.add(CraftingHelper.getIngredient(element, context));
			}
		}

		int required_artifact_count = JsonUtils.getInt(json, "required_artifact_count", artifacts.size());

		List<SkillRequirement> skillRequirements = new ArrayList<>();
		for (JsonElement element : JsonUtils.getJsonArray(json, "skill_requirements")) {
			if (element.isJsonObject()) {
				skillRequirements.add(parseSkillRequirement(element.getAsJsonObject()));
			}
			else {
				throw new JsonParseException("Invalid Skill Requirement when parsing Research: " + name);
			}
		}

		if (skillRequirements.isEmpty()) {
			throw new JsonParseException("No skill requirements defined for an Unlock Research: " + name);
		}

		ResearchBase researchBase = new ResearchBase(name, artifacts, required_artifact_count, skillRequirements, true);

		List<ResourceLocation> parentResearches = new ArrayList<>();
		if (json.has("parent_researches")) {
			for (JsonElement element : JsonUtils.getJsonArray(json, "parent_researches")) {
				parentResearches.add(new ResourceLocation(element.getAsString()));
			}
		}

		if (!parentResearches.isEmpty()) {
			KnowledgeManagerResearch.PARENT_RESEARCH_KEY_MAP.put(researchBase, parentResearches);
		}

		return researchBase;
	}

	private SkillRequirement parseSkillRequirement(JsonObject json) {
		Skill skill = Skill.fromName(JsonUtils.getString(json, "skill", "none"));
		int required_skill_level = JsonUtils.getInt(json, "required_skill_level", 0);
		return new SkillRequirement(skill, required_skill_level);
	}
}
