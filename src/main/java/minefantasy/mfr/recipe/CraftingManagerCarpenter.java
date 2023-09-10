package minefantasy.mfr.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.recipe.factories.CarpenterRecipeFactory;
import minefantasy.mfr.recipe.types.CarpenterRecipeType;
import minefantasy.mfr.util.FileUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
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

public class CraftingManagerCarpenter {

	public static final String CONFIG_RECIPE_DIRECTORY = "config/" + Constants.CONFIG_DIRECTORY + "/custom/recipes/carpenter_recipes/";

	public CraftingManagerCarpenter() {
	}

	private static final IForgeRegistry<CarpenterRecipeBase> CARPENTER_RECIPES = (new RegistryBuilder<CarpenterRecipeBase>()).setName(new ResourceLocation(MineFantasyReforged.MOD_ID, "carpenter_recipes")).setType(CarpenterRecipeBase.class).setMaxID(Integer.MAX_VALUE >> 5).disableSaving().allowModification().create();
	public static void init() {
		//call this so that the static final gets initialized at proper time
	}

	public static Collection<CarpenterRecipeBase> getRecipes() {
		return CARPENTER_RECIPES.getValuesCollection();
	}

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final CarpenterRecipeFactory factory = new CarpenterRecipeFactory();
	private static final Method LOAD_CONSTANTS = ReflectionHelper.findMethod(JsonContext.class, "loadConstants", null, JsonObject[].class);

	public static void loadRecipes() {
		ModContainer modContainer = Loader.instance().activeModContainer();

		FileUtils.createCustomDataDirectory(CONFIG_RECIPE_DIRECTORY);
		//noinspection ConstantConditions
		loadRecipes(modContainer, new File(CONFIG_RECIPE_DIRECTORY), "");
		Loader.instance().getActiveModList().forEach(m -> CraftingManagerCarpenter.loadRecipes(m, m.getSource(), "assets/" + m.getModId() + "/carpenter_recipes"));

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
					if (CarpenterRecipeType.getByNameWithModId(type, mod.getModId()) != CarpenterRecipeType.NONE) {
						CarpenterRecipeBase recipe = factory.parse(ctx, json);
						recipe.setRegistryName(key);
						addRecipe(recipe, mod.getModId().equals(MineFantasyReforged.MOD_ID));
					} else {
						MineFantasyReforged.LOG.info("Skipping recipe {} of type {} because it's not a MFR Carpenter recipe", key, type);
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

	public static void addRecipe(CarpenterRecipeBase recipe, boolean checkForExistence) {
		ItemStack itemStack = recipe.getCarpenterRecipeOutput();
		if (ConfigCrafting.isCarpenterItemCraftable(itemStack)) {
			NonNullList<ItemStack> subItems = NonNullList.create();

			itemStack.getItem().getSubItems(itemStack.getItem().getCreativeTab(), subItems);
			if (subItems.stream().anyMatch(s -> recipe.getCarpenterRecipeOutput().isItemEqual(s))
					&& (!checkForExistence || !CARPENTER_RECIPES.containsKey(recipe.getRegistryName()))) {
				CARPENTER_RECIPES.register(recipe);
			}
		}
	}

	public static ItemStack findMatchingRecipe(ICarpenter carpenter, CarpenterCraftMatrix matrix, World world) {
		int time;
		int carpenterTier;
		int toolTier;
		int var2 = 0;
		String toolType;
		SoundEvent sound;
		ItemStack var3 = ItemStack.EMPTY;
		ItemStack var4 = ItemStack.EMPTY;

		for (int var5 = 0; var5 < matrix.getSizeInventory(); ++var5) {
			ItemStack matrixSlot = matrix.getStackInSlot(var5);

			if (!matrixSlot.isEmpty()) {
				if (var2 == 0) {
					var3 = matrixSlot;
				}

				if (var2 == 1) {
					var4 = matrixSlot;
				}

				++var2;
			}
		}

		if (var2 == 2 && var3.getItem() == var4.getItem() && var3.getCount() == 1 && var4.getCount() == 1
				&& var3.getItem().isRepairable()) {
			Item var10 = var3.getItem();
			int var12 = var10.getMaxDamage() - var3.getItemDamage();
			int var7 = var10.getMaxDamage() - var4.getItemDamage();
			int var8 = var12 + var7 + var10.getMaxDamage() * 10 / 100;
			int var9 = var10.getMaxDamage() - var8;

			if (var9 < 0) {
				var9 = 0;
			}

			return new ItemStack(var3.getItem(), 1, var9);
		} else {
			Iterator<CarpenterRecipeBase> recipeIterator = getRecipes().iterator();
			CarpenterRecipeBase carpenterRecipeBase = null;

			while (recipeIterator.hasNext()) {
				CarpenterRecipeBase rec = recipeIterator.next();

				if (rec.matches(matrix, world)) {
					carpenterRecipeBase = rec;
					break;
				}
			}

			if (carpenterRecipeBase != null) {
				time = carpenterRecipeBase.getCraftTime();
				toolTier = carpenterRecipeBase.getToolTier();
				carpenterTier = carpenterRecipeBase.getCarpenterTier();
				toolType = carpenterRecipeBase.getToolType();
				sound = carpenterRecipeBase.getSound();

				if (!carpenterRecipeBase.useCustomTiers()) {
					carpenter.setProgressMax(time);
					carpenter.setRequiredToolTier(toolTier);
					carpenter.setRequiredCarpenterTier(carpenterTier);
				}

				carpenter.setRequiredToolType(toolType);
				carpenter.setCraftingSound(sound);
				carpenter.setRequiredResearch(carpenterRecipeBase.getResearch());
				carpenter.setRequiredSkill(carpenterRecipeBase.getSkill());

				return carpenterRecipeBase.getCraftingResult(matrix);
			}
			return ItemStack.EMPTY;
		}
	}

	public static CarpenterRecipeBase getRecipeByName(String name) {
		ResourceLocation resourceLocation = new ResourceLocation(MineFantasyReforged.MOD_ID + ":" + name);
		if (!CARPENTER_RECIPES.containsKey(resourceLocation)) {
			MineFantasyReforged.LOG.error("Carpenter Recipe Registry does not contain recipe: {}", name);
		}
		return CARPENTER_RECIPES.getValue(resourceLocation);
	}

	public static List<CarpenterRecipeBase> getRecipesByName(String... names) {
		List<CarpenterRecipeBase> recipes = new ArrayList<>();
		for (String name : names) {
			recipes.add(getRecipeByName(name));
		}
		return recipes;
	}
}
