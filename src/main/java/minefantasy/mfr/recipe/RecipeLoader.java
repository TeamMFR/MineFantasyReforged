package minefantasy.mfr.recipe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import minefantasy.mfr.registry.DataLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreIngredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public abstract class RecipeLoader extends DataLoader {

	protected static String[] shrink(String... strings) {
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

	public static Ingredient deserializeIngredient(@Nullable JsonElement element) {
		if (element != null && !element.isJsonNull()) {
			if (element.isJsonObject()) {
				if (element.getAsJsonObject().has("type") && JsonUtils.getString(element.getAsJsonObject(), "type").equals("oreDict")){
					return new OreIngredient(JsonUtils.getString(element.getAsJsonObject(), "ore"));
				}
				else {
					return Ingredient.fromStacks(minefantasy.mfr.util.JsonUtils.getItemStack(element));
				}
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

	/**
	 * This method matches the ingredients from the ingredient list with their keys (keys like "J", "B", etc.) and returns a list of itemStacks for
	 * every pattern slot with its corresponding item.
	 *
	 * @param pattern       The pattern element from the json. Each member of the array corresponds to a pattern line (top->down)
	 * @param ingredientMap The ingredients with their key mappings and itemStacks which are required to craft this item
	 * @param patternWidth  The width of the pattern, e.g. 6 for Anvil
	 * @param patternHeight The height of the pattern, e.g. 4 for Anvil
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

	static Map<Character, Ingredient> deserializeKeyToCharMap(JsonObject jsonObject) {
		Map<Character, Ingredient> map = Maps.<Character, Ingredient>newHashMap();

		for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			if (entry.getKey().length() != 1) {
				throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
			}

			if (" ".equals(entry.getKey())) {
				throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
			}

			map.put(entry.getKey().charAt(0), deserializeIngredient(entry.getValue()));
		}

		map.put(' ', Ingredient.EMPTY);
		return map;
	}

	static Object[] appendValue(Object[] obj, Object newObj) {

		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
		temp.add(newObj);
		return temp.toArray();
	}

	static String[] patternFromJson(int columns, int rows, JsonArray jsonArray) {
		String[] astring = new String[jsonArray.size()];

		if (astring.length > rows) {
			throw new JsonSyntaxException("Invalid pattern: too many rows, " + rows + " is maximum");
		} else if (astring.length == 0) {
			throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
		} else {
			for (int i = 0; i < astring.length; ++i) {
				String s = JsonUtils.getString(jsonArray.get(i), "pattern[" + i + "]");

				if (s.length() > columns) {
					throw new JsonSyntaxException("Invalid pattern: too many columns, " + columns + " is maximum");
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
	 * Returns an ItemStack from a json "item" object. An example input object: "item": "minecraft:milk_bucket"
	 *
	 * @param jsonObject the item json object with its related properties (registry name, data, count, etc)
	 * @param useCount   if true, also attempt to set the count of the itemStack
	 * @return An ItemStack from the item
	 */
	static ItemStack deserializeItem(JsonObject jsonObject, boolean useCount) {
		String itemRegistryName = JsonUtils.getString(jsonObject, "item");
		Item item = Item.getByNameOrId(itemRegistryName);

		if (item == null) {
			throw new JsonSyntaxException("Unknown item '" + itemRegistryName + "'");
		} else if (item.getHasSubtypes() && !jsonObject.has("data")) {
			throw new JsonParseException("Missing data for item '" + itemRegistryName + "'");
		} else {
			NBTTagCompound nbt = null;
			if (jsonObject.has("nbt")) {
				try {
					JsonElement element = jsonObject.get("nbt");

					if (element.isJsonObject())
						nbt = new NBTTagCompound();
					else
						nbt = JsonToNBT.getTagFromJson(JsonUtils.getString(element, "nbt"));
				}
				catch (NBTException e) {
					throw new JsonSyntaxException("Invalid NBT Entry: " + e.toString());
				}
			}
			int data = JsonUtils.getInt(jsonObject, "data", 0);
			int count = useCount ? JsonUtils.getInt(jsonObject, "count", 1) : 1;
			ItemStack stack = new ItemStack(item, count, data);
			if (nbt != null){
				stack.setTagCompound(nbt);
			}
			return stack;
		}
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
