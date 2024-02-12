package minefantasy.mfr.recipe.exporters;

import java.util.Map;

/**
 * Class which can be used to serialize jsons from the recipes in the MFR recipe registry
 */
public class RecipeExporterAnvil {

	public RecipeExporterAnvil() {
		exportRecipes();
	}

	public void exportRecipes() {
//
//		CraftingManagerAnvil instance = CraftingManagerAnvil.getInstance();
//		List recipes = instance.recipes;
//
//		Gson g = new GsonBuilder().setPrettyPrinting().create();
//
//		for (int r = 0; r < recipes.size(); r++) {
//
//			Object recipe = recipes.get(r);
//			IAnvilRecipe iAnvilRecipe = (IAnvilRecipe) recipe;
//
//			//// Properties ////
//
//			boolean is_tool_recipe = recipe instanceof CustomToolRecipeAnvil;
//
//			String type = recipe instanceof CustomToolRecipeAnvil ? "CustomToolRecipe" : "ShapedAnvilRecipes";
//
//			int recipe_time = 5;
//			try { recipe_time = iAnvilRecipe.getCraftTime(); } catch (Exception e) {}
//
//			String skill_used = "none";
//			try { skill_used = iAnvilRecipe.getSkill().unlocalizedName; } catch (Exception e) {}
//
//			String tool_type = "none";
//			try { tool_type = iAnvilRecipe.getToolType(); } catch (Exception e) {}
//
//			String research = "none";
//			try { research = iAnvilRecipe.getResearch(); } catch (Exception e) {}
//
//			int anvil_tier = 0;
//			try { anvil_tier = iAnvilRecipe.getAnvilTier(); } catch (Exception e) {}
//
//			boolean output_hot = false;
//			try { output_hot = iAnvilRecipe.outputHot(); } catch (Exception e) {}
//
//			int recipe_hammer = 0;
//			try { recipe_hammer = iAnvilRecipe.getRecipeHammer(); } catch (Exception e) {}
//
//			int recipeWidth = ((ShapedAnvilRecipes) recipe).recipeWidth;
//			int recipeHeight = ((ShapedAnvilRecipes) recipe).recipeHeight;
//
//			ItemStack[] recipeItems = ((ShapedAnvilRecipes) recipe).recipeItems;
//
//			List<String> recipeUniqueIngredientItemList = new ArrayList<>();
//			List<ItemStack> recipeItemsList = new ArrayList<>();
//
//			for (ItemStack stack : recipeItems) {
//				if (stack == null) {
//					continue;
//				}
//
//				recipeItemsList.add(stack);
//
//				String itemName = stack.getItem().getRegistryName().toString();
//				if (!recipeUniqueIngredientItemList.contains(itemName)) {
//					recipeUniqueIngredientItemList.add(itemName);
//				}
//			}
//
//			Map<Character, String> keyMap = new HashMap<>();
//
//			for (String item : recipeUniqueIngredientItemList) {
//
//				String trimmed = RecipeHelper.stripNamespace(item);
//
//				if (!keyMap.keySet().contains(trimmed.toUpperCase().charAt(0))) {
//					keyMap.put(trimmed.toUpperCase().charAt(0), item);
//				} else if ((trimmed.length() > 1 && !keyMap.keySet().contains(trimmed.toUpperCase().charAt(1)))) {
//					keyMap.put(trimmed.toUpperCase().charAt(1), item);
//				} else if ((trimmed.length() > 2 && !keyMap.keySet().contains(trimmed.toUpperCase().charAt(2)))) {
//					keyMap.put(trimmed.toUpperCase().charAt(2), item);
//				} else if ((trimmed.length() > 3 && !keyMap.keySet().contains(trimmed.toUpperCase().charAt(3)))) {
//					keyMap.put(trimmed.toUpperCase().charAt(3), item);
//				} else if ((trimmed.length() > 4 && !keyMap.keySet().contains(trimmed.toUpperCase().charAt(4)))) {
//					keyMap.put(trimmed.toUpperCase().charAt(4), item);
//				} else if ((trimmed.length() > 5 && !keyMap.keySet().contains(trimmed.toUpperCase().charAt(5)))) {
//					keyMap.put(trimmed.toUpperCase().charAt(5), item);
//				} else {
//					boolean foundKey = false;
//					while (!foundKey) {
//						Character character = Character.toUpperCase(RecipeHelper.getRandomChar());
//						if (!keyMap.keySet().contains(character)) {
//							keyMap.put(Character.toUpperCase(character), item);
//							foundKey = true;
//						}
//
//					}
//				}
//			}
//
//			Map<String, Character> reverseKeyMap = new HashMap<>();
//			for (Map.Entry<Character, String> entry : keyMap.entrySet()) {
//				reverseKeyMap.put(entry.getValue(), entry.getKey());
//			}
//
//			String[] pattern = {"      ", "      ", "      ", "      ",};
//
//			List<ItemStack> list = new ArrayList<>();
//
//			Collections.addAll(list, recipeItems);
//
//			Iterator iterator = list.iterator();
//			for (int row = 0; row < recipeHeight; row++) {
//
//				for (int column = 0; column < recipeWidth; column++) {
//
//					if (!iterator.hasNext()) {
//						System.out.println("this shouldn't happen");
//					}
//
//					ItemStack itemStack = (ItemStack) iterator.next();
//					if (itemStack == null || itemStack.isEmpty()) {
//						continue;
//					}
//					String curritem = (itemStack.getItem().getRegistryName().toString());
//					Character character = reverseKeyMap.get(curritem);
//
//					char[] myNameChars = pattern[row].toCharArray();
//					myNameChars[column] = character;
//					pattern[row] = String.valueOf(myNameChars);
//				}
//			}
//
//			Map<String, Object> result = new HashMap<>();
//
//			//// Result ////
//
//			// result item
//			result.put("item", iAnvilRecipe.getAnvilRecipeOutput().getItem().getRegistryName().toString());
//
//			// result count, if greater than 1
//			if (iAnvilRecipe.getAnvilRecipeOutput().getCount() > 1) {
//				result.put("count", (iAnvilRecipe.getAnvilRecipeOutput().getCount()));
//			}
//
//			// result's nbt
//			if (iAnvilRecipe.getAnvilRecipeOutput().hasTagCompound()) {
//				Object nbt = iAnvilRecipe.getAnvilRecipeOutput().getTagCompound();
//				result.put("nbt", nbt.toString());
//			}
//
//			//// construct Key ////
//			Map<Character, Map<String, Object>> key = new HashMap<>();
//			for (Map.Entry entry : keyMap.entrySet()) {
//
//				if (entry.getValue().equals("minecraft:air")) {
//					continue;
//				}
//
//				//				Map<Character,  Map<String, String>> characterStringMap = new HashMap<>();
//				Map<String, Object> subMap = new HashMap<>();
//
//				for (ItemStack stack : recipeItemsList) {
//
//					if (stack.getItem().getRegistryName().toString().equals(entry.getValue().toString())) {
//
//						if (stack.getItemDamage() != 0 && stack.getItemDamage() != 32767) {
//							subMap.put("data", (stack.getItemDamage()));
//						}
//
//						if (stack.hasTagCompound()) {
//							subMap.put("nbt", stack.getTagCompound().toString());
//						}
//					}
//
//					break;
//				}
//				subMap.put("item", entry.getValue());
//				//				characterStringMap.put((Character) entry.getKey(), subMap);
//				key.put((Character) entry.getKey(), subMap);
//			}
//
//			//// Writing Properties to new obj ////
//			TempRecipe tempRecipe = new TempRecipe();
//
//			tempRecipe.type = type;
//			tempRecipe.skill_used = skill_used;
//			tempRecipe.research = research;
//			tempRecipe.tool_type = tool_type;
//			tempRecipe.anvil_tier = anvil_tier;
//			tempRecipe.recipe_time = recipe_time;
//			tempRecipe.recipe_hammer = recipe_hammer;
//			tempRecipe.output_hot = output_hot;
//			tempRecipe.pattern = pattern;
//
//			tempRecipe.result = result;
//
//			tempRecipe.key = key;
//
//			String fileName = iAnvilRecipe.getAnvilRecipeOutput().getItem().getRegistryName().toString();
//			String trimmedName = RecipeHelper.stripNamespace(fileName);
//			String filePath = "C:\\git\\MineFantasy-reforged\\src\\main\\resources\\assets\\minefantasyreforged\\generated_recipes\\";
//			RecipeHelper.writeFile(filePath, trimmedName, tempRecipe, g);
//		}
	}

	/**
	 * Temporary recipe object which can be printed to json with gson
	 */
	public class TempRecipe {

		public String type;
		public String skill_used;
		public String research;
		public String tool_type;
		public boolean is_tool_recipe;
		public int anvil_tier;
		public int recipe_time;
		public int recipe_hammer;
		public boolean output_hot;
		public String[] pattern;
		public Map<Character, Map<String, Object>> key;
		public Map<String, Object> result;

		TempRecipe() {}

	}
}