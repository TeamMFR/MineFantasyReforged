package minefantasy.mfr.util;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonWriter;
import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RecipeHelper {

	private RecipeHelper() {} // no instances!

	public static IRecipe getRecipeFromRegistry(String resourceName) {
		return CraftingManager.getRecipe(new ResourceLocation(resourceName));
	}

	public static IRecipe getMFRRecipe(String fileName) {
		return getRecipeFromRegistry(MineFantasyReforged.MOD_ID + ":" + fileName);
	}

	public static void writeFile(String filePath, String trimmedName, Object tempRecipe, Gson g) {

		// check if file already exists
		File tmp = new File(filePath + trimmedName + ".json");
		boolean exists = tmp.exists();

		if (exists) {
			int index = 1;
			while (exists) {
				index++;
				File tmpFile = new File(filePath + trimmedName + "-" + index + ".json");
				exists = tmpFile.exists();
			}
			trimmedName += "-" + index;

		}

		try (FileWriter writer = new FileWriter(filePath + trimmedName + ".json")) {
			JsonWriter jsonWriter = g.newJsonWriter(writer);
			jsonWriter.setIndent("\t");
			g.toJson(tempRecipe, tempRecipe.getClass(),  jsonWriter);
		}
		catch (IOException e) {
			System.out.println("Error during writing recipe json file:");
			e.printStackTrace();
		}
	}

	/**
	 * Returns a random Character from a-z
	 *
	 * @return a random lowercase Character
	 */
	public static Character getRandomChar() {
		Random r = new Random();
		return (char) (r.nextInt(26) + 'a');
	}

	public static String stripNamespace(String string) {
		return string.replaceAll(".+:", "");
	}

	public static NonNullList<Ingredient> duplicateList(NonNullList<Ingredient> list, Integer repeatAmount) {
		if (repeatAmount == null) {
			return NonNullList.create();
		}
		NonNullList<Ingredient> duplicateList = NonNullList.create();
		for (int i = 0; i < repeatAmount; i++) {
			duplicateList.addAll(list);
		}
		return duplicateList;
	}

	/**
	 * Expands a small pattern (e.g. 3x2 size) to the given width and height by adding ItemStack.EMPTY stacks to the new, empty slots (e.g. 5x5 size)
	 *
	 * @param stacks       The original list of the ItemStacks
	 * @param oldWidth     The original width of this recipe, e.g. 3
	 * @param oldHeight    The original height of this recipe, e.g. 3
	 * @param targetWidth  The target width of this recipe, e.g. 5
	 * @param targetHeight The target height of this recipe, e.g. 4
	 * @return A new List<ItemStack> with the original recipe aligned to the "beginning" of the list ("top-left aligned") and wrapped around with empty ItemStacks to fill the required grid size
	 */
	public static List<ItemStack> expandPattern(List<ItemStack> stacks, int oldWidth, int oldHeight, int targetWidth, int targetHeight) {
		List<ItemStack> expandedPattern = new ArrayList<>();
		int index = 0;

		if (oldWidth < targetWidth) {
			for (int i = 0; i < oldHeight; i++) {
				for (int j = 0; j < oldWidth; j++) {
					expandedPattern.add(stacks.get(index++));
				}
				for (int j = 0; j < targetWidth - oldWidth; j++) {
					expandedPattern.add(ItemStack.EMPTY);
				}
			}
		} else {
			expandedPattern.addAll(stacks);
		}
		if (oldHeight < targetHeight) {
			for (int i = 0; i < (targetHeight - oldHeight) * targetWidth; i++) {
				expandedPattern.add(ItemStack.EMPTY);
			}
		}
		return expandedPattern;
	}

	/**
	 * Expands a small pattern (e.g. 3x2 size) to the given width and height by adding ItemStack.EMPTY stacks to the new, empty slots (e.g. 5x5 size)
	 *
	 * @param ingredients  The original list of the Ingredients
	 * @param oldWidth     The original width of this recipe, e.g. 3
	 * @param oldHeight    The original height of this recipe, e.g. 3
	 * @param targetWidth  The target width of this recipe, e.g. 5
	 * @param targetHeight The target height of this recipe, e.g. 4
	 * @return A new List<ItemStack> with the original recipe aligned to the "beginning" of the list ("top-left aligned") and wrapped around with empty ItemStacks to fill the required grid size
	 */
	public static List<Ingredient> expandPattern(NonNullList<Ingredient> ingredients, int oldWidth, int oldHeight, int targetWidth, int targetHeight) {
		List<Ingredient> expandedPattern = new ArrayList<>();
		int index = 0;

		if (oldWidth < targetWidth) {
			for (int i = 0; i < oldHeight; i++) {
				for (int j = 0; j < oldWidth; j++) {
					expandedPattern.add(ingredients.get(index++));
				}
				for (int j = 0; j < targetWidth - oldWidth; j++) {
					expandedPattern.add(Ingredient.EMPTY);
				}
			}
		} else {
			expandedPattern.addAll(ingredients);
		}
		if (oldHeight < targetHeight) {
			for (int i = 0; i < (targetHeight - oldHeight) * targetWidth; i++) {
				expandedPattern.add(Ingredient.EMPTY);
			}
		}
		return expandedPattern;
	}

	/**
	 * Author: Choonster in TestMod3
	 * Parse the input of a shaped recipe.
	 * <p>
	 * Adapted from {@link ShapedOreRecipe#factory}.
	 *
	 * @param context The parsing context
	 * @param json    The recipe's JSON object
	 * @return A ShapedPrimer containing the input specified in the JSON object
	 */
	public static CraftingHelper.ShapedPrimer parseShaped(final JsonContext context, final JsonObject json) {
		final Map<Character, Ingredient> ingredientMap = Maps.newHashMap();
		for (final Map.Entry<String, JsonElement> entry : net.minecraft.util.JsonUtils.getJsonObject(json, "key").entrySet()) {
			if (entry.getKey().length() != 1)
				throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
			if (" ".equals(entry.getKey()))
				throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");

			ingredientMap.put(entry.getKey().toCharArray()[0], CraftingHelper.getIngredient(entry.getValue(), context));
		}

		ingredientMap.put(' ', Ingredient.EMPTY);

		final JsonArray patternJ = net.minecraft.util.JsonUtils.getJsonArray(json, "pattern");

		if (patternJ.size() == 0)
			throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");

		final String[] pattern = new String[patternJ.size()];
		for (int x = 0; x < pattern.length; ++x) {
			final String line = net.minecraft.util.JsonUtils.getString(patternJ.get(x), "pattern[" + x + "]");
			if (x > 0 && pattern[0].length() != line.length())
				throw new JsonSyntaxException("Invalid pattern: each row must  be the same width");
			pattern[x] = line;
		}

		final CraftingHelper.ShapedPrimer primer = new CraftingHelper.ShapedPrimer();
		primer.width = pattern[0].length();
		primer.height = pattern.length;
		primer.mirrored = net.minecraft.util.JsonUtils.getBoolean(json, "mirrored", true);
		primer.input = NonNullList.withSize(primer.width * primer.height, Ingredient.EMPTY);

		final Set<Character> keys = Sets.newHashSet(ingredientMap.keySet());
		keys.remove(' ');

		int index = 0;
		for (final String line : pattern) {
			for (final char chr : line.toCharArray()) {
				final Ingredient ing = ingredientMap.get(chr);
				if (ing == null)
					throw new JsonSyntaxException("Pattern references symbol '" + chr + "' but it's not defined in the key");
				primer.input.set(index++, ing);
				keys.remove(chr);
			}
		}

		if (!keys.isEmpty())
			throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + keys);

		return primer;
	}

	/**
	 * Author: Choonster in TestMod3
	 * Parse the input of a shapeless recipe.
	 * <p>
	 * Adapted from {@link ShapelessOreRecipe#factory}.
	 *
	 * @param context The parsing context
	 * @param json    The recipe's JSON object
	 * @return A NonNullList containing the ingredients specified in the JSON object
	 */
	public static NonNullList<Ingredient> parseShapeless(final JsonContext context, final JsonObject json) {
		final NonNullList<Ingredient> ingredients = NonNullList.create();
		for (final JsonElement element : JsonUtils.getJsonArray(json, "ingredients"))
			ingredients.add(CraftingHelper.getIngredient(element, context));

		if (ingredients.isEmpty())
			throw new JsonParseException("No ingredients for shapeless recipe");

		return ingredients;
	}
}
