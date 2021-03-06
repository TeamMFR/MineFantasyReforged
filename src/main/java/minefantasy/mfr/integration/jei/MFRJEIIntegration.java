package minefantasy.mfr.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import minefantasy.mfr.config.ConfigIntegration;
import minefantasy.mfr.init.MineFantasyItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Arrays;

@JEIPlugin
public class MFRJEIIntegration implements IModPlugin {

	public MFRJEIIntegration() {
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {

		if (!ConfigIntegration.jeiIntegration)
			return;

		registry.addRecipeCategories(new JEICarpenterRecipeCategory(registry));
		// TODO: same for anvil, salvage, ...
	}

	@Override
	public void register(IModRegistry registry) {

		if (!ConfigIntegration.jeiIntegration)
			return;

		// UNUSED
		// If we want to hide some items, those can be listed here
		IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
		// e.g.:
		// blacklist.addIngredientToBlacklist(MineFantasyItems.ANCIENT_JEWEL_ADAMANT);
		// /UNUSED


		// yet again cant use this because of the messy minefantasy.mfr.item.ItemComponentMFR.getSubItems method and class
		addExtendedInfo(registry, MineFantasyItems.FLUX, ".desc_extended"); // not working yet

		addExtendedInfo(registry, new ItemStack(MineFantasyItems.FLUX), ".desc_extended"); // this works

		registry.addRecipes(JEICarpenterRecipeCategory.generateRecipes(), JEICarpenterRecipeCategory.UID);

	}

	private static void addExtendedInfo(IModRegistry registry, Item item, String... suffixes) {
		NonNullList<ItemStack> subItems = NonNullList.create();
		item.getSubItems(item.getCreativeTab(), subItems);
		for (ItemStack stack : subItems)
			addExtendedInfo(registry, stack, suffixes);
	}

	private static void addExtendedInfo(IModRegistry registry, ItemStack stack, String... suffixes) {
		String prefix = stack.getItem().getUnlocalizedName(stack);
		String[] keys = Arrays.stream(suffixes).map(s -> prefix + s).toArray(String[]::new);
		registry.addIngredientInfo(stack, VanillaTypes.ITEM, keys);
	}
}
