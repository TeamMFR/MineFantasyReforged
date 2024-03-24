package minefantasy.mfr.recipe.exporters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.recipe.AnvilRecipeBase;
import minefantasy.mfr.recipe.CarpenterRecipeBase;
import minefantasy.mfr.recipe.CraftingManagerAnvil;
import minefantasy.mfr.recipe.CraftingManagerCarpenter;
import minefantasy.mfr.recipe.CraftingManagerSalvage;
import minefantasy.mfr.recipe.SalvageRecipeBase;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.RecipeHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class which can be used to serialize jsons from the recipes in the MFR recipe registry
 */
public class SalvageRecipeExporter {

	public SalvageRecipeExporter() {
	}

	public void exportRecipes() {
		Gson g = new GsonBuilder().setPrettyPrinting().create();

		List<Object> recipes = new ArrayList<>();
		for (IRecipe vanillaRecipe : ForgeRegistries.RECIPES) {
			recipes.add(vanillaRecipe);
		}
		recipes.addAll(CraftingManagerCarpenter.getRecipes());
		recipes.addAll(CraftingManagerAnvil.getRecipes());



		for (Object recipe : recipes) {

			ItemStack result = ItemStack.EMPTY;
			List<Ingredient> ingredients = new ArrayList<>();
			String recipeType;
			if (recipe != null) {
				if (recipe instanceof CarpenterRecipeBase) {
					recipeType = "Carpenter";
					result = ((CarpenterRecipeBase) recipe).getCarpenterRecipeOutput().copy();
					ingredients = new ArrayList<>(((CarpenterRecipeBase) recipe).getIngredients());
				} else if (recipe instanceof AnvilRecipeBase) {
					recipeType = "Anvil";
					result = ((AnvilRecipeBase) recipe).getAnvilRecipeOutput().copy();
					ingredients = new ArrayList<>(((AnvilRecipeBase) recipe).getIngredients());
				}
				else {
					recipeType = "Vanilla";
					result = ((IRecipe) recipe).getRecipeOutput().copy();
					ingredients = new ArrayList<>(((IRecipe) recipe).getIngredients());
				}
			}
			else {
				MineFantasyReforged.LOG.warn("fuck: " + recipe.toString());
				recipeType = "ah fuck cunts";
			}

			// If you want to activate this again, institute an override for player in this check, or this will break
			SalvageRecipeBase matchingRecipe = CraftingManagerSalvage.findMatchingRecipe(result, null);
			if (matchingRecipe != null || result.isEmpty()) {
				MineFantasyReforged.LOG.error("Rejected" + result.toString());
				continue;
			}
			MineFantasyReforged.LOG.error(recipeType + ": " + recipe);

			Map<ItemStack, Integer> outputStacks = createOutputsFromInputs(ingredients);

			int countInput = result.getCount();
			if (countInput > 1) {
				for (ItemStack stack : outputStacks.keySet()) {
					if (outputStacks.get(stack) > 1) {
						outputStacks.put(stack, Math.max(1, stack.getCount() / countInput));
					}
				}
			}

			LinkedHashMap<String, Object> input = new LinkedHashMap<>();
			List<Map<String, Object>> outputs = new ArrayList<>();

			putItemAsMap(input, result);
			input.remove("count");

			putOutputsToMap(outputStacks, outputs);

			//// Writing Properties to new obj ////
			TempRecipe tempRecipe = new TempRecipe();

			tempRecipe.input = input;
			tempRecipe.outputs = outputs;

			String fileName = result.getItem().getRegistryName().toString();
			String trimmedName = RecipeHelper.stripNamespace(fileName);

			if (result.hasTagCompound()) {
				if (result.getTagCompound().hasKey("mf_custom_materials")) {
					String metalName = CustomToolHelper.getComponentMaterial(result, "metal");
					String woodName = CustomToolHelper.getComponentMaterial(result, "wood");
					if (metalName != null) {
						trimmedName = trimmedName + "_" + metalName;
					}
					if (woodName != null) {
						trimmedName = trimmedName + "_" + woodName;
					}
				}
			}

			String filePath = "C:\\Users\\user\\Desktop\\Coding\\MineFantasyReforged\\src\\main\\resources\\assets\\minefantasyreforged\\generated_recipes\\";
			RecipeHelper.writeFile(filePath, trimmedName, tempRecipe, g);

		}
	}

	private static void putOutputsToMap(Map<ItemStack, Integer> outputStacks, List<Map<String, Object>> outputs) {
		for (ItemStack stack : outputStacks.keySet()) {
			LinkedHashMap<String, Object> itemMap = new LinkedHashMap<>();
			int count = outputStacks.get(stack);
			if (count > 1) {
				itemMap.put("type", "minefantasyreforged:item_count");
				stack.setCount(count);
				putItemAsMap(itemMap, stack);

			}
			else {
				putItemAsMap(itemMap, stack);
			}
			outputs.add(itemMap);
		}
	}

	private static Map<ItemStack, Integer> createOutputsFromInputs(List<Ingredient> ingredients) {
		ingredients.removeIf(ingredient -> ingredient.getValidItemStacksPacked().isEmpty());

		Map<ItemStack, Integer> ingredientCounts = new HashMap<>();
		for (Ingredient ingredient : ingredients) {
			ItemStack[] stacks = ingredient.getMatchingStacks();
			if (!ingredientCounts.containsKey(stacks[0])) {
				ingredientCounts.put(stacks[0], 1);
			}
			else {
				int increment = ingredientCounts.get(stacks[0]);
				increment++;
				ingredientCounts.put(stacks[0], increment);
			}
		}

		return ingredientCounts;
	}

	@Nonnull
	private static Map<String, Object> putItemAsMap(LinkedHashMap<String, Object> itemStackMap, ItemStack stack) {

		//// Result ////

		// itemStackMap item
		itemStackMap.put("item", stack.getItem().getRegistryName().toString());

		//Item Metadata
		if (stack.getMetadata() > 0) {
			itemStackMap.put("data", stack.getMetadata());
		}

		// itemStackMap's nbt
		if (stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			itemStackMap.put("nbt", nbt.toString());
		}

		// result count, if greater than 1
		if (stack.getCount() > 1) {
			itemStackMap.put("count", (stack.getCount()));
		}
		return itemStackMap;
	}

	/**
	 * Temporary recipe object which can be printed to json with gson
	 */
	public class TempRecipe {

		public String type = "salvage_recipe";
		public Map<String, Object> input;
		public List<Map<String, Object>> outputs;

		public TempRecipe() {}

	}
}
