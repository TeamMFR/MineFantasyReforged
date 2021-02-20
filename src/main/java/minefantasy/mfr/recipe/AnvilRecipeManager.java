package minefantasy.mfr.recipe;

import com.google.gson.JsonObject;
import minefantasy.mfr.api.crafting.anvil.CraftingManagerAnvil;
import minefantasy.mfr.api.rpg.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class responsible for loading and parsing the anvil recipe json files from the assets and config folders
 */
// TODO: check net.minecraftforge.fml.common.registry.GameRegistry.addShapedRecipe as we should probably register our recipes as proper IRecipes
public class AnvilRecipeManager extends RecipeManager {

	public static final AnvilRecipeManager INSTANCE = new AnvilRecipeManager();

	private static final int GRID_WIDTH = 6;
	private static final int GRID_HEIGHT = 4;
	private static final String TYPE = "anvil";
	private static final String DEFAULT_RECIPE_DIRECTORY = "assets/minefantasyreborn/anvil_recipes";
	private static final String CUSTOM_RECIPE_DIRECTORY = "config/minefantasyreborn/recipes/anvil_recipes/";

	public static final List<JsonObject> list = new ArrayList<>();

	private static Map<String, IRecipe> loaded = new TreeMap<>();

	private AnvilRecipeManager() {} // no instances!

	public void postInit() {
		createCustomRecipeDirectory(CUSTOM_RECIPE_DIRECTORY);
		loadRecipes(TYPE, DEFAULT_RECIPE_DIRECTORY, CUSTOM_RECIPE_DIRECTORY, loaded);
	}

	@Override
	protected IRecipe parseRecipeJson(String name, JsonObject json) {
		String s = JsonUtils.getString(json, "type");

		Skill skill = Skill.fromName(JsonUtils.getString(json, "skill", "none"));
		String research = JsonUtils.getString(json, "research", "none");
		String tool_type = JsonUtils.getString(json, "tool_type", "none");
		boolean output_hot = JsonUtils.getBoolean(json, "output_hot", false);
		int recipe_hammer = JsonUtils.getInt(json, "recipe_hammer", 0);
		int anvil_tier = JsonUtils.getInt(json, "anvil_tier", 0);
		int recipe_time = JsonUtils.getInt(json, "recipe_time", 0);

		IRecipe recipe = deserialize(GRID_WIDTH, GRID_HEIGHT, json);

		String[] pattern = shrink(patternFromJson(GRID_WIDTH, GRID_HEIGHT, JsonUtils.getJsonArray(json, "pattern")));
		ItemStack resultStack = deserializeItem(JsonUtils.getJsonObject(json, "result"), true);
		Object[] o = getInputs(pattern, json);

		byte type = s.equals("CustomToolRecipe") ? (byte) 1 : (byte) 0;
		CraftingManagerAnvil.getInstance().addRecipe(resultStack, skill, research, output_hot, tool_type, recipe_hammer, anvil_tier, recipe_time, type, o);
		return recipe;
	}
}