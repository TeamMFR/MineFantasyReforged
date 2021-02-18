package minefantasy.mfr.recipe;

import com.google.gson.Gson;
import minefantasy.mfr.MineFantasyReborn;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RecipeHelper {

	private RecipeHelper() {} // no instances!

	public static IRecipe getRecipeFromRegistry(String resourceName) {
		return CraftingManager.getRecipe(new ResourceLocation(resourceName));
	}

	public static IRecipe getMFRRecipe(String fileName) {
		return getRecipeFromRegistry(MineFantasyReborn.MOD_ID + ":" + fileName);
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
			g.toJson(tempRecipe, writer);
		}
		catch (IOException e) {
			System.out.println("Error during writing recipe json file:");
			e.printStackTrace();
		}
	}

	/**
	 * Returns a random Character from a-z
	 * @return a random lowercase Character
	 */
	public static Character getRandomChar() {
		Random r = new Random();
		return (char) (r.nextInt(26) + 'a');
	}

	public static String stripNamespace(String string) {
		return string.replaceAll(".+:", "");
	}

}
