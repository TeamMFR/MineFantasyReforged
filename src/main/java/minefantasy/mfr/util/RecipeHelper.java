package minefantasy.mfr.util;

import com.google.gson.Gson;
import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
			g.toJson(tempRecipe, writer);
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

	/**
	 * Expands a small pattern (e.g. 3x2 size) to the given width and height by adding ItemStack.EMPTY stacks to the new, empty slots (e.g. 5x5 size)
	 * @param stacks The original list of the ItemStacks
	 * @param oldWidth The original width of this recipe, e.g. 3
	 * @param oldHeight The original height of this recipe, e.g. 3
	 * @param targetWidth The target width of this recipe, e.g. 5
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
}
