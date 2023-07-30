package minefantasy.mfr.recipe.factories;

import com.google.gson.JsonObject;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.recipe.AnvilRecipeBase;
import minefantasy.mfr.recipe.ShapedAnvilRecipes;
import minefantasy.mfr.recipe.types.AnvilRecipeType;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class AnvilRecipeFactory {

	public AnvilRecipeBase parse(JsonContext context, JsonObject json) {
		String type = JsonUtils.getString(json, "type");
		//String split = type.split(":")[1];
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
		ShapedOreRecipe recipe = ShapedOreRecipe.factory(context, json);
		Skill skill = Skill.fromName(JsonUtils.getString(json, "skill", "none"));
		String research = JsonUtils.getString(json, "research", "none");
		String tool_type = JsonUtils.getString(json, "tool_type", "none");
		boolean output_hot = JsonUtils.getBoolean(json, "output_hot", false);
		int recipe_hammer = JsonUtils.getInt(json, "recipe_hammer", 0);
		int anvil_tier = JsonUtils.getInt(json, "anvil_tier", 0);
		int recipe_time = JsonUtils.getInt(json, "recipe_time", 0);

		return new ShapedAnvilRecipes(recipe.getIngredients(), recipe.getRecipeOutput(), 
				tool_type, recipe_time, recipe_hammer, anvil_tier, output_hot, research, skill);
	}
}
