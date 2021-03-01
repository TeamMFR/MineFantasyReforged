package minefantasy.mfr.recipe;

import com.google.gson.JsonObject;
import minefantasy.mfr.api.rpg.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

/**
 * Class responsible for loading and parsing the carpenter recipe json files from the assets and config folders
 */
public class CarpenterRecipeLoader extends RecipeLoader {

	public static final CarpenterRecipeLoader INSTANCE = new CarpenterRecipeLoader();

	private static final int GRID_WIDTH = 4;
	private static final int GRID_HEIGHT = 4;
	private static final String TYPE = "carpenter";
	private static final String DEFAULT_RECIPE_DIRECTORY = "assets/minefantasyreborn/carpenter_recipes";
	private static final String CUSTOM_RECIPE_DIRECTORY = "config/minefantasyreborn/custom/recipes/carpenter_recipes/";

	private CarpenterRecipeLoader() {} // no instances!

	public void postInit() {
		createCustomDataDirectory(CUSTOM_RECIPE_DIRECTORY);
		loadRegistry(TYPE, DEFAULT_RECIPE_DIRECTORY, CUSTOM_RECIPE_DIRECTORY);
	}

	@Override
	protected void parse(String name, JsonObject json) {

		String type = JsonUtils.getString(json, "type");
		Skill skill = Skill.fromName(JsonUtils.getString(json, "skill", "none"));
		String research = JsonUtils.getString(json, "research", "none");
		String sound = JsonUtils.getString(json, "sound", "minecraft:block.wood.hit");
		String tool_type = JsonUtils.getString(json, "tool_type", "none");
		float experience = JsonUtils.getFloat(json, "experience", 0F);
		int craft_time = JsonUtils.getInt(json, "craft_time", 0);
		int tool_tier = JsonUtils.getInt(json, "tool_tier", 0);
		byte tool_recipe = JsonUtils.getBoolean(json, "is_tool_recipe") ? (byte) 1 : (byte) 0;

		/* TODO:
		//  - check the difference between CustomToolRecipeCarpenter and ShapedCarpenterRecipes
		//	- check if there are any shapeless recipes*/

		//	IRecipe recipe;
		//	if ("crafting_shaped".equals(type) || "crafting_shapeless".equals(type)) {
		//		recipe = deserialize(GRID_WIDTH, GRID_HEIGHT, json);
		//	} else {
		//		throw new JsonSyntaxException("Invalid or unsupported recipe type '" + type + "'");
		//	}

		String[] pattern = shrink(patternFromJson(GRID_WIDTH, GRID_HEIGHT, JsonUtils.getJsonArray(json, "pattern")));
		ItemStack resultStack = deserializeItem(JsonUtils.getJsonObject(json, "result"), true);
		Object[] o = getInputs(pattern, json);

		CraftingManagerCarpenter.getInstance().addRecipe(name, resultStack, skill, research, SoundEvent.REGISTRY.getObject(new ResourceLocation(sound)), experience, tool_type, tool_tier, 0, craft_time, tool_recipe, o);
	}
}
