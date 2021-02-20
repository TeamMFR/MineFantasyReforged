package minefantasy.mfr.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import minefantasy.mfr.api.crafting.carpenter.CraftingManagerCarpenter;
import minefantasy.mfr.api.rpg.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class responsible for loading and parsing the carpenter recipe json files from the assets and config folders
 */
// TODO: check net.minecraftforge.fml.common.registry.GameRegistry.addShapedRecipe as we should probably register our recipes as proper IRecipes
public class CarpenterRecipeManager extends RecipeManager {

	public static final CarpenterRecipeManager INSTANCE = new CarpenterRecipeManager();

	private static final int GRID_WIDTH = 4;
	private static final int GRID_HEIGHT = 4;
	private static final String TYPE = "carpenter";
	private static final String DEFAULT_RECIPE_DIRECTORY = "assets/minefantasyreborn/carpenter_recipes";
	private static final String CUSTOM_RECIPE_DIRECTORY = "config/minefantasyreborn/recipes/carpenter_recipes/";

	public static final List<JsonObject> list = new ArrayList<>();

	private static Map<String, IRecipe> loaded = new TreeMap<>();

	private CarpenterRecipeManager() {} // no instances!

	public void postInit() {
		createCustomRecipeDirectory(CUSTOM_RECIPE_DIRECTORY);
		loadRecipes(TYPE, DEFAULT_RECIPE_DIRECTORY, CUSTOM_RECIPE_DIRECTORY, loaded);
	}

	@Override
	protected IRecipe parseRecipeJson(String name, JsonObject json) {

		String type = JsonUtils.getString(json, "type");
		Skill skill = Skill.fromName(JsonUtils.getString(json, "skill", "none"));
		String research = JsonUtils.getString(json, "research", "none");
		String sound = JsonUtils.getString(json, "sound", "minecraft:block.wood.hit");
		String tool_type = JsonUtils.getString(json, "tool_type", "none");
		float experience = JsonUtils.getFloat(json, "experience", 0F);
		int craft_time = JsonUtils.getInt(json, "craft_time", 0);
		int tool_tier = JsonUtils.getInt(json, "tool_tier", 0);
		byte tool_recipe = JsonUtils.getBoolean(json, "is_tool_recipe") ? (byte) 1 : (byte) 0;

		IRecipe recipe;
		if ("crafting_shaped".equals(type) || "crafting_shapeless".equals(type)) {
			recipe = deserialize(GRID_WIDTH, GRID_HEIGHT, json);
		} else {
			throw new JsonSyntaxException("Invalid or unsupported recipe type '" + type + "'");
		}

		String[] pattern = shrink(patternFromJson(GRID_WIDTH, GRID_HEIGHT, JsonUtils.getJsonArray(json, "pattern")));
		ItemStack resultStack = deserializeItem(JsonUtils.getJsonObject(json, "result"), true);
		Object[] o = getInputs(pattern, json);

		CraftingManagerCarpenter.getInstance().addRecipe(name, resultStack, skill, research, SoundEvent.REGISTRY.getObject(new ResourceLocation(sound)), experience, tool_type, tool_tier, 0, craft_time, tool_recipe, o);
		return recipe;
	}
}
