package minefantasy.mfr.registry;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.util.FileUtils;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class DataLoader {

	public static final String JSON_FILE_EXT = "json";

	public void loadRegistry(String type, String defaultDirectory, String configDirectory) {
		MineFantasyReforged.LOG.info("Loading " + type + " registry entries from config directory");
		loadRegistryFiles(new File(configDirectory), "", type);

		MineFantasyReforged.LOG.info("Loading default " + type + " registry entries");
		loadRegistryFiles(Loader.instance().activeModContainer().getSource(), defaultDirectory, type);
	}

	protected void createCustomDataDirectory(String directory) {
		// create custom data dirs if they don't exist
		File existTest = new File(directory);
		if (!existTest.exists()) {
			existTest.mkdirs();
		}
	}

	public void loadRegistryFiles(File source, String base, String type) {

		FileUtils.findFiles(source, base, (root, file) -> {
			Path relative = root.relativize(file);

			String name = FilenameUtils.removeExtension(relative.toString()).replaceAll("\\\\", "/");

			String extension = FilenameUtils.getExtension(file.toString());

			if (extension.equals(JSON_FILE_EXT)) {
				parse(name, fileToJsonObject(file, relative, type));
			}
		});
	}

	public JsonObject fileToJsonObject(Path file, Path relative, String type) {
		try {
			Reader reader = Files.newBufferedReader((file.toAbsolutePath()));
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
			return jsonObject;
		}

		catch (Exception e) {
			MineFantasyReforged.LOG.error("Error loading MFR " + type + " registry file " + relative);
			e.printStackTrace();
		}

		return new JsonObject();
	}

	protected abstract void parse(String name, JsonObject json);
}
