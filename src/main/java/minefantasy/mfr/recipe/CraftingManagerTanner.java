package minefantasy.mfr.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.mixin.InvokerLoadConstants;
import minefantasy.mfr.recipe.factories.TannerRecipeFactory;
import minefantasy.mfr.recipe.types.TannerRecipeType;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.FileUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CraftingManagerTanner {

	public static final String RECIPE_FOLDER_PATH = "/recipes_mfr/tanner_recipes";

	public static final String CONFIG_RECIPE_DIRECTORY = "config/" + Constants.CONFIG_DIRECTORY + "/custom/recipes/tanner_recipes/";

	public CraftingManagerTanner() {
	}

	private static final IForgeRegistry<TannerRecipeBase> TANNER_RECIPES = (new RegistryBuilder<TannerRecipeBase>()).setName(new ResourceLocation(MineFantasyReforged.MOD_ID, "tanner_recipes")).setType(TannerRecipeBase.class).setMaxID(Integer.MAX_VALUE >> 5).disableSaving().allowModification().create();
	private static final Set<String> TANNER_RESEARCHES = new HashSet<>();

	public static void init() {
		//call this so that the static final gets initialized at proper time
	}

	public static Collection<TannerRecipeBase> getRecipes() {
		return TANNER_RECIPES.getValuesCollection();
	}

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final TannerRecipeFactory factory = new TannerRecipeFactory();

	public static void loadRecipes() {
		ModContainer modContainer = Loader.instance().activeModContainer();

		FileUtils.createCustomDataDirectory(CONFIG_RECIPE_DIRECTORY);
		Loader.instance().getActiveModList().forEach(m -> CraftingHelper
				.loadFactories(m,"assets/" + m.getModId() + RECIPE_FOLDER_PATH, CraftingHelper.CONDITIONS));
		//noinspection ConstantConditions
		loadRecipes(modContainer, new File(CONFIG_RECIPE_DIRECTORY), "");
		Loader.instance().getActiveModList().forEach(m ->
				loadRecipesForEachModDirectory(m, m.getSource(), "assets/" + m.getModId() + RECIPE_FOLDER_PATH));

		Loader.instance().setActiveModContainer(modContainer);
	}

	private static void loadRecipesForEachModDirectory(ModContainer mod, File source, String base) {
		File recipeDirectory = new File(source.toPath().resolve(base).toString());
		File[] files = recipeDirectory.listFiles();
		if (files != null) {
			for (File d : files) {
				if (d.isDirectory()) {
					Path modId = d.toPath().getName(d.toPath().getNameCount() - 1);
					if (!Loader.isModLoaded(modId.toString())) {
						return;
					}
					String modBase = base + "/" + modId;
					loadRecipes(mod, source, modBase);
				}
			}
		}
	}

	private static void loadRecipes(ModContainer mod, File source, String base) {
		JsonContext ctx = new JsonContext(mod.getModId());

		FileUtils.findFiles(source, base, root -> {
			Path fPath = root.resolve("_constants.json");
			if (fPath != null && Files.exists(fPath)) {
				BufferedReader reader = null;
				try {
					reader = Files.newBufferedReader(fPath);
					InvokerLoadConstants.loadContext(ctx, new File(fPath.toString()));
				}
				catch (IOException e) {
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
					if (TannerRecipeType.getByNameWithModId(type, mod.getModId()) != TannerRecipeType.NONE) {
						TannerRecipeBase recipe = factory.parse(ctx, json);
						if (CraftingHelper.processConditions(json, "conditions", ctx)) {
							addRecipe(recipe, mod.getModId().equals(MineFantasyReforged.MOD_ID), key);
						}
					} else {
						MineFantasyReforged.LOG.info("Skipping recipe {} of type {} because it's not a MFR Tanner recipe", key, type);
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

	public static void addRecipe(TannerRecipeBase recipe, boolean checkForExistence, ResourceLocation key) {
		ItemStack itemStack = recipe.getTannerRecipeOutput();
		if (ConfigCrafting.isTannerItemCraftable(itemStack)) {
			NonNullList<ItemStack> subItems = NonNullList.create();

			recipe.setRegistryName(key);
			itemStack.getItem().getSubItems(itemStack.getItem().getCreativeTab(), subItems);
			if (subItems.stream().anyMatch(s -> recipe.getTannerRecipeOutput().isItemEqual(s))
					&& (!checkForExistence || !TANNER_RECIPES.containsKey(recipe.getRegistryName()))) {
				TANNER_RECIPES.register(recipe);
				TANNER_RESEARCHES.add(recipe.getRequiredResearch());
			}
		}
	}

	public static TannerRecipeBase findMatchingRecipe(ItemStack input, Set<String> knownResearches) {
		//// Normal, registered recipes.

		for (TannerRecipeBase rec : getRecipes()) {
			if (rec.matches(input)) {
				if (StringUtils.isEmpty(rec.getRequiredResearch())
						|| knownResearches.contains(rec.getRequiredResearch())) {
					return rec;
				}
			}
		}
		return null;
	}

	public static TannerRecipeBase findRecipeByOutput(ItemStack output) {
		for (TannerRecipeBase recipe : getRecipes()) {
			if (CustomToolHelper.areEqual(recipe.getTannerRecipeOutput(), output)) {
				return recipe;
			}
		}
		return null;
	}

	public static TannerRecipeBase getRecipeByName(String name, boolean isNullable) {
		ResourceLocation resourceLocation = new ResourceLocation(MineFantasyReforged.MOD_ID + ":" + name);
		if (!TANNER_RECIPES.containsKey(resourceLocation) && !isNullable) {
			MineFantasyReforged.LOG.error("Tanner Recipe Registry does not contain recipe: {}", name);
		}
		return TANNER_RECIPES.getValue(resourceLocation);
	}

	public static List<TannerRecipeBase> getRecipesByName(String... names) {
		List<TannerRecipeBase> recipes = new ArrayList<>();
		for (String name : names) {
			recipes.add(getRecipeByName(name, false));
		}
		return recipes;
	}

	public static String getRecipeName(TannerRecipeBase recipe) {
		ResourceLocation recipeLocation = TANNER_RECIPES.getKey(recipe);
		if (recipeLocation != null) {
			return recipeLocation.getPath();
		}
		return "";
	}

	public static Set<String> getTannerResearches() {
		return TANNER_RESEARCHES;
	}
}
