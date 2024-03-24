package minefantasy.mfr.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.mixin.InvokerLoadConstants;
import minefantasy.mfr.recipe.factories.QuernRecipeFactory;
import minefantasy.mfr.recipe.types.QuernRecipeType;
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

public class CraftingManagerQuern {

	public static final String RECIPE_FOLDER_PATH = "/recipes_mfr/quern_recipes";

	public static final String CONFIG_RECIPE_DIRECTORY = "config/" + Constants.CONFIG_DIRECTORY + "/custom/recipes/quern_recipes/";

	public CraftingManagerQuern() {

	}

	private static final IForgeRegistry<QuernRecipeBase> QUERN_RECIPES = (new RegistryBuilder<QuernRecipeBase>()).setName(new ResourceLocation(MineFantasyReforged.MOD_ID, "quern_recipes")).setType(QuernRecipeBase.class).setMaxID(Integer.MAX_VALUE >> 5).disableSaving().allowModification().create();
	private static final Set<String> QUERN_RESEARCHES = new HashSet<>();

	public static void init() {
		//call this so that the static final gets initialized at proper time
	}

	public static Collection<QuernRecipeBase> getRecipes() {
		return QUERN_RECIPES.getValuesCollection();
	}

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final QuernRecipeFactory factory = new QuernRecipeFactory();

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
					if (QuernRecipeType.getByNameWithModId(type, mod.getModId()) != QuernRecipeType.NONE) {
						QuernRecipeBase recipe = factory.parse(ctx, json);
						if (CraftingHelper.processConditions(json, "conditions", ctx)) {
							addRecipe(recipe, mod.getModId().equals(MineFantasyReforged.MOD_ID), key);
						}
					} else {
						MineFantasyReforged.LOG.info("Skipping recipe {} of type {} because it's not a MFR Quern recipe", key, type);
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

	public static void addRecipe(QuernRecipeBase recipe, boolean checkForExistence, ResourceLocation key) {
		ItemStack itemStack = recipe.getQuernRecipeOutput();
		if (ConfigCrafting.isQuernItemCraftable(itemStack)) {
			NonNullList<ItemStack> subItems = NonNullList.create();

			recipe.setRegistryName(key);
			itemStack.getItem().getSubItems(itemStack.getItem().getCreativeTab(), subItems);
			if (subItems.stream().anyMatch(s -> recipe.getQuernRecipeOutput().isItemEqual(s))
					&& (!checkForExistence || !QUERN_RECIPES.containsKey(recipe.getRegistryName()))) {
				QUERN_RECIPES.register(recipe);
				String requiredResearch = recipe.getRequiredResearch();
				if (!requiredResearch.equals("none")) {
					QUERN_RESEARCHES.add(requiredResearch);
				}
			}
		}
	}

	public static QuernRecipeBase findMatchingRecipe(ItemStack input, ItemStack potInput, Set<String> knownResearches) {
		//// Normal, registered recipes.

		for (QuernRecipeBase rec : getRecipes()) {
			if (rec.matches(input, potInput)) {
				if (rec.getRequiredResearch().equals("none")
						|| knownResearches.contains(rec.getRequiredResearch())) {
					return rec;
				}
			}
		}
		return null;
	}

	public static boolean findMatchingInputs(ItemStack input, Set<String> knownResearches) {
		for (QuernRecipeBase rec : getRecipes()) {
			if (rec.inputMatches(input)) {
				if (StringUtils.isEmpty(rec.getRequiredResearch())
						|| knownResearches.contains(rec.getRequiredResearch())) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean findMatchingPotInputs(ItemStack potInputs, Set<String> knownResearches) {
		for (QuernRecipeBase rec : getRecipes()) {
			if (rec.inputPotMatches(potInputs)) {
				if (StringUtils.isEmpty(rec.getRequiredResearch())
						|| knownResearches.contains(rec.getRequiredResearch())) {
					return true;
				}
			}
		}
		return false;
	}

	public static QuernRecipeBase findRecipeByOutput(ItemStack output) {
		for (QuernRecipeBase recipe : getRecipes()) {
			if (CustomToolHelper.areEqual(recipe.getQuernRecipeOutput(), output)) {
				return recipe;
			}
		}
		return null;
	}

	public static QuernRecipeBase getRecipeByName(String name, boolean isNullable) {
		ResourceLocation resourceLocation = new ResourceLocation(MineFantasyReforged.MOD_ID + ":" + name);
		if (!QUERN_RECIPES.containsKey(resourceLocation) && !isNullable) {
			MineFantasyReforged.LOG.error("Quern Recipe Registry does not contain recipe: {}", name);
		}
		return QUERN_RECIPES.getValue(resourceLocation);
	}

	public static List<QuernRecipeBase> getRecipesByName(String... names) {
		List<QuernRecipeBase> recipes = new ArrayList<>();
		for (String name : names) {
			recipes.add(getRecipeByName(name, false));
		}
		return recipes;
	}

	public static String getRecipeName(QuernRecipeBase recipe) {
		ResourceLocation recipeLocation = QUERN_RECIPES.getKey(recipe);
		if (recipeLocation != null) {
			return recipeLocation.getPath();
		}
		return "";
	}

	public static Set<String> getQuernResearches() {
		return QUERN_RESEARCHES;
	}
}
