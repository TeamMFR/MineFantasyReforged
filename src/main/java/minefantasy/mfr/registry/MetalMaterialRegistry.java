package minefantasy.mfr.registry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import minefantasy.mfr.material.MetalMaterial;
import minefantasy.mfr.util.FileUtils;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;

public class MetalMaterialRegistry extends DataLoader {
	private static final String JSON_FILE_EXT = "json";
	private static final String METAL_TYPES = "metal_types";
	private static final String INJECT_FOLDER = "inject";

	public static final MetalMaterialRegistry INSTANCE = new MetalMaterialRegistry();

	private static final String TYPE = "metal material";
	private static final String DEFAULT_RECIPE_DIRECTORY = "assets/minefantasyreborn/registry";
	private static final String CUSTOM_RECIPE_DIRECTORY = "config/minefantasyreborn/custom/registry";

	public void preInit() {
		createCustomDataDirectory(CUSTOM_RECIPE_DIRECTORY);
		loadRegistry(TYPE, DEFAULT_RECIPE_DIRECTORY, CUSTOM_RECIPE_DIRECTORY);
	}

	public void loadRegistryFiles(File source, String base, String type) {
		//noinspection ConstantConditions
		FileUtils.findFiles(Loader.instance().activeModContainer().getSource(), DEFAULT_RECIPE_DIRECTORY, (root, file) -> {
			String extension = FilenameUtils.getExtension(file.toString());

			if (!extension.equals(JSON_FILE_EXT)) {
				return;
			}

			Path relative = root.relativize(file);
			//			String modName = relative.getName(0).toString();

			//			if (relative.getName(0).toString().equals(METAL_TYPES) && relative.getNameCount() > 1) {
			if (relative.getNameCount() > 1) {
				String modName = relative.getName(0).toString();
				String fileName = FilenameUtils.removeExtension(relative.getFileName().toString());

				if (!Loader.isModLoaded(modName) || !fileName.equals(METAL_TYPES)) {
					return;
				}

				JsonObject jsonObject = fileToJsonObject(file, relative, type);
				parse(fileName, jsonObject);

				if (relative.getNameCount() > 2 && relative.getName(2).toString().equals(INJECT_FOLDER)) {
					//					String lootTableName = convertToRegistryName(root.resolve(METAL_TYPES).resolve(modName).resolve(INJECT_FOLDER).relativize(file));
					//					injectTables.put(lootTableName, file);
					return;
				} else {
					//					LootTableList.register(new ResourceLocation(AncientWarfareCore.MOD_ID, convertToRegistryName(relative)));
					return;
				}
			}

			//			LootTableList.register(new ResourceLocation(AncientWarfareCore.MOD_ID, convertToRegistryName(relative)));
		});
	}

	@Override
	protected void parse(String name, JsonObject json) {
		JsonArray metals = JsonUtils.getJsonArray(json, "metals");

		for (JsonElement e : metals) {
			JsonObject metal = JsonUtils.getJsonObject(e, "");
			parseMetal(metal);
		}
	}

	private void parseMetal(JsonObject json) {
		String name = JsonUtils.getString(json, "name");

		if (MetalMaterial.getMaterial(name) != null) {
			return;
		}

		JsonObject properties = JsonUtils.getJsonObject(json, "properties");
		float durability = JsonUtils.getFloat(properties, "durability");
		float flexibility = JsonUtils.getFloat(properties, "flexibility");
		float sharpness = JsonUtils.getFloat(properties, "sharpness");
		float hardness = JsonUtils.getFloat(properties, "hardness");
		float resistance = JsonUtils.getFloat(properties, "resistance");
		float density = JsonUtils.getFloat(properties, "density");
		int tier = JsonUtils.getInt(properties, "tier");
		int meltingPoint = JsonUtils.getInt(properties, "melting_point");
		int rarity = JsonUtils.getInt(properties, "rarity");
		int craftTier = JsonUtils.getInt(properties, "craft_tier");
		int craftTimeModifier = JsonUtils.getInt(properties, "craft_time_modifier");
		boolean unbreakable = JsonUtils.getBoolean(properties, "unbreakable");

		JsonObject armourStats = JsonUtils.getJsonObject(json, "armour_stats");
		float cuttingProtection = JsonUtils.getFloat(armourStats, "cutting");
		float bluntProtection = JsonUtils.getFloat(armourStats, "blunt");
		float piercingProtection = JsonUtils.getFloat(armourStats, "piercing");
		float[] armour = {cuttingProtection, bluntProtection, piercingProtection};

		JsonObject color = JsonUtils.getJsonObject(json, "color");
		int red = JsonUtils.getInt(color, "red");
		int green = JsonUtils.getInt(color, "green");
		int blue = JsonUtils.getInt(color, "blue");
		int[] colors = {red, green, blue};

		MetalMaterial metal = new MetalMaterial(name, tier, hardness, durability, flexibility, sharpness, resistance, density, armour, colors);
		metal.setMeltingPoint(meltingPoint);
		metal.setRarity(rarity);
		metal.setCrafterTiers(craftTier);
		metal.craftTimeModifier = craftTimeModifier;
		metal.setUnbreakable(unbreakable);
		metal.register();
	}
}
