package minefantasy.mfr.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.mixin.InvokerLoadConstants;
import minefantasy.mfr.recipe.factories.AnvilRecipeFactory;
import minefantasy.mfr.recipe.types.AnvilRecipeType;
import minefantasy.mfr.tile.TileEntityAnvil;
import minefantasy.mfr.util.FileUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CraftingManagerAnvil {

	public static final String RECIPE_FOLDER_PATH = "/recipes_mfr/anvil_recipes/";

	public static final String CONFIG_RECIPE_DIRECTORY = "config/" + Constants.CONFIG_DIRECTORY + "/custom/recipes/anvil_recipes/";

	public CraftingManagerAnvil() {
	}

	private static final IForgeRegistry<AnvilRecipeBase> ANVIL_RECIPES = (new RegistryBuilder<AnvilRecipeBase>()).setName(new ResourceLocation(MineFantasyReforged.MOD_ID, "anvil_recipes")).setType(AnvilRecipeBase.class).setMaxID(Integer.MAX_VALUE >> 5).disableSaving().allowModification().create();
	public static void init() {
		//call this so that the static final gets initialized at proper time
	}

	public static Collection<AnvilRecipeBase> getRecipes() {
		return ANVIL_RECIPES.getValuesCollection();
	}

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final AnvilRecipeFactory factory = new AnvilRecipeFactory();

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
					if (AnvilRecipeType.getByNameWithModId(type, mod.getModId()) != AnvilRecipeType.NONE) {
						AnvilRecipeBase recipe = factory.parse(ctx, json);
						if (CraftingHelper.processConditions(json, "conditions", ctx)) {
							addRecipe(recipe, mod.getModId().equals(MineFantasyReforged.MOD_ID), key);
						}
					} else {
						MineFantasyReforged.LOG.info("Skipping recipe {} of type {} because it's not a MFR Anvil recipe", key, type);
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

	public static void addRecipe(AnvilRecipeBase recipe, boolean checkForExistence, ResourceLocation key) {
		ItemStack itemStack = recipe.getAnvilRecipeOutput();
		if (ConfigCrafting.isAnvilItemCraftable(itemStack)) {
			NonNullList<ItemStack> subItems = NonNullList.create();

			recipe.setRegistryName(key);
			itemStack.getItem().getSubItems(itemStack.getItem().getCreativeTab(), subItems);
			if (subItems.stream().anyMatch(s -> recipe.getAnvilRecipeOutput().isItemEqual(s))
					&& (!checkForExistence || !ANVIL_RECIPES.containsKey(recipe.getRegistryName()))) {
				ANVIL_RECIPES.register(recipe);
			}
		}
	}

	public static AnvilRecipeBase findMatchingRecipe(TileEntityAnvil anvil, AnvilCraftMatrix matrix, World world) {
		int time;
		int anvilTier;
		int toolTier;

		//// Normal, registered recipes.
		Iterator<AnvilRecipeBase> recipeIterator = getRecipes().iterator();
		AnvilRecipeBase anvilRecipeBase = null;

		while (recipeIterator.hasNext()) {
			AnvilRecipeBase rec = recipeIterator.next();

			if (rec.matches(matrix, world)) {
				anvilRecipeBase = rec;
				break;
			}
		}

		if (anvilRecipeBase != null) {
			time = anvilRecipeBase.getCraftTime();
			toolTier = anvilRecipeBase.getToolTier();
			anvilTier = anvilRecipeBase.getAnvilTier();

			if (!anvilRecipeBase.useCustomTiers()){
				anvil.setProgressMax(time);
				anvil.setRequiredToolTier(toolTier);
				anvil.setRequiredAnvilTier(anvilTier);
			}

			if (!anvilRecipeBase.getRequiredResearch().equalsIgnoreCase("tier")){
				anvil.setRequiredResearch(anvilRecipeBase.getRequiredResearch());
			}

			return anvilRecipeBase;
		}
		return null;
	}

	public static AnvilRecipeBase findRecipeByOutput(Ingredient output) {
		for (AnvilRecipeBase anvilRecipe : getRecipes()) {
			if (output.apply(anvilRecipe.getAnvilRecipeOutput())) {
				return anvilRecipe;
			}
		}
		return null;
	}

	public static AnvilRecipeBase getRecipeByName(String name, boolean isNullable) {
		ResourceLocation resourceLocation = new ResourceLocation(MineFantasyReforged.MOD_ID + ":" + name);
		if (!ANVIL_RECIPES.containsKey(resourceLocation) && !isNullable) {
			MineFantasyReforged.LOG.error("Anvil Recipe Registry does not contain recipe: {}", name);
		}
		return ANVIL_RECIPES.getValue(resourceLocation);
	}

	public static List<AnvilRecipeBase> getRecipesByName(String... names) {
		List<AnvilRecipeBase> recipes = new ArrayList<>();
		for (String name : names) {
			recipes.add(getRecipeByName(name, false));
		}
		return recipes;
	}

	public static String getRecipeName(AnvilRecipeBase recipe) {
		ResourceLocation recipeLocation = ANVIL_RECIPES.getKey(recipe);
		if (recipeLocation != null) {
			return recipeLocation.getPath();
		}
		return "";
	}
}
