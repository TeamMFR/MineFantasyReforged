package minefantasy.mfr.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.recipe.factories.KitchenBenchRecipeFactory;
import minefantasy.mfr.recipe.types.KitchenBenchRecipeType;
import minefantasy.mfr.util.FileUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
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
import java.util.Iterator;
import java.util.List;

public class CraftingManagerKitchenBench {

	public static final String CONFIG_RECIPE_DIRECTORY = "config/" + Constants.CONFIG_DIRECTORY + "/custom/recipes/kitchen_bench_recipes/";

	public CraftingManagerKitchenBench() {
	}

	private static final IForgeRegistry<KitchenBenchRecipeBase> KITCHEN_BENCH_RECIPES = (new RegistryBuilder<KitchenBenchRecipeBase>()).setName(new ResourceLocation(MineFantasyReforged.MOD_ID, "kitchen_bench_recipes")).setType(KitchenBenchRecipeBase.class).setMaxID(Integer.MAX_VALUE >> 5).disableSaving().allowModification().create();
	public static void init() {
		//call this so that the static final gets initialized at proper time
	}

	public static Collection<KitchenBenchRecipeBase> getRecipes() {
		return KITCHEN_BENCH_RECIPES.getValuesCollection();
	}

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final KitchenBenchRecipeFactory factory = new KitchenBenchRecipeFactory();
	private static final Method LOAD_CONSTANTS = ReflectionHelper.findMethod(JsonContext.class, "loadConstants", null, JsonObject[].class);

	public static void loadRecipes() {
		ModContainer modContainer = Loader.instance().activeModContainer();

		FileUtils.createCustomDataDirectory(CONFIG_RECIPE_DIRECTORY);

		Loader.instance().getActiveModList().forEach(m -> CraftingHelper.loadFactories(m,"assets/" + m.getModId() + "/kitchen_bench_recipes", CraftingHelper.CONDITIONS));
		//noinspection ConstantConditions
		loadRecipes(modContainer, new File(CONFIG_RECIPE_DIRECTORY), "");
		Loader.instance().getActiveModList().forEach(m -> CraftingManagerKitchenBench.loadRecipes(m, m.getSource(), "assets/" + m.getModId() + "/kitchen_bench_recipes"));

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
					if (KitchenBenchRecipeType.getByNameWithModId(type, mod.getModId()) != KitchenBenchRecipeType.NONE) {
						KitchenBenchRecipeBase recipe = factory.parse(ctx, json);
						recipe.setRegistryName(key);
						if (CraftingHelper.processConditions(json, "conditions", ctx)) {
							addRecipe(recipe, mod.getModId().equals(MineFantasyReforged.MOD_ID));
						}
					} else {
						MineFantasyReforged.LOG.info("Skipping recipe {} of type {} because it's not a MFR Kitchen Bench recipe", key, type);
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

	public static void addRecipe(KitchenBenchRecipeBase recipe, boolean checkForExistence) {
		ItemStack itemStack = recipe.getKitchenBenchRecipeOutput();
		if (ConfigCrafting.isKitchenBenchItemCraftable(itemStack)) {
			NonNullList<ItemStack> subItems = NonNullList.create();

			itemStack.getItem().getSubItems(itemStack.getItem().getCreativeTab(), subItems);
			if (subItems.stream().anyMatch(s -> recipe.getKitchenBenchRecipeOutput().isItemEqual(s))
					&& (!checkForExistence || !KITCHEN_BENCH_RECIPES.containsKey(recipe.getRegistryName()))) {
				KITCHEN_BENCH_RECIPES.register(recipe);
			}
		}
	}

	public static ItemStack findMatchingRecipe(IKitchenBench kitchenBench, KitchenBenchCraftMatrix matrix, World world) {
		int time;
		int kitchenBenchTier;
		int toolTier;
		String toolType;
		SoundEvent sound;

		Iterator<KitchenBenchRecipeBase> recipeIterator = getRecipes().iterator();
		KitchenBenchRecipeBase kitchenBenchRecipeBase = null;

		while (recipeIterator.hasNext()) {
			KitchenBenchRecipeBase rec = recipeIterator.next();

			if (rec.matches(matrix, world)) {
				kitchenBenchRecipeBase = rec;
				break;
			}
		}

		if (kitchenBenchRecipeBase != null) {
			time = kitchenBenchRecipeBase.getCraftTime();
			toolTier = kitchenBenchRecipeBase.getToolTier();
			kitchenBenchTier = kitchenBenchRecipeBase.getKitchenBenchTier();
			toolType = kitchenBenchRecipeBase.getToolType();
			sound = kitchenBenchRecipeBase.getSound();

			kitchenBench.setProgressMax(time);
			kitchenBench.setRequiredToolTier(toolTier);
			kitchenBench.setRequiredKitchenBenchTier(kitchenBenchTier);

			kitchenBench.setRequiredToolType(toolType);
			kitchenBench.setCraftingSound(sound);
			kitchenBench.setRequiredResearch(kitchenBenchRecipeBase.getResearch());
			kitchenBench.setRequiredSkill(kitchenBenchRecipeBase.getSkill());
			kitchenBench.setDirtyProgressAmount(kitchenBenchRecipeBase.getDirtyProgressAmount());

			return kitchenBenchRecipeBase.getCraftingResult(matrix);
		}
		return ItemStack.EMPTY;
	}

	public static KitchenBenchRecipeBase getRecipeByName(String name) {
		ResourceLocation resourceLocation = new ResourceLocation(MineFantasyReforged.MOD_ID + ":" + name);
		if (!KITCHEN_BENCH_RECIPES.containsKey(resourceLocation)) {
			MineFantasyReforged.LOG.error("Kitchen Bench Recipe Registry does not contain recipe: {}", name);
		}
		return KITCHEN_BENCH_RECIPES.getValue(resourceLocation);
	}

	public static List<KitchenBenchRecipeBase> getRecipesByName(String... names) {
		List<KitchenBenchRecipeBase> recipes = new ArrayList<>();
		for (String name : names) {
			recipes.add(getRecipeByName(name));
		}
		return recipes;
	}
}
