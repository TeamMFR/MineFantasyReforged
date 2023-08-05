package minefantasy.mfr.recipe.factories;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.recipe.AnvilRecipeBase;
import minefantasy.mfr.recipe.ShapedAnvilRecipes;
import minefantasy.mfr.recipe.ShapedCustomMaterialAnvilRecipe;
import minefantasy.mfr.recipe.ShapelessAnvilRecipes;
import minefantasy.mfr.recipe.ShapelessCustomMaterialAnvilRecipe;
import minefantasy.mfr.recipe.types.AnvilRecipeType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class AnvilRecipeFactory {

	public AnvilRecipeBase parse(JsonContext context, JsonObject json) {
		String type = JsonUtils.getString(json, "type");
		AnvilRecipeType recipeType = AnvilRecipeType.deserialize(type);
		switch (recipeType) {
			case SHAPED_ANVIL_RECIPE:
				return parseShaped(context, json);
			case SHAPELESS_ANVIL_RECIPE:
				return parseShapeless(context, json);
			case SHAPED_CUSTOM_MATERIAL_ANVIL_RECIPE:
				return parseShapedCustomMaterial(context, json);
			case SHAPELESS_CUSTOM_MATERIAL_ANVIL_RECIPE:
				return parseShapelessCustomMaterial(context, json);
			default:
				return null;
		}
	}

	private AnvilRecipeBase parseShapelessCustomMaterial(JsonContext context, JsonObject json) {
		NonNullList<Ingredient> ingredients = NonNullList.create();
		for (JsonElement ele : JsonUtils.getJsonArray(json, "ingredients")) {
			ingredients.add(CraftingHelper.getIngredient(ele, context));
		}

		if (ingredients.isEmpty()) {
			throw new JsonParseException("No ingredients for shapeless recipe");
		}

		Skill skill = Skill.fromName(JsonUtils.getString(json, "skill", "none"));
		String research = JsonUtils.getString(json, "research", "none");
		String tool_type = JsonUtils.getString(json, "tool_type", "none");
		boolean output_hot = JsonUtils.getBoolean(json, "output_hot", false);
		int recipe_hammer = JsonUtils.getInt(json, "recipe_hammer", 0);
		int anvil_tier = JsonUtils.getInt(json, "anvil_tier", 0);
		int recipe_time = JsonUtils.getInt(json, "recipe_time", 0);

		ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);

		return new ShapelessCustomMaterialAnvilRecipe(ingredients, result,
				tool_type, recipe_time, recipe_hammer, anvil_tier, output_hot, research, skill);
	}

	private AnvilRecipeBase parseShapedCustomMaterial(JsonContext context, JsonObject json) {
		ShapedOreRecipe recipe = ShapedOreRecipe.factory(context, json);
		Skill skill = Skill.fromName(JsonUtils.getString(json, "skill", "none"));
		String research = JsonUtils.getString(json, "research", "none");
		String tool_type = JsonUtils.getString(json, "tool_type", "none");
		boolean output_hot = JsonUtils.getBoolean(json, "output_hot", false);
		int recipe_hammer = JsonUtils.getInt(json, "recipe_hammer", 0);
		int anvil_tier = JsonUtils.getInt(json, "anvil_tier", 0);
		int recipe_time = JsonUtils.getInt(json, "recipe_time", 0);

		return new ShapedCustomMaterialAnvilRecipe(recipe.getIngredients(), recipe.getRecipeOutput(),
				tool_type, recipe_time, recipe_hammer, anvil_tier, output_hot, research, skill);
	}

	private AnvilRecipeBase parseShapeless(JsonContext context, JsonObject json) {
		NonNullList<Ingredient> ingredients = NonNullList.create();
		for (JsonElement ele : JsonUtils.getJsonArray(json, "ingredients")) {
			ingredients.add(CraftingHelper.getIngredient(ele, context));
		}

		if (ingredients.isEmpty()) {
			throw new JsonParseException("No ingredients for shapeless recipe");
		}

		Skill skill = Skill.fromName(JsonUtils.getString(json, "skill", "none"));
		String research = JsonUtils.getString(json, "research", "none");
		String tool_type = JsonUtils.getString(json, "tool_type", "none");
		boolean output_hot = JsonUtils.getBoolean(json, "output_hot", false);
		int recipe_hammer = JsonUtils.getInt(json, "recipe_hammer", 0);
		int anvil_tier = JsonUtils.getInt(json, "anvil_tier", 0);
		int recipe_time = JsonUtils.getInt(json, "recipe_time", 0);

		ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);

		return new ShapelessAnvilRecipes(ingredients, result,
				tool_type, recipe_time, recipe_hammer, anvil_tier, output_hot, research, skill);
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
