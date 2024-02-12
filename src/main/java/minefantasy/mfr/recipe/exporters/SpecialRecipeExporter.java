package minefantasy.mfr.recipe.exporters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class which can be used to serialize jsons from the recipes in the MFR recipe registry
 */
public class SpecialRecipeExporter {

	public SpecialRecipeExporter() {
	}

	public void exportRecipes() {
		Gson g = new GsonBuilder().setPrettyPrinting().create();

//		for (Item key : SpecialForging.specialCrafts.keySet()) {
//			ItemStack input = new ItemStack(key);
//			ItemStack output = new ItemStack(SpecialForging.specialCrafts.get(key));
//
//			LinkedHashMap<String, Object> inputMap = new LinkedHashMap<>();
//			LinkedHashMap<String, Object> outputMap = new LinkedHashMap<>();
//			LinkedHashMap<String, Object> specialCraftMap = new LinkedHashMap<>();
//			// itemStackMap item
//			specialCraftMap.put("item", "#ORNATE_ITEMS");
//
//			//// Writing Properties to new obj ////
//			TempRecipe tempRecipe = new TempRecipe();
//
//			tempRecipe.type = "special_recipe_ornate";
//			tempRecipe.input = putItemAsMap(inputMap, input);
//			tempRecipe.result = putItemAsMap(outputMap, output);
//			tempRecipe.specialInput = specialCraftMap;
//			tempRecipe.research = "craft_ornate";
//
//			String fileName = output.getItem().getRegistryName().toString();
//			String trimmedName = RecipeHelper.stripNamespace(fileName);
//
//			String filePath = "C:\\Users\\user\\Desktop\\Coding\\MineFantasyReforged\\src\\main\\resources\\assets\\minefantasyreforged\\generated_recipes\\";
//			RecipeHelper.writeFile(filePath, trimmedName, tempRecipe, g);
//		}
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

		public String type;
		public String research;
		public Map<String, Object> input;
		public Map<String, Object> specialInput;
		public Map<String, Object> result;


		public TempRecipe() {}

	}
}
