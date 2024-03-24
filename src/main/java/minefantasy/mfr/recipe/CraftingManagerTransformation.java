package minefantasy.mfr.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.mixin.InvokerLoadConstants;
import minefantasy.mfr.recipe.factories.TransformationRecipeFactory;
import minefantasy.mfr.recipe.types.TransformationRecipeType;
import minefantasy.mfr.util.FileUtils;
import net.minecraft.block.state.IBlockState;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CraftingManagerTransformation {

	public static final String RECIPE_FOLDER_PATH = "/recipes_mfr/transformation_recipes";

	public static final String CONFIG_RECIPE_DIRECTORY = "config/" + Constants.CONFIG_DIRECTORY + "/custom/recipes/transformation_recipes/";

	public CraftingManagerTransformation() {
	}

	private static final IForgeRegistry<TransformationRecipeBase> TRANSFORMATION_RECIPES = (new RegistryBuilder<TransformationRecipeBase>()).setName(new ResourceLocation(MineFantasyReforged.MOD_ID, "transformation_recipes")).setType(TransformationRecipeBase.class).setMaxID(Integer.MAX_VALUE >> 5).disableSaving().allowModification().create();
	public static void init() {
		//call this so that the static final gets initialized at proper time
	}

	public static Collection<TransformationRecipeBase> getRecipes() {
		return TRANSFORMATION_RECIPES.getValuesCollection();
	}

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final TransformationRecipeFactory factory = new TransformationRecipeFactory();

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
					if (TransformationRecipeType.getByNameWithModId(type, mod.getModId()) != TransformationRecipeType.NONE) {
						TransformationRecipeBase recipe = factory.parse(ctx, json);
						if (CraftingHelper.processConditions(json, "conditions", ctx)) {
							addRecipe(recipe, mod.getModId().equals(MineFantasyReforged.MOD_ID), key);
						}
					} else {
						MineFantasyReforged.LOG.info("Skipping recipe {} of type {} because it's not a MFR Transformation recipe", key, type);
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

	public static void addRecipe(TransformationRecipeBase recipe, boolean checkForExistence, ResourceLocation key) {
		recipe.setRegistryName(key);
		if (recipe instanceof TransformationRecipeStandard) {
			addStandardRecipe((TransformationRecipeStandard) recipe, checkForExistence);
		}
		else if (recipe instanceof TransformationRecipeBlockState) {
			addBlockStateRecipe((TransformationRecipeBlockState) recipe, checkForExistence);
		}
	}

	private static void addBlockStateRecipe(TransformationRecipeBlockState recipe, boolean checkForExistence) {
		IBlockState state = recipe.getOutput();
		if (ConfigCrafting.isBlockStateTransformable(state)) {
			if (!checkForExistence || !TRANSFORMATION_RECIPES.containsKey(recipe.getRegistryName())) {
				TRANSFORMATION_RECIPES.register(recipe);
			}

			List<IBlockState> states = state.getBlock().getBlockState().getValidStates();

			if (states.stream().anyMatch(s -> recipe.getOutput().equals(state))
					&& (!checkForExistence || !TRANSFORMATION_RECIPES.containsKey(recipe.getRegistryName()))) {
				TRANSFORMATION_RECIPES.register(recipe);
			}
		}
	}

	private static void addStandardRecipe(TransformationRecipeStandard recipe, boolean checkForExistence) {
		ItemStack itemStack = recipe.getOutput();
		if (ConfigCrafting.isItemTransformable(itemStack)) {
			NonNullList<ItemStack> subItems = NonNullList.create();

			itemStack.getItem().getSubItems(itemStack.getItem().getCreativeTab(), subItems);
			if (subItems.stream().anyMatch(s -> recipe.getOutput().isItemEqual(s))
					&& (!checkForExistence || !TRANSFORMATION_RECIPES.containsKey(recipe.getRegistryName()))) {
				TRANSFORMATION_RECIPES.register(recipe);
			}
		}
	}

	public static TransformationRecipeBase findMatchingRecipe(ItemStack tool, ItemStack input, IBlockState state) {
		//// Normal, registered recipes.

		for (TransformationRecipeBase rec : getRecipes()) {
			if (rec.matches(tool, input, state)) {
				return rec;
			}
		}
		return null;
	}

	public static TransformationRecipeBase getRecipeByName(String name) {
		ResourceLocation resourceLocation = new ResourceLocation(MineFantasyReforged.MOD_ID + ":" + name);
		if (!TRANSFORMATION_RECIPES.containsKey(resourceLocation)) {
			MineFantasyReforged.LOG.error("Transformation Recipe Registry does not contain recipe: {}", name);
		}
		return TRANSFORMATION_RECIPES.getValue(resourceLocation);
	}

	public static List<TransformationRecipeBase> getRecipesByName(String... names) {
		List<TransformationRecipeBase> recipes = new ArrayList<>();
		for (String name : names) {
			recipes.add(getRecipeByName(name));
		}
		return recipes;
	}
}
