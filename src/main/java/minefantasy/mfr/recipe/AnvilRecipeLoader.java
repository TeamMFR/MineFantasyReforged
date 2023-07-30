package minefantasy.mfr.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.constants.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for loading and parsing the anvil recipe json files from the assets and config folders
 */
// TODO: check net.minecraftforge.fml.common.registry.GameRegistry.addShapedRecipe as we should probably register our recipes as proper IRecipes
public class AnvilRecipeLoader extends RecipeLoader {

	public static final AnvilRecipeLoader INSTANCE = new AnvilRecipeLoader();

	private static final int GRID_WIDTH = 6;
	private static final int GRID_HEIGHT = 4;
	private static final String TYPE = "anvil";
	private static final String DEFAULT_RECIPE_DIRECTORY = "assets/" + Constants.ASSET_DIRECTORY +"/anvil_recipes";
	private static final String CUSTOM_RECIPE_DIRECTORY = "config/" + Constants.CONFIG_DIRECTORY +"/custom/recipes/anvil_recipes/";

	public static final List<JsonObject> list = new ArrayList<>();

	private AnvilRecipeLoader() {} // no instances!

	public void postInit() {
		createCustomDataDirectory(CUSTOM_RECIPE_DIRECTORY);
		loadRegistry(TYPE, DEFAULT_RECIPE_DIRECTORY, CUSTOM_RECIPE_DIRECTORY);
	}
	@Override
	protected void parse(String name, JsonObject json) {
		String type = JsonUtils.getString(json, "type");

		Skill skill = Skill.fromName(JsonUtils.getString(json, "skill", "none"));
		String research = JsonUtils.getString(json, "research", "none");
		String tool_type = JsonUtils.getString(json, "tool_type", "none");
		boolean output_hot = JsonUtils.getBoolean(json, "output_hot", false);
		int recipe_hammer = JsonUtils.getInt(json, "recipe_hammer", 0);
		int anvil_tier = JsonUtils.getInt(json, "anvil_tier", 0);
		int recipe_time = JsonUtils.getInt(json, "recipe_time", 0);

		String[] pattern = shrink(patternFromJson(GRID_WIDTH, GRID_HEIGHT, JsonUtils.getJsonArray(json, "pattern")));
		JsonObject key = JsonUtils.getJsonObject(json, "key");

		ItemStack resultStack = deserializeIngredient(JsonUtils.getJsonObject(json, "result")).getMatchingStacks()[0];
		String oreDictList = null;

		if (type.equals("CustomToolOreDictAnvilRecipes")){
			for (Map.Entry<String, JsonElement> entry : key.entrySet()) {
				if (entry.getKey().length() != 1) {
					throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
				}

				if (" ".equals(entry.getKey())) {
					throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
				}
				if (entry.getValue().getAsJsonObject().has("type") && JsonUtils.getString(entry.getValue().getAsJsonObject(), "type").equals("oreDict")){
					oreDictList = JsonUtils.getString(entry.getValue().getAsJsonObject(), "ore");
				}
			}
		}

		Object[] inputs = this.getInputs(pattern, json);

		//CraftingManagerAnvilOld.getInstance().addRecipe(name, resultStack, skill, research, output_hot, tool_type, recipe_hammer, anvil_tier, recipe_time, type, oreDictList, inputs);
	}

	Object[] getInputs(String[] pattern, JsonObject json) {
		Map<Character, Ingredient> map = deserializeKeyToCharMap(JsonUtils.getJsonObject(json, "key"));

		Object[] object = new Object[] {};

		for (String p : pattern) {
			if (p.trim().length() > 0) {
				object = appendValue(object, p);
			}
		}

		for (Map.Entry<Character, Ingredient> i : map.entrySet()) {
			if (!Character.isWhitespace(i.getKey())) {
				object = appendValue(object, i.getKey());
				if (i.getValue().getMatchingStacks().length > 1){
					object = appendValue(object, i.getValue().getMatchingStacks());
				}
				else {
					object = appendValue(object, i.getValue().getMatchingStacks()[0]);
				}
			}
		}

		return object;
	}
}