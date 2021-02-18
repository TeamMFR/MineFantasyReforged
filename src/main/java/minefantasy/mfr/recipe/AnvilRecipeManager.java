package minefantasy.mfr.recipe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.crafting.anvil.CraftingManagerAnvil;
import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.util.FileUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.FilenameUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Class responsible for loading and parsing the anvil recipe json files from the assets and config folders
 */
@SuppressWarnings("Duplicates")
public class AnvilRecipeManager {
	public static final List<JsonObject> list = new ArrayList<>();

	public static Map<String, IRecipe> loaded = new TreeMap<>();

	// TODO: check net.minecraftforge.fml.common.registry.GameRegistry.addShapedRecipe as we should probably register our recipes as proper IRecipes

	public static final AnvilRecipeManager INSTANCE = new AnvilRecipeManager();

	private AnvilRecipeManager() {} // no instances!

	public static final String DEFAULT_RECIPE_DIRECTORY = "assets/minefantasyreborn/anvil_recipes";
	private static final String JSON_FILE_EXT = "json";
	public static final String ANVIL_RECIPE_CONFIG_DIRECTORY = "config/minefantasyreborn/recipes/anvil_recipes/";

	/**
	 * Called during preInit from {@link MineFantasyReborn#preInit(net.minecraftforge.fml.common.event.FMLPreInitializationEvent)} to create the config recipe
	 * directories if they are missing
	 */
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public void initializeAndExportDefaults() {
		// create recipe dirs if they don't exist

		File existTest = new File(ANVIL_RECIPE_CONFIG_DIRECTORY);
		if (!existTest.exists()) {
			existTest.mkdirs();
		}
	}

	public void loadRecipes() {
		int loadedCount = 0;
		MineFantasyReborn.LOG.info("loading anvil recipes from config directory");
		loadedCount += loadRecipesFromSource(new File(ANVIL_RECIPE_CONFIG_DIRECTORY), "");

		if (ConfigCrafting.loadDefaultRecipes) {
			loadedCount += loadRecipesFromSource(Loader.instance().activeModContainer().getSource(), DEFAULT_RECIPE_DIRECTORY);
		}

		MineFantasyReborn.LOG.info("AnvilRecipeManager: loaded {} anvil recipe(s)", loadedCount);
	}

	public static int loadRecipesFromSource(File source, String base) {

		FileUtils.findFiles(source, base, (root, file) -> {
			String relative = root.relativize(file).toString();

			//			@SuppressWarnings("squid:S4784")
			String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");

			String extension = FilenameUtils.getExtension(file.toString());

			if (extension.equals(JSON_FILE_EXT)) {

				List<String> lines;
				try {

					String path = source.getPath();

					Reader reader = Files.newBufferedReader((file.toAbsolutePath()));

					Gson gson = new Gson();
					JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
					IRecipe recipe = parseRecipeJson(jsonObject);

					if (!loaded.containsKey(name)) {
						loaded.put(name, recipe);
					}

				}

				catch (Exception e) {
					MineFantasyReborn.LOG.error("Error processing MFR recipe from file " + relative);
					e.printStackTrace();
				}
			}
		});

		return loaded.size();
	}

	public static void registerAnvilRecipes() {

	}

	private static IRecipe parseRecipeJson(JsonObject json) {
		String s = JsonUtils.getString(json, "type");

		/// custom tags of anvil
		String skill_used = JsonUtils.getString(json, "skill_used", "none");
		String research = JsonUtils.getString(json, "research", "none");
		boolean output_hot = JsonUtils.getBoolean(json, "output_hot", false);
		String tool_type = JsonUtils.getString(json, "tool_type", "none");
		int recipe_hammer = JsonUtils.getInt(json, "recipe_hammer", 0);
		int anvil_tier = JsonUtils.getInt(json, "anvil_tier", 0);
		int recipe_time = JsonUtils.getInt(json, "recipe_time", 0);

		IRecipe recipe = null;

//		if ("crafting_shaped".equals(s)) {
			recipe = deserialize(json);
//		} else if ("crafting_shapeless".equals(s)) {
//			recipe = deserialize(json);
//		} else {
//			throw new JsonSyntaxException("Invalid or unsupported recipe type '" + s + "'");
//		}

		String[] pattern = shrink(patternFromJson(JsonUtils.getJsonArray(json, "pattern")));

		Map<Character, Ingredient> map = deserializeKeyToCharMap(JsonUtils.getJsonObject(json, "key"));

		NonNullList<Ingredient> e = recipe.getIngredients();

//		ItemStack resultStack = deserializeItem(JsonUtils.getJsonObject(json, "result"), true);
		ItemStack resultStack = minefantasy.mfr.util.JsonUtils.getItemStack(JsonUtils.getJsonObject(json, "result"));
		Object[] o = new Object[] {};

		for (String p : pattern) {
			if (p.trim().length() > 0) {
				o = appendValue(o, p);
			}
		}

		byte type = s.equals("CustomToolRecipe") ? (byte) 1 :(byte) 0;


		for (Map.Entry<Character, Ingredient> i : map.entrySet()) {
			if (!Character.isWhitespace(i.getKey())) {
				o = appendValue(o, i.getKey());
				o = appendValue(o, i.getValue().getMatchingStacks()[0].getItem());
			}
		}
//  private IAnvilRecipe addRecipe(ItemStack result, Skill skill, String research, boolean hot, String tool,  int hammer, int anvil, int time, byte recipeType, Object... input) {
		CraftingManagerAnvil.getInstance().addRecipe(resultStack, null, research, output_hot, tool_type, recipe_hammer, anvil_tier, recipe_time, type, o);
		return recipe;
	}

	private static Object[] appendValue(Object[] obj, Object newObj) {

		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
		temp.add(newObj);
		return temp.toArray();
	}

	private static String[] patternFromJson(JsonArray jsonArray) {
		String[] astring = new String[jsonArray.size()];

		if (astring.length > 4) {
			throw new JsonSyntaxException("Invalid pattern: too many rows, 4 is maximum");
		} else if (astring.length == 0) {
			throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
		} else {
			for (int i = 0; i < astring.length; ++i) {
				String s = JsonUtils.getString(jsonArray.get(i), "pattern[" + i + "]");

				if (s.length() > 6) {
					throw new JsonSyntaxException("Invalid pattern: too many columns, 6 is maximum");
				}

				if (i > 0 && astring[0].length() != s.length()) {
					throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
				}

				astring[i] = s;
			}

			return astring;
		}
	}

	/**
	 * Deserialize a json object and return a ShapedRecipe from it
	 *
	 * @param json the whole json file
	 * @return a ShapedRecipe object of the json
	 */
	private static ShapedRecipes deserialize(JsonObject json) {
		String s = JsonUtils.getString(json, "group", "");
		Map<String, Ingredient> map = deserializeKey(JsonUtils.getJsonObject(json, "key"));
		String[] pattern = shrink(patternFromJson(JsonUtils.getJsonArray(json, "pattern")));
		int patternWidth = pattern[0].length(); // columns
		int patternHeight = pattern.length; // rows

		// a list of all slots with their matching ingredient ItemStack, 4x4 for Carpenter
		NonNullList<Ingredient> IngredientList = deserializeIngredients(pattern, map, patternWidth, patternHeight);
		ItemStack resultStack = minefantasy.mfr.util.JsonUtils.getItemStack(JsonUtils.getJsonObject(json, "result"));
//		ItemStack resultStack = deserializeItem(JsonUtils.getJsonObject(json, "result"), true);
		return new ShapedRecipes(s, patternWidth, patternHeight, IngredientList, resultStack);
	}

	/**
	 * This method matches the ingredients from the ingredient list with their keys (keys like "J", "B", etc.) and returns a list of itemStacks for
	 * every pattern slot with its corresponding item.
	 *
	 * @param pattern       The pattern element from the json. Each member of the array corresponds to a pattern line (top->down)
	 * @param ingredientMap The ingredients with their key mappings and itemStacks which are required to craft this item
	 * @param patternWidth  The width of the pattern, 6 for Anvil
	 * @param patternHeight The height of the pattern, 4 for Anvil
	 * @return a list with an itemStack for every pattern element
	 */
	private static NonNullList<Ingredient> deserializeIngredients(String[] pattern, Map<String, Ingredient> ingredientMap, int patternWidth, int patternHeight) {
		NonNullList<Ingredient> ingredientsList = NonNullList.<Ingredient>withSize(patternWidth * patternHeight, Ingredient.EMPTY);
		Set<String> set = Sets.newHashSet(ingredientMap.keySet());
		set.remove(" ");

		for (int i = 0; i < pattern.length; ++i) {
			for (int j = 0; j < pattern[i].length(); ++j) {
				String s = pattern[i].substring(j, j + 1);
				Ingredient ingredient = ingredientMap.get(s);

				if (ingredient == null) {
					throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
				}

				set.remove(s);
				ingredientsList.set(j + patternWidth * i, ingredient);
			}
		}

		if (!set.isEmpty()) {
			throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
		} else {
			return ingredientsList;
		}
	}

	private static Map<String, Ingredient> deserializeKey(JsonObject jsonObject) {
		Map<String, Ingredient> map = Maps.<String, Ingredient>newHashMap();

		for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			if (((String) entry.getKey()).length() != 1) {
				throw new JsonSyntaxException("Invalid key entry: '" + (String) entry.getKey() + "' is an invalid symbol (must be 1 character only).");
			}

			if (" ".equals(entry.getKey())) {
				throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
			}

			map.put(entry.getKey(), deserializeIngredient(entry.getValue()));
		}

		map.put(" ", Ingredient.EMPTY);
		return map;
	}

	// TODO: This should be removed once we deco the old addRecipe methods
	private static Map<Character, Ingredient> deserializeKeyToCharMap(JsonObject jsonObject) {
		Map<Character, Ingredient> map = Maps.<Character, Ingredient>newHashMap();

		for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			if (((String) entry.getKey()).length() != 1) {
				throw new JsonSyntaxException("Invalid key entry: '" + (String) entry.getKey() + "' is an invalid symbol (must be 1 character only).");
			}

			if (" ".equals(entry.getKey())) {
				throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
			}

			map.put(entry.getKey().charAt(0), deserializeIngredient(entry.getValue()));
		}

		map.put(' ', Ingredient.EMPTY);
		return map;
	}

	private static Ingredient deserializeIngredient(@Nullable JsonElement element) {
		if (element != null && !element.isJsonNull()) {
			if (element.isJsonObject()) {
				return Ingredient.fromStacks(minefantasy.mfr.util.JsonUtils.getItemStack(element));
			} else if (!element.isJsonArray()) {
				throw new JsonSyntaxException("Expected item to be object or array of objects");
			} else {
				JsonArray jsonarray = element.getAsJsonArray();

				if (jsonarray.size() == 0) {
					throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
				} else {
					ItemStack[] aitemstack = new ItemStack[jsonarray.size()];

					for (int i = 0; i < jsonarray.size(); ++i) {

						aitemstack[i] = minefantasy.mfr.util.JsonUtils.getItemStack(element);
					}

					return Ingredient.fromStacks(aitemstack);
				}
			}
		} else {
			throw new JsonSyntaxException("Item cannot be null");
		}
	}

	private static String[] shrink(String... strings) {
		int i = Integer.MAX_VALUE;
		int j = 0;
		int k = 0;
		int l = 0;

		for (int i1 = 0; i1 < strings.length; ++i1) {
			String s = strings[i1];
			i = Math.min(i, firstNonSpace(s));
			int j1 = lastNonSpace(s);
			j = Math.max(j, j1);

			if (j1 < 0) {
				if (k == i1) {
					++k;
				}

				++l;
			} else {
				l = 0;
			}
		}

		if (strings.length == l) {
			return new String[0];
		} else {
			String[] astring = new String[strings.length - l - k];

			for (int k1 = 0; k1 < astring.length; ++k1) {
				astring[k1] = strings[k1 + k].substring(i, j + 1);
			}

			return astring;
		}
	}

	private static int firstNonSpace(String str) {
		int i;

		for (i = 0; i < str.length() && str.charAt(i) == ' '; ++i) {
			;
		}

		return i;
	}

	private static int lastNonSpace(String str) {
		int i;

		for (i = str.length() - 1; i >= 0 && str.charAt(i) == ' '; --i) {
			;
		}

		return i;
	}

}
