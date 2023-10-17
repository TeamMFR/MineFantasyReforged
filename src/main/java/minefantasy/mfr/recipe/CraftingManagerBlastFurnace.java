package minefantasy.mfr.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.recipe.factories.BlastFurnaceRecipeFactory;
import minefantasy.mfr.recipe.types.BlastFurnaceRecipeType;
import minefantasy.mfr.util.FileUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class CraftingManagerBlastFurnace {
	public static final String CONFIG_RECIPE_DIRECTORY = "config/" + Constants.CONFIG_DIRECTORY + "/custom/recipes/blast_furnace_recipes/";

	public CraftingManagerBlastFurnace() {
	}

	private static final IForgeRegistry<BlastFurnaceRecipeBase> BLAST_FURNACE_RECIPES = (new RegistryBuilder<BlastFurnaceRecipeBase>()).setName(new ResourceLocation(MineFantasyReforged.MOD_ID, "blast_furnace_recipes")).setType(BlastFurnaceRecipeBase.class).setMaxID(Integer.MAX_VALUE >> 5).disableSaving().allowModification().create();
	public static void init() {
		//call this so that the static final gets initialized at proper time
	}

	public static Collection<BlastFurnaceRecipeBase> getRecipes() {
		return BLAST_FURNACE_RECIPES.getValuesCollection();
	}

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final BlastFurnaceRecipeFactory factory = new BlastFurnaceRecipeFactory();
	private static final Method LOAD_CONSTANTS = ReflectionHelper.findMethod(JsonContext.class, "loadConstants", null, JsonObject[].class);

	public static void loadRecipes() {
		ModContainer modContainer = Loader.instance().activeModContainer();

		FileUtils.createCustomDataDirectory(CONFIG_RECIPE_DIRECTORY);
		Loader.instance().getActiveModList().forEach(m -> CraftingHelper.loadFactories(m,"assets/" + m.getModId() + "/blast_furnace_recipes", CraftingHelper.CONDITIONS));
		//noinspection ConstantConditions
		loadRecipes(modContainer, new File(CONFIG_RECIPE_DIRECTORY), "");
		Loader.instance().getActiveModList().forEach(m -> CraftingManagerBlastFurnace.loadRecipes(m, m.getSource(), "assets/" + m.getModId() + "/blast_furnace_recipes"));

		Loader.instance().setActiveModContainer(modContainer);
	}

	private static void loadRecipes(ModContainer mod, File source, String base) {
		JsonContext ctx = new JsonContext(mod.getModId());

		FileUtils.findFiles(source, base, root -> {
			Path fPath = root.resolve("_constants.json");
			if (fPath != null && Files.exists(fPath)) {
				BufferedReader reader = null;
				try {
					reader = Files.newBufferedReader(fPath);
					JsonObject[] json = JsonUtils.fromJson(GSON, reader, JsonObject[].class);
					LOAD_CONSTANTS.invoke(ctx, new Object[] {json});
				}
				catch (IOException | IllegalAccessException | InvocationTargetException e) {
					MineFantasyReforged.LOG.error("Error loading _constants.json: ", e);
					return false;
				}
				finally {
					IOUtils.closeQuietly(reader);
				}
			}
			return true;
		}, (root, file) -> {
			Loader.instance().setActiveModContainer(mod);

			String relative = root.relativize(file).toString();
			if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_"))
				return;

			String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
			ResourceLocation key = new ResourceLocation(ctx.getModId(), name);

			BufferedReader reader = null;
			try {
				reader = Files.newBufferedReader(file);
				JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);

				String type = ctx.appendModId(JsonUtils.getString(json, "type"));
				if (Loader.isModLoaded(mod.getModId())) {
					if (BlastFurnaceRecipeType.getByNameWithModId(type, mod.getModId()) != BlastFurnaceRecipeType.NONE) {
						BlastFurnaceRecipeBase recipe = factory.parse(ctx, json);
						recipe.setRegistryName(key);
						if (CraftingHelper.processConditions(json, "conditions", ctx)) {
							addRecipe(recipe, mod.getModId().equals(MineFantasyReforged.MOD_ID));
						}
					} else {
						MineFantasyReforged.LOG.info("Skipping recipe {} of type {} because it's not a MFR Blast Furnace recipe", key, type);
					}
				}
				else {
					MineFantasyReforged.LOG.info("Skipping recipe {} of type {} because it the mod it depends on is not loaded", key, type);
				}
			}
			catch (JsonParseException e) {
				MineFantasyReforged.LOG.error("Parsing error loading recipe {}", key, e);
			}
			catch (IOException e) {
				MineFantasyReforged.LOG.error("Couldn't read recipe {} from {}", key, file, e);
			}
			finally {
				IOUtils.closeQuietly(reader);
			}
		});
	}

	public static void addRecipe(BlastFurnaceRecipeBase recipe, boolean checkForExistence) {
		ItemStack itemStack = recipe.getBlastFurnaceRecipeOutput();
		if (ConfigCrafting.isBlastFurnaceItemCraftable(itemStack)) {
			NonNullList<ItemStack> subItems = NonNullList.create();

			itemStack.getItem().getSubItems(itemStack.getItem().getCreativeTab(), subItems);
			if (subItems.stream().anyMatch(s -> recipe.getBlastFurnaceRecipeOutput().isItemEqual(s))
					&& (!checkForExistence || !BLAST_FURNACE_RECIPES.containsKey(recipe.getRegistryName()))) {
				BLAST_FURNACE_RECIPES.register(recipe);
			}
		}
	}

	public static BlastFurnaceRecipeBase findMatchingRecipe(ItemStack input) {
		//// Normal, registered recipes.

		for (BlastFurnaceRecipeBase rec : getRecipes()) {
			if (rec.matches(input)) {
				return rec;
			}
		}
		return null;
	}

	public static BlastFurnaceRecipeBase getRecipeByName(String name) {
		ResourceLocation resourceLocation = new ResourceLocation(MineFantasyReforged.MOD_ID + ":" + name);
		if (!BLAST_FURNACE_RECIPES.containsKey(resourceLocation)) {
			MineFantasyReforged.LOG.error("Blast Furnace Recipe Registry does not contain recipe: {}", name);
		}
		return BLAST_FURNACE_RECIPES.getValue(resourceLocation);
	}
}
