package minefantasy.mfr.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.recipe.factories.RoastRecipeFactory;
import minefantasy.mfr.recipe.types.RoastRecipeType;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CraftingManagerRoast {

	public static final String CONFIG_RECIPE_DIRECTORY = "config/" + Constants.CONFIG_DIRECTORY + "/custom/recipes/roast_recipes/";

	public CraftingManagerRoast() {

	}

	private static final IForgeRegistry<RoastRecipeBase> ROAST_RECIPES = (new RegistryBuilder<RoastRecipeBase>()).setName(new ResourceLocation(MineFantasyReforged.MOD_ID, "roast_recipes")).setType(RoastRecipeBase.class).setMaxID(Integer.MAX_VALUE >> 5).disableSaving().allowModification().create();

	public static void init() {
		//call this so that the static final gets initialized at proper time
	}

	public static Collection<RoastRecipeBase> getRecipes() {
		return ROAST_RECIPES.getValuesCollection();
	}

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final RoastRecipeFactory factory = new RoastRecipeFactory();
	private static final Method LOAD_CONSTANTS = ReflectionHelper.findMethod(JsonContext.class, "loadConstants", null, JsonObject[].class);

	public static void loadRecipes() {
		ModContainer modContainer = Loader.instance().activeModContainer();

		FileUtils.createCustomDataDirectory(CONFIG_RECIPE_DIRECTORY);
		Loader.instance().getActiveModList().forEach(m -> CraftingHelper.loadFactories(m,"assets/" + m.getModId() + "/roast_recipes", CraftingHelper.CONDITIONS));
		//noinspection ConstantConditions
		loadRecipes(modContainer, new File(CONFIG_RECIPE_DIRECTORY), "");
		Loader.instance().getActiveModList().forEach(m -> CraftingManagerRoast.loadRecipes(m, m.getSource(), "assets/" + m.getModId() + "/roast_recipes"));

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
					if (RoastRecipeType.getByNameWithModId(type, mod.getModId()) != RoastRecipeType.NONE) {
						RoastRecipeBase recipe = factory.parse(ctx, json);
						recipe.setRegistryName(key);
						if (CraftingHelper.processConditions(json, "conditions", ctx)) {
							addRecipe(recipe, mod.getModId().equals(MineFantasyReforged.MOD_ID));
						}
					} else {
						MineFantasyReforged.LOG.info("Skipping recipe {} of type {} because it's not a MFR Roast recipe", key, type);
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

	public static void addRecipe(RoastRecipeBase recipe, boolean checkForExistence) {
		ItemStack itemStack = recipe.getRoastRecipeOutput();
		if (ConfigCrafting.isRoastItemCraftable(itemStack)) {
			NonNullList<ItemStack> subItems = NonNullList.create();

			itemStack.getItem().getSubItems(itemStack.getItem().getCreativeTab(), subItems);
			if (subItems.stream().anyMatch(s -> recipe.getRoastRecipeOutput().isItemEqual(s))
					&& (!checkForExistence || !ROAST_RECIPES.containsKey(recipe.getRegistryName()))) {
				ROAST_RECIPES.register(recipe);
			}
		}
	}

	public static RoastRecipeBase findMatchingRecipe(ItemStack input, boolean isOven) {
		//// Normal, registered recipes.

		for (RoastRecipeBase rec : getRecipes()) {
			if (rec.matches(input, isOven)) {
				return rec;
			}
		}
		return null;
	}

	public static RoastRecipeBase getRecipeByName(String name) {
		ResourceLocation resourceLocation = new ResourceLocation(MineFantasyReforged.MOD_ID + ":" + name);
		if (!ROAST_RECIPES.containsKey(resourceLocation)) {
			MineFantasyReforged.LOG.error("Roast Recipe Registry does not contain recipe: {}", name);
		}
		return ROAST_RECIPES.getValue(resourceLocation);
	}

	public static List<RoastRecipeBase> getRecipesByName(String... names) {
		List<RoastRecipeBase> recipes = new ArrayList<>();
		for (String name : names) {
			recipes.add(getRecipeByName(name));
		}
		return recipes;
	}
}
