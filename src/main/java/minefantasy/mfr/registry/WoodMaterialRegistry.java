package minefantasy.mfr.registry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import minefantasy.mfr.material.WoodMaterial;
import minefantasy.mfr.util.FileUtils;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;

public class WoodMaterialRegistry extends DataLoader {
	private static final String WOOD_TYPES = "wood_types";

	public static final WoodMaterialRegistry INSTANCE = new WoodMaterialRegistry();

	private static final String TYPE = "wood material";
	private static final String DEFAULT_RECIPE_DIRECTORY = "assets/minefantasyreforged/registry";
	private static final String CUSTOM_RECIPE_DIRECTORY = "config/minefantasyreforged/custom/registry";

	public void preInit() {
		createCustomDataDirectory(CUSTOM_RECIPE_DIRECTORY);
		loadRegistry(TYPE, DEFAULT_RECIPE_DIRECTORY, CUSTOM_RECIPE_DIRECTORY);
	}

	public void loadRegistryFiles(File source, String base, String type) {
		FileUtils.findFiles(source, base, (root, file) -> {
			String extension = FilenameUtils.getExtension(file.toString());

			if (!extension.equals(JSON_FILE_EXT)) {
				return;
			}

			Path relative = root.relativize(file);
			if (relative.getNameCount() > 1) {
				String modName = relative.getName(0).toString();
				String fileName = FilenameUtils.removeExtension(relative.getFileName().toString());

				if (!Loader.isModLoaded(modName) || !fileName.equals(WOOD_TYPES)) {
					return;
				}

				JsonObject jsonObject = fileToJsonObject(file, relative, type);
				parse(fileName, jsonObject);
			}

		});
	}

	@Override
	protected void parse(String name, JsonObject json) {
		JsonArray woods = JsonUtils.getJsonArray(json, "woods");

		for (JsonElement e : woods) {
			JsonObject wood = JsonUtils.getJsonObject(e, "");
			parseWood(wood);
		}
	}

	private void parseWood(JsonObject json) {
		String name = JsonUtils.getString(json, "name");

		ResourceLocation inputItemResourceLocation = new ResourceLocation(JsonUtils.getString(json, "inputItem"));
		int inputItemMeta = JsonUtils.getInt(json, "inputItemMeta");

		if (WoodMaterial.getMaterial(name) != null) {
			return;
		}

		JsonObject properties = JsonUtils.getJsonObject(json, "properties");
		float durability = JsonUtils.getFloat(properties, "durability");
		float flexibility = JsonUtils.getFloat(properties, "flexibility");
		float hardness = JsonUtils.getFloat(properties, "hardness");
		float resistance = JsonUtils.getFloat(properties, "resistance");
		float density = JsonUtils.getFloat(properties, "density");
		int tier = JsonUtils.getInt(properties, "tier");
		int rarity = JsonUtils.getInt(properties, "rarity");
		int craftTier = JsonUtils.getInt(properties, "craft_tier");
		int craftTimeModifier = JsonUtils.getInt(properties, "craft_time_modifier");

		JsonObject color = JsonUtils.getJsonObject(json, "color");
		int red = JsonUtils.getInt(color, "red");
		int green = JsonUtils.getInt(color, "green");
		int blue = JsonUtils.getInt(color, "blue");
		int[] colors = {red, green, blue};
		WoodMaterial wood = new WoodMaterial(name, tier, hardness, durability, flexibility, resistance, density, colors, inputItemResourceLocation, inputItemMeta);
		wood.setRarity(rarity);
		wood.setCrafterTiers(craftTier);
		wood.craftTimeModifier = craftTimeModifier;
		wood.register();
	}
}
