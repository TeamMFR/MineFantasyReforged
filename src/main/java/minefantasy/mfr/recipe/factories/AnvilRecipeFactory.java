package minefantasy.mfr.recipe.factories;

import com.google.gson.JsonObject;
import minefantasy.mfr.recipe.AnvilRecipeBase;
import minefantasy.mfr.recipe.types.AnvilRecipeType;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.JsonContext;

public class AnvilRecipeFactory {

	public AnvilRecipeBase parse(JsonContext context, JsonObject json) {
		String type = JsonUtils.getString(json, "type").split(":")[1];
		AnvilRecipeType recipeType = AnvilRecipeType.deserialize(type);
		switch (recipeType) {
			case SHAPED_ANVIL_RECIPE:
				return parseShaped(context, json);
			case SHAPELESS_ANVIL_RECIPE:
				return parseShapeless(context, json);
			case CUSTOM_MATERIAL_SHAPED_ANVIL_RECIPE:
				return parseCustomMaterialShaped(context, json);
			case CUSTOM_MATERIAL_SHAPELESS_ANVIL_RECIPE:
				return parseCustomMaterialShapeless(context, json);
			default:
				return null;
		}
	}

	private AnvilRecipeBase parseCustomMaterialShapeless(JsonContext context, JsonObject json) {
		return null;
	}

	private AnvilRecipeBase parseCustomMaterialShaped(JsonContext context, JsonObject json) {
		return null;
	}

	private AnvilRecipeBase parseShapeless(JsonContext context, JsonObject json) {
		return null;
	}

	private AnvilRecipeBase parseShaped(JsonContext context, JsonObject json) {
		return null;
	}
}
