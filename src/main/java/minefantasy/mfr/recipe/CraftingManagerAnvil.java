package minefantasy.mfr.recipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.recipe.factories.AnvilRecipeFactory;
import minefantasy.mfr.recipe.types.AnvilRecipeType;
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

public class CraftingManagerAnvil {
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
	private static final Method LOAD_CONSTANTS = ReflectionHelper.findMethod(JsonContext.class, "loadConstants", null, JsonObject[].class);

	public static void loadRecipes() {
		ModContainer modContainer = Loader.instance().activeModContainer();

		//noinspection ConstantConditions
		loadRecipes(modContainer, new File("config/" + Constants.CONFIG_DIRECTORY +"/custom/recipes/anvil_recipes/"), "");
		Loader.instance().getActiveModList().forEach(m -> CraftingManagerAnvil.loadRecipes(m, m.getSource(), "assets/" + m.getModId() + "/anvil_recipes"));

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
					if (AnvilRecipeType.getByNameWithModId(type, mod.getModId()) != AnvilRecipeType.NONE) {
						AnvilRecipeBase recipe = factory.parse(ctx, json);
						recipe.setRegistryName(key);
						addRecipe(recipe, mod.getModId().equals(MineFantasyReforged.MOD_ID));
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

	public static void addRecipe(AnvilRecipeBase recipe, boolean checkForExistence) {
		Item item = recipe.getAnvilRecipeOutput().getItem();
		if (ConfigCrafting.isAnvilItemCraftable(item)) {
			NonNullList<ItemStack> subItems = NonNullList.create();

			item.getSubItems(item.getCreativeTab(), subItems);
			if (subItems.stream().anyMatch(s -> recipe.getAnvilRecipeOutput().isItemEqual(s))
					&& (!checkForExistence || !ANVIL_RECIPES.containsKey(recipe.getRegistryName()))) {
				ANVIL_RECIPES.register(recipe);
			}
		}
	}

	public static ItemStack findMatchingRecipe(IAnvil anvil, AnvilCraftMatrix matrix, World world) {
		int time;
		int anvilTier;
		boolean hot;
		int hammerTier;
		int matrixItemStackCount = 0;
		String toolType;
		SoundEvent sound;
		ItemStack stack0 = ItemStack.EMPTY;
		ItemStack stack1 = ItemStack.EMPTY;

		for (int currentSlotIndex = 0; currentSlotIndex < matrix.getSizeInventory(); ++currentSlotIndex) {
			ItemStack matrixSlotStack = matrix.getStackInSlot(currentSlotIndex);

			// Logic to check if the matrix should match an item repair recipe. (The same two items next to each other in the first two slow).
			if (!matrixSlotStack.isEmpty()) {
				if (matrixItemStackCount == 0) {
					stack0 = matrixSlotStack;
				}

				if (matrixItemStackCount == 1) {
					stack1 = matrixSlotStack;
				}

				++matrixItemStackCount;
			}
		}

		// Logic to check if the matrix should match an item repair recipe. (The same two items next to each other in the first two slow).
		if (matrixItemStackCount == 2 && stack0.getItem() == stack1.getItem() && stack0.getCount() == 1 && stack1.getCount() == 1
				&& stack0.getItem().isRepairable()) {

			Item item0 = stack0.getItem();

			int item0RemainingDurability = item0.getMaxDamage() - stack0.getItemDamage();
			int itemDamageDifference = item0.getMaxDamage() - stack1.getItemDamage();
			int itemDamageModifier =  (int) (item0RemainingDurability + itemDamageDifference + item0.getMaxDamage() * 0.1);
			int newItemDamage = Math.max(0, item0.getMaxDamage() - itemDamageModifier);

			return new ItemStack(stack0.getItem(), 1, newItemDamage);
		} else {
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
				hammerTier = anvilRecipeBase.getHammerTier();
				anvilTier = anvilRecipeBase.getAnvilTier();
				hot = anvilRecipeBase.isHotOutput();
				toolType = anvilRecipeBase.getToolType();

				if (!anvilRecipeBase.useCustomTiers()){
					anvil.setProgressMax(time);
					anvil.setRequiredHammerTier(hammerTier);
					anvil.setRequiredAnvilTier(anvilTier);
				}

				anvil.setHotOutput(hot);
				anvil.setRequiredToolType(toolType);

				if (!anvilRecipeBase.getResearch().equalsIgnoreCase("tier")){
					anvil.setRequiredResearch(anvilRecipeBase.getResearch());
				}

				anvil.setRequiredSkill(anvilRecipeBase.getSkill());

				return anvilRecipeBase.getCraftingResult(matrix);
			}
			return ItemStack.EMPTY;
		}
	}

	public static AnvilRecipeBase getRecipeByName(String name) {
		ResourceLocation resourceLocation = new ResourceLocation(MineFantasyReforged.MOD_ID + ":" + name);
		if (!ANVIL_RECIPES.containsKey(resourceLocation)) {
			MineFantasyReforged.LOG.error("Anvil Recipe Registry does not contain recipe: {}", name);
		}
		return ANVIL_RECIPES.getValue(resourceLocation);
	}

	public static List<AnvilRecipeBase> getRecipesByName(String... names) {
		List<AnvilRecipeBase> recipes = new ArrayList<>();
		for (String name : names) {
			recipes.add(getRecipeByName(name));
		}
		return recipes;
	}

}
