package minefantasy.mfr.recipe.exporters;

import java.util.Map;

/**
 * Class which can be used to serialize jsons from the recipes in the MFR recipe registry
 */
public class RecipeExporterCarpenter {

	public RecipeExporterCarpenter() {
		exportRecipes();
	}

	public void exportRecipes() {

//		CraftingManagerCarpenter instance = CraftingManagerCarpenter.getInstance();
//		List recipes = instance.recipes;
//
//		Gson g = new GsonBuilder().setPrettyPrinting().create();
//
//		for (int r = 0; r < recipes.size(); r++) {
//
//			Object recipe = recipes.get(r);
//			ICarpenterRecipe iCarpenterRecipe = (ICarpenterRecipe) recipe;
//
//			//// Properties ////
//
//			boolean is_tool_recipe = recipe instanceof CustomToolRecipeCarpenter;
//
//			String type = recipe instanceof ShapedCarpenterRecipes ? "crafting_shaped" : "crafting_shapeless";
//
//			int craft_time = 5;
//			try { craft_time = iCarpenterRecipe.getCraftTime(); } catch (Exception e) {}
//
//			String skill = "none";
//			try { skill = iCarpenterRecipe.getSkill().unlocalizedName; } catch (Exception e) {}
//
//			String tool_type = "none";
//			try { tool_type = iCarpenterRecipe.getToolType(); } catch (Exception e) {}
//
//			int experience = 0;
//			try { experience = (int) iCarpenterRecipe.getExperience(); } catch (Exception e) {}
//
//			String sound = "none";
//			try { sound = iCarpenterRecipe.getSound().getSoundName().toString(); } catch (Exception e) {}
//
//			String research = "none";
//			try { research = iCarpenterRecipe.getResearch(); } catch (Exception e) {}
//
//			int block_tier = -1;
//			try { block_tier = iCarpenterRecipe.getBlockTier(); } catch (Exception e) {}
//
//			int recipe_hammer = 0;
//			try { recipe_hammer = iCarpenterRecipe.getRecipeHammer(); } catch (Exception e) {}
//
//			int recipeWidth = ((ShapedCarpenterRecipes) recipe).recipeWidth;
//			int recipeHeight = ((ShapedCarpenterRecipes) recipe).recipeHeight;
//
//			ItemStack[] recipeItems = ((ShapedCarpenterRecipes) recipe).recipeItems;
//
//			List<String> recipeUniqueIngredientItemList = new ArrayList<>();
//			List<ItemStack> recipeItemsList = new ArrayList<>();
//
//			for (ItemStack stack : recipeItems) {
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
//			String[] pattern = {"    ", "    ", "    ", "    ",};
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
//					if (itemStack.isEmpty()) {
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
//			result.put("item", iCarpenterRecipe.getCarpenterRecipeOutput().getItem().getRegistryName().toString());
//
//			// result count, if greater than 1
//			if (iCarpenterRecipe.getCarpenterRecipeOutput().getCount() > 1) {
//				result.put("count", (iCarpenterRecipe.getCarpenterRecipeOutput().getCount()));
//			}
//
//			// result's nbt
//			if (iCarpenterRecipe.getCarpenterRecipeOutput().hasTagCompound()) {
//				Object nbt = iCarpenterRecipe.getCarpenterRecipeOutput().getTagCompound();
//				result.put("nbt", nbt);
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
//				subMap.put("item", entry.getValue());
//
//				for (ItemStack stack : recipeItemsList) {
//					if (stack.getItem().getRegistryName().toString().equals(entry.getValue()) && stack.getItemDamage() != 0 && stack.getItemDamage() != 32767) {
//						subMap.put("data", (stack.getItemDamage()));
//						if (stack.hasTagCompound()) {
//							subMap.put("nbt", stack.getTagCompound());
//						}
//						break;
//					}
//				}
//
//				//				characterStringMap.put((Character) entry.getKey(), subMap);
//				key.put((Character) entry.getKey(), subMap);
//			}
//
//			//// Writing Properties to new obj ////
//			TempRecipe tempRecipe = new TempRecipe();
//
//			tempRecipe.type = type;
//			tempRecipe.skill = skill;
//			tempRecipe.research = research;
//			tempRecipe.tool_type = tool_type;
//			tempRecipe.is_tool_recipe = is_tool_recipe;
//			tempRecipe.block_tier = block_tier;
//			tempRecipe.craft_time = craft_time;
//			tempRecipe.recipe_hammer = recipe_hammer;
//			tempRecipe.experience = experience;
//			tempRecipe.sound = sound;
//			tempRecipe.pattern = pattern;
//
//			tempRecipe.result = result;
//
//			tempRecipe.key = key;
//
//			String fileName = iCarpenterRecipe.getCarpenterRecipeOutput().getItem().getRegistryName().toString();
//			String trimmedName = RecipeHelper.stripNamespace(fileName);
//			String filePath = "C:\\git\\MineFantasy-reforged\\src\\main\\resources\\assets\\minefantasyreforged\\generated_recipes\\";
//			RecipeHelper.writeFile(filePath, trimmedName, tempRecipe, g);
//
//		}
	}

	/**
	 * Temporary recipe object which can be printed to json with gson
	 */
	public class TempRecipe {

		public String type;
		public String skill;
		public String research;
		public String tool_type;
		public boolean is_tool_recipe;
		public int block_tier;
		public int craft_time;
		public int recipe_hammer;
		public int experience;
		public String sound;
		public String[] pattern;
		public Map<Character, Map<String, Object>> key;
		public Map<String, Object> result;

		public TempRecipe() {}

	}
}