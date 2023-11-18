package minefantasy.mfr.integration.jei;

import mezz.jei.Internal;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.gui.GuiHelper;
import mezz.jei.runtime.JeiHelpers;
import minefantasy.mfr.config.ConfigIntegration;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

@JEIPlugin
public class JEIIntegration implements IModPlugin {

	public JEIIntegration() {
	}

	@Override
	public void registerCategories(@Nonnull IRecipeCategoryRegistration registry) {

		if (!ConfigIntegration.jeiIntegration) {
			return;
		}

		JeiHelpers jeiHelpers = Internal.getHelpers();
		GuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registry.addRecipeCategories(new JEICarpenterRecipeCategory(registry));
		registry.addRecipeCategories(new JEIAnvilRecipeCategory(registry));
		registry.addRecipeCategories(new JEIBigFurnaceRecipeCategory(registry, guiHelper));
		registry.addRecipeCategories(new JEIAlloyRecipeCategory(registry, guiHelper));
		registry.addRecipeCategories(new JEIBloomeryRecipeCategory(registry, guiHelper));
		registry.addRecipeCategories(new JEIBlastFurnaceRecipeCategory(registry, guiHelper));
		registry.addRecipeCategories(new JEIQuernRecipeCategory(registry, guiHelper));
		registry.addRecipeCategories(new JEITannerRecipeCategory(registry));
		registry.addRecipeCategories(new JEIRoastRecipeCategory(registry, guiHelper, 0, MineFantasyBlocks.OVEN, "oven"));
		registry.addRecipeCategories(new JEIRoastRecipeCategory(registry, guiHelper, 56, MineFantasyBlocks.STOVE, "stovetop"));
		// TODO: same for anvil, salvage, ...
	}

	@Override
	public void register(@Nonnull IModRegistry registry) {

		if (!ConfigIntegration.jeiIntegration)
			return;

		// UNUSED
		// If we want to hide some items, those can be listed here
		IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
		// e.g.:
		// blacklist.addIngredientToBlacklist(MineFantasyItems.ANCIENT_JEWEL_ADAMANT);
		// /UNUSED

		IStackHelper stackHelper = registry.getJeiHelpers().getStackHelper();

		List<ItemStack> fuelItemStacks = registry.getIngredientRegistry().getFuels();

		addExtendedInfo(registry, MineFantasyItems.FLUX, ".desc_extended");

		registry.addRecipes(JEICarpenterRecipeCategory.generateRecipes(stackHelper), JEICarpenterRecipeCategory.UID);
		registry.addRecipes(JEIAnvilRecipeCategory.generateRecipes(stackHelper), JEIAnvilRecipeCategory.UID);
		registry.addRecipes(JEIBigFurnaceRecipeCategory.generateRecipes(stackHelper), JEIBigFurnaceRecipeCategory.UID);
		registry.addRecipes(JEIAlloyRecipeCategory.generateRecipes(stackHelper), JEIAlloyRecipeCategory.UID);
		registry.addRecipes(JEIBloomeryRecipeCategory.generateRecipes(stackHelper, fuelItemStacks), JEIBloomeryRecipeCategory.UID);
		registry.addRecipes(JEIBlastFurnaceRecipeCategory.generateRecipes(stackHelper, fuelItemStacks), JEIBlastFurnaceRecipeCategory.UID);
		registry.addRecipes(JEIQuernRecipeCategory.generateRecipes(stackHelper), JEIQuernRecipeCategory.UID);
		registry.addRecipes(JEITannerRecipeCategory.generateRecipes(stackHelper), JEITannerRecipeCategory.UID);
		registry.addRecipes(JEIRoastRecipeCategory.generateRecipes(stackHelper, true), "minefantasyreforged:oven");
		registry.addRecipes(JEIRoastRecipeCategory.generateRecipes(stackHelper, false), "minefantasyreforged:stovetop");
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
		subtypeRegistry.useNbtForSubtypes(MineFantasyItems.BAR);
	}

	private static void addExtendedInfo(IModRegistry registry, Item item, String... suffixes) {
		NonNullList<ItemStack> subItems = NonNullList.create();
		item.getSubItems(item.getCreativeTab(), subItems);
		for (ItemStack stack : subItems)
			addExtendedInfo(registry, stack, suffixes);
	}

	private static void addExtendedInfo(IModRegistry registry, ItemStack stack, String... suffixes) {
		String prefix = stack.getItem().getTranslationKey(stack);
		String[] keys = Arrays.stream(suffixes).map(s -> prefix + s).toArray(String[]::new);
		registry.addIngredientInfo(stack, VanillaTypes.ITEM, keys);
	}
}
